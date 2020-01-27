package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import static edu.pse.beast.toolbox.CCodeHelper.lineBreak;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * The Class BooleanExpressionNode.
 *
 * @author Lukas Stapelbroek
 */
public abstract class BooleanExpressionNode {
    /** The Constant PRIME. */
    protected static final int PRIME = 31;

    /** The Constant LINE_BREAK. */
    protected static final String LINE_BREAK = lineBreak();

    /** The Constant TAB. */
    protected static final String TAB = "\t";

    /** The Constant TABS. */
    protected static final String TABS = "\t\t\t\t\t\t\t\t\t\t\t\t";

    /** The Constant LHS. */
    protected static final String LHS = "lhs: ";

    /** The Constant RHS. */
    protected static final String RHS = "rhs: ";

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
