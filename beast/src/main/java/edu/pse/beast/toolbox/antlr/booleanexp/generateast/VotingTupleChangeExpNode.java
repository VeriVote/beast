package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SplitExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleContext;

/**
 * The Class VotingTupleChangeExpNode.
 *
 * @author Lukas Stapelbroek
 */
public class VotingTupleChangeExpNode extends BooleanExpressionNode {

    /** The tuple. */
    private final TupleContext tuple;

    /** The split exp. */
    private final SplitExpContext splitExp;

    /**
     * The constructor.
     *
     * @param tupleContext
     *            the tuple context
     * @param splitExpContext
     *            the split exp context
     */
    public VotingTupleChangeExpNode(final TupleContext tupleContext,
                                    final SplitExpContext splitExpContext) {
        this.tuple = tupleContext;
        this.splitExp = splitExpContext;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitVotingTupleChangeNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    /**
     * Gets the tuple.
     *
     * @return the tuple
     */
    public TupleContext getTuple() {
        return tuple;
    }

    /**
     * Gets the split exp.
     *
     * @return the split exp
     */
    public SplitExpContext getSplitExp() {
        return splitExp;
    }
}
