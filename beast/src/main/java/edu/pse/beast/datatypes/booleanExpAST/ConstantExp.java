package edu.pse.beast.datatypes.booleanExpAST;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpConstant;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;

/**
 * 
 * @author Holger
 *
 */
public class ConstantExp extends TypeExpression {

    private final String constant;
    
    
    /**
     * 
     * @param internalTypeRep the type of this node
     * @param constant the constant saved in this node
     */
    public ConstantExp(InternalTypeContainer internalTypeContainer, String constant) {
        super(internalTypeContainer);
        this.constant = constant;
    }

    /**
     * 
     * @return the constant saved in this node
     */
    public String getConstant() {
        return constant;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitConstExp(this);
    }
}
