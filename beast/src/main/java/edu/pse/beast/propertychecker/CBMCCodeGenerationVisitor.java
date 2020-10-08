package edu.pse.beast.propertychecker;

import static edu.pse.beast.propertychecker.CBMCCodeGenerator.getRandomSplitLinesFunction;
import static edu.pse.beast.toolbox.CCodeHelper.arrAcc;
import static edu.pse.beast.toolbox.CCodeHelper.arrAccess;
import static edu.pse.beast.toolbox.CCodeHelper.conjunct;
import static edu.pse.beast.toolbox.CCodeHelper.disjunct;
import static edu.pse.beast.toolbox.CCodeHelper.dotArr;
import static edu.pse.beast.toolbox.CCodeHelper.dotArrStructAccess;
import static edu.pse.beast.toolbox.CCodeHelper.dotStructAccess;
import static edu.pse.beast.toolbox.CCodeHelper.forLoopHeaderCode;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.i;
import static edu.pse.beast.toolbox.CCodeHelper.j;
import static edu.pse.beast.toolbox.CCodeHelper.leq;
import static edu.pse.beast.toolbox.CCodeHelper.lineComment;
import static edu.pse.beast.toolbox.CCodeHelper.lt;
import static edu.pse.beast.toolbox.CCodeHelper.not;
import static edu.pse.beast.toolbox.CCodeHelper.notNullOrEmpty;
import static edu.pse.beast.toolbox.CCodeHelper.one;
import static edu.pse.beast.toolbox.CCodeHelper.parenthesize;
import static edu.pse.beast.toolbox.CCodeHelper.plusEquals;
import static edu.pse.beast.toolbox.CCodeHelper.pointer;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.uintVarEqualsCode;
import static edu.pse.beast.toolbox.CCodeHelper.underscore;
import static edu.pse.beast.toolbox.CCodeHelper.unsignedIntVar;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;
import static edu.pse.beast.toolbox.CCodeHelper.varSubtractCode;
import static edu.pse.beast.toolbox.CCodeHelper.zero;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.EquivalenceNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntegerComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntersectTypeExpNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.NotNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.QuantifierNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.AccessValueNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.AtPosExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.ConstantExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.VoteSumForCandExp;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingListChangeContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.CandidateListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VoteEquivalentsNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingTupleChangeExpNode;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.outputplugins.CandidateList;

/**
 * This is the visitor for the codeGeneration For every assert or assume it
 * should be called to visit the top node 1 time it will move down and generate
 * the code. to get the generated code, you have to use the method generateCode
 * You also have to set it either to Pre- or PostConditionMode.
 *
 * @author Niels Hanselmann
 */
public final class CBMCCodeGenerationVisitor implements BooleanExpNodeVisitor {
    /** The Constant TMP. */
    private static final String TMP = "tmp";
    /** The Constant ASSUME. */
    private static final String ASSUME = "assume";
    /** The Constant ASSERT. */
    private static final String ASSERT = "assert";
    /** The Constant AND. */
    private static final String AND = "and";
    /** The Constant OR. */
    private static final String OR = "or";
    /** The Constant IMPLICATION. */
    private static final String IMPLICATION = "implication";
    /** The Constant EQUIVALENCE. */
    private static final String EQUIVALENCE = "equivalence";
    /** The Constant FOR_ALL. */
    private static final String FOR_ALL = "forAll";
    /** The Constant COUNT. */
    private static final String COUNT = "count";
    /** The Constant THERE_EXISTS. */
    private static final String THERE_EXISTS = "thereExists";
    /** The Constant NOT. */
    private static final String NOT = "not";
    /** The Constant COMPARISON. */
    private static final String COMPARISON = "comparison";
    /** The Constant UNIQUE. */
    private static final String UNIQUE = "Unique";
    /** The Constant VOTE_SUM_FOR_CANDIDATE. */
    private static final String VOTE_SUM_FOR_CANDIDATE =
            CBMCCodeGenerator.VOTE_SUM_FOR_CANDIDATE;
    /** The Constant VOTE_SUM_EXP. */
    private static final String VOTE_SUM_EXP = "voteSumExp";
    /** The Constant INTEGER_VAR. */
    private static final String INTEGER_VAR = "integerVar";
    /** The Constant INTEGER_COMP. */
    private static final String INTEGER_COMP = "integer_comp";
    /** The Constant UNDERSCORE. */
    private static final String UNDERSCORE = "_";
    /** The Constant AT_POS. */
    private static final String AT_POS = "AtPos";
    /** The Constant SPLITS. */
    private static final String SPLITS = "splits";
    /** The Constant LINES. */
    private static final String LINES = "Lines";
    /** The Constant LAST_SPLIT. */
    private static final String LAST_SPLIT = "last_split";
    /** The Constant OUTPUT. */
    private static final String OUTPUT = "output";
    /** The Constant INTERSECT. */
    private static final String INTERSECT = "intersect";
    /** The Constant CONCAT_ONE. */
    private static final String CONCAT_ONE = CBMCCodeGenerator.CONCAT_ONE;
    /** The Constant CONCAT_TWO. */
    private static final String CONCAT_TWO = CBMCCodeGenerator.CONCAT_TWO;
    /** The Constant PERMUTATE_ONE. */
    private static final String PERMUTATE_ONE = "permutateOne";
    /** The Constant PERMUTATE_TWO. */
    private static final String PERMUTATE_TWO = "permutateTwo";
    /** The Constant NOT_EMPTY. */
    private static final String NOT_EMPTY = "not_empty";
    /** The Constant BOOL. */
    private static final String BOOL = "bool";

    /** The Constant NUM_TEMPLATE. */
    private static final String NUM_TEMPLATE = "NUM";
    /** The Constant NUMBER_TEMPLATE. */
    private static final String NUMBER_TEMPLATE = "NUMBER";
    /** The Constant VARNAME_TEMPLATE. */
    private static final String VARNAME_TEMPLATE = "VARNAME";

    /** The Constant EXISTS_TEMPLATE. */
    private static final String EXISTS_TEMPLATE = "THEREEXISTS";
    /** The Constant BOOL_TEMPLATE. */
    private static final String BOOL_TEMPLATE = "BOOL";
    /** The Constant VAR_TEMPLATE. */
    private static final String VAR_TEMPLATE = "VAR";
    /** The Constant FORALL_TEMPLATE. */
    private static final String FORALL_TEMPLATE = "FORALL";
    /** The Constant MAX_TEMPLATE. */
    private static final String MAX_TEMPLATE = "MAX";
    /** The Constant SYMBVAR_TEMPLATE. */
    private static final String SYMBVAR_TEMPLATE = "SYMBVAR";

    /** The Constant VOTES_TEMPLATE. */
    private static final String VOTES_TEMPLATE = "VOTES";
    /** The Constant CAND_TEMPLATE. */
    private static final String CAND_TEMPLATE = "CAND";

    /** The Constant C. */
    private static final String C = UnifiedNameContainer.getCandidate();
    /** The Constant V. */
    private static final String V = UnifiedNameContainer.getVoter();

    /** The Constant VOTE. */
    private static final String VOTE = "vote";
    /** The Constant VOTES. */
    private static final String VOTES = "votes";
    /** The Constant ELECT. */
    private static final String ELECT = "elect";
    /** The Constant SIZE. */
    private static final String SIZE = "size";
    /** The Constant SPLIT. */
    private static final String SPLIT = "split";
    /** The Constant SPLIT_LINES. */
    private static final String SPLIT_LINES = "splitLines";

    /**
     * This String must always be "assume" or "assert". If it is not set, it is
     * null.
     */
    private String assumeOrAssert;

    /*
     * The following attributes are the counters for the variables in the code.
     * They make the variable names unique.
     */

    /** The and node counter. */
    private int andNodeCounter;

    /** The or node counter. */
    private int orNodeCounter;

    /** The implication node counter. */
    private int implicationNodeCounter;

    /** The equivalence node counter. */
    private int equivalenceNodeCounter;

    /** The for all node counter. */
    private int forAllNodeCounter;

    /** The there exists node counter. */
    private int thereExistsNodeCounter;

    /** The not node counter. */
    private int notNodeCounter;

    /** The comparison node counter. */
    private int comparisonNodeCounter;

    /** The vote sum counter. */
    private int voteSumCounter;

    /** The listlvl. */
    private int listlvl;

    /** The amt by pos var. */
    private int amtByPosVar;

    /** The number vars. */
    private int numberVars;

    /**
     * Stack of the variable names.
     */
    private Stack<String> variableNames;

    /**
     * Object that handles the generated code.
     */
    private CodeArrayListBeautifier code;

    /** The tmp index. */
    private int tmpIndex;

    /** The election type container. */
    private final ElectionTypeContainer electionTypeContainer;

    /** The post. */
    private boolean post;

    /**
     * This creates the visitor. You should create one and only one visitor for
     * every c-file you want to make. You must set it to pre- or post-property
     * mode in order for it to function.
     *
     * @param elecTypeContainer
     *            the election type container.
     *
     */
    public CBMCCodeGenerationVisitor(final ElectionTypeContainer elecTypeContainer) {
        this.electionTypeContainer = elecTypeContainer;
        andNodeCounter = 0;
        orNodeCounter = 0;
        implicationNodeCounter = 0;
        equivalenceNodeCounter = 0;
        forAllNodeCounter = 0;
        thereExistsNodeCounter = 0;
        notNodeCounter = 0;
        comparisonNodeCounter = 0;
        voteSumCounter = 0;
        numberVars = 0;
        listlvl = 0;
        amtByPosVar = 0;
        code = new CodeArrayListBeautifier();
    }

    /**
     * Generate code for split function.
     *
     * @param param1
     *            the first parameter
     * @param param2
     *            the second parameter
     * @param param3
     *            the third parameter
     * @return the string
     */
    private static String splitFunctionCode(final String param1,
                                            final String param2,
                                            final String param3) {
        return functionCode(SPLIT, param1, param2, param3);
    }

    /**
     * Gets the max string.
     *
     * @param node
     *            the node
     * @return the max string
     */
    private static String getMaxString(final QuantifierNode node) {
        final String max;
        final InternalTypeRep typeRep =
                node.getDeclaredSymbolicVar()
                .getInternalTypeContainer()
                .getInternalType();
        switch (typeRep) {
        case VOTER:
            max = UnifiedNameContainer.getVoter();
            break;
        case CANDIDATE:
            max = UnifiedNameContainer.getCandidate();
            break;
        case SEAT:
            max = UnifiedNameContainer.getSeats();
            break;
        default:
            throw new AssertionError(typeRep.name());
        }
        return max;
    }

    /**
     * Gets the tmp index.
     *
     * @return the tmp index
     */
    private synchronized int getTmpIndex() {
        final int tmp = tmpIndex;
        tmpIndex++;
        return tmp;
    }

    /**
     * Create new temp variable string.
     *
     * @param name
     *            the name
     * @return the string
     */
    private String newTempVarString(final String name) {
        final String newVarString =
                TMP + (notNullOrEmpty(name)
                        ? underscore(name)
                                : "");
        return newVarString + getTmpIndex();
    }

    /**
     * Create new temp variable string.
     *
     * @return the string
     */
    private String newTempVarString() {
        return newTempVarString(null);
    }

    /**
     * Sets the visitor to preConditionMode. That means the top node will be
     * assumed in the code.
     */
    public void setToPreConditionMode() {
        assumeOrAssert = ASSUME;
        this.post = true; // refactor this later
    }

    /**
     * Sets the visitor to postConditionMode. That means the top node will be
     * asserted in the code.
     */
    public void setToPostConditionMode() {
        assumeOrAssert = ASSERT;
        this.post = true;
    }

    /**
     * This generates the c-code for a property description-statement.
     *
     * @param node
     *            this should be the top node of a statement
     * @return the generated code
     */
    public ArrayList<String> generateCode(final BooleanExpressionNode node) {
        code = new CodeArrayListBeautifier();
        variableNames = new Stack<String>();
        node.getVisited(this);
        return code.getCodeArrayList();
    }

    @Override
    public void visitAndNode(final LogicalAndNode node) {
        final String varName = AND + UNDERSCORE + andNodeCounter;
        andNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        code.add(uintVarEqualsCode(varName)
                + parenthesize(
                        conjunct(
                                parenthesize(variableNames.pop()),
                                parenthesize(variableNames.pop())
                                )
                        )
                + CCodeHelper.SEMICOLON);
        testIfLast();
    }

    @Override
    public void visitOrNode(final LogicalOrNode node) {
        final String varName = OR + UNDERSCORE + orNodeCounter;
        orNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        code.add(uintVarEqualsCode(varName)
                + parenthesize(
                        disjunct(parenthesize(variableNames.pop()),
                                parenthesize(variableNames.pop()))
                        )
                + CCodeHelper.SEMICOLON);
        testIfLast();
    }

    @Override
    public void visitImplicationNode(final ImplicationNode node) {
        final String varName = IMPLICATION + UNDERSCORE + implicationNodeCounter;
        implicationNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        final String rhsName = variableNames.pop();
        final String lhsName = variableNames.pop();
        code.add(uintVarEqualsCode(varName)
                + parenthesize(
                        disjunct(not(parenthesize(lhsName)),
                                parenthesize(rhsName))
                        )
                + CCodeHelper.SEMICOLON);
        testIfLast();
    }

    @Override
    public void visitEquivalenceNode(final EquivalenceNode node) {
        final String varName = EQUIVALENCE + UNDERSCORE + equivalenceNodeCounter;
        equivalenceNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        final String rhs = variableNames.pop();
        final String lhs = variableNames.pop();
        code.add(uintVarEqualsCode(varName)
                + parenthesize(
                        disjunct(
                                parenthesize(
                                        conjunct(parenthesize(lhs),
                                                parenthesize(rhs))
                                        ),
                                parenthesize(
                                        conjunct(not(parenthesize(lhs)),
                                                parenthesize(not(rhs)))
                                        )
                                )
                        )
                + CCodeHelper.SEMICOLON);
        testIfLast();
    }

    @Override
    public void visitForAllNode(final ForAllNode node) {
        final String varName = FOR_ALL + UNDERSCORE + forAllNodeCounter;
        forAllNodeCounter++;
        variableNames.push(varName);
        code.add(uintVarEqualsCode(varName) + one() + CCodeHelper.SEMICOLON);
        final String max = getMaxString(node);
        String tempString =
                forLoopHeaderCode(SYMBVAR_TEMPLATE, lt(),
                                  MAX_TEMPLATE, FORALL_TEMPLATE);
        tempString = tempString.replaceAll(SYMBVAR_TEMPLATE,
                                           node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replace(MAX_TEMPLATE, max);
        tempString = tempString.replace(FORALL_TEMPLATE, varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varAssignCode(varName, variableNames.pop()) + CCodeHelper.SEMICOLON);
        code.deleteTab();
        code.add(CCodeHelper.CLOSING_BRACES);
        testIfLast();
    }

    /**
     * Compute counter.
     *
     * @param varName
     *            the var name
     * @param maxListLevel
     *            the max list level
     * @param cont
     *            the cont
     * @return the array list
     */
    private ArrayList<String> computeCounter(final String varName,
                                             final int maxListLevel,
                                             final InternalTypeContainer cont) {
        InternalTypeContainer container = cont;
        final ArrayList<String> counter = new ArrayList<String>();
        // Generate the for loops used to test the two types.
        for (int i = 0; i < maxListLevel; ++i) {
            String max = "";
            final String countingVar = COUNT + UNDERSCORE + i;
            counter.add(countingVar);
            if (null != container.getAccessTypeIfList()) {
                switch (container.getAccessTypeIfList()) {
                case VOTER:
                    max = UnifiedNameContainer.getVoter();
                    break;
                case CANDIDATE:
                    max = UnifiedNameContainer.getCandidate();
                    break;
                case SEAT:
                    max = UnifiedNameContainer.getSeats();
                    break;
                default:
                    break;
                }
            }
            // TODO This is just a hotfix because it used V when it should have
            //      used C for candidate lists.
            // TODO Maybe extract this to the voting types.
            if (electionTypeContainer
                    .getOutputType() instanceof CandidateList) {
                max = UnifiedNameContainer.getCandidate();
            }

            String loop = forLoopHeaderCode(VAR_TEMPLATE, lt(),
                                            MAX_TEMPLATE, BOOL_TEMPLATE);
            loop = loop.replaceAll(VAR_TEMPLATE, countingVar);
            loop = loop.replaceAll(MAX_TEMPLATE, max);
            loop = loop.replaceAll(BOOL_TEMPLATE, varName);
            code.add(loop);
            container = container.getListedType();
        }
        return counter;
    }

    @Override
    public void visitThereExistsNode(final ThereExistsNode node) {
        final String varName = THERE_EXISTS + UNDERSCORE + thereExistsNodeCounter;
        thereExistsNodeCounter++;
        variableNames.push(varName);
        code.add(uintVarEqualsCode(varName) + zero() + CCodeHelper.SEMICOLON);
        final String max = getMaxString(node);

        String tempString = forLoopHeaderCode(SYMBVAR_TEMPLATE, lt(),
                                              MAX_TEMPLATE, not(EXISTS_TEMPLATE));
        tempString =
                tempString.replaceAll(SYMBVAR_TEMPLATE,
                                      node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replaceAll(MAX_TEMPLATE, max);
        tempString = tempString.replaceAll(EXISTS_TEMPLATE, varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varAssignCode(varName, variableNames.pop()) + CCodeHelper.SEMICOLON);
        code.deleteTab();
        code.add(CCodeHelper.CLOSING_BRACES);
        testIfLast();
    }

    @Override
    public void visitNotNode(final NotNode node) {
        if (node.getNegatedExpNode() instanceof NotNode) {
            final NotNode node2 = (NotNode) node.getNegatedExpNode();
            node2.getNegatedExpNode().getVisited(this);
            return;
        }
        final String varName = NOT + UNDERSCORE + notNodeCounter;
        notNodeCounter++;
        variableNames.push(varName);
        node.getNegatedExpNode().getVisited(this);
        code.add(uintVarEqualsCode(varName)
                + not(parenthesize(variableNames.pop()))
                + CCodeHelper.SEMICOLON);
        testIfLast();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Generates the code for the comparison of 2 types which are not integers.
     * These types can be lists which may have different depth and might be
     * accessed by variables.
     */
    @Override
    public void visitComparisonNode(final ComparisonNode node) {
        final String varName = COMPARISON + UNDERSCORE + comparisonNodeCounter;
        comparisonNodeCounter++;
        variableNames.push(varName);
        // Let the visitor generate the code for the left value
        // which will be compared. This is also used to find out
        // how many list levels need to be compared, meaning how
        // many for loops need to be generated
        listlvl = 0;
        for (InternalTypeContainer cont = node.getLHSBooleanExpNode()
                .getInternalTypeContainer(); cont
                        .isList(); cont = cont.getListedType()) {
            listlvl++;
        }
        code.add(lineComment("Comparison left side:"));
        node.getLHSBooleanExpNode().getVisited(this);
        final int lhslistLevel = listlvl;
        listlvl = 0;
        for (InternalTypeContainer cont =
                node.getRHSBooleanExpNode().getInternalTypeContainer();
                cont.isList();
                cont = cont.getListedType()) {
            listlvl++;
        }
        code.add(lineComment("Comparison right side:"));
        node.getRHSBooleanExpNode().getVisited(this);
        final int rhslistLevel = listlvl;
        final int maxListLevel =
                lhslistLevel > rhslistLevel ? lhslistLevel : rhslistLevel;
        InternalTypeContainer cont = lhslistLevel > rhslistLevel
                ? node.getLHSBooleanExpNode().getInternalTypeContainer()
                : node.getRHSBooleanExpNode().getInternalTypeContainer();
        while (cont.getListLvl() != maxListLevel) {
            cont = cont.getListedType();
        }
        String internCode = uintVarEqualsCode(BOOL_TEMPLATE) + one() + CCodeHelper.SEMICOLON;
        internCode = internCode.replace(BOOL_TEMPLATE, varName);
        code.add(internCode);
        final ArrayList<String> counter = computeCounter(varName, maxListLevel, cont);
        final String lhs;
        final String rhs;
        // if (node.getRHSBooleanExpNode() instanceof IntersectTypeExpNode) {
        //     IntersectTypeExpNode rightNode =
        //         (IntersectTypeExpNode)
        //             node.getRHSBooleanExpNode();
        //     IntersectExpContext context = rightNode.context;
        //
        //     String voteInputOne = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputOne + ";"); // Create the var
        //     String voteInputTwo = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputTwo + ";"); // Create the var
        //     rhs = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + rhs + ";"); // Create the var
        //     intersectHelpMethod(context.intersectContent(0), voteInputOne,
        //                         context.intersectContent(1), voteInputTwo,
        //                         rhs);
        //     rhs = rhs + ".arr";
        // } else {
        //     rhs = variableNames.pop();
        // }
        //
        // if (node.getLHSBooleanExpNode() instanceof IntersectTypeExpNode) {
        //     IntersectTypeExpNode leftNode =
        //         (IntersectTypeExpNode)
        //             node.getLHSBooleanExpNode();
        //     IntersectExpContext context = leftNode.context;
        //
        //     String voteInputOne = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputOne + ";"); // Create the var
        //     String voteInputTwo = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputTwo + ";"); // Create the var
        //     lhs = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + lhs + ";"); // Create the var
        //     intersectHelpMethod(context.intersectContent(0), voteInputOne,
        //                         context.intersectContent(1), voteInputTwo,
        //                         lhs);
        //     lhs = lhs + ".arr";
        // } else {
        //     lhs = variableNames.pop();
        // }
        rhs = variableNames.pop();
        lhs = variableNames.pop();
        // setToPostConditionMode();
        internCode = varAssignCode(varName, lhs);
        // TODO Use unified name container, or defer to the Types
        final String prefix = post ? dotArr() : "";
        String preFixCopy = prefix;
        for (int i = 0; i < lhslistLevel; ++i) {
            internCode += preFixCopy
                    + arrAcc(VAR_TEMPLATE).replace(VAR_TEMPLATE, counter.get(i));
            // TODO Rewrite this with using input and output types
            preFixCopy = "";
        }
        internCode += space()
                + node.getComparisonSymbol().getCStringRep()
                + space();
        internCode += rhs;

        preFixCopy = prefix;
        for (int i = 0; i < rhslistLevel; ++i) {
            internCode += preFixCopy
                    + arrAcc(VAR_TEMPLATE).replace(VAR_TEMPLATE, counter.get(i));
            // TODO Rewrite this with using input and output types
            preFixCopy = "";
        }
        code.add(internCode + CCodeHelper.SEMICOLON);
        for (int i = 0; i < maxListLevel; ++i) {
            code.add(CCodeHelper.CLOSING_BRACES);
        }
        testIfLast();
    }

    @Override
    public void visitSymbVarExp(final SymbolicVarExp exp) {
        variableNames.push(exp.getSymbolicVar().getId());
    }

    @Override
    public void visitConstExp(final ConstantExp exp) {
        variableNames.push(exp.getConstant());
    }

    @Override
    public void visitElectExp(final ElectExp exp) {
        visitAccessingNodesReverseOrder(exp);
        String tempCode = (ELECT + NUMBER_TEMPLATE).replace(NUMBER_TEMPLATE,
                String.valueOf(exp.getCount()));
        for (int i = 0; i < exp.getAccessingVars().length; ++i) {
            tempCode += arrAcc(VAR_TEMPLATE).replace(VAR_TEMPLATE, variableNames.pop());
            listlvl--;
        }
        variableNames.push(tempCode);
    }

    @Override
    public void visitVoteExp(final VoteExp exp) {
        visitAccessingNodesReverseOrder(exp);
        String tempCode = (VOTES + NUMBER_TEMPLATE)
                            .replace(NUMBER_TEMPLATE, String.valueOf(exp.getCount()));
        // TODO: Is the following correct for all vote expressions or only for
        //       single-choice ballots?
        tempCode += (0 < exp.getAccessingVars().length) ? dotArr() : "";
        for (int i = 0; i < exp.getAccessingVars().length; ++i) {
            tempCode += arrAcc(VAR_TEMPLATE).replace(VAR_TEMPLATE, variableNames.pop());
            listlvl--;
        }
        variableNames.push(tempCode);
    }

    /**
     * Visit accessing nodes reverse order.
     *
     * @param exp
     *            the exp
     */
    private void visitAccessingNodesReverseOrder(final AccessValueNode exp) {
        for (int i = exp.getAccessingVars().length - 1; i >= 0; i--) {
            exp.getAccessingVars()[i].getVisited(this);
        }
    }

    @Override
    public void visitVoteSumExp(final VoteSumForCandExp exp,
                                final boolean unique) {
        exp.getAccessingVariable().getVisited(this);
        String funcCallTemplate =
                uintVarEqualsCode(VARNAME_TEMPLATE)
                + functionCode(VOTE_SUM_FOR_CANDIDATE + (unique ? UNIQUE : ""),
                               VOTES + NUM_TEMPLATE,
                               UnifiedNameContainer.getVoter() + voteSumCounter,
                               CAND_TEMPLATE)
                + CCodeHelper.SEMICOLON;
        final String counter = VOTE_SUM_EXP + UNDERSCORE + voteSumCounter;
        voteSumCounter++;
        funcCallTemplate =
                funcCallTemplate.replaceAll(VARNAME_TEMPLATE, counter);
        funcCallTemplate =
                funcCallTemplate.replaceAll(NUM_TEMPLATE,
                                            String.valueOf(exp.getVoteNumber()));
        funcCallTemplate =
                funcCallTemplate.replaceAll(CAND_TEMPLATE, variableNames.pop());
        code.add(funcCallTemplate);
        variableNames.push(counter);
    }

    @Override
    public void visitIntegerNode(final IntegerNode integerNode) {
        final String varName = getNewNumberVariableName();
        code.add(uintVarEqualsCode(varName)
                + integerNode.getHeldInteger() + CCodeHelper.SEMICOLON);
        variableNames.push(varName);
    }

    /**
     * Gets the new number variable name.
     *
     * @return the new number variable name
     */
    private String getNewNumberVariableName() {
        final String newNumberVariable = INTEGER_VAR + UNDERSCORE + numberVars;
        numberVars++;
        return newNumberVariable;
    }

    @Override
    public void visitIntegerComparisonNode(final IntegerComparisonNode listComparisonNode) {
        listComparisonNode.getLHSBooleanExpNode().getVisited(this);
        listComparisonNode.getRHSBooleanExpNode().getVisited(this);
        final String varNameDecl = INTEGER_COMP + UNDERSCORE + comparisonNodeCounter;
        comparisonNodeCounter++;
        final String rhs = variableNames.pop();
        final String lhs = variableNames.pop();
        final String comparisonString =
                uintVarEqualsCode(varNameDecl)
                + parenthesize(lhs + space()
                                + listComparisonNode.getComparisonSymbol().getCStringRep()
                                + space() + rhs)
                + CCodeHelper.SEMICOLON;
        code.add(comparisonString);
        variableNames.push(varNameDecl);
        testIfLast();
    }

    @Override
    public void visitBinaryIntegerValuedNode(final BinaryIntegerValuedNode
                                                        binaryIntegerValuedNode) {
        binaryIntegerValuedNode.getLhs().getVisited(this);
        binaryIntegerValuedNode.getRhs().getVisited(this);
        final String rhs = variableNames.pop();
        final String lhs = variableNames.pop();
        final String varname = getNewNumberVariableName();
        code.add(uintVarEqualsCode(varname) + lhs + space()
                + binaryIntegerValuedNode.getRelationSymbol()
                + space() + rhs + CCodeHelper.SEMICOLON);
        variableNames.push(varname);
    }

    @Override
    public void visitAtPosNode(final AtPosExp atPosExp) {
        atPosExp.getIntegerValuedExpression().getVisited(this);
        final String varName = getAtPosVarName(atPosExp);
        String template = uintVarEqualsCode(VAR_TEMPLATE)
                            + NUMBER_TEMPLATE + CCodeHelper.SEMICOLON;
        template = template.replace(VAR_TEMPLATE, varName);
        template = template.replace(NUMBER_TEMPLATE, variableNames.pop());
        code.add(template);
        variableNames.push(varName);
    }

    /**
     * Gets the at pos var name.
     *
     * @param atPosExp
     *            the at pos exp
     * @return the at pos var name
     */
    private String getAtPosVarName(final AtPosExp atPosExp) {
        final int amtByPos = amtByPosVar;
        amtByPosVar++;
        return atPosExp.getInternalTypeContainer().getInternalType().toString()
                .toLowerCase() + AT_POS + UNDERSCORE + amtByPos;
    }

    /**
     * Test if last.
     */
    private void testIfLast() {
        if (variableNames.size() == 1) {
            if (assumeOrAssert != null) {
                code.add(assumeOrAssert
                        + parenthesize(variableNames.pop())
                        + CCodeHelper.SEMICOLON);
            } else {
                ErrorLogger.log(
                        "The CodeGeneration Visitor was not set to a proper mode.");
            }
        }
    }

    @Override
    public void visitVotingTupleChangeNode(final VotingTupleChangeExpNode node) {
        final List<String> tupleVotes = new ArrayList<String>();
        // Add the first element to the list
        tupleVotes.add(node.getTuple().getChild(1).getText());

        // Extract the vote elements one by one. The terminal symbol is saved in
        // child(1). If there is another nonterminal, it is saved in child(2),
        // else, that will be null.
        ParseTree subNode = node.getTuple().getChild(2);
        do {
            tupleVotes.add(subNode.getChild(1).getText());
            subNode = subNode.getChild(2);
        } while (subNode != null);
        // There is only one child
        final ParseTree voteEquivalent = node.getSplitExp().getChild(2);
        String voteInput = "";
        String voteInputSize = "";
        // We have to go deeper
        if (!(voteEquivalent instanceof TerminalNodeImpl)) {
            voteInput = newTempVarString(VOTE);
            // Create the variable
            code.add(electionTypeContainer.getInputStruct().getStructAccess()
                    + space() + voteInput + CCodeHelper.SEMICOLON);
            voteInputSize = newTempVarString(SIZE);
            code.add(unsignedIntVar(voteInputSize) + CCodeHelper.SEMICOLON);
            final VoteEquivalentsNode equiNode =
                    new VoteEquivalentsNode(
                            node.getSplitExp().voteEquivalents(),
                            voteInput, voteInputSize
                            );
            equiNode.getVisited(this);
        } else { // We only have one vote to split
            voteInput = node.getSplitExp().voteEquivalents().Vote().getText()
                    .toLowerCase();
            voteInputSize = V + voteInput.substring(VOTES.length());
        }
        // We now have only one voting array to care about, and its name is
        // written in voteInput;
        final String splits = SPLITS + getTmpIndex();
        // Determine how many splits we need
        code.add(uintVarEqualsCode(splits) + (tupleVotes.size() - 1)
                + CCodeHelper.SEMICOLON);
        final String tmpLines = newTempVarString(LINES);
        code.add(unsignedIntVar(pointer(tmpLines)) + CCodeHelper.SEMICOLON);
        // TODO Maybe make this non-dynamic
        code.add(varAssignCode(tmpLines,
                               getRandomSplitLinesFunction(splits, voteInputSize))
                + CCodeHelper.SEMICOLON);
        final String splitLines = SPLIT_LINES + getTmpIndex();
        code.add(unsignedIntVar(arrAccess(splitLines, splits)) + CCodeHelper.SEMICOLON);
        code.add(forLoopHeaderCode(i(), leq(), splits));
        code.add(varAssignCode(arrAccess(splitLines, i()),
                               arrAccess(tmpLines, i()))
                + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
        final int tupleSize = tupleVotes.size();
        final String lastSplit = LAST_SPLIT + getTmpIndex();
        final int dim =
                electionTypeContainer.getInputType().getAmountOfDimensions();
        code.add(uintVarEqualsCode(lastSplit) + zero() + CCodeHelper.SEMICOLON);
        // Split the array and extract the votes for all but one tuple element
        final String voteStruct =
                electionTypeContainer.getInputStruct().getStructAccess();

        // -1, as the last one has to take the rest
        for (int i = 0; i < tupleSize - 1; i++) {
            final String tmp = newTempVarString();
            code.add(voteStruct + space()
                    + varAssignCode(tmp,
                                    splitFunctionCode(voteInput, lastSplit,
                                                      arrAccess(splitLines, i)))
                    + CCodeHelper.SEMICOLON);
            code.add(forLoopHeaderCode(i(), lt(), V));
            String secDim = "";
            if (2 <= dim) {
                code.add(space() + space()
                        + forLoopHeaderCode(j(), lt(), C));
                secDim = j();
            }
            code.add(space() + space()
                    + varAssignCode(
                            dotArrStructAccess(tupleVotes.get(i).toLowerCase(), i(), secDim),
                                               dotArrStructAccess(tmp, i(), secDim)
                            )
                    + CCodeHelper.SEMICOLON);
            code.add((dim < 2 ? "" : space()
                    + space() + CCodeHelper.CLOSING_BRACES)
                    + "");
            code.add(CCodeHelper.CLOSING_BRACES);

            // Set the size value for this array to its new size:
            code.add(varAssignCode(V + tupleVotes.get(i).substring(VOTES.length()),
                        varSubtractCode(arrAccess(splitLines, i), lastSplit))
                    + CCodeHelper.SEMICOLON);
            code.add(varAssignCode(lastSplit, arrAccess(splitLines, i))
                    + CCodeHelper.SEMICOLON);
        }
        final String tmp = newTempVarString();
        final String votingStruct =
                electionTypeContainer.getInputStruct().getStructAccess();
        code.add(votingStruct + space()
                + varAssignCode(tmp,
                                splitFunctionCode(voteInput, lastSplit,
                                                  voteInputSize))
                + CCodeHelper.SEMICOLON);
        // TODO Is V correct here?
        code.add(forLoopHeaderCode(i(), lt(), V));
        String secDim = "";
        if (2 <= dim) {
            // TODO Change to dimensions
            code.add(forLoopHeaderCode(j(), lt(), C));
            secDim = j();
        }
        code.add(varAssignCode(dotArrStructAccess(tupleVotes.get(tupleSize - 1).toLowerCase(),
                                                  i(), secDim),
                               dotArrStructAccess(tmp, i(), secDim))
                + CCodeHelper.SEMICOLON);
        if (2 <= dim) {
            code.add(space() + space() + CCodeHelper.CLOSING_BRACES);
        }
        code.add(CCodeHelper.CLOSING_BRACES);
        final int index = tupleSize - 1;
        code.add(varAssignCode(V + tupleVotes.get(index).substring(VOTES.length()),
                                varSubtractCode(voteInputSize, lastSplit))
                + CCodeHelper.SEMICOLON);
        // Finished the split
    }

    @Override
    public void visitVotingListChangeNode(final VotingListChangeExpNode node) {
        final String voteToSaveInto = node.getVote().getText().toLowerCase();
        final VotingListChangeContentContext contentContext =
                node.getVotingListChangeContent();
        // We save the result of the sub computation in this variable
        final String voteInput = newTempVarString(VOTE);
        // Create the variable

        code.add(electionTypeContainer.getInputStruct().getStructAccess()
                + space() + voteInput + CCodeHelper.SEMICOLON);
        final String voteInputSize = newTempVarString(SIZE);
        code.add(unsignedIntVar(voteInputSize) + CCodeHelper.SEMICOLON);
        if (contentContext.getChild(0) instanceof ConcatenationExpContext) {
            // We have a concatenation
            final ConcatenationExpNode concNode =
                    new ConcatenationExpNode(
                            contentContext.concatenationExp(), voteInput,
                            voteInputSize);
            concNode.getVisited(this);
        } else {
            // We have a permutation
            final PermutationExpNode permNode =
                    new PermutationExpNode(
                            contentContext.permutationExp(),
                            voteInput, voteInputSize
                            );
            permNode.getVisited(this);
        }
        // The result of the sub computation is now saved in "voteInput"

        // TODO Extract this logic into InputType
        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(forLoopHeaderCode(i(), lt(), V));
            code.add(varAssignCode(dotArrStructAccess(voteToSaveInto, i()),
                                   dotArrStructAccess(voteInput, i()))
                    + CCodeHelper.SEMICOLON);
            code.add(CCodeHelper.CLOSING_BRACES);
        } else { // We have two dimensions
            code.add(forLoopHeaderCode(i(), lt(), V));
            code.add(space() + space()
                    + forLoopHeaderCode(j(), lt(), C));
            code.add(varAssignCode(dotArrStructAccess(voteToSaveInto, i(), j()),
                                   dotArrStructAccess(voteInput, i(), j()))
                    + CCodeHelper.SEMICOLON);
            code.add(space() + space() + CCodeHelper.CLOSING_BRACES);
            code.add(CCodeHelper.CLOSING_BRACES);
        }
    }

    @Override
    public void visitCandidateListChangeExpNode(final CandidateListChangeExpNode node) {
        if (electionTypeContainer.getOutputType() instanceof CandidateList) {
            final String voteInput =  newTempVarString(ELECT);
            // Create the variable
            code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                    + space() + voteInput + CCodeHelper.SEMICOLON);
            final IntersectExpNode intersectNode =
                    new IntersectExpNode(
                            node.getIntersectExp(), voteInput
                            );
            intersectNode.getVisited(this);
            // The value the new array shall have is now in voteInput
            final String toSaveInto = node.getElect().getText().toLowerCase();
            code.add(forLoopHeaderCode(i(), lt(),
                                       UnifiedNameContainer.getCandidate()));
            final String structVarAcc =
                    dotStructAccess(toSaveInto,
                                    UnifiedNameContainer.getStructValueName(),
                                    i());
            final String structVarAcc2 =
                    dotStructAccess(voteInput,
                                    UnifiedNameContainer.getStructValueName(),
                                    i());
            code.add(varAssignCode(structVarAcc, structVarAcc2)
                    + CCodeHelper.SEMICOLON);
            code.add(CCodeHelper.CLOSING_BRACES);
        } else {
            GUIController.setErrorText(
                    "So far only candidate lists can be intersected!");
        }
    }

    @Override
    public void visitIntersectExpNode(final IntersectExpNode node) {
        final IntersectExpContext context = node.getIntersectExpContext();
        final String voteInputOne = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + space() + voteInputOne + CCodeHelper.SEMICOLON);
        final String voteInputTwo = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + space() + voteInputTwo + CCodeHelper.SEMICOLON);
        intersectHelpMethod(context.intersectContent(0), voteInputOne,
                            context.intersectContent(1), voteInputTwo,
                            node.getVoteOutput());
        // We now have both inputs saved in their variables.
    }

    @Override
    public void visitIntersectTypeExpNode(final IntersectTypeExpNode node) {
        final String voteInputOne = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + space() + voteInputOne + CCodeHelper.SEMICOLON);
        final String voteInputTwo = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + space() + voteInputTwo + CCodeHelper.SEMICOLON);
        final String toOutputTo = newTempVarString(OUTPUT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + space() + toOutputTo + CCodeHelper.SEMICOLON);
        intersectHelpMethod(node.getContext().intersectContent(0), voteInputOne,
                            node.getContext().intersectContent(1), voteInputTwo,
                            toOutputTo);
        variableNames.push(toOutputTo);
    }

    /**
     * Intersect help method.
     *
     * @param contextOne
     *            the context one
     * @param voteInputOne
     *            the vote input one
     * @param contextTwo
     *            the context two
     * @param voteInputTwo
     *            the vote input two
     * @param toOutputTo
     *            the to output to
     */
    private void intersectHelpMethod(final IntersectContentContext contextOne,
                                     final String voteInputOne,
                                     final IntersectContentContext contextTwo,
                                     final String voteInputTwo,
                                     final String toOutputTo) {
        final IntersectContentNode intersectContentNodeOne =
                new IntersectContentNode(contextOne, voteInputOne);
        intersectContentNodeOne.getVisited(this);
        final IntersectContentNode intersectContentNodeTwo =
                new IntersectContentNode(contextTwo, voteInputTwo);
        intersectContentNodeTwo.getVisited(this);
        code.add(varAssignCode(toOutputTo,
                functionCode(INTERSECT, voteInputOne, voteInputTwo))
                + CCodeHelper.SEMICOLON);
    }

    @Override
    public void visitIntersectContentNode(final IntersectContentNode node) {
        final String voteInput = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + space() + voteInput + CCodeHelper.SEMICOLON);
        if (node.getIntersectContentContext().Elect() != null) {
            code.add(varAssignCode(voteInput,
                                   node.getIntersectContentContext().Elect()
                                       .getText().toLowerCase())
                    + CCodeHelper.SEMICOLON);
        } else {
            // We have an intersect expression
            final IntersectExpNode intersectNode =
                    new IntersectExpNode(node.getIntersectContentContext().intersectExp(),
                                         voteInput);
            intersectNode.getVisited(this);
        }
        // We now have the result standing in
        final String toOutputTo = node.getVoteOutput();
        code.add(forLoopHeaderCode(i(), lt(), C));
        code.add(varAssignCode(dotArrStructAccess(toOutputTo, i()),
                               dotArrStructAccess(voteInput, i()))
                + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
    }

    @Override
    public void visitVoteEquivalentsNode(final VoteEquivalentsNode node) {
        String voteInput = "";
        String voteInputSize = "";
        if (node.getVoteEquivalentsContext()
                .getChild(0) instanceof PermutationExpContext) {
            // We have a permutation
            voteInput = newTempVarString(VOTE);
            // Create the variable
            code.add(electionTypeContainer.getInputStruct().getStructAccess()
                    + space() + voteInput + CCodeHelper.SEMICOLON);
            voteInputSize = newTempVarString(SIZE);
            code.add(unsignedIntVar(voteInputSize) + CCodeHelper.SEMICOLON);
            final PermutationExpNode permNode =
                    new PermutationExpNode(
                            node.getVoteEquivalentsContext().permutationExp(),
                            voteInput, voteInputSize
                            );
            permNode.getVisited(this);
        } else if (node.getVoteEquivalentsContext()
                .getChild(0) instanceof ConcatenationExpContext) {
            // We have a concatenation
            voteInput = electionTypeContainer.getInputStruct().getStructAccess()
                    + space() + newTempVarString(VOTE);
            final ConcatenationExpNode concNode =
                    new ConcatenationExpNode(
                            node.getVoteEquivalentsContext().concatenationExp(),
                            voteInput, voteInputSize
                            );
            concNode.getVisited(this);
        } else {
            // We just have a simple vote
            voteInput = node.getVoteEquivalentsContext().Vote().getText()
                    .toLowerCase();
            voteInputSize = V + node.getVoteEquivalentsContext().Vote()
                    .getText().substring(VOTES_TEMPLATE.length());
        }
        // Now a created voting array is saved in "voteInput"
        final String voteToSaveInto = node.getToOutput();
        final String voteOutputLength = node.getVoteOutputLength();
        // TODO Extract this logic into InputType
        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(forLoopHeaderCode(i(), lt(), V));
            code.add(varAssignCode(dotArrStructAccess(voteToSaveInto, i()),
                                   dotArrStructAccess(voteInput, i()))
                    + CCodeHelper.SEMICOLON);
            code.add(CCodeHelper.CLOSING_BRACES);
        } else { // We have two dimensions
            code.add(forLoopHeaderCode(i(), lt(), V));
            code.add(space() + space()
                    + forLoopHeaderCode(j(), lt(), C));
            code.add(varAssignCode(dotArrStructAccess(voteToSaveInto, i(), j()),
                                   dotArrStructAccess(voteInput, i(), j()))
                    + CCodeHelper.SEMICOLON);
            code.add(space() + space() + CCodeHelper.CLOSING_BRACES);
            code.add(CCodeHelper.CLOSING_BRACES);
        }
        code.add(varAssignCode(voteOutputLength, voteInputSize) + CCodeHelper.SEMICOLON);
        // We fulfilled the task of writing the result of the lower variable
        // into the given output variable.
    }

    @Override
    public void visitConcatenationExpNode(final ConcatenationExpNode node) {
        int offset = 0;
        if (node.getConcatenationExpContext().getChild(0).getText()
                .equals(CCodeHelper.OPENING_PARENTHESES)) {
            // We have to skip one child if we start with "("
            offset = 1; // TODO Wieso?
            // System.out.println("Hat eine Klammer!!");
        }
        final ConcatenationExpContext context = node.getConcatenationExpContext();
        final ParseTree firstChild = context.getChild(0 + offset);
        String voteInputOne = newTempVarString(VOTE);
        // Create the variable.
        code.add(electionTypeContainer.getInputStruct().getStructAccess()
                + space() + voteInputOne + CCodeHelper.SEMICOLON);
        final String voteInputOneSize = newTempVarString(SIZE);
        // We have a vote equivalent.
        if (firstChild instanceof VoteEquivalentsContext) {
            code.add(unsignedIntVar(voteInputOneSize) + CCodeHelper.SEMICOLON);
            final VoteEquivalentsNode voteEqNode =
                    new VoteEquivalentsNode(
                            context.voteEquivalents(0),
                            voteInputOne, voteInputOneSize
                            );
            voteEqNode.getVisited(this);
        } else if (firstChild instanceof PermutationExpContext) {
            // We have a permutation
            code.add(unsignedIntVar(voteInputOneSize) + CCodeHelper.SEMICOLON);
            final PermutationExpNode permNode =
                    new PermutationExpNode(
                            context.permutationExp(),
                            voteInputOne,
                            voteInputOneSize);
            permNode.getVisited(this);
        } else {
            // We have just a single vote.
            voteInputOne = context.Vote().getText().toLowerCase();
        }
        // Do the same thing for the other non terminal
        final String voteInputTwo = newTempVarString(VOTE);
        // Create the variable
        code.add(electionTypeContainer.getInputStruct().getStructAccess()
                + space() + voteInputTwo + CCodeHelper.SEMICOLON);
        final String voteInputSecondSize = newTempVarString(SIZE);
        code.add(unsignedIntVar(voteInputSecondSize) + CCodeHelper.SEMICOLON);
        final VoteEquivalentsNode voteEqNodeTwo =
                new VoteEquivalentsNode(context.voteEquivalents(offset),
                                        voteInputTwo, voteInputSecondSize);
        voteEqNodeTwo.getVisited(this);
        // We now have both sides ready in voteInputOne and voteInputTwo
        final String toSaveInto = node.getVoteOutput();
        final String tmp = newTempVarString();
        // TODO Extract into input type
        final String outputStructDesc =
                electionTypeContainer.getOutputStruct().getStructAccess();

        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(outputStructDesc + space()
                    + varAssignCode(tmp,
                                    functionCode(CONCAT_ONE,
                                                 voteInputOne,
                                                 voteInputOneSize,
                                                 voteInputTwo,
                                                 voteInputSecondSize)
                            )
                    + CCodeHelper.SEMICOLON);
            // Result is now in tmp
            code.add(forLoopHeaderCode(i(), lt(), V));
            code.add(varAssignCode(dotArrStructAccess(toSaveInto, i()),
                                   dotArrStructAccess(tmp, i()))
                    + CCodeHelper.SEMICOLON);
            code.add(CCodeHelper.CLOSING_BRACES);
        } else {
            // We have 2 dim
            code.add(outputStructDesc + space()
                    + varAssignCode(tmp,
                                    functionCode(CONCAT_TWO,
                                                 voteInputOne,
                                                 voteInputOneSize,
                                                 voteInputTwo,
                                                 voteInputSecondSize)
                            )
                    + CCodeHelper.SEMICOLON);
            // Result is now in tmp //TODO Redo all of this later on
            code.add(forLoopHeaderCode(i(), lt(), V));
            code.add(forLoopHeaderCode(j(), lt(), C));
            code.add(varAssignCode(dotArrStructAccess(toSaveInto, i(), j()),
                                   dotArrStructAccess(tmp, i(), j()))
                    + CCodeHelper.SEMICOLON);
            code.add(CCodeHelper.CLOSING_BRACES);
            code.add(CCodeHelper.CLOSING_BRACES);
        }
        // The result is now saved in "toSaveInto"
    }

    @Override
    public void visitPermutationExpNode(final PermutationExpNode node) {
        final String tmpVoteVar = newTempVarString(VOTE);
        final String voteInput =
                electionTypeContainer.getInputStruct().getStructAccess()
                + space() + tmpVoteVar;
        // Create the variable
        code.add(voteInput + CCodeHelper.SEMICOLON);
        final String voteInputSize = newTempVarString(SIZE);
        code.add(unsignedIntVar(voteInputSize) + CCodeHelper.SEMICOLON);
        final VoteEquivalentsNode voteEqNode =
                new VoteEquivalentsNode(
                        node.getPermutationExpContext().voteEquivalents(),
                        tmpVoteVar, voteInputSize);
        // voteInput now holds the vote to permute.
        // voteInputSize now is set to the size of the returned array
        voteEqNode.getVisited(this);
        final String toSaveInto = node.getVoteOutput();
        final String toSaveSizeInto = node.getVoteOutputLength();
        final String tmp = newTempVarString();
        // TODO Extract to input type

        final String voteStruct =
                electionTypeContainer.getInputStruct().getStructAccess();
        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(voteStruct + space()
                    + varAssignCode(tmp,
                                    functionCode(PERMUTATE_ONE,
                                                 tmpVoteVar,
                                                 voteInputSize))
                    + CCodeHelper.SEMICOLON);
            // Result is now in tmp
            code.add(forLoopHeaderCode(i(), lt(), V));
            code.add(varAssignCode(dotArrStructAccess(toSaveInto, i()),
                                   dotArrStructAccess(tmp, i()))
                    + CCodeHelper.SEMICOLON);
            code.add(CCodeHelper.CLOSING_BRACES);
            code.add(varAssignCode(toSaveSizeInto, voteInputSize) + CCodeHelper.SEMICOLON);
        } else {
            code.add(voteStruct + space()
                    + varAssignCode(tmp,
                                    functionCode(PERMUTATE_TWO,
                                                 tmpVoteVar,
                                                 voteInputSize)
                            )
                    + CCodeHelper.SEMICOLON);
            // Result is now in tmp
            code.add(forLoopHeaderCode(i(), lt(), V));
            code.add(forLoopHeaderCode(j(), lt(), C));
            code.add(varAssignCode(dotArrStructAccess(toSaveInto, i(), j()),
                                   dotArrStructAccess(tmp, i(), j()))
                    + CCodeHelper.SEMICOLON);
            code.add(CCodeHelper.CLOSING_BRACES);
            code.add(CCodeHelper.CLOSING_BRACES);
            code.add(varAssignCode(toSaveSizeInto, voteInputSize) + CCodeHelper.SEMICOLON);
        }
    }

    @Override
    public void visitNotEmptyExpNode(final NotEmptyExpressionNode node) {
        final String notEmpty = NOT_EMPTY + getTmpIndex();
        code.add(unsignedIntVar(notEmpty) + CCodeHelper.SEMICOLON);
        // We will save the result in this variable.
        // 0 for false, everything else for true
        variableNames.push(notEmpty);
        final String subResult = newTempVarString(BOOL);
        code.add(uintVarEqualsCode(subResult) + zero() + CCodeHelper.SEMICOLON);
        final NotEmptyContentNode notEmptyNode =
                new NotEmptyContentNode(
                        node.getContext().notEmptyContent(),
                        subResult);
        notEmptyNode.getVisited(this);
        // The result is now saved in votingInput
        code.add(varAssignCode(notEmpty, subResult) + CCodeHelper.SEMICOLON);
        if (node.isTop()) {
            code.add(functionCode(assumeOrAssert, notEmpty) + CCodeHelper.SEMICOLON);
        }
    }

    @Override
    public void visitNotEmptyContentNode(final NotEmptyContentNode node) {
        final String subResult = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + space() + subResult + CCodeHelper.SEMICOLON);
        // We have an intersect beneath
        if (node.getContext().intersectExp() != null) {
            final IntersectExpNode intersectNode =
                    new IntersectExpNode(node.getContext().intersectExp(),
                                         subResult);
            intersectNode.getVisited(this);
        } else {
            // We only have an elect
            code.add(varAssignCode(subResult,
                                   node.getContext().Elect().getText().toLowerCase())
                    + CCodeHelper.SEMICOLON);
        }
        final String toReturn = node.getVotingOutput();
        // The value is now saved in subResult
        code.add(forLoopHeaderCode(i(), lt(), C));
        code.add(plusEquals(toReturn, dotArrStructAccess(subResult, i()))
                + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
    }
}
