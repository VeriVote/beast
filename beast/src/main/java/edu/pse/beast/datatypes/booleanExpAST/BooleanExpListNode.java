package edu.pse.beast.datatypes.booleanExpAST;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * 
 * @author Lukas, Holger
 *
 */
public class BooleanExpListNode {
    /**
     * this list stores the subnodes by the highest number of ELECT statement appearing
     * in them. This is important for code generation. 
     * Ex:  FOR_ALL_VOTERS(v) : VOTES4(v) ==  ELECT3; <-- would be stored in boolNodesPerElectLevel.get(3)
     *      ELECT2 == ELECT5; <-- would be stored in boolNodesPerElectLevel.get(5) 
     */
    private List<List<BooleanExpressionNode>> boolNodesPerElectLevel = new ArrayList<>();
    private int maxVoteLevel;
    
    public List<List<BooleanExpressionNode>> getBooleanExpressions() {
        return boolNodesPerElectLevel;
    }
    
    public int getHighestElect() {
        return boolNodesPerElectLevel.size() - 1;
    }

    public void setMaxVoteLevel(int maxVoteLevel) {
        this.maxVoteLevel = maxVoteLevel;
    }
    
    public int getMaxVoteLevel() {
        return maxVoteLevel;
    }
    
    public void addNode(BooleanExpressionNode node, int highestElectNumber) {
        while(highestElectNumber >= boolNodesPerElectLevel.size()) {
            boolNodesPerElectLevel.add(new ArrayList<BooleanExpressionNode>());
        }
        boolNodesPerElectLevel.get(highestElectNumber).add(node);
    }
}
