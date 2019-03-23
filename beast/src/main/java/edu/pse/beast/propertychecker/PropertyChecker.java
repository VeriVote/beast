package edu.pse.beast.propertychecker;

import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.ResultCheckerCommunicator;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;

/**
 *
 * @author Niels
 */
public class PropertyChecker implements ResultCheckerCommunicator {
    private FactoryController factoryController;
    private final String checkerID;

    /**
     * 
     * @param checkerID
     *            the ID for the checker to be used
     */
    public PropertyChecker(String checkerID) {
        this.checkerID = checkerID;
    }
    
    
    @Override
    public List<Result> checkPropertiesForDescription(ElectionDescription elecDescr,
    		List<ParentTreeItem> parentProperties, ElectionCheckParameter electionCheckParameter) {
    	 if (elecDescr == null || parentProperties == null || electionCheckParameter == null) {
    		 return null;
         } else {
             this.factoryController = new FactoryController(elecDescr, parentProperties, electionCheckParameter, checkerID,
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
