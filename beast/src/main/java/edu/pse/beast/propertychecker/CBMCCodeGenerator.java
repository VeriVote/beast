package edu.pse.beast.propertychecker;

import static edu.pse.beast.toolbox.CCodeHelper.arrAcc;
import static edu.pse.beast.toolbox.CCodeHelper.arrAccess;
import static edu.pse.beast.toolbox.CCodeHelper.conjunct;
import static edu.pse.beast.toolbox.CCodeHelper.eq;
import static edu.pse.beast.toolbox.CCodeHelper.forLoopHeaderCode;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.include;
import static edu.pse.beast.toolbox.CCodeHelper.leq;
import static edu.pse.beast.toolbox.CCodeHelper.lt;
import static edu.pse.beast.toolbox.CCodeHelper.neq;
import static edu.pse.beast.toolbox.CCodeHelper.one;
import static edu.pse.beast.toolbox.CCodeHelper.unsignedIntVar;
import static edu.pse.beast.toolbox.CCodeHelper.varAddCode;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;
import static edu.pse.beast.toolbox.CCodeHelper.varEqualsCode;
import static edu.pse.beast.toolbox.CCodeHelper.varSubtractCode;
import static edu.pse.beast.toolbox.CCodeHelper.zero;

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
import edu.pse.beast.toolbox.CCodeHelper;
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

    // This number should be the number of votes we want to perform
    /** The number of times voted. */
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
        code.add("// Code of the user");
        ArrayList<String> electionDescriptionCode = new ArrayList<String>();
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
        // Add the header and the voting data
        addMarginHeaders(votingData);
        // Add the code the user wrote (e.g the election function)
        code.addAll(GUIController.getController().getElectionDescription()
                .getComplexCode());
        // Add the code which defines the votes
        String origVotes =
                varAssignCode(electionDesc.getContainer().getInputStruct().getStructAccess()
                                + UnifiedNameContainer.getOrigVotesName(),
                              getVotingResultCode((CBMCResultValueWrapper)
                                                      votingData.getValues()))
                + CCodeHelper.SEMICOLON;
        String sizeOfVote = "" + electionDesc.getContainer().getInputType()
                .getSizesInOrder(votingData.getVoters(),
                        votingData.getCandidates(), votingData.getSeats())
                .get(0);
        String origVotesSize =
                varEqualsCode("ORIG_VOTES_SIZE") + sizeOfVote + CCodeHelper.SEMICOLON;
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
        String origVotes =
                varAssignCode(electionDesc.getContainer().getInputStruct().getStructAccess()
                                + CCodeHelper.BLANK + UnifiedNameContainer.getOrigVotesName(),
                              getVotingResultCode((CBMCResultValueWrapper)
                                                      votingData.getValues()))
                + CCodeHelper.SEMICOLON;
        code.add(origVotes);
        String sizeOfVote = "" + electionDesc.getContainer().getInputType()
                .getSizesInOrder(votingData.getVoters(),
                        votingData.getCandidates(), votingData.getSeats())
                .get(0);
        String origVotesSize =
                varEqualsCode("ORIG_VOTES_SIZE") + sizeOfVote + CCodeHelper.SEMICOLON;
        code.add(origVotesSize);
        addMarginMainTest(); // TODO Add gcc ability here
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
        // We add the margin which will get computed by the model checker.
        code.add("#ifndef MARGIN\n #define MARGIN " + marginVal + "\n #endif");
        // We also add the original result, which is calculated by compiling the
        // program and running it.

        String origResultName = UnifiedNameContainer.getOrigResultName();
        electionDesc.getContainer().getOutputType()
            .addLastResultAsCode(code, origResult, origResultName);
        // Add the verify method:
        // Taken and adjusted from the paper:
        // https://formal.iti.kit.edu/~beckert/pub/evoteid2016.pdf

        // Add the main method.
        code.add(CCodeHelper.INT + CCodeHelper.BLANK
                + functionCode("main") + CCodeHelper.BLANK
                + CCodeHelper.OPENING_PARENTHESES);
        addMarginCompMethod(code, electionDesc.getContainer().getInputType(),
                electionDesc.getContainer().getOutputType(), origResultName);
        code.add(CCodeHelper.CLOSING_BRACES);
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
        codeList.add("// Verify for input");
        String voteName = UnifiedNameContainer.getNewVotesName();
        addMarginCompMethodInput(codeList, inType, voteName);
        codeList.add("// Verify for output");
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
        codeList.add(varAssignCode(CCodeHelper.INT + CCodeHelper.BLANK + "total_diff",
                                   zero())
                + CCodeHelper.SEMICOLON);
        codeList.add(varAssignCode(CCodeHelper.INT + CCodeHelper.BLANK + "pos_diff",
                                   zero())
                + CCodeHelper.SEMICOLON);
        String voteContainer = inType.getStruct().getStructAccess() + CCodeHelper.BLANK
                                + voteName + CCodeHelper.SEMICOLON;
        codeList.add(voteContainer);

        // addInitialisedValue(voteName, inType,
        //                     electionDesc.getContainer().getInputStruct(),
        //                     inType.getMinimalValue(),
        //                     inType.getMaximalValue());

        // addConditionalValue(voteName, inType); // The votes had to be valid
        //                                           beforehand

        // List<String> tmploopVars =
        //     addNestedForLoopTop(code, inType.getSizeOfDimensionsAsList(),
        //                         new ArrayList<String>());

        // code.add(inType.setVoteValue(voteName,
        //                              electionDesc.getContainer()
        //                                  .getNameContainer().getOrigVotesName(),
        //                              tmploopVars));
        // // Set the previous votes to the new votes.

        // addNestedForrLoopBot(code, inType.getAmountOfDimensions());
        List<String> loopVars =
                addNestedForLoopTop(codeList,
                                    inType.getSizeOfDimensionsAsList(),
                                    new ArrayList<String>());
        inType.flipVote(voteName, UnifiedNameContainer.getOrigVotesName(),
                        loopVars, codeList);

        // addConditionalValue(voteName, inType);
        // The votes have to be valid afterwards
        addNestedForrLoopBot(codeList, inType.getAmountOfDimensions());

        // No more changes than margin allows
        codeList.add(functionCode("assume", leq("pos_diff", "MARGIN"))
                    + CCodeHelper.SEMICOLON);
        codeList.add(functionCode("assume", eq("total_diff", zero()))
                    + CCodeHelper.SEMICOLON);
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
        String resultContainer = outType.getStruct().getStructAccess() + CCodeHelper.BLANK
                + resultName;
        String resultAssignment =
                varAssignCode(resultContainer,
                              functionCode(UnifiedNameContainer.getVotingMethod(),
                                           "ORIG_VOTES_SIZE", newVotesName))
                + CCodeHelper.SEMICOLON;
        codeList.add(resultAssignment);
        List<String> loopVars = addNestedForLoopTop(codeList,
                                                    outType.getSizeOfDimensionsAsList(),
                                                    new ArrayList<String>());
        String newResultAcc = outType.getFullVarAccess(resultName, loopVars);
        String origResultAcc = outType.getFullVarAccess(origResultName, loopVars);
        codeList.add(functionCode("assert", eq(newResultAcc, origResultAcc))
                    + CCodeHelper.SEMICOLON);
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
            code.add(forLoopHeaderCode(name, CCodeHelper.LT_SIGN, dimensions.get(0)));
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
            code.add(CCodeHelper.CLOSING_BRACES);
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
                + UnifiedNameContainer.getVoter() + CCodeHelper.BLANK + votingData.getVoters()
                + "\n #endif");
        code.add("#ifndef " + UnifiedNameContainer.getCandidate()
                + " \n #define " + UnifiedNameContainer.getCandidate() + CCodeHelper.BLANK
                + votingData.getCandidates() + "\n #endif");
        code.add("#ifndef " + UnifiedNameContainer.getSeats() + "\n #define "
                + UnifiedNameContainer.getSeats() + CCodeHelper.BLANK + votingData.getSeats()
                + "\n #endif");
    }

    /**
     * Adds the margin main test.
     */
    private void addMarginMainTest() {
        // Since we only have one vote, we hard-code the
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
                .getStructAccess() + CCodeHelper.BLANK + "tmp_struct";
        code.add(
            unsignedIntVar(
                functionCode("voteSumForCandidate" + (unique ? "Unique" : ""),
                             "INPUT".replace("INPUT", input),
                             unsignedIntVar("amountVotes"),
                             unsignedIntVar("candidate"))
            )   + CCodeHelper.BLANK
                + CCodeHelper.OPENING_BRACES);
        int dimensions = electionDesc.getContainer().getInputType()
                .getAmountOfDimensions();

        String[] sizes = electionDesc.getContainer().getInputType()
                .getSizeOfDimensions();
        sizes[0] = "amountVotes";
        String forLoopStart = "";
        List<String> loopVariables = generateLoopVariables(dimensions, "arr");
        for (int i = 0; i < dimensions; i++) { // Add all needed loop headers
            forLoopStart += generateForLoopHeader(loopVariables.get(i),
                                                  sizes[i]);
        }
        String forLoopEnd = "";
        for (int i = 0; i < dimensions; i++) {
            forLoopEnd += CCodeHelper.CLOSING_BRACES; // Close the for-loops
        }
        String access = "";
        for (int i = 0; i < dimensions; i++) {
            access += arrAcc(loopVariables.get(i));
        }
        String dataDef = electionDesc.getContainer().getInputType()
                .getDataTypeAndSign();
        String definition = dataDef + CCodeHelper.BLANK + "arr" + electionDesc.getContainer()
                .getInputType().getDimensionDescriptor(true) + CCodeHelper.SEMICOLON;
        code.add(definition);
        String assignment =
                forLoopStart
                + varAssignCode("arr" + access, CCodeHelper.BLANK + "tmp_struct.arr" + access)
                + CCodeHelper.SEMICOLON
                + forLoopEnd + "\n";
        code.add(assignment);
        code.add(varEqualsCode("sum") + zero() + CCodeHelper.SEMICOLON);
        code.add(forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "amountVotes"));

        // Add the specific code which differs for different input types
        electionDesc.getContainer().getInputType().addCodeForVoteSum(code,
                                                                     unique);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add(CCodeHelper.RETURN + CCodeHelper.BLANK + "sum" + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
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
                + CCodeHelper.BLANK
                + functionCode("intersect",
                        electionDesc.getContainer().getOutputStruct().getStructAccess()
                            + CCodeHelper.BLANK + "one",
                        electionDesc.getContainer().getOutputStruct().getStructAccess()
                            + CCodeHelper.BLANK + "two")
                + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
        code.add(electionDesc.getContainer().getOutputStruct().getStructAccess()
                + CCodeHelper.BLANK + "toReturn" + CCodeHelper.SEMICOLON);
        code.add("  ");
        code.add("  " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "C"));
        code.add("    "
                + varAssignCode("toReturn.arr[i]", zero())
                + CCodeHelper.SEMICOLON);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add("  " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "C"));
        code.add("    "
                + functionCode(CCodeHelper.IF, conjunct("one.arr[i]", "two.arr[i]"))
                + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
        code.add("      "
                + varAssignCode("toReturn.arr[i]", one())
                + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add("  " + CCodeHelper.RETURN + CCodeHelper.BLANK
                + "toReturn" + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
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

        code.add(voteStruct + CCodeHelper.BLANK
                + functionCode("permutateTwo", voteStruct, "votes",
                               unsignedIntVar("length"))
                + CCodeHelper.BLANK
                + CCodeHelper.OPENING_BRACES);
        code.add(voteStruct + CCodeHelper.BLANK + "sub_arr" + CCodeHelper.SEMICOLON);
        code.add("  ");
        code.add(unsignedIntVar(arrAccess("already_used_arr", "V"))
                + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add(forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V")
                + " // Set all to C in the beginning");
        code.add(forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "C"));
        code.add(varAssignCode("sub_arr.arr[i][j]", "C")
                + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add();
        code.add("  ");
        code.add(forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "length"));
        code.add(varAssignCode(unsignedIntVar("new_index"),
                               functionCode("nondet_uint"))
                + CCodeHelper.SEMICOLON);
        code.add(functionCode("assume", conjunct(leq(zero(), "new_index"),
                                                 lt("new_index", "length")))
                + CCodeHelper.SEMICOLON);
        code.add();
        code.add(forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "i"));
        code.add(functionCode("assume",
                              neq("new_index", "already_used_arr[j]"))
                + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add("    ");
        code.add("    " + varAssignCode("already_used_arr[i]", "new_index")
                + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("    " + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "C"));
        code.add("      "
                + varAssignCode("sub_arr.arr[new_index][j]",
                                "votes.arr[i][j]")
                + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add(voteStruct + CCodeHelper.BLANK + "toReturn" + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
        code.add("    "
                + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "C"));
        code.add("      "
                + varAssignCode("toReturn.arr[i][j]",
                                "sub_arr.arr[i][j]")
                + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("    ");
        code.add("  " + CCodeHelper.RETURN + CCodeHelper.BLANK
                + "toReturn" + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
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

        code.add(voteStruct + CCodeHelper.BLANK
                + functionCode("permutateOne",
                               voteStruct + CCodeHelper.BLANK + "votes",
                               unsignedIntVar("length"))
                + CCodeHelper.BLANK
                + CCodeHelper.OPENING_BRACES);
        code.add(voteStruct + CCodeHelper.BLANK + "sub_arr" + CCodeHelper.SEMICOLON);
        code.add("  ");
        code.add(" " + unsignedIntVar("already_used_arr[V]")
                + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V")
                + " // Set all to C in the beginning");
        code.add("    "
                + varAssignCode("sub_arr.arr[i]", "C")
                + CCodeHelper.SEMICOLON);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add("  ");
        code.add("  " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "length"));
        code.add("    "
                + varEqualsCode("new_index")
                + functionCode("nondet_uint")
                + CCodeHelper.SEMICOLON);
        code.add("    "
                + functionCode("assume", conjunct("(" + leq(zero(), "new_index") + ")",
                                                  "(" + lt("new_index", "length") + ")"))
                + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("    "
                + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "i"));
        code.add("      "
                + functionCode("assume", neq("new_index", "already_used_arr[j]"))
                + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("    ");
        code.add("    " + varAssignCode("already_used_arr[i]", "new_index")
               + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("    " + varAssignCode("sub_arr.arr[new_index]", "votes.arr[i]")
               + CCodeHelper.SEMICOLON);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add(voteStruct + CCodeHelper.BLANK + "toReturn" + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
        code.add("    " + varAssignCode("toReturn.arr[i]", "sub_arr.arr[i]")
                + CCodeHelper.SEMICOLON);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("    ");
        code.add("  " + CCodeHelper.RETURN + CCodeHelper.BLANK
                + "toReturn" + CCodeHelper.SEMICOLON);
        code.add("  ");
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add();
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
        code.add(voteStruct + CCodeHelper.BLANK
                + functionCode("concatTwo",
                               voteStruct + CCodeHelper.BLANK + "votesOne",
                               unsignedIntVar("sizeOne"),
                               voteStruct + CCodeHelper.BLANK + "votesTwo",
                               unsignedIntVar("sizeTwo"))
                + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
        code.add(voteStruct + CCodeHelper.BLANK + "sub_arr" + CCodeHelper.SEMICOLON);
        code.add("  ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V")
                + " // Set all to C in the beginning");
        code.add("    "
                + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "C"));
        code.add("      "
                + varAssignCode("sub_arr.arr[i][j]", "C")
                + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "sizeOne")
                + " // Limit the size to the upper bound V");
        code.add("    "
                + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "C"));
        code.add("      "
                + functionCode(CCodeHelper.IF, lt("i", "V"))
                + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
        code.add("        "
                + varAssignCode("sub_arr.arr[i][j]", "votesOne.arr[i][j]")
                + CCodeHelper.SEMICOLON);
        code.add("      " + CCodeHelper.CLOSING_BRACES);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "sizeTwo")
                + " // Limit the size to the upper bound V");
        code.add("    "
                + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "C"));
        code.add("      "
                + functionCode(CCodeHelper.IF,
                               lt(varAddCode("sizeTwo", "i"), "V"))
                + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
        code.add("        " + varAssignCode("sub_arr.arr[i][j]", "votesTwo.arr[i][j]")
                + CCodeHelper.SEMICOLON);
        code.add("      " + CCodeHelper.CLOSING_BRACES);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                + CCodeHelper.BLANK + "toReturn" + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
        code.add("    "
                + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "C"));
        code.add("      "
                + varAssignCode("toReturn.arr[i][j]", "sub_arr.arr[i][j]")
                + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("    ");
        code.add("  " + CCodeHelper.RETURN + CCodeHelper.BLANK
                + "toReturn" + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
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
        String voteStruct =
                electionDesc.getContainer().getInputStruct().getStructAccess();
        code.add(voteStruct + CCodeHelper.BLANK
                + functionCode("concatOne",
                               voteStruct + CCodeHelper.BLANK + "votesOne",
                               unsignedIntVar("sizeOne"),
                               voteStruct + CCodeHelper.BLANK + "votesTwo",
                               unsignedIntVar("sizeTwo")) + CCodeHelper.BLANK
                + CCodeHelper.OPENING_BRACES);
        code.add(voteStruct + " sub_arr" + CCodeHelper.SEMICOLON);
        code.add("  ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V")
                + " // Set all to C in the beginning");
        code.add("    "
                + varAssignCode("sub_arr.arr[i]", "C")
                + CCodeHelper.SEMICOLON);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "sizeOne")
                + " // Limit the size to the upper bound V");
        code.add("    " + functionCode(CCodeHelper.IF, lt("i", "V"))
                + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
        code.add("      " + varAssignCode("sub_arr.arr[i]", "votesOne.arr[i]")
                + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add("  "
                + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "sizeTwo")
                + " // Limit the size to the upper bound V");
        code.add("    " + functionCode(CCodeHelper.IF, lt("sizeOne + i", "V"))
                + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
        code.add("      "
                + varAssignCode("sub_arr.arr[sizeOne + i]", "votesTwo.arr[i]")
                + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  ");
        code.add(voteStruct + CCodeHelper.BLANK + "toReturn" + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("  " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
        code.add("    " + eq("toReturn.arr[i]", "sub_arr.arr[i]") + CCodeHelper.SEMICOLON);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("    ");
        code.add("  " + CCodeHelper.RETURN + CCodeHelper.BLANK
                + "toReturn" + CCodeHelper.SEMICOLON);
        code.add("  ");
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add();
    }

    /**
     * Adds the split array functions.
     */
    private void addSplitArrayFunctions() {
        code.add("// Split array");
        code.add();
        code.add("// Get splits cuts through an array of size max");
        code.add(unsignedIntVar(functionCode("*getRandomSplitLines",
                                             unsignedIntVar("splits"),
                                             unsignedIntVar("max")))
                + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
        code.add("  " + varEqualsCode("*split_arr")
                + functionCode("malloc", "splits * sizeof(*split_arr)") + CCodeHelper.SEMICOLON);
        code.add("  ");
        code.add("  " + functionCode(CCodeHelper.IF, eq("splits", one()))
                + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
        code.add("    "
                + varEqualsCode("next_split") + functionCode("nondet_uint")
                + CCodeHelper.SEMICOLON);
        code.add("    " + functionCode("assume", leq(zero(), "next_split"))
                + CCodeHelper.SEMICOLON);
        code.add("    " + functionCode("assume", leq("next_split", "(max / 2)"))
                + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("    " + varAssignCode("split_arr[" + zero() + "]", "next_split")
                + CCodeHelper.SEMICOLON);
        code.add("  " + "} else {");
        code.add("    " + varEqualsCode("last_split") + zero() + CCodeHelper.SEMICOLON);
        code.add("    " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "splits"));
        code.add("      ");
        code.add("      " + varEqualsCode("next_split")
                + functionCode("nondet_uint")
                + CCodeHelper.SEMICOLON);
        code.add("      "
                + functionCode("assume", leq("last_split", "next_split"))
                + CCodeHelper.SEMICOLON);
        code.add("      " + functionCode("assume", leq("next_split", "max"))
                + CCodeHelper.SEMICOLON);
        code.add("      ");
        code.add("      ");
        code.add("      ");
        code.add("      " + varAssignCode("split_arr[i]", "next_split") + CCodeHelper.SEMICOLON);
        code.add("      " + varAssignCode("last_split", "next_split") + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("    ");
        code.add("    " + unsignedIntVar("*splitLines") + CCodeHelper.SEMICOLON);
        code.add("    " + varAssignCode("splitLines", "split_arr")
                + CCodeHelper.SEMICOLON);
        code.add("    ");
        code.add("    " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "splits"));
        code.add("      "
                + varEqualsCode("debugrandom") + "splitLines[i]"
                + CCodeHelper.SEMICOLON);
        code.add("    " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.CLOSING_BRACES);
        code.add("  " + CCodeHelper.RETURN + CCodeHelper.BLANK
                + "split_arr" + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add();
        code.add("// Start is inclusive, stop is exclusive");
        code.add();
        String voteStruct =
                electionDesc.getContainer().getInputStruct().getStructAccess();

        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() == 1
                && electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals("V")) {
            code.add(voteStruct + CCodeHelper.BLANK
                    + functionCode("split",
                                   voteStruct + CCodeHelper.BLANK + " votes",
                                   unsignedIntVar("start"),
                                   unsignedIntVar("stop"))
                    + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
            code.add(voteStruct + " sub_arr" + CCodeHelper.SEMICOLON);
            code.add("  ");
            code.add("  " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V")
                    + " // Set all to C in the beginning");
            code.add("    "
                    + varAssignCode("sub_arr.arr[i]", "C")
                    + CCodeHelper.SEMICOLON);
            code.add("  " + CCodeHelper.CLOSING_BRACES);
            code.add("  ");
            code.add("  "
                    + functionCode(CCodeHelper.IF, eq("start", "stop"))
                    + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES
                    + " // The sub array should be empty");
            code.add(electionDesc.getContainer().getInputStruct()
                    .getStructAccess() + " toReturn" + CCodeHelper.SEMICOLON);
            code.add("    ");
            code.add("    " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
            code.add("      "
                    + varAssignCode("toReturn.arr[i]", "sub_arr.arr[i]")
                    + CCodeHelper.SEMICOLON);
            code.add("    " + CCodeHelper.CLOSING_BRACES);
            code.add("    ");
            code.add("    "
                    + CCodeHelper.RETURN + CCodeHelper.BLANK
                    + "toReturn" + CCodeHelper.SEMICOLON);
            code.add("  " + "} else {");
            code.add();
            code.add("    "
                    + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
            code.add("      "
                    + functionCode(CCodeHelper.IF,
                                   conjunct("(" + leq("start", "i") + ")",
                                            "(" + lt("i", "stop") + ")"))
                    + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
            code.add("        "
                    + varAssignCode("sub_arr.arr["
                                        + varSubtractCode("i", "start")
                                        + "]",
                                    "votes.arr[i]")
                    + CCodeHelper.SEMICOLON);
            code.add("      " + CCodeHelper.CLOSING_BRACES);
            code.add("    " + CCodeHelper.CLOSING_BRACES);
            code.add("  ");
            code.add("    ");
            code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                    + CCodeHelper.BLANK + "toReturn" + CCodeHelper.SEMICOLON);
            code.add("    ");
            code.add("    " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
            code.add("      " + varAssignCode("toReturn.arr[i]", "sub_arr.arr[i]")
                    + CCodeHelper.SEMICOLON);
            code.add("    " + CCodeHelper.CLOSING_BRACES);
            code.add("    ");
            code.add("    " + CCodeHelper.RETURN + CCodeHelper.BLANK
                    + "toReturn" + CCodeHelper.SEMICOLON);
            code.add("  " + CCodeHelper.CLOSING_BRACES);
            code.add(CCodeHelper.CLOSING_BRACES);
            code.add();
        }

        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() == 2
                && electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals("V")
                && electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[1].equals("C")) {

            code.add("// Start is inclusive, stop is exclusive");
            code.add("// Used for 2 dim arrays");
            code.add(voteStruct + CCodeHelper.BLANK
                    + functionCode("split", voteStruct + " votes",
                                   "unsigned int start",
                                   "unsigned int stop")
                    + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
            code.add(voteStruct + " " + "sub_arr" + CCodeHelper.SEMICOLON);
            code.add("  ");
            code.add("  " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V")
                    + " // Set all to C in the beginning");
            code.add("    " + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "C"));
            code.add("      " + varAssignCode("sub_arr.arr[i][j]", "C")
                    + CCodeHelper.SEMICOLON);
            code.add("    " + CCodeHelper.CLOSING_BRACES);
            code.add("  " + CCodeHelper.CLOSING_BRACES);
            code.add("  ");
            code.add("  " + functionCode(CCodeHelper.IF, eq("start", "stop"))
                    + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES
                    + CCodeHelper.BLANK + " // The sub array should be empty");
            code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                    + CCodeHelper.BLANK + "toReturn" + CCodeHelper.SEMICOLON);
            code.add("    ");
            code.add("    "
                    + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
            code.add("      " + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "V"));
            code.add("        " + varAssignCode("toReturn.arr[i][j]", "sub_arr.arr[i][j]")
                    + CCodeHelper.SEMICOLON);
            code.add("      " + CCodeHelper.CLOSING_BRACES);
            code.add("    " + CCodeHelper.CLOSING_BRACES);
            code.add("    ");
            code.add("    " +CCodeHelper.RETURN + CCodeHelper.BLANK
                    + "toReturn" + CCodeHelper.SEMICOLON);
            code.add("  " + "} else {");
            code.add();
            code.add("    " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
            code.add("      " + functionCode(CCodeHelper.IF,
                                             conjunct(leq("start", "i"), lt("i", "stop")))
                    + CCodeHelper.BLANK + CCodeHelper.OPENING_BRACES);
            code.add("        " + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "C"));
            code.add("          " + varAssignCode("sub_arr.arr["
                                                    + varSubtractCode("i", "start")
                                                    + "][j]",
                                                  "votes.arr[i][j]")
                    + CCodeHelper.SEMICOLON);
            code.add("        " + CCodeHelper.CLOSING_BRACES);
            code.add("      " + CCodeHelper.CLOSING_BRACES);
            code.add("    " + CCodeHelper.CLOSING_BRACES);
            code.add("    ");
            code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                    + CCodeHelper.BLANK + "toReturn" + CCodeHelper.SEMICOLON);
            code.add("    ");
            code.add("    " + forLoopHeaderCode("i", CCodeHelper.LT_SIGN, "V"));
            code.add("      " + forLoopHeaderCode("j", CCodeHelper.LT_SIGN, "V"));
            code.add("        " + varAssignCode("toReturn.arr[i][j]", "sub_arr.arr[i][j]")
                    + CCodeHelper.SEMICOLON);
            code.add("      " + CCodeHelper.CLOSING_BRACES);
            code.add("    " + CCodeHelper.CLOSING_BRACES);
            code.add("    ");
            code.add("    " + CCodeHelper.RETURN + CCodeHelper.BLANK
                    + "toReturn" + CCodeHelper.SEMICOLON);
            code.add("  " + CCodeHelper.CLOSING_BRACES);
            code.add(CCodeHelper.CLOSING_BRACES);
            code.add();
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
        codeLst.add(include("stdlib"));
        codeLst.add(include("stdint"));
        codeLst.add(include("assert"));
        codeLst.add("");
        codeLst.add(unsignedIntVar(functionCode("nondet_uint"))
                + CCodeHelper.SEMICOLON);
        codeLst.add("int nondet_int()" + CCodeHelper.SEMICOLON);
        codeLst.add("");
        codeLst.add("#define " + functionCode("assert2", "x", "y")
                + " " + functionCode("__CPROVER_assert", "x", "y"));
        codeLst.add("#define " + functionCode("assume", "x")
                + " " + functionCode("__CPROVER_assume", "x"));
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
        code.add(CCodeHelper.INT + CCodeHelper.BLANK
                + functionCode("main", CCodeHelper.INT + "argc",
                               "char *argv[]") + CCodeHelper.BLANK
                + CCodeHelper.OPENING_BRACES);
        code.addTab();
        // Generating the pre and post AbstractSyntaxTrees.
        BooleanExpListNode preAST = generateAST(
                preAndPostCondDesc.getPreConditionsDescription().getCode());
        BooleanExpListNode postAST = generateAST(
                preAndPostCondDesc.getPostConditionsDescription().getCode());
        initializeNumberOfTimesVoted(preAST, postAST);
        // Initialize all voting variables for the voters.
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add(varEqualsCode(UnifiedNameContainer.getVoter() + i)
                    + UnifiedNameContainer.getVoter() + CCodeHelper.SEMICOLON);
        }
        // Initialize all voting variables for the candidates.
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add(varEqualsCode(UnifiedNameContainer.getCandidate() + i)
                    + UnifiedNameContainer.getCandidate() + CCodeHelper.SEMICOLON);
        }
        // Initialize all voting variables for the seats.
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add(varEqualsCode(UnifiedNameContainer.getSeats() + i)
                    + UnifiedNameContainer.getSeats() + CCodeHelper.SEMICOLON);
        }
        List<String> boundedVars = preAndPostCondDesc.getBoundedVarDescription()
                .getCodeAsList();
        code.addList(boundedVars);
        // First the variables have to be initialized.
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
            code.add("// Init for election: " + voteNumber);
            addInitialisedValue(
                    UnifiedNameContainer.getVotingArray() + voteNumber,
                    electionDesc.getContainer().getInputType(),
                    electionDesc.getContainer().getInputStruct(), minV, maxV);
        }

        // The preconditions must be defined
        addPreProperties(preAST);
        // Now hold all the elections
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add("// Election number: " + i);
            String sizeOfVotes = UnifiedNameContainer.getVoter() + i;
            String electX =
                    varAssignCode(
                        electionDesc.getContainer().getOutputStruct().getStructAccess()
                            + CCodeHelper.BLANK + "elect" + i,
                        functionCode(UnifiedNameContainer.getVotingMethod(),
                                     sizeOfVotes, "votes"))
                    + CCodeHelper.SEMICOLON;
            code.add(electX);
        }
        // Now the postconditions can be checked
        addPostProperties(postAST);
        code.deleteTab();
        code.add(CCodeHelper.CLOSING_BRACES);
    }

    /**
     * This should be used to create the VarInitialisation within the main
     * method.
     */
    private void addSymbVarInitialisation() {
        List<SymbolicVariable> symbolicVariableList =
                preAndPostCondDesc.getSymbolicVariablesAsList();
        code.add("// Symbolic variables initialisation");
        symbolicVariableList.forEach(symbVar -> {
            InternalTypeContainer internalType = symbVar
                    .getInternalTypeContainer();
            String id = symbVar.getId();
            if (!internalType.isList()) {
                switch (internalType.getInternalType()) {
                case VOTER:
                    code.add(varEqualsCode(id) + functionCode("nondet_uint")
                            + CCodeHelper.SEMICOLON);
                    // A voter is basically an unsigned integer.
                    // The number shows which vote from votesX (the array of all
                    // votes) belongs to the voter.
                    code.add(functionCode("assume", conjunct(leq(zero(), id), lt(id, "V")))
                            + CCodeHelper.SEMICOLON);
                    // The voter has to be in the range of possible voters. V is
                    // the total amount of Voters.
                    break;
                case CANDIDATE:
                    code.add(varEqualsCode(id) + functionCode("nondet_uint")
                            + CCodeHelper.SEMICOLON);
                    // A candidate is basically an unsigned int. Candidate 0 is
                    // 0 and so on.
                    code.add(functionCode("assume", conjunct(leq(zero(), id), lt(id, "C")))
                            + CCodeHelper.SEMICOLON);
                    // C is the total number of all candidates. 0 is a candidate. C
                    // is not a candidate.
                    break;
                case SEAT:
                    // A seat is a also an unsigned int.
                    // The return of a voting method (an array) gives the
                    // elected candidate(value) of the seat(id).
                    code.add(varEqualsCode(id) + functionCode("nondet_uint")
                            + CCodeHelper.SEMICOLON);
                    // There are S seats. From 0 to S-1.
                    code.add(functionCode("assume", conjunct(leq(zero(), id), lt(id, "S")))
                            + CCodeHelper.SEMICOLON);
                    break;
                case APPROVAL:
                    break;
                case WEIGHTED_APPROVAL:
                    break;
                case INTEGER:
                    code.add(varEqualsCode(id) + functionCode("nondet_uint")
                            + CCodeHelper.SEMICOLON);
                    break;
                default:
                    reportUnsupportedType(id);
                }
            } else {
                reportUnsupportedType(id);
            }
        });
        code.add();
    }

    /**
     * This adds the Code of the PreProperties. It uses a visitor which it
     * creates.
     *
     * @param preAST
     *            the pre AST
     */
    private void addPreProperties(final BooleanExpListNode preAST) {
        code.add();
        code.add("// Preconditions ");
        code.add();
        visitor.setToPreConditionMode();
        preAST.getBooleanExpressions().forEach(booleanExpressionLists -> {
            booleanExpressionLists.forEach(booleanNode -> {
                code.addList(visitor.generateCode(booleanNode));
            });
        });
    }

    /**
     * This adds the Code of the postconditions. It uses a Visitor it creates.
     *
     * @param postAST
     *            the post AST
     */
    private void addPostProperties(final BooleanExpListNode postAST) {
        code.add();
        code.add("// Postconditions ");
        code.add();
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
        ErrorLogger.log("The type of the symbolic variable " + id
                        + " is not supported.");
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
        code.add("// Init of variable " + valueName);
        String declaration =
                complexType.getStructAccess() + CCodeHelper.BLANK
                + valueName + CCodeHelper.SEMICOLON;
        code.add(declaration);
        List<String> loopVariables =
                addNestedForLoopTop(this.code,
                                    inOutType.getSizeOfDimensionsAsList(),
                                    new ArrayList<String>());
        String assignment =
                valueName + "."
                + UnifiedNameContainer.getStructValueName();

        for (int i = 0; i < inOutType.getAmountOfDimensions(); i++) {
            assignment += arrAcc(loopVariables.get(i));
        }
        code.add(varAssignCode(assignment, functionCode("nondet_uint"))
                + CCodeHelper.SEMICOLON);
        code.add(functionCode("assume", conjunct("(" + leq(minValue, assignment) + ")",
                                                 "(" + leq(assignment, maxValue) + ")"))
                + CCodeHelper.SEMICOLON);
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
        // Use i as the default name for a loop
        String defaultName = "loop_index_";
        for (int i = 0; i < dimensions; i++) {
            String varName = defaultName + currentIndex;
            boolean duplicate = true;
            int length = 1;
            while (duplicate) {
                if (code.contains(varName) || variableName.equals(varName)) {
                    varName = generateRandomString(length) + "_" + currentIndex;
                    // Increase the length in case all words from that
                    // length are already taken.
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
        return forLoopHeaderCode(indexName, CCodeHelper.LT_SIGN, maxSize);
    }
}
