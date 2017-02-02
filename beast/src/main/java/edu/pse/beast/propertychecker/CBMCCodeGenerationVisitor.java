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
import edu.pse.beast.datatypes.booleanExpAST.TypeExpression;
import edu.pse.beast.datatypes.booleanExpAST.VoteExp;
import edu.pse.beast.datatypes.booleanExpAST.VoteSumForCandExp;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
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
                throw new AssertionError(node.getDeclaredSymbolicVar().getInternalTypeContainer().getInternalType().name());
        }
        String tempString = "for(unsigned int SYMBVAR = 0; SYMBVAR < MAX && FORALL; symbVar++) {";
        tempString = tempString.replace("SYMBVAR", node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replace("MAX", max);
        tempString = tempString.replace("FORALL", varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varName + " = " + variableNames.pop());
        code.deleteTab();
        code.add("}");
        testIfLast();
    }

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
                throw new AssertionError(node.getDeclaredSymbolicVar().getInternalTypeContainer().getInternalType().name());
        }
        String tempString = "for(unsigned int SYMBVAR = 0; SYMBVAR < MAX && !THEREEXISTS; symbVar++) {";
        tempString = tempString.replace("SYMBVAR", node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replace("MAX", max);
        tempString = tempString.replace("THEREEXISTS", varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varName + " = " + variableNames.pop());
        code.deleteTab();
        code.add("}");
        testIfLast();
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
        String varName = "comparison_" + comparisonNodeCounter;
        comparisonNodeCounter++;
        variableNames.push(varName);

        int lhslistLevel = 0;
        int rhslistLevel = 0;
        InternalTypeContainer cont = node.getLHSBooleanExpNode().getInternalTypeContainer();

        while (cont.isList()) {
            lhslistLevel++;
            cont = cont.getListedType();
        }
        cont = node.getRHSBooleanExpNode().getInternalTypeContainer();
        while (cont.isList()) {
            rhslistLevel++;
            cont = cont.getListedType();
        }

        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);

        if (node.getLHSBooleanExpNode().getAccessVar() != null) {
            for (int i = 0; i < node.getLHSBooleanExpNode().getAccessVar().length; i++) {
                System.out.println(lhslistLevel);
                lhslistLevel--;
            }
        }

        if (node.getRHSBooleanExpNode().getAccessVar() != null) {
            for (int i = 0; i < node.getRHSBooleanExpNode().getAccessVar().length; i++) {
                rhslistLevel--;
            }
        }

        int maxListLevel = lhslistLevel > rhslistLevel ? lhslistLevel : rhslistLevel;
        cont = lhslistLevel > rhslistLevel ? node.getLHSBooleanExpNode().getInternalTypeContainer() : node.getRHSBooleanExpNode().getInternalTypeContainer();

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
        cont = node.getLHSBooleanExpNode().getInternalTypeContainer();
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
        variableNames.push(exp.getSymbolicVariable().getId());

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
