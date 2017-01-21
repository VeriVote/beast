/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes;

import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.propertylist.PropertyList;

/**
 *
 * @author Niels
 */
public class Project {
    private final ElectionCheckParameter params;
    private final PropertyList propList;
    private final ElectionDescription elecDescr;
    
    public Project (ElectionCheckParameter params, PropertyList propList, ElectionDescription elecDescr) {
        this.params = params;
        this.propList = propList;
        this.elecDescr = elecDescr;
    }
    public ElectionDescription getElecDescr (){
        return elecDescr;
    }
    public PropertyList getPropList() {
        return propList;
    }
    public ElectionCheckParameter getElectionCheckParameter() {
        return params;
    }
}
