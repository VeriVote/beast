package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.TypeExpression;

public class BooleanExpIsEmptyNode extends BooleanExpressionNode {
    private TypeExpression innerNode;

    public BooleanExpIsEmptyNode(final TypeExpression innerExpressionNode) {
        super();
        this.innerNode = innerExpressionNode;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitEmptyNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        return "EMPTY " + innerNode.getTreeString(depth + 1);
    }

    public TypeExpression getInnerNode() {
        return innerNode;
    }
}
