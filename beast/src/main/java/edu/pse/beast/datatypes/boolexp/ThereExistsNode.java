package edu.pse.beast.datatypes.boolexp;

import toBeImplemented.SymbolicVariable;

/**
 * 
 * @author Lukas
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
