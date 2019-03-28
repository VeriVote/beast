package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import java.util.Arrays;

import edu.pse.beast.types.InOutType;

/**
 * @author Holger Klein
 */
public abstract class AccessValueNode extends TypeExpression {
    private final TypeExpression[] accessingVars;
    private final int count;

    protected AccessValueNode(InOutType type, TypeExpression[] accessingVars, int count) {
        super(type);
        this.accessingVars = accessingVars;
        this.count = count;
    }

    public TypeExpression[] getAccessingVars() {
        return accessingVars;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(accessingVars);
        result = prime * result + count;
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
        AccessValueNode that = (AccessValueNode) o;
        if (count != that.count) {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(accessingVars, that.accessingVars);
    }
}