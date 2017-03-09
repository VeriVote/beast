package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;

/**
 * Created by holger on 09.03.17.
 */
public abstract class AccessValueNode extends TypeExpression {
    private final TypeExpression[] accessingVars;
    private final int count;

    protected AccessValueNode(InternalTypeContainer internalTypeContainer,
                              TypeExpression[] accessingVars, int count) {
        super(internalTypeContainer);
        this.accessingVars = accessingVars;
        this.count = count;
    }

    public TypeExpression[] getAccessingVars() {
        return accessingVars;
    }

    public int getCount() {
        return count;
    }

}
