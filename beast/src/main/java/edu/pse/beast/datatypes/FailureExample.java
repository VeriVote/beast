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

	public FailureExample(InternalTypeContainer internalType, List<int[]> votes, List<int[][]> voteList,
			List<Integer> elect, List<int[]> seats, int numOfCandidates) {
		
		this.internalType = internalType;
		this.votes = votes;
		this.voteList = voteList;
		this.elect = elect;
		this.seats = seats;
		this.numOfCandidates = numOfCandidates;

	}
	

}
