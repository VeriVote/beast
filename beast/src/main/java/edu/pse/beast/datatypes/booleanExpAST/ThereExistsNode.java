package edu.pse.beast.datatypes.booleanExpAST;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;


/**
 * 
 * @author Holger
 *
 */
public class ThereExistsNode extends QuantorNode {

    /**
     * 
     * @param declSymbVar the symbolic variable of this quantor
     * @param followingNode the following node of this quantor
     */
    public ThereExistsNode(SymbolicVariable declSymbVar, BooleanExpressionNode followingNode) {
        super(declSymbVar, followingNode);
    }

}
