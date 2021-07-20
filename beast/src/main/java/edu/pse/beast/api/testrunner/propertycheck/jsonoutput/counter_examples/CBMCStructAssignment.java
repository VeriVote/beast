package edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples;

import java.util.HashMap;
import java.util.Map;

public class CBMCStructAssignment {
    private CBMCAssignmentType assignmentType;
    private String structName;
    private Map<String, String> memberToAssignment = new HashMap<>();
    private String varInfo;

    private int sortNumber;

    public CBMCStructAssignment(final CBMCAssignmentType varAssignmentType,
                                final String varName, final String variableInfo) {
        this.assignmentType = varAssignmentType;
        this.structName = varName;
        this.varInfo = variableInfo;
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

    public void add(final String memberName, final String assignment, final int sortNum) {
        memberToAssignment.put(memberName, assignment);
        this.sortNumber = sortNum;
    }

    public String getAssignmentFor(final String memberName) {
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
