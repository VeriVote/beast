package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BinaryRelationshipNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpIsEmptyNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ComparisonNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.FalseTrueNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ForAllNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.NotNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ThereExistsNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectPermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectTupleNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VotePermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteTupleNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.BinaryIntegerValuedNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.ConstantExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.IntegerNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.VoteSumForCandExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var.SymbVarByPosExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var.SymbolicVarByNameExp;
import edu.pse.beast.api.codegen.c_code.CCodeBlock;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.helperfunctions.ComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.ElectComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.IsElectEmptyHelper;
import edu.pse.beast.api.codegen.helperfunctions.IsVoteEmptyHelper;
import edu.pse.beast.api.codegen.helperfunctions.PerformVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.VoteComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect.ElectIntersectionHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect.ElectPermutationHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect.ElectTupleHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VoteExpHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VoteIntersectionHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VotePermutationHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VoteTupleHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VotesumHelper;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.paths.PathHandler;

/**
 * Visits the Boolean Exp AST to generate the cbmc code
 * which checks for the property described by the AST.
 *
 * @author Holger Klein
 *
 */
public class CodeGenASTVisitor implements BooleanAstVisitor {
    private static final String EXISTS_CANDIDATE_KEY = "EXISTS_CANDIDATE";
    private static final String FORALL_VOTERS_KEY = "FORALL_VOTERS";

    private static final String NONE = "";
    private static final String BLANK = " ";
    private static final String PAREN_OP = "(";
    private static final String PAREN_CL = ")";
    private static final String BRACE_CL = "}";
    private static final String SEMI = "; ";
    private static final String NOT = "!";
    private static final String IMP = " ==> ";
    private static final String EQUIV = " <==> ";
    private static final String AND = " && ";
    private static final String OR = " || ";
    private static final String EQ = " = ";
    private static final String AND_EQ = " &= ";
    private static final String OR_EQ = " |= ";
    private static final String UINT = "unsigned int ";
    private static final String ZERO = "0";
    private static final String ONE = "1";

    private static final String VOTE_COMPARE = "voteCompare";
    private static final String ELECT_COMPARE = "electCompare";
    private static final String COMPARISON_VAR = "comparison";
    private static final String VOTE_INTERSECTION = "voteIntersection";
    private static final String ELECT_INTERSECTION = "electIntersection";
    private static final String VOTE_PERMUTATION = "votePermutation";
    private static final String ELECT_SEQUENCE = "electSequence";
    private static final String VOTE_SEQUENCE = "voteSequence";
    private static final String FOR_ALL_VOTERS = "forAllVoters";
    private static final String NOT_VAR = "not";
    private static final String EXISTS_CANDIDATE = "existsCandidate";
    private static final String VOTE_SUM = "voteSum";
    private static final String IS_EMPTY = "isEmpty";
    private static final String COMBINED_VAR = "combined";

    private static final String VAR_NAME = "VAR_NAME";
    private static final String LHS_VAR = "LHS_VAR";
    private static final String RHS_VAR = "RHS_VAR";

    private static final String AMOUNT_VOTERS = "AMT_VOTERS";
    private static final String AMOUNT_CANDIDATES = "AMT_CANDIDATES";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public enum Mode {
        ASSUME, ASSERT
    }

    private ScopeHandler scopeHandler = new ScopeHandler();

    private CodeGenLoopBoundHandler loopBoundHandler;
    private CCodeBlock codeBlock;

    private Stack<String> expVarNameStack = new Stack<String>();
    private Stack<CElectionVotingType> expTypes = new Stack<CElectionVotingType>();
    private int amtVoteVars;
    private int amtElectVars;

    private Stack<String> booleanVarNameStack = new Stack<String>();

    private ElectionTypeCStruct voteArrStruct;
    private VotingInputTypes votingInputType;
    private ElectionTypeCStruct voteResultStruct;
    private VotingOutputTypes votingOutputType;
    private CodeGenOptions options;

    private Mode assumeAssertMode;
    private String assumeAssert;

    private CBMCGeneratedCodeInfo cbmcGeneratedCode;

    public CodeGenASTVisitor(final ElectionTypeCStruct voteArrayStruct,
                             final VotingInputTypes inputType,
                             final ElectionTypeCStruct resultStruct,
                             final VotingOutputTypes outputType,
                             final CodeGenOptions codeGenerationOptions,
                             final CodeGenLoopBoundHandler codeGenLoopBoundHandler,
                             final CBMCGeneratedCodeInfo generatedCodeInfo) {
        this.voteArrStruct = voteArrayStruct;
        this.votingInputType = inputType;
        this.voteResultStruct = resultStruct;
        this.votingOutputType = outputType;
        this.options = codeGenerationOptions;
        this.loopBoundHandler = codeGenLoopBoundHandler;
        this.cbmcGeneratedCode = generatedCodeInfo;
    }

    private static String paren(final String s) {
        return PAREN_OP + s + PAREN_CL;
    }

    public static final String getTemplate(final String key,
                                           final Class<?> c) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, NONE, c);
    }

    public final CCodeBlock getCodeBlock() {
        return codeBlock;
    }

    public final Mode getMode() {
        return this.assumeAssertMode;
    }

    public final void setMode(final Mode mode) {
        this.assumeAssertMode = mode;
        if (mode == Mode.ASSUME) {
            assumeAssert = options.getCbmcAssumeName();
        }
        if (mode == Mode.ASSERT) {
            assumeAssert = options.getCbmcAssertName();
        }
    }

    @Override
    public final void visitBooleanExpListElementNode(final BooleanExpListElementNode node) {
        codeBlock = new CCodeBlock();
        codeBlock.addComment(node.getCompleteCode());
        amtElectVars = 0;
        amtVoteVars = 0;
        node.getFirstChild().getVisited(this);
        final String topBoolean = booleanVarNameStack.pop();
        codeBlock.addSnippet(assumeAssert + BLANK + paren(topBoolean) + SEMI.trim());
    }

    private void visitVoteComparison(final ComparisonNode node) {
        final String rhsVarName = expVarNameStack.pop();
        final String lhsVarName = expVarNameStack.pop();
        final String generatedVarName = codeBlock.newVarName(VOTE_COMPARE);
        final VoteComparisonHelper.Comparison comparison =
                new VoteComparisonHelper.Comparison(node.getComparisonSymbol(),
                                                     lhsVarName, rhsVarName);
        final String code =
                VoteComparisonHelper.generateCode(generatedVarName, comparison,
                                                  voteArrStruct, votingInputType,
                                                  options, loopBoundHandler);
        codeBlock.addSnippet(code);
        booleanVarNameStack.push(generatedVarName);
    }

    private void visitElectComparison(final ComparisonNode node) {
        final String rhsVarName = expVarNameStack.pop();
        final String lhsVarName = expVarNameStack.pop();
        final String generatedVarName = codeBlock.newVarName(ELECT_COMPARE);
        final ElectComparisonHelper.Comparison comparison =
                new ElectComparisonHelper.Comparison(node.getComparisonSymbol(),
                                                     lhsVarName, rhsVarName);
        final String code =
                ElectComparisonHelper.generateCode(generatedVarName, comparison,
                                                   voteResultStruct, votingOutputType,
                                                   options, loopBoundHandler);
        codeBlock.addSnippet(code);
        booleanVarNameStack.push(generatedVarName);
    }

    @Override
    public final void visitComparisonNode(final ComparisonNode node) {
        amtElectVars = 0;
        amtVoteVars = 0;
        node.getLhsTypeExp().getVisited(this);
        node.getRhsTypeExp().getVisited(this);
        if (amtVoteVars == 2) {
            visitVoteComparison(node);
        } else if (amtElectVars == 2) {
            visitElectComparison(node);
        } else {
            final String rhsVarName = expVarNameStack.pop();
            final String lhsVarName = expVarNameStack.pop();
            final CElectionVotingType rhsType = expTypes.pop();
            // final CElectionVotingType lhsType = expTypes.pop();

            final String generatedVar = codeBlock.newVarName(COMPARISON_VAR);
            final ComparisonHelper.Comparison comparison =
                    new ComparisonHelper.Comparison(node.getComparisonSymbol(),
                                                    lhsVarName, rhsVarName);
            codeBlock.addSnippet(
                    ComparisonHelper.generateCode(generatedVar, comparison, rhsType,
                                                  options, assumeAssert, loopBoundHandler));
            booleanVarNameStack.push(generatedVar);
        }
        amtElectVars = 0;
        amtVoteVars = 0;
    }

    @Override
    public final void visitVoteIntersectionNode(final VoteIntersectionNode node) {
        final String generatedVarName = codeBlock.newVarName(VOTE_INTERSECTION);
        cbmcGeneratedCode.addedGeneratedVotingVar(generatedVarName);
        final List<String> varNames = new ArrayList<String>();
        for (final int number : node.getNumbers()) {
            varNames.add(SymbVarInitVoteHelper.getVoteVarName(options, number));
        }
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(BLANK, varNames));
        codeBlock.addSnippet(
                VoteIntersectionHelper.generateVoteIntersection(generatedVarName, varNames,
                                                                voteArrStruct, votingInputType,
                                                                options, loopBoundHandler));
        expTypes.push(voteArrStruct.getVotingType());
        expVarNameStack.push(generatedVarName);
        amtVoteVars++;
    }

    @Override
    public final void visitElectIntersectionNode(final ElectIntersectionNode node) {
        final String generatedVarName = codeBlock.newVarName(ELECT_INTERSECTION);
        cbmcGeneratedCode.addedGeneratedElectVar(generatedVarName);

        final List<String> varNames = new ArrayList<String>();
        for (final int number : node.getNumbers()) {
            varNames.add(PerformVoteHelper.getResultVarName(number));
        }
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(BLANK, varNames));
        codeBlock.addSnippet(
                ElectIntersectionHelper.generateCode(generatedVarName, varNames,
                                                     voteResultStruct, votingOutputType,
                                                     options, loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        expTypes.push(voteResultStruct.getVotingType());
        amtElectVars++;
    }

    @Override
    public final void visitVoteExpNode(final VoteExp node) {
        if (node.getAccessingCBMCVars().size() == 0) {
            expVarNameStack.push(
                    SymbVarInitVoteHelper.getVoteVarName(options,
                                                         Integer.valueOf(node.getNumber())));
            amtVoteVars++;
        } else {
            final String voteVarName =
                    SymbVarInitVoteHelper.getVoteVarName(options,
                                                         Integer.valueOf(node.getNumber()));
            final String varName =
                    VoteExpHelper.getVarFromVoteAccess(voteVarName,
                                                       node.getAccessingCBMCVars(),
                                                       options, voteArrStruct);
            expVarNameStack.push(varName);

            CElectionVotingType type = voteArrStruct.getVotingType();
            for (int i = 0; i < node.getAccessingCBMCVars().size(); ++i) {
                type = type.getTypeOneDimLess();
            }
            expTypes.push(type);
        }
    }

    @Override
    public final void visitElectExpNode(final ElectExp node) {
        if (node.getAccessingCBMCVars().size() == 0) {
            expVarNameStack.push(PerformVoteHelper.getResultVarName(node.getNumber()));
            expTypes.push(voteResultStruct.getVotingType());
            amtElectVars++;
        }
    }

    @Override
    public final void visitVotePermutation(final VotePermutationNode node) {
        final String generatedVarName = codeBlock.newVarName(VOTE_PERMUTATION);
        cbmcGeneratedCode.addedGeneratedVotingVar(generatedVarName);

        final String varName =
                SymbVarInitVoteHelper.getVoteVarName(options, node.getVoteNumber());
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(BLANK, varName));
        codeBlock.addSnippet(
                VotePermutationHelper.generateCode(generatedVarName, varName,
                                                   voteArrStruct, votingInputType,
                                                   options, loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        amtVoteVars++;
    }

    @Override
    public final void visitElectPermutation(final ElectPermutationNode node) {
        final String generatedVarName = codeBlock.newVarName(VOTE_PERMUTATION);
        cbmcGeneratedCode.addedGeneratedElectVar(generatedVarName);
        final String varName =
                PerformVoteHelper.getResultVarName(node.getElectNumber());
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(BLANK, varName));
        codeBlock.addSnippet(
                ElectPermutationHelper.generateCode(generatedVarName, varName,
                                                    voteResultStruct, votingOutputType,
                                                    options, loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        amtElectVars++;
    }

    @Override
    public final void visitElectTuple(final ElectTupleNode node) {
        final String generatedVarName = codeBlock.newVarName(ELECT_SEQUENCE);
        cbmcGeneratedCode.addedGeneratedElectVar(generatedVarName);

        final List<String> electNames = new ArrayList<String>();
        for (final int number : node.getElectNumbers()) {
            electNames.add(PerformVoteHelper.getResultVarName(number));
        }
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(BLANK, electNames));
        codeBlock.addSnippet(
                ElectTupleHelper.generateCode(generatedVarName, electNames,
                                              voteResultStruct, votingOutputType,
                                              options, loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        amtElectVars++;
    }

    @Override
    public final void visitVoteTuple(final VoteTupleNode node) {
        final String generatedVarName = codeBlock.newVarName(VOTE_SEQUENCE);
        cbmcGeneratedCode.addedGeneratedVotingVar(generatedVarName);

        final List<String> voteNames = new ArrayList<String>();
        for (final int number : node.getNumbers()) {
            voteNames.add(SymbVarInitVoteHelper.getVoteVarName(options, number));
        }
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(BLANK, voteNames));

        codeBlock.addSnippet(
                VoteTupleHelper.generateCode(generatedVarName,
                                             voteNames, voteArrStruct,
                                             votingInputType, options,
                                             loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        amtVoteVars++;
    }

    @Override
    public final void visitForAllVotersNode(final ForAllNode node) {
        final String symbVarName = node.getVar().getName();
        scopeHandler.push();
        scopeHandler.add(node.getVar());

        String code = getTemplate(FORALL_VOTERS_KEY, this.getClass());
        code = code.replaceAll(VAR_NAME, symbVarName);
        code = code.replaceAll(AMOUNT_VOTERS,
                options.getCbmcAmountMaxVotersVarName());

        final String varName = codeBlock.newVarName(FOR_ALL_VOTERS);
        codeBlock.addAssignment(UINT + varName, ONE);
        codeBlock.addSnippet(code);
        node.getFollowingExpNode().getVisited(this);
        codeBlock.addSnippet(varName + AND_EQ + booleanVarNameStack.pop());
        codeBlock.addSnippet(BRACE_CL);
        booleanVarNameStack.push(varName);
        scopeHandler.pop();
    }

    @Override
    public final void visitNotNode(final NotNode node) {
        node.getNegatedExpNode().getVisited(this);
        final String generatedVar = codeBlock.newVarName(NOT_VAR);
        codeBlock.addAssignment(UINT + generatedVar,
                                NOT + booleanVarNameStack.pop());
        booleanVarNameStack.push(generatedVar);
    }

    @Override
    public final void visitExistsCandidateNode(final ThereExistsNode node) {
        final String symbolicVarName = node.getDeclaredSymbolicVar().getName();
        scopeHandler.push();
        scopeHandler.add(node.getDeclaredSymbolicVar());

        final String boolVarName = codeBlock.newVarName(EXISTS_CANDIDATE);
        String code = getTemplate(EXISTS_CANDIDATE_KEY, this.getClass());
        code = code.replaceAll(VAR_NAME, symbolicVarName);
        code = code.replaceAll(AMOUNT_CANDIDATES,
                               options.getCbmcAmountMaxCandsVarName());

        codeBlock.addSnippet(UINT + boolVarName + EQ + ZERO + SEMI.trim());
        codeBlock.addSnippet(code);
        node.getFollowingExpNode().getVisited(this);
        codeBlock.addSnippet(
                boolVarName + OR_EQ + booleanVarNameStack.pop() + SEMI.trim());
        codeBlock.addSnippet(BRACE_CL);
        booleanVarNameStack.push(boolVarName);
        scopeHandler.pop();
    }

    @Override
    public final void visitSymbolicVarExp(final SymbolicVarByNameExp node) {
        expVarNameStack.push(node.getCbmcVar().getName());
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public final void visitVoteSumExp(final VoteSumForCandExp node) {
        final String generatedVarName = codeBlock.newVarName(VOTE_SUM);
        final int voteNumber = node.getVoteNumber();
        final String symbolicVarCand = node.getCandCbmcVar().getName();
        final VotesumHelper.VoteArrayAccess access =
                new VotesumHelper.VoteArrayAccess(voteArrStruct, voteNumber, symbolicVarCand);
        final String code =
                VotesumHelper.generateCode(generatedVarName, access,
                                           votingInputType, options,
                                           loopBoundHandler);
        codeBlock.addSnippet(code);
        expVarNameStack.add(generatedVarName);
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public final void visitIntegerExp(final IntegerNode node) {
        final int heldInteger = node.getInteger();
        expVarNameStack.push(String.valueOf(heldInteger));
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public final void visitBinaryIntegerExpression(final BinaryIntegerValuedNode binaryIntValNode) {
        binaryIntValNode.getLhs().getVisited(this);
        binaryIntValNode.getRhs().getVisited(this);
        final String rhsNumber = expVarNameStack.pop();
        final String lhsNumber = expVarNameStack.pop();

        expTypes.pop();
        expTypes.pop();

        final String op = binaryIntValNode.getRelationSymbol();
        expVarNameStack.push(paren(lhsNumber + op + rhsNumber));
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public final void visitConstantExp(final ConstantExp constantExp) {
        final String varName;
        final int number = constantExp.getNumber();
        switch (constantExp.getVarType()) {
        case VOTER:
            varName = SymbVarInitVoteHelper.getCurrentAmtVoter(options, number);
            break;
        case CANDIDATE:
            varName = SymbVarInitVoteHelper.getCurrentAmtCand(options, number);
            break;
        case SEAT:
            varName = SymbVarInitVoteHelper.getCurrentAmtSeat(options, number);
            break;
        default:
            varName = NONE;
        }
        expVarNameStack.push(varName);
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public final void visitEmptyNode(final BooleanExpIsEmptyNode booleanExpIsEmptyNode) {
        booleanExpIsEmptyNode.getInnerNode().getVisited(this);
        final String testedVarName = expVarNameStack.pop();
        final String generatedVar = codeBlock.newVarName(IS_EMPTY + testedVarName);
        if (amtElectVars == 1) {
            codeBlock.addSnippet(
                    IsElectEmptyHelper.generateCode(generatedVar,
                                                    testedVarName, voteResultStruct,
                                                    votingOutputType, options,
                                                    loopBoundHandler));

        } else if (amtVoteVars == 1) {
            codeBlock.addSnippet(
                    IsVoteEmptyHelper.generateCode(generatedVar,
                                                   testedVarName, voteArrStruct,
                                                   votingInputType, options,
                                                   loopBoundHandler));
        }

        booleanVarNameStack.push(generatedVar);
    }

    @Override
    public final void visitBinaryRelationNode(final BinaryRelationshipNode node,
                                              final String binaryCombinationSymbol) {
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);

        final String rhsVarName = booleanVarNameStack.pop();
        final String lhsVarName = booleanVarNameStack.pop();

        final String generatedVarName = codeBlock.newVarName(COMBINED_VAR);

        final Map<String, String> replacementMap =
                Map.of(VAR_NAME, generatedVarName,
                       LHS_VAR, lhsVarName,
                       RHS_VAR, rhsVarName);

        String code = UINT + VAR_NAME + EQ;
        if (AND.trim().equals(binaryCombinationSymbol)
                || OR.trim().equals(binaryCombinationSymbol)) {
            code += LHS_VAR + binaryCombinationSymbol + RHS_VAR;
        } else if (IMP.trim().equals(binaryCombinationSymbol)) {
            code += NOT + LHS_VAR + OR + RHS_VAR;
        } else if (EQUIV.trim().equals(binaryCombinationSymbol)) {
            code += paren(LHS_VAR + AND + RHS_VAR)
                    + OR + paren(NOT + LHS_VAR + AND + NOT + RHS_VAR);
        }
        code += SEMI.trim();

        code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
        booleanVarNameStack.push(generatedVarName);
        codeBlock.addSnippet(code);
    }

    @Override
    public final void visitSymbVarByPosExp(final SymbVarByPosExp symbVarByPosExp) {
        final String generatedVarName =
                String.valueOf(symbVarByPosExp.getAccessingNumber());
        expTypes.push(CElectionVotingType.simple());
        expVarNameStack.push(generatedVarName);
    }

    @Override
    public final void visitBooleanExpFalseTrueNode(final FalseTrueNode falseTrueNode) {
        if (falseTrueNode.getFalseOrTrue()) {
            booleanVarNameStack.push(ONE);
        } else {
            booleanVarNameStack.push(ZERO);
        }
    }
}
