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
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.FalseNode;
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
import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.helperfunctions.ComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.ElectComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.IsElectEmptyHelper;
import edu.pse.beast.api.codegen.helperfunctions.IsVoteEmptyHelper;
import edu.pse.beast.api.codegen.helperfunctions.PerformVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.VoteComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect.ElectIntersectionHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect.ElectPermutationHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect.ElectTupleHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VoteExpHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VoteIntersectionHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VotePermutationHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VoteTupleHelper;
import edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote.VotesumHelper;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class CodeGenASTVisitor implements BooleanAstVisitor {

	public enum Mode {
		ASSUME, ASSERT
	}

	private CodeGenLoopBoundHandler loopBoundHandler;
	private CCodeBlock codeBlock;

	ScopeHandler scopeHandler = new ScopeHandler();

	private Stack<String> expVarNameStack = new Stack<>();
	private Stack<CElectionVotingType> expTypes = new Stack<>();
	private int amtVoteVars = 0;
	private int amtElectVars = 0;

	private Stack<String> booleanVarNameStack = new Stack<>();

	private ElectionTypeCStruct voteArrStruct;
	private VotingInputTypes votingInputType;
	private ElectionTypeCStruct voteResultStruct;
	private VotingOutputTypes votingOutputType;
	private CodeGenOptions options;

	private Mode mode;
	private String assumeAssert;

	private CBMCGeneratedCodeInfo cbmcGeneratedCode;

	public CodeGenASTVisitor(ElectionTypeCStruct voteArrStruct,
			VotingInputTypes votingInputType,
			ElectionTypeCStruct voteResultStruct,
			VotingOutputTypes votingOutputType, CodeGenOptions options,
			CodeGenLoopBoundHandler loopBoundHandler,
			CBMCGeneratedCodeInfo cbmcGeneratedCode) {
		this.voteArrStruct = voteArrStruct;
		this.votingInputType = votingInputType;
		this.voteResultStruct = voteResultStruct;
		this.votingOutputType = votingOutputType;
		this.options = options;
		this.loopBoundHandler = loopBoundHandler;
		this.cbmcGeneratedCode = cbmcGeneratedCode;
	}

	public CCodeBlock getCodeBlock() {
		return codeBlock;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
		if (mode == Mode.ASSUME) {
			assumeAssert = options.getCbmcAssumeName();
		}
		if (mode == Mode.ASSERT) {
			assumeAssert = options.getCbmcAssertName();
		}
	}

	@Override
	public void visitBooleanExpListElementNode(BooleanExpListElementNode node) {
		codeBlock = new CCodeBlock();
		codeBlock.addComment(node.getCompleteCode());
		amtElectVars = 0;
		amtVoteVars = 0;

		node.getFirstChild().getVisited(this);

		String topBoolean = booleanVarNameStack.pop();
		codeBlock.addSnippet(assumeAssert + "(" + topBoolean + ");\n");
	}

	private void visitVoteComparison(ComparisonNode node) {
		String rhsVarName = expVarNameStack.pop();
		String lhsVarName = expVarNameStack.pop();
		String generatedVarName = codeBlock.newVarName("voteCompare");
		String code = VoteComparisonHelper.generateCode(generatedVarName,
				lhsVarName, rhsVarName, voteArrStruct, votingInputType, options,
				node.getComparisonSymbol(), loopBoundHandler);
		codeBlock.addSnippet(code);
		booleanVarNameStack.push(generatedVarName);
	}

	private void visitElectComparison(ComparisonNode node) {
		String rhsVarName = expVarNameStack.pop();
		String lhsVarName = expVarNameStack.pop();
		String generatedVarName = codeBlock.newVarName("electCompare");
		String code = ElectComparisonHelper.generateCode(generatedVarName,
				lhsVarName, rhsVarName, voteResultStruct, votingOutputType,
				options, node.getComparisonSymbol(), loopBoundHandler);
		codeBlock.addSnippet(code);
		booleanVarNameStack.push(generatedVarName);
	}

	@Override
	public void visitComparisonNode(ComparisonNode node) {
		amtElectVars = 0;
		amtVoteVars = 0;
		node.getLhsTypeExp().getVisited(this);
		node.getRhsTypeExp().getVisited(this);
		if (amtVoteVars == 2) {
			visitVoteComparison(node);
		} else if (amtElectVars == 2) {
			visitElectComparison(node);
		} else {
			String rhsVarName = expVarNameStack.pop();
			String lhsVarName = expVarNameStack.pop();

			CElectionVotingType rhsType = expTypes.pop();
			CElectionVotingType lhsType = expTypes.pop();

			String generatedVar = codeBlock.newVarName("comparison");
			codeBlock.addSnippet(ComparisonHelper.generateCode(generatedVar,
					node.getComparisonSymbol(), lhsVarName, rhsVarName, rhsType,
					options, assumeAssert, loopBoundHandler));

			booleanVarNameStack.push(generatedVar);
		}
		amtElectVars = 0;
		amtVoteVars = 0;
	}

	@Override
	public void visitVoteIntersectionNode(VoteIntersectionNode node) {
		String generatedVarName = codeBlock.newVarName("voteIntersection");
		cbmcGeneratedCode.addedGeneratedVotingVar(generatedVarName);

		List<String> varNames = new ArrayList<>();
		for (int number : node.getNumbers()) {
			varNames.add(InitVoteHelper.getVoteVarName(number));
		}

		cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", varNames));

		codeBlock.addSnippet(VoteIntersectionHelper.generateVoteIntersection(
				generatedVarName, varNames, voteArrStruct, votingInputType,
				options, loopBoundHandler));

		expTypes.push(voteArrStruct.getVotingType());
		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
	}

	@Override
	public void visitElectIntersectionNode(ElectIntersectionNode node) {
		String generatedVarName = codeBlock.newVarName("electIntersection");
		cbmcGeneratedCode.addedGeneratedElectVar(generatedVarName);

		List<String> varNames = new ArrayList<>();
		for (int number : node.getNumbers()) {
			varNames.add(PerformVoteHelper.getResultVarName(number));
		}
		cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", varNames));

		codeBlock.addSnippet(ElectIntersectionHelper.generateCode(
				generatedVarName, varNames, voteResultStruct, votingOutputType,
				options, loopBoundHandler));

		expVarNameStack.push(generatedVarName);
		expTypes.push(voteResultStruct.getVotingType());
		amtElectVars++;
	}

	@Override
	public void visitVoteExpNode(VoteExp node) {
		if (node.getAccessingCBMCVars().size() == 0) {
			expVarNameStack.push(InitVoteHelper
					.getVoteVarName(Integer.valueOf(node.getNumber())));
			amtVoteVars++;
		} else {
			String voteVarName = InitVoteHelper
					.getVoteVarName(Integer.valueOf(node.getNumber()));
			String varName = VoteExpHelper.getVarFromVoteAccess(voteVarName,
					node.getAccessingCBMCVars(), options, voteArrStruct);
			expVarNameStack.push(varName);

			CElectionVotingType type = voteArrStruct.getVotingType();

			for (int i = 0; i < node.getAccessingCBMCVars().size(); ++i) {
				type = type.getTypeOneDimLess();
			}

			expTypes.push(type);

			int i = 0;
		}
	}

	@Override
	public void visitElectExpNode(ElectExp node) {
		if (node.getAccessingCBMCVars().size() == 0) {
			expVarNameStack
					.push(PerformVoteHelper.getResultVarName(node.getNumber()));
			expTypes.push(voteResultStruct.getVotingType());
			amtElectVars++;
		}
	}

	@Override
	public void visitVotePermutation(VotePermutationNode node) {
		String generatedVarName = codeBlock.newVarName("votePermutation");
		cbmcGeneratedCode.addedGeneratedVotingVar(generatedVarName);

		String varName = InitVoteHelper.getVoteVarName(node.getVoteNumber());

		cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", varName));

		codeBlock.addSnippet(VotePermutationHelper.generateCode(
				generatedVarName, varName, voteArrStruct, votingInputType,
				options, loopBoundHandler));

		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
	}

	@Override
	public void visitElectPermutation(ElectPermutationNode node) {
		String generatedVarName = codeBlock.newVarName("votePermutation");
		cbmcGeneratedCode.addedGeneratedElectVar(generatedVarName);

		String varName = PerformVoteHelper
				.getResultVarName(node.getElectNumber());

		cbmcGeneratedCode.addInfo(generatedVarName, String.join(" ", varName));

		codeBlock.addSnippet(ElectPermutationHelper.generateCode(
				generatedVarName, varName, voteResultStruct, votingOutputType,
				options, loopBoundHandler));

		expVarNameStack.push(generatedVarName);
		amtElectVars++;
	}

	@Override
	public void visitElectTuple(ElectTupleNode node) {
		String generatedVarName = codeBlock.newVarName("electSequence");
		cbmcGeneratedCode.addedGeneratedElectVar(generatedVarName);

		List<String> electNames = new ArrayList<>();
		for (int number : node.getElectNumbers()) {
			electNames.add(PerformVoteHelper.getResultVarName(number));
		}

		cbmcGeneratedCode.addInfo(generatedVarName,
				String.join(" ", electNames));

		codeBlock.addSnippet(ElectTupleHelper.generateCode(generatedVarName,
				electNames, voteResultStruct, votingOutputType, options,
				loopBoundHandler));

		expVarNameStack.push(generatedVarName);
		amtElectVars++;
	}

	@Override
	public void visitVoteTuple(VoteTupleNode node) {
		String generatedVarName = codeBlock.newVarName("voteSequence");
		cbmcGeneratedCode.addedGeneratedVotingVar(generatedVarName);

		List<String> voteNames = new ArrayList<>();
		for (int number : node.getNumbers()) {
			voteNames.add(InitVoteHelper.getVoteVarName(number));
		}

		cbmcGeneratedCode.addInfo(generatedVarName,
				String.join(" ", voteNames));

		codeBlock.addSnippet(VoteTupleHelper.generateCode(generatedVarName,
				voteNames, voteArrStruct, votingInputType, options,
				loopBoundHandler));
		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
	}

	@Override
	public void visitForAllVotersNode(ForAllNode node) {
		String symbVarName = node.getVar().getName();
		scopeHandler.push();
		scopeHandler.add(node.getVar());

		String code = "for(unsigned int VAR_NAME = 0; VAR_NAME < AMT_VOTERS; ++VAR_NAME) {\n";
		code = code.replaceAll("VAR_NAME", symbVarName);
		code = code.replaceAll("AMT_VOTERS",
				options.getCbmcAmountMaxVotersVarName());

		String varName = codeBlock.newVarName("forAllVoters");
		codeBlock.addAssignment("unsigned int " + varName, "1");
		codeBlock.addSnippet(code);
		node.getFollowingExpNode().getVisited(this);
		codeBlock.addSnippet(varName + " &= " + booleanVarNameStack.pop());
		codeBlock.addSnippet("}\n");
		booleanVarNameStack.push(varName);

		scopeHandler.pop();
	}

	@Override
	public void visitNotNode(NotNode node) {
		node.getNegatedExpNode().getVisited(this);

		String generatedVar = codeBlock.newVarName("not");
		codeBlock.addAssignment("unsigned int " + generatedVar,
				"!" + booleanVarNameStack.pop());
		booleanVarNameStack.push(generatedVar);

	}

	@Override
	public void visitExistsCandidateNode(ThereExistsNode node) {

		String symbolicVarName = node.getDeclaredSymbolicVar().getName();
		scopeHandler.push();
		scopeHandler.add(node.getDeclaredSymbolicVar());

		String boolVarName = codeBlock.newVarName("existsCandidate");
		String code = "for(unsigned int VAR_NAME = 0; VAR_NAME < AMT_CANDIDATES; ++VAR_NAME) {\n";
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
	public void visitSymbolicVarExp(SymbolicVarByNameExp node) {
		expVarNameStack.push(node.getCbmcVar().getName());
		expTypes.push(CElectionVotingType.simple());
	}

	@Override
	public void visitVoteSumExp(VoteSumForCandExp node) {
		String generatedVarName = codeBlock.newVarName("voteSum");
		int voteNumber = node.getVoteNumber();
		String symbolicVarCand = node.getCandCbmcVar().getName();
		String code = VotesumHelper.generateCode(generatedVarName, voteNumber,
				symbolicVarCand, voteArrStruct, votingInputType, options,
				loopBoundHandler);
		codeBlock.addSnippet(code);
		expVarNameStack.add(generatedVarName);
		expTypes.push(CElectionVotingType.simple());
	}

	@Override
	public void visitIntegerExp(IntegerNode node) {
		int heldInteger = node.getInteger();
		expVarNameStack.push(String.valueOf(heldInteger));
		expTypes.push(CElectionVotingType.simple());
	}

	@Override
	public void visitBinaryIntegerExpression(
			BinaryIntegerValuedNode binaryIntegerValuedNode) {
		binaryIntegerValuedNode.getLhs().getVisited(this);
		binaryIntegerValuedNode.getRhs().getVisited(this);
		String rhsNumber = expVarNameStack.pop();
		String lhsNumber = expVarNameStack.pop();

		expTypes.pop();
		expTypes.pop();

		String op = binaryIntegerValuedNode.getRelationSymbol();
		expVarNameStack.push("(" + lhsNumber + op + rhsNumber + ")");
		expTypes.push(CElectionVotingType.simple());
	}

	@Override
	public void visitConstantExp(ConstantExp constantExp) {
		String varName = "";
		int number = constantExp.getNumber();
		switch(constantExp.getVarType()) {
		case VOTER:
			varName = InitVoteHelper.getCurrentAmtVoter(number);
			break;
		case CANDIDATE:
			varName = InitVoteHelper.getCurrentAmtCand(number);
			break;
		case SEAT:
			varName = InitVoteHelper.getCurrentAmtSeat(number);
			break;
		}
		expVarNameStack.push(varName);
		expTypes.push(CElectionVotingType.simple());
	}

	@Override
	public void visitEmptyNode(BooleanExpIsEmptyNode booleanExpIsEmptyNode) {
		booleanExpIsEmptyNode.getInnerNode().getVisited(this);
		String testedVarName = expVarNameStack.pop();
		String generatedVar = codeBlock.newVarName("isEmpty" + testedVarName);
		if (amtElectVars == 1) {
			codeBlock.addSnippet(IsElectEmptyHelper.generateCode(generatedVar,
					testedVarName, voteResultStruct, votingOutputType, options,
					loopBoundHandler));

		} else if (amtVoteVars == 1) {
			codeBlock.addSnippet(IsVoteEmptyHelper.generateCode(generatedVar,
					testedVarName, voteArrStruct, votingInputType, options,
					loopBoundHandler));
		}

		booleanVarNameStack.push(generatedVar);
	}

	@Override
	public void visitBinaryRelationNode(BinaryRelationshipNode node,
			String binaryCombinationSymbol) {
		node.getLHSBooleanExpNode().getVisited(this);
		node.getRHSBooleanExpNode().getVisited(this);

		String rhsVarName = booleanVarNameStack.pop();
		String lhsVarName = booleanVarNameStack.pop();

		String generatedVarName = codeBlock.newVarName("combined");

		Map<String, String> replacementMap = Map.of("VAR_NAME",
				generatedVarName, "LHS_VAR", lhsVarName, "RHS_VAR", rhsVarName);

		String code = null;

		if (binaryCombinationSymbol.equals("&&")) {
			code = "unsigned int VAR_NAME = LHS_VAR && RHS_VAR;\n";
		} else if (binaryCombinationSymbol.equals("||")) {
			code = "unsigned int VAR_NAME = LHS_VAR || RHS_VAR;\n";
		} else if (binaryCombinationSymbol.equals("==>")) {
			code = "unsigned int VAR_NAME = !LHS_VAR || RHS_VAR;\n";
		} else if (binaryCombinationSymbol.equals("<==>")) {
			code = "unsigned int VAR_NAME = (LHS_VAR && RHS_VAR) || (!LHS_VAR && !RHS_VAR);\n";
		}

		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);

		booleanVarNameStack.push(generatedVarName);
		codeBlock.addSnippet(code);
	}

	@Override
	public void visitBooleanExpFalseNode(FalseNode falseNode) {
		String zeroVarBecauseFalse = "0";
		booleanVarNameStack.push(zeroVarBecauseFalse);
	}

	@Override
	public void visitSymbVarByPosExp(SymbVarByPosExp symbVarByPosExp) {
		String generatedVarName = String.valueOf(symbVarByPosExp.getAccessingNumber());
		expTypes.push(CElectionVotingType.simple());
		expVarNameStack.push(generatedVarName);
	}
}
