package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class VoteExp.
 *
 * @author Lukas Stapelbroek
 */
public final class VoteExp extends AccessedVoteElectValueNode {

	public VoteExp(List<SymbolicCBMCVar> accessingCBMCVars, String voteNumber) {
		super(accessingCBMCVars, Integer.valueOf(voteNumber));
	}

	@Override
	public String getTreeString(final int depth) {
		return null;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitVoteExpNode(this);
	}
}
