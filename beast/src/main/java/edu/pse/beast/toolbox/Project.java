package edu.pse.beast.toolbox;

import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.electionsimulator.InputDataDescription;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;

/**
 * class that contains all properties of a project, used to save and load
 * projects
 *
 * @author Lukas Stapelbroek
 */
public class Project {

    public final ElectionDescription electionDescription;
    public final List<ParentTreeItem> properties;
    public final InputDataDescription inputData;

    public Project(ElectionDescription electionDescription, List<ParentTreeItem> properties,
            InputDataDescription inputData) {
        this.electionDescription = electionDescription;
        this.properties = properties;
        this.inputData = inputData;
    }
}
