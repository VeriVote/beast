package edu.pse.beast.api.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpIsEmptyNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VotePermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteTupleNode;
import edu.pse.beast.api.codegen.c_code.CCodeBlock;
import edu.pse.beast.api.codegen.helperfunctions.VoteComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.ComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.IntersectionHelper;
import edu.pse.beast.api.codegen.helperfunctions.PermutationHelper;
import edu.pse.beast.api.codegen.helperfunctions.TupleHelper;
import edu.pse.beast.api.codegen.helperfunctions.VoteExpHelper;
import edu.pse.beast.api.codegen.helperfunctions.VotesumHelper;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode.ComparisonType;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalAndNode;
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
	private Stack<String> booleanVarNameStack = new Stack<>();

	private Stack<CElectionVotingType> expTypes = new Stack<>();

	private int amtVoteVars = 0;
	private int amtElectVars = 0;

	private ElectionTypeCStruct voteArrStruct;
	private ElectionTypeCStruct voteResultStruct;
	private CodeGenOptions options;

	private Mode mode;
	private String assumeAssert;

	private int level = 0;

	public CodeGenASTVisitor(ElectionTypeCStruct voteArrStruct,
			ElectionTypeCStruct voteResultStruct, CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {
		this.voteArrStruct = voteArrStruct;
		this.voteResultStruct = voteResultStruct;
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
		this.level = 0;
		amtElectVars = 0;
		amtVoteVars = 0;
		node.getFirstChild().getVisited(this);
	}

	private void visitVoteTypeComparison(ComparisonNode node, ElectionTypeCStruct voteTypeCStruct) {
		String code = null;
		if (node.getComparisonType() != ComparisonType.UNEQ) {
			if (level == 0) {
				code = VoteComparisonHelper.generateTopLevelVoteComparisonCode(
						expVarNameStack.pop(), expVarNameStack.pop(),
						voteTypeCStruct, options, assumeAssert,
						node.getComparisonSymbol().getCStringRep(),
						loopBoundHandler);
				codeBlock.addSnippet(code);
			} else {
				String generatedBoolName = codeBlock.newVarName("comparison");
				code = VoteComparisonHelper.generateVoteComparisonCode(
						generatedBoolName, expVarNameStack.pop(),
						expVarNameStack.pop(), voteTypeCStruct, options,
						node.getComparisonSymbol().getCStringRep());
				codeBlock.addSnippet(code);
				booleanVarNameStack.push(generatedBoolName);
			}
		} else if (node.getComparisonType() == ComparisonType.UNEQ) {
			String generatedBoolName = codeBlock.newVarName("notEqual");
			code = VoteComparisonHelper.generateVoteUneqCode(generatedBoolName,
					expVarNameStack.pop(), expVarNameStack.pop(), voteTypeCStruct,
					options);
			codeBlock.addSnippet(code);
			if (level == 0) {
				codeBlock.addSnippet(
						assumeAssert + "(" + generatedBoolName + ")");
			} else {
				booleanVarNameStack.push(generatedBoolName);
			}
		}
	}

	@Override
	public void visitComparisonNode(ComparisonNode node) {
		node.getLhsTypeExp().getVisited(this);
		node.getRhsTypeExp().getVisited(this);

		if (amtVoteVars == 2) {
			visitVoteTypeComparison(node, voteArrStruct);
			amtVoteVars = 0;
		} else if (amtElectVars == 2) {
			visitVoteTypeComparison(node, voteResultStruct);
			amtElectVars = 0;
		} else {
			String rhsVarName = expVarNameStack.pop();
			String lhsVarName = expVarNameStack.pop();

			CElectionVotingType rhsType = expTypes.pop();
			CElectionVotingType lhsType = expTypes.pop();

			if (amtElectVars == 1) {
				// we are comparing a number or symbolic var with an election
				// result
				// this can only happen for an election type of single candidate
				// TODO clean this up!!

				if (lhsVarName.contains("elect")) {
					lhsVarName += "." + voteResultStruct.getListName();
				} else if (rhsVarName.contains("elect")) {
					rhsVarName += voteResultStruct.getListName();
				}
			}

			if (level == 0 && node.getComparisonType() != ComparisonType.UNEQ) {
				String code = ComparisonHelper.generateTopLevelCompCode(
						node.getComparisonSymbol().getCStringRep(), lhsVarName,
						rhsVarName, lhsType, options, assumeAssert,
						loopBoundHandler);
				codeBlock.addSnippet(code);
			} else {
				String varName = codeBlock.newVarName("comparison");
				String code = ComparisonHelper.generateCompCode(varName,
						node.getComparisonSymbol().getCStringRep(), lhsVarName,
						rhsVarName, lhsType, options, assumeAssert);
				codeBlock.addSnippet(code);
				booleanVarNameStack.push(varName);
			}
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
				IntersectionHelper.generateVoteIntersection(generatedVarName,
						varNames, voteArrStruct, options, loopBoundHandler));

		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
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
		String generatedVarName = codeBlock.newVarName("permutation");
		String varName = "voteNUMBER".replaceAll("NUMBER",
				String.valueOf(node.getVoteNumber()));

		codeBlock.addSnippet(
				PermutationHelper.generateVotePermutation(generatedVarName,
						varName, voteArrStruct, options, loopBoundHandler));

		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
	}

	@Override
	public void visitVoteTuple(VoteTupleNode node) {
		String generatedVarName = codeBlock.newVarName("sequence");
		List<String> voteNames = new ArrayList<>();
		for (int number : node.getNumbers()) {
			voteNames.add(
					"voteNUMBER".replaceAll("NUMBER", String.valueOf(number)));
		}

		codeBlock.addSnippet(TupleHelper.generateCode(generatedVarName,
				voteNames, voteArrStruct, options, loopBoundHandler));
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

		if (level == 0) {
			codeBlock.addSnippet(code);

			node.getFollowingExpNode().getVisited(this);

			codeBlock.addSnippet("}\n");
		} else {
			String varName = codeBlock.newVarName("forAllVoters");
			codeBlock.addAssignment("unsigned int " + varName, "1");
			codeBlock.addSnippet(code);
			node.getFollowingExpNode().getVisited(this);
			codeBlock.addSnippet(varName + " &= " + booleanVarNameStack.pop());
			codeBlock.addSnippet("}\n");
			booleanVarNameStack.push(varName);
		}

		scopeHandler.pop();
	}

	@Override
	public void visitNotNode(NotNode node) {
		level++;
		node.getNegatedExpNode().getVisited(this);
		level--;

		if (level == 0) {
			String template = "ASSUME_OR_ASSERT(!BOOL);";
			template = template.replaceAll("ASSUME_OR_ASSERT", assumeAssert);
			template = template.replaceAll("BOOL", booleanVarNameStack.pop());
			codeBlock.addSnippet(template);
		} else {
			String generatedVar = codeBlock.newVarName("not");
			codeBlock.addAssignment("unsigned int " + generatedVar,
					"!" + booleanVarNameStack.pop());
			booleanVarNameStack.push(generatedVar);
		}

	}

	@Override
	public void visitExistsCandidateNode(ThereExistsNode node) {
		level++;

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

		level--;
		codeBlock.addSnippet("}");

		if (level == 0) {
			codeBlock.addSnippet(assumeAssert + "(" + boolVarName + ");");
		} else {
			booleanVarNameStack.push(boolVarName);
		}

		scopeHandler.pop();
	}

	@Override
	public void visitSymbolicVarExp(SymbolicVarExp node) {
		expVarNameStack.push(node.getCbmcVar().getName());
		expTypes.push(CElectionVotingType.simple());
	}

	@Override
	public void visitAndNode(LogicalAndNode logicalAndNode) {
		level++;
		logicalAndNode.getLHSBooleanExpNode().getVisited(this);
		logicalAndNode.getRHSBooleanExpNode().getVisited(this);
		level--;
		if (level == 0) {

		} else {
			String varName = codeBlock.newVarName("and");
			codeBlock.addAssignment("unsigned int " + varName,
					booleanVarNameStack.pop() + " && "
							+ booleanVarNameStack.pop());
			booleanVarNameStack.push(varName);
		}
	}

	@Override
	public void visitVoteSumExp(VoteSumForCandExp node) {
		String generatedVarName = codeBlock.newVarName("voteSum");
		int voteNumber = node.getVoteNumber();
		String symbolicVarCand = node.getCbmcVar().getName();
		String code = VotesumHelper.generateCode(generatedVarName, voteNumber,
				symbolicVarCand, voteArrStruct, options, loopBoundHandler);
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
		String emptyVarName = expVarNameStack.pop();
		CElectionVotingType emptyType = expTypes.pop();
		if (amtVoteVars == 1) {

		} else if (amtElectVars == 1) {
			if (level == 0) {
				codeBlock.addSnippet(assumeAssert + "(" + emptyVarName + "."
						+ voteResultStruct.getAmtName() + " == 0);\n");
			} else {
				String boolVarName = codeBlock.newVarName("isElectEmpty");
				codeBlock.addAssignment(
						"unsigned int " + boolVarName, 
						emptyVarName + "."+ voteResultStruct.getAmtName() + " == 0");
				booleanVarNameStack.push(boolVarName);
			}
		}
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
				IntersectionHelper.generateElectIntersection(generatedVarName,
						varNames, voteResultStruct, options, loopBoundHandler));

		expVarNameStack.push(generatedVarName);
		expTypes.push(voteResultStruct.getVotingType());
		amtElectVars++;
	}
}
