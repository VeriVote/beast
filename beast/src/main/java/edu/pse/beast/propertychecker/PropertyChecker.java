package edu.pse.beast.propertychecker;

import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.ResultCheckerCommunicator;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;

/**
 *
 * @author Niels Hanselmann
 */
public class PropertyChecker implements ResultCheckerCommunicator {
    private FactoryController factoryController;
    private final String checkerID;

    /**
     *
     * @param checkerIdString the ID for the checker to be used
     */
    public PropertyChecker(final String checkerIdString) {
        this.checkerID = checkerIdString;
    }

    @Override
    public List<Result>
            checkPropertiesForDescription(final ElectionDescription elecDescr,
                                          final List<ParentTreeItem> parentProperties,
                                          final ElectionCheckParameter electionCheckParameter) {
        if (elecDescr == null || parentProperties == null || electionCheckParameter == null) {
            return null;
        } else {
            this.factoryController =
                    new FactoryController(elecDescr, parentProperties,
                                          electionCheckParameter,
                                          checkerID,
                                          electionCheckParameter.getProcesses());
            return factoryController.getResults();
        }
    }

    @Override
    public boolean abortChecking() {
        if (factoryController != null) {
            factoryController.stopChecking(false);
            return true;
        } else {
            return false;
        }
    }
}
