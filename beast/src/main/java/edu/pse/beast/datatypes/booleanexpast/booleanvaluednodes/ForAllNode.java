package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.api.codegen.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.SymbolicCBMCVar.CBMCVarType;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 * The Class ForAllNode.
 *
 * @author Holger Klein
 */
public final class ForAllNode extends QuantifierNode {
	
	private SymbolicCBMCVar var;
    /**
     * Instantiates a new for all node.
     *
     * @param declSymbVar
     *            the symbolic variable of this quantifier
     * @param followingNode
     *            the following node of this quantifier
     */
    public ForAllNode(final SymbolicVariable declSymbVar,
                      final BooleanExpressionNode followingNode, SymbolicCBMCVar var) {
        super(declSymbVar, followingNode);
        this.var = var;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitForAllNode(this);
    }

    public SymbolicCBMCVar getVar() {
		return var;
	}
    
    @Override
    public String getTreeString(final int depth) {
        return "ForAll: Declared var: " + var.getName()
                + LINE_BREAK + TABS.substring(0, depth + 1)
                + "following: "
                + getFollowingExpNode().getTreeString(depth + 1);
    }

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		if(var.getVarType() == CBMCVarType.VOTER) {
			visitor.visitForAllVotersNode(this);
		}		
	}
}
