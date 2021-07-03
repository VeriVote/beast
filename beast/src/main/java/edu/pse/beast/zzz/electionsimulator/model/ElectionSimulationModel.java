package edu.pse.beast.zzz.electionsimulator.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;

/**
 * The Class ElectionSimulationModel.
 *
 * @author Lukas Stapelbroek
 */
public class ElectionSimulationModel {
    /** The Constant ONE. */
    private static final String ONE = "1";

    /** The Constant ELEMENT_WIDTH. */
    private static final double ELEMENT_WIDTH = 50;
    /** The Constant ELEMENT_HEIGHT. */
    private static final double ELEMENT_HEIGHT = 25;

    /** The support. */
    private PropertyChangeSupport support;

    /** The rows. */
    private List<NEWRowOfValues> rows = new ArrayList<NEWRowOfValues>();

    /** The x descriptors. */
    private List<TextField> xDescriptors = new ArrayList<TextField>();

    /** The y descriptors. */
    private List<TextField> yDescriptors = new ArrayList<TextField>();

    /** The input grid pane. */
    private final GridPane inputGridPane;

    /** The voter grid pane. */
    private final GridPane voterGridPane;

    /** The candidate grid pane. */
    private final GridPane candidateGridPane;

    /** The container. */
    private ElectionTypeContainer container;

    /** The amount candidates. */
    private int amountCandidates = 1;

    /** The amount voters. */
    private int amountVoters = 1;

    /** The amount seats. */
    private int amountSeats = 1;

    /** The current rows. */
    private int currentRows;

    /** The current row size. */
    private int currentRowSize;

    /** The max rows. */
    private int maxRows;

    /** The current candidates. */
    private int currentCandidates;

    /** The x label. */
    private String xLabel;

    /** The y label. */
    private final String yLabel;

    /** The is two dim. */
    private boolean isTwoDim;

    /**
     * Instantiates a new election simulation model.
     *
     * @param elTypeContainer
     *            the el type container
     * @param inputDataGridPane
     *            the input data grid pane
     * @param votGridPane
     *            the vot grid pane
     * @param candGridPane
     *            the cand grid pane
     */
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
        GUIController.getController()
            .updateElectionInputIndexText(elTypeContainer.getInputType());
        this.isTwoDim = elTypeContainer.getInputType().getAmountOfDimensions() == 2;
        if (isTwoDim) {
            this.xLabel = elTypeContainer.getInputType().getSizeOfDimensions()[1];
        } else {
            this.xLabel = "";
        }
    }

    /**
     * Adds the property change listener.
     *
     * @param pcl
     *            the pcl
     */
    public void addPropertyChangeListener(final PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    /**
     * Removes the property change listener.
     *
     * @param pcl
     *            the pcl
     */
    public void removePropertyChangeListener(final PropertyChangeListener pcl) {
        this.support.removePropertyChangeListener(pcl);
    }

    /**
     * Adds the row.
     */
    private synchronized void addRow() {
        if (currentRows == maxRows) {
            final NEWRowOfValues toAdd =
                    new NEWRowOfValues(this, container,
                                       this.getAmountCandidates(),
                                       this.getAmountVoters(),
                                       this.getAmountSeats(),
                                       currentRows, ELEMENT_WIDTH,
                                       ELEMENT_HEIGHT);
            rows.add(toAdd);
            final TextField newVoter = new TextField(yLabel + currentRows);
            yDescriptors.add(newVoter);
            newVoter.setMinSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
            newVoter.setPrefSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
            newVoter.setMaxSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
            voterGridPane.add(newVoter, 0, currentRows);
            currentRows++;
            maxRows++;
        } else {
            // We already have a row with for this index, so we just make it
            // visible again.
            rows.get(currentRows).enable();
            voterGridPane.add(yDescriptors.get(currentRows), 0, currentRows);
            currentRows++;
        }
    }

    /**
     * Removes the row.
     */
    private void removeRow() {
        if (currentRows > 0) {
            currentRows--;
            rows.get(currentRows).disable();
            voterGridPane.getChildren().remove(yDescriptors.get(currentRows));
        }
    }

    /**
     * Change container.
     *
     * @param elTypeContainer
     *            the el type container
     */
    public void changeContainer(final ElectionTypeContainer elTypeContainer) {
        this.container = elTypeContainer;
        this.isTwoDim = elTypeContainer.getInputType().getAmountOfDimensions() == 2;
        if (this.isTwoDim) {
            this.xLabel = elTypeContainer.getInputType().getSizeOfDimensions()[1];
        } else {
            this.xLabel = "";
        }
        for (Iterator<NEWRowOfValues> iterator = rows.iterator();
                iterator.hasNext();) {
            final NEWRowOfValues currentRow = iterator.next();
            currentRow.setContainer(elTypeContainer);
        }
        update();
    }

    /**
     * Gets the candidates.
     *
     * @return the candidates
     */
    public List<TextField> getCandidates() {
        return xDescriptors;
    }

    /**
     * Sets the candidates.
     *
     * @param candidates
     *            the new candidates
     */
    public void setCandidates(final ArrayList<TextField> candidates) {
        this.xDescriptors = candidates;
    }

    /**
     * Gets the container.
     *
     * @return the container
     */
    public ElectionTypeContainer getContainer() {
        return container;
    }

    /**
     * Update.
     */
    private void update() {
        final List<Integer> list =
                container.getInputType()
                .getSizesInOrder(amountVoters, amountCandidates, amountSeats);
        for (Iterator<NEWRowOfValues> iterator = rows.iterator();
                iterator.hasNext();) {
            final NEWRowOfValues row = iterator.next();
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

    /**
     * Gets the amount candidates.
     *
     * @return the amount candidates
     */
    public int getAmountCandidates() {
        return amountCandidates;
    }

    /**
     * Gets the amount seats.
     *
     * @return the amount seats
     */
    public int getAmountSeats() {
        return amountSeats;
    }

    /**
     * Gets the amount voters.
     *
     * @return the amount voters
     */
    public int getAmountVoters() {
        return amountVoters;
    }

    /**
     * Sets the amount candidates.
     *
     * @param candidates
     *            the new amount candidates
     */
    public void setAmountCandidates(final int candidates) {
        this.amountCandidates =
                container.getInputType()
                .vetAmountCandidates(candidates);
        update();
    }

    /**
     * Sets the amount voters.
     *
     * @param voters
     *            the new amount voters
     */
    public void setAmountVoters(final int voters) {
        this.amountVoters = container.getInputType().vetAmountVoters(voters);
        update();
    }

    /**
     * Sets the amount seats.
     *
     * @param seats
     *            the new amount seats
     */
    public void setAmountSeats(final int seats) {
        this.amountSeats = container.getInputType().vetAmountSeats(seats);
        update();
    }

    /**
     * Sets the value.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @param value
     *            the value
     */
    public void setValue(final int x, final int y, final String value) {
        while (rows.size() <= y) {
            addRow();
        }
        rows.get(y).setValue(x, value);
    }

    /**
     * Gets the rows.
     *
     * @return the rows
     */
    public List<NEWRowOfValues> getRows() {
        final List<NEWRowOfValues> toReturn = new ArrayList<NEWRowOfValues>();
        if (rows.size() == 0) {
            toReturn.addAll(rows);
        } else {
            toReturn.addAll(rows.subList(0, currentRows));
        }
        return toReturn;
    }

    /**
     * Gets the voters.
     *
     * @return the voters
     */
    public List<TextField> getVoters() {
        return yDescriptors;
    }

    /**
     * Reset.
     */
    public void reset() {
        GUIController.getController().getInputVoters().setText(ONE);
        GUIController.getController().getInputCandidates().setText(ONE);
        GUIController.getController().getInputSeats().setText(ONE);

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

    /**
     * Gets the input grid pane.
     *
     * @return the input grid pane
     */
    public GridPane getInputGridPane() {
        return inputGridPane;
    }

    /**
     * Update Y.
     *
     * @param size
     *            the size
     */
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

    /**
     * Update X.
     *
     * @param size
     *            the size
     */
    private void updateX(final int size) {
        for (Iterator<NEWRowOfValues> iterator = rows.iterator();
                iterator.hasNext();) {
            final NEWRowOfValues row = iterator.next();
            row.setRowSize(size);
        }

        if (isTwoDim) {
            if (currentRowSize < size) {
                while (currentRowSize < size) {
                    if (xDescriptors.size() > currentRowSize) {
                        final TextField template = xDescriptors.get(currentRowSize);
                        final TextField toAdd =
                                new TextField(template.getText());
                        toAdd.setEditable(template.isEditable());
                        toAdd.setMinSize(template.getMinWidth(), template.getMinHeight());
                        toAdd.setPrefSize(template.getPrefWidth(), template.getPrefHeight());
                        toAdd.setMaxSize(template.getMaxWidth(), template.getMaxHeight());
                        xDescriptors.add(toAdd);
                        candidateGridPane.add(toAdd, currentRowSize, 0);
                    } else {
                        final TextField candToAdd =
                                new TextField(xLabel + currentCandidates);
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
        } else if (xDescriptors.size() == 0) {
            final TextField descriptorToAdd = new TextField("Values");
            descriptorToAdd.setEditable(false);
            descriptorToAdd.setMinSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
            descriptorToAdd.setPrefSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
            descriptorToAdd.setMaxSize(ELEMENT_WIDTH, ELEMENT_HEIGHT);
            xDescriptors.add(descriptorToAdd);
            candidateGridPane.add(descriptorToAdd, 0, 0);
        }
    }

    /**
     * Update vetting.
     */
    private void updateVetting() {
        for (Iterator<NEWRowOfValues> iterator = rows.iterator();
                iterator.hasNext();) {
            final NEWRowOfValues row = iterator.next();
            row.updateVetting();
        }
    }
}
