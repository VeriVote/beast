/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import java.util.ArrayList;
import java.util.LinkedList;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.internal.VotingMethodInput;
import edu.pse.beast.datatypes.internal.VotingMethodOutput;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.FormalPropertySyntaxTreeToAstTranslator;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * This creates the .c file which will be used to check it with CBMC It
 * generates a mainmethod, (with the FormalProperty inside it) important
 * IncludingCode and the votingMethode (the ElectionDescription)
 *
 * @author Niels
 */
public class CBMCCodeGenerator {
    
    private final CodeArrayListBeautifier code;
    private final ElectionDescription electionDescription;
    private final PostAndPrePropertiesDescription postAndPrePropertiesDescription;
    private final FormalPropertySyntaxTreeToAstTranslator translator;
    private final CBMCCodeGenerationVisitor visitor;
    private VotingMethodInput inputType;
    private VotingMethodOutput outputType;
    private int numberOfTimesVoted; // this number should be the number of rounds of votes the Propertys compare.
    private int loopVariableCounter;
    
    public CBMCCodeGenerator(ElectionDescription electionDescription, PostAndPrePropertiesDescription postAndPrePropertiesDescription) {
        
        
        this.translator = new FormalPropertySyntaxTreeToAstTranslator();
        this.electionDescription = electionDescription;
        this.postAndPrePropertiesDescription = postAndPrePropertiesDescription;
        code = new CodeArrayListBeautifier();
        
        ElectionTypeContainer inputTypeContainer = electionDescription.getInputType();
        ElectionTypeContainer outputTypeContainer = electionDescription.getOutputType(); 
        
        /**
         * this code is only here, because VotingInputType and VotingOutputType aren't used by the CElection Description yet.
         */
        
        
        
        switch(inputTypeContainer.getType().getInternalType()) {
            case VOTER:
                break;
            case CANDIDATE:
                break;
            case SEAT:
                break;
            case APPROVAL:
                break;
            case WEIGHTEDAPPROVAL:
                break;
            case INTEGER:
                break;
            default:
                throw new AssertionError(inputTypeContainer.getType().getInternalType().name());
      
        }
        switch(outputTypeContainer.getType().getInternalType()) {
            case VOTER:
                break;
            case CANDIDATE:
                break;
            case SEAT:
                break;
            case APPROVAL:
                break;
            case WEIGHTEDAPPROVAL:
                break;
            case INTEGER:
                break;
            default:
                throw new AssertionError(outputTypeContainer.getType().getInternalType().name());
        }
        
        
        this.visitor = new CBMCCodeGenerationVisitor(inputType, outputType);
        /*
        the variable loopVariableCounter is supposed to provide an index so that it is possible to have loops within loops in the generated code
         */
        this.loopVariableCounter = 0;
        generateCode();
    }
    
    public ArrayList<String> getCode() {
        return code.getCodeArrayList();
    }
    
    private void generateCode() {
        addHeader();
        
        addMainMethod();
        
        code.add("//Code of the user");
        ArrayList<String> electionDescriptionCode = (ArrayList<String>) electionDescription.getCode();
        electionDescriptionCode.forEach((item) -> {
            code.add(item);
        });
        
    }

    // maybe add something that let's the user use imports
    private void addHeader() {
        code.add("#inlcude <stdlib.h>");
        code.add("#inlcude <stdint.h>");
        code.add("#inlcude <assert.h>");
        code.add("");
        code.add("int nondet_uint();");
        code.add("int nondet_int();");
        code.add("");
        code.add("#define assert2(x, y) __CPROVER_assert(x, y)");
        code.add("#define assume(x) __CPROVER_assume(x)");
        code.add("");
    }

    /**
     * adds the main method the main method declares the boolean expression. In
     * the main method the votingmethod is called
     */
    private void addMainMethod() {
        
        code.add("int main(int argc, char *argv[]) {");
        code.addTab();

        // first the Variables have to be Initialized
        addSymbVarInitialisation();

        //generating the pre and post AbstractSyntaxTrees
        BooleanExpListNode preAST
                = generateAST(postAndPrePropertiesDescription.getPrePropertiesDescription().getCode());
        BooleanExpListNode postAST
                = generateAST(postAndPrePropertiesDescription.getPostPropertiesDescription().getCode());
        
        initializeNumberOfTimesVoted(preAST, postAST);
        
        addVotesArrayAndElectInitialisation();

        // the the PreProperties must be definied
        addPreProperties(preAST);

        // now the Post Properties can be checked
        addPostProperties(postAST);
        
        code.deleteTab();
        code.add("}");
    }

    /**
     * this should be used to create the VarInitialisation within the main
     * method.
     */
    private void addSymbVarInitialisation() {
        LinkedList<SymbolicVariable> symbolicVariableList = postAndPrePropertiesDescription.getSymbolicVariableList();
        code.add("//Symbolic Variables initialisation");
        symbolicVariableList.forEach((symbVar) -> {
            InternalTypeContainer internalType = symbVar.getInternalTypeContainer();
            String id = symbVar.getId();
            
            if (!internalType.isList()) {
                switch (internalType.getInternalType()) {
                    
                    case VOTER:
                        code.add("unsigned int " + id + " = nondet_uint();");
                        // a Voter is basically an unsigned int.
                        // The number shows which vote from votesX (the Array of all votes) belongs to the voter.
                        code.add("assume(0 <= " + id + " && " + id + " < V);");
                        // The Voter has to be in the range of possible Voters. V is the total amount of Voters.
                        break;
                    case CANDIDATE:
                        code.add("unsigned int " + id + " = nondet_uint();");
                        // a Candidate is basically an unsigned int. Candidate 1 is 1 and so on
                        code.add("assume(0 < " + id + " && " + id + " <= C);");
                        // C is the number of total Candidates. 0 is NOT a Candidate
                        break;
                    case SEAT:
                        // a Seat is a also an unsigned int. 
                        // The return of a votingmethod (an Array) gives you the elected candidate(value) of the seat(id)
                        code.add("unsigned int " + id + " = nondet_uint();");
                        // there are S seats. From 0 to S-1
                        code.add("assume(0 <= " + id + " && " + id + " < S);");
                        break;
                    case APPROVAL:
                        break;
                    case WEIGHTEDAPPROVAL:
                        break;
                    case INTEGER:
                        code.add("unsigned int " + id + " = nondet_uint();");
                        break;
                    default:
                        reportUnsupportedType(id);
                    
                }
            } else {
                reportUnsupportedType(id);
            }
        });
        code.add("");
        
    }

    /**
     * this adds the Code of the PreProperties. It uses a Visitor it creates
     */
    private void addPreProperties(BooleanExpListNode preAST) {
        
        visitor.setToPrePropertyMode();
        preAST.getBooleanExpressions().forEach((booleanExpressionLists) -> {
            booleanExpressionLists.forEach((booleanNode) -> {
                code.addArrayList(visitor.generateCode(booleanNode));
            });
        });
        
    }

    /**
     * this adds the Code of the PostProperties. It uses a Visitor it creates
     */
    private void addPostProperties(BooleanExpListNode postAST) {
        visitor.setToPostPropertyMode();
        postAST.getBooleanExpressions().forEach((booleanExpressionLists) -> {
            booleanExpressionLists.forEach((booleanNode) -> {
                code.addArrayList(visitor.generateCode(booleanNode));
            });
        });
        
    }
    
    private void reportUnsupportedType(String id) {
        ErrorLogger.log("Der Typ der symbolischen Variable " + id + " wird nicht unterstützt");
    }
    
    private void initializeNumberOfTimesVoted(BooleanExpListNode preAST, BooleanExpListNode postAST) {
        numberOfTimesVoted = (preAST.getMaxVoteLevel() > postAST.getMaxVoteLevel())
                ? preAST.getMaxVoteLevel() : postAST.getMaxVoteLevel();
        numberOfTimesVoted = (preAST.getHighestElect() > numberOfTimesVoted)
                ? preAST.getHighestElect() : numberOfTimesVoted;
        numberOfTimesVoted = (postAST.getHighestElect() > numberOfTimesVoted)
                ? postAST.getHighestElect() : numberOfTimesVoted;
    }
    
    private void addVotesArrayAndElectInitialisation() {
        
        code.add("//voting-array and elect variable initialisation");
        
        ElectionTypeContainer inputElectionType = electionDescription.getInputType();
        InternalTypeContainer inputInternalType = inputElectionType.getType();
        
        ElectionTypeContainer outputElectionType = electionDescription.getOutputType();
        InternalTypeContainer outputInternalType = outputElectionType.getType();
        
        if (outputInternalType.getInternalType() == InternalTypeRep.CANDIDATE
                || outputInternalType.isList()
                && outputInternalType.getListedType().getInternalType() == InternalTypeRep.CANDIDATE) {
            
            switch (inputInternalType.getInternalType()) {
                case VOTER:
                    ErrorLogger.log("The input Type should not be VOTER");
                    break;
                case CANDIDATE:
                    for (int i = 1; i <= numberOfTimesVoted; i++) {
                        code.add("unsigned int votes" + i + "[V];");
                        code.add("unsigned int i" + loopVariableCounter + ";");
                        code.add("for(i" + loopVariableCounter + " = 0; i" + loopVariableCounter
                                + " < V; i" + loopVariableCounter + "++) {");
                        code.addTab();
                        code.add("votes" + i + "[i" + loopVariableCounter + "] = nondet_uint();");
                        code.deleteTab();
                        code.add("}");
                        loopVariableCounter++;
                        if (outputInternalType.isList()) {
                            code.add("unsigned int elect" + i + "[C+1] = voting(votes" + i + ");");
                        } else {
                            code.add("unsigned int elect" + i + " = voting(votes" + i + ");");
                        }
                    }
                    
                    break;
                case SEAT:
                    ErrorLogger.log("The input Type should not be SEAT");
                    break;
                case APPROVAL:
                    for (int i = 1; i <= numberOfTimesVoted; i++) {
                        code.add("unsigned int votes" + i + "[V][C];");
                        code.add("unsigned int i" + loopVariableCounter + ";");
                        code.add("for(i" + loopVariableCounter + " = 0; i" + loopVariableCounter
                                + " < V; i" + loopVariableCounter + "++) {");
                        code.addTab();
                        code.add("unsigned int i" + (loopVariableCounter + 1) + ";");
                        code.add("for(i" + (loopVariableCounter + 1) + " = 0; i" + (loopVariableCounter + 1)
                                + " < V; i" + (loopVariableCounter + 1) + "++) {");
                        code.addTab();
                        code.add("votes" + i + "[i" + loopVariableCounter
                                + "][i" + (loopVariableCounter + 1) + " = nondet_uint();");
                        // for approval the values are either 0 or 1 for every candidate
                        code.add("assume(0 <= votes" + i + "[i" + loopVariableCounter
                                + "][i" + (loopVariableCounter + 1)
                                + "&& + votes" + i + "[i" + loopVariableCounter
                                + "][i" + (loopVariableCounter + 1) + " <=1);");
                        code.deleteTab();
                        code.add("}");
                        code.deleteTab();
                        code.add("}");
                        loopVariableCounter++;
                        loopVariableCounter++;
                        if (outputInternalType.isList()) {
                            code.add("unsigned int elect" + i + "[C+1] = voting(votes" + i + ");");
                        } else {
                            code.add("unsigned int elect" + i + " = voting(votes" + i + ");");
                        }
                    }
                    break;
                case WEIGHTEDAPPROVAL:
                    for (int i = 1; i <= numberOfTimesVoted; i++) {
                        code.add("unsigned int votes" + i + "[V][C];");
                        code.add("unsigned int i" + loopVariableCounter + ";");
                        code.add("for(i" + loopVariableCounter + " = 0; i" + loopVariableCounter
                                + " < V; i" + loopVariableCounter + "++) {");
                        code.addTab();
                        code.add("unsigned int i" + (loopVariableCounter + 1) + ";");
                        code.add("for(i" + (loopVariableCounter + 1) + " = 0; i" + (loopVariableCounter + 1)
                                + " < V; i" + (loopVariableCounter + 1) + "++) {");
                        code.addTab();
                        code.add("votes" + i + "[i" + loopVariableCounter
                                + "][i" + (loopVariableCounter + 1) + " = nondet_uint();");
                        code.add("assume(" + inputElectionType.getLowerBound()
                                + "<= votes" + i + "[i" + loopVariableCounter
                                + "][i" + (loopVariableCounter + 1)
                                + "&& + votes" + i + "[i" + loopVariableCounter
                                + "][i" + (loopVariableCounter + 1) + " <=" + inputElectionType.getUpperBound() + ");");
                        code.deleteTab();
                        code.add("}");
                        code.deleteTab();
                        code.add("}");
                        loopVariableCounter++;
                        loopVariableCounter++;
                        if (outputInternalType.isList()) {
                            code.add("unsigned int elect" + i + "[C+1] = voting(votes" + i + ");");
                        } else {
                            code.add("unsigned int elect" + i + " = voting(votes" + i + ");");
                        }
                    }
                    break;
                case INTEGER:
                    ErrorLogger.log("The input Type should not be INTEGER");
                    break;
                default:
                    throw new AssertionError(inputInternalType.getInternalType().name());
                
            }
        } else {
            ErrorLogger.log("The output Type can only be CANDIDATE");
        }
    }
    
    private BooleanExpListNode generateAST(String code) {
        FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(new ANTLRInputStream(code));
        CommonTokenStream ts = new CommonTokenStream(l);
        FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(ts);
        
        BooleanExpScope declaredVars = new BooleanExpScope();
        
        postAndPrePropertiesDescription.getSymbolicVariableList().forEach((v) -> {
            declaredVars.addTypeForId(v.getId(), v.getInternalTypeContainer());
        });
        
        return translator.generateFromSyntaxTree(
                p.booleanExpList(),
                electionDescription.getInputType().getType(),
                electionDescription.getOutputType().getType(),
                declaredVars);
    }
}
