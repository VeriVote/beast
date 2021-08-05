package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class FalseTrueNode extends BooleanExpressionNode {
    private boolean falseOrTrue;

    public FalseTrueNode(final boolean nodeType) {
        this.falseOrTrue = nodeType;
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitBooleanExpFalseTrueNode(this);
    }

    @Override
    public final String getTreeString(final int depth) {
        // TODO Auto-generated method stub
        return "False";
    }

    public final boolean getFalseOrTrue() {
        return falseOrTrue;
    }
}
