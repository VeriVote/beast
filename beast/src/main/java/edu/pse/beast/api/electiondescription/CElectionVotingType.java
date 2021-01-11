package edu.pse.beast.api.electiondescription;

import java.util.List;

/*
 * so far:
 * 
 * input:
 * approval int [V][C]
 * preference int [V][C]
 * single choice int[V]
 * single choice stack int[V]
 * weighted approval int[V][C]
 * 
 * output:
 * candidate list int [C]
 * parliament int [C]
 * parliament stack int [C]
 * single cand int
 */

public class CElectionVotingType {
	private int listDimensions;
	private CElectionSimpleTypes simpleType;
	private List<CBMCVars> listSizes;		
}
