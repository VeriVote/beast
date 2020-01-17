package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import java.util.ArrayList;

import edu.pse.beast.types.InternalTypeContainer;

/**
 * The Class BooleanExpScopehandler.
 *
 * @author Holger Klein
 */
public class BooleanExpScopehandler {

    /** The current scopes. */
    private final ArrayList<BooleanExpScope> currentScopes =
            new ArrayList<BooleanExpScope>();

    /**
     * The constructor.
     */
    public void enterNewScope() {
        currentScopes.add(new BooleanExpScope());
    }

    /**
     * Enter new scope.
     *
     * @param scope
     *            the scope
     */
    public void enterNewScope(final BooleanExpScope scope) {
        currentScopes.add(scope);
    }

    /**
     * Exit scope.
     */
    public void exitScope() {
        currentScopes.remove(currentScopes.size() - 1);
    }

    /**
     * Adds the variable.
     *
     * @param id
     *            the id
     * @param type
     *            the type
     */
    public void addVariable(final String id, final InternalTypeContainer type) {
        if (currentScopes.size() == 0) {
            enterNewScope();
        }
        currentScopes.get(currentScopes.size() - 1).addTypeForId(id, type);
    }

    /**
     * Gets the type for variable.
     *
     * @param id
     *            the id
     * @return the type for variable
     */
    public InternalTypeContainer getTypeForVariable(final String id) {
        InternalTypeContainer cont = null;
        for (int i = currentScopes.size() - 1; i >= 0; --i) {
            cont = currentScopes.get(i).getTypeForId(id);
            if (cont != null) {
                return cont;
            }
        }
        return cont;
    }

    /**
     * Removes the from top scope.
     *
     * @param id
     *            the id
     */
    public void removeFromTopScope(final String id) {
        currentScopes.get(0).remove(id);
    }
}
