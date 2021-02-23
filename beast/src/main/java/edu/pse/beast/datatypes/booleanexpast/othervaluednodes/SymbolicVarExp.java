package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.api.codegen.CBMCVar;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.types.InternalTypeContainer;

/**
 * The Class SymbolicVarExp.
 *
 * @author Lukas Stapelbroek
 */
public final class SymbolicVarExp extends TypeExpression {
    /** The symb var. */
    private final SymbolicVariable symbVar;
    private CBMCVar cbmcVar;
    /**
     * Instantiates a new symbolic var exp.
     *
     * @param internalTypeContainer
     *            the type of this node
     * @param symbolicVar
     *            the symbolic variable that this node describes
     */
    public SymbolicVarExp(final InternalTypeContainer internalTypeContainer,
                          final SymbolicVariable symbolicVar) {
        super(internalTypeContainer);
        this.symbVar = symbolicVar;
    }

    public SymbolicVarExp(CBMCVar cbmcVar) {
        super(null);
        this.symbVar = null;
    	this.cbmcVar = cbmcVar;
	}
    
    public CBMCVar getCbmcVar() {
		return cbmcVar;
	}

	/**
     * Gets the symbolic var.
     *
     * @return the symbolic variable that this node describes
     */
    public SymbolicVariable getSymbolicVar() {
        return symbVar;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitSymbVarExp(this);
    }

    @Override
    public String getTreeString(final int depth) {
        return "SymbVar: {id "
                + symbVar.getId() + ", type: " + symbVar
                        .getInternalTypeContainer().getInternalType().toString()
                + "}";
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = PRIME * result + ((symbVar == null) ? 0 : symbVar.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SymbolicVarExp that = (SymbolicVarExp) o;
        return symbVar != null ? symbVar.equals(that.symbVar)
                : that.symbVar == null;
    }

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
	}
}
