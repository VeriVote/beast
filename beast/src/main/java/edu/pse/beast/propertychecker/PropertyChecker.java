/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.io.File;
import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PreAndPostConditionsDescriptionSource;
import edu.pse.beast.highlevel.ResultCheckerCommunicator;
import edu.pse.beast.highlevel.ResultInterface;
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
//
//    @Override
//    public List<ResultInterface> checkPropertiesForDescription(ElectionDescriptionSource elecDescr,
//            PreAndPostConditionsDescriptionSource propDescrSrc, ParameterSource params) {
//
//        if (elecDescr == null || propDescrSrc == null || params == null) {
//            return null;
//        } else {
//            this.factoryController = new FactoryController(elecDescr, propDescrSrc, params, checkerID,
//                    params.getParameter().getProcesses());
//            return factoryController.getResults();
//        }
//    }
    
    
    @Override
    public boolean checkPropertiesForDescription(ElectionDescription elecDescr,
    		List<ParentTreeItem> parentProperties, ElectionCheckParameter electionCheckParameter) {
    	 if (elecDescr == null || parentProperties == null || electionCheckParameter == null) {
    		 return false;
         } else {
             this.factoryController = new FactoryController(elecDescr, parentProperties, electionCheckParameter, checkerID,
            		 electionCheckParameter.getProcesses());
             return true;
         }
    }
//    
//    @Override
//    public UnprocessedCBMCResult checkFile(File toCheck, ElectionDescription electionDescr, ParameterSource params) {
//        if (toCheck == null || params == null) {
//            return null;
//        } else {
//            this.factoryController = new FactoryController(toCheck, params, checkerID,
//                    params.getParameter().getProcesses());
//            //because we only have ONE file to check, we  only give back the first result
//            UnprocessedCBMCResult toReturn = factoryController.getUnprocessedResults().get(0);
//            toReturn.setElectionType(electionDescr);
//            return toReturn;
//        }
//    }

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
