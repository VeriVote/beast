/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;


import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.*;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.*;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.*;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
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
    private int voteSumCounter;
    private final ElectionTypeContainer inputType;
    private Queue<String> voteSumVars = new LinkedList<>();
    private Queue<Integer> voteSumLevels = new LinkedList<>();
    private int numberExpDepth = 0;

    private Stack<String> variableNames; //stack of the variable names. 
    private CodeArrayListBeautifier code; // object, that handels the generated code

    /**
     * this creates the visitor You should create 1 and only 1 visitor for every
     * c. file you want to make you have to set it to pre- or post-property mode
     * in order for it to function
     * @param inputType the input Type of the Election
     */
    public CBMCCodeGenerationVisitor(ElectionTypeContainer inputType) {
        this.inputType = inputType;
        andNodeCounter = 0;
        orNodeCounter = 0;
        implicationNodeCounter = 0;
        aquivalencyNodeCounter = 0;
        forAllNodeCounter = 0;
        thereExistsNodeCounter = 0;
        notNodeCounter = 0;
        comparisonNodeCounter = 0;
        voteSumCounter = 0;

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
     * @param node the visited and node
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
     * @param node the visited or node
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
     * @param node the visited implication node
     */
    @Override
    public void visitImplicationNode(ImplicationNode node) {
        String varName = "implication_" + implicationNodeCounter;
        implicationNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        String rhsName = variableNames.pop();
        String lhsName = variableNames.pop();
        code.add("unsigned int " + varName + " = (!(" + lhsName + ") || (" + rhsName + "));");
        testIfLast();
    }

    /**
     * generates the code for an EquivalencyNode
     *
     * @param node equivalencz node
     */
    @Override
    public void visitAquivalencyNode(EquivalencyNode node) {
        String varName = "aquivalency_" + aquivalencyNodeCounter;
        aquivalencyNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();
        code.add("unsigned int " + varName + " = (((" + lhs + ") && (" + rhs
                + ")) || (!(" + lhs + ") && (!" + rhs + ")));");
        testIfLast();
    }

    /**
     * generates the code for an ForAllNode
     *
     * @param node the visited node
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
     * @param node the visited node
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
        String tempString = "for(unsigned int SYMBVAR = 0; SYMBVAR < MAX && !THEREEXISTS; SYMBVAR++) {";
        tempString = tempString.replaceAll("SYMBVAR", node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replaceAll("MAX", max);
        tempString = tempString.replaceAll("THEREEXISTS", varName);
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
     *
     * @param node the visited node
     */
    @Override
    public void visitNotNode(NotNode node) {
        if (node.getNegatedExpNode() instanceof NotNode) {
            NotNode node2 = (NotNode) node.getNegatedExpNode();
            node2.getNegatedExpNode().getVisited(this);
            return;
        }
        String varName = "not_" + notNodeCounter;
        notNodeCounter++;
        variableNames.push(varName);
        node.getNegatedExpNode().getVisited(this);
        code.add("unsigned int " + varName + " = !(" + variableNames.pop() + ");");
        testIfLast();
    }
    int listlvl = 0;
    @Override
    public void visitComparisonNode(ComparisonNode node) {
        String varName = "comparison_" + comparisonNodeCounter;
        comparisonNodeCounter++;
        variableNames.push(varName);
        listlvl = 0;

        for (InternalTypeContainer cont = node.getLHSBooleanExpNode().getInternalTypeContainer();
             cont.isList(); cont = cont.getListedType()) {
            listlvl++;
        }
        node.getLHSBooleanExpNode().getVisited(this);
        int lhslistLevel = listlvl;

        listlvl = 0;

        for (InternalTypeContainer cont = node.getRHSBooleanExpNode().getInternalTypeContainer();
             cont.isList(); cont = cont.getListedType()) {
            listlvl++;
        }

        node.getRHSBooleanExpNode().getVisited(this);
        int rhslistLevel = listlvl;

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
        visitAcessingNodesReverseOrder(exp);

        String tempCode = "electNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
        for (int i = 0; i < exp.getAccessingVars().length; ++i) {
            tempCode += "[VAR]".replace("VAR", variableNames.pop());
            listlvl--;
        }
        variableNames.push(tempCode);
    }



    @Override
    public void visitVoteExp(VoteExp exp) {
        visitAcessingNodesReverseOrder(exp);

        String tempCode = "votesNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
        for (int i = 0; i < exp.getAccessingVars().length; ++i) {
            tempCode += "[VAR]".replace("VAR", variableNames.pop());
            listlvl--;
        }
        variableNames.push(tempCode);
    }

    private void visitAcessingNodesReverseOrder(AccessValueNode exp) {
        for (int i = exp.getAccessingVars().length - 1; i >= 0; i--) {
            exp.getAccessingVars()[i].getVisited(this);
        }
    }

    @Override
    public void visitVoteSumExp(VoteSumForCandExp exp) {
        exp.getAccesingVariable().getVisited(this);
        String funcCallTemplate = "unsigned int VARNAME = voteSumForCandidate(votesNUM, CAND);";
        String counter = "voteSumExp_" + voteSumCounter;
        voteSumCounter++;
        funcCallTemplate = funcCallTemplate.replaceAll("VARNAME", counter);
        funcCallTemplate = funcCallTemplate.replaceAll("NUM", String.valueOf(exp.getVoteNumber()));
        funcCallTemplate = funcCallTemplate.replaceAll("CAND", variableNames.pop());
        code.add(funcCallTemplate);
        variableNames.push(counter);
    }

    @Override
    public void visitIntegerNode(IntegerNode integerNode) {
        String varName = getNewNumberVariableName();
        code.add("unsigned int " + varName + " = " + integerNode.getHeldInteger() + ";");
        variableNames.push(varName);
    }

    private int numberVars = 0;
    private String getNewNumberVariableName() {
        return "integerVar_" + numberVars++;
    }

    @Override
    public void visitIntegerComparisonNode(IntegerComparisonNode listComparisonNode) {
        listComparisonNode.getLHSBooleanExpNode().getVisited(this);
        listComparisonNode.getRHSBooleanExpNode().getVisited(this);

        String varNameDecl = "integer_comp_" + comparisonNodeCounter++;
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();

        String comparisonString = "unsigned int " + varNameDecl + " = "  +
                rhs + " " +
                listComparisonNode.getComparisonSymbol().getCStringRep() + " " +
                lhs + ";";

        code.add(comparisonString);
        variableNames.push(varNameDecl);
        testIfLast();
    }

    @Override
    public void visitBinaryIntegerValuedNode(BinaryIntegerValuedNode binaryIntegerValuedNode) {
        binaryIntegerValuedNode.lhs.getVisited(this);
        binaryIntegerValuedNode.rhs.getVisited(this);
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();
        String varname = getNewNumberVariableName();
        code.add("unsigned int " + varname + " = " + lhs +
                " " + binaryIntegerValuedNode.getRelationSymbol() + " " + rhs + ";");
        variableNames.push(varname);
    }

    @Override
    public void visitAtPosNode(AtPosExp atPosExp) {
        atPosExp.getIntegerValuedExpression().getVisited(this);
        String varName = getAtPosVarName(atPosExp);
        String template = "unsigned int VAR = NUMBER;";
        template = template.replace("VAR", varName);
        template = template.replace("NUMBER", variableNames.pop());
        code.add(template);
        variableNames.push(varName);
    }

    private int amtByPosVar = 0;
    private String getAtPosVarName(AtPosExp atPosExp) {
        return atPosExp.getInternalTypeContainer().getInternalType().toString().toLowerCase() +
                "AtPos_" + amtByPosVar++;
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
