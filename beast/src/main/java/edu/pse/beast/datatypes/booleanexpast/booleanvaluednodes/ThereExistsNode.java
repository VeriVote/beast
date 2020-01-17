package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 * The Class ThereExistsNode.
 *
 * @author Holger
 */
public class ThereExistsNode extends QuantifierNode {

    /**
     * Instantiates a new there exists node.
     *
     * @param declSymbVar
     *            the symbolic variable of this quantifier
     * @param followingNode
     *            the following node of this quantifier
     */
    public ThereExistsNode(final SymbolicVariable declSymbVar,
                           final BooleanExpressionNode followingNode) {
        super(declSymbVar, followingNode);
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitThereExistsNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        return "ExistsOne: Declared var: " + getDeclaredSymbolicVar().getId()
                + "\n" + "\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1)
                + "following: "
                + getFollowingExpNode().getTreeString(depth + 1);
    }
}
