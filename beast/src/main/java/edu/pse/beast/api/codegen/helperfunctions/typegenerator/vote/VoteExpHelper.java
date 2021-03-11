package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;

import edu.pse.beast.api.codegen.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;

public class VoteExpHelper {
	public static String getVarFromVoteAccess(
			String voteVarName, 
			List<SymbolicCBMCVar> list, 
			CodeGenOptions options,
			ElectionTypeCStruct voteStruct) {
		String code = "VOTE_VAR.LIST_MEMBER--ACC--";

		String accBrackets = "";
		for (SymbolicCBMCVar var : list) {
			accBrackets += "[" + var.getName() + "]";
		}

		code = code.replaceAll("--ACC--", accBrackets);
		code = code.replaceAll("VOTE_VAR", voteVarName);
		code = code.replaceAll("VOTE_VAR", voteVarName);
		code = code.replaceAll("LIST_MEMBER", voteStruct.getListName());

		return code;
	}
}
