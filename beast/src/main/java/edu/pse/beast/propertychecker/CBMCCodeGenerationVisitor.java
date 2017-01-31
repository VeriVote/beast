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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitOrNode(LogicalOrNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitImplicationNode(ImplicationNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitAquivalencyNode(EquivalencyNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitForAllNode(ForAllNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitThereExistsNode(ThereExistsNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitNotNode(NotNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitComparisonNode(ComparisonNode node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitSymbVarExp(SymbolicVarExp exp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitConstExp(ConstantExp exp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
