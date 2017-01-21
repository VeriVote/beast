/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
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
     * @param checkerID the ID for the checker to be used
     */
    public PropertyChecker(String checkerID) {
        this.checkerID = checkerID;
    }

    @Override
    public ResultInterface[] checkPropertiesForDescription(PostAndPrePropertiesDescription propDescr,
            ElectionDescription elecDescr, ElectionCheckParameter params) {
        
        factoryController = new FactoryController();
        
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void abortChecking() {
        // TODO Auto-generated method stub
        
    }
    
}
