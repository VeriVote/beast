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
     * Creator without a SymbolicVariableList
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

    /**
     * Creator with a SymbolicVariableList
     *
     * @param name name of the PostAndPrePropertiesDescription
     * @param preDescr The prePropertyDescription
     * @param postDescr The postPropertyDescription
     * @param symbolicVariableList the symbolicVariableList
     */
    public PostAndPrePropertiesDescription(String name,
            FormalPropertiesDescription preDescr,
            FormalPropertiesDescription postDescr, SymbolicVariableList symbolicVariableList) {
        this.name = name;
        this.prePropertiesDescription = preDescr;
        this.postPropertiesDescription = postDescr;
        this.symbolicVariableList = symbolicVariableList;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return the SymbolicVariableList as a List
     */
    public List<SymbolicVariable> getSymbolicVariableList() {
        return symbolicVariableList.getSymbolicVariables();
    }

    /**
     *
     * @return the symbolicVariableList as the datatype
     */
    public SymbolicVariableList getSymVarList() {
        return symbolicVariableList;
    }

    /**
     *
     * @param symbolicVariableList the list, which is to be setted as new
     */
    public void setSymbolicVariableList(SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList;
    }

    /**
     *
     * @return the postPropertiesDescription
     */
    public FormalPropertiesDescription getPostPropertiesDescription() {
        return postPropertiesDescription;
    }

    /**
     *
     * @return the prePropertiesDescirption
     */
    public FormalPropertiesDescription getPrePropertiesDescription() {
        return prePropertiesDescription;
    }

    /**
     *
     * @param postPropertiesDescription the new postPropertiesDescription
     */
    public void setPostPropertiesDescription(FormalPropertiesDescription postPropertiesDescription) {
        this.postPropertiesDescription = postPropertiesDescription;
    }

    /**
     *
     * @param prePropertiesDescription the new prePropertiesDescription
     */
    public void setPrePropertiesDescription(FormalPropertiesDescription prePropertiesDescription) {
        this.prePropertiesDescription = prePropertiesDescription;
    }

    @Override
    public void setNewName(String newName) {
        this.name = newName;
    }
}
