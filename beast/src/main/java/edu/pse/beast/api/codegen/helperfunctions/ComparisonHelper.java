package edu.pse.beast.api.codegen.helperfunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.CodeTemplateComparison;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.CBMCVars;
import edu.pse.beast.api.electiondescription.CElectionVotingType;

public class ComparisonHelper {	
	
	public static String generateCode(
			String generatedVar,
			String comparison, 
			String lhsVarName, 
			String rhsVarName,
			CElectionVotingType type, 
			CodeGenOptions options, 
			String assumeOrAssert,
			CodeGenLoopBoundHandler loopBoundHandler) {
		
		Map<String, String> replacementMap = null;
		
		String code = null;
		if(type.getListDimensions() == 0) {
			replacementMap = Map.of(
					"ASSUME_OR_ASSERT", assumeOrAssert,
					"LHS_VAR_NAME", lhsVarName,
					"RHS_VAR_NAME", rhsVarName,
					"COMP", comparison,
					"GENERATED_VAR", generatedVar
					);
			
			code = CodeTemplateComparison.template0d;
		} else if (type.getListDimensions() == 1) {
			CBMCVars listSize = type.getListSizes().get(0);
			String amtString = null;
			
			List<LoopBound> loopbounds = new ArrayList<>();
			
			switch (listSize) {
				case AMT_CANDIDATES:
					loopbounds.add(LoopBound.codeGenLoopbound(LoopBoundType.LOOP_BOUND_AMT_CANDS));
					break;
				case AMT_VOTERS:
					loopbounds.add(LoopBound.codeGenLoopbound(LoopBoundType.LOOP_BOUND_AMT_VOTERS));
					break;
			}			
			
			loopBoundHandler.pushMainLoopBounds(loopbounds);
			
			replacementMap = Map.of(
					"AMT", amtString,
					"ASSUME_OR_ASSERT", assumeOrAssert,
					"LHS_VAR_NAME", lhsVarName,
					"RHS_VAR_NAME", rhsVarName,
					"COMP", comparison,			
					"GENERATED_VAR", generatedVar		
					);	
			if(comparison.equals("!=")) {
				code = CodeTemplateComparison.template1dUneq;
			} else {
				code = CodeTemplateComparison.template1d;
			}

		}
		
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}

}
