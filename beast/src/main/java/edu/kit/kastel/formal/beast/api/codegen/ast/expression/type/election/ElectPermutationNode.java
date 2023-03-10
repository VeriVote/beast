package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ElectPermutationNode extends ElectionTypeNode {
    private static final String PERMUTATION_ELECT_TAG = "Permutation Elect ";

    private int electNumber;

    @Override
    public final String getTreeString(final int depth) {
        return PERMUTATION_ELECT_TAG + electNumber;
    }

    public final void setElectNumber(final int electionNumber) {
        this.electNumber = electionNumber;
    }

    public final int getElectNumber() {
        return electNumber;
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitElectPermutation(this);
    }
}
