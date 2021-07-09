package edu.pse.beast.api.codegen.helperfunctions.init_vote;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.StringReplacementMap;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.code_template.templates.CodeTemplateInitVote;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class SymbVarInitVoteHelper extends InitVoteHelper {

	public String generateCode(int voteNumber,
			ElectionTypeCStruct voteArrStruct, VotingInputTypes votingInputType,
			CodeGenOptions options, CodeGenLoopBoundHandler loopBoundHandler,
			CBMCGeneratedCodeInfo codeInfo) {
		String varName = getVoteVarName(voteNumber);
		codeInfo.addVotingVariableName(voteNumber, varName);

		String currentAmtVoterVarName = getCurrentAmtVoter(voteNumber);
		String currentAmtCandVarName = getCurrentAmtCand(voteNumber);
		String currentAmtSeatVarName = getCurrentAmtSeat(voteNumber);

		Map<String, String> replacementMap = StringReplacementMap.genMap(
				"MAX_AMT_CAND", options.getCbmcAmountMaxCandsVarName(),
				"MAX_AMT_VOTER", options.getCbmcAmountMaxVotersVarName(),
				"MAX_AMT_SEAT", options.getCbmcAmountMaxSeatsVarName(),
				"CURRENT_AMT_VOTER", currentAmtVoterVarName, "CURRENT_AMT_CAND",
				currentAmtCandVarName, "CURRENT_AMT_SEAT",
				currentAmtSeatVarName, "VOTE_TYPE",
				voteArrStruct.getStruct().getName(), "AMT_MEMBER",
				voteArrStruct.getAmtName(), "VOTE_NUMBER",
				String.valueOf(voteNumber), "LIST_MEMBER",
				voteArrStruct.getListName(), "VAR_NAME", varName, "ASSUME",
				options.getCbmcAssumeName(), "NONDET_UINT",
				options.getCbmcNondetUintName());

		String code = null;
		List<LoopBound> loopbounds = List.of();

		switch (votingInputType) {
		case APPROVAL: {
			code = CodeTemplateInitVote.templateApproval;
			loopbounds = CodeTemplateInitVote.loopBoundsApproval;
			break;
		}
		case WEIGHTED_APPROVAL: {
			throw new NotImplementedException();
		}
		case PREFERENCE: {
			code = CodeTemplateInitVote.templatePreference;
			loopbounds = CodeTemplateInitVote.loopBoundsPreference;
			break;
		}
		case SINGLE_CHOICE: {
			code = CodeTemplateInitVote.templateSingleChoice;
			loopbounds = CodeTemplateInitVote.loopBoundsSingleChoice;
			break;
		}
		case SINGLE_CHOICE_STACK: {
			throw new NotImplementedException();
		}
		}

		loopBoundHandler.pushMainLoopBounds(loopbounds);
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}

	@Override
	public int getHighestVote() {
		return 0;
	}
}
