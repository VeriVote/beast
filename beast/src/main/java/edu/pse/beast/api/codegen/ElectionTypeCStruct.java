package edu.pse.beast.api.codegen;

import edu.pse.beast.api.codegen.c_code.CForLoop;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.c_code.CTypeAndName;
import edu.pse.beast.api.electiondescription.CElectionVotingType;

public class ElectionTypeCStruct {
	private CElectionVotingType votingType;
	private CStruct struct;
	
	public ElectionTypeCStruct(CElectionVotingType votingType, CStruct struct) {
		super();
		this.votingType = votingType;
		this.struct = struct;
	}

	public CStruct getStruct() {
		return struct;
	}
	
	public CElectionVotingType getVotingType() {
		return votingType;
	}

	private final String condString = "COUNTER < AMT";
	
	public CForLoop loopOverOuterList(CTypeAndName counterVar, String tempInnerVarName) {
		String cond = condString.replaceAll("COUNTER", counterVar.getName()).replaceAll("AMT", "");
		CForLoop created = new CForLoop(counterVar, "", "");
		return created;
	}
}
