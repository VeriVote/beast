package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;

/**
 * Created by holger on 08.03.17.
 */
public class IntegerComparisonNode extends ComparisonNode {
    /**
     * @param lhsTypeExp       the lhsExpression
     * @param rhsTypeExp       the rhsExpression
     * @param comparisonSymbol the symbol that describes this comparision (for example <, >, == )
     */
    public IntegerComparisonNode(
            TypeExpression lhsTypeExp, TypeExpression rhsTypeExp,
            ComparisonSymbol comparisonSymbol) {
        super(lhsTypeExp, rhsTypeExp, comparisonSymbol);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitIntegerComparisonNode(this);
    }

    @Override
    public String getTreeString(int depth) {
            StringBuilder b = new StringBuilder();
            String tabs = "\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth);
            b.append(tabs + "IntegerComparisonNode: Symbol " + comparisonSymbol.getCStringRep() + "\n");
            b.append(tabs + "\t" + "lhs: " + lhsTypeExp.getTreeString(depth + 1));
            b.append(tabs + "\t" + "rhs: " + rhsTypeExp.getTreeString(depth + 1));
            return b.toString();
    }
}