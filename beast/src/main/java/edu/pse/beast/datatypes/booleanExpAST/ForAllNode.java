package edu.pse.beast.datatypes.booleanExpAST;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;


/**
 * 
 * @author Holger
 *
 */
public class ForAllNode extends QuantorNode {

    /**
     * 
     * @param declSymbVar the symbolic variable of this quantor
     * @param followingNode the following node of this quantor
     */
    public ForAllNode(SymbolicVariable declSymbVar, BooleanExpressionNode followingNode) {
        super(declSymbVar, followingNode);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitForAllNode(this);
    }
}
