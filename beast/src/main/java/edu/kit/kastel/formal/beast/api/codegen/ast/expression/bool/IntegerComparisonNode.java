package edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool;

import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.TypeExpression;

/**
 * The Class IntegerComparisonNode.
 *
 * @author Holger Klein
 */
public final class IntegerComparisonNode extends ComparisonNode {
    /**
     * Instantiates a new integer comparison node.
     *
     * @param lhsTypeExp       the lhsExpression
     * @param rhsTypeExp       the rhsExpression
     * @param comparisonSymbol the symbol that describes this comparison (for
     *                         example <, >, == )
     */
    public IntegerComparisonNode(final TypeExpression lhsTypeExp,
            final TypeExpression rhsTypeExp, final String comparisonSymbol) {
        super(lhsTypeExp, rhsTypeExp, comparisonSymbol);
    }

}
