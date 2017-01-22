package edu.pse.beast.datatypes.boolexp;

import toBeImplemented.InternalTypeRep;
import toBeImplemented.SymbolicVariable;

/**
 * 
 * @author Lukas
 *
 */
public class SymbolicVarExp extends TypeExpression {

    private final SymbolicVariable symbVar;
    
    /**
     * 
     * @param internalTypeRep the type of this node
     * @param symbVar the symbolic variable that this node describes
     */
    public SymbolicVarExp(InternalTypeRep internalTypeRep, SymbolicVariable symbVar) {
        super(internalTypeRep);
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

}
