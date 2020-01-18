package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

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
    public ElectExp(final InOutType type, final TypeExpression[] accessVar,
                    final int count) {
        super(type, accessVar, count);
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitElectExp(this);
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }
}
