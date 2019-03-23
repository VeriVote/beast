package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;

/**
 *
 * @author Lukas
 *
 */
public class ComparisonNode extends BooleanExpressionNode {
    protected final TypeExpression lhsTypeExp;
    protected final TypeExpression rhsTypeExp;
    protected final ComparisonSymbol comparisonSymbol;

    /**
     *
     * @param lhsTypeExp the lhsExpression
     * @param rhsTypeExp the rhsExpression
     * @param comparisonSymbol the symbol that describes this comparision (for example <, >, == )
     */
    public ComparisonNode(
            TypeExpression lhsTypeExp,
            TypeExpression rhsTypeExp,
            ComparisonSymbol comparisonSymbol) {
        this.lhsTypeExp = lhsTypeExp;
        this.rhsTypeExp = rhsTypeExp;
        this.comparisonSymbol = comparisonSymbol;
    }

    /**
     *
     * @return the symbol that describes this comparison
     */
    public ComparisonSymbol getComparisonSymbol() {
        return comparisonSymbol;
    }

    /**
     *
     * @return the LHSNode
     */
    public TypeExpression getLHSBooleanExpNode() {
        return lhsTypeExp;
    }

    /**
     *
     * @return the RHSNode
     */
    public TypeExpression getRHSBooleanExpNode() {
        return rhsTypeExp;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitComparisonNode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComparisonNode that = (ComparisonNode) o;
        if (lhsTypeExp != null ? !lhsTypeExp.equals(that.lhsTypeExp) : that.lhsTypeExp != null) return false;
        if (rhsTypeExp != null ? !rhsTypeExp.equals(that.rhsTypeExp) : that.rhsTypeExp != null) return false;
        return comparisonSymbol != null ? comparisonSymbol.equals(that.comparisonSymbol) : that.comparisonSymbol == null;
    }

    @Override
    public String getTreeString(int depth) {
        StringBuilder b = new StringBuilder();
        String tabs = "\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth);
        b.append(tabs + comparisonSymbol.getCStringRep() + "\n");
        b.append(tabs + "\t" + "lhs: " + lhsTypeExp.getTreeString(depth + 1));
        b.append(tabs + "\t" + "rhs: " + rhsTypeExp.getTreeString(depth + 1));
        return b.toString();
    }
}