package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class ElectPermutationNode extends ElectionTypeNode {
    private int electNumber;

    @Override
    public String getTreeString(final int depth) {
        return "Permutation Elect " + electNumber;
    }

    public void setElectNumber(final int electionNumber) {
        this.electNumber = electionNumber;
    }

    public int getElectNumber() {
        return electNumber;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitElectPermutation(this);
    }
}
