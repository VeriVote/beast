package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.TypeExpression;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class ElectExp.
 *
 * @author Holger Klein
 */
public final class ElectExp extends AccessedVoteElectValueNode {

	public ElectExp(List<SymbolicCBMCVar> accessingCBMCVars,
			int electionNumber) {
		super(accessingCBMCVars, electionNumber);
	}

	@Override
	public String getTreeString(final int depth) {
		return null;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitElectExpNode(this);
	}
}
