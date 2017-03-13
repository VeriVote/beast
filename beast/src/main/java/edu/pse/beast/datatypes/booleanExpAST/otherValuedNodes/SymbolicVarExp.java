package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.TypeExpression;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 * 
 * @author Lukas
 *
 */
public class SymbolicVarExp extends TypeExpression {

    private final SymbolicVariable symbVar;
    
    /**
     * 
     * @param internalTypeContainer the type of this node
     * @param symbVar the symbolic variable that this node describes
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SymbolicVarExp that = (SymbolicVarExp) o;

        return symbVar != null ? symbVar.equals(that.symbVar) : that.symbVar == null;
    }
}
