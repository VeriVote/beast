package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;

public class IntersectionHelper {

	public static String generateVoteIntersection(String generatedVarName,
			List<String> varNames, ElectionTypeCStruct voteArrStruct,
			CodeGenOptions options, LoopBoundHandler loopBoundHandler) {
		String code = null;

		if (voteArrStruct.getVotingType().getListDimensions() == 2) {

			// loopbounds
			List<String> loopBounds = CodeTemplates.Intersection.template2dloopBounds;
			CodeTemplates.replaceAll(loopBounds, "OUTER_BOUND",
					options.getCbmcAmountVotersVarName());
			CodeTemplates.replaceAll(loopBounds, "INNER_BOUND",
					options.getCbmcAmountCandidatesVarName());
			loopBoundHandler.addMainLoopBounds(loopBounds);

			// code
			code = CodeTemplates.Intersection.template2d;

			String comparison = "";
			int i = 0;
			for (; i < varNames.size() - 2; ++i) {
				comparison += varNames.get(i) + ".LIST_MEMBER[i][j] == "
						+ varNames.get(i + 1) + ".LIST_MEMBER[i][j] && ";
			}
			comparison += varNames.get(i) + ".LIST_MEMBER[i][j] == "
					+ varNames.get(i + 1) + ".LIST_MEMBER[i][j]";

			code = code.replaceAll("COMPARE_VARS", comparison);

			code = code.replaceAll("VOTE_TYPE",
					voteArrStruct.getStruct().getName());
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());

			code = code.replaceAll("GENERATED_VAR_NAME", generatedVarName);

			code = code.replaceAll("LHS_VAR_NAME", varNames.get(0));

			code = code.replaceAll("OUTER_BOUND",
					options.getCbmcAmountVotersVarName());
			code = code.replaceAll("INNER_BOUND",
					options.getCbmcAmountCandidatesVarName());

			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());
			code = code.replaceAll("NONDET_UINT",
					options.getCbmcNondetUintName());
		}

		return code;
	}

}
