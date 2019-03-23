package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;


/**
 *
 * @author Holger
 *
 */
public abstract class QuantorNode extends BooleanExpressionNode {
    private final SymbolicVariable declSymbVar;
    private final BooleanExpressionNode followingNode;

    /**
     *
     * @param declSymbVar the symbolic variable of this quantor
     * @param followingNode the following node of this quantor
     */
    public QuantorNode(SymbolicVariable declSymbVar, BooleanExpressionNode followingNode) {
        this.declSymbVar = declSymbVar;
        this.followingNode = followingNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuantorNode that = (QuantorNode) o;
        if (declSymbVar != null ? !declSymbVar.equals(that.declSymbVar) : that.declSymbVar != null) return false;
        return followingNode != null ? followingNode.equals(that.followingNode) : that.followingNode == null;
    }

    /**
     *
     * @return the symbolic variable of this expression
     */
    public SymbolicVariable getDeclaredSymbolicVar() {
        return declSymbVar;
    }

    /**
     *
     * @return the following node
     */
    public BooleanExpressionNode getFollowingExpNode() {
        return followingNode;
    }
}