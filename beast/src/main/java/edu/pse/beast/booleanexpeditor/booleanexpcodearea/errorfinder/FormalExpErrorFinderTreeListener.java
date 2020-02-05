package edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.codeareajavafx.NewCodeArea;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntersectTypeExpNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.AtPosExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.ConstantExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerValuedExpression;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.VoteSumForCandExp;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.datatypes.propertydescription.VariableListListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.AddedContentExpContext;
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
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.BooleanExpScopehandler;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * The listener interface for receiving formalExpErrorFinderTree events. The
 * class that is interested in processing a formalExpErrorFinderTree event
 * implements this interface, and the object created with that class is
 * registered with a component using the component's
 * <code>addFormalExpErrorFinderTreeListener</code> method. When the
 * formalExpErrorFinderTree event occurs, that object's appropriate method is
 * invoked.
 *
 * @author Holger Klein
 */
public final class FormalExpErrorFinderTreeListener
        implements FormalPropertyDescriptionListener, VariableListListener {
    /** The Constant UNDERSCORE. */
    private static final String UNDERSCORE = "_";
    /** The Constant VOTES. */
    private static final String VOTES = "VOTES";
    /** The Constant CANDIDATE. */
    private static final String CANDIDATE = "CANDIDATE";
    /** The Constant VOTE_SUM_FOR_. */
    private static final String VOTE_SUM_FOR = "VOTE_SUM_FOR_";
    /** The Constant UNIQUE. */
    private static final String UNIQUE = "UNIQUE";
    /** The Constant VOTE_SUM_FOR_CANDIDATE. */
    private static final String VOTE_SUM_FOR_CANDIDATE =
            VOTE_SUM_FOR + CANDIDATE;
    /** The Constant VOTE_SUM_FOR_UNIQUE_CANDIDATE. */
    private static final String VOTE_SUM_FOR_UNIQUE_CANDIDATE =
            VOTE_SUM_FOR + UNIQUE + UNDERSCORE + CANDIDATE;
    /** The Constant ELECT. */
    private static final String ELECT = "ELECT";

    /** The created. */
    private final ArrayList<CodeError> created = new ArrayList<CodeError>();

    /** The scope handler. */
    private final BooleanExpScopehandler scopeHandler = new BooleanExpScopehandler();

    /** The elec description. */
    private final ElectionDescription elecDescription;

    /** The container. */
    private ElectionTypeContainer container;

    /** The exp stack. */
    private Stack<TypeExpression> expStack;

    /**
     * Constructor to create the error finder in the tree list.
     *
     * @param list
     *            the list with the symbolic variables
     * @param codeArea
     *            the editor where the code is
     * @param elecDescr
     *            the voting rule
     */
    public FormalExpErrorFinderTreeListener(final SymbolicVariableList list,
                                            final NewCodeArea codeArea,
                                            final ElectionDescription elecDescr) {
        this.elecDescription = elecDescr;
        list.addListener(this);
        scopeHandler.enterNewScope();
        for (SymbolicVariable var : list.getSymbolicVariables()) {
            scopeHandler.addVariable(var.getId(), var.getInternalTypeContainer());
        }
        this.container = codeArea.getElectionDescription().getContainer();
    }

    /**
     * Gives all code errors found.
     *
     * @return a list of all found errors
     */
    public ArrayList<CodeError> getErrors() {
        return created;
    }

    @Override
    public void enterBooleanExpList(final BooleanExpListContext ctx) {
        expStack = new Stack<TypeExpression>();
        created.clear();
    }

    @Override
    public void exitBooleanExpList(final BooleanExpListContext ctx) {
    }

    @Override
    public void enterBooleanExpListElement(final BooleanExpListElementContext ctx) {
    }

    @Override
    public void exitBooleanExpListElement(final BooleanExpListElementContext ctx) {
    }

    @Override
    public void enterBooleanExp(final BooleanExpContext ctx) {
    }

    @Override
    public void exitBooleanExp(final BooleanExpContext ctx) {
    }

    @Override
    public void enterBinaryRelationExp(final BinaryRelationExpContext ctx) {
    }

    @Override
    public void exitBinaryRelationExp(final BinaryRelationExpContext ctx) {
    }

    @Override
    public void enterQuantifierExp(final QuantifierExpContext ctx) {
        final String quantifierTypeString = ctx.Quantifier().getText();
        InternalTypeContainer varType = null;
        final InternalTypeRep[] internalTypeReps =
                new InternalTypeRep[] {
                    InternalTypeRep.VOTER,
                    InternalTypeRep.CANDIDATE,
                    InternalTypeRep.SEAT
                };
        for (final InternalTypeRep typeRep : internalTypeReps) {
            if (quantifierTypeString.contains(typeRep.name())) {
                varType = new InternalTypeContainer(typeRep);
            }
        }
        scopeHandler.enterNewScope();
        final String id = ctx.passSymbVar().symbolicVarExp().Identifier().getText();
        scopeHandler.addVariable(id, varType);
    }

    @Override
    public void exitQuantifierExp(final QuantifierExpContext ctx) {
        scopeHandler.exitScope();
    }

    @Override
    public void enterNotExp(final NotExpContext ctx) {
    }

    @Override
    public void exitNotExp(final NotExpContext ctx) {
    }

    @Override
    public void enterComparisonExp(final ComparisonExpContext ctx) {
    }

    @Override
    public void exitComparisonExp(final ComparisonExpContext ctx) {
        final TypeExpContext rhexp = ctx.typeExp(1);
        final TypeExpContext lhexp = ctx.typeExp(0);

        final TypeExpression rhs; // the right arguments
        if (rhexp.getChild(0) instanceof IntersectExpContext) {
            rhs = new IntersectTypeExpNode(
                    elecDescription.getContainer().getOutputType(),
                    (IntersectExpContext) rhexp.getChild(0));
        } else {
            rhs = expStack.pop();
        }
        final TypeExpression lhs; // the left argument
        if (lhexp.getChild(0) instanceof IntersectExpContext) {
            lhs = new IntersectTypeExpNode(
                    elecDescription.getContainer().getOutputType(),
                    (IntersectExpContext) lhexp.getChild(0));
        } else {
            lhs = expStack.pop();
        }

        InternalTypeContainer lhsCont = lhs.getInternalTypeContainer();
        InternalTypeContainer rhsCont = rhs.getInternalTypeContainer();
        if (lhsCont.getListLvl() != rhsCont.getListLvl()) {
            final CodeError codeError =
                    BooleanExpErrorFactory
                    .createCantCompareDifferentListLevels(ctx, lhsCont, rhsCont);
            created.add(codeError);
        } else {
            while (lhsCont.isList()) {
                lhsCont = lhsCont.getListedType();
                rhsCont = rhsCont.getListedType();
            }
            if (lhsCont.getInternalType() != rhsCont.getInternalType()) {
                final CodeError codeError =
                        BooleanExpErrorFactory
                        .createCantCompareTypes(ctx, lhsCont, rhsCont);
                created.add(codeError);
            }
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
        if (ctx.Mult() != null || ctx.Add() != null) {
            final IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
            final IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
            final BinaryIntegerValuedNode expNode =
                    new BinaryIntegerValuedNode(lsh, rhs,
                                                ctx.Mult() != null
                                                ? ctx.Mult().getText()
                                                        : ctx.Add().getText());
            expStack.push(expNode);
        }
    }

    @Override
    public void enterTypeByPosExp(final TypeByPosExpContext ctx) {
    }

    @Override
    public void exitTypeByPosExp(final TypeByPosExpContext ctx) {
    }

    @Override
    public void enterVoterByPosExp(final VoterByPosExpContext ctx) {
    }

    @Override
    public void exitVoterByPosExp(final VoterByPosExpContext ctx) {
        expStack.push(new AtPosExp(new InternalTypeContainer(InternalTypeRep.VOTER),
                                   (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void enterCandByPosExp(final CandByPosExpContext ctx) {
    }

    @Override
    public void exitCandByPosExp(final CandByPosExpContext ctx) {
        expStack.push(new AtPosExp(new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                                   (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void enterSeatByPosExp(final SeatByPosExpContext ctx) {
    }

    @Override
    public void exitSeatByPosExp(final SeatByPosExpContext ctx) {
        expStack.push(new AtPosExp(new InternalTypeContainer(InternalTypeRep.SEAT),
                                   (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void enterInteger(final IntegerContext ctx) {
    }

    @Override
    public void exitInteger(final IntegerContext ctx) {
        final int heldInteger = Integer.valueOf(ctx.getText());
        expStack.push(new IntegerNode(heldInteger));
    }

    @Override
    public void enterElectExp(final ElectExpContext ctx) {
        testIfTooManyVarsPassed(ctx.passType(),
                                container.getOutputType().getInternalTypeContainer());
    }

    @Override
    public void exitElectExp(final ElectExpContext ctx) {
        InternalTypeContainer cont =
                container.getOutputType().getInternalTypeContainer();
        testIfWrongTypePassed(ctx.passType(), cont);
        for (int i = 0; i < ctx.passType().size() && cont.isList(); ++i) {
            cont = cont.getListedType();
        }
        final String numberString = ctx.Elect().getText().substring(ELECT.length());
        if (Integer.valueOf(numberString).intValue() == 0) {
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroElect(ctx));
        }
        expStack.add(new ElectExp(cont, null, 0));
    }

    @Override
    public void enterVoteExp(final VoteExpContext ctx) {
        testIfTooManyVarsPassed(ctx.passType(),
                                container.getInputType().getInternalTypeContainer());
    }

    @Override
    public void exitVoteExp(final VoteExpContext ctx) {
        InternalTypeContainer cont =
                container.getInputType().getInternalTypeContainer();
        testIfWrongTypePassed(ctx.passType(), cont);
        for (int i = 0; i < ctx.passType().size() && cont.isList(); ++i) {
            cont = cont.getListedType();
        }
        final String numberString = ctx.Vote().getText().substring(VOTES.length());
        if (Integer.valueOf(numberString).intValue() == 0) {
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroVotes(ctx));
        }
        expStack.add(new ElectExp(cont, null, 0));
    }

    @Override
    public void enterPassType(final PassTypeContext ctx) {
    }

    @Override
    public void exitPassType(final PassTypeContext ctx) {
    }

    /**
     * Test if too many vars passed.
     *
     * @param ctx
     *            the ctx
     * @param cont
     *            the cont
     */
    private void testIfTooManyVarsPassed(final List<PassTypeContext> ctx,
                                         final InternalTypeContainer cont) {
        final int amountPassedVariables = ctx.size();
        int listDepth = 0;
        InternalTypeContainer c = cont;
        while (c.isList()) {
            listDepth++;
            c = c.getListedType();
        }
        for (; listDepth < amountPassedVariables; ++listDepth) {
            created.add(BooleanExpErrorFactory.createTooManyVarsPassedError(ctx.get(listDepth)));
        }
    }

    /**
     * Test if wrong type passed.
     *
     * @param ctx
     *            the ctx
     * @param cont
     *            the cont
     */
    private void testIfWrongTypePassed(final List<PassTypeContext> ctx,
                                       final InternalTypeContainer cont) {
        final int amtPassed = ctx.size();
        final Stack<TypeExpression> passedTypes = new Stack<TypeExpression>();
        for (int i = 0; i < amtPassed; ++i) {
            passedTypes.add(expStack.pop());
        }
        int i = 0;
        InternalTypeContainer c = cont;
        while (c.isList() && i < ctx.size()) {
            final TypeExpression currentVarExp = passedTypes.pop();
            if (c.getAccessTypeIfList()
                    != currentVarExp.getInternalTypeContainer().getInternalType()) {
                final CodeError codeError =
                        BooleanExpErrorFactory
                        .createWrongVarTypePassed(c, ctx.get(i), currentVarExp);
                created.add(codeError);
            }
            ++i;
            c = c.getListedType();
        }
    }

    @Override
    public void enterConstantExp(final ConstantExpContext ctx) {
    }

    @Override
    public void exitConstantExp(final ConstantExpContext ctx) {
        expStack.add(new ConstantExp(ctx.getText()));
    }

    @Override
    public void enterVoteSumExp(final VoteSumExpContext ctx) {
    }

    /**
     * Exit vote sum exp.
     *
     * @param ctx
     *            the ctx
     * @param unique
     *            the unique
     */
    private void exitVoteSumExp(final ParserRuleContext ctx,
                                final boolean unique) {
        final Class<VoteSumUniqueExpContext> cu = VoteSumUniqueExpContext.class;
        final Class<VoteSumExpContext> c = VoteSumExpContext.class;

        final TypeExpression passedVar = expStack.pop();
        if (passedVar.getInternalTypeContainer().getInternalType()
                != InternalTypeRep.CANDIDATE) {
            final CodeError ce = unique
                    ? BooleanExpErrorFactory.createWrongVarToVotesumError(
                            cu.cast(ctx), passedVar.getInternalTypeContainer())
                    : BooleanExpErrorFactory.createWrongVarToVotesumError(
                            c.cast(ctx), passedVar.getInternalTypeContainer());
            created.add(ce);
        }
        final TerminalNode tn =
                unique ? cu.cast(ctx).VotesumUnique() : c.cast(ctx).Votesum();
        final String expStr =
                unique ? VOTE_SUM_FOR_UNIQUE_CANDIDATE : VOTE_SUM_FOR_CANDIDATE;
        final String numberString = tn.getText().substring(expStr.length());

        final int number = Integer.valueOf(numberString);
        if (number == 0) {
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroVotesum(ctx));
        }
        expStack.add(new VoteSumForCandExp(number, passedVar, unique));
    }

    @Override
    public void exitVoteSumExp(final VoteSumExpContext ctx) {
        exitVoteSumExp(ctx, false);
    }

    @Override
    public void enterVoteSumUniqueExp(final VoteSumUniqueExpContext ctx) {
    }

    @Override
    public void exitVoteSumUniqueExp(final VoteSumUniqueExpContext ctx) {
        exitVoteSumExp(ctx, true);
    }

    @Override
    public void enterPassSymbVar(final PassSymbVarContext ctx) {
    }

    @Override
    public void exitPassSymbVar(final PassSymbVarContext ctx) {
    }

    @Override
    public void enterPassPosition(final PassPositionContext ctx) {
    }

    @Override
    public void exitPassPosition(final PassPositionContext ctx) {
    }

    @Override
    public void enterPassByPos(final PassByPosContext ctx) {
    }

    @Override
    public void exitPassByPos(final PassByPosContext ctx) {
    }

    @Override
    public void enterSymbolicVarExp(final SymbolicVarExpContext ctx) {
    }

    @Override
    public void exitSymbolicVarExp(final SymbolicVarExpContext ctx) {
        final String name = ctx.getText();
        InternalTypeContainer type = scopeHandler.getTypeForVariable(name);
        if (type == null) {
            created.add(BooleanExpErrorFactory.createVarNotDeclaredErr(ctx));
            type = new InternalTypeContainer(InternalTypeRep.NULL);
        }
        expStack.add(new SymbolicVarExp(type, new SymbolicVariable(name, type)));
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

    @Override
    public void addedVar(final SymbolicVariable var) {
        scopeHandler.addVariable(var.getId(), var.getInternalTypeContainer());
    }

    @Override
    public void removedVar(final SymbolicVariable var) {
        scopeHandler.removeFromTopScope(var.getId());
    }

    // Here are the new commands:

    @Override
    public void enterVotingListChangeExp(final VotingListChangeExpContext ctx) {
    }

    @Override
    public void exitVotingListChangeExp(final VotingListChangeExpContext ctx) {
    }

    @Override
    public void enterVotingListChangeContent(final VotingListChangeContentContext ctx) {
    }

    @Override
    public void exitVotingListChangeContent(final VotingListChangeContentContext ctx) {
    }

    @Override
    public void enterVotingTupleChangeExp(final VotingTupleChangeExpContext ctx) {
    }

    @Override
    public void exitVotingTupleChangeExp(final VotingTupleChangeExpContext ctx) {
    }

    @Override
    public void enterCandidateListChangeExp(final CandidateListChangeExpContext ctx) {
    }

    @Override
    public void exitCandidateListChangeExp(final CandidateListChangeExpContext ctx) {
    }

    @Override
    public void enterVoteEquivalents(final VoteEquivalentsContext ctx) {
    }

    @Override
    public void exitVoteEquivalents(final VoteEquivalentsContext ctx) {
    }

    @Override
    public void enterConcatenationExp(final ConcatenationExpContext ctx) {
    }

    @Override
    public void exitConcatenationExp(final ConcatenationExpContext ctx) {
    }

    @Override
    public void enterSplitExp(final SplitExpContext ctx) {
    }

    @Override
    public void exitSplitExp(final SplitExpContext ctx) {
    }

    @Override
    public void enterPermutationExp(final PermutationExpContext ctx) {
    }

    @Override
    public void exitPermutationExp(final PermutationExpContext ctx) {
    }

    @Override
    public void enterIntersectExp(final IntersectExpContext ctx) {
    }

    @Override
    public void exitIntersectExp(final IntersectExpContext ctx) {
    }

    @Override
    public void enterIntersectContent(final IntersectContentContext ctx) {
    }

    @Override
    public void exitIntersectContent(final IntersectContentContext ctx) {
    }

    @Override
    public void enterTuple(final TupleContext ctx) {
    }

    @Override
    public void exitTuple(final TupleContext ctx) {
    }

    @Override
    public void enterTupleContent(final TupleContentContext ctx) {
    }

    @Override
    public void exitTupleContent(final TupleContentContext ctx) {
    }

    // And here are the new/recent new ones.

    @Override
    public void enterNotEmptyExp(final NotEmptyExpContext ctx) {
    }

    @Override
    public void exitNotEmptyExp(final NotEmptyExpContext ctx) {
    }

    @Override
    public void enterNotEmptyContent(final NotEmptyContentContext ctx) {
    }

    @Override
    public void exitNotEmptyContent(final NotEmptyContentContext ctx) {
    }

    @Override
    public void enterAddedContentExp(final AddedContentExpContext ctx) {
    }

    @Override
    public void exitAddedContentExp(final AddedContentExpContext ctx) {
    }
}
