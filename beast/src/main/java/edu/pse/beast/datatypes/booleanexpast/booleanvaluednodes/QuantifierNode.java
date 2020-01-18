package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

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
     * @param declSymbVariable
     *            the symbolic variable of this quantifier
     * @param followingExprNode
     *            the following node of this quantifier
     */
    public QuantifierNode(final SymbolicVariable declSymbVariable,
                          final BooleanExpressionNode followingExprNode) {
        this.declSymbVar = declSymbVariable;
        this.followingNode = followingExprNode;
    }

    @Override
    public final int hashCode() {
        int result = 1;
        result = PRIME * result
                + ((declSymbVar == null) ? 0 : declSymbVar.hashCode());
        result = PRIME * result
                + ((followingNode == null) ? 0 : followingNode.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuantifierNode that = (QuantifierNode) o;
        if (declSymbVar != null ? !declSymbVar.equals(that.declSymbVar)
                : that.declSymbVar != null) {
            return false;
        }
        return followingNode != null ? followingNode.equals(that.followingNode)
                : that.followingNode == null;
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
