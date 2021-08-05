package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CCodeBlock {
    private static final String BLANK = " ";
    private static final String LINE_BREAK = "\n";
    private static final String SEMICOLON = ";";

    private static final String BRACE_OP = "{";
    private static final String BRACE_CL = "}";
    private static final String LINE_COMMENT = "//";
    private static final String EQ = "=";

    private List<String> code = new ArrayList<>();
    private int number;

    public final void addComment(final String comment) {
        code.add(LINE_COMMENT + BLANK + comment);
    }

    public final void addSnippet(final String snippet) {
        code.add(snippet);
    }

    public final void addSnippet(final List<String> snippet) {
        code.addAll(snippet);
    }

    public final String generateCode() {
        return BRACE_OP + LINE_BREAK
                + String.join(LINE_BREAK, CFunction.indent(code))
                + LINE_BREAK + BRACE_CL + LINE_BREAK;
    }

    public final String newVarName(final String hint) {
        return hint + number++;
    }

    public final void addVarDecl(final String type, final String generatedVarName) {
        code.add(type + BLANK + generatedVarName + SEMICOLON);

    }

    public final void addAssignment(final String lhs, final String rhs) {
        code.add(lhs + BLANK + EQ + BLANK + rhs + SEMICOLON);
    }
}
