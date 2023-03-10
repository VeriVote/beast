package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election;

import java.util.List;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * The Class VoteExp.
 *
 * @author Lukas Stapelbroek
 */
public final class VoteExp extends AccessedVoteElectValueNode {

    public VoteExp(final List<SymbolicVariable> accessingVariables,
                   final int voteNumber) {
        super(accessingVariables, voteNumber);
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitVoteExpNode(this);
    }
}
