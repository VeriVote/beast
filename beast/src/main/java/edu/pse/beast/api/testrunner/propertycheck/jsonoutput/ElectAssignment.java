package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

public class ElectAssignment {
	private String line;
	private String assignmentFunc;
	private int electNumber;
	private String varName;
	private String memberName;
	private int value;

	public ElectAssignment(String line, String assignmentFunc, int electNumber,
			String varName, String memberName, int value) {
		this.line = line;
		this.assignmentFunc = assignmentFunc;
		this.electNumber = electNumber;
		this.varName = varName;
		this.memberName = memberName;
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
