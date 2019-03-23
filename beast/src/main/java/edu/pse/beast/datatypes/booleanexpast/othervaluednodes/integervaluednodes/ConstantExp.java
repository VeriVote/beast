package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

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

    @Override
    public String getTreeString(int depth) {
        return "const " + constant + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConstantExp that = (ConstantExp) o;
        return constant != null ? constant.equals(that.constant) : that.constant == null;
    }

    @Override
    public int hashCode() {
        return constant != null ? constant.hashCode() : 0;
    }
}