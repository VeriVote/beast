package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class EmptyListNode extends ElectionTypeNode {

    @Override
    public final String getTreeString(final int depth) {
        return "EmptyList";
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
    }
}
