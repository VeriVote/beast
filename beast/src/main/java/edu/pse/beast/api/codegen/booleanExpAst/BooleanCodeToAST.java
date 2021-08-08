package edu.pse.beast.api.codegen.booleanExpAst;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.booleanExpAst.data.BinaryCombinationSymbols;
import edu.pse.beast.api.codegen.booleanExpAst.data.Quantifier;
import edu.pse.beast.api.codegen.booleanExpAst.data.VariableTypeNames;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.BooleanExpListNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BinaryRelationshipNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpIsEmptyNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpressionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ComparisonNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.EquivalenceNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.FalseTrueNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ForAllNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ImplicationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.LogicalAndNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.LogicalOrNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.NotNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.QuantifierNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ThereExistsNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.TypeExpression;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectPermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectTupleNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VotePermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteTupleNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.BinaryIntegerValuedNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.ConstantExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.IntegerNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.IntegerValuedExpression;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.VoteSumForCandExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var.SymbVarByPosExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var.SymbolicVarByNameExp;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ScopeHandler;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;
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
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.FalseExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntegerContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectElectContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectVotesContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IsEmptyExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NumberExpressionContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermElectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermVoteExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.QuantifierExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SymbVarByNameExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SymbVarByPosExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SymbolicVarExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TrueExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteSumExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteSumUniqueExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteTupleExpContext;

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

    private int highestElect;
    private int highestVote;
    private int highestElectInThisListNode;

    private BooleanCodeToAST(final List<SymbolicCBMCVar> declaredVariables,
                             final CodeGenOptions options) {
        candidateAmountSymbol = options.getCurrentAmountCandsVarName();
        voterAmountSymbol = options.getCurrentAmountVotersVarName();
        seatAmountSymbol = options.getCurrentAmountSeatsVarName();
        scopeHandlerNew = new ScopeHandler();
        scopeHandlerNew.push();
        for (final SymbolicCBMCVar var : declaredVariables) {
            scopeHandlerNew.add(var);
        }
    }

    public static BooleanExpASTData generateAST(final String boolExpCode,
                                                final List<SymbolicCBMCVar> declaredVariables,
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

    private void setHighestElect(final int elect) {
        if (elect > highestElect) {
            highestElect = elect;
        }
    }

    private void setHighestVote(final int vote) {
        if (vote > highestVote) {
            highestVote = vote;
        }
    }

    @Override
    public void enterBooleanExpList(final BooleanExpListContext ctx) {
        generated = new BooleanExpASTData();
        expStack = new Stack<TypeExpression>();
        nodeStack = new Stack<BooleanExpressionNode>();
        ast = new BooleanExpListNode();
        highestElect = 0;
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
        generated.setHighestElect(highestElect);
        generated.setHighestVote(highestVote);
        generated.setTopAstNode(ast);
    }

    @Override
    public void enterBooleanExpListElement(final BooleanExpListElementContext ctx) {
        highestElectInThisListNode = 0;
    }

    @Override
    public void exitBooleanExpListElement(final BooleanExpListElementContext ctx) {
        final BooleanExpListElementNode node =
                new BooleanExpListElementNode(ctx.getText(), nodeStack.pop());
        ast.addNode(node);
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
        SymbolicCBMCVar.CBMCVarType type = null;
        if (quantifierTypeString.contains(VariableTypeNames.VOTER)) {
            type = CBMCVarType.VOTER;
        } else if (quantifierTypeString.contains(VariableTypeNames.CANDIDATE)) {
            type = CBMCVarType.CANDIDATE;
        } else if (!quantifierTypeString.contains(VariableTypeNames.SEAT)) {
            throw new NotImplementedException();
        }
        final String id =
                ctx.passSymbVarByName().symbVarByNameExp().Identifier().getText();

        scopeHandlerNew.push();
        scopeHandlerNew.add(new SymbolicCBMCVar(id, type));
    }

    @Override
    public void exitQuantifierExp(final QuantifierExpContext ctx) {
        final String quantifierType = ctx.Quantifier().getText();
        final QuantifierNode node;

        SymbolicCBMCVar var = null;
        final String name = ctx.passSymbVarByName().symbVarByNameExp().getText();
        if (quantifierType.contains(VOTER)) {
            var = new SymbolicCBMCVar(name, SymbolicCBMCVar.CBMCVarType.VOTER);
        } else if (quantifierType.contains(CANDIDATE)) {
            var = new SymbolicCBMCVar(name,
                    SymbolicCBMCVar.CBMCVarType.CANDIDATE);
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
        final CBMCVarType varType = scopeHandlerNew.getType(name);
        expStack.push(new SymbolicVarByNameExp(new SymbolicCBMCVar(name, varType)));
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
        setHighestElect(number);

        final int amtAccessingTypes = ctx.passSymbVar().size();
        final List<SymbolicCBMCVar> accessingVars = new ArrayList<SymbolicCBMCVar>();

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
        setHighestVote(number);
        final int amtAccessingTypes = ctx.passSymbVar().size();
        final List<SymbolicCBMCVar> accessingVars = new ArrayList<SymbolicCBMCVar>();
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
        CBMCVarType type = CBMCVarType.VOTER;
        if (text.startsWith(voterAmountSymbol)) {
            type = CBMCVarType.VOTER;
        } else if (text.startsWith(candidateAmountSymbol)) {
            type = CBMCVarType.CANDIDATE;
        } else if (text.startsWith(seatAmountSymbol)) {
            type = CBMCVarType.SEAT;
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
        setHighestVote(number);
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
        setHighestVote(number);
        node.setVoteNumber(number);
        expStack.push(node);
    }

    @Override
    public void exitPermElectExp(final PermElectExpContext ctx) {
        final ElectPermutationNode node = new ElectPermutationNode();
        final int number = extractNumberFromElect(ctx.Elect().getText());
        setHighestElect(number);
        node.setElectNumber(number);
        expStack.push(node);
    }

    @Override
    public void exitIntersectElect(final IntersectElectContext ctx) {
        final ElectIntersectionNode node = new ElectIntersectionNode();
        for (final TerminalNode elect : ctx.Elect()) {
            final int number = extractNumberFromElect(elect.getText());
            node.addElectNumber(number);
            setHighestElect(number);
        }
        expStack.push(node);
    }

    @Override
    public void exitIntersectVotes(final IntersectVotesContext ctx) {
        final VoteIntersectionNode node = new VoteIntersectionNode();
        for (final TerminalNode elect : ctx.Vote()) {
            final int number = extractNumberFromElect(elect.getText());
            node.addVoteNumber(number);
            setHighestVote(number);
        }
        expStack.push(node);
    }

    @Override
    public void exitTupleExp(final TupleExpContext ctx) {
        final VoteTupleNode node = new VoteTupleNode();
        for (final TerminalNode voteNode : ctx.voteTupleExp().Vote()) {
            final int number = extractNumberFromVote(voteNode.getText());
            node.addVoteNumber(number);
            setHighestVote(number);
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
            expStack.add(new SymbVarByPosExp(CBMCVarType.VOTER, number));
        } else if (text.startsWith(CAND)) {
            expStack.add(new SymbVarByPosExp(CBMCVarType.CANDIDATE, number));
        } else if (text.startsWith(SEAT)) {
            expStack.add(new SymbVarByPosExp(CBMCVarType.SEAT, number));
        }
    }
}
