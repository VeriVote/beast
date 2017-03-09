package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.IntegerValuedExpression;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;

import java.net.Inet6Address;

/**
 * Created by holger on 09.03.17.
 */
public class AtPosExp extends TypeExpression {

    private final IntegerValuedExpression integerValuedExpression;

    public AtPosExp(InternalTypeContainer container, IntegerValuedExpression integerValuedExpression) {
        super(container);
        this.integerValuedExpression = integerValuedExpression;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitAtPosNode(this);
    }

    public IntegerValuedExpression getIntegerValuedExpression() {
        return integerValuedExpression;
    }
}
