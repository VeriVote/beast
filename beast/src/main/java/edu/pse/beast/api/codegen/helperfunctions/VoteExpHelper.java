package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;

public class VoteExpHelper {
	public static String getVarFromVoteAccess(String varName, String voteVarName, String accessingVarName, CodeGenOptions options, 
			ElectionTypeCStruct voteStruct) {
		String code = "unsigned int * VAR_NAME = VOTE_VAR.LIST_MEMBER[INDEX]";
		code = code.replaceAll("VAR_NAME", varName);
		code = code.replaceAll("VOTE_VAR", voteVarName);
		code = code.replaceAll("LIST_MEMBER", voteStruct.getListName());
		code = code.replaceAll("INDEX", accessingVarName);

		return code;
	}
}
