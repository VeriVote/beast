package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.RandomStringUtils;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpListNode;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.electionsimulator.ElectionSimulationData;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.FormalPropertySyntaxTreeToAstTranslator;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;
import edu.pse.beast.types.ComplexType;
import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.OutputType;

/**
 * This class creates the .c file which will be checked with CBMC. It generates
 * a main method (including the FormalProperty), important IncludingCode and the
 * votingMethod (the ElectionDescription).
 *
 * @author Niels Hanselmann
 */
public class CBMCCodeGenerator { // TODO refactor this into multiple sub classes later on
	private CodeArrayListBeautifier code;
	private final ElectionDescription electionDesc;
	private final PreAndPostConditionsDescription preAndPostCondDesc;
	private final FormalPropertySyntaxTreeToAstTranslator translator;
	private final CBMCCodeGenerationVisitor visitor;

	// this number should be the number of votes we want to perform
	private int numberOfTimesVoted;
	private int margin;
	private ElectionSimulationData origResult;

	/**
	 * After the build the code is fully generated and can be acquired by getCode();
	 *
	 * @param electionDesc                    the election decription that holds the
	 *                                        code that describes the voting method.
	 *                                        that code will be merged with the
	 *                                        generated code
	 * @param preAndPostConditionsDescription the Descriptions that will be used to
	 *                                        generate the C-Code for CBMC
	 */
	public CBMCCodeGenerator(ElectionDescription electionDesc,
			PreAndPostConditionsDescription preAndPostConditionsDescription) {

		this.translator = new FormalPropertySyntaxTreeToAstTranslator();
		this.electionDesc = electionDesc;
		this.preAndPostCondDesc = preAndPostConditionsDescription;
		code = new CodeArrayListBeautifier();
		electionDesc.getContainer();
		this.visitor = new CBMCCodeGenerationVisitor(electionDesc.getContainer());
		// new CCodeHelper();
		generateCodeCheck();

	}

	/**
	 * After the build the code is fully generated and can be acquired by getCode();
	 *
	 * @param electionDesc       the electionDecription that holds the code that
	 *                           describes the voting method. that code will be
	 *                           merged with the generated code
	 * @param preAndPostCondDesc the descriptions that will be used to generate the
	 *                           C-Code for CBMC
	 * @param margin             the margin
	 * @param origResult         the original election result
	 * @param votingData         the voting data
	 */
	public CBMCCodeGenerator(ElectionDescription electionDesc, PreAndPostConditionsDescription preAndPostCondDesc,
			int margin, ElectionSimulationData origResult, ElectionSimulationData votingData) {
		this.translator = new FormalPropertySyntaxTreeToAstTranslator();
		this.electionDesc = electionDesc;
		this.preAndPostCondDesc = preAndPostCondDesc;
		code = new CodeArrayListBeautifier();
		electionDesc.getContainer();
		this.visitor = new CBMCCodeGenerationVisitor(electionDesc.getContainer());
		// new CCodeHelper();
		this.margin = margin;
		this.origResult = origResult;
		generateCodeMargin(votingData);
	}

	/**
	 * Constructor when it will be used to get the code for a test.
	 *
	 * @param electionDesc       the electionDecription that holds the code that
	 *                           describes the voting method. that code will be
	 *                           merged with the generated code
	 * @param preAndPostCondDesc the descriptions that will be used to generate the
	 *                           C-Code for CBMC
	 * @param votingData         the voting data
	 */
	public CBMCCodeGenerator(ElectionDescription electionDesc, PreAndPostConditionsDescription preAndPostCondDesc,
			ElectionSimulationData votingData) {
		this.translator = new FormalPropertySyntaxTreeToAstTranslator();
		this.electionDesc = electionDesc;
		this.preAndPostCondDesc = preAndPostCondDesc;
		code = new CodeArrayListBeautifier();
		electionDesc.getContainer();
		this.visitor = new CBMCCodeGenerationVisitor(electionDesc.getContainer());
		// new CCodeHelper();
		generateCodeTest(votingData);
	}

	/**
	 *
	 * @return returns the generated code
	 */
	public ArrayList<String> getCode() {
		return code.getCodeArrayList();
	}

	private void generateCodeCheck() {
		code = addHeader(code);
		addVoteSumFunc(false);
		addVoteSumFunc(true);
		addOtherFunctions();
		code.add("//Code of the user");
		ArrayList<String> electionDescriptionCode = new ArrayList<>();
		electionDescriptionCode.addAll(electionDesc.getComplexCode());
		code.addList(electionDescriptionCode);
		addMainMethod();
	}

	/**
	 * We want to create code for a margin computation or just a test run.
	 *
	 * @param votingData
	 */
	private void generateCodeMargin(ElectionSimulationData votingData) {
		// add the header and the voting data
		addMarginHeaders(votingData);
		// add the code the user wrote (e.g the election function)
		code.addAll(GUIController.getController().getElectionDescription().getComplexCode());
		// add the code which defines the votes

		String origVotes = electionDesc.getContainer().getInputStruct().getStructAccess() + " "
				+ electionDesc.getContainer().getNameContainer().getOrigVotesName() + " = "
				+ getVotingResultCode((CBMCResultValueWrapper) votingData.values) + ";";

		String sizeOfVote = "" + electionDesc.getContainer().getInputType()
				.getSizesInOrder(votingData.voters, votingData.candidates, votingData.seats).get(0);
		String origVotesSize = "int ORIG_VOTES_SIZE = " + sizeOfVote + ";";

		code.add(origVotesSize);

		code.add(origVotes);
		addMarginMainCheck(margin, origResult);
	}

	/**
	 * We want to create code for a margin computation or just a test run.
	 *
	 * @param votingData
	 */
	private void generateCodeTest(ElectionSimulationData votingData) {
		// add the header and the voting data
		addMarginHeaders(votingData);
		// add the code the user wrote (e.g the election function)
		code.addAll(GUIController.getController().getElectionDescription().getComplexCode());
		// add the code which defines the votes
		String origVotes = electionDesc.getContainer().getInputStruct().getStructAccess() + " "
				+ electionDesc.getContainer().getNameContainer().getOrigVotesName() + " = "
				+ getVotingResultCode((CBMCResultValueWrapper) votingData.values) + ";";

		code.add(origVotes);

		String sizeOfVote = "" + electionDesc.getContainer().getInputType()
				.getSizesInOrder(votingData.voters, votingData.candidates, votingData.seats).get(0);
		String origVotesSize = "int ORIG_VOTES_SIZE = " + sizeOfVote + ";";

		code.add(origVotesSize);

		addMarginMainTest(); // TODO add gcc ability here
	}

	private void addMarginMainCheck(int margin, ElectionSimulationData origData) {
		// we add the margin which will get computed by the model checker
		code.add("#ifndef MARGIN\n #define MARGIN " + margin + "\n #endif");
		// we also add the original result, which is calculated by compiling the
		// program and running it

		String origResultName = electionDesc.getContainer().getNameContainer().getOrigResultName();

		electionDesc.getContainer().getOutputType().addLastResultAsCode(code, origResult, origResultName);
		// add the verify method:
		// taken and adjusted from the paper:
		// https://formal.iti.kit.edu/~beckert/pub/evoteid2016.pdf

		// add the main method
		code.add("int main() {");

		addMarginCompMethod(code, electionDesc.getContainer().getInputType(),
				electionDesc.getContainer().getOutputType(), origResultName);

		code.add("}");
	}

	private void addMarginCompMethod(CodeArrayListBeautifier code, InputType inType, OutputType outType,
			String origResultName) {

		code.add("//Verify for input");

		String voteName = UnifiedNameContainer.getNewVotesName();
		addMarginCompMethodInput(code, inType, voteName);

		code.add("//Verify for output");

		addMarginCompMethodOutput(code, outType, voteName, origResultName);

	}

	private void addMarginCompMethodInput(CodeArrayListBeautifier code, InputType inType, String voteName) {

		code.add("int total_diff = 0;");
		code.add("int pos_diff = 0;");

		String voteContainer = inType.getStruct().getStructAccess() + " " + voteName + ";";
		
		code.add(voteContainer);
		
		//addInitialisedValue(voteName, inType, electionDesc.getContainer().getInputStruct(), inType.getMinimalValue(),
		//		inType.getMaximalValue());

		//addConditionalValue(voteName, inType); //the votes had to be valid before hand

		//List<String> tmploopVars = addNestedForLoopTop(code, inType.getSizeOfDimensionsAsList(), new ArrayList<String>());
		
		//code.add(inType.setVoteValue(voteName, electionDesc.getContainer().getNameContainer().getOrigVotesName(),
		//		tmploopVars)); //set the previous votes to the new votes
		
		//addNestedForrLoopBot(code, inType.getAmountOfDimensions());

		List<String> loopVars = addNestedForLoopTop(code, inType.getSizeOfDimensionsAsList(), new ArrayList<String>());

		inType.flipVote(voteName, electionDesc.getContainer().getNameContainer().getOrigVotesName(), loopVars, code);
		
		//addConditionalValue(voteName, inType); //the votes have to be valid afterwards

		addNestedForrLoopBot(code, inType.getAmountOfDimensions());

		// no more changes than margin allows
		code.add("assume(pos_diff <= MARGIN);");
		code.add("assume(total_diff == 0);");
	}

	private void addConditionalValue(String voteName, InputType inType) {
		inType.restrictVotes(voteName, code);
	}

	private void addMarginCompMethodOutput(CodeArrayListBeautifier code, OutputType outType, String newVotesName,
			String origResultName) {
		String resultName = outType.getContainer().getNameContainer().getNewResultName();

		String resultContainer = outType.getStruct().getStructAccess() + " " + resultName;

		String resultAssignment = resultContainer + " = " + outType.getContainer().getNameContainer().getVotingMethod()
				+ "(ORIG_VOTES_SIZE," + newVotesName + ");";

		code.add(resultAssignment);

		List<String> loopVars = addNestedForLoopTop(code, outType.getSizeOfDimensionsAsList(), new ArrayList<String>());

		String newResultAcc = outType.getFullVarAccess(resultName, loopVars);

		String origResultAcc = outType.getFullVarAccess(origResultName, loopVars);

		code.add("assert(" + newResultAcc + " == " + origResultAcc + ");");

		addNestedForrLoopBot(code, loopVars.size());
	}

	/**
	 *TODO move to utility class
	 * @param code                the code beautifier it should be added to
	 * @param dimensions          the size of each dimension,
	 * @param nameOfLoopVariables an empty, new list. Every new loop variable will
	 *                            be appended
	 * @return
	 */
	public static List<String> addNestedForLoopTop(CodeArrayListBeautifier code, List<String> dimensions,
			List<String> nameOfLoopVariables) {

		if (dimensions.size() > 0) {

			String name = "loop_" + nameOfLoopVariables.size();
			name = code.getNotUsedVarName(name);

			nameOfLoopVariables.add(name);

			code.add("for (int " + name + " = 0; " + name + " < " + dimensions.get(0) + "; " + name + "++) {");
			code.addTab();
			return addNestedForLoopTop(code, dimensions.subList(1, dimensions.size()), nameOfLoopVariables);
		}

		return nameOfLoopVariables;
	}

	/**
	 * TODO move to utility class
	 * @param code
	 * @param dimensions
	 */
	public static void addNestedForrLoopBot(CodeArrayListBeautifier code, int dimensions) {
		for (int i = 0; i < dimensions; i++) {
			code.add("}");
		}
	}

	/**
	 * Add the headers CBMC needs.
	 *
	 * @param votingData
	 */
	private void addMarginHeaders(ElectionSimulationData votingData) {
		code = addHeader(code);

		code.add("#ifndef " + UnifiedNameContainer.getVoter() + "\n #define " + UnifiedNameContainer.getVoter() + " "
				+ votingData.voters + "\n #endif");
		code.add("#ifndef " + UnifiedNameContainer.getCandidate() + " \n #define " + UnifiedNameContainer.getCandidate()
				+ " " + votingData.candidates + "\n #endif");
		code.add("#ifndef " + UnifiedNameContainer.getSeats() + "\n #define " + UnifiedNameContainer.getSeats() + " "
				+ votingData.seats + "\n #endif");
	}

	private void addMarginMainTest() {
		int voteNumber = 1; // because we only have one vote, we hard-code the
							// value "one" here
		code = electionDesc.getContainer().getOutputType().addMarginMainTest(code, voteNumber);
	}

	private void addVoteSumFunc(boolean unique) {
		String input = electionDesc.getContainer().getInputStruct().getStructAccess() + " tmp_struct";

		code.add("unsigned int voteSumForCandidate" + (unique ? "Unique" : "")
				+ "(INPUT, unsigned int amountVotes, unsigned int candidate) {".replace("INPUT", input));

		int dimensions = electionDesc.getContainer().getInputType().getAmountOfDimensions();

		String[] sizes = electionDesc.getContainer().getInputType().getSizeOfDimensions();

		sizes[0] = "amountVotes";

		String forLoopStart = "";

		List<String> loopVariables = generateLoopVariables(dimensions, "arr");

		for (int i = 0; i < dimensions; i++) {
			forLoopStart = forLoopStart // add all needed loop headers
					+ generateForLoopHeader(loopVariables.get(i), sizes[i]);
		}

		String forLoopEnd = "";

		for (int i = 0; i < dimensions; i++) {
			forLoopEnd = forLoopEnd + "}"; // close the for loops
		}

		String access = "";

		for (int i = 0; i < dimensions; i++) {
			access = access + "[" + loopVariables.get(i) + "]";
		}

		String dataDef = electionDesc.getContainer().getInputType().getDataTypeAndSign();

		String definition = dataDef + " arr" + electionDesc.getContainer().getInputType().getDimensionDescriptor(true)
				+ ";";

		code.add(definition);

		String assignment = forLoopStart + "arr" + access + " = " + " tmp_struct.arr" + access + ";" + forLoopEnd
				+ "\n";

		code.add(assignment);

		code.add("unsigned int sum = 0;");
		code.add("for(unsigned int i = 0; i < amountVotes; i++) {");

		// add the specific code which differs for different input types
		electionDesc.getContainer().getInputType().addCodeForVoteSum(code, unique);

		code.add("}");
		code.add("return sum;");

		code.add("}");
	}

	/**
	 * Other functions needed for the check.
	 */
	private void addOtherFunctions() {
		addSplitArrayFunctions();
		addConcatenationFunctions();
		addPermutationFunctions();
		addIntersectFunction();
	}

	private void addIntersectFunction() {
		if (electionDesc.getContainer().getOutputType().getAmountOfDimensions() != 1
				|| !electionDesc.getContainer().getOutputType().getSizeOfDimensions()[0].equals("C")) {
			return;
		}

		code.add(electionDesc.getContainer().getOutputStruct().getStructAccess() + " intersect("
				+ electionDesc.getContainer().getOutputStruct().getStructAccess() + " one, "
				+ electionDesc.getContainer().getOutputStruct().getStructAccess() + " two) {");
		code.add(electionDesc.getContainer().getOutputStruct().getStructAccess() + " toReturn;");
		code.add("  ");
		code.add("  for (int i = 0; i < C; i++) {");
		code.add("    toReturn.arr[i] = 0;");
		code.add("  }");
		code.add("  ");
		code.add("  for (int i = 0; i < C; i++) {");
		code.add("    if (one.arr[i] && two.arr[i]) {");
		code.add("      toReturn.arr[i] = 1;");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add("  return toReturn;");
		code.add("}");
	}

	private void addPermutationFunctions() {
		addPermutateOneFunction();
		addPermutateTwoFunction();
	}

	private void addPermutateTwoFunction() {
		if (electionDesc.getContainer().getInputType().getAmountOfDimensions() != 2
				|| !electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("V")
				|| !electionDesc.getContainer().getInputType().getSizeOfDimensions()[1].equals("C")) {
			return;
		}

		String voteStruct = electionDesc.getContainer().getInputStruct().getStructAccess();

		code.add(voteStruct + " permutateTwo(" + voteStruct + " votes, " + "unsigned int length) {");
		code.add(voteStruct + " sub_arr;");
		code.add("  ");
		code.add("unsigned int already_used_arr[V];");
		code.add("    ");
		code.add("for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("for(int j = 0; j < C; j++) {");
		code.add("sub_arr.arr[i][j] = C;");
		code.add("}");
		code.add("}");
		code.add("");
		code.add("  ");
		code.add("for(int i = 0; i < length; i++) {");
		code.add("unsigned int new_index = nondet_uint();");
		code.add("assume((new_index >= 0) && (new_index < length));");
		code.add("");
		code.add("for(int j = 0; j < i; j++) {");
		code.add("assume(new_index != already_used_arr[j]);");
		code.add("}");
		code.add("    ");
		code.add("    already_used_arr[i] = new_index;");
		code.add("    ");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      sub_arr.arr[new_index][j] = votes.arr[i][j];");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add(voteStruct + " toReturn;");
		code.add("    ");
		code.add("  for (int i = 0; i < V; i++) {");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      toReturn.arr[i][j] = sub_arr.arr[i][j];");
		code.add("    }");
		code.add("  }");
		code.add("    ");
		code.add("  return toReturn;");
		code.add("}");
	}

	private void addPermutateOneFunction() {
		if (electionDesc.getContainer().getInputType().getAmountOfDimensions() != 1
				|| !electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("V")) {
			return;
		}

		String voteStruct = electionDesc.getContainer().getInputStruct().getStructAccess();

		code.add(voteStruct + " permutateOne(" + voteStruct + " votes, unsigned int length) {");
		code.add(voteStruct + " sub_arr;");
		code.add("  ");
		code.add(" unsigned int already_used_arr[V];");
		code.add("    ");
		code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("    sub_arr.arr[i] = C;");
		code.add("  }");
		code.add("  ");
		code.add("  ");
		code.add("  for(int i = 0; i < length; i++) {");
		code.add("    unsigned int new_index = nondet_uint();");
		code.add("    assume((new_index >= 0) && (new_index < length));");
		code.add("    ");
		code.add("    for(int j = 0; j < i; j++) {");
		code.add("      assume(new_index != already_used_arr[j]);");
		code.add("    }");
		code.add("    ");
		code.add("    already_used_arr[i] = new_index;");
		code.add("    ");
		code.add("    sub_arr.arr[new_index] = votes.arr[i];");
		code.add("  }");
		code.add("  ");
		code.add(voteStruct + " toReturn;");
		code.add("    ");
		code.add("  for (int i = 0; i < V; i++) {");
		code.add("    toReturn.arr[i] = sub_arr.arr[i];");
		code.add("  }");
		code.add("    ");
		code.add("  return toReturn;");
		code.add("  ");
		code.add("}");
		code.add("");
	}

	private void addConcatenationFunctions() {
		addConcatOneFunction();
		addConcatTwoFunction();
	}

	private void addConcatTwoFunction() {
		if (electionDesc.getContainer().getInputType().getAmountOfDimensions() != 2
				|| !electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("V")
				|| !electionDesc.getContainer().getInputType().getSizeOfDimensions()[1].equals("C")) {
			return;
		}

		String voteStruct = electionDesc.getContainer().getInputStruct().getStructAccess();

		code.add(voteStruct + " concatTwo(" + voteStruct + " votesOne, unsigned int sizeOne, " + voteStruct
				+ " votesTwo, unsigned int sizeTwo) {");
		code.add(voteStruct + " sub_arr;");
		code.add("  ");
		code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      sub_arr.arr[i][j] = C;");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add("  for(int i = 0; i < sizeOne; i++) { //limit the size to the upper bound V");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      if (i < V) {");
		code.add("        sub_arr.arr[i][j] = votesOne.arr[i][j];");
		code.add("      }");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add("  for(int i = 0; i < sizeTwo; i++) { //limit the size to the upper bound V");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      if (sizeTwo + i < V) {");
		code.add("        sub_arr.arr[i][j] = votesTwo.arr[i][j];");
		code.add("      }");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
		code.add("    ");
		code.add("  for (int i = 0; i < V; i++) {");
		code.add("    for (int j = 0; j < C; j++) {");
		code.add("      toReturn.arr[i][j] = sub_arr.arr[i][j];");
		code.add("    }");
		code.add("  }");
		code.add("    ");
		code.add("  return toReturn;");
		code.add("}");
	}

	private void addConcatOneFunction() {
		if (electionDesc.getContainer().getInputType().getAmountOfDimensions() != 1
				|| !electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("V")) {
			return;
		}

		String voteStruct = electionDesc.getContainer().getInputStruct().getStructAccess();

		code.add(voteStruct + " concatOne(" + voteStruct + " votesOne, unsigned int sizeOne, " + voteStruct
				+ " votesTwo, unsigned int sizeTwo) {");
		code.add(voteStruct + " sub_arr;");
		code.add("  ");
		code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("    sub_arr.arr[i] = C;");
		code.add("  }");
		code.add("  ");
		code.add("  for(int i = 0; i < sizeOne; i++) { //limit the size to the upper bound V");
		code.add("    if (i < V) {");
		code.add("      sub_arr.arr[i] = votesOne.arr[i];");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add("  for(int i = 0; i < sizeTwo; i++) { //limit the size to the upper bound V");
		code.add("    if (sizeOne + i < V) {");
		code.add("      sub_arr.arr[sizeOne + i] = votesTwo.arr[i];");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add(voteStruct + " toReturn;");
		code.add("    ");
		code.add("  for (int i = 0; i < V; i++) {");
		code.add("    toReturn.arr[i] = sub_arr.arr[i];");
		code.add("  }");
		code.add("    ");
		code.add("  return toReturn;");
		code.add("  ");
		code.add("}");
		code.add("");
	}

	private void addSplitArrayFunctions() {

		code.add("//split array");
		code.add("");
		code.add("//get splits cuts through an array of size max");
		code.add("unsigned int *getRandomSplitLines(unsigned int splits, unsigned int max) {");
		code.add("  unsigned int *split_arr = malloc(splits * sizeof(*split_arr));");
		code.add("  ");
		code.add("  if (splits == 1) {");
		code.add("    unsigned int next_split = nondet_uint();");
		code.add("    assume(next_split >= 0);");
		code.add("    assume(next_split <= (max / 2));");
		code.add("    ");
		code.add("    split_arr[0] = next_split;");
		code.add("  } else {");
		code.add("    unsigned int last_split = 0;");
		code.add("    for (int i = 0; i < splits; i++) {");
		code.add("      ");
		code.add("      unsigned int next_split = nondet_uint();");
		code.add("      assume(next_split >= last_split);");
		code.add("      assume(next_split <= max);");
		code.add("      ");
		code.add("      ");
		code.add("      ");
		code.add("      split_arr[i] = next_split;");
		code.add("      last_split = next_split;");
		code.add("    }");
		code.add("    ");
		code.add("    unsigned int *splitLines;");
		code.add("    splitLines = split_arr;");
		code.add("    ");
		code.add("    for (int i = 0; i < splits; i++) {");
		code.add("      unsigned int debugrandom = splitLines[i];");
		code.add("    }");
		code.add("  }");
		code.add("  return split_arr;");
		code.add("}");
		code.add("");
		code.add("//start is inclusive, stop is exclusive");
		code.add("");

		String voteStruct = electionDesc.getContainer().getInputStruct().getStructAccess();

		if ((electionDesc.getContainer().getInputType().getAmountOfDimensions() == 1
				&& electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("V"))) {

			code.add(voteStruct + " split(" + voteStruct + " votes, " + "unsigned int start, unsigned int stop) {");
			code.add(voteStruct + " sub_arr;");
			code.add("  ");
			code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
			code.add("    sub_arr.arr[i] = C;");
			code.add("  }");
			code.add("  ");
			code.add("  if(start == stop) { //the sub array should be empty");
			code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
			code.add("    ");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      toReturn.arr[i] = sub_arr.arr[i];");
			code.add("    }");
			code.add("    ");
			code.add("    return toReturn;");
			code.add("  } else {");
			code.add("");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      if ((i >= start) && (i < stop)) {");
			code.add("        sub_arr.arr[i - start] = votes.arr[i];");
			code.add("      }");
			code.add("    }");
			code.add("  ");
			code.add("    ");
			code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
			code.add("    ");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      toReturn.arr[i] = sub_arr.arr[i];");
			code.add("    }");
			code.add("    ");
			code.add("    return toReturn;");
			code.add("  }");
			code.add("}");
			code.add("");
		}

		if ((electionDesc.getContainer().getInputType().getAmountOfDimensions() == 2
				&& electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("V")
				&& electionDesc.getContainer().getInputType().getSizeOfDimensions()[1].equals("C"))) {

			code.add("//start is inclusive, stop is exclusive");
			code.add("//used for 2 dim arrays");
			code.add(voteStruct + " split(" + voteStruct + " votes, " + "unsigned int start, unsigned int stop) {");
			code.add(voteStruct + " sub_arr;");
			code.add("  ");
			code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
			code.add("    for(int j = 0; j < C; j++) {");
			code.add("      sub_arr.arr[i][j] = C;");
			code.add("    }");
			code.add("  }");
			code.add("  ");
			code.add("  if(start == stop) { //the sub array should be empty");
			code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
			code.add("    ");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      for(int j = 0; j < V; j++) {");
			code.add("        toReturn.arr[i][j] = sub_arr.arr[i][j];");
			code.add("      }");
			code.add("    }");
			code.add("    ");
			code.add("    return toReturn;");
			code.add("  } else {");
			code.add("");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      if ((i >= start) && (i < stop)) {");
			code.add("        for(int j = 0; j < C; j++) {");
			code.add("          sub_arr.arr[i - start][j] = votes.arr[i][j];");
			code.add("        }");
			code.add("      }");
			code.add("    }");
			code.add("    ");
			code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
			code.add("    ");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      for(int j = 0; j < V; j++) {");
			code.add("        toReturn.arr[i][j] = sub_arr.arr[i][j];");
			code.add("      }");
			code.add("    }");
			code.add("    ");
			code.add("    return toReturn;");
			code.add("  }");
			code.add("}");
			code.add("");
		}
	}

	/**
	 * writes all lines of the header
	 *
	 * @param code the list in which the header should be written
	 * @return the finished header
	 */
	public CodeArrayListBeautifier addHeader(CodeArrayListBeautifier code) {
		code.add("#include <stdlib.h>");
		code.add("#include <stdint.h>");
		code.add("#include <assert.h>");
		code.add("");
		code.add("unsigned int nondet_uint();");
		code.add("int nondet_int();");
		code.add("");
		code.add("#define assert2(x, y) __CPROVER_assert(x, y)");
		code.add("#define assume(x) __CPROVER_assume(x)");
		code.add("");

		code.addAll(Arrays.asList(electionDesc.getContainer().getStructDefinitions().split("\\n")));

		return code;
	}

	/**
	 * Adds the main method the main method declares the boolean expression. In the
	 * main method the voting method is called.
	 */
	private void addMainMethod() {
		code.add("int main(int argc, char *argv[]) {");
		code.addTab();
		// generating the pre and post AbstractSyntaxTrees
		BooleanExpListNode preAST = generateAST(preAndPostCondDesc.getPreConditionsDescription().getCode());
		BooleanExpListNode postAST = generateAST(preAndPostCondDesc.getPostConditionsDescription().getCode());
		initializeNumberOfTimesVoted(preAST, postAST);
		// init all voting vars for the voters
		for (int i = 1; i <= numberOfTimesVoted; i++) {
			code.add("unsigned int " + UnifiedNameContainer.getVoter() + i + " = " + UnifiedNameContainer.getVoter()
					+ ";");
		}
		// init all voting vars for the candidates
		for (int i = 1; i <= numberOfTimesVoted; i++) {
			code.add("unsigned int " + UnifiedNameContainer.getCandidate() + i + " = "
					+ UnifiedNameContainer.getCandidate() + ";");
		}
		// init all voting vars for the seats
		for (int i = 1; i <= numberOfTimesVoted; i++) {
			code.add("unsigned int " + UnifiedNameContainer.getSeats() + i + " = " + UnifiedNameContainer.getSeats()
					+ ";");
		}
		List<String> boundedVars = preAndPostCondDesc.getBoundedVarDescription().getCodeAsList();
		code.addList(boundedVars);
		// first the Variables have to be Initialized
		addSymbVarInitialisation();

		for (int voteNumber = 1; voteNumber <= numberOfTimesVoted; voteNumber++) {
			String minV = "" + electionDesc.getContainer().getInputType().getMinimalValue();
			if (electionDesc.getContainer().getInputType().hasVariableAsMinValue()) {
				minV += voteNumber;
			}
			String maxV = "" + electionDesc.getContainer().getInputType().getMaximalValue();
			if (electionDesc.getContainer().getInputType().hasVariableAsMaxValue()) {
				maxV += voteNumber;
			}

			code.add("//init for election: " + voteNumber);
			addInitialisedValue(electionDesc.getContainer().getNameContainer().getVotingArray() + voteNumber,
					electionDesc.getContainer().getInputType(), electionDesc.getContainer().getInputStruct(), minV,
					maxV);
		}

		// the the PreProperties must be defined
		addPreProperties(preAST);
		// now hold all the elections
		for (int i = 1; i <= numberOfTimesVoted; i++) {
			code.add("//election number: " + i);

			String sizeOfVotes = electionDesc.getContainer().getNameContainer().getVoter() + i;

			String electX = electionDesc.getContainer().getOutputStruct().getStructAccess() + " elect" + i + " = "
					+ UnifiedNameContainer.getVotingMethod() + "(" + sizeOfVotes + ", votes" + i + ");";
			code.add(electX);
		}
		// now the Post Properties can be checked
		addPostProperties(postAST);
		code.deleteTab();
		code.add("}");
	}

	/**
	 * This should be used to create the VarInitialisation within the main method.
	 */
	private void addSymbVarInitialisation() {
		List<SymbolicVariable> symbolicVariableList = preAndPostCondDesc.getSymbolicVariablesAsList();
		code.add("//Symbolic Variables initialisation");
		symbolicVariableList.forEach((symbVar) -> {
			InternalTypeContainer internalType = symbVar.getInternalTypeContainer();
			String id = symbVar.getId();
			if (!internalType.isList()) {
				switch (internalType.getInternalType()) {
				case VOTER:
					code.add("unsigned int " + id + " = nondet_uint();");
					// a Voter is basically an unsigned int.
					// The number shows which vote from votesX (the Array of all
					// votes) belongs to the voter.
					code.add("assume(0 <= " + id + " && " + id + " < V);");
					// The Voter has to be in the range of possible Voters. V is
					// the total amount of Voters.
					break;
				case CANDIDATE:
					code.add("unsigned int " + id + " = nondet_uint();");
					// a Candidate is basically an unsigned int. Candidate 0 is
					// 0 and so on
					code.add("assume(0 <= " + id + " && " + id + " < C);");
					// C is the number of total Candidates. 0 is A Candidate. C
					// is not a candidate
					break;
				case SEAT:
					// a seat is a also an unsigned int.
					// The return of a voting method (an Array) gives the elected
					// candidate(value) of the seat(id)
					code.add("unsigned int " + id + " = nondet_uint();");
					// there are S seats. From 0 to S-1
					code.add("assume(0 <= " + id + " && " + id + " < S);");
					break;
				case APPROVAL:
					break;
				case WEIGHTEDAPPROVAL:
					break;
				case INTEGER:
					code.add("unsigned int " + id + " = nondet_uint();");
					break;
				default:
					reportUnsupportedType(id);

				}
			} else {
				reportUnsupportedType(id);
			}
		});
		code.add("");

	}

	/**
	 * This adds the Code of the PreProperties. It uses a visitor which it creates.
	 */
	private void addPreProperties(BooleanExpListNode preAST) {
		code.add("");
		code.add("//preproperties ");
		code.add("");
		visitor.setToPreConditionMode();
		preAST.getBooleanExpressions().forEach((booleanExpressionLists) -> {
			booleanExpressionLists.forEach((booleanNode) -> {
				code.addList(visitor.generateCode(booleanNode));
			});
		});
	}

	/**
	 * this adds the Code of the PostProperties. It uses a Visitor it creates
	 */
	private void addPostProperties(BooleanExpListNode postAST) {
		code.add("");
		code.add("//postproperties ");
		code.add("");
		visitor.setToPostConditionMode();
		postAST.getBooleanExpressions().forEach((booleanExpressionLists) -> {
			booleanExpressionLists.forEach((booleanNode) -> {
				code.addList(visitor.generateCode(booleanNode));
			});
		});
	}

	private void reportUnsupportedType(String id) {
		ErrorLogger.log("Der Typ der symbolischen Variable " + id + " wird nicht unterstützt");
	}

	private void initializeNumberOfTimesVoted(BooleanExpListNode preAST, BooleanExpListNode postAST) {
		numberOfTimesVoted = (preAST.getMaxVoteLevel() > postAST.getMaxVoteLevel()) ? preAST.getMaxVoteLevel()
				: postAST.getMaxVoteLevel();
		numberOfTimesVoted = (preAST.getHighestElect() > numberOfTimesVoted) ? preAST.getHighestElect()
				: numberOfTimesVoted;
		numberOfTimesVoted = (postAST.getHighestElect() > numberOfTimesVoted) ? postAST.getHighestElect()
				: numberOfTimesVoted;
	}

	/**
	 * adds a value of the fitting size as described
	 * 
	 * @param valueName
	 * @param type
	 * @param voteNumber if multiple instances of this variable should be created
	 *                   for multiple votes, this number has to be given
	 */
	private void addInitialisedValue(String valueName, InOutType inOutType, ComplexType complexType, String minValue,
			String maxValue) {
		code.add("// init of variable " + valueName);

		String declaration = complexType.getStructAccess() + " " + valueName + ";";
		code.add(declaration);

		List<String> loopVariables = addNestedForLoopTop(this.code, inOutType.getSizeOfDimensionsAsList(),
				new ArrayList<String>());

		String assignment = valueName + "." + electionDesc.getContainer().getNameContainer().getStructValueName();

		for (int i = 0; i < inOutType.getAmountOfDimensions(); i++) {
			assignment = assignment + "[" + loopVariables.get(i) + "]";
		}

		code.add(assignment + " = nondet_uint();");
		code.add("assume((" + minValue + " <= " + assignment + ") && (" + assignment + " <= " + maxValue + "));");

		inOutType.addExtraCodeAtEndOfCodeInit(code, valueName, loopVariables);

		addNestedForrLoopBot(this.code, inOutType.getAmountOfDimensions());

	}

	private BooleanExpListNode generateAST(String code) {
		FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(CharStreams.fromString(code));
		CommonTokenStream ts = new CommonTokenStream(l);
		FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(ts);

		BooleanExpScope declaredVars = new BooleanExpScope();
		preAndPostCondDesc.getSymbolicVariablesAsList().forEach((v) -> {
			declaredVars.addTypeForId(v.getId(), v.getInternalTypeContainer());
		});
		return translator.generateFromSyntaxTree(p.booleanExpList(), electionDesc.getContainer().getInputType(),
				electionDesc.getContainer().getOutputType(), declaredVars);
	}

	private String getVotingResultCode(CBMCResultValueWrapper votingData) {
		return electionDesc.getContainer().getInputType().getVotingResultCode(votingData);
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
					length++; // increase the length in case all words from that length are already taken
				} else {
					duplicate = false;
				}
			}
			generatedVariables.add(varName);
			currentIndex++;
		}
		return generatedVariables;
	}

	private String generateRandomString(int length) {
		String generatedString = RandomStringUtils.random(length, true, false);

		return generatedString;
	}

	private String generateForLoopHeader(String indexName, String maxSize) {
		return "for (unsigned int " + indexName + " = 0; " + indexName + " < " + maxSize + "; " + indexName + "++ ) { ";
	}
}