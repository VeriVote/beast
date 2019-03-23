package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import java.util.ArrayList;

import edu.pse.beast.types.InternalTypeContainer;

/**
 *
 * @author Holger Klein
 */
public class BooleanExpScopehandler {

    private final ArrayList<BooleanExpScope> currentScopes = new ArrayList<>();

    public void enterNewScope() {
        currentScopes.add(new BooleanExpScope());
    }

    public void enterNewScope(BooleanExpScope scope) {
        currentScopes.add(scope);
    }

    public void exitScope() {
        currentScopes.remove(currentScopes.size() - 1);
    }

    public void addVariable(String id, InternalTypeContainer type) {
        if (currentScopes.size() == 0) {
            enterNewScope();
        }
        currentScopes.get(currentScopes.size() - 1).addTypeForId(id, type);
    }

    public InternalTypeContainer getTypeForVariable(String id) {
        InternalTypeContainer cont = null;
        for (int i = currentScopes.size() - 1; i >= 0; --i) {
            cont = currentScopes.get(i).getTypeForId(id);
            if (cont != null) {
                return cont;
            }
        }
        return cont;
    }

    public void removeFromTopScope(String id) {
        currentScopes.get(0).remove(id);
    }
}
