package edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;


/**
 * 
 * @author Holger
 *
 */
public class ForAllNode extends QuantorNode {

    /**
     * 
     * @param declSymbVar the symbolic variable of this quantor
     * @param followingNode the following node of this quantor
     */
    public ForAllNode(SymbolicVariable declSymbVar, BooleanExpressionNode followingNode) {
        super(declSymbVar, followingNode);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitForAllNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        return "ForAll: Declared var: " + getDeclaredSymbolicVar().getId() + "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t".substring(0,depth + 1) + "following: " +
                getFollowingExpNode().getTreeString(depth + 1);
    }
}
