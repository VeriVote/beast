package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;

/**
 * Created by holger on 08.03.17.
 */
public class IntegerNode extends IntegerValuedExpression {

    private final int heldInteger;

    public IntegerNode(int heldInteger) {
        this.heldInteger = heldInteger;
    }


    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitIntegerNode(this);
    }
}
