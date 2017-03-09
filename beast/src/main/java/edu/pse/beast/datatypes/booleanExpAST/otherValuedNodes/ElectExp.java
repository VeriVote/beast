package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 * 
 * @author Holger
 *
 */
public class ElectExp extends AccessValueNode {
/**
     * @param accesVar
     * @param count the count of this vote expression
     */
    public ElectExp(InternalTypeContainer container, TypeExpression[] accesVar, int count) {
        super(container, accesVar, count);
    }
    
    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitElectExp(this);
    }

}
