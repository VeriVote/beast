package edu.pse.beast.api.codegen.code_template.templates.vote;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVotePermutation {
    public static final String TEMPLATE_PREFERENCE =
            "    VOTE_TYPE GENERATED_VAR_NAME;\n"
                    + "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
                    + "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == RHS.AMT_MEMBER);\n"
                    + "    unsigned int PERM[AMT_VOTERS];\n"
                    + "    for (int i = 0; i < RHS.AMT_MEMBER && i < AMT_VOTERS; ++i) {\n"
                    + "        PERM[i] = NONDET_UINT();\n"
                    + "        ASSUME(PERM[i] >= 0);\n"
                    + "        ASSUME(PERM[i] < RHS.AMT_MEMBER);\n"
                    + "    }\n"
                    + "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_VOTERS; ++i) {\n"
                    + "        for (int j = i + 1; j < RHS.AMT_MEMBER && j < AMT_VOTERS; ++j) {\n"
                    + "            ASSUME(PERM[i] != PERM[j]);\n"
                    + "        }\n"
                    + "    }\n"
                    + "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_VOTERS; ++i) {\n"
                    + "        for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
                    + "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[i][j] == "
                    + "RHS.LIST_MEMBER[PERM[i]][j]);\n"
                    + "        }\n"
                    + "    }";

    public static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_CANDS)
            );

    public static final String TEMPLATE_APPROVAL = TEMPLATE_PREFERENCE;
    public static final List<LoopBound> LOOP_BOUNDS_APPROVAL = LOOP_BOUNDS_PREFERENCE;
}
