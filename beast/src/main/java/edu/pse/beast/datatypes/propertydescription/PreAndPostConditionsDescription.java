/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import java.util.List;

/**
 *
 * @author Niels
 */
public class PreAndPostConditionsDescription {

    private String name;
    private final SymbolicVariableList symbolicVariableList;
    private final FormalPropertiesDescription preConditionsDescription;
    private final FormalPropertiesDescription postConditionsDescription;
    private final FormalPropertiesDescription boundedVarDescription;

    /**
     *
     * @param name HAS to be UNIQUE in the context
     */
    public PreAndPostConditionsDescription(String name) {
        this.name = name;
        this.symbolicVariableList = new SymbolicVariableList();
        this.preConditionsDescription = new FormalPropertiesDescription("");
        this.postConditionsDescription = new FormalPropertiesDescription("");
        this.boundedVarDescription = new FormalPropertiesDescription("");
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
            FormalPropertiesDescription postDescr, FormalPropertiesDescription boundedVarDescription, SymbolicVariableList symbolicVariableList) {
        this.name = name;
        this.preConditionsDescription = preDescr;
        this.postConditionsDescription = postDescr;
        this.boundedVarDescription = boundedVarDescription;
        this.symbolicVariableList = symbolicVariableList;
    }

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
    
    public FormalPropertiesDescription getBoundedVarDescription() {
    	return boundedVarDescription;
    }

	public void setName(String name) {
		this.name = name;
	}
}
