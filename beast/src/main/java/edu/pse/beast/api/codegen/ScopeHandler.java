package edu.pse.beast.api.codegen;

import java.util.Stack;

import edu.pse.beast.api.codegen.CBMCVar.CBMCVarType;

public class ScopeHandler {
	private Stack<Scope> scopes = new Stack<>();
	
	public void push() {
		scopes.push(new Scope());
	}
	
	public void pop() {
		scopes.pop();
	}

	public void add(CBMCVar var) {
		scopes.peek().add(var);
	}

	public CBMCVarType getType(String name) {
		CBMCVarType type = null;
		for(int i = 0; i < scopes.size() && type == null; ++i) {
			type = scopes.get(scopes.size() - 1 - i).getType(name);			
		}
		return type;
	}
}
