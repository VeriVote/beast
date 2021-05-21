package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

public class VoteAssignment {
	private String line;
	private String assignmentFunc;

	private int voteNumber;
	private String varName;
	private String memberName;
	private int value;

	public VoteAssignment(String line, String assignmentFunc, int voteNumber,
			String varName, String memberName, int value) {
		this.line = line;
		this.assignmentFunc = assignmentFunc;
		this.voteNumber = voteNumber;
		this.varName = varName;
		this.memberName = memberName;
		this.value = value;
	}

	@Override
	public String toString() {
		return "VoteAssignment [line=" + line + ", assignmentFunc="
				+ assignmentFunc + ", voteNumber=" + voteNumber + ", varName="
				+ varName + ", memberName=" + memberName + ", value=" + value
				+ "]";
	}

}
