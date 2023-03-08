package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;

/**
 * The Class IntegerNode.
 *
 * @author Holger Klein
 */
public final class IntegerNode extends IntegerValuedExpression {
    private static final String INTEGER_TAG = "Integer: ";

    /** The held integer. */
    private final int integer;

    /**
     * Instantiates a new integer node.
     *
     * @param integerValue the held integer value
     */
    public IntegerNode(final int integerValue) {
        this.integer = integerValue;
    }

    @Override
    public String getTreeString(final int depth) {
        return INTEGER_TAG + integer;
    }

    /**
     * Gets the held integer.
     *
     * @return the held integer
     */
    public int getInteger() {
        return integer;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitIntegerExp(this);
    }
}
