package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electionsimulator.ElectionSimulationData;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.javafx.AnalysisType;
import edu.pse.beast.highlevel.javafx.ResultTreeItem;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;

/**
 *
 * @author Niels Hanselmann, Lukas Stapelbroek
 */
public abstract class Result implements ResultInterface {
    private ElectionSimulationData origVoting;
    private ElectionSimulationData origWinner;
    private ElectionSimulationData newVotes;
    private ElectionSimulationData newWinner;

    private transient ResultTreeItem owner;

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

    private List<String> statusStrings = new ArrayList<String>();
    private int exitCode;

    /**
     * Returns all currently available result types.
     *
     * @return list of result types
     */
    public static List<Result> getResultTypes() {
        ServiceLoader<Result> loader = ServiceLoader.load(Result.class);
        List<Result> types = new ArrayList<Result>();
        for (Result type : loader) {
            types.add(type);
        }
        return types;
    }

    public boolean isTest() {
        return getAnalysisType() == AnalysisType.Test;
    }

    public boolean isMarginComp() {
        return getAnalysisType() == AnalysisType.Margin;
    }

    public void setMarginComp(final boolean isMarginComputation) {
        this.isMarginComp = isMarginComputation;
    }

    /**
     *
     * @return whether the result is in a state that it can be presented.
     */
    @Override
    public boolean readyToPresent() {
        return finished;
    }

    /**
     *
     * @return whether the result is valid (the checking finished normally and
     *         was not stopped by something else).
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
     * @return if the result did not finish because of a timeout
     */
    public boolean isTimedOut() {
        return timeOut;
    }

    /**
     *
     * @return whether the checking was successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @return true, if the process was stopped by the user or a timeout, else
     *         false
     */
    public boolean isFocefullyStopped() {
        return forcefulleStopped;
    }

    /**
     * To be set when the checking for this result is completed.
     */
    public void setFinished() {
        finished = true;
        owner.setPresentable();
    }

    public void setStarted() {
        wasStarted = true;
    }

    /**
     * To be set when the checking was completed and there were no errors during
     * the checking.
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
     * Sets the flag that shows that a timeout was responsible for the stopped
     * checking and sets the result to finished.
     */
    public void setTimeoutFlag() {
        timeOut = true;
        this.setFinished();
    }

    /**
     * Sets the result of this object, so it can be displayed later.
     *
     * @param res
     *            the result of the check that should be stored in this result
     *            object
     */
    public void setResult(final List<String> res) {
        this.result = res;
    }

    /**
     * Sets the error of this object, so it can be displayed later.
     *
     * @param errorList
     *            the error of the check that should be stored in this result
     *            object
     */
    public void setError(final List<String> errorList) {
        this.error = errorList;
    }

    /**
     * Sets a single line as the error output.
     *
     * @param errorLine
     *            the error to log
     */
    public void setError(final String errorLine) {
        this.error = new ArrayList<String>();
        this.error.add(errorLine);
    }

    /**
     * Sets the property that this result contains the result from.
     *
     * @param propertyDescr
     *            the property.
     */
    public void setProperty(final PreAndPostConditionsDescription propertyDescr) {
        this.property = propertyDescr;
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
     * @param numVotersInteger
     *            the amount of voters to be set
     */
    public void setNumVoters(final int numVotersInteger) {
        this.numVoters = numVotersInteger;
    }

    /**
     *
     * @param numSeatsInteger
     *            the amount of seats to be set
     */
    public void setNumSeats(final int numSeatsInteger) {
        this.numSeats = numSeatsInteger;
    }

    /**
     *
     * @param numCand
     *            the amount of candidates to be set
     */
    public void setNumCandidates(final int numCand) {
        this.numCandidates = numCand;
    }

    /**
     *
     * @param electionDescr
     *            the election type to be set
     */
    public void setElectionType(final ElectionDescription electionDescr) {
        this.electionDescription = electionDescr;
    }

    /**
     * Sets the result to forcefully stop, to indicate that it was stopped by
     * the user or a timeout.
     */
    public void setForcefullyStopped() {
        this.forcefulleStopped = true;
    }

    public void setFinalMargin(final int margin) {
        this.finalMargin = margin;
    }

    public int getFinalMargin() {
        return finalMargin;
    }

    public void setHasFinalMargin(final boolean b) {
        this.hasMargin = b;
    }

    public boolean hasFinalMargin() {
        return hasMargin;
    }

    public void addSubResult(final Result subResultVal) {
        this.hasSubResult = true;
        this.subResult = subResultVal;
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
     * Checks if the assertion holds.
     *
     * @return true if the assertion holds, else false
     */
    public abstract boolean checkAssertionSuccess();

    /**
     * Checks if the assertion does not hold.
     *
     * @return true, if the assertion failed, else false
     */
    public abstract boolean checkAssertionFailure();

    /**
     * Extracts the last values of all variables which name matches the given
     * String.
     *
     * @param variableMatcher
     *            a regex which should match all variable names, e.g "votes1",
     *            "votes2", ...
     * @return a list containing the last values a variable with the following
     *         name has
     */
    public abstract List<ResultValueWrapper> readVariableValue(String variableMatcher);

    public ElectionSimulationData getOrigVoting() {
        return origVoting;
    }

    public void setOrigVoting(final ElectionSimulationData origVotingData) {
        this.origVoting = origVotingData;
    }

    public ElectionSimulationData getOrigWinner() {
        return origWinner;
    }

    public void setOrigWinner(final ElectionSimulationData origWinnerData) {
        this.origWinner = origWinnerData;
    }

    public ElectionSimulationData getNewVotes() {
        return newVotes;
    }

    public void setNewVotes(final ElectionSimulationData newVoteData) {
        this.newVotes = newVoteData;
    }

    public ElectionSimulationData getNewWinner() {
        return newWinner;
    }

    public void setNewWinner(final ElectionSimulationData newWinnerData) {
        this.newWinner = newWinnerData;
    }

    public void setOwner(final ResultTreeItem ownerItem) {
        this.owner = ownerItem;
    }

    public AnalysisType getAnalysisType() {
        return owner.getOwner().getAnalysisType();
    }

    public boolean wasStarted() {
        return wasStarted;
    }

    public void setLastTmpResult(final List<String> tmpResult) {
        // TODO maybe refactor this so no "tmp" result is used
        this.result = tmpResult;
        // this.lastTmpResult = tmpResult; TODO
    }

    public List<String> getLastTmpResult() {
        return this.lastTmpResult;
    }

    public void setLastTmpError(final List<String> tmpError) {
        this.error = tmpError;
        // this.lastTmpError = tmpError; TODO
    }

    public List<String> getLastTmpError() {
        return this.lastTmpError;
    }

    public void addStatusString(final String statusToAdd) {
        this.statusStrings.add(statusToAdd);
    }

    public List<String> getStatusStrings() {
        return this.statusStrings;
    }

    private List<String> addNewLineToList(final List<String> toConvert) {
        List<String> toReturn = new ArrayList<String>();
        for (int i = 0; i < toConvert.size(); i++) {
            toReturn.add(toConvert.get(i) + "\n");
        }
        return toReturn;
    }

    public List<String> getResultText() {
        return addNewLineToList(result);
    }

    public List<String> getErrorText() {
        return addNewLineToList(error);
    }

    public void setExitCode(final int exitCodeNumber) {
        this.exitCode = exitCodeNumber;
    }

    public int getExitCode() {
        return isFinished() ? this.exitCode : -1;
    }
}
