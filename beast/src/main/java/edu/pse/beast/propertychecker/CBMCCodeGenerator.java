/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.FormalPropertySyntaxTreeToAstTranslator;
import edu.pse.beast.types.InternalTypeContainer;

/**
 * This class creates the .c file which will be checked with CBMC. It generates
 * a main method (including the FormalProperty), important IncludingCode and the
 * votingMethod (the ElectionDescription).
 *
 * @author Niels
 */
public class CBMCCodeGenerator {

	private CodeArrayListBeautifier code;
	private final ElectionDescription electionDesc;
	private final PreAndPostConditionsDescription preAndPostCondDesc;
	private final FormalPropertySyntaxTreeToAstTranslator translator;
	private final CBMCCodeGenerationVisitor visitor;
	private int numberOfTimesVoted; // this number should be the number of
	private int margin;
	private List<String> origResult;
	// private boolean isTest;

	/**
	 * After the build the code is fully generated and can be acquired by getCode();
	 *
	 * @param electionDescription
	 *            the lectionDecription that holds the code that describes the
	 *            voting method. that code will be merged with the generated code
	 * @param PreAndPostConditionsDescription
	 *            the Descriptions that will be used to generate the C-Code for CBMC
	 */
	public CBMCCodeGenerator(ElectionDescription electionDesc,
			PreAndPostConditionsDescription PreAndPostConditionsDescription) {

		this.translator = new FormalPropertySyntaxTreeToAstTranslator();
		this.electionDesc = electionDesc;
		this.preAndPostCondDesc = PreAndPostConditionsDescription;
		code = new CodeArrayListBeautifier();
		electionDesc.getContainer();
		this.visitor = new CBMCCodeGenerationVisitor(electionDesc.getContainer());
		new CCodeHelper();
		generateCodeCheck();

	}

	/**
	 * After the build the code is fully generated and can be aquired by getCode();
	 *
	 * @param electionDescription
	 *            the lectionDecription that holds the code that describes the
	 *            voting method. that code will be merged with the generated code
	 * @param PreAndPostConditionsDescription
	 *            the Descriptions that will be used to generate the C-Code for CBMC
	 */
	public CBMCCodeGenerator(ElectionDescription electionDesc, PreAndPostConditionsDescription preAndPostCondDesc,
			int margin, List<String> origResult, String[][] votingData) {

		this.translator = new FormalPropertySyntaxTreeToAstTranslator();
		this.electionDesc = electionDesc;
		this.preAndPostCondDesc = preAndPostCondDesc;
		code = new CodeArrayListBeautifier();
		electionDesc.getContainer();
		this.visitor = new CBMCCodeGenerationVisitor(electionDesc.getContainer());
		new CCodeHelper();
		this.margin = margin;
		this.origResult = origResult;

		generateCodeMargin(votingData);

	}

	// constructor when it will be used to get the code for a test
	public CBMCCodeGenerator(ElectionDescription electionDesc, PreAndPostConditionsDescription preAndPostCondDesc,
			String[][] votingData) {

		this.translator = new FormalPropertySyntaxTreeToAstTranslator();
		this.electionDesc = electionDesc;
		this.preAndPostCondDesc = preAndPostCondDesc;
		code = new CodeArrayListBeautifier();
		electionDesc.getContainer();
		this.visitor = new CBMCCodeGenerationVisitor(electionDesc.getContainer());
		new CCodeHelper();

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
		electionDescriptionCode.addAll(electionDesc.getCode());
		code.addList(electionDescriptionCode);

		addMainMethod();

	}

	private void generateCodeMargin(String[][] votingData) { // we want to create code for a margin
		// computation or just a test run

		// add the header and the voting data
		addMarginHeaders(votingData);

		// add the code the user wrote (e.g the election function)
		code.addAll(GUIController.getController().getElectionDescription().getCode());

		// add the code which defines the votes
		code.addAll(getVotingResultCode(votingData));

		addMarginMainCheck(margin, origResult);

	}

	private void generateCodeTest(String[][] votingData) { // we want to create code for a margin
		// computation or just a test run

		// add the header and the voting data
		addMarginHeaders(votingData);

		// add the code the user wrote (e.g the election function)

		code.addAll(GUIController.getController().getElectionDescription().getCode());

		// add the code which defines the votes
		code.addAll(getVotingResultCode(votingData));

		addMarginMainTest(); // TODO add gcc ability here

	}

	private void addMarginMainCheck(int margin, List<String> origResult) {
		// we add the margin which will get computed by the model checker
		code.add("#ifndef MARGIN\n #define MARGIN " + margin + "\n #endif");
		// we also add the original result, which is calculated by compiling the
		// program and running it

		electionDesc.getContainer().getOutputType().addLastResultAsCode(code, origResult);

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

	private void addMarginHeaders(String[][] votingData) {

		// add the headers CBMC needs;

		code = addHeader(code);

		// define some pre processor values
		code.add("#ifndef " + UnifiedNameContainer.getVoter() + "\n #define " + UnifiedNameContainer.getVoter() + " "
				+ votingData.length + "\n #endif");
		code.add("#ifndef " + UnifiedNameContainer.getCandidate() + " \n #define " + UnifiedNameContainer.getCandidate()
				+ " " + votingData[0].length + "\n #endif");
		code.add("#ifndef " + UnifiedNameContainer.getSeats() + "\n #define " + UnifiedNameContainer.getVoter() + " "
				+ votingData[0].length + "\n #endif");
	}

	private void addMarginMainTest() {

		int voteNumber = 1; // because we only have one vote, we hardcode the
							// value one here

		code = electionDesc.getContainer().getOutputType().addMarginMainTest(code, voteNumber);
	}

	private void addVoteSumFunc(boolean unique) {
		String input = "unsigned int arr" + electionDesc.getContainer().getInputType().getInputString();
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
	 * other functions needed for the check
	 */
	private void addOtherFunctions() {
		// split array functions

		code.add("//split array");
		code.add("");
		code.add("//get splits cuts through an array of size max");
		code.add("unsigned int *getRandomSplitLines(unsigned int splits, unsigned int max) {");
		code.add("	unsigned int *split_arr = malloc(splits * sizeof(*split_arr));");
		code.add("	");
		code.add("	if (splits == 1) {");
		code.add("		unsigned int next_split = nondet_uint();");
		code.add("		assume(next_split >= 0);");
		code.add("		assume(next_split <= (max / 2));");
		code.add("		");
		code.add("		split_arr[0] = next_split;");
		code.add("	} else {");
		code.add("		unsigned int last_split = 0;");
		code.add("		for (int i = 0; i < splits; i++) {");
		code.add("			");
		code.add("			unsigned int next_split = nondet_uint();");
		code.add("			assume(next_split >= last_split);");
		code.add("			assume(next_split <= max);");
		code.add("			");
		code.add("					");
		code.add("		unsigned int debugHier =max + 1;");
		code.add("			");
		code.add("			split_arr[i] = next_split;");
		code.add("			last_split = next_split;");
		code.add("		}");
		code.add("		");
		code.add("		unsigned int *splitLines;");
		code.add("		splitLines = split_arr;");
		code.add("		");
		code.add("		for (int i = 0; i < splits; i++) {");
		code.add("			unsigned int debugrandom = splitLines[i];");
		code.add("		}");
		code.add("	}");
		code.add("	return split_arr;");
		code.add("}");
		code.add("");
		code.add("//start is inclusive, stop is exclusive");
		code.add("struct vote_single split(unsigned int votes[V], unsigned int start, unsigned int stop) {");
		code.add("	static unsigned int sub_arr[V];");
		code.add("	");
		code.add("	for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("		sub_arr[i] = C;");
		code.add("	}");
		code.add("	");
		code.add("	if(start == stop) { //the sub array should be empty");
		code.add("		struct vote_single toReturn;");
		code.add("		");
		code.add("		for (int i = 0; i < V; i++) {");
		code.add("			toReturn.arr[i] = sub_arr[i];");
		code.add("		}");
		code.add("		");
		code.add("		return toReturn;");
		code.add("	} else {");
		code.add("");
		code.add("		for (int i = 0; i < V; i++) {");
		code.add("			if ((i >= start) && (i < stop)) {");
		code.add("				sub_arr[i - start] = votes[i];");
		code.add("			}");
		code.add("		}");
		code.add("	");
		code.add("		//struct vote_single toReturn = {sub_arr};");
		code.add("		");
		code.add("		struct vote_single toReturn;");
		code.add("		");
		code.add("		for (int i = 0; i < V; i++) {");
		code.add("			toReturn.arr[i] = sub_arr[i];");
		code.add("		}");
		code.add("		");
		code.add("		return toReturn;");
		code.add("	}");
		code.add("}");
		code.add("");
		code.add("//start is inclusive, stop is exclusive");
		code.add("//used for 2 dim arrays");
		code.add("struct vote_double split(unsigned int votes[V][C], unsigned int start, unsigned int stop) {");
		code.add("	static unsigned int sub_arr[V];");
		code.add("	");
		code.add("	for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("		for(int j = 0; j < C; j++) {");
		code.add("			sub_arr[i][j] = C;");
		code.add("		}");
		code.add("	}");
		code.add("	");
		code.add("	if(start == stop) { //the sub array should be empty");
		code.add("		struct vote_double toReturn;");
		code.add("		");
		code.add("		for (int i = 0; i < V; i++) {");
		code.add("			for(int j = 0; j < V; j++) {");
		code.add("				toReturn.arr[i][j] = sub_arr[i][j];");
		code.add("			}");
		code.add("		}");
		code.add("		");
		code.add("		return toReturn;");
		code.add("	} else {");
		code.add("");
		code.add("		for (int i = 0; i < V; i++) {");
		code.add("			if ((i >= start) && (i < stop)) {");
		code.add("				for(int j = 0; j < C; j++) {");
		code.add("					sub_arr[i - start][j] = votes[i][j];");
		code.add("				}");
		code.add("			}");
		code.add("		}");
		code.add("		");
		code.add("		struct vote_double toReturn;");
		code.add("		");
		code.add("		for (int i = 0; i < V; i++) {");
		code.add("			for(int j = 0; j < V; j++) {");
		code.add("				toReturn.arr[i][j] = sub_arr[i][j];");
		code.add("			}");
		code.add("		}");
		code.add("		");
		code.add("		return toReturn;");
		code.add("	}");
		code.add("}");
		code.add("}}");
		// end split array functions

		// concatination

		code.add(
				"struct vote_single concat(unsigned int votesOne[V], unsigned int sizeOne, unsigned int votesTwo[V], unsigned int sizeTwo) {");
		code.add("	static unsigned int sub_arr[V];");
		code.add("	");
		code.add("	for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("		sub_arr[i] = C;");
		code.add("	}");
		code.add("	");
		code.add("	for(int i = 0; i < sizeOne; i++) { //limit the size to the upper bound V");
		code.add("		if (i < V) {");
		code.add("			sub_arr[i] = votesOne[i];");
		code.add("		}");
		code.add("	}");
		code.add("	");
		code.add("	for(int i = 0; i < sizeTwo; i++) { //limit the size to the upper bound V");
		code.add("		if (sizeOne + i < V) {");
		code.add("			sub_arr[sizeOne + i] = votesTwo[i];");
		code.add("		}");
		code.add("	}");
		code.add("	");
		code.add("	struct vote_single toReturn;");
		code.add("		");
		code.add("	for (int i = 0; i < V; i++) {");
		code.add("		toReturn.arr[i] = sub_arr[i];");
		code.add("	}");
		code.add("		");
		code.add("	return toReturn;");
		code.add("	");
		code.add("}");
		code.add("");
		code.add(
				"struct vote_double concat(unsigned int votesOne[V][C], unsigned int sizeOne, unsigned int votesTwo[V], unsigned int sizeTwo) {");
		code.add("	static unsigned int sub_arr[V];");
		code.add("	");
		code.add("	for(int i = 0; i < V; i++) { //set all to C in the beginning");
		code.add("		for(int j = 0; j < C; j++) {");
		code.add("			sub_arr[i][j] = C;");
		code.add("		}");
		code.add("	}");
		code.add("	");
		code.add("	for(int i = 0; i < sizeOne; i++) { //limit the size to the upper bound V");
		code.add("		for(int j = 0; j < C; j++) {");
		code.add("			if (i < V) {");
		code.add("				sub_arr[i][j] = votesOne[i][j];");
		code.add("			}");
		code.add("		}");
		code.add("	}");
		code.add("	");
		code.add("	for(int i = 0; i < sizeTwo; i++) { //limit the size to the upper bound V");
		code.add("		for(int j = 0; j < C; j++) {");
		code.add("			if (sizeTwo + i < V) {");
		code.add("				sub_arr[i][j] = votesTwo[i][j];");
		code.add("			}");
		code.add("		}");
		code.add("	}");
		code.add("	");
		code.add("	struct vote_double toReturn;");
		code.add("		");
		code.add("	for (int i = 0; i < V; i++) {");
		code.add("		for (int j = 0; j < C; j++) {");
		code.add("			toReturn.arr[i][j] = sub_arr[i][j];");
		code.add("		}");
		code.add("	}");
		code.add("		");
		code.add("	return toReturn;");
		code.add("}");

		// end concatination
	}

	/**
	 * writes all lines of the header
	 * 
	 * @param code
	 *            the list in which the header should be written
	 * @return the finished header
	 */
	public static CodeArrayListBeautifier addHeader(CodeArrayListBeautifier code) {
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
		code.add(UnifiedNameContainer.getStruct_result() + " { unsigned int "
				+ UnifiedNameContainer.getResult_arr_name() + "[" + UnifiedNameContainer.getSeats() + "]; };"); // add a
																												// result
		// struct to be
		// returned in case
		// of a parliament

		code.add(UnifiedNameContainer.getStruct_stack_result() + " { unsigned int "
				+ UnifiedNameContainer.getResult_arr_name() + "[" + UnifiedNameContainer.getSeats() + "]; };"); // add a

		code.add("struct vote_single { unsigned int arr[V]; };");
		code.add("struct vote_double { unsigned int arr[V][C]; };"); // TODO make them use the UnifiedNameContainer

		// result
		// same for a stacked result for each party
		return code;
	}

	/**
	 * adds the main method the main method declares the boolean expression. In the
	 * main method the votingmethod is called
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

		// now the Post Properties can be checked
		addPostProperties(postAST);

		code.deleteTab();
		code.add("}");
	}

	/**
	 * this should be used to create the VarInitialisation within the main method.
	 */
	private void addSymbVarInitialisation() {
		List<SymbolicVariable> symbolicVariableList = preAndPostCondDesc.getSymbolicVariableList();
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
					// a Seat is a also an unsigned int.
					// The return of a votingmethod (an Array) gives the elected
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
	 * this adds the Code of the PreProperties. It uses a Visitor it creates
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

			String votesX = "unsigned int votes" + voteNumber;
			votesX = votesX + electionDesc.getContainer().getInputType().getInputString();
			code.add(votesX + ";");

			// String[] counter = { "counter_0", "counter_1", "counter_2", "counter_3" };
			String forTemplate = "for(unsigned int COUNTER = 0; COUNTER < UPPER; COUNTER++){";

			int listDepth = 0;

			for (int i = 0; i < electionDesc.getContainer().getInputType().getDimension(); i++) {
				String currentFor = forTemplate.replaceAll("COUNTER", "counter_" + listDepth);
				currentFor = currentFor.replaceAll("UPPER",
						electionDesc.getContainer().getInputType().getMaximalSize(listDepth) + voteNumber);
				code.add(currentFor);
				code.addTab();
				listDepth++;
			}
			String min = "" + electionDesc.getContainer().getInputType().getMinimalValue();

			if (electionDesc.getContainer().getInputType().hasVariableAsMinValue()) {
				min = min + voteNumber;
			}

			String max = "" + electionDesc.getContainer().getInputType().getMaximalValue();

			if (electionDesc.getContainer().getInputType().hasVariableAsMaxValue()) {
				max = max + voteNumber;
			}

			String votesElement = "votes" + voteNumber;
			for (int i = 0; i < listDepth; ++i) {
				votesElement += "[COUNTER]".replace("COUNTER", "counter_" + i);
			}

			String nondetInt = (votesElement + " = nondet_uint();");
			String voteDecl = ("assume((MIN <= " + votesElement + ") && (" + votesElement + " < MAX));");
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

			code = electionDesc.getContainer().getOutputType().addVotesArrayAndInit(code, voteNumber);
			code.add("");
		}

	}

	private BooleanExpListNode generateAST(String code) {
		FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(new ANTLRInputStream(code));
		CommonTokenStream ts = new CommonTokenStream(l);
		FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(ts);

		BooleanExpScope declaredVars = new BooleanExpScope();

		preAndPostCondDesc.getSymbolicVariableList().forEach((v) -> {
			declaredVars.addTypeForId(v.getId(), v.getInternalTypeContainer());
		});

		return translator.generateFromSyntaxTree(p.booleanExpList(), electionDesc.getContainer().getInputType(),
				electionDesc.getContainer().getOutputType(), declaredVars);
	}

	private List<String> getVotingResultCode(String[][] votingData) {
		// first create the declaration of the array:
		return electionDesc.getContainer().getInputType().getVotingResultCode(votingData);
	}
}