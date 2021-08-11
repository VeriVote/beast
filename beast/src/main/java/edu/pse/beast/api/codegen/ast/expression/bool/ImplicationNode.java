package edu.pse.beast.api.codegen.ast.expression.bool;

import edu.pse.beast.api.codegen.ast.BooleanAstVisitor;
import edu.pse.beast.api.codegen.ast.data.BinaryCombinationSymbols;

/**
 * The Class ImplicationNode.
 *
 * @author Lukas Stapelbroek
 */
public final class ImplicationNode extends BinaryRelationshipNode {
    /**
     * Instantiates a new implication node.
     *
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public ImplicationNode(final BooleanExpressionNode lhsExpNode,
                           final BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitBinaryRelationNode(this, BinaryCombinationSymbols.IMPL);
    }
}
