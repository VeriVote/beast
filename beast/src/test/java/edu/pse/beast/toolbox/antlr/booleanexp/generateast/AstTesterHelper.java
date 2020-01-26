package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;

/**
 * Utility methods for the AST tests.
 *
 * @author Holger Klein
 */
public final class AstTesterHelper {
    private AstTesterHelper() { }

    /**
     * Are the same.
     *
     * @param lhs
     *            the lhs
     * @param rhs
     *            the rhs
     */
    public static void areTheSame(final BooleanExpListNode lhs,
                                  final BooleanExpListNode rhs) {
        assertEquals(lhs.getHighestElect(), rhs.getHighestElect());
        assertEquals(lhs.getMaxVoteLevel(), rhs.getMaxVoteLevel());
        for (int i = 0; i < lhs.getBooleanExpressions().size(); i++) {
            final List<BooleanExpressionNode> lhsList = lhs.getBooleanExpressions().get(i);
            final List<BooleanExpressionNode> rhsList = rhs.getBooleanExpressions().get(i);
            areTheSame(lhsList, rhsList);
        }
    }

    /**
     * Are the same.
     *
     * @param lhsList
     *            the lhs list
     * @param rhsList
     *            the rhs list
     */
    private static void areTheSame(final List<BooleanExpressionNode> lhsList,
                                   final List<BooleanExpressionNode> rhsList) {
        assertEquals(lhsList.size(), rhsList.size());
        for (int i = 0; i < lhsList.size(); i++) {
            areTheSame(lhsList.get(i), rhsList.get(i));
        }
    }

    /**
     * Are the same.
     *
     * @param lhsNode
     *            the lhs node
     * @param rhsNode
     *            the rhs node
     */
    private static void areTheSame(final BooleanExpressionNode lhsNode,
                                   final BooleanExpressionNode rhsNode) {
        if (!lhsNode.equals(rhsNode)) {
            System.out.println("expected: " + lhsNode.toString()
                               + " actual: " + rhsNode.toString());
        }
        assertTrue(lhsNode.equals(rhsNode));
    }
}
