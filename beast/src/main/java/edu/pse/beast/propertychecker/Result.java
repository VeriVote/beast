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
 * The Class Result.
 *
 * @author Niels Hanselmann, Lukas Stapelbroek
 */
public abstract class Result implements ResultInterface {

    /** The orig voting. */
    private ElectionSimulationData origVoting;

    /** The orig winner. */
    private ElectionSimulationData origWinner;

    /** The new votes. */
    private ElectionSimulationData newVotes;

    /** The new winner. */
    private ElectionSimulationData newWinner;

    /** The owner. */
    private transient ResultTreeItem owner;

    /** The was started. */
    private boolean wasStarted = false;

    /** The valid. */
    private boolean valid = false;

    /** The finished. */
    private boolean finished = false;

    /** The result. */
    private List<String> result;

    /** The error. */
    private List<String> error;

    /** The last tmp result. */
    private List<String> lastTmpResult;

    /** The last tmp error. */
    private List<String> lastTmpError;

    /** The time out. */
    private boolean timeOut = false;

    /** The success. */
    private boolean success = false;

    /** The has sub result. */
    private boolean hasSubResult = false;

    /** The num voters. */
    private int numVoters;

    /** The num seats. */
    private int numSeats;

    /** The num candidates. */
    private int numCandidates;

    /** The election description. */
    private ElectionDescription electionDescription;

    /** The property. */
    private PreAndPostConditionsDescription property;

    /** The forcefulle stopped. */
    private boolean forcefulleStopped;

    /** The has margin. */
    private boolean hasMargin = false;

    /** The final margin. */
    private int finalMargin = -1;

    /** The sub result. */
    private Result subResult = null;

    /** The is margin comp. */
    private boolean isMarginComp = false;

    /** The status strings. */
    private List<String> statusStrings = new ArrayList<String>();

    /** The exit code. */
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

    /**
     * Checks if is test.
     *
     * @return true, if is test
     */
    public boolean isTest() {
        return getAnalysisType() == AnalysisType.Test;
    }

    /**
     * Checks if is margin comp.
     *
     * @return true, if is margin comp
     */
    public boolean isMarginComp() {
        return getAnalysisType() == AnalysisType.Margin;
    }

    /**
     * Sets the margin comp.
     *
     * @param isMarginComputation the new margin comp
     */
    public void setMarginComp(final boolean isMarginComputation) {
        this.isMarginComp = isMarginComputation;
    }

    @Override
    public boolean readyToPresent() {
        return finished;
    }

    /**
     * Checks if is valid.
     *
     * @return whether the result is valid (the checking finished normally and
     *         was not stopped by something else).
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Checks if is finished.
     *
     * @return if the result is ready yet
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Gets the result.
     *
     * @return the result List
     */
    public List<String> getResult() {
        return result;
    }

    /**
     * Gets the error.
     *
     * @return the error list
     */
    public List<String> getError() {
        return error;
    }

    /**
     * Checks if is timed out.
     *
     * @return if the result did not finish because of a timeout
     */
    public boolean isTimedOut() {
        return timeOut;
    }

    /**
     * Checks if is success.
     *
     * @return whether the checking was successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Checks if is focefully stopped.
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

    /**
     * Sets the started.
     */
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
     * Sets the success.
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
     * Gets the num voters.
     *
     * @return the amount of votes for which the verification failed
     */
    public int getNumVoters() {
        return numVoters;
    }

    /**
     * Gets the num seats.
     *
     * @return the amount of seats for which the verification failed
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * Gets the num candidates.
     *
     * @return the amount of candidates for which the verification failed
     */
    public int getNumCandidates() {
        return numCandidates;
    }

    /**
     * Gets the election description.
     *
     * @return the election type
     */
    public ElectionDescription getElectionDescription() {
        return electionDescription;
    }

    /**
     * Sets the num voters.
     *
     * @param numVotersInteger            the amount of voters to be set
     */
    public void setNumVoters(final int numVotersInteger) {
        this.numVoters = numVotersInteger;
    }

    /**
     * Sets the num seats.
     *
     * @param numSeatsInteger            the amount of seats to be set
     */
    public void setNumSeats(final int numSeatsInteger) {
        this.numSeats = numSeatsInteger;
    }

    /**
     * Sets the num candidates.
     *
     * @param numCand            the amount of candidates to be set
     */
    public void setNumCandidates(final int numCand) {
        this.numCandidates = numCand;
    }

    /**
     * Sets the election type.
     *
     * @param electionDescr            the election type to be set
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

    /**
     * Sets the final margin.
     *
     * @param margin the new final margin
     */
    public void setFinalMargin(final int margin) {
        this.finalMargin = margin;
    }

    /**
     * Gets the final margin.
     *
     * @return the final margin
     */
    public int getFinalMargin() {
        return finalMargin;
    }

    /**
     * Sets the checks for final margin.
     *
     * @param b the new checks for final margin
     */
    public void setHasFinalMargin(final boolean b) {
        this.hasMargin = b;
    }

    /**
     * Checks for final margin.
     *
     * @return true, if successful
     */
    public boolean hasFinalMargin() {
        return hasMargin;
    }

    /**
     * Adds the sub result.
     *
     * @param subResultVal the sub result val
     */
    public void addSubResult(final Result subResultVal) {
        this.hasSubResult = true;
        this.subResult = subResultVal;
    }

    /**
     * Gets the property desctiption.
     *
     * @return the property desctiption
     */
    public PreAndPostConditionsDescription getPropertyDesctiption() {
        return property;
    }

    /**
     * Checks for sub result.
     *
     * @return true, if successful
     */
    public boolean hasSubResult() {
        return this.hasSubResult;
    }

    /**
     * Gets the sub result.
     *
     * @return the sub result
     */
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

    /**
     * Gets the orig voting.
     *
     * @return the orig voting
     */
    public ElectionSimulationData getOrigVoting() {
        return origVoting;
    }

    /**
     * Sets the orig voting.
     *
     * @param origVotingData the new orig voting
     */
    public void setOrigVoting(final ElectionSimulationData origVotingData) {
        this.origVoting = origVotingData;
    }

    /**
     * Gets the orig winner.
     *
     * @return the orig winner
     */
    public ElectionSimulationData getOrigWinner() {
        return origWinner;
    }

    /**
     * Sets the orig winner.
     *
     * @param origWinnerData the new orig winner
     */
    public void setOrigWinner(final ElectionSimulationData origWinnerData) {
        this.origWinner = origWinnerData;
    }

    /**
     * Gets the new votes.
     *
     * @return the new votes
     */
    public ElectionSimulationData getNewVotes() {
        return newVotes;
    }

    /**
     * Sets the new votes.
     *
     * @param newVoteData the new new votes
     */
    public void setNewVotes(final ElectionSimulationData newVoteData) {
        this.newVotes = newVoteData;
    }

    /**
     * Gets the new winner.
     *
     * @return the new winner
     */
    public ElectionSimulationData getNewWinner() {
        return newWinner;
    }

    /**
     * Sets the new winner.
     *
     * @param newWinnerData the new new winner
     */
    public void setNewWinner(final ElectionSimulationData newWinnerData) {
        this.newWinner = newWinnerData;
    }

    /**
     * Sets the owner.
     *
     * @param ownerItem the new owner
     */
    public void setOwner(final ResultTreeItem ownerItem) {
        this.owner = ownerItem;
    }

    /**
     * Gets the analysis type.
     *
     * @return the analysis type
     */
    public AnalysisType getAnalysisType() {
        return owner.getOwner().getAnalysisType();
    }

    /**
     * Was started.
     *
     * @return true, if successful
     */
    public boolean wasStarted() {
        return wasStarted;
    }

    /**
     * Sets the last tmp result.
     *
     * @param tmpResult the new last tmp result
     */
    public void setLastTmpResult(final List<String> tmpResult) {
        // TODO maybe refactor this so no "tmp" result is used
        this.result = tmpResult;
        // this.lastTmpResult = tmpResult; TODO
    }

    /**
     * Gets the last tmp result.
     *
     * @return the last tmp result
     */
    public List<String> getLastTmpResult() {
        return this.lastTmpResult;
    }

    /**
     * Sets the last tmp error.
     *
     * @param tmpError the new last tmp error
     */
    public void setLastTmpError(final List<String> tmpError) {
        this.error = tmpError;
        // this.lastTmpError = tmpError; TODO
    }

    /**
     * Gets the last tmp error.
     *
     * @return the last tmp error
     */
    public List<String> getLastTmpError() {
        return this.lastTmpError;
    }

    /**
     * Adds the status string.
     *
     * @param statusToAdd the status to add
     */
    public void addStatusString(final String statusToAdd) {
        this.statusStrings.add(statusToAdd);
    }

    /**
     * Gets the status strings.
     *
     * @return the status strings
     */
    public List<String> getStatusStrings() {
        return this.statusStrings;
    }

    /**
     * Adds the new line to list.
     *
     * @param toConvert the to convert
     * @return the list
     */
    private List<String> addNewLineToList(final List<String> toConvert) {
        List<String> toReturn = new ArrayList<String>();
        for (int i = 0; i < toConvert.size(); i++) {
            toReturn.add(toConvert.get(i) + "\n");
        }
        return toReturn;
    }

    /**
     * Gets the result text.
     *
     * @return the result text
     */
    public List<String> getResultText() {
        return addNewLineToList(result);
    }

    /**
     * Gets the error text.
     *
     * @return the error text
     */
    public List<String> getErrorText() {
        return addNewLineToList(error);
    }

    /**
     * Sets the exit code.
     *
     * @param exitCodeNumber the new exit code
     */
    public void setExitCode(final int exitCodeNumber) {
        this.exitCode = exitCodeNumber;
    }

    /**
     * Gets the exit code.
     *
     * @return the exit code
     */
    public int getExitCode() {
        return isFinished() ? this.exitCode : -1;
    }
}
