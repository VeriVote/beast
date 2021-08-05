package edu.pse.beast.api.codegen.cbmc;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class SymbolicCBMCVar {
    private static final String BLANK = " ";

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
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
