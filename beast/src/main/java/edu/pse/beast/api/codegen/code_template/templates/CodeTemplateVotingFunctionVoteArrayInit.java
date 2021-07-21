package edu.pse.beast.api.codegen.code_template.templates;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVotingFunctionVoteArrayInit {
    public static final List<LoopBound> LOOP_BOUNDS_SINGLE_CHOICE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(
                            LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS)
            );

    public static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(
                            LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS,
                            LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS)
            );

    private static final String LINE_BREAK = "\n";

    public static final String TEMPLATE_SINGLE_CHOICE =
              " unsigned int VOTE_ARR[AMT_VOTERS];" + LINE_BREAK
            + "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {" + LINE_BREAK
            + "     VOTE_ARR[i] = VOTE_INPUT_STRUCT_VAR.LIST_MEMBER[i];" + LINE_BREAK
            + " }";

    public static final String TEMPLATE_PREFERENCE =
              " unsigned int VOTE_ARR[AMT_VOTERS][AMT_CANDIDATES];" + LINE_BREAK
            + "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {" + LINE_BREAK
            + "     for (int j = 0; j < CURRENT_AMT_CAND; ++j) {" + LINE_BREAK
            + "         VOTE_ARR[i][j] = VOTE_INPUT_STRUCT_VAR.LIST_MEMBER[i][j];" + LINE_BREAK
            + "     }" + LINE_BREAK
            + " }";

    public static final String TEMPLATE_APPROVAL = TEMPLATE_PREFERENCE;
    public static final List<LoopBound> LOOP_BOUNDS_APPROVAL = LOOP_BOUNDS_PREFERENCE;
}
