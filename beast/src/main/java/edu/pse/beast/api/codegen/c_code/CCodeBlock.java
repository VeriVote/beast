package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CCodeBlock {
	private List<String> code = new ArrayList<>();
	private int number = 0;

	public void addComment(String comment) {
		code.add("// " + comment);
	}

	public void addSnippet(String snippet) {
		code.add(snippet);
	}

	public void addSnippet(List<String> snippet) {
		code.addAll(snippet);
	}

	public String generateCode() {
		return "{\n" + String.join("\n", code) + "\n}\n";
	}

	public String newVarName(String hint) {
		return hint + number++;
	}

	public void addVarDecl(String type, String generatedVarName) {
		code.add(type + " " + generatedVarName + ";");

	}

	public void addAssignment(String lhs, String rhs) {
		code.add(lhs + " = " + rhs + ";");
	}
}
