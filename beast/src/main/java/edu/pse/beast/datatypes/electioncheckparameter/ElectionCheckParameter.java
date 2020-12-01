package edu.pse.beast.datatypes.electioncheckparameter;

import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;

/**
 * The Class ElectionCheckParameter.
 *
 * @author Lukas Stapelbroek
 */
public class ElectionCheckParameter {

    /** The amount voters. */
    private final List<Integer> amountVoters;

    /** The amount candidates. */
    private final List<Integer> amountCandidates;

    /** The amount seats. */
    private final List<Integer> amountSeats;

    /** The time out. */
    private final TimeOut timeOut;

    /** The processes. */
    private final Integer maxNumProcesses;

    /** The argument. */
    private final String argument;

    /** The margin votes. */
    private final int marginVotes;

    /** The margin candidates. */
    private final int marginCandidates;

    /** The margin seats. */
    private final int marginSeats;

    /**
     * Instantiates a new election check parameter.
     *
     * @param amountOfVoters
     *            the list that specifies the range of voters
     * @param amountOfCandidates
     *            the list that specifies the range of candidates
     * @param amountOfSeats
     *            the list that specifies the range of seats
     * @param marginVoteNumber
     *            the amount of votes for the computed margin
     * @param marginCandidateNumber
     *            the amount of candidates for the computed margin
     * @param marginSeatNumber
     *            the amount of seats for the computed margin
     * @param timeOutVal
     *            the timeout that specifies how long the checker should run
     * @param maxNumProcesses
     *            max number of processes of the checker
     * @param arg
     *            the arguments given by the user
     */
    public ElectionCheckParameter(final List<Integer> amountOfVoters,
                                  final List<Integer> amountOfCandidates,
                                  final List<Integer> amountOfSeats,
                                  final int marginVoteNumber,
                                  final int marginCandidateNumber,
                                  final int marginSeatNumber,
                                  final TimeOut timeOutVal,
                                  final Integer maxNumProcesses,
                                  final String arg) {
        this.amountVoters = amountOfVoters;
        this.amountCandidates = amountOfCandidates;
        this.amountSeats = amountOfSeats;
        this.marginVotes = marginVoteNumber;
        this.marginCandidates = marginCandidateNumber;
        this.marginSeats = marginSeatNumber;
        this.timeOut = timeOutVal;
        this.maxNumProcesses = maxNumProcesses;
        this.argument = arg;
    }

    /**
     * Instantiates a new election check parameter.
     *
     * @param amountOfVoters
     *            the amount of voters
     * @param amountOfCandidates
     *            the amount of candidates
     * @param amountOfSeats
     *            the amount of seats
     * @param timeOutVal
     *            the time out val
     * @param procs
     *            the procs
     * @param arg
     *            the arg
     */
    public ElectionCheckParameter(final List<Integer> amountOfVoters,
                                  final List<Integer> amountOfCandidates,
                                  final List<Integer> amountOfSeats,
                                  final TimeOut timeOutVal,
                                  final Integer procs, final String arg) {
        this(amountOfVoters, amountOfCandidates, amountOfSeats, 1, 1, 1,
             timeOutVal, procs, arg);
    }

    /**
     * Gets the amount voters.
     *
     * @return the list that specifies the range of voters
     */
    public List<Integer> getRangeOfVoters() {
        return amountVoters;
    }

    /**
     * Gets the amount candidates.
     *
     * @return the list that specifies the range of candidates
     */
    public List<Integer> getRangeofCandidates() {
        return amountCandidates;
    }

    /**
     * Gets the amount seats.
     *
     * @return the list that specifies the range of seats
     */
    public List<Integer> getRangeOfSeats() {
        return amountSeats;
    }

    /**
     * Gets the timeout.
     *
     * @return the timeout that specifies how long the checker should run
     */
    public TimeOut getTimeout() {
        return timeOut;
    }

    /**
     * Gets the processes.
     *
     * @return the amount of processes the user wishes to run at the same time
     */
    public Integer getProcesses() {
        return maxNumProcesses;
    }

    /**
     * Gets the argument.
     *
     * @return the String that defines the advanced options
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Gets the margin votes.
     *
     * @return the margin votes
     */
    public int getMarginVotes() {
        return marginVotes;
    }

    /**
     * Gets the margin candidates.
     *
     * @return the margin candidates
     */
    public int getMarginCandidates() {
        return marginCandidates;
    }

    /**
     * Gets the margin seats.
     *
     * @return the margin seats
     */
    public int getMarginSeats() {
        return marginSeats;
    }

    /**
     * Gets the num voting points.
     *
     * @return the num voting points
     */
    public int getNumVotingPoints() {
        return GUIController.getController().getElectionSimulation()
                .getNumVotingPoints();
    }
}
