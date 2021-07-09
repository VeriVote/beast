package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class ElectTupleNode extends ElectionTypeNode {
    private List<Integer> electNumbers = new ArrayList<>();

    @Override
    public String getTreeString(int depth) {
        return "Elect Tuple";
    }

    public void addElectNumber(int number) {
        electNumbers.add(number);
    }

    public List<Integer> getElectNumbers() {
        return electNumbers;
    }

    @Override
    public void getVisited(BooleanAstVisitor visitor) {
        visitor.visitElectTuple(this);
    }

}
