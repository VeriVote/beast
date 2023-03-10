package edu.kit.kastel.formal.beast.api.codegen.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.NotImplementedException;

import edu.kit.kastel.formal.beast.api.codegen.ast.data.BinaryCombinationSymbols;
import edu.kit.kastel.formal.beast.api.codegen.ast.data.Quantifier;
import edu.kit.kastel.formal.beast.api.codegen.ast.data.VariableTypeNames;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.BooleanExpListNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.BinaryRelationshipNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.BooleanExpIsEmptyNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.BooleanExpListElementNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.BooleanExpressionNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.ComparisonNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.EquivalenceNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.FalseTrueNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.ForAllNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.ImplicationNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.LogicalAndNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.LogicalOrNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.NotNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.QuantifierNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.ThereExistsNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.TypeExpression;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.ElectExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.ElectIntersectionNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.ElectPermutationNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.ElectTupleNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.VoteExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.VoteIntersectionNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.VotePermutationNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.VoteTupleNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer.BinaryIntegerValuedNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer.ConstantExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer.IntegerNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer.IntegerValuedExpression;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer.VoteSumForCandExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.symbolic.SymbVarByPosExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.symbolic.SymbolicVarByNameExp;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ScopeHandler;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable.VariableType;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionBaseListener;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionLexer;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.BinaryRelationExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.BooleanExpListContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.BooleanExpListElementContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.ComparisonExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.ConstantExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.ElectExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.ElectTupleExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.FalseExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.IntegerContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.IntersectElectContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.IntersectVotesContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.IsEmptyExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.NotExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.NumberExpressionContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.PermElectExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.PermVoteExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.QuantifierExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.SymbVarByNameExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.SymbVarByPosExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.SymbolicVarExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.TrueExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.TupleExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.VoteExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.VoteSumExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.VoteSumUniqueExpContext;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser.VoteTupleExpContext;

/**
 * Walks the antlr parse tree to generate our custom AST
 * of boolean expressions.
 *
 * @author Holger Klein
 *
 */
public final class BooleanCodeToAST extends FormalPropertyDescriptionBaseListener {
    private static final String VOTE_SUM = "VOTE_SUM_FOR_CANDIDATE";
    private static final String VOTE_SUM_UNIQUE = "VOTE_SUM_FOR_UNIQUE_CANDIDATE";

    private static final String ELECT = "ELECT";
    private static final String VOTER = "VOTER";
    private static final String CANDIDATE = "CANDIDATE";

    private static final String CAND = "CAND";
    private static final String SEAT = "SEAT";

    private String candidateAmountSymbol;
    private String voterAmountSymbol;
    private String seatAmountSymbol;

    private ScopeHandler scopeHandlerNew;

    private BooleanExpASTData generated;
    private BooleanExpListNode ast;

    private Stack<BooleanExpressionNode> nodeStack;
    private Stack<TypeExpression> expStack;

    private int lastElectNumber;
    private int lastVoterNumber;
    private int lastElectNumberInThisListNode;

    private BooleanCodeToAST(final List<SymbolicVariable> declaredVariables,
                             final CodeGenOptions options) {
        candidateAmountSymbol = options.getCurrentAmountCandsVarName();
        voterAmountSymbol = options.getCurrentAmountVotersVarName();
        seatAmountSymbol = options.getCurrentAmountSeatsVarName();
        scopeHandlerNew = new ScopeHandler();
        scopeHandlerNew.push();
        for (final SymbolicVariable var : declaredVariables) {
            scopeHandlerNew.add(var);
        }
    }

    public static BooleanExpASTData generateAST(final String boolExpCode,
                                                final List<SymbolicVariable> declaredVariables,
                                                final CodeGenOptions options) {
        final FormalPropertyDescriptionLexer l =
                new FormalPropertyDescriptionLexer(CharStreams.fromString(boolExpCode));
        final CommonTokenStream ts = new CommonTokenStream(l);
        final FormalPropertyDescriptionParser p =
                new FormalPropertyDescriptionParser(ts);

        final ParseTreeWalker walker = new ParseTreeWalker();
        final BooleanCodeToAST listener = new BooleanCodeToAST(declaredVariables, options);
        walker.walk(listener, p.booleanExpList());
        return listener.generated;
    }

    private void setLastElectNumber(final int elect) {
        if (elect > lastElectNumber) {
            lastElectNumber = elect;
        }
    }

    private void setLastVoteNumber(final int vote) {
        if (vote > lastVoterNumber) {
            lastVoterNumber = vote;
        }
    }

    @Override
    public void enterBooleanExpList(final BooleanExpListContext ctx) {
        generated = new BooleanExpASTData();
        expStack = new Stack<TypeExpression>();
        nodeStack = new Stack<BooleanExpressionNode>();
        ast = new BooleanExpListNode();
        lastElectNumber = 0;
    }

    private void exitExp(final boolean value) {
        nodeStack.push(new FalseTrueNode(value));
    }

    @Override
    public void exitFalseExp(final FalseExpContext ctx) {
        exitExp(false);
    }

    @Override
    public void exitTrueExp(final TrueExpContext ctx) {
        exitExp(true);
    }

    @Override
    public void exitBooleanExpList(final BooleanExpListContext ctx) {
        generated.setLastElectNumber(lastElectNumber);
        generated.setLastVoteNumber(lastVoterNumber);
        generated.setTopAstNode(ast);
    }

    @Override
    public void enterBooleanExpListElement(final BooleanExpListElementContext ctx) {
        lastElectNumberInThisListNode = 0;
    }

    @Override
    public void exitBooleanExpListElement(final BooleanExpListElementContext ctx) {
        final BooleanExpListElementNode node =
                new BooleanExpListElementNode(ctx.getText(), nodeStack.pop());
        ast.addNode(node);
        setLastElectNumber(lastElectNumberInThisListNode);
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
        SymbolicVariable.VariableType type = null;
        if (quantifierTypeString.contains(VariableTypeNames.VOTER)) {
            type = VariableType.VOTER;
        } else if (quantifierTypeString.contains(VariableTypeNames.CANDIDATE)) {
            type = VariableType.CANDIDATE;
        } else if (!quantifierTypeString.contains(VariableTypeNames.SEAT)) {
            throw new NotImplementedException();
        }
        final String id =
                ctx.passSymbVarByName().symbVarByNameExp().Identifier().getText();

        scopeHandlerNew.push();
        scopeHandlerNew.add(new SymbolicVariable(id, type));
    }

    @Override
    public void exitQuantifierExp(final QuantifierExpContext ctx) {
        final String quantifierType = ctx.Quantifier().getText();
        final QuantifierNode node;

        SymbolicVariable var = null;
        final String name = ctx.passSymbVarByName().symbVarByNameExp().getText();
        if (quantifierType.contains(VOTER)) {
            var = new SymbolicVariable(name, SymbolicVariable.VariableType.VOTER);
        } else if (quantifierType.contains(CANDIDATE)) {
            var = new SymbolicVariable(name,
                    SymbolicVariable.VariableType.CANDIDATE);
        }

        if (quantifierType.contains(Quantifier.FOR_ALL)) {
            node = new ForAllNode(var, nodeStack.pop());
        } else if (quantifierType.contains(Quantifier.EXISTS)) {
            node = new ThereExistsNode(var, nodeStack.pop());
        } else {
            node = null;
        }
        nodeStack.add(node);
        scopeHandlerNew.pop();
    }

    @Override
    public void enterSymbolicVarExp(final SymbolicVarExpContext ctx) {
    }

    @Override
    public void exitSymbolicVarExp(final SymbolicVarExpContext ctx) {
    }

    @Override
    public void exitSymbVarByNameExp(final SymbVarByNameExpContext ctx) {
        final String name = ctx.getText();
        final VariableType varType = scopeHandlerNew.getType(name);
        expStack.push(new SymbolicVarByNameExp(new SymbolicVariable(name, varType)));
    }

    @Override
    public void enterNotExp(final NotExpContext ctx) {
    }

    @Override
    public void exitNotExp(final NotExpContext ctx) {
        nodeStack.add(new NotNode(nodeStack.pop()));
    }

    @Override
    public void enterComparisonExp(final ComparisonExpContext ctx) {
    }

    @Override
    public void exitComparisonExp(final ComparisonExpContext ctx) {
        final String comparisonSymbol = ctx.ComparisonSymbol().getText();
        final TypeExpression rhs = expStack.pop();
        final TypeExpression lhs = expStack.pop();
        final ComparisonNode node =
                new ComparisonNode(lhs, rhs, comparisonSymbol);
        nodeStack.add(node);
    }

    @Override
    public void enterNumberExpression(final NumberExpressionContext ctx) {
    }

    @Override
    public void exitNumberExpression(final NumberExpressionContext ctx) {
        if (ctx.Mult() != null) {
            final IntegerValuedExpression rhs =
                    (IntegerValuedExpression) expStack.pop();
            final IntegerValuedExpression lsh =
                    (IntegerValuedExpression) expStack.pop();
            final BinaryIntegerValuedNode expNode =
                    new BinaryIntegerValuedNode(lsh, rhs, ctx.Mult().getText());
            expStack.push(expNode);
        } else if (ctx.Add() != null) {
            final IntegerValuedExpression rhs =
                    (IntegerValuedExpression) expStack.pop();
            final IntegerValuedExpression lsh =
                    (IntegerValuedExpression) expStack.pop();
            final BinaryIntegerValuedNode expNode =
                    new BinaryIntegerValuedNode(lsh, rhs, ctx.Add().getText());
            expStack.push(expNode);
        }
    }

    @Override
    public void enterElectExp(final ElectExpContext ctx) {
    }

    @Override
    public void exitElectExp(final ElectExpContext ctx) {
        final String numberString =
                ctx.Elect().getText().substring(ELECT.length());
        final int number = Integer.valueOf(numberString);
        setLastElectNumber(number);

        final int amtAccessingTypes = ctx.passSymbVar().size();
        final List<SymbolicVariable> accessingVars = new ArrayList<SymbolicVariable>();

        for (int i = 0; i < amtAccessingTypes; ++i) {
            accessingVars
                    .add(((SymbolicVarByNameExp) expStack.pop()).getCbmcVar());
        }
        generated.electAccessedBy(accessingVars, number);
        expStack.push(new ElectExp(accessingVars, number));
    }

    @Override
    public void enterVoteExp(final VoteExpContext ctx) {
    }

    @Override
    public void exitVoteExp(final VoteExpContext ctx) {
        final String numberString =
                ctx.Vote().getText().substring(VOTER.length());
        final int number = Integer.valueOf(numberString);
        setLastVoteNumber(number);
        final int amtAccessingTypes = ctx.passSymbVar().size();
        final List<SymbolicVariable> accessingVars = new ArrayList<SymbolicVariable>();
        for (int i = 0; i < amtAccessingTypes; ++i) {
            accessingVars
                    .add(((SymbolicVarByNameExp) expStack.pop()).getCbmcVar());
        }
        generated.voteAccessedBy(accessingVars, number);
        expStack.push(new VoteExp(accessingVars, number));
    }

    @Override
    public void enterConstantExp(final ConstantExpContext ctx) {
    }

    @Override
    public void exitConstantExp(final ConstantExpContext ctx) {
        final int number =
                Integer.valueOf(((IntegerNode) expStack.pop()).getInteger());
        final String text = ctx.getText();
        VariableType type = VariableType.VOTER;
        if (text.startsWith(voterAmountSymbol)) {
            type = VariableType.VOTER;
        } else if (text.startsWith(candidateAmountSymbol)) {
            type = VariableType.CANDIDATE;
        } else if (text.startsWith(seatAmountSymbol)) {
            type = VariableType.SEAT;
        }

        expStack.push(new ConstantExp(type, number));
    }

    @Override
    public void exitInteger(final IntegerContext ctx) {
        expStack.push(new IntegerNode(Integer.valueOf(ctx.getText())));
    }

    @Override
    public void enterVoteSumExp(final VoteSumExpContext ctx) {
    }

    private void exitVoteSum(final String exprStr,
                             final TerminalNode tn,
                             final boolean unique) {
        final String numberString = tn.getText().substring(exprStr.length());
        final int number = Integer.valueOf(numberString);
        setLastVoteNumber(number);
        final SymbolicVarByNameExp access = (SymbolicVarByNameExp) expStack.pop();
        final VoteSumForCandExp expNode =
                new VoteSumForCandExp(number, access.getCbmcVar(), unique);
        expStack.push(expNode);

    }

    @Override
    public void exitVoteSumExp(final VoteSumExpContext ctx) {
        exitVoteSum(VOTE_SUM, ctx.Votesum(), false);
    }

    @Override
    public void enterVoteSumUniqueExp(final VoteSumUniqueExpContext ctx) {
    }

    @Override
    public void exitVoteSumUniqueExp(final VoteSumUniqueExpContext ctx) {
        exitVoteSum(VOTE_SUM_UNIQUE, ctx.VotesumUnique(), true);
    }

    private int extractNumberFromVote(final String voteString) {
        return Integer.valueOf(voteString.substring(VOTER.length()));
    }

    private int extractNumberFromElect(final String electString) {
        return Integer.valueOf(electString.substring(ELECT.length()));
    }

    @Override
    public void exitVoteTupleExp(final VoteTupleExpContext ctx) {
        final VoteTupleNode node = new VoteTupleNode();
        for (final TerminalNode vote : ctx.Vote()) {
            node.addVoteNumber(extractNumberFromVote(vote.getText()));
        }
        expStack.push(node);
    }

    @Override
    public void exitElectTupleExp(final ElectTupleExpContext ctx) {
        final ElectTupleNode node = new ElectTupleNode();
        for (final TerminalNode elect : ctx.Elect()) {
            node.addElectNumber(extractNumberFromElect(elect.getText()));
        }
        expStack.push(node);
    }

    @Override
    public void exitPermVoteExp(final PermVoteExpContext ctx) {
        final VotePermutationNode node = new VotePermutationNode();
        final int number = extractNumberFromVote(ctx.Vote().getText());
        setLastVoteNumber(number);
        node.setVoteNumber(number);
        expStack.push(node);
    }

    @Override
    public void exitPermElectExp(final PermElectExpContext ctx) {
        final ElectPermutationNode node = new ElectPermutationNode();
        final int number = extractNumberFromElect(ctx.Elect().getText());
        setLastElectNumber(number);
        node.setElectNumber(number);
        expStack.push(node);
    }

    @Override
    public void exitIntersectElect(final IntersectElectContext ctx) {
        final ElectIntersectionNode node = new ElectIntersectionNode();
        for (final TerminalNode elect : ctx.Elect()) {
            final int number = extractNumberFromElect(elect.getText());
            node.addElectNumber(number);
            setLastElectNumber(number);
        }
        expStack.push(node);
    }

    @Override
    public void exitIntersectVotes(final IntersectVotesContext ctx) {
        final VoteIntersectionNode node = new VoteIntersectionNode();
        for (final TerminalNode elect : ctx.Vote()) {
            final int number = extractNumberFromElect(elect.getText());
            node.addVoteNumber(number);
            setLastVoteNumber(number);
        }
        expStack.push(node);
    }

    @Override
    public void exitTupleExp(final TupleExpContext ctx) {
        final VoteTupleNode node = new VoteTupleNode();
        for (final TerminalNode voteNode : ctx.voteTupleExp().Vote()) {
            final int number = extractNumberFromVote(voteNode.getText());
            node.addVoteNumber(number);
            setLastVoteNumber(number);
        }
    }

    @Override
    public void exitIsEmptyExp(final IsEmptyExpContext ctx) {
        nodeStack.add(new BooleanExpIsEmptyNode(expStack.pop()));
    }

    @Override
    public void exitSymbVarByPosExp(final SymbVarByPosExpContext ctx) {
        final int number = ((IntegerNode) expStack.pop()).getInteger();
        final String text = ctx.getText();
        if (text.startsWith(VOTER)) {
            expStack.add(new SymbVarByPosExp(VariableType.VOTER, number));
        } else if (text.startsWith(CAND)) {
            expStack.add(new SymbVarByPosExp(VariableType.CANDIDATE, number));
        } else if (text.startsWith(SEAT)) {
            expStack.add(new SymbVarByPosExp(VariableType.SEAT, number));
        }
    }
}
