package edu.pse.beast.api.codegen.code_template.templates.elect;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateElectPermutation {
    public static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                LoopBoundType.LOOP_BOUND_AMT_CANDS,
                LoopBoundType.LOOP_BOUND_AMT_CANDS,
                LoopBoundType.LOOP_BOUND_AMT_CANDS,
                LoopBoundType.LOOP_BOUND_AMT_CANDS)
            );

    private static final String LINE_BREAK = "\n";

    public static final String TEMPLATE_CANDIDATE_LIST =
            "    ELECT_TYPE GENERATED_VAR_NAME;" + LINE_BREAK
            + "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == RHS.AMT_MEMBER);" + LINE_BREAK
            + "    unsigned int PERM[AMT_VOTES];" + LINE_BREAK
            + "    for (int i = 0; i < RHS.AMT_MEMBER && i < AMT_CANDIDATES; ++i) {" + LINE_BREAK
            + "        PERM[i] = NONDET_UINT();" + LINE_BREAK
            + "        ASSUME(PERM[i] >= 0);" + LINE_BREAK
            + "        ASSUME(PERM[i] < RHS.AMT_MEMBER);" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_CANDIDATES; ++i) {"
            + LINE_BREAK
            + "        for (int j = i + 1; j < RHS.AMT_MEMBER && j < AMT_CANDIDATES; ++j) {"
            + LINE_BREAK
            + "            ASSUME(PERM[i] != PERM[j]);" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_CANDIDATES; ++i) {"
            + LINE_BREAK
            + "        ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[i] == RHS.LIST_MEMBER[PERM[i]]);"
            + LINE_BREAK
            + "    }";
}
