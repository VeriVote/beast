/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.ElectionTemplates;

import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class ElectionTemplateHandler {
    private String[] inputIds = {
        "one_candidate_per_voter",
        "list_of_candidates_per_voter", 
        "list_of_yes_no_per_voter",
        "list_of_integer_vals_per_voter"
    };
    
    private String[] resultIds = {
        "one_candidate_or_zero",
        "list_of_integers"
    };
    
    private ArrayList<ElectionTypeContainer> inputTypes = new ArrayList<>();
    private ArrayList<ElectionTypeContainer> resTypes = new ArrayList<>();
    
    
    public ElectionTemplateHandler() {
        inputTypes.add(
                new ElectionTypeContainer(
                        new InternalTypeContainer(
                                new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER),
                        "one_candidate_per_voter"));
        inputTypes.add(
                new ElectionTypeContainer(
                        new InternalTypeContainer(
                                new InternalTypeContainer(
                                        new InternalTypeContainer(InternalTypeRep.INTEGER),
                                        InternalTypeRep.CANDIDATE 
                                ), InternalTypeRep.VOTER),
                        "list_of_candidates_per_voter"));
        
        inputTypes.add(
                new ElectionTypeContainer(
                        new InternalTypeContainer(
                                new InternalTypeContainer(
                                        new InternalTypeContainer(InternalTypeRep.APPROVAL),
                                        InternalTypeRep.CANDIDATE
                                ), InternalTypeRep.VOTER),
                        "list_of_yes_no_per_voter"));
        
        inputTypes.add(
                new ElectionTypeContainer(
                        new InternalTypeContainer(
                                new InternalTypeContainer(
                                        new InternalTypeContainer(InternalTypeRep.WEIGHTEDAPPROVAL),
                                        InternalTypeRep.CANDIDATE
                                ), InternalTypeRep.VOTER),
                        "list_of_integer_vals_per_voter"));
        
        resTypes.add(new ElectionTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                "one_candidate_or_zero"));
        
        resTypes.add(new ElectionTypeContainer(
                new InternalTypeContainer(
                        new InternalTypeContainer(InternalTypeRep.INTEGER),
                        InternalTypeRep.CANDIDATE),
                "list_of_integers"));
    }    
    
    public String[] getInputIds() {
        return inputIds;
    }
    
    public String[] getOutputTypes() {
        return resultIds;
    }
    
    public ElectionTypeContainer getById(String id) {
        for(int i = 0; i < inputIds.length; ++i) {
            if(inputIds[i].equals(id))
                return inputTypes.get(i);
        }
        
        for(int i = 0; i < resultIds.length; ++i) {
            if(resultIds[i].equals(id))
                return resTypes.get(i);        
        }
        
        return  null;
    }
}
