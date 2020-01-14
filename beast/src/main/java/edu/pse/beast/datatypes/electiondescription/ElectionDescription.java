package edu.pse.beast.datatypes.electiondescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.Tuple;
import edu.pse.beast.toolbox.Tuple3;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class ElectionDescription {
    private String name;
    private String code = "";
    private ElectionTypeContainer container;

    private int lockedLineStart = 0;
    private int lockedLineEnd = 0;
    private int lockedBracePos = 0;

    private boolean isNew = false;

    /**
     *
     * @param name
     *            the name of the description
     * @param inputType
     *            the input type
     * @param outputType
     *            the output type
     * @param votingDeclLine
     *            the votingDeclerationLine
     * @param lockedLineStart
     *            start of locked line
     * @param lockedLineEnd
     *            end of locked line
     * @param lockedBrace
     *            locked brace
     * @param isNew
     *            isNew
     */
    public ElectionDescription(String name, InputType inputType,
                               OutputType outputType, int lockedLineStart, int lockedLineEnd,
                               int lockedBrace, boolean isNew) {
        this.name = name;
        this.container = new ElectionTypeContainer(inputType, outputType);
        this.lockedLineStart = lockedLineStart;
        this.lockedLineEnd = lockedLineEnd;
        this.lockedBracePos = lockedBrace;
        this.isNew = isNew;
    }

    public ElectionDescription(String name, InputType inputType, OutputType outputType) {
        this.name = name;
        this.container = new ElectionTypeContainer(inputType, outputType);
        this.isNew = true;
    }

    /**
     *
     * @return code of this description as seen by the user The methode
     *         signature consists of the simplest possible types, eg int[]
     */
    public List<String> getSimpleCode() {
        return stringToList(code);
    }

    /**
     *
     * @return code of this description with structs used where applicable
     */
    public List<String> getComplexCode() {
        String firstPart = code.substring(0, lockedLineStart);
        boolean duplicate = true;
        String votesName = "auto_votes";
        int length = 4;
        while (duplicate) {
            if (code.contains(votesName)) {
                votesName = generateRandomString(length);
                length++; // increase the length in case all words from that
                          // length are already taken
            } else {
                duplicate = false;
            }
        }
        String replacementLine = CCodeHelper.generateStructDeclString(container, votesName);
        int dimensions = container.getInputType().getAmountOfDimensions();
        List<String> loopVariables = generateLoopVariables(dimensions, "votes");
        String[] sizes = container.getInputType().getSizeOfDimensions();
        sizes[0] = "amountVotes";
        String forLoopStart = "";
        for (int i = 0; i < dimensions; i++) { // add all needed loop headers
            forLoopStart += generateForLoopHeader(loopVariables.get(i), sizes[i]);
        }
        String forLoopEnd = "";
        for (int i = 0; i < dimensions; i++) {
            forLoopEnd += "}"; // close the for loops
        }
        String access = "";
        for (int i = 0; i < dimensions; i++) {
            access += "[" + loopVariables.get(i) + "]";
        }
        String assignment =
                UnifiedNameContainer.getVotingArray()
                + access + " = " + votesName + ".arr" + access + ";";
        String switchedArray =
                getContainer().getInputType()
                .getDataTypeAndSign() + " "
                + UnifiedNameContainer.getVotingArray()
                + getContainer().getInputType().getDimensionDescriptor(true)
                + ";" + forLoopStart + assignment + forLoopEnd;
        replacementLine += " " + switchedArray;
        String middlePart = code.substring(lockedLineEnd, lockedBracePos);
        String thirdPart = code.substring(lockedBracePos);
        // in the second part, we have to replace every return statement with a
        // loop
        // which transforms the one data type into another
        duplicate = true;
        String electName = "toReturn";
        length = 4;
        while (duplicate) {
            if (code.contains(electName)) {
                electName = generateRandomString(length);
                length++; // increase the length in case all words from that
                          // length are already taken
            } else {
                duplicate = false;
            }
        }
        middlePart = replaceReturns(middlePart, electName);
        return stringToList(firstPart + replacementLine + middlePart + thirdPart);
    }

    public String getCodeAsString() {
        return code;
    }

    /**
     *
     * @return the name of the Description
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the outputType of this description
     */
    public ElectionTypeContainer getContainer() {
        return container;
    }

    /**
     *
     * @param code
     *            of this description
     */
    public void setCode(List<String> code) {
        this.code = String.join("\n", code);
    }

    /**
     *
     * @param code
     *            of this description
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @param name
     *            of this description
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param newContainer
     *            type container of this description
     */
    public void setContainer(ElectionTypeContainer newContainer) {
        this.container = newContainer;
    }

    public ElectionDescription getDeepCopy() {
        ElectionDescription deepCopy =
                new ElectionDescription(name, container.getInputType(), container.getOutputType(),
                                        lockedLineStart, lockedLineEnd, lockedBracePos, isNew);
        deepCopy.setCode(code);
        return deepCopy;
    }

    public boolean isNew() {
        return isNew;
    }

    public int getLockedLineStart() {
        return lockedLineStart;
    }

    public int getLockedLineEnd() {
        return lockedLineEnd;
    }

    public int getLockedBracePos() {
        return lockedBracePos;
    }

    public void setNotNew() {
        this.isNew = false;
    }

    public void setLockedPositions(int lockedLineStart, int lockedLineEnd, int lockedBracePos) {
        this.lockedLineStart = lockedLineStart;
        this.lockedLineEnd = lockedLineEnd;
        this.lockedBracePos = lockedBracePos;
    }

    /**
     * splits a string at "\n"
     *
     * @param toConvert
     * @return
     */
    private List<String> stringToList(String toConvert) {
        String[] split = toConvert.split("\n");
        return new ArrayList<>(Arrays.asList(split));
    }

    /**
     * replaces all "return" statements in the voting methode with the more
     * complex data types
     *
     * @param toProcess
     *            the String from which the return statements should be
     *            replaced. The String can not be preceded by a comment symbol
     *            which would still be active for this part. It also cannot
     *            start being a text (meaning it has a single (") in front of
     *            it.
     *
     * @return
     */
    private String replaceReturns(String toProcess, String variableName) { // change this to
                                                                           // antLR maybe
        String toReturn = "";
        Pattern returnPattern = Pattern.compile("(?:\\s|^)return");
        Tuple3<Boolean, Boolean, Boolean> executionValues = new Tuple3<>(false, false, false);
        Matcher matcher = returnPattern.matcher(toProcess);
        while (matcher.find()) {
            executionValues = checkIfExecutedCode(executionValues, toProcess, 0, matcher.end());
            if (!checkForTrue(executionValues)) { // the return statement was
                                                  // NOT standing in a comment block
                Tuple<String, Integer> replacement =
                        replaceSpecificReturnStatement(toProcess.substring(matcher.end()),
                                                       variableName);
                // replacement now contains one or multiple lines which assign
                // the fitting
                // return value to "variableName"
                // at first we have to add the assignment lines, afterwards we
                // just have to add
                // "return variablename"

                // the whole segment is put in curly braces, as the user could
                // use single line
                // if cases or similar things
                String toInsert =
                        wrapInCurlyBraces(replacement.first + " return " + variableName + "; ");
                // the part which was changed
                String leadingPart = toProcess.substring(0, matcher.start()) + toInsert;
                // replacement.second contains the position after the ";",
                // therefore we have to
                // append this end part again at the end
                String trailingPart = toProcess.substring((matcher.end() + replacement.second));
                toReturn += leadingPart;
                // now that we changed the underlying string, we have to update
                // the matcher
                matcher = returnPattern.matcher(trailingPart);
                toProcess = trailingPart;
            } else { // we are in a comment, so we add the part to here and continue on
                toReturn += toProcess.substring(0, matcher.end()); // add the analysed part
                String trailingPart = toProcess.substring(matcher.end());
                // now that we changed the underlying string, we have to update
                // the matcher
                matcher = returnPattern.matcher(trailingPart);
                toProcess = trailingPart;
            }
        }
        toReturn += toProcess; // add the last parts which were not processed
        return toReturn;
    }

    /**
     *
     * @param toProcess
     *            the String to process, should start immediately after the
     *            "return";
     * @return a tuple containgin the string containing the next return
     *         statement, and the length of the previous expression after
     *         "return" up to ";"
     */
    private Tuple<String, Integer> replaceSpecificReturnStatement(String toProcess,
                                                                  String variableName) {
        Tuple3<Boolean, Boolean, Boolean> executionValues = new Tuple3<>(false, false, false);
        Pattern returnPattern = Pattern.compile(";");
        Matcher matcher = returnPattern.matcher(toProcess);

        // find the first ";" which is valid
        while (matcher.find()) {
            executionValues = checkIfExecutedCode(executionValues, toProcess, 0, matcher.end());
            if (!checkForTrue(executionValues)) { // the found ";" is valid
                String wrapped = wrapInStruct(variableName,
                        toProcess.substring(0, matcher.end() - 1)); // ignore the last char, as
                                                                    // it will be a ";"
                return new Tuple<>(wrapped, matcher.end());
            }
            // otherwise, the found ";" is not valid, so we continue
        }
        throw new RuntimeException("no fitting return found, this should not happen");
    }

    /**
     * wraps the value in the fitting struct for its datatype, and adds
     *
     * @return return + wrapper + ;
     *
     */
    private String wrapInStruct(String variableName, String valueDefinition) {
        String toReturn = container.getOutputStruct().getStructAccess() + " " + variableName + "; ";
        int dimensions = container.getOutputType().getAmountOfDimensions();
        List<String> loopVariables = generateLoopVariables(dimensions, variableName);
        String[] sizes = container.getInputType().getSizeOfDimensions();
        for (int i = 0; i < dimensions; i++) {
            toReturn = toReturn // add all needed loop headers
                    + generateForLoopHeader(loopVariables.get(i), sizes[i]);
        }
        String arrayAccess = "";
        for (int i = 0; i < dimensions; i++) {
            arrayAccess += "[" + loopVariables.get(i) + "]";
        }
        toReturn += variableName + "." + UnifiedNameContainer.getStructValueName()
                    + arrayAccess + " = " + valueDefinition + arrayAccess + ";";
        for (int i = 0; i < dimensions; i++) {
            toReturn += "}"; // close the for loops
        }
        return toReturn;
    }

    private String generateForLoopHeader(String indexName, String maxSize) {
        return "for (unsigned int " + indexName + " = 0; " + indexName + " < "
                + maxSize + "; " + indexName + "++ ) { ";
    }

    private List<String> generateLoopVariables(int dimensions, String variableName) {
        List<String> generatedVariables = new ArrayList<String>(dimensions);
        int currentIndex = 0;
        String defaultName = "loop_index_"; // use i as the default name for a loop

        for (int i = 0; i < dimensions; i++) {
            String varName = defaultName + currentIndex;
            boolean duplicate = true;
            int length = 1;
            while (duplicate) {
                if (code.contains(varName) || variableName.equals(varName)) {
                    varName = generateRandomString(length) + "_" + currentIndex;
                    length++; // increase the length in case all words from that
                              // length are already taken
                } else {
                    duplicate = false;
                }
            }
            generatedVariables.add(varName);
            currentIndex++;
        }
        return generatedVariables;
    }

    private Tuple3<Boolean, Boolean, Boolean> checkIfExecutedCode(
            Tuple3<Boolean, Boolean, Boolean> prevValues, String text,
            int start, int end) {
        boolean lineComment = prevValues.first;
        boolean multiComment = prevValues.second;
        boolean isText = prevValues.third;

        boolean lastCharSlash = false;
        boolean lastCharStar = false;

        for (int i = start; i < end; i++) {
            boolean prevLastCharSlash = lastCharSlash;
            boolean prevLastCharStar = lastCharStar;
            lastCharSlash = false;
            lastCharStar = false;
            char currentChar = text.charAt(i);
            if (isText) {
                if (currentChar == '"') {
                    isText = false;
                }
            } else if (lineComment || multiComment) { // we are in a commented area
                if (currentChar == '\n') {
                    lineComment = false; // a new line ends a line comment
                }
                if (currentChar == '/') {
                    if (prevLastCharStar) { // */ ends a comment in c no matter what
                        lineComment = false;
                        multiComment = false;
                    } else {
                        lastCharSlash = true;
                    }
                }
                if (currentChar == '*') {
                    lastCharStar = true;
                }
            } else {
                if (currentChar == '"') {
                    isText = true;
                }
                if (currentChar == '/') {
                    if (prevLastCharSlash) {
                        lineComment = true;
                    } else {
                        lastCharSlash = true;
                    }
                }
                if (currentChar == '*') {
                    if (prevLastCharSlash) {
                        multiComment = true;
                    } else {
                        lastCharStar = true;
                    }
                }
            }
        }
        return new Tuple3<Boolean, Boolean, Boolean>(lineComment, multiComment, isText);
    }

    /**
     *
     * @param toCheck
     *            the tuple to check if it contains at least one "true"
     *            statement
     * @return true, if at least one true statement is present, false otherwise
     */
    private boolean checkForTrue(Tuple3<Boolean, Boolean, Boolean> toCheck) {
        return toCheck.first || toCheck.second || toCheck.third;
    }

    /**
     * Wraps a String into curly braces. This is done so single line if
     * conditions or for loops still work when muliple lines are added
     *
     * @param toWrap
     *            the String to wrap
     * @return "{toWrap}"
     */
    private String wrapInCurlyBraces(String toWrap) {
        return "{" + toWrap + "}";
    }

    private String generateRandomString(int length) {
        return RandomStringUtils.random(length, true, false);
    }
}
