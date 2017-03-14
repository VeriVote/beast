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

    @Override
    public String getTreeString(int depth) {
        return "Integer: " + heldInteger + "\n";
    }

    public int getHeldInteger() {
        return heldInteger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        IntegerNode that = (IntegerNode) o;

        return heldInteger == that.heldInteger;
    }

    @Override
    public int hashCode() {
        return heldInteger;
    }
}
