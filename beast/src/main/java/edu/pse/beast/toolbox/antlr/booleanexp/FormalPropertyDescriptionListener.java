// Generated from FormalPropertyDescription.g4 by ANTLR 4.7.1
package edu.pse.beast.toolbox.antlr.booleanexp;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FormalPropertyDescriptionParser}.
 */
public interface FormalPropertyDescriptionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#booleanExpList}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#booleanExpList}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#booleanExpListElement}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#booleanExpListElement}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#votingListChangeExp}.
	 * @param ctx the parse tree
	 */
	void enterVotingListChangeExp(FormalPropertyDescriptionParser.VotingListChangeExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#votingListChangeExp}.
	 * @param ctx the parse tree
	 */
	void exitVotingListChangeExp(FormalPropertyDescriptionParser.VotingListChangeExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#votingListChangeContent}.
	 * @param ctx the parse tree
	 */
	void enterVotingListChangeContent(FormalPropertyDescriptionParser.VotingListChangeContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#votingListChangeContent}.
	 * @param ctx the parse tree
	 */
	void exitVotingListChangeContent(FormalPropertyDescriptionParser.VotingListChangeContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#votingTupelChangeExp}.
	 * @param ctx the parse tree
	 */
	void enterVotingTupelChangeExp(FormalPropertyDescriptionParser.VotingTupelChangeExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#votingTupelChangeExp}.
	 * @param ctx the parse tree
	 */
	void exitVotingTupelChangeExp(FormalPropertyDescriptionParser.VotingTupelChangeExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#candidateListChangeExp}.
	 * @param ctx the parse tree
	 */
	void enterCandidateListChangeExp(FormalPropertyDescriptionParser.CandidateListChangeExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#candidateListChangeExp}.
	 * @param ctx the parse tree
	 */
	void exitCandidateListChangeExp(FormalPropertyDescriptionParser.CandidateListChangeExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#booleanExp}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#booleanExp}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#binaryRelationExp}.
	 * @param ctx the parse tree
	 */
	void enterBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#binaryRelationExp}.
	 * @param ctx the parse tree
	 */
	void exitBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#voteEquivalents}.
	 * @param ctx the parse tree
	 */
	void enterVoteEquivalents(FormalPropertyDescriptionParser.VoteEquivalentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#voteEquivalents}.
	 * @param ctx the parse tree
	 */
	void exitVoteEquivalents(FormalPropertyDescriptionParser.VoteEquivalentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#concatenationExp}.
	 * @param ctx the parse tree
	 */
	void enterConcatenationExp(FormalPropertyDescriptionParser.ConcatenationExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#concatenationExp}.
	 * @param ctx the parse tree
	 */
	void exitConcatenationExp(FormalPropertyDescriptionParser.ConcatenationExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#splitExp}.
	 * @param ctx the parse tree
	 */
	void enterSplitExp(FormalPropertyDescriptionParser.SplitExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#splitExp}.
	 * @param ctx the parse tree
	 */
	void exitSplitExp(FormalPropertyDescriptionParser.SplitExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#permutationExp}.
	 * @param ctx the parse tree
	 */
	void enterPermutationExp(FormalPropertyDescriptionParser.PermutationExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#permutationExp}.
	 * @param ctx the parse tree
	 */
	void exitPermutationExp(FormalPropertyDescriptionParser.PermutationExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#intersectExp}.
	 * @param ctx the parse tree
	 */
	void enterIntersectExp(FormalPropertyDescriptionParser.IntersectExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#intersectExp}.
	 * @param ctx the parse tree
	 */
	void exitIntersectExp(FormalPropertyDescriptionParser.IntersectExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#intersectContent}.
	 * @param ctx the parse tree
	 */
	void enterIntersectContent(FormalPropertyDescriptionParser.IntersectContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#intersectContent}.
	 * @param ctx the parse tree
	 */
	void exitIntersectContent(FormalPropertyDescriptionParser.IntersectContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#tuple}.
	 * @param ctx the parse tree
	 */
	void enterTuple(FormalPropertyDescriptionParser.TupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#tuple}.
	 * @param ctx the parse tree
	 */
	void exitTuple(FormalPropertyDescriptionParser.TupleContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#tupleContent}.
	 * @param ctx the parse tree
	 */
	void enterTupleContent(FormalPropertyDescriptionParser.TupleContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#tupleContent}.
	 * @param ctx the parse tree
	 */
	void exitTupleContent(FormalPropertyDescriptionParser.TupleContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#quantorExp}.
	 * @param ctx the parse tree
	 */
	void enterQuantorExp(FormalPropertyDescriptionParser.QuantorExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#quantorExp}.
	 * @param ctx the parse tree
	 */
	void exitQuantorExp(FormalPropertyDescriptionParser.QuantorExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#notExp}.
	 * @param ctx the parse tree
	 */
	void enterNotExp(FormalPropertyDescriptionParser.NotExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#notExp}.
	 * @param ctx the parse tree
	 */
	void exitNotExp(FormalPropertyDescriptionParser.NotExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#comparisonExp}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#comparisonExp}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#typeExp}.
	 * @param ctx the parse tree
	 */
	void enterTypeExp(FormalPropertyDescriptionParser.TypeExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#typeExp}.
	 * @param ctx the parse tree
	 */
	void exitTypeExp(FormalPropertyDescriptionParser.TypeExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#numberExpression}.
	 * @param ctx the parse tree
	 */
	void enterNumberExpression(FormalPropertyDescriptionParser.NumberExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#numberExpression}.
	 * @param ctx the parse tree
	 */
	void exitNumberExpression(FormalPropertyDescriptionParser.NumberExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#typeByPosExp}.
	 * @param ctx the parse tree
	 */
	void enterTypeByPosExp(FormalPropertyDescriptionParser.TypeByPosExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#typeByPosExp}.
	 * @param ctx the parse tree
	 */
	void exitTypeByPosExp(FormalPropertyDescriptionParser.TypeByPosExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#voterByPosExp}.
	 * @param ctx the parse tree
	 */
	void enterVoterByPosExp(FormalPropertyDescriptionParser.VoterByPosExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#voterByPosExp}.
	 * @param ctx the parse tree
	 */
	void exitVoterByPosExp(FormalPropertyDescriptionParser.VoterByPosExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#candByPosExp}.
	 * @param ctx the parse tree
	 */
	void enterCandByPosExp(FormalPropertyDescriptionParser.CandByPosExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#candByPosExp}.
	 * @param ctx the parse tree
	 */
	void exitCandByPosExp(FormalPropertyDescriptionParser.CandByPosExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#seatByPosExp}.
	 * @param ctx the parse tree
	 */
	void enterSeatByPosExp(FormalPropertyDescriptionParser.SeatByPosExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#seatByPosExp}.
	 * @param ctx the parse tree
	 */
	void exitSeatByPosExp(FormalPropertyDescriptionParser.SeatByPosExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(FormalPropertyDescriptionParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(FormalPropertyDescriptionParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#electExp}.
	 * @param ctx the parse tree
	 */
	void enterElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#electExp}.
	 * @param ctx the parse tree
	 */
	void exitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#voteExp}.
	 * @param ctx the parse tree
	 */
	void enterVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#voteExp}.
	 * @param ctx the parse tree
	 */
	void exitVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#passType}.
	 * @param ctx the parse tree
	 */
	void enterPassType(FormalPropertyDescriptionParser.PassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#passType}.
	 * @param ctx the parse tree
	 */
	void exitPassType(FormalPropertyDescriptionParser.PassTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#constantExp}.
	 * @param ctx the parse tree
	 */
	void enterConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#constantExp}.
	 * @param ctx the parse tree
	 */
	void exitConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#voteSumExp}.
	 * @param ctx the parse tree
	 */
	void enterVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#voteSumExp}.
	 * @param ctx the parse tree
	 */
	void exitVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#voteSumUniqueExp}.
	 * @param ctx the parse tree
	 */
	void enterVoteSumUniqueExp(FormalPropertyDescriptionParser.VoteSumUniqueExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#voteSumUniqueExp}.
	 * @param ctx the parse tree
	 */
	void exitVoteSumUniqueExp(FormalPropertyDescriptionParser.VoteSumUniqueExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#passSymbVar}.
	 * @param ctx the parse tree
	 */
	void enterPassSymbVar(FormalPropertyDescriptionParser.PassSymbVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#passSymbVar}.
	 * @param ctx the parse tree
	 */
	void exitPassSymbVar(FormalPropertyDescriptionParser.PassSymbVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#passPosition}.
	 * @param ctx the parse tree
	 */
	void enterPassPosition(FormalPropertyDescriptionParser.PassPositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#passPosition}.
	 * @param ctx the parse tree
	 */
	void exitPassPosition(FormalPropertyDescriptionParser.PassPositionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#passByPos}.
	 * @param ctx the parse tree
	 */
	void enterPassByPos(FormalPropertyDescriptionParser.PassByPosContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#passByPos}.
	 * @param ctx the parse tree
	 */
	void exitPassByPos(FormalPropertyDescriptionParser.PassByPosContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormalPropertyDescriptionParser#symbolicVarExp}.
	 * @param ctx the parse tree
	 */
	void enterSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormalPropertyDescriptionParser#symbolicVarExp}.
	 * @param ctx the parse tree
	 */
	void exitSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx);
}