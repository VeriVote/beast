/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.List;

import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.toolbox.ErrorLogger;

/**
 *
 * @author Niels
 */
public abstract class Result implements ResultInterface{
    
    private boolean valid = false;
    private boolean finished = false;
    private List<String> result;
    private boolean timeOut = false;
    private boolean success = false;
    
    @Override
    public boolean readyToPresent() {
        return finished;
    }

    @Override
    public void presentTo(ResultPresenterElement presenter) {
        
        if (!finished) {
            ErrorLogger.log("Result isn't ready yet");
            return;
        } else 
        if (timeOut) {
            presenter.presentTimeOut();
        } else
        if (!valid) {
            presenter.presentFailure();
        }
        if (success) {
            presenter.presentSuccess();
        } else 
        if ()
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setFinished() {
        finished = true;
    }
    
    public void setTimeoutFlag() {
        timeOut = true;
    }
    
}
