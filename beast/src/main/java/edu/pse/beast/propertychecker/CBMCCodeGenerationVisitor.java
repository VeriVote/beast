/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

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
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This is the visitor for the codeGeneration For every assert or assume it
 * should be called to visit the top node 1 time it will move down and generate
 * the code. to get the generated code, you have to use the method generateCode
 * You also have to set it either to Pre oder PostPropertyMode.
 *
 * @author Niels
 */
public class CBMCCodeGenerationVisitor implements BooleanExpNodeVisitor {

    private String assumeOrAssert; // this String must always be "assume" or "assert". If it is not set it is null


    /* the following attributes are the counters for the variables in the code
    they make the variable names unique
     */
    private int andNodeCounter;
    private int orNodeCounter;
    private int implicationNodeCounter;
    private int aquivalencyNodeCounter;
    private int forAllNodeCounter;
    private int thereExistsNodeCounter;
    private int notNodeCounter;
    private int comparisonNodeCounter;

    private Stack<String> variableNames; //stack of the variable names. 
    private CodeArrayListBeautifier code; // object, that handels the generated code

    /**
     * this creates the visitor You should create 1 and only 1 visitor for every
     * c. file you want to make you have to set it to pre- or post-property mode
     * in order for it to function
     */
    public CBMCCodeGenerationVisitor() {
        andNodeCounter = 0;
        orNodeCounter = 0;
        implicationNodeCounter = 0;
        aquivalencyNodeCounter = 0;
        forAllNodeCounter = 0;
        thereExistsNodeCounter = 0;
        notNodeCounter = 0;
        comparisonNodeCounter = 0;

        code = new CodeArrayListBeautifier();

    }

    /**
     * sets the visitor to prePropertyMode. That means the top node will be
     * assumed in the code
     */
    public void setToPrePropertyMode() {
        assumeOrAssert = "assume";
    }

    /**
     * sets the visitor to postPropertyMode. That means the top node will be
     * asserted in the code
     */
    public void setToPostPropertyMode() {
        assumeOrAssert = "assert";

    }

    /**
     * this generates the c-code for a propertydescription-statement
     *
     * @param node this should be the top node of a statement
     * @return the generated code
     */
    public ArrayList<String> generateCode(BooleanExpressionNode node) {
        code = new CodeArrayListBeautifier();
        variableNames = new Stack<>();

        node.getVisited(this);

        return code.getCodeArrayList();
    }

    /**
     * generates the code for an logical and node
     *
     * @param node
     */
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

    /**
     * generates the code for logical an or node
     *
     * @param node
     */
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

    /**
     * generates the code for the Implication Node
     *
     * @param node
     */
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

    /**
     * generates the code for an EquivalencyNode
     *
     * @param node
     */
    @Override
    public void visitAquivalencyNode(EquivalencyNode node) {
        String varName = "aquivalency_" + aquivalencyNodeCounter;
        aquivalencyNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        String lhs = variableNames.pop();
        String rhs = variableNames.pop();
        code.add("unsigned int " + varName + " = (((" + lhs + ") && (" + rhs
                + ")) || (!(" + lhs + ") && (!" + rhs + ")));");
        testIfLast();
    }

    /**
     * generates the code for an ForAllNode
     *
     * @param node
     */
    @Override
    public void visitForAllNode(ForAllNode node) {
        String varName = "forAll_" + forAllNodeCounter;
        forAllNodeCounter++;
        variableNames.push(varName);
        code.add("unsigned int " + varName + " = 1;");
        String max = "";
        switch (node.getDeclaredSymbolicVar().getInternalTypeContainer().getInternalType()) {
            case VOTER:
                max = "V";
                break;
            case CANDIDATE:
                max = "C";
                break;
            case SEAT:
                max = "S";
                break;
            default:
                throw new AssertionError(node.getDeclaredSymbolicVar().getInternalTypeContainer()
                        .getInternalType().name());
        }
        String tempString = "for(unsigned int SYMBVAR = 0; SYMBVAR < MAX && FORALL; SYMBVAR++) {";
        tempString = tempString.replaceAll("SYMBVAR", node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replace("MAX", max);
        tempString = tempString.replace("FORALL", varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varName + " = " + variableNames.pop() + ";");
        code.deleteTab();
        code.add("}");
        testIfLast();
    }

    /**
     * generates the code to an ThereExistsNode
     *
     * @param node
     */
    @Override
    public void visitThereExistsNode(ThereExistsNode node) {
        String varName = "thereExists_" + thereExistsNodeCounter;
        thereExistsNodeCounter++;
        variableNames.push(varName);
        code.add("unsigned int " + varName + " = 0;");
        String max = "";
        switch (node.getDeclaredSymbolicVar().getInternalTypeContainer().getInternalType()) {
            case VOTER:
                max = "V";
                break;
            case CANDIDATE:
                max = "C";
                break;
            case SEAT:
                max = "S";
                break;
            default:
                throw new AssertionError(node.getDeclaredSymbolicVar().getInternalTypeContainer()
                        .getInternalType().name());
        }
        String tempString = "for(unsigned int SYMBVAR = 0; SYMBVAR < MAX && !THEREEXISTS; symbVar++) {";
        tempString = tempString.replace("SYMBVAR", node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replace("MAX", max);
        tempString = tempString.replace("THEREEXISTS", varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varName + " = " + variableNames.pop() + ";");
        code.deleteTab();
        code.add("}");
        testIfLast();
    }

    /**
     * generates the code for a notNode
     * @param node 
     */
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
        String varName = "comparison_" + comparisonNodeCounter++;
        variableNames.push(varName);
        int lhslistLevel = 0;
        int rhslistLevel = 0;
        
        for (InternalTypeContainer cont = node.getLHSBooleanExpNode().getInternalTypeContainer();
                cont.isList(); cont = cont.getListedType()) {
            lhslistLevel++;
        }
        for (InternalTypeContainer cont = node.getRHSBooleanExpNode().getInternalTypeContainer();
                cont.isList(); cont = cont.getListedType()) {
            rhslistLevel++;
        }
        
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        if (node.getLHSBooleanExpNode().getAccessVar() != null) {
            for (SymbolicVariable accessVar : node.getLHSBooleanExpNode().getAccessVar()) {
                System.out.println(lhslistLevel);
                lhslistLevel--;
            }
        }
        if (node.getRHSBooleanExpNode().getAccessVar() != null) {
            for (SymbolicVariable accessVar : node.getRHSBooleanExpNode().getAccessVar()) {
                rhslistLevel--;
            }
        }
        int maxListLevel = lhslistLevel > rhslistLevel ? lhslistLevel : rhslistLevel;
        InternalTypeContainer cont = lhslistLevel > rhslistLevel
                ? node.getLHSBooleanExpNode().getInternalTypeContainer()
                : node.getRHSBooleanExpNode().getInternalTypeContainer();
        String internCode = "unsigned int BOOL = 1;";
        internCode = internCode.replace("BOOL", varName);
        code.add(internCode);
        ArrayList<String> counter = new ArrayList<>();
        for (int i = 0; i < maxListLevel; ++i) {
            String max = "";
            String countingVar = "count_" + i;
            counter.add(countingVar);
            if (null != cont.getAccesTypeIfList()) {
                switch (cont.getAccesTypeIfList()) {
                    case VOTER:
                        max = "V";
                        break;
                    case CANDIDATE:
                        max = "C";
                        break;
                    case SEAT:
                        max = "S";
                        break;
                    default:
                        break;
                }
            }
            String loop = "for(unsigned int VAR = 0; VAR < MAX && BOOL; ++VAR) {";
            loop = loop.replaceAll("VAR", countingVar);
            loop = loop.replaceAll("MAX", max);
            loop = loop.replaceAll("BOOL", varName);
            code.add(loop);
            code.addTab();
        }
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();
        internCode = varName + " = " + rhs;
        for (int i = 0; i < lhslistLevel; ++i) {
            internCode += "[VAR]".replace("VAR", counter.get(i));
        }
        internCode += " " + node.getComparisonSymbol().getCStringRep() + " ";
        internCode += lhs;
        for (int i = 0; i < rhslistLevel; ++i) {
            internCode += "[VAR]".replace("VAR", counter.get(i));
        }
        code.add(internCode + ";");
        for (int i = 0; i < maxListLevel; ++i) {
            code.deleteTab();
            code.add("}");
        }
        testIfLast();
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

        String tempCode = "electNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
        SymbolicVariable[] accessVars = exp.getAccessVar();
        for (int i = 0; i < accessVars.length; ++i) {
            tempCode += "[VAR]".replace("VAR", accessVars[i].getId());
        }
        variableNames.push(tempCode);
    }

    @Override
    public void visitVoteExp(VoteExp exp) {
        String tempCode = "votesNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
        SymbolicVariable[] accessVars = exp.getAccessVar();
        for (int i = 0; i < accessVars.length; ++i) {
            tempCode += "[VAR]".replace("VAR", accessVars[i].getId());
        }
        variableNames.push(tempCode);
    }

    @Override
    public void visitVoteSumExp(VoteSumForCandExp exp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visitNumberExpNode(NumberExpression exp) {
        variableNames.push(String.valueOf(exp.getNumber()));
    }

    private void testIfLast() {
        if (variableNames.size() == 1) {
            if (assumeOrAssert.equals("assert") || assumeOrAssert.equals("assume")) {
                code.add(assumeOrAssert + "(" + variableNames.pop() + ");");
            } else {
                ErrorLogger.log("The CodeGeneration Visitor wasn't set to a proper mode");
            }
        }
    }
}
