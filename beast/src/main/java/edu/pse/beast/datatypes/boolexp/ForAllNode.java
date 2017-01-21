package edu.pse.beast.datatypes.boolexp;

import toBeImplemented.SymbolicVariable;

/**
 * 
 * @author Lukas
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


}
