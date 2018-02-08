/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.electionSimulator.ElectionSimulation;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
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
	private final ElectionDescription electionDescription;
	private final PreAndPostConditionsDescription PreAndPostConditionsDescription;
	private final FormalPropertySyntaxTreeToAstTranslator translator;
	private final CBMCCodeGenerationVisitor visitor;
	private final ElectionTypeContainer types;
	private final CCodeHelper cCodeHelper;
	private int numberOfTimesVoted; // this number should be the number of
									// rounds of votes the Properties compare.
	private final boolean isMargin;
	private int margin;
	private List<String> origResult;
	private boolean isTest;

	/**
	 * After the build the code is fully generated and can be acquired by getCode();
	 *
	 * @param electionDescription
	 *            the lectionDecription that holds the code that describes the
	 *            voting method. that code will be merged with the generated code
	 * @param PreAndPostConditionsDescription
	 *            the Descriptions that will be used to generate the C-Code for CBMC
	 */
	public CBMCCodeGenerator(ElectionDescription electionDescription,
			PreAndPostConditionsDescription PreAndPostConditionsDescription) {

		this.translator = new FormalPropertySyntaxTreeToAstTranslator();
		this.electionDescription = electionDescription;
		this.PreAndPostConditionsDescription = PreAndPostConditionsDescription;
		code = new CodeArrayListBeautifier();
		types = electionDescription.getContainer();
		this.visitor = new CBMCCodeGenerationVisitor();
		cCodeHelper = new CCodeHelper();
		this.isMargin = false;
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
	public CBMCCodeGenerator(ElectionDescription electionDescription,
			PreAndPostConditionsDescription PreAndPostConditionsDescription, int margin, List<String> origResult,
			boolean isTest) {

		this.translator = new FormalPropertySyntaxTreeToAstTranslator();
		this.electionDescription = electionDescription;
		this.PreAndPostConditionsDescription = PreAndPostConditionsDescription;
		code = new CodeArrayListBeautifier();
		types = electionDescription.getContainer();
		this.visitor = new CBMCCodeGenerationVisitor();
		cCodeHelper = new CCodeHelper();
		this.margin = margin;
		this.origResult = origResult;
		this.isTest = isTest;

		this.isMargin = true;
		generateCodeMargin();

	}

	/**
	 *
	 * @return returns the generated code
	 */
	public ArrayList<String> getCode() {
		return code.getCodeArrayList();
	}

	private void generateCodeCheck() {

		addHeader();
		addVoteSumFunc(false);
		addVoteSumFunc(true);

		code.add("//Code of the user");
		ArrayList<String> electionDescriptionCode = new ArrayList<>();
		electionDescriptionCode.addAll(electionDescription.getCode());
		code.addArrayList(electionDescriptionCode);

		addMainMethod();

	}

	private void generateCodeMargin() { // we want to create code for a margin
										// computation or just a test run

		// boolean multiOut = electionDescription.getOutputType().getOutputID() ==
		// ElectionOutputTypeIds.CAND_PER_SEAT;

		String[][] votingData = ElectionSimulation.getVotingData();

		// add the header and the voting data
		addMarginHeaders(votingData);

		// add the code the user wrote (e.g the election function)
		code.addAll(BEASTCommunicator.getCentralObjectProvider().getElectionDescriptionSource().getElectionDescription()
				.getCode());

		// add the code which defines the votes
		code.addAll(getVotingResultCode(votingData));

		switch (ElectionSimulation.getMode()) {
		case compileAndRunCBMC:

			addMarginMainTest();
			break;

		case searchMinDiffAndShowCBMC:

			if (isTest) {
				addMarginMainTest();
			} else {
				addMarginMainCheck(margin, origResult);
			}

			break;

		default:

			System.err.println("unknown mode used");

			break;
		}

	}

	private void addMarginMainCheck( int margin, List<String> origResult) {
		// we add the margin which will get computed by the model checker
		code.add("#ifndef MARGIN\n #define MARGIN " + margin + "\n #endif");
		// we also add the original result, which is calculated by compiling the
		// program and running it

		boolean singleOut = electionDescription.getContainer().getOutputType().isOutputOneCandidate();
		
		if (singleOut) {
			code.addAll(getLastResultCode(origResult.get(0)));
		} else {
			code.addAll(getLastResultCode(origResult));
		}

		// add the main methode for the margin computation
		code = electionDescription.getContainer().getInputType().addMarginMainCheck(code, margin, origResult);

		// add the verify methode:
		// taken and adjusted from the paper:
		// https://formal.iti.kit.edu/~beckert/pub/evoteid2016.pdf

		code = electionDescription.getContainer().getOutputType().addMarginVerifyCheck(code);

		// not used lines ( I think) //TODO if they really aren't needed, delete
		// them
		// code.add("new_votes1[i] = ORIG_VOTES[i] + diff[i];");
		// code.add("if (0 < diff[i]) pos_diff += diff[i];");
		// code.add("total_diff += diff[i];");
		// code.add("}");
		// code.add("__CPROVER_assume (pos_diff ≤ MARGIN);");
		// code.add("__CPROVER_assume (total_diff == 0);");
		// code.add("int *result = voting(new_votes1);");
		// code.add("assert (equals(result, ORIG_RESULT));");
		// code.add("}");

		// add the main methode
		code.add("int main() {");
		code.addTab();
		code.add("verify();");
		code.deleteTab();
		code.add("}");
	}

	private void addMarginHeaders(String[][] votingData) {

		// add the headers CBMC needs;

		addHeader();

		// define some pre processor values
		code.add("#ifndef V\n #define V " + votingData.length + "\n #endif");
		code.add("#ifndef C\n #define C " + votingData[0].length + "\n #endif");
		code.add("#ifndef S\n #define S " + votingData[0].length + "\n #endif");
	}

	private void addMarginMainTest() {

		int voteNumber = 1; // because we only have one vote, we hardcode the
							// value one here

		code = electionDescription.getContainer().getOutputType().addMarginMainTest(code, voteNumber);
	}

	private void addVoteSumFunc(boolean unique) {
		String input = "unsigned int arr" + electionDescription.getContainer().getInputType().getInputString();
		code.add("unsigned int voteSumForCandidate" + (unique ? "Unique" : "")
				+ "(INPUT, unsigned int candidate) {".replace("INPUT", input));
		code.addTab();
		code.add("unsigned int sum = 0;");
		code.add("for(unsigned int i = 0; i < V; ++i) {");
		code.addTab();
		
		//add the specific code which differs for different input types
		code = electionDescription.getContainer().getInputType().getCodeForVoteSum(code, unique);

		code.deleteTab();
		code.add("}");
		code.add("return sum;");
		code.deleteTab();
		code.add("}");
	}

	// maybe add something that let's the user use imports
	private void addHeader() {
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
		code.add("struct result { unsigned int arr[S]; };"); // add a result
																// struct to be
																// returned in case
																// of a parliament
	}

	/**
	 * adds the main method the main method declares the boolean expression. In the
	 * main method the votingmethod is called
	 */
	private void addMainMethod() {

		code.add("int main(int argc, char *argv[]) {");
		code.addTab();

		// first the Variables have to be Initialized
		addSymbVarInitialisation();

		// generating the pre and post AbstractSyntaxTrees
		BooleanExpListNode preAST = generateAST(
				PreAndPostConditionsDescription.getPreConditionsDescription().getCode());
		BooleanExpListNode postAST = generateAST(
				PreAndPostConditionsDescription.getPostConditionsDescription().getCode());

		initializeNumberOfTimesVoted(preAST, postAST);

		addVotesArrayAndElectInitialisation();

		// the the PreProperties must be definied
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
		List<SymbolicVariable> symbolicVariableList = PreAndPostConditionsDescription.getSymbolicVariableList();
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
				code.addArrayList(visitor.generateCode(booleanNode));
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
				code.addArrayList(visitor.generateCode(booleanNode));
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

			String votesX = "unsigned int votes" + voteNumber;
			votesX = votesX + electionDescription.getContainer().getInputType().getArrayType();
			code.add(votesX + ";");

			String[] counter = { "counter_0", "counter_1", "counter_2", "counter_3" };
			String forTemplate = "for(unsigned int COUNTER = 0; COUNTER < MAX; COUNTER++){";

			int listDepth = 0;

			for (int i = 0; i < electionDescription.getContainer().getInputType().getDimension(); i++) {
				String currentFor = forTemplate.replaceAll("COUNTER", counter[listDepth]);
				currentFor = currentFor.replaceAll("MAX", electionDescription.getContainer().getInputType()
						.getMaximalValue(electionDescription.getContainer()));
				code.add(currentFor);
				code.addTab();
				listDepth++;
			}
			String min = "" + electionDescription.getContainer().getLowerBound();

			String max = "" + electionDescription.getContainer().getUpperBound();

			String votesElement = "votes" + voteNumber;
			for (int i = 0; i < listDepth; ++i) {
				votesElement += "[COUNTER]".replace("COUNTER", counter[i]);
			}

			String nondetInt = (votesElement + " = nondet_uint();");
			String voteDecl = ("assume((MIN <= " + votesElement + ") && (" + votesElement + " < MAX));");
			voteDecl = voteDecl.replace("MIN", min);
			voteDecl = voteDecl.replace("MAX", max);

			code.add(nondetInt);
			code.add(voteDecl);

			// if we need to add something extra
			code = electionDescription.getContainer().getInputType().addVotesArrayAndInit(code, voteNumber);

			// close the function
			for (int i = 0; i < listDepth; ++i) {
				code.deleteTab();
				code.add("}");
			}

		}

	}

	private BooleanExpListNode generateAST(String code) {
		FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(new ANTLRInputStream(code));
		CommonTokenStream ts = new CommonTokenStream(l);
		FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(ts);

		BooleanExpScope declaredVars = new BooleanExpScope();

		PreAndPostConditionsDescription.getSymbolicVariableList().forEach((v) -> {
			declaredVars.addTypeForId(v.getId(), v.getInternalTypeContainer());
		});

		return translator.generateFromSyntaxTree(p.booleanExpList(), electionDescription.getContainer().getInputType(),
				electionDescription.getContainer().getOutputType(), declaredVars);
	}

	private List<String> getVotingResultCode(String[][] votingData) {
		// first create the declaration of the array:
		return electionDescription.getContainer().getInputType().getVotingResultCode(votingData);
	}

	private List<String> getLastResultCode(List<String> origResult) {

		List<String> dataAsArray = new ArrayList<String>();

		// first create the declaration of the array:
		String declaration = "";

		declaration = "int ORIG_RESULT[" + origResult.size() + "] = {";

		dataAsArray.add(declaration);

		String tmp = ""; // saves the amount of votes this seat got
		for (int i = 0; i < origResult.size(); i++) {
			if (i < origResult.size() - 1) {
				tmp = tmp + origResult.get(i) + ",";
			} else {
				tmp = tmp + origResult.get(i);
			}
		}
		dataAsArray.add(tmp);

		dataAsArray.add("};");

		return dataAsArray;
	}

	private List<String> getLastResultCode(String origResult) {

		List<String> dataAsArray = new ArrayList<String>();

		// first create the declaration of the array:
		String declaration = "";

		declaration = "int ORIG_RESULT = " + origResult + ";";

		dataAsArray.add(declaration);

		return dataAsArray;
	}
}