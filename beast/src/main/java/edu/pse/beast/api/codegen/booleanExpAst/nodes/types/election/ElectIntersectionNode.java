package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ElectIntersectionNode extends ElectionTypeNode {
    private List<Integer> numbers = new ArrayList<>();

    public final List<Integer> getNumbers() {
        return numbers;
    }

    @Override
    public final String getTreeString(final int depth) {
        return "Intersect Elects";
    }

    public final void addElectNumber(final int number) {
        numbers.add(number);
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitElectIntersectionNode(this);
    }
}
