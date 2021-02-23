package edu.pse.beast.api.codegen;

public class CBMCVar {
	public enum CBMCVarType {
		CANDIDATE, VOTER,
	}
	
	private String name;
	private CBMCVarType varType;
	
	public CBMCVar(String name, CBMCVarType varType) {
		this.name = name;
		this.varType = varType;
	}
	
	public String getName() {
		return name;
	}
	
	public CBMCVarType getVarType() {
		return varType;
	}	
}
