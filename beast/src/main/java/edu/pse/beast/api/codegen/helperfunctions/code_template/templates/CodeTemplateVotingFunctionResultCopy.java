package edu.pse.beast.api.codegen.helperfunctions.code_template.templates;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVotingFunctionResultCopy {
		public final static String templateSingleCandidate = 
				  "	RESULT_TYPE RESULT_VAR;\n"
				+ " RESULT_VAR.AMT_MEMBER = NONDET_UINT();\n"
				+ " ASSUME(RESULT_VAR.AMT_MEMBER == 1);\n"
				+ " RESULT_VAR.LIST_MEMBER = NONDET_UINT();\n"
				+ "	ASSUME(RESULT_VAR.LIST_MEMBER == RESULT_ARR);\n";
		
		
		public final static String templateCandidateList= 
				  "	RESULT_TYPE RESULT_VAR;\n"
				+ "	RESULT_VAR.AMT_MEMBER == NONDET_UINT();\n"
				+ " ASSUME(RESULT_VAR.AMT_MEMBER == CURRENT_AMT_CAND);\n"
				+ "	for(int i = 0; i < CURRENT_AMT_CAND; ++i) {\n"
				+ " 	RESULT_VAR.LIST_MEMBER[i] = NONDET_UINT();\n"
				+ "		ASSUME(RESULT_VAR.LIST_MEMBER[i] == RESULT_ARR[i]);\n"
				+ "	}\n"
				+ "	for(int i = CURRENT_AMT_CAND; i < MAX_AMT_CANDIDATES; ++i) {\n"
				+ " 	RESULT_VAR.LIST_MEMBER[i] = NONDET_UINT();\n"
				+ "		ASSUME(RESULT_VAR.LIST_MEMBER[i] == INVALID_RESULT);\n"
				+ "	}\n";
		
		public final static List<LoopBound> loopBoundsCandidateList = 
				LoopBound.codeGenLoopboundList(
						Arrays.asList(LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS)
				);
		
		public final static String templateParliament = templateCandidateList;
		public final static List<LoopBound> loopBoundsParliament = loopBoundsCandidateList;
}
