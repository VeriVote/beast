package edu.pse.beast.api.electiondescription;

public enum VotingInputTypes {
	
	/*
	 * every voter gives a list of 0s and 1s
	 * which states whether they approve of the
	 * candidate in that position.
	 * 
	 * I.E a list such as 0,0,1,1
	 * 
	 * would mean that the voter does not
	 * approve of candidates 0 and 1 but 
	 * does approve of candidates 2 and 3
	 */
	APPROVAL, 
	/*
	 * The same as approval, but the numbers can now range
	 * from 0 to whatever
	 */
	WEIGHTED_APPROVAL,
	/*
	 * each voter gives a list of all candidates. 
	 * The position of each candidate in
	 * this list states how much the voter
	 * prefers this candidate.
	 * 
	 * Example: the list 4,3,0,1,2
	 * 
	 * means the voter approves most of
	 * candidate four, followed by 3 0 1 and
	 * 2
	 */
	PREFERENCE, 
	/*
	 * Each candidate gives a single value corresponding
	 * to the candidate they prefer
	 */
	SINGLE_CHOICE, 
	/*
	 * The voting input is a list of the amount
	 * of votes for each candidate.
	 * 
	 * For example the list 5,3,1,1
	 * 
	 * means candidate 0 got 5 votes,
	 * candidate 1 got 3, candidate 2 and candidate 3 got 1 
	 * each
	 */
	SINGLE_CHOICE_STACK,
}
