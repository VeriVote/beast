/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.List;

import edu.pse.beast.highlevel.FailureExample;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.toolbox.ErrorLogger;

/**
 *
 * @author Niels & Lukas
 */
public abstract class Result implements ResultInterface {

    protected boolean valid = false;
    protected boolean finished = false;
    protected List<String> result;
    protected boolean timeOut = false;
    protected boolean success = false;

    /**
     * 
     * @return if the result is in a state that it can be presented
     */
    @Override
    public boolean readyToPresent() {
        return finished;
    }

    /**
     * Presents the result of the check.
     * Every class that extends this class has to implement it for itself.
     * @param presenter the presentable where it is supposed to be presented on
     */
    public abstract void presentTo(ResultPresenterElement presenter);

    /**
     * to be set when the checking for this result is completed
     */
    public void setFinished() {
        finished = true;
    }
    
    /**
     * to be set when the checking was completed and there were no errors during
     * the checking
     */
    public void setValid() {
        valid = true;
    }

    public void setSuccess() {
        success = true;
    }

    /**
     * sets the flag that shows that a timeout was responsible for the stopped checking
     */
    public void setTimeoutFlag() {
        timeOut = true;
    }

    /**
     * sets the result of this object, so it can be displayed later.
     * @param result the result of the check that should be stored in this result object
     */
    public void setResult(List<String> result) {
        this.result = result;
    }
}
