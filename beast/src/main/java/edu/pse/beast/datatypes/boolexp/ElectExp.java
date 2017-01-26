package edu.pse.beast.datatypes.boolexp;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import toBeImplemented.InternalTypeRep;
import toBeImplemented.SymbolicVariable;

/**
 * 
 * @author Lukas
 *
 */
public class ElectExp extends TypeExpression {

    private final int count;
    private final SymbolicVariable[] accesVar;
    private final InternalTypeContainer container;
    /**
     * 
     * @param internalTypeRep the type of this node
     * @param count the count of this vote expression
     */
    public ElectExp(InternalTypeContainer container, SymbolicVariable[] accesVar, int count) {
        super(container);
        this.count = count;
        this.accesVar = accesVar;
        this.container = container;
    }
    
    /**
     * 
     * @return the count of this vote expression
     */
    public int getCount() {
        return count;
    }

    public SymbolicVariable getAccesVar() {
        return accesVar;
    }
    
    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitElectExp(this);
    }

}
