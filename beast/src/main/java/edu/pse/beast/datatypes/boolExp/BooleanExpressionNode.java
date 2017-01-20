package edu.pse.beast.datatypes.boolExp;

public abstract class BooleanExpressionNode {
    
    
    /**
     * visits this node
     * @param visitor the visitor that visits
     */
    public abstract void getVisited(BooleanExpNodeVisitor visitor);
    
}
