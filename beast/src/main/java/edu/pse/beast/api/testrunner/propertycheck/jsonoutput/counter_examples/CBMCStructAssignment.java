package edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
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

    public final String getVarName() {
        return structName;
    }

    public final int getSortNumber() {
        return sortNumber;
    }

    public final CBMCAssignmentType getAssignmentType() {
        return assignmentType;
    }

    public final String getVarInfo() {
        return varInfo;
    }

    public final void add(final String memberName, final String assignment, final int sortNum) {
        memberToAssignment.put(memberName, assignment);
        this.sortNumber = sortNum;
    }

    public final String getAssignmentFor(final String memberName) {
        return memberToAssignment.get(memberName);
    }

    public final Map<String, String> getMemberToAssignment() {
        return memberToAssignment;
    }

    @Override
    public final String toString() {
        return structName;
    }

}
