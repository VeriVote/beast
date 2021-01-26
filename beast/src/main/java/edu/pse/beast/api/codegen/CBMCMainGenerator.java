package edu.pse.beast.api.codegen;

import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.intVar;
import static edu.pse.beast.toolbox.CCodeHelper.lineComment;
import static edu.pse.beast.toolbox.CCodeHelper.pointer;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.uintVarEqualsCode;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanCodeToAST;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.FormalPropertySyntaxTreeToAstTranslator;

public class CBMCMainGenerator {

	// for the voting arrays declare the max votes variables. They depend on
	// the properties
	// create the voting arrays, depending on how often is voted
	// fill them with nondet int
	// depending on voting type, make sure the votes make sense (are between bounds)
	// other preconditions
	// call the voting func
	// post conditions
	public static CFunction main(CElectionDescription descr, PreAndPostConditionsDescription propDescr,
			BooleanExpASTData astData, CStruct voteArrStruct, CStruct voteResultStruct) {
		CFunction created = new CFunction("main", List.of("int argc", "char ** argv"), "int");
		created.setCode(List.of("return 1;"));
		

		CodeGenASTVisitor visitor = new CodeGenASTVisitor(voteArrStruct, voteResultStruct);
		int highestVote = astData.getHighestVote();
		for (int i = 0; i < highestVote; ++i) {
			for (BooleanExpressionNode node : astData.getTopAstNode().getBooleanExpressions().get(i)) {
				String s = node.getTreeString(0);
				node.getVisited(visitor);
				System.out.println(visitor.getCodeBlock().generateCode());
			}
		}

		return created;
	}

//	private BooleanExpListNode generateAST(final String codeString,
//			FormalPropertySyntaxTreeToAstTranslator translator) {
//		final FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(CharStreams.fromString(codeString));
//		final CommonTokenStream ts = new CommonTokenStream(l);
//		final FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(ts);
//		final BooleanExpScope declaredVars = new BooleanExpScope();
//		preAndPostCondDesc.getSymbolicVariablesAsList().forEach(v -> {
//			declaredVars.addTypeForId(v.getId(), v.getInternalTypeContainer());
//		});
//		return translator.generateFromSyntaxTree(p.booleanExpList(), electionDesc.getContainer().getInputType(),
//				electionDesc.getContainer().getOutputType(), declaredVars);
//	}

	/*
	 * private void addMainMethod() { code.add(intVar(functionCode(MAIN,
	 * intVar("argc"), CCodeHelper.CHAR + space() + pointer("argv[]"))) + space() +
	 * CCodeHelper.OPENING_BRACES); code.addTab(); // Generating the pre and post
	 * AbstractSyntaxTrees. final BooleanExpListNode preAST =
	 * generateAST(preAndPostCondDesc.getPreConditionsDescription().getCode());
	 * final BooleanExpListNode postAST =
	 * generateAST(preAndPostCondDesc.getPostConditionsDescription().getCode());
	 * initializeNumberOfTimesVoted(preAST, postAST); // Initialize all voting
	 * variables for the voters. for (int i = 1; i <= numberOfTimesVoted; i++) {
	 * code.add(uintVarEqualsCode(UnifiedNameContainer.getVoter() + i) +
	 * UnifiedNameContainer.getVoter() + CCodeHelper.SEMICOLON); } // Initialize all
	 * voting variables for the candidates. for (int i = 1; i <= numberOfTimesVoted;
	 * i++) { code.add(uintVarEqualsCode(UnifiedNameContainer.getCandidate() + i) +
	 * UnifiedNameContainer.getCandidate() + CCodeHelper.SEMICOLON); } // Initialize
	 * all voting variables for the seats. for (int i = 1; i <= numberOfTimesVoted;
	 * i++) { code.add(uintVarEqualsCode(UnifiedNameContainer.getSeats() + i) +
	 * UnifiedNameContainer.getSeats() + CCodeHelper.SEMICOLON); } final
	 * List<String> boundedVars =
	 * preAndPostCondDesc.getBoundedVarDescription().getCodeAsList();
	 * code.addList(boundedVars); // First the variables have to be initialized.
	 * addSymbVarInitialisation();
	 * 
	 * for (int voteNumber = 1; voteNumber <= numberOfTimesVoted; voteNumber++) {
	 * String minV = "" +
	 * electionDesc.getContainer().getInputType().getMinimalValue(); if
	 * (electionDesc.getContainer().getInputType().hasVariableAsMinValue()) { minV
	 * += voteNumber; } String maxV = "" +
	 * electionDesc.getContainer().getInputType().getMaximalValue(); if
	 * (electionDesc.getContainer().getInputType().hasVariableAsMaxValue()) { maxV
	 * += voteNumber; } code.add(lineComment("Init for election:") + space() +
	 * voteNumber); addInitialisedValue(UnifiedNameContainer.getVotingArray() +
	 * voteNumber, electionDesc.getContainer().getInputType(),
	 * electionDesc.getContainer().getInputStruct(), minV, maxV); }
	 * 
	 * // The preconditions must be defined addPreProperties(preAST); // Now hold
	 * all the elections for (int i = 1; i <= numberOfTimesVoted; i++) {
	 * code.add(lineComment("Election number:") + space() + i); final String
	 * sizeOfVotes = UnifiedNameContainer.getVoter() + i; final String electX =
	 * varAssignCode(
	 * electionDesc.getContainer().getOutputStruct().getStructAccess() + space() +
	 * ELECT + i, functionCode(UnifiedNameContainer.getVotingMethod(), sizeOfVotes,
	 * VOTES + i)) + CCodeHelper.SEMICOLON; code.add(electX); } // Now the
	 * postconditions can be checked addPostProperties(postAST); code.deleteTab();
	 * code.add(CCodeHelper.CLOSING_BRACES); }
	 * 
	 * private void addPreProperties(final BooleanExpListNode preAST) { code.add();
	 * code.add(lineComment("Preconditions")); code.add();
	 * visitor.setToPreConditionMode();
	 * preAST.getBooleanExpressions().forEach(booleanExpressionLists -> {
	 * booleanExpressionLists.forEach(booleanNode -> {
	 * code.addList(visitor.generateCode(booleanNode)); }); }); }
	 * 
	 * 
	 * private void addPostProperties(final BooleanExpListNode postAST) {
	 * code.add(); code.add(lineComment("Postconditions")); code.add();
	 * visitor.setToPostConditionMode();
	 * postAST.getBooleanExpressions().forEach(booleanExpressionLists -> {
	 * booleanExpressionLists.forEach(booleanNode -> {
	 * code.addList(visitor.generateCode(booleanNode)); }); }); }
	 */
}
