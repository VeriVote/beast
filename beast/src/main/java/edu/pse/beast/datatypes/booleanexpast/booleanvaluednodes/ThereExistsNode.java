package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 * The Class ThereExistsNode.
 *
 * @author Holger Klein
 */
public final class ThereExistsNode extends QuantifierNode {
    /**
     * Instantiates a new there exists node.
     *
     * @param declSymbVar
     *            the symbolic variable of this quantifier
     * @param followingNode
     *            the following node of this quantifier
     */
	private SymbolicCBMCVar var;
	
    public ThereExistsNode(final SymbolicVariable declSymbVar,
                           final BooleanExpressionNode followingNode) {
        super(declSymbVar, followingNode);
    }

    public ThereExistsNode(SymbolicVariable symbolicVar, BooleanExpressionNode followingNode, SymbolicCBMCVar var) {
        super(symbolicVar, followingNode);
        this.var = var;
	}

    public SymbolicCBMCVar getVar() {
		return var;
	}
    
	@Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitThereExistsNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        return "ExistsOne: Declared var: " + var.getName()
                + LINE_BREAK + TABS.substring(0, depth + 1)
                + "following: "
                + getFollowingExpNode().getTreeString(depth + 1);
    }

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitExistsCandidateNode(this);
	}
}
