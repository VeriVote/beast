package edu.pse.beast.api.codegen.ast.expression;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.ast.expression.bool.BooleanExpressionNode;

/**
 * The Class BooleanExpListNode.
 *
 * @author Lukas Stapelbroek, Holger Klein
 */
public final class BooleanExpListNode {

    private List<BooleanExpressionNode> booleanNodes = new ArrayList<BooleanExpressionNode>();

    /**
     * Gets the boolean expressions.
     *
     * @return the boolean expressions
     */
    public List<BooleanExpressionNode> getBooleanNodes() {
        return booleanNodes;
    }

    public void addNode(final BooleanExpressionNode node) {
        booleanNodes.add(node);
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();
        booleanNodes.forEach(n -> b.append(n.toString()));
        return b.toString();
    }

    /**
     * Gets the tree string.
     *
     * @return the tree string
     */
    public String getTreeString() {
        final StringBuilder b = new StringBuilder();
        booleanNodes.forEach(n -> b.append(n.getTreeString(0)));
        return b.toString();
    }
}
