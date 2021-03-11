package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteEmpty;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class IsVoteEmptyHelper {
	public static String generateCode(
				String generatedVarName,
				String testedVarName,
				ElectionTypeCStruct votingStruct,
				VotingInputTypes votingInputType,
				CodeGenOptions options,
				LoopBoundHandler loopBoundHandler			
			) {
		
		Map<String, String> replacementMap = Map.of(
					"GENERATED_VAR", generatedVarName,
					"TESTED_VAR", testedVarName,
					"AMT_MEMBER", votingStruct.getAmtName()
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
				code = CodeTemplateVoteEmpty.templatePreference;
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
