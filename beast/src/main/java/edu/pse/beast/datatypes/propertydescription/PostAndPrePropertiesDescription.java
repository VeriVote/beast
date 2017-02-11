/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.datatypes.NameInterface;

import java.util.List;

/**
 *
 * @author Niels
 */
public class PostAndPrePropertiesDescription implements NameInterface {

    private String name;
    private SymbolicVariableList symbolicVariableList;
    private FormalPropertiesDescription prePropertiesDescription;
    private FormalPropertiesDescription postPropertiesDescription;

    /**
     * 
     * @param name HAS to be UNIQUE in the context
     */
    public PostAndPrePropertiesDescription(String name) {
        this.name = name;
        this.symbolicVariableList = new SymbolicVariableList();
        this.prePropertiesDescription = new FormalPropertiesDescription("");
        this.postPropertiesDescription = new FormalPropertiesDescription("");
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
        this.prePropertiesDescription = preDescr;
        this.postPropertiesDescription = postDescr;
    }

    public PostAndPrePropertiesDescription(String name, FormalPropertiesDescription preDescr,
                                           FormalPropertiesDescription postDescr, SymbolicVariableList symbolicVariableList) {
        this.name = name;
        this.prePropertiesDescription = preDescr;
        this.postPropertiesDescription = postDescr;
        this.symbolicVariableList = symbolicVariableList;
    }

    
    public String getName() {
    	return this.name;
    }
    

    public List<SymbolicVariable> getSymbolicVariableList() {
        return symbolicVariableList.getSymbolicVariables();
    }
    public SymbolicVariableList getSymVarList() {
    	return symbolicVariableList;
    }

    public void setSymbolicVariableList(SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList;
    }

    public FormalPropertiesDescription getPostPropertiesDescription() {
        return postPropertiesDescription;
    }

    public FormalPropertiesDescription getPrePropertiesDescription() {
        return prePropertiesDescription;
    }

    public void setPostPropertiesDescription(FormalPropertiesDescription postPropertiesDescription) {
        this.postPropertiesDescription = postPropertiesDescription;
    }

    public void setPrePropertiesDescription(FormalPropertiesDescription prePropertiesDescription) {
        this.prePropertiesDescription = prePropertiesDescription;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setNewName(String newName) {
        setName(newName);
    }
}
