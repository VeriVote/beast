package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateElectComparison;
import edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.elect.CodeTemplateElectPermutation;
import edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.vote.CodeTemplateVotePermutation;
import edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.vote.CodeTemplateVoteTuple;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class ElectPermutationHelper {
	public static String generateCode(
			String generatedVarName,
			String varName,
			ElectionTypeCStruct electStruct,
			VotingOutputTypes votingOutputType,
			CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {
		
		Map<String, String> replacementMap = Map.of(
					"ELECT_TYPE", electStruct.getStruct().getName(),
					"AMT_MEMBER", electStruct.getAmtName(),
					"LIST_MEMBER", electStruct.getListName(),
					"GENERATED_VAR_NAME", generatedVarName,
					"ASSUME", options.getCbmcAssumeName(),
					"NONDET_UINT", options.getCbmcNondetUintName(),
					"RHS", varName,
					"PERM", "permutationIndices",
					"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName()
				);
		
		String code = null;		
	
		switch(votingOutputType) {
			case CANDIDATE_LIST : {
				code = CodeTemplateElectPermutation.templateCandidateList;
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
