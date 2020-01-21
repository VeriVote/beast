package edu.pse.beast.propertychecker;

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
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant SEMICOLON. */
    private static final String SEMICOLON = ";";
    /** The Constant EQUALS_SIGN. */
    private static final String EQUALS_SIGN = "=";
    /** The Constant PIPE. */
    private static final String PIPE = "|";
    /** The Constant PLUS_PLUS. */
    private static final String PLUS_PLUS = "++";

    /** The Constant OPENING_PARENTHESES. */
    private static final String OPENING_PARENTHESES = "(";
    /** The Constant CLOSING_PARENTHESES. */
    private static final String CLOSING_PARENTHESES = ")";
    /** The Constant OPENING_BRACES. */
    private static final String OPENING_BRACES = "{";

    /** The Constant ZERO. */
    private static final String ZERO = "0";

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
     * Gets the tmp index.
     *
     * @return the tmp index
     */
    private synchronized int getTmpIndex() {
        int tmp = tmpIndex;
        tmpIndex++;
        return tmp;
    }

    /**
     * Sets the visitor to preConditionMode. That means the top node will be
     * assumed in the code.
     */
    public void setToPreConditionMode() {
        assumeOrAssert = "assume";
        this.post = true; // refactor this later
    }

    /**
     * Sets the visitor to postConditionMode. That means the top node will be
     * asserted in the code.
     */
    public void setToPostConditionMode() {
        assumeOrAssert = "assert";
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


    /**
     * Var equals code.
     *
     * @param varName the var name
     * @return the string
     */
    private static String varEqualsCode(final String varName) {
        return CCodeHelper.UNSIGNED + BLANK
                + CCodeHelper.INT + BLANK
                + varName + BLANK + EQUALS_SIGN + BLANK;
    }

    @Override
    public void visitAndNode(final LogicalAndNode node) {
        String varName = "and_" + andNodeCounter;
        andNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        code.add(varEqualsCode(varName)
                + "((" + variableNames.pop()
                + ") && (" + variableNames.pop() + "));");
        testIfLast();
    }

    @Override
    public void visitOrNode(final LogicalOrNode node) {
        String varName = "or_" + orNodeCounter;
        orNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        code.add(varEqualsCode(varName)
                + OPENING_PARENTHESES + OPENING_PARENTHESES + variableNames.pop()
                + CLOSING_PARENTHESES + BLANK
                + PIPE + PIPE + BLANK + OPENING_PARENTHESES + variableNames.pop()
                + CLOSING_PARENTHESES + CLOSING_PARENTHESES + SEMICOLON);
        testIfLast();
    }

    @Override
    public void visitImplicationNode(final ImplicationNode node) {
        String varName = "implication_" + implicationNodeCounter;
        implicationNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        String rhsName = variableNames.pop();
        String lhsName = variableNames.pop();
        code.add(varEqualsCode(varName) + "(!(" + lhsName + ") || ("
                + rhsName + "));");
        testIfLast();
    }

    @Override
    public void visitEquivalenceNode(final EquivalenceNode node) {
        String varName = "equivalence_" + equivalenceNodeCounter;
        equivalenceNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();
        code.add(varEqualsCode(varName) + "(((" + lhs + ") && (" + rhs
                + ")) || (!(" + lhs + ") && (!" + rhs + ")));");
        testIfLast();
    }

    @Override
    public void visitForAllNode(final ForAllNode node) {
        String varName = "forAll_" + forAllNodeCounter;
        forAllNodeCounter++;
        variableNames.push(varName);
        code.add(varEqualsCode(varName) + "1;");
        String max = "";
        max = getMaxString(node);
        String tempString = CCodeHelper.FOR + OPENING_PARENTHESES
                      + CCodeHelper.UNSIGNED + BLANK + CCodeHelper.INT
                      + " SYMBVAR = 0; SYMBVAR < MAX && FORALL; SYMBVAR++) {";
        tempString = tempString.replaceAll("SYMBVAR",
                node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replace("MAX", max);
        tempString = tempString.replace("FORALL", varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varName + " = " + variableNames.pop() + SEMICOLON);
        code.deleteTab();
        code.add("}");
        testIfLast();
    }

    /**
     * Gets the max string.
     *
     * @param node
     *            the node
     * @return the max string
     */
    private String getMaxString(final QuantifierNode node) {
        String max;
        final InternalTypeRep typeRep = node.getDeclaredSymbolicVar()
                .getInternalTypeContainer().getInternalType();
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
        ArrayList<String> counter = new ArrayList<String>();
        // generate the for loops used to test the two types..
        //
        for (int i = 0; i < maxListLevel; ++i) {
            String max = "";
            String countingVar = "count_" + i;
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
            // TODO this is just a hotfix because it used V when it should have
            // used C
            // TODO for candidate lists
            // TODO maybe extract this to the voting types
            if (electionTypeContainer
                    .getOutputType() instanceof CandidateList) {
                max = UnifiedNameContainer.getCandidate();
            }
            String loop = "for(unsigned int VAR = 0; BOOL && VAR < MAX; ++VAR) {";
            loop = loop.replaceAll("VAR", countingVar);
            loop = loop.replaceAll("MAX", max);
            loop = loop.replaceAll("BOOL", varName);
            code.add(loop);
            container = container.getListedType();
        }
        return counter;
    }

    @Override
    public void visitThereExistsNode(final ThereExistsNode node) {
        String varName = "thereExists_" + thereExistsNodeCounter;
        thereExistsNodeCounter++;
        variableNames.push(varName);
        code.add("unsigned int " + varName + " = 0;");
        String max = getMaxString(node);
        String tempString =
                "for(unsigned int SYMBVAR = 0; SYMBVAR < MAX && !THEREEXISTS; SYMBVAR++) {";
        tempString = tempString.replaceAll("SYMBVAR",
                node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replaceAll("MAX", max);
        tempString = tempString.replaceAll("THEREEXISTS", varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varName + " = " + variableNames.pop() + SEMICOLON);
        code.deleteTab();
        code.add("}");
        testIfLast();
    }

    @Override
    public void visitNotNode(final NotNode node) {
        if (node.getNegatedExpNode() instanceof NotNode) {
            NotNode node2 = (NotNode) node.getNegatedExpNode();
            node2.getNegatedExpNode().getVisited(this);
            return;
        }
        String varName = "not_" + notNodeCounter;
        notNodeCounter++;
        variableNames.push(varName);
        node.getNegatedExpNode().getVisited(this);
        code.add("unsigned int " + varName + " = !(" + variableNames.pop()
                + ");");
        testIfLast();
    }

    // Generates the code for the comparison of 2 types which are not integers.
    // These types can be lists which may have different depth and might be
    // accessed by variables.
    @Override
    public void visitComparisonNode(final ComparisonNode node) {
        String varName = "comparison_" + comparisonNodeCounter;
        comparisonNodeCounter++;
        variableNames.push(varName);
        // let the visitor generate the code for the left value
        // which will be compared. This is also used to find out
        // how many list levels need to be compared, meaning how
        // many for loops need to be generated
        listlvl = 0;
        for (InternalTypeContainer cont = node.getLHSBooleanExpNode()
                .getInternalTypeContainer(); cont
                        .isList(); cont = cont.getListedType()) {
            listlvl++;
        }
        code.add("//comparison left side");
        node.getLHSBooleanExpNode().getVisited(this);
        final int lhslistLevel = listlvl;
        listlvl = 0;
        for (InternalTypeContainer cont =
                node.getRHSBooleanExpNode().getInternalTypeContainer();
                cont.isList();
                cont = cont.getListedType()) {
            listlvl++;
        }
        code.add("//comparison right side:");
        node.getRHSBooleanExpNode().getVisited(this);
        int rhslistLevel = listlvl;
        int maxListLevel = lhslistLevel > rhslistLevel
                ? lhslistLevel : rhslistLevel;
        InternalTypeContainer cont = lhslistLevel > rhslistLevel
                ? node.getLHSBooleanExpNode().getInternalTypeContainer()
                : node.getRHSBooleanExpNode().getInternalTypeContainer();
        while (cont.getListLvl() != maxListLevel) {
            cont = cont.getListedType();
        }
        String internCode = "unsigned int BOOL = 1;";
        internCode = internCode.replace("BOOL", varName);
        code.add(internCode);
        ArrayList<String> counter = computeCounter(varName, maxListLevel, cont);
        String lhs;
        String rhs;
        // if (node.getRHSBooleanExpNode() instanceof IntersectTypeExpNode) {
        //     IntersectTypeExpNode rightNode =
        //         (IntersectTypeExpNode)
        //             node.getRHSBooleanExpNode();
        //     IntersectExpContext context = rightNode.context;
        //
        //     String voteInputOne = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputOne + ";"); // create the var
        //     String voteInputTwo = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputTwo + ";"); // create the var
        //     rhs = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + rhs + ";"); // create the var
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
        //         + " " + voteInputOne + ";"); // create the var
        //     String voteInputTwo = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputTwo + ";"); // create the var
        //     lhs = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + lhs + ";"); // create the var
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
        internCode = varName + " = " + lhs;
        // TODO use unified name container, or defer to the Types
        final String prefix = post ? ".arr" : "";
        String preFixCopy = prefix;
        for (int i = 0; i < lhslistLevel; ++i) {
            internCode += preFixCopy + "[VAR]".replace("VAR", counter.get(i));
            // TODO rewrite this with using input and output types
            preFixCopy = "";
        }
        internCode += BLANK + node.getComparisonSymbol().getCStringRep() + BLANK;
        internCode += rhs;

        preFixCopy = prefix;
        for (int i = 0; i < rhslistLevel; ++i) {
            internCode += preFixCopy + "[VAR]".replace("VAR", counter.get(i));
            // TODO rewrite this with using input and output types
            preFixCopy = "";
        }
        code.add(internCode + SEMICOLON);
        for (int i = 0; i < maxListLevel; ++i) {
            code.add("}");
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
        String tempCode = "electNUMBER".replace("NUMBER",
                String.valueOf(exp.getCount()));
        for (int i = 0; i < exp.getAccessingVars().length; ++i) {
            tempCode += "[VAR]".replace("VAR", variableNames.pop());
            listlvl--;
        }
        variableNames.push(tempCode);
    }

    @Override
    public void visitVoteExp(final VoteExp exp) {
        visitAccessingNodesReverseOrder(exp);
        String tempCode = "votesNUMBER".replace("NUMBER",
                String.valueOf(exp.getCount()));
        for (int i = 0; i < exp.getAccessingVars().length; ++i) {
            tempCode += "[VAR]".replace("VAR", variableNames.pop());
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
        String funcCallTemplate = "unsigned int VARNAME = voteSumForCandidate"
                + (unique ? "Unique" : "") + "(votesNUM, "
                + UnifiedNameContainer.getVoter() + voteSumCounter + ", CAND);";
        String counter = "voteSumExp_" + voteSumCounter;
        voteSumCounter++;
        funcCallTemplate = funcCallTemplate.replaceAll("VARNAME", counter);
        funcCallTemplate = funcCallTemplate.replaceAll("NUM",
                String.valueOf(exp.getVoteNumber()));
        funcCallTemplate = funcCallTemplate.replaceAll("CAND",
                variableNames.pop());
        code.add(funcCallTemplate);
        variableNames.push(counter);
    }

    @Override
    public void visitIntegerNode(final IntegerNode integerNode) {
        String varName = getNewNumberVariableName();
        code.add("unsigned int " + varName + " = "
                + integerNode.getHeldInteger() + SEMICOLON);
        variableNames.push(varName);
    }

    /**
     * Gets the new number variable name.
     *
     * @return the new number variable name
     */
    private String getNewNumberVariableName() {
        String newNumberVariable = "integerVar_" + numberVars;
        numberVars++;
        return newNumberVariable;
    }

    @Override
    public void visitIntegerComparisonNode(final IntegerComparisonNode listComparisonNode) {
        listComparisonNode.getLHSBooleanExpNode().getVisited(this);
        listComparisonNode.getRHSBooleanExpNode().getVisited(this);
        String varNameDecl = "integer_comp_" + comparisonNodeCounter;
        comparisonNodeCounter++;
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();
        String comparisonString = "unsigned int " + varNameDecl + " = " + lhs
                + BLANK + listComparisonNode.getComparisonSymbol().getCStringRep()
                + BLANK + rhs + SEMICOLON;
        code.add(comparisonString);
        variableNames.push(varNameDecl);
        testIfLast();
    }

    @Override
    public void visitBinaryIntegerValuedNode(final BinaryIntegerValuedNode
                                                        binaryIntegerValuedNode) {
        binaryIntegerValuedNode.getLhs().getVisited(this);
        binaryIntegerValuedNode.getRhs().getVisited(this);
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();
        String varname = getNewNumberVariableName();
        code.add("unsigned int " + varname + " = " + lhs + BLANK
                + binaryIntegerValuedNode.getRelationSymbol() + BLANK + rhs
                + SEMICOLON);
        variableNames.push(varname);
    }

    @Override
    public void visitAtPosNode(final AtPosExp atPosExp) {
        atPosExp.getIntegerValuedExpression().getVisited(this);
        String varName = getAtPosVarName(atPosExp);
        String template = "unsigned int VAR = NUMBER;";
        template = template.replace("VAR", varName);
        template = template.replace("NUMBER", variableNames.pop());
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
        int amtByPos = amtByPosVar;
        amtByPosVar++;
        return atPosExp.getInternalTypeContainer().getInternalType().toString()
                .toLowerCase() + "AtPos_" + amtByPos;
    }

    /**
     * Test if last.
     */
    private void testIfLast() {
        if (variableNames.size() == 1) {
            if (assumeOrAssert != null) {
                code.add(assumeOrAssert + OPENING_PARENTHESES + variableNames.pop() + ");");
            } else {
                ErrorLogger.log(
                        "The CodeGeneration Visitor was not set to a proper mode");
            }
        }
    }

    /**
     * Generate the loop header code of a for-loop given the name string of
     * the count variable, the string of the comparison symbol, and the name
     * string of the bound constant.
     *
     * @param countVar
     *            the name string of the counting variable
     * @param comparison
     *            the string of the comparison symbol
     * @param boundConst
     *            the name string of the loop bound constant
     * @return the code string of the for-loop header
     */
    private static String forLoopHeaderCode(final String countVar,
                                            final String comparison,
                                            final String boundConst) {
        return CCodeHelper.FOR + OPENING_PARENTHESES
               + CCodeHelper.INT + BLANK + countVar + BLANK + EQUALS_SIGN + BLANK
               + ZERO + SEMICOLON + BLANK
               + countVar + BLANK + comparison + BLANK + boundConst + SEMICOLON
               + countVar + PLUS_PLUS + CLOSING_PARENTHESES + BLANK + OPENING_BRACES;
    }

    @Override
    public void visitVotingTupleChangeNode(final VotingTupleChangeExpNode node) {
        List<String> tupleVotes = new ArrayList<String>();
        // add the first element to the list
        tupleVotes.add(node.getTuple().getChild(1).getText());

        // Extract the vote elements one by one. The terminal symbol is saved in
        // child(1). If there is another nonterminal, it is saved in child(2),
        // else, that will be null.
        ParseTree subNode = node.getTuple().getChild(2);
        do {
            tupleVotes.add(subNode.getChild(1).getText());
            subNode = subNode.getChild(2);
        } while (subNode != null);
        // there is only one child
        ParseTree voteEquivalent = node.getSplitExp().getChild(2);
        String voteInput = "";
        String voteInputSize = "";
        // we have to go deeper
        if (!(voteEquivalent instanceof TerminalNodeImpl)) {
            voteInput = "tmp_vote" + getTmpIndex();
            // create the variable
            code.add(electionTypeContainer.getInputStruct().getStructAccess()
                    + BLANK + voteInput + SEMICOLON);
            voteInputSize = "tmp_size" + getTmpIndex();
            code.add("unsigned int " + voteInputSize + SEMICOLON);
            VoteEquivalentsNode equiNode = new VoteEquivalentsNode(
                    node.getSplitExp().voteEquivalents(), voteInput,
                    voteInputSize);
            equiNode.getVisited(this);
        } else { // we only have one vote to split
            voteInput = node.getSplitExp().voteEquivalents().Vote().getText()
                    .toLowerCase();
            voteInputSize = "V" + voteInput.substring("votes".length());
        }
        // we now have only one voting array to care about, and its name is
        // written in
        // voteInput;
        String splits = "splits" + getTmpIndex();
        // determine how many splits we need
        code.add("unsigned int " + splits + " = " + (tupleVotes.size() - 1)
                + SEMICOLON);
        String tmpLines = "tmp_Lines" + getTmpIndex();
        code.add("unsigned int *" + tmpLines + SEMICOLON);
        // TODO maybe make this non-dynamic
        code.add(tmpLines + " = getRandomSplitLines(" + splits + ", "
                + voteInputSize + ");");
        String splitLines = "splitLines" + getTmpIndex();
        code.add("unsigned int " + splitLines + "[" + splits + "];");
        code.add(forLoopHeaderCode("i", "<=", splits));
        code.add(splitLines + "[i] = " + tmpLines + "[i];");
        code.add("}");
        int tupleSize = tupleVotes.size();
        String lastSplit = "last_split" + getTmpIndex();

        final int dim = electionTypeContainer.getInputType()
                .getAmountOfDimensions();
        code.add("unsigned int " + lastSplit + " = 0;");
        // Split the array and extract the votes for all but one tuple element
        String voteStruct = electionTypeContainer.getInputStruct()
                .getStructAccess();

        // -1, as the last one has to take the rest
        for (int i = 0; i < tupleSize - 1; i++) {
            String tmp = "tmp" + getTmpIndex();
            code.add(voteStruct + BLANK + tmp + " = split" + "( " + voteInput
                    + ", " + lastSplit + ", " + splitLines + "[" + i + "]);");
            code.add(forLoopHeaderCode("i", "<", "V"));
            String secDim = "";
            if (2 <= dim) {
                code.add("  " + forLoopHeaderCode("j", "<", "C"));
                secDim = "[j]";
            }
            code.add("  " + tupleVotes.get(i).toLowerCase() + ".arr[i]" + secDim
                    + " = " + tmp + ".arr[i]" + secDim + SEMICOLON);
            code.add((dim < 2 ? "" : "  }") + "");
            code.add("}");

            // Set the size value for this array to its new size:
            code.add("V" + tupleVotes.get(i).substring("votes".length()) + " = "
                    + splitLines + "[" + i + "] - " + lastSplit + SEMICOLON);
            code.add(lastSplit + " = " + splitLines + "[" + i + "];");
        }
        String tmp = "tmp" + getTmpIndex();
        String votingStruct = electionTypeContainer.getInputStruct()
                .getStructAccess();
        code.add(votingStruct + BLANK + tmp + " = split" + "( " + voteInput + ", "
                + lastSplit + ", " + voteInputSize + ");");
        // TODO is V correct here?
        code.add(forLoopHeaderCode("i", "<", "V"));
        if (2 <= dim) {
            // TODO change to dimensions
            code.add(forLoopHeaderCode("j", "<", "C"));
        }
        code.add(tupleVotes.get(tupleSize - 1).toLowerCase() + ".arr[i]"
                + (2 <= dim ? "[j]" : "") + " = " + tmp + ".arr[i]" + ""
                + (2 <= dim ? "[j]" : "") + SEMICOLON);
        if (2 <= dim) {
            code.add("  }");
        }
        code.add("}");
        int index = tupleSize - 1;
        code.add("V" + tupleVotes.get(index).substring("votes".length()) + " = "
                + voteInputSize + " - " + lastSplit + SEMICOLON);
        // finished the split
    }

    @Override
    public void visitVotingListChangeNode(final VotingListChangeExpNode node) {
        String voteToSaveInto = node.getVote().getText().toLowerCase();
        VotingListChangeContentContext contentContext = node
                .getVotingListChangeContent();
        // we save the result of the sub computation in this variable
        String voteInput = "tmp_vote" + getTmpIndex();
        // create the variable

        code.add(electionTypeContainer.getInputStruct().getStructAccess() + BLANK
                + voteInput + SEMICOLON);
        String voteInputSize = "tmp_size" + getTmpIndex();
        code.add("unsigned int " + voteInputSize + SEMICOLON);
        if (contentContext.getChild(0) instanceof ConcatenationExpContext) {
            // we have a concatenation
            ConcatenationExpNode concNode = new ConcatenationExpNode(
                    contentContext.concatenationExp(), voteInput,
                    voteInputSize);
            concNode.getVisited(this);
        } else {
            // we have a permutation
            PermutationExpNode permNode = new PermutationExpNode(
                    contentContext.permutationExp(), voteInput, voteInputSize);
            permNode.getVisited(this);
        }
        // the result of the sub computation is now saved in "voteInput"

        // TODO extract this logic into InputType
        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(forLoopHeaderCode("i", "<", "V"));
            code.add(voteToSaveInto + ".arr[i] = " + voteInput + ".arr[i];");
            code.add("}");
        } else { // we have two dimensions
            code.add(forLoopHeaderCode("i", "<", "V"));
            code.add("  " + forLoopHeaderCode("j", "<", "C"));
            code.add(voteToSaveInto + ".arr[i][j] = " + voteInput
                    + ".arr[i][j];");
            code.add("  }");
            code.add("}");
        }
    }

    @Override
    public void visitCandidateListChangeExpNode(final CandidateListChangeExpNode node) {
        if (electionTypeContainer.getOutputType() instanceof CandidateList) {
            String voteInput = "tmp_elect" + getTmpIndex();
            // create the variable
            code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                    + BLANK + voteInput + SEMICOLON);
            IntersectExpNode intersectNode = new IntersectExpNode(
                    node.getIntersectExp(), voteInput);
            intersectNode.getVisited(this);
            // the value the new array shall have is now in voteInput
            String toSaveInto = node.getElect().getText().toLowerCase();
            code.add(forLoopHeaderCode("i", "<", UnifiedNameContainer.getCandidate()));
            String structVarAcc = "."
                    + UnifiedNameContainer.getStructValueName() + "[i]";
            code.add(toSaveInto + BLANK + structVarAcc + " = " + voteInput
                    + structVarAcc + SEMICOLON);
            code.add("}");
        } else {
            GUIController.setErrorText(
                    "So far only candidate lists can be intersected!");
        }
    }

    @Override
    public void visitIntersectExpNode(final IntersectExpNode node) {
        IntersectExpContext context = node.getIntersectExpContext();
        String voteInputOne = "tmp_elect" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess() + BLANK
                + voteInputOne + SEMICOLON);
        String voteInputTwo = "tmp_elect" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess() + BLANK
                + voteInputTwo + SEMICOLON);
        intersectHelpMethod(context.intersectContent(0), voteInputOne,
                context.intersectContent(1), voteInputTwo,
                node.getVoteOutput());
        // we now have both inputs saved in their variables
    }

    @Override
    public void visitIntersectTypeExpNode(final IntersectTypeExpNode node) {
        String voteInputOne = "tmp_elect" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess() + BLANK
                + voteInputOne + SEMICOLON);
        String voteInputTwo = "tmp_elect" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess() + BLANK
                + voteInputTwo + SEMICOLON);
        String toOutputTo = "tmp_output" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess() + BLANK
                + toOutputTo + SEMICOLON);
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
        IntersectContentNode intersectContentNodeOne =
                new IntersectContentNode(contextOne, voteInputOne);
        intersectContentNodeOne.getVisited(this);
        IntersectContentNode intersectContentNodeTwo =
                new IntersectContentNode(contextTwo, voteInputTwo);
        intersectContentNodeTwo.getVisited(this);
        code.add(toOutputTo + " = intersect(" + voteInputOne + ", "
                + voteInputTwo + ");");
    }

    @Override
    public void visitIntersectContentNode(final IntersectContentNode node) {
        String voteInput = "tmp_elect" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess() + BLANK
                + voteInput + SEMICOLON);
        if (node.getIntersectContentContext().Elect() != null) {
            code.add(voteInput + " = " + node.getIntersectContentContext()
                    .Elect().getText().toLowerCase() + SEMICOLON);
        } else {
            // we have an intersect expression
            IntersectExpNode intersectNode =
                    new IntersectExpNode(node.getIntersectContentContext().intersectExp(),
                                         voteInput);
            intersectNode.getVisited(this);
        }
        // we now have the result standing in
        String toOutputTo = node.getVoteOutput();
        code.add(forLoopHeaderCode("i", "<", "C"));
        code.add(toOutputTo + ".arr[i] = " + voteInput + ".arr[i];");
        code.add("}");
    }

    @Override
    public void visitVoteEquivalentsNode(final VoteEquivalentsNode node) {
        String voteInput = "";
        String voteInputSize = "";
        if (node.getVoteEquivalentsContext()
                .getChild(0) instanceof PermutationExpContext) {
            // we have a permutation
            voteInput = "tmp_vote" + getTmpIndex();
            // create the variable
            code.add(electionTypeContainer.getInputStruct().getStructAccess()
                    + BLANK + voteInput + SEMICOLON);
            voteInputSize = "tmp_size" + getTmpIndex();
            code.add("unsigned int " + voteInputSize + SEMICOLON);
            PermutationExpNode permNode = new PermutationExpNode(
                    node.getVoteEquivalentsContext().permutationExp(),
                    voteInput, voteInputSize);
            permNode.getVisited(this);
        } else if (node.getVoteEquivalentsContext()
                .getChild(0) instanceof ConcatenationExpContext) {
            // we have a concatenation
            voteInput = electionTypeContainer.getInputStruct().getStructAccess()
                    + BLANK + "tmp_vote" + getTmpIndex();
            ConcatenationExpNode concNode = new ConcatenationExpNode(
                    node.getVoteEquivalentsContext().concatenationExp(),
                    voteInput, voteInputSize);
            concNode.getVisited(this);
        } else {
            // we just have a simple vote
            voteInput = node.getVoteEquivalentsContext().Vote().getText()
                    .toLowerCase();
            voteInputSize = "V" + node.getVoteEquivalentsContext().Vote()
                    .getText().substring("VOTES".length());
        }
        // now a created voting array is saved in "voteInput"
        String voteToSaveInto = node.getToOutput();
        String voteOutputLength = node.getVoteOutputLength();
        // TODO extract this logic into InputType
        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(forLoopHeaderCode("i", "<", "V"));
            code.add(voteToSaveInto + ".arr[i] = " + voteInput + ".arr[i];");
            code.add("}");
        } else { // we have two dimensions
            code.add(forLoopHeaderCode("i", "<", "V"));
            code.add("  " + forLoopHeaderCode("j", "<", "C"));
            code.add(voteToSaveInto + ".arr[i][j] = " + voteInput
                    + ".arr[i][j];");
            code.add("  }");
            code.add("}");
        }
        code.add(voteOutputLength + " = " + voteInputSize + SEMICOLON);
        // we fulfilled the task of writing the result of the lower variable
        // into the
        // given
        // output variable
    }

    @Override
    public void visitConcatenationExpNode(final ConcatenationExpNode node) {
        int offset = 0;
        if (node.getConcatenationExpContext().getChild(0).getText()
                .equals(OPENING_PARENTHESES)) {
            // we have to skip one child if we start with "("
            offset = 1; // TODO wieso?
            // System.out.println("hat eine klammer!!");
        }
        ConcatenationExpContext context = node.getConcatenationExpContext();
        ParseTree firstChild = context.getChild(0 + offset);
        String voteInputOne = "tmp_vote" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getInputStruct().getStructAccess() + BLANK
                + voteInputOne + SEMICOLON);
        String voteInputOneSize = "tmp_size" + getTmpIndex();
        // we have a vote equivalent
        if (firstChild instanceof VoteEquivalentsContext) {
            code.add("unsigned int " + voteInputOneSize + SEMICOLON);
            VoteEquivalentsNode voteEqNode = new VoteEquivalentsNode(
                    context.voteEquivalents(0), voteInputOne, voteInputOneSize);
            voteEqNode.getVisited(this);
        } else if (firstChild instanceof PermutationExpContext) {
            // we have a permutation
            code.add("unsigned int " + voteInputOneSize + SEMICOLON);
            PermutationExpNode permNode = new PermutationExpNode(
                    context.permutationExp(), voteInputOne, voteInputOneSize);
            permNode.getVisited(this);
        } else {
            // we have just a single vote
            voteInputOne = context.Vote().getText().toLowerCase();
        }
        // do the same thing for the other non terminal
        String voteInputTwo = "tmp_vote" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getInputStruct().getStructAccess() + BLANK
                + voteInputTwo + SEMICOLON);
        String voteInputSecondSize = "tmp_size" + getTmpIndex();
        code.add("unsigned int " + voteInputSecondSize + SEMICOLON);
        VoteEquivalentsNode voteEqNodeTwo = new VoteEquivalentsNode(
                context.voteEquivalents(offset), voteInputTwo,
                voteInputSecondSize);
        voteEqNodeTwo.getVisited(this);
        // we now have both sides ready in voteInputOne and voteInputTwo
        String toSaveInto = node.getVoteOutput();
        String tmp = "tmp" + getTmpIndex();
        // TODO extract into input type
        String outputStructDesc = electionTypeContainer.getOutputStruct()
                .getStructAccess();

        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(outputStructDesc + BLANK + tmp + " = concatOne("
                    + voteInputOne + ", " + voteInputOneSize + " , "
                    + voteInputTwo + ", " + voteInputSecondSize + ");");
            // result is now in tmp
            code.add(forLoopHeaderCode("i", "<", "V"));
            code.add(toSaveInto + ".arr[i] = " + tmp + ".arr[i];");
            code.add("}");
        } else {
            // we have 2 dim
            code.add(outputStructDesc + BLANK + tmp + " = concatTwo("
                    + voteInputOne + ", " + voteInputOneSize + " , "
                    + voteInputTwo + ", " + voteInputSecondSize + ");");
            // result is now in tmp //TODO redo all of this later on
            code.add(forLoopHeaderCode("i", "<", "V"));
            code.add(forLoopHeaderCode("j", "<", "C"));
            code.add(toSaveInto + ".arr[i][j] = " + tmp + ".arr[i][j];");
            code.add("}");
            code.add("}");
        }
        // the result is now saved in "toSaveInto"
    }

    @Override
    public void visitPermutationExpNode(final PermutationExpNode node) {
        String voteInput = "tmp_vote" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getInputStruct().getStructAccess() + BLANK
                + voteInput + SEMICOLON);
        String voteInputSize = "tmp_size" + getTmpIndex();
        code.add("unsigned int " + voteInputSize + SEMICOLON);
        VoteEquivalentsNode voteEqNode = new VoteEquivalentsNode(
                node.getPermutationExpContext().voteEquivalents(), voteInput,
                voteInputSize);
        // voteInput now holds the vote to permute.
        // voteInputSize now is set to the size of the returned array
        voteEqNode.getVisited(this);
        String toSaveInto = node.getVoteOutput();
        String toSaveSizeInto = node.getVoteOutputLength();
        String tmp = "tmp" + getTmpIndex();
        // TODO extract to input type

        String voteStruct = electionTypeContainer.getInputStruct()
                .getStructAccess();
        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(voteStruct + BLANK + tmp + " = permutateOne(" + voteInput
                    + ", " + voteInputSize + ");");
            // result is now in tmp
            code.add(forLoopHeaderCode("i", "<", "V"));
            code.add(toSaveInto + ".arr[i] = " + tmp + ".arr[i];");
            code.add("}");
            code.add(toSaveSizeInto + " = " + voteInputSize + SEMICOLON);
        } else {
            code.add(voteStruct + BLANK + tmp + " = permutateTwo(" + voteInput
                    + ", " + voteInputSize + ");");
            // result is now in tmp
            code.add(forLoopHeaderCode("i", "<", "V"));
            code.add(forLoopHeaderCode("j", "<", "C"));
            code.add(toSaveInto + ".arr[i][j] = " + tmp + ".arr[i][j];");
            code.add("}");
            code.add("}");
            code.add(toSaveSizeInto + " = " + voteInputSize + SEMICOLON);
        }
    }

    @Override
    public void visitNotEmptyExpNode(final NotEmptyExpressionNode node) {
        String notEmpty = "not_empty" + getTmpIndex();
        code.add("unsigned int " + notEmpty + SEMICOLON);
        // we will save the result in this variable.
        // 0 for false, everything else for true
        variableNames.push(notEmpty);
        String subResult = "tmp_bool" + getTmpIndex();
        code.add("unsigned int " + subResult + " = 0;");
        NotEmptyContentNode notEmptyNode = new NotEmptyContentNode(
                node.getContext().notEmptyContent(), subResult);
        notEmptyNode.getVisited(this);
        // the result is now saved in votingInput
        code.add(notEmpty + " = " + subResult + SEMICOLON);
        if (node.isTop()) {
            code.add(assumeOrAssert + OPENING_PARENTHESES + notEmpty + ");");
        }
    }

    @Override
    public void visitNotEmptyContentNode(final NotEmptyContentNode node) {
        String subResult = "tmp_elect" + getTmpIndex();
        // create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess() + BLANK
                + subResult + SEMICOLON);
        // we have an intersect beneath
        if (node.getContext().intersectExp() != null) {
            IntersectExpNode intersectNode =
                    new IntersectExpNode(node.getContext().intersectExp(),
                                         subResult);
            intersectNode.getVisited(this);
        } else {
            // we only have an elect
            code.add(subResult + " = "
                    + node.getContext().Elect().getText().toLowerCase() + SEMICOLON);
        }
        String toReturn = node.getVotingOutput();
        // the value is now saved in subResult
        code.add(forLoopHeaderCode("i", "<", "C"));
        code.add(toReturn + " += " + subResult + ".arr[i];");
        code.add("}");
    }
}
