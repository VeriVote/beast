package edu.pse.beast.api.codegen.c_code;

public class CInclude {
	private String filePath;

	public CInclude(String filePath) {
		this.filePath = filePath;
	}

	public String generateCode() {
		return "#include <" + filePath + ">";
	}

}
