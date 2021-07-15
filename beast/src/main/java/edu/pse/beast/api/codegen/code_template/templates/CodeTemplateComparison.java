package edu.pse.beast.api.codegen.code_template.templates;

public class CodeTemplateComparison {
    public final static String template1d =
              " unsigned int GENERATED_VAR = 1;\n"
            + "    for(int i = 0; i < AMT; ++i) {\n"
            + "        GENERATED_VAR &= LHS_VAR_NAME[i] COMP RHS_VAR_NAME[i];\n"
            + "    }\n";

    public final static String template1dUneq =
              " unsigned int GENERATED_VAR = 0;\n"
            + "    for(int i = 0; i < AMT; ++i) {\n"
            + "        GENERATED_VAR |= LHS_VAR_NAME[i] != RHS_VAR_NAME[i];\n"
            + "    }\n";

    public final static String template0d =
            "unsigned int GENERATED_VAR = LHS_VAR_NAME COMP RHS_VAR_NAME;\n";
}
