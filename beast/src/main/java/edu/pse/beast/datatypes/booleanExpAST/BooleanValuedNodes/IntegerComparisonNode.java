package edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.TypeExpression;

/**
 * Created by holger on 08.03.17.
 */
public class IntegerComparisonNode extends ComparisonNode {
    public final String comparisonString;
    /**
     * @param lhsTypeExp       the lhsExpression
     * @param rhsTypeExp       the rhsExpression
     * @param comparisonSymbol the symbol that describes this comparision (for example <, >, == )
     * @param comparisonString
     */
    public IntegerComparisonNode(
            TypeExpression lhsTypeExp, TypeExpression rhsTypeExp,
            ComparisonSymbol comparisonSymbol, String comparisonString) {
        super(lhsTypeExp, rhsTypeExp, comparisonSymbol);
        this.comparisonString = comparisonString;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitIntegerComparisonNode(this);
    }
}
