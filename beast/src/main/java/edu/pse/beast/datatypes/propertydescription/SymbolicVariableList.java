/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Niels
 */
public class SymbolicVariableList {

    private final LinkedList<SymbolicVariable> symbolicVariableList;
    private List<VariableListListener> listener = new ArrayList<>();
    public SymbolicVariableList() {
        symbolicVariableList = new LinkedList<>();
    }

    public SymbolicVariableList(SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList.getSymbolicVariables();
    }
    public void addSymbolicVariable(String id, InternalTypeContainer internalTypeContainer) {
        SymbolicVariable var = new SymbolicVariable(id, internalTypeContainer);
        symbolicVariableList.add(var);
        for(VariableListListener l : listener) l.addedVar(var);
    }

    public boolean isVarIDAllowed(String id) {
        boolean varAllowed = true;
        for (SymbolicVariable var : symbolicVariableList) {
            if (var.getId().equals(id)) {
                varAllowed = false;
                break;
            }
        }
        return varAllowed;
    }

    public void setSymbolicVariableList(LinkedList<SymbolicVariable> symbolicVariableList) {
        this.symbolicVariableList.clear();
        this.symbolicVariableList.addAll(symbolicVariableList);
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
                for(VariableListListener l : listener) l.removedVar(var);
                break;
            }
        }
        return varFound;
    }

    public void removeSymbolicVariable(int index) {
        SymbolicVariable var = symbolicVariableList.get(index);
        for(VariableListListener l : listener) l.removedVar(var);
        symbolicVariableList.remove(index);
    }
    
    public void addListener(VariableListListener l) {
        this.listener.add(l);
    }
    
    public void removeListener(VariableListListener l) {
        this.listener.remove(l);
    }
}
