package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * The Class IntegerValuedExpression.
 *
 * @author Holger Klein
 */
public abstract class IntegerValuedExpression extends TypeExpression {

    /**
     * Instantiates a new integer valued expression.
     */
    public IntegerValuedExpression() {
        super(new InternalTypeContainer(InternalTypeRep.INTEGER));
    }

}
