/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes;

/**
 * 
 * @author Niels
 */
public class InternalTypeContainer 
{
    private final boolean isList; 
    private final InternalType internalType;
    private final InternalTypeContainer listedType;
    /**
     * Constructor for a listed TypeContainer
     * @param internalType sets the InternalType
     * @param listedType sets the Type of the listelements
     */
    InternalTypeContainer(InternalType internalType, InternalTypeContainer listedType) {
        this.isList = true;
        this.internalType = internalType;
        this.listedType = listedType;
    }
    /**
     * Constructor for a TypeContainer that is NOT a List
     * @param internalType 
     */
    InternalTypeContainer(InternalType internalType) {
        this.isList = false;
        this.internalType = internalType;
        this.listedType = null;
    }
    /**
     * Returns if the TypeContainer is a list
     * @return isList
     */
    public boolean isList() {
        return isList;
    }
    /**
     * getter for internalType
     * @return returns internalType
     */
    public InternalType getInternalType() {
        return internalType;
    }
    /**
     * getter for listedType
     * If isList is false it returns null
     * @return returns listedType
     */
    public InternalTypeContainer getListedType() {
        return listedType;
    }
}
