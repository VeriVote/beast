package edu.pse.beast.api.codegen.code_template.templates;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateInitVote {
    public static final List<LoopBound> LOOP_BOUNDS_SINGLE_CHOICE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(
                            LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                            LoopBoundType.LOOP_BOUND_AMT_VOTERS)
                    );

    public static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                Arrays
                .asList(LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS,
                        LoopBoundType.LOOP_BOUND_AMT_CANDS,
                        LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                        LoopBoundType.LOOP_BOUND_AMT_CANDS,
                        LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS,
                        LoopBoundType.LOOP_BOUND_AMT_CANDS)
            );

    public static final List<LoopBound> LOOP_BOUNDS_APPROVAL =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_CANDS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_CANDS)
            );

    private static final String LINE_BREAK = "\n";

    public static final String TEMPLATE_SINGLE_CHOICE =
            "    //initializing Vote VOTE_NUMBER" + LINE_BREAK
            + "    unsigned int CURRENT_AMT_VOTER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(CURRENT_AMT_VOTER <= MAX_AMT_VOTER);" + LINE_BREAK
            + "    unsigned int CURRENT_AMT_SEAT = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(CURRENT_AMT_SEAT <= MAX_AMT_SEAT);" + LINE_BREAK
            + "    unsigned int CURRENT_AMT_CAND = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(CURRENT_AMT_CAND <= MAX_AMT_CAND);" + LINE_BREAK
            + "    VOTE_TYPE VAR_NAME;" + LINE_BREAK
            + "    VAR_NAME.AMT_MEMBER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(VAR_NAME.AMT_MEMBER == CURRENT_AMT_VOTER);" + LINE_BREAK
            + "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {" + LINE_BREAK
            + "            VAR_NAME.LIST_MEMBER[i] = NONDET_UINT();" + LINE_BREAK
            + "            ASSUME(VAR_NAME.votes[i] >= 0);" + LINE_BREAK
            + "            ASSUME(VAR_NAME.votes[i] < CURRENT_AMT_CAND);" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {" + LINE_BREAK
            + "            VAR_NAME.LIST_MEMBER[i] = NONDET_UINT();" + LINE_BREAK
            + "            ASSUME(VAR_NAME.votes[i] == INVALID_VOTE);" + LINE_BREAK
            + "    }" + LINE_BREAK;

    public static final String TEMPLATE_PREFERENCE =
            "    //initializing Vote VOTE_NUMBER" + LINE_BREAK
            + "    unsigned int CURRENT_AMT_VOTER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(CURRENT_AMT_VOTER <= MAX_AMT_VOTER);" + LINE_BREAK
            + "    unsigned int CURRENT_AMT_SEAT = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(CURRENT_AMT_SEAT <= MAX_AMT_SEAT);" + LINE_BREAK
            + "    unsigned int CURRENT_AMT_CAND = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(CURRENT_AMT_CAND <= MAX_AMT_CAND);" + LINE_BREAK
            + "    VOTE_TYPE VAR_NAME;" + LINE_BREAK
            + "    VAR_NAME.AMT_MEMBER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(VAR_NAME.AMT_MEMBER == CURRENT_AMT_VOTER);" + LINE_BREAK
            + "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {" + LINE_BREAK
            + "        for (int j = 0; j < CURRENT_AMT_CAND; ++j) {" + LINE_BREAK
            + "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();" + LINE_BREAK
            + "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] >= 0);" + LINE_BREAK
            + "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] <= CURRENT_AMT_CAND);" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    for (int i = CURRENT_AMT_VOTER; i < MAX_AMT_VOTER; ++i) {" + LINE_BREAK
            + "        for (int j = 0; j < MAX_AMT_CAND; ++j) {" + LINE_BREAK
            + "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();" + LINE_BREAK
            + "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] == INVALID_VOTE);" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {" + LINE_BREAK
            + "        unsigned int tmp[MAX_AMT_CAND];" + LINE_BREAK
            + "        for (int k = 0; k < CURRENT_AMT_CAND; ++k) {" + LINE_BREAK
            + "            tmp[k] = 0;" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "        for (int j = 0; j < CURRENT_AMT_CAND; ++j) {" + LINE_BREAK
            + "            for (int k = 0; k < CURRENT_AMT_CAND; ++k) {" + LINE_BREAK
            + "                if (VAR_NAME.LIST_MEMBER[i][j] == k) {" + LINE_BREAK
            + "                    ASSUME(tmp[k] == 0);" + LINE_BREAK
            + "                    tmp[k] = 1;" + LINE_BREAK
            + "                }" + LINE_BREAK
            + "            }" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK;

    public static final String TEMPLATE_APPROVAL =
            "    // initializing Vote VOTE_NUMBER" + LINE_BREAK
            + "    unsigned int CURRENT_AMT_VOTER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(CURRENT_AMT_VOTER <= MAX_AMT_VOTER);" + LINE_BREAK
            + "    unsigned int CURRENT_AMT_SEAT = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(CURRENT_AMT_SEAT <= MAX_AMT_SEAT);" + LINE_BREAK
            + "    unsigned int CURRENT_AMT_CAND = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(CURRENT_AMT_CAND <= MAX_AMT_CAND);" + LINE_BREAK
            + "    VOTE_TYPE VAR_NAME;" + LINE_BREAK
            + "    VAR_NAME.AMT_MEMBER = NONDET_UINT();" + LINE_BREAK
            + "    ASSUME(VAR_NAME.AMT_MEMBER == CURRENT_AMT_VOTER);" + LINE_BREAK
            + "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {" + LINE_BREAK
            + "        for (int j = 0; j < CURRENT_AMT_CAND; ++j) {" + LINE_BREAK
            + "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();" + LINE_BREAK
            + "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] >= 0);" + LINE_BREAK
            + "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] <= 1);" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    for (int i = CURRENT_AMT_VOTER; i < MAX_AMT_VOTER; ++i) {" + LINE_BREAK
            + "        for (int j = 0; j < MAX_AMT_CAND; ++j) {" + LINE_BREAK
            + "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();" + LINE_BREAK
            + "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] == INVALID_VOTE);" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + LINE_BREAK;

}
