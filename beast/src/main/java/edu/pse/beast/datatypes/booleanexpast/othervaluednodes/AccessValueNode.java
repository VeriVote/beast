package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import java.util.Arrays;

import edu.pse.beast.types.InOutType;

/**
 * The Class AccessValueNode.
 *
 * @author Holger Klein
 */
public abstract class AccessValueNode extends TypeExpression {
    /** The accessing vars. */
    private final TypeExpression[] accessingVars;

    /** The count. */
    private final int count;

    /**
     * Instantiates a new access value node.
     *
     * @param type
     *            the type
     * @param accessingVariables
     *            the accessing variables
     * @param countVal
     *            the count val
     */
    protected AccessValueNode(final InOutType type,
                              final TypeExpression[] accessingVariables,
                              final int countVal) {
        super(type);
        this.accessingVars = accessingVariables;
        this.count = countVal;
    }

    /**
     * Gets the accessing vars.
     *
     * @return the accessing vars
     */
    public TypeExpression[] getAccessingVars() {
        return accessingVars;
    }

    /**
     * Gets the count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    @Override
    public final int hashCode() {
        int result = super.hashCode();
        result = PRIME * result + Arrays.hashCode(accessingVars);
        result = PRIME * result + count;
        return result;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccessValueNode that = (AccessValueNode) o;
        if (count != that.count) {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(accessingVars, that.accessingVars);
    }
}
