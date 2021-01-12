package edu.pse.beast.api.codegen.c_code;

public class CTypeAndName {
	
	String type;
	String name;
	
	public CTypeAndName(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String generateCode() {
		return type + " " + name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
}
