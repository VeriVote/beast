package edu.pse.beast.datatypes.electioncheckparameter;

import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class ElectionCheckParameter {
    private final List<Integer> amountVoters;
    private final List<Integer> amountCandidates;
    private final List<Integer> amountSeats;
    private final TimeOut timeOut;
    private final Integer processes;
    private final String argument;
    private final int marginVotes;
    private final int marginCandidates;
    private final int marginSeats;

    /**
     *
     * @param amountOfVoters        the list that specifies the range of voters
     * @param amountOfCandidates    the list that specifies the range of candidates
     * @param amountOfSeats         the list that specifies the range of seats
     * @param marginVoteNumber      the amount of votes for the computed margin
     * @param marginCandidateNumber the amount of candidates for the computed margin
     * @param marginSeatNumber      the amount of seats for the computed margin
     * @param timeOutVal            the timeout that specifies how long the checker
     *                              should run
     * @param procs                 max number of processes of the checker
     * @param arg                   the arguments given by the user
     */
    public ElectionCheckParameter(final List<Integer> amountOfVoters,
                                  final List<Integer> amountOfCandidates,
                                  final List<Integer> amountOfSeats,
                                  final int marginVoteNumber,
                                  final int marginCandidateNumber,
                                  final int marginSeatNumber,
                                  final TimeOut timeOutVal,
                                  final Integer procs,
                                  final String arg) {
        this.amountVoters = amountOfVoters;
        this.amountCandidates = amountOfCandidates;
        this.amountSeats = amountOfSeats;
        this.marginVotes = marginVoteNumber;
        this.marginCandidates = marginCandidateNumber;
        this.marginSeats = marginSeatNumber;
        this.timeOut = timeOutVal;
        this.processes = procs;
        this.argument = arg;
    }

    public ElectionCheckParameter(final List<Integer> amountOfVoters,
                                  final List<Integer> amountOfCandidates,
                                  final List<Integer> amountOfSeats,
                                  final TimeOut timeOutVal,
                                  final Integer procs,
                                  final String arg) {
        this(amountOfVoters, amountOfCandidates, amountOfSeats,
             1, 1, 1, timeOutVal, procs, arg);
    }

    /**
     *
     * @return the list that specifies the range of voters
     */
    public List<Integer> getAmountVoters() {
        return amountVoters;
    }

    /**
     *
     * @return the list that specifies the range of candidates
     */
    public List<Integer> getAmountCandidates() {
        return amountCandidates;
    }

    /**
     *
     * @return the list that specifies the range of seats
     */
    public List<Integer> getAmountSeats() {
        return amountSeats;
    }

    /**
     *
     * @return the timeout that specifies how long the checker should run
     */
    public TimeOut getTimeout() {
        return timeOut;
    }

    /**
     *
     * @return the amount of processes the user wishes to run at the same time
     */
    public Integer getProcesses() {
        return processes;
    }

    /**
     *
     * @return the String that defines the advanced options
     */
    public String getArgument() {
        return argument;
    }

    public int getMarginVotes() {
        return marginVotes;
    }

    public int getMarginCandidates() {
        return marginCandidates;
    }

    public int getMarginSeats() {
        return marginSeats;
    }

    public int getNumVotingPoints() {
        return GUIController.getController().getElectionSimulation().getNumVotingPoints();
    }
}
