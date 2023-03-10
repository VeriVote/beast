package edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * The Class ThereExistsNode.
 *
 * @author Holger Klein
 */
public final class ThereExistsNode extends QuantifierNode {
    /**
     * Instantiates a new there exists node.
     *
     * @param declSymbVar   the symbolic variable of this quantifier
     * @param followingNode the following node of this quantifier
     */
    public ThereExistsNode(final SymbolicVariable declSymbVar,
                           final BooleanExpressionNode followingNode) {
        super(declSymbVar, followingNode);
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitExistsCandidateNode(this);
    }
}
