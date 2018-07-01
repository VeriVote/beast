// Generated from FormalPropertyDescription.g4 by ANTLR 4.7.1
package edu.pse.beast.toolbox.antlr.booleanexp;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class FormalPropertyDescriptionParser extends Parser {
	static {
		RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
			T__9 = 10, Mult = 11, Add = 12, Concatenate = 13, Intersect = 14, Vote = 15, Elect = 16, NotEmpty = 17,
			Votesum = 18, VotesumUnique = 19, ClosedBracket = 20, OpenBracket = 21, Quantor = 22, Split = 23,
			Permutation = 24, ValueAssign = 25, ComparisonSymbol = 26, BinaryRelationSymbol = 27, Integer = 28,
			Identifier = 29, Whitespace = 30, Newline = 31, BlockComment = 32, LineComment = 33;
	public static final int RULE_booleanExpList = 0, RULE_booleanExpListElement = 1, RULE_votingListChangeExp = 2,
			RULE_votingListChangeContent = 3, RULE_votingTupelChangeExp = 4, RULE_candidateListChangeExp = 5,
			RULE_booleanExp = 6, RULE_binaryRelationExp = 7, RULE_notEmptyExp = 8, RULE_notEmptyContent = 9,
			RULE_voteEquivalents = 10, RULE_concatenationExp = 11, RULE_splitExp = 12, RULE_permutationExp = 13,
			RULE_intersectExp = 14, RULE_intersectContent = 15, RULE_tuple = 16, RULE_tupleContent = 17,
			RULE_quantorExp = 18, RULE_notExp = 19, RULE_comparisonExp = 20, RULE_typeExp = 21,
			RULE_numberExpression = 22, RULE_typeByPosExp = 23, RULE_voterByPosExp = 24, RULE_candByPosExp = 25,
			RULE_seatByPosExp = 26, RULE_integer = 27, RULE_electExp = 28, RULE_voteExp = 29, RULE_passType = 30,
			RULE_constantExp = 31, RULE_voteSumExp = 32, RULE_voteSumUniqueExp = 33, RULE_passSymbVar = 34,
			RULE_passPosition = 35, RULE_passByPos = 36, RULE_symbolicVarExp = 37;
	public static final String[] ruleNames = { "booleanExpList", "booleanExpListElement", "votingListChangeExp",
			"votingListChangeContent", "votingTupelChangeExp", "candidateListChangeExp", "booleanExp",
			"binaryRelationExp", "notEmptyExp", "notEmptyContent", "voteEquivalents", "concatenationExp", "splitExp",
			"permutationExp", "intersectExp", "intersectContent", "tuple", "tupleContent", "quantorExp", "notExp",
			"comparisonExp", "typeExp", "numberExpression", "typeByPosExp", "voterByPosExp", "candByPosExp",
			"seatByPosExp", "integer", "electExp", "voteExp", "passType", "constantExp", "voteSumExp",
			"voteSumUniqueExp", "passSymbVar", "passPosition", "passByPos", "symbolicVarExp" };

	private static final String[] _LITERAL_NAMES = { null, "';'", "','", "':'", "'!'", "'VOTER_AT_POS'",
			"'CAND_AT_POS'", "'SEAT_AT_POS'", "'V'", "'C'", "'S'", null, null, "'++'", "'INTERSECT'", null, null,
			"'NOTEMPTY'", null, null, "')'", "'('", null, "'SPLIT'", "'PERM'", "'<-'" };
	private static final String[] _SYMBOLIC_NAMES = { null, null, null, null, null, null, null, null, null, null, null,
			"Mult", "Add", "Concatenate", "Intersect", "Vote", "Elect", "NotEmpty", "Votesum", "VotesumUnique",
			"ClosedBracket", "OpenBracket", "Quantor", "Split", "Permutation", "ValueAssign", "ComparisonSymbol",
			"BinaryRelationSymbol", "Integer", "Identifier", "Whitespace", "Newline", "BlockComment", "LineComment" };
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() {
		return "FormalPropertyDescription.g4";
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	public FormalPropertyDescriptionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	public static class BooleanExpListContext extends ParserRuleContext {
		public List<BooleanExpListElementContext> booleanExpListElement() {
			return getRuleContexts(BooleanExpListElementContext.class);
		}

		public BooleanExpListElementContext booleanExpListElement(int i) {
			return getRuleContext(BooleanExpListElementContext.class, i);
		}

		public BooleanExpListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_booleanExpList;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterBooleanExpList(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitBooleanExpList(this);
		}
	}

	public final BooleanExpListContext booleanExpList() throws RecognitionException {
		BooleanExpListContext _localctx = new BooleanExpListContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_booleanExpList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__4) | (1L << T__5)
						| (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << Intersect) | (1L << Vote)
						| (1L << Elect) | (1L << NotEmpty) | (1L << Votesum) | (1L << VotesumUnique)
						| (1L << OpenBracket) | (1L << Quantor) | (1L << Integer) | (1L << Identifier))) != 0)) {
					{
						{
							setState(76);
							booleanExpListElement();
						}
					}
					setState(81);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanExpListElementContext extends ParserRuleContext {
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class, 0);
		}

		public VotingListChangeExpContext votingListChangeExp() {
			return getRuleContext(VotingListChangeExpContext.class, 0);
		}

		public VotingTupelChangeExpContext votingTupelChangeExp() {
			return getRuleContext(VotingTupelChangeExpContext.class, 0);
		}

		public CandidateListChangeExpContext candidateListChangeExp() {
			return getRuleContext(CandidateListChangeExpContext.class, 0);
		}

		public BooleanExpListElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_booleanExpListElement;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener) {
				((FormalPropertyDescriptionListener) listener).enterBooleanExpListElement(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener) {
				((FormalPropertyDescriptionListener) listener).exitBooleanExpListElement(this);
			}
		}
	}

	public final BooleanExpListElementContext booleanExpListElement() throws RecognitionException {
		BooleanExpListElementContext _localctx = new BooleanExpListElementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_booleanExpListElement);
		try {
			setState(94);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(82);
				booleanExp();
				setState(83);
				match(T__0);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(85);
				votingListChangeExp();
				setState(86);
				match(T__0);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(88);
				votingTupelChangeExp();
				setState(89);
				match(T__0);
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(91);
				candidateListChangeExp();
				setState(92);
				match(T__0);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VotingListChangeExpContext extends ParserRuleContext {
		public TerminalNode Vote() {
			return getToken(FormalPropertyDescriptionParser.Vote, 0);
		}

		public TerminalNode ValueAssign() {
			return getToken(FormalPropertyDescriptionParser.ValueAssign, 0);
		}

		public VotingListChangeContentContext votingListChangeContent() {
			return getRuleContext(VotingListChangeContentContext.class, 0);
		}

		public VotingListChangeExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_votingListChangeExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterVotingListChangeExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitVotingListChangeExp(this);
		}
	}

	public final VotingListChangeExpContext votingListChangeExp() throws RecognitionException {
		VotingListChangeExpContext _localctx = new VotingListChangeExpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_votingListChangeExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(96);
				match(Vote);
				setState(97);
				match(ValueAssign);
				setState(98);
				votingListChangeContent();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VotingListChangeContentContext extends ParserRuleContext {
		public ConcatenationExpContext concatenationExp() {
			return getRuleContext(ConcatenationExpContext.class, 0);
		}

		public PermutationExpContext permutationExp() {
			return getRuleContext(PermutationExpContext.class, 0);
		}

		public VotingListChangeContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_votingListChangeContent;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterVotingListChangeContent(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitVotingListChangeContent(this);
		}
	}

	public final VotingListChangeContentContext votingListChangeContent() throws RecognitionException {
		VotingListChangeContentContext _localctx = new VotingListChangeContentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_votingListChangeContent);
		try {
			setState(102);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(100);
				concatenationExp();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(101);
				permutationExp();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VotingTupelChangeExpContext extends ParserRuleContext {
		public TupleContext tuple() {
			return getRuleContext(TupleContext.class, 0);
		}

		public TerminalNode ValueAssign() {
			return getToken(FormalPropertyDescriptionParser.ValueAssign, 0);
		}

		public SplitExpContext splitExp() {
			return getRuleContext(SplitExpContext.class, 0);
		}

		public VotingTupelChangeExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_votingTupelChangeExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterVotingTupelChangeExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitVotingTupelChangeExp(this);
		}
	}

	public final VotingTupelChangeExpContext votingTupelChangeExp() throws RecognitionException {
		VotingTupelChangeExpContext _localctx = new VotingTupelChangeExpContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_votingTupelChangeExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(104);
				tuple();
				setState(105);
				match(ValueAssign);
				setState(106);
				splitExp();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CandidateListChangeExpContext extends ParserRuleContext {
		public TerminalNode Elect() {
			return getToken(FormalPropertyDescriptionParser.Elect, 0);
		}

		public TerminalNode ValueAssign() {
			return getToken(FormalPropertyDescriptionParser.ValueAssign, 0);
		}

		public IntersectExpContext intersectExp() {
			return getRuleContext(IntersectExpContext.class, 0);
		}

		public CandidateListChangeExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_candidateListChangeExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterCandidateListChangeExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitCandidateListChangeExp(this);
		}
	}

	public final CandidateListChangeExpContext candidateListChangeExp() throws RecognitionException {
		CandidateListChangeExpContext _localctx = new CandidateListChangeExpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_candidateListChangeExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(108);
				match(Elect);
				setState(109);
				match(ValueAssign);
				setState(110);
				intersectExp();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanExpContext extends ParserRuleContext {
		public QuantorExpContext quantorExp() {
			return getRuleContext(QuantorExpContext.class, 0);
		}

		public NotEmptyExpContext notEmptyExp() {
			return getRuleContext(NotEmptyExpContext.class, 0);
		}

		public IntersectExpContext intersectExp() {
			return getRuleContext(IntersectExpContext.class, 0);
		}

		public BinaryRelationExpContext binaryRelationExp() {
			return getRuleContext(BinaryRelationExpContext.class, 0);
		}

		public NotExpContext notExp() {
			return getRuleContext(NotExpContext.class, 0);
		}

		public ComparisonExpContext comparisonExp() {
			return getRuleContext(ComparisonExpContext.class, 0);
		}

		public TerminalNode OpenBracket() {
			return getToken(FormalPropertyDescriptionParser.OpenBracket, 0);
		}

		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class, 0);
		}

		public TerminalNode ClosedBracket() {
			return getToken(FormalPropertyDescriptionParser.ClosedBracket, 0);
		}

		public BooleanExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_booleanExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterBooleanExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitBooleanExp(this);
		}
	}

	public final BooleanExpContext booleanExp() throws RecognitionException {
		BooleanExpContext _localctx = new BooleanExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_booleanExp);
		try {
			setState(123);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(112);
				quantorExp();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(113);
				notEmptyExp();
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(114);
				intersectExp();
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(115);
				intersectExp();
			}
				break;
			case 5:
				enterOuterAlt(_localctx, 5); {
				setState(116);
				binaryRelationExp(0);
			}
				break;
			case 6:
				enterOuterAlt(_localctx, 6); {
				setState(117);
				notExp();
			}
				break;
			case 7:
				enterOuterAlt(_localctx, 7); {
				setState(118);
				comparisonExp();
			}
				break;
			case 8:
				enterOuterAlt(_localctx, 8); {
				setState(119);
				match(OpenBracket);
				setState(120);
				booleanExp();
				setState(121);
				match(ClosedBracket);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryRelationExpContext extends ParserRuleContext {
		public QuantorExpContext quantorExp() {
			return getRuleContext(QuantorExpContext.class, 0);
		}

		public List<TerminalNode> BinaryRelationSymbol() {
			return getTokens(FormalPropertyDescriptionParser.BinaryRelationSymbol);
		}

		public TerminalNode BinaryRelationSymbol(int i) {
			return getToken(FormalPropertyDescriptionParser.BinaryRelationSymbol, i);
		}

		public List<BooleanExpContext> booleanExp() {
			return getRuleContexts(BooleanExpContext.class);
		}

		public BooleanExpContext booleanExp(int i) {
			return getRuleContext(BooleanExpContext.class, i);
		}

		public NotExpContext notExp() {
			return getRuleContext(NotExpContext.class, 0);
		}

		public ComparisonExpContext comparisonExp() {
			return getRuleContext(ComparisonExpContext.class, 0);
		}

		public NotEmptyExpContext notEmptyExp() {
			return getRuleContext(NotEmptyExpContext.class, 0);
		}

		public IntersectExpContext intersectExp() {
			return getRuleContext(IntersectExpContext.class, 0);
		}

		public BinaryRelationExpContext binaryRelationExp() {
			return getRuleContext(BinaryRelationExpContext.class, 0);
		}

		public BinaryRelationExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_binaryRelationExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterBinaryRelationExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitBinaryRelationExp(this);
		}
	}

	public final BinaryRelationExpContext binaryRelationExp() throws RecognitionException {
		return binaryRelationExp(0);
	}

	private BinaryRelationExpContext binaryRelationExp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BinaryRelationExpContext _localctx = new BinaryRelationExpContext(_ctx, _parentState);
		BinaryRelationExpContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_binaryRelationExp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(181);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
				case 1: {
					setState(126);
					quantorExp();
					setState(127);
					match(BinaryRelationSymbol);
					setState(128);
					booleanExp();
				}
					break;
				case 2: {
					setState(130);
					notExp();
					setState(131);
					match(BinaryRelationSymbol);
					setState(132);
					booleanExp();
				}
					break;
				case 3: {
					setState(134);
					comparisonExp();
					setState(135);
					match(BinaryRelationSymbol);
					setState(136);
					booleanExp();
				}
					break;
				case 4: {
					setState(138);
					notEmptyExp();
					setState(139);
					match(BinaryRelationSymbol);
					setState(140);
					booleanExp();
				}
					break;
				case 5: {
					setState(142);
					intersectExp();
					setState(143);
					match(BinaryRelationSymbol);
					setState(144);
					booleanExp();
				}
					break;
				case 6: {
					setState(146);
					match(OpenBracket);
					setState(147);
					binaryRelationExp(0);
					setState(148);
					match(ClosedBracket);
					setState(149);
					match(BinaryRelationSymbol);
					setState(150);
					booleanExp();
				}
					break;
				case 7: {
					setState(152);
					match(OpenBracket);
					setState(153);
					quantorExp();
					setState(154);
					match(ClosedBracket);
					setState(155);
					match(BinaryRelationSymbol);
					setState(156);
					booleanExp();
				}
					break;
				case 8: {
					setState(158);
					match(OpenBracket);
					setState(159);
					notExp();
					setState(160);
					match(ClosedBracket);
					setState(161);
					match(BinaryRelationSymbol);
					setState(162);
					booleanExp();
				}
					break;
				case 9: {
					setState(164);
					match(OpenBracket);
					setState(165);
					comparisonExp();
					setState(166);
					match(ClosedBracket);
					setState(167);
					match(BinaryRelationSymbol);
					setState(168);
					booleanExp();
				}
					break;
				case 10: {
					setState(170);
					match(OpenBracket);
					setState(171);
					notEmptyExp();
					setState(172);
					match(ClosedBracket);
					setState(173);
					match(BinaryRelationSymbol);
					setState(174);
					booleanExp();
					setState(175);
					match(OpenBracket);
					setState(176);
					intersectExp();
					setState(177);
					match(ClosedBracket);
					setState(178);
					match(BinaryRelationSymbol);
					setState(179);
					booleanExp();
				}
					break;
				}
				_ctx.stop = _input.LT(-1);
				setState(188);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						if (_parseListeners != null)
							triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							{
								_localctx = new BinaryRelationExpContext(_parentctx, _parentState);
								pushNewRecursionContext(_localctx, _startState, RULE_binaryRelationExp);
								setState(183);
								if (!(precpred(_ctx, 11)))
									throw new FailedPredicateException(this, "precpred(_ctx, 11)");
								setState(184);
								match(BinaryRelationSymbol);
								setState(185);
								booleanExp();
							}
						}
					}
					setState(190);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NotEmptyExpContext extends ParserRuleContext {
		public TerminalNode NotEmpty() {
			return getToken(FormalPropertyDescriptionParser.NotEmpty, 0);
		}

		public NotEmptyContentContext notEmptyContent() {
			return getRuleContext(NotEmptyContentContext.class, 0);
		}

		public NotEmptyExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_notEmptyExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterNotEmptyExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitNotEmptyExp(this);
		}
	}

	public final NotEmptyExpContext notEmptyExp() throws RecognitionException {
		NotEmptyExpContext _localctx = new NotEmptyExpContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_notEmptyExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(191);
				match(NotEmpty);
				setState(192);
				match(OpenBracket);
				setState(193);
				notEmptyContent();
				setState(194);
				match(ClosedBracket);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotEmptyContentContext extends ParserRuleContext {
		public TerminalNode Elect() {
			return getToken(FormalPropertyDescriptionParser.Elect, 0);
		}

		public IntersectExpContext intersectExp() {
			return getRuleContext(IntersectExpContext.class, 0);
		}

		public NotEmptyContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_notEmptyContent;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterNotEmptyContent(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitNotEmptyContent(this);
		}
	}

	public final NotEmptyContentContext notEmptyContent() throws RecognitionException {
		NotEmptyContentContext _localctx = new NotEmptyContentContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_notEmptyContent);
		try {
			setState(198);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1); {
				setState(196);
				match(Elect);
			}
				break;
			case Intersect:
				enterOuterAlt(_localctx, 2); {
				setState(197);
				intersectExp();
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoteEquivalentsContext extends ParserRuleContext {
		public TerminalNode Vote() {
			return getToken(FormalPropertyDescriptionParser.Vote, 0);
		}

		public PermutationExpContext permutationExp() {
			return getRuleContext(PermutationExpContext.class, 0);
		}

		public ConcatenationExpContext concatenationExp() {
			return getRuleContext(ConcatenationExpContext.class, 0);
		}

		public VoteEquivalentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_voteEquivalents;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterVoteEquivalents(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitVoteEquivalents(this);
		}
	}

	public final VoteEquivalentsContext voteEquivalents() throws RecognitionException {
		VoteEquivalentsContext _localctx = new VoteEquivalentsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_voteEquivalents);
		try {
			setState(203);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(200);
				match(Vote);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(201);
				permutationExp();
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(202);
				concatenationExp();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConcatenationExpContext extends ParserRuleContext {
		public List<VoteEquivalentsContext> voteEquivalents() {
			return getRuleContexts(VoteEquivalentsContext.class);
		}

		public VoteEquivalentsContext voteEquivalents(int i) {
			return getRuleContext(VoteEquivalentsContext.class, i);
		}

		public TerminalNode Concatenate() {
			return getToken(FormalPropertyDescriptionParser.Concatenate, 0);
		}

		public TerminalNode Vote() {
			return getToken(FormalPropertyDescriptionParser.Vote, 0);
		}

		public PermutationExpContext permutationExp() {
			return getRuleContext(PermutationExpContext.class, 0);
		}

		public ConcatenationExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_concatenationExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterConcatenationExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitConcatenationExp(this);
		}
	}

	public final ConcatenationExpContext concatenationExp() throws RecognitionException {
		ConcatenationExpContext _localctx = new ConcatenationExpContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_concatenationExp);
		try {
			setState(218);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
				enterOuterAlt(_localctx, 1); {
				setState(205);
				match(OpenBracket);
				setState(206);
				voteEquivalents();
				setState(207);
				match(Concatenate);
				setState(208);
				voteEquivalents();
				setState(209);
				match(ClosedBracket);
			}
				break;
			case Vote:
				enterOuterAlt(_localctx, 2); {
				setState(211);
				match(Vote);
				setState(212);
				match(Concatenate);
				setState(213);
				voteEquivalents();
			}
				break;
			case Permutation:
				enterOuterAlt(_localctx, 3); {
				setState(214);
				permutationExp();
				setState(215);
				match(Concatenate);
				setState(216);
				voteEquivalents();
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SplitExpContext extends ParserRuleContext {
		public TerminalNode Split() {
			return getToken(FormalPropertyDescriptionParser.Split, 0);
		}

		public VoteEquivalentsContext voteEquivalents() {
			return getRuleContext(VoteEquivalentsContext.class, 0);
		}

		public SplitExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_splitExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterSplitExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitSplitExp(this);
		}
	}

	public final SplitExpContext splitExp() throws RecognitionException {
		SplitExpContext _localctx = new SplitExpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_splitExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(220);
				match(Split);
				setState(221);
				match(OpenBracket);
				setState(222);
				voteEquivalents();
				setState(223);
				match(ClosedBracket);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PermutationExpContext extends ParserRuleContext {
		public TerminalNode Permutation() {
			return getToken(FormalPropertyDescriptionParser.Permutation, 0);
		}

		public VoteEquivalentsContext voteEquivalents() {
			return getRuleContext(VoteEquivalentsContext.class, 0);
		}

		public PermutationExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_permutationExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterPermutationExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitPermutationExp(this);
		}
	}

	public final PermutationExpContext permutationExp() throws RecognitionException {
		PermutationExpContext _localctx = new PermutationExpContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_permutationExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(225);
				match(Permutation);
				setState(226);
				match(OpenBracket);
				setState(227);
				voteEquivalents();
				setState(228);
				match(ClosedBracket);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntersectExpContext extends ParserRuleContext {
		public TerminalNode Intersect() {
			return getToken(FormalPropertyDescriptionParser.Intersect, 0);
		}

		public List<IntersectContentContext> intersectContent() {
			return getRuleContexts(IntersectContentContext.class);
		}

		public IntersectContentContext intersectContent(int i) {
			return getRuleContext(IntersectContentContext.class, i);
		}

		public IntersectExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_intersectExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterIntersectExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitIntersectExp(this);
		}
	}

	public final IntersectExpContext intersectExp() throws RecognitionException {
		IntersectExpContext _localctx = new IntersectExpContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_intersectExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(230);
				match(Intersect);
				setState(231);
				match(OpenBracket);
				setState(232);
				intersectContent();
				setState(233);
				match(T__1);
				setState(234);
				intersectContent();
				setState(235);
				match(ClosedBracket);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntersectContentContext extends ParserRuleContext {
		public TerminalNode Elect() {
			return getToken(FormalPropertyDescriptionParser.Elect, 0);
		}

		public IntersectExpContext intersectExp() {
			return getRuleContext(IntersectExpContext.class, 0);
		}

		public IntersectContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_intersectContent;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterIntersectContent(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitIntersectContent(this);
		}
	}

	public final IntersectContentContext intersectContent() throws RecognitionException {
		IntersectContentContext _localctx = new IntersectContentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_intersectContent);
		try {
			setState(239);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1); {
				setState(237);
				match(Elect);
			}
				break;
			case Intersect:
				enterOuterAlt(_localctx, 2); {
				setState(238);
				intersectExp();
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TupleContext extends ParserRuleContext {
		public TerminalNode Vote() {
			return getToken(FormalPropertyDescriptionParser.Vote, 0);
		}

		public TupleContentContext tupleContent() {
			return getRuleContext(TupleContentContext.class, 0);
		}

		public TupleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_tuple;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterTuple(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitTuple(this);
		}
	}

	public final TupleContext tuple() throws RecognitionException {
		TupleContext _localctx = new TupleContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_tuple);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(241);
				match(OpenBracket);
				setState(242);
				match(Vote);
				setState(243);
				tupleContent();
				setState(244);
				match(ClosedBracket);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TupleContentContext extends ParserRuleContext {
		public TerminalNode Vote() {
			return getToken(FormalPropertyDescriptionParser.Vote, 0);
		}

		public TupleContentContext tupleContent() {
			return getRuleContext(TupleContentContext.class, 0);
		}

		public TupleContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_tupleContent;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterTupleContent(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitTupleContent(this);
		}
	}

	public final TupleContentContext tupleContent() throws RecognitionException {
		TupleContentContext _localctx = new TupleContentContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_tupleContent);
		try {
			setState(251);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 10, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(246);
				match(T__1);
				setState(247);
				match(Vote);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(248);
				match(T__1);
				setState(249);
				match(Vote);
				setState(250);
				tupleContent();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantorExpContext extends ParserRuleContext {
		public TerminalNode Quantor() {
			return getToken(FormalPropertyDescriptionParser.Quantor, 0);
		}

		public PassSymbVarContext passSymbVar() {
			return getRuleContext(PassSymbVarContext.class, 0);
		}

		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class, 0);
		}

		public QuantorExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_quantorExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterQuantorExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitQuantorExp(this);
		}
	}

	public final QuantorExpContext quantorExp() throws RecognitionException {
		QuantorExpContext _localctx = new QuantorExpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_quantorExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(253);
				match(Quantor);
				setState(254);
				passSymbVar();
				setState(255);
				match(T__2);
				setState(256);
				booleanExp();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotExpContext extends ParserRuleContext {
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class, 0);
		}

		public NotExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_notExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterNotExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitNotExp(this);
		}
	}

	public final NotExpContext notExp() throws RecognitionException {
		NotExpContext _localctx = new NotExpContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_notExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(258);
				match(T__3);
				setState(259);
				booleanExp();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonExpContext extends ParserRuleContext {
		public List<TypeExpContext> typeExp() {
			return getRuleContexts(TypeExpContext.class);
		}

		public TypeExpContext typeExp(int i) {
			return getRuleContext(TypeExpContext.class, i);
		}

		public TerminalNode ComparisonSymbol() {
			return getToken(FormalPropertyDescriptionParser.ComparisonSymbol, 0);
		}

		public ComparisonExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_comparisonExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterComparisonExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitComparisonExp(this);
		}
	}

	public final ComparisonExpContext comparisonExp() throws RecognitionException {
		ComparisonExpContext _localctx = new ComparisonExpContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_comparisonExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(261);
				typeExp();
				setState(262);
				match(ComparisonSymbol);
				setState(263);
				typeExp();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeExpContext extends ParserRuleContext {
		public ElectExpContext electExp() {
			return getRuleContext(ElectExpContext.class, 0);
		}

		public VoteExpContext voteExp() {
			return getRuleContext(VoteExpContext.class, 0);
		}

		public NumberExpressionContext numberExpression() {
			return getRuleContext(NumberExpressionContext.class, 0);
		}

		public SymbolicVarExpContext symbolicVarExp() {
			return getRuleContext(SymbolicVarExpContext.class, 0);
		}

		public TypeByPosExpContext typeByPosExp() {
			return getRuleContext(TypeByPosExpContext.class, 0);
		}

		public TypeExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_typeExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterTypeExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitTypeExp(this);
		}
	}

	public final TypeExpContext typeExp() throws RecognitionException {
		TypeExpContext _localctx = new TypeExpContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_typeExp);
		try {
			setState(270);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1); {
				setState(265);
				electExp();
			}
				break;
			case Vote:
				enterOuterAlt(_localctx, 2); {
				setState(266);
				voteExp();
			}
				break;
			case T__7:
			case T__8:
			case T__9:
			case Votesum:
			case VotesumUnique:
			case OpenBracket:
			case Integer:
				enterOuterAlt(_localctx, 3); {
				setState(267);
				numberExpression(0);
			}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4); {
				setState(268);
				symbolicVarExp();
			}
				break;
			case T__4:
			case T__5:
			case T__6:
				enterOuterAlt(_localctx, 5); {
				setState(269);
				typeByPosExp();
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberExpressionContext extends ParserRuleContext {
		public List<NumberExpressionContext> numberExpression() {
			return getRuleContexts(NumberExpressionContext.class);
		}

		public NumberExpressionContext numberExpression(int i) {
			return getRuleContext(NumberExpressionContext.class, i);
		}

		public VoteSumExpContext voteSumExp() {
			return getRuleContext(VoteSumExpContext.class, 0);
		}

		public VoteSumUniqueExpContext voteSumUniqueExp() {
			return getRuleContext(VoteSumUniqueExpContext.class, 0);
		}

		public ConstantExpContext constantExp() {
			return getRuleContext(ConstantExpContext.class, 0);
		}

		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class, 0);
		}

		public TerminalNode Mult() {
			return getToken(FormalPropertyDescriptionParser.Mult, 0);
		}

		public TerminalNode Add() {
			return getToken(FormalPropertyDescriptionParser.Add, 0);
		}

		public NumberExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_numberExpression;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterNumberExpression(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitNumberExpression(this);
		}
	}

	public final NumberExpressionContext numberExpression() throws RecognitionException {
		return numberExpression(0);
	}

	private NumberExpressionContext numberExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		NumberExpressionContext _localctx = new NumberExpressionContext(_ctx, _parentState);
		NumberExpressionContext _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_numberExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(281);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case OpenBracket: {
					setState(273);
					match(OpenBracket);
					setState(274);
					numberExpression(0);
					setState(275);
					match(ClosedBracket);
				}
					break;
				case Votesum: {
					setState(277);
					voteSumExp();
				}
					break;
				case VotesumUnique: {
					setState(278);
					voteSumUniqueExp();
				}
					break;
				case T__7:
				case T__8:
				case T__9: {
					setState(279);
					constantExp();
				}
					break;
				case Integer: {
					setState(280);
					integer();
				}
					break;
				default:
					throw new NoViableAltException(this);
				}
				_ctx.stop = _input.LT(-1);
				setState(291);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						if (_parseListeners != null)
							triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							setState(289);
							_errHandler.sync(this);
							switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
							case 1: {
								_localctx = new NumberExpressionContext(_parentctx, _parentState);
								pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
								setState(283);
								if (!(precpred(_ctx, 6)))
									throw new FailedPredicateException(this, "precpred(_ctx, 6)");
								setState(284);
								match(Mult);
								setState(285);
								numberExpression(7);
							}
								break;
							case 2: {
								_localctx = new NumberExpressionContext(_parentctx, _parentState);
								pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
								setState(286);
								if (!(precpred(_ctx, 5)))
									throw new FailedPredicateException(this, "precpred(_ctx, 5)");
								setState(287);
								match(Add);
								setState(288);
								numberExpression(6);
							}
								break;
							}
						}
					}
					setState(293);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TypeByPosExpContext extends ParserRuleContext {
		public VoterByPosExpContext voterByPosExp() {
			return getRuleContext(VoterByPosExpContext.class, 0);
		}

		public CandByPosExpContext candByPosExp() {
			return getRuleContext(CandByPosExpContext.class, 0);
		}

		public SeatByPosExpContext seatByPosExp() {
			return getRuleContext(SeatByPosExpContext.class, 0);
		}

		public TypeByPosExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_typeByPosExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterTypeByPosExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitTypeByPosExp(this);
		}
	}

	public final TypeByPosExpContext typeByPosExp() throws RecognitionException {
		TypeByPosExpContext _localctx = new TypeByPosExpContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_typeByPosExp);
		try {
			setState(297);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1); {
				setState(294);
				voterByPosExp();
			}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2); {
				setState(295);
				candByPosExp();
			}
				break;
			case T__6:
				enterOuterAlt(_localctx, 3); {
				setState(296);
				seatByPosExp();
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoterByPosExpContext extends ParserRuleContext {
		public PassPositionContext passPosition() {
			return getRuleContext(PassPositionContext.class, 0);
		}

		public VoterByPosExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_voterByPosExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterVoterByPosExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitVoterByPosExp(this);
		}
	}

	public final VoterByPosExpContext voterByPosExp() throws RecognitionException {
		VoterByPosExpContext _localctx = new VoterByPosExpContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_voterByPosExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(299);
				match(T__4);
				setState(300);
				passPosition();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CandByPosExpContext extends ParserRuleContext {
		public PassPositionContext passPosition() {
			return getRuleContext(PassPositionContext.class, 0);
		}

		public CandByPosExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_candByPosExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterCandByPosExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitCandByPosExp(this);
		}
	}

	public final CandByPosExpContext candByPosExp() throws RecognitionException {
		CandByPosExpContext _localctx = new CandByPosExpContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_candByPosExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(302);
				match(T__5);
				setState(303);
				passPosition();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SeatByPosExpContext extends ParserRuleContext {
		public PassPositionContext passPosition() {
			return getRuleContext(PassPositionContext.class, 0);
		}

		public SeatByPosExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_seatByPosExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterSeatByPosExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitSeatByPosExp(this);
		}
	}

	public final SeatByPosExpContext seatByPosExp() throws RecognitionException {
		SeatByPosExpContext _localctx = new SeatByPosExpContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_seatByPosExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(305);
				match(T__6);
				setState(306);
				passPosition();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegerContext extends ParserRuleContext {
		public TerminalNode Integer() {
			return getToken(FormalPropertyDescriptionParser.Integer, 0);
		}

		public IntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_integer;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterInteger(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitInteger(this);
		}
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_integer);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(308);
				match(Integer);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElectExpContext extends ParserRuleContext {
		public TerminalNode Elect() {
			return getToken(FormalPropertyDescriptionParser.Elect, 0);
		}

		public List<PassTypeContext> passType() {
			return getRuleContexts(PassTypeContext.class);
		}

		public PassTypeContext passType(int i) {
			return getRuleContext(PassTypeContext.class, i);
		}

		public ElectExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_electExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterElectExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitElectExp(this);
		}
	}

	public final ElectExpContext electExp() throws RecognitionException {
		ElectExpContext _localctx = new ElectExpContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_electExp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(310);
				match(Elect);
				setState(314);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 16, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						{
							{
								setState(311);
								passType();
							}
						}
					}
					setState(316);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 16, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoteExpContext extends ParserRuleContext {
		public TerminalNode Vote() {
			return getToken(FormalPropertyDescriptionParser.Vote, 0);
		}

		public List<PassTypeContext> passType() {
			return getRuleContexts(PassTypeContext.class);
		}

		public PassTypeContext passType(int i) {
			return getRuleContext(PassTypeContext.class, i);
		}

		public VoteExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_voteExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterVoteExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitVoteExp(this);
		}
	}

	public final VoteExpContext voteExp() throws RecognitionException {
		VoteExpContext _localctx = new VoteExpContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_voteExp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(317);
				match(Vote);
				setState(321);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 17, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						{
							{
								setState(318);
								passType();
							}
						}
					}
					setState(323);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 17, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PassTypeContext extends ParserRuleContext {
		public PassSymbVarContext passSymbVar() {
			return getRuleContext(PassSymbVarContext.class, 0);
		}

		public PassByPosContext passByPos() {
			return getRuleContext(PassByPosContext.class, 0);
		}

		public PassTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_passType;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterPassType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitPassType(this);
		}
	}

	public final PassTypeContext passType() throws RecognitionException {
		PassTypeContext _localctx = new PassTypeContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_passType);
		try {
			setState(326);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 18, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(324);
				passSymbVar();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(325);
				passByPos();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantExpContext extends ParserRuleContext {
		public ConstantExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_constantExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterConstantExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitConstantExp(this);
		}
	}

	public final ConstantExpContext constantExp() throws RecognitionException {
		ConstantExpContext _localctx = new ConstantExpContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_constantExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(328);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9))) != 0))) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoteSumExpContext extends ParserRuleContext {
		public TerminalNode Votesum() {
			return getToken(FormalPropertyDescriptionParser.Votesum, 0);
		}

		public PassTypeContext passType() {
			return getRuleContext(PassTypeContext.class, 0);
		}

		public VoteSumExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_voteSumExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterVoteSumExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitVoteSumExp(this);
		}
	}

	public final VoteSumExpContext voteSumExp() throws RecognitionException {
		VoteSumExpContext _localctx = new VoteSumExpContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_voteSumExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(330);
				match(Votesum);
				setState(331);
				passType();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoteSumUniqueExpContext extends ParserRuleContext {
		public TerminalNode VotesumUnique() {
			return getToken(FormalPropertyDescriptionParser.VotesumUnique, 0);
		}

		public PassTypeContext passType() {
			return getRuleContext(PassTypeContext.class, 0);
		}

		public VoteSumUniqueExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_voteSumUniqueExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterVoteSumUniqueExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitVoteSumUniqueExp(this);
		}
	}

	public final VoteSumUniqueExpContext voteSumUniqueExp() throws RecognitionException {
		VoteSumUniqueExpContext _localctx = new VoteSumUniqueExpContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_voteSumUniqueExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(333);
				match(VotesumUnique);
				setState(334);
				passType();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PassSymbVarContext extends ParserRuleContext {
		public TerminalNode OpenBracket() {
			return getToken(FormalPropertyDescriptionParser.OpenBracket, 0);
		}

		public SymbolicVarExpContext symbolicVarExp() {
			return getRuleContext(SymbolicVarExpContext.class, 0);
		}

		public TerminalNode ClosedBracket() {
			return getToken(FormalPropertyDescriptionParser.ClosedBracket, 0);
		}

		public PassSymbVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_passSymbVar;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterPassSymbVar(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitPassSymbVar(this);
		}
	}

	public final PassSymbVarContext passSymbVar() throws RecognitionException {
		PassSymbVarContext _localctx = new PassSymbVarContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_passSymbVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(336);
				match(OpenBracket);
				setState(337);
				symbolicVarExp();
				setState(338);
				match(ClosedBracket);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PassPositionContext extends ParserRuleContext {
		public TerminalNode OpenBracket() {
			return getToken(FormalPropertyDescriptionParser.OpenBracket, 0);
		}

		public NumberExpressionContext numberExpression() {
			return getRuleContext(NumberExpressionContext.class, 0);
		}

		public TerminalNode ClosedBracket() {
			return getToken(FormalPropertyDescriptionParser.ClosedBracket, 0);
		}

		public PassPositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_passPosition;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterPassPosition(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitPassPosition(this);
		}
	}

	public final PassPositionContext passPosition() throws RecognitionException {
		PassPositionContext _localctx = new PassPositionContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_passPosition);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(340);
				match(OpenBracket);
				setState(341);
				numberExpression(0);
				setState(342);
				match(ClosedBracket);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PassByPosContext extends ParserRuleContext {
		public TerminalNode OpenBracket() {
			return getToken(FormalPropertyDescriptionParser.OpenBracket, 0);
		}

		public TypeByPosExpContext typeByPosExp() {
			return getRuleContext(TypeByPosExpContext.class, 0);
		}

		public TerminalNode ClosedBracket() {
			return getToken(FormalPropertyDescriptionParser.ClosedBracket, 0);
		}

		public PassByPosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_passByPos;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterPassByPos(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitPassByPos(this);
		}
	}

	public final PassByPosContext passByPos() throws RecognitionException {
		PassByPosContext _localctx = new PassByPosContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_passByPos);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(344);
				match(OpenBracket);
				setState(345);
				typeByPosExp();
				setState(346);
				match(ClosedBracket);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SymbolicVarExpContext extends ParserRuleContext {
		public TerminalNode Identifier() {
			return getToken(FormalPropertyDescriptionParser.Identifier, 0);
		}

		public SymbolicVarExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_symbolicVarExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).enterSymbolicVarExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof FormalPropertyDescriptionListener)
				((FormalPropertyDescriptionListener) listener).exitSymbolicVarExp(this);
		}
	}

	public final SymbolicVarExpContext symbolicVarExp() throws RecognitionException {
		SymbolicVarExpContext _localctx = new SymbolicVarExpContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_symbolicVarExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(348);
				match(Identifier);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return binaryRelationExp_sempred((BinaryRelationExpContext) _localctx, predIndex);
		case 22:
			return numberExpression_sempred((NumberExpressionContext) _localctx, predIndex);
		}
		return true;
	}

	private boolean binaryRelationExp_sempred(BinaryRelationExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		}
		return true;
	}

	private boolean numberExpression_sempred(NumberExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN = "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3#\u0161\4\2\t\2\4"
			+ "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
			+ "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
			+ "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
			+ "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
			+ "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\7\2P\n\2\f\2\16\2S\13"
			+ "\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3a\n\3\3\4\3\4\3"
			+ "\4\3\4\3\5\3\5\5\5i\n\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3"
			+ "\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b~\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"
			+ "\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"
			+ "\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"
			+ "\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00b8\n\t\3"
			+ "\t\3\t\3\t\7\t\u00bd\n\t\f\t\16\t\u00c0\13\t\3\n\3\n\3\n\3\n\3\n\3\13"
			+ "\3\13\5\13\u00c9\n\13\3\f\3\f\3\f\5\f\u00ce\n\f\3\r\3\r\3\r\3\r\3\r\3"
			+ "\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00dd\n\r\3\16\3\16\3\16\3\16\3\16"
			+ "\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21"
			+ "\5\21\u00f2\n\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\5\23"
			+ "\u00fe\n\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\26"
			+ "\3\27\3\27\3\27\3\27\3\27\5\27\u0111\n\27\3\30\3\30\3\30\3\30\3\30\3\30"
			+ "\3\30\3\30\3\30\5\30\u011c\n\30\3\30\3\30\3\30\3\30\3\30\3\30\7\30\u0124"
			+ "\n\30\f\30\16\30\u0127\13\30\3\31\3\31\3\31\5\31\u012c\n\31\3\32\3\32"
			+ "\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\36\3\36\7\36\u013b\n\36"
			+ "\f\36\16\36\u013e\13\36\3\37\3\37\7\37\u0142\n\37\f\37\16\37\u0145\13"
			+ "\37\3 \3 \5 \u0149\n \3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%"
			+ "\3%\3&\3&\3&\3&\3\'\3\'\3\'\2\4\20.(\2\4\6\b\n\f\16\20\22\24\26\30\32"
			+ "\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJL\2\3\3\2\n\f\2\u0166\2Q\3\2\2\2"
			+ "\4`\3\2\2\2\6b\3\2\2\2\bh\3\2\2\2\nj\3\2\2\2\fn\3\2\2\2\16}\3\2\2\2\20"
			+ "\u00b7\3\2\2\2\22\u00c1\3\2\2\2\24\u00c8\3\2\2\2\26\u00cd\3\2\2\2\30\u00dc"
			+ "\3\2\2\2\32\u00de\3\2\2\2\34\u00e3\3\2\2\2\36\u00e8\3\2\2\2 \u00f1\3\2"
			+ "\2\2\"\u00f3\3\2\2\2$\u00fd\3\2\2\2&\u00ff\3\2\2\2(\u0104\3\2\2\2*\u0107"
			+ "\3\2\2\2,\u0110\3\2\2\2.\u011b\3\2\2\2\60\u012b\3\2\2\2\62\u012d\3\2\2"
			+ "\2\64\u0130\3\2\2\2\66\u0133\3\2\2\28\u0136\3\2\2\2:\u0138\3\2\2\2<\u013f"
			+ "\3\2\2\2>\u0148\3\2\2\2@\u014a\3\2\2\2B\u014c\3\2\2\2D\u014f\3\2\2\2F"
			+ "\u0152\3\2\2\2H\u0156\3\2\2\2J\u015a\3\2\2\2L\u015e\3\2\2\2NP\5\4\3\2"
			+ "ON\3\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2R\3\3\2\2\2SQ\3\2\2\2TU\5\16\b"
			+ "\2UV\7\3\2\2Va\3\2\2\2WX\5\6\4\2XY\7\3\2\2Ya\3\2\2\2Z[\5\n\6\2[\\\7\3"
			+ "\2\2\\a\3\2\2\2]^\5\f\7\2^_\7\3\2\2_a\3\2\2\2`T\3\2\2\2`W\3\2\2\2`Z\3"
			+ "\2\2\2`]\3\2\2\2a\5\3\2\2\2bc\7\21\2\2cd\7\33\2\2de\5\b\5\2e\7\3\2\2\2"
			+ "fi\5\30\r\2gi\5\34\17\2hf\3\2\2\2hg\3\2\2\2i\t\3\2\2\2jk\5\"\22\2kl\7"
			+ "\33\2\2lm\5\32\16\2m\13\3\2\2\2no\7\22\2\2op\7\33\2\2pq\5\36\20\2q\r\3"
			+ "\2\2\2r~\5&\24\2s~\5\22\n\2t~\5\36\20\2u~\5\36\20\2v~\5\20\t\2w~\5(\25"
			+ "\2x~\5*\26\2yz\7\27\2\2z{\5\16\b\2{|\7\26\2\2|~\3\2\2\2}r\3\2\2\2}s\3"
			+ "\2\2\2}t\3\2\2\2}u\3\2\2\2}v\3\2\2\2}w\3\2\2\2}x\3\2\2\2}y\3\2\2\2~\17"
			+ "\3\2\2\2\177\u0080\b\t\1\2\u0080\u0081\5&\24\2\u0081\u0082\7\35\2\2\u0082"
			+ "\u0083\5\16\b\2\u0083\u00b8\3\2\2\2\u0084\u0085\5(\25\2\u0085\u0086\7"
			+ "\35\2\2\u0086\u0087\5\16\b\2\u0087\u00b8\3\2\2\2\u0088\u0089\5*\26\2\u0089"
			+ "\u008a\7\35\2\2\u008a\u008b\5\16\b\2\u008b\u00b8\3\2\2\2\u008c\u008d\5"
			+ "\22\n\2\u008d\u008e\7\35\2\2\u008e\u008f\5\16\b\2\u008f\u00b8\3\2\2\2"
			+ "\u0090\u0091\5\36\20\2\u0091\u0092\7\35\2\2\u0092\u0093\5\16\b\2\u0093"
			+ "\u00b8\3\2\2\2\u0094\u0095\7\27\2\2\u0095\u0096\5\20\t\2\u0096\u0097\7"
			+ "\26\2\2\u0097\u0098\7\35\2\2\u0098\u0099\5\16\b\2\u0099\u00b8\3\2\2\2"
			+ "\u009a\u009b\7\27\2\2\u009b\u009c\5&\24\2\u009c\u009d\7\26\2\2\u009d\u009e"
			+ "\7\35\2\2\u009e\u009f\5\16\b\2\u009f\u00b8\3\2\2\2\u00a0\u00a1\7\27\2"
			+ "\2\u00a1\u00a2\5(\25\2\u00a2\u00a3\7\26\2\2\u00a3\u00a4\7\35\2\2\u00a4"
			+ "\u00a5\5\16\b\2\u00a5\u00b8\3\2\2\2\u00a6\u00a7\7\27\2\2\u00a7\u00a8\5"
			+ "*\26\2\u00a8\u00a9\7\26\2\2\u00a9\u00aa\7\35\2\2\u00aa\u00ab\5\16\b\2"
			+ "\u00ab\u00b8\3\2\2\2\u00ac\u00ad\7\27\2\2\u00ad\u00ae\5\22\n\2\u00ae\u00af"
			+ "\7\26\2\2\u00af\u00b0\7\35\2\2\u00b0\u00b1\5\16\b\2\u00b1\u00b2\7\27\2"
			+ "\2\u00b2\u00b3\5\36\20\2\u00b3\u00b4\7\26\2\2\u00b4\u00b5\7\35\2\2\u00b5"
			+ "\u00b6\5\16\b\2\u00b6\u00b8\3\2\2\2\u00b7\177\3\2\2\2\u00b7\u0084\3\2"
			+ "\2\2\u00b7\u0088\3\2\2\2\u00b7\u008c\3\2\2\2\u00b7\u0090\3\2\2\2\u00b7"
			+ "\u0094\3\2\2\2\u00b7\u009a\3\2\2\2\u00b7\u00a0\3\2\2\2\u00b7\u00a6\3\2"
			+ "\2\2\u00b7\u00ac\3\2\2\2\u00b8\u00be\3\2\2\2\u00b9\u00ba\f\r\2\2\u00ba"
			+ "\u00bb\7\35\2\2\u00bb\u00bd\5\16\b\2\u00bc\u00b9\3\2\2\2\u00bd\u00c0\3"
			+ "\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\21\3\2\2\2\u00c0"
			+ "\u00be\3\2\2\2\u00c1\u00c2\7\23\2\2\u00c2\u00c3\7\27\2\2\u00c3\u00c4\5"
			+ "\24\13\2\u00c4\u00c5\7\26\2\2\u00c5\23\3\2\2\2\u00c6\u00c9\7\22\2\2\u00c7"
			+ "\u00c9\5\36\20\2\u00c8\u00c6\3\2\2\2\u00c8\u00c7\3\2\2\2\u00c9\25\3\2"
			+ "\2\2\u00ca\u00ce\7\21\2\2\u00cb\u00ce\5\34\17\2\u00cc\u00ce\5\30\r\2\u00cd"
			+ "\u00ca\3\2\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ce\27\3\2\2"
			+ "\2\u00cf\u00d0\7\27\2\2\u00d0\u00d1\5\26\f\2\u00d1\u00d2\7\17\2\2\u00d2"
			+ "\u00d3\5\26\f\2\u00d3\u00d4\7\26\2\2\u00d4\u00dd\3\2\2\2\u00d5\u00d6\7"
			+ "\21\2\2\u00d6\u00d7\7\17\2\2\u00d7\u00dd\5\26\f\2\u00d8\u00d9\5\34\17"
			+ "\2\u00d9\u00da\7\17\2\2\u00da\u00db\5\26\f\2\u00db\u00dd\3\2\2\2\u00dc"
			+ "\u00cf\3\2\2\2\u00dc\u00d5\3\2\2\2\u00dc\u00d8\3\2\2\2\u00dd\31\3\2\2"
			+ "\2\u00de\u00df\7\31\2\2\u00df\u00e0\7\27\2\2\u00e0\u00e1\5\26\f\2\u00e1"
			+ "\u00e2\7\26\2\2\u00e2\33\3\2\2\2\u00e3\u00e4\7\32\2\2\u00e4\u00e5\7\27"
			+ "\2\2\u00e5\u00e6\5\26\f\2\u00e6\u00e7\7\26\2\2\u00e7\35\3\2\2\2\u00e8"
			+ "\u00e9\7\20\2\2\u00e9\u00ea\7\27\2\2\u00ea\u00eb\5 \21\2\u00eb\u00ec\7"
			+ "\4\2\2\u00ec\u00ed\5 \21\2\u00ed\u00ee\7\26\2\2\u00ee\37\3\2\2\2\u00ef"
			+ "\u00f2\7\22\2\2\u00f0\u00f2\5\36\20\2\u00f1\u00ef\3\2\2\2\u00f1\u00f0"
			+ "\3\2\2\2\u00f2!\3\2\2\2\u00f3\u00f4\7\27\2\2\u00f4\u00f5\7\21\2\2\u00f5"
			+ "\u00f6\5$\23\2\u00f6\u00f7\7\26\2\2\u00f7#\3\2\2\2\u00f8\u00f9\7\4\2\2"
			+ "\u00f9\u00fe\7\21\2\2\u00fa\u00fb\7\4\2\2\u00fb\u00fc\7\21\2\2\u00fc\u00fe"
			+ "\5$\23\2\u00fd\u00f8\3\2\2\2\u00fd\u00fa\3\2\2\2\u00fe%\3\2\2\2\u00ff"
			+ "\u0100\7\30\2\2\u0100\u0101\5F$\2\u0101\u0102\7\5\2\2\u0102\u0103\5\16"
			+ "\b\2\u0103\'\3\2\2\2\u0104\u0105\7\6\2\2\u0105\u0106\5\16\b\2\u0106)\3"
			+ "\2\2\2\u0107\u0108\5,\27\2\u0108\u0109\7\34\2\2\u0109\u010a\5,\27\2\u010a"
			+ "+\3\2\2\2\u010b\u0111\5:\36\2\u010c\u0111\5<\37\2\u010d\u0111\5.\30\2"
			+ "\u010e\u0111\5L\'\2\u010f\u0111\5\60\31\2\u0110\u010b\3\2\2\2\u0110\u010c"
			+ "\3\2\2\2\u0110\u010d\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u010f\3\2\2\2\u0111"
			+ "-\3\2\2\2\u0112\u0113\b\30\1\2\u0113\u0114\7\27\2\2\u0114\u0115\5.\30"
			+ "\2\u0115\u0116\7\26\2\2\u0116\u011c\3\2\2\2\u0117\u011c\5B\"\2\u0118\u011c"
			+ "\5D#\2\u0119\u011c\5@!\2\u011a\u011c\58\35\2\u011b\u0112\3\2\2\2\u011b"
			+ "\u0117\3\2\2\2\u011b\u0118\3\2\2\2\u011b\u0119\3\2\2\2\u011b\u011a\3\2"
			+ "\2\2\u011c\u0125\3\2\2\2\u011d\u011e\f\b\2\2\u011e\u011f\7\r\2\2\u011f"
			+ "\u0124\5.\30\t\u0120\u0121\f\7\2\2\u0121\u0122\7\16\2\2\u0122\u0124\5"
			+ ".\30\b\u0123\u011d\3\2\2\2\u0123\u0120\3\2\2\2\u0124\u0127\3\2\2\2\u0125"
			+ "\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126/\3\2\2\2\u0127\u0125\3\2\2\2"
			+ "\u0128\u012c\5\62\32\2\u0129\u012c\5\64\33\2\u012a\u012c\5\66\34\2\u012b"
			+ "\u0128\3\2\2\2\u012b\u0129\3\2\2\2\u012b\u012a\3\2\2\2\u012c\61\3\2\2"
			+ "\2\u012d\u012e\7\7\2\2\u012e\u012f\5H%\2\u012f\63\3\2\2\2\u0130\u0131"
			+ "\7\b\2\2\u0131\u0132\5H%\2\u0132\65\3\2\2\2\u0133\u0134\7\t\2\2\u0134"
			+ "\u0135\5H%\2\u0135\67\3\2\2\2\u0136\u0137\7\36\2\2\u01379\3\2\2\2\u0138"
			+ "\u013c\7\22\2\2\u0139\u013b\5> \2\u013a\u0139\3\2\2\2\u013b\u013e\3\2"
			+ "\2\2\u013c\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d;\3\2\2\2\u013e\u013c"
			+ "\3\2\2\2\u013f\u0143\7\21\2\2\u0140\u0142\5> \2\u0141\u0140\3\2\2\2\u0142"
			+ "\u0145\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144=\3\2\2\2"
			+ "\u0145\u0143\3\2\2\2\u0146\u0149\5F$\2\u0147\u0149\5J&\2\u0148\u0146\3"
			+ "\2\2\2\u0148\u0147\3\2\2\2\u0149?\3\2\2\2\u014a\u014b\t\2\2\2\u014bA\3"
			+ "\2\2\2\u014c\u014d\7\24\2\2\u014d\u014e\5> \2\u014eC\3\2\2\2\u014f\u0150"
			+ "\7\25\2\2\u0150\u0151\5> \2\u0151E\3\2\2\2\u0152\u0153\7\27\2\2\u0153"
			+ "\u0154\5L\'\2\u0154\u0155\7\26\2\2\u0155G\3\2\2\2\u0156\u0157\7\27\2\2"
			+ "\u0157\u0158\5.\30\2\u0158\u0159\7\26\2\2\u0159I\3\2\2\2\u015a\u015b\7"
			+ "\27\2\2\u015b\u015c\5\60\31\2\u015c\u015d\7\26\2\2\u015dK\3\2\2\2\u015e"
			+ "\u015f\7\37\2\2\u015fM\3\2\2\2\25Q`h}\u00b7\u00be\u00c8\u00cd\u00dc\u00f1"
			+ "\u00fd\u0110\u011b\u0123\u0125\u012b\u013c\u0143\u0148";
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}