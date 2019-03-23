package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 *
 * @author Holger Klein
 */
public abstract class IntegerValuedExpression extends TypeExpression {

    public IntegerValuedExpression() {
        super(new InternalTypeContainer(InternalTypeRep.INTEGER));
    }

}
