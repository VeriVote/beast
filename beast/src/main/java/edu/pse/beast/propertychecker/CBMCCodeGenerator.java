package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.RandomStringUtils;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpListNode;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.electionsimulator.ElectionSimulationData;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.FormalPropertySyntaxTreeToAstTranslator;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;
import edu.pse.beast.types.ComplexType;
import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.OutputType;

/**
 * This class creates the .c file which will be checked with CBMC. It generates
 * a main method (including the FormalProperty), important IncludingCode and the
 * votingMethod (the ElectionDescription).
 *
 * <p>TODO refactor this into multiple sub classes later on
 *
 * @author Niels Hanselmann
 */
public class CBMCCodeGenerator {
    /** The code. */
    private CodeArrayListBeautifier code;

    /** The election desc. */
    private final ElectionDescription electionDesc;

    /** The pre and post cond desc. */
    private final PreAndPostConditionsDescription preAndPostCondDesc;

    /** The translator. */
    private final FormalPropertySyntaxTreeToAstTranslator translator;

    /** The visitor. */
    private final CBMCCodeGenerationVisitor visitor;

    /** The number of times voted. */
    // this number should be the number of votes we want to perform
    private int numberOfTimesVoted;

    /** The margin. */
    private int margin;

    /** The orig result. */
    private ElectionSimulationData origResult;

    /**
     * After the build the code is fully generated and can be acquired by
     * getCode().
     *
     * @param electionDescription
     *            the election decription that holds the code that describes the
     *            voting method. that code will be merged with the generated
     *            code
     * @param propCondDesc
     *            the Descriptions that will be used to generate the C-Code for
     *            CBMC
     */
    public CBMCCodeGenerator(final ElectionDescription electionDescription,
                             final PreAndPostConditionsDescription propCondDesc) {
        this.translator = new FormalPropertySyntaxTreeToAstTranslator();
        this.electionDesc = electionDescription;
        this.preAndPostCondDesc = propCondDesc;
        code = new CodeArrayListBeautifier();
        electionDescription.getContainer();
        this.visitor = new CBMCCodeGenerationVisitor(
                electionDescription.getContainer());
        // new CCodeHelper();
        generateCodeCheck();
    }

    /**
     * After the build the code is fully generated and can be acquired by
     * getCode().
     *
     * @param electionDescription
     *            the electionDecription that holds the code that describes the
     *            voting method. that code will be merged with the generated
     *            code
     * @param propertyCondDesc
     *            the descriptions that will be used to generate the C-Code for
     *            CBMC
     * @param marginVal
     *            the margin
     * @param origResultData
     *            the original election result
     * @param votingData
     *            the voting data
     */
    public CBMCCodeGenerator(final ElectionDescription electionDescription,
                             final PreAndPostConditionsDescription propertyCondDesc,
                             final int marginVal,
                             final ElectionSimulationData origResultData,
                             final ElectionSimulationData votingData) {
        this.translator = new FormalPropertySyntaxTreeToAstTranslator();
        this.electionDesc = electionDescription;
        this.preAndPostCondDesc = propertyCondDesc;
        code = new CodeArrayListBeautifier();
        electionDescription.getContainer();
        this.visitor = new CBMCCodeGenerationVisitor(
                electionDescription.getContainer());
        // new CCodeHelper();
        this.margin = marginVal;
        this.origResult = origResultData;
        generateCodeMargin(votingData);
    }

    /**
     * Constructor when it will be used to get the code for a test.
     *
     * @param electionDescription
     *            the electionDecription that holds the code that describes the
     *            voting method. that code will be merged with the generated
     *            code
     * @param propertyCondDesc
     *            the descriptions that will be used to generate the C-Code for
     *            CBMC
     * @param votingData
     *            the voting data
     */
    public CBMCCodeGenerator(final ElectionDescription electionDescription,
                             final PreAndPostConditionsDescription propertyCondDesc,
                             final ElectionSimulationData votingData) {
        this.translator = new FormalPropertySyntaxTreeToAstTranslator();
        this.electionDesc = electionDescription;
        this.preAndPostCondDesc = propertyCondDesc;
        code = new CodeArrayListBeautifier();
        electionDescription.getContainer();
        this.visitor =
                new CBMCCodeGenerationVisitor(
                        electionDescription.getContainer()
                        );
        // new CCodeHelper();
        generateCodeTest(votingData);
    }

    /**
     * Gets the code.
     *
     * @return returns the generated code
     */
    public ArrayList<String> getCode() {
        return code.getCodeArrayList();
    }

    /**
     * Generate code check.
     */
    private void generateCodeCheck() {
        code = addHeader(code);
        addVoteSumFunc(false);
        addVoteSumFunc(true);
        addOtherFunctions();
        code.add("//Code of the user");
        ArrayList<String> electionDescriptionCode = new ArrayList<>();
        electionDescriptionCode.addAll(electionDesc.getComplexCode());
        code.addList(electionDescriptionCode);
        addMainMethod();
    }

    /**
     * We want to create code for a margin computation or just a test run.
     *
     * @param votingData
     *            the voting data
     */
    private void generateCodeMargin(final ElectionSimulationData votingData) {
        // add the header and the voting data
        addMarginHeaders(votingData);
        // add the code the user wrote (e.g the election function)
        code.addAll(GUIController.getController().getElectionDescription()
                .getComplexCode());
        // add the code which defines the votes
        String origVotes = electionDesc.getContainer().getInputStruct()
                .getStructAccess() + " "
                + UnifiedNameContainer.getOrigVotesName() + " = "
                + getVotingResultCode(
                        (CBMCResultValueWrapper) votingData.getValues())
                + ";";
        String sizeOfVote = "" + electionDesc.getContainer().getInputType()
                .getSizesInOrder(votingData.getVoters(),
                        votingData.getCandidates(), votingData.getSeats())
                .get(0);
        String origVotesSize = "int ORIG_VOTES_SIZE = " + sizeOfVote + ";";

        code.add(origVotesSize);

        code.add(origVotes);
        addMarginMainCheck(margin, origResult);
    }

    /**
     * We want to create code for a margin computation or just a test run.
     *
     * @param votingData
     *            the voting data
     */
    private void generateCodeTest(final ElectionSimulationData votingData) {
        // add the header and the voting data
        addMarginHeaders(votingData);
        // add the code the user wrote (e.g the election function)
        code.addAll(GUIController.getController().getElectionDescription()
                .getComplexCode());
        // add the code which defines the votes
        String origVotes = electionDesc.getContainer().getInputStruct()
                .getStructAccess() + " "
                + UnifiedNameContainer.getOrigVotesName() + " = "
                + getVotingResultCode(
                        (CBMCResultValueWrapper) votingData.getValues())
                + ";";
        code.add(origVotes);
        String sizeOfVote = "" + electionDesc.getContainer().getInputType()
                .getSizesInOrder(votingData.getVoters(),
                        votingData.getCandidates(), votingData.getSeats())
                .get(0);
        String origVotesSize = "int ORIG_VOTES_SIZE = " + sizeOfVote + ";";
        code.add(origVotesSize);
        addMarginMainTest(); // TODO add gcc ability here
    }

    /**
     * Adds the margin main check.
     *
     * @param marginVal
     *            the margin val
     * @param origData
     *            the orig data
     */
    private void addMarginMainCheck(final int marginVal,
            final ElectionSimulationData origData) {
        // we add the margin which will get computed by the model checker
        code.add("#ifndef MARGIN\n #define MARGIN " + marginVal + "\n #endif");
        // we also add the original result, which is calculated by compiling the
        // program and running it

        String origResultName = UnifiedNameContainer.getOrigResultName();
        electionDesc.getContainer().getOutputType().addLastResultAsCode(code,
                origResult, origResultName);
        // add the verify method:
        // taken and adjusted from the paper:
        // https://formal.iti.kit.edu/~beckert/pub/evoteid2016.pdf

        // add the main method
        code.add("int main() {");
        addMarginCompMethod(code, electionDesc.getContainer().getInputType(),
                electionDesc.getContainer().getOutputType(), origResultName);
        code.add("}");
    }

    /**
     * Adds the margin comp method.
     *
     * @param codeList
     *            the code list
     * @param inType
     *            the in type
     * @param outType
     *            the out type
     * @param origResultName
     *            the orig result name
     */
    private void addMarginCompMethod(final CodeArrayListBeautifier codeList,
                                     final InputType inType, final OutputType outType,
                                     final String origResultName) {
        codeList.add("//Verify for input");
        String voteName = UnifiedNameContainer.getNewVotesName();
        addMarginCompMethodInput(codeList, inType, voteName);
        codeList.add("//Verify for output");
        addMarginCompMethodOutput(codeList, outType, voteName, origResultName);
    }

    /**
     * Adds the margin comp method input.
     *
     * @param codeList
     *            the code list
     * @param inType
     *            the in type
     * @param voteName
     *            the vote name
     */
    private void addMarginCompMethodInput(final CodeArrayListBeautifier codeList,
                                          final InputType inType,
                                          final String voteName) {
        codeList.add("int total_diff = 0;");
        codeList.add("int pos_diff = 0;");
        String voteContainer = inType.getStruct().getStructAccess() + " "
                                + voteName + ";";
        codeList.add(voteContainer);

        // addInitialisedValue(voteName, inType,
        // electionDesc.getContainer().getInputStruct(),
        // inType.getMinimalValue(),
        // inType.getMaximalValue());

        // addConditionalValue(voteName, inType); //the votes had to be valid
        // before hand

        // List<String> tmploopVars = addNestedForLoopTop(code,
        // inType.getSizeOfDimensionsAsList(), new ArrayList<String>());

        // code.add(inType.setVoteValue(voteName,
        // electionDesc.getContainer().getNameContainer().getOrigVotesName(),
        // tmploopVars)); //set the previous votes to the new votes

        // addNestedForrLoopBot(code, inType.getAmountOfDimensions());
        List<String> loopVars =
                addNestedForLoopTop(codeList,
                                    inType.getSizeOfDimensionsAsList(),
                                    new ArrayList<String>());
        inType.flipVote(voteName, UnifiedNameContainer.getOrigVotesName(),
                        loopVars, codeList);

        // addConditionalValue(voteName, inType); //the votes have to be valid
        // afterwards
        addNestedForrLoopBot(codeList, inType.getAmountOfDimensions());

        // no more changes than margin allows
        codeList.add("assume(pos_diff <= MARGIN);");
        codeList.add("assume(total_diff == 0);");
    }

    /**
     * Adds the conditional value.
     *
     * @param voteName
     *            the vote name
     * @param inType
     *            the in type
     */
    private void addConditionalValue(final String voteName,
                                     final InputType inType) {
        inType.restrictVotes(voteName, code);
    }

    /**
     * Adds the margin comp method output.
     *
     * @param codeList
     *            the code list
     * @param outType
     *            the out type
     * @param newVotesName
     *            the new votes name
     * @param origResultName
     *            the orig result name
     */
    private void addMarginCompMethodOutput(final CodeArrayListBeautifier codeList,
                                           final OutputType outType,
                                           final String newVotesName,
                                           final String origResultName) {
        String resultName = UnifiedNameContainer.getNewResultName();
        String resultContainer = outType.getStruct().getStructAccess() + " "
                + resultName;
        String resultAssignment = resultContainer + " = "
                + UnifiedNameContainer.getVotingMethod() + "(ORIG_VOTES_SIZE,"
                + newVotesName + ");";
        codeList.add(resultAssignment);
        List<String> loopVars = addNestedForLoopTop(codeList,
                outType.getSizeOfDimensionsAsList(), new ArrayList<String>());
        String newResultAcc = outType.getFullVarAccess(resultName, loopVars);
        String origResultAcc = outType.getFullVarAccess(origResultName, loopVars);
        codeList.add("assert(" + newResultAcc + " == " + origResultAcc + ");");
        addNestedForrLoopBot(codeList, loopVars.size());
    }

    /**
     * TODO move to utility class.
     *
     * @param code
     *            the code beautifier it should be added to
     * @param dimensions
     *            the size of each dimension,
     * @param nameOfLoopVariables
     *            an empty, new list. Every new loop variable will be appended
     * @return the list
     */
    public static List<String> addNestedForLoopTop(final CodeArrayListBeautifier code,
                                                   final List<String> dimensions,
                                                   final List<String> nameOfLoopVariables) {
        if (dimensions.size() > 0) {
            String name = "loop_" + nameOfLoopVariables.size();
            name = code.getNotUsedVarName(name);
            nameOfLoopVariables.add(name);
            code.add("for (int " + name + " = 0; " + name + " < "
                    + dimensions.get(0) + "; " + name + "++) {");
            code.addTab();
            return addNestedForLoopTop(code,
                                       dimensions.subList(1, dimensions.size()),
                                       nameOfLoopVariables);
        }
        return nameOfLoopVariables;
    }

    /**
     * TODO move to utility class.
     *
     * @param code
     *            the code
     * @param dimensions
     *            the dimensions
     */
    public static void addNestedForrLoopBot(final CodeArrayListBeautifier code,
                                            final int dimensions) {
        for (int i = 0; i < dimensions; i++) {
            code.add("}");
        }
    }

    /**
     * Add the headers CBMC needs.
     *
     * @param votingData
     *            the voting data
     */
    private void addMarginHeaders(final ElectionSimulationData votingData) {
        code = addHeader(code);
        code.add("#ifndef " + UnifiedNameContainer.getVoter() + "\n #define "
                + UnifiedNameContainer.getVoter() + " " + votingData.getVoters()
                + "\n #endif");
        code.add("#ifndef " + UnifiedNameContainer.getCandidate()
                + " \n #define " + UnifiedNameContainer.getCandidate() + " "
                + votingData.getCandidates() + "\n #endif");
        code.add("#ifndef " + UnifiedNameContainer.getSeats() + "\n #define "
                + UnifiedNameContainer.getSeats() + " " + votingData.getSeats()
                + "\n #endif");
    }

    /**
     * Adds the margin main test.
     */
    private void addMarginMainTest() {
        // because we only have one vote, we hard-code the
        // value "one" here
        int voteNumber = 1;
        code = electionDesc.getContainer().getOutputType()
                .addMarginMainTest(code, voteNumber);
    }

    /**
     * Adds the vote sum func.
     *
     * @param unique
     *            the unique
     */
    private void addVoteSumFunc(final boolean unique) {
        String input = electionDesc.getContainer().getInputStruct()
                .getStructAccess() + " tmp_struct";
        code.add("unsigned int voteSumForCandidate" + (unique ? "Unique" : "")
                + "(INPUT, unsigned int amountVotes, unsigned int candidate) {"
                        .replace("INPUT", input));
        int dimensions = electionDesc.getContainer().getInputType()
                .getAmountOfDimensions();

        String[] sizes = electionDesc.getContainer().getInputType()
                .getSizeOfDimensions();
        sizes[0] = "amountVotes";
        String forLoopStart = "";
        List<String> loopVariables = generateLoopVariables(dimensions, "arr");
        for (int i = 0; i < dimensions; i++) { // add all needed loop headers
            forLoopStart += generateForLoopHeader(loopVariables.get(i),
                    sizes[i]);
        }
        String forLoopEnd = "";
        for (int i = 0; i < dimensions; i++) {
            forLoopEnd += "}"; // close the for loops
        }
        String access = "";
        for (int i = 0; i < dimensions; i++) {
            access += "[" + loopVariables.get(i) + "]";
        }
        String dataDef = electionDesc.getContainer().getInputType()
                .getDataTypeAndSign();
        String definition = dataDef + " arr" + electionDesc.getContainer()
                .getInputType().getDimensionDescriptor(true) + ";";
        code.add(definition);
        String assignment = forLoopStart + "arr" + access + " = "
                + " tmp_struct.arr" + access + ";" + forLoopEnd + "\n";
        code.add(assignment);
        code.add("unsigned int sum = 0;");
        code.add("for(unsigned int i = 0; i < amountVotes; i++) {");

        // add the specific code which differs for different input types
        electionDesc.getContainer().getInputType().addCodeForVoteSum(code,
                unique);
        code.add("}");
        code.add("return sum;");
        code.add("}");
    }

    /**
     * Other functions needed for the check.
     */
    private void addOtherFunctions() {
        addSplitArrayFunctions();
        addConcatenationFunctions();
        addPermutationFunctions();
        addIntersectFunction();
    }

    /**
     * Adds the intersect function.
     */
    private void addIntersectFunction() {
        if (electionDesc.getContainer().getOutputType()
                .getAmountOfDimensions() != 1
                || !electionDesc.getContainer().getOutputType()
                        .getSizeOfDimensions()[0].equals("C")) {
            return;
        }

        code.add(electionDesc.getContainer().getOutputStruct().getStructAccess()
                + " intersect("
                + electionDesc.getContainer().getOutputStruct()
                        .getStructAccess()
                + " one, " + electionDesc.getContainer().getOutputStruct()
                        .getStructAccess()
                + " two) {");
        code.add(electionDesc.getContainer().getOutputStruct().getStructAccess()
                + " toReturn;");
        code.add("  ");
        code.add("  for (int i = 0; i < C; i++) {");
        code.add("    toReturn.arr[i] = 0;");
        code.add("  }");
        code.add("  ");
        code.add("  for (int i = 0; i < C; i++) {");
        code.add("    if (one.arr[i] && two.arr[i]) {");
        code.add("      toReturn.arr[i] = 1;");
        code.add("    }");
        code.add("  }");
        code.add("  ");
        code.add("  return toReturn;");
        code.add("}");
    }

    /**
     * Adds the permutation functions.
     */
    private void addPermutationFunctions() {
        addPermutateOneFunction();
        addPermutateTwoFunction();
    }

    /**
     * Adds the permutate two function.
     */
    private void addPermutateTwoFunction() {
        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() != 2
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals("V")
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[1].equals("C")) {
            return;
        }
        String voteStruct = electionDesc.getContainer().getInputStruct()
                .getStructAccess();

        code.add(voteStruct + " permutateTwo(" + voteStruct + " votes, "
                + "unsigned int length) {");
        code.add(voteStruct + " sub_arr;");
        code.add("  ");
        code.add("unsigned int already_used_arr[V];");
        code.add("    ");
        code.add(
                "for(int i = 0; i < V; i++) { //set all to C in the beginning");
        code.add("for(int j = 0; j < C; j++) {");
        code.add("sub_arr.arr[i][j] = C;");
        code.add("}");
        code.add("}");
        code.add("");
        code.add("  ");
        code.add("for(int i = 0; i < length; i++) {");
        code.add("unsigned int new_index = nondet_uint();");
        code.add("assume((new_index >= 0) && (new_index < length));");
        code.add("");
        code.add("for(int j = 0; j < i; j++) {");
        code.add("assume(new_index != already_used_arr[j]);");
        code.add("}");
        code.add("    ");
        code.add("    already_used_arr[i] = new_index;");
        code.add("    ");
        code.add("    for(int j = 0; j < C; j++) {");
        code.add("      sub_arr.arr[new_index][j] = votes.arr[i][j];");
        code.add("    }");
        code.add("  }");
        code.add("  ");
        code.add(voteStruct + " toReturn;");
        code.add("    ");
        code.add("  for (int i = 0; i < V; i++) {");
        code.add("    for(int j = 0; j < C; j++) {");
        code.add("      toReturn.arr[i][j] = sub_arr.arr[i][j];");
        code.add("    }");
        code.add("  }");
        code.add("    ");
        code.add("  return toReturn;");
        code.add("}");
    }

    /**
     * Adds the permutate one function.
     */
    private void addPermutateOneFunction() {
        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() != 1
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals("V")) {
            return;
        }
        String voteStruct =
                electionDesc.getContainer().getInputStruct().getStructAccess();

        code.add(voteStruct + " permutateOne(" + voteStruct
                + " votes, unsigned int length) {");
        code.add(voteStruct + " sub_arr;");
        code.add("  ");
        code.add(" unsigned int already_used_arr[V];");
        code.add("    ");
        code.add(
                "  for(int i = 0; i < V; i++) { //set all to C in the beginning");
        code.add("    sub_arr.arr[i] = C;");
        code.add("  }");
        code.add("  ");
        code.add("  ");
        code.add("  for(int i = 0; i < length; i++) {");
        code.add("    unsigned int new_index = nondet_uint();");
        code.add("    assume((new_index >= 0) && (new_index < length));");
        code.add("    ");
        code.add("    for(int j = 0; j < i; j++) {");
        code.add("      assume(new_index != already_used_arr[j]);");
        code.add("    }");
        code.add("    ");
        code.add("    already_used_arr[i] = new_index;");
        code.add("    ");
        code.add("    sub_arr.arr[new_index] = votes.arr[i];");
        code.add("  }");
        code.add("  ");
        code.add(voteStruct + " toReturn;");
        code.add("    ");
        code.add("  for (int i = 0; i < V; i++) {");
        code.add("    toReturn.arr[i] = sub_arr.arr[i];");
        code.add("  }");
        code.add("    ");
        code.add("  return toReturn;");
        code.add("  ");
        code.add("}");
        code.add("");
    }

    /**
     * Adds the concatenation functions.
     */
    private void addConcatenationFunctions() {
        addConcatOneFunction();
        addConcatTwoFunction();
    }

    /**
     * Adds the concat two function.
     */
    private void addConcatTwoFunction() {
        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() != 2
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals("V")
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[1].equals("C")) {
            return;
        }
        String voteStruct = electionDesc.getContainer().getInputStruct()
                .getStructAccess();
        code.add(voteStruct + " concatTwo(" + voteStruct
                + " votesOne, unsigned int sizeOne, " + voteStruct
                + " votesTwo, unsigned int sizeTwo) {");
        code.add(voteStruct + " sub_arr;");
        code.add("  ");
        code.add(
                "  for(int i = 0; i < V; i++) { //set all to C in the beginning");
        code.add("    for(int j = 0; j < C; j++) {");
        code.add("      sub_arr.arr[i][j] = C;");
        code.add("    }");
        code.add("  }");
        code.add("  ");
        code.add(
                "  for(int i = 0; i < sizeOne; i++) { //limit the size to the upper bound V");
        code.add("    for(int j = 0; j < C; j++) {");
        code.add("      if (i < V) {");
        code.add("        sub_arr.arr[i][j] = votesOne.arr[i][j];");
        code.add("      }");
        code.add("    }");
        code.add("  }");
        code.add("  ");
        code.add(
                "  for(int i = 0; i < sizeTwo; i++) { //limit the size to the upper bound V");
        code.add("    for(int j = 0; j < C; j++) {");
        code.add("      if (sizeTwo + i < V) {");
        code.add("        sub_arr.arr[i][j] = votesTwo.arr[i][j];");
        code.add("      }");
        code.add("    }");
        code.add("  }");
        code.add("  ");
        code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                + " toReturn;");
        code.add("    ");
        code.add("  for (int i = 0; i < V; i++) {");
        code.add("    for (int j = 0; j < C; j++) {");
        code.add("      toReturn.arr[i][j] = sub_arr.arr[i][j];");
        code.add("    }");
        code.add("  }");
        code.add("    ");
        code.add("  return toReturn;");
        code.add("}");
    }

    /**
     * Adds the concat one function.
     */
    private void addConcatOneFunction() {
        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() != 1
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals("V")) {
            return;
        }
        String voteStruct = electionDesc.getContainer().getInputStruct()
                .getStructAccess();
        code.add(voteStruct + " concatOne(" + voteStruct
                + " votesOne, unsigned int sizeOne, " + voteStruct
                + " votesTwo, unsigned int sizeTwo) {");
        code.add(voteStruct + " sub_arr;");
        code.add("  ");
        code.add(
                "  for(int i = 0; i < V; i++) { //set all to C in the beginning");
        code.add("    sub_arr.arr[i] = C;");
        code.add("  }");
        code.add("  ");
        code.add(
                "  for(int i = 0; i < sizeOne; i++) { //limit the size to the upper bound V");
        code.add("    if (i < V) {");
        code.add("      sub_arr.arr[i] = votesOne.arr[i];");
        code.add("    }");
        code.add("  }");
        code.add("  ");
        code.add(
                "  for(int i = 0; i < sizeTwo; i++) { //limit the size to the upper bound V");
        code.add("    if (sizeOne + i < V) {");
        code.add("      sub_arr.arr[sizeOne + i] = votesTwo.arr[i];");
        code.add("    }");
        code.add("  }");
        code.add("  ");
        code.add(voteStruct + " toReturn;");
        code.add("    ");
        code.add("  for (int i = 0; i < V; i++) {");
        code.add("    toReturn.arr[i] = sub_arr.arr[i];");
        code.add("  }");
        code.add("    ");
        code.add("  return toReturn;");
        code.add("  ");
        code.add("}");
        code.add("");
    }

    /**
     * Adds the split array functions.
     */
    private void addSplitArrayFunctions() {
        code.add("//split array");
        code.add("");
        code.add("//get splits cuts through an array of size max");
        code.add(
                "unsigned int *getRandomSplitLines(unsigned int splits, unsigned int max) {");
        code.add(
                "  unsigned int *split_arr = malloc(splits * sizeof(*split_arr));");
        code.add("  ");
        code.add("  if (splits == 1) {");
        code.add("    unsigned int next_split = nondet_uint();");
        code.add("    assume(next_split >= 0);");
        code.add("    assume(next_split <= (max / 2));");
        code.add("    ");
        code.add("    split_arr[0] = next_split;");
        code.add("  } else {");
        code.add("    unsigned int last_split = 0;");
        code.add("    for (int i = 0; i < splits; i++) {");
        code.add("      ");
        code.add("      unsigned int next_split = nondet_uint();");
        code.add("      assume(next_split >= last_split);");
        code.add("      assume(next_split <= max);");
        code.add("      ");
        code.add("      ");
        code.add("      ");
        code.add("      split_arr[i] = next_split;");
        code.add("      last_split = next_split;");
        code.add("    }");
        code.add("    ");
        code.add("    unsigned int *splitLines;");
        code.add("    splitLines = split_arr;");
        code.add("    ");
        code.add("    for (int i = 0; i < splits; i++) {");
        code.add("      unsigned int debugrandom = splitLines[i];");
        code.add("    }");
        code.add("  }");
        code.add("  return split_arr;");
        code.add("}");
        code.add("");
        code.add("//start is inclusive, stop is exclusive");
        code.add("");
        String voteStruct = electionDesc.getContainer().getInputStruct()
                .getStructAccess();

        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() == 1
                && electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals("V")) {

            code.add(voteStruct + " split(" + voteStruct + " votes, "
                    + "unsigned int start, unsigned int stop) {");
            code.add(voteStruct + " sub_arr;");
            code.add("  ");
            code.add(
                    "  for(int i = 0; i < V; i++) { //set all to C in the beginning");
            code.add("    sub_arr.arr[i] = C;");
            code.add("  }");
            code.add("  ");
            code.add("  if(start == stop) { //the sub array should be empty");
            code.add(electionDesc.getContainer().getInputStruct()
                    .getStructAccess() + " toReturn;");
            code.add("    ");
            code.add("    for (int i = 0; i < V; i++) {");
            code.add("      toReturn.arr[i] = sub_arr.arr[i];");
            code.add("    }");
            code.add("    ");
            code.add("    return toReturn;");
            code.add("  } else {");
            code.add("");
            code.add("    for (int i = 0; i < V; i++) {");
            code.add("      if ((i >= start) && (i < stop)) {");
            code.add("        sub_arr.arr[i - start] = votes.arr[i];");
            code.add("      }");
            code.add("    }");
            code.add("  ");
            code.add("    ");
            code.add(electionDesc.getContainer().getInputStruct()
                    .getStructAccess() + " toReturn;");
            code.add("    ");
            code.add("    for (int i = 0; i < V; i++) {");
            code.add("      toReturn.arr[i] = sub_arr.arr[i];");
            code.add("    }");
            code.add("    ");
            code.add("    return toReturn;");
            code.add("  }");
            code.add("}");
            code.add("");
        }

        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() == 2
                && electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals("V")
                && electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[1].equals("C")) {

            code.add("//start is inclusive, stop is exclusive");
            code.add("//used for 2 dim arrays");
            code.add(voteStruct + " split(" + voteStruct + " votes, "
                    + "unsigned int start, unsigned int stop) {");
            code.add(voteStruct + " sub_arr;");
            code.add("  ");
            code.add(
                    "  for(int i = 0; i < V; i++) { //set all to C in the beginning");
            code.add("    for(int j = 0; j < C; j++) {");
            code.add("      sub_arr.arr[i][j] = C;");
            code.add("    }");
            code.add("  }");
            code.add("  ");
            code.add("  if(start == stop) { //the sub array should be empty");
            code.add(electionDesc.getContainer().getInputStruct()
                    .getStructAccess() + " toReturn;");
            code.add("    ");
            code.add("    for (int i = 0; i < V; i++) {");
            code.add("      for(int j = 0; j < V; j++) {");
            code.add("        toReturn.arr[i][j] = sub_arr.arr[i][j];");
            code.add("      }");
            code.add("    }");
            code.add("    ");
            code.add("    return toReturn;");
            code.add("  } else {");
            code.add("");
            code.add("    for (int i = 0; i < V; i++) {");
            code.add("      if ((i >= start) && (i < stop)) {");
            code.add("        for(int j = 0; j < C; j++) {");
            code.add("          sub_arr.arr[i - start][j] = votes.arr[i][j];");
            code.add("        }");
            code.add("      }");
            code.add("    }");
            code.add("    ");
            code.add(electionDesc.getContainer().getInputStruct()
                    .getStructAccess() + " toReturn;");
            code.add("    ");
            code.add("    for (int i = 0; i < V; i++) {");
            code.add("      for(int j = 0; j < V; j++) {");
            code.add("        toReturn.arr[i][j] = sub_arr.arr[i][j];");
            code.add("      }");
            code.add("    }");
            code.add("    ");
            code.add("    return toReturn;");
            code.add("  }");
            code.add("}");
            code.add("");
        }
    }

    /**
     * Writes all lines of the header.
     *
     * @param codeLst
     *            the list in which the header should be written
     * @return the finished header
     */
    public CodeArrayListBeautifier addHeader(final CodeArrayListBeautifier codeLst) {
        codeLst.add("#include <stdlib.h>");
        codeLst.add("#include <stdint.h>");
        codeLst.add("#include <assert.h>");
        codeLst.add("");
        codeLst.add("unsigned int nondet_uint();");
        codeLst.add("int nondet_int();");
        codeLst.add("");
        codeLst.add("#define assert2(x, y) __CPROVER_assert(x, y)");
        codeLst.add("#define assume(x) __CPROVER_assume(x)");
        codeLst.add("");
        codeLst.addAll(Arrays.asList(electionDesc.getContainer()
                .getStructDefinitions().split("\\n")));
        return codeLst;
    }

    /**
     * Adds the main method the main method declares the boolean expression. In
     * the main method the voting method is called.
     */
    private void addMainMethod() {
        code.add("int main(int argc, char *argv[]) {");
        code.addTab();
        // generating the pre and post AbstractSyntaxTrees
        BooleanExpListNode preAST = generateAST(
                preAndPostCondDesc.getPreConditionsDescription().getCode());
        BooleanExpListNode postAST = generateAST(
                preAndPostCondDesc.getPostConditionsDescription().getCode());
        initializeNumberOfTimesVoted(preAST, postAST);
        // init all voting vars for the voters
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add("unsigned int " + UnifiedNameContainer.getVoter() + i
                    + " = " + UnifiedNameContainer.getVoter() + ";");
        }
        // init all voting vars for the candidates
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add("unsigned int " + UnifiedNameContainer.getCandidate() + i
                    + " = " + UnifiedNameContainer.getCandidate() + ";");
        }
        // init all voting vars for the seats
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add("unsigned int " + UnifiedNameContainer.getSeats() + i
                    + " = " + UnifiedNameContainer.getSeats() + ";");
        }
        List<String> boundedVars = preAndPostCondDesc.getBoundedVarDescription()
                .getCodeAsList();
        code.addList(boundedVars);
        // first the Variables have to be Initialized
        addSymbVarInitialisation();

        for (int voteNumber = 1; voteNumber <= numberOfTimesVoted; voteNumber++) {
            String minV = "" + electionDesc.getContainer().getInputType()
                    .getMinimalValue();
            if (electionDesc.getContainer().getInputType()
                    .hasVariableAsMinValue()) {
                minV += voteNumber;
            }
            String maxV = "" + electionDesc.getContainer().getInputType()
                    .getMaximalValue();
            if (electionDesc.getContainer().getInputType()
                    .hasVariableAsMaxValue()) {
                maxV += voteNumber;
            }
            code.add("//init for election: " + voteNumber);
            addInitialisedValue(
                    UnifiedNameContainer.getVotingArray() + voteNumber,
                    electionDesc.getContainer().getInputType(),
                    electionDesc.getContainer().getInputStruct(), minV, maxV);
        }

        // the the PreProperties must be defined
        addPreProperties(preAST);
        // now hold all the elections
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add("//election number: " + i);
            String sizeOfVotes = UnifiedNameContainer.getVoter() + i;
            String electX = electionDesc.getContainer().getOutputStruct()
                    .getStructAccess() + " elect" + i + " = "
                    + UnifiedNameContainer.getVotingMethod() + "(" + sizeOfVotes
                    + ", votes" + i + ");";
            code.add(electX);
        }
        // now the Post Properties can be checked
        addPostProperties(postAST);
        code.deleteTab();
        code.add("}");
    }

    /**
     * This should be used to create the VarInitialisation within the main
     * method.
     */
    private void addSymbVarInitialisation() {
        List<SymbolicVariable> symbolicVariableList =
                preAndPostCondDesc.getSymbolicVariablesAsList();
        code.add("//Symbolic Variables initialisation");
        symbolicVariableList.forEach(symbVar -> {
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
                    // a seat is a also an unsigned int.
                    // The return of a voting method (an Array) gives the
                    // elected
                    // candidate(value) of the seat(id)
                    code.add("unsigned int " + id + " = nondet_uint();");
                    // there are S seats. From 0 to S-1
                    code.add("assume(0 <= " + id + " && " + id + " < S);");
                    break;
                case APPROVAL:
                    break;
                case WEIGHTED_APPROVAL:
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
     * This adds the Code of the PreProperties. It uses a visitor which it
     * creates.
     *
     * @param preAST
     *            the pre AST
     */
    private void addPreProperties(final BooleanExpListNode preAST) {
        code.add("");
        code.add("//preproperties ");
        code.add("");
        visitor.setToPreConditionMode();
        preAST.getBooleanExpressions().forEach(booleanExpressionLists -> {
            booleanExpressionLists.forEach(booleanNode -> {
                code.addList(visitor.generateCode(booleanNode));
            });
        });
    }

    /**
     * this adds the Code of the PostProperties. It uses a Visitor it creates.
     *
     * @param postAST
     *            the post AST
     */
    private void addPostProperties(final BooleanExpListNode postAST) {
        code.add("");
        code.add("//postproperties ");
        code.add("");
        visitor.setToPostConditionMode();
        postAST.getBooleanExpressions().forEach(booleanExpressionLists -> {
            booleanExpressionLists.forEach(booleanNode -> {
                code.addList(visitor.generateCode(booleanNode));
            });
        });
    }

    /**
     * Report unsupported type.
     *
     * @param id
     *            the id
     */
    private void reportUnsupportedType(final String id) {
        ErrorLogger.log("Der Typ der symbolischen Variable " + id
                        + " wird nicht unterstützt.");
    }

    /**
     * Initialize number of times voted.
     *
     * @param preAST
     *            the pre AST
     * @param postAST
     *            the post AST
     */
    private void initializeNumberOfTimesVoted(final BooleanExpListNode preAST,
                                              final BooleanExpListNode postAST) {
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

    /**
     * Adds a value of the fitting size as described.
     *
     * @param valueName
     *            the value name
     * @param inOutType
     *            the in out type
     * @param complexType
     *            the complex type
     * @param minValue
     *            the min value
     * @param maxValue
     *            the max value
     */
    private void addInitialisedValue(final String valueName,
                                     final InOutType inOutType,
                                     final ComplexType complexType,
                                     final String minValue,
                                     final String maxValue) {
        code.add("// init of variable " + valueName);
        String declaration =
                complexType.getStructAccess() + " " + valueName + ";";
        code.add(declaration);
        List<String> loopVariables =
                addNestedForLoopTop(this.code,
                                    inOutType.getSizeOfDimensionsAsList(),
                                    new ArrayList<String>());
        String assignment =
                valueName + "."
                + UnifiedNameContainer.getStructValueName();

        for (int i = 0; i < inOutType.getAmountOfDimensions(); i++) {
            assignment += "[" + loopVariables.get(i) + "]";
        }
        code.add(assignment + " = nondet_uint();");
        code.add("assume((" + minValue + " <= " + assignment + ") && ("
                + assignment + " <= " + maxValue + "));");
        inOutType.addExtraCodeAtEndOfCodeInit(code, valueName, loopVariables);
        addNestedForrLoopBot(this.code, inOutType.getAmountOfDimensions());
    }

    /**
     * Generate AST.
     *
     * @param codeString
     *            the code string
     * @return the boolean exp list node
     */
    private BooleanExpListNode generateAST(final String codeString) {
        FormalPropertyDescriptionLexer l =
                new FormalPropertyDescriptionLexer(CharStreams.fromString(codeString));
        CommonTokenStream ts = new CommonTokenStream(l);
        FormalPropertyDescriptionParser p =
                new FormalPropertyDescriptionParser(ts);
        BooleanExpScope declaredVars = new BooleanExpScope();
        preAndPostCondDesc.getSymbolicVariablesAsList().forEach(v -> {
            declaredVars.addTypeForId(v.getId(), v.getInternalTypeContainer());
        });
        return translator.generateFromSyntaxTree(
                p.booleanExpList(),
                electionDesc.getContainer().getInputType(),
                electionDesc.getContainer().getOutputType(), declaredVars
                );
    }

    /**
     * Gets the voting result code.
     *
     * @param votingData
     *            the voting data
     * @return the voting result code
     */
    private String getVotingResultCode(final CBMCResultValueWrapper votingData) {
        return electionDesc.getContainer().getInputType()
                .getVotingResultCode(votingData);
    }

    /**
     * Generate loop variables.
     *
     * @param dimensions
     *            the dimensions
     * @param variableName
     *            the variable name
     * @return the list
     */
    private List<String> generateLoopVariables(final int dimensions,
                                               final String variableName) {
        List<String> generatedVariables = new ArrayList<String>(dimensions);
        int currentIndex = 0;
        // use i as the default name for a loop
        String defaultName = "loop_index_";
        for (int i = 0; i < dimensions; i++) {
            String varName = defaultName + currentIndex;
            boolean duplicate = true;
            int length = 1;
            while (duplicate) {
                if (code.contains(varName) || variableName.equals(varName)) {
                    varName = generateRandomString(length) + "_" + currentIndex;
                    // increase the length in case all words from that
                    // length are already taken
                    length++;
                } else {
                    duplicate = false;
                }
            }
            generatedVariables.add(varName);
            currentIndex++;
        }
        return generatedVariables;
    }

    /**
     * Generate random string.
     *
     * @param length
     *            the length
     * @return the string
     */
    private String generateRandomString(final int length) {
        return RandomStringUtils.random(length, true, false);
    }

    /**
     * Generate for loop header.
     *
     * @param indexName
     *            the index name
     * @param maxSize
     *            the max size
     * @return the string
     */
    private String generateForLoopHeader(final String indexName,
                                         final String maxSize) {
        return "for (unsigned int " + indexName + " = 0; " + indexName + " < "
                + maxSize + "; " + indexName + "++ ) { ";
    }
}
