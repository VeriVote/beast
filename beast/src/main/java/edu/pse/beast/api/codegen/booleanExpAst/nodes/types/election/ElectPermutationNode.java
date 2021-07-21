package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class ElectPermutationNode extends ElectionTypeNode {
    private int electNumber;

    @Override
    public final String getTreeString(final int depth) {
        return "Permutation Elect " + electNumber;
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
