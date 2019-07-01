package edu.pse.beast.datatypes.electiondescription;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pse.beast.datatypes.NameInterface;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.Tuple;
import edu.pse.beast.toolbox.Tuple3;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class ElectionDescription implements NameInterface {
	private String name;
	private String code = "";
	private ElectionTypeContainer container;

	private int lockedLineStart = 0;
	private int lockedLineEnd = 0;
	private int lockedBracePos = 0;

	private boolean isNew = false;

	/**
	 *
	 * @param name            the name of the description
	 * @param inputType       the input type
	 * @param outputType      the output type
	 * @param votingDeclLine  the votingDeclerationLine
	 * @param lockedLineStart start of locked line
	 * @param lockedLineEnd   end of locked line
	 * @param lockedBrace     locked brace
	 * @param isNew           isNew
	 */
	public ElectionDescription(String name, InputType inputType, OutputType outputType, int lockedLineStart,
			int lockedLineEnd, int lockedBrace, boolean isNew) {
		this.name = name;
		this.container = new ElectionTypeContainer(inputType, outputType);
		this.lockedLineStart = lockedLineStart;
		this.lockedLineEnd = lockedLineEnd;
		this.lockedBracePos = lockedBrace;

		this.isNew = isNew;
	}

	/**
	 *
	 * @return code of this description as seen by the user The methode signature
	 *         consists of the simplest possible types, eg int[]
	 */
	public List<String> getSimpleCode() {
		return stringToList(code);
	}

	/**
	 * 
	 * @return code of this description with structs used where applicable
	 */
	public List<String> getComplexCode() {

		System.out
				.println("check in generated code if the boundaries are working. If not, maybe add a \"+1\" somewhere");

		String firstPart = code.substring(0, lockedLineStart);

		String replacementLine = CCodeHelper.generateStructDeclString(container);

		String secondPart = code.substring(lockedLineEnd);
		// in the second part, we have to replace every return statement with a loop
		// which transforms the one data type into another

		secondPart = replaceReturns(secondPart);

		return stringToList(firstPart + replacementLine + secondPart);
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
	 * @param code of this description
	 */
	public void setCode(List<String> code) {
		this.code = String.join("\n", code);
	}

	/**
	 *
	 * @param code of this description
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 *
	 * @param name of this description
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @param newContainer type container of this description
	 */
	public void setContainer(ElectionTypeContainer newContainer) {
		this.container = newContainer;
	}

	@Override
	public void setNewName(String newName) {
		setName(newName);
	}

	public ElectionDescription getDeepCopy() {
		ElectionDescription deepCopy = new ElectionDescription(name, container.getInputType(),
				container.getOutputType(), lockedLineStart, lockedLineEnd, lockedBracePos, isNew);
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
	 * replaces all "return" statements in the voting methode with the more complex
	 * data types
	 * 
	 * @param toProcess the String from which the return statements should be
	 *                  replaced. The String can not be preceded by a comment symbol
	 *                  which would still be active for this part. It also cannot
	 *                  start being a text (meaning it has a single (") in front of
	 *                  it.
	 * 
	 * @return
	 */
	private String replaceReturns(String toProcess) { // change this to antLR maybe

		Pattern returnPattern = Pattern.compile("\\sreturn");

		Matcher matcher = returnPattern.matcher(toProcess);

		Tuple3<Boolean, Boolean, Boolean> executionValues = new Tuple3<>(false, false, false);

		int currentPos = 0;

		while (matcher.find()) {
			executionValues = checkIfExecutedCode(executionValues, toProcess, 0, matcher.end());

			if (checkForTrue(executionValues)) { // the return statement was standing in a comment block
				break;
			} else {
				Tuple<String, Integer> replacement = replaceSpecificReturnStatement(toProcess.substring(matcher.end()));

				toProcess = toProcess.substring(matcher.end()) + replacement.first; // replace the string

				// now that we changed the underlying string, we have to update the matcher

				matcher = returnPattern.matcher(toProcess.substring(matcher.end() + replacement.second));
			}
		}

		return toProcess;
	}

	/**
	 * 
	 * @param toProcess the String to process, should start immediately after the
	 *                  "return";
	 * @return a tuple containgin the string containing the next return statement,
	 *         and the length of the new return statement
	 */
	private Tuple<String, Integer> replaceSpecificReturnStatement(String toProcess) {
		Tuple3<Boolean, Boolean, Boolean> executionValues = new Tuple3<>(false, false, false);

		Pattern returnPattern = Pattern.compile(";");

		Matcher matcher = returnPattern.matcher(toProcess);

		// find the first ";" which is valid
		while (matcher.find()) {
			executionValues = checkIfExecutedCode(executionValues, toProcess, 0, matcher.end());

			if (!checkForTrue(executionValues)) { // the found ";" is valid
				Tuple<String, Integer> wrapped = wrapInStruct(toProcess.substring(0, matcher.end()));

				Tuple<String, Integer> toReturn = new Tuple<>((wrapped.first + toProcess.substring(matcher.end())),
						wrapped.second);

				return toReturn;
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
	private Tuple<String, Integer> wrapInStruct(String valueDefinition) {
		String replacement = "TESTREPLACEMENT;";

		return new Tuple<>(replacement, replacement.length()); // TODO implement this correctly
	}

	private Tuple3<Boolean, Boolean, Boolean> checkIfExecutedCode(Tuple3<Boolean, Boolean, Boolean> prevValues,
			String text, int start, int end) {
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
			}

			if (lineComment || multiComment) { // we are in a commented area
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
	 * @param toCheck the tuple to check if it contains at least one "true"
	 *                statement
	 * @return true, if at least one true statement is present, false otherwise
	 */
	private boolean checkForTrue(Tuple3<Boolean, Boolean, Boolean> toCheck) {
		return toCheck.first || toCheck.second || toCheck.third;
	}
}