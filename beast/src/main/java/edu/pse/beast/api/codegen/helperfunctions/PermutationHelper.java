package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;

public class PermutationHelper {

	public static String generateVotePermutation(String generatedVarName,
			String varName, ElectionTypeCStruct voteArrStruct,
			CodeGenOptions options, LoopBoundHandler loopBoundHandler) {
		String code = null;

		if (voteArrStruct.getVotingType().getListDimensions() == 2) {
			code = CodeTemplates.Permutation.template2d;

			List<String> loopBounds = CodeTemplates.Permutation.template2dloopBounds;
			CodeTemplates.replaceAll(loopBounds, "AMT_VOTES",
					options.getCbmcAmountVotersVarName());
			CodeTemplates.replaceAll(loopBounds, "INNER_BOUND",
					options.getCbmcAmountCandidatesVarName());
			loopBoundHandler.addMainLoopBounds(loopBounds);

			code = code.replaceAll("VOTE_TYPE",
					voteArrStruct.getStruct().getName());
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());
			code = code.replaceAll("GENERATED_VAR_NAME", generatedVarName);
			code = code.replaceAll("INNER_BOUND",
					options.getCbmcAmountCandidatesVarName());
			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());
			code = code.replaceAll("NONDET_UINT",
					options.getCbmcNondetUintName());
			code = code.replaceAll("RHS", varName);
			code = code.replaceAll("PERM", "permutationIndices");
			code = code.replaceAll("AMT_VOTES",
					options.getCbmcAmountVotersVarName());
		}

		return code;
	}

}
