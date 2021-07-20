package edu.pse.beast.api.codegen.code_template.templates.vote;

public class CodeTemplateVoteEmpty {
    private static String templatePreference =
            "unsigned int GENERATED_VAR = TESTED_VAR.AMT_MEMBER == 0;\n";

    public static String getTemplatePreference() {
        return templatePreference;
    }
}
