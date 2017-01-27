/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Holger-Desktop
 */
public class BooleanExpScopehandler {
    private ArrayList<BooleanExpScope> currentScopes = new ArrayList<>();
    
    
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
        currentScopes.get(currentScopes.size() - 1).addTypeForId(id, type);
    }
    
    public InternalTypeContainer getTypeForVariable(String id) {
        InternalTypeContainer cont = null;
        for(int i = currentScopes.size() - 1; i >= 0; --i) {
            cont = currentScopes.get(i).getTypeForId(id);
            if(cont != null) return cont;
        }
        return cont;
    }
}
