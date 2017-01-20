package edu.pse.beast.datatypes.boolexp;

import java.util.List;
/**
 * 
 * @author Lukas
 *
 */
public class BooleanListNode {
    private List<BooleanExpressionNode> boolNodes;
    
    /**
     * 
     * @return the list of nodes
     */
    public List<BooleanExpressionNode> getBooleanExpressions() {
        return boolNodes;
    }
    
    public void getVisited() {
        
    }
}
