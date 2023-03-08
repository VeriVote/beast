package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.symbolic;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable.VariableType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class SymbVarByPosExp extends SymbolicVarExp {
    private VariableType variableType;
    private int accessingNumber;

    public SymbVarByPosExp(final VariableType varType,
                           final int accNumber) {
        this.variableType = varType;
        this.accessingNumber = accNumber;
    }

    public final VariableType getVarType() {
        return variableType;
    }

    public final int getAccessingNumber() {
        return accessingNumber;
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitSymbVarByPosExp(this);
    }

    @Override
    public final String getTreeString(final int depth) {
        // TODO Auto-generated method stub
        return null;
    }
}
