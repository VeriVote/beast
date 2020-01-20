package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpConstant;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanexpast.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BinaryRelationshipNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.EquivalenceNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntegerComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntersectTypeExpNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.NotNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.QuantifierNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.AtPosExp;
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
import edu.pse.beast.propertychecker.NotEmptyExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionBaseListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BinaryRelationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpListContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpListElementContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.CandByPosExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.CandidateListChangeExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ComparisonExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConstantExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ElectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntegerContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NumberExpressionContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PassByPosContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PassPositionContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PassSymbVarContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PassTypeContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.QuantifierExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SeatByPosExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SplitExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SymbolicVarExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TypeByPosExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TypeExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteSumExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteSumUniqueExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoterByPosExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingListChangeContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingListChangeExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingTupleChangeExpContext;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;

/**
 * The Class FormalPropertySyntaxTreeToAstTranslator.
 *
 * @author Holger Klein
 */
public final class FormalPropertySyntaxTreeToAstTranslator
        extends FormalPropertyDescriptionBaseListener {
    /** The Constant AND. */
    private static final String AND = "&&";

    /** The Constant OR. */
    private static final String OR = "||";

    /** The Constant IMPL. */
    private static final String IMPL = "==>";

    /** The Constant EQUIV. */
    private static final String EQUIV = "<==>";

    /** The Constant FOR_ALL. */
    private static final String FOR_ALL = "FOR_ALL";

    /** The Constant EXISTS. */
    private static final String EXISTS = "EXISTS_ONE";

    /** The Constant VOTE_SUM. */
    private static final String VOTE_SUM = "VOTE_SUM_FOR_CANDIDATE";

    /** The Constant VOTE_SUM_UNIQUE. */
    private static final String VOTE_SUM_UNIQUE = "VOTE_SUM_FOR_UNIQUE_CANDIDATE";

    /** The Constant VOTER. */
    private static final String VOTER = "VOTER";

    /** The Constant CANDIDATE. */
    private static final String CANDIDATE = "CANDIDATE";

    /** The Constant SEAT. */
    private static final String SEAT = "SEAT";

    /** The Constant ELECT. */
    private static final String ELECT = "ELECT";

    /** The generated. */
    private BooleanExpListNode generated;

    /** The input type. */
    private InputType inputType;

    /** The res type. */
    private OutputType resType;

    /** The max vote exp. */
    private int maxVoteExp;

    /** The current highest elect. */
    private int currentHighestElect;

    /** The scope handler. */
    private BooleanExpScopehandler scopeHandler;

    /** The had binary before. */
    private boolean hadBinaryBefore;

    /** The node stack. */
    // Stacks
    private Stack<BooleanExpressionNode> nodeStack;

    /** The exp stack. */
    private Stack<TypeExpression> expStack;

    /**
     * The constructor.
     *
     * @param parseTree
     *            the parse tree
     * @param inType
     *            the in type
     * @param resultType
     *            the result type
     * @param declaredVars
     *            the declared vars
     * @return the boolean exp list node
     */
    public BooleanExpListNode generateFromSyntaxTree(final BooleanExpListContext
                                                            parseTree,
                                                     final InputType inType,
                                                     final OutputType
                                                             resultType,
                                                     final BooleanExpScope
                                                             declaredVars) {
        scopeHandler = new BooleanExpScopehandler();
        scopeHandler.enterNewScope(declaredVars);
        this.inputType = inType;
        this.resType = resultType;
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, parseTree);
        return generated;
    }

    /**
     * Sets the new max vote.
     *
     * @param number
     *            the new new max vote
     */
    private void setNewMaxVote(final int number) {
        if (number > maxVoteExp) {
            maxVoteExp = number;
        }
    }

    @Override
    public void enterBooleanExpList(final BooleanExpListContext ctx) {
        generated = new BooleanExpListNode();
        maxVoteExp = 0;
        expStack = new Stack<TypeExpression>();
        nodeStack = new Stack<BooleanExpressionNode>();
    }

    @Override
    public void exitBooleanExpList(final BooleanExpListContext ctx) {
        generated.setMaxVoteLevel(maxVoteExp);
    }

    @Override
    public void enterBooleanExpListElement(
            final BooleanExpListElementContext ctx) {
        currentHighestElect = 0;
        this.hadBinaryBefore = false;
    }

    @Override
    public void exitBooleanExpListElement(
            final BooleanExpListElementContext ctx) {
        generated.addNode(nodeStack.pop(), currentHighestElect);
        setNewMaxVote(currentHighestElect);
    }

    @Override
    public void enterBooleanExp(final BooleanExpContext ctx) {
    }

    @Override
    public void exitBooleanExp(final BooleanExpContext ctx) {
        if (ctx.notEmptyExp() != null) {
            NotEmptyExpressionNode node =
                    new NotEmptyExpressionNode(
                            ctx.notEmptyExp(), !hadBinaryBefore
                    );
            // FALLS FEHLER
            nodeStack.add(node);
        }
    }

    @Override
    public void enterBinaryRelationExp(final BinaryRelationExpContext ctx) {
        this.hadBinaryBefore = true;
    }

    @Override
    public void exitBinaryRelationExp(final BinaryRelationExpContext ctx) {
        String symbol = ctx.BinaryRelationSymbol().toString();
        BooleanExpressionNode rhs = nodeStack.pop();
        BooleanExpressionNode lhs = nodeStack.pop();

        BinaryRelationshipNode node = null;

        if (symbol.equals(AND)) {
            node = new LogicalAndNode(lhs, rhs);
        } else if (symbol.equals(OR)) {
            node = new LogicalOrNode(lhs, rhs);
        } else if (symbol.equals(IMPL)) {
            node = new ImplicationNode(lhs, rhs);
        } else if (symbol.equals(EQUIV)) {
            node = new EquivalenceNode(lhs, rhs);
        }
        nodeStack.add(node);
    }

    @Override
    public void enterQuantifierExp(final QuantifierExpContext ctx) {
        String quantifierTypeString = ctx.Quantifier().getText();
        InternalTypeContainer varType = null;
        if (quantifierTypeString.contains(VOTER)) {
            varType = new InternalTypeContainer(InternalTypeRep.VOTER);
        } else if (quantifierTypeString.contains(CANDIDATE)) {
            varType = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        } else if (quantifierTypeString.contains(SEAT)) {
            varType = new InternalTypeContainer(InternalTypeRep.SEAT);
        }
        scopeHandler.enterNewScope();
        String id = ctx.passSymbVar().symbolicVarExp().Identifier().getText();
        scopeHandler.addVariable(id, varType);
    }

    @Override
    public void exitQuantifierExp(final QuantifierExpContext ctx) {
        String quantifierType = ctx.Quantifier().getText();
        QuantifierNode node = null;
        if (quantifierType.contains(FOR_ALL)) {
            node = new ForAllNode(
                    ((SymbolicVarExp) expStack.pop()).getSymbolicVar(),
                    nodeStack.pop());
        } else if (quantifierType.contains(EXISTS)) {
            node = new ThereExistsNode(
                    ((SymbolicVarExp) expStack.pop()).getSymbolicVar(),
                    nodeStack.pop());
        }
        nodeStack.add(node);
        scopeHandler.exitScope();
    }

    @Override
    public void enterNotExp(final NotExpContext ctx) {
    }

    @Override
    public void exitNotExp(final NotExpContext ctx) {
        NotNode node = new NotNode(nodeStack.pop());
        nodeStack.add(node);
    }

    @Override
    public void enterComparisonExp(final ComparisonExpContext ctx) {
    }

    @Override
    public void exitComparisonExp(final ComparisonExpContext ctx) {
        String comparisonSymbolString = ctx.ComparisonSymbol().getText();
        ComparisonSymbol comparisonSymbol = new ComparisonSymbol(
                comparisonSymbolString);

        TypeExpression lhs;
        TypeExpression rhs;

        if (ctx.typeExp(1).getChild(0) instanceof IntersectExpContext) {
            // the right arguments
            rhs = new IntersectTypeExpNode(resType,
                    (IntersectExpContext) ctx.typeExp(1).getChild(0));
        } else {
            rhs = expStack.pop();
        }

        if (ctx.typeExp(0).getChild(0) instanceof IntersectExpContext) {
            // the left argument
            lhs = new IntersectTypeExpNode(resType,
                    (IntersectExpContext) ctx.typeExp(0).getChild(0));
        } else {
            lhs = expStack.pop();
        }

        if (lhs.getInternalTypeContainer()
                .getInternalType() == InternalTypeRep.INTEGER) {
            IntegerComparisonNode node = new IntegerComparisonNode(lhs, rhs,
                    comparisonSymbol);
            nodeStack.add(node);
        } else {
            ComparisonNode node = new ComparisonNode(lhs, rhs,
                    comparisonSymbol);
            nodeStack.add(node);
        }
    }

    @Override
    public void enterTypeExp(final TypeExpContext ctx) {
    }

    @Override
    public void exitTypeExp(final TypeExpContext ctx) {
    }

    @Override
    public void enterNumberExpression(final NumberExpressionContext ctx) {
    }

    @Override
    public void exitNumberExpression(final NumberExpressionContext ctx) {
        if (ctx.Mult() != null) {
            IntegerValuedExpression rhs = (IntegerValuedExpression) expStack
                    .pop();
            IntegerValuedExpression lsh = (IntegerValuedExpression) expStack
                    .pop();
            BinaryIntegerValuedNode expNode = new BinaryIntegerValuedNode(lsh,
                    rhs, ctx.Mult().getText());
            expStack.push(expNode);
        } else if (ctx.Add() != null) {
            IntegerValuedExpression rhs = (IntegerValuedExpression) expStack
                    .pop();
            IntegerValuedExpression lsh = (IntegerValuedExpression) expStack
                    .pop();
            BinaryIntegerValuedNode expNode = new BinaryIntegerValuedNode(lsh,
                    rhs, ctx.Add().getText());
            expStack.push(expNode);
        }
    }

    @Override
    public void enterElectExp(final ElectExpContext ctx) {
    }

    @Override
    public void exitElectExp(final ElectExpContext ctx) {
        // get number
        String numberString = ctx.Elect().getText().substring(ELECT.length());
        int number = Integer.valueOf(numberString);
        if (currentHighestElect < number) {
            currentHighestElect = number;
        }
        int amtAccessingTypes = ctx.passType().size();
        TypeExpression[] accessingVars = new TypeExpression[amtAccessingTypes];

        for (int i = 0; i < amtAccessingTypes; ++i) {
            accessingVars[amtAccessingTypes - i - 1] = expStack.pop();
        }

        ElectExp expNode = new ElectExp(resType, accessingVars, number);
        expStack.push(expNode);
    }

    @Override
    public void enterVoteExp(final VoteExpContext ctx) {

    }

    @Override
    public void exitVoteExp(final VoteExpContext ctx) {
        String numberString = ctx.Vote().getText().substring(VOTER.length());
        int number = Integer.valueOf(numberString);
        setNewMaxVote(number);

        int amtAccessingTypes = ctx.passType().size();
        TypeExpression[] accessingVars = new TypeExpression[amtAccessingTypes];
        for (int i = 0; i < amtAccessingTypes; ++i) {
            accessingVars[amtAccessingTypes - i - 1] = expStack.pop();
        }
        VoteExp expNode = new VoteExp(inputType, accessingVars, number);
        expStack.push(expNode);
    }

    @Override
    public void enterConstantExp(final ConstantExpContext ctx) {
    }

    @Override
    public void exitConstantExp(final ConstantExpContext ctx) {
        String constString = ctx.getText();
        ConstantExp expNode = null;
        if (constString.equals(BooleanExpConstant.getConstForVoterAmt())) {
            expNode = new ConstantExp(constString);
        } else if (constString
                .equals(BooleanExpConstant.getConstForCandidateAmt())) {
            expNode = new ConstantExp(constString);
        } else if (constString.equals(BooleanExpConstant.getConstForSeatAmt())) {
            expNode = new ConstantExp(constString);
        }
        expStack.push(expNode);
    }

    @Override
    public void enterVoteSumExp(final VoteSumExpContext ctx) {
    }

    /**
     * Exit vote sum.
     *
     * @param exprStr
     *            the expr str
     * @param tn
     *            the tn
     * @param unique
     *            the unique
     */
    private void exitVoteSum(final String exprStr, final TerminalNode tn,
                             final boolean unique) {
        String numberString = tn.getText().substring(exprStr.length());
        int number = Integer.valueOf(numberString);
        setNewMaxVote(number);
        VoteSumForCandExp expNode =
                new VoteSumForCandExp(number, expStack.pop(), unique);
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

    @Override
    public void enterPassSymbVar(final PassSymbVarContext ctx) {
    }

    @Override
    public void exitPassSymbVar(final PassSymbVarContext ctx) {
    }

    @Override
    public void enterSymbolicVarExp(final SymbolicVarExpContext ctx) {
    }

    @Override
    public void exitSymbolicVarExp(final SymbolicVarExpContext ctx) {
        String name = ctx.getText();
        InternalTypeContainer type = scopeHandler.getTypeForVariable(name);
        SymbolicVarExp expNode =
                new SymbolicVarExp(type, new SymbolicVariable(name, type));
        expStack.push(expNode);
    }

    @Override
    public void exitInteger(final IntegerContext ctx) {
        String integerString = ctx.getText();
        int heldInteger = Integer.valueOf(integerString);
        IntegerNode integerNode = new IntegerNode(heldInteger);
        expStack.push(integerNode);
    }

    /**
     * Push at position node.
     *
     * @param rep
     *            internal type presentation
     */
    private void pushAtPosNode(final InternalTypeRep rep) {
        expStack.push(new AtPosExp(new InternalTypeContainer(rep),
                                   (IntegerValuedExpression) expStack.pop())
        );
    }

    @Override
    public void exitVoterByPosExp(final VoterByPosExpContext ctx) {
        pushAtPosNode(InternalTypeRep.VOTER);
    }

    @Override
    public void exitCandByPosExp(final CandByPosExpContext ctx) {
        pushAtPosNode(InternalTypeRep.CANDIDATE);
    }

    @Override
    public void exitSeatByPosExp(final SeatByPosExpContext ctx) {
        pushAtPosNode(InternalTypeRep.SEAT);
    }

    @Override
    public void visitTerminal(final TerminalNode tn) {
    }

    @Override
    public void visitErrorNode(final ErrorNode en) {
    }

    @Override
    public void enterEveryRule(final ParserRuleContext prc) {
    }

    @Override
    public void exitEveryRule(final ParserRuleContext prc) {
    }

    // new part here:
    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterVotingListChangeExp(final VotingListChangeExpContext ctx) {
        InternalTypeContainer varType =
                new InternalTypeContainer(InternalTypeRep.VOTER);
        String voteNumber = ctx.Vote().getText().substring(VOTER.length());
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
    public void exitVotingListChangeExp(final VotingListChangeExpContext ctx) {
        BooleanExpressionNode node =
                new VotingListChangeExpNode(
                        ctx.Vote(), ctx.votingListChangeContent()
                );
        nodeStack.add(node);
        scopeHandler.exitScope();
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterVotingListChangeContent(final VotingListChangeContentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitVotingListChangeContent(final VotingListChangeContentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterVotingTupleChangeExp(final VotingTupleChangeExpContext ctx) {
        InternalTypeContainer varType =
                new InternalTypeContainer(InternalTypeRep.VOTER);
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
    public void exitVotingTupleChangeExp(final VotingTupleChangeExpContext ctx) {
        BooleanExpressionNode node = new VotingTupleChangeExpNode(ctx.tuple(),
                ctx.splitExp());
        nodeStack.add(node);
        scopeHandler.exitScope();
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterCandidateListChangeExp(final CandidateListChangeExpContext ctx) {
        InternalTypeContainer varType =
                new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        String electNumber = ctx.Elect().getText().substring(ELECT.length());
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
    public void exitCandidateListChangeExp(final CandidateListChangeExpContext ctx) {
        BooleanExpressionNode node =
                new CandidateListChangeExpNode(
                        ctx.Elect(), ctx.intersectExp()
                );
        nodeStack.add(node);
        scopeHandler.exitScope();
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterVoteEquivalents(final VoteEquivalentsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitVoteEquivalents(final VoteEquivalentsContext ctx) {
        if (ctx.Vote() != null) {
            String vote = ctx.Vote().getText();
            String votingNumer = vote.substring(VOTER.length());
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
    public void enterConcatenationExp(final ConcatenationExpContext ctx) {
        // TODO
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitConcatenationExp(final ConcatenationExpContext ctx) {
        // TODO
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterSplitExp(final SplitExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitSplitExp(final SplitExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterPermutationExp(final PermutationExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitPermutationExp(final PermutationExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterIntersectExp(final IntersectExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitIntersectExp(final IntersectExpContext ctx) {
        // IntersectExpNode node =
        //     new IntersectExpNode(ctx, "Dies ist ein test.");
        // nodeStack.add(node);
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterIntersectContent(final IntersectContentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitIntersectContent(final IntersectContentContext ctx) {
        if (ctx.Elect() != null) {
            String elect = ctx.Elect().getText();
            String electNumber = elect.substring(ELECT.length());
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
    public void enterTuple(final TupleContext ctx) {
        String votenumber = ctx.Vote().getText().substring(VOTER.length());
        int number = Integer.parseInt(votenumber);
        setNewMaxVote(number);
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitTuple(final TupleContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterTupleContent(final TupleContentContext ctx) {
        String votenumber = ctx.Vote().getText().substring(VOTER.length());
        int number = Integer.parseInt(votenumber);
        setNewMaxVote(number);
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterTypeByPosExp(final TypeByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitTypeByPosExp(final TypeByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterVoterByPosExp(final VoterByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterCandByPosExp(final CandByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterSeatByPosExp(final SeatByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterInteger(final IntegerContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterPassType(final PassTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitPassType(final PassTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterPassPosition(final PassPositionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitPassPosition(final PassPositionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterPassByPos(final PassByPosContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitPassByPos(final PassByPosContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterNotEmptyExp(final NotEmptyExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitNotEmptyExp(final NotEmptyExpContext ctx) {
        NotEmptyExpressionNode node =
                new NotEmptyExpressionNode(ctx, !hadBinaryBefore);
        // FALLS FEHLER
        nodeStack.add(node);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterNotEmptyContent(final NotEmptyContentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitNotEmptyContent(final NotEmptyContentContext ctx) {
        TerminalNode elect = ctx.Elect();
        if (elect != null) {
            String electNumber = elect.getText().substring(ELECT.length());
            int number = Integer.parseInt(electNumber);
            setNewMaxVote(number);
        }
    }
}
