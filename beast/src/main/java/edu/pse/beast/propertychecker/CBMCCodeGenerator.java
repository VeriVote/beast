/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.toolbox.CCodeHelper;
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
    private final ElectionTypeContainer inputType;
    private final ElectionTypeContainer outputType;
    private final CCodeHelper cCodeHelper;
    private int numberOfTimesVoted; // this number should be the number of rounds of votes the Propertys compare.

    public CBMCCodeGenerator(ElectionDescription electionDescription, PostAndPrePropertiesDescription postAndPrePropertiesDescription) {

        this.translator = new FormalPropertySyntaxTreeToAstTranslator();
        this.electionDescription = electionDescription;
        this.postAndPrePropertiesDescription = postAndPrePropertiesDescription;
        code = new CodeArrayListBeautifier();

        inputType = electionDescription.getInputType();
        outputType = electionDescription.getOutputType();

        this.visitor = new CBMCCodeGenerationVisitor();
        /*
        the variable loopVariableCounter is supposed to provide an index so that it is possible to have loops within loops in the generated code
         */

        cCodeHelper = new CCodeHelper();

        generateCode();

    }

    public ArrayList<String> getCode() {
        return code.getCodeArrayList();
    }

    private void generateCode() {
        addHeader();

        code.add("//Code of the user");
        ArrayList<String> electionDescriptionCode = new ArrayList<>();
        electionDescriptionCode.addAll(electionDescription.getCode());
        code.addArrayList(electionDescriptionCode);

        addMainMethod();

    }

    // maybe add something that let's the user use imports
    private void addHeader() {
        code.add("#include <stdlib.h>");
        code.add("#include <stdint.h>");
        code.add("#include <assert.h>");
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
        List<SymbolicVariable> symbolicVariableList = postAndPrePropertiesDescription.getSymbolicVariableList();
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
                        code.add("assume(0 <= " + id + " && " + id + " < C);");
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

        code.add("");
        code.add("//preproperties ");
        code.add("");
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

        code.add("");
        code.add("//postproperties ");
        code.add("");
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

        for (int voteNumber = 1; voteNumber <= numberOfTimesVoted; voteNumber++) {

            String votesX = "unsigned int votes" + voteNumber;

            votesX += cCodeHelper.getCArrayType(inputType.getType());

            code.add(votesX + ";");

            String[] counter = {"counter_0", "counter_1", "counter_2", "counter_3"};

            String forTemplate = "for(unsigned int COUNTER = 0; COUNTER < MAX; ++COUNTER){";

            InternalTypeContainer cont = inputType.getType();
            int listDepth = 0;
            while (cont.isList()) {
                String currentFor = forTemplate.replaceAll("COUNTER", counter[listDepth]);
                currentFor = currentFor.replaceAll("MAX", cCodeHelper.getListSize(cont));
                code.add(currentFor);
                code.addTab();
                cont = cont.getListedType();
                listDepth++;
            }
            String min = cCodeHelper.getMin(inputType, cont.getInternalType());
            String max = cCodeHelper.getMax(inputType, cont.getInternalType());

            String votesElement = "votes" + voteNumber;
            for (int i = 0; i < listDepth; ++i) {
                votesElement += "[COUNTER]".replace("COUNTER", counter[i]);
            }

            String nondetInt = (votesElement + " = nondet_uint();");
            String voteDecl = ("assume((MIN <= " + votesElement + ") && (" + votesElement + " < MAX));");
            voteDecl = voteDecl.replace("MIN", min);
            voteDecl = voteDecl.replace("MAX", max);

            code.add(nondetInt);
            code.add(voteDecl);

            for (int i = 0; i < listDepth; ++i) {
                code.deleteTab();
                code.add("}");
            }

            String electX = "unsigned int elect" + voteNumber;

            electX += cCodeHelper.getCArrayType(outputType.getType());

            code.add(electX + ";");

            code.add("elect" + voteNumber + " = voting(votes" + voteNumber + ");");

            //initialize elects
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
