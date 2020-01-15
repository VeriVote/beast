package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.types.InternalTypeContainer;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class SymbolicVarExp extends TypeExpression {
    private final SymbolicVariable symbVar;

    /**
     *
     * @param internalTypeContainer the type of this node
     * @param symbolicVar           the symbolic variable that this node describes
     */
    public SymbolicVarExp(final InternalTypeContainer internalTypeContainer,
                          final SymbolicVariable symbolicVar) {
        super(internalTypeContainer);
        this.symbVar = symbolicVar;
    }

    /**
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
        return "SymbVar: {id " + symbVar.getId() + ", type: "
                + symbVar.getInternalTypeContainer().getInternalType().toString() + "}\n";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((symbVar == null)
                        ? 0 : symbVar.hashCode());
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
        SymbolicVarExp that = (SymbolicVarExp) o;
        return symbVar != null
                ? symbVar.equals(that.symbVar)
                        : that.symbVar == null;
    }
}
