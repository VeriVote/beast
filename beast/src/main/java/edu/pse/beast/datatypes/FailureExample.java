package edu.pse.beast.datatypes;

import java.util.List;

import edu.pse.beast.propertychecker.CBMC_Result_Wrapper_long;
import edu.pse.beast.propertychecker.CBMC_Result_Wrapper_multiArray;
import edu.pse.beast.propertychecker.CBMC_Result_Wrapper_singleArray;

/**
 * This class provides the result presenter with the necessary data about the counterexample.
 * @author Justin & Lukas
 */
public class FailureExample {

	private final ElectionType electionType;

	private final List<CBMC_Result_Wrapper_singleArray> votes;

	private final List<CBMC_Result_Wrapper_multiArray> voteList;

	private final List<CBMC_Result_Wrapper_long> elect;

	private final List<CBMC_Result_Wrapper_singleArray> seats;
	
	private final int numOfCandidates;
	
	private final int numOfSeats;
	
	private final int numOfVoters;
	
	private final int numOfElections;

	/**
	 * Creates the FailureExample from the returned data of CBMC. If specific data is not given, initialize with null reference.
	 * @param electionType The type of the election description
	 * @param votes A list of votings for a single candidate given as an integer list. Null if another voting procedure was used.
	 * @param voteList A list of votings for multiple candidates (maybe as a list or range voting). Null if only one candidate could be chosen.
	 * @param elect A list of elected candidates from different votings. Null if more than one candidate is elected.
	 * @param seats A list of elected seasts from different votings. Null if only one candidate is elected.
	 * @param numOfCandidates The number of candidates in an election.
	 * @param numOfSeats The number of seats that are awarded in an election.
	 * @param numOfVoters The number of voters that voted for the candidates.
	 */
	public FailureExample(ElectionType electionType, List<CBMC_Result_Wrapper_singleArray> votes, List<CBMC_Result_Wrapper_multiArray> voteList,
			List<CBMC_Result_Wrapper_long> elect, List<CBMC_Result_Wrapper_singleArray> seats, int numOfCandidates, int numOfSeats, int numOfVoters) {
		
		this.electionType = electionType;
		this.votes = votes;
		this.voteList = voteList;
		this.elect = elect;
		this.seats = seats;
		this.numOfCandidates = numOfCandidates;
		this.numOfSeats = numOfSeats;
		this.numOfVoters = numOfVoters;

		if (isChooseOneCandidate()) this.numOfElections = votes.size();
		else this.numOfElections = voteList.size();
	}

	public ElectionType getElectionType() {
		return electionType;
	}

	public List<CBMC_Result_Wrapper_singleArray> getVotes() {
		return votes;
	}

	public List<CBMC_Result_Wrapper_multiArray> getVoteList() {
		return voteList;
	}

	public List<CBMC_Result_Wrapper_long> getElect() {
		return elect;
	}

	public List<CBMC_Result_Wrapper_singleArray> getSeats() {
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
		if (getVotes() != null) return true;
		else return false;
	}
	
	public boolean isOneSeatOnly() {
		if (getElect() != null) return true;
		return false;
	}
	
	public String getTypeString() {
		switch (electionType) {
		case SINGLECHOICE : return "single-choice"; 
		case APPROVAL : return "approval"; 
		case WEIGHTEDAPPROVAL : return "weighted-approval";
		case PREFERENCE : return "preference";
		default : return "unknown";
		}
	}

}
