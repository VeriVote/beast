package edu.pse.beast.api.codegen.cbmc;

public class SymbolicCBMCVar {
    public enum CBMCVarType {
        CANDIDATE, VOTER, SEAT
    }

    private String name;
    private CBMCVarType varType;

    public SymbolicCBMCVar(final String nameString, final CBMCVarType variableType) {
        this.name = nameString;
        this.varType = variableType;
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
