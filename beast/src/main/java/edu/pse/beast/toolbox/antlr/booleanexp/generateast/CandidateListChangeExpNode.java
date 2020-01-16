package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;

/**
 * The Class CandidateListChangeExpNode.
 */
public class CandidateListChangeExpNode extends BooleanExpressionNode {

    /** The elect. */
    private final TerminalNode elect;

    /** The intersect exp. */
    private final IntersectExpContext intersectExp;

    /**
     * The constructor.
     *
     * @param electNode the elect node
     * @param intersectExpContext the intersect exp context
     */
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

    /**
     * Gets the elect.
     *
     * @return the elect
     */
    public TerminalNode getElect() {
        return elect;
    }

    /**
     * Gets the intersect exp.
     *
     * @return the intersect exp
     */
    public IntersectExpContext getIntersectExp() {
        return intersectExp;
    }
}
