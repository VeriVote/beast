package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;

public class VotesumHelper {

	public static String generateCode(String generatedVarName,
			int voteNumber, String symbolicVarCand, ElectionTypeCStruct voteStruct,
			CodeGenOptions options, LoopBoundHandler loopBoundHandler) {
		String code = CodeTemplatesAndLoopBounds.VoteSumForCandidate.templateSingleChoice;
		
		code = code.replaceAll("GENERATED_VAR", generatedVarName);
		code = code.replaceAll("AMT_MEMBER", voteStruct.getAmtName());
		code = code.replaceAll("LIST_MEMBER", voteStruct.getListName());
		code = code.replaceAll("CANDIDATE_VAR", symbolicVarCand);
		code = code.replaceAll("VOTE_VAR", "voteNUMBER".replace("NUMBER", String.valueOf(voteNumber)));
		
		return code;
	}
}
