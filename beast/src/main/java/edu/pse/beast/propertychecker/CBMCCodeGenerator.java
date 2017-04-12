/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.FormalPropertySyntaxTreeToAstTranslator;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * After the build the code is fully generated and can be aquired by
     * getCode();
     *
     * @param electionDescription the lectionDecription that holds the code that
     * describes the voting method. that code will be merged with the generated
     * code
     * @param postAndPrePropertiesDescription the Descriptions that will be used
     * to generate the C-Code for CBMC
     */
    public CBMCCodeGenerator(ElectionDescription electionDescription,
            PostAndPrePropertiesDescription postAndPrePropertiesDescription) {

        this.translator = new FormalPropertySyntaxTreeToAstTranslator();
        this.electionDescription = electionDescription;
        this.postAndPrePropertiesDescription = postAndPrePropertiesDescription;
        code = new CodeArrayListBeautifier();
        inputType = electionDescription.getInputType();
        outputType = electionDescription.getOutputType();
        this.visitor = new CBMCCodeGenerationVisitor();
        cCodeHelper = new CCodeHelper();
        generateCode();

    }

    /**
     *
     * @return returns the generated code
     */
    public ArrayList<String> getCode() {
        return code.getCodeArrayList();
    }

    private void generateCode() {
        addHeader();
        addVoteSumFunc();

        code.add("//Code of the user");
        ArrayList<String> electionDescriptionCode = new ArrayList<>();
        electionDescriptionCode.addAll(electionDescription.getCode());
        code.addArrayList(electionDescriptionCode);

        addMainMethod();
    }

    private void addVoteSumFunc() {
        String input = inputType.getType().getListedType().isList() ? "unsigned int arr[V][C]" : "unsigned int arr[V]";
        code.add("unsigned int voteSumForCandidate(INPUT, unsigned int candidate) {".replace("INPUT", input));
        code.addTab();
        code.add("unsigned int sum = 0;");
        code.add("for(unsigned int i = 0; i < V; ++i) {");
        code.addTab();
        if (inputType.getType().getListLvl() == 1) {
            code.add("if(arr[i] == candidate) sum++;");
        } else {
            code.add("sum += arr[i][candidate];");
        }
        code.deleteTab();
        code.add("}");
        code.add("return sum;");
        code.deleteTab();
        code.add("}");
    }

    // maybe add something that let's the user use imports
    private void addHeader() {
        code.add("#include <stdlib.h>");
        code.add("#include <stdint.h>");
        code.add("#include <assert.h>");
        code.add("");
        code.add("unsigned int nondet_uint();");
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
                        // a Candidate is basically an unsigned int. Candidate 0 is 0 and so on
                        code.add("assume(0 <= " + id + " && " + id + " < C);");
                        // C is the number of total Candidates. 0 is A Candidate. C is not a candidate
                        break;
                    case SEAT:
                        // a Seat is a also an unsigned int. 
                        // The return of a votingmethod (an Array) gives the elected candidate(value) of the seat(id)
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
            String forTemplate = "for(unsigned int COUNTER = 0; COUNTER < MAX; COUNTER++){";

            InternalTypeContainer inputContainer = inputType.getType();
            int listDepth = 0;
            while (inputContainer.isList()) {
                String currentFor = forTemplate.replaceAll("COUNTER", counter[listDepth]);
                currentFor = currentFor.replaceAll("MAX", cCodeHelper.getListSize(inputContainer));
                code.add(currentFor);
                code.addTab();
                inputContainer = inputContainer.getListedType();
                listDepth++;
            }
            String min = cCodeHelper.getMin(inputType);
            String max = cCodeHelper.getMax(inputType);
            
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

            if (inputType.getId() == ElectionTypeContainer.ElectionTypeIds.PREFERENCE) {
                addPreferenceVotingArrayInitialisation(voteNumber);
            }

            for (int i = 0; i < listDepth; ++i) {
                code.deleteTab();
                code.add("}");
            }
            //initialize elects
            if (outputType.getType().isList()) {
                String temp = "unsigned int* tempElect" + voteNumber + " = voting(votes" + voteNumber + ");";
                code.add(temp);
                String electX = "unsigned int elect" + voteNumber + "[S];";
                code.add(electX);
                String forLoop = "for (int electLoop = 0; electLoop < S; electLoop++) {";
                code.add(forLoop);
                code.addTab();
                code.add("elect" + voteNumber + "[electLoop] = tempElect" + voteNumber + "[electLoop];");
                code.deleteTab();
                code.add("}");

            } else {
                String electX = "unsigned int elect" + voteNumber;
                electX += cCodeHelper.getCArrayType(outputType.getType());
                code.add(electX + ";");
                code.add("elect" + voteNumber + " = voting(votes" + voteNumber + ");");
            }

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

    private void addPreferenceVotingArrayInitialisation(int voteNumber) {
        code.add("for (unsigned int j_prime = 0; j_prime < counter_1; j_prime++) {");
        code.addTab();
        code.add("assume (votes" + voteNumber + "[counter_0][counter_1] != votes"
                + voteNumber + "[counter_0][j_prime]);");
        code.deleteTab();
        code.add("}");
    }
}
