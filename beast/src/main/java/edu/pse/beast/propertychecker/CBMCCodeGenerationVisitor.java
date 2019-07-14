package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.EquivalencyNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntegerComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntersectTypeExpNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.NotNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.QuantifierNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.AccessValueNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.AtPosExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.ConstantExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.VoteSumForCandExp;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingListChangeContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.CandidateListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VoteEquivalentsNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingTupelChangeExpNode;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.outputplugins.CandidateList;

/**
 * This is the visitor for the codeGeneration For every assert or assume it
 * should be called to visit the top node 1 time it will move down and generate
 * the code. to get the generated code, you have to use the method generateCode
 * You also have to set it either to Pre- or PostConditionMode.
 *
 * @author Niels Hanselmann
 */
public class CBMCCodeGenerationVisitor implements BooleanExpNodeVisitor {
	/**
	 * This String must always be "assume" or "assert". If it is not set it is null.
	 */
	private String assumeOrAssert;

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

	/**
	 * Stack of the variable names.
	 */
	private Stack<String> variableNames;

	/**
	 * Object that handles the generated code.
	 */
	private CodeArrayListBeautifier code;

	private int tmpIndex = 0;
	private final ElectionTypeContainer electionTypeContainer;
	private boolean post;

	/**
	 * This creates the visitor. You should create one and only one visitor for
	 * every c-file you want to make. You must set it to pre- or post-property mode
	 * in order for it to function.
	 *
	 * @param electionTypeContainer the election type container.
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

	private synchronized int getTmpIndex() {
		int tmp = tmpIndex;
		tmpIndex++;
		return tmp;
	}

	/**
	 * Sets the visitor to preConditionMode. That means the top node will be assumed
	 * in the code.
	 */
	public void setToPreConditionMode() {
		assumeOrAssert = "assume";
		this.post = true; // refactor this later
	}

	/**
	 * Sets the visitor to postConditionMode. That means the top node will be
	 * asserted in the code.
	 */
	public void setToPostConditionMode() {
		assumeOrAssert = "assert";
		this.post = true;
	}

	/**
	 * This generates the c-code for a property description-statement.
	 *
	 * @param node this should be the top node of a statement
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
	 * @param node the visited and node
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
	 * Generates the code for logical an or node.
	 *
	 * @param node the visited or node
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
	 * Generates the code for the Implication Node.
	 *
	 * @param node the visited implication node
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
	 * Generates the code for an EquivalencyNode.
	 *
	 * @param node equivalence node
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
	 * Generates the code for an ForAllNode.
	 *
	 * @param node the visited node
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

	private String getMaxString(QuantifierNode node) {
		String max;
		final InternalTypeRep typeRep = node.getDeclaredSymbolicVar().getInternalTypeContainer().getInternalType();
		switch (typeRep) {
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
			throw new AssertionError(typeRep.name());
		}
		return max;
	}

	/**
	 * Generates the code to an ThereExistsNode.
	 *
	 * @param node the visited node
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
	 * Generates the code for a notNode.
	 *
	 * @param node the visited node
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
	 * Generates the code for the comparison of 2 types which are not integers These
	 * types can be lists which may have different depth and might be accessed by
	 * variables.
	 *
	 * @param node the node to visit
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
		code.add("//comparison left side");
		node.getLHSBooleanExpNode().getVisited(this);
		int lhslistLevel = listlvl;
		listlvl = 0;
		for (InternalTypeContainer cont = node.getRHSBooleanExpNode().getInternalTypeContainer(); cont
				.isList(); cont = cont.getListedType()) {
			listlvl++;
		}
		code.add("//comparison right side:");
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
			if (null != cont.getAccessTypeIfList()) {
				switch (cont.getAccessTypeIfList()) {
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
			// TODO this is just a hotfix because it used V when it should have used C
			// TODO for candidate lists
			// TODO maybe extract this to the voting types
			if (electionTypeContainer.getOutputType() instanceof CandidateList) {
				max = UnifiedNameContainer.getCandidate();
			}
			String loop = "for(unsigned int VAR = 0; BOOL && VAR < MAX; ++VAR) {";
			loop = loop.replaceAll("VAR", countingVar);
			loop = loop.replaceAll("MAX", max);
			loop = loop.replaceAll("BOOL", varName);
			code.add(loop);
			cont = cont.getListedType();
		}
		String lhs;
		String rhs;
		// if (node.getRHSBooleanExpNode() instanceof IntersectTypeExpNode) {
		// IntersectTypeExpNode rightNode = (IntersectTypeExpNode)
		// node.getRHSBooleanExpNode();
		//
		// IntersectExpContext context = rightNode.context;
		//
		// String voteInputOne = "tmp_elect" + getTmpIndex();
		//
		// code.add(electionTypeContainer.getOutputType().getOutputString() + " " +
		// voteInputOne + ";"); // create the
		// // var
		//
		// String voteInputTwo = "tmp_elect" + getTmpIndex();
		//
		// code.add(electionTypeContainer.getOutputType().getOutputString() + " " +
		// voteInputTwo + ";"); // create the
		// // var
		//
		// rhs = "tmp_elect" + getTmpIndex();
		//
		// code.add(electionTypeContainer.getOutputType().getOutputString() + " " + rhs
		// + ";"); // create the var
		//
		// intersectHelpMethod(context.intersectContent(0), voteInputOne,
		// context.intersectContent(1), voteInputTwo,
		// rhs);
		//
		// rhs = rhs + ".arr";
		//
		// } else {
		// rhs = variableNames.pop();
		// }
		//
		// if (node.getLHSBooleanExpNode() instanceof IntersectTypeExpNode) {
		// IntersectTypeExpNode leftNode = (IntersectTypeExpNode)
		// node.getLHSBooleanExpNode();
		//
		// IntersectExpContext context = leftNode.context;
		//
		// String voteInputOne = "tmp_elect" + getTmpIndex();
		//
		// code.add(electionTypeContainer.getOutputType().getOutputString() + " " +
		// voteInputOne + ";"); // create the
		// // var
		//
		// String voteInputTwo = "tmp_elect" + getTmpIndex();
		//
		// code.add(electionTypeContainer.getOutputType().getOutputString() + " " +
		// voteInputTwo + ";"); // create the
		// // var
		//
		// lhs = "tmp_elect" + getTmpIndex();
		//
		// code.add(electionTypeContainer.getOutputType().getOutputString() + " " + lhs
		// + ";"); // create the var
		//
		// intersectHelpMethod(context.intersectContent(0), voteInputOne,
		// context.intersectContent(1), voteInputTwo,
		// lhs);
		//
		// lhs = lhs + ".arr";
		//
		// } else {
		// lhs = variableNames.pop();
		// }
		rhs = variableNames.pop();
		lhs = variableNames.pop();
		// setToPostConditionMode();
		internCode = varName + " = " + lhs;
		final String prefix = post ? ".arr" : ""; // TODO use unifed name container, or defer to the Types

		String preFixCopy = prefix;

		for (int i = 0; i < lhslistLevel; ++i) {
			internCode += preFixCopy + "[VAR]".replace("VAR", counter.get(i));
			preFixCopy = ""; // TODO rewrite this with using input and output types
		}
		internCode += " " + node.getComparisonSymbol().getCStringRep() + " ";
		internCode += rhs;

		preFixCopy = prefix;

		for (int i = 0; i < rhslistLevel; ++i) {
			internCode += preFixCopy + "[VAR]".replace("VAR", counter.get(i));
			preFixCopy = ""; // TODO rewrite this with using input and output types
		}
		code.add(internCode + ";");
		for (int i = 0; i < maxListLevel; ++i) {
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
		visitAccessingNodesReverseOrder(exp);
		String tempCode = "electNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
		for (int i = 0; i < exp.getAccessingVars().length; ++i) {
			tempCode += "[VAR]".replace("VAR", variableNames.pop());
			listlvl--;
		}
		variableNames.push(tempCode);
	}

	@Override
	public void visitVoteExp(VoteExp exp) {
		visitAccessingNodesReverseOrder(exp);
		String tempCode = "votesNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
		for (int i = 0; i < exp.getAccessingVars().length; ++i) {
			tempCode += "[VAR]".replace("VAR", variableNames.pop());
			listlvl--;
		}
		variableNames.push(tempCode);
	}

	private void visitAccessingNodesReverseOrder(AccessValueNode exp) {
		for (int i = exp.getAccessingVars().length - 1; i >= 0; i--) {
			exp.getAccessingVars()[i].getVisited(this);
		}
	}

	@Override
	public void visitVoteSumExp(VoteSumForCandExp exp, boolean unique) {
		exp.getAccessingVariable().getVisited(this);
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
				ErrorLogger.log("The CodeGeneration Visitor was not set to a proper mode");
			}
		}
	}

	@Override
	public void visitVotingTupleChangeNode(VotingTupelChangeExpNode node) {
		List<String> tupleVotes = new ArrayList<String>();
		// add the first element to the list
		tupleVotes.add(node.tuple.getChild(1).getText());

		// Extract the vote elements one by one. The terminal symbol is saved in
		// child(1). If there is another nonterminal, it is saved in child(2),
		// else, that will be null.
		ParseTree subNode = node.tuple.getChild(2);
		do {
			tupleVotes.add(subNode.getChild(1).getText());
			subNode = subNode.getChild(2);
		} while (subNode != null);
		// there is only one child
		ParseTree voteEquivalent = node.splitExp.getChild(2);
		String voteInput = "";
		String voteInputSize = "";
		// we have to go deeper
		if (!(voteEquivalent instanceof TerminalNodeImpl)) {
			voteInput = "tmp_vote" + getTmpIndex();
			// create the variable
			code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + voteInput + ";");
			voteInputSize = "tmp_size" + getTmpIndex();
			code.add("unsigned int " + voteInputSize + ";");
			VoteEquivalentsNode equiNode = new VoteEquivalentsNode(node.splitExp.voteEquivalents(), voteInput,
					voteInputSize);
			equiNode.getVisited(this);
		} else { // we only have one vote to split
			voteInput = node.splitExp.voteEquivalents().Vote().getText().toLowerCase();
			voteInputSize = "V" + voteInput.substring("votes".length());
		}

		// we now have only one voting array to care about, and its name is written in
		// voteInput;
		String splits = "splits" + getTmpIndex();
		// determine how many splits we need
		code.add("unsigned int " + splits + " = " + (tupleVotes.size() - 1) + ";");
		String tmpLines = "tmp_Lines" + getTmpIndex();
		code.add("unsigned int *" + tmpLines + ";");
		code.add(tmpLines + " = getRandomSplitLines(" + splits + ", " + voteInputSize + ");"); // TODO maybe make this
																								// non dynamic
		String splitLines = "splitLines" + getTmpIndex();
		code.add("unsigned int " + splitLines + "[" + splits + "];");
		code.add("for (int i = 0; i <= " + splits + "; i++) {");
		code.add(splitLines + "[i] = " + tmpLines + "[i];");
		code.add("}");
		int tupleSize = tupleVotes.size();
		String lastSplit = "last_split" + getTmpIndex();

		final int dim = electionTypeContainer.getInputType().getAmountOfDimensions();
		code.add("unsigned int " + lastSplit + " = 0;");
		// Split the array and extract the votes for all but one tuple element
		for (int i = 0; i < tupleSize - 1; i++) {
			String tmp = "tmp" + getTmpIndex();
			code.add("struct vote_" + (dim < 2 ? "single" : "double") + " " + tmp + " = split"
					+ (dim < 2 ? "One" : "Two") + "( " + voteInput + ", " + lastSplit + ", " + splitLines + "[" + i
					+ "]);");
			code.add("for (int i = 0; i < V; i++) {");
			if (2 <= dim) {
				code.add("  for (int j = 0; j < C; j++) {");
			}
			final String secDim = "[j]";
			code.add("  " + tupleVotes.get(i).toLowerCase() + "[i]" + secDim + " = " + tmp + ".arr[i]" + secDim + ";");
			code.add((dim < 2 ? "" : "  }") + "");
			code.add("}");

			// Set the size value for this array to its new size:
			code.add("V" + tupleVotes.get(i).substring("votes".length()) + " = " + splitLines + "[" + i + "] - "
					+ lastSplit + ";");
			code.add(lastSplit + " = " + splitLines + "[" + i + "];");
		}
		String tmp = "tmp" + getTmpIndex();

		String votingStruct = electionTypeContainer.getInputStruct().getStructAccess();

		code.add(votingStruct + " " + tmp + " = split" + "( " + voteInput + ", " + lastSplit + ", " + voteInputSize + ");");
		// TODO is V correct here?
		code.add("for (int i = 0; i < V; i++) {");
		if (2 <= dim) {
			code.add("for (int j = 0; j < C; j++) {"); //TODO change to dimensions
		}
		code.add(tupleVotes.get(tupleSize - 1).toLowerCase() + ".arr[i]" + (2 <= dim ? "[j]" : "") + " = " + tmp
				+ ".arr[i]" + "" + (2 <= dim ? "[j]" : "") + ";");
		if (2 <= dim) {
			code.add("  }");
		}
		code.add("}");
		// finished the split
	}

	@Override
	public void visitVotingListChangeNode(VotingListChangeExpNode node) {

		String voteToSaveInto = node.vote.getText().toLowerCase();
		VotingListChangeContentContext contentContext = node.votingListChangeContent;
		// we save the result of the sub computation in this variable
		String voteInput = "tmp_vote" + getTmpIndex();
		// create the variable

		code.add(electionTypeContainer.getInputStruct().getStructAccess() + " " + voteInput + ";");

		String voteInputSize = "tmp_size" + getTmpIndex();
		code.add("unsigned int " + voteInputSize + ";");
		if (contentContext.getChild(0) instanceof ConcatenationExpContext) {
			// we have a concatenation
			ConcatenationExpNode concNode = new ConcatenationExpNode(contentContext.concatenationExp(), voteInput,
					voteInputSize);
			concNode.getVisited(this);
		} else {
			// we have a permutation
			PermutationExpNode permNode = new PermutationExpNode(contentContext.permutationExp(), voteInput,
					voteInputSize);
			permNode.getVisited(this);
		}
		// the result of the sub computation is now saved in "voteInput"

		// TODO extract this logic into InputType
		if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
			code.add("for (int i = 0; i < V; i++) {");
			code.add(voteToSaveInto + ".arr[i] = " + voteInput + ".arr[i];");
			code.add("}");
		} else { // we have two dimensions
			code.add("for (int i = 0; i < V; i++) {");
			code.add("  for (int j = 0; j < C; j++) {");
			code.add(voteToSaveInto + ".arr[i][j] = " + voteInput + ".arr[i][j];");
			code.add("  }");
			code.add("}");
		}
	}

	@Override
	public void visitCandidateListChangeExpNode(CandidateListChangeExpNode node) {
		if (electionTypeContainer.getOutputType() instanceof CandidateList) {
			String voteInput = "tmp_elect" + getTmpIndex();
			// create the variable
			code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + voteInput + ";");
			IntersectExpNode intersectNode = new IntersectExpNode(node.intersectExp, voteInput);
			intersectNode.getVisited(this);
			// the value the new array shall have is now in voteInput
			String toSaveInto = node.elect.getText().toLowerCase();
			code.add("for(int i = 0; i < " + electionTypeContainer.getNameContainer().getCandidate() + "; i++) {");

			String structVarAcc = "." + electionTypeContainer.getNameContainer().getStructValueName() + "[i]";

			code.add(toSaveInto + " " + structVarAcc + " = " + voteInput + structVarAcc + ";");
			code.add("}");
		} else {
			GUIController.setErrorText("So far only candidate lists can be intersected!");
		}
	}

	@Override
	public void visitIntersectExpNode(IntersectExpNode node) {
		IntersectExpContext context = node.intersectExpContext;
		String voteInputOne = "tmp_elect" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + voteInputOne + ";");
		String voteInputTwo = "tmp_elect" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + voteInputTwo + ";");

		intersectHelpMethod(context.intersectContent(0), voteInputOne, context.intersectContent(1), voteInputTwo,
				node.voteOutput);
		// we now have both inputs saved in their variables
	}

	@Override
	public void visitIntersectTypeExpNode(IntersectTypeExpNode node) {
		String voteInputOne = "tmp_elect" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + voteInputOne + ";");
		String voteInputTwo = "tmp_elect" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + voteInputTwo + ";");
		String toOutputTo = "tmp_output" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + toOutputTo + ";");
		intersectHelpMethod(node.context.intersectContent(0), voteInputOne, node.context.intersectContent(1),
				voteInputTwo, toOutputTo);
		variableNames.push(toOutputTo);
	}

	private void intersectHelpMethod(IntersectContentContext contextOne, String voteInputOne,
			IntersectContentContext contextTwo, String voteInputTwo, String toOutputTo) {
		IntersectContentNode intersectContentNodeOne = new IntersectContentNode(contextOne, voteInputOne);
		intersectContentNodeOne.getVisited(this);
		IntersectContentNode intersectContentNodeTwo = new IntersectContentNode(contextTwo, voteInputTwo);
		intersectContentNodeTwo.getVisited(this);
		code.add(toOutputTo + " = intersect(" + voteInputOne + ", " + voteInputTwo + ");");
	}

	@Override
	public void visitIntersectContentNode(IntersectContentNode node) {
		String voteInput = "tmp_elect" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + voteInput + ";");
		if (node.intersectContentContext.Elect() != null) {
			code.add(voteInput + " = " + node.intersectContentContext.Elect().getText().toLowerCase() + ";");
		} else {
			// we have an intersect expression
			IntersectExpNode intersectNode = new IntersectExpNode(node.intersectContentContext.intersectExp(),
					voteInput);
			intersectNode.getVisited(this);
		}
		// we now have the result standing in
		String toOutputTo = node.voteOutput;
		code.add("for(int i = 0; i < C; i++) {");
		code.add(toOutputTo + ".arr[i] = " + voteInput + ".arr[i];");
		code.add("}");
	}

	@Override
	public void visitVoteEquivalentsNode(VoteEquivalentsNode node) {
		String voteInput = "";
		String voteInputSize = "";
		if (node.voteEquivalentsContext.getChild(0) instanceof PermutationExpContext) {
			// we have a permutation
			voteInput = "tmp_vote" + getTmpIndex();
			// create the variable
			code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + voteInput + ";");
			voteInputSize = "tmp_size" + getTmpIndex();
			code.add("unsigned int " + voteInputSize + ";");
			PermutationExpNode permNode = new PermutationExpNode(node.voteEquivalentsContext.permutationExp(),
					voteInput, voteInputSize);
			permNode.getVisited(this);
		} else if (node.voteEquivalentsContext.getChild(0) instanceof ConcatenationExpContext) {
			// we have a concatenation
			voteInput = electionTypeContainer.getOutputStruct().getStructAccess() + " " + "tmp_vote" + getTmpIndex();
			ConcatenationExpNode concNode = new ConcatenationExpNode(node.voteEquivalentsContext.concatenationExp(),
					voteInput, voteInputSize);
			concNode.getVisited(this);
		} else {
			// we just have a simple vote
			voteInput = node.voteEquivalentsContext.Vote().getText().toLowerCase();
			voteInputSize = "V" + node.voteEquivalentsContext.Vote().getText().substring("VOTES".length());
		}
		// now a created voting array is saved in "voteInput"
		String voteToSaveInto = node.toOutput;
		String voteOutputLength = node.voteOutputLength;
		// TODO extract this logic into InputType
		if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
			code.add("for(int i = 0; i < V; i++) {");
			code.add(voteToSaveInto + ".arr[i] = " + voteInput + ".arr[i];");
			code.add("}");
		} else { // we have two dimensions
			code.add("for(int i = 0; i < V; i++) {");
			code.add("  for(int j = 0; j < C; j++) {");
			code.add(voteToSaveInto + ".arr[i][j] = " + voteInput + ".arr[i][j];");
			code.add("  }");
			code.add("}");
		}
		code.add(voteOutputLength + " = " + voteInputSize + ";");
		// we fulfilled the task of writing the result of the lower variable into the
		// given
		// output variable

	}

	@Override
	public void visitConcatenationExpNode(ConcatenationExpNode node) {
		int offset = 0;
		if (node.concatenationExpContext.getChild(0).getText().equals("(")) {
			// we have to skip one child if we start with "("
			offset = 1; // TODO wieso?
			// System.out.println("hat eine klammer!!");
		}
		ConcatenationExpContext context = node.concatenationExpContext;
		ParseTree firstChild = context.getChild(0 + offset);
		String voteInputOne = "tmp_vote" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getInputStruct().getStructAccess() + " " + voteInputOne + ";");
		String voteInputOneSize = "tmp_size" + getTmpIndex();
		// we have a vote equivalent
		if (firstChild instanceof VoteEquivalentsContext) {
			code.add("unsigned int " + voteInputOneSize + ";");
			VoteEquivalentsNode voteEqNode = new VoteEquivalentsNode(context.voteEquivalents(0), voteInputOne,
					voteInputOneSize);
			voteEqNode.getVisited(this);

		} else if (firstChild instanceof PermutationExpContext) {
			// we have a permutation
			code.add("unsigned int " + voteInputOneSize + ";");
			PermutationExpNode permNode = new PermutationExpNode(context.permutationExp(), voteInputOne,
					voteInputOneSize);
			permNode.getVisited(this);

		} else {
			// we have just a single vote
			voteInputOne = context.Vote().getText().toLowerCase();
		}
		// do the same thing for the other non terminal
		String voteInputTwo = "tmp_vote" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getInputStruct().getStructAccess() + " " + voteInputTwo + ";");
		String voteInputSecondSize = "tmp_size" + getTmpIndex();
		code.add("unsigned int " + voteInputSecondSize + ";");
		VoteEquivalentsNode voteEqNodeTwo = new VoteEquivalentsNode(context.voteEquivalents(offset), voteInputTwo,
				voteInputSecondSize);
		voteEqNodeTwo.getVisited(this);
		// we now have both sides ready in voteInputOne and voteInputTwo
		String toSaveInto = node.voteOutput;
		String tmp = "tmp" + getTmpIndex();
		// TODO extract into input type

		String outputStructDesc = electionTypeContainer.getOutputStruct().getStructAccess();

		if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
			code.add(outputStructDesc + " " + tmp + " = concatOne(" + voteInputOne + ", " + voteInputOneSize + " , "
					+ voteInputTwo + ", " + voteInputSecondSize + ");");
			// result is now in tmp
			code.add("for(int i = 0; i < V; i++) {");
			code.add(toSaveInto + ".arr[i] = " + tmp + ".arr[i];");
			code.add("}");
		} else {
			// we have 2 dim
			code.add(outputStructDesc + " " + tmp + " = concatTwo(" + voteInputOne + ", " + voteInputOneSize + " , "
					+ voteInputTwo + ", " + voteInputSecondSize + ");");
			// result is now in tmp //TODO redo all of this later on
			code.add("for(int i = 0; i < V; i++) {");
			code.add("for(int j = 0; j < C; j++) {");
			code.add(toSaveInto + ".arr[i][j] = " + tmp + ".arr[i][j];");
			code.add("}");
			code.add("}");
		}
		// the result is now saved in "toSaveInto"
	}

	@Override
	public void visitPermutationExpNode(PermutationExpNode node) {
		String voteInput = "tmp_vote" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + voteInput + ";");
		String voteInputSize = "tmp_size" + getTmpIndex();
		code.add("unsigned int " + voteInputSize + ";");
		VoteEquivalentsNode voteEqNode = new VoteEquivalentsNode(node.permutationExpContext.voteEquivalents(),
				voteInput, voteInputSize);
		// voteInput now holds the vote to permute.
		// voteInputSize now is set to the size of the returned array
		voteEqNode.getVisited(this);
		String toSaveInto = node.voteOutput;
		String toSaveSizeInto = node.voteOutoutLength;
		String tmp = "tmp" + getTmpIndex();
		// TODO extract to input type

		String voteStruct = electionTypeContainer.getInputStruct().getStructAccess();

		if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
			code.add(voteStruct + " " + tmp + " = permutateOne(" + voteInput + ", " + voteInputSize + ");");
			// result is now in tmp
			code.add("for(int i = 0; i < V; i++) {");
			code.add(toSaveInto + ".arr[i] = " + tmp + ".arr[i];");
			code.add("}");
			code.add(toSaveSizeInto + " = " + voteInputSize + ";");
		} else {
			code.add(voteStruct + " " + tmp + " = permutateTwo(" + voteInput + ", " + voteInputSize + ");");
			// result is now in tmp
			code.add("for(int i = 0; i < V; i++) {");
			code.add("for(int j = 0; j < C; j++) {");
			code.add(toSaveInto + ".arr[i][j] = " + tmp + ".arr[i][j];");
			code.add("}");
			code.add("}");
			code.add(toSaveSizeInto + " = " + voteInputSize + ";");
		}
	}

	@Override
	public void visitNotEmptyExpNode(NotEmptyExpressionNode node) {
		String notEmpty = "not_empty" + getTmpIndex();
		code.add("unsigned int " + notEmpty + ";");
		// we will save the result in this variable.
		// 0 for false, everything else for true
		variableNames.push(notEmpty);
		String subResult = "tmp_bool" + getTmpIndex();
		code.add("unsigned int " + subResult + " = 0;");
		NotEmptyContentNode notEmptyNode = new NotEmptyContentNode(node.context.notEmptyContent(), subResult);
		notEmptyNode.getVisited(this);
		// the result is now saved in votingInput
		code.add(notEmpty + " = " + subResult + ";");
		if (node.isTop) {
			code.add(assumeOrAssert + "(" + notEmpty + ");");
		}
	}

	@Override
	public void visitNotEmptyContentNode(NotEmptyContentNode node) {
		String subResult = "tmp_elect" + getTmpIndex();
		// create the variable
		code.add(electionTypeContainer.getOutputStruct().getStructAccess() + " " + subResult + ";");
		// we have an intersect beneath
		if (node.context.intersectExp() != null) {
			IntersectExpNode intersectNode = new IntersectExpNode(node.context.intersectExp(), subResult);
			intersectNode.getVisited(this);
		} else {
			// we only have an elect
			code.add(subResult + " = " + node.context.Elect().getText().toLowerCase() + ";");
		}
		String toReturn = node.votingOutput;
		// the value is now saved in subResult
		code.add("for(int i = 0; i < C; i++) { ");
		code.add(toReturn + " += " + subResult + ".arr[i];");
		code.add("}");
	}
}