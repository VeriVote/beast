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
    private InternalTypeRep accesType;
    
    public InternalTypeContainer(InternalTypeRep containedType) {
        this.isList = false;
        this.containedType = containedType;
    }
    
    public InternalTypeContainer(InternalTypeContainer listElem, InternalTypeRep accesType) {
        this.isList = true;
        this.listElem = listElem;
        this.accesType = accesType;
    }

    public InternalTypeRep getAccesType() {
        return accesType;
    }

    public InternalTypeRep getContainedType() {
        return containedType;
    }

    public InternalTypeContainer getListElem() {
        return listElem;
    }
    
    public boolean getIsList() {
        return isList;
    }
}
