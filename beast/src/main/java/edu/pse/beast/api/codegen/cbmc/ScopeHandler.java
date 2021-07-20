package edu.pse.beast.api.codegen.cbmc;

import java.util.Stack;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;

public class ScopeHandler {
    private Stack<Scope> scopes = new Stack<>();

    public void push() {
        scopes.push(new Scope());
    }

    public void pop() {
        scopes.pop();
    }

    public void add(final SymbolicCBMCVar var) {
        scopes.peek().add(var);
    }

    public CBMCVarType getType(final String name) {
        CBMCVarType type = null;
        for (int i = 0; i < scopes.size() && type == null; ++i) {
            type = scopes.get(scopes.size() - 1 - i).getType(name);
        }
        return type;
    }
}
