package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteEmpty;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class IsVoteEmptyHelper {
	public static String generateCode(
				String generatedVarName,
				String testedVarName,
				ElectionTypeCStruct votingStruct,
				VotingInputTypes votingInputType,
				CodeGenOptions options,
				CodeGenLoopBoundHandler loopBoundHandler			
			) {
		
		Map<String, String> replacementMap = Map.of(
					"GENERATED_VAR", generatedVarName,
					"TESTED_VAR", testedVarName,
					"AMT_MEMBER", votingStruct.getAmtName()
				);
		
		String code = null;		
		switch(votingInputType) {
			case APPROVAL : {			
				throw new NotImplementedException();
			}
			case WEIGHTED_APPROVAL : {
				throw new NotImplementedException();
			}
			case PREFERENCE : {			
				code = CodeTemplateVoteEmpty.templatePreference;
				break;
			}
			case SINGLE_CHOICE : {
				throw new NotImplementedException();
			}
			case SINGLE_CHOICE_STACK : {
				throw new NotImplementedException();
			}
		}
		
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}
}
