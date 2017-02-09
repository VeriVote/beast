/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.booleanExpAST.ConstantExp;
import edu.pse.beast.datatypes.booleanExpAST.ElectExp;
import edu.pse.beast.datatypes.booleanExpAST.NumberExpression;
import edu.pse.beast.datatypes.booleanExpAST.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanExpAST.TypeExpression;
import edu.pse.beast.datatypes.booleanExpAST.VoteSumForCandExp;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Holger-Desktop
 */
public class FormalExpErrorFinderTreeListener implements FormalPropertyDescriptionListener,
        VariableListListener, ElectionDescriptionChangeListener {
    private ArrayList<CodeError> created = new ArrayList<>();
    private BooleanExpScopehandler scopeHandler = new BooleanExpScopehandler();
    private ElectionTypeContainer input;
    private ElectionTypeContainer output;
    private Stack<TypeExpression> expStack;
    
    public FormalExpErrorFinderTreeListener(SymbolicVariableList list, CElectionDescriptionEditor ceditor) {
        list.addListener(this);
        scopeHandler.enterNewScope();
        for(SymbolicVariable var : list.getSymbolicVariables()) {
            scopeHandler.addVariable(var.getId(), var.getInternalTypeContainer());
        }
        this.input = ceditor.getElectionDescription().getInputType();
        this.output = ceditor.getElectionDescription().getOutputType();
        ceditor.addListener(this);
    }
    
    public void setInput(ElectionTypeContainer input) {
        this.input = input;
    }

    public void setOutput(ElectionTypeContainer output) {
        this.output = output;
    }

    
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
        if(quantorTypeString.contains("VOTER")) {
            varType = new InternalTypeContainer(InternalTypeRep.VOTER);
        } else if(quantorTypeString.contains("CANDIDATE")) {
            varType = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        } else if(quantorTypeString.contains("SEAT")) {
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
        TypeExpression rhs = expStack.pop();
        TypeExpression lhs = expStack.pop();
        InternalTypeContainer lhsCont = lhs.getInternalTypeContainer();
        InternalTypeContainer rhsCont = rhs.getInternalTypeContainer();
        if(lhsCont.getListLvl() != rhsCont.getListLvl()) {
            created.add(BooleanExpErrorFactory.createCantCompareDifferentListLevels(ctx, lhsCont, rhsCont));
        } else {
            while(lhsCont.isList()) {
                lhsCont = lhsCont.getListedType();
                rhsCont = rhsCont.getListedType();
            }
            if(lhsCont.getInternalType() != rhsCont.getInternalType()) {
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
        expStack.add(new NumberExpression(Integer.valueOf(ctx.getText())));
    }

    @Override
    public void enterElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        testIfTooManyVarsPassed(ctx.passSymbVar(), output.getType());
    }

    @Override
    public void exitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        testIfWrongTypePassed(ctx.passSymbVar(), output.getType());
        InternalTypeContainer cont = output.getType();
        for(int i = 0; i < ctx.passSymbVar().size() && cont.isList(); ++i) {
            cont = cont.getListedType();
        }
        expStack.add(new ElectExp(cont, null, 0));
    }

    @Override
    public void enterVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
        testIfTooManyVarsPassed(ctx.passSymbVar(), input.getType());
    }    
    

    @Override
    public void exitVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
        testIfWrongTypePassed(ctx.passSymbVar(), input.getType());
        InternalTypeContainer cont = input.getType();
        for(int i = 0; i < ctx.passSymbVar().size() && cont.isList(); ++i) {
            cont = cont.getListedType();
        }
        expStack.add(new ElectExp(cont, null, 0));
    }
    
    private void testIfTooManyVarsPassed(List<FormalPropertyDescriptionParser.PassSymbVarContext> ctx, InternalTypeContainer cont) {
        int amountPassedVariables = ctx.size();
        int listDepth = 0;
        for(; cont.isList(); cont = cont.getListedType()) {
            listDepth++;
        }
        for(; listDepth < amountPassedVariables; ++listDepth) {
            created.add(BooleanExpErrorFactory.createTooManyVarsPassedError(ctx.get(listDepth)));
        }
    }
    
    private void testIfWrongTypePassed(List<FormalPropertyDescriptionParser.PassSymbVarContext> ctx, InternalTypeContainer cont) {
        int amtPassed = ctx.size();
        Stack<SymbolicVarExp> passedSymbVars = new Stack<>();
        for(int i = 0; i < amtPassed; ++i) {
            passedSymbVars.add((SymbolicVarExp) expStack.pop());
        }
        int i = 0;
        for(; cont.isList() && i < ctx.size(); cont = cont.getListedType()) {
            SymbolicVarExp currentVarExp = passedSymbVars.pop();
            if(cont.getAccesTypeIfList() != currentVarExp.getSymbolicVar().getInternalTypeContainer().getInternalType()) {
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
        expStack.add(new ConstantExp(getContainerForConstant(ctx), ctx.getText()));
    }
    
    private InternalTypeContainer getContainerForConstant(FormalPropertyDescriptionParser.ConstantExpContext ctx) {
        InternalTypeRep rep = InternalTypeRep.NULL;
        if(ctx.getText() == "V") {
            rep = InternalTypeRep.VOTER;
        } else if(ctx.getText() == "C") {
            rep = InternalTypeRep.CANDIDATE;
        } else if(ctx.getText() == "S") {
            rep = InternalTypeRep.SEAT;
        }
        return new InternalTypeContainer(rep);
    }

    @Override
    public void enterVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
        
    }

    @Override
    public void exitVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
        SymbolicVarExp passedVar = (SymbolicVarExp) expStack.pop();
        if(passedVar.getSymbolicVar().getInternalTypeContainer().getInternalType() != InternalTypeRep.CANDIDATE) {
            created.add(BooleanExpErrorFactory.createWrongVarToVotesumError(ctx, passedVar.getSymbolicVar()));
        }
        String numberString = ctx.Votesum().getText().substring("VOTE_SUM_FOR_CANDIDATE".length());
        int number = Integer.valueOf(numberString);
        expStack.add(new VoteSumForCandExp(number, passedVar.getSymbolicVar()));
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
        SymbolicVarExp expNode;
        if(type == null) {
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
