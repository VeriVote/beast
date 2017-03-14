package edu.pse.beast.datatypes.booleanExpAST;

import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;

import javax.print.DocFlavor;
import java.util.ArrayList;
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
    private ArrayList<ArrayList<BooleanExpressionNode>> boolNodesPerElectLevel = new ArrayList<>();
    private int maxVoteLevel;
    
    public ArrayList<ArrayList<BooleanExpressionNode>> getBooleanExpressions() {
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

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        boolNodesPerElectLevel.forEach( l -> l.forEach( n -> b.append(n.toString())));
        return b.toString();
    }

    public String getTreeString() {
        StringBuilder b = new StringBuilder();
        boolNodesPerElectLevel.forEach(l -> l.forEach(n -> b.append(n.getTreeString(0))));
        return b.toString();
    }
}
