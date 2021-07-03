package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;

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
    public ForAllNode(final SymbolicCBMCVar declSymbVar,
                      final BooleanExpressionNode followingNode) {
        super(declSymbVar, followingNode);
    }


    public SymbolicCBMCVar getVar() {
		return var;
	}
    
    @Override
    public String getTreeString(final int depth) {
        return null;
    }

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		if(var.getVarType() == CBMCVarType.VOTER) {
			visitor.visitForAllVotersNode(this);
		}		
	}
}
