package edu.pse.beast.api.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VotePermutationNode;
import edu.pse.beast.api.codegen.c_code.CCodeBlock;
import edu.pse.beast.api.codegen.helperfunctions.ComparisonHelper;
import edu.pse.beast.api.codegen.helperfunctions.IntersectionHelper;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode.ComparisonType;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;

public class CodeGenASTVisitor implements BooleanAstVisitor {

	public enum Mode {
		ASSUME, ASSERT
	}

	private CCodeBlock codeBlock;

	private Stack<String> expVarNameStack = new Stack<>();
	private Stack<ElectionTypeCStruct> comparedTypes = new Stack<>();
	private Stack<String> booleanVarNameStack = new Stack<>();

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

	@Override
	public void visitComparisonNode(ComparisonNode node) {
		node.getLhsTypeExp().getVisited(this);
		node.getRhsTypeExp().getVisited(this);

		String code = null;
	
		if (node.getComparisonType() != ComparisonType.UNEQ) {
			if (level == 0) {
				code = ComparisonHelper.generateTopLevelComparisonCode(expVarNameStack.pop(), expVarNameStack.pop(),
						comparedTypes.pop(), options, assumeAssert, node.getComparisonSymbol().getCStringRep());
				codeBlock.addSnippet(code);
				comparedTypes.pop();
			} else {
				String generatedBoolName = codeBlock.newVarName("comparison");
				code = ComparisonHelper.generateComparisonCode(generatedBoolName, expVarNameStack.pop(),
						expVarNameStack.pop(), comparedTypes.pop(), options, node.getComparisonSymbol().getCStringRep());
				codeBlock.addSnippet(code);
				comparedTypes.pop();
				booleanVarNameStack.push(generatedBoolName);
			}			
		} else if (node.getComparisonType() == ComparisonType.UNEQ) {
			String generatedBoolName = codeBlock.newVarName("notEqual");
			code = ComparisonHelper.generateUneqCode(generatedBoolName, expVarNameStack.pop(), expVarNameStack.pop(),
					comparedTypes.pop(), options);
			codeBlock.addSnippet(code);
			comparedTypes.pop();
			if (level == 0) {
				codeBlock.addSnippet(assumeAssert + "(" + generatedBoolName + ")");
			} else {
				booleanVarNameStack.push(generatedBoolName);
			}
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
		comparedTypes.push(voteArrStruct);
	}

	@Override
	public void visitVoteExpNode(VoteExp node) {
		expVarNameStack.push("vote" + node.getCount());
		comparedTypes.push(voteArrStruct);
	}

	@Override
	public void visitElectExpNode(ElectExp node) {
		expVarNameStack.push("elect" + node.getCount());
		comparedTypes.push(voteResultStruct);
	}

	@Override
	public void visitVotePermutation(VotePermutationNode node) {
				
	}

}
