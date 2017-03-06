/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
/**
 *
 * @author Niels
 */
public class SymbolicVariable {
    private final String id;
    private final InternalTypeContainer internalTypeContainer;

    /**
     * creates a new SymbolicVariable
     * @param id the id of the new variabele
     * @param internalTypeRep the Type of the new variable
     */
    public SymbolicVariable(String id, InternalTypeContainer internalTypeRep) {
        this.id = id;
        this.internalTypeContainer = internalTypeRep;
    }
    /**
     * 
     * @return the Id of the variable 
     */
    public String getId() {
        return id;
    }
    /**
     * 
     * @return the type of the Variable 
     */
    public InternalTypeContainer getInternalTypeContainer() {
        return internalTypeContainer;
    }
}
