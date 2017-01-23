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
    public SymbolicVariable (String id, InternalTypeContainer internalTypeRep) {
        this.id = id;
        this.internalTypeContainer = internalTypeRep;
    }
    public String getId() {
        return id;
    }
    public InternalTypeContainer getInternalTypeContainer() {
        return internalTypeContainer;
    }
}
