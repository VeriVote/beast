package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

public class ElectAssignment {
	private String line;
	private String assignmentFunc;
	private int electNumber;
	private String varName;
	private String memberName;
	private String value;

	public ElectAssignment(String line, String assignmentFunc, int electNumber,
			String varName, String memberName, String value) {
		this.line = line;
		this.assignmentFunc = assignmentFunc;
		this.electNumber = electNumber;
		this.varName = varName;
		this.memberName = memberName;
		this.value = value;
	}

	public String getLine() {
		return line;
	}

	public String getAssignmentFunc() {
		return assignmentFunc;
	}

	public int getElectNumber() {
		return electNumber;
	}

	public String getVarName() {
		return varName;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getValue() {
		return value;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public void setAssignmentFunc(String assignmentFunc) {
		this.assignmentFunc = assignmentFunc;
	}

	public void setElectNumber(int electNumber) {
		this.electNumber = electNumber;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ElectAssignment [line=" + line + ", assignmentFunc="
				+ assignmentFunc + ", electNumber=" + electNumber + ", varName="
				+ varName + ", memberName=" + memberName + ", value=" + value
				+ "]";
	}

}
