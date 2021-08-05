package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.TypeExpression;

/**
 * The Class ComparisonNode.
 *
 * @author Lukas Stapelbroek
 */
public class ComparisonNode extends BooleanExpressionNode {

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public enum ComparisonType {
        EQ, UNEQ, L, G, LEQ, GEQ
    }

    private ComparisonType comparisonType;

    /** The lhs type exp. */
    private final TypeExpression lhsTypeExp;

    /** The rhs type exp. */
    private final TypeExpression rhsTypeExp;

    /** The comparison symbol. */
    private final String comparisonSymbol;

    /**
     * Instantiates a new comparison node.
     *
     * @param lhsTypeExpr    the lhsExpression
     * @param rhsTypeExpr    the rhsExpression
     * @param comparisonSymb the symbol that describes this comparison (for
     *                       example <, >, == )
     */
    public ComparisonNode(final TypeExpression lhsTypeExpr,
                          final TypeExpression rhsTypeExpr,
                          final String comparisonSymb) {
        this.lhsTypeExp = lhsTypeExpr;
        this.rhsTypeExp = rhsTypeExpr;
        this.comparisonSymbol = comparisonSymb;
        if ("==".equals(comparisonSymb)) {
            this.comparisonType = ComparisonType.EQ;
        } else if ("!=".equals(comparisonSymb)) {
            this.comparisonType = ComparisonType.UNEQ;
        }
    }

    private String getTreeStringExtra() {
        final String simpleClassName = this.getClass().getSimpleName();
        final String text = ": Symbol ";
        return "ComparisonNode".equals(simpleClassName)
                ? "" : simpleClassName + text;
    }

    public final ComparisonType getComparisonType() {
        return comparisonType;
    }

    public final String getComparisonSymbol() {
        return comparisonSymbol;
    }

    /**
     * Gets the lhs type exp.
     *
     * @return the lhs type exp
     */
    public final TypeExpression getLhsTypeExp() {
        return lhsTypeExp;
    }

    /**
     * Gets the rhs type exp.
     *
     * @return the rhs type exp
     */
    public final TypeExpression getRhsTypeExp() {
        return rhsTypeExp;
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitComparisonNode(this);
    }

    @Override
    public final String getTreeString(final int depth) {
        // TODO Auto-generated method stub
        return null;
    }
}
