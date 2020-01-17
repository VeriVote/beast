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

/**
 * The Class NEWRowOfValues.
 *
 * @author Lukas Stapelbroek
 */
public class NEWRowOfValues {

    /** The element height. */
    private double elementHeight;

    /** The element width. */
    private double elementWidth;

    /** The row index. */
    private int rowIndex;

    /** The values. */
    private ArrayList<String> values;

    /** The fields. */
    private ArrayList<TextField> fields;

    /** The container. */
    private ElectionTypeContainer container;

    /** The amount of candidates. */
    private int amountOfCandidates = 0;

    /** The amount of voters. */
    private int amountOfVoters = 0;

    /** The amount of seats. */
    private int amountOfSeats = 0;

    /** The parent. */
    private ElectionSimulationModel parent;

    /** The disabled. */
    private boolean disabled;

    /** The is two dim. */
    private final boolean isTwoDim;

    /** The row size. */
    private int rowSize;

    /** The blocked. */
    private boolean blocked = true;

    /**
     * The constructor.
     *
     * @param parentModel
     *            the parent model
     * @param elecTypeContainer
     *            the elec type container
     * @param amountCandidates
     *            the amount candidates
     * @param amountVoters
     *            the amount voters
     * @param amountSeats
     *            the amount seats
     * @param rowIdx
     *            the row idx
     * @param elemWidth
     *            the elem width
     * @param elemHeight
     *            the elem height
     */
    public NEWRowOfValues(final ElectionSimulationModel parentModel,
                          final ElectionTypeContainer elecTypeContainer,
                          final int amountCandidates, final int amountVoters,
                          final int amountSeats, final int rowIdx,
                          final double elemWidth, final double elemHeight) {
        this.parent = parentModel;
        this.container = elecTypeContainer;
        this.rowIndex = rowIdx;
        this.elementWidth = elemWidth;
        this.elementHeight = elemHeight;
        values = new ArrayList<>(rowSize);
        fields = new ArrayList<>(rowSize);
        this.amountOfSeats = amountSeats;
        this.amountOfVoters = amountVoters;
        this.isTwoDim = (elecTypeContainer.getInputType()
                .getAmountOfDimensions() == 2);
        this.setRowSize(1);
    }

    /**
     * Adds the column.
     */
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
            private int position = fields.indexOf(field);

            @Override
            public void changed(final ObservableValue<? extends String> observable,
                                final String oldValue, final String newValue) {
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

    /**
     * Removes the column.
     */
    public synchronized void removeColumn() {
        if (fields.size() > 0) {
            rowSize--;
        }
        update();
    }

    /**
     * Update.
     */
    public void update() {
        if (!disabled) {
            for (Iterator<TextField> iterator = fields.iterator();
                    iterator.hasNext();) {
                TextField textField = iterator.next();
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

    /**
     * Check and insert value.
     *
     * @param newValue
     *            the new value
     * @param positionInRow
     *            the position in row
     * @param block
     *            the block
     */
    private void checkAndInsertValue(final String newValue,
                                     final int positionInRow,
                                     final boolean block) {
        List<NEWRowOfValues> allRows = parent.getRows();
        allRows.remove(this); // in case that this row is created before it is
                              // saved by its parent
        allRows.add(this);
        String vettedValue = container.getInputType().vetValue(container,
                allRows, rowIndex, positionInRow, newValue);
        values.set(positionInRow, vettedValue);
        if (positionInRow < fields.size()) {
            fields.get(positionInRow).setText(vettedValue);
        }
        update();
    }

    /**
     * Gets the values.
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

    /**
     * Sets the values.
     *
     * @param vals
     *            the new values
     */
    public void setValues(final ArrayList<String> vals) {
        this.values = vals;
        this.setRowSize(vals.size());
    }

    /**
     * Sets the container.
     *
     * @param electTypeContainer
     *            the new container
     */
    public void setContainer(final ElectionTypeContainer electTypeContainer) {
        this.container = electTypeContainer;
        for (int i = 0; i < values.size(); i++) {
            checkAndInsertValue("0", i, false);
        }
    }

    /**
     * Gets the amount candidates.
     *
     * @return the amount candidates
     */
    public int getAmountCandidates() {
        return amountOfCandidates;
    }

    /**
     * Gets the amount voters.
     *
     * @return the amount voters
     */
    public int getAmountVoters() {
        return amountOfVoters;
    }

    /**
     * Gets the amount seats.
     *
     * @return the amount seats
     */
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

    /**
     * Sets the row size.
     *
     * @param rowSizeInteger
     *            the new row size
     */
    public void setRowSize(final int rowSizeInteger) {
        if (this.rowSize < rowSizeInteger) {
            if (!isTwoDim) {
                if (this.rowSize == 0) {
                    addColumn();
                }
            } else {
                while (this.rowSize < rowSizeInteger) {
                    addColumn();
                }
            }
        } else if (this.rowSize > rowSizeInteger) {
            while (this.rowSize > rowSizeInteger) {
                removeColumn();
            }
        }
        this.rowSize = rowSizeInteger;
        updateVetting();
    }

    /**
     * Sets the candidates.
     *
     * @param amountCandidates
     *            the new candidates
     */
    public void setCandidates(final int amountCandidates) {
        this.amountOfCandidates = amountCandidates;
        updateVetting();
    }

    /**
     * Sets the voters.
     *
     * @param amountVoters
     *            the new voters
     */
    public void setVoters(final int amountVoters) {
        this.amountOfVoters = amountVoters;
        updateVetting();
    }

    /**
     * Sets the seats.
     *
     * @param amountSeats
     *            the new seats
     */
    public void setSeats(final int amountSeats) {
        this.amountOfSeats = amountSeats;
        updateVetting();
    }

    /**
     * Vets all numbers in the fields again, in case they are dependent of a
     * variable that was changed.
     */
    public void updateVetting() {
        for (int i = 0; i < values.size(); i++) {
            checkAndInsertValue(values.get(i), i, true);
        }
    }

    /**
     * Enable.
     */
    public void enable() {
        this.disabled = false;
        update();
    }

    /**
     * Disable.
     */
    public void disable() {
        this.disabled = true;
        for (Iterator<TextField> iterator = fields.iterator(); iterator
                .hasNext();) {
            TextField textField = iterator.next();
            parent.getInputGridPane().getChildren().remove(textField);
        }
    }

    /**
     * Sets the value.
     *
     * @param x
     *            the x
     * @param value
     *            the value
     */
    public void setValue(final int x, final String value) {
        checkAndInsertValue(value, x, true);
    }
}
