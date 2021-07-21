package edu.pse.beast.api.codegen.code_template.templates;

public class CodeTemplateComparison {
    private static final String LINE_BREAK = "\n";

    public static final String TEMPLATE_1D =
              " unsigned int GENERATED_VAR = 1;" + LINE_BREAK
            + "    for(int i = 0; i < AMT; ++i) {" + LINE_BREAK
            + "        GENERATED_VAR &= LHS_VAR_NAME[i] COMP RHS_VAR_NAME[i];" + LINE_BREAK
            + "    }" + LINE_BREAK;

    public static final String TEMPLATE_1D_UNEQ =
              " unsigned int GENERATED_VAR = 0;" + LINE_BREAK
            + "    for(int i = 0; i < AMT; ++i) {" + LINE_BREAK
            + "        GENERATED_VAR |= LHS_VAR_NAME[i] != RHS_VAR_NAME[i];" + LINE_BREAK
            + "    }" + LINE_BREAK;

    public static final String TEMPLATE_0D =
            "unsigned int GENERATED_VAR = LHS_VAR_NAME COMP RHS_VAR_NAME;" + LINE_BREAK;
}
