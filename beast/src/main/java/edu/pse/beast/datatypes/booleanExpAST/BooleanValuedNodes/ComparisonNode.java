package edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.TypeExpression;

/**
 * 
 * @author Lukas
 *
 */
public class ComparisonNode extends BooleanExpressionNode {

    private final TypeExpression lhsTypeExp;
    private final TypeExpression rhsTypeExp;

    private final ComparisonSymbol comparisonSymbol;
    
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

}
