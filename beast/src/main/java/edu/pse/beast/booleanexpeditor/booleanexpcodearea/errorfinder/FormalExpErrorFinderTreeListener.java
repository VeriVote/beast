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
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.AddedContentExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.CandidateListChangeExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SplitExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;
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
 * @author Holger-Desktop
 */
public class FormalExpErrorFinderTreeListener
        implements FormalPropertyDescriptionListener, VariableListListener, ElectionDescriptionChangeListener {
    private final ArrayList<CodeError> created = new ArrayList<>();
    private final BooleanExpScopehandler scopeHandler = new BooleanExpScopehandler();
    private ElectionTypeContainer container;
    private Stack<TypeExpression> expStack;
    private final ElectionDescription elecDescription;

    /**
     * constructor to create the error finder in the tree list
     *
     * @param list    the list with the symbolic variables
     * @param ceditor the editor where the code is
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
     * sets up the input for the error finder
     *
     * @param input the election type container
     */
    public void setInput(InputType inputType) {
        container.setInput(inputType);
    }

    /**
     * sets up the output for the error finder
     *
     * @param output the election type container
     */
    public void setOutput(OutputType outputType) {
        container.setOutput(outputType);
    }

    /**
     * gives all code errors found
     *
     * @return a list of all found errors
     */
    public ArrayList<CodeError> getErrors() {
        return created;
    }

    @Override
    public void enterBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx) {
        expStack = new Stack<>();
        created.clear();
    }

    @Override
    public void exitBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx) {
    }

    @Override
    public void enterBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx) {
    }

    @Override
    public void exitBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx) {
    }

    @Override
    public void enterBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx) {
    }

    @Override
    public void exitBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx) {
    }

    @Override
    public void enterBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx) {
    }

    @Override
    public void exitBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx) {
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
        scopeHandler.exitScope();
    }

    @Override
    public void enterNotExp(FormalPropertyDescriptionParser.NotExpContext ctx) {
    }

    @Override
    public void exitNotExp(FormalPropertyDescriptionParser.NotExpContext ctx) {
    }

    @Override
    public void enterComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx) {
    }

    @Override
    public void exitComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx) {
        TypeExpression lhs;
        TypeExpression rhs;

        if (ctx.typeExp(1).getChild(0) instanceof IntersectExpContext) { // the right arguments
            rhs = new IntersectTypeExpNode(elecDescription.getContainer().getOutputType(),
                    (IntersectExpContext) ctx.typeExp(1).getChild(0));
        } else {
            rhs = expStack.pop();
        }

        if (ctx.typeExp(0).getChild(0) instanceof IntersectExpContext) { // the left argument
            lhs = new IntersectTypeExpNode(elecDescription.getContainer().getOutputType(),
                    (IntersectExpContext) ctx.typeExp(0).getChild(0));
        } else {
            lhs = expStack.pop();
        }

        InternalTypeContainer lhsCont = lhs.getInternalTypeContainer();
        InternalTypeContainer rhsCont = rhs.getInternalTypeContainer();
        if (lhsCont.getListLvl() != rhsCont.getListLvl()) {
            created.add(BooleanExpErrorFactory.createCantCompareDifferentListLevels(ctx, lhsCont, rhsCont));
        } else {
            while (lhsCont.isList()) {
                lhsCont = lhsCont.getListedType();
                rhsCont = rhsCont.getListedType();
            }
            if (lhsCont.getInternalType() != rhsCont.getInternalType()) {
                created.add(BooleanExpErrorFactory.createCantCompareTypes(ctx, lhsCont, rhsCont));
            }
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
    public void enterTypeByPosExp(FormalPropertyDescriptionParser.TypeByPosExpContext ctx) {
    }

    @Override
    public void exitTypeByPosExp(FormalPropertyDescriptionParser.TypeByPosExpContext ctx) {
    }

    @Override
    public void enterVoterByPosExp(FormalPropertyDescriptionParser.VoterByPosExpContext ctx) {
    }

    @Override
    public void exitVoterByPosExp(FormalPropertyDescriptionParser.VoterByPosExpContext ctx) {
        expStack.push(new AtPosExp(new InternalTypeContainer(InternalTypeRep.VOTER),
                (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void enterCandByPosExp(FormalPropertyDescriptionParser.CandByPosExpContext ctx) {
    }

    @Override
    public void exitCandByPosExp(FormalPropertyDescriptionParser.CandByPosExpContext ctx) {
        expStack.push(new AtPosExp(new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void enterSeatByPosExp(FormalPropertyDescriptionParser.SeatByPosExpContext ctx) {
    }

    @Override
    public void exitSeatByPosExp(FormalPropertyDescriptionParser.SeatByPosExpContext ctx) {
        expStack.push(new AtPosExp(new InternalTypeContainer(InternalTypeRep.SEAT),
                (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void enterInteger(FormalPropertyDescriptionParser.IntegerContext ctx) {
    }

    @Override
    public void exitInteger(FormalPropertyDescriptionParser.IntegerContext ctx) {
        String integerString = ctx.getText();
        int heldInteger = Integer.valueOf(integerString);
        IntegerNode integerNode = new IntegerNode(heldInteger);
        expStack.push(integerNode);
    }

    @Override
    public void enterElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        testIfTooManyVarsPassed(ctx.passType(), container.getOutputType().getInternalTypeContainer());
    }

    @Override
    public void exitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        testIfWrongTypePassed(ctx.passType(), container.getOutputType().getInternalTypeContainer());
        InternalTypeContainer cont = container.getOutputType().getInternalTypeContainer();
        for (int i = 0; i < ctx.passType().size() && cont.isList(); ++i) {
            cont = cont.getListedType();
        }
        String numberString = ctx.Elect().getText().substring("ELECT".length());
        int number = Integer.valueOf(numberString);
        if (number == 0)
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroElect(ctx));
        expStack.add(new ElectExp(cont, null, 0));
    }

    @Override
    public void enterVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
        testIfTooManyVarsPassed(ctx.passType(), container.getInputType().getInternalTypeContainer());
    }

    @Override
    public void exitVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
        testIfWrongTypePassed(ctx.passType(), container.getInputType().getInternalTypeContainer());
        InternalTypeContainer cont = container.getInputType().getInternalTypeContainer();
        for (int i = 0; i < ctx.passType().size() && cont.isList(); ++i) {
            cont = cont.getListedType();
        }
        String numberString = ctx.Vote().getText().substring("VOTES".length());
        int number = Integer.valueOf(numberString);
        if (number == 0)
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroVotes(ctx));
        expStack.add(new ElectExp(cont, null, 0));
    }

    @Override
    public void enterPassType(FormalPropertyDescriptionParser.PassTypeContext ctx) {
    }

    @Override
    public void exitPassType(FormalPropertyDescriptionParser.PassTypeContext ctx) {
    }

    private void testIfTooManyVarsPassed(List<FormalPropertyDescriptionParser.PassTypeContext> ctx,
            InternalTypeContainer cont) {
        int amountPassedVariables = ctx.size();
        int listDepth = 0;
        for (; cont.isList(); cont = cont.getListedType()) {
            listDepth++;
        }
        for (; listDepth < amountPassedVariables; ++listDepth) {
            created.add(BooleanExpErrorFactory.createTooManyVarsPassedError(ctx.get(listDepth)));
        }
    }

    private void testIfWrongTypePassed(List<FormalPropertyDescriptionParser.PassTypeContext> ctx,
            InternalTypeContainer cont) {
        int amtPassed = ctx.size();
        Stack<TypeExpression> passedTypes = new Stack<>();
        for (int i = 0; i < amtPassed; ++i) {
            passedTypes.add(expStack.pop());
        }
        int i = 0;
        for (; cont.isList() && i < ctx.size(); cont = cont.getListedType()) {
            TypeExpression currentVarExp = passedTypes.pop();
            if (cont.getAccesTypeIfList() != currentVarExp.getInternalTypeContainer().getInternalType()) {
                created.add(BooleanExpErrorFactory.createWrongVarTypePassed(cont, ctx.get(i), currentVarExp));
            }
            ++i;
        }
    }

    @Override
    public void enterConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {
    }

    @Override
    public void exitConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {
        expStack.add(new ConstantExp(ctx.getText()));
    }

    @Override
    public void enterVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
    }

    private void exitVoteSumExp(ParserRuleContext ctx, boolean unique) {
        final Class<FormalPropertyDescriptionParser.VoteSumUniqueExpContext> cu = FormalPropertyDescriptionParser.VoteSumUniqueExpContext.class;
        final Class<FormalPropertyDescriptionParser.VoteSumExpContext> c = FormalPropertyDescriptionParser.VoteSumExpContext.class;

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

        int number = Integer.valueOf(numberString);
        if (number == 0)
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroVotesum(ctx));
        expStack.add(new VoteSumForCandExp(number, passedVar, unique));
    }

    @Override
    public void exitVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
        exitVoteSumExp(ctx, false);
    }

    @Override
    public void enterVoteSumUniqueExp(FormalPropertyDescriptionParser.VoteSumUniqueExpContext ctx) {
    }

    @Override
    public void exitVoteSumUniqueExp(FormalPropertyDescriptionParser.VoteSumUniqueExpContext ctx) {
        exitVoteSumExp(ctx, true);
    }

    @Override
    public void enterPassSymbVar(FormalPropertyDescriptionParser.PassSymbVarContext ctx) {
    }

    @Override
    public void exitPassSymbVar(FormalPropertyDescriptionParser.PassSymbVarContext ctx) {
    }

    @Override
    public void enterPassPosition(FormalPropertyDescriptionParser.PassPositionContext ctx) {
    }

    @Override
    public void exitPassPosition(FormalPropertyDescriptionParser.PassPositionContext ctx) {
    }

    @Override
    public void enterPassByPos(FormalPropertyDescriptionParser.PassByPosContext ctx) {
    }

    @Override
    public void exitPassByPos(FormalPropertyDescriptionParser.PassByPosContext ctx) {
    }

    @Override
    public void enterSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {
    }

    @Override
    public void exitSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {
        String name = ctx.getText();
        InternalTypeContainer type = scopeHandler.getTypeForVariable(name);
        SymbolicVarExp expNode;
        if (type == null) {
            created.add(BooleanExpErrorFactory.createVarNotDeclaredErr(ctx));
            type = new InternalTypeContainer(InternalTypeRep.NULL);
            expNode = new SymbolicVarExp(type, new SymbolicVariable(name, type));
        } else {
            expNode = new SymbolicVarExp(type, new SymbolicVariable(name, type));
        }
        expStack.add(expNode);
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

    // hier sind die neuen befehle:
    @Override
    public void enterVotingListChangeExp(VotingListChangeExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitVotingListChangeExp(VotingListChangeExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterVotingListChangeContent(VotingListChangeContentContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitVotingListChangeContent(VotingListChangeContentContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterVotingTupelChangeExp(VotingTupelChangeExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitVotingTupelChangeExp(VotingTupelChangeExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterCandidateListChangeExp(CandidateListChangeExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitCandidateListChangeExp(CandidateListChangeExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterVoteEquivalents(VoteEquivalentsContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitVoteEquivalents(VoteEquivalentsContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterConcatenationExp(ConcatenationExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitConcatenationExp(ConcatenationExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterSplitExp(SplitExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitSplitExp(SplitExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterPermutationExp(PermutationExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitPermutationExp(PermutationExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterIntersectExp(IntersectExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitIntersectExp(IntersectExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterIntersectContent(IntersectContentContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitIntersectContent(IntersectContentContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterTuple(TupleContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitTuple(TupleContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterTupleContent(TupleContentContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitTupleContent(TupleContentContext ctx) {
        // TODO Auto-generated method stub
    }

    // und hier die neuen neuen

    @Override
    public void enterNotEmptyExp(NotEmptyExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitNotEmptyExp(NotEmptyExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterNotEmptyContent(NotEmptyContentContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitNotEmptyContent(NotEmptyContentContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterAddedContentExp(AddedContentExpContext ctx) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exitAddedContentExp(AddedContentExpContext ctx) {
        // TODO Auto-generated method stub
    }
}