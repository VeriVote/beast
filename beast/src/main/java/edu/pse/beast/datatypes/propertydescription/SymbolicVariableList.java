/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import java.util.LinkedList;

/**
 *
 * @author Niels
 */
public class SymbolicVariableList {

    private final LinkedList<SymbolicVariable> symbolicVariableList;

    public SymbolicVariableList() {
        symbolicVariableList = new LinkedList<>();
    }

    public void addSymbolicVariable(String id, InternalTypeContainer internalTypeContainer) {
        symbolicVariableList.add(new SymbolicVariable(id, internalTypeContainer));
    }

    public boolean isVarIDAllowed(String id) {
        boolean varAllowed = true;
        for (SymbolicVariable var : symbolicVariableList) {
            varAllowed = var.getId().equals(id);
            if (!varAllowed) {
                break;
            }
        }
        return varAllowed;
    }

    public LinkedList<SymbolicVariable> getSymbolicVariables() {
        return symbolicVariableList;
    }

    public boolean removeSymbolicVariable(String id) {
        boolean varFound = false;
        for (SymbolicVariable var : symbolicVariableList) {
            varFound = var.getId().equals(id);
            if (varFound) {
                symbolicVariableList.remove(var);
                break;
            }
        }
        return varFound;
    }
}
