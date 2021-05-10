package edu.pse.beast.api.codegen.cbmc;

public class SymbolicCBMCVar {
	public enum CBMCVarType {
		CANDIDATE, VOTER, SEAT
	}
	
	private String name;
	private CBMCVarType varType;
	
	public SymbolicCBMCVar(String name, CBMCVarType varType) {
		this.name = name;
		this.varType = varType;
	}
	
	@Override
	public String toString() {
		return varType.toString() + " " + name;
	}
	
	public String getName() {
		return name;
	}
	
	public CBMCVarType getVarType() {
		return varType;
	}	
}
