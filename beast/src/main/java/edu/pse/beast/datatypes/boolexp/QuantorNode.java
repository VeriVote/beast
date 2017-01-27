package edu.pse.beast.datatypes.boolexp;

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

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        // TODO Auto-generated method stub
        
    }
}
