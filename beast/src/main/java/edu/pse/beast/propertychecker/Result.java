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

    private boolean valid = false;
    private boolean finished = false;
    private List<String> result;
    private boolean timeOut = false;
    private boolean success = false;

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
    @Override
    public void presentTo(ResultPresenterElement presenter) {

        if (!finished) {
            ErrorLogger.log("Result isn't ready yet");
            return;
        } else if (timeOut) {
            presenter.presentTimeOut();
        } else if (!valid) {
            presenter.presentFailure();
        }
        if (success) {
            presenter.presentSuccess();
        } else {
            presenter.presentFailureExample(new FailureExample(result));
        }
    }

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
