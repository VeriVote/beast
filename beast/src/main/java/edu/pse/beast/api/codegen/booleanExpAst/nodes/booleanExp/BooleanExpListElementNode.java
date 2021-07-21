package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class BooleanExpListElementNode extends BooleanExpressionNode {

    private BooleanExpressionNode firstChild;
    private String completeCode;

    public BooleanExpListElementNode(final String completeCodeString,
                                     final BooleanExpressionNode firstChildNode) {
        this.firstChild = firstChildNode;
        this.completeCode = completeCodeString;
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitBooleanExpListElementNode(this);
    }

    @Override
    public final String getTreeString(final int depth) {
        return firstChild.getTreeString(depth);
    }

    public final String getCompleteCode() {
        return completeCode;
    }

    public final BooleanExpressionNode getFirstChild() {
        return firstChild;
    }

}
