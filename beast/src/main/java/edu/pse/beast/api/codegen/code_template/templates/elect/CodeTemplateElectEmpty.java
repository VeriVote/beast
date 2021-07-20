package edu.pse.beast.api.codegen.code_template.templates.elect;

public class CodeTemplateElectEmpty {
    private static String templateParliament =
            "unsigned int GENERATED_VAR = TESTED_VAR.AMT_MEMBER == 0;\n";
    private static String templateCandidateList = templateParliament;

    public static String getTemplateParliament() {
        return templateParliament;
    }

    public static String getTemplateCandidateList() {
        return templateCandidateList;
    }
}
