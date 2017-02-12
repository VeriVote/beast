/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.List;

import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.highlevel.ResultCheckerCommunicator;
import edu.pse.beast.highlevel.ResultInterface;

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
    public boolean abortChecking() {
        if (factoryController != null) {
            factoryController.stopChecking(false);
            return true;
        } else {
            return false;
        }
    }

}
