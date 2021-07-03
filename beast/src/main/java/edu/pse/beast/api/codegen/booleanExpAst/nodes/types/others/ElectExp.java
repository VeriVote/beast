package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * The Class ElectExp.
 *
 * @author Holger Klein
 */
public final class ElectExp extends AccessValueNode {
    /**
     * Instantiates a new elect exp.
     *
     * @param type
     *            type
     * @param accessVar
     *            accessing variable
     * @param count
     *            the count of this vote expression
     */
    public ElectExp(final TypeExpression[] accessVar,
                    final int count) {
        super(accessVar, count);
    }


    @Override
    public String getTreeString(final int depth) {
        return "ELECT" + count;
    }

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitElectExpNode(this);
	}
}
