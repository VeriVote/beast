/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.stringresource.StringResourceLoader;

/**
 *
 * @author Niels
 */
public class Project implements ChangeNameInterface {
    private String name;
    private final ElectionCheckParameter params;
    private final PLModel propList;
    private final ElectionDescription elecDescr;

    public Project(ElectionCheckParameter params, PLModel propList, ElectionDescription elecDescr, String name) {
        this.params = params;
        this.propList = propList;
        this.elecDescr = elecDescr;
        this.name = name;
    }

    public ElectionDescription getElecDescr() {
        return elecDescr;
    }

    public PLModel getPropList() {
        return propList;
    }

    public ElectionCheckParameter getElectionCheckParameter() {
        return params;
    }

    @Override
    public void setNewName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return name;
    }
}