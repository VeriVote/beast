package edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples;

import java.util.HashMap;
import java.util.Map;

public class CBMCStructAssignment {
	private CBMCAssignmentType assignmentType;
	private String structName;
	private Map<String, String> memberToAssignment = new HashMap<>();

	public CBMCStructAssignment(CBMCAssignmentType assignmentType,
			String varName) {
		this.assignmentType = assignmentType;
		this.structName = varName;
	}

	public String getVarName() {
		return structName;
	}

	public CBMCAssignmentType getAssignmentType() {
		return assignmentType;
	}

	public void add(String memberName, String assignment) {
		memberToAssignment.put(memberName, assignment);
	}

	@Override
	public String toString() {
		return structName;
	}

}
