package edu.pse.beast.api.codegen.ast.expression.type.integer;

import edu.pse.beast.api.codegen.ast.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicVariable.VariableType;

/**
 * The Class ConstantExp.
 *
 * @author Holger Klein
 */
public final class ConstantExp extends IntegerValuedExpression {
    private VariableType varType;
    private int number;

    public ConstantExp(final VariableType variableType, final int num) {
        this.varType = variableType;
        this.number = num;
    }

    public VariableType getVarType() {
        return varType;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitConstantExp(this);
    }
}
