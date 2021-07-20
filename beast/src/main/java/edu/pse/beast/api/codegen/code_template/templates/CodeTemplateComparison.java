package edu.pse.beast.api.codegen.code_template.templates;

public class CodeTemplateComparison {
    public static final String TEMPLATE_1D =
              " unsigned int GENERATED_VAR = 1;\n"
            + "    for(int i = 0; i < AMT; ++i) {\n"
            + "        GENERATED_VAR &= LHS_VAR_NAME[i] COMP RHS_VAR_NAME[i];\n"
            + "    }\n";

    public static final String TEMPLATE_1D_UNEQ =
              " unsigned int GENERATED_VAR = 0;\n"
            + "    for(int i = 0; i < AMT; ++i) {\n"
            + "        GENERATED_VAR |= LHS_VAR_NAME[i] != RHS_VAR_NAME[i];\n"
            + "    }\n";

    public static final String TEMPLATE_0D =
            "unsigned int GENERATED_VAR = LHS_VAR_NAME COMP RHS_VAR_NAME;\n";
}
