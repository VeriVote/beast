package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * @author Holger Klein
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