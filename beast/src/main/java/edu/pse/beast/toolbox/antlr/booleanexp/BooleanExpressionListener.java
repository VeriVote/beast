package edu.pse.beast.toolbox.antlr.booleanexp;
// Generated from BooleanExpression.g by ANTLR 4.6
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BooleanExpressionParser}.
 */
public interface BooleanExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(BooleanExpressionParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(BooleanExpressionParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#booleanExp}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExp(BooleanExpressionParser.BooleanExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#booleanExp}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExp(BooleanExpressionParser.BooleanExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#relationalNode}.
	 * @param ctx the parse tree
	 */
	void enterRelationalNode(BooleanExpressionParser.RelationalNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#relationalNode}.
	 * @param ctx the parse tree
	 */
	void exitRelationalNode(BooleanExpressionParser.RelationalNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#equalityRelation}.
	 * @param ctx the parse tree
	 */
	void enterEqualityRelation(BooleanExpressionParser.EqualityRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#equalityRelation}.
	 * @param ctx the parse tree
	 */
	void exitEqualityRelation(BooleanExpressionParser.EqualityRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#inequalityRelation}.
	 * @param ctx the parse tree
	 */
	void enterInequalityRelation(BooleanExpressionParser.InequalityRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#inequalityRelation}.
	 * @param ctx the parse tree
	 */
	void exitInequalityRelation(BooleanExpressionParser.InequalityRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#lessThanRelation}.
	 * @param ctx the parse tree
	 */
	void enterLessThanRelation(BooleanExpressionParser.LessThanRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#lessThanRelation}.
	 * @param ctx the parse tree
	 */
	void exitLessThanRelation(BooleanExpressionParser.LessThanRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#lessOrEqualThanRelation}.
	 * @param ctx the parse tree
	 */
	void enterLessOrEqualThanRelation(BooleanExpressionParser.LessOrEqualThanRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#lessOrEqualThanRelation}.
	 * @param ctx the parse tree
	 */
	void exitLessOrEqualThanRelation(BooleanExpressionParser.LessOrEqualThanRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#greaterThanRelation}.
	 * @param ctx the parse tree
	 */
	void enterGreaterThanRelation(BooleanExpressionParser.GreaterThanRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#greaterThanRelation}.
	 * @param ctx the parse tree
	 */
	void exitGreaterThanRelation(BooleanExpressionParser.GreaterThanRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#greaterOrEqualThanRelation}.
	 * @param ctx the parse tree
	 */
	void enterGreaterOrEqualThanRelation(BooleanExpressionParser.GreaterOrEqualThanRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#greaterOrEqualThanRelation}.
	 * @param ctx the parse tree
	 */
	void exitGreaterOrEqualThanRelation(BooleanExpressionParser.GreaterOrEqualThanRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#relationParameter}.
	 * @param ctx the parse tree
	 */
	void enterRelationParameter(BooleanExpressionParser.RelationParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#relationParameter}.
	 * @param ctx the parse tree
	 */
	void exitRelationParameter(BooleanExpressionParser.RelationParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(BooleanExpressionParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(BooleanExpressionParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#forAllVoters}.
	 * @param ctx the parse tree
	 */
	void enterForAllVoters(BooleanExpressionParser.ForAllVotersContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#forAllVoters}.
	 * @param ctx the parse tree
	 */
	void exitForAllVoters(BooleanExpressionParser.ForAllVotersContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#forAllCandidates}.
	 * @param ctx the parse tree
	 */
	void enterForAllCandidates(BooleanExpressionParser.ForAllCandidatesContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#forAllCandidates}.
	 * @param ctx the parse tree
	 */
	void exitForAllCandidates(BooleanExpressionParser.ForAllCandidatesContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#forAllSeats}.
	 * @param ctx the parse tree
	 */
	void enterForAllSeats(BooleanExpressionParser.ForAllSeatsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#forAllSeats}.
	 * @param ctx the parse tree
	 */
	void exitForAllSeats(BooleanExpressionParser.ForAllSeatsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#existsOneVoter}.
	 * @param ctx the parse tree
	 */
	void enterExistsOneVoter(BooleanExpressionParser.ExistsOneVoterContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#existsOneVoter}.
	 * @param ctx the parse tree
	 */
	void exitExistsOneVoter(BooleanExpressionParser.ExistsOneVoterContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#existsOneCandidate}.
	 * @param ctx the parse tree
	 */
	void enterExistsOneCandidate(BooleanExpressionParser.ExistsOneCandidateContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#existsOneCandidate}.
	 * @param ctx the parse tree
	 */
	void exitExistsOneCandidate(BooleanExpressionParser.ExistsOneCandidateContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#existsOneSeat}.
	 * @param ctx the parse tree
	 */
	void enterExistsOneSeat(BooleanExpressionParser.ExistsOneSeatContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#existsOneSeat}.
	 * @param ctx the parse tree
	 */
	void exitExistsOneSeat(BooleanExpressionParser.ExistsOneSeatContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#sumVotesForCandidate}.
	 * @param ctx the parse tree
	 */
	void enterSumVotesForCandidate(BooleanExpressionParser.SumVotesForCandidateContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#sumVotesForCandidate}.
	 * @param ctx the parse tree
	 */
	void exitSumVotesForCandidate(BooleanExpressionParser.SumVotesForCandidateContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#votesVariable}.
	 * @param ctx the parse tree
	 */
	void enterVotesVariable(BooleanExpressionParser.VotesVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#votesVariable}.
	 * @param ctx the parse tree
	 */
	void exitVotesVariable(BooleanExpressionParser.VotesVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link BooleanExpressionParser#electVariable}.
	 * @param ctx the parse tree
	 */
	void enterElectVariable(BooleanExpressionParser.ElectVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link BooleanExpressionParser#electVariable}.
	 * @param ctx the parse tree
	 */
	void exitElectVariable(BooleanExpressionParser.ElectVariableContext ctx);
}