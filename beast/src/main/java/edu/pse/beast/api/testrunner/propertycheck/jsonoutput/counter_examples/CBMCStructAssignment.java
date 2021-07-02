package edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples;

import java.util.HashMap;
import java.util.Map;

public class CBMCStructAssignment {
	private CBMCAssignmentType assignmentType;
	private String structName;
	private Map<String, String> memberToAssignment = new HashMap<>();
	private String varInfo;
	
	private int sortNumber = 0;

	public CBMCStructAssignment(CBMCAssignmentType assignmentType,
			String varName, String varInfo) {
		this.assignmentType = assignmentType;
		this.structName = varName;
		this.varInfo = varInfo;
	}

	public String getVarName() {
		return structName;
	}
	
	public int getSortNumber() {
		return sortNumber;
	}

	public CBMCAssignmentType getAssignmentType() {
		return assignmentType;
	}
	
	public String getVarInfo() {
		return varInfo;
	}

	public void add(String memberName, String assignment, int sortNumber) {
		memberToAssignment.put(memberName, assignment);
		this.sortNumber = sortNumber;
	}
	
	public String getAssignmentFor(String memberName) {
		return memberToAssignment.get(memberName);
	}
	
	public Map<String, String> getMemberToAssignment() {
		return memberToAssignment;
	}

	@Override
	public String toString() {
		return structName;
	}

}
