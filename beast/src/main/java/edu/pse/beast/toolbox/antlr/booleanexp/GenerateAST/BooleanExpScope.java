/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import java.util.HashMap;

/**
 *
 * @author Holger-Desktop
 */
public class BooleanExpScope {
    private HashMap<String, InternalTypeContainer> typesForIdentifier = new HashMap<>();
    
    public void addTypeForId(String id, InternalTypeContainer type) {
        typesForIdentifier.put(id, type);
    }

    public InternalTypeContainer getTypeForId(String id) {
        return typesForIdentifier.get(id);
    }
}
