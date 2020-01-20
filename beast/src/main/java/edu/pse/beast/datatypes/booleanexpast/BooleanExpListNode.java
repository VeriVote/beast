package edu.pse.beast.datatypes.booleanexpast;

import java.util.ArrayList;

import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;

/**
 * The Class BooleanExpListNode.
 *
 * @author Lukas Stapelbroek, Holger Klein
 */
public final class BooleanExpListNode {
    /**
     * This list stores the subnodes by the highest number of ELECT statement
     * appearing in them. This is important for code generation. Ex:
     * FOR_ALL_VOTERS(v) : VOTES4(v) == ELECT3; <-- would be stored in
     * boolNodesPerElectLevel.get(3) ELECT2 == ELECT5; <-- would be stored in
     * boolNodesPerElectLevel.get(5)
     */
    private ArrayList<ArrayList<BooleanExpressionNode>> boolNodesPerElectLevel =
            new ArrayList<ArrayList<BooleanExpressionNode>>();

    /** The max vote level. */
    private int maxVoteLevel;

    /**
     * Gets the boolean expressions.
     *
     * @return the boolean expressions
     */
    public ArrayList<ArrayList<BooleanExpressionNode>> getBooleanExpressions() {
        return boolNodesPerElectLevel;
    }

    /**
     * Gets the highest elect.
     *
     * @return the highest elect
     */
    public int getHighestElect() {
        return boolNodesPerElectLevel.size() - 1;
    }

    /**
     * Sets the max vote level.
     *
     * @param maxVoteLevelNumber
     *            the new max vote level
     */
    public void setMaxVoteLevel(final int maxVoteLevelNumber) {
        if (maxVoteLevelNumber > this.maxVoteLevel) {
            this.maxVoteLevel = maxVoteLevelNumber;
        }
    }

    /**
     * Gets the max vote level.
     *
     * @return the max vote level
     */
    public int getMaxVoteLevel() {
        return maxVoteLevel;
    }

    /**
     * Adds the node.
     *
     * @param node
     *            the node
     * @param highestElectNumber
     *            the highest elect number
     */
    public void addNode(final BooleanExpressionNode node,
                        final int highestElectNumber) {
        while (highestElectNumber >= boolNodesPerElectLevel.size()) {
            boolNodesPerElectLevel.add(new ArrayList<BooleanExpressionNode>());
        }
        boolNodesPerElectLevel.get(highestElectNumber).add(node);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        boolNodesPerElectLevel
                .forEach(l -> l.forEach(n -> b.append(n.toString())));
        return b.toString();
    }

    /**
     * Gets the tree string.
     *
     * @return the tree string
     */
    public String getTreeString() {
        StringBuilder b = new StringBuilder();
        boolNodesPerElectLevel
                .forEach(l -> l.forEach(n -> b.append(n.getTreeString(0))));
        return b.toString();
    }
}
