/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpConstant;
import edu.pse.beast.datatypes.booleanExpAST.NumberExpression;
import edu.pse.beast.datatypes.booleanExpAST.EquivalencyNode;
import edu.pse.beast.datatypes.booleanExpAST.BinaryRelationshipNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanExpAST.ComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanExpAST.ConstantExp;
import edu.pse.beast.datatypes.booleanExpAST.ElectExp;
import edu.pse.beast.datatypes.booleanExpAST.ForAllNode;
import edu.pse.beast.datatypes.booleanExpAST.ImplicationNode;
import edu.pse.beast.datatypes.booleanExpAST.LogicalAndNode;
import edu.pse.beast.datatypes.booleanExpAST.LogicalOrNode;
import edu.pse.beast.datatypes.booleanExpAST.NotNode;
import edu.pse.beast.datatypes.booleanExpAST.QuantorNode;
import edu.pse.beast.datatypes.booleanExpAST.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanExpAST.ThereExistsNode;
import edu.pse.beast.datatypes.booleanExpAST.TypeExpression;
import edu.pse.beast.datatypes.booleanExpAST.VoteExp;
import edu.pse.beast.datatypes.booleanExpAST.VoteSumForCandExp;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionBaseListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpListContext;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Holger-Desktop
 */
public class FormalPropertySyntaxTreeToAstTranslator extends FormalPropertyDescriptionBaseListener {
    
    private BooleanExpListNode generated;
    private InternalTypeContainer inputType;
    private InternalTypeContainer resType;
    private Stack<BooleanExpressionNode> nodeStack;
    private Stack<TypeExpression> expStack;
    private int maxElectExp = 0;
    private int maxVoteExp = 0; 
    private int currentHighestElect = 0;
    private BooleanExpScopehandler scopeHandler;
    
    public BooleanExpListNode generateFromSyntaxTree(
            BooleanExpListContext parseTree, 
            InternalTypeContainer inputType,
            InternalTypeContainer resType,
            BooleanExpScope declaredVars) {
        scopeHandler = new BooleanExpScopehandler();
        scopeHandler.enterNewScope(declaredVars);
        this.inputType = inputType;
        this.resType = resType;
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, parseTree);
        return generated;
    }

    @Override
    public void enterBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx) {
        generated = new BooleanExpListNode();        
        maxElectExp = 0;
        maxVoteExp = 0;    
        expStack = new Stack<>();
        nodeStack = new Stack<>();
    }

    @Override
    public void exitBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx) {
        generated.setMaxVoteLevel(maxVoteExp);
    }

    @Override
    public void enterBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx) {
        currentHighestElect = 0;
    }

    @Override
    public void exitBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx) {
        generated.addNode(nodeStack.pop(), currentHighestElect);
        if(currentHighestElect > maxElectExp) {
            maxElectExp = currentHighestElect;
        }
    }

    @Override
    public void enterBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx) {
        
    }

    @Override
    public void exitBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx) {
        
    }

    @Override
    public void enterBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx) {
        System.out.println("enterbinary " + ctx.BinaryRelationSymbol().getText());
    }

    @Override
    public void exitBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx) {
        String symbol = ctx.BinaryRelationSymbol().getText();
        BooleanExpressionNode rhs = nodeStack.pop();
        BooleanExpressionNode lhs = nodeStack.pop();
        
        BinaryRelationshipNode node = null;
        
        if(symbol.equals("&&")) {
            node = new LogicalAndNode(lhs, rhs);
        } else if(symbol.equals("||")) {
            node = new LogicalOrNode(lhs, rhs);
        } else if(symbol.equals("==>")) {
            node = new ImplicationNode(lhs, rhs);
        } else if(symbol.equals("<==>")) {
            node = new EquivalencyNode(lhs, rhs);
        }
        System.out.println("binary " + symbol);
        nodeStack.add(node);
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
        String quantorType = ctx.Quantor().getText();
        
        QuantorNode node = null;
        
        if(quantorType.contains("FOR_ALL")) {
            node = new ForAllNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(), nodeStack.pop());
        } else if(quantorType.contains("EXISTS_ONE")) {
            node = new ThereExistsNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(), nodeStack.pop());
        }
        
        System.out.println("quantor " + quantorType);
        nodeStack.add(node);
        scopeHandler.exitScope();
    }

    @Override
    public void enterNotExp(FormalPropertyDescriptionParser.NotExpContext ctx) {
        
    }

    @Override
    public void exitNotExp(FormalPropertyDescriptionParser.NotExpContext ctx) {
        NotNode node = new NotNode(nodeStack.pop());
        nodeStack.add(node);
    }

    @Override
    public void enterComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx) {        
        System.out.println("enter comparison " + ctx.ComparisonSymbol().getText());
    }

    @Override
    public void exitComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx) {
        String comparisonSymbolString = ctx.ComparisonSymbol().getText();
        ComparisonSymbol comparisonSymbol = new ComparisonSymbol(comparisonSymbolString);
        TypeExpression rhs = expStack.pop();
        TypeExpression lhs = expStack.pop();
        ComparisonNode node = new ComparisonNode(rhs, lhs, comparisonSymbol);       
        nodeStack.add(node);
        
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
        int number = Integer.valueOf(ctx.getText());
        NumberExpression expNode = new NumberExpression(number);
        expStack.add(expNode);
    }

    @Override
    public void enterElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        InternalTypeContainer container = resType;
        
    }

    @Override
    public void exitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
         //get number
        String numberString = ctx.Elect().getText().substring("ELECT".length());
        int number = Integer.valueOf(numberString);
        if(currentHighestElect < number) currentHighestElect = number;
        
        SymbolicVariable[] accessingVars = new SymbolicVariable[ctx.passSymbVar().size()];
        
        for(int i = 0; i < accessingVars.length; ++i) {
            accessingVars[i] = ((SymbolicVarExp) expStack.pop()).getSymbolicVar();
        }
        
        ElectExp expNode = new ElectExp(resType, accessingVars, number);
        
        System.out.println("electnode " + numberString);
        expStack.add(expNode);
    }

    @Override
    public void enterVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
        
    }

    @Override
    public void exitVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
       String numberString = ctx.Vote().getText().substring("VOTES".length());
        int number = Integer.valueOf(numberString);
        if(maxVoteExp < number) maxVoteExp = number;
        
        
        SymbolicVariable[] accessingVars = new SymbolicVariable[ctx.passSymbVar().size()];
        
        for(int i = 0; i < accessingVars.length; ++i) {
            accessingVars[i] = ((SymbolicVarExp) expStack.pop()).getSymbolicVar();
        }
        
        VoteExp expNode = new VoteExp(inputType, accessingVars, number);
        
        System.out.println("votenode " + number);
        expStack.add(expNode);
    }

    @Override
    public void enterConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {

    }

    @Override
    public void exitConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {
        String constString = ctx.getText();
        ConstantExp expNode = null;
        if(constString.equals(BooleanExpConstant.getConstForVoterAmt())) {
            expNode = new ConstantExp(new InternalTypeContainer(InternalTypeRep.VOTER), constString);
        } else if(constString.equals(BooleanExpConstant.getConstForCandidateAmt())) {
            expNode = new ConstantExp(new InternalTypeContainer(InternalTypeRep.CANDIDATE), constString);
        } else if(constString.equals(BooleanExpConstant.getConstForSeatAmt())) {
            expNode = new ConstantExp(new InternalTypeContainer(InternalTypeRep.SEAT), constString);
        }
        expStack.push(expNode);
    }

    @Override
    public void enterVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
        
    }

    @Override
    public void exitVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
        VoteSumForCandExp expNode = new VoteSumForCandExp(
                new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                ((SymbolicVarExp) expStack.pop()).getSymbolicVar());
        expStack.add(expNode);
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
        SymbolicVarExp expNode = new SymbolicVarExp(type, new SymbolicVariable(name, type));
        expStack.add(expNode);
        System.out.println("symbvar " + name);
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
