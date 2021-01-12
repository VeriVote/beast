package edu.pse.beast.api.electiondescription;

import java.util.List;

/*
 * so far:
 * 
 * input:
 * approval int [V][C]
 * preference int [V][C]
 * single choice int[V]
 * single choice stack int[C]
 * TODO Parameter für range (0-1), (0-100)
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

	public int getListDimensions() {
		return listDimensions;
	}

	public List<CBMCVars> getListSizes() {
		return listSizes;
	}

	public CElectionSimpleTypes getSimpleType() {
		return simpleType;
	}

	public static CElectionVotingType of(VotingOutputTypes outType) {
		CElectionVotingType created = new CElectionVotingType();
		switch (outType) {
		case CANDIDATE_LIST:
			created.simpleType = CElectionSimpleTypes.UNSIGNED_INT;
			created.listDimensions = 1;
			created.listSizes = List.of(CBMCVars.AMT_CANDIDATES);
			break;
		case PARLIAMENT:
			created.simpleType = CElectionSimpleTypes.UNSIGNED_INT;
			created.listDimensions = 1;
			created.listSizes = List.of(CBMCVars.AMT_CANDIDATES);
			break;
		case PARLIAMENT_STACK:
			created.simpleType = CElectionSimpleTypes.UNSIGNED_INT;
			created.listDimensions = 1;
			created.listSizes = List.of(CBMCVars.AMT_CANDIDATES);
			break;
		case SINGLE_CANDIDATE:
			created.simpleType = CElectionSimpleTypes.UNSIGNED_INT;
			created.listDimensions = 0;
			created.listSizes = List.of();
			break;
		default:
			break;
		}
		return created;
	}

	public static CElectionVotingType of(VotingInputTypes inType) {
		CElectionVotingType created = new CElectionVotingType();
		switch (inType) {
		case APPROVAL:
			created.simpleType = CElectionSimpleTypes.UNSIGNED_INT;
			created.listDimensions = 2;
			created.listSizes = List.of(CBMCVars.AMT_VOTERS, CBMCVars.AMT_CANDIDATES);
			break;
		case PREFERENCE:
			created.simpleType = CElectionSimpleTypes.UNSIGNED_INT;
			created.listDimensions = 2;
			created.listSizes = List.of(CBMCVars.AMT_VOTERS, CBMCVars.AMT_CANDIDATES);
			break;
		case SINGLE_CHOICE:
			created.simpleType = CElectionSimpleTypes.UNSIGNED_INT;
			created.listDimensions = 1;
			created.listSizes = List.of(CBMCVars.AMT_VOTERS);
			break;
		case SINGLE_CHOICE_STACK:
			created.simpleType = CElectionSimpleTypes.UNSIGNED_INT;
			created.listDimensions = 1;
			created.listSizes = List.of(CBMCVars.AMT_VOTERS);
			break;
		case WEIGHTED_APPROVAL:
			created.simpleType = CElectionSimpleTypes.UNSIGNED_INT;
			created.listDimensions = 2;
			created.listSizes = List.of(CBMCVars.AMT_VOTERS, CBMCVars.AMT_CANDIDATES);
			break;
		default:
			break;
		}
		return created;
	}

}
