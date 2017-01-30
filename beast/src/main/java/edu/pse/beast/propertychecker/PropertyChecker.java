/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.List;

import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.highlevel.ResultCheckerCommunicator;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.toolbox.ErrorLogger;

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

        this.factoryController = new FactoryController(elecDescr, propDescrSrc, params, checkerID, params.getParameter().getProcesses());
        return factoryController.getResults();
    }

    @Override
    public void abortChecking() {
        if (factoryController != null) {
            factoryController.stopChecking(false);
        } else {
            ErrorLogger.log("Tried to stop the checking before a factory controller existed!");
        }
    }

}
