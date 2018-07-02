/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST;

import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.sun.jna.platform.win32.OaIdl.VARKIND;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpConstant;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanExpAST.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BinaryRelationshipNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.EquivalencyNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ForAllNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.IntegerComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.IntersectTypeExpNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.NotNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.QuantorNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.AtPosExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.ElectExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.TypeExpression;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.VoteExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.ConstantExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.IntegerNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.IntegerValuedExpression;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.VoteSumForCandExp;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.propertychecker.IntersectExpNode;
import edu.pse.beast.propertychecker.NotEmptyExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionBaseListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpListContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;

/**
 *
 * @author Holger-Desktop
 */
public class FormalPropertySyntaxTreeToAstTranslator extends FormalPropertyDescriptionBaseListener {

	private BooleanExpListNode generated;
	private InputType inputType;
	private OutputType resType;
	private int maxVoteExp = 0;
	private int currentHighestElect = 0;
	private BooleanExpScopehandler scopeHandler;
	
	private boolean hadBinaryBefore = false;

	// Stacks
	private Stack<BooleanExpressionNode> nodeStack;
	private Stack<TypeExpression> expStack;

	public BooleanExpListNode generateFromSyntaxTree(BooleanExpListContext parseTree, InputType inputType,
			OutputType resType, BooleanExpScope declaredVars) {
		scopeHandler = new BooleanExpScopehandler();
		scopeHandler.enterNewScope(declaredVars);
		this.inputType = inputType;
		this.resType = resType;
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(this, parseTree);
		return generated;
	}
	
	private void setNewMaxVote(int number) {
		if (number > maxVoteExp) {
			maxVoteExp = number;
		}
	}

	@Override
	public void enterBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx) {
		generated = new BooleanExpListNode();
		maxVoteExp = 0;
		expStack = new Stack<>();
		nodeStack = new Stack<>();
	}

	@Override
	public void exitBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx) {
		generated.setMaxVoteLevel(maxVoteExp);
	}

	@Override
	public void enterBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx) {
		currentHighestElect = 0;
		this.hadBinaryBefore = false;
	}

	@Override
	public void exitBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx) {
		
		generated.addNode(nodeStack.pop(), currentHighestElect);
		
		setNewMaxVote(currentHighestElect);
	}

	@Override
	public void enterBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx) {

	}

	@Override
	public void exitBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx) {
		if (ctx.notEmptyExp() != null) {
			NotEmptyExpressionNode node = new NotEmptyExpressionNode(ctx.notEmptyExp(), !hadBinaryBefore);
			
			//FALLS FEHLER
			
			nodeStack.add(node);
		}
	}

	@Override
	public void enterBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx) {
		this.hadBinaryBefore = true;
	}

	@Override
	public void exitBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx) {
		String symbol = ctx.BinaryRelationSymbol().toString();
		
		BooleanExpressionNode rhs = nodeStack.pop();
		BooleanExpressionNode lhs = nodeStack.pop();

		BinaryRelationshipNode node = null;

		if (symbol.equals("&&")) {
			node = new LogicalAndNode(lhs, rhs);
		} else if (symbol.equals("||")) {
			node = new LogicalOrNode(lhs, rhs);
		} else if (symbol.equals("==>")) {
			node = new ImplicationNode(lhs, rhs);
		} else if (symbol.equals("<==>")) {
			node = new EquivalencyNode(lhs, rhs);
		}
		nodeStack.add(node);
	}

	@Override
	public void enterQuantorExp(FormalPropertyDescriptionParser.QuantorExpContext ctx) {
		String quantorTypeString = ctx.Quantor().getText();
		InternalTypeContainer varType = null;
		if (quantorTypeString.contains("VOTER")) {
			varType = new InternalTypeContainer(InternalTypeRep.VOTER);
		} else if (quantorTypeString.contains("CANDIDATE")) {
			varType = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
		} else if (quantorTypeString.contains("SEAT")) {
			varType = new InternalTypeContainer(InternalTypeRep.SEAT);
		}
		scopeHandler.enterNewScope();
		String id = ctx.passSymbVar().symbolicVarExp().Identifier().getText();
		scopeHandler.addVariable(id, varType);
	}

	@Override
	public void exitQuantorExp(FormalPropertyDescriptionParser.QuantorExpContext ctx) {
		String quantorType = ctx.Quantor().getText();

		QuantorNode node = null;

		if (quantorType.contains("FOR_ALL")) {
			node = new ForAllNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(), nodeStack.pop());
		} else if (quantorType.contains("EXISTS_ONE")) {
			node = new ThereExistsNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(), nodeStack.pop());
		}

		nodeStack.add(node);
		scopeHandler.exitScope();
	}

	@Override
	public void enterNotExp(FormalPropertyDescriptionParser.NotExpContext ctx) {

	}

	@Override
	public void exitNotExp(FormalPropertyDescriptionParser.NotExpContext ctx) {
		NotNode node = new NotNode(nodeStack.pop());
		nodeStack.add(node);
	}

	@Override
	public void enterComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx) {

	}

	@Override
	public void exitComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx) {
		String comparisonSymbolString = ctx.ComparisonSymbol().getText();
		ComparisonSymbol comparisonSymbol = new ComparisonSymbol(comparisonSymbolString);
		
		TypeExpression lhs;
		TypeExpression rhs;

		if (ctx.typeExp(1).getChild(0) instanceof IntersectExpContext) { //the right arguments 
			rhs = new IntersectTypeExpNode(resType, (IntersectExpContext) ctx.typeExp(1).getChild(0));
		} else {
			rhs = expStack.pop();
		}
		
		if (ctx.typeExp(0).getChild(0) instanceof IntersectExpContext) { //the left argument
			lhs = new IntersectTypeExpNode(resType, (IntersectExpContext) ctx.typeExp(0).getChild(0));
		} else {
			lhs = expStack.pop();
		}
		
		if (lhs.getInternalTypeContainer().getInternalType() == InternalTypeRep.INTEGER) {
			IntegerComparisonNode node = new IntegerComparisonNode(lhs, rhs, comparisonSymbol);
			nodeStack.add(node);
		} else {
			ComparisonNode node = new ComparisonNode(lhs, rhs, comparisonSymbol);
			nodeStack.add(node);
		}
	}

	@Override
	public void enterTypeExp(FormalPropertyDescriptionParser.TypeExpContext ctx) {

	}

	@Override
	public void exitTypeExp(FormalPropertyDescriptionParser.TypeExpContext ctx) {

	}

	@Override
	public void enterNumberExpression(FormalPropertyDescriptionParser.NumberExpressionContext ctx) {

	}

	@Override
	public void exitNumberExpression(FormalPropertyDescriptionParser.NumberExpressionContext ctx) {
		if (ctx.Mult() != null) {
			IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
			IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
			BinaryIntegerValuedNode expNode = new BinaryIntegerValuedNode(lsh, rhs, ctx.Mult().getText());
			expStack.push(expNode);
		} else if (ctx.Add() != null) {
			IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
			IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
			BinaryIntegerValuedNode expNode = new BinaryIntegerValuedNode(lsh, rhs, ctx.Add().getText());
			expStack.push(expNode);
		}
	}

	@Override
	public void enterElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
	}

	@Override
	public void exitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
		// get number
		String numberString = ctx.Elect().getText().substring("ELECT".length());
		int number = Integer.valueOf(numberString);
		if (currentHighestElect < number) {
			currentHighestElect = number;
		}
		int amtAcessingTypes = ctx.passType().size();
		TypeExpression[] accessingVars = new TypeExpression[amtAcessingTypes];

		for (int i = 0; i < amtAcessingTypes; ++i) {
			accessingVars[amtAcessingTypes - i - 1] = expStack.pop();
		}

		ElectExp expNode = new ElectExp(resType, accessingVars, number);
		expStack.push(expNode);
	}

	@Override
	public void enterVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {

	}

	@Override
	public void exitVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
		String numberString = ctx.Vote().getText().substring("VOTES".length());
		int number = Integer.valueOf(numberString);
		
		setNewMaxVote(number);
		
		int amtAcessingTypes = ctx.passType().size();
		TypeExpression[] accessingVars = new TypeExpression[amtAcessingTypes];

		for (int i = 0; i < amtAcessingTypes; ++i) {
			accessingVars[amtAcessingTypes - i - 1] = expStack.pop();
		}

		VoteExp expNode = new VoteExp(inputType, accessingVars, number);
		expStack.push(expNode);
	}

	@Override
	public void enterConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {

	}

	@Override
	public void exitConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {
		String constString = ctx.getText();
		ConstantExp expNode = null;
		if (constString.equals(BooleanExpConstant.getConstForVoterAmt())) {
			expNode = new ConstantExp(constString);
		} else if (constString.equals(BooleanExpConstant.getConstForCandidateAmt())) {
			expNode = new ConstantExp(constString);
		} else if (constString.equals(BooleanExpConstant.getConstForSeatAmt())) {
			expNode = new ConstantExp(constString);
		}
		expStack.push(expNode);
	}

	@Override
	public void enterVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {

	}

	private void exitVoteSum(final String exprStr, final TerminalNode tn, final boolean unique) {
		String numberString = tn.getText().substring(exprStr.length());
		int number = Integer.valueOf(numberString);
		
		setNewMaxVote(number);
		
		VoteSumForCandExp expNode = new VoteSumForCandExp(number, expStack.pop(), unique);
		expStack.push(expNode);
	}

	@Override
	public void exitVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
		final String exprStr = "VOTE_SUM_FOR_CANDIDATE";
		final TerminalNode tn = ctx.Votesum();
		exitVoteSum(exprStr, tn, false);
	}

	@Override
	public void enterVoteSumUniqueExp(FormalPropertyDescriptionParser.VoteSumUniqueExpContext ctx) {

	}

	@Override
	public void exitVoteSumUniqueExp(FormalPropertyDescriptionParser.VoteSumUniqueExpContext ctx) {
		final String exprStr = "VOTE_SUM_FOR_UNIQUE_CANDIDATE";
		final TerminalNode tn = ctx.VotesumUnique();
		exitVoteSum(exprStr, tn, true);
	}

	@Override
	public void enterPassSymbVar(FormalPropertyDescriptionParser.PassSymbVarContext ctx) {

	}

	@Override
	public void exitPassSymbVar(FormalPropertyDescriptionParser.PassSymbVarContext ctx) {

	}

	@Override
	public void enterSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {

	}

	@Override
	public void exitSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {
		String name = ctx.getText();
		InternalTypeContainer type = scopeHandler.getTypeForVariable(name);
		SymbolicVarExp expNode = new SymbolicVarExp(type, new SymbolicVariable(name, type));
		expStack.push(expNode);
	}

	@Override
	public void exitInteger(FormalPropertyDescriptionParser.IntegerContext ctx) {
		String integerString = ctx.getText();
		int heldInteger = Integer.valueOf(integerString);
		IntegerNode integerNode = new IntegerNode(heldInteger);
		expStack.push(integerNode);
	}

	private void pushAtPosNode(InternalTypeRep rep) {
		expStack.push(new AtPosExp(new InternalTypeContainer(rep), (IntegerValuedExpression) expStack.pop()));
	}

	@Override
	public void exitVoterByPosExp(FormalPropertyDescriptionParser.VoterByPosExpContext ctx) {
		pushAtPosNode(InternalTypeRep.VOTER);
	}

	@Override
	public void exitCandByPosExp(FormalPropertyDescriptionParser.CandByPosExpContext ctx) {
		pushAtPosNode(InternalTypeRep.CANDIDATE);
	}

	@Override
	public void exitSeatByPosExp(FormalPropertyDescriptionParser.SeatByPosExpContext ctx) {
		pushAtPosNode(InternalTypeRep.SEAT);
	}

	@Override
	public void visitTerminal(TerminalNode tn) {

	}

	@Override
	public void visitErrorNode(ErrorNode en) {

	}

	@Override
	public void enterEveryRule(ParserRuleContext prc) {

	}

	@Override
	public void exitEveryRule(ParserRuleContext prc) {

	}

	// new part here:
	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterVotingListChangeExp(FormalPropertyDescriptionParser.VotingListChangeExpContext ctx) {
		InternalTypeContainer varType = new InternalTypeContainer(InternalTypeRep.VOTER);
		
		String voteNumber = ctx.Vote().getText().substring("VOTES".length());
		
		int number = Integer.parseInt(voteNumber);
		
		setNewMaxVote(number);

		scopeHandler.enterNewScope();
		String id = ctx.Vote().getText();
		scopeHandler.addVariable(id, varType);
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitVotingListChangeExp(FormalPropertyDescriptionParser.VotingListChangeExpContext ctx) {

		BooleanExpressionNode node = new VotingListChangeExpNode(ctx.Vote(), ctx.votingListChangeContent());

		nodeStack.add(node);
		scopeHandler.exitScope();
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterVotingListChangeContent(FormalPropertyDescriptionParser.VotingListChangeContentContext ctx) {
		
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitVotingListChangeContent(FormalPropertyDescriptionParser.VotingListChangeContentContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterVotingTupelChangeExp(FormalPropertyDescriptionParser.VotingTupelChangeExpContext ctx) {
		
		InternalTypeContainer varType = new InternalTypeContainer(InternalTypeRep.VOTER);

		scopeHandler.enterNewScope();
		String id = ctx.tuple().getText();
		scopeHandler.addVariable(id, varType);
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitVotingTupelChangeExp(FormalPropertyDescriptionParser.VotingTupelChangeExpContext ctx) {
		
		BooleanExpressionNode node = new VotingTupelChangeExpNode(ctx.tuple(), ctx.splitExp());
		
		nodeStack.add(node);
		scopeHandler.exitScope();
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterCandidateListChangeExp(FormalPropertyDescriptionParser.CandidateListChangeExpContext ctx) {
		InternalTypeContainer varType = new InternalTypeContainer(InternalTypeRep.CANDIDATE);

		String electNumber = ctx.Elect().getText().substring("ELECT".length());
		
		int number = Integer.parseInt(electNumber);
		
		setNewMaxVote(number);
		
		scopeHandler.enterNewScope();
		String id = ctx.Elect().getText();
		scopeHandler.addVariable(id, varType);
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitCandidateListChangeExp(FormalPropertyDescriptionParser.CandidateListChangeExpContext ctx) {
		BooleanExpressionNode node = new CandidateListChangeExpNode(ctx.Elect(), ctx.intersectExp());

		nodeStack.add(node);
		scopeHandler.exitScope();
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterVoteEquivalents(FormalPropertyDescriptionParser.VoteEquivalentsContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitVoteEquivalents(FormalPropertyDescriptionParser.VoteEquivalentsContext ctx) {		
		if (ctx.Vote() != null) {
			
			String vote = ctx.Vote().getText();
			String votingNumer = vote.substring("VOTES".length());
			
			int number = Integer.parseInt(votingNumer);
			
			setNewMaxVote(number);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterConcatenationExp(FormalPropertyDescriptionParser.ConcatenationExpContext ctx) {
	//TODO
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitConcatenationExp(FormalPropertyDescriptionParser.ConcatenationExpContext ctx) {
	//TODO
	}
	

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterSplitExp(FormalPropertyDescriptionParser.SplitExpContext ctx) {

	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitSplitExp(FormalPropertyDescriptionParser.SplitExpContext ctx) {

	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterPermutationExp(FormalPropertyDescriptionParser.PermutationExpContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitPermutationExp(FormalPropertyDescriptionParser.PermutationExpContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterIntersectExp(FormalPropertyDescriptionParser.IntersectExpContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitIntersectExp(FormalPropertyDescriptionParser.IntersectExpContext ctx) {
	//	IntersectExpNode node = new IntersectExpNode(ctx, "dies ist ein test");
	//	nodeStack.add(node);
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterIntersectContent(FormalPropertyDescriptionParser.IntersectContentContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitIntersectContent(FormalPropertyDescriptionParser.IntersectContentContext ctx) {
		
		if (ctx.Elect() != null) {
			String elect = ctx.Elect().getText();
			String electNumber = elect.substring("ELECT".length());
			
			int number = Integer.parseInt(electNumber);
			
			setNewMaxVote(number);
		}
		
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterTuple(FormalPropertyDescriptionParser.TupleContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitTuple(FormalPropertyDescriptionParser.TupleContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterTupleContent(FormalPropertyDescriptionParser.TupleContentContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterTypeByPosExp(FormalPropertyDescriptionParser.TypeByPosExpContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitTypeByPosExp(FormalPropertyDescriptionParser.TypeByPosExpContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterVoterByPosExp(FormalPropertyDescriptionParser.VoterByPosExpContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterCandByPosExp(FormalPropertyDescriptionParser.CandByPosExpContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterSeatByPosExp(FormalPropertyDescriptionParser.SeatByPosExpContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterInteger(FormalPropertyDescriptionParser.IntegerContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterPassType(FormalPropertyDescriptionParser.PassTypeContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitPassType(FormalPropertyDescriptionParser.PassTypeContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterPassPosition(FormalPropertyDescriptionParser.PassPositionContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitPassPosition(FormalPropertyDescriptionParser.PassPositionContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void enterPassByPos(FormalPropertyDescriptionParser.PassByPosContext ctx) {
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 */
	@Override
	public void exitPassByPos(FormalPropertyDescriptionParser.PassByPosContext ctx) {
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterNotEmptyExp(FormalPropertyDescriptionParser.NotEmptyExpContext ctx) {
		
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitNotEmptyExp(FormalPropertyDescriptionParser.NotEmptyExpContext ctx) {
		
		NotEmptyExpressionNode node = new NotEmptyExpressionNode(ctx, !hadBinaryBefore);
		
		//FALLS FEHLER
		
		nodeStack.add(node);
		
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterNotEmptyContent(FormalPropertyDescriptionParser.NotEmptyContentContext ctx) {
		
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitNotEmptyContent(FormalPropertyDescriptionParser.NotEmptyContentContext ctx) {
		TerminalNode elect = ctx.Elect();
		
		if (elect != null) {
			String electNumber = elect.getText().substring("ELECT".length());
			
			int number = Integer.parseInt(electNumber);
			
			setNewMaxVote(number);
		}
	}

}
