package edu.pse.beast.api.codegen.code_template.templates.elect;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateElectPermutation {
	public final static String templateCandidateList = 
			  "    ELECT_TYPE GENERATED_VAR_NAME;\n"
			+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == RHS.AMT_MEMBER);\n"
			+ "    unsigned int PERM[AMT_VOTES];\n"
			+ "    for (int i = 0; i < RHS.AMT_MEMBER && i < AMT_CANDIDATES; ++i) {\n"
			+ "        PERM[i] = NONDET_UINT();\n"
			+ "        ASSUME(PERM[i] >= 0);\n"
			+ "        ASSUME(PERM[i] < RHS.AMT_MEMBER);\n"
			+ "    }\n"
			+ "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_CANDIDATES; ++i) {\n"
			+ "        for (int j = i + 1; j < RHS.AMT_MEMBER && j < AMT_CANDIDATES; ++j) {\n"
			+ "            ASSUME(PERM[i] != PERM[j]);\n"
			+ "        }\n"
			+ "    }\n"
			+ "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_CANDIDATES; ++i) {\n"
			+ "        ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[i] == RHS.LIST_MEMBER[PERM[i]]);\n"
			+ "    }";
	
	public final static List<LoopBound> loopBoundsCandidateList =
			LoopBound.codeGenLoopboundList(
				Arrays.asList(
				LoopBoundType.LOOP_BOUND_AMT_CANDS,
				LoopBoundType.LOOP_BOUND_AMT_CANDS, 
				LoopBoundType.LOOP_BOUND_AMT_CANDS,
				LoopBoundType.LOOP_BOUND_AMT_CANDS)
			);
}
