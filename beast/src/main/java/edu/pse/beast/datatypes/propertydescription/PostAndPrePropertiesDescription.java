/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import java.util.LinkedList;

/**
 *
 * @author Niels
 */
public class PostAndPrePropertiesDescription {

    private final String name;
    private SymbolicVariableList symbolicVariableList;
    private FormalPropertiesDescription prePropertiesDescription;
    private FormalPropertiesDescription postPropertiesDescription;

    /**
     * 
     * @param name HAS to be UNIQUE in the context
     */
    public PostAndPrePropertiesDescription(String name) {
        this.name = name;
    }

    /**
     * 
     * @param name HAS to be UNIQUE in the context
     * @param preDescr the prePropterie Description of the Property
     * @param postDescr the postPropterie Description of the Property
     */
    public PostAndPrePropertiesDescription(String name, FormalPropertiesDescription preDescr,
            FormalPropertiesDescription postDescr) {
        this.name = name;
        this.postPropertiesDescription = postDescr;
        this.prePropertiesDescription = preDescr;
    }

    public FormalPropertiesDescription getPrePropertiesDescription() {
        return prePropertiesDescription;
    }

    public FormalPropertiesDescription getPostPropertiesDescription() {
        return postPropertiesDescription;
    }

    public LinkedList<SymbolicVariable> getSymbolicVariableList() {
        return symbolicVariableList.getSymbolicVariables();
    }

    public void setSymbolicVariableList(SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList;
    }
}
