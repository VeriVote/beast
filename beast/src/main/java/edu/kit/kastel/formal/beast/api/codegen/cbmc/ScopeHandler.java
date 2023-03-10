package edu.kit.kastel.formal.beast.api.codegen.cbmc;

import java.util.Stack;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable.VariableType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ScopeHandler {
    private Stack<Scope> scopes = new Stack<Scope>();

    public final void push() {
        scopes.push(new Scope());
    }

    public final void pop() {
        scopes.pop();
    }

    public final void add(final SymbolicVariable var) {
        scopes.peek().add(var);
    }

    public final VariableType getType(final String name) {
        VariableType type = null;
        for (int i = 0; i < scopes.size() && type == null; ++i) {
            type = scopes.get(scopes.size() - 1 - i).getType(name);
        }
        return type;
    }
}
