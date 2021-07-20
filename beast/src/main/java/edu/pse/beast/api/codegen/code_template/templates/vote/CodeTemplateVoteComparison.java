package edu.pse.beast.api.codegen.code_template.templates.vote;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVoteComparison {
    public static final List<LoopBound> LOOP_BOUNDS_SINGLE_CHOICE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_VOTERS)
                    );

    public static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(
                            LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                            LoopBoundType.LOOP_BOUND_AMT_SEATS)
                    );

    private static final String TEMPLATE_SINGLE_CHOICE_EQ =
            "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER == RHS_VAR.AMT_MEMBER;\n"
                    + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
                    + "    GENERATED_VAR &= LHS_VAR.LIST_MEMBER[i] COMP RHS_VAR.LIST_MEMBER[i];\n"
                    + "}\n";

    private static final String TEMPLATE_SINGLE_CHOICE_UNEQ =
            "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER != RHS_VAR.AMT_MEMBER;\n"
                    + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
                    + "    GENERATED_VAR |= LHS_VAR.LIST_MEMBER[i] != RHS_VAR.LIST_MEMBER[i];\n"
                    + "}\n";

    private static final String TEMPLATE_PREFERENCE =
            "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER == RHS_VAR.AMT_MEMBER;\n"
                    + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
                    + "    for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
                    + "        GENERATED_VAR &= "
                    + "LHS_VAR.LIST_MEMBER[i][j] COMP RHS_VAR.LIST_MEMBER[i][j];\n"
                    + "    }\n"
                    + "}\n";

    private static final String TEMPLATE_PREFERENCE_UNEQ =
            "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER != RHS_VAR.AMT_MEMBER;\n"
                    + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
                    + "    for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
                    + "        GENERATED_VAR |= "
                    + "LHS_VAR.LIST_MEMBER[i][j] != RHS_VAR.LIST_MEMBER[i][j];\n"
                    + "    }\n"
                    + "}\n";

    private static final String TEMPLATE_APPROVAL = getTemplatePreference();
    private static final String TEMPLATE_APPROVAL_UNEQ = getTemplatePreferenceUneq();

    public static final List<LoopBound> LOOP_BOUNDS_APPROVAL = LOOP_BOUNDS_PREFERENCE;

    public static String getTemplateSingleChoiceEq() {
        return TEMPLATE_SINGLE_CHOICE_EQ;
    }

    public static String getTemplateSingleChoiceUnEq() {
        return TEMPLATE_SINGLE_CHOICE_UNEQ;
    }

    public static String getTemplatePreference() {
        return TEMPLATE_PREFERENCE;
    }

    public static String getTemplatePreferenceUneq() {
        return TEMPLATE_PREFERENCE_UNEQ;
    }

    public static String getTemplateApproval() {
        return TEMPLATE_APPROVAL;
    }

    public static String getTemplateApprovalUneq() {
        return TEMPLATE_APPROVAL_UNEQ;
    }
}
