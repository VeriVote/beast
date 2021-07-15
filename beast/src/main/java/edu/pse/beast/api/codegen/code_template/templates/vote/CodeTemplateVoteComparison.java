package edu.pse.beast.api.codegen.code_template.templates.vote;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVoteComparison {

        public static String templateSingleChoiceEq =
                  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER == RHS_VAR.AMT_MEMBER;\n"
                + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
                + "    GENERATED_VAR &= LHS_VAR.LIST_MEMBER[i] COMP RHS_VAR.LIST_MEMBER[i];\n"
                + "}\n";

        public static String templateSingleChoiceUnEq =
                  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER != RHS_VAR.AMT_MEMBER;\n"
                + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
                + "    GENERATED_VAR |= LHS_VAR.LIST_MEMBER[i] != RHS_VAR.LIST_MEMBER[i];\n"
                + "}\n";

        public final static List<LoopBound> loopBoundsSingleChoice =
                LoopBound.codeGenLoopboundList(
                        Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_VOTERS)
                );

        public static String templatePreference =
                  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER == RHS_VAR.AMT_MEMBER;\n"
                + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
                + "    for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
                + "        GENERATED_VAR &= LHS_VAR.LIST_MEMBER[i][j] COMP RHS_VAR.LIST_MEMBER[i][j];\n"
                + "    }\n"
                + "}\n";

        public static String templatePreferenceUneq =
                  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER != RHS_VAR.AMT_MEMBER;\n"
                + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
                + "    for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
                + "        GENERATED_VAR |= LHS_VAR.LIST_MEMBER[i][j] != RHS_VAR.LIST_MEMBER[i][j];\n"
                + "    }\n"
                + "}\n";

        public final static List<LoopBound> loopBoundsPreference =
                LoopBound.codeGenLoopboundList(
                Arrays.asList(
                        LoopBoundType.LOOP_BOUND_AMT_VOTERS, LoopBoundType.LOOP_BOUND_AMT_SEATS)
                );

        public static String templateApproval = templatePreference;
        public static String templateApprovalUneq = templatePreferenceUneq;
        public final static List<LoopBound> loopBoundsApproval = loopBoundsPreference;
}
