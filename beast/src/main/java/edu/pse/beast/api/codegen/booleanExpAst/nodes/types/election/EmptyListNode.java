package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class EmptyListNode extends ElectionTypeNode {

    @Override
    public String getTreeString(final int depth) {
        return "EmptyList";
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
    }
}
