package edu.pse.beast.api.codegen.helperfunctions;

public enum HelperFunctionTypes {
	INTERSECT_VOTES, COPY_VOTES, COMPARE_SINGLE_VOTES_EQUAL, INIT_VOTE, COMPARE_VOTE_EQUAL;
	
	public String str() {
		switch(this) {
		case COMPARE_SINGLE_VOTES_EQUAL:
			return "compareSingleVotesEqual";
		case COPY_VOTES:
			return "copyVotes";
		case INTERSECT_VOTES:
			return "intersectVotes";
		case INIT_VOTE:
			return "initializeVote";		
		case COMPARE_VOTE_EQUAL:
			return "compareVoteEqual";
		default:
			return null;
		}
	}
	
}
