package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class FalseTrueNode extends BooleanExpressionNode {
    private boolean falseOrTrue;

    public FalseTrueNode(final boolean nodeType) {
        this.falseOrTrue = nodeType;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitBooleanExpFalseTrueNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        // TODO Auto-generated method stub
        return "False";
    }

    public boolean getFalseOrTrue() {
        return falseOrTrue;
    }
}
