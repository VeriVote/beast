package edu.pse.beast.api.codegen.code_template.templates.vote;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVoteIntersection {
    public static final List<LoopBound> LOOP_BOUNDS_SINGLE_CHOICE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_VOTERS)
            );

    public static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS)
            );

    private static final String LINE_BREAK = "\n";

    public static final String TEMPLATE_SINGLE_CHOICE =
            "VOTE_TYPE GENERATED_VAR_NAME;" + LINE_BREAK
            + "{" + LINE_BREAK
            + "    unsigned int count = 0;" + LINE_BREAK
            + "    for (int i = 0; i < AMT_VOTERS; ++i) {" + LINE_BREAK
            + "        unsigned int eq = COMPARE_VARS;" + LINE_BREAK
            + "        if (eq) {" + LINE_BREAK
            + "            GENERATED_VAR_NAME.LIST_MEMBER[count] = NONDET_UINT();" + LINE_BREAK
            + "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[count] == "
            + "LHS_VAR_NAME.LIST_MEMBER[i]);" + LINE_BREAK
            + "            count++;" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == count);" + LINE_BREAK
            + "}" + LINE_BREAK;

    public static final String TEMPLATE_PREFERENCE =
            "VOTE_TYPE GENERATED_VAR_NAME;" + LINE_BREAK
            + "{" + LINE_BREAK
            + "    unsigned int count = 0;" + LINE_BREAK
            + "    for (int i = 0; i < LHS_VAR_NAME.AMT_MEMBER; ++i) {" + LINE_BREAK
            + "        unsigned int eq = true;" + LINE_BREAK
            + "        for (int j = 0; j < AMT_CANDIDATES; ++j) {" + LINE_BREAK
            + "            eq = eq && COMPARE_VARS;" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "        if (eq) {" + LINE_BREAK
            + "            for (int j = 0; j < AMT_CANDIDATES; ++j) {" + LINE_BREAK
            + "                GENERATED_VAR_NAME.LIST_MEMBER[count][j] = NONDET_UINT();"
            + LINE_BREAK
            + "                ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[count][j] == "
            + "LHS_VAR_NAME.LIST_MEMBER[i][j]);" + LINE_BREAK
            + "            }" + LINE_BREAK
            + "        count++;" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == count);"
            + "}" + LINE_BREAK;

    public static final String TEMPLATE_APPROVAL = TEMPLATE_PREFERENCE;
    public static final List<LoopBound> LOOP_BOUNDS_APPROVAL = LOOP_BOUNDS_PREFERENCE;
}
