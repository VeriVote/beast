package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;

public class TupleHelper {

	public static String generateCode(String generatedVarName,
			List<String> voteNames, ElectionTypeCStruct voteArrStruct,
			CodeGenOptions options, LoopBoundHandler loopBoundHandler) {
		String code = CodeTemplates.Tuple.varSetupTemplate;
		String voteSum = "";
		for (int i = 0; i < voteNames.size() - 1; ++i) {
			voteSum += "CURRENT_VOTE.AMT_MEMBER + ".replaceAll("CURRENT_VOTE",
					voteNames.get(i));
		}
		voteSum += "CURRENT_VOTE.AMT_MEMBER".replaceAll("CURRENT_VOTE",
				voteNames.get(voteNames.size() - 1));
		code = code.replaceAll("VOTEAMTSUM", voteSum);

		if (voteArrStruct.getVotingType().getListDimensions() == 2) {
			for (String voteVarName : voteNames) {
				code += CodeTemplates.Tuple.template2d;
				code = code.replaceAll("CURRENT_VOTE", voteVarName);

				List<String> loopbounds = CodeTemplates.Tuple.template2dloopBounds;
				CodeTemplates.replaceAll(loopbounds, "AMT_CANDIDATES",
						options.getCbmcAmountCandidatesVarName());
				CodeTemplates.replaceAll(loopbounds, "AMT_VOTES",
						options.getCbmcAmountVotersVarName());
				loopBoundHandler.addMainLoopBounds(loopbounds);
			}
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());
			code = code.replaceAll("NONDET_UINT",
					options.getCbmcNondetUintName());
			code = code.replaceAll("AMT_CANDIDATES",
					options.getCbmcAmountCandidatesVarName());
			code = code.replaceAll("VOTE_TYPE",
					voteArrStruct.getStruct().getName());
			code = code.replaceAll("VAR_NAME", generatedVarName);
			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());

		}

		System.out.println(code);
		return code;
	}
}
