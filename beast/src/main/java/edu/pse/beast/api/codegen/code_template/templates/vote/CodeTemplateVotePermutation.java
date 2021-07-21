package edu.pse.beast.api.codegen.code_template.templates.vote;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVotePermutation {
    public static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_CANDS)
            );

    private static final String LINE_BREAK = "\n";

    public static final String TEMPLATE_PREFERENCE =
            "    VOTE_TYPE GENERATED_VAR_NAME;" + LINE_BREAK
            + "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == RHS.AMT_MEMBER);" + LINE_BREAK
            + "    unsigned int PERM[AMT_VOTERS];" + LINE_BREAK
            + "    for (int i = 0; i < RHS.AMT_MEMBER && i < AMT_VOTERS; ++i) {" + LINE_BREAK
            + "        PERM[i] = NONDET_UINT();" + LINE_BREAK
            + "        ASSUME(PERM[i] >= 0);" + LINE_BREAK
            + "        ASSUME(PERM[i] < RHS.AMT_MEMBER);" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_VOTERS; ++i) {" + LINE_BREAK
            + "        for (int j = i + 1; j < RHS.AMT_MEMBER && j < AMT_VOTERS; ++j) {"
            + LINE_BREAK
            + "            ASSUME(PERM[i] != PERM[j]);" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_VOTERS; ++i) {" + LINE_BREAK
            + "        for (int j = 0; j < AMT_CANDIDATES; ++j) {" + LINE_BREAK
            + "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[i][j] == "
            + "RHS.LIST_MEMBER[PERM[i]][j]);" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }";

    public static final String TEMPLATE_APPROVAL = TEMPLATE_PREFERENCE;
    public static final List<LoopBound> LOOP_BOUNDS_APPROVAL = LOOP_BOUNDS_PREFERENCE;
}
