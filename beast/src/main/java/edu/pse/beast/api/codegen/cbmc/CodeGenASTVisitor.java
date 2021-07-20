package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
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

/**
 * Visits the Boolean Exp AST to generate the cbmc code
 * which checks for the property described by the AST.
 *
 * @author Holger Klein
 *
 */
public class CodeGenASTVisitor implements BooleanAstVisitor {
    public enum Mode {
        ASSUME, ASSERT
    }

    private ScopeHandler scopeHandler = new ScopeHandler();

    private CodeGenLoopBoundHandler loopBoundHandler;
    private CCodeBlock codeBlock;

    private Stack<String> expVarNameStack = new Stack<>();
    private Stack<CElectionVotingType> expTypes = new Stack<>();
    private int amtVoteVars;
    private int amtElectVars;

    private Stack<String> booleanVarNameStack = new Stack<>();

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

    public CCodeBlock getCodeBlock() {
        return codeBlock;
    }

    public void setMode(final Mode mode) {
        this.assumeAssertMode = mode;
        if (mode == Mode.ASSUME) {
            assumeAssert = options.getCbmcAssumeName();
        }
        if (mode == Mode.ASSERT) {
            assumeAssert = options.getCbmcAssertName();
        }
    }

    @Override
    public void visitBooleanExpListElementNode(final BooleanExpListElementNode node) {
        codeBlock = new CCodeBlock();
        codeBlock.addComment(node.getCompleteCode());
        amtElectVars = 0;
        amtVoteVars = 0;
        node.getFirstChild().getVisited(this);
        final String topBoolean = booleanVarNameStack.pop();
        codeBlock.addSnippet(assumeAssert + "(" + topBoolean + ");\n");
    }

    private void visitVoteComparison(final ComparisonNode node) {
        final String rhsVarName = expVarNameStack.pop();
        final String lhsVarName = expVarNameStack.pop();
        final String generatedVarName = codeBlock.newVarName("voteCompare");
        final String code =
                VoteComparisonHelper.generateCode(generatedVarName,
                                                  lhsVarName, rhsVarName,
                                                  voteArrStruct, votingInputType,
                                                  options, node.getComparisonSymbol(),
                                                  loopBoundHandler);
        codeBlock.addSnippet(code);
        booleanVarNameStack.push(generatedVarName);
    }

    private void visitElectComparison(final ComparisonNode node) {
        final String rhsVarName = expVarNameStack.pop();
        final String lhsVarName = expVarNameStack.pop();
        final String generatedVarName = codeBlock.newVarName("electCompare");
        final String code =
                ElectComparisonHelper.generateCode(generatedVarName,
                                                   lhsVarName, rhsVarName,
                                                   voteResultStruct, votingOutputType,
                                                   options, node.getComparisonSymbol(),
                                                   loopBoundHandler);
        codeBlock.addSnippet(code);
        booleanVarNameStack.push(generatedVarName);
    }

    @Override
    public void visitComparisonNode(final ComparisonNode node) {
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

            final String generatedVar = codeBlock.newVarName("comparison");
            codeBlock.addSnippet(
                    ComparisonHelper.generateCode(generatedVar,
                                                  node.getComparisonSymbol(),
                                                  lhsVarName, rhsVarName, rhsType,
                                                  options, assumeAssert,
                                                  loopBoundHandler));
            booleanVarNameStack.push(generatedVar);
        }
        amtElectVars = 0;
        amtVoteVars = 0;
    }

    @Override
    public void visitVoteIntersectionNode(final VoteIntersectionNode node) {
        final String generatedVarName = codeBlock.newVarName("voteIntersection");
        cbmcGeneratedCode.addedGeneratedVotingVar(generatedVarName);
        final List<String> varNames = new ArrayList<>();
        for (final int number : node.getNumbers()) {
            varNames.add(SymbVarInitVoteHelper.getVoteVarName(number));
        }
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", varNames));
        codeBlock.addSnippet(
                VoteIntersectionHelper.generateVoteIntersection(generatedVarName, varNames,
                                                                voteArrStruct, votingInputType,
                                                                options, loopBoundHandler));
        expTypes.push(voteArrStruct.getVotingType());
        expVarNameStack.push(generatedVarName);
        amtVoteVars++;
    }

    @Override
    public void visitElectIntersectionNode(final ElectIntersectionNode node) {
        final String generatedVarName = codeBlock.newVarName("electIntersection");
        cbmcGeneratedCode.addedGeneratedElectVar(generatedVarName);

        final List<String> varNames = new ArrayList<>();
        for (final int number : node.getNumbers()) {
            varNames.add(PerformVoteHelper.getResultVarName(number));
        }
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", varNames));
        codeBlock.addSnippet(
                ElectIntersectionHelper.generateCode(generatedVarName, varNames,
                                                     voteResultStruct, votingOutputType,
                                                     options, loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        expTypes.push(voteResultStruct.getVotingType());
        amtElectVars++;
    }

    @Override
    public void visitVoteExpNode(final VoteExp node) {
        if (node.getAccessingCBMCVars().size() == 0) {
            expVarNameStack.push(
                    SymbVarInitVoteHelper.getVoteVarName(Integer.valueOf(node.getNumber())));
            amtVoteVars++;
        } else {
            final String voteVarName =
                    SymbVarInitVoteHelper.getVoteVarName(Integer.valueOf(node.getNumber()));
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
    public void visitElectExpNode(final ElectExp node) {
        if (node.getAccessingCBMCVars().size() == 0) {
            expVarNameStack.push(PerformVoteHelper.getResultVarName(node.getNumber()));
            expTypes.push(voteResultStruct.getVotingType());
            amtElectVars++;
        }
    }

    @Override
    public void visitVotePermutation(final VotePermutationNode node) {
        final String generatedVarName = codeBlock.newVarName("votePermutation");
        cbmcGeneratedCode.addedGeneratedVotingVar(generatedVarName);

        final String varName =
                SymbVarInitVoteHelper.getVoteVarName(node.getVoteNumber());
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", varName));
        codeBlock.addSnippet(
                VotePermutationHelper.generateCode(generatedVarName, varName,
                                                   voteArrStruct, votingInputType,
                                                   options, loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        amtVoteVars++;
    }

    @Override
    public void visitElectPermutation(final ElectPermutationNode node) {
        final String generatedVarName = codeBlock.newVarName("votePermutation");
        cbmcGeneratedCode.addedGeneratedElectVar(generatedVarName);
        final String varName =
                PerformVoteHelper.getResultVarName(node.getElectNumber());
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", varName));
        codeBlock.addSnippet(
                ElectPermutationHelper.generateCode(generatedVarName, varName,
                                                    voteResultStruct, votingOutputType,
                                                    options, loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        amtElectVars++;
    }

    @Override
    public void visitElectTuple(final ElectTupleNode node) {
        final String generatedVarName = codeBlock.newVarName("electSequence");
        cbmcGeneratedCode.addedGeneratedElectVar(generatedVarName);

        final List<String> electNames = new ArrayList<>();
        for (final int number : node.getElectNumbers()) {
            electNames.add(PerformVoteHelper.getResultVarName(number));
        }
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", electNames));
        codeBlock.addSnippet(
                ElectTupleHelper.generateCode(generatedVarName, electNames,
                                              voteResultStruct, votingOutputType,
                                              options, loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        amtElectVars++;
    }

    @Override
    public void visitVoteTuple(final VoteTupleNode node) {
        final String generatedVarName = codeBlock.newVarName("voteSequence");
        cbmcGeneratedCode.addedGeneratedVotingVar(generatedVarName);

        final List<String> voteNames = new ArrayList<>();
        for (final int number : node.getNumbers()) {
            voteNames.add(SymbVarInitVoteHelper.getVoteVarName(number));
        }
        cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", voteNames));

        codeBlock.addSnippet(
                VoteTupleHelper.generateCode(generatedVarName,
                                             voteNames, voteArrStruct,
                                             votingInputType, options,
                                             loopBoundHandler));
        expVarNameStack.push(generatedVarName);
        amtVoteVars++;
    }

    @Override
    public void visitForAllVotersNode(final ForAllNode node) {
        final String symbVarName = node.getVar().getName();
        scopeHandler.push();
        scopeHandler.add(node.getVar());

        String code = "for(unsigned int VAR_NAME = 0; VAR_NAME < AMT_VOTERS; ++VAR_NAME) {\n";
        code = code.replaceAll("VAR_NAME", symbVarName);
        code = code.replaceAll("AMT_VOTERS",
                options.getCbmcAmountMaxVotersVarName());

        final String varName = codeBlock.newVarName("forAllVoters");
        codeBlock.addAssignment("unsigned int " + varName, "1");
        codeBlock.addSnippet(code);
        node.getFollowingExpNode().getVisited(this);
        codeBlock.addSnippet(varName + " &= " + booleanVarNameStack.pop());
        codeBlock.addSnippet("}\n");
        booleanVarNameStack.push(varName);
        scopeHandler.pop();
    }

    @Override
    public void visitNotNode(final NotNode node) {
        node.getNegatedExpNode().getVisited(this);
        final String generatedVar = codeBlock.newVarName("not");
        codeBlock.addAssignment("unsigned int " + generatedVar,
                                "!" + booleanVarNameStack.pop());
        booleanVarNameStack.push(generatedVar);
    }

    @Override
    public void visitExistsCandidateNode(final ThereExistsNode node) {
        final String symbolicVarName = node.getDeclaredSymbolicVar().getName();
        scopeHandler.push();
        scopeHandler.add(node.getDeclaredSymbolicVar());

        final String boolVarName = codeBlock.newVarName("existsCandidate");
        String code =
                "for(unsigned int VAR_NAME = 0; VAR_NAME < AMT_CANDIDATES; ++VAR_NAME) {\n";
        code = code.replaceAll("VAR_NAME", symbolicVarName);
        code = code.replaceAll("AMT_CANDIDATES",
                               options.getCbmcAmountMaxCandsVarName());

        codeBlock.addSnippet("unsigned int " + boolVarName + " = 0;");
        codeBlock.addSnippet(code);
        node.getFollowingExpNode().getVisited(this);
        codeBlock.addSnippet(
                boolVarName + " |= " + booleanVarNameStack.pop() + ";");
        codeBlock.addSnippet("}");
        booleanVarNameStack.push(boolVarName);
        scopeHandler.pop();
    }

    @Override
    public void visitSymbolicVarExp(final SymbolicVarByNameExp node) {
        expVarNameStack.push(node.getCbmcVar().getName());
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public void visitVoteSumExp(final VoteSumForCandExp node) {
        final String generatedVarName = codeBlock.newVarName("voteSum");
        final int voteNumber = node.getVoteNumber();
        final String symbolicVarCand = node.getCandCbmcVar().getName();
        final String code =
                VotesumHelper.generateCode(generatedVarName, voteNumber,
                                           symbolicVarCand, voteArrStruct,
                                           votingInputType, options,
                                           loopBoundHandler);
        codeBlock.addSnippet(code);
        expVarNameStack.add(generatedVarName);
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public void visitIntegerExp(final IntegerNode node) {
        final int heldInteger = node.getInteger();
        expVarNameStack.push(String.valueOf(heldInteger));
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public void visitBinaryIntegerExpression(final BinaryIntegerValuedNode binaryIntValNode) {
        binaryIntValNode.getLhs().getVisited(this);
        binaryIntValNode.getRhs().getVisited(this);
        final String rhsNumber = expVarNameStack.pop();
        final String lhsNumber = expVarNameStack.pop();

        expTypes.pop();
        expTypes.pop();

        final String op = binaryIntValNode.getRelationSymbol();
        expVarNameStack.push("(" + lhsNumber + op + rhsNumber + ")");
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public void visitConstantExp(final ConstantExp constantExp) {
        final String varName;
        final int number = constantExp.getNumber();
        switch (constantExp.getVarType()) {
        case VOTER:
            varName = SymbVarInitVoteHelper.getCurrentAmtVoter(number);
            break;
        case CANDIDATE:
            varName = SymbVarInitVoteHelper.getCurrentAmtCand(number);
            break;
        case SEAT:
            varName = SymbVarInitVoteHelper.getCurrentAmtSeat(number);
            break;
        default:
            varName = "";
        }
        expVarNameStack.push(varName);
        expTypes.push(CElectionVotingType.simple());
    }

    @Override
    public void visitEmptyNode(final BooleanExpIsEmptyNode booleanExpIsEmptyNode) {
        booleanExpIsEmptyNode.getInnerNode().getVisited(this);
        final String testedVarName = expVarNameStack.pop();
        final String generatedVar = codeBlock.newVarName("isEmpty" + testedVarName);
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
    public void visitBinaryRelationNode(final BinaryRelationshipNode node,
                                        final String binaryCombinationSymbol) {
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);

        final String rhsVarName = booleanVarNameStack.pop();
        final String lhsVarName = booleanVarNameStack.pop();

        final String generatedVarName = codeBlock.newVarName("combined");

        final Map<String, String> replacementMap =
                Map.of("VAR_NAME", generatedVarName,
                       "LHS_VAR", lhsVarName,
                       "RHS_VAR", rhsVarName);
        String code = null;

        if ("&&".equals(binaryCombinationSymbol)) {
            code = "unsigned int VAR_NAME = LHS_VAR && RHS_VAR;\n";
        } else if ("||".equals(binaryCombinationSymbol)) {
            code = "unsigned int VAR_NAME = LHS_VAR || RHS_VAR;\n";
        } else if ("==>".equals(binaryCombinationSymbol)) {
            code = "unsigned int VAR_NAME = !LHS_VAR || RHS_VAR;\n";
        } else if ("<==>".equals(binaryCombinationSymbol)) {
            code = "unsigned int VAR_NAME = (LHS_VAR && RHS_VAR) || (!LHS_VAR && !RHS_VAR);\n";
        }
        code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);

        booleanVarNameStack.push(generatedVarName);
        codeBlock.addSnippet(code);
    }

    @Override
    public void visitSymbVarByPosExp(final SymbVarByPosExp symbVarByPosExp) {
        final String generatedVarName =
                String.valueOf(symbVarByPosExp.getAccessingNumber());
        expTypes.push(CElectionVotingType.simple());
        expVarNameStack.push(generatedVarName);
    }

    @Override
    public void visitBooleanExpFalseTrueNode(final FalseTrueNode falseTrueNode) {
        if (falseTrueNode.getFalseOrTrue()) {
            booleanVarNameStack.push("1");
        } else {
            booleanVarNameStack.push("0");
        }
    }
}
