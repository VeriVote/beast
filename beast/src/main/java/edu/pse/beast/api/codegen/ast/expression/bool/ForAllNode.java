package edu.pse.beast.api.codegen.ast.expression.bool;

import edu.pse.beast.api.codegen.ast.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicVariable;
import edu.pse.beast.api.codegen.cbmc.SymbolicVariable.VariableType;

/**
 * The Class ForAllNode.
 *
 * @author Holger Klein
 */
public final class ForAllNode extends QuantifierNode {

    /**
     * Instantiates a new for all node.
     *
     * @param declSymbVar   the symbolic variable of this quantifier
     * @param followingNode the following node of this quantifier
     */
    public ForAllNode(final SymbolicVariable declSymbVar,
                      final BooleanExpressionNode followingNode) {
        super(declSymbVar, followingNode);
    }

    public SymbolicVariable getVar() {
        return getDeclaredSymbolicVar();
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        if (getDeclaredSymbolicVar().getVarType() == VariableType.VOTER) {
            visitor.visitForAllVotersNode(this);
        }
    }
}
