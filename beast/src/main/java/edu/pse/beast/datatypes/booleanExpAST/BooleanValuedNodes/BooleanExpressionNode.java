package edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;

/**
 * 
 * @author Lukas
 *
 */
public abstract class BooleanExpressionNode {
    
    
    /**
     * visits this node
     * @param visitor the visitor that visits
     */
    public abstract void getVisited(BooleanExpNodeVisitor visitor);
    
}
