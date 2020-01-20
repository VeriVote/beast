package edu.pse.beast.celectiondescriptioneditor.electiontemplates;
//TODO clean up

import java.util.ArrayList;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;

/**
 * This class handles all available ElectionTypeContainer for input and output.
 *
 * @author Holger Klein
 */
public class ElectionTemplateHandler {

    /** The input types. */
    private final ArrayList<ElectionTypeContainer> inputTypes =
            new ArrayList<ElectionTypeContainer>();

    /** The res types. */
    private final ArrayList<ElectionTypeContainer> resTypes =
            new ArrayList<ElectionTypeContainer>();

    /**
     * Creates the template handler and sets it up for usage.
     */
    public ElectionTemplateHandler() {
        // inputTypes.add(new ElectionTypeContainer(
        //         new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE),
        //                                   InternalTypeRep.VOTER),
        //         ElectionTypeContainer.ElectionInputTypeIds.SINGLE_CHOICE));
        // inputTypes.add(new ElectionTypeContainer(
        //         new InternalTypeContainer(new InternalTypeContainer(
        //                 new InternalTypeContainer(InternalTypeRep.INTEGER),
        //                 InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER),
        //         ElectionTypeContainer.ElectionInputTypeIds.PREFERENCE));
        //
        // inputTypes.add(new ElectionTypeContainer(
        //         new InternalTypeContainer(new InternalTypeContainer(
        //                 new InternalTypeContainer(InternalTypeRep.INTEGER),
        //                 InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER),
        //         ElectionTypeContainer.ElectionInputTypeIds.APPROVAL));
        //
        // inputTypes.add(new ElectionTypeContainer(
        //         new InternalTypeContainer(new InternalTypeContainer(
        //                 new InternalTypeContainer(InternalTypeRep.INTEGER),
        //                 InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER),
        //         ElectionTypeContainer.ElectionInputTypeIds.WEIGHTED_APPROVAL));
        //
        // resTypes.add(new ElectionTypeContainer(
        //         new InternalTypeContainer(InternalTypeRep.CANDIDATE),
        //         ElectionTypeContainer.ElectionOutputTypeIds.CAND_OR_UNDEF
        // ));
        //
        // resTypes.add(new ElectionTypeContainer(
        //         new InternalTypeContainer(
        //                 new InternalTypeContainer(InternalTypeRep.CANDIDATE),
        //                 InternalTypeRep.SEAT
        //         ),
        //         ElectionTypeContainer.ElectionOutputTypeIds.CAND_PER_SEAT)
        // );
    }

    // /**
    //  * Returns the election type container having the supplied id.
    //  *
    //  * @param id the id of the election type container to be retrieved
    //  * @return the election type container if it exists, null otherwise
    //  */
    // public ElectionTypeContainer getById(ElectionInputTypeIds id) {
    //     for (ElectionTypeContainer cont : inputTypes) {
    //         if (cont.getInputID() == id)
    //             return cont;
    //     }
    //     return null;
    // }

    // /**
    //  * Returns the election type container having the supplied id.
    //  *
    //  * @param id
    //  *            the id of the election type container to be retrieved
    //  * @return the election type container if it exists, null otherwise
    //  */
    // public ElectionTypeContainer getById(ElectionOutputTypeIds id) {
    //     for (ElectionTypeContainer cont : resTypes) {
    //         if (cont.getOutputID() == id)
    //             return cont;
    //     }
    //     return null;
    // }

    /**
     * Get standard input.
     * @return the first election type container in input types
     */
    public ElectionTypeContainer getStandardInput() {
        return inputTypes.get(0);
    }

    /**
     * Get standard result.
     * @return the first election type container in result types
     */
    public ElectionTypeContainer getStandardResult() {
        return resTypes.get(0);
    }
}
