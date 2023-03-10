package edu.kit.kastel.formal.beast.api.codegen.cbmc;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class SymbolicVariable {
    private static final String BLANK = " ";

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public enum VariableType {
        CANDIDATE, VOTER, SEAT;
    }

    private String name;
    private VariableType varType;

    public SymbolicVariable(final String nameString, final VariableType variableType) {
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

    public final VariableType getVarType() {
        return varType;
    }
}
