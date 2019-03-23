package edu.pse.beast.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;

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
    public abstract String[] extractResult(List<String> toExtract);

    public abstract List<CBMCResultWrapperSingleArray> readSeatList(List<String> toExtract);

    public abstract List<CBMCResultWrapperLong> readElect(List<String> toExtract);

    public abstract CodeArrayListBeautifier addMarginVerifyCheck(CodeArrayListBeautifier code);

    public abstract CodeArrayListBeautifier addVotesArrayAndInit(CodeArrayListBeautifier code, int voteNumber);

    public abstract String getCArrayType();

    /**
     * returns the code with the added line of the marginmaintest method. The method
     * has to end with an assertion that lets cbmc fail, so we can extract the
     * result
     * 
     * @param code
     * @param voteNumber
     * @return
     */
    public abstract CodeArrayListBeautifier addMarginMainTest(CodeArrayListBeautifier code, int voteNumber);

    public abstract List<String> getCodeToRunMargin(List<String> origResult, List<String> lastResult);

    public abstract List<String> getNewResult(List<String> lastFailedRun, int index);

    public abstract InternalTypeContainer getInternalTypeContainer();

    public abstract void addVerifyOutput(CodeArrayListBeautifier code);

    public abstract void addLastResultAsCode(CodeArrayListBeautifier code, List<String> origResult);

    public abstract String getResultDescriptionString(List<String> result);
}