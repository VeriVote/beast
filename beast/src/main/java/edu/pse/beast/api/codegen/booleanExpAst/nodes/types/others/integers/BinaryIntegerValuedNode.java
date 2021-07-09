package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * The Class BinaryIntegerValuedNode.
 *
 * @author Holger Klein
 */
public final class BinaryIntegerValuedNode extends IntegerValuedExpression {
    /** The lhs. */
    private final IntegerValuedExpression lhs;

    /** The rhs. */
    private final IntegerValuedExpression rhs;

    /** The relation symbol. */
    private final String relationSymbol;

    /**
     * Instantiates a new binary integer valued node.
     *
     * @param lhsExpr      the lhs expr
     * @param rhsExpr      the rhs expr
     * @param relationSymb the relation symb
     */
    public BinaryIntegerValuedNode(final IntegerValuedExpression lhsExpr,
            final IntegerValuedExpression rhsExpr, final String relationSymb) {
        this.lhs = lhsExpr;
        this.rhs = rhsExpr;
        this.relationSymbol = relationSymb;
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    /**
     * Gets the relation symbol.
     *
     * @return the relation symbol
     */
    public String getRelationSymbol() {
        return relationSymbol;
    }

    public IntegerValuedExpression getLhs() {
        return lhs;
    }

    public IntegerValuedExpression getRhs() {
        return rhs;
    }

    @Override
    public void getVisited(BooleanAstVisitor visitor) {
        visitor.visitBinaryIntegerExpression(this);
    }
}
