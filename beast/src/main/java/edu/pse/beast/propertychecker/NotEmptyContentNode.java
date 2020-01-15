package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyContentContext;

public class NotEmptyContentNode extends BooleanExpressionNode {

    private final NotEmptyContentContext context;
    private final String votingOutput;

    public NotEmptyContentNode(final NotEmptyContentContext contentContext,
                               final String votingOutputString) {
        this.context = contentContext;
        this.votingOutput = votingOutputString;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitNotEmptyContentNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        // TODO Auto-generated method stub
        return null;
    }

    public NotEmptyContentContext getContext() {
        return context;
    }

    public String getVotingOutput() {
        return votingOutput;
    }

}
