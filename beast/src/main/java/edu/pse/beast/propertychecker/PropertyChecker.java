/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.*;

import java.io.File;
import java.util.List;

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
    public List<ResultInterface> checkPropertiesForDescription(ElectionDescriptionSource elecDescr,
            PostAndPrePropertiesDescriptionSource propDescrSrc, ParameterSource params) {

        if (elecDescr == null || propDescrSrc == null || params == null) {
            return null;
        } else {
            this.factoryController = new FactoryController(elecDescr, propDescrSrc, params, checkerID,
                    params.getParameter().getProcesses());
            return factoryController.getResults();
        }
    }
    
    @Override
    public UnprocessedCBMCResult checkFile(File toCheck, ElectionDescription electionDescr, ParameterSource params) {
        if (toCheck == null || params == null) {
            return null;
        } else {
            this.factoryController = new FactoryController(toCheck, params, checkerID,
                    params.getParameter().getProcesses());
            //because we only have ONE file to check, we  only give back the first result
            UnprocessedCBMCResult toReturn = factoryController.getUnprocessedResults().get(0);
            toReturn.setElectionType(electionDescr);
            return toReturn;
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
