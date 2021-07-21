package edu.pse.beast.api.codegen.code_template.templates.elect;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateElectComparison {
    public static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS)
                    );

    private static final String LINE_BREAK = "\n";

    public static final List<LoopBound> LOOP_BOUNDS_PARLIAMENT = LOOP_BOUNDS_CANDIDATE_LIST;

    private static final String TEMPLATE_SINGLE_CANDIDATE =
            "unsigned int GENERATED_VAR = LHS_VAR.LIST_MEMBER COMP RHS_VAR.LIST_MEMBER;"
                    + LINE_BREAK;

    private static final String TEMPLATE_CANDIDATE_LIST =
            "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER == RHS_VAR.AMT_MEMBER;" + LINE_BREAK
            + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {" + LINE_BREAK
            + "    GENERATED_VAR &= LHS_VAR.LIST_MEMBER[i] COMP RHS_VAR.LIST_MEMBER[i];"
            + LINE_BREAK
            + "}" + LINE_BREAK;

    private static final String TEMPLATE_CANDIDATE_LIST_UNEQ =
            "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER != RHS_VAR.AMT_MEMBER;" + LINE_BREAK
            + "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {" + LINE_BREAK
            + "    GENERATED_VAR |= LHS_VAR.LIST_MEMBER[i] != RHS_VAR.LIST_MEMBER[i];" + LINE_BREAK
            + "}" + LINE_BREAK;

    private static final String TEMPLATE_PARLIAMENT_UNEQ = getTemplateCandidateListUneq();
    private static final String TEMPLATE_PARLIAMENT = getTemplateCandidateList();

    public static String getTemplateSingleCandidate() {
        return TEMPLATE_SINGLE_CANDIDATE;
    }

    public static String getTemplateCandidateList() {
        return TEMPLATE_CANDIDATE_LIST;
    }

    public static String getTemplateCandidateListUneq() {
        return TEMPLATE_CANDIDATE_LIST_UNEQ;
    }

    public static String getTemplateParliamentUneq() {
        return TEMPLATE_PARLIAMENT_UNEQ;
    }

    public static String getTemplateParliament() {
        return TEMPLATE_PARLIAMENT;
    }
}
