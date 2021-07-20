package edu.pse.beast.api.codegen.code_template.templates.elect;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateElectTuple {
    public static final String TEMPLATE_VAR_SETUP =
              "        ELECT_TYPE VAR_NAME;\n"
            + "        VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
            + "        ASSUME(VAR_NAME.AMT_MEMBER == VOTE_ELECT_SUM);\n"
            + "        unsigned int pos = 0;\n";

    public static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_CANDS)
            );

    private static final String TEMPLATE_CANDIDATE_LIST =
            "        for (unsigned int i = 0; "
                    + "i < CURRENT_ELECT.AMT_MEMBER && i < AMT_CANDIDATES; ++pos) {\n"
                    + "            VAR_NAME.LIST_MEMBER[pos] = NONDET_UINT();\n"
                    + "            ASSUME(VAR_NAME.LIST_MEMBER[pos] == "
                    + "CURRENT_VOTE.LIST_MEMBER[i]);\n"
                    + "            pos++;\n"
                    + "        }\n";

    public static String getTemplateCandidateList() {
        return TEMPLATE_CANDIDATE_LIST;
    }
}
