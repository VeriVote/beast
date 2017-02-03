package edu.pse.beast.datatypes;

import java.util.List;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;

/**
 * @author Justin & Lukas
 *
 */
public class FailureExample {

	private final InternalTypeContainer internalType;

	private final List<int[]> votes;

	private final List<int[][]> voteList;

	private final List<Integer> elect;

	private final List<int[]> seats;
	
	private final int numOfCandidates;
	
	private final int numOfSeats;
	
	private final int numOfVoters;

	/**
	 * Creates the FailureExample from the returned data of CBMC. If specific data is not given, initialize with null reference.
	 * @param internalType The type of the election description
	 * @param votes A list of votings for a single candidate given as an integer array. Null if another voting procedure was used.
	 * @param voteList A list of votings for multiple candidates (maybe as a list or range voting). Null if only one candidate could be chosen.
	 * @param elect A list of elected candidates from different votings. Null if more than one candidate is elected.
	 * @param seats A list of elected seasts from different votings. Null if only one candidate is elected.
	 * @param numOfCandidates The number of candidates in an election.
	 * @param numOfSeats The number of seats that are awarded in an election.
	 * @param numOfVoters The number of voters that voted for the candidates.
	 */
	public FailureExample(InternalTypeContainer internalType, List<int[]> votes, List<int[][]> voteList,
			List<Integer> elect, List<int[]> seats, int numOfCandidates, int numOfSeats, int numOfVoters) {
		
		this.internalType = internalType;
		this.votes = votes;
		this.voteList = voteList;
		this.elect = elect;
		this.seats = seats;
		this.numOfCandidates = numOfCandidates;
		this.numOfSeats = numOfSeats;
		this.numOfVoters = numOfVoters;

	}

	public InternalTypeContainer getInternalType() {
		return internalType;
	}

	public List<int[]> getVotes() {
		return votes;
	}

	public List<int[][]> getVoteList() {
		return voteList;
	}

	public List<Integer> getElect() {
		return elect;
	}

	public List<int[]> getSeats() {
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
	
	public boolean isChooseOneCandidate() {
		if (getVotes() != null) return true;
		else return false;
	}
	
	public boolean isOneSeatOnly() {
		if (getElect() != null) return true;
		return false;
	}

}
