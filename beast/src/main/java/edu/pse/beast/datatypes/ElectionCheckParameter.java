package edu.pse.beast.datatypes;

import java.util.List;

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
    private final String advanced;

    /**
     * 
     * @param amountVoters the list that specifies the range of voters
     * @param amountCandidates the list that specifies the range of candidates
     * @param amountSeats the list that specifies the range of seats
     * @param timeOut the timeout that specifies how long the checker should run
     */
    public ElectionCheckParameter(List<Integer> amountVoters, List<Integer> amountCandidates, 
            List<Integer> amountSeats, TimeOut timeOut, String advanced) {
        this.amountVoters = amountVoters;
        this.amountCandidates = amountCandidates;
        this.amountSeats = amountSeats;
        this.timeOut = timeOut;
        this.advanced = advanced;
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
     * @return the String that defines the advanced options 
     */
    public String getAdvanced() {
        return advanced;
    }
}