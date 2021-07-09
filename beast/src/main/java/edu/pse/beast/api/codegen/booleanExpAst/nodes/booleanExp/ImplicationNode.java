package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.data.BinaryCombinationSymbols;

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
    public void getVisited(BooleanAstVisitor visitor) {
        visitor.visitBinaryRelationNode(this, BinaryCombinationSymbols.IMPL);
    }
}
