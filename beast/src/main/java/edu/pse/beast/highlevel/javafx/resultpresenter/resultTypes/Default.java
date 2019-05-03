package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 * We just print out the input votes, and the result
 *
 * @author Lukas Stapelbroek
 *
 */
public class Default extends ResultPresentationType {

    @Override
    public void presentResult(Result result) {
        InputType inType     = result.getElectionDescription().getContainer().getInputType();
        OutputType outType    = result.getElectionDescription().getContainer().getOutputType();    
        inType.getDimension();

        CBMCResultWrapperMultiArray votesWrapper
                = inType.extractVotesWrappedMulti(result.getResult(), result.getNumCandidates());

        String[] electedWrapper = outType.extractResult(result.getResult());
        //inType.extractVotesWrappedMulti(result, numberCandidates)(result.getResult(),
        //                                result.getNumCandidates());

        //ResultImageRenderer.
    }

    @Override
    public String getName() {
        return "Default";
    }
}
