package edu.pse.beast.api.electiondescription.to_c;

import edu.pse.beast.api.codegen.TypeManager;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;

public class FunctionToC {

	public static String getConstEnding(VotingSigFunction func) {
		return "return " + func.getResultVarName() + ";";
	}

	public static CTypeNameBrackets votingTypeToC(CElectionVotingType type, String name) {
		String arrayBracks = "";
		for (int i = 0; i < type.getListDimensions(); ++i) {
			arrayBracks += "[" + CBMCVarNames.name(type.getListSizes().get(i)) + "]";
		}
		return new CTypeNameBrackets(TypeManager.SimpleTypeToCType(type.getSimpleType()), name, arrayBracks);
	}

}
