package edu.pse.beast.datatypes.electioncheckparameter;

import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;

/**
 * 
 * @author Lukas
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
     * @param amountVoters     the list that specifies the range of voters
     * @param amountCandidates the list that specifies the range of candidates
     * @param amountSeats      the list that specifies the range of seats
     * @param timeOut          the timeout that specifies how long the checker
     *                         should run
     * @param processes        max number of processes of the checker
     * @param argument         the arguments given by the user
     */
    public ElectionCheckParameter(List<Integer> amountVoters, List<Integer> amountCandidates, List<Integer> amountSeats,
            int marginVotes, int marginCandidates, int marginSeats, TimeOut timeOut, Integer processes,
            String argument) {
        this.amountVoters = amountVoters;
        this.amountCandidates = amountCandidates;
        this.amountSeats = amountSeats;
        this.marginVotes = marginVotes;
        this.marginCandidates = marginCandidates;
        this.marginSeats = marginSeats;
        this.timeOut = timeOut;
        this.processes = processes;
        this.argument = argument;
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

        System.out.println("TODO getNumVotingPoints in electionCheckParameters");

        return GUIController.getController().getElectionSimulation().getNumVotingPoints();
    }
}