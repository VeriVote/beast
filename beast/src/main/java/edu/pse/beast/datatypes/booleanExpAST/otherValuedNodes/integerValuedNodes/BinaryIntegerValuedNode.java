package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;

/**
 * Created by holger on 08.03.17.
 */
public class BinaryIntegerValuedNode extends IntegerValuedExpression {

    public final IntegerValuedExpression lhs;
    public final IntegerValuedExpression rhs;
    private final String relationSymbol;

    public BinaryIntegerValuedNode(IntegerValuedExpression lhs, IntegerValuedExpression rhs, String relationSymbol) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.relationSymbol = relationSymbol;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitBinaryIntegerValuedNode(this);
    }

    public String getRelationSymbol() {
        return relationSymbol;
    }
}
