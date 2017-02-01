/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanExpAST.ComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.ConstantExp;
import edu.pse.beast.datatypes.booleanExpAST.ElectExp;
import edu.pse.beast.datatypes.booleanExpAST.EquivalencyNode;
import edu.pse.beast.datatypes.booleanExpAST.ForAllNode;
import edu.pse.beast.datatypes.booleanExpAST.ImplicationNode;
import edu.pse.beast.datatypes.booleanExpAST.LogicalAndNode;
import edu.pse.beast.datatypes.booleanExpAST.LogicalOrNode;
import edu.pse.beast.datatypes.booleanExpAST.NotNode;
import edu.pse.beast.datatypes.booleanExpAST.NumberExpression;
import edu.pse.beast.datatypes.booleanExpAST.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanExpAST.ThereExistsNode;
import edu.pse.beast.datatypes.booleanExpAST.TypeExpression;
import edu.pse.beast.datatypes.booleanExpAST.VoteExp;
import edu.pse.beast.datatypes.booleanExpAST.VoteSumForCandExp;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Niels
 */
public class CBMCCodeGenerationVisitor implements BooleanExpNodeVisitor {

    private String assumeOrAssert;
    private int andNodeCounter;
    private int orNodeCounter;
    private int implicationNodeCounter;
    private int aquivalencyNodeCounter;
    private int forAllNodeCounter;
    private int thereExistsNodeCounter;
    private int notNodeCounter;
    private int comparisonNodeCounter;
    private int symbVarExpressionCounter;
    private int constExpCounter;
    private int electExpCounter;
    private int voteExpCounter;
    private int voteSumExpressionCounter;
    private int loopVariable;
    private Stack<String> variableNames;
    private CodeArrayListBeautifier code;

    public CBMCCodeGenerationVisitor() {
        andNodeCounter = 0;
        orNodeCounter = 0;
        implicationNodeCounter = 0;
        aquivalencyNodeCounter = 0;
        forAllNodeCounter = 0;
        thereExistsNodeCounter = 0;
        notNodeCounter = 0;
        comparisonNodeCounter = 0;
        symbVarExpressionCounter = 0;
        constExpCounter = 0;
        electExpCounter = 0;
        voteExpCounter = 0;
        voteSumExpressionCounter = 0;
        loopVariable = 0;
        code = new CodeArrayListBeautifier();
    }

    public void setToPrePropertyMode() {
        assumeOrAssert = "assume";
    }

    public void setToPostPropertyMode() {
        assumeOrAssert = "assert";

    }

    public ArrayList<String> generateCode(BooleanExpressionNode node) {
        code = new CodeArrayListBeautifier();
        variableNames = new Stack<>();

        node.getVisited(this);

        return code.getCodeArrayList();
    }

    @Override
    public void visitAndNode(LogicalAndNode node) {

        String varName = "and_" + andNodeCounter;
        andNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        code.add("unsigned int " + varName + " = ((" + variableNames.pop() + ") && (" + variableNames.pop() + "));");
        testIfLast();

    }

    @Override
    public void visitOrNode(LogicalOrNode node) {
        String varName = "or_" + orNodeCounter;
        orNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        code.add("unsigned int " + varName + " = ((" + variableNames.pop() + ") || (" + variableNames.pop() + "));");
        testIfLast();
    }

    @Override
    public void visitImplicationNode(ImplicationNode node) {
        String varName = "implication_" + implicationNodeCounter;
        implicationNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        code.add("unsigned int " + varName + " = (!(" + variableNames.pop() + ") || (" + variableNames.pop() + "));");
        testIfLast();
    }

    @Override
    public void visitAquivalencyNode(EquivalencyNode node) {
        String varName = "aquivalency_" + aquivalencyNodeCounter;
        aquivalencyNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        String lhs = variableNames.pop();
        String rhs = variableNames.pop();
        code.add("unsigned int " + varName + " = (((" + lhs + ") && (" + rhs + ")) || (!(" + lhs + ") && (!" + rhs + ")));");
        testIfLast();
    }

    @Override
    public void visitForAllNode(ForAllNode node) {
        String varName = "forAll_" + forAllNodeCounter;
        forAllNodeCounter++;
        variableNames.push(varName);

        node.getDeclaredSymbolicVar().getInternalTypeContainer();
        node.getFollowingExpNode();
    }

    @Override
    public void visitThereExistsNode(ThereExistsNode node) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitNotNode(NotNode node) {
        String varName = "not_" + notNodeCounter;
        notNodeCounter++;
        variableNames.push(varName);
        node.getNegatedExpNode().getVisited(this);
        code.add("unsigned int " + varName + " = !(" + variableNames.pop() + ");");
        testIfLast();
    }

    @Override
    public void visitComparisonNode(ComparisonNode node) {
        // error here
        String varName = "comparison_" + comparisonNodeCounter;
        comparisonNodeCounter++;
        variableNames.push(varName);
        TypeExpression lhs = node.getLHSBooleanExpNode();
        TypeExpression rhs = node.getRHSBooleanExpNode();
        if (lhs instanceof ConstantExp || lhs instanceof NumberExpression) { //BooleanExpConst ???
            lhs.getVisited(this);
        }
        
        else if (lhs instanceof ElectExp){
            
        }
            
        
            
            if (rhs instanceof ConstantExp || rhs instanceof NumberExpression) { //BooleanExpConst ???
            rhs.getVisited(this);
        }

        code.add("unsigned int " + varName + " = ((" + variableNames.pop() + ") "
                + node.getComparisonSymbol().getCStringRep() + " (" + variableNames.pop() + "));");
        testIfLast();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitSymbVarExp(SymbolicVarExp exp) {
        variableNames.push(exp.getSymbolicVar().getId());
    }

    @Override
    public void visitConstExp(ConstantExp exp) {
        variableNames.push(exp.getConstant());
    }

    @Override
    public void visitElectExp(ElectExp exp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitVoteExp(VoteExp exp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitVoteSumExp(VoteSumForCandExp exp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitNumberExpNode(NumberExpression exp) {
        variableNames.push(String.valueOf(exp.getNumber()));
    }

    private void testIfLast() {
        if (variableNames.size() == 1) {
            code.add(assumeOrAssert + "(" + variableNames.pop() + ");");
        }
    }
}
