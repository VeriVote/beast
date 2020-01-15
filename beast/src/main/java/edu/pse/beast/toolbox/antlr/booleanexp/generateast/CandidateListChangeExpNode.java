package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;

public class CandidateListChangeExpNode extends BooleanExpressionNode {
    private final TerminalNode elect;
    private final IntersectExpContext intersectExp;

    public CandidateListChangeExpNode(final TerminalNode electNode,
                                      final IntersectExpContext
                                              intersectExpContext) {
        this.elect = electNode;
        this.intersectExp = intersectExpContext;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitCandidateListChangeExpNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    public TerminalNode getElect() {
        return elect;
    }

    public IntersectExpContext getIntersectExp() {
        return intersectExp;
    }
}
