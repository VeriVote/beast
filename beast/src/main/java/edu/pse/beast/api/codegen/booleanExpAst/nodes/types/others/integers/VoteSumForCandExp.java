package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class VoteSumForCandExp.
 *
 * @author Lukas Stapelbroek
 */
public final class VoteSumForCandExp extends IntegerValuedExpression {

    private SymbolicCBMCVar candCbmcVar;

    private final int voteNumber;

    private final boolean unique;

    public VoteSumForCandExp(int voteNumber, SymbolicCBMCVar cbmcVar,
            final boolean uniqueAttr) {
        this.candCbmcVar = cbmcVar;
        this.voteNumber = voteNumber;
        this.unique = uniqueAttr;
    }

    public SymbolicCBMCVar getCandCbmcVar() {
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
    public void getVisited(BooleanAstVisitor visitor) {
        visitor.visitVoteSumExp(this);
    }
}
