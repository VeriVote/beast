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


    
}
