package edu.pse.beast.api.codegen.code_template.templates.elect;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateElectIntersection {
    public static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_CANDS)
            );

    private static final String LINE_BREAK = "\n";

    public static final String TEMPLATE_CANDIDATE_LIST =
            "    ELECT_TYPE GENERATED_VAR_NAME;" + LINE_BREAK
            + "    unsigned int count = 0;" + LINE_BREAK
            + "    for (int i = 0; i < LHS.AMT_MEMBER; ++i) {" + LINE_BREAK
            + "        unsigned int contains = 0;" + LINE_BREAK
            + "        for(int j = 0; j < RHS.AMT_MEMBER; ++j) {" + LINE_BREAK
            + "            contains |= LHS.LIST_MEMBER[i] == RHS.LIST_MEMBER[j];" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "        if(contains) {" + LINE_BREAK
            + "            GENERATED_VAR_NAME.LIST_MEMBER[count] = NONDET_UINT();" + LINE_BREAK
            + "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[count] == LHS.LIST_MEMBER[i]);"
            + LINE_BREAK
            + "            count++;" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == count);" + LINE_BREAK
            + "    for (int i = count; i < MAX_AMT_CANDIDATES; ++i) {" + LINE_BREAK
            + "        GENERATED_VAR_NAME.LIST_MEMBER[i] = NONDET_UINT();" + LINE_BREAK
            + "        ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[i] == INVALID_VOTE);" + LINE_BREAK
            + "    }" + LINE_BREAK;

    public static final String TEMPLATE_PARLIAMENT = TEMPLATE_CANDIDATE_LIST;
    public static final List<LoopBound> LOOP_BOUNDS_PARLIAMENT = LOOP_BOUNDS_CANDIDATE_LIST;
}
