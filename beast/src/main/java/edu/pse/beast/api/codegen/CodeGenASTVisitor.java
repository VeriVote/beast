package edu.pse.beast.api.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import edu.pse.beast.api.codegen.booleanExpAst.BinaryCombinationSymbols;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpIsEmptyNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectPermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectTupleNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VotePermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteTupleNode;
import edu.pse.beast.api.codegen.c_code.CCodeBlock;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.helperfunctions.ComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.ElectComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.ElectIntersectionHelper;
import edu.pse.beast.api.codegen.helperfunctions.ElectPermutationHelper;
import edu.pse.beast.api.codegen.helperfunctions.ElectTupleHelper;
import edu.pse.beast.api.codegen.helperfunctions.IsElectEmptyHelper;
import edu.pse.beast.api.codegen.helperfunctions.IsVoteEmptyHelper;
import edu.pse.beast.api.codegen.helperfunctions.VoteComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.VoteExpHelper;
import edu.pse.beast.api.codegen.helperfunctions.VoteIntersectionHelper;
import edu.pse.beast.api.codegen.helperfunctions.VotePermutationHelper;
import edu.pse.beast.api.codegen.helperfunctions.VoteTupleHelper;
import edu.pse.beast.api.codegen.helperfunctions.VotesumHelper;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BinaryRelationshipNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.NotNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.ConstantExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.VoteSumForCandExp;

public class CodeGenASTVisitor implements BooleanAstVisitor {

	public enum Mode {
		ASSUME, ASSERT
	}

	private LoopBoundHandler loopBoundHandler;
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


	public CodeGenASTVisitor(
			ElectionTypeCStruct voteArrStruct,
			VotingInputTypes votingInputType,
			ElectionTypeCStruct voteResultStruct,
			VotingOutputTypes votingOutputType,
			CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {
		this.voteArrStruct = voteArrStruct;
		this.votingInputType = votingInputType;
		this.voteResultStruct = voteResultStruct;
		this.votingOutputType = votingOutputType;
		this.options = options;
		this.loopBoundHandler = loopBoundHandler;
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
		codeBlock.addSnippet(assumeAssert + "(" + topBoolean + ")");
	}

	private void visitVoteComparison(
			ComparisonNode node) {
		String rhsVarName = expVarNameStack.pop();
		String lhsVarName = expVarNameStack.pop();
		String generatedVarName = codeBlock.newVarName("voteCompare");
		String code = VoteComparisonHelper.generateCode(
				generatedVarName, 
				lhsVarName, rhsVarName, 
				voteArrStruct, 
				votingInputType, 
				options, 
				node.getComparisonSymbol().getCStringRep(),
				loopBoundHandler);
		codeBlock.addSnippet(code);
		booleanVarNameStack.push(generatedVarName);
	}
	
	private void visitElectComparison(
			ComparisonNode node) {
		String rhsVarName = expVarNameStack.pop();
		String lhsVarName = expVarNameStack.pop();
		String generatedVarName = codeBlock.newVarName("electCompare");
		String code = ElectComparisonHelper.generateCode(
				generatedVarName, 
				lhsVarName, rhsVarName, 
				voteResultStruct, 
				votingOutputType, 
				options, 
				node.getComparisonSymbol().getCStringRep(),
				loopBoundHandler);
		codeBlock.addSnippet(code);
		booleanVarNameStack.push(generatedVarName);
	}

	@Override
	public void visitComparisonNode(ComparisonNode node) {
		node.getLhsTypeExp().getVisited(this);
		node.getRhsTypeExp().getVisited(this);
		if (amtVoteVars == 2) {
			visitVoteComparison(node);
			amtVoteVars = 0;
		} else if (amtElectVars == 2) {
			visitElectComparison(node);
			amtElectVars = 0;
		} else {
			String rhsVarName = expVarNameStack.pop();
			String lhsVarName = expVarNameStack.pop();

			CElectionVotingType rhsType = expTypes.pop();
			CElectionVotingType lhsType = expTypes.pop();	
			
			String generatedVar = codeBlock.newVarName("comparison");
			codeBlock.addSnippet(ComparisonHelper.generateCode(
					generatedVar,
					node.getComparisonSymbol().getCStringRep(), 
					lhsVarName, 
					rhsVarName,
					rhsType, 
					options, 
					assumeAssert, 
					loopBoundHandler));
			
			booleanVarNameStack.push(generatedVar);
		}
	}

	@Override
	public void visitVoteIntersectionNode(VoteIntersectionNode node) {
		String generatedVarName = codeBlock.newVarName("voteIntersection");

		List<String> varNames = new ArrayList<>();
		for (int number : node.getNumbers()) {
			varNames.add(
					"voteNUMBER".replaceAll("NUMBER", String.valueOf(number)));
		}

		codeBlock.addSnippet(
				VoteIntersectionHelper.generateVoteIntersection(
						generatedVarName,
						varNames, 
						voteArrStruct, 
						votingInputType,
						options, 
						loopBoundHandler));
		
		expTypes.push(voteArrStruct.getVotingType());
		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
	}
	
	@Override
	public void visitElectIntersectionNode(ElectIntersectionNode node) {
		String generatedVarName = codeBlock.newVarName("electIntersection");

		List<String> varNames = new ArrayList<>();
		for (int number : node.getNumbers()) {
			varNames.add(
					"electNUMBER".replaceAll("NUMBER", String.valueOf(number)));
		}

		codeBlock.addSnippet(
					ElectIntersectionHelper.generateCode(
							generatedVarName,
							varNames,
							voteResultStruct, 
							votingOutputType, 
							options, 
							loopBoundHandler)
				);

		expVarNameStack.push(generatedVarName);
		expTypes.push(voteResultStruct.getVotingType());
		amtElectVars++;
	}

	@Override
	public void visitVoteExpNode(VoteExp node) {
		if (node.getAccessingCBMCVars().size() == 0) {
			expVarNameStack.push("vote" + node.getCount());
			amtVoteVars++;
		} else {
			String voteVarName = "voteNUMBER".replaceAll("NUMBER",
					node.getVoteNumber());
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
		if (node.getAccessingVars().length == 0) {
			expVarNameStack.push("elect" + node.getCount());
			expTypes.push(voteResultStruct.getVotingType());
			amtElectVars++;
		}
	}

	@Override
	public void visitVotePermutation(VotePermutationNode node) {
		String generatedVarName = codeBlock.newVarName("votePermutation");
		String varName = "voteNUMBER".replaceAll("NUMBER",
				String.valueOf(node.getVoteNumber()));

		codeBlock.addSnippet(
				VotePermutationHelper.generateCode(
						generatedVarName,
						varName, 
						voteArrStruct, 
						votingInputType, 
						options, 
						loopBoundHandler));

		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
	}
	
	@Override
	public void visitElectPermutation(ElectPermutationNode node) {
		String generatedVarName = codeBlock.newVarName("votePermutation");
		String varName = "electNUMBER".replaceAll("NUMBER",
				String.valueOf(node.getElectNumber()));
		
		codeBlock.addSnippet(
					ElectPermutationHelper.generateCode(
							generatedVarName, 
							varName, 
							voteResultStruct,
							votingOutputType,
							options, 
							loopBoundHandler)
				);

		expVarNameStack.push(generatedVarName);
		amtElectVars++;
	}
	
	@Override
	public void visitElectTuple(ElectTupleNode node) {
		String generatedVarName = codeBlock.newVarName("electSequence");
		
		List<String> electNames = new ArrayList<>();		
		for (int number : node.getElectNumbers()) {
			electNames.add("electNUMBER".replaceAll("NUMBER", String.valueOf(number)));
		}
		
		codeBlock.addSnippet(ElectTupleHelper.generateCode(
				generatedVarName,
				electNames, 
				voteResultStruct, 
				votingOutputType,
				options, 
				loopBoundHandler));
		
		expVarNameStack.push(generatedVarName);
		amtElectVars++;
	}

	@Override
	public void visitVoteTuple(VoteTupleNode node) {
		String generatedVarName = codeBlock.newVarName("voteSequence");
		
		List<String> voteNames = new ArrayList<>();		
		for (int number : node.getNumbers()) {
			voteNames.add(
					"voteNUMBER".replaceAll("NUMBER", String.valueOf(number)));
		}

		codeBlock.addSnippet(
				VoteTupleHelper.generateCode(
						generatedVarName,
						voteNames, 
						voteArrStruct, 
						votingInputType, 
						options, 
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
				options.getCbmcAmountVotersVarName());

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

		String symbolicVarName = node.getVar().getName();
		scopeHandler.push();
		scopeHandler.add(node.getVar());

		String boolVarName = codeBlock.newVarName("existsCandidate");
		String code = "for(unsigned int VAR_NAME = 0; VAR_NAME < AMT_CANDIDATES; ++VAR_NAME) {\n";
		code = code.replaceAll("VAR_NAME", symbolicVarName);
		code = code.replaceAll("AMT_CANDIDATES",
				options.getCbmcAmountCandidatesVarName());

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
	public void visitSymbolicVarExp(SymbolicVarExp node) {
		expVarNameStack.push(node.getCbmcVar().getName());
		expTypes.push(CElectionVotingType.simple());
	}



	@Override
	public void visitVoteSumExp(VoteSumForCandExp node) {
		String generatedVarName = codeBlock.newVarName("voteSum");
		int voteNumber = node.getVoteNumber();
		String symbolicVarCand = node.getCbmcVar().getName();
		String code = VotesumHelper.generateCode(
				generatedVarName, 
				voteNumber,
				symbolicVarCand, 
				voteArrStruct, 
				votingInputType,
				options, 
				loopBoundHandler);
		codeBlock.addSnippet(code);
		expVarNameStack.add(generatedVarName);
		expTypes.push(CElectionVotingType.simple());
	}

	@Override
	public void visitIntegerExp(IntegerNode node) {
		int heldInteger = node.getHeldInteger();
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
		expVarNameStack.push(constantExp.getConstant());
		expTypes.push(CElectionVotingType.simple());
	}

	@Override
	public void visitEmptyNode(BooleanExpIsEmptyNode booleanExpIsEmptyNode) {
		booleanExpIsEmptyNode.getInnerNode().getVisited(this);
		String testedVarName = expVarNameStack.pop();
		String generatedVar = codeBlock.newVarName("isEmpty" + testedVarName);
		if(amtElectVars == 1) {
			codeBlock.addSnippet(
					IsElectEmptyHelper.generateCode(
							generatedVar, 
							testedVarName,
							voteResultStruct,
							votingOutputType, 
							options, 
							loopBoundHandler));
			
		} else if(amtVoteVars == 1) {
			codeBlock.addSnippet(
					IsVoteEmptyHelper.generateCode(
							generatedVar, 
							testedVarName,
							voteArrStruct,
							votingInputType, 
							options, 
							loopBoundHandler));
		}
		
		booleanVarNameStack.push(generatedVar);
	}

	@Override
	public void visitBinaryRelationNode(
			BinaryRelationshipNode node,
			String binaryCombinationSymbol) {		
		node.getLHSBooleanExpNode().getVisited(this);
		node.getRHSBooleanExpNode().getVisited(this);
		
		String rhsVarName = booleanVarNameStack.pop();
		String lhsVarName = booleanVarNameStack.pop();
		
		String generatedVarName = codeBlock.newVarName("combined");
		
		Map<String, String> replacementMap = Map.of(
					"VAR_NAME", generatedVarName,
					"LHS_VAR", lhsVarName,
					"RHS_VAR", rhsVarName
				);
		
		String code = null;
		
		if(binaryCombinationSymbol.equals("&&")) {
			code = "unsigned int VAR_NAME = LHS_VAR && RHS_VAR;\n";
		} else if(binaryCombinationSymbol.equals("||")) {
			code = "unsigned int VAR_NAME = LHS_VAR || RHS_VAR;\n";			
		} else if(binaryCombinationSymbol.equals("==>")) {
			code = "unsigned int VAR_NAME = !LHS_VAR || RHS_VAR;\n";			
		} else if(binaryCombinationSymbol.equals("<==>")) {
			code = "unsigned int VAR_NAME = (LHS_VAR && RHS_VAR) || (!LHS_VAR && !RHS_VAR);\n";			
		}
		
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		
		codeBlock.addSnippet(code);
	}

	


}
