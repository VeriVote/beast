package edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;

/**
 * 
 * @author Lukas
 *
 */
public class LogicalAndNode extends BinaryRelationshipNode {

    /**
     * 
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public LogicalAndNode(BooleanExpressionNode lhsExpNode, BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitAndNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        String tabs = "\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1);
        return "&&\n" +
                tabs + "lhs: " + getLHSBooleanExpNode().getTreeString(depth + 1) +
                tabs + "rhs: " + getRHSBooleanExpNode().getTreeString(depth + 1);
    }

}
