package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.TypeExpression;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class AccessValueNode.
 *
 * @author Holger Klein
 */
public abstract class AccessedVoteElectValueNode extends ElectionTypeNode {
	/** The accessing vars. */
	private final List<SymbolicCBMCVar> accessingCBMCVars;

	/** The count. */
	private final int number;

	public AccessedVoteElectValueNode(List<SymbolicCBMCVar> accessingCBMCVars,
			int number) {
		this.accessingCBMCVars = accessingCBMCVars;
		this.number = number;
	}

	public List<SymbolicCBMCVar> getAccessingCBMCVars() {
		return accessingCBMCVars;
	}

	public int getNumber() {
		return number;
	}

}
