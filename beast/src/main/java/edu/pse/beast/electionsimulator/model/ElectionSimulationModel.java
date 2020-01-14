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

    private double elementWidth = 50;
    private double elementHeight = 25;

    private final String xLabel;
    private final String yLabel;
    private final boolean isTwoDim;

    public ElectionSimulationModel(ElectionTypeContainer container,
                                   GridPane inputGridPane, GridPane voterGridPane,
                                   GridPane candidateGridPane) {
        this.support = new PropertyChangeSupport(this);
        this.container = container;
        this.inputGridPane = inputGridPane;
        this.voterGridPane = voterGridPane;
        this.candidateGridPane = candidateGridPane;
        this.yLabel = container.getInputType().getSizeOfDimensions()[0];

        if (container.getInputType().getAmountOfDimensions() == 2) {
            this.xLabel = container.getInputType().getSizeOfDimensions()[1];
        } else {
            this.xLabel = "";
        }
        isTwoDim = (container.getInputType().getAmountOfDimensions() == 2);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        this.support.removePropertyChangeListener(pcl);
    }

    private synchronized void addRow() {
        if (currentRows == maxRows) {
            NEWRowOfValues toAdd =
                    new NEWRowOfValues(this, container,
                                       this.getAmountCandidates(), this.getAmountVoters(),
                                       this.getAmountSeats(), currentRows, elementWidth,
                                       elementHeight);
            rows.add(toAdd);
            TextField newVoter = new TextField(yLabel + currentRows);
            yDescriptors.add(newVoter);
            newVoter.setMinSize(elementWidth, elementHeight);
            newVoter.setPrefSize(elementWidth, elementHeight);
            newVoter.setMaxSize(elementWidth, elementHeight);
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

    public void changeContainer(ElectionTypeContainer container) {
        this.container = container;
        for (Iterator<NEWRowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
            NEWRowOfValues currentRow = iterator.next();
            currentRow.setContainer(container);
        }
    }

    public List<TextField> getCandidates() {
        return xDescriptors;
    }

    public void setCandidates(ArrayList<TextField> candidates) {
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

    public void setAmountCandidates(int amountCandidates) {
        this.amountCandidates = container.getInputType().vetAmountCandidates(amountCandidates);
        update();
    }

    public void setAmountVoters(int amountVoters) {
        this.amountVoters = container.getInputType().vetAmountVoters(amountVoters);
        update();
    }

    public void setAmountSeats(int amountSeats) {
        this.amountSeats = container.getInputType().vetAmountSeats(amountSeats);
        update();
    }

    public void setValue(int x, int y, String value) {
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

    private void updateY(int size) {
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

    private void updateX(int size) {
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
                        candToAdd.setMinSize(elementWidth, elementHeight);
                        candToAdd.setPrefSize(elementWidth, elementHeight);
                        candToAdd.setMaxSize(elementWidth, elementHeight);
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
                descriptorToAdd.setMinSize(elementWidth, elementHeight);
                descriptorToAdd.setPrefSize(elementWidth, elementHeight);
                descriptorToAdd.setMaxSize(elementWidth, elementHeight);
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
