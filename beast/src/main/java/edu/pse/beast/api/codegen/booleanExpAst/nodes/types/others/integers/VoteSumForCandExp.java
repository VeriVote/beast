package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.TypeExpression;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * The Class VoteSumForCandExp.
 *
 * @author Lukas Stapelbroek
 */
public final class VoteSumForCandExp extends IntegerValuedExpression {
	/** The accessing var. */
	private final TypeExpression accessingVar;
	
	private SymbolicCBMCVar cbmcVar;

	/** The vote arr num. */
	private final int voteArrNum;

	/** The unique. */
	private final boolean unique;

	/**
	 * Instantiates a new vote sum for cand exp.
	 *
	 * @param voteArrayNumber
	 *            the number of the vote array
	 * @param accessingVarExpr
	 *            the accessing variable
	 * @param uniqueAttr
	 *            whether the variable is unique
	 */
	public VoteSumForCandExp(final int voteArrayNumber,
			final TypeExpression accessingVarExpr, final boolean uniqueAttr) {
		this.accessingVar = accessingVarExpr;
		this.voteArrNum = voteArrayNumber;
		this.unique = uniqueAttr;
	}

	public VoteSumForCandExp(final int voteArrayNumber,
			final SymbolicCBMCVar cbmcVar, final boolean uniqueAttr) {
		this.accessingVar = null;
		this.cbmcVar = cbmcVar;
		this.voteArrNum = voteArrayNumber;
		this.unique = uniqueAttr;
	}
	
	public SymbolicCBMCVar getCbmcVar() {
		return cbmcVar;
	}

	/**
	 * Gets the accessing variable.
	 *
	 * @return the symbolic variable of this node
	 */
	public TypeExpression getAccessingVariable() {
		return accessingVar;
	}

	@Override
	public void getVisited(final BooleanExpNodeVisitor visitor) {
		visitor.visitVoteSumExp(this, unique);
	}

	@Override
	public String getTreeString(final int depth) {
		return "Votesum" + (unique ? "Unique" : "") + " " + voteArrNum
				+ LINE_BREAK + TABS.substring(0, depth + 1) + "var "
				+ cbmcVar.getName();
	}

	/**
	 * Gets the vote number.
	 *
	 * @return the number of the vote array
	 */
	public int getVoteNumber() {
		return voteArrNum;
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
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		final VoteSumForCandExp that = (VoteSumForCandExp) o;

		if (unique != that.unique) {
			return false;
		}
		if (voteArrNum != that.voteArrNum) {
			return false;
		}
		return accessingVar != null
				? accessingVar.equals(that.accessingVar)
				: that.accessingVar == null;
	}

	@Override
	public int hashCode() {
		int result = PRIME
				+ (accessingVar != null ? accessingVar.hashCode() : 0);
		result = PRIME * result + (unique ? PRIME_TWO : PRIME_THREE);
		result = PRIME * result + voteArrNum;
		return result;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitVoteSumExp(this);
	}
}
