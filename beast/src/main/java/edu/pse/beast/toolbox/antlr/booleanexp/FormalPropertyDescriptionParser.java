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

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FormalPropertyDescriptionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, Mult=11, Add=12, Concatenate=13, Intersect=14, Vote=15, Elect=16, 
		Votesum=17, VotesumUnique=18, ClosedBracket=19, OpenBracket=20, Quantor=21, 
		Split=22, Permutation=23, ValueAssign=24, ComparisonSymbol=25, BinaryRelationSymbol=26, 
		Integer=27, Identifier=28, Whitespace=29, Newline=30, BlockComment=31, 
		LineComment=32;
	public static final int
		RULE_booleanExpList = 0, RULE_booleanExpListElement = 1, RULE_votingListChangeExp = 2, 
		RULE_votingListChangeContent = 3, RULE_votingTupelChangeExp = 4, RULE_candidateListChangeExp = 5, 
		RULE_booleanExp = 6, RULE_binaryRelationExp = 7, RULE_voteEquivalents = 8, 
		RULE_concatenationExp = 9, RULE_splitExp = 10, RULE_permutationExp = 11, 
		RULE_intersectExp = 12, RULE_intersectContent = 13, RULE_tuple = 14, RULE_tupleContent = 15, 
		RULE_quantorExp = 16, RULE_notExp = 17, RULE_comparisonExp = 18, RULE_typeExp = 19, 
		RULE_numberExpression = 20, RULE_typeByPosExp = 21, RULE_voterByPosExp = 22, 
		RULE_candByPosExp = 23, RULE_seatByPosExp = 24, RULE_integer = 25, RULE_electExp = 26, 
		RULE_voteExp = 27, RULE_passType = 28, RULE_constantExp = 29, RULE_voteSumExp = 30, 
		RULE_voteSumUniqueExp = 31, RULE_passSymbVar = 32, RULE_passPosition = 33, 
		RULE_passByPos = 34, RULE_symbolicVarExp = 35;
	public static final String[] ruleNames = {
		"booleanExpList", "booleanExpListElement", "votingListChangeExp", "votingListChangeContent", 
		"votingTupelChangeExp", "candidateListChangeExp", "booleanExp", "binaryRelationExp", 
		"voteEquivalents", "concatenationExp", "splitExp", "permutationExp", "intersectExp", 
		"intersectContent", "tuple", "tupleContent", "quantorExp", "notExp", "comparisonExp", 
		"typeExp", "numberExpression", "typeByPosExp", "voterByPosExp", "candByPosExp", 
		"seatByPosExp", "integer", "electExp", "voteExp", "passType", "constantExp", 
		"voteSumExp", "voteSumUniqueExp", "passSymbVar", "passPosition", "passByPos", 
		"symbolicVarExp"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "','", "':'", "'!'", "'VOTER_AT_POS'", "'CAND_AT_POS'", "'SEAT_AT_POS'", 
		"'V'", "'C'", "'S'", null, null, "'++'", "'INTERSECT'", null, null, null, 
		null, "')'", "'('", null, "'SPLIT'", "'PERM'", "'<-'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "Mult", 
		"Add", "Concatenate", "Intersect", "Vote", "Elect", "Votesum", "VotesumUnique", 
		"ClosedBracket", "OpenBracket", "Quantor", "Split", "Permutation", "ValueAssign", 
		"ComparisonSymbol", "BinaryRelationSymbol", "Integer", "Identifier", "Whitespace", 
		"Newline", "BlockComment", "LineComment"
	};
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
	public String getGrammarFileName() { return "FormalPropertyDescription.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FormalPropertyDescriptionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class BooleanExpListContext extends ParserRuleContext {
		public List<BooleanExpListElementContext> booleanExpListElement() {
			return getRuleContexts(BooleanExpListElementContext.class);
		}
		public BooleanExpListElementContext booleanExpListElement(int i) {
			return getRuleContext(BooleanExpListElementContext.class,i);
		}
		public BooleanExpListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterBooleanExpList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitBooleanExpList(this);
		}
	}

	public final BooleanExpListContext booleanExpList() throws RecognitionException {
		BooleanExpListContext _localctx = new BooleanExpListContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_booleanExpList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(72);
					booleanExpListElement();
					}
					} 
				}
				setState(77);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanExpListElementContext extends ParserRuleContext {
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public VotingListChangeExpContext votingListChangeExp() {
			return getRuleContext(VotingListChangeExpContext.class,0);
		}
		public VotingTupelChangeExpContext votingTupelChangeExp() {
			return getRuleContext(VotingTupelChangeExpContext.class,0);
		}
		public CandidateListChangeExpContext candidateListChangeExp() {
			return getRuleContext(CandidateListChangeExpContext.class,0);
		}
		public BooleanExpListElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpListElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterBooleanExpListElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitBooleanExpListElement(this);
		}
	}

	public final BooleanExpListElementContext booleanExpListElement() throws RecognitionException {
		BooleanExpListElementContext _localctx = new BooleanExpListElementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_booleanExpListElement);
		try {
			setState(90);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				booleanExp();
				setState(79);
				match(T__0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				votingListChangeExp();
				setState(82);
				match(T__0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(84);
				votingTupelChangeExp();
				setState(85);
				match(T__0);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(87);
				candidateListChangeExp();
				setState(88);
				match(T__0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VotingListChangeExpContext extends ParserRuleContext {
		public TerminalNode Vote() { return getToken(FormalPropertyDescriptionParser.Vote, 0); }
		public TerminalNode ValueAssign() { return getToken(FormalPropertyDescriptionParser.ValueAssign, 0); }
		public VotingListChangeContentContext votingListChangeContent() {
			return getRuleContext(VotingListChangeContentContext.class,0);
		}
		public VotingListChangeExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_votingListChangeExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterVotingListChangeExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitVotingListChangeExp(this);
		}
	}

	public final VotingListChangeExpContext votingListChangeExp() throws RecognitionException {
		VotingListChangeExpContext _localctx = new VotingListChangeExpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_votingListChangeExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(Vote);
			setState(93);
			match(ValueAssign);
			setState(94);
			votingListChangeContent();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VotingListChangeContentContext extends ParserRuleContext {
		public ConcatenationExpContext concatenationExp() {
			return getRuleContext(ConcatenationExpContext.class,0);
		}
		public PermutationExpContext permutationExp() {
			return getRuleContext(PermutationExpContext.class,0);
		}
		public VotingListChangeContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_votingListChangeContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterVotingListChangeContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitVotingListChangeContent(this);
		}
	}

	public final VotingListChangeContentContext votingListChangeContent() throws RecognitionException {
		VotingListChangeContentContext _localctx = new VotingListChangeContentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_votingListChangeContent);
		try {
			setState(98);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				concatenationExp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				permutationExp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VotingTupelChangeExpContext extends ParserRuleContext {
		public TupleContext tuple() {
			return getRuleContext(TupleContext.class,0);
		}
		public TerminalNode ValueAssign() { return getToken(FormalPropertyDescriptionParser.ValueAssign, 0); }
		public SplitExpContext splitExp() {
			return getRuleContext(SplitExpContext.class,0);
		}
		public VotingTupelChangeExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_votingTupelChangeExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterVotingTupelChangeExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitVotingTupelChangeExp(this);
		}
	}

	public final VotingTupelChangeExpContext votingTupelChangeExp() throws RecognitionException {
		VotingTupelChangeExpContext _localctx = new VotingTupelChangeExpContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_votingTupelChangeExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			tuple();
			setState(101);
			match(ValueAssign);
			setState(102);
			splitExp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CandidateListChangeExpContext extends ParserRuleContext {
		public TerminalNode Elect() { return getToken(FormalPropertyDescriptionParser.Elect, 0); }
		public TerminalNode ValueAssign() { return getToken(FormalPropertyDescriptionParser.ValueAssign, 0); }
		public IntersectExpContext intersectExp() {
			return getRuleContext(IntersectExpContext.class,0);
		}
		public CandidateListChangeExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_candidateListChangeExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterCandidateListChangeExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitCandidateListChangeExp(this);
		}
	}

	public final CandidateListChangeExpContext candidateListChangeExp() throws RecognitionException {
		CandidateListChangeExpContext _localctx = new CandidateListChangeExpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_candidateListChangeExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(Elect);
			setState(105);
			match(ValueAssign);
			setState(106);
			intersectExp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanExpContext extends ParserRuleContext {
		public QuantorExpContext quantorExp() {
			return getRuleContext(QuantorExpContext.class,0);
		}
		public ConcatenationExpContext concatenationExp() {
			return getRuleContext(ConcatenationExpContext.class,0);
		}
		public SplitExpContext splitExp() {
			return getRuleContext(SplitExpContext.class,0);
		}
		public BinaryRelationExpContext binaryRelationExp() {
			return getRuleContext(BinaryRelationExpContext.class,0);
		}
		public NotExpContext notExp() {
			return getRuleContext(NotExpContext.class,0);
		}
		public ComparisonExpContext comparisonExp() {
			return getRuleContext(ComparisonExpContext.class,0);
		}
		public TerminalNode OpenBracket() { return getToken(FormalPropertyDescriptionParser.OpenBracket, 0); }
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public TerminalNode ClosedBracket() { return getToken(FormalPropertyDescriptionParser.ClosedBracket, 0); }
		public BooleanExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterBooleanExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitBooleanExp(this);
		}
	}

	public final BooleanExpContext booleanExp() throws RecognitionException {
		BooleanExpContext _localctx = new BooleanExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_booleanExp);
		try {
			setState(118);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(108);
				quantorExp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				concatenationExp();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(110);
				splitExp();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(111);
				binaryRelationExp(0);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(112);
				notExp();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(113);
				comparisonExp();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(114);
				match(OpenBracket);
				setState(115);
				booleanExp();
				setState(116);
				match(ClosedBracket);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryRelationExpContext extends ParserRuleContext {
		public QuantorExpContext quantorExp() {
			return getRuleContext(QuantorExpContext.class,0);
		}
		public TerminalNode BinaryRelationSymbol() { return getToken(FormalPropertyDescriptionParser.BinaryRelationSymbol, 0); }
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public NotExpContext notExp() {
			return getRuleContext(NotExpContext.class,0);
		}
		public ComparisonExpContext comparisonExp() {
			return getRuleContext(ComparisonExpContext.class,0);
		}
		public BinaryRelationExpContext binaryRelationExp() {
			return getRuleContext(BinaryRelationExpContext.class,0);
		}
		public BinaryRelationExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryRelationExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterBinaryRelationExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitBinaryRelationExp(this);
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
			setState(158);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(121);
				quantorExp();
				setState(122);
				match(BinaryRelationSymbol);
				setState(123);
				booleanExp();
				}
				break;
			case 2:
				{
				setState(125);
				notExp();
				setState(126);
				match(BinaryRelationSymbol);
				setState(127);
				booleanExp();
				}
				break;
			case 3:
				{
				setState(129);
				comparisonExp();
				setState(130);
				match(BinaryRelationSymbol);
				setState(131);
				booleanExp();
				}
				break;
			case 4:
				{
				setState(133);
				match(OpenBracket);
				setState(134);
				binaryRelationExp(0);
				setState(135);
				match(ClosedBracket);
				setState(136);
				match(BinaryRelationSymbol);
				setState(137);
				booleanExp();
				}
				break;
			case 5:
				{
				setState(139);
				match(OpenBracket);
				setState(140);
				quantorExp();
				setState(141);
				match(ClosedBracket);
				setState(142);
				match(BinaryRelationSymbol);
				setState(143);
				booleanExp();
				}
				break;
			case 6:
				{
				setState(145);
				match(OpenBracket);
				setState(146);
				notExp();
				setState(147);
				match(ClosedBracket);
				setState(148);
				match(BinaryRelationSymbol);
				setState(149);
				booleanExp();
				}
				break;
			case 7:
				{
				setState(151);
				match(OpenBracket);
				setState(152);
				comparisonExp();
				setState(153);
				match(ClosedBracket);
				setState(154);
				match(BinaryRelationSymbol);
				setState(155);
				booleanExp();
				}
				break;
			case 8:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(165);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BinaryRelationExpContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_binaryRelationExp);
					setState(160);
					if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
					setState(161);
					match(BinaryRelationSymbol);
					setState(162);
					booleanExp();
					}
					} 
				}
				setState(167);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class VoteEquivalentsContext extends ParserRuleContext {
		public TerminalNode Vote() { return getToken(FormalPropertyDescriptionParser.Vote, 0); }
		public PermutationExpContext permutationExp() {
			return getRuleContext(PermutationExpContext.class,0);
		}
		public ConcatenationExpContext concatenationExp() {
			return getRuleContext(ConcatenationExpContext.class,0);
		}
		public VoteEquivalentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voteEquivalents; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterVoteEquivalents(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitVoteEquivalents(this);
		}
	}

	public final VoteEquivalentsContext voteEquivalents() throws RecognitionException {
		VoteEquivalentsContext _localctx = new VoteEquivalentsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_voteEquivalents);
		try {
			setState(171);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				match(Vote);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				permutationExp();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(170);
				concatenationExp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConcatenationExpContext extends ParserRuleContext {
		public List<VoteEquivalentsContext> voteEquivalents() {
			return getRuleContexts(VoteEquivalentsContext.class);
		}
		public VoteEquivalentsContext voteEquivalents(int i) {
			return getRuleContext(VoteEquivalentsContext.class,i);
		}
		public TerminalNode Concatenate() { return getToken(FormalPropertyDescriptionParser.Concatenate, 0); }
		public TerminalNode Vote() { return getToken(FormalPropertyDescriptionParser.Vote, 0); }
		public PermutationExpContext permutationExp() {
			return getRuleContext(PermutationExpContext.class,0);
		}
		public ConcatenationExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_concatenationExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterConcatenationExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitConcatenationExp(this);
		}
	}

	public final ConcatenationExpContext concatenationExp() throws RecognitionException {
		ConcatenationExpContext _localctx = new ConcatenationExpContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_concatenationExp);
		try {
			setState(186);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
				enterOuterAlt(_localctx, 1);
				{
				setState(173);
				match(OpenBracket);
				setState(174);
				voteEquivalents();
				setState(175);
				match(Concatenate);
				setState(176);
				voteEquivalents();
				setState(177);
				match(ClosedBracket);
				}
				break;
			case Vote:
				enterOuterAlt(_localctx, 2);
				{
				setState(179);
				match(Vote);
				setState(180);
				match(Concatenate);
				setState(181);
				voteEquivalents();
				}
				break;
			case Permutation:
				enterOuterAlt(_localctx, 3);
				{
				setState(182);
				permutationExp();
				setState(183);
				match(Concatenate);
				setState(184);
				voteEquivalents();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SplitExpContext extends ParserRuleContext {
		public TerminalNode Split() { return getToken(FormalPropertyDescriptionParser.Split, 0); }
		public VoteEquivalentsContext voteEquivalents() {
			return getRuleContext(VoteEquivalentsContext.class,0);
		}
		public SplitExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_splitExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterSplitExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitSplitExp(this);
		}
	}

	public final SplitExpContext splitExp() throws RecognitionException {
		SplitExpContext _localctx = new SplitExpContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_splitExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(Split);
			setState(189);
			match(OpenBracket);
			setState(190);
			voteEquivalents();
			setState(191);
			match(ClosedBracket);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PermutationExpContext extends ParserRuleContext {
		public TerminalNode Permutation() { return getToken(FormalPropertyDescriptionParser.Permutation, 0); }
		public VoteEquivalentsContext voteEquivalents() {
			return getRuleContext(VoteEquivalentsContext.class,0);
		}
		public PermutationExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_permutationExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterPermutationExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitPermutationExp(this);
		}
	}

	public final PermutationExpContext permutationExp() throws RecognitionException {
		PermutationExpContext _localctx = new PermutationExpContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_permutationExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(Permutation);
			setState(194);
			match(OpenBracket);
			setState(195);
			voteEquivalents();
			setState(196);
			match(ClosedBracket);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntersectExpContext extends ParserRuleContext {
		public TerminalNode Intersect() { return getToken(FormalPropertyDescriptionParser.Intersect, 0); }
		public List<IntersectContentContext> intersectContent() {
			return getRuleContexts(IntersectContentContext.class);
		}
		public IntersectContentContext intersectContent(int i) {
			return getRuleContext(IntersectContentContext.class,i);
		}
		public IntersectExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intersectExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterIntersectExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitIntersectExp(this);
		}
	}

	public final IntersectExpContext intersectExp() throws RecognitionException {
		IntersectExpContext _localctx = new IntersectExpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_intersectExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(Intersect);
			setState(199);
			match(OpenBracket);
			setState(200);
			intersectContent();
			setState(201);
			match(T__1);
			setState(202);
			intersectContent();
			setState(203);
			match(ClosedBracket);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntersectContentContext extends ParserRuleContext {
		public TerminalNode Elect() { return getToken(FormalPropertyDescriptionParser.Elect, 0); }
		public IntersectExpContext intersectExp() {
			return getRuleContext(IntersectExpContext.class,0);
		}
		public IntersectContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intersectContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterIntersectContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitIntersectContent(this);
		}
	}

	public final IntersectContentContext intersectContent() throws RecognitionException {
		IntersectContentContext _localctx = new IntersectContentContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_intersectContent);
		try {
			setState(207);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				match(Elect);
				}
				break;
			case Intersect:
				enterOuterAlt(_localctx, 2);
				{
				setState(206);
				intersectExp();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TupleContext extends ParserRuleContext {
		public TerminalNode Vote() { return getToken(FormalPropertyDescriptionParser.Vote, 0); }
		public TupleContentContext tupleContent() {
			return getRuleContext(TupleContentContext.class,0);
		}
		public TupleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tuple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitTuple(this);
		}
	}

	public final TupleContext tuple() throws RecognitionException {
		TupleContext _localctx = new TupleContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_tuple);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(OpenBracket);
			setState(210);
			match(Vote);
			setState(211);
			tupleContent();
			setState(212);
			match(ClosedBracket);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TupleContentContext extends ParserRuleContext {
		public TerminalNode Vote() { return getToken(FormalPropertyDescriptionParser.Vote, 0); }
		public TupleContentContext tupleContent() {
			return getRuleContext(TupleContentContext.class,0);
		}
		public TupleContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterTupleContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitTupleContent(this);
		}
	}

	public final TupleContentContext tupleContent() throws RecognitionException {
		TupleContentContext _localctx = new TupleContentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_tupleContent);
		try {
			setState(219);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(214);
				match(T__1);
				setState(215);
				match(Vote);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(216);
				match(T__1);
				setState(217);
				match(Vote);
				setState(218);
				tupleContent();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantorExpContext extends ParserRuleContext {
		public TerminalNode Quantor() { return getToken(FormalPropertyDescriptionParser.Quantor, 0); }
		public PassSymbVarContext passSymbVar() {
			return getRuleContext(PassSymbVarContext.class,0);
		}
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public QuantorExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantorExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterQuantorExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitQuantorExp(this);
		}
	}

	public final QuantorExpContext quantorExp() throws RecognitionException {
		QuantorExpContext _localctx = new QuantorExpContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_quantorExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			match(Quantor);
			setState(222);
			passSymbVar();
			setState(223);
			match(T__2);
			setState(224);
			booleanExp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotExpContext extends ParserRuleContext {
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public NotExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterNotExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitNotExp(this);
		}
	}

	public final NotExpContext notExp() throws RecognitionException {
		NotExpContext _localctx = new NotExpContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_notExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(T__3);
			setState(227);
			booleanExp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonExpContext extends ParserRuleContext {
		public List<TypeExpContext> typeExp() {
			return getRuleContexts(TypeExpContext.class);
		}
		public TypeExpContext typeExp(int i) {
			return getRuleContext(TypeExpContext.class,i);
		}
		public TerminalNode ComparisonSymbol() { return getToken(FormalPropertyDescriptionParser.ComparisonSymbol, 0); }
		public ComparisonExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterComparisonExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitComparisonExp(this);
		}
	}

	public final ComparisonExpContext comparisonExp() throws RecognitionException {
		ComparisonExpContext _localctx = new ComparisonExpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_comparisonExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			typeExp();
			setState(230);
			match(ComparisonSymbol);
			setState(231);
			typeExp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeExpContext extends ParserRuleContext {
		public ElectExpContext electExp() {
			return getRuleContext(ElectExpContext.class,0);
		}
		public VoteExpContext voteExp() {
			return getRuleContext(VoteExpContext.class,0);
		}
		public NumberExpressionContext numberExpression() {
			return getRuleContext(NumberExpressionContext.class,0);
		}
		public SymbolicVarExpContext symbolicVarExp() {
			return getRuleContext(SymbolicVarExpContext.class,0);
		}
		public TypeByPosExpContext typeByPosExp() {
			return getRuleContext(TypeByPosExpContext.class,0);
		}
		public TypeExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterTypeExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitTypeExp(this);
		}
	}

	public final TypeExpContext typeExp() throws RecognitionException {
		TypeExpContext _localctx = new TypeExpContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_typeExp);
		try {
			setState(238);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1);
				{
				setState(233);
				electExp();
				}
				break;
			case Vote:
				enterOuterAlt(_localctx, 2);
				{
				setState(234);
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
				enterOuterAlt(_localctx, 3);
				{
				setState(235);
				numberExpression(0);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(236);
				symbolicVarExp();
				}
				break;
			case T__4:
			case T__5:
			case T__6:
				enterOuterAlt(_localctx, 5);
				{
				setState(237);
				typeByPosExp();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberExpressionContext extends ParserRuleContext {
		public List<NumberExpressionContext> numberExpression() {
			return getRuleContexts(NumberExpressionContext.class);
		}
		public NumberExpressionContext numberExpression(int i) {
			return getRuleContext(NumberExpressionContext.class,i);
		}
		public VoteSumExpContext voteSumExp() {
			return getRuleContext(VoteSumExpContext.class,0);
		}
		public VoteSumUniqueExpContext voteSumUniqueExp() {
			return getRuleContext(VoteSumUniqueExpContext.class,0);
		}
		public ConstantExpContext constantExp() {
			return getRuleContext(ConstantExpContext.class,0);
		}
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public TerminalNode Mult() { return getToken(FormalPropertyDescriptionParser.Mult, 0); }
		public TerminalNode Add() { return getToken(FormalPropertyDescriptionParser.Add, 0); }
		public NumberExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numberExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterNumberExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitNumberExpression(this);
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
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_numberExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
				{
				setState(241);
				match(OpenBracket);
				setState(242);
				numberExpression(0);
				setState(243);
				match(ClosedBracket);
				}
				break;
			case Votesum:
				{
				setState(245);
				voteSumExp();
				}
				break;
			case VotesumUnique:
				{
				setState(246);
				voteSumUniqueExp();
				}
				break;
			case T__7:
			case T__8:
			case T__9:
				{
				setState(247);
				constantExp();
				}
				break;
			case Integer:
				{
				setState(248);
				integer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(259);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(257);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new NumberExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
						setState(251);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(252);
						match(Mult);
						setState(253);
						numberExpression(7);
						}
						break;
					case 2:
						{
						_localctx = new NumberExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
						setState(254);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(255);
						match(Add);
						setState(256);
						numberExpression(6);
						}
						break;
					}
					} 
				}
				setState(261);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TypeByPosExpContext extends ParserRuleContext {
		public VoterByPosExpContext voterByPosExp() {
			return getRuleContext(VoterByPosExpContext.class,0);
		}
		public CandByPosExpContext candByPosExp() {
			return getRuleContext(CandByPosExpContext.class,0);
		}
		public SeatByPosExpContext seatByPosExp() {
			return getRuleContext(SeatByPosExpContext.class,0);
		}
		public TypeByPosExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeByPosExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterTypeByPosExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitTypeByPosExp(this);
		}
	}

	public final TypeByPosExpContext typeByPosExp() throws RecognitionException {
		TypeByPosExpContext _localctx = new TypeByPosExpContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_typeByPosExp);
		try {
			setState(265);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				voterByPosExp();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(263);
				candByPosExp();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 3);
				{
				setState(264);
				seatByPosExp();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoterByPosExpContext extends ParserRuleContext {
		public PassPositionContext passPosition() {
			return getRuleContext(PassPositionContext.class,0);
		}
		public VoterByPosExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voterByPosExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterVoterByPosExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitVoterByPosExp(this);
		}
	}

	public final VoterByPosExpContext voterByPosExp() throws RecognitionException {
		VoterByPosExpContext _localctx = new VoterByPosExpContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_voterByPosExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(T__4);
			setState(268);
			passPosition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CandByPosExpContext extends ParserRuleContext {
		public PassPositionContext passPosition() {
			return getRuleContext(PassPositionContext.class,0);
		}
		public CandByPosExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_candByPosExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterCandByPosExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitCandByPosExp(this);
		}
	}

	public final CandByPosExpContext candByPosExp() throws RecognitionException {
		CandByPosExpContext _localctx = new CandByPosExpContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_candByPosExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(T__5);
			setState(271);
			passPosition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SeatByPosExpContext extends ParserRuleContext {
		public PassPositionContext passPosition() {
			return getRuleContext(PassPositionContext.class,0);
		}
		public SeatByPosExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_seatByPosExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterSeatByPosExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitSeatByPosExp(this);
		}
	}

	public final SeatByPosExpContext seatByPosExp() throws RecognitionException {
		SeatByPosExpContext _localctx = new SeatByPosExpContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_seatByPosExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			match(T__6);
			setState(274);
			passPosition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegerContext extends ParserRuleContext {
		public TerminalNode Integer() { return getToken(FormalPropertyDescriptionParser.Integer, 0); }
		public IntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitInteger(this);
		}
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_integer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			match(Integer);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElectExpContext extends ParserRuleContext {
		public TerminalNode Elect() { return getToken(FormalPropertyDescriptionParser.Elect, 0); }
		public List<PassTypeContext> passType() {
			return getRuleContexts(PassTypeContext.class);
		}
		public PassTypeContext passType(int i) {
			return getRuleContext(PassTypeContext.class,i);
		}
		public ElectExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_electExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterElectExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitElectExp(this);
		}
	}

	public final ElectExpContext electExp() throws RecognitionException {
		ElectExpContext _localctx = new ElectExpContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_electExp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			match(Elect);
			setState(282);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(279);
					passType();
					}
					} 
				}
				setState(284);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoteExpContext extends ParserRuleContext {
		public TerminalNode Vote() { return getToken(FormalPropertyDescriptionParser.Vote, 0); }
		public List<PassTypeContext> passType() {
			return getRuleContexts(PassTypeContext.class);
		}
		public PassTypeContext passType(int i) {
			return getRuleContext(PassTypeContext.class,i);
		}
		public VoteExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voteExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterVoteExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitVoteExp(this);
		}
	}

	public final VoteExpContext voteExp() throws RecognitionException {
		VoteExpContext _localctx = new VoteExpContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_voteExp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(Vote);
			setState(289);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(286);
					passType();
					}
					} 
				}
				setState(291);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PassTypeContext extends ParserRuleContext {
		public PassSymbVarContext passSymbVar() {
			return getRuleContext(PassSymbVarContext.class,0);
		}
		public PassByPosContext passByPos() {
			return getRuleContext(PassByPosContext.class,0);
		}
		public PassTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_passType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterPassType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitPassType(this);
		}
	}

	public final PassTypeContext passType() throws RecognitionException {
		PassTypeContext _localctx = new PassTypeContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_passType);
		try {
			setState(294);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(292);
				passSymbVar();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(293);
				passByPos();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantExpContext extends ParserRuleContext {
		public ConstantExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterConstantExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitConstantExp(this);
		}
	}

	public final ConstantExpContext constantExp() throws RecognitionException {
		ConstantExpContext _localctx = new ConstantExpContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_constantExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoteSumExpContext extends ParserRuleContext {
		public TerminalNode Votesum() { return getToken(FormalPropertyDescriptionParser.Votesum, 0); }
		public PassTypeContext passType() {
			return getRuleContext(PassTypeContext.class,0);
		}
		public VoteSumExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voteSumExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterVoteSumExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitVoteSumExp(this);
		}
	}

	public final VoteSumExpContext voteSumExp() throws RecognitionException {
		VoteSumExpContext _localctx = new VoteSumExpContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_voteSumExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			match(Votesum);
			setState(299);
			passType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoteSumUniqueExpContext extends ParserRuleContext {
		public TerminalNode VotesumUnique() { return getToken(FormalPropertyDescriptionParser.VotesumUnique, 0); }
		public PassTypeContext passType() {
			return getRuleContext(PassTypeContext.class,0);
		}
		public VoteSumUniqueExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voteSumUniqueExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterVoteSumUniqueExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitVoteSumUniqueExp(this);
		}
	}

	public final VoteSumUniqueExpContext voteSumUniqueExp() throws RecognitionException {
		VoteSumUniqueExpContext _localctx = new VoteSumUniqueExpContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_voteSumUniqueExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			match(VotesumUnique);
			setState(302);
			passType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PassSymbVarContext extends ParserRuleContext {
		public TerminalNode OpenBracket() { return getToken(FormalPropertyDescriptionParser.OpenBracket, 0); }
		public SymbolicVarExpContext symbolicVarExp() {
			return getRuleContext(SymbolicVarExpContext.class,0);
		}
		public TerminalNode ClosedBracket() { return getToken(FormalPropertyDescriptionParser.ClosedBracket, 0); }
		public PassSymbVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_passSymbVar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterPassSymbVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitPassSymbVar(this);
		}
	}

	public final PassSymbVarContext passSymbVar() throws RecognitionException {
		PassSymbVarContext _localctx = new PassSymbVarContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_passSymbVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			match(OpenBracket);
			setState(305);
			symbolicVarExp();
			setState(306);
			match(ClosedBracket);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PassPositionContext extends ParserRuleContext {
		public TerminalNode OpenBracket() { return getToken(FormalPropertyDescriptionParser.OpenBracket, 0); }
		public NumberExpressionContext numberExpression() {
			return getRuleContext(NumberExpressionContext.class,0);
		}
		public TerminalNode ClosedBracket() { return getToken(FormalPropertyDescriptionParser.ClosedBracket, 0); }
		public PassPositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_passPosition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterPassPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitPassPosition(this);
		}
	}

	public final PassPositionContext passPosition() throws RecognitionException {
		PassPositionContext _localctx = new PassPositionContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_passPosition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			match(OpenBracket);
			setState(309);
			numberExpression(0);
			setState(310);
			match(ClosedBracket);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PassByPosContext extends ParserRuleContext {
		public TerminalNode OpenBracket() { return getToken(FormalPropertyDescriptionParser.OpenBracket, 0); }
		public TypeByPosExpContext typeByPosExp() {
			return getRuleContext(TypeByPosExpContext.class,0);
		}
		public TerminalNode ClosedBracket() { return getToken(FormalPropertyDescriptionParser.ClosedBracket, 0); }
		public PassByPosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_passByPos; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterPassByPos(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitPassByPos(this);
		}
	}

	public final PassByPosContext passByPos() throws RecognitionException {
		PassByPosContext _localctx = new PassByPosContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_passByPos);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			match(OpenBracket);
			setState(313);
			typeByPosExp();
			setState(314);
			match(ClosedBracket);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SymbolicVarExpContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(FormalPropertyDescriptionParser.Identifier, 0); }
		public SymbolicVarExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbolicVarExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterSymbolicVarExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitSymbolicVarExp(this);
		}
	}

	public final SymbolicVarExpContext symbolicVarExp() throws RecognitionException {
		SymbolicVarExpContext _localctx = new SymbolicVarExpContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_symbolicVarExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return binaryRelationExp_sempred((BinaryRelationExpContext)_localctx, predIndex);
		case 20:
			return numberExpression_sempred((NumberExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean binaryRelationExp_sempred(BinaryRelationExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 9);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\"\u0141\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\7\2L\n\2\f\2\16\2O\13\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3]\n\3\3\4\3\4\3\4\3\4\3\5\3\5"+
		"\5\5e\n\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\5\by\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00a1\n\t\3\t\3\t\3\t\7\t\u00a6"+
		"\n\t\f\t\16\t\u00a9\13\t\3\n\3\n\3\n\5\n\u00ae\n\n\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00bd\n\13\3\f\3\f"+
		"\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\17\3\17\5\17\u00d2\n\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21"+
		"\3\21\5\21\u00de\n\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\25\5\25\u00f1\n\25\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\5\26\u00fc\n\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\7\26\u0104\n\26\f\26\16\26\u0107\13\26\3\27\3\27\3\27\5\27\u010c\n\27"+
		"\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\7\34"+
		"\u011b\n\34\f\34\16\34\u011e\13\34\3\35\3\35\7\35\u0122\n\35\f\35\16\35"+
		"\u0125\13\35\3\36\3\36\5\36\u0129\n\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3\""+
		"\3\"\3\"\3\"\3#\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\2\4\20*&\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH\2\3\3\2\n\f\2\u0144"+
		"\2M\3\2\2\2\4\\\3\2\2\2\6^\3\2\2\2\bd\3\2\2\2\nf\3\2\2\2\fj\3\2\2\2\16"+
		"x\3\2\2\2\20\u00a0\3\2\2\2\22\u00ad\3\2\2\2\24\u00bc\3\2\2\2\26\u00be"+
		"\3\2\2\2\30\u00c3\3\2\2\2\32\u00c8\3\2\2\2\34\u00d1\3\2\2\2\36\u00d3\3"+
		"\2\2\2 \u00dd\3\2\2\2\"\u00df\3\2\2\2$\u00e4\3\2\2\2&\u00e7\3\2\2\2(\u00f0"+
		"\3\2\2\2*\u00fb\3\2\2\2,\u010b\3\2\2\2.\u010d\3\2\2\2\60\u0110\3\2\2\2"+
		"\62\u0113\3\2\2\2\64\u0116\3\2\2\2\66\u0118\3\2\2\28\u011f\3\2\2\2:\u0128"+
		"\3\2\2\2<\u012a\3\2\2\2>\u012c\3\2\2\2@\u012f\3\2\2\2B\u0132\3\2\2\2D"+
		"\u0136\3\2\2\2F\u013a\3\2\2\2H\u013e\3\2\2\2JL\5\4\3\2KJ\3\2\2\2LO\3\2"+
		"\2\2MK\3\2\2\2MN\3\2\2\2N\3\3\2\2\2OM\3\2\2\2PQ\5\16\b\2QR\7\3\2\2R]\3"+
		"\2\2\2ST\5\6\4\2TU\7\3\2\2U]\3\2\2\2VW\5\n\6\2WX\7\3\2\2X]\3\2\2\2YZ\5"+
		"\f\7\2Z[\7\3\2\2[]\3\2\2\2\\P\3\2\2\2\\S\3\2\2\2\\V\3\2\2\2\\Y\3\2\2\2"+
		"]\5\3\2\2\2^_\7\21\2\2_`\7\32\2\2`a\5\b\5\2a\7\3\2\2\2be\5\24\13\2ce\5"+
		"\30\r\2db\3\2\2\2dc\3\2\2\2e\t\3\2\2\2fg\5\36\20\2gh\7\32\2\2hi\5\26\f"+
		"\2i\13\3\2\2\2jk\7\22\2\2kl\7\32\2\2lm\5\32\16\2m\r\3\2\2\2ny\5\"\22\2"+
		"oy\5\24\13\2py\5\26\f\2qy\5\20\t\2ry\5$\23\2sy\5&\24\2tu\7\26\2\2uv\5"+
		"\16\b\2vw\7\25\2\2wy\3\2\2\2xn\3\2\2\2xo\3\2\2\2xp\3\2\2\2xq\3\2\2\2x"+
		"r\3\2\2\2xs\3\2\2\2xt\3\2\2\2y\17\3\2\2\2z{\b\t\1\2{|\5\"\22\2|}\7\34"+
		"\2\2}~\5\16\b\2~\u00a1\3\2\2\2\177\u0080\5$\23\2\u0080\u0081\7\34\2\2"+
		"\u0081\u0082\5\16\b\2\u0082\u00a1\3\2\2\2\u0083\u0084\5&\24\2\u0084\u0085"+
		"\7\34\2\2\u0085\u0086\5\16\b\2\u0086\u00a1\3\2\2\2\u0087\u0088\7\26\2"+
		"\2\u0088\u0089\5\20\t\2\u0089\u008a\7\25\2\2\u008a\u008b\7\34\2\2\u008b"+
		"\u008c\5\16\b\2\u008c\u00a1\3\2\2\2\u008d\u008e\7\26\2\2\u008e\u008f\5"+
		"\"\22\2\u008f\u0090\7\25\2\2\u0090\u0091\7\34\2\2\u0091\u0092\5\16\b\2"+
		"\u0092\u00a1\3\2\2\2\u0093\u0094\7\26\2\2\u0094\u0095\5$\23\2\u0095\u0096"+
		"\7\25\2\2\u0096\u0097\7\34\2\2\u0097\u0098\5\16\b\2\u0098\u00a1\3\2\2"+
		"\2\u0099\u009a\7\26\2\2\u009a\u009b\5&\24\2\u009b\u009c\7\25\2\2\u009c"+
		"\u009d\7\34\2\2\u009d\u009e\5\16\b\2\u009e\u00a1\3\2\2\2\u009f\u00a1\3"+
		"\2\2\2\u00a0z\3\2\2\2\u00a0\177\3\2\2\2\u00a0\u0083\3\2\2\2\u00a0\u0087"+
		"\3\2\2\2\u00a0\u008d\3\2\2\2\u00a0\u0093\3\2\2\2\u00a0\u0099\3\2\2\2\u00a0"+
		"\u009f\3\2\2\2\u00a1\u00a7\3\2\2\2\u00a2\u00a3\f\13\2\2\u00a3\u00a4\7"+
		"\34\2\2\u00a4\u00a6\5\16\b\2\u00a5\u00a2\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7"+
		"\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\21\3\2\2\2\u00a9\u00a7\3\2\2"+
		"\2\u00aa\u00ae\7\21\2\2\u00ab\u00ae\5\30\r\2\u00ac\u00ae\5\24\13\2\u00ad"+
		"\u00aa\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ac\3\2\2\2\u00ae\23\3\2\2"+
		"\2\u00af\u00b0\7\26\2\2\u00b0\u00b1\5\22\n\2\u00b1\u00b2\7\17\2\2\u00b2"+
		"\u00b3\5\22\n\2\u00b3\u00b4\7\25\2\2\u00b4\u00bd\3\2\2\2\u00b5\u00b6\7"+
		"\21\2\2\u00b6\u00b7\7\17\2\2\u00b7\u00bd\5\22\n\2\u00b8\u00b9\5\30\r\2"+
		"\u00b9\u00ba\7\17\2\2\u00ba\u00bb\5\22\n\2\u00bb\u00bd\3\2\2\2\u00bc\u00af"+
		"\3\2\2\2\u00bc\u00b5\3\2\2\2\u00bc\u00b8\3\2\2\2\u00bd\25\3\2\2\2\u00be"+
		"\u00bf\7\30\2\2\u00bf\u00c0\7\26\2\2\u00c0\u00c1\5\22\n\2\u00c1\u00c2"+
		"\7\25\2\2\u00c2\27\3\2\2\2\u00c3\u00c4\7\31\2\2\u00c4\u00c5\7\26\2\2\u00c5"+
		"\u00c6\5\22\n\2\u00c6\u00c7\7\25\2\2\u00c7\31\3\2\2\2\u00c8\u00c9\7\20"+
		"\2\2\u00c9\u00ca\7\26\2\2\u00ca\u00cb\5\34\17\2\u00cb\u00cc\7\4\2\2\u00cc"+
		"\u00cd\5\34\17\2\u00cd\u00ce\7\25\2\2\u00ce\33\3\2\2\2\u00cf\u00d2\7\22"+
		"\2\2\u00d0\u00d2\5\32\16\2\u00d1\u00cf\3\2\2\2\u00d1\u00d0\3\2\2\2\u00d2"+
		"\35\3\2\2\2\u00d3\u00d4\7\26\2\2\u00d4\u00d5\7\21\2\2\u00d5\u00d6\5 \21"+
		"\2\u00d6\u00d7\7\25\2\2\u00d7\37\3\2\2\2\u00d8\u00d9\7\4\2\2\u00d9\u00de"+
		"\7\21\2\2\u00da\u00db\7\4\2\2\u00db\u00dc\7\21\2\2\u00dc\u00de\5 \21\2"+
		"\u00dd\u00d8\3\2\2\2\u00dd\u00da\3\2\2\2\u00de!\3\2\2\2\u00df\u00e0\7"+
		"\27\2\2\u00e0\u00e1\5B\"\2\u00e1\u00e2\7\5\2\2\u00e2\u00e3\5\16\b\2\u00e3"+
		"#\3\2\2\2\u00e4\u00e5\7\6\2\2\u00e5\u00e6\5\16\b\2\u00e6%\3\2\2\2\u00e7"+
		"\u00e8\5(\25\2\u00e8\u00e9\7\33\2\2\u00e9\u00ea\5(\25\2\u00ea\'\3\2\2"+
		"\2\u00eb\u00f1\5\66\34\2\u00ec\u00f1\58\35\2\u00ed\u00f1\5*\26\2\u00ee"+
		"\u00f1\5H%\2\u00ef\u00f1\5,\27\2\u00f0\u00eb\3\2\2\2\u00f0\u00ec\3\2\2"+
		"\2\u00f0\u00ed\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f0\u00ef\3\2\2\2\u00f1)"+
		"\3\2\2\2\u00f2\u00f3\b\26\1\2\u00f3\u00f4\7\26\2\2\u00f4\u00f5\5*\26\2"+
		"\u00f5\u00f6\7\25\2\2\u00f6\u00fc\3\2\2\2\u00f7\u00fc\5> \2\u00f8\u00fc"+
		"\5@!\2\u00f9\u00fc\5<\37\2\u00fa\u00fc\5\64\33\2\u00fb\u00f2\3\2\2\2\u00fb"+
		"\u00f7\3\2\2\2\u00fb\u00f8\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fb\u00fa\3\2"+
		"\2\2\u00fc\u0105\3\2\2\2\u00fd\u00fe\f\b\2\2\u00fe\u00ff\7\r\2\2\u00ff"+
		"\u0104\5*\26\t\u0100\u0101\f\7\2\2\u0101\u0102\7\16\2\2\u0102\u0104\5"+
		"*\26\b\u0103\u00fd\3\2\2\2\u0103\u0100\3\2\2\2\u0104\u0107\3\2\2\2\u0105"+
		"\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106+\3\2\2\2\u0107\u0105\3\2\2\2"+
		"\u0108\u010c\5.\30\2\u0109\u010c\5\60\31\2\u010a\u010c\5\62\32\2\u010b"+
		"\u0108\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010a\3\2\2\2\u010c-\3\2\2\2"+
		"\u010d\u010e\7\7\2\2\u010e\u010f\5D#\2\u010f/\3\2\2\2\u0110\u0111\7\b"+
		"\2\2\u0111\u0112\5D#\2\u0112\61\3\2\2\2\u0113\u0114\7\t\2\2\u0114\u0115"+
		"\5D#\2\u0115\63\3\2\2\2\u0116\u0117\7\35\2\2\u0117\65\3\2\2\2\u0118\u011c"+
		"\7\22\2\2\u0119\u011b\5:\36\2\u011a\u0119\3\2\2\2\u011b\u011e\3\2\2\2"+
		"\u011c\u011a\3\2\2\2\u011c\u011d\3\2\2\2\u011d\67\3\2\2\2\u011e\u011c"+
		"\3\2\2\2\u011f\u0123\7\21\2\2\u0120\u0122\5:\36\2\u0121\u0120\3\2\2\2"+
		"\u0122\u0125\3\2\2\2\u0123\u0121\3\2\2\2\u0123\u0124\3\2\2\2\u01249\3"+
		"\2\2\2\u0125\u0123\3\2\2\2\u0126\u0129\5B\"\2\u0127\u0129\5F$\2\u0128"+
		"\u0126\3\2\2\2\u0128\u0127\3\2\2\2\u0129;\3\2\2\2\u012a\u012b\t\2\2\2"+
		"\u012b=\3\2\2\2\u012c\u012d\7\23\2\2\u012d\u012e\5:\36\2\u012e?\3\2\2"+
		"\2\u012f\u0130\7\24\2\2\u0130\u0131\5:\36\2\u0131A\3\2\2\2\u0132\u0133"+
		"\7\26\2\2\u0133\u0134\5H%\2\u0134\u0135\7\25\2\2\u0135C\3\2\2\2\u0136"+
		"\u0137\7\26\2\2\u0137\u0138\5*\26\2\u0138\u0139\7\25\2\2\u0139E\3\2\2"+
		"\2\u013a\u013b\7\26\2\2\u013b\u013c\5,\27\2\u013c\u013d\7\25\2\2\u013d"+
		"G\3\2\2\2\u013e\u013f\7\36\2\2\u013fI\3\2\2\2\24M\\dx\u00a0\u00a7\u00ad"+
		"\u00bc\u00d1\u00dd\u00f0\u00fb\u0103\u0105\u010b\u011c\u0123\u0128";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}