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

    private final ElectionDescription electionDescription;
    private final List<ParentTreeItem> properties;
    private final InputDataDescription inputData;

    public Project(final ElectionDescription electionDescr,
                   final List<ParentTreeItem> propertyList,
                   final InputDataDescription inputDataDescr) {
        this.electionDescription = electionDescr;
        this.properties = propertyList;
        this.inputData = inputDataDescr;
    }

    public ElectionDescription getElectionDescription() {
        return electionDescription;
    }

    public List<ParentTreeItem> getProperties() {
        return properties;
    }

    public InputDataDescription getInputData() {
        return inputData;
    }
}
