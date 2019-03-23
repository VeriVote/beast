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
     * @param symbVar               the symbolic variable that this node describes
     */
    public SymbolicVarExp(InternalTypeContainer internalTypeContainer, SymbolicVariable symbVar) {
        super(internalTypeContainer);
        this.symbVar = symbVar;
    }

    /**
     *
     * @return the symbolic variable that this node describes
     */
    public SymbolicVariable getSymbolicVar() {
        return symbVar;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitSymbVarExp(this);
    }

    @Override
    public String getTreeString(int depth) {
        return "SymbVar: {id " + symbVar.getId() + ", type: "
                + symbVar.getInternalTypeContainer().getInternalType().toString() + "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SymbolicVarExp that = (SymbolicVarExp) o;
        return symbVar != null ? symbVar.equals(that.symbVar) : that.symbVar == null;
    }
}