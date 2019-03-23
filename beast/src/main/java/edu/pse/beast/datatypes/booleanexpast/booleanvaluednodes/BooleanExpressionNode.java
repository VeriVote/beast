package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 *
 * @author Lukas
 *
 */
public abstract class BooleanExpressionNode {
    /**
     * visits this node
     *
     * @param visitor the visitor that visits
     */
    public abstract void getVisited(BooleanExpNodeVisitor visitor);

    public abstract String getTreeString(int depth);
}