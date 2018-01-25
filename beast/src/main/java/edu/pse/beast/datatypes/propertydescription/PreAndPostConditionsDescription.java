/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import java.util.List;

import edu.pse.beast.datatypes.NameInterface;

/**
 *
 * @author Niels
 */
public class PreAndPostConditionsDescription implements NameInterface {

    private String name;
    private SymbolicVariableList symbolicVariableList;
    private FormalPropertiesDescription preConditionsDescription;
    private FormalPropertiesDescription postConditionsDescription;

    /**
     *
     * @param name HAS to be UNIQUE in the context
     */
    public PreAndPostConditionsDescription(String name) {
        this.name = name;
        this.symbolicVariableList = new SymbolicVariableList();
        this.preConditionsDescription = new FormalPropertiesDescription("");
        this.postConditionsDescription = new FormalPropertiesDescription("");
    }

    /**
     * Creator without a SymbolicVariableList
     *
     * @param name HAS to be UNIQUE in the context
     * @param preDescr the preCondition Description of the Property
     * @param postDescr the postCondition Description of the Property
     */
    public PreAndPostConditionsDescription(String name, FormalPropertiesDescription preDescr,
            FormalPropertiesDescription postDescr) {
        this.name = name;
        this.preConditionsDescription = preDescr;
        this.postConditionsDescription = postDescr;
    }

    /**
     * Creator with a SymbolicVariableList
     *
     * @param name name of the PreAndPostConditionsDescription
     * @param preDescr The preConditionDescription
     * @param postDescr The postConditionDescription
     * @param symbolicVariableList the symbolicVariableList
     */
    public PreAndPostConditionsDescription(String name,
            FormalPropertiesDescription preDescr,
            FormalPropertiesDescription postDescr, SymbolicVariableList symbolicVariableList) {
        this.name = name;
        this.preConditionsDescription = preDescr;
        this.postConditionsDescription = postDescr;
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
    
    public List<SymbolicVariable> getSymbolicVariablesCloned() {
    	return symbolicVariableList.getSymbolicVariablesCloned();
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
     * @param symbolicVariableList the list, which is to be settled as new
     */
    public void setSymbolicVariableList(SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList;
    }

    /**
     *
     * @return the postConditionsDescription
     */
    public FormalPropertiesDescription getPostConditionsDescription() {
        return postConditionsDescription;
    }

    /**
     *
     * @return the preConditionsDescirption
     */
    public FormalPropertiesDescription getPreConditionsDescription() {
        return preConditionsDescription;
    }

    /**
     *
     * @param postConditionsDescription the new postConditionsDescription
     */
    public void setPostConditionsDescription(FormalPropertiesDescription postConditionsDescription) {
        this.postConditionsDescription = postConditionsDescription;
    }

    /**
     *
     * @param preConditionsDescription the new preConditionsDescription
     */
    public void setPreConditionsDescription(FormalPropertiesDescription preConditionsDescription) {
        this.preConditionsDescription = preConditionsDescription;
    }

    @Override
    public void setNewName(String newName) {
        this.name = newName;
    }
}
