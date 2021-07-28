package edu.pse.beast.api.codegen.code_template.templates.elect;

public class CodeTemplateElectEmpty {
    private static final String TEMPLATE_PARLIAMENT =
            "unsigned int GENERATED_VAR = TESTED_VAR.AMT_MEMBER == 0;\n";
    private static final String TEMPLATE_CANDIDATE_LIST = TEMPLATE_PARLIAMENT;

    public static String getTemplateParliament() {
        return TEMPLATE_PARLIAMENT;
    }

    public static String getTemplateCandidateList() {
        return TEMPLATE_CANDIDATE_LIST;
    }
}
