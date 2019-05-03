package edu.pse.beast.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainers.ResultValueWrapper;

public abstract class OutputType implements InOutType {
    protected CommonHelpMethods helper;

    public OutputType() {
        getHelper();
    }

    public static List<OutputType> getOutputTypes() {
        ServiceLoader<OutputType> loader = ServiceLoader.load(OutputType.class);

        List<OutputType> types = new ArrayList<OutputType>();

        for (Iterator<OutputType> iterator = loader.iterator(); iterator.hasNext();) {
            OutputType type = (OutputType) iterator.next();
            types.add(type);
        }
        return types;
    }

    @Override
    public String toString() {
        return otherToString();
    }

    protected abstract void getHelper();

    /**
     *
     * @return A string which describes the output type of the election in C. e.g
     *         "struct result"
     */
    public abstract String getOutputString();

    /**
     *
     * @return the ID this output type uses in the string resources
     */
    public abstract String getOutputIDinFile();

    /**
     *
     * @return true, if the output is just one candidate
     */
    public abstract boolean isOutputOneCandidate();

    /**
     * extracts the result from the given list
     *
     * @param toExtract the list from which the result will be extracted
     * @return an array with the Result
     */
 //   public final String[] extractResult(List<String> toExtract) {
 //       return this.helper.extractVariable("" + UnifiedNameContainer.getNewResultName() + "", getDimension(), toExtract)
 //               .get(0).getArray();
 //   }

    public List<ResultValueWrapper> readElectionResult(List<String> toExtract) {
        return helper.extractVariable(UnifiedNameContainer.getElect(), getDimension(), toExtract);
    }

    public abstract CodeArrayListBeautifier addMarginVerifyCheck(CodeArrayListBeautifier code);

    public abstract CodeArrayListBeautifier addVotesArrayAndInit(CodeArrayListBeautifier code, int voteNumber);

    public abstract String getCArrayType();

    /**
     * returns the code with the added line of the margin main test method. The
     * method must end with an assertion that let's cbmc fail, so we can extract the
     * result.
     *
     * @param code       the code
     * @param voteNumber the vote number
     * @return the beautified code
     */
    public abstract CodeArrayListBeautifier addMarginMainTest(CodeArrayListBeautifier code, int voteNumber);

    public abstract List<String> getCodeToRunMargin(List<String> origResult, List<String> lastResult);

    public abstract List<String> getNewResult(List<String> lastFailedRun, int index);

    public abstract InternalTypeContainer getInternalTypeContainer();

    public abstract void addVerifyOutput(CodeArrayListBeautifier code);

    public abstract void addLastResultAsCode(CodeArrayListBeautifier code, List<String> origResult);

    public abstract String getResultDescriptionString(List<String> result);

    /**
     * 
     * @return the dimension of this output type
     */
    public abstract int getDimension();
}