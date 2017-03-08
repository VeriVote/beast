package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.IntegerValuedExpression;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;

/**
 * Created by holger on 08.03.17.
 */
public class VoterByPosExp extends TypeExpression {
    private final IntegerValuedExpression passedPositionNode;

    public VoterByPosExp(IntegerValuedExpression passedPositionNode) {
        super(new InternalTypeContainer(InternalTypeRep.VOTER));
        this.passedPositionNode = passedPositionNode;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitVoterByPosNode(this);
    }

    public IntegerValuedExpression getPassedPositionNode() {
        return passedPositionNode;
    }
}
