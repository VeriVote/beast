package edu.pse.beast.celectiondescriptioneditor.electiontemplates;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.pluginhandler.TypeLoader;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 * This class handles all available ElectionTypeContainer for input and output.
 * 
 * @author Holger-Desktop
 */
public class ElectionTemplateHandler {

    private final ArrayList<ElectionTypeContainer> inputTypes = new ArrayList<>();
    private final ArrayList<ElectionTypeContainer> resTypes = new ArrayList<>();

    /**
     * creates the template handler and sets it up for usage
     */
    public ElectionTemplateHandler() {
//        inputTypes.add(new ElectionTypeContainer(
//                new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE),
//                                          InternalTypeRep.VOTER),
//                ElectionTypeContainer.ElectionInputTypeIds.SINGLE_CHOICE));
//        inputTypes.add(new ElectionTypeContainer(
//                new InternalTypeContainer(new InternalTypeContainer(
//                    new InternalTypeContainer(InternalTypeRep.INTEGER),
//                    InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER),
//                ElectionTypeContainer.ElectionInputTypeIds.PREFERENCE));
//
//        inputTypes.add(new ElectionTypeContainer(
//                new InternalTypeContainer(new InternalTypeContainer(
//                    new InternalTypeContainer(InternalTypeRep.INTEGER),
//                    InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER),
//                ElectionTypeContainer.ElectionInputTypeIds.APPROVAL));
//
//        inputTypes.add(new ElectionTypeContainer(
//                new InternalTypeContainer(new InternalTypeContainer(
//                    new InternalTypeContainer(InternalTypeRep.INTEGER),
//                        InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER),
//                ElectionTypeContainer.ElectionInputTypeIds.WEIGHTED_APPROVAL));
//
//        resTypes.add(new ElectionTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE),
//                ElectionTypeContainer.ElectionOutputTypeIds.CAND_OR_UNDEF));
//
//        resTypes.add(new ElectionTypeContainer(
//                new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.SEAT),
//                ElectionTypeContainer.ElectionOutputTypeIds.CAND_PER_SEAT));
    }

    /**
     * 
     * @return the ids that are used for the input
     */
    public List<InputType> getInputTypes() {
        return TypeLoader.getAvailableInputTypes();
    }

    /**
     * 
     * @return the ids that are used for the output
     */
    public List<OutputType> getOutputTypes() {
        return TypeLoader.getAvailableOutputTypes();
    }
//
//    /**
//     * returns the elecitontypecontainer having the supplied id
//     * 
//     * @param id the id of the elecitontypecontainer to be retrieved
//     * @return the electiontypecontainer if it exists, null otherwise
//     */
//    public ElectionTypeContainer getById(ElectionInputTypeIds id) {
//        for (ElectionTypeContainer cont : inputTypes) {
//            if (cont.getInputID() == id)
//                return cont;
//        }
//
//        return null;
//    }
//    
//    /**
//     * returns the elecitontypecontainer having the supplied id
//     * 
//     * @param id
//     *            the id of the elecitontypecontainer to be retrieved
//     * @return the electiontypecontainer if it exists, null otherwise
//     */
//    public ElectionTypeContainer getById(ElectionOutputTypeIds id) {
//        for (ElectionTypeContainer cont : resTypes) {
//            if (cont.getOutputID() == id)
//                return cont;
//        }
//        return null;
//    }

    public ElectionTypeContainer getStandardInput() {
        return inputTypes.get(0);
    }

    public ElectionTypeContainer getStandardResult() {
        return resTypes.get(0);
    }
}