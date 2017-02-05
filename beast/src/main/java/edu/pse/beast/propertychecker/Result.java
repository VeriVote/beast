/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.List;

import edu.pse.beast.datatypes.ElectionType;
import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenterElement;

/**
 *
 * @author Niels & Lukas
 */
public abstract class Result implements ResultInterface {

    private boolean valid = false;
    private boolean finished = false;
    private List<String> result;
    private List<String> error;
    private boolean timeOut = false;
    private boolean success = false;
    private int numVoters;
    private int numSeats;
    private int numCandidates;
    private ElectionType electionType;
    private FailureExample failExample;

    
    /**
     * Presents the result of the check.
     * Every class that extends this class has to implement it for itself.
     * @param presenter the presentable where it is supposed to be presented on
     */
    public abstract void presentTo(ResultPresenterElement presenter);
    
    /**
     * 
     * @return if the result is in a state that it can be presented
     */
    @Override
    public boolean readyToPresent() {
        return finished;
    }
    
    public boolean isValid() {
        return valid;
    }

    public boolean isFinished() {
        return finished;
    }

    public List<String> getResult() {
        return result;
    }
    
    public List<String> getError() {
        return error;
    }

    public boolean isTimedOut() {
        return timeOut;
    }

    public boolean isSuccess() {
        return success;
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

    /**
     * 
     */
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
    
    /**
     * sets the result of this object, so it can be displayed later.
     * @param result the result of the check that should be stored in this result object
     */
    public void setError(List<String> error) {
        this.error = error;
    }

    public int getNumVoters() {
        return numVoters;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public int getNumCandidates() {
        return numCandidates;
    }

    public ElectionType getElectionType() {
        return electionType;
    }

    public void setNumVoters(int numVoters) {
        this.numVoters = numVoters;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public void setNumCandidates(int numCandidates) {
        this.numCandidates = numCandidates;
    }

    public void setElectionType(ElectionType electionType) {
        this.electionType = electionType;
    }
}
