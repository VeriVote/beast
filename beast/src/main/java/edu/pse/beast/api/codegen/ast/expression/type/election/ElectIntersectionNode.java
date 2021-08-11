package edu.pse.beast.api.codegen.ast.expression.type.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.ast.BooleanAstVisitor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ElectIntersectionNode extends ElectionTypeNode {
    private static final String INTERSECT_ELECTS_TAG = "Intersect Elects";

    private List<Integer> numbers = new ArrayList<Integer>();

    public final List<Integer> getNumbers() {
        return numbers;
    }

    @Override
    public final String getTreeString(final int depth) {
        return INTERSECT_ELECTS_TAG;
    }

    public final void addElectNumber(final int number) {
        numbers.add(number);
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitElectIntersectionNode(this);
    }
}
