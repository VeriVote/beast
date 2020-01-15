package edu.pse.beast.electionsimulator.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ElectionSimulationModel {
    private static final double ELEMENT_WIDTH = 50;
    private static final double ELEMENT_HEIGHT = 25;

    private PropertyChangeSupport support;
    private List<NEWRowOfValues> rows = new ArrayList<NEWRowOfValues>();
    private List<TextField> xDescriptors = new ArrayList<TextField>();
    private List<TextField> yDescriptors = new ArrayList<TextField>();
    private final GridPane inputGridPane;
    private final GridPane voterGridPane;
    private final GridPane candidateGridPane;
    private ElectionTypeContainer container;

    private int amountCandidates = 1;
    private int amountVoters = 1;
    private int amountSeats = 1;

    private int currentRows = 0;
    private int currentRowSize;
    private int maxRows = 0;

    private int currentCandidates = 0;

    private final String xLabel;
    private final String yLabel;
    private final boolean isTwoDim;

    public ElectionSimulationModel(final ElectionTypeContainer elTypeContainer,
                                   final GridPane inputDataGridPane,
                                   final GridPane votGridPane,
                                   final GridPane candGridPane) {
        this.support = new PropertyChangeSupport(this);
        this.container = elTypeContainer;
        this.inputGridPane = inputDataGridPane;
        this.voterGridPane = votGridPane;
        this.candidateGridPane = candGridPane;
        this.yLabel = elTypeContainer.getInputType().getSizeOfDimensions()[0];

        if (elTypeContainer.getInputType().getAmountOfDimensions() == 2) {
            this.xLabel = elTypeContainer.getInputType().getSizeOfDimensions()[1];
        } else {
            this.xLabel = "";
        }
        isTwoDim = (elTypeContainer.getInputType().getAmountOfDimensions() == 2);
    }

    public void addPropertyChangeListener(final PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(final PropertyChangeListener pcl) {
        this.support.removePropertyChangeListener(pcl);
    }

    private synchronized void addRow() {
        if (currentRows == maxRows) {
            NEWRowOfValues toAdd =
                    new NEWRowOfValues(this, container,
                                       this.getAmountCandidates(), this.getAmountVoters(),
                                       this.getAmountSeats(), currentRows, ELEMENT_WIDTH,
                                       ELEMENT_HEIGHT);
            rows.add(toAdd);
            TextField newVoter = new TextField(yLabel + currentRows);
            yDescriptors.add(newVoter);
            newVoter.setMinSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
            newVoter.setPrefSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
            newVoter.setMaxSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
            voterGridPane.add(newVoter, 0, currentRows);
            currentRows++;
            maxRows++;
        } else { // we already have a row with for this index, so we just make it
               // visible again
            rows.get(currentRows).enable();
            voterGridPane.add(yDescriptors.get(currentRows), 0, currentRows);
            currentRows++;
        }
    }

    private void removeRow() {
        if (currentRows > 0) {
            currentRows--;
            rows.get(currentRows).disable();
            voterGridPane.getChildren().remove(yDescriptors.get(currentRows));
        }
    }

    public void changeContainer(final ElectionTypeContainer elTypeContainer) {
        this.container = elTypeContainer;
        for (Iterator<NEWRowOfValues> iterator = rows.iterator();
                iterator.hasNext();) {
            NEWRowOfValues currentRow = iterator.next();
            currentRow.setContainer(elTypeContainer);
        }
    }

    public List<TextField> getCandidates() {
        return xDescriptors;
    }

    public void setCandidates(final ArrayList<TextField> candidates) {
        this.xDescriptors = candidates;
    }

    public ElectionTypeContainer getContainer() {
        return container;
    }

    private void update() {
        List<Integer> list =
                container.getInputType().getSizesInOrder(amountVoters, amountCandidates,
                                                         amountSeats);
        for (Iterator<NEWRowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
            NEWRowOfValues row = (NEWRowOfValues) iterator.next();
            row.setVoters(getAmountVoters());
            row.setCandidates(getAmountCandidates());
            row.setSeats(getAmountSeats());
        }
        updateY(list.get(0));
        int secondSize = 1;
        if (isTwoDim) {
            secondSize = list.get(1);
        }
        updateX(secondSize);
        updateVetting();
    }

    public int getAmountCandidates() {
        return amountCandidates;
    }

    public int getAmountSeats() {
        return amountSeats;
    }

    public int getAmountVoters() {
        return amountVoters;
    }

    public void setAmountCandidates(final int candidates) {
        this.amountCandidates = container.getInputType().vetAmountCandidates(candidates);
        update();
    }

    public void setAmountVoters(final int voters) {
        this.amountVoters = container.getInputType().vetAmountVoters(voters);
        update();
    }

    public void setAmountSeats(final int seats) {
        this.amountSeats = container.getInputType().vetAmountSeats(seats);
        update();
    }

    public void setValue(final int x, final int y,
                         final String value) {
        while (rows.size() <= y) {
            addRow();
        }
        rows.get(y).setValue(x, value);
    }

    public List<NEWRowOfValues> getRows() {
        List<NEWRowOfValues> toReturn = new ArrayList<NEWRowOfValues>();
        if (rows.size() == 0) {
            toReturn.addAll(rows);
        } else {
            toReturn.addAll(rows.subList(0, currentRows));
        }
        return toReturn;
    }

    public List<TextField> getVoters() {
        return yDescriptors;
    }

    public void reset() {
        GUIController.getController().getInputVoters().setText("1");
        GUIController.getController().getInputCandidates().setText("1");
        GUIController.getController().getInputSeats().setText("1");

        inputGridPane.getChildren().clear();
        voterGridPane.getChildren().clear();
        candidateGridPane.getChildren().clear();

        this.amountCandidates = 1;
        this.amountVoters = 1;
        this.amountSeats = 1;

        this.currentRows = 0;
        this.maxRows = 0;

        this.currentCandidates = 0;

        rows = new ArrayList<NEWRowOfValues>();
        yDescriptors = new ArrayList<TextField>();
        xDescriptors = new ArrayList<TextField>();
    }

    public GridPane getInputGridPane() {
        return inputGridPane;
    }

    private void updateY(final int size) {
        if (currentRows < size) {
            while (currentRows < size) {
                addRow();
            }
        } else {
            while (currentRows > size) {
                removeRow();
            }
        }
    }

    private void updateX(final int size) {
        for (Iterator<NEWRowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
            NEWRowOfValues row = (NEWRowOfValues) iterator.next();
            row.setRowSize(size);
        }

        if (isTwoDim) {
            if (currentRowSize < size) {
                while (currentRowSize < size) {
                    if (xDescriptors.size() > currentRowSize) {
                        candidateGridPane.add(xDescriptors.get(currentRowSize), currentRowSize, 0);
                    } else {
                        TextField candToAdd = new TextField(xLabel + currentCandidates);
                        candToAdd.setEditable(true);
                        candToAdd.setMinSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
                        candToAdd.setPrefSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
                        candToAdd.setMaxSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
                        xDescriptors.add(candToAdd);
                        candidateGridPane.add(candToAdd, currentCandidates, 0);
                    }
                    currentRowSize++;
                }
            } else {
                while (currentRowSize > size) {
                    currentRowSize--;
                    candidateGridPane.getChildren()
                            .remove(xDescriptors.get(currentCandidates));
                }
            }
        } else {
            if (xDescriptors.size() == 0) {
                TextField descriptorToAdd = new TextField("Values");
                descriptorToAdd.setEditable(false);
                descriptorToAdd.setMinSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
                descriptorToAdd.setPrefSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
                descriptorToAdd.setMaxSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
                xDescriptors.add(descriptorToAdd);
                candidateGridPane.add(descriptorToAdd, 0, 0);
            }
        }
    }

    private void updateVetting() {
        for (Iterator<NEWRowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
            NEWRowOfValues row = (NEWRowOfValues) iterator.next();
            row.updateVetting();
        }
    }
}
