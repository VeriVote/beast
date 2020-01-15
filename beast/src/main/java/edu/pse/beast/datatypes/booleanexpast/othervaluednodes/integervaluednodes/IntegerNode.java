package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * @author Holger Klein
 */
public class IntegerNode extends IntegerValuedExpression {
    private final int heldInteger;

    public IntegerNode(final int heldIntValue) {
        this.heldInteger = heldIntValue;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitIntegerNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        return "Integer: " + heldInteger + "\n";
    }

    public int getHeldInteger() {
        return heldInteger;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        IntegerNode that = (IntegerNode) o;
        return heldInteger == that.heldInteger;
    }

    @Override
    public int hashCode() {
        return heldInteger;
    }
}
