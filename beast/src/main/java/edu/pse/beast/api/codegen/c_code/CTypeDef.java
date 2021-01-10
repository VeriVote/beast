package edu.pse.beast.api.codegen.c_code;

public class CTypeDef {
	private String type;
	private String newTypeName;

	public CTypeDef(String type, String newTypeName) {
		this.type = type;
		this.newTypeName = newTypeName;
	}
}
