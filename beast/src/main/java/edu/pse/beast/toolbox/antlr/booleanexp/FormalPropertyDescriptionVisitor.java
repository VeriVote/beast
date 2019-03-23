package edu.pse.beast.toolbox.antlr.booleanexp;
// Generated from FormalPropertyDescription.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FormalPropertyDescriptionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FormalPropertyDescriptionVisitor<T> extends ParseTreeVisitor<T> {
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#booleanExpList}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#booleanExpListElement}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#votingListChangeExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVotingListChangeExp(FormalPropertyDescriptionParser.VotingListChangeExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#votingListChangeContent}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVotingListChangeContent(FormalPropertyDescriptionParser.VotingListChangeContentContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#votingTupelChangeExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVotingTupelChangeExp(FormalPropertyDescriptionParser.VotingTupelChangeExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#candidateListChangeExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitCandidateListChangeExp(FormalPropertyDescriptionParser.CandidateListChangeExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#booleanExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#binaryRelationExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#voteEquivalents}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVoteEquivalents(FormalPropertyDescriptionParser.VoteEquivalentsContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#concatenationExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitConcatenationExp(FormalPropertyDescriptionParser.ConcatenationExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#splitExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitSplitExp(FormalPropertyDescriptionParser.SplitExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#permutationExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitPermutationExp(FormalPropertyDescriptionParser.PermutationExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#intersectExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitIntersectExp(FormalPropertyDescriptionParser.IntersectExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#intersectContent}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitIntersectContent(FormalPropertyDescriptionParser.IntersectContentContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#tuple}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitTuple(FormalPropertyDescriptionParser.TupleContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#tupleContent}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitTupleContent(FormalPropertyDescriptionParser.TupleContentContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#quantorExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitQuantorExp(FormalPropertyDescriptionParser.QuantorExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#notExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitNotExp(FormalPropertyDescriptionParser.NotExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#comparisonExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#typeExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitTypeExp(FormalPropertyDescriptionParser.TypeExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#numberExpression}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitNumberExpression(FormalPropertyDescriptionParser.NumberExpressionContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#typeByPosExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitTypeByPosExp(FormalPropertyDescriptionParser.TypeByPosExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#voterByPosExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVoterByPosExp(FormalPropertyDescriptionParser.VoterByPosExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#candByPosExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitCandByPosExp(FormalPropertyDescriptionParser.CandByPosExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#seatByPosExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitSeatByPosExp(FormalPropertyDescriptionParser.SeatByPosExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#integer}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitInteger(FormalPropertyDescriptionParser.IntegerContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#electExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#voteExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#passType}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitPassType(FormalPropertyDescriptionParser.PassTypeContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#constantExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#voteSumExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#voteSumUniqueExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVoteSumUniqueExp(FormalPropertyDescriptionParser.VoteSumUniqueExpContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#passSymbVar}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitPassSymbVar(FormalPropertyDescriptionParser.PassSymbVarContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#passPosition}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitPassPosition(FormalPropertyDescriptionParser.PassPositionContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#passByPos}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitPassByPos(FormalPropertyDescriptionParser.PassByPosContext ctx);
  /**
   * Visit a parse tree produced by {@link FormalPropertyDescriptionParser#symbolicVarExp}.
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx);
}