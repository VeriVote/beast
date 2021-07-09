package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.data.BinaryCombinationSymbols;

/**
 * The Class LogicalAndNode.
 *
 * @author Lukas Stapelbroek
 */
public final class LogicalAndNode extends BinaryRelationshipNode {
    /**
     * Instantiates a new logical and node.
     *
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public LogicalAndNode(final BooleanExpressionNode lhsExpNode,
            final BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(BooleanAstVisitor visitor) {
        visitor.visitBinaryRelationNode(this, BinaryCombinationSymbols.AND);
    }
}
