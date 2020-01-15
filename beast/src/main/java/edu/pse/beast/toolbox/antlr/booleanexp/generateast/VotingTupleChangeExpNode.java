package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SplitExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleContext;

public class VotingTupleChangeExpNode extends BooleanExpressionNode {

    private final TupleContext tuple;
    private final SplitExpContext splitExp;

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

    public TupleContext getTuple() {
        return tuple;
    }

    public SplitExpContext getSplitExp() {
        return splitExp;
    }
}
