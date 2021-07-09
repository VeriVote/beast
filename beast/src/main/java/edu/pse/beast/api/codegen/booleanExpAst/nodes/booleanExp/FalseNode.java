package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class FalseNode extends BooleanExpressionNode {
    @Override
    public void getVisited(BooleanAstVisitor visitor) {
        visitor.visitBooleanExpFalseNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        // TODO Auto-generated method stub
        return "False";
    }
}
