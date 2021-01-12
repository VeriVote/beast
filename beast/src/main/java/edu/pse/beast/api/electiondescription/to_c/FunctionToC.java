package edu.pse.beast.api.electiondescription.to_c;

import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.VotingSigFunction;

public class FunctionToC {
	public static String getFunctionSignature(VotingSigFunction func) {
		return votingTypeToCString(CElectionVotingType.of(func.getOutputType())) + " " + func.getName() + "("
				+ votingTypeToCString(CElectionVotingType.of(func.getInputType())) + " " + func.getVotingVarName()
				+ ")";
	}

	public static String getConstPreamble(VotingSigFunction func) {
		return votingTypeToCString(CElectionVotingType.of(func.getOutputType())) + " " + func.getResultVarName() + ";";
	}

	public static String getConstEnding(VotingSigFunction func) {
		return "return " + func.getResultVarName() + ";";
	}
	
	public static String votingTypeToCString(CElectionVotingType type) {
		String arrayBracks = "";
		for (int i = 0; i < type.getListDimensions(); ++i) {
			arrayBracks += "[" + CBMCVarNames.name(type.getListSizes().get(i)) + "]";
		}
		return type.getSimpleType().toCType() + arrayBracks;
	}

}
