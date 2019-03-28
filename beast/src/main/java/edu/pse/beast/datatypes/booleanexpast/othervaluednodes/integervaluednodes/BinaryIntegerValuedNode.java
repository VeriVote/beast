package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * @author Holger Klein
 */
public class BinaryIntegerValuedNode extends IntegerValuedExpression {

    public final IntegerValuedExpression lhs;
    public final IntegerValuedExpression rhs;
    private final String relationSymbol;

    public BinaryIntegerValuedNode(IntegerValuedExpression lhs,
                                   IntegerValuedExpression rhs,
                                   String relationSymbol) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.relationSymbol = relationSymbol;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitBinaryIntegerValuedNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        StringBuilder b = new StringBuilder();
        String tabs = "\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1);
        b.append("BinaryIntNode: Symbol " + relationSymbol + "\n");
        b.append(tabs + "lhs: " + lhs.getTreeString(depth + 1));
        b.append(tabs + "rhs: " + rhs.getTreeString(depth + 1));
        return b.toString();
    }

    public String getRelationSymbol() {
        return relationSymbol;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((lhs == null) ? 0 : lhs.hashCode());
        result = prime * result
                + ((relationSymbol == null)
                        ? 0 : relationSymbol.hashCode());
        result = prime * result
                + ((rhs == null) ? 0 : rhs.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BinaryIntegerValuedNode that = (BinaryIntegerValuedNode) o;
        if (lhs != null ? !lhs.equals(that.lhs) : that.lhs != null) {
            return false;
        }
        if (rhs != null ? !rhs.equals(that.rhs) : that.rhs != null) {
            return false;
        }
        if (relationSymbol != null
                ? !relationSymbol.equals(that.relationSymbol)
                        : that.relationSymbol != null) {
            return false;
        }
        return true;
    }
}