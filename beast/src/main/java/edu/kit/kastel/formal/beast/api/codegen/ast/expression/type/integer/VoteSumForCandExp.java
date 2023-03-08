package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * The Class VoteSumForCandExp.
 *
 * @author Lukas Stapelbroek
 */
public final class VoteSumForCandExp extends IntegerValuedExpression {
    private SymbolicVariable candCbmcVar;
    private final int voteNumber;
    private final boolean unique;

    public VoteSumForCandExp(final int voteNum,
                             final SymbolicVariable cbmcVar,
                             final boolean uniqueAttr) {
        this.candCbmcVar = cbmcVar;
        this.voteNumber = voteNum;
        this.unique = uniqueAttr;
    }

    public SymbolicVariable getCandCbmcVar() {
        return candCbmcVar;
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    public int getVoteNumber() {
        return voteNumber;
    }

    /**
     * Checks if is unique.
     *
     * @return whether only unique candidate sums are taken into account
     */
    public boolean isUnique() {
        return unique;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitVoteSumExp(this);
    }
}
