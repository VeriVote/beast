/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Niels
 */
public class SymbolicVariableList {

    private final LinkedList<SymbolicVariable> symbolicVariableList;
    private final List<VariableListListener> listener = new ArrayList<>();

    /**
     *
     */
    public SymbolicVariableList() {
        symbolicVariableList = new LinkedList<>();
    }

    /**
     *
     * @param symbolicVariableList
     */
    public SymbolicVariableList(SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList.getSymbolicVariables();
    }

    /**
     *
     * @param id
     * @param internalTypeContainer
     */
    public void addSymbolicVariable(String id, InternalTypeContainer internalTypeContainer) {
        SymbolicVariable var = new SymbolicVariable(id, internalTypeContainer);
        symbolicVariableList.add(var);
        for (VariableListListener l : listener) {
            l.addedVar(var);
        }
    }

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @param symbolicVariableList
     */
    public void setSymbolicVariableList(LinkedList<SymbolicVariable> symbolicVariableList) {
        this.symbolicVariableList.clear();
        this.symbolicVariableList.addAll(symbolicVariableList);
    }

    /**
     *
     * @return returns the linked List of SymbolicVariables
     */
    public LinkedList<SymbolicVariable> getSymbolicVariables() {
        return symbolicVariableList;
    }

    /**
     *
     * @param id the id of the variable, that is to be removed
     * @return returns true if the variable was found
     */
    public boolean removeSymbolicVariable(String id) {
        boolean varFound = false;
        for (SymbolicVariable var : symbolicVariableList) {
            varFound = var.getId().equals(id);
            if (varFound) {
                symbolicVariableList.remove(var);
                listener.forEach((l) -> {
                    l.removedVar(var);
                });
                break;
            }
        }
        return varFound;
    }

    /**
     *
     * @param index the index of the variable, that is to be removed
     */
    public void removeSymbolicVariable(int index) {
        if (index >= 0) {
            SymbolicVariable var = symbolicVariableList.get(index);
            listener.forEach((l) -> {
                l.removedVar(var);
            });
            symbolicVariableList.remove(index);
        }
    }

    /**
     *
     * @param listener the listener which is to add
     */
    public void addListener(VariableListListener listener) {
        this.listener.add(listener);
    }

    /**
     *
     * @param listener the listener that will be removed
     */
    public void removeListener(VariableListListener listener) {
        this.listener.remove(listener);
    }
}
