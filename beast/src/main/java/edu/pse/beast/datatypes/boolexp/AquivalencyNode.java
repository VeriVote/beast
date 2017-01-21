package edu.pse.beast.datatypes.boolexp;

/**
 * 
 * @author Lukas
 *
 */
public class AquivalencyNode extends BinaryRelationshipNode {

    /**
     * 
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public AquivalencyNode(BinaryRelationshipNode lhsExpNode, BinaryRelationshipNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

}
