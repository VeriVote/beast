/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.TypeExpression;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
/**
 *
 * @author Holger-Desktop
 */
public abstract class IntegerValuedExpression extends TypeExpression {

    public IntegerValuedExpression() {
        super(new InternalTypeContainer(InternalTypeRep.INTEGER));
    }


}
