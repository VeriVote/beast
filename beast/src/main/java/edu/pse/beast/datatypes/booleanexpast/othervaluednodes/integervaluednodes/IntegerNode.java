package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * The Class IntegerNode.
 *
 * @author Holger Klein
 */
public class IntegerNode extends IntegerValuedExpression {

    /** The held integer. */
    private final int heldInteger;

    /**
     * Instantiates a new integer node.
     *
     * @param heldIntValue the held int value
     */
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

    /**
     * Gets the held integer.
     *
     * @return the held integer
     */
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
