/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.EquivalencyNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ForAllNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.IntegerComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.NotNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.QuantorNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.AccessValueNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.AtPosExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.ElectExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.VoteExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.ConstantExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.IntegerNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.VoteSumForCandExp;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingListChangeContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.CandidateListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.VoteEquivalentsNode;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.VotingListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.VotingTupelChangeExpNode;
import edu.pse.beast.types.InternalTypeContainer;

/**
 * This is the visitor for the codeGeneration For every assert or assume it
 * should be called to visit the top node 1 time it will move down and generate
 * the code. to get the generated code, you have to use the method generateCode
 * You also have to set it either to Pre- or PostConditionMode.
 *
 * @author Niels
 */
public class CBMCCodeGenerationVisitor implements BooleanExpNodeVisitor {

	private String assumeOrAssert; // this String must always be "assume" or "assert". If it is not set it is null

	/*
	 * the following attributes are the counters for the variables in the code they
	 * make the variable names unique
	 */
	private int andNodeCounter;
	private int orNodeCounter;
	private int implicationNodeCounter;
	private int aquivalencyNodeCounter;
	private int forAllNodeCounter;
	private int thereExistsNodeCounter;
	private int notNodeCounter;
	private int comparisonNodeCounter;
	private int voteSumCounter;
	private int listlvl = 0;
	private int amtByPosVar = 0;

	private int numberVars;

	private Stack<String> variableNames; // stack of the variable names.
	private CodeArrayListBeautifier code; // object, that handels the generated code

	private int tmpIndex = 0;

	private final ElectionTypeContainer electionTypeContainer;

	/**
	 * this creates the visitor You should create 1 and only 1 visitor for every c.
	 * file you want to make you have to set it to pre- or post-property mode in
	 * order for it to function
	 * 
	 * @param electionTypeContainer
	 *
	 */
	public CBMCCodeGenerationVisitor(ElectionTypeContainer electionTypeContainer) {
		this.electionTypeContainer = electionTypeContainer;

		andNodeCounter = 0;
		orNodeCounter = 0;
		implicationNodeCounter = 0;
		aquivalencyNodeCounter = 0;
		forAllNodeCounter = 0;
		thereExistsNodeCounter = 0;
		notNodeCounter = 0;
		comparisonNodeCounter = 0;
		voteSumCounter = 0;
		numberVars = 0;
		listlvl = 0;
		amtByPosVar = 0;

		code = new CodeArrayListBeautifier();

	}

	private int getTmpIndex() {
		int tmp = tmpIndex;

		tmpIndex++;

		return tmp;
	}

	/**
	 * sets the visitor to preConditionMode. That means the top node will be assumed
	 * in the code
	 */
	public void setToPreConditionMode() {
		assumeOrAssert = "assume";
	}

	/**
	 * sets the visitor to postConditionMode. That means the top node will be
	 * asserted in the code
	 */
	public void setToPostConditionMode() {
		assumeOrAssert = "assert";
	}

	/**
	 * this generates the c-code for a propertydescription-statement
	 *
	 * @param node
	 *            this should be the top node of a statement
	 * @return the generated code
	 */
	public ArrayList<String> generateCode(BooleanExpressionNode node) {
		code = new CodeArrayListBeautifier();
		variableNames = new Stack<>();
		node.getVisited(this);
		return code.getCodeArrayList();
	}

	/**
	 * generates the code for an logical and node
	 *
	 * @param node
	 *            the visited and node
	 */
	@Override
	public void visitAndNode(LogicalAndNode node) {
		String varName = "and_" + andNodeCounter;
		andNodeCounter++;
		variableNames.push(varName);
		node.getLHSBooleanExpNode().getVisited(this);
		node.getRHSBooleanExpNode().getVisited(this);
		code.add("unsigned int " + varName + " = ((" + variableNames.pop() + ") && (" + variableNames.pop() + "));");
		testIfLast();
	}

	/**
	 * generates the code for logical an or node
	 *
	 * @param node
	 *            the visited or node
	 */
	@Override
	public void visitOrNode(LogicalOrNode node) {
		String varName = "or_" + orNodeCounter;
		orNodeCounter++;
		variableNames.push(varName);
		node.getLHSBooleanExpNode().getVisited(this);
		node.getRHSBooleanExpNode().getVisited(this);
		code.add("unsigned int " + varName + " = ((" + variableNames.pop() + ") || (" + variableNames.pop() + "));");
		testIfLast();
	}

	/**
	 * generates the code for the Implication Node
	 *
	 * @param node
	 *            the visited implication node
	 */
	@Override
	public void visitImplicationNode(ImplicationNode node) {
		String varName = "implication_" + implicationNodeCounter;
		implicationNodeCounter++;
		variableNames.push(varName);
		node.getLHSBooleanExpNode().getVisited(this);
		node.getRHSBooleanExpNode().getVisited(this);
		String rhsName = variableNames.pop();
		String lhsName = variableNames.pop();
		code.add("unsigned int " + varName + " = (!(" + lhsName + ") || (" + rhsName + "));");
		testIfLast();
	}

	/**
	 * generates the code for an EquivalencyNode
	 *
	 * @param node
	 *            equivalencz node
	 */
	@Override
	public void visitEquivalencyNode(EquivalencyNode node) {
		String varName = "aquivalency_" + aquivalencyNodeCounter;
		aquivalencyNodeCounter++;
		variableNames.push(varName);
		node.getLHSBooleanExpNode().getVisited(this);
		node.getRHSBooleanExpNode().getVisited(this);
		String rhs = variableNames.pop();
		String lhs = variableNames.pop();
		code.add("unsigned int " + varName + " = (((" + lhs + ") && (" + rhs + ")) || (!(" + lhs + ") && (!" + rhs
				+ ")));");
		testIfLast();
	}

	/**
	 * generates the code for an ForAllNode
	 *
	 * @param node
	 *            the visited node
	 */
	@Override
	public void visitForAllNode(ForAllNode node) {
		String varName = "forAll_" + forAllNodeCounter;
		forAllNodeCounter++;
		variableNames.push(varName);
		code.add("unsigned int " + varName + " = 1;");
		String max = "";
		max = getMaxString(node);
		String tempString = "for(unsigned int SYMBVAR = 0; SYMBVAR < MAX && FORALL; SYMBVAR++) {";
		tempString = tempString.replaceAll("SYMBVAR", node.getDeclaredSymbolicVar().getId());
		tempString = tempString.replace("MAX", max);
		tempString = tempString.replace("FORALL", varName);
		code.add(tempString);
		code.addTab();
		node.getFollowingExpNode().getVisited(this);
		code.add(varName + " = " + variableNames.pop() + ";");
		code.deleteTab();
		code.add("}");
		testIfLast();
	}

	private String getMaxString(QuantorNode node) {
		String max;
		switch (node.getDeclaredSymbolicVar().getInternalTypeContainer().getInternalType()) {
		case VOTER:
			max = UnifiedNameContainer.getVoter();
			break;
		case CANDIDATE:
			max = UnifiedNameContainer.getCandidate();
			break;
		case SEAT:
			max = UnifiedNameContainer.getSeats();
			break;
		default:
			throw new AssertionError(node.getDeclaredSymbolicVar().getInternalTypeContainer().getInternalType().name());
		}
		return max;
	}

	/**
	 * generates the code to an ThereExistsNode
	 *
	 * @param node
	 *            the visited node
	 */
	@Override
	public void visitThereExistsNode(ThereExistsNode node) {
		String varName = "thereExists_" + thereExistsNodeCounter;
		thereExistsNodeCounter++;
		variableNames.push(varName);
		code.add("unsigned int " + varName + " = 0;");
		String max = getMaxString(node);
		String tempString = "for(unsigned int SYMBVAR = 0; SYMBVAR < MAX && !THEREEXISTS; SYMBVAR++) {";
		tempString = tempString.replaceAll("SYMBVAR", node.getDeclaredSymbolicVar().getId());
		tempString = tempString.replaceAll("MAX", max);
		tempString = tempString.replaceAll("THEREEXISTS", varName);
		code.add(tempString);
		code.addTab();
		node.getFollowingExpNode().getVisited(this);
		code.add(varName + " = " + variableNames.pop() + ";");
		code.deleteTab();
		code.add("}");
		testIfLast();
	}

	/**
	 * generates the code for a notNode
	 *
	 * @param node
	 *            the visited node
	 */
	@Override
	public void visitNotNode(NotNode node) {
		if (node.getNegatedExpNode() instanceof NotNode) {
			NotNode node2 = (NotNode) node.getNegatedExpNode();
			node2.getNegatedExpNode().getVisited(this);
			return;
		}
		String varName = "not_" + notNodeCounter;
		notNodeCounter++;
		variableNames.push(varName);
		node.getNegatedExpNode().getVisited(this);
		code.add("unsigned int " + varName + " = !(" + variableNames.pop() + ");");
		testIfLast();
	}

	/**
	 * generates the code for the comparison of 2 types which aren't integers These
	 * types can be lists which may have diferrent depth and might be acessed by
	 * variables.
	 * 
	 * @param node
	 *            the node to visit
	 */
	@Override
	public void visitComparisonNode(ComparisonNode node) {
		String varName = "comparison_" + comparisonNodeCounter;
		comparisonNodeCounter++;
		variableNames.push(varName);

		// let the visitor generate the code for the left value
		// which will be compared. This is also used to find out
		// how many list levels need to be compared, meaning how
		// many for loops need to be generated
		listlvl = 0;
		for (InternalTypeContainer cont = node.getLHSBooleanExpNode().getInternalTypeContainer(); cont
				.isList(); cont = cont.getListedType()) {
			listlvl++;
		}
		node.getLHSBooleanExpNode().getVisited(this);
		int lhslistLevel = listlvl;

		listlvl = 0;
		for (InternalTypeContainer cont = node.getRHSBooleanExpNode().getInternalTypeContainer(); cont
				.isList(); cont = cont.getListedType()) {
			listlvl++;
		}

		node.getRHSBooleanExpNode().getVisited(this);
		int rhslistLevel = listlvl;

		int maxListLevel = lhslistLevel > rhslistLevel ? lhslistLevel : rhslistLevel;
		InternalTypeContainer cont = lhslistLevel > rhslistLevel
				? node.getLHSBooleanExpNode().getInternalTypeContainer()
				: node.getRHSBooleanExpNode().getInternalTypeContainer();

		while (cont.getListLvl() != maxListLevel) {
			cont = cont.getListedType();
		}

		String internCode = "unsigned int BOOL = 1;";
		internCode = internCode.replace("BOOL", varName);
		code.add(internCode);
		ArrayList<String> counter = new ArrayList<>();

		// generate the for loops used to test the two types..
		//
		for (int i = 0; i < maxListLevel; ++i) {
			String max = "";
			String countingVar = "count_" + i;
			counter.add(countingVar);
			if (null != cont.getAccesTypeIfList()) {
				switch (cont.getAccesTypeIfList()) {
				case VOTER:
					max = UnifiedNameContainer.getVoter();
					break;
				case CANDIDATE:
					max = UnifiedNameContainer.getCandidate();
					break;
				case SEAT:
					max = UnifiedNameContainer.getSeats();
					break;
				default:
					break;
				}
			}
			String loop = "for(unsigned int VAR = 0; VAR < MAX && BOOL; ++VAR) {";
			loop = loop.replaceAll("VAR", countingVar);
			loop = loop.replaceAll("MAX", max);
			loop = loop.replaceAll("BOOL", varName);
			code.add(loop);
			code.addTab();
			cont = cont.getListedType();
		}
		String rhs = variableNames.pop();
		String lhs = variableNames.pop();
		internCode = varName + " = " + lhs;
		for (int i = 0; i < lhslistLevel; ++i) {
			internCode += "[VAR]".replace("VAR", counter.get(i));
		}
		internCode += " " + node.getComparisonSymbol().getCStringRep() + " ";
		internCode += rhs;
		for (int i = 0; i < rhslistLevel; ++i) {
			internCode += "[VAR]".replace("VAR", counter.get(i));
		}
		code.add(internCode + ";");
		for (int i = 0; i < maxListLevel; ++i) {
			code.deleteTab();
			code.add("}");
		}
		testIfLast();
	}

	@Override
	public void visitSymbVarExp(SymbolicVarExp exp) {
		variableNames.push(exp.getSymbolicVar().getId());
	}

	@Override
	public void visitConstExp(ConstantExp exp) {
		variableNames.push(exp.getConstant());
	}

	@Override
	public void visitElectExp(ElectExp exp) {
		visitAcessingNodesReverseOrder(exp);

		String tempCode = "electNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
		for (int i = 0; i < exp.getAccessingVars().length; ++i) {
			tempCode += "[VAR]".replace("VAR", variableNames.pop());
			listlvl--;
		}
		variableNames.push(tempCode);
	}

	@Override
	public void visitVoteExp(VoteExp exp) {
		visitAcessingNodesReverseOrder(exp);

		String tempCode = "votesNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
		for (int i = 0; i < exp.getAccessingVars().length; ++i) {
			tempCode += "[VAR]".replace("VAR", variableNames.pop());
			listlvl--;
		}
		variableNames.push(tempCode);
	}

	private void visitAcessingNodesReverseOrder(AccessValueNode exp) {
		for (int i = exp.getAccessingVars().length - 1; i >= 0; i--) {
			exp.getAccessingVars()[i].getVisited(this);
		}
	}

	@Override
	public void visitVoteSumExp(VoteSumForCandExp exp, boolean unique) {
		exp.getAccesingVariable().getVisited(this);
		String funcCallTemplate = "unsigned int VARNAME = voteSumForCandidate" + (unique ? "Unique" : "")
				+ "(votesNUM, " + UnifiedNameContainer.getVoter() + voteSumCounter + ", CAND);";
		String counter = "voteSumExp_" + voteSumCounter;
		voteSumCounter++;
		funcCallTemplate = funcCallTemplate.replaceAll("VARNAME", counter);
		funcCallTemplate = funcCallTemplate.replaceAll("NUM", String.valueOf(exp.getVoteNumber()));
		funcCallTemplate = funcCallTemplate.replaceAll("CAND", variableNames.pop());
		code.add(funcCallTemplate);
		variableNames.push(counter);
	}

	@Override
	public void visitIntegerNode(IntegerNode integerNode) {
		String varName = getNewNumberVariableName();
		code.add("unsigned int " + varName + " = " + integerNode.getHeldInteger() + ";");
		variableNames.push(varName);
	}

	private String getNewNumberVariableName() {
		String newNumberVariable = "integerVar_" + numberVars;
		numberVars++;
		return newNumberVariable;
	}

	@Override
	public void visitIntegerComparisonNode(IntegerComparisonNode listComparisonNode) {
		listComparisonNode.getLHSBooleanExpNode().getVisited(this);
		listComparisonNode.getRHSBooleanExpNode().getVisited(this);

		String varNameDecl = "integer_comp_" + comparisonNodeCounter;
		comparisonNodeCounter++;
		String rhs = variableNames.pop();
		String lhs = variableNames.pop();

		String comparisonString = "unsigned int " + varNameDecl + " = " + lhs + " "
				+ listComparisonNode.getComparisonSymbol().getCStringRep() + " " + rhs + ";";

		code.add(comparisonString);
		variableNames.push(varNameDecl);
		testIfLast();
	}

	@Override
	public void visitBinaryIntegerValuedNode(BinaryIntegerValuedNode binaryIntegerValuedNode) {
		binaryIntegerValuedNode.lhs.getVisited(this);
		binaryIntegerValuedNode.rhs.getVisited(this);
		String rhs = variableNames.pop();
		String lhs = variableNames.pop();
		String varname = getNewNumberVariableName();
		code.add("unsigned int " + varname + " = " + lhs + " " + binaryIntegerValuedNode.getRelationSymbol() + " " + rhs
				+ ";");
		variableNames.push(varname);
	}

	@Override
	public void visitAtPosNode(AtPosExp atPosExp) {
		atPosExp.getIntegerValuedExpression().getVisited(this);
		String varName = getAtPosVarName(atPosExp);
		String template = "unsigned int VAR = NUMBER;";
		template = template.replace("VAR", varName);
		template = template.replace("NUMBER", variableNames.pop());
		code.add(template);
		variableNames.push(varName);
	}

	private String getAtPosVarName(AtPosExp atPosExp) {
		int amtByPos = amtByPosVar;
		amtByPosVar++;
		return atPosExp.getInternalTypeContainer().getInternalType().toString().toLowerCase() + "AtPos_" + amtByPos;
	}

	private void testIfLast() {
		if (variableNames.size() == 1) {
			if (assumeOrAssert != null) {
				code.add(assumeOrAssert + "(" + variableNames.pop() + ");");
			} else {
				ErrorLogger.log("The CodeGeneration Visitor wasn't set to a proper mode");
			}
		}
	}

	@Override
	public void visitVotingTupleChangeNode(VotingTupelChangeExpNode node) {
		List<String> tupelVotes = new ArrayList<String>();

		tupelVotes.add(node.tuple.getChild(1).getText()); // add the first element to the list

		// extract the vote elements one by one. The terminal symbol is saved in
		// child(1).
		// if there is another non terminal, it is saved in child(2), else, that will be
		// null
		ParseTree subNode = node.tuple.getChild(2);
		do {
			tupelVotes.add(subNode.getChild(1).getText());

			subNode = subNode.getChild(2);
		} while (subNode != null);

		ParseTree voteEquivalent = node.splitExp.getChild(0); // there is only one child

		int voteTmpIndex = getTmpIndex(); // save the index the new vote variable will have, when it will be finished

		String voteInput = "";

		if (!(voteEquivalent instanceof TerminalNodeImpl)) { // we have to go deeper

			voteInput = "unsigned int tmp_vote" + getTmpIndex() + " "
					+ electionTypeContainer.getInputType().getInputString();

			VoteEquivalentsNode equiNode = new VoteEquivalentsNode(node.splitExp.voteEquivalents(), voteInput);
			equiNode.getVisited(this);
		} else { // we only have one vote to split
			voteInput = node.splitExp.voteEquivalents().Vote().getText();
		}

		// we now have only one voting array to care about, and its name is written in
		// voteInput;

		code.add("unsigned int splits = " + (tupelVotes.size() - 1)); // determine how many splits we need

		code.add("unsigned int *splitLines;");
		code.add("splitLines = getRandomSplitLines(splits, V);"); // TODO is V correct here?

		int tupelSize = tupelVotes.size();

		// TODO extract into input type
		if (electionTypeContainer.getInputType().getDimension() == 1) {

			code.add("unsigned int last_split = 0;");

			for (int i = 0; i < tupelSize - 1; i++) { // split the array and extract the votes for all but one tupel
														// element
				code.add("struct vote_single tmp = split( " + voteInput + ", last_split, splitLines[" + i + "]);");

				code.add("for(int i = 0; i < V; i++) {");
				code.add("	" + tupelVotes.get(i) + "[i] = tmp.arr[i];");
				code.add("}");

				// set the size value for this array to its new size:

				code.add("V" + tupelVotes.get(i).substring("VOTES".length()) + " = ");

				code.add("last_split = splitLines[" + i + "];");
			}

			code.add("struct vote_single tmp = split( " + voteInput + ", last_split, V);"); // TODO is V correct here?

			code.add("for(int i = 0; i < V; i++) {");
			code.add("	" + tupelVotes.get(tupelSize - 1) + "[i] = tmp.arr[i];");
			code.add("}");

		} else { // we have 2 dim
			code.add("unsigned int last_split = 0;");

			for (int i = 0; i < tupelSize - 1; i++) { // split the array and extract the votes for all but one tupel
														// element
				code.add("struct vote_double tmp = split( " + voteInput + ", last_split, splitLines[" + i + "]);");

				code.add("for(int i = 0; i < V; i++) {");
				code.add("	for(int j = 0; j < C; i++) {");
				code.add("	" + tupelVotes.get(i) + "[i][j] = tmp.arr[i][j];");
				code.add("	}");
				code.add("}");

				// set the size value for this array to its new size:

				code.add("V" + tupelVotes.get(i).substring("VOTES".length()) + " = ");

				code.add("last_split = splitLines[" + i + "];");
			}

			code.add("struct vote_double tmp = split( " + voteInput + ", last_split, V);"); // TODO is V correct here?

			code.add("for(int i = 0; i < V; i++) {");
			code.add("	for(int j = 0; j < C; i++) {");
			code.add("	" + tupelVotes.get(tupelSize - 1) + "[i][j] = tmp.arr[i][j];");
			code.add("	}");
			code.add("}");
		}
		// finished the split
	}

	@Override
	public void visitVotingListChangeNode(VotingListChangeExpNode node) {
		String voteToSaveInto = node.vote.getText();

		VotingListChangeContentContext contentContext = node.votingListChangeContent;

		// we save the result of the sub computation in this variable
		String voteInput = "unsigned int tmp_vote" + getTmpIndex() + " "
				+ electionTypeContainer.getInputType().getInputString();

		if (contentContext.getChild(0) instanceof ConcatenationExpContext) { // we have a concatenation

			ConcatenationExpNode concNode = new ConcatenationExpNode(contentContext.concatenationExp(), voteInput);
			concNode.getVisited(this);
		} else { // we have a permutation

			PermutationExpNode permNode = new PermutationExpNode(contentContext.permutationExp(), voteInput);
			permNode.getVisited(this);
		}

		// the result of the sub computation is now saved in "voteInput"

		// TODO extract this logic into InputType
		if (electionTypeContainer.getInputType().getDimension() == 1) {
			code.add("for(int i = 0; i < V; i++) {");
			code.add(voteToSaveInto + "[i] = " + voteInput + "[i];");
			code.add("}");
		} else { // we have two dimensions
			code.add("for(int i = 0; i < V; i++) {");
			code.add("	for(int j = 0; j < C; j++) {");
			code.add(voteToSaveInto + "[i][j] = " + voteInput + "[i][j];");
			code.add("	}");
			code.add("}");
		}

	}

	@Override
	public void visitCandidateListChangeExpNode(CandidateListChangeExpNode node) {
		// TODO Auto-generated method stub
		System.out.println("still to do");

	}

	@Override
	public void visitVoteEquivalentsNode(VoteEquivalentsNode node) {

		String voteInput = "";

		if (node.voteEquivalentsContext.getChild(0) instanceof PermutationExpContext) { // we have a permutation

			voteInput = "unsigned int tmp_vote" + getTmpIndex() + " "
					+ electionTypeContainer.getInputType().getInputString();

			PermutationExpNode permNode = new PermutationExpNode(node.voteEquivalentsContext.permutationExp(),
					voteInput);
			permNode.getVisited(this);
		} else if (node.voteEquivalentsContext.getChild(0) instanceof ConcatenationExpContext) { // we have a
																									// concatenatio

			voteInput = "unsigned int tmp_vote" + getTmpIndex() + " "
					+ electionTypeContainer.getInputType().getInputString();

			ConcatenationExpNode concNode = new ConcatenationExpNode(node.voteEquivalentsContext.concatenationExp(),
					voteInput);

			concNode.getVisited(this);

		} else { // we just have a simple vote
			voteInput = node.voteEquivalentsContext.Vote().getText();
		}

		// now a created voting array is saved in "voteInput"

		String voteToSaveInto = node.toOutput;

		// TODO extract this logic into InputType
		if (electionTypeContainer.getInputType().getDimension() == 1) {
			code.add("for(int i = 0; i < V; i++) {");
			code.add(voteToSaveInto + "[i] = " + voteInput + "[i];");
			code.add("}");
		} else { // we have two dimensions
			code.add("for(int i = 0; i < V; i++) {");
			code.add("	for(int j = 0; j < C; j++) {");
			code.add(voteToSaveInto + "[i][j] = " + voteInput + "[i][j];");
			code.add("	}");
			code.add("}");
		}
		// we fullfilled the task of writing the result of the lower var into the given
		// output var

	}

	@Override
	public void visitConcatenationExpNode(ConcatenationExpNode node) {
		int offset = 0;

		if (node.concatenationExpContext.getChild(0).getText().equals('(')) { // we have to skip one child if we start
																				// with "("
			offset = 1;
			System.out.println("hat eine klammer!!");
		}

		ConcatenationExpContext context = node.concatenationExpContext;

		ParseTree firstChild = context.getChild(0 + offset);

		String voteInputOne = "unsigned int tmp_vote" + getTmpIndex() + " "
				+ electionTypeContainer.getInputType().getInputString();
		;

		if (firstChild instanceof VoteEquivalentsContext) { // we have a vote equivalent

			List<VoteEquivalentsContext> list = context.voteEquivalents();

			VoteEquivalentsNode voteEqNode = new VoteEquivalentsNode(context.voteEquivalents(0), voteInputOne);

			voteEqNode.getVisited(this);

		} else if (firstChild instanceof PermutationExpContext) { // we have a permutation

			PermutationExpNode permNode = new PermutationExpNode(context.permutationExp(), voteInputOne);

			permNode.getVisited(this);

		} else { // we have just a single vote
			voteInputOne = context.Vote().getText();
		}

		// do the same thing for the other non terminal

		ParseTree secondChild = context.getChild(2 + offset);

		String voteInputTwo = "unsigned int tmp_vote" + getTmpIndex() + " "
				+ electionTypeContainer.getInputType().getInputString();
		;

		// we already know that it can only be a voteEquivalent

		VoteEquivalentsContext test0 = context.voteEquivalents(0);

		VoteEquivalentsContext test1 = context.voteEquivalents(1);

		VoteEquivalentsContext test2 = context.voteEquivalents(2);

		VoteEquivalentsContext test3 = context.voteEquivalents(3);

		VoteEquivalentsNode voteEqNodeTwo = new VoteEquivalentsNode(context.voteEquivalents(1), voteInputTwo);

		List<VoteEquivalentsContext> list = context.voteEquivalents();

		voteEqNodeTwo.getVisited(this);

		// we now have both sides ready in voteInputOne and voteInputTwo

		String toSaveInto = node.voteOutput;

		// TODO extract into input type
		if (electionTypeContainer.getInputType().getDimension() == 1) {

			code.add("struct vote_single tmp = split(" + voteInputOne + ", 5, " + voteInputTwo + ", 5);");

			//result is now in tmp
			
			code.add("for(int i = 0; i < V; i++ {");
			code.add(toSaveInto + "[i] = tmp[i]");
			code.add("}");
			

		} else { // we have 2 dim
			code.add("struct vote_double tmp = split(" + voteInputOne + ", 5, " + voteInputTwo + ", 5);");

			//result is now in tmp
			
			code.add("for(int i = 0; i < V; i++ {");
			code.add("for(int j = 0; j < C; j++) {"); 
			code.add(toSaveInto + "[i] = tmp[i]");
			code.add("}");
			code.add("}");
		}

		
		//the result is now saved in "toSaveInto"
	}

	@Override
	public void visitPermutationExpNode(PermutationExpNode permutationExpNode) {
		// TODO Auto-generated method stub

	}
}
