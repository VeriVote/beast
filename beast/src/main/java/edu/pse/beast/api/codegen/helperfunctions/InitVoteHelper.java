package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;

public abstract class InitVoteHelper {

	public static String generateCode(String varName,
			ElectionTypeCStruct voteArrStruct, CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {
		String code = null;
		if (voteArrStruct.getVotingType().getListDimensions() == 1) {
			code = CodeTemplatesAndLoopBounds.VoteInit.template1d;

			List<String> loopBounds = CodeTemplatesAndLoopBounds.VoteInit.template1dloopBounds;
			CodeTemplatesAndLoopBounds.replaceAll(loopBounds, "AMT_VOTES",
					options.getCbmcAmountVotersVarName());
			loopBoundHandler.addMainLoopBounds(loopBounds);

			code = code.replaceAll("AMT_CANDIDATES",
					options.getCbmcAmountCandidatesVarName());

			code = code.replaceAll("VOTE_TYPE",
					voteArrStruct.getStruct().getName());
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());

			code = code.replaceAll("AMT_VOTES",
					options.getCbmcAmountVotersVarName());

			code = code.replaceAll("VAR_NAME", varName);
			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());
			code = code.replaceAll("NONDET_UINT",
					options.getCbmcNondetUintName());
			code = code.replaceAll("LOWER_VOTE_BOUND",
					options.getVotesLowerBoundVarName());
			code = code.replaceAll("UPPER_VOTE_BOUND",
					options.getVotesUpperBoundVarName());

		} else if (voteArrStruct.getVotingType().getListDimensions() == 2) {
			code = CodeTemplatesAndLoopBounds.VoteInit.template2d;

			if (voteArrStruct.getVotingType().isUniqueVotes()) {
				code += CodeTemplatesAndLoopBounds.VoteInit.uniquenessTemplate;
				code = code.replaceAll("AMT_CANDIDATES",
						options.getCbmcAmountCandidatesVarName());

				List<String> uniqLoopBounds = CodeTemplatesAndLoopBounds.VoteInit.uniquenessTemplateloopBounds;
				CodeTemplatesAndLoopBounds.replaceAll(uniqLoopBounds, "AMT_CANDIDATES",
						options.getCbmcAmountCandidatesVarName());
				CodeTemplatesAndLoopBounds.replaceAll(uniqLoopBounds, "AMT_VOTES",
						options.getCbmcAmountVotersVarName());

				loopBoundHandler.addMainLoopBounds(uniqLoopBounds);
			}

			code = code.replaceAll("VOTE_TYPE",
					voteArrStruct.getStruct().getName());
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());

			code = code.replaceAll("AMT_VOTES",
					options.getCbmcAmountVotersVarName());

			code = code.replaceAll("VAR_NAME", varName);

			code = code.replaceAll("OUTER_BOUND",
					options.getCbmcAmountVotersVarName());
			code = code.replaceAll("INNER_BOUND",
					options.getCbmcAmountCandidatesVarName());

			List<String> loopBounds = CodeTemplatesAndLoopBounds.VoteInit.template2dloopBounds;
			CodeTemplatesAndLoopBounds.replaceAll(loopBounds, "OUTER_BOUND",
					options.getCbmcAmountVotersVarName());
			CodeTemplatesAndLoopBounds.replaceAll(loopBounds, "INNER_BOUND",
					options.getCbmcAmountCandidatesVarName());
			loopBoundHandler.addMainLoopBounds(loopBounds);

			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());
			code = code.replaceAll("NONDET_UINT",
					options.getCbmcNondetUintName());
			code = code.replaceAll("LOWER_VOTE_BOUND",
					options.getVotesLowerBoundVarName());
			code = code.replaceAll("UPPER_VOTE_BOUND",
					options.getVotesUpperBoundVarName());
		}

		return code;
	}
}
