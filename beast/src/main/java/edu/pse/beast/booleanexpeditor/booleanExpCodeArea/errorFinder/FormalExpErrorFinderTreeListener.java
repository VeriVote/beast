/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanExpAST.ForAllNode;
import edu.pse.beast.datatypes.booleanExpAST.QuantorNode;
import edu.pse.beast.datatypes.booleanExpAST.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanExpAST.ThereExistsNode;
import edu.pse.beast.datatypes.booleanExpAST.TypeExpression;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScopehandler;
import java.util.ArrayList;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Holger-Desktop
 */
public class FormalExpErrorFinderTreeListener implements FormalPropertyDescriptionListener {
    private ArrayList<CodeError> created = new ArrayList<>();
    private BooleanExpScopehandler scopeHandler = new BooleanExpScopehandler();
    private ElectionTypeContainer input;
    private ElectionTypeContainer output;
    private Stack<TypeExpression> expStack;
    
    public void setUp(
            BooleanExpScopehandler scopeHandler,
            ElectionTypeContainer input,
            ElectionTypeContainer output) {
        this.scopeHandler = scopeHandler;
        this.input = input;
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
        
    }

    @Override
    public void enterElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        
    }

    @Override
    public void exitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        
    }

    @Override
    public void enterVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
        
    }

    @Override
    public void exitVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
        
    }

    @Override
    public void enterConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {
        
    }

    @Override
    public void exitConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {
        
    }

    @Override
    public void enterVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
        
    }

    @Override
    public void exitVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
        
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
        
        if(type == null) {
            created.add(BooleanExpErrorFactory.createVarNotDeclaredErr(ctx));
        }
        
        SymbolicVarExp expNode = new SymbolicVarExp(type, new SymbolicVariable(name, type));
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
    
}
