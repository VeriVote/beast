/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class CScopeHandler {
    private ArrayList<CScope> scopes = new ArrayList<>();
    
    public void enterScope() {
        scopes.add(new CScope());
    }
    
    public void leaveScope() {
        
    }
    
    public void addVar(String id, String type) {
        scopes.get(scopes.size() - 1).addVar(id, type);
    }
}
