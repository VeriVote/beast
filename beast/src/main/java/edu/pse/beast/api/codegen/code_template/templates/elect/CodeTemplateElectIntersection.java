package edu.pse.beast.api.codegen.code_template.templates.elect;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateElectIntersection {
	public final static String templateCandidateList = 
			  "    ELECT_TYPE GENERATED_VAR_NAME;\n"
			+ "    unsigned int count = 0;\n"
			+ "    for (int i = 0; i < LHS.AMT_MEMBER; ++i) {\n"
			+ "        unsigned int contains = 0;\n"
			+ "        for(int j = 0; j < RHS.AMT_MEMBER; ++j) {\n"
			+ "            contains |= LHS.LIST_MEMBER[i] == RHS.LIST_MEMBER[j];\n"
			+ "        }\n"
			+ "        if(contains) {\n"
			+ "            GENERATED_VAR_NAME.LIST_MEMBER[count] = NONDET_UINT();\n"
			+ "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[count] == LHS.LIST_MEMBER[i]);\n"
			+ "            count++;\n"
			+ "        }\n"
			+ "    }\n"
			+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == count);\n"
			+ "    for (int i = count; i < MAX_AMT_CANDIDATES; ++i) {\n"
			+ "        GENERATED_VAR_NAME.LIST_MEMBER[i] = NONDET_UINT();\n"
			+ "        ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[i] == INVALID_VOTE);\n"
			+ "    }\n";		
			

	
	public final static List<LoopBound> loopboundsCandidateList = 
			LoopBound.codeGenLoopboundList(
					Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_CANDS)
			);	
	
	public final static String templateParliament = templateCandidateList; 
	public final static List<LoopBound> loopboundsParliament = loopboundsCandidateList;
}
