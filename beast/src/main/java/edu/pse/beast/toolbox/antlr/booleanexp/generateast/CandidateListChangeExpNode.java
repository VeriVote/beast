package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;

public class CandidateListChangeExpNode extends BooleanExpressionNode {
    public final TerminalNode elect;
    public final IntersectExpContext intersectExp;

    public CandidateListChangeExpNode(TerminalNode elect, IntersectExpContext intersectExp) {
        this.elect = elect;
        this.intersectExp = intersectExp;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitCandidateListChangeExpNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        System.out.println("might add treestring");
        return "";
    }
}
