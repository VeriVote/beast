package edu.pse.beast.datatypes;

import edu.pse.beast.datatypes.electiondescription.ElectionType;
import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.propertychecker.SymbolicVarNameAndNumber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides the result presenter with the necessary data about the
 * counterexample.
 * 
 * @author Justin & Lukas
 */
public class FailureExample {

    private final ElectionType electionType;

    private final List<CBMCResultWrapperSingleArray> votes;

    private final List<CBMCResultWrapperMultiArray> voteList;

    private final List<CBMCResultWrapperLong> elect;

    private final List<CBMCResultWrapperSingleArray> seats;

    private final int numOfCandidates;

    private final int numOfSeats;

    private final int numOfVoters;

    private final int numOfElections;
    
    private List<SymbolicVarNameAndNumber> symbCandidates = new ArrayList<SymbolicVarNameAndNumber>();
    private List<SymbolicVarNameAndNumber> symbVoters = new ArrayList<SymbolicVarNameAndNumber>();
    private List<SymbolicVarNameAndNumber> symbSeats = new ArrayList<SymbolicVarNameAndNumber>();

    /**
     * Creates the FailureExample from the returned data of CBMC. If specific
     * data is not given, initialize with null reference.
     * 
     * @param electionType
     *            The type of the election description
     * @param votes
     *            A list of votings for a single candidate given as an integer
     *            list. Null if another voting procedure was used.
     * @param voteList
     *            A list of votings for multiple candidates (maybe as a list or
     *            range voting). Null if only one candidate could be chosen.
     * @param elect
     *            A list of elected candidates from different votings. Null if
     *            more than one candidate is elected.
     * @param seats
     *            A list of elected seasts from different votings. Null if only
     *            one candidate is elected.
     * @param numOfCandidates
     *            The number of candidates in an election.
     * @param numOfSeats
     *            The number of seats that are awarded in an election.
     * @param numOfVoters
     *            The number of voters that voted for the candidates.
     */
    public FailureExample(ElectionType electionType, List<CBMCResultWrapperSingleArray> votes,
            List<CBMCResultWrapperMultiArray> voteList, List<CBMCResultWrapperLong> elect,
            List<CBMCResultWrapperSingleArray> seats, int numOfCandidates, int numOfSeats, int numOfVoters) {

        this.electionType = electionType;
        this.votes = votes;
        this.voteList = voteList;
        this.elect = elect;
        this.seats = seats;
        this.numOfCandidates = numOfCandidates;
        this.numOfSeats = numOfSeats;
        this.numOfVoters = numOfVoters;

        if (isChooseOneCandidate())
            this.numOfElections = votes.size();
        else
            this.numOfElections = voteList.size();
    }

    public ElectionType getElectionType() {
        return electionType;
    }

    public List<CBMCResultWrapperSingleArray> getVotes() {
        return votes;
    }

    public List<CBMCResultWrapperMultiArray> getVoteList() {
        return voteList;
    }

    public List<CBMCResultWrapperLong> getElect() {
        return elect;
    }

    public List<CBMCResultWrapperSingleArray> getSeats() {
        return seats;
    }

    public int getNumOfCandidates() {
        return numOfCandidates;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public int getNumOfVoters() {
        return numOfVoters;
    }

    public int getNumOfElections() {
        return numOfElections;
    }

    public boolean isChooseOneCandidate() {
        if (electionType == ElectionType.SINGLECHOICE)
            return true;
        else
            return false;
    }

    public boolean isOneSeatOnly() {
        if (numOfSeats == 1)
            return true;
        return false;
    }
    
    /**
     * saves a candidate symbolic variable in the list
     * @param name
     * @param number
     */
    public void addSymbolicCadidate(String name, long number) {
    	symbCandidates.add(new SymbolicVarNameAndNumber(name, number));
    }
    
    /**
     * saves a seat symbolic variable in the list
     * @param name
     * @param number
     */
    public void addSymbolicSeat(String name, long number) {
    	symbSeats.add(new SymbolicVarNameAndNumber(name, number));
    }
    
    /**
     * saves a voter symbolic variable in the list
     * @param name
     * @param number
     */
    public void addSymbolicVoters(String name, long number) {
    	symbVoters.add(new SymbolicVarNameAndNumber(name, number));
    }
    
    public String getSymbolicCandidateForIndex(long number) {
        String toOutput = "";        
        for (Iterator<SymbolicVarNameAndNumber> iterator = symbCandidates.iterator(); iterator.hasNext();) {
            SymbolicVarNameAndNumber symbolicVarCandidate = (SymbolicVarNameAndNumber) iterator.next();        
            if (symbolicVarCandidate.getNumber() == number) { //if multiple variables share the same number
                if (toOutput != "") {
                    toOutput = toOutput + " / " + symbolicVarCandidate.getName();
                } else { //if this is the first variable with this index 
                    toOutput = symbolicVarCandidate.getName();
                }
            }
        }
        if (toOutput == "") {
        	toOutput = "" + number;
        }
        return toOutput;
    }
    
    public String getSymbolicSeatForIndex(long number) {
        String toOutput = "";        
        for (Iterator<SymbolicVarNameAndNumber> iterator = symbSeats.iterator(); iterator.hasNext();) {
            SymbolicVarNameAndNumber symbolicVarSeat = (SymbolicVarNameAndNumber) iterator.next();        
            if (symbolicVarSeat.getNumber() == number) { //if multiple variables share the same number
                if (toOutput != "") {
                    toOutput = toOutput + " / " + symbolicVarSeat.getName();
                } else { //if this is the first variable with this index 
                    toOutput = symbolicVarSeat.getName();
                }
            }
        }
        if (toOutput == "") {
        	toOutput = "" + number;
        }
        return toOutput;
    }
    
    public String getSymbolicVoterForIndex(long number) {
        String toOutput = "";  
        for (Iterator<SymbolicVarNameAndNumber> iterator = symbVoters.iterator(); iterator.hasNext();) {
            SymbolicVarNameAndNumber symbolicVarVoter = (SymbolicVarNameAndNumber) iterator.next();        
            if (symbolicVarVoter.getNumber() == number) { //if multiple variables share the same number
                if (toOutput != "") {
                    toOutput = toOutput + " / " + symbolicVarVoter.getName();
                } else { //if this is the first variable with this index 
                    toOutput = symbolicVarVoter.getName();
                }
            }
        }
        if (toOutput == "") {
        	toOutput = "" + number;
        }
        return toOutput;
    }
    

    public String getTypeString() {
        switch (electionType) {
        case SINGLECHOICE:
            return "single-choice";
        case APPROVAL:
            return "approval";
        case WEIGHTEDAPPROVAL:
            return "weighted-approval";
        case PREFERENCE:
            return "preference";
        default:
            return "unknown";
        }
    }

}
