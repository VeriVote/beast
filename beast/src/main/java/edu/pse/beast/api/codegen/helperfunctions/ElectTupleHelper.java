package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateElectComparison;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateElectTuple;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteTuple;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class ElectTupleHelper {
	public static String generateCode(
			String generatedVarName,
			List<String> electNames, 
			ElectionTypeCStruct electStruct,
			VotingOutputTypes votingOutputType,
			CodeGenOptions options, 
			LoopBoundHandler loopBoundHandler
			) {				
		
		String electAmtSum = "";
		for (int i = 0; i < electNames.size() - 1; ++i) {
			electAmtSum += "CURRENT_ELECT.AMT_MEMBER + ".replaceAll("CURRENT_ELECT",
					electNames.get(i));
		}
		electAmtSum += "CURRENT_ELECT.AMT_MEMBER".replaceAll("CURRENT_ELECT",
				electNames.get(electNames.size() - 1));
		
		Map<String, String> replacementMap = Map.of(
					"ELECT_AMT_SUM", electAmtSum,
					"AMT_MEMBER", electStruct.getAmtName(),
					"LIST_MEMBER", electStruct.getListName(),
					"NONDET_UINT", options.getCbmcNondetUintName(),
					"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName(),
					"VOTE_TYPE", electStruct.getStruct().getName(),
					"VAR_NAME", generatedVarName,
					"ASSUME", options.getCbmcAssumeName()
				);
		
		String code = CodeTemplateElectTuple.templateVarSetup;
		
		switch(votingOutputType) {
			case CANDIDATE_LIST : {
				for (String electVarName : electNames) {
					code += CodeTemplateElectTuple.templateCandidateList.
							replaceAll("CURRENT_ELECT", electVarName);
				}
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
