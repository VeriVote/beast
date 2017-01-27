/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.internal;

/**
 *
 * @author Niels
 */
public class InternalTypeContainer {

    private final boolean isList;
    private final InternalType internalType;
    private final InternalTypeContainer listedType;
    
    /**
     * if the container is a list, say of votes, then the acces type is the type
     * of symbolic var which can be used to acess elements of the list.
     * Ex:  Every voter elects one candidate: VOTES1(v) <-- acesstype would be VOTER
     *      Every voter lists candidates by preference: VOTES1(v)(c) <--- acesstype c would be Candidate
     * This var is also used to determine the size of the list:
     * type         size
     * VOTER        V
     * CANDIDATE    C
     * SEAT         S
     */
    private final InternalType accesTypeIfList;

    /**
     * Constructor for a listed TypeContainer
     *
     * @param internalType sets the InternalType
     * @param listedType sets the Type of the listelements
     */
    InternalTypeContainer(InternalType internalType,
            InternalTypeContainer listedType,
            InternalType accesTypeIfList) {
        this.isList = true;
        this.internalType = internalType;
        this.listedType = listedType;
        this.accesTypeIfList = accesTypeIfList;
    }

    /**
     * Constructor for a TypeContainer that is NOT a List
     *
     * @param internalType the type of this election
     */
    InternalTypeContainer(InternalType internalType) {
        this.isList = false;
        this.internalType = internalType;
        this.listedType = null;
        this.accesTypeIfList = null;
    }

    /**
     * Returns if the TypeContainer is a list
     *
     * @return isList
     */
    public boolean isList() {
        return isList;
    }

    /**
     * getter for internalType
     *
     * @return returns internalType
     */
    public InternalType getInternalType() {
        return internalType;
    }

    /**
     * getter for listedType If isList is false it returns null
     *
     * @return returns listedType
     */
    public InternalTypeContainer getListedType() {
        return listedType;
    }
    

    
    public InternalType getAccesTypeIfList() {
        return accesTypeIfList;
    }
    
}
