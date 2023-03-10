package edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;
import edu.kit.kastel.formal.beast.api.codegen.ast.data.BinaryCombinationSymbols;

/**
 * The Class EquivalenceNode.
 *
 * @author Lukas Stapelbroek
 */
public final class EquivalenceNode extends BinaryRelationshipNode {
    /**
     * Instantiates a new equivalence node.
     *
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public EquivalenceNode(final BooleanExpressionNode lhsExpNode,
                           final BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitBinaryRelationNode(this, BinaryCombinationSymbols.EQUIV);
    }
}
