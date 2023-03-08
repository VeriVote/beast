package edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * The Class QuantifierNode.
 *
 * @author Holger Klein, Lukas Stapelbroek
 */
public abstract class QuantifierNode extends BooleanExpressionNode {
    /** The decl symb var. */
    private final SymbolicVariable declSymbVar;

    /** The following node. */
    private final BooleanExpressionNode followingNode;

    /**
     * Instantiates a new quantifier node.
     *
     * @param declSymbVariable  the symbolic variable of this quantifier
     * @param followingExprNode the following node of this quantifier
     */
    public QuantifierNode(final SymbolicVariable declSymbVariable,
            final BooleanExpressionNode followingExprNode) {
        this.declSymbVar = declSymbVariable;
        this.followingNode = followingExprNode;
    }

    /**
     * Gets the declared symbolic var.
     *
     * @return the symbolic variable of this expression
     */
    public SymbolicVariable getDeclaredSymbolicVar() {
        return declSymbVar;
    }

    /**
     * Gets the following exp node.
     *
     * @return the following node
     */
    public BooleanExpressionNode getFollowingExpNode() {
        return followingNode;
    }
}
