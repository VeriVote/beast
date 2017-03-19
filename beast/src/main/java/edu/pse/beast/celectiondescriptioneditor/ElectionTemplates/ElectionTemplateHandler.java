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
import java.util.Arrays;
import java.util.List;

/**
 * This class handles all available ElectionTypeContainer for input and output.
 * @author Holger-Desktop
 */
public class ElectionTemplateHandler {
    
    private final ArrayList<ElectionTypeContainer> inputTypes = new ArrayList<>();
    private final ArrayList<ElectionTypeContainer> resTypes = new ArrayList<>();
    
    
    public ElectionTemplateHandler() {
        inputTypes.add(
                new ElectionTypeContainer(
                        new InternalTypeContainer(
                                new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER),
                        ElectionTypeContainer.ElectionTypeIds.SINGLE_CHOICE));
        inputTypes.add(
                new ElectionTypeContainer(
                        new InternalTypeContainer(
                                new InternalTypeContainer(
                                        new InternalTypeContainer(InternalTypeRep.INTEGER),
                                        InternalTypeRep.CANDIDATE 
                                ), InternalTypeRep.VOTER),
                        ElectionTypeContainer.ElectionTypeIds.PREFERENCE));
        
        inputTypes.add(
                new ElectionTypeContainer(
                        new InternalTypeContainer(
                                new InternalTypeContainer(
                                        new InternalTypeContainer(InternalTypeRep.INTEGER),
                                        InternalTypeRep.CANDIDATE
                                ), InternalTypeRep.VOTER),
                        ElectionTypeContainer.ElectionTypeIds.APPROVAL));
        
        inputTypes.add(
                new ElectionTypeContainer(
                        new InternalTypeContainer(
                                new InternalTypeContainer(
                                        new InternalTypeContainer(InternalTypeRep.INTEGER),
                                        InternalTypeRep.CANDIDATE
                                ), InternalTypeRep.VOTER),
                        ElectionTypeContainer.ElectionTypeIds.WEIGHTED_APPROVAL));
        
        resTypes.add(new ElectionTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                ElectionTypeContainer.ElectionTypeIds.CAND_OR_UNDEF));
        
        resTypes.add(new ElectionTypeContainer(
                new InternalTypeContainer(
                        new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                        InternalTypeRep.SEAT),
                ElectionTypeContainer.ElectionTypeIds.CAND_PER_SEAT));
    }

    public List<ElectionTypeContainer.ElectionTypeIds> getInputIds() {
        return Arrays.asList(ElectionTypeContainer.ElectionTypeIds.SINGLE_CHOICE,
                ElectionTypeContainer.ElectionTypeIds.PREFERENCE,
                ElectionTypeContainer.ElectionTypeIds.APPROVAL,
                ElectionTypeContainer.ElectionTypeIds.WEIGHTED_APPROVAL);
    }

    public List<ElectionTypeContainer.ElectionTypeIds> getResIds() {
        return Arrays.asList(ElectionTypeContainer.ElectionTypeIds.CAND_OR_UNDEF,
                ElectionTypeContainer.ElectionTypeIds.CAND_PER_SEAT);
    }

    /**
     * returns the elecitontypecontainer having the supplied id
     * @param id the id of the elecitontypecontainer to be retrieved
     * @return the electiontypecontainer if it exists, null otherwise
     */
    public ElectionTypeContainer getById(ElectionTypeContainer.ElectionTypeIds id) {
        for (ElectionTypeContainer cont :
                inputTypes) {
            if(cont.getId() == id) return cont;
        }

        for (ElectionTypeContainer cont :
                resTypes) {
            if (cont.getId() == id) return cont;
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
