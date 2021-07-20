package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.List;

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

    public AccessedVoteElectValueNode(final List<SymbolicCBMCVar> accessingVariables,
                                      final int numberCount) {
        this.accessingCBMCVars = accessingVariables;
        this.number = numberCount;
    }

    public List<SymbolicCBMCVar> getAccessingCBMCVars() {
        return accessingCBMCVars;
    }

    public int getNumber() {
        return number;
    }
}
