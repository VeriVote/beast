/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.*;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.*;
import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.datatypes.propertydescription.VariableListListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScopehandler;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Holger-Desktop
 */
public class FormalExpErrorFinderTreeListener
        implements FormalPropertyDescriptionListener, VariableListListener, ElectionDescriptionChangeListener {

    private final ArrayList<CodeError> created = new ArrayList<>();
    private final BooleanExpScopehandler scopeHandler = new BooleanExpScopehandler();
    private ElectionTypeContainer input;
    private ElectionTypeContainer output;
    private Stack<TypeExpression> expStack;

    /**
     * constructor to create the error finder in the tree list
     * 
     * @param list
     *            the list with the symbolic variables
     * @param ceditor
     *            the editor where the code is
     */
    public FormalExpErrorFinderTreeListener(SymbolicVariableList list, CElectionDescriptionEditor ceditor) {
        list.addListener(this);
        scopeHandler.enterNewScope();
        for (SymbolicVariable var : list.getSymbolicVariables()) {
            scopeHandler.addVariable(var.getId(), var.getInternalTypeContainer());
        }
        this.input = ceditor.getElectionDescription().getInputType();
        this.output = ceditor.getElectionDescription().getOutputType();
        ceditor.addListener(this);
    }

    /**
     * sets up the input for the error finder
     * 
     * @param input
     *            the election type container
     */
    public void setInput(ElectionTypeContainer input) {
        this.input = input;
    }

    /**
     * sets up the output for the error finder
     * 
     * @param output
     *            the election type container
     */
    public void setOutput(ElectionTypeContainer output) {
        this.output = output;
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
        String s = ctx.getText();
        TypeExpression rhs = expStack.pop();
        TypeExpression lhs = expStack.pop();
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
        testIfTooManyVarsPassed(ctx.passType(), output.getType());
    }

    @Override
    public void exitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        testIfWrongTypePassed(ctx.passType(), output.getType());
        InternalTypeContainer cont = output.getType();
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
        testIfTooManyVarsPassed(ctx.passType(), input.getType());
    }

    @Override
    public void exitVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
        testIfWrongTypePassed(ctx.passType(), input.getType());
        InternalTypeContainer cont = input.getType();
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

    @Override
    public void exitVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
        TypeExpression passedVar = expStack.pop();
        if (passedVar.getInternalTypeContainer().getInternalType() != InternalTypeRep.CANDIDATE) {
            created.add(BooleanExpErrorFactory.createWrongVarToVotesumError(ctx, passedVar.getInternalTypeContainer()));
        }
        String numberString = ctx.Votesum().getText().substring("VOTE_SUM_FOR_CANDIDATE".length());
        int number = Integer.valueOf(numberString);
        if (number == 0)
            created.add(BooleanExpErrorFactory.createNumberMustBeGreaterZeroVotesum(ctx));
        expStack.add(new VoteSumForCandExp(number, passedVar));
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
    public void inputChanged(ElectionTypeContainer input) {
        this.input = input;
    }

    @Override
    public void outputChanged(ElectionTypeContainer output) {
        this.output = output;
    }

}
