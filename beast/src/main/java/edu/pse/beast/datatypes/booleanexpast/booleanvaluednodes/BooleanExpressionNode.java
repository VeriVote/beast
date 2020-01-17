package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * The Class BooleanExpressionNode.
 *
 * @author Lukas Stapelbroek
 */
public abstract class BooleanExpressionNode {

    /**
     * Visits this node.
     *
     * @param visitor
     *            the visitor that visits
     */
    public abstract void getVisited(BooleanExpNodeVisitor visitor);

    /**
     * Gets the tree string.
     *
     * @param depth
     *            the depth
     * @return the tree string
     */
    public abstract String getTreeString(int depth);
}
