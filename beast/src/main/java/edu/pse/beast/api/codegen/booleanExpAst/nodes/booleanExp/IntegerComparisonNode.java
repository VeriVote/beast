package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.TypeExpression;

/**
 * The Class IntegerComparisonNode.
 *
 * @author Holger Klein
 */
public final class IntegerComparisonNode extends ComparisonNode {
    /**
     * Instantiates a new integer comparison node.
     *
     * @param lhsTypeExp
     *            the lhsExpression
     * @param rhsTypeExp
     *            the rhsExpression
     * @param comparisonSymbol
     *            the symbol that describes this comparison (for example <, >,
     *            == )
     */
    public IntegerComparisonNode(final TypeExpression lhsTypeExp,
                                 final TypeExpression rhsTypeExp,
                                 final String comparisonSymbol) {
        super(lhsTypeExp, rhsTypeExp, comparisonSymbol);
    }

  
}
