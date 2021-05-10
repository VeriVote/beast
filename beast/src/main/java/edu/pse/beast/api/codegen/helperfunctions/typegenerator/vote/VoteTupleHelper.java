package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.vote.CodeTemplateVoteTuple;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class VoteTupleHelper {
	
	public static String generateCode(
			String generatedVarName,
			List<String> voteNames, 
			ElectionTypeCStruct voteArrStruct,
			VotingInputTypes votingInputType,
			CodeGenOptions options, 
			LoopBoundHandler loopBoundHandler
			) {
		
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
					"AMT_VOTERS", options.getCbmcAmountVotersVarName(),
					"VOTE_TYPE", voteArrStruct.getStruct().getName(),
					"VAR_NAME", generatedVarName,
					"ASSUME", options.getCbmcAssumeName()
				);
		
		String code = CodeTemplateVoteTuple.templateVarSetup;
		List<LoopBoundType> loopbounds = new ArrayList<>();
		
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
					loopbounds = CodeTemplateVoteTuple.loopBoundsPreference;
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

		loopBoundHandler.pushMainLoopBounds(loopbounds);
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}
}
