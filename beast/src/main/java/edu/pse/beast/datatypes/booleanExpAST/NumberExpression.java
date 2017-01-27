/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.booleanExpAST;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.booleanExpAST.TypeExpression;
/**
 *
 * @author Holger-Desktop
 */
public class NumberExpression extends TypeExpression{
    private final int number;
    
    public NumberExpression(int number) {
        super(new InternalTypeContainer(InternalTypeRep.INTEGER));
        this.number = number;
    }

    public int getNumber() {
        return number;
    }    

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitNumberExpNode(this);
    }
}
