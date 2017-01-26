package edu.pse.beast.datatypes.boolexp;

import java.util.Iterator;
import java.util.List;
/**
 * 
 * @author Lukas
 *
 */
public class BooleanExpListNode {
    private List<BooleanExpressionNode> boolNodes;
    
    /**
     * 
     * @return the list of nodes
     */
    public List<BooleanExpressionNode> getBooleanExpressions() {
        return boolNodes;
    }
    
    /**
     * 
     * @param visitor to be visited
     */
    public void getVisited(BooleanExpNodeVisitor visitor) {
        boolNodes.forEach((booleanExpressionNode) -> {
            booleanExpressionNode.getVisited(visitor);
        });
    }
    
    public void addNode(BooleanExpressionNode node) {
        boolNodes.add(node);
    }
}
