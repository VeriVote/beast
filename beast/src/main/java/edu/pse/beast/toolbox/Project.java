package edu.pse.beast.toolbox;

import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.electionsimulator.InputDataDescription;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;

/**
 * Class that contains all properties of a project, used to save and load
 * projects.
 *
 * @author Lukas Stapelbroek
 */
public class Project {

    /** The election description. */
    private final ElectionDescription electionDescription;

    /** The properties. */
    private final List<ParentTreeItem> properties;

    /** The input data. */
    private final InputDataDescription inputData;

    /**
     * The constructor.
     *
     * @param electionDescr
     *            the election descr
     * @param propertyList
     *            the property list
     * @param inputDataDescr
     *            the input data descr
     */
    public Project(final ElectionDescription electionDescr,
                   final List<ParentTreeItem> propertyList,
                   final InputDataDescription inputDataDescr) {
        this.electionDescription = electionDescr;
        this.properties = propertyList;
        this.inputData = inputDataDescr;
    }

    /**
     * Gets the election description.
     *
     * @return the election description
     */
    public ElectionDescription getElectionDescription() {
        return electionDescription;
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public List<ParentTreeItem> getProperties() {
        return properties;
    }

    /**
     * Gets the input data.
     *
     * @return the input data
     */
    public InputDataDescription getInputData() {
        return inputData;
    }
}
