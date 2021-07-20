package edu.pse.beast.api.codegen.code_template.templates;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVotingFunctionResultCopy {
    public static final String TEMPLATE_SINGLE_CANDIDATE =
            "    RESULT_TYPE RESULT_VAR;\n"
                    + " RESULT_VAR.AMT_MEMBER = NONDET_UINT();\n"
                    + " ASSUME(RESULT_VAR.AMT_MEMBER == 1);\n"
                    + " RESULT_VAR.LIST_MEMBER = NONDET_UINT();\n"
                    + "    ASSUME(RESULT_VAR.LIST_MEMBER == RESULT_ARR);\n";

    public static final String TEMPLATE_CANDIDATE_LIST =
            "    RESULT_TYPE RESULT_VAR;\n"
                    + "    RESULT_VAR.AMT_MEMBER == NONDET_UINT();\n"
                    + " ASSUME(RESULT_VAR.AMT_MEMBER == CURRENT_AMT_CAND);\n"
                    + "    for(int i = 0; i < CURRENT_AMT_CAND; ++i) {\n"
                    + "     RESULT_VAR.LIST_MEMBER[i] = NONDET_UINT();\n"
                    + "        ASSUME(RESULT_VAR.LIST_MEMBER[i] == RESULT_ARR[i]);\n"
                    + "    }\n"
                    + "    for(int i = CURRENT_AMT_CAND; i < MAX_AMT_CANDIDATES; ++i) {\n"
                    + "     RESULT_VAR.LIST_MEMBER[i] = NONDET_UINT();\n"
                    + "        ASSUME(RESULT_VAR.LIST_MEMBER[i] == INVALID_RESULT);\n"
                    + "    }\n";

    public static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS)
                    );

    public static final String TEMPLATE_PARLIAMENT = TEMPLATE_CANDIDATE_LIST;
    public static final List<LoopBound> LOOP_BOUNDS_PARLIAMENT = LOOP_BOUNDS_CANDIDATE_LIST;
}
