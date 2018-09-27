/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.javafx.AnalysisType;
import edu.pse.beast.highlevel.javafx.ResultTreeItem;

/**
 *
 * @author Niels & Lukas
 */
public abstract class Result implements ResultInterface {

	protected FailureExample failureExample = null;
	private boolean wasStarted = false;
    private boolean valid = false;
    private boolean finished = false;
    private List<String> result;
    private List<String> error;
    
    private List<String> lastTmpResult;
    private List<String> lastTmpError;
    
    private boolean timeOut = false;
    private boolean success = false;
    private boolean hasSubResult = false;
    private int numVoters;
    private int numSeats;
    private int numCandidates;
    private ElectionDescription electionDescription;
    private PreAndPostConditionsDescription property;
    private boolean forcefulleStopped;
    private boolean hasMargin = false;
    private int finalMargin = -1;
    
    private Result subResult = null;
    private boolean isMarginComp = false;
    
	protected List<List<String>> origVoting;
	protected List<String> origWinner;
	protected List<List<String>> newVotes;
	protected List<String> newWinner;
	
	protected transient ResultTreeItem owner;
	private List<String> statusStrings;

	public static List<Result> getResultTypes() {
		ServiceLoader<Result> loader = ServiceLoader.load(Result.class);
		
		List<Result> types = new ArrayList<Result>();
		
		for (Iterator<Result> iterator = loader.iterator(); iterator.hasNext();) {
			Result type = (Result) iterator.next();
			types.add(type);
		}
		
		return types;
	}
	
    public boolean isMarginComp() {
		return isMarginComp;
	}

	public void setMarginComp(boolean isMarginComp) {
		this.isMarginComp = isMarginComp;
	}

    /**
     * 
     * @return if the result is in a state that it can be presented
     */
    @Override
    public boolean readyToPresent() {
        return finished;
    }

    /**
     * 
     * @return if the result is valid (the checking finished normally and wasn't
     *         stopped by something else)
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * 
     * @return if the result is ready yet
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * 
     * @return the result List
     */
    public List<String> getResult() {
        return result;
    }

    /**
     * 
     * @return the error list
     */
    public List<String> getError() {
        return error;
    }

    /**
     * 
     * @return if the result didn't finish because of a timeout
     */
    public boolean isTimedOut() {
        return timeOut;
    }

    /**
     * 
     * @return if the checking was successfull
     */
    public boolean isSuccess() {
        return success;
    }
    
    /**
     * 
     * @return true, if the process was stopped by the user or a timeout, else false
     */
    public boolean isFocefullyStopped() {
        return forcefulleStopped;
    }

    /**
     * to be set when the checking for this result is completed
     */
    public void setFinished() {
        finished = true;
        owner.setPresentable();
    }
    
    public void setStarted() {
    	wasStarted = true;
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
     * sets the flag that shows that a timeout was responsible for the stopped
     * checking and sets the result to finished
     */
    public void setTimeoutFlag() {
        timeOut = true;
        this.setFinished();
    }

    /**
     * sets the result of this object, so it can be displayed later.
     * 
     * @param result
     *            the result of the check that should be stored in this result
     *            object
     */
    public void setResult(List<String> result) {
        this.result = result;
    }

    /**
     * sets the error of this object, so it can be displayed later.
     * 
     * @param error
     *            the error of the check that should be stored in this result
     *            object
     */
    public void setError(List<String> error) {
        this.error = error;
    }

    /**
     * sets a single line as the error output
     * 
     * @param errorLine
     *            the error to log
     */
    public void setError(String errorLine) {
        this.error = new ArrayList<String>();
        this.error.add(errorLine);
    }
    
    /**
     * sets the property that this result contains the result from
     * @param property
     */
    public void setProperty(PreAndPostConditionsDescription property) {
        this.property = property;
    }

    /**
     * 
     * @return the amount of votes for which the verification failed
     */
    public int getNumVoters() {
        return numVoters;
    }

    /**
     * 
     * @return the amount of seats for which the verification failed
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * 
     * @return the amount of candidates for which the verification failed
     */
    public int getNumCandidates() {
        return numCandidates;
    }

    /**
     * 
     * @return the election type
     */
    public ElectionDescription getElectionDescription() {
        return electionDescription;
    }

    /**
     * 
     * @param numVoters
     *            the amount of voters to be set
     */
    public void setNumVoters(int numVoters) {
        this.numVoters = numVoters;
    }

    /**
     * 
     * @param numSeats
     *            the amount of seats to be set
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    /**
     * 
     * @param numCandidates
     *            the amount of candidates to be set
     */
    public void setNumCandidates(int numCandidates) {
        this.numCandidates = numCandidates;
    }

    /**
     * 
     * @param electionTypeContainer
     *            the electiontype to be set
     */
    public void setElectionType(ElectionDescription electionDescription) {
        this.electionDescription = electionDescription;
    }

    /**
     * sets the result to forcefully stop, to indicate
     * that it was stopped by the user or a timeout
     */
    public void setForcefullyStopped() {
        this.forcefulleStopped = true;
    }
    public void setFinalMargin(int margin) {
    	this.finalMargin = margin;
    }
    
    public int getFinalMargin() {
    	return finalMargin;
    }
    
    public void setHasFinalMargin(boolean b) {
    	this.hasMargin = b;
    }
    
    public boolean hasFinalMargin() {
    	return hasMargin;
    }
    
    public void addSubResult(Result subResult) {
    	this.hasSubResult = true;
    	this.subResult = subResult;
    }
    
    public PreAndPostConditionsDescription getPropertyDesctiption() {
    	return property;
    }
    
    public boolean hasSubResult() {
    	return this.hasSubResult;
    }
    
    public Result getSubResult() {
    	return subResult;
    }

    /**
     * checks if the assertion holds
     * @return true, if the assertion holds, else false
     */
    public abstract boolean checkAssertionSuccess();

    /**
     * checks if the assertion doesn't hold
     * @return true, if the assertion failed, else false
     */
    public abstract boolean checkAssertionFailure();

    public FailureExample getFailureExample() {
    	return failureExample;
    }

	public List<List<String>> getOrigVoting() {
		return origVoting;
	}

	public void setOrigVoting(List<List<String>> origVoting) {
		this.origVoting = origVoting;
	}

	public List<String> getOrigWinner() {
		return origWinner;
	}

	public void setOrigWinner(List<String> origWinner) {
		this.origWinner = origWinner;
	}

	public List<List<String>> getNewVotes() {
		return newVotes;
	}

	public void setNewVotes(List<List<String>> newVotes) {
		this.newVotes = newVotes;
	}

	public List<String> getNewWinner() {
		return newWinner;
	}

	public void setNewWinner(List<String> newWinner) {
		this.newWinner = newWinner;
	}

	public void setOwner(ResultTreeItem owner) {
		this.owner = owner;
	}

	public AnalysisType getAnalysisType() {
		return owner.getOwner().getAnalysisType();
	}

	public boolean wasStarted() {
		return wasStarted;
	}
	
	public void setLastTmpResult(List<String> tmpResult) {
		this.lastTmpResult = tmpResult;
	}
	
	public List<String> getLastTmpResult() {
		return this.lastTmpResult;
	}
	
	public void setLastTmpError(List<String> tmpError) {
		this.lastTmpError = tmpError;
	}
	
	public List<String> getLastTmpError() {
		return this.lastTmpError;
	}
	
	public void addStatusString(String statusToAdd) {
		this.statusStrings.add(statusToAdd);
	}
	
	public List<String> getStatusStrings() {
		return this.statusStrings;
	}
}
