package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * The Class BooleanExpressionNode.
 *
 * @author Lukas Stapelbroek
 */
public abstract class BooleanExpressionNode {

    public abstract void getVisited(BooleanAstVisitor visitor);

    /**
     * Gets the tree string.
     *
     * @param depth the depth
     * @return the tree string
     */
    public abstract String getTreeString(int depth);
}
