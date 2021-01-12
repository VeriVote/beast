package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CFunction {
	private String name;
	private List<CTypeAndName> args = new ArrayList<>();
	private String returnType;
	private List<String> code;

	public CFunction(String name, List<String> args, String returnType) {
		this.name = name;
		for (String s : args) {
			String typeAndName[] = s.split(" ");
			this.args.add(new CTypeAndName(typeAndName[0], typeAndName[1]));
		}
		this.returnType = returnType;
	}

	public void setCode(List<String> code) {
		this.code = code;
	}

	private String signature() {
		return returnType + " " + name + "("
				+ String.join(", ", args.stream().map(a -> a.generateCode()).collect(Collectors.toList())) + ")";
	}

	public String generateDefCode() {
		List<String> created = new ArrayList<>();
		created.add(signature());
		created.add("{");
		created.addAll(code);
		created.add("}");

		return String.join("\n", created);
	}

	public String generateDeclCode() {
		return signature() + ";";
	}

}
