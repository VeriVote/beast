package edu.pse.beast.api.codegen.code_template.templates.vote;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVoteSumForCandidate {
    public static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_VOTERS)
                    );

    private static final String TEMPLATE_SINGLE_CHOICE =
            "    unsigned int GENERATED_VAR = 0;\n"
                    + "    for (int i = 0; i < VOTE_VAR.AMT_MEMBER; ++i) {\n"
                    + "        if (VOTE_VAR.LIST_MEMBER[i] == CANDIDATE_VAR) {\n"
                    + "            GENERATED_VAR++;\n"
                    + "        }\n"
                    + "    }";

    private static final String TEMPLATE_SINGLE_CHOICE_STACK =
            "unsigned int GENERATED_VAR = VOTE_VAR.LIST_MEMBER[CANDIDATE_VAR];\n";

    public static String getTemplateSingleChoice() {
        return TEMPLATE_SINGLE_CHOICE;
    }

    public static String getTemplateSingleChoiceStack() {
        return TEMPLATE_SINGLE_CHOICE_STACK;
    }
}
