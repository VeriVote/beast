package edu.pse.beast.api.codegen.booleanExpAst;

import java.util.Stack;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpIsEmptyNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectPermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectTupleNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VotePermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteTupleNode;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpConstant;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanexpast.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BinaryRelationshipNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.EquivalenceNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.NotNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.QuantifierNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.ConstantExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerValuedExpression;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.VoteSumForCandExp;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionBaseListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BinaryRelationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpListContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpListElementContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ComparisonExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConstantExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ElectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ElectTupleExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntegerContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectElectContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectVotesContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IsEmptyExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NumberExpressionContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermElectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermVoteExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.QuantifierExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SymbolicVarExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteSumExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteSumUniqueExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteTupleExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.BooleanExpScopehandler;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.CBMCInputType;
import edu.pse.beast.types.cbmctypes.CBMCOutputType;

public class BooleanCodeToAST extends FormalPropertyDescriptionBaseListener {

	private static final String VOTE_SUM = "VOTE_SUM_FOR_CANDIDATE";
	private static final String VOTE_SUM_UNIQUE = "VOTE_SUM_FOR_UNIQUE_CANDIDATE";

	private BooleanExpASTData generated;
	private BooleanExpListNode AST;

	private BooleanExpScopehandler scopeHandler;
	private Stack<BooleanExpressionNode> nodeStack;
	private Stack<TypeExpression> expStack;

	private int highestElect;
	private int highestVote;
	private int highestElectInThisListNode;

	private BooleanCodeToAST(BooleanExpScope declaredVars) {
		scopeHandler = new BooleanExpScopehandler();
		scopeHandler.enterNewScope(declaredVars);
	}

	public static BooleanExpASTData generateAST(String boolExpCode, BooleanExpScope declaredVars) {
		final FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(
				CharStreams.fromString(boolExpCode));
		final CommonTokenStream ts = new CommonTokenStream(l);
		final FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(ts);

		ParseTreeWalker walker = new ParseTreeWalker();
		BooleanCodeToAST listener = new BooleanCodeToAST(declaredVars);
		walker.walk(listener, p.booleanExpList());

		return listener.generated;
	}

	private void setHighestElect(int elect) {
		if (elect > highestElect) {
			highestElect = elect;
		}
	}

	private void setHighestVote(int vote) {
		if (vote > highestVote) {
			highestVote = vote;
		}
	}

	@Override
	public void enterBooleanExpList(final BooleanExpListContext ctx) {
		generated = new BooleanExpASTData();
		expStack = new Stack<TypeExpression>();
		nodeStack = new Stack<BooleanExpressionNode>();
		AST = new BooleanExpListNode();
		highestElect = 0;
	}

	@Override
	public void exitBooleanExpList(final BooleanExpListContext ctx) {
		generated.setHighestElect(highestElect);
		generated.setHighestVote(highestVote);
		generated.setTopAstNode(AST);
	}

	@Override
	public void enterBooleanExpListElement(final BooleanExpListElementContext ctx) {
		highestElectInThisListNode = 0;
	}

	@Override
	public void exitBooleanExpListElement(final BooleanExpListElementContext ctx) {
		BooleanExpListElementNode node = new BooleanExpListElementNode(ctx.getText(), nodeStack.pop());
		AST.addNode(node, highestElectInThisListNode);
		setHighestElect(highestElectInThisListNode);
	}

	@Override
	public void enterBinaryRelationExp(final BinaryRelationExpContext ctx) {
	}

	@Override
	public void exitBinaryRelationExp(final BinaryRelationExpContext ctx) {
		final String symbol = ctx.BinaryRelationSymbol().toString();
		final BooleanExpressionNode rhs = nodeStack.pop();
		final BooleanExpressionNode lhs = nodeStack.pop();

		final BinaryRelationshipNode node;
		if (symbol.equals(BinaryCombinationSymbols.AND)) {
			node = new LogicalAndNode(lhs, rhs);
		} else if (symbol.equals(BinaryCombinationSymbols.OR)) {
			node = new LogicalOrNode(lhs, rhs);
		} else if (symbol.equals(BinaryCombinationSymbols.IMPL)) {
			node = new ImplicationNode(lhs, rhs);
		} else if (symbol.equals(BinaryCombinationSymbols.EQUIV)) {
			node = new EquivalenceNode(lhs, rhs);
		} else {
			// TODO(Error)
			node = null;
		}
		nodeStack.add(node);
	}

	@Override
	public void enterQuantifierExp(final QuantifierExpContext ctx) {
		final String quantifierTypeString = ctx.Quantifier().getText();
		final InternalTypeContainer varType;
		if (quantifierTypeString.contains(VariableTypeNames.VOTER)) {
			varType = new InternalTypeContainer(InternalTypeRep.VOTER);
		} else if (quantifierTypeString.contains(VariableTypeNames.CANDIDATE)) {
			varType = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
		} else if (quantifierTypeString.contains(VariableTypeNames.SEAT)) {
			varType = new InternalTypeContainer(InternalTypeRep.SEAT);
		} else {
			varType = null;
		}
		scopeHandler.enterNewScope();
		final String id = ctx.passSymbVar().symbolicVarExp().Identifier().getText();
		scopeHandler.addVariable(id, varType);
	}

	@Override
	public void exitQuantifierExp(final QuantifierExpContext ctx) {
		final String quantifierType = ctx.Quantifier().getText();
		final QuantifierNode node;
		if (quantifierType.contains(Quantifier.FOR_ALL)) {
			node = new ForAllNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(), nodeStack.pop());
		} else if (quantifierType.contains(Quantifier.EXISTS)) {
			node = new ThereExistsNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(), nodeStack.pop());
		} else {
			node = null;
		}
		nodeStack.add(node);
		scopeHandler.exitScope();
	}

	@Override
	public void enterSymbolicVarExp(final SymbolicVarExpContext ctx) {
	}

	@Override
	public void exitSymbolicVarExp(final SymbolicVarExpContext ctx) {
		final String name = ctx.getText();
		final InternalTypeContainer type = scopeHandler.getTypeForVariable(name);
		final SymbolicVarExp expNode = new SymbolicVarExp(type, new SymbolicVariable(name, type));
		expStack.push(expNode);
	}

	@Override
	public void enterNotExp(final NotExpContext ctx) {
	}

	@Override
	public void exitNotExp(final NotExpContext ctx) {
		final NotNode node = new NotNode(nodeStack.pop());
		nodeStack.add(node);
	}

	@Override
	public void enterComparisonExp(final ComparisonExpContext ctx) {
	}

	@Override
	public void exitComparisonExp(final ComparisonExpContext ctx) {
		final String comparisonSymbolString = ctx.ComparisonSymbol().getText();
		final ComparisonSymbol comparisonSymbol = new ComparisonSymbol(comparisonSymbolString);

		TypeExpression rhs = expStack.pop();
		TypeExpression lhs = expStack.pop();

		final ComparisonNode node = new ComparisonNode(lhs, rhs, comparisonSymbol);
		nodeStack.add(node);
	}

	@Override
	public void enterNumberExpression(final NumberExpressionContext ctx) {
	}

	@Override
	public void exitNumberExpression(final NumberExpressionContext ctx) {
		if (ctx.Mult() != null) {
			final IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
			final IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
			final BinaryIntegerValuedNode expNode = new BinaryIntegerValuedNode(lsh, rhs, ctx.Mult().getText());
			expStack.push(expNode);
		} else if (ctx.Add() != null) {
			final IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
			final IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
			final BinaryIntegerValuedNode expNode = new BinaryIntegerValuedNode(lsh, rhs, ctx.Add().getText());
			expStack.push(expNode);
		}
	}

	@Override
	public void enterElectExp(final ElectExpContext ctx) {
	}

	private String ELECT = "ELECT";

	@Override
	public void exitElectExp(final ElectExpContext ctx) {
		final String numberString = ctx.Elect().getText().substring(ELECT.length());
		final int number = Integer.valueOf(numberString);
		if (highestElectInThisListNode < number) {
			highestElectInThisListNode = number;
		}
		final int amtAccessingTypes = ctx.passType().size();
		final TypeExpression[] accessingVars = new TypeExpression[amtAccessingTypes];

		for (int i = 0; i < amtAccessingTypes; ++i) {
			accessingVars[amtAccessingTypes - i - 1] = expStack.pop();
		}

		// TODO make work with new output types
		final ElectExp expNode = new ElectExp(CBMCOutputType.getOutputTypes().get(3), accessingVars, number);
		expStack.push(expNode);
	}

	@Override
	public void enterVoteExp(final VoteExpContext ctx) {
	}

	private String VOTER = "VOTER";

	@Override
	public void exitVoteExp(final VoteExpContext ctx) {
		final String numberString = ctx.Vote().getText().substring(VOTER.length());
		final int number = Integer.valueOf(numberString);
		setHighestVote(number);

		final int amtAccessingTypes = ctx.passType().size();
		final TypeExpression[] accessingVars = new TypeExpression[amtAccessingTypes];
		for (int i = 0; i < amtAccessingTypes; ++i) {
			accessingVars[amtAccessingTypes - i - 1] = expStack.pop();
		}
		// TODO make work with new input types
		final VoteExp expNode = new VoteExp(CBMCInputType.getInputTypes().get(0), accessingVars, number);
		expStack.push(expNode);
	}

	@Override
	public void enterConstantExp(final ConstantExpContext ctx) {
	}

	@Override
	public void exitConstantExp(final ConstantExpContext ctx) {
		final String constString = ctx.getText();
		final ConstantExp expNode;
		if (constString.equals(BooleanExpConstant.getConstForVoterAmt())) {
			expNode = new ConstantExp(constString);
		} else if (constString.equals(BooleanExpConstant.getConstForCandidateAmt())) {
			expNode = new ConstantExp(constString);
		} else if (constString.equals(BooleanExpConstant.getConstForSeatAmt())) {
			expNode = new ConstantExp(constString);
		} else {
			expNode = null;
		}
		expStack.push(expNode);
	}

	@Override
	public void exitInteger(final IntegerContext ctx) {
		final String integerString = ctx.getText();
		final int heldInteger = Integer.valueOf(integerString);
		final IntegerNode integerNode = new IntegerNode(heldInteger);
		expStack.push(integerNode);
	}

	@Override
	public void enterVoteSumExp(final VoteSumExpContext ctx) {
	}

	private void exitVoteSum(final String exprStr, final TerminalNode tn, final boolean unique) {
		final String numberString = tn.getText().substring(exprStr.length());
		final int number = Integer.valueOf(numberString);
		setHighestVote(number);
		final VoteSumForCandExp expNode = new VoteSumForCandExp(number, expStack.pop(), unique);
		expStack.push(expNode);
	}

	@Override
	public void exitVoteSumExp(final VoteSumExpContext ctx) {
		final String exprStr = VOTE_SUM;
		final TerminalNode tn = ctx.Votesum();
		exitVoteSum(exprStr, tn, false);
	}

	@Override
	public void enterVoteSumUniqueExp(final VoteSumUniqueExpContext ctx) {
	}

	@Override
	public void exitVoteSumUniqueExp(final VoteSumUniqueExpContext ctx) {
		final String exprStr = VOTE_SUM_UNIQUE;
		final TerminalNode tn = ctx.VotesumUnique();
		exitVoteSum(exprStr, tn, true);
	}

	private int extractNumberFromVote(String voteString) {
		final String numberString = voteString.substring(VOTER.length());
		final int number = Integer.valueOf(numberString);
		return number;
	}

	private int extractNumberFromElect(String electString) {
		final String numberString = electString.substring(ELECT.length());
		final int number = Integer.valueOf(numberString);
		return number;
	}

	@Override
	public void exitVoteTupleExp(VoteTupleExpContext ctx) {
		VoteTupleNode node = new VoteTupleNode(null);
		for (TerminalNode vote : ctx.Vote()) {
			int number = extractNumberFromVote(vote.getText());
			node.addVoteNumber(number);
		}
		expStack.push(node);
	}

	@Override
	public void exitElectTupleExp(ElectTupleExpContext ctx) {
		ElectTupleNode node = new ElectTupleNode(null);
		for (TerminalNode elect : ctx.Elect()) {
			int number = extractNumberFromElect(elect.getText());
			node.addElectNumber(number);
		}
		expStack.push(node);
	}

	@Override
	public void exitPermVoteExp(PermVoteExpContext ctx) {
		VotePermutationNode node = new VotePermutationNode(null);
		int number = extractNumberFromVote(ctx.Vote().getText());
		node.setVoteNumber(number);
		expStack.push(node);
	}

	@Override
	public void exitPermElectExp(PermElectExpContext ctx) {
		ElectPermutationNode node = new ElectPermutationNode(null);
		int number = extractNumberFromElect(ctx.Elect().getText());
		node.setElectNumber(number);
		expStack.push(node);
	}

	@Override
	public void exitIntersectElect(IntersectElectContext ctx) {
		ElectIntersectionNode node = new ElectIntersectionNode(null);
		for (TerminalNode elect : ctx.Elect()) {
			int number = extractNumberFromElect(elect.getText());
			node.addElectNumber(number);
			setHighestElect(number);
		}
		expStack.push(node);
	}

	@Override
	public void exitIntersectVotes(IntersectVotesContext ctx) {
		VoteIntersectionNode node = new VoteIntersectionNode(null);
		for (TerminalNode elect : ctx.Vote()) {
			int number = extractNumberFromElect(elect.getText());
			node.addVoteNumber(number);
			setHighestVote(number);
		}
		expStack.push(node);

	}

	@Override
	public void exitIsEmptyExp(IsEmptyExpContext ctx) {
		BooleanExpIsEmptyNode node = new BooleanExpIsEmptyNode(expStack.pop());
		nodeStack.add(node);
	}
}
