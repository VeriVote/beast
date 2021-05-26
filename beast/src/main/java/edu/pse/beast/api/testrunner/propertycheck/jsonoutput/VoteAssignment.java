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

	public String getLine() {
		return line;
	}

	public String getAssignmentFunc() {
		return assignmentFunc;
	}

	public int getVoteNumber() {
		return voteNumber;
	}

	public String getVarName() {
		return varName;
	}

	public String getMemberName() {
		return memberName;
	}

	public int getValue() {
		return value;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public void setAssignmentFunc(String assignmentFunc) {
		this.assignmentFunc = assignmentFunc;
	}

	public void setVoteNumber(int voteNumber) {
		this.voteNumber = voteNumber;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
