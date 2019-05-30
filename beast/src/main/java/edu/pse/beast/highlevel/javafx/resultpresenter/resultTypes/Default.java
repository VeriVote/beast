package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import java.util.List;

import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
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
        InputType  inType   = result.getElectionDescription().getContainer().getInputType();
        OutputType outType  = result.getElectionDescription().getContainer().getOutputType();

        inType.getDimension();

       // CBMCResultWrapperMultiArray votesWrapper
       //     = inType.extractVotesWrappedMulti(result.getResult(), result.getNumCandidates());

        System.out.println("include in Default.java a way that the saved value of a variable name is used");
        List<ResultValueWrapper> votesWrapper = result.readVariableValue("vote\\d");
        
        List<ResultValueWrapper> electWrapper = result.readVariableValue("elect\\d");

        //intype.extractv

       //inType.extractVotesWrappedMulti(result, numberCandidates)(result.getResult(),
       //                                                          result.getNumCandidates());
       //ResultImageRenderer.
        
        System.out.println("test wrapper: " + votesWrapper.get(0));
    }

    @Override
    public String getName() {
        return "Default";
    }
}
