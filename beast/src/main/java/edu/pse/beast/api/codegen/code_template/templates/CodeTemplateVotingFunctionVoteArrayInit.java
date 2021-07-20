package edu.pse.beast.api.codegen.code_template.templates;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVotingFunctionVoteArrayInit {
    public static final String TEMPLATE_SINGLE_CHOICE =
              " unsigned int VOTE_ARR[AMT_VOTERS];\n"
            + "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {\n"
            + "     VOTE_ARR[i] = VOTE_INPUT_STRUCT_VAR.LIST_MEMBER[i];\n"
            + " }";

    public static final List<LoopBound> LOOP_BOUNDS_SINGLE_CHOICE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(
                            LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS)
            );

    public static final String TEMPLATE_PREFERENCE =
              " unsigned int VOTE_ARR[AMT_VOTERS][AMT_CANDIDATES];\n"
            + "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {\n"
            + "     for (int j = 0; j < CURRENT_AMT_CAND; ++j) {\n"
            + "         VOTE_ARR[i][j] = VOTE_INPUT_STRUCT_VAR.LIST_MEMBER[i][j];\n"
            + "     }\n"
            + " }";

    public static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(
                            LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS,
                            LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS)
            );

    public static final String TEMPLATW_APPROVAL = TEMPLATE_PREFERENCE;
    public static final List<LoopBound> LOOP_BOUNDS_APPROVAL = LOOP_BOUNDS_PREFERENCE;
}
