/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

/**
 *
 * @author Holger-Desktop
 */
public interface VariableListListener {
    public void addedVar(SymbolicVariable var);
    public void removedVar(SymbolicVariable var);
}
