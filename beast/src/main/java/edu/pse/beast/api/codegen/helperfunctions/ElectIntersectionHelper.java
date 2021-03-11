package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateElectIntersection;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateElectPermutation;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteIntersection;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVotePermutation;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class ElectIntersectionHelper {

	public static String generateCode(
			String generatedVarName,
			List<String> intersectedElectNames, 
			ElectionTypeCStruct electStruct,
			VotingOutputTypes votingOutputType,
			CodeGenOptions options, 
			LoopBoundHandler loopBoundHandler) {
		
		Map<String, String> replacementMap = Map.of(
				"ELECT_TYPE", electStruct.getStruct().getName(),
				"AMT_MEMBER", electStruct.getAmtName(),
				"LIST_MEMBER", electStruct.getListName(),
				"GENERATED_VAR_NAME", generatedVarName,
				"LHS_VAR_NAME", intersectedElectNames.get(0),
				"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName(),
				"ASSUME", options.getCbmcAssumeName(),
				"NONDET_UINT", options.getCbmcNondetUintName()
				);
		
		String code = null;
		
		String comparison = "";
		
		if(electStruct.getVotingType().getListDimensions() == 1) {
			int i = 0;
			for (; i < intersectedElectNames.size() - 2; ++i) {
				comparison += intersectedElectNames.get(i) + ".LIST_MEMBER[i]] == "
						+ intersectedElectNames.get(i + 1) + ".LIST_MEMBER[i] && ";
			}
			comparison += intersectedElectNames.get(i) + ".LIST_MEMBER[i] == "
					+ intersectedElectNames.get(i + 1) + ".LIST_MEMBER[i]";
		}		
		
		switch(votingOutputType) {
			case CANDIDATE_LIST : {
				code = CodeTemplateElectIntersection.templateCandidateList;
				break;
			}
			case PARLIAMENT : {
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
