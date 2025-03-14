package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election;

import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ElectTupleNode extends ElectionTypeNode {
    private static final String ELECT_TUPLE_TAG = "Elect Tuple";

    private List<Integer> electNumbers = new ArrayList<Integer>();

    @Override
    public final String getTreeString(final int depth) {
        return ELECT_TUPLE_TAG;
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
