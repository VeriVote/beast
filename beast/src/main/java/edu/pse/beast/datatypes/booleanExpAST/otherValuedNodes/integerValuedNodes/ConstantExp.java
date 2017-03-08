package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;

/**
 * 
 * @author Holger
 *
 */
public class ConstantExp extends IntegerValuedExpression {

    private final String constant;
    
    
    /**
     * @param constant the constant saved in this node
     */
    public ConstantExp(String constant) {
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
