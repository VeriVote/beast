package edu.pse.beast.api.codegen.helperfunctions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.electiondescription.CElectionVotingType;

public class HelperFunctionMap {
	private Set<String> containedHelperFunctionNames = new HashSet<>();
	private List<HelperFunction> helperFunctions = new ArrayList<>();
	private Map<HelperFunctionTypes, HelperFunction> funcByType = new HashMap<>();

	public void add(HelperFunction func) {
		if (!containedHelperFunctionNames.contains(func.uniqueName())) {
			helperFunctions.add(func);
			containedHelperFunctionNames.add(func.uniqueName());
			funcByType.put(func.getType(), func);
		}
	}

	public List<HelperFunction> getHelperFunctions() {
		return helperFunctions;
	}

	public CopyVotesHelperFunc getCopyVotesFunction() {
		return (CopyVotesHelperFunc) funcByType.get(HelperFunctionTypes.COPY_VOTES);
	}

	public CompareSingleVotesHelperFunc getCompareSingleVotesFunction() {
		return (CompareSingleVotesHelperFunc) funcByType.get(HelperFunctionTypes.COMPARE_SINGLE_VOTES_EQUAL);
	}

	public InitializeVoteHelperFunc getInitVoteFunction() {
		return (InitializeVoteHelperFunc) funcByType.get(HelperFunctionTypes.INIT_VOTE);
	}

	public CompareVotesHelperFunction getCompareVotesFunction() {
		return (CompareVotesHelperFunction) funcByType.get(HelperFunctionTypes.COMPARE_VOTE_EQUAL);
	}
	
	public IntersectVotesHelperFunc getIntersectVotesFunction() {
		return (IntersectVotesHelperFunc) funcByType.get(HelperFunctionTypes.INTERSECT_VOTES);
	}
}
