package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 *
 * @author Holger
 *
 */
public class ThereExistsNode extends QuantifierNode {
    /**
     *
     * @param declSymbVar   the symbolic variable of this quantifier
     * @param followingNode the following node of this quantifier
     */
    public ThereExistsNode(SymbolicVariable declSymbVar, BooleanExpressionNode followingNode) {
        super(declSymbVar, followingNode);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitThereExistsNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        return "ExistsOne: Declared var: " + getDeclaredSymbolicVar().getId() + "\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1) + "following: "
                + getFollowingExpNode().getTreeString(depth + 1);
    }
}