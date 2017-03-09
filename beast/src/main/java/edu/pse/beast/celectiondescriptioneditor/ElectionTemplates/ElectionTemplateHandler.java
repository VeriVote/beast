/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.ElectionTemplates;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;

import java.util.ArrayList;

/**
 * This class handles all available ElectionTypeContainer for input and output.
 * @author Holger-Desktop
 */
public class ElectionTemplateHandler {
    private final String[] inputIds = {
        "one_candidate_per_voter",
        "list_of_candidates_per_voter", 
        "list_of_yes_no_per_voter",
        "list_of_integer_vals_per_voter"
    };
    
    private final String[] resultIds = {
        "one_candidate_or_zero",
        "candidate_per_seat"
    };
    
    private final ArrayList<ElectionTypeContainer> inputTypes = new ArrayList<>();
    private final ArrayList<ElectionTypeContainer> resTypes = new ArrayList<>();
    
    
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
                        "list_of_candidate_placements_per_voter"));
        
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
                        new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                        InternalTypeRep.SEAT),
                "candidate_per_seat"));
    }    
    
    public String[] getInputIds() {
        return inputIds;
    }
    
    public String[] getOutputIds() {
        return resultIds;
    }
    
    /**
     * returns the elecitontypecontainer having the supplied id
     * @param id the id of the elecitontypecontainer to be retrieved
     * @return the electiontypecontainer if it exists, null otherwise
     */
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

    public ElectionTypeContainer getStandardInput() {
        return inputTypes.get(0);
    }


    public ElectionTypeContainer getStandardResult() {
        return resTypes.get(0);
    }
}
