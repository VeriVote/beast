/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer.ElectionInputTypeIds;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer.ElectionOutputTypeIds;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.electionSimulator.ElectionSimulation;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
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
 * This class creates the .c file which will be checked with CBMC. It generates
 * a main method (including the FormalProperty), important IncludingCode and the
 * votingMethod (the ElectionDescription).
 *
 * @author Niels
 */
public class CBMCCodeGenerator {

    private final CodeArrayListBeautifier code;
    private final ElectionDescription electionDescription;
    private final PreAndPostConditionsDescription PreAndPostConditionsDescription;
    private final FormalPropertySyntaxTreeToAstTranslator translator;
    private final CBMCCodeGenerationVisitor visitor;
    private final ElectionTypeContainer inputType;
    private final ElectionTypeContainer outputType;
    private final CCodeHelper cCodeHelper;
    private int numberOfTimesVoted; // this number should be the number of
                                    // rounds of votes the Properties compare.
    private final boolean isMargin;
    private int margin;
    private List<Long> origResult;
    private boolean isTest;

    /**
     * After the build the code is fully generated and can be aquired by
     * getCode();
     *
     * @param electionDescription
     *            the lectionDecription that holds the code that describes the
     *            voting method. that code will be merged with the generated
     *            code
     * @param PreAndPostConditionsDescription
     *            the Descriptions that will be used to generate the C-Code for
     *            CBMC
     */
    public CBMCCodeGenerator(ElectionDescription electionDescription,
            PreAndPostConditionsDescription PreAndPostConditionsDescription) {

        this.translator = new FormalPropertySyntaxTreeToAstTranslator();
        this.electionDescription = electionDescription;
        this.PreAndPostConditionsDescription = PreAndPostConditionsDescription;
        code = new CodeArrayListBeautifier();
        inputType = electionDescription.getInputType();
        outputType = electionDescription.getOutputType();
        this.visitor = new CBMCCodeGenerationVisitor();
        cCodeHelper = new CCodeHelper();
        this.isMargin = false;
        generateCodeCheck();

    }

    /**
     * After the build the code is fully generated and can be aquired by
     * getCode();
     *
     * @param electionDescription
     *            the lectionDecription that holds the code that describes the
     *            voting method. that code will be merged with the generated
     *            code
     * @param PreAndPostConditionsDescription
     *            the Descriptions that will be used to generate the C-Code for
     *            CBMC
     */
    public CBMCCodeGenerator(ElectionDescription electionDescription,
            PreAndPostConditionsDescription PreAndPostConditionsDescription,
            int margin, List<Long> origResult, boolean isTest) {

        this.translator = new FormalPropertySyntaxTreeToAstTranslator();
        this.electionDescription = electionDescription;
        this.PreAndPostConditionsDescription = PreAndPostConditionsDescription;
        code = new CodeArrayListBeautifier();
        inputType = electionDescription.getInputType();
        outputType = electionDescription.getOutputType();
        this.visitor = new CBMCCodeGenerationVisitor();
        cCodeHelper = new CCodeHelper();
        this.margin = margin;
        this.origResult = origResult;
        this.isTest = isTest;

        this.isMargin = true;
        generateCodeMargin();

    }

    /**
     *
     * @return returns the generated code
     */
    public ArrayList<String> getCode() {
        return code.getCodeArrayList();
    }

    private void generateCodeCheck() {

        addHeader();
        addVoteSumFunc(false);
        addVoteSumFunc(true);

        code.add("//Code of the user");
        ArrayList<String> electionDescriptionCode = new ArrayList<>();
        electionDescriptionCode.addAll(electionDescription.getCode());
        code.addArrayList(electionDescriptionCode);

        addMainMethod();

    }

    private void generateCodeMargin() { // we want to create code for a margin
                                        // computation or just a test run

        boolean multiOut = electionDescription.getOutputType()
                .getOutputID() == ElectionOutputTypeIds.CAND_PER_SEAT;

        int[][] votingData = ElectionSimulation.getVotingData();

        // add the header and the voting data
        addMarginHeaders(votingData);

        // add the code the user wrote (e.g the election function)
        code.addAll(BEASTCommunicator.getCentralObjectProvider()
                .getElectionDescriptionSource().getElectionDescription()
                .getCode());

        // add the code which defines the votes
        code.addAll(getVotingResultCode(votingData));

        switch (ElectionSimulation.getMode()) {
        case compileAndRunCBMC:

            addMarginMainTest(multiOut);
            break;

        case searchMinDiffAndShowCBMC:

            if (isTest) {
                addMarginMainTest(multiOut);
            }
            else {
                addMarginMainCheck(multiOut, margin, origResult);
            }

            break;

        default:

            System.err.println("unknown mode used");

            break;
        }

    }

    private void addMarginMainCheck(boolean multiOut, int margin,
            List<Long> origResult) {
        // we add the margin which will get computed by the model checker
        code.add("#ifndef MARGIN\n #define MARGIN " + margin + "\n #endif");
        // we also add the original result, which is calculated by compiling the
        // program and running it

        if (multiOut) {
            code.addAll(getLastResultCode(origResult));
        }
        else {
            code.addAll(getLastResultCode(origResult.get(0)));
        }

        // add the verify methode:
        // taken and adjusted from the paper:
        // https://formal.iti.kit.edu/~beckert/pub/evoteid2016.pdf
        code.add("void verify() {");
        // code.add("int new_votes1[V], diff[V], total_diff, pos_diff;");

        code.addTab();

        code.add("int total_diff = 0;");

        switch (inputType.getInputID()) {
        case SINGLE_CHOICE:
            code.add("int new_votes1[V];");
            code.add("for (int i = 0; i < V; i++) {"); // go over all voters
            code.addTab();
            code.add("int changed = nondet_int();"); // determine, if we want to
                                                     // changed votes for
                                                     // this
                                                     // voter
            code.add("assume(0 <= changed);");
            code.add("assume(changed <= 1);");
            code.add("if(changed) {");
            code.addTab();
            code.add("total_diff++;"); // if we changed the vote, we keep track
                                       // of it
            code.add("new_votes1[i] = !ORIG_VOTES[i];"); // flip the vote (0 ->
                                                         // 1 |
                                                         // 1 -> 0)
            code.deleteTab();
            code.add("} else {");
            code.addTab();
            code.add("new_votes1[i] = ORIG_VOTES[i];");
            code.deleteTab();
            code.add("}");
            code.deleteTab();
            code.add("}");
            code.add("assume(total_diff <= MARGIN);"); // no more changes than
                                                       // margin allows
            // if (multiOut) {
            // code.add("int *tmp_result = voting(new_votes1);");
            //
            // code.add("int new_result1[S];"); // create the array where the
            // // new seats will get saved
            //
            // code.add("for (int i = 0; i < S; i++) {"); // iterate over the
            // // seat array, and
            // // fill it
            // // we do this, so our cbmc parser can read out the value of the
            // // array
            // code.add("new_result1[i] = tmp_result[i];");
            // code.add("}"); // close the for loop
            //
            // code.add("for (int i = 0; i < S; i++) {"); // iterate over all
            // // candidates /
            // // seats and assert
            // // their equality
            // code.add("assert(new_result1[i] == ORIG_RESULT[i]);");
            // code.add("}"); // end of the for loop
            // } else {
            // code.add("int new_result1 = voting(new_votes1);");
            // code.add("assert(new_result1 == ORIG_RESULT);");
            // }
            // code.add("}"); // end of the function
            break;
        case APPROVAL:

            code.add("int new_votes1[V][C];");

            code.add("for (int i = 0; i < V; i++) {"); // go over all voters
            code.addTab();
            code.add("for (int j = 0; i < C; i++) {"); // go over all candidates
            code.addTab();
            code.add("int changed = nondet_int();"); // determine, if we want to
                                                     // changed votes for
                                                     // this
                                                     // voter - candidate
                                                     // pair
            code.add("assume(0 <= changed);");
            code.add("assume(changed <= 1);");
            code.add("if(changed) {");
            code.addTab();
            code.add("total_diff++;"); // if we changed the vote, we keep track
                                       // of it
            code.add("new_votes1[i][j] = !ORIG_VOTES[i][j];"); // flip the vote
                                                               // (0 -> 1 |
            // 1 -> 0)
            code.deleteTab();
            code.add("} else {");
            code.addTab();
            code.add("new_votes1[i][j] = ORIG_VOTES[i][j];");
            code.deleteTab();
            code.add("}");
            code.deleteTab();
            code.add("}");
            code.deleteTab();
            code.add("}"); // end of the double for loop
            code.add("assume(total_diff <= MARGIN);"); // no more changes than
                                                       // margin allows
            // if (multiOut) {
            // code.add("int *tmp_result = voting(new_votes1);");
            //
            // code.add("int new_result1[S];"); // create the array where the
            // // new seats will get saved
            //
            // code.add("for (int i = 0; i < S; i++) {"); // iterate over the
            // // seat array, and
            // // fill it
            // // we do this, so our cbmc parser can read out the value of the
            // // array
            // code.add("new_result1[i] = tmp_result[i];");
            // code.add("}"); // close the for loop
            //
            // code.add("for (int i = 0; i < S; i++) {"); // iterate over all
            // // candidates /
            // // seats
            // code.add("assert(new_result1[i] == ORIG_RESULT[i]);");
            // code.add("}"); // end of the for loop
            // } else {
            // code.add("int new_result1 = voting(new_votes1);");
            // code.add("assert(new_result1 == ORIG_RESULT);");
            // }
            // code.add("}"); // end of the function
            break;
        case WEIGHTED_APPROVAL:

            code.add("int new_votes1[V][C];");

            code.add("for (int i = 0; i < V; i++) {"); // go over all voters
            code.addTab();
            code.add("for (int j = 0; i < C; i++) {"); // go over all candidates
            code.addTab();
            code.add("int changed = nondet_int();"); // determine, if we want to
                                                     // changed votes for
                                                     // this
                                                     // voter - candidate
                                                     // pair
            code.add("assume(0 <= changed);");
            code.add("assume(changed <= 1);");
            code.add("if(changed) {");
            code.addTab();
            code.add("total_diff++;"); // if we changed the vote, we keep track
                                       // of it
            code.add("new_votes1[i][j] = nondet_int();");
            code.add("assume(new_votes1[i][j] != ORIG_VOTES[i][j]);"); // set
                                                                       // the
                                                                       // vote
                                                                       // to
            // (0-100), but
            // different
            // from
            // original
            code.add("assume(0 <= new_votes1[i][j]);");
            code.add("assume(new_votes1[i][j] <= 100);");
            code.deleteTab();
            code.add("} else {");
            code.addTab();
            code.add("new_votes1[i][j] = ORIG_VOTES[i][j];");
            code.deleteTab();
            code.add("}");
            code.deleteTab();
            code.add("}");
            code.deleteTab();
            code.add("}"); // end of the double for loop
            code.add("assume(total_diff <= MARGIN);"); // no more changes than
                                                       // margin allows
            // if (multiOut) {
            // code.add("int *tmp_result = voting(new_votes1);");
            //
            // code.add("int new_result1[S];"); // create the array where the
            // // new seats will get saved
            //
            // code.add("for (int i = 0; i < S; i++) {"); // iterate over the
            // // seat array, and
            // // fill it
            // // we do this, so our cbmc parser can read out the value of the
            // // array
            // code.add("new_result1[i] = tmp_result[i];");
            // code.add("}"); // close the for loop
            //
            // code.add("for (int i = 0; i < S; i++) {"); // iterate over all
            // // candidates /
            // // seats
            // code.add("assert(new_result1[i] == ORIG_RESULT[i]);");
            // code.add("}"); // end of the for loop
            // } else {
            // code.add("int new_result1 = voting(new_votes1);");
            // code.add("assert(new_result1 == ORIG_RESULT);");
            // }
            // code.add("}"); // end of the function
            break;
        case PREFERENCE:
            // TODO fix
            code.add("int new_votes1[V][C];");

            code.add("for (int i = 0; i < V; i++) {"); // go over all voters
            code.addTab();
            code.add("for (int j = 0; i < C; i++) {"); // go over all candidates
            code.addTab();
            code.add("int changed = nondet_int();"); // determine, if we want to
                                                     // changed votes for
                                                     // this
                                                     // voter - candidate
                                                     // pair
            code.add("assume(0 <= changed);");
            code.add("assume(changed <= 1);");
            code.add("if(changed) {");
            code.addTab();
            code.add("total_diff++;"); // if we changed the vote, we keep track
                                       // of it
            code.add("new_votes1[i][j] = nondet_int();");
            code.add("assume(new_votes1[i][j] != ORIG_VOTES[i][j]);"); // set
                                                                       // the
                                                                       // vote
                                                                       // to
            // (0-100), but
            // different
            // from
            // original
            code.add("assume(0 <= new_votes1[i][j]);");
            code.add("assume(new_votes1[i][j] <= 100);");
            code.deleteTab();
            code.add("} else {");
            code.addTab();
            code.add("new_votes1[i][j] = ORIG_VOTES[i][j];");
            code.deleteTab();
            code.add("}");
            code.deleteTab();
            code.add("}");
            code.deleteTab();
            code.add("}"); // end of the double for loop
            code.add("assume(total_diff <= MARGIN);"); // no more changes than
                                                       // margin allows
            // if (multiOut) {
            // code.add("int *tmp_result = voting(new_votes1);");
            //
            // code.add("int new_result1[S];"); // create the array where the
            // // new seats will get saved
            //
            // code.add("for (int i = 0; i < S; i++) {"); // iterate over the
            // // seat array, and
            // // fill it
            // // we do this, so our cbmc parser can read out the value of the
            // // array
            // code.add("new_result1[i] = tmp_result[i];");
            // code.add("}"); // close the for loop
            //
            // code.add("for (int i = 0; i < S; i++) {"); // iterate over all
            // // candidates /
            // // seats
            // code.add("assert(new_result1[i] == ORIG_RESULT[i]);");
            // code.add("}"); // end of the for loop
            // } else {
            // code.add("int new_result1 = voting(new_votes1);");
            // code.add("assert(new_result1 == ORIG_RESULT);");
            // }
            // code.add("}"); // end of the function

            break;

        default:

            ErrorForUserDisplayer.displayError(
                    "the current input type was not found. Please extend the methode \"generateMarginComputationCode\" in the class ElectionSimulation ");
            break;
        }

        if (multiOut) {
            code.add("struct result tmp = voting(new_votes1);");

            code.add("int *tmp_result = tmp.arr;");

            code.add("int new_result1[S];"); // create the array where the
            // new seats will get saved

            code.add("for (int i = 0; i < S; i++) {"); // iterate over the
            code.addTab();
            // seat array, and
            // fill it
            // we do this, so our cbmc parser can read out the value of the
            // array
            code.add("new_result1[i] = tmp_result[i];");
            code.deleteTab();
            code.add("}"); // close the for loop

            code.add("for (int i = 0; i < S; i++) {"); // iterate over all
            code.addTab();
            // candidates /
            // seats
            code.add("assert(new_result1[i] == ORIG_RESULT[i]);");
            code.deleteTab();
            code.add("}"); // end of the for loop
        }
        else {
            code.add("int new_result1 = voting(new_votes1);");
            code.add("assert(new_result1 == ORIG_RESULT);");
        }

        code.deleteTab();

        code.add("}"); // end of the function

        // not used lines ( I think) //TODO if they really aren't needed, delete
        // them
        // code.add("new_votes1[i] = ORIG_VOTES[i] + diff[i];");
        // code.add("if (0 < diff[i]) pos_diff += diff[i];");
        // code.add("total_diff += diff[i];");
        // code.add("}");
        // code.add("__CPROVER_assume (pos_diff ≤ MARGIN);");
        // code.add("__CPROVER_assume (total_diff == 0);");
        // code.add("int *result = voting(new_votes1);");
        // code.add("assert (equals(result, ORIG_RESULT));");
        // code.add("}");

        // add the main methode
        code.add("int main() {");
        code.addTab();
        code.add("verify();");
        code.deleteTab();
        code.add("}");
    }

    private void addMarginHeaders(int[][] votingData) {

        // add the headers CBMC needs;

        addHeader();

        // define some pre processor values
        code.add("#ifndef V\n #define V " + votingData.length + "\n #endif");
        code.add("#ifndef C\n #define C " + votingData[0].length + "\n #endif");
        code.add("#ifndef S\n #define S " + votingData[0].length + "\n #endif");
    }

    private void addMarginMainTest(boolean multiOut) {

        int voteNumber = 1; // because we only have one vote, we hardcode the
                            // value one here

        code.add("int main() {");
        code.addTab();

        if (multiOut) {

            String temp = "struct result tmp" + voteNumber
                    + " = voting(ORIG_VOTES);";
            code.add(temp);
            String tempElect = "unsigned int *tempElect" + voteNumber + " = tmp"
                    + voteNumber + ".arr;";
            code.add(tempElect);
            String electX = "unsigned int elect" + voteNumber + "[S];";
            code.add(electX);
            String forLoop = "for (int electLoop = 0; electLoop < S; electLoop++) {";
            code.add(forLoop);
            code.addTab();
            code.add("elect" + voteNumber + "[electLoop] = tempElect"
                    + voteNumber + "[electLoop];");
            code.deleteTab();
            code.add("}");

        }
        else {
            code.add("int elect1 = voting(ORIG_VOTES);"); // we just have a
            // single int as
            // the winner
        }

        // switch (electionDescription.getInputType().getInputID()) {
        //
        //
        //
        //
        //
        // case SINGLE_CHOICE:
        // if (multiOut) {
        // code.add("int *winner1 = voting(ORIG_VOTES);"); // call the
        // // voting method
        // } else {
        // code.add("int winner1 = voting(ORIG_VOTES);"); // we just have a
        // // single int as
        // // the winner
        // }
        // break;
        // case APPROVAL:
        // if (multiOut) {
        // code.add("int *winner1 = voting(ORIG_VOTES);"); // call the
        // // voting method
        // } else {
        // code.add("int winner1 = voting(ORIG_VOTES);"); // we just have a
        // // single int as
        // // the winner
        // }
        // break;
        // case WEIGHTED_APPROVAL:
        // if (multiOut) {
        // code.add("int *winner1 = voting(ORIG_VOTES);"); // call the
        // // voting method
        // } else {
        // code.add("int winner1 = voting(ORIG_VOTES);"); // we just have a
        // // single int as
        // // the winner
        // }
        // break;
        // case PREFERENCE:
        // if (multiOut) {
        // code.add("int *winner1 = voting(ORIG_VOTES);"); // call the
        // // voting method
        // } else {
        // code.add("int winner1 = voting(ORIG_VOTES);"); // we just have a
        // // single int as
        // // the winner
        // }
        // break;
        //
        // default:
        // ErrorForUserDisplayer.displayError(
        // "the current input type was not found. Please extend the methode
        // \"generateRunnableCode\" in the class ElectionSimulation ");
        // break;
        // }

        // add an assertion that always fails, so we can extract the trace
        code.add("assert(0);");

        code.deleteTab();

        code.add("}");
    }

    private void addVoteSumFunc(boolean unique) {
        String input = inputType.getType().getListedType().isList()
                ? "unsigned int arr[V][C]"
                : "unsigned int arr[V]";
        code.add("unsigned int voteSumForCandidate" + (unique ? "Unique" : "")
                + "(INPUT, unsigned int candidate) {".replace("INPUT", input));
        code.addTab();
        code.add("unsigned int sum = 0;");
        code.add("for(unsigned int i = 0; i < V; ++i) {");
        code.addTab();
        if (inputType.getType().getListLvl() == 1) {
            code.add("if(arr[i] == candidate) sum++;");
        }
        else if (inputType.getInputID() == ElectionInputTypeIds.APPROVAL
                || inputType
                        .getInputID() == ElectionInputTypeIds.WEIGHTED_APPROVAL) {
            code.add("unsigned int candSum = arr[i][candidate];");
            if (unique) {
                code.add("for(unsigned int j = 0; j < C; ++i) {");
                code.add(
                        "if(j != candidate && arr[i][j] >= candSum) candSum = 0;");
                code.add("}");
            }
            code.add("sum += candSum;");
        }
        else {
            code.add("if(arr[i][0] == candidate) sum++;");
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
        code.add("struct result { unsigned int arr[S]; };"); // add a result
                                                             // struct to be
                                                             // returned in case
                                                             // of a parliament
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

        // generating the pre and post AbstractSyntaxTrees
        BooleanExpListNode preAST = generateAST(PreAndPostConditionsDescription
                .getPreConditionsDescription().getCode());
        BooleanExpListNode postAST = generateAST(PreAndPostConditionsDescription
                .getPostConditionsDescription().getCode());

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
        List<SymbolicVariable> symbolicVariableList = PreAndPostConditionsDescription
                .getSymbolicVariableList();
        code.add("//Symbolic Variables initialisation");
        symbolicVariableList.forEach((symbVar) -> {
            InternalTypeContainer internalType = symbVar
                    .getInternalTypeContainer();
            String id = symbVar.getId();

            if (!internalType.isList()) {
                switch (internalType.getInternalType()) {

                case VOTER:
                    code.add("unsigned int " + id + " = nondet_uint();");
                    // a Voter is basically an unsigned int.
                    // The number shows which vote from votesX (the Array of all
                    // votes) belongs to the voter.
                    code.add("assume(0 <= " + id + " && " + id + " < V);");
                    // The Voter has to be in the range of possible Voters. V is
                    // the total amount of Voters.
                    break;
                case CANDIDATE:
                    code.add("unsigned int " + id + " = nondet_uint();");
                    // a Candidate is basically an unsigned int. Candidate 0 is
                    // 0 and so on
                    code.add("assume(0 <= " + id + " && " + id + " < C);");
                    // C is the number of total Candidates. 0 is A Candidate. C
                    // is not a candidate
                    break;
                case SEAT:
                    // a Seat is a also an unsigned int.
                    // The return of a votingmethod (an Array) gives the elected
                    // candidate(value) of the seat(id)
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
            }
            else {
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
        visitor.setToPreConditionMode();
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
        visitor.setToPostConditionMode();
        postAST.getBooleanExpressions().forEach((booleanExpressionLists) -> {
            booleanExpressionLists.forEach((booleanNode) -> {
                code.addArrayList(visitor.generateCode(booleanNode));
            });
        });

    }

    private void reportUnsupportedType(String id) {
        ErrorLogger.log("Der Typ der symbolischen Variable " + id
                + " wird nicht unterstützt");
    }

    private void initializeNumberOfTimesVoted(BooleanExpListNode preAST,
            BooleanExpListNode postAST) {
        numberOfTimesVoted = (preAST.getMaxVoteLevel() > postAST
                .getMaxVoteLevel()) ? preAST.getMaxVoteLevel()
                        : postAST.getMaxVoteLevel();
        numberOfTimesVoted = (preAST.getHighestElect() > numberOfTimesVoted)
                ? preAST.getHighestElect()
                : numberOfTimesVoted;
        numberOfTimesVoted = (postAST.getHighestElect() > numberOfTimesVoted)
                ? postAST.getHighestElect()
                : numberOfTimesVoted;
    }

    private void addVotesArrayAndElectInitialisation() {

        code.add("//voting-array and elect variable initialisation");

        for (int voteNumber = 1; voteNumber <= numberOfTimesVoted; voteNumber++) {

            String votesX = "unsigned int votes" + voteNumber;
            votesX += cCodeHelper.getCArrayType(inputType.getType());
            code.add(votesX + ";");

            String[] counter = { "counter_0", "counter_1", "counter_2",
                    "counter_3" };
            String forTemplate = "for(unsigned int COUNTER = 0; COUNTER < MAX; COUNTER++){";

            InternalTypeContainer inputContainer = inputType.getType();
            int listDepth = 0;
            while (inputContainer.isList()) {
                String currentFor = forTemplate.replaceAll("COUNTER",
                        counter[listDepth]);
                currentFor = currentFor.replaceAll("MAX",
                        cCodeHelper.getListSize(inputContainer));
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
            String voteDecl = ("assume((MIN <= " + votesElement + ") && ("
                    + votesElement + " < MAX));");
            voteDecl = voteDecl.replace("MIN", min);
            voteDecl = voteDecl.replace("MAX", max);

            code.add(nondetInt);
            code.add(voteDecl);

            if (inputType
                    .getInputID() == ElectionTypeContainer.ElectionInputTypeIds.PREFERENCE) {
                addPreferenceVotingArrayInitialisation(voteNumber);
            }

            for (int i = 0; i < listDepth; ++i) {
                code.deleteTab();
                code.add("}");
            }
            // initialize elects
            if (outputType.getType().isList()) {
                String temp = "struct result tmp" + voteNumber
                        + " = voting(votes" + voteNumber + ");";
                code.add(temp);
                String tempElect = "unsigned int *tempElect" + voteNumber
                        + " = tmp" + voteNumber + ".arr;";
                code.add(tempElect);
                String electX = "unsigned int elect" + voteNumber + "[S];";
                code.add(electX);
                String forLoop = "for (int electLoop = 0; electLoop < S; electLoop++) {";
                code.add(forLoop);
                code.addTab();
                code.add("elect" + voteNumber + "[electLoop] = tempElect"
                        + voteNumber + "[electLoop];");
                code.deleteTab();
                code.add("}");

            }
            else {
                String electX = "unsigned int elect" + voteNumber;
                electX += cCodeHelper.getCArrayType(outputType.getType());
                code.add(electX + ";");
                code.add("elect" + voteNumber + " = voting(votes" + voteNumber
                        + ");");
            }

        }

    }

    private BooleanExpListNode generateAST(String code) {
        FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(
                new ANTLRInputStream(code));
        CommonTokenStream ts = new CommonTokenStream(l);
        FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(
                ts);

        BooleanExpScope declaredVars = new BooleanExpScope();

        PreAndPostConditionsDescription.getSymbolicVariableList()
                .forEach((v) -> {
                    declaredVars.addTypeForId(v.getId(),
                            v.getInternalTypeContainer());
                });

        return translator.generateFromSyntaxTree(p.booleanExpList(),
                electionDescription.getInputType().getType(),
                electionDescription.getOutputType().getType(), declaredVars);
    }

    private void addPreferenceVotingArrayInitialisation(int voteNumber) {
        code.add(
                "for (unsigned int j_prime = 0; j_prime < counter_1; j_prime++) {");
        code.addTab();
        code.add(
                "assume (votes" + voteNumber + "[counter_0][counter_1] != votes"
                        + voteNumber + "[counter_0][j_prime]);");
        code.deleteTab();
        code.add("}");
    }

    private List<String> getVotingResultCode(int[][] votingData) {

        // compile the input data as an integer array for c
        // and save the lines in this list

        boolean twoDim = false;

        switch (electionDescription.getInputType().getInputID()) {
        case SINGLE_CHOICE:
            twoDim = false;
            break;
        case APPROVAL:
            twoDim = true;
            break;
        case WEIGHTED_APPROVAL:
            twoDim = true;
            break;
        case PREFERENCE:
            twoDim = true;
            break;

        default:
            ErrorForUserDisplayer.displayError(
                    "the current input type was not found. Please extend the methode \"getVotingResultCode\" in the class ElectionSimulation ");
            break;
        }

        List<String> dataAsArray = new ArrayList<String>();

        // first create the declaration of the array:
        String declaration = "";

        if (twoDim) {
            declaration = "int ORIG_VOTES[" + votingData.length + "]["
                    + votingData[0].length + "] = {";
        }
        else {
            declaration = "int ORIG_VOTES[" + votingData.length + "] = {";
        }
        dataAsArray.add(declaration);

        if (twoDim) { // we have a two dimensional variable from the get go
            for (int i = 0; i < votingData.length; i++) {
                String tmp = "";
                for (int j = 0; j < votingData[i].length; j++) {
                    if (j < (votingData[i].length - 1)) {
                        tmp = tmp + votingData[i][j] + ",";
                    }
                    else {
                        tmp = tmp + votingData[i][j];
                    }
                }

                tmp = "{" + tmp + "}";
                if (i < votingData.length - 1) {
                    dataAsArray.add(tmp + ",");
                }
                else {
                    dataAsArray.add(tmp); // the last entry doesn't need a
                                          // trailing comma
                }
            }
        }
        else { // we have to map the two dimensional array to an one
               // dimensional one
            for (int i = 0; i < votingData.length; i++) {
                int tmp = 0; // saves what this voter voted for
                int tmpSum = 0;
                for (int j = 0; j < votingData[i].length; j++) {
                    tmpSum += votingData[i][j];
                    if (votingData[i][j] != 0) {
                        tmp = j;
                    }
                }

                if (tmpSum == 0) {
                    if (i < votingData.length - 1) {
                        dataAsArray.add("C ,");
                    }
                    else {
                        dataAsArray.add("C");
                    }
                } else {

                    if (i < votingData.length - 1) {
                        dataAsArray.add(tmp + ",");
                    }
                    else {
                        dataAsArray.add("" + tmp);
                    }
                }
            }
        }

        dataAsArray.add("};"); // close the array declaration

        return dataAsArray;
    }

    private List<String> getLastResultCode(List<Long> origResult) {

        List<String> dataAsArray = new ArrayList<String>();

        // first create the declaration of the array:
        String declaration = "";

        declaration = "int ORIG_RESULT[" + origResult.size() + "] = {";

        dataAsArray.add(declaration);

        String tmp = ""; // saves the amount of votes this seat got
        for (int i = 0; i < origResult.size(); i++) {
            if (i < origResult.size() - 1) {
                tmp = tmp + origResult.get(i) + ",";
            }
            else {
                tmp = tmp + origResult.get(i);
            }
        }
        dataAsArray.add(tmp);

        dataAsArray.add("};");

        return dataAsArray;
    }

    private List<String> getLastResultCode(Long origResult) {

        List<String> dataAsArray = new ArrayList<String>();

        // first create the declaration of the array:
        String declaration = "";

        declaration = "int ORIG_RESULT = " + origResult + ";";

        dataAsArray.add(declaration);

        return dataAsArray;
    }
}