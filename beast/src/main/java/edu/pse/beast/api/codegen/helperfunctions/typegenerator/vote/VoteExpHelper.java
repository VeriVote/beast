package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteExp;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

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
