/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

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
    /**
     * this methode allows to pass a full file onward, so code generation can happen
     * before, and it gets ignored beforehand.
     * @param toCheck the file to check
     * @param params the parameter with which the check will be run
     * @return a 
     */
    public List<UnprocessedResult> checkFile(File toCheck, ParameterSource params) {
        if (toCheck == null || params == null) {
            return null;
        } else {
            this.factoryController = new FactoryController(toCheck, params, checkerID,
                    params.getParameter().getProcesses());
            return factoryController.getUnprocessedResults();
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
