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
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingTupelChangeExpContext;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;

/**
 *
 * @author Holger Klein
 */
public class FormalPropertySyntaxTreeToAstTranslator extends FormalPropertyDescriptionBaseListener {
    private static final String AND             = "&&";
    private static final String OR              = "||";
    private static final String IMPL            = "==>";
    private static final String EQUIV           = "<==>";
    private static final String FOR_ALL         = "FOR_ALL";
    private static final String EXISTS          = "EXISTS_ONE";

    private static final String VOTE_SUM        = "VOTE_SUM_FOR_CANDIDATE";
    private static final String VOTE_SUM_UNIQUE = "VOTE_SUM_FOR_UNIQUE_CANDIDATE";

    private static final String VOTER           = "VOTER";
    private static final String CANDIDATE       = "CANDIDATE";
    private static final String SEAT            = "SEAT";
    private static final String ELECT           = "ELECT";

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

    public BooleanExpListNode generateFromSyntaxTree(BooleanExpListContext parseTree,
                                                     InputType inputType,
                                                     OutputType resType,
                                                     BooleanExpScope declaredVars) {
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
    public void enterBooleanExpList(BooleanExpListContext ctx) {
        generated = new BooleanExpListNode();
        maxVoteExp = 0;
        expStack = new Stack<>();
        nodeStack = new Stack<>();
    }

    @Override
    public void exitBooleanExpList(BooleanExpListContext ctx) {
        generated.setMaxVoteLevel(maxVoteExp);
    }

    @Override
    public void enterBooleanExpListElement(BooleanExpListElementContext ctx) {
        currentHighestElect = 0;
        this.hadBinaryBefore = false;
    }

    @Override
    public void exitBooleanExpListElement(BooleanExpListElementContext ctx) {
        generated.addNode(nodeStack.pop(), currentHighestElect);
        setNewMaxVote(currentHighestElect);
    }

    @Override
    public void enterBooleanExp(BooleanExpContext ctx) {
    }

    @Override
    public void exitBooleanExp(BooleanExpContext ctx) {
        if (ctx.notEmptyExp() != null) {
            NotEmptyExpressionNode node
                  = new NotEmptyExpressionNode(ctx.notEmptyExp(), !hadBinaryBefore);
            // FALLS FEHLER
            nodeStack.add(node);
        }
    }

    @Override
    public void enterBinaryRelationExp(BinaryRelationExpContext ctx) {
        this.hadBinaryBefore = true;
    }

    @Override
    public void exitBinaryRelationExp(BinaryRelationExpContext ctx) {
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
            node = new EquivalencyNode(lhs, rhs);
        }
        nodeStack.add(node);
    }

    @Override
    public void enterQuantifierExp(QuantifierExpContext ctx) {
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
    public void exitQuantifierExp(QuantifierExpContext ctx) {
        String quantifierType = ctx.Quantifier().getText();
        QuantifierNode node = null;
        if (quantifierType.contains(FOR_ALL)) {
            node = new ForAllNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(),
                                  nodeStack.pop());
        } else if (quantifierType.contains(EXISTS)) {
            node = new ThereExistsNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(),
                                       nodeStack.pop());
        }
        nodeStack.add(node);
        scopeHandler.exitScope();
    }

    @Override
    public void enterNotExp(NotExpContext ctx) {
    }

    @Override
    public void exitNotExp(NotExpContext ctx) {
        NotNode node = new NotNode(nodeStack.pop());
        nodeStack.add(node);
    }

    @Override
    public void enterComparisonExp(ComparisonExpContext ctx) {
    }

    @Override
    public void exitComparisonExp(ComparisonExpContext ctx) {
        String comparisonSymbolString = ctx.ComparisonSymbol().getText();
        ComparisonSymbol comparisonSymbol = new ComparisonSymbol(comparisonSymbolString);

        TypeExpression lhs;
        TypeExpression rhs;

        if (ctx.typeExp(1).getChild(0) instanceof IntersectExpContext) {
            // the right arguments
            rhs = new IntersectTypeExpNode(
                    resType,
                    (IntersectExpContext) ctx.typeExp(1).getChild(0));
        } else {
            rhs = expStack.pop();
        }

        if (ctx.typeExp(0).getChild(0) instanceof IntersectExpContext) {
            // the left argument
            lhs = new IntersectTypeExpNode(
                    resType,
                    (IntersectExpContext) ctx.typeExp(0).getChild(0));
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
    public void enterTypeExp(TypeExpContext ctx) {
    }

    @Override
    public void exitTypeExp(TypeExpContext ctx) {
    }

    @Override
    public void enterNumberExpression(NumberExpressionContext ctx) {
    }

    @Override
    public void exitNumberExpression(NumberExpressionContext ctx) {
        if (ctx.Mult() != null) {
            IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
            IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
            BinaryIntegerValuedNode expNode
                  = new BinaryIntegerValuedNode(lsh, rhs, ctx.Mult().getText());
            expStack.push(expNode);
        } else if (ctx.Add() != null) {
            IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
            IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
            BinaryIntegerValuedNode expNode
                  = new BinaryIntegerValuedNode(lsh, rhs, ctx.Add().getText());
            expStack.push(expNode);
        }
    }

    @Override
    public void enterElectExp(ElectExpContext ctx) {
    }

    @Override
    public void exitElectExp(ElectExpContext ctx) {
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
    public void enterVoteExp(VoteExpContext ctx) {

    }

    @Override
    public void exitVoteExp(VoteExpContext ctx) {
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
    public void enterConstantExp(ConstantExpContext ctx) {
    }

    @Override
    public void exitConstantExp(ConstantExpContext ctx) {
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
    public void enterVoteSumExp(VoteSumExpContext ctx) {
    }

    private void exitVoteSum(final String exprStr, final TerminalNode tn, final boolean unique) {
        String numberString = tn.getText().substring(exprStr.length());
        int number = Integer.valueOf(numberString);
        setNewMaxVote(number);
        VoteSumForCandExp expNode = new VoteSumForCandExp(number, expStack.pop(), unique);
        expStack.push(expNode);
    }

    @Override
    public void exitVoteSumExp(VoteSumExpContext ctx) {
        final String exprStr = VOTE_SUM;
        final TerminalNode tn = ctx.Votesum();
        exitVoteSum(exprStr, tn, false);
    }

    @Override
    public void enterVoteSumUniqueExp(VoteSumUniqueExpContext ctx) {
    }

    @Override
    public void exitVoteSumUniqueExp(VoteSumUniqueExpContext ctx) {
        final String exprStr = VOTE_SUM_UNIQUE;
        final TerminalNode tn = ctx.VotesumUnique();
        exitVoteSum(exprStr, tn, true);
    }

    @Override
    public void enterPassSymbVar(PassSymbVarContext ctx) {
    }

    @Override
    public void exitPassSymbVar(PassSymbVarContext ctx) {
    }

    @Override
    public void enterSymbolicVarExp(SymbolicVarExpContext ctx) {
    }

    @Override
    public void exitSymbolicVarExp(SymbolicVarExpContext ctx) {
        String name = ctx.getText();
        InternalTypeContainer type = scopeHandler.getTypeForVariable(name);
        SymbolicVarExp expNode = new SymbolicVarExp(type, new SymbolicVariable(name, type));
        expStack.push(expNode);
    }

    @Override
    public void exitInteger(IntegerContext ctx) {
        String integerString = ctx.getText();
        int heldInteger = Integer.valueOf(integerString);
        IntegerNode integerNode = new IntegerNode(heldInteger);
        expStack.push(integerNode);
    }

    private void pushAtPosNode(InternalTypeRep rep) {
        expStack.push(new AtPosExp(new InternalTypeContainer(rep),
                                   (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void exitVoterByPosExp(VoterByPosExpContext ctx) {
        pushAtPosNode(InternalTypeRep.VOTER);
    }

    @Override
    public void exitCandByPosExp(CandByPosExpContext ctx) {
        pushAtPosNode(InternalTypeRep.CANDIDATE);
    }

    @Override
    public void exitSeatByPosExp(SeatByPosExpContext ctx) {
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
    public void enterVotingListChangeExp(VotingListChangeExpContext ctx) {
        InternalTypeContainer varType = new InternalTypeContainer(InternalTypeRep.VOTER);

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
    public void exitVotingListChangeExp(VotingListChangeExpContext ctx) {
        BooleanExpressionNode node
              = new VotingListChangeExpNode(ctx.Vote(),
                                            ctx.votingListChangeContent());
        nodeStack.add(node);
        scopeHandler.exitScope();
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterVotingListChangeContent(VotingListChangeContentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitVotingListChangeContent(VotingListChangeContentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterVotingTupelChangeExp(VotingTupelChangeExpContext ctx) {
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
    public void exitVotingTupelChangeExp(VotingTupelChangeExpContext ctx) {
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
    public void enterCandidateListChangeExp(CandidateListChangeExpContext ctx) {
        InternalTypeContainer varType = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
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
    public void exitCandidateListChangeExp(CandidateListChangeExpContext ctx) {
        BooleanExpressionNode node
              = new CandidateListChangeExpNode(ctx.Elect(), ctx.intersectExp());
        nodeStack.add(node);
        scopeHandler.exitScope();
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterVoteEquivalents(VoteEquivalentsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitVoteEquivalents(VoteEquivalentsContext ctx) {
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
    public void enterConcatenationExp(ConcatenationExpContext ctx) {
        // TODO
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitConcatenationExp(ConcatenationExpContext ctx) {
        // TODO
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterSplitExp(SplitExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitSplitExp(SplitExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterPermutationExp(PermutationExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitPermutationExp(PermutationExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterIntersectExp(IntersectExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitIntersectExp(IntersectExpContext ctx) {
        // IntersectExpNode node = new IntersectExpNode(ctx, "dies ist ein test");
        // nodeStack.add(node);
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterIntersectContent(IntersectContentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitIntersectContent(IntersectContentContext ctx) {
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
    public void enterTuple(TupleContext ctx) {
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
    public void exitTuple(TupleContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterTupleContent(TupleContentContext ctx) {
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
    public void enterTypeByPosExp(TypeByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitTypeByPosExp(TypeByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterVoterByPosExp(VoterByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterCandByPosExp(CandByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterSeatByPosExp(SeatByPosExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterInteger(IntegerContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterPassType(PassTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitPassType(PassTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterPassPosition(PassPositionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitPassPosition(PassPositionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void enterPassByPos(PassByPosContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     *
     */
    @Override
    public void exitPassByPos(PassByPosContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterNotEmptyExp(NotEmptyExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitNotEmptyExp(NotEmptyExpContext ctx) {
        NotEmptyExpressionNode node = new NotEmptyExpressionNode(ctx, !hadBinaryBefore);
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
    public void enterNotEmptyContent(NotEmptyContentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitNotEmptyContent(NotEmptyContentContext ctx) {
        TerminalNode elect = ctx.Elect();
        if (elect != null) {
            String electNumber = elect.getText().substring(ELECT.length());
            int number = Integer.parseInt(electNumber);
            setNewMaxVote(number);
        }
    }
}