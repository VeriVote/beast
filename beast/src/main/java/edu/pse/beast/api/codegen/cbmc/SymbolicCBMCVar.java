package edu.pse.beast.api.codegen.cbmc;

public class SymbolicCBMCVar {
    private static final String BLANK = " ";

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
    public final String toString() {
        return varType.toString() + BLANK + name;
    }

    public final String getName() {
        return name;
    }

    public final CBMCVarType getVarType() {
        return varType;
    }
}
