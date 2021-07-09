package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;

/**
 * The Class ConstantExp.
 *
 * @author Holger Klein
 */
public final class ConstantExp extends IntegerValuedExpression {

    private CBMCVarType varType;
    private int number;

    public ConstantExp(CBMCVarType varType, int number) {
        this.varType = varType;
        this.number = number;
    }

    public CBMCVarType getVarType() {
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
    public void getVisited(BooleanAstVisitor visitor) {
        visitor.visitConstantExp(this);
    }
}
