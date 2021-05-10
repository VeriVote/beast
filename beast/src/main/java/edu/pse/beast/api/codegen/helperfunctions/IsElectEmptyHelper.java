package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.elect.CodeTemplateElectEmpty;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class IsElectEmptyHelper {
	public static String generateCode(
				String generatedVarName,
				String testedVarName,
				ElectionTypeCStruct electStruct,
				VotingOutputTypes votingOutputType,
				CodeGenOptions options,
				LoopBoundHandler loopBoundHandler			
			) {
		
		Map<String, String> replacementMap = Map.of(
					"GENERATED_VAR", generatedVarName,
					"TESTED_VAR", testedVarName,
					"AMT_MEMBER", electStruct.getAmtName()
				);
		
		String code = null;		
		switch(votingOutputType) {
			case CANDIDATE_LIST : {
				code = CodeTemplateElectEmpty.templateCandidateList;
				break;
			}
			case PARLIAMENT : {
				code = CodeTemplateElectEmpty.templateParliament;
				break;
			}
			case PARLIAMENT_STACK : {
				break;
			}
			case SINGLE_CANDIDATE : {
				break;
			}
		}
		
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}
}
