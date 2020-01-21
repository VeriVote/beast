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
 * The Class ElectionDescription.
 *
 * @author Lukas Stapelbroek
 */
public class ElectionDescription {
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant LINE_BREAK. */
    private static final String LINE_BREAK = "\n";
    /** The Constant SEMICOLON. */
    private static final String SEMICOLON = ";";
    /** The Constant EQUALS_SIGN. */
    private static final String EQUALS_SIGN = "=";
    /** The Constant OPENING_BRACES. */
    private static final String OPENING_BRACES = "{";
    /** The Constant CLOSING_BRACES. */
    private static final String CLOSING_BRACES = "}";
    /** The Constant OPENING_BRACKET. */
    private static final String OPENING_BRACKET = "[";
    /** The Constant CLOSING_BRACKET. */
    private static final String CLOSING_BRACKET = "]";

    /** The Constant LEN. */
    private static final int LEN = 4;

    /** The name. */
    private String name;

    /** The code. */
    private String code = "";

    /** The container. */
    private ElectionTypeContainer container;

    /** The locked line start. */
    private int lockedLineStart;

    /** The locked line end. */
    private int lockedLineEnd;

    /** The locked brace pos. */
    private int lockedBracePos;

    /** The is new. */
    private boolean isNew;

    /**
     * Instantiates a new election description.
     *
     * @param nameString
     *            the name of the description
     * @param inputType
     *            the input type
     * @param outputType
     *            the output type
     * @param lockedLineStartPos
     *            start of locked line
     * @param lockedLineEndPos
     *            end of locked line
     * @param lockedBracePosition
     *            locked brace
     * @param isNewAttribute
     *            isNew
     */
    public ElectionDescription(final String nameString,
                               final InputType inputType,
                               final OutputType outputType,
                               final int lockedLineStartPos,
                               final int lockedLineEndPos,
                               final int lockedBracePosition,
                               final boolean isNewAttribute) {
        this.name = nameString;
        this.container = new ElectionTypeContainer(inputType, outputType);
        this.lockedLineStart = lockedLineStartPos;
        this.lockedLineEnd = lockedLineEndPos;
        this.lockedBracePos = lockedBracePosition;
        this.isNew = isNewAttribute;
    }

    /**
     * Instantiates a new election description.
     *
     * @param nameString
     *            the name string
     * @param inputType
     *            the input type
     * @param outputType
     *            the output type
     */
    public ElectionDescription(final String nameString,
                               final InputType inputType,
                               final OutputType outputType) {
        this.name = nameString;
        this.container = new ElectionTypeContainer(inputType, outputType);
        this.isNew = true;
    }

    /**
     * Gets the simple code.
     *
     * @return code of this description as seen by the user The methode
     *         signature consists of the simplest possible types, eg int[]
     */
    public List<String> getSimpleCode() {
        return stringToList(code);
    }

    /**
     * Generate variable name. We have to replace every return statement with
     * a loop, which transforms the one data type into another.
     *
     * @param codeString
     *            the code string
     * @param name
     *            the name
     * @return the string
     */
    private static String generateVariableName(final String codeString,
                                               final String name) {
        boolean duplicate = true;
        String nameString = name;
        for (int length = LEN; duplicate; length++) {
            if (codeString.contains(nameString)) {
                // Increase the length in case all words from that
                // length are already taken
                nameString = generateRandomString(length);
            } else {
                duplicate = false;
            }
        }
        return nameString;
    }

    /**
     * Gets the complex code.
     *
     * @return code of this description with structs used where applicable
     */
    public List<String> getComplexCode() {
        final String firstPart = code.substring(0, lockedLineStart);
        String votesName = generateVariableName(code, "auto_votes");
        final String replacementLine =
                CCodeHelper.generateStructDeclString(container, votesName);
        final int dimensions = container.getInputType().getAmountOfDimensions();
        final List<String> loopVariables =
                generateLoopVariables(code, dimensions, "votes");
        String[] sizes = container.getInputType().getSizeOfDimensions();
        sizes[0] = "amountVotes";
        String forLoopStart = "";
        for (int i = 0; i < dimensions; i++) { // Add all needed loop headers
            forLoopStart += generateForLoopHeader(loopVariables.get(i),
                                                  sizes[i]);
        }
        String forLoopEnd = "";
        for (int i = 0; i < dimensions; i++) {
            forLoopEnd += CLOSING_BRACES; // Close the for loops
        }
        String access = "";
        for (int i = 0; i < dimensions; i++) {
            access += OPENING_BRACKET + loopVariables.get(i) + CLOSING_BRACKET;
        }
        final String assignment =
                UnifiedNameContainer.getVotingArray() + access
                + BLANK + EQUALS_SIGN + BLANK + votesName + ".arr" + access + SEMICOLON;
        final String switchedArray =
                BLANK + getContainer().getInputType().getDataTypeAndSign()
                + BLANK + UnifiedNameContainer.getVotingArray()
                + getContainer().getInputType().getDimensionDescriptor(true)
                + SEMICOLON + forLoopStart + assignment + forLoopEnd;
        String middlePart = code.substring(lockedLineEnd, lockedBracePos);
        final String thirdPart = code.substring(lockedBracePos);
        middlePart = replaceReturns(middlePart,
                                    generateVariableName(code, "toReturn"));
        return stringToList(
                firstPart + replacementLine + switchedArray
                + middlePart + thirdPart);
    }

    /**
     * Gets the code as string.
     *
     * @return the code as string
     */
    public String getCodeAsString() {
        return code;
    }

    /**
     * Gets the name.
     *
     * @return the name of the Description
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the container.
     *
     * @return the outputType of this description
     */
    public ElectionTypeContainer getContainer() {
        return container;
    }

    /**
     * Sets the code.
     *
     * @param codeList
     *            of this description
     */
    public void setCode(final List<String> codeList) {
        this.code = String.join(LINE_BREAK, codeList);
    }

    /**
     * Sets the code.
     *
     * @param codeString
     *            of this description
     */
    public void setCode(final String codeString) {
        this.code = codeString;
    }

    /**
     * Sets the name.
     *
     * @param nameString
     *            of this description
     */
    public void setName(final String nameString) {
        this.name = nameString;
    }

    /**
     * Sets the container.
     *
     * @param newContainer
     *            type container of this description
     */
    public void setContainer(final ElectionTypeContainer newContainer) {
        this.container = newContainer;
    }

    /**
     * Gets the deep copy.
     *
     * @return the deep copy
     */
    public ElectionDescription getDeepCopy() {
        ElectionDescription deepCopy =
                new ElectionDescription(name, container.getInputType(),
                                        container.getOutputType(),
                                        lockedLineStart, lockedLineEnd,
                                        lockedBracePos, isNew);
        deepCopy.setCode(code);
        return deepCopy;
    }

    /**
     * Checks if is new.
     *
     * @return true, if is new
     */
    public boolean isNew() {
        return isNew;
    }

    /**
     * Gets the locked line start.
     *
     * @return the locked line start
     */
    public int getLockedLineStart() {
        return lockedLineStart;
    }

    /**
     * Gets the locked line end.
     *
     * @return the locked line end
     */
    public int getLockedLineEnd() {
        return lockedLineEnd;
    }

    /**
     * Gets the locked brace pos.
     *
     * @return the locked brace pos
     */
    public int getLockedBracePos() {
        return lockedBracePos;
    }

    /**
     * Sets the not new.
     */
    public void setNotNew() {
        this.isNew = false;
    }

    /**
     * Sets the locked positions.
     *
     * @param lockedLineStartPosition
     *            the locked line start position
     * @param lockedLineEndPosition
     *            the locked line end position
     * @param lockedBracePosition
     *            the locked brace position
     */
    public void setLockedPositions(final int lockedLineStartPosition,
                                   final int lockedLineEndPosition,
                                   final int lockedBracePosition) {
        this.lockedLineStart = lockedLineStartPosition;
        this.lockedLineEnd = lockedLineEndPosition;
        this.lockedBracePos = lockedBracePosition;
    }

    /**
     * Splits a string at "\n".
     *
     * @param toConvert
     *            the to convert
     * @return the list
     */
    private static List<String> stringToList(final String toConvert) {
        String[] split = toConvert.split(LINE_BREAK);
        return new ArrayList<String>(Arrays.asList(split));
    }

    /**
     * Replaces all "return" statements in the voting methode with the more
     * complex data types.
     *
     * @param toProcess
     *            the String from which the return statements should be
     *            replaced. The String cannot be preceded by a comment symbol
     *            which would still be active for this part. It also cannot
     *            start being a text (meaning it has a single (") in front of
     *            it.
     * @param variableName
     *            the variable name
     * @return the string
     */
    private String replaceReturns(final String toProcess,
                                  final String variableName) {
        // Change this to antLR maybe.
        String toProc = toProcess;
        String toReturn = "";
        Pattern returnPattern = Pattern.compile("(?:\\s|^)return");
        Tuple3<Boolean, Boolean, Boolean> executionValues =
                new Tuple3<Boolean, Boolean, Boolean>(false, false, false);
        Matcher matcher = returnPattern.matcher(toProc);
        while (matcher.find()) {
            executionValues =
                    checkIfExecutedCode(executionValues, toProc, 0,
                                        matcher.end());
            if (!checkForTrue(executionValues)) {
                // The return statement was NOT standing in a comment block
                Tuple<String, Integer> replacement =
                        replaceSpecificReturnStatement(
                                toProc.substring(matcher.end()),
                                variableName
                                );
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
                        wrapInCurlyBraces(
                                replacement.first() + BLANK + "return" + BLANK
                                        + variableName + SEMICOLON + BLANK
                                );
                // the part which was changed
                String leadingPart = toProc.substring(0, matcher.start())
                                    + toInsert;
                // replacement.second contains the position after the ";",
                // therefore we have to
                // append this end part again at the end
                String trailingPart =
                        toProc.substring(
                                matcher.end()
                                        + replacement.second()
                                );
                toReturn += leadingPart;
                // now that we changed the underlying string, we have to update
                // the matcher
                matcher = returnPattern.matcher(trailingPart);
                toProc = trailingPart;
            } else {
                // We are in a comment, so we add the part to here and
                // continue on
                toReturn += toProc.substring(0, matcher.end());
                // add the analysed part
                String trailingPart = toProc.substring(matcher.end());
                // now that we changed the underlying string, we have to update
                // the matcher
                matcher = returnPattern.matcher(trailingPart);
                toProc = trailingPart;
            }
        }
        toReturn += toProc; // Add the last parts which were not processed.
        return toReturn;
    }

    /**
     * Replace specific return statement.
     *
     * @param toProcess
     *            the String to process, should start immediately after the
     *            "return";
     * @param variableName
     *            the variable name
     * @return a tuple containgin the string containing the next return
     *         statement, and the length of the previous expression after
     *         "return" up to ";"
     */
    private Tuple<String, Integer>
                replaceSpecificReturnStatement(final String toProcess,
                                               final String variableName) {
        Tuple3<Boolean, Boolean, Boolean> executionValues =
                new Tuple3<Boolean, Boolean, Boolean>(false, false, false);
        Pattern returnPattern = Pattern.compile(SEMICOLON);
        Matcher matcher = returnPattern.matcher(toProcess);

        // find the first ";" which is valid
        while (matcher.find()) {
            executionValues = checkIfExecutedCode(executionValues, toProcess, 0,
                                                  matcher.end());
            if (!checkForTrue(executionValues)) { // the found ";" is valid
                String wrapped =
                        wrapInStruct(variableName,
                                     toProcess.substring(0, matcher.end() - 1));
                // ignore the last char, as it will be a ";"

                return new Tuple<String, Integer>(wrapped, matcher.end());
            }
            // otherwise, the found ";" is not valid, so we continue
        }
        throw new RuntimeException(
                "No fitting return statement found, this should not happen.");
    }

    /**
     * Wraps the value in the fitting struct for its datatype, and adds.
     *
     * @param variableName
     *            the variable name
     * @param valueDefinition
     *            the value definition
     * @return return + wrapper + ;
     */
    private String wrapInStruct(final String variableName,
                                final String valueDefinition) {
        String toReturn = container.getOutputStruct().getStructAccess() + BLANK
                            + variableName + SEMICOLON + BLANK;
        int dimensions = container.getOutputType().getAmountOfDimensions();
        List<String> loopVariables = generateLoopVariables(code,
                                                           dimensions,
                                                           variableName);
        String[] sizes = container.getInputType().getSizeOfDimensions();
        for (int i = 0; i < dimensions; i++) {
            toReturn += // add all needed loop headers
                    generateForLoopHeader(loopVariables.get(i), sizes[i]);
        }
        String arrayAccess = "";
        for (int i = 0; i < dimensions; i++) {
            arrayAccess += OPENING_BRACKET + loopVariables.get(i) + CLOSING_BRACKET;
        }
        toReturn += variableName + "."
                    + UnifiedNameContainer.getStructValueName() + arrayAccess
                    + BLANK + EQUALS_SIGN + BLANK + valueDefinition + arrayAccess + SEMICOLON;
        for (int i = 0; i < dimensions; i++) {
            toReturn += CLOSING_BRACES; // close the for loops
        }
        return toReturn;
    }

    /**
     * Generate for loop header.
     *
     * @param indexName
     *            the index name
     * @param maxSize
     *            the max size
     * @return the string
     */
    private static String generateForLoopHeader(final String indexName,
                                                final String maxSize) {
        return "for (unsigned int" + BLANK + indexName + BLANK
                + EQUALS_SIGN + BLANK + "0" + SEMICOLON + BLANK
                + indexName + BLANK + "<" + BLANK + maxSize + SEMICOLON + BLANK
                + indexName + "++ )" + BLANK + OPENING_BRACES + BLANK;
    }

    /**
     * Generate loop variables.
     *
     * @param codeString
     *            the code string
     * @param dimensions
     *            the dimensions
     * @param variableName
     *            the variable name
     * @return the list
     */
    private static List<String> generateLoopVariables(final String codeString,
                                                      final int dimensions,
                                                      final String variableName) {
        List<String> generatedVariables = new ArrayList<String>(dimensions);
        int currentIndex = 0;
        // Use i as the default name for a loop
        final String defaultName = "loop_index_";
        for (int i = 0; i < dimensions; i++) {
            String varName = defaultName + currentIndex;
            boolean duplicate = true;
            int length = 1;
            while (duplicate) {
                if (codeString.contains(varName)
                        || variableName.equals(varName)) {
                    varName = generateRandomString(length) + "_" + currentIndex;
                    // Increase the length in case all words from that
                    // length are already taken
                    length++;
                } else {
                    duplicate = false;
                }
            }
            generatedVariables.add(varName);
            currentIndex++;
        }
        return generatedVariables;
    }

    /**
     * Check if executed code.
     *
     * @param prevValues
     *            the prev values
     * @param text
     *            the text
     * @param start
     *            the start
     * @param end
     *            the end
     * @return the tuple 3
     */
    private static Tuple3<Boolean, Boolean, Boolean>
                checkIfExecutedCode(final Tuple3<Boolean, Boolean, Boolean> prevValues,
                                    final String text, final int start, final int end) {
        final char quote = '"';
        final char slash = '/';
        final char star = '*';

        boolean lineComment = prevValues.first();
        boolean multiComment = prevValues.second();
        boolean isText = prevValues.third();

        boolean lastCharSlash = false;
        boolean lastCharStar = false;

        for (int i = start; i < end; i++) {
            boolean prevLastCharSlash = lastCharSlash;
            boolean prevLastCharStar = lastCharStar;
            lastCharSlash = false;
            lastCharStar = false;
            char currentChar = text.charAt(i);
            if (isText) {
                if (currentChar == quote) {
                    isText = false;
                }
            } else if (lineComment || multiComment) {
                // we are in a commented area
                if (currentChar == LINE_BREAK.charAt(0)) {
                    lineComment = false; // a new line ends a line comment
                }
                if (currentChar == slash) {
                    if (prevLastCharStar) {
                        // */ ends a comment in c no matter what
                        lineComment = false;
                        multiComment = false;
                    } else {
                        lastCharSlash = true;
                    }
                }
                if (currentChar == star) {
                    lastCharStar = true;
                }
            } else {
                if (currentChar == quote) {
                    isText = true;
                }
                if (currentChar == slash) {
                    if (prevLastCharSlash) {
                        lineComment = true;
                    } else {
                        lastCharSlash = true;
                    }
                }
                if (currentChar == star) {
                    if (prevLastCharSlash) {
                        multiComment = true;
                    } else {
                        lastCharStar = true;
                    }
                }
            }
        }
        return new Tuple3<Boolean, Boolean, Boolean>(lineComment, multiComment,
                                                     isText);
    }

    /**
     * Check for true.
     *
     * @param toCheck
     *            the tuple to check if it contains at least one "true"
     *            statement
     * @return true, if at least one true statement is present, false otherwise
     */
    private static boolean checkForTrue(final Tuple3<Boolean, Boolean, Boolean> toCheck) {
        return toCheck.first() || toCheck.second() || toCheck.third();
    }

    /**
     * Wraps a String into curly braces. This is done so single line if
     * conditions or for loops still work when muliple lines are added
     *
     * @param toWrap
     *            the String to wrap
     * @return "{toWrap}"
     */
    private static String wrapInCurlyBraces(final String toWrap) {
        return OPENING_BRACES + toWrap + CLOSING_BRACES;
    }

    /**
     * Generate random string.
     *
     * @param length
     *            the length
     * @return the string
     */
    private static String generateRandomString(final int length) {
        return RandomStringUtils.random(length, true, false);
    }
}
