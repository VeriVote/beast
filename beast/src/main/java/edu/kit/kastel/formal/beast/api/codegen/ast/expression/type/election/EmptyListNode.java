package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class EmptyListNode extends ElectionTypeNode {
    private static final String EMPTY_LIST_TAG = "EmptyList";

    @Override
    public final String getTreeString(final int depth) {
        return EMPTY_LIST_TAG;
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
    }
}
