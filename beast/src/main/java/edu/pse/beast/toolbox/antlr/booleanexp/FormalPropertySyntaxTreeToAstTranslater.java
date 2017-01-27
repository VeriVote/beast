/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox.antlr.booleanexp;

import edu.pse.beast.datatypes.boolexp.EquivalencyNode;
import edu.pse.beast.datatypes.boolexp.BinaryRelationshipNode;
import edu.pse.beast.datatypes.boolexp.BooleanExpListNode;
import edu.pse.beast.datatypes.boolexp.BooleanExpressionNode;
import edu.pse.beast.datatypes.boolexp.ComparisonNode;
import edu.pse.beast.datatypes.boolexp.ComparisonSymbol;
import edu.pse.beast.datatypes.boolexp.ElectExp;
import edu.pse.beast.datatypes.boolexp.ForAllNode;
import edu.pse.beast.datatypes.boolexp.ImplicationNode;
import edu.pse.beast.datatypes.boolexp.LogicalAndNode;
import edu.pse.beast.datatypes.boolexp.LogicalOrNode;
import edu.pse.beast.datatypes.boolexp.NotNode;
import edu.pse.beast.datatypes.boolexp.QuantorNode;
import edu.pse.beast.datatypes.boolexp.ThereExistsNode;
import edu.pse.beast.datatypes.boolexp.TypeExpression;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpListContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.beans.binding.BooleanExpression;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Holger-Desktop
 */
public class FormalPropertySyntaxTreeToAstTranslater extends FormalPropertyDescriptionBaseListener {
    
    private BooleanExpListNode generated;
    private InternalTypeContainer inputType;
    private InternalTypeContainer outputType;
    private Stack<BooleanExpressionNode> nodeStack;
    private Stack<TypeExpression> expStack;
    private int maxElectExp = 0;
    private int maxVoteExp = 0; 
    private int currentHighestElect = 0;
    private Stack<SymbolicVariable> symbVarStack;
    private ArrayList<InternalTypeContainer> symbVarTypeList;
    
    public BooleanExpListNode generateFromSyntaxTree(
            BooleanExpListContext parseTree,
            InternalTypeContainer inputType, 
            InternalTypeContainer outputType) {
        this.inputType = inputType;
        this.outputType = outputType;
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
        symbVarStack = new Stack<>();
        symbVarTypeList = new ArrayList<>();
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
        
        nodeStack.add(node);
    }

    @Override
    public void enterQuantorExp(FormalPropertyDescriptionParser.QuantorExpContext ctx) {
        
    }

    @Override
    public void exitQuantorExp(FormalPropertyDescriptionParser.QuantorExpContext ctx) {
        String quantorType = ctx.Quantor().getText();
        String varName = ctx.passSymbVar().symbolicVarExp().Identifier().getText();
        QuantorNode node = null;
        SymbolicVariable symbVar = null;
        
        if(quantorType.contains("VOTER")) {
            symbVar = new SymbolicVariable(varName, new InternalTypeContainer(InternalTypeRep.VOTER));
        } else if(quantorType.contains("CANDIDATE")) {
            symbVar = new SymbolicVariable(varName, new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        } else if(quantorType.contains("SEAT")) {
            symbVar = new SymbolicVariable(varName, new InternalTypeContainer(InternalTypeRep.SEAT));
        }
        
        if(quantorType.contains("FOR_ALL")) {
            node = new ForAllNode(symbVar, nodeStack.pop());
        } else if(quantorType.contains("EXISTS_ONE")){
            node = new ThereExistsNode(symbVar, nodeStack.pop());
        }
        
        nodeStack.add(node);
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
        
    }

    @Override
    public void enterElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        System.out.println("enter elect");
        InternalTypeContainer container = outputType;
        while(container.isList()) {
            symbVarTypeList.add(container.getListedType());
            container = container.getListedType();
        }
        symbVarTypeList.add(new InternalTypeContainer(container.getInternalType()));
    }

    @Override
    public void exitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        System.out.println("exit elect");
        //get number
        String numberString = ctx.Elect().getText().substring("ELECT".length());
        int number = Integer.valueOf(numberString);
        if(currentHighestElect < number) currentHighestElect = number;
        ArrayList<SymbolicVariable> accessingVars = new ArrayList<>();
        while(!symbVarStack.empty()) accessingVars.add(symbVarStack.pop());
        ElectExp expNode = new ElectExp(outputType, (SymbolicVariable[]) accessingVars.toArray(), number);
        expStack.add(expNode);
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
        InternalTypeContainer type = symbVarTypeList.get(0);
        String name = ctx.symbolicVarExp().getText();
        symbVarStack.add(new SymbolicVariable(name, inputType));
    }

    @Override
    public void enterSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {

    }

    @Override
    public void exitSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {

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
