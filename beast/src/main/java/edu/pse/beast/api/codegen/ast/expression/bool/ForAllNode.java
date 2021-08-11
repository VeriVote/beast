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

    private SymbolicVariable var;

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
        return var;
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        if (var.getVarType() == VariableType.VOTER) {
            visitor.visitForAllVotersNode(this);
        }
    }
}
