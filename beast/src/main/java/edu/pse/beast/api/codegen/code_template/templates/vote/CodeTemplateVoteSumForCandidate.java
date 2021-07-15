package edu.pse.beast.api.codegen.code_template.templates.vote;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVoteSumForCandidate {
    public static String templateSingleChoice =
              "    unsigned int GENERATED_VAR = 0;\n"
            + "    for (int i = 0; i < VOTE_VAR.AMT_MEMBER; ++i) {\n"
            + "        if (VOTE_VAR.LIST_MEMBER[i] == CANDIDATE_VAR) {\n"
            + "            GENERATED_VAR++;\n"
            + "        }\n"
            + "    }";

    public final static List<LoopBound> loopBoundsPreference =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_VOTERS)
            );

    public static String templateSingleChoiceStack =
            "unsigned int GENERATED_VAR = VOTE_VAR.LIST_MEMBER[CANDIDATE_VAR];\n";
}
