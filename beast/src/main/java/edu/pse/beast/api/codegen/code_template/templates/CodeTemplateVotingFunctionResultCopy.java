package edu.pse.beast.api.codegen.code_template.templates;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVotingFunctionResultCopy {
    public static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS)
                    );

    private static final String LINE_BREAK = "\n";

    public static final String TEMPLATE_SINGLE_CANDIDATE =
            "    RESULT_TYPE RESULT_VAR;" + LINE_BREAK
                    + " RESULT_VAR.AMT_MEMBER = NONDET_UINT();" + LINE_BREAK
                    + " ASSUME(RESULT_VAR.AMT_MEMBER == 1);" + LINE_BREAK
                    + " RESULT_VAR.LIST_MEMBER = NONDET_UINT();" + LINE_BREAK
                    + "    ASSUME(RESULT_VAR.LIST_MEMBER == RESULT_ARR);" + LINE_BREAK;

    public static final String TEMPLATE_CANDIDATE_LIST =
            "    RESULT_TYPE RESULT_VAR;" + LINE_BREAK
                    + "    RESULT_VAR.AMT_MEMBER == NONDET_UINT();" + LINE_BREAK
                    + " ASSUME(RESULT_VAR.AMT_MEMBER == CURRENT_AMT_CAND);" + LINE_BREAK
                    + "    for(int i = 0; i < CURRENT_AMT_CAND; ++i) {" + LINE_BREAK
                    + "     RESULT_VAR.LIST_MEMBER[i] = NONDET_UINT();" + LINE_BREAK
                    + "        ASSUME(RESULT_VAR.LIST_MEMBER[i] == RESULT_ARR[i]);" + LINE_BREAK
                    + "    }" + LINE_BREAK
                    + "    for(int i = CURRENT_AMT_CAND; i < MAX_AMT_CANDIDATES; ++i) {"
                    + LINE_BREAK
                    + "     RESULT_VAR.LIST_MEMBER[i] = NONDET_UINT();" + LINE_BREAK
                    + "        ASSUME(RESULT_VAR.LIST_MEMBER[i] == INVALID_RESULT);" + LINE_BREAK
                    + "    }" + LINE_BREAK;

    public static final String TEMPLATE_PARLIAMENT = TEMPLATE_CANDIDATE_LIST;
    public static final List<LoopBound> LOOP_BOUNDS_PARLIAMENT = LOOP_BOUNDS_CANDIDATE_LIST;
}
