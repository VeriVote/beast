package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election;

import java.util.List;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * The Class AccessValueNode.
 *
 * @author Holger Klein
 */
public abstract class AccessedVoteElectValueNode extends ElectionTypeNode {
    /** The accessing vars. */
    private final List<SymbolicVariable> accessingVariables;

    /** The count. */
    private final int number;

    public AccessedVoteElectValueNode(final List<SymbolicVariable> accVariables,
                                      final int numberCount) {
        this.accessingVariables = accVariables;
        this.number = numberCount;
    }

    public final List<SymbolicVariable> getAccessingCBMCVars() {
        return accessingVariables;
    }

    public final int getNumber() {
        return number;
    }
}
