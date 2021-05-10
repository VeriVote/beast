package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;

public class PerformVoteHelper {

	private static String template = "ELECT_TYPE GENERATED_VAR = VOTE_FUNC_NAME(VOTE_VAR);\n";

	public static String generateCode(String generatedVarName,
			String voteVarName, ElectionTypeCStruct voteStruct,
			ElectionTypeCStruct voteResultStruct, CodeGenOptions options,
			String votingFunctionName) {
		String code = template;
		code = code.replaceAll("ELECT_TYPE",
				voteResultStruct.getStruct().getName());
		code = code.replaceAll("GENERATED_VAR", generatedVarName);
		code = code.replaceAll("VOTE_FUNC_NAME", votingFunctionName);
		code = code.replaceAll("VOTE_TYPE", voteStruct.getStruct().getName());
		code = code.replaceAll("VOTE_VAR", voteVarName);

		return code;
	}
}
