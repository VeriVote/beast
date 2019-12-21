package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionsimulator.model.ElectionSimulationModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NEWRowOfValues {
    private double elementHeight;
    private double elementWidth;
    private int rowIndex;

    private ArrayList<String> values;
    private ArrayList<TextField> fields;
    private ElectionTypeContainer container;

    private int amountOfCandidates = 0;
    private int amountOfVoters = 0;
    private int amountOfSeats = 0;
    private ElectionSimulationModel parent;
    private boolean disabled;

    private final boolean isTwoDim;
    private int rowSize;
    private boolean blocked = true;

    public NEWRowOfValues(ElectionSimulationModel parent,
                          ElectionTypeContainer container, int amountOfCandidates,
                          int amountOfVoters, int amountOfSeats, int rowIndex,
                          double elementWidth, double elementHeight) {
        this.parent = parent;
        this.container = container;
        this.rowIndex = rowIndex;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
        values = new ArrayList<>(rowSize);
        fields = new ArrayList<>(rowSize);
        this.amountOfSeats = amountOfSeats;
        this.amountOfVoters = amountOfVoters;
        this.isTwoDim = (container.getInputType().getAmountOfDimensions() == 2);
        this.setRowSize(1);
    }

    public synchronized void addColumn() {
        while (values.size() <= rowSize) {
            values.add("0");
        }
        TextField field = new TextField(values.get(rowSize));
        field.setMinSize(elementWidth, elementHeight);
        field.setMaxSize(elementWidth, elementHeight);
        field.setPrefSize(elementWidth, elementHeight);
        fields.add(field);
        field.textProperty().addListener(new ChangeListener<String>() {
            int position = fields.indexOf(field);
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                Platform.runLater(() -> {
                    if (!blocked) {
                        checkAndInsertValue(newValue, position, false);
                    } else {
                        field.setText(values.get(position));
                        blocked = false;
                    }
                });
            }
        });
        rowSize++;
        update();
    }

    public synchronized void removeColumn() {
        if (fields.size() > 0) {
            rowSize--;
        }
        update();
    }

    public void update() {
        if (!disabled) {
            for (Iterator<TextField> iterator = fields.iterator(); iterator.hasNext();) {
                TextField textField = (TextField) iterator.next();
                parent.getInputGridPane().getChildren().remove(textField);
            }
            int iterations = 1;
            if (isTwoDim) {
                iterations = rowSize;
            }
            for (int i = 0; i < iterations; i++) {
                TextField field = fields.get(i);
                field.setText(values.get(i));
                parent.getInputGridPane().add(field, i, rowIndex);
            }
        }
    }

    private void checkAndInsertValue(String newValue, int positionInRow, boolean block) {
        List<NEWRowOfValues> allRows = parent.getRows();
        allRows.remove(this); // in case that this row is created before it is saved by its parent
        allRows.add(this);
        String vettedValue = container.getInputType().vetValue(container, allRows, rowIndex,
                                                               positionInRow, newValue);
        values.set(positionInRow, vettedValue);
        if (positionInRow < fields.size()) {
            fields.get(positionInRow).setText(vettedValue);
        }
        update();
    }

    /**
     * 
     * @return the values of this row, from 0 up to
     */
    public List<String> getValues() {
        if (rowSize == 0) {
            throw new IllegalArgumentException();
        } else {
            if (isTwoDim) {
                return values.subList(0, rowSize);
            } else {
                return values.subList(0, 1);
            }
        }
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
        this.setRowSize(values.size());
    }

    public void setContainer(ElectionTypeContainer container) {
        this.container = container;
        for (int i = 0; i < values.size(); i++) {
            checkAndInsertValue("0", i, false);
        }
    }

    public int getAmountCandidates() {
        return amountOfCandidates;
    }

    public int getAmountVoters() {
        return amountOfVoters;
    }

    public int getAmountSeats() {
        return amountOfSeats;
    }
    //
    // private synchronized void addValueEnforcer(TextField field, int index) {
    // System.out.println(field.getText());
    // String vettedValue =
    // this.container.getInputType().vetValue(field.getText(), container, this);
    // field.setText(vettedValue);
    // values.set(index, vettedValue);
    // }

    public void setRowSize(int rowSize) {
        if (this.rowSize < rowSize) {
            if (!isTwoDim) {
                if (this.rowSize == 0) {
                    addColumn();
                }
            } else {
                while (this.rowSize < rowSize) {
                    addColumn();
                }
            }
        } else if (this.rowSize > rowSize) {
            while (this.rowSize > rowSize) {
                removeColumn();
            }
        }
        this.rowSize = rowSize;
        updateVetting();
    }

    public void setCandidates(int amountOfCandidates) {
        this.amountOfCandidates = amountOfCandidates;
        updateVetting();
    }

    public void setVoters(int amountOfVoters) {
        this.amountOfVoters = amountOfVoters;
        updateVetting();
    }

    public void setSeats(int amountOfSeats) {
        this.amountOfSeats = amountOfSeats;
        updateVetting();
    }

    /**
     * vets all numbers in the fields again, in case they are dependent of a
     * variable that was changed
     */
    public void updateVetting() {
        for (int i = 0; i < values.size(); i++) {
            checkAndInsertValue(values.get(i), i, true);
        }
    }

    public void enable() {
        this.disabled = false;
        update();
    }

    public void disable() {
        this.disabled = true;
        for (Iterator<TextField> iterator = fields.iterator(); iterator.hasNext();) {
            TextField textField = (TextField) iterator.next();
            parent.getInputGridPane().getChildren().remove(textField);
        }
    }

    public void setValue(int x, String value) {
        checkAndInsertValue(value, x, true);
    }
}