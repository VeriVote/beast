package edu.pse.beast.api.codegen.code_template.templates.elect;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateElectIntersection {
	public final static String templateCandidateList = 
			  "ELECT_TYPE GENERATED_VAR_NAME;\n"
			+ "{\n"
			+ "    unsigned int count = 0;\n"
			+ "    for (int i = 0; i < AMT_CANDIDATES; ++i) {\n"
			+ "        unsigned int eq = COMPARE_VARS;\n"
			+ "        if (eq) {\n"
			+ "            GENERATED_VAR_NAME.LIST_MEMBER[count] = NONDET_UINT();\n"
			+ "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[count] == LHS_VAR_NAME.LIST_MEMBER[i]);\n"
			+ "            count++;\n" 
			+ "        }\n"
			+ "    }\n"
			+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == count);\n"
			+ "}\n";
	
	
	public final static List<LoopBound> loopboundsCandidateList = 
			LoopBound.codeGenLoopboundList(
					Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_CANDS)
			);	
	
	public final static String templateParliament = templateCandidateList; 
	public final static List<LoopBound> loopboundsParliament = loopboundsCandidateList;
}
