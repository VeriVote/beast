package edu.pse.beast.propertychecker;

import static edu.pse.beast.toolbox.CCodeHelper.arr;
import static edu.pse.beast.toolbox.CCodeHelper.arrAcc;
import static edu.pse.beast.toolbox.CCodeHelper.arrAccess;
import static edu.pse.beast.toolbox.CCodeHelper.conjunct;
import static edu.pse.beast.toolbox.CCodeHelper.define;
import static edu.pse.beast.toolbox.CCodeHelper.defineIfNonDef;
import static edu.pse.beast.toolbox.CCodeHelper.dotArrStructAccess;
import static edu.pse.beast.toolbox.CCodeHelper.eq;
import static edu.pse.beast.toolbox.CCodeHelper.forLoopHeaderCode;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.i;
import static edu.pse.beast.toolbox.CCodeHelper.include;
import static edu.pse.beast.toolbox.CCodeHelper.intVar;
import static edu.pse.beast.toolbox.CCodeHelper.j;
import static edu.pse.beast.toolbox.CCodeHelper.leq;
import static edu.pse.beast.toolbox.CCodeHelper.lineBreak;
import static edu.pse.beast.toolbox.CCodeHelper.lineComment;
import static edu.pse.beast.toolbox.CCodeHelper.lt;
import static edu.pse.beast.toolbox.CCodeHelper.neq;
import static edu.pse.beast.toolbox.CCodeHelper.one;
import static edu.pse.beast.toolbox.CCodeHelper.parenthesize;
import static edu.pse.beast.toolbox.CCodeHelper.pointer;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.spaces;
import static edu.pse.beast.toolbox.CCodeHelper.two;
import static edu.pse.beast.toolbox.CCodeHelper.uintVarEqualsCode;
import static edu.pse.beast.toolbox.CCodeHelper.unsignedIntVar;
import static edu.pse.beast.toolbox.CCodeHelper.varAddCode;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;
import static edu.pse.beast.toolbox.CCodeHelper.varDivideCode;
import static edu.pse.beast.toolbox.CCodeHelper.varMultiplyCode;
import static edu.pse.beast.toolbox.CCodeHelper.varSubtractCode;
import static edu.pse.beast.toolbox.CCodeHelper.x;
import static edu.pse.beast.toolbox.CCodeHelper.y;
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
 * <p>TODO Refactor this into multiple sub classes later on.
 *
 * @author Niels Hanselmann
 */
public class CBMCCodeGenerator {
    /** The Constant C. */
    public static final String C = "C";
    /** The Constant S. */
    public static final String S = "S";
    /** The Constant V. */
    public static final String V = "V";

    /** The Constant STD_LIB. */
    public static final String STD_LIB = "stdlib";
    /** The Constant STD_INT. */
    public static final String STD_INT = "stdint";

    /** The Constant CPROVER_ASSUME. */
    public static final String CPROVER_ASSUME = "__CPROVER_assume";
    /** The Constant CPROVER_ASSERT. */
    public static final String CPROVER_ASSERT = "__CPROVER_assert";

    /** The Constant ASSUME. */
    public static final String ASSUME = "assume";
    /** The Constant ASSERT. */
    public static final String ASSERT = "assert";
    /** The Constant ASSERT2. */
    public static final String ASSERT2 = "assert2";
    /** The Constant NONDET_INT. */
    public static final String NONDET_INT = "nondet_int";
    /** The Constant NONDET_UINT. */
    public static final String NONDET_UINT = "nondet_uint";
    /** The Constant NONDET_LONG. */
    public static final String NONDET_LONG = "nondet_long";
    /** The Constant NONDET_CHAR. */
    public static final String NONDET_CHAR = "nondet_char";
    /** The Constant NONDET_UCHAR. */
    public static final String NONDET_UCHAR = "nondet_uchar";

    /** The Constant MAIN. */
    public static final String MAIN = "main";

    /** The Constant TMP_STRUCT. */
    private static final String TMP_STRUCT = "tmp_struct";

    /** The Constant START. */
    private static final String START = "start";
    /** The Constant STOP. */
    private static final String STOP = "stop";

    /** The Constant TO_RETURN. */
    private static final String TO_RETURN = "toReturn";
    /** The Constant ALREADY_USED_ARR. */
    private static final String ALREADY_USED_ARR = "already_used_arr";

    /** The Constant NEW_INDEX. */
    private static final String NEW_INDEX = "new_index";
    /** The Constant SUB_ARR. */
    private static final String SUB_ARR = "sub_arr";
    /** The Constant LENGTH. */
    private static final String LENGTH = "length";

    /** The Constant VOTES. */
    private static final String VOTES = "votes";
    /** The Constant ELECT. */
    private static final String ELECT = "elect";

    /** The Constant ORIG_VOTES_SIZE. */
    private static final String ORIG_VOTES_SIZE = "ORIG_VOTES_SIZE";
    /** The Constant POS_DIFF. */
    private static final String POS_DIFF = "pos_diff";
    /** The Constant TOTAL_DIFF. */
    private static final String TOTAL_DIFF = "total_diff";
    /** The Constant MARGIN. */
    private static final String MARGIN = "MARGIN";

    /** The Constant CANDIDATE. */
    private static final String CANDIDATE = "candidate";
    /** The Constant AMOUNT_VOTES. */
    private static final String AMOUNT_VOTES = "amountVotes";

    /** The Constant SUM. */
    private static final String SUM = "sum";
    /** The Constant MAX. */
    private static final String MAX = "max";

    /** The Constant SPLIT. */
    private static final String SPLIT = "split";
    /** The Constant SPLITS. */
    private static final String SPLITS = "splits";
    /** The Constant SPLIT_LINES. */
    private static final String SPLIT_LINES = "splitLines";
    /** The Constant SPLIT_ARR. */
    private static final String SPLIT_ARR = "split_arr";
    /** The Constant LAST_SPLIT. */
    private static final String LAST_SPLIT = "last_split";
    /** The Constant NEXT_SPLIT. */
    private static final String NEXT_SPLIT = "next_split";
    /** The Constant GET_RANDOM_SPLIT_LINES. */
    private static final String GET_RANDOM_SPLIT_LINES = "getRandomSplitLines";

    /** The Constant VOTES_ONE. */
    private static final String VOTES_ONE = "votesOne";
    /** The Constant VOTES_TWO. */
    private static final String VOTES_TWO = "votesTwo";
    /** The Constant SIZE_ONE. */
    private static final String SIZE_ONE = "sizeOne";
    /** The Constant SIZE_TWO. */
    private static final String SIZE_TWO = "sizeTwo";

    /** The Constant CONCAT_ONE. */
    private static final String CONCAT_ONE = "concatOne";
    /** The Constant PERMUTATE_ONE. */
    private static final String PERMUTATE_ONE = "permutateOne";
    /** The Constant PERMUTATE_TWO. */
    private static final String PERMUTATE_TWO = "permutateTwo";
    /** The Constant VOTE_SUM_FOR_CANDIDATE. */
    private static final String VOTE_SUM_FOR_CANDIDATE =
            "voteSumForCandidate";
    /** The Constant UNIQUE. */
    private static final String UNIQUE = "Unique";

    /** The Constant COMMENT_EMPTY_SUB_ARRAY. */
    private static final String COMMENT_EMPTY_SUB_ARRAY =
            "The sub array should be empty.";
    /** The Constant COMMENT_START_STOP. */
    private static final String COMMENT_START_STOP =
            "Start is inclusive, stop is exclusive.";
    /** The Constant COMMENT_LIMIT_UPPER_BOUND. */
    private static final String COMMENT_LIMIT_UPPER_BOUND =
            "Limit the size to the upper bound V.";
    /** The Constant COMMENT_SET_BEGINNING. */
    private static final String COMMENT_SET_BEGINNING =
            "Set all to C in the beginning.";

    /** Two spaces. */
    private static final int SPACES_PER_HALF_TAB = 2;
    /** Four spaces. */
    private static final int SPACES_PER_TAB = 4;
    /** Six spaces. */
    private static final int SPACES_PER_ONE_AND_HALF_TABS = 6;
    /** Eight spaces. */
    private static final int SPACES_PER_TWO_TABS = 8;
    /** Ten spaces. */
    private static final int SPACES_PER_TWO_AND_HALF_TABS = 10;

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
        code.add(lineComment("User code."));
        final ArrayList<String> electionDescriptionCode = new ArrayList<String>();
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
        final String origVotes =
                varAssignCode(electionDesc.getContainer().getInputStruct().getStructAccess()
                                + UnifiedNameContainer.getOrigVotesName(),
                              getVotingResultCode((CBMCResultValueWrapper)
                                                      votingData.getValues()))
                + CCodeHelper.SEMICOLON;
        final String sizeOfVote =
                "" + electionDesc.getContainer().getInputType()
                        .getSizesInOrder(votingData.getVoters(),
                                         votingData.getCandidates(),
                                         votingData.getSeats())
                        .get(0);
        final String origVotesSize =
                uintVarEqualsCode(ORIG_VOTES_SIZE) + sizeOfVote + CCodeHelper.SEMICOLON;
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
        // Add the header and the voting data.
        addMarginHeaders(votingData);
        // Add the code the user wrote (e.g the election function).
        code.addAll(GUIController.getController().getElectionDescription()
                .getComplexCode());
        // Add the code which defines the votes.
        final String origVotes =
                varAssignCode(electionDesc.getContainer().getInputStruct().getStructAccess()
                                + space() + UnifiedNameContainer.getOrigVotesName(),
                              getVotingResultCode((CBMCResultValueWrapper)
                                                      votingData.getValues()))
                + CCodeHelper.SEMICOLON;
        code.add(origVotes);
        final String sizeOfVote =
                "" + electionDesc.getContainer().getInputType()
                        .getSizesInOrder(votingData.getVoters(),
                                         votingData.getCandidates(),
                                         votingData.getSeats())
                        .get(0);
        final String origVotesSize =
                uintVarEqualsCode(ORIG_VOTES_SIZE)
                + sizeOfVote + CCodeHelper.SEMICOLON;
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
        code.add(defineIfNonDef(MARGIN, marginVal));
        // We also add the original result, which is calculated by compiling the
        // program and running it.

        final String origResultName = UnifiedNameContainer.getOrigResultName();
        electionDesc.getContainer().getOutputType()
            .addLastResultAsCode(code, origResult, origResultName);
        // Add the verify method:
        // Taken and adjusted from the paper:
        // https://formal.iti.kit.edu/~beckert/pub/evoteid2016.pdf

        // Add the main method.
        code.add(intVar(functionCode(MAIN))
                + space() + CCodeHelper.OPENING_BRACES);
        addMarginCompMethod(code, electionDesc.getContainer().getInputType(),
                electionDesc.getContainer().getOutputType(), origResultName, electionDesc);
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
                                     final String origResultName, ElectionDescription desc) {
        codeList.add(lineComment("Verify for input."));
        final String voteName = UnifiedNameContainer.getNewVotesName();
        addMarginCompMethodInput(codeList, desc);
        codeList.add(lineComment("Verify for output."));
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
                                          ElectionDescription desc) {
        codeList.addList(Arrays.asList(desc.getBallotModifier().split("\n")));
    }

    // /**
    //  * Adds the conditional value.
    //  *
    //  * <p>FIXME: Purpose? Remove?
    //  *
    //  * @param voteName
    //  *            the vote name
    //  * @param inType
    //  *            the in type
    //  */
    // private void addConditionalValue(final String voteName,
    //                                  final InputType inType) {
    //     inType.restrictVotes(voteName, code);
    // }

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
        final String resultName = UnifiedNameContainer.getNewResultName();
        final String resultContainer =
                outType.getStruct().getStructAccess()
                + space() + resultName;
        final String resultAssignment =
                varAssignCode(resultContainer,
                              functionCode(UnifiedNameContainer.getVotingMethod(),
                                           ORIG_VOTES_SIZE, newVotesName))
                + CCodeHelper.SEMICOLON;
        codeList.add(resultAssignment);
        final List<String> loopVars =
                CCodeHelper.addNestedForLoopTop(codeList,
                                    outType.getSizeOfDimensionsAsList(),
                                    new ArrayList<String>());
        final String newResultAcc = outType.getFullVarAccess(resultName, loopVars);
        final String origResultAcc = outType.getFullVarAccess(origResultName, loopVars);
        codeList.add(functionCode(ASSERT, eq(newResultAcc, origResultAcc))
                    + CCodeHelper.SEMICOLON);
        CCodeHelper.addNestedForLoopBot(codeList, loopVars.size());
    }

    /**
     * Add the headers CBMC needs.
     *
     * @param votingData
     *            the voting data
     */
    private void addMarginHeaders(final ElectionSimulationData votingData) {
        code = addHeader(code);
        code.add(defineIfNonDef(UnifiedNameContainer.getVoter(),
                                votingData.getVoters()));
        code.add(defineIfNonDef(UnifiedNameContainer.getCandidate(),
                                votingData.getCandidates()));
        code.add(defineIfNonDef(UnifiedNameContainer.getSeats(),
                                votingData.getSeats()));
    }

    /**
     * Adds the margin main test.
     */
    private void addMarginMainTest() {
        // Since we only have one vote, we hard-code the
        // value "one" here
        final int voteNumber = 1;
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
        final String input =
                electionDesc.getContainer().getInputStruct().getStructAccess()
                    + space() + TMP_STRUCT;
        code.add(
            unsignedIntVar(
                functionCode(VOTE_SUM_FOR_CANDIDATE + (unique ? UNIQUE : ""),
                             input,
                             unsignedIntVar(AMOUNT_VOTES),
                             unsignedIntVar(CANDIDATE))
            )   + space()
                + CCodeHelper.OPENING_BRACES);
        final int dimensions =
                electionDesc.getContainer().getInputType().getAmountOfDimensions();

        final String[] sizes =
                electionDesc.getContainer().getInputType().getSizeOfDimensions();
        sizes[0] = AMOUNT_VOTES;
        String forLoopStart = "";
        final List<String> loopVariables = generateLoopVariables(dimensions, arr());
        for (int i = 0; i < dimensions; i++) { // Add all needed loop headers
            forLoopStart += generateForLoopHeader(loopVariables.get(i),
                                                  sizes[i]);
        }
        String forLoopEnd = "";
        for (int i = 0; i < dimensions; i++) {
            forLoopEnd += CCodeHelper.CLOSING_BRACES; // Close the for-loops
        }
        final List<String> access = new ArrayList<String>();
        for (int i = 0; i < dimensions; i++) {
            access.add(loopVariables.get(i));
        }
        final String dataDef =
                electionDesc.getContainer().getInputType().getDataTypeAndSign();
        final String definition =
                dataDef + space() + arr()
                + electionDesc.getContainer()
                    .getInputType().getDimensionDescriptor(true)
                + CCodeHelper.SEMICOLON;
        code.add(definition);
        final String assignment =
                forLoopStart
                + varAssignCode(arrAccess(arr(), access),
                                dotArrStructAccess(TMP_STRUCT, access))
                + CCodeHelper.SEMICOLON
                + lineBreak(forLoopEnd);
        code.add(assignment);
        code.add(uintVarEqualsCode(SUM) + zero() + CCodeHelper.SEMICOLON);
        code.add(forLoopHeaderCode(i(), lt(), AMOUNT_VOTES));

        // Add the specific code which differs for different input types
        electionDesc.getContainer().getInputType().addCodeForVoteSum(code,
                                                                     unique);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add(CCodeHelper.RETURN + space() + SUM + CCodeHelper.SEMICOLON);
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
                        .getSizeOfDimensions()[0].equals(C)) {
            return;
        }

        code.add(electionDesc.getContainer().getOutputStruct().getStructAccess()
                + space()
                + functionCode("intersect",
                        electionDesc.getContainer().getOutputStruct().getStructAccess()
                            + space() + one(),
                        electionDesc.getContainer().getOutputStruct().getStructAccess()
                            + space() + two())
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(electionDesc.getContainer().getOutputStruct().getStructAccess()
                + space() + TO_RETURN + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), C));
        code.add(spaces(SPACES_PER_TAB)
                + varAssignCode(dotArrStructAccess(TO_RETURN, i()), zero())
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), C));
        code.add(spaces(SPACES_PER_TAB)
                + functionCode(CCodeHelper.IF, conjunct(dotArrStructAccess(one(), i()),
                                                        dotArrStructAccess(two(), i())))
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + varAssignCode(dotArrStructAccess(TO_RETURN, i()), one())
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.RETURN + space()
                + TO_RETURN + CCodeHelper.SEMICOLON);
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
                .getAmountOfDimensions() != SPACES_PER_HALF_TAB
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals(V)
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[1].equals(C)) {
            return;
        }
        final String voteStruct =
                electionDesc.getContainer().getInputStruct().getStructAccess();

        code.add(voteStruct + space()
                + functionCode(PERMUTATE_TWO, voteStruct, VOTES,
                               unsignedIntVar(LENGTH))
                + space()
                + CCodeHelper.OPENING_BRACES);
        code.add(voteStruct + space() + SUB_ARR + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(unsignedIntVar(arrAccess(ALREADY_USED_ARR, V))
                + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(forLoopHeaderCode(i(), lt(), V)
                + space() + lineComment(COMMENT_SET_BEGINNING));
        code.add(forLoopHeaderCode(j(), lt(), C));
        code.add(varAssignCode(dotArrStructAccess(SUB_ARR, i(), j()), C)
                + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add();
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(forLoopHeaderCode(i(), lt(), LENGTH));
        code.add(varAssignCode(unsignedIntVar(NEW_INDEX),
                               functionCode(NONDET_UINT))
                + CCodeHelper.SEMICOLON);
        code.add(functionCode(ASSUME, conjunct(leq(zero(), NEW_INDEX),
                                                 lt(NEW_INDEX, LENGTH)))
                + CCodeHelper.SEMICOLON);
        code.add();
        code.add(forLoopHeaderCode(j(), lt(), i()));
        code.add(functionCode(ASSUME,
                              neq(NEW_INDEX, arrAccess(ALREADY_USED_ARR, j())))
                + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_TAB)
                + varAssignCode(arrAccess(ALREADY_USED_ARR, i()), NEW_INDEX)
                + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_TAB) + forLoopHeaderCode(j(), lt(), C));
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + varAssignCode(dotArrStructAccess(SUB_ARR, NEW_INDEX, j()),
                                dotArrStructAccess(VOTES, i(), j()))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(voteStruct + space() + TO_RETURN + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), V));
        code.add(spaces(SPACES_PER_TAB)
                + forLoopHeaderCode(j(), lt(), C));
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + varAssignCode(dotArrStructAccess(TO_RETURN, i(), j()),
                                dotArrStructAccess(SUB_ARR, i(), j()))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.RETURN + space()
                + TO_RETURN + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
    }

    /**
     * Adds the permutate one function.
     */
    private void addPermutateOneFunction() {
        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() != 1
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals(V)) {
            return;
        }
        final String voteStruct =
                electionDesc.getContainer().getInputStruct().getStructAccess();

        code.add(voteStruct + space()
                + functionCode(PERMUTATE_ONE,
                               voteStruct + space() + VOTES,
                               unsignedIntVar(LENGTH))
                + space()
                + CCodeHelper.OPENING_BRACES);
        code.add(voteStruct + space() + SUB_ARR + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(space() + unsignedIntVar(arrAccess(ALREADY_USED_ARR, V))
                + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), V)
                + space() + lineComment(COMMENT_SET_BEGINNING));
        code.add(spaces(SPACES_PER_TAB)
                + varAssignCode(dotArrStructAccess(SUB_ARR, i()), C)
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), LENGTH));
        code.add(spaces(SPACES_PER_TAB)
                + uintVarEqualsCode(NEW_INDEX)
                + functionCode(NONDET_UINT)
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB)
                + functionCode(ASSUME, conjunct(parenthesize(leq(zero(), NEW_INDEX)),
                                                parenthesize(lt(NEW_INDEX, LENGTH))))
                + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_TAB)
                + forLoopHeaderCode(j(), lt(), i()));
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + functionCode(ASSUME, neq(NEW_INDEX, arrAccess(ALREADY_USED_ARR, j())))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_TAB)
                + varAssignCode(arrAccess(ALREADY_USED_ARR, i()), NEW_INDEX)
               + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_TAB)
                + varAssignCode(dotArrStructAccess(SUB_ARR, NEW_INDEX),
                                dotArrStructAccess(VOTES, i()))
               + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(voteStruct + space() + TO_RETURN + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), V));
        code.add(spaces(SPACES_PER_TAB)
                + varAssignCode(dotArrStructAccess(TO_RETURN, i()),
                                dotArrStructAccess(SUB_ARR, i()))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.RETURN + space()
                + TO_RETURN + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_HALF_TAB);
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
                .getAmountOfDimensions() != SPACES_PER_HALF_TAB
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals(V)
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[1].equals(C)) {
            return;
        }
        final String voteStruct =
                electionDesc.getContainer().getInputStruct().getStructAccess();
        code.add(voteStruct + space()
                + functionCode("concatTwo",
                               voteStruct + space() + VOTES_ONE,
                               unsignedIntVar(SIZE_ONE),
                               voteStruct + space() + VOTES_TWO,
                               unsignedIntVar(SIZE_TWO))
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(voteStruct + space() + SUB_ARR + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), V)
                + space() + lineComment(COMMENT_SET_BEGINNING));
        code.add(spaces(SPACES_PER_TAB)
                + forLoopHeaderCode(j(), lt(), C));
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + varAssignCode(dotArrStructAccess(SUB_ARR, i(), j()), C)
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), SIZE_ONE)
                + space() + lineComment(COMMENT_LIMIT_UPPER_BOUND));
        code.add(spaces(SPACES_PER_TAB)
                + forLoopHeaderCode(j(), lt(), C));
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + functionCode(CCodeHelper.IF, lt(i(), V))
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(spaces(SPACES_PER_TWO_TABS)
                + varAssignCode(dotArrStructAccess(SUB_ARR, i(), j()),
                                dotArrStructAccess(VOTES_ONE, i(), j()))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), SIZE_TWO)
                + space() + lineComment(COMMENT_LIMIT_UPPER_BOUND));
        code.add(spaces(SPACES_PER_TAB)
                + forLoopHeaderCode(j(), lt(), C));
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + functionCode(CCodeHelper.IF,
                               lt(varAddCode(SIZE_TWO, i()), V))
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(spaces(SPACES_PER_TWO_TABS)
                + varAssignCode(dotArrStructAccess(SUB_ARR, i(), j()),
                                dotArrStructAccess(VOTES_TWO, i(), j()))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                + space() + TO_RETURN + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), V));
        code.add(spaces(SPACES_PER_TAB)
                + forLoopHeaderCode(j(), lt(), C));
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + varAssignCode(dotArrStructAccess(TO_RETURN, i(), j()),
                                dotArrStructAccess(SUB_ARR, i(), j()))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.RETURN + space()
                + TO_RETURN + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
    }

    /**
     * Adds the concat one function.
     */
    private void addConcatOneFunction() {
        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() != 1
                || !electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals(V)) {
            return;
        }
        final String voteStruct =
                electionDesc.getContainer().getInputStruct().getStructAccess();
        code.add(voteStruct + space()
                + functionCode(CONCAT_ONE,
                               voteStruct + space() + VOTES_ONE,
                               unsignedIntVar(SIZE_ONE),
                               voteStruct + space() + VOTES_TWO,
                               unsignedIntVar(SIZE_TWO)) + space()
                + CCodeHelper.OPENING_BRACES);
        code.add(voteStruct + space() + SUB_ARR + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), V)
                + space() + lineComment(COMMENT_SET_BEGINNING));
        code.add(spaces(SPACES_PER_TAB)
                + varAssignCode(dotArrStructAccess(SUB_ARR, i()), C)
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), SIZE_ONE)
                + space() + lineComment(COMMENT_LIMIT_UPPER_BOUND));
        code.add(spaces(SPACES_PER_TAB) + functionCode(CCodeHelper.IF, lt(i(), V))
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + varAssignCode(dotArrStructAccess(SUB_ARR, i()),
                                dotArrStructAccess(VOTES_ONE, i()))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), SIZE_TWO)
                + space() + lineComment(COMMENT_LIMIT_UPPER_BOUND));
        code.add(spaces(SPACES_PER_TAB)
                + functionCode(CCodeHelper.IF,
                               lt(varAddCode(SIZE_ONE, i()), V))
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + varAssignCode(dotArrStructAccess(SUB_ARR, varAddCode(SIZE_ONE, i())),
                                dotArrStructAccess(VOTES_TWO, i()))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(voteStruct + space() + TO_RETURN + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB)
                + forLoopHeaderCode(i(), lt(), V));
        code.add(spaces(SPACES_PER_TAB) + eq(dotArrStructAccess(TO_RETURN, i()),
                                             dotArrStructAccess(SUB_ARR, i()))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.RETURN + space()
                + TO_RETURN + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add();
    }

    /**
     * Adds the split array functions.
     */
    private void addSplitArrayFunctions() {
        code.add(lineComment("Split array."));
        code.add();
        code.add(lineComment("Get splits cuts through an array of size max."));
        code.add(unsignedIntVar(pointer(
                functionCode(GET_RANDOM_SPLIT_LINES,
                             unsignedIntVar(SPLITS),
                             unsignedIntVar(MAX))))
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + uintVarEqualsCode(pointer(SPLIT_ARR))
                + functionCode("malloc", varMultiplyCode(SPLITS,
                                                         functionCode(CCodeHelper.SIZE_OF,
                                                                      pointer(SPLIT_ARR))))
                + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_HALF_TAB);
        code.add(spaces(SPACES_PER_HALF_TAB) + functionCode(CCodeHelper.IF, eq(SPLITS, one()))
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(spaces(SPACES_PER_TAB)
                + uintVarEqualsCode(NEXT_SPLIT) + functionCode(NONDET_UINT)
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + functionCode(ASSUME, leq(zero(), NEXT_SPLIT))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + functionCode(ASSUME,
                                                       leq(NEXT_SPLIT,
                                                           parenthesize(varDivideCode(MAX,
                                                                                      two()))))
                + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_TAB)
                + varAssignCode(arrAccess(SPLIT_ARR, zero()), NEXT_SPLIT)
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES + space()
                + CCodeHelper.ELSE + space() + CCodeHelper.OPENING_BRACES);
        code.add(spaces(SPACES_PER_TAB)
                + uintVarEqualsCode(LAST_SPLIT) + zero()
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + forLoopHeaderCode(i(), lt(), SPLITS));
        code.addSpaces(SPACES_PER_ONE_AND_HALF_TABS);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS) + uintVarEqualsCode(NEXT_SPLIT)
                + functionCode(NONDET_UINT)
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + functionCode(ASSUME, leq(LAST_SPLIT, NEXT_SPLIT))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + functionCode(ASSUME, leq(NEXT_SPLIT, MAX))
                + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_ONE_AND_HALF_TABS);
        code.addSpaces(SPACES_PER_ONE_AND_HALF_TABS);
        code.addSpaces(SPACES_PER_ONE_AND_HALF_TABS);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + varAssignCode(arrAccess(SPLIT_ARR, i()), NEXT_SPLIT)
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + varAssignCode(LAST_SPLIT, NEXT_SPLIT)
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_TAB)
                + unsignedIntVar(pointer(SPLIT_LINES))
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + varAssignCode(SPLIT_LINES, SPLIT_ARR)
                + CCodeHelper.SEMICOLON);
        code.addSpaces(SPACES_PER_TAB);
        code.add(spaces(SPACES_PER_TAB)
                + forLoopHeaderCode(i(), lt(), SPLITS));
        code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                + uintVarEqualsCode("debugrandom") + arrAccess(SPLIT_LINES, i())
                + CCodeHelper.SEMICOLON);
        code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
        code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.RETURN + space()
                + SPLIT_ARR + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add();
        code.add(lineComment(COMMENT_START_STOP));
        code.add();
        final String voteStruct =
                electionDesc.getContainer().getInputStruct().getStructAccess();

        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() == 1
                && electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals(V)) {
            code.add(voteStruct + space()
                    + functionCode(SPLIT,
                                   voteStruct + space() + VOTES,
                                   unsignedIntVar(START),
                                   unsignedIntVar(STOP))
                    + space() + CCodeHelper.OPENING_BRACES);
            code.add(voteStruct + space() + SUB_ARR + CCodeHelper.SEMICOLON);
            code.addSpaces(SPACES_PER_HALF_TAB);
            code.add(spaces(SPACES_PER_HALF_TAB) + forLoopHeaderCode(i(), lt(), V)
                    + space() + lineComment(COMMENT_SET_BEGINNING));
            code.add(spaces(SPACES_PER_TAB)
                    + varAssignCode(dotArrStructAccess(SUB_ARR, i()), C)
                    + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
            code.addSpaces(SPACES_PER_HALF_TAB);
            code.add(spaces(SPACES_PER_HALF_TAB)
                    + functionCode(CCodeHelper.IF, eq(START, STOP))
                    + space() + CCodeHelper.OPENING_BRACES
                    + space() + lineComment(COMMENT_EMPTY_SUB_ARRAY));
            code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                    + space() + TO_RETURN + CCodeHelper.SEMICOLON);
            code.addSpaces(SPACES_PER_TAB);
            code.add(spaces(SPACES_PER_TAB) + forLoopHeaderCode(i(), lt(), V));
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                    + varAssignCode(dotArrStructAccess(TO_RETURN, i()),
                                    dotArrStructAccess(SUB_ARR, i()))
                    + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
            code.addSpaces(SPACES_PER_TAB);
            code.add(spaces(SPACES_PER_TAB)
                    + CCodeHelper.RETURN + space()
                    + TO_RETURN + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES + space()
                    + CCodeHelper.ELSE + space() + CCodeHelper.OPENING_BRACES);
            code.add();
            code.add(spaces(SPACES_PER_TAB)
                    + forLoopHeaderCode(i(), lt(), V));
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                    + functionCode(CCodeHelper.IF,
                                   conjunct(parenthesize(leq(START, i())),
                                            parenthesize(lt(i(), STOP))))
                    + space() + CCodeHelper.OPENING_BRACES);
            code.add(spaces(SPACES_PER_TWO_TABS)
                    + varAssignCode(dotArrStructAccess(SUB_ARR, varSubtractCode(i(), START)),
                                    dotArrStructAccess(VOTES, i()))
                    + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS) + CCodeHelper.CLOSING_BRACES);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
            code.addSpaces(SPACES_PER_HALF_TAB);
            code.addSpaces(SPACES_PER_TAB);
            code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                    + space() + TO_RETURN + CCodeHelper.SEMICOLON);
            code.addSpaces(SPACES_PER_TAB);
            code.add(spaces(SPACES_PER_TAB) + forLoopHeaderCode(i(), lt(), V));
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                    + varAssignCode(dotArrStructAccess(TO_RETURN, i()),
                                    dotArrStructAccess(SUB_ARR, i()))
                    + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
            code.addSpaces(SPACES_PER_TAB);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.RETURN + space()
                    + TO_RETURN + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
            code.add(CCodeHelper.CLOSING_BRACES);
            code.add();
        }

        if (electionDesc.getContainer().getInputType()
                .getAmountOfDimensions() == SPACES_PER_HALF_TAB
                && electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[0].equals(V)
                && electionDesc.getContainer().getInputType()
                        .getSizeOfDimensions()[1].equals(C)) {

            code.add(lineComment(COMMENT_START_STOP));
            code.add(lineComment("Used for 2 dim arrays."));
            code.add(voteStruct + space()
                    + functionCode(SPLIT, voteStruct
                                            + space() + VOTES,
                                   unsignedIntVar(START),
                                   unsignedIntVar(STOP))
                    + space() + CCodeHelper.OPENING_BRACES);
            code.add(voteStruct + space() + SUB_ARR + CCodeHelper.SEMICOLON);
            code.addSpaces(SPACES_PER_HALF_TAB);
            code.add(spaces(SPACES_PER_HALF_TAB) + forLoopHeaderCode(i(), lt(), V)
                    + space() + lineComment(COMMENT_SET_BEGINNING));
            code.add(spaces(SPACES_PER_TAB) + forLoopHeaderCode(j(), lt(), C));
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                    + varAssignCode(dotArrStructAccess(SUB_ARR, i(), j()), C)
                    + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
            code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
            code.addSpaces(SPACES_PER_HALF_TAB);
            code.add(spaces(SPACES_PER_HALF_TAB) + functionCode(CCodeHelper.IF, eq(START, STOP))
                    + space() + CCodeHelper.OPENING_BRACES
                    + space() + lineComment(COMMENT_EMPTY_SUB_ARRAY));
            code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                    + space() + TO_RETURN + CCodeHelper.SEMICOLON);
            code.addSpaces(SPACES_PER_TAB);
            code.add(spaces(SPACES_PER_TAB)
                    + forLoopHeaderCode(i(), lt(), V));
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                    + forLoopHeaderCode(j(), lt(), V));
            code.add(spaces(SPACES_PER_TWO_TABS)
                    + varAssignCode(dotArrStructAccess(TO_RETURN, i(), j()),
                                    dotArrStructAccess(SUB_ARR, i(), j()))
                    + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS) + CCodeHelper.CLOSING_BRACES);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
            code.addSpaces(SPACES_PER_TAB);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.RETURN + space()
                    + TO_RETURN + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES + space()
                    + CCodeHelper.ELSE + space() + CCodeHelper.OPENING_BRACES);
            code.add();
            code.add(spaces(SPACES_PER_TAB) + forLoopHeaderCode(i(), lt(), V));
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS) + functionCode(CCodeHelper.IF,
                                             conjunct(leq(START, i()), lt(i(), STOP)))
                    + space() + CCodeHelper.OPENING_BRACES);
            code.add(spaces(SPACES_PER_TWO_TABS) + forLoopHeaderCode(j(), lt(), C));
            code.add(spaces(SPACES_PER_TWO_AND_HALF_TABS)
                    + varAssignCode(dotArrStructAccess(SUB_ARR,
                                                       varSubtractCode(i(), START),
                                                       j()),
                                    dotArrStructAccess(VOTES, i(), j()))
                    + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_TWO_TABS) + CCodeHelper.CLOSING_BRACES);
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS) + CCodeHelper.CLOSING_BRACES);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
            code.addSpaces(SPACES_PER_TAB);
            code.add(electionDesc.getContainer().getInputStruct().getStructAccess()
                    + space() + TO_RETURN + CCodeHelper.SEMICOLON);
            code.addSpaces(SPACES_PER_TAB);
            code.add(spaces(SPACES_PER_TAB) + forLoopHeaderCode(i(), lt(), V));
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS)
                    + forLoopHeaderCode(j(), lt(), V));
            code.add(spaces(SPACES_PER_TWO_TABS)
                    + varAssignCode(dotArrStructAccess(TO_RETURN, i(), j()),
                                    dotArrStructAccess(SUB_ARR, i(), j()))
                    + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_ONE_AND_HALF_TABS) + CCodeHelper.CLOSING_BRACES);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.CLOSING_BRACES);
            code.addSpaces(SPACES_PER_TAB);
            code.add(spaces(SPACES_PER_TAB) + CCodeHelper.RETURN + space()
                    + TO_RETURN + CCodeHelper.SEMICOLON);
            code.add(spaces(SPACES_PER_HALF_TAB) + CCodeHelper.CLOSING_BRACES);
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
        codeLst.add(include(STD_LIB));
        codeLst.add(include(STD_INT));
        codeLst.add(include(ASSERT));
        codeLst.add();
        codeLst.add(unsignedIntVar(functionCode(NONDET_UINT))
                + CCodeHelper.SEMICOLON);
        codeLst.add(intVar(functionCode(NONDET_INT)) + CCodeHelper.SEMICOLON);
        codeLst.add();
        codeLst.add(define(functionCode(ASSERT2, x(), y()),
                           functionCode(CPROVER_ASSERT, x(), y())));
        codeLst.add(define(functionCode(ASSUME, x()),
                           functionCode(CPROVER_ASSUME, x())));
        codeLst.add();
        codeLst.addAll(Arrays.asList(electionDesc.getContainer()
                .getStructDefinitions().split("\\n")));
        return codeLst;
    }

    /**
     * Adds the main method the main method declares the boolean expression. In
     * the main method the voting method is called.
     */
    private void addMainMethod() {
        code.add(intVar(functionCode(MAIN,
                                     intVar("argc"),
                                     CCodeHelper.CHAR + space() + pointer("argv[]")))
                + space() + CCodeHelper.OPENING_BRACES);
        code.addTab();
        // Generating the pre and post AbstractSyntaxTrees.
        final BooleanExpListNode preAST = generateAST(
                preAndPostCondDesc.getPreConditionsDescription().getCode());
        final BooleanExpListNode postAST = generateAST(
                preAndPostCondDesc.getPostConditionsDescription().getCode());
        initializeNumberOfTimesVoted(preAST, postAST);
        // Initialize all voting variables for the voters.
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add(uintVarEqualsCode(UnifiedNameContainer.getVoter() + i)
                    + UnifiedNameContainer.getVoter() + CCodeHelper.SEMICOLON);
        }
        // Initialize all voting variables for the candidates.
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add(uintVarEqualsCode(UnifiedNameContainer.getCandidate() + i)
                    + UnifiedNameContainer.getCandidate() + CCodeHelper.SEMICOLON);
        }
        // Initialize all voting variables for the seats.
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add(uintVarEqualsCode(UnifiedNameContainer.getSeats() + i)
                    + UnifiedNameContainer.getSeats() + CCodeHelper.SEMICOLON);
        }
        final List<String> boundedVars =
                preAndPostCondDesc.getBoundedVarDescription().getCodeAsList();
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
            code.add(lineComment("Init for election:") + space() + voteNumber);
            addInitialisedValue(
                    UnifiedNameContainer.getVotingArray() + voteNumber,
                    electionDesc.getContainer().getInputType(),
                    electionDesc.getContainer().getInputStruct(), minV, maxV);
        }

        // The preconditions must be defined
        addPreProperties(preAST);
        // Now hold all the elections
        for (int i = 1; i <= numberOfTimesVoted; i++) {
            code.add(lineComment("Election number:") + space() + i);
            final String sizeOfVotes = UnifiedNameContainer.getVoter() + i;
            final String electX =
                    varAssignCode(
                        electionDesc.getContainer().getOutputStruct().getStructAccess()
                            + space() + ELECT + i,
                        functionCode(UnifiedNameContainer.getVotingMethod(),
                                     sizeOfVotes, VOTES + i))
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
        final List<SymbolicVariable> symbolicVariableList =
                preAndPostCondDesc.getSymbolicVariablesAsList();
        code.add(lineComment("Symbolic variables initialisation"));
        symbolicVariableList.forEach(symbVar -> {
            final InternalTypeContainer internalType =
                    symbVar.getInternalTypeContainer();
            final String id = symbVar.getId();
            if (!internalType.isList()) {
                switch (internalType.getInternalType()) {
                case VOTER:
                    code.add(uintVarEqualsCode(id) + functionCode(NONDET_UINT)
                            + CCodeHelper.SEMICOLON);
                    // A voter is basically an unsigned integer.
                    // The number shows which vote from votesX (the array of all
                    // votes) belongs to the voter.
                    code.add(functionCode(ASSUME, conjunct(leq(zero(), id), lt(id, V)))
                            + CCodeHelper.SEMICOLON);
                    // The voter has to be in the range of possible voters. V is
                    // the total amount of Voters.
                    break;
                case CANDIDATE:
                    code.add(uintVarEqualsCode(id) + functionCode(NONDET_UINT)
                            + CCodeHelper.SEMICOLON);
                    // A candidate is basically an unsigned int. Candidate 0 is
                    // 0 and so on.
                    code.add(functionCode(ASSUME, conjunct(leq(zero(), id), lt(id, C)))
                            + CCodeHelper.SEMICOLON);
                    // C is the total number of all candidates. 0 is a candidate. C
                    // is not a candidate.
                    break;
                case SEAT:
                    // A seat is a also an unsigned int.
                    // The return of a voting method (an array) gives the
                    // elected candidate(value) of the seat(id).
                    code.add(uintVarEqualsCode(id) + functionCode(NONDET_UINT)
                            + CCodeHelper.SEMICOLON);
                    // There are S seats. From 0 to S-1.
                    code.add(functionCode(ASSUME, conjunct(leq(zero(), id), lt(id, S)))
                            + CCodeHelper.SEMICOLON);
                    break;
                case APPROVAL:
                    break;
                case WEIGHTED_APPROVAL:
                    break;
                case INTEGER:
                    code.add(uintVarEqualsCode(id) + functionCode(NONDET_UINT)
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
        code.add(lineComment("Preconditions "));
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
        code.add(lineComment("Postconditions "));
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
        code.add(lineComment("Init of variable" + space() + valueName));
        final String declaration =
                complexType.getStructAccess() + space()
                + valueName + CCodeHelper.SEMICOLON;
        code.add(declaration);
        final List<String> loopVariables =
                CCodeHelper.addNestedForLoopTop(this.code,
                                    inOutType.getSizeOfDimensionsAsList(),
                                    new ArrayList<String>());
        String assignment =
                valueName + "."
                + UnifiedNameContainer.getStructValueName();
        for (int i = 0; i < inOutType.getAmountOfDimensions(); i++) {
            assignment += arrAcc(loopVariables.get(i));
        }
        code.add(varAssignCode(assignment, functionCode(NONDET_UINT))
                + CCodeHelper.SEMICOLON);
        code.add(functionCode(ASSUME, conjunct(parenthesize(leq(minValue, assignment)),
                                               parenthesize(leq(assignment, maxValue))))
                + CCodeHelper.SEMICOLON);
        inOutType.addExtraCodeAtEndOfCodeInit(code, valueName, loopVariables);
        CCodeHelper.addNestedForLoopBot(this.code, inOutType.getAmountOfDimensions());
    }

    /**
     * Generate AST.
     *
     * @param codeString
     *            the code string
     * @return the boolean exp list node
     */
    private BooleanExpListNode generateAST(final String codeString) {
        final FormalPropertyDescriptionLexer l =
                new FormalPropertyDescriptionLexer(CharStreams.fromString(codeString));
        final CommonTokenStream ts = new CommonTokenStream(l);
        final FormalPropertyDescriptionParser p =
                new FormalPropertyDescriptionParser(ts);
        final BooleanExpScope declaredVars = new BooleanExpScope();
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
        final List<String> generatedVariables = new ArrayList<String>(dimensions);
        int currentIndex = 0;
        // Use i as the default name for a loop
        final String defaultName = "loop_index_";
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
        return forLoopHeaderCode(indexName, lt(), maxSize);
    }
}
