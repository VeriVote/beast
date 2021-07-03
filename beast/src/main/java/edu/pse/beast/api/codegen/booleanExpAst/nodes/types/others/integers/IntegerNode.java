package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * The Class IntegerNode.
 *
 * @author Holger Klein
 */
public final class IntegerNode extends IntegerValuedExpression {
    /** The held integer. */
    private final int integer;

    /**
     * Instantiates a new integer node.
     *
     * @param heldIntValue
     *            the held int value
     */
    public IntegerNode(int integer) {
        this.integer = integer;
    }


    @Override
    public String getTreeString(final int depth) {
        return "Integer: " + integer;
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
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitIntegerExp(this);
	}
}
