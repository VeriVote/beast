package edu.pse.beast.api.electiondescription.to_c;

import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.TypeManager;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;

public class FunctionToC {

	public static CTypeNameBrackets votingTypeToC(CElectionVotingType type,
			String name, CodeGenOptions codeGenOptions) {
		String arrayBracks = "";
		for (int i = 0; i < type.getListDimensions(); ++i) {
			String arraySize = "";
			switch (type.getListSizes().get(i)) {
				case AMT_VOTERS:
					arraySize = codeGenOptions.getCbmcAmountMaxVotersVarName();
					break;
				case AMT_CANDIDATES :
					arraySize = codeGenOptions.getCbmcAmountMaxCandidatesVarName();
					break;
				case AMT_SEATS :
					arraySize = codeGenOptions.getCbmcAmountMaxSeatsVarName();
					break;
			}
			arrayBracks += "[" + arraySize + "]";
		}
		return new CTypeNameBrackets(
				TypeManager.SimpleTypeToCType(type.getSimpleType()), name,
				arrayBracks);
	}
}
