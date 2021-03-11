package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteTuple;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class TupleHelper {

	public static String generateCode(
			String generatedVarName,
			List<String> voteNames, 
			ElectionTypeCStruct voteArrStruct,
			VotingInputTypes votingInputType,
			CodeGenOptions options, 
			LoopBoundHandler loopBoundHandler) {
		
		String voteAmtSum = "";
		for (int i = 0; i < voteNames.size() - 1; ++i) {
			voteAmtSum += "CURRENT_VOTE.AMT_MEMBER + ".replaceAll("CURRENT_VOTE",
					voteNames.get(i));
		}
		voteAmtSum += "CURRENT_VOTE.AMT_MEMBER".replaceAll("CURRENT_VOTE",
				voteNames.get(voteNames.size() - 1));

		Map<String, String> replacementMap = Map.of(
					"VOTE_AMT_SUM", voteAmtSum,
					"AMT_MEMBER", voteArrStruct.getAmtName(),
					"LIST_MEMBER", voteArrStruct.getListName(),
					"NONDET_UINT", options.getCbmcNondetUintName(),
					"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName(),
					"AMT_VOTES", options.getCbmcAmountVotersVarName(),
					"VOTE_TYPE", voteArrStruct.getStruct().getName(),
					"VAR_NAME", generatedVarName,
					"ASSUME", options.getCbmcAssumeName()
				);
		
		String code = null;
		
		switch(votingInputType) {
			case APPROVAL : {					
				break;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {		
				for (String voteVarName : voteNames) {
					code += CodeTemplateVoteTuple.templatePreference.
							replaceAll("CURRENT_VOTE", voteVarName);
				}
				break;
			}
			case SINGLE_CHOICE : {
				break;
			}
			case SINGLE_CHOICE_STACK : {
				break;
			}			
		}				

		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}
}
