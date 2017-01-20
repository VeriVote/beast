package edu.pse.beast.datatypes.boolexp;

/**
 * 
 * @author Lukas
 *
 */
public class ComparisonNode extends BooleanExpressionNode {

    private final TypeExpression lhsTypeExp;
    private final TypeExpression rhsTypeExp;

    private ComparisonSymbol comparisonSymbol;
    
    
    /**
     * 
     * @param lhsTypeExp the lhsExpression
     * @param rhsTypeExp the rhsExpression
     */
    public ComparisonNode(TypeExpression lhsTypeExp, TypeExpression rhsTypeExp) {
        this.lhsTypeExp = lhsTypeExp;
        this.rhsTypeExp = rhsTypeExp;
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
        // TODO Auto-generated method stub
        
    }

}
