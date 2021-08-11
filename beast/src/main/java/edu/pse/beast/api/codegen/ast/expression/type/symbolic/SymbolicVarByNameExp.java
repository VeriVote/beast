package edu.pse.beast.api.codegen.ast.expression.type.symbolic;

import edu.pse.beast.api.codegen.ast.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * The Class SymbolicVarExp.
 *
 * @author Lukas Stapelbroek
 */
public final class SymbolicVarByNameExp extends SymbolicVarExp {
    private static final String OPENING_BRACE = "{";
    private static final String CLOSING_BRACE = "}";
    private static final String SYMB_VAR_TAG = "SymbVar: ";
    private static final String ID_TAG = "id ";

    private SymbolicVariable variable;

    public SymbolicVarByNameExp(final SymbolicVariable symbolicVariable) {
        this.variable = symbolicVariable;
    }

    public SymbolicVariable getCbmcVar() {
        return variable;
    }

    @Override
    public String getTreeString(final int depth) {
        return SYMB_VAR_TAG + OPENING_BRACE + ID_TAG + variable.getName() + CLOSING_BRACE;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitSymbolicVarExp(this);
    }
}
