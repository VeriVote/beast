// Generated from FormalPropertyDescription.g4 by ANTLR 4.5.3
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