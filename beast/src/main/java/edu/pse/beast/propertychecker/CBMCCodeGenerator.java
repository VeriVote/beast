package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

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
import edu.pse.beast.types.InternalTypeContainer;

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

		addMarginMainTest(); // TODO add gcc ability here
	}

	private void addMarginMainCheck(int margin, ElectionSimulationData origResult) {
		// we add the margin which will get computed by the model checker
		code.add("#ifndef MARGIN\n #define MARGIN " + margin + "\n #endif");
		// we also add the original result, which is calculated by compiling the
		// program and running it
		electionDesc.getContainer().getOutputType().addLastResultAsCode(code, origResult.values);
		// add the verify method:
		// taken and adjusted from the paper:
		// https://formal.iti.kit.edu/~beckert/pub/evoteid2016.pdf
		electionDesc.getContainer().getInputType().addVerifyMethod(code, electionDesc.getContainer().getOutputType());
		// add the main method
		code.add("int main() {");
		code.addTab();
		code.add("verify();");
		code.deleteTab();
		code.add("}");
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
		String input = electionDesc.getContainer().getOutputType().getDataTypeAndSign() + " arr"
				+ electionDesc.getContainer().getInputType().getDimensionDescriptor(true);
		code.add("unsigned int voteSumForCandidate" + (unique ? "Unique" : "")
				+ "(INPUT, unsigned int amountVotes, unsigned int candidate) {".replace("INPUT", input));
		code.addTab();
		code.add("unsigned int sum = 0;");
		code.add("for(unsigned int i = 0; i < amountVotes; i++) {");
		code.addTab();
		// add the specific code which differs for different input types
		electionDesc.getContainer().getInputType().addCodeForVoteSum(code, unique);
		code.deleteTab();
		code.add("}");
		code.add("return sum;");
		code.deleteTab();
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
				|| !electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("C")) {
			return;
		}

		code.add(electionDesc.getContainer().getOutputStruct().getStructAccess()
				+ " permutateTwo(unsigned int votes[V][C], " + "unsigned int length) {");
		code.add("  static unsigned int sub_arr[V][C];");
		code.add("  ");
		code.add("  static unsigned int already_used_arr[V];");
		code.add("    ");
		code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      sub_arr[i][j] = C;");
		code.add("    }");
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
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      sub_arr[new_index][j] = votes[i][j];");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add("  struct vote_double toReturn;");
		code.add("    ");
		code.add("  for (int i = 0; i < V; i++) {");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      toReturn.arr[i][j] = sub_arr[i][j];");
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

		code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
				+ " permutateOne(unsigned int votes[V], unsigned int length) {");
		code.add("  static unsigned int sub_arr[V];");
		code.add("  ");
		code.add("  static unsigned int already_used_arr[V];");
		code.add("    ");
		code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("    sub_arr[i] = C;");
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
		code.add("    sub_arr[new_index] = votes[i];");
		code.add("  }");
		code.add("  ");
		code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
		code.add("    ");
		code.add("  for (int i = 0; i < V; i++) {");
		code.add("    toReturn.arr[i] = sub_arr[i];");
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
				|| !electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("C")) {
			return;
		}

		code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
				+ " concatTwo(unsigned int votesOne[V][C], unsigned int sizeOne, "
				+ "unsigned int votesTwo[V][C], unsigned int sizeTwo) {");
		code.add("  static unsigned int sub_arr[V][C];");
		code.add("  ");
		code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      sub_arr[i][j] = C;");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add("  for(int i = 0; i < sizeOne; i++) { //limit the size to the upper bound V");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      if (i < V) {");
		code.add("        sub_arr[i][j] = votesOne[i][j];");
		code.add("      }");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add("  for(int i = 0; i < sizeTwo; i++) { //limit the size to the upper bound V");
		code.add("    for(int j = 0; j < C; j++) {");
		code.add("      if (sizeTwo + i < V) {");
		code.add("        sub_arr[i][j] = votesTwo[i][j];");
		code.add("      }");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
		code.add("    ");
		code.add("  for (int i = 0; i < V; i++) {");
		code.add("    for (int j = 0; j < C; j++) {");
		code.add("      toReturn.arr[i][j] = sub_arr[i][j];");
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

		code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
				+ " concatOne(unsigned int votesOne[V], unsigned int sizeOne, "
				+ "unsigned int votesTwo[V], unsigned int sizeTwo) {");
		code.add("  static unsigned int sub_arr[V];");
		code.add("  ");
		code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("    sub_arr[i] = C;");
		code.add("  }");
		code.add("  ");
		code.add("  for(int i = 0; i < sizeOne; i++) { //limit the size to the upper bound V");
		code.add("    if (i < V) {");
		code.add("      sub_arr[i] = votesOne[i];");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add("  for(int i = 0; i < sizeTwo; i++) { //limit the size to the upper bound V");
		code.add("    if (sizeOne + i < V) {");
		code.add("      sub_arr[sizeOne + i] = votesTwo[i];");
		code.add("    }");
		code.add("  }");
		code.add("  ");
		code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
		code.add("    ");
		code.add("  for (int i = 0; i < V; i++) {");
		code.add("    toReturn.arr[i] = sub_arr[i];");
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

		if ((electionDesc.getContainer().getInputType().getAmountOfDimensions() == 1
				&& !electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("V"))) {

			code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
					+ " splitOne(unsigned int votes[V], " + "unsigned int start, unsigned int stop) {");
			code.add("  static unsigned int sub_arr[V];");
			code.add("  ");
			code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
			code.add("    sub_arr[i] = C;");
			code.add("  }");
			code.add("  ");
			code.add("  if(start == stop) { //the sub array should be empty");
			code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
			code.add("    ");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      toReturn.arr[i] = sub_arr[i];");
			code.add("    }");
			code.add("    ");
			code.add("    return toReturn;");
			code.add("  } else {");
			code.add("");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      if ((i >= start) && (i < stop)) {");
			code.add("        sub_arr[i - start] = votes[i];");
			code.add("      }");
			code.add("    }");
			code.add("  ");
			code.add("    ");
			code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
			code.add("    ");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      toReturn.arr[i] = sub_arr[i];");
			code.add("    }");
			code.add("    ");
			code.add("    return toReturn;");
			code.add("  }");
			code.add("}");
			code.add("");
		}

		if ((electionDesc.getContainer().getInputType().getAmountOfDimensions() == 2
				&& !electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("V")
				&& !electionDesc.getContainer().getInputType().getSizeOfDimensions()[0].equals("C"))) {

			code.add("//start is inclusive, stop is exclusive");
			code.add("//used for 2 dim arrays");
			code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
					+ " splitTwo(unsigned int votes[V][C], " + "unsigned int start, unsigned int stop) {");
			code.add("  static unsigned int sub_arr[V][C];");
			code.add("  ");
			code.add("  for(int i = 0; i < V; i++) { //set all to C in the beginning");
			code.add("    for(int j = 0; j < C; j++) {");
			code.add("      sub_arr[i][j] = C;");
			code.add("    }");
			code.add("  }");
			code.add("  ");
			code.add("  if(start == stop) { //the sub array should be empty");
			code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
			code.add("    ");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      for(int j = 0; j < V; j++) {");
			code.add("        toReturn.arr[i][j] = sub_arr[i][j];");
			code.add("      }");
			code.add("    }");
			code.add("    ");
			code.add("    return toReturn;");
			code.add("  } else {");
			code.add("");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      if ((i >= start) && (i < stop)) {");
			code.add("        for(int j = 0; j < C; j++) {");
			code.add("          sub_arr[i - start][j] = votes[i][j];");
			code.add("        }");
			code.add("      }");
			code.add("    }");
			code.add("    ");
			code.add(electionDesc.getContainer().getInputStruct().getStructAccess() + " toReturn;");
			code.add("    ");
			code.add("    for (int i = 0; i < V; i++) {");
			code.add("      for(int j = 0; j < V; j++) {");
			code.add("        toReturn.arr[i][j] = sub_arr[i][j];");
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
		addVotesArrayAndElectInitialisation();
		// the the PreProperties must be defined
		addPreProperties(preAST);
		// now hold all the elections
		for (int i = 1; i <= numberOfTimesVoted; i++) {
			code.add("//election number: " + i);

			String electX = electionDesc.getContainer().getOutputStruct().getStructAccess() + " elect" + i + " = "
					+ UnifiedNameContainer.getVotingMethod() + "(votes" + i + ");";
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

	private void addVotesArrayAndElectInitialisation() {
		code.add("//voting-array and elect variable initialisation");
		for (int voteNumber = 1; voteNumber <= numberOfTimesVoted; voteNumber++) {
			code.add("//init for election: " + voteNumber);

			String votesX = electionDesc.getContainer().getInputStruct().getStructAccess();

			votesX = votesX + " " + UnifiedNameContainer.getVotingArray() + voteNumber;

			code.add(votesX + ";");

			String forTemplate = "for(unsigned int COUNTER = 0; COUNTER < UPPER; COUNTER++) {";

			int listDepth = 0;
			for (int i = 0; i < electionDesc.getContainer().getInputType().getAmountOfDimensions(); i++) {
				String currentFor = forTemplate.replaceAll("COUNTER", "counter_" + listDepth);

				currentFor = currentFor.replaceAll("UPPER",
						electionDesc.getContainer().getInputType().getSizeOfDimensions()[listDepth] + voteNumber);

				code.add(currentFor);
				code.addTab();
				listDepth++;
			}

			String min = "" + electionDesc.getContainer().getInputType().getMinimalValue();
			if (electionDesc.getContainer().getInputType().hasVariableAsMinValue()) {
				min += voteNumber;
			}

			String max = "" + electionDesc.getContainer().getInputType().getMaximalValue();
			if (electionDesc.getContainer().getInputType().hasVariableAsMaxValue()) {
				max += voteNumber;
			}

			String votesElement = UnifiedNameContainer.getVotingArray() + voteNumber + "."
					+ electionDesc.getContainer().getNameContainer().getResultArrName();

			for (int i = 0; i < listDepth; ++i) {
				votesElement += "[COUNTER]".replace("COUNTER", "counter_" + i);
			}
			String nondetInt = (votesElement + " = nondet_uint();");
			String voteDecl = ("assume((MIN <= " + votesElement + ") && (" + votesElement + " <= MAX));");
			voteDecl = voteDecl.replace("MIN", min);
			voteDecl = voteDecl.replace("MAX", max);
			code.add(nondetInt);
			code.add(voteDecl);
			// if we need to add something extra
			electionDesc.getContainer().getInputType().addExtraCodeAtEndOfCodeInit(code, voteNumber);
			// close the for loops
			for (int i = 0; i < listDepth; ++i) {
				code.deleteTab();
				code.add("}");
			}
			code.add("");
		}
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
}