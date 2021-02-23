package edu.pse.beast.api.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VotePermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteTupleNode;
import edu.pse.beast.api.codegen.c_code.CCodeBlock;
import edu.pse.beast.api.codegen.helperfunctions.VoteComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.IntersectionHelper;
import edu.pse.beast.api.codegen.helperfunctions.PermutationHelper;
import edu.pse.beast.api.codegen.helperfunctions.TupleHelper;
import edu.pse.beast.api.codegen.helperfunctions.VoteExpHelper;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode.ComparisonType;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;

public class CodeGenASTVisitor implements BooleanAstVisitor {

	public enum Mode {
		ASSUME, ASSERT
	}

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

	public CodeGenASTVisitor(ElectionTypeCStruct voteArrStruct, ElectionTypeCStruct voteResultStruct,
			CodeGenOptions options) {
		this.voteArrStruct = voteArrStruct;
		this.voteResultStruct = voteResultStruct;
		this.options = options;
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
		node.getFirstChild().getVisited(this);
	}

	private void visitVoteComparison(ComparisonNode node) {

		String code = null;
		if (node.getComparisonType() != ComparisonType.UNEQ) {
			if (level == 0) {
				code = VoteComparisonHelper.generateTopLevelVoteComparisonCode(expVarNameStack.pop(), expVarNameStack.pop(),
						voteArrStruct, options, assumeAssert, node.getComparisonSymbol().getCStringRep());
				codeBlock.addSnippet(code);
			} else {
				String generatedBoolName = codeBlock.newVarName("comparison");
				code = VoteComparisonHelper.generateVoteComparisonCode(generatedBoolName, expVarNameStack.pop(),
						expVarNameStack.pop(), voteArrStruct, options, node.getComparisonSymbol().getCStringRep());
				codeBlock.addSnippet(code);
				booleanVarNameStack.push(generatedBoolName);
			}
		} else if (node.getComparisonType() == ComparisonType.UNEQ) {
			String generatedBoolName = codeBlock.newVarName("notEqual");
			code = VoteComparisonHelper.generateVoteUneqCode(generatedBoolName, expVarNameStack.pop(), expVarNameStack.pop(),
					voteArrStruct, options);
			codeBlock.addSnippet(code);
			if (level == 0) {
				codeBlock.addSnippet(assumeAssert + "(" + generatedBoolName + ")");
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
			visitVoteComparison(node);
			amtVoteVars = 0;
		} else if (amtElectVars == 2) {
			amtElectVars = 0;
		} else {
			int i = 0;
		}

	}

	@Override
	public void visitVoteIntersectionNode(VoteIntersectionNode node) {
		String generatedVarName = codeBlock.newVarName("intersection");

		List<String> varNames = new ArrayList<>();
		for (int number : node.getNumbers()) {
			varNames.add("voteNUMBER".replaceAll("NUMBER", String.valueOf(number)));
		}

		codeBlock.addSnippet(
				IntersectionHelper.generateVoteIntersection(generatedVarName, varNames, voteArrStruct, options));

		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
	}

	@Override
	public void visitVoteExpNode(VoteExp node) {
		if (node.getAccessingCBMCVars().size() == 0) {
			expVarNameStack.push("vote" + node.getCount());
			amtVoteVars++;
		} else if (node.getAccessingCBMCVars().size() == 1) {
			String varName = codeBlock.newVarName("voteArrayVar");
			String voteVarName = "voteNUMBER".replaceAll("NUMBER", node.getVoteNumber());
			String declString = VoteExpHelper.getVarFromVoteAccess(varName, voteVarName,
					node.getAccessingCBMCVars().get(0).getName(), options, voteArrStruct);
			codeBlock.addSnippet(declString);
			expVarNameStack.push(varName);
			
			expTypes.push(voteArrStruct.getVotingType().getTypeOneDimLess());
			
			int i = 0;
		}
	}

	@Override
	public void visitElectExpNode(ElectExp node) {
		if (node.getAccessingVars().length == 0) {
			expVarNameStack.push("elect" + node.getCount());
			amtElectVars++;
		}
	}

	@Override
	public void visitVotePermutation(VotePermutationNode node) {
		String generatedVarName = codeBlock.newVarName("permutation");
		String varName = "voteNUMBER".replaceAll("NUMBER", String.valueOf(node.getVoteNumber()));

		codeBlock.addSnippet(
				PermutationHelper.generateVotePermutation(generatedVarName, varName, voteArrStruct, options));

		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
	}

	@Override
	public void visitVoteTuple(VoteTupleNode node) {
		String generatedVarName = codeBlock.newVarName("tuple");
		List<String> voteNames = new ArrayList<>();
		for (int number : node.getNumbers()) {
			voteNames.add("voteNUMBER".replaceAll("NUMBER", String.valueOf(number)));
		}

		codeBlock.addSnippet(TupleHelper.generateCode(generatedVarName, voteNames, voteArrStruct, options));
		expVarNameStack.push(generatedVarName);
		amtVoteVars++;
	}

	@Override
	public void visitForAllVotersNode(ForAllNode node) {
		String varName = node.getVar().getName();
		scopeHandler.push();
		scopeHandler.add(node.getVar());
		String code = "for(unsigned int VAR_NAME = 0; VAR_NAME < AMT_VOTERS; ++VAR_NAME) {\n";
		code = code.replaceAll("VAR_NAME", varName);
		code = code.replaceAll("AMT_VOTERS", options.getCbmcAmountVotersVarName());
		codeBlock.addSnippet(code);

		node.getFollowingExpNode().getVisited(this);

		codeBlock.addSnippet("}\n");
	}

}
