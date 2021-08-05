package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.TypeExpression;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class BooleanExpIsEmptyNode extends BooleanExpressionNode {
    private TypeExpression innerNode;

    public BooleanExpIsEmptyNode(final TypeExpression innerExpressionNode) {
        super();
        this.innerNode = innerExpressionNode;
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitEmptyNode(this);
    }

    @Override
    public final String getTreeString(final int depth) {
        return "EMPTY " + innerNode.getTreeString(depth + 1);
    }

    public final TypeExpression getInnerNode() {
        return innerNode;
    }
}
