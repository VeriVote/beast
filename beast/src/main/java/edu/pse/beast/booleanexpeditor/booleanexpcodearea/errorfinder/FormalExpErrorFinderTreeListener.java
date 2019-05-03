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
import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
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
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingTupelChangeExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.BooleanExpScopehandler;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;

/**
 *
 * @author Holger Klein
 */
public class FormalExpErrorFinderTreeListener
        implements FormalPropertyDescriptionListener,
                    VariableListListener,
                    ElectionDescriptionChangeListener {
    private final ArrayList<CodeError> created = new ArrayList<>();
    private final BooleanExpScopehandler scopeHandler = new BooleanExpScopehandler();
    private final ElectionDescription elecDescription;

    private ElectionTypeContainer container;
    private Stack<TypeExpression> expStack;

    /**
     * Constructor to create the error finder in the tree list.
     *
     * @param list            the list with the symbolic variables
     * @param codeArea        the editor where the code is
     * @param elecDescription the voting rule
     */
    public FormalExpErrorFinderTreeListener(SymbolicVariableList list, NewCodeArea codeArea,
                                            ElectionDescription elecDescription) {
        this.elecDescription = elecDescription;
        list.addListener(this);
        scopeHandler.enterNewScope();
        for (SymbolicVariable var : list.getSymbolicVariables()) {
            scopeHandler.addVariable(var.getId(), var.getInternalTypeContainer());
        }
        this.container = codeArea.getElectionDescription().getContainer();
        codeArea.addListener(this);
    }

    /**
     * Sets up the input for the error finder.
     *
     * @param inputType the election type container
     */
    public void setInput(InputType inputType) {
        container.setInput(inputType);
    }

    /**
     * Sets up the output for the error finder.
     *
     * @param outputType the election type container
     */
    public void setOutput(OutputType outputType) {
        container.setOutput(outputType);
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
    public void enterBooleanExpList(BooleanExpListContext ctx) {
        expStack = new Stack<>();
        created.clear();
    }

    @Override
    public void exitBooleanExpList(BooleanExpListContext ctx) {
    }

    @Override
    public void enterBooleanExpListElement(BooleanExpListElementContext ctx) {
    }

    @Override
    public void exitBooleanExpListElement(BooleanExpListElementContext ctx) {
    }

    @Override
    public void enterBooleanExp(BooleanExpContext ctx) {
    }

    @Override
    public void exitBooleanExp(BooleanExpContext ctx) {
    }

    @Override
    public void enterBinaryRelationExp(BinaryRelationExpContext ctx) {
    }

    @Override
    public void exitBinaryRelationExp(BinaryRelationExpContext ctx) {
    }

    @Override
    public void enterQuantifierExp(QuantifierExpContext ctx) {
        String quantifierTypeString = ctx.Quantifier().getText();
        InternalTypeContainer varType = null;
        for (InternalTypeRep typeRep
                : new InternalTypeRep[]
                    {InternalTypeRep.VOTER, InternalTypeRep.CANDIDATE, InternalTypeRep.SEAT }
        ) {
            if (quantifierTypeString.contains(typeRep.name())) {
                varType = new InternalTypeContainer(typeRep);
            }
        }
        scopeHandler.enterNewScope();
        final String id = ctx.passSymbVar().symbolicVarExp().Identifier().getText();
        scopeHandler.addVariable(id, varType);
    }

    @Override
    public void exitQuantifierExp(QuantifierExpContext ctx) {
        scopeHandler.exitScope();
    }

    @Override
    public void enterNotExp(NotExpContext ctx) {
    }

    @Override
    public void exitNotExp(NotExpContext ctx) {
    }

    @Override
    public void enterComparisonExp(ComparisonExpContext ctx) {
    }

    @Override
    public void exitComparisonExp(ComparisonExpContext ctx) {
        final TypeExpContext rhexp = ctx.typeExp(1);
        final TypeExpContext lhexp = ctx.typeExp(0);

        final TypeExpression rhs; // the right arguments
        if (rhexp.getChild(0) instanceof IntersectExpContext) {
            rhs = new IntersectTypeExpNode(elecDescription.getContainer().getOutputType(),
                    (IntersectExpContext) rhexp.getChild(0));
        } else {
            rhs = expStack.pop();
        }
        final TypeExpression lhs; // the left argument
        if (lhexp.getChild(0) instanceof IntersectExpContext) {
            lhs = new IntersectTypeExpNode(elecDescription.getContainer().getOutputType(),
                    (IntersectExpContext) lhexp.getChild(0));
        } else {
            lhs = expStack.pop();
        }

        InternalTypeContainer lhsCont = lhs.getInternalTypeContainer();
        InternalTypeContainer rhsCont = rhs.getInternalTypeContainer();
        if (lhsCont.getListLvl() != rhsCont.getListLvl()) {
            final CodeError codeError
                  = BooleanExpErrorFactory
                    .createCantCompareDifferentListLevels(ctx, lhsCont, rhsCont);
            created.add(codeError);
        } else {
            while (lhsCont.isList()) {
                lhsCont = lhsCont.getListedType();
                rhsCont = rhsCont.getListedType();
            }
            if (lhsCont.getInternalType() != rhsCont.getInternalType()) {
                final CodeError codeError
                      = BooleanExpErrorFactory.createCantCompareTypes(ctx, lhsCont, rhsCont);
                created.add(codeError);
            }
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
        if (ctx.Mult() != null || ctx.Add() != null) {
            IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
            IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
            final BinaryIntegerValuedNode expNode
                        = new BinaryIntegerValuedNode(lsh, rhs,
                                                      ctx.Mult() != null
                                                      ? ctx.Mult().getText()
                                                              : ctx.Add().getText());
            expStack.push(expNode);
        }
    }

    @Override
    public void enterTypeByPosExp(TypeByPosExpContext ctx) {
    }

    @Override
    public void exitTypeByPosExp(TypeByPosExpContext ctx) {
    }

    @Override
    public void enterVoterByPosExp(VoterByPosExpContext ctx) {
    }

    @Override
    public void exitVoterByPosExp(VoterByPosExpContext ctx) {
        expStack.push(new AtPosExp(new InternalTypeContainer(InternalTypeRep.VOTER),
                (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void enterCandByPosExp(CandByPosExpContext ctx) {
    }

    @Override
    public void exitCandByPosExp(CandByPosExpContext ctx) {
        expStack.push(new AtPosExp(new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void enterSeatByPosExp(SeatByPosExpContext ctx) {
    }

    @Override
    public void exitSeatByPosExp(SeatByPosExpContext ctx) {
        expStack.push(new AtPosExp(new InternalTypeContainer(InternalTypeRep.SEAT),
                (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void enterInteger(IntegerContext ctx) {
    }

    @Override
    public void exitInteger(IntegerContext ctx) {
        int heldInteger = Integer.valueOf(ctx.getText());
        expStack.push(new IntegerNode(heldInteger));
    }

    @Override
    public void enterElectExp(ElectExpContext ctx) {
        testIfTooManyVarsPassed(ctx.passType(),
                                container.getOutputType().getInternalTypeContainer());
    }

    @Override
    public void exitElectExp(ElectExpContext ctx) {
        InternalTypeContainer cont = container.getOutputType().getInternalTypeContainer();
        testIfWrongTypePassed(ctx.passType(), cont);
        for (int i = 0; i < ctx.passType().size() && cont.isList(); ++i) {
            cont = cont.getListedType();
        }
        String numberString = ctx.Elect().getText().substring("ELECT".length());
        if (Integer.valueOf(numberString).intValue() == 0) {
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroElect(ctx));
        }
        expStack.add(new ElectExp(cont, null, 0));
    }

    @Override
    public void enterVoteExp(VoteExpContext ctx) {
        testIfTooManyVarsPassed(ctx.passType(),
                                container.getInputType().getInternalTypeContainer());
    }

    @Override
    public void exitVoteExp(VoteExpContext ctx) {
        InternalTypeContainer cont = container.getInputType().getInternalTypeContainer();
        testIfWrongTypePassed(ctx.passType(), cont);
        for (int i = 0; i < ctx.passType().size() && cont.isList(); ++i) {
            cont = cont.getListedType();
        }
        String numberString = ctx.Vote().getText().substring("VOTES".length());
        if (Integer.valueOf(numberString).intValue() == 0) {
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroVotes(ctx));
        }
        expStack.add(new ElectExp(cont, null, 0));
    }

    @Override
    public void enterPassType(PassTypeContext ctx) {
    }

    @Override
    public void exitPassType(PassTypeContext ctx) {
    }

    private void testIfTooManyVarsPassed(List<PassTypeContext> ctx,
                                         InternalTypeContainer cont) {
        int amountPassedVariables = ctx.size();
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

    private void testIfWrongTypePassed(List<PassTypeContext> ctx,
                                       InternalTypeContainer cont) {
        int amtPassed = ctx.size();
        Stack<TypeExpression> passedTypes = new Stack<>();
        for (int i = 0; i < amtPassed; ++i) {
            passedTypes.add(expStack.pop());
        }
        int i = 0;
        InternalTypeContainer c = cont;
        while (c.isList() && i < ctx.size()) {
            TypeExpression currentVarExp = passedTypes.pop();
            if (c.getAccessTypeIfList()
                    != currentVarExp.getInternalTypeContainer().getInternalType()) {
                final CodeError codeError
                      = BooleanExpErrorFactory.createWrongVarTypePassed(c,
                                                                        ctx.get(i),
                                                                        currentVarExp);
                created.add(codeError);
            }
            ++i;
            c = c.getListedType();
        }
    }

    @Override
    public void enterConstantExp(ConstantExpContext ctx) {
    }

    @Override
    public void exitConstantExp(ConstantExpContext ctx) {
        expStack.add(new ConstantExp(ctx.getText()));
    }

    @Override
    public void enterVoteSumExp(VoteSumExpContext ctx) {
    }

    private void exitVoteSumExp(ParserRuleContext ctx, boolean unique) {
        final Class<VoteSumUniqueExpContext> cu = VoteSumUniqueExpContext.class;
        final Class<VoteSumExpContext> c = VoteSumExpContext.class;

        TypeExpression passedVar = expStack.pop();
        if (passedVar.getInternalTypeContainer().getInternalType() != InternalTypeRep.CANDIDATE) {
            final CodeError ce = unique
                    ? BooleanExpErrorFactory.createWrongVarToVotesumError(cu.cast(ctx),
                            passedVar.getInternalTypeContainer())
                    : BooleanExpErrorFactory.createWrongVarToVotesumError(c.cast(ctx),
                            passedVar.getInternalTypeContainer());
            created.add(ce);
        }
        final TerminalNode tn = unique ? cu.cast(ctx).VotesumUnique() : c.cast(ctx).Votesum();
        final String expStr = unique ? "VOTE_SUM_FOR_UNIQUE_CANDIDATE" : "VOTE_SUM_FOR_CANDIDATE";
        String numberString = tn.getText().substring(expStr.length());

        final int number = Integer.valueOf(numberString);
        if (number == 0) {
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroVotesum(ctx));
        }
        expStack.add(new VoteSumForCandExp(number, passedVar, unique));
    }

    @Override
    public void exitVoteSumExp(VoteSumExpContext ctx) {
        exitVoteSumExp(ctx, false);
    }

    @Override
    public void enterVoteSumUniqueExp(VoteSumUniqueExpContext ctx) {
    }

    @Override
    public void exitVoteSumUniqueExp(VoteSumUniqueExpContext ctx) {
        exitVoteSumExp(ctx, true);
    }

    @Override
    public void enterPassSymbVar(PassSymbVarContext ctx) {
    }

    @Override
    public void exitPassSymbVar(PassSymbVarContext ctx) {
    }

    @Override
    public void enterPassPosition(PassPositionContext ctx) {
    }

    @Override
    public void exitPassPosition(PassPositionContext ctx) {
    }

    @Override
    public void enterPassByPos(PassByPosContext ctx) {
    }

    @Override
    public void exitPassByPos(PassByPosContext ctx) {
    }

    @Override
    public void enterSymbolicVarExp(SymbolicVarExpContext ctx) {
    }

    @Override
    public void exitSymbolicVarExp(SymbolicVarExpContext ctx) {
        String name = ctx.getText();
        InternalTypeContainer type = scopeHandler.getTypeForVariable(name);
        if (type == null) {
            created.add(BooleanExpErrorFactory.createVarNotDeclaredErr(ctx));
            type = new InternalTypeContainer(InternalTypeRep.NULL);
        }
        expStack.add(new SymbolicVarExp(type, new SymbolicVariable(name, type)));
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

    @Override
    public void addedVar(SymbolicVariable var) {
        scopeHandler.addVariable(var.getId(), var.getInternalTypeContainer());
    }

    @Override
    public void removedVar(SymbolicVariable var) {
        scopeHandler.removeFromTopScope(var.getId());
    }

    @Override
    public void inputChanged(InputType input) {
        this.container.setInput(input);
    }

    @Override
    public void outputChanged(OutputType output) {
        this.container.setOutput(output);
    }

    // Here are the new commands:

    @Override
    public void enterVotingListChangeExp(VotingListChangeExpContext ctx) {
    }

    @Override
    public void exitVotingListChangeExp(VotingListChangeExpContext ctx) {
    }

    @Override
    public void enterVotingListChangeContent(VotingListChangeContentContext ctx) {
    }

    @Override
    public void exitVotingListChangeContent(VotingListChangeContentContext ctx) {
    }

    @Override
    public void enterVotingTupelChangeExp(VotingTupelChangeExpContext ctx) {
    }

    @Override
    public void exitVotingTupelChangeExp(VotingTupelChangeExpContext ctx) {
    }

    @Override
    public void enterCandidateListChangeExp(CandidateListChangeExpContext ctx) {
    }

    @Override
    public void exitCandidateListChangeExp(CandidateListChangeExpContext ctx) {
    }

    @Override
    public void enterVoteEquivalents(VoteEquivalentsContext ctx) {
    }

    @Override
    public void exitVoteEquivalents(VoteEquivalentsContext ctx) {
    }

    @Override
    public void enterConcatenationExp(ConcatenationExpContext ctx) {
    }

    @Override
    public void exitConcatenationExp(ConcatenationExpContext ctx) {
    }

    @Override
    public void enterSplitExp(SplitExpContext ctx) {
    }

    @Override
    public void exitSplitExp(SplitExpContext ctx) {
    }

    @Override
    public void enterPermutationExp(PermutationExpContext ctx) {
    }

    @Override
    public void exitPermutationExp(PermutationExpContext ctx) {
    }

    @Override
    public void enterIntersectExp(IntersectExpContext ctx) {
    }

    @Override
    public void exitIntersectExp(IntersectExpContext ctx) {
    }

    @Override
    public void enterIntersectContent(IntersectContentContext ctx) {
    }

    @Override
    public void exitIntersectContent(IntersectContentContext ctx) {
    }

    @Override
    public void enterTuple(TupleContext ctx) {
    }

    @Override
    public void exitTuple(TupleContext ctx) {
    }

    @Override
    public void enterTupleContent(TupleContentContext ctx) {
    }

    @Override
    public void exitTupleContent(TupleContentContext ctx) {
    }

    // And here are the new/recent new ones.

    @Override
    public void enterNotEmptyExp(NotEmptyExpContext ctx) {
    }

    @Override
    public void exitNotEmptyExp(NotEmptyExpContext ctx) {
    }

    @Override
    public void enterNotEmptyContent(NotEmptyContentContext ctx) {
    }

    @Override
    public void exitNotEmptyContent(NotEmptyContentContext ctx) {
    }

    @Override
    public void enterAddedContentExp(AddedContentExpContext ctx) {
    }

    @Override
    public void exitAddedContentExp(AddedContentExpContext ctx) {
    }
}