/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toBeImplemented;

/**
 *
 * @author Holger-Desktop
 */
public class InternalTypeContainer {
    private InternalTypeRep containedType;
    private boolean isList;
    private InternalTypeContainer listElem;
    
    public InternalTypeContainer(InternalTypeRep containedType) {
        this.isList = false;
        this.containedType = containedType;
    }
    
    public InternalTypeContainer(InternalTypeContainer listElem) {
        this.isList = true;
        this.listElem = listElem;
    }
}
