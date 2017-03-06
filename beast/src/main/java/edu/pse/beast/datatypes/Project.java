/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.propertylist.Model.PLModel;

/**
 *
 * @author Niels
 */
public class Project implements NameInterface {
    private String name = "NewProject";
    private final ElectionCheckParameter params;
    private final PLModel propList;
    private final ElectionDescription elecDescr;

    /**
     * creates the project
     * @param params the params 
     * @param propList the propertyList model
     * @param elecDescr the ElectionDescription
     * @param name The Name of the Project
     */
    public Project(ElectionCheckParameter params, PLModel propList, ElectionDescription elecDescr, String name) {
        this.params = params;
        this.propList = propList;
        this.elecDescr = elecDescr;
        this.name = name;
    }

    /**
     * 
     * @return the elctionDescription
     */
    public ElectionDescription getElecDescr() {
        return elecDescr;
    }

    /**
     * 
     * @return propertyList 
     */
    public PLModel getPropList() {
        return propList;
    }

    /**
     * 
     * @return  ElectionCheckParameter
     */
    public ElectionCheckParameter getElectionCheckParameter() {
        return params;
    }

    @Override
    public void setNewName(String newName) {
        this.name = newName;
    }

    @Override
    public String getName(){
        return name;
    }
}