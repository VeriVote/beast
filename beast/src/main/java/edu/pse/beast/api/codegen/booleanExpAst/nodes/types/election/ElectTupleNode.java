package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class ElectTupleNode extends ElectionTypeNode {
    private List<Integer> electNumbers = new ArrayList<>();

    @Override
    public final String getTreeString(final int depth) {
        return "Elect Tuple";
    }

    public final void addElectNumber(final int number) {
        electNumbers.add(number);
    }

    public final List<Integer> getElectNumbers() {
        return electNumbers;
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitElectTuple(this);
    }
}
