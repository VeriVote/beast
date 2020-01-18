package edu.pse.beast.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import edu.pse.beast.electionsimulator.ElectionSimulationData;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;

/**
 * The Class OutputType.
 *
 * @author Lukas Stapelbroek
 */
public abstract class OutputType extends InOutType {
    // protected CommonHelpMethods helper; TODO remove

    /**
     * The constructor.
     *
     * @param unsigned
     *            the unsigned
     * @param dataType
     *            the data type
     * @param dimensions
     *            the dimensions
     * @param sizeOfDimensions
     *            the size of dimensions
     */
    public OutputType(final boolean unsigned, final DataType dataType,
                      final int dimensions,
                      final String[] sizeOfDimensions) {
        super(unsigned, dataType, dimensions, sizeOfDimensions);
    }

    /**
     * Gets the output types.
     *
     * @return the output types
     */
    public static List<OutputType> getOutputTypes() {
        ServiceLoader<OutputType> loader = ServiceLoader.load(OutputType.class);
        List<OutputType> types = new ArrayList<OutputType>();
        for (Iterator<OutputType> iterator = loader.iterator();
                iterator.hasNext();) {
            OutputType type = iterator.next();
            types.add(type);
        }
        return types;
    }

    @Override
    public final String toString() {
        return otherToString();
    }

    // protected abstract void getHelper();

    /**
     * Gets the output I din file.
     *
     * @return the ID this output type uses in the string resources
     */
    public abstract String getOutputIDinFile();

    /**
     * Checks if is output one candidate.
     *
     * @return true, if the output is just one candidate
     */
    public abstract boolean isOutputOneCandidate();

    // /**
    // * extracts a variable with a given name from a checker output
    // * @param toExtract the raw data from which the data should be extracted
    // * @param variableMather a regex matcher of the variable to be extracted
    // (can e.g. include trailing numbers)
    // * @return a list cotaining the last state this variable was seen in, and
    // all the i
    // */
    // public List<ResultValueWrapper> readResult(List<String> toExtract, String
    // variableMatcher) {
    // return helper.extractVariable(variableMatcher, toExtract);
    // }

    /**
     * Adds the margin verify check.
     *
     * @param code
     *            the code
     * @return the code array list beautifier
     */
    public abstract CodeArrayListBeautifier addMarginVerifyCheck(CodeArrayListBeautifier code);

    /**
     * Adds the votes array and init.
     *
     * @param code
     *            the code
     * @param voteNumber
     *            the vote number
     * @return the code array list beautifier
     */
    @Deprecated
    public abstract CodeArrayListBeautifier addVotesArrayAndInit(CodeArrayListBeautifier code,
                                                                 int voteNumber);

    /**
     * Gets the c array type.
     *
     * @return the c array type
     */
    public abstract String getCArrayType();

    /**
     * returns the code with the added line of the margin main test method. The
     * method must end with an assertion that let's cbmc fail, so we can extract
     * the result.
     *
     * @param code
     *            the code
     * @param voteNumber
     *            the vote number
     * @return the beautified code
     */
    public abstract CodeArrayListBeautifier addMarginMainTest(CodeArrayListBeautifier code,
                                                              int voteNumber);

    // public List<ResultValueWrapper> extractVariable(String variableMatcher,
    // List<String> lastResult) {
    // return helper.extractVariable(variableMatcher, lastResult);
    // } TODO remove

    @Override
    public abstract InternalTypeContainer getInternalTypeContainer();

    /**
     * Adds the last result as code.
     *
     * @param code
     *            the code
     * @param origResult
     *            the orig result
     * @param origResultName
     *            the orig result name
     */
    public void addLastResultAsCode(final CodeArrayListBeautifier code,
                                    final ElectionSimulationData origResult,
                                    final String origResultName) {
        // first create the declaration of the array:
        String declaration = getContainer().getOutputStruct().getStructAccess()
                + " " + origResultName + " = {"
                + printArray((CBMCResultValueWrapper) origResult.getValues())
                + "};";
        code.add(declaration);
    }

    /**
     * Gets the result description string.
     *
     * @param result
     *            the result
     * @return the result description string
     */
    public abstract String getResultDescriptionString(List<String> result);

    @Override
    public final String getInfo() { // TODO move later on further down
        return "";
    }

    @Override
    public void addExtraCodeAtEndOfCodeInit(final CodeArrayListBeautifier code,
                                            final String valueName,
                                            final List<String> loopVariables) {
        // as this is a output type, nothing has to be done
    }
}
