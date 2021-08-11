package edu.pse.beast.api.trace;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class StructAssignment {
    private AssignmentType assignmentType;
    private String structName;
    private Map<String, String> memberToAssignment = new LinkedHashMap<String, String>();
    private String varInfo;

    private int sortNumber;

    public StructAssignment(final AssignmentType varAssignmentType,
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

    public final AssignmentType getAssignmentType() {
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
