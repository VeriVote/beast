package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CCodeBlock {
    private List<String> code = new ArrayList<>();
    private int number;

    public void addComment(final String comment) {
        code.add("// " + comment);
    }

    public void addSnippet(final String snippet) {
        code.add(snippet);
    }

    public void addSnippet(final List<String> snippet) {
        code.addAll(snippet);
    }

    public String generateCode() {
        return "{\n" + String.join("\n", code) + "\n}\n";
    }

    public String newVarName(final String hint) {
        return hint + number++;
    }

    public void addVarDecl(final String type, final String generatedVarName) {
        code.add(type + " " + generatedVarName + ";");

    }

    public void addAssignment(final String lhs, final String rhs) {
        code.add(lhs + " = " + rhs + ";");
    }
}
