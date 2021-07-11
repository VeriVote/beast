package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class FalseTrueNode extends BooleanExpressionNode {

    boolean falseOrTrue;

    public FalseTrueNode(boolean nodeType) {
        this.falseOrTrue = nodeType;
    }

    @Override
    public void getVisited(BooleanAstVisitor visitor) {
        visitor.visitBooleanExpFalseTrueNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        // TODO Auto-generated method stub
        return "False";
    }
    
    public boolean getFalseOrTrue() {
        return falseOrTrue;
    }
}
