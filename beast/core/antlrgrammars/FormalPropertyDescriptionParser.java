// Generated from FormalPropertyDescription.g4 by ANTLR 4.7.1
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << Vote) | (1L << Elect) | (1L << Votesum) | (1L << VotesumUnique) | (1L << OpenBracket) | (1L << Quantor) | (1L << Split) | (1L << Integer) | (1L << Identifier))) != 0)) {
				{
				{
				setState(72);
				booleanExpListElement();
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
			switch (_input.LA(1)) {
			case OpenBracket:
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				concatenationExp();
				}
				break;
			case Permutation:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				permutationExp();
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
		public ConcatenationExpContext concatenationExp() {
			return getRuleContext(ConcatenationExpContext.class,0);
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
			setState(167);
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
				concatenationExp();
				setState(134);
				match(BinaryRelationSymbol);
				setState(135);
				booleanExp();
				}
				break;
			case 5:
				{
				setState(137);
				match(OpenBracket);
				setState(138);
				binaryRelationExp(0);
				setState(139);
				match(ClosedBracket);
				setState(140);
				match(BinaryRelationSymbol);
				setState(141);
				booleanExp();
				}
				break;
			case 6:
				{
				setState(143);
				match(OpenBracket);
				setState(144);
				quantorExp();
				setState(145);
				match(ClosedBracket);
				setState(146);
				match(BinaryRelationSymbol);
				setState(147);
				booleanExp();
				}
				break;
			case 7:
				{
				setState(149);
				match(OpenBracket);
				setState(150);
				notExp();
				setState(151);
				match(ClosedBracket);
				setState(152);
				match(BinaryRelationSymbol);
				setState(153);
				booleanExp();
				}
				break;
			case 8:
				{
				setState(155);
				match(OpenBracket);
				setState(156);
				comparisonExp();
				setState(157);
				match(ClosedBracket);
				setState(158);
				match(BinaryRelationSymbol);
				setState(159);
				booleanExp();
				}
				break;
			case 9:
				{
				setState(161);
				match(OpenBracket);
				setState(162);
				concatenationExp();
				setState(163);
				match(ClosedBracket);
				setState(164);
				match(BinaryRelationSymbol);
				setState(165);
				booleanExp();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(174);
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
					setState(169);
					if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
					setState(170);
					match(BinaryRelationSymbol);
					setState(171);
					booleanExp();
					}
					} 
				}
				setState(176);
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
			setState(180);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Vote:
				enterOuterAlt(_localctx, 1);
				{
				setState(177);
				match(Vote);
				}
				break;
			case Permutation:
				enterOuterAlt(_localctx, 2);
				{
				setState(178);
				permutationExp();
				}
				break;
			case OpenBracket:
				enterOuterAlt(_localctx, 3);
				{
				setState(179);
				concatenationExp();
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

	public static class ConcatenationExpContext extends ParserRuleContext {
		public List<VoteEquivalentsContext> voteEquivalents() {
			return getRuleContexts(VoteEquivalentsContext.class);
		}
		public VoteEquivalentsContext voteEquivalents(int i) {
			return getRuleContext(VoteEquivalentsContext.class,i);
		}
		public TerminalNode Concatenate() { return getToken(FormalPropertyDescriptionParser.Concatenate, 0); }
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
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(OpenBracket);
			setState(183);
			voteEquivalents();
			setState(184);
			match(Concatenate);
			setState(185);
			voteEquivalents();
			setState(186);
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
		public IntersectContentContext intersectContent() {
			return getRuleContext(IntersectContentContext.class,0);
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
			setState(205);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1);
				{
				setState(203);
				match(Elect);
				}
				break;
			case Intersect:
				enterOuterAlt(_localctx, 2);
				{
				setState(204);
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
			setState(207);
			match(OpenBracket);
			setState(208);
			match(Vote);
			setState(209);
			tupleContent();
			setState(210);
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
			setState(217);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(212);
				match(T__1);
				setState(213);
				match(Vote);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(214);
				match(T__1);
				setState(215);
				match(Vote);
				setState(216);
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
			setState(219);
			match(Quantor);
			setState(220);
			passSymbVar();
			setState(221);
			match(T__2);
			setState(222);
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
			setState(224);
			match(T__3);
			setState(225);
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
			setState(227);
			typeExp();
			setState(228);
			match(ComparisonSymbol);
			setState(229);
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
			setState(236);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1);
				{
				setState(231);
				electExp();
				}
				break;
			case Vote:
				enterOuterAlt(_localctx, 2);
				{
				setState(232);
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
				setState(233);
				numberExpression(0);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(234);
				symbolicVarExp();
				}
				break;
			case T__4:
			case T__5:
			case T__6:
				enterOuterAlt(_localctx, 5);
				{
				setState(235);
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
			setState(247);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
				{
				setState(239);
				match(OpenBracket);
				setState(240);
				numberExpression(0);
				setState(241);
				match(ClosedBracket);
				}
				break;
			case Votesum:
				{
				setState(243);
				voteSumExp();
				}
				break;
			case VotesumUnique:
				{
				setState(244);
				voteSumUniqueExp();
				}
				break;
			case T__7:
			case T__8:
			case T__9:
				{
				setState(245);
				constantExp();
				}
				break;
			case Integer:
				{
				setState(246);
				integer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(257);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(255);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new NumberExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
						setState(249);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(250);
						match(Mult);
						setState(251);
						numberExpression(7);
						}
						break;
					case 2:
						{
						_localctx = new NumberExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
						setState(252);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(253);
						match(Add);
						setState(254);
						numberExpression(6);
						}
						break;
					}
					} 
				}
				setState(259);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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
			setState(263);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(260);
				voterByPosExp();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(261);
				candByPosExp();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 3);
				{
				setState(262);
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
			setState(265);
			match(T__4);
			setState(266);
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
			setState(268);
			match(T__5);
			setState(269);
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
			setState(271);
			match(T__6);
			setState(272);
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
			setState(274);
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
			setState(276);
			match(Elect);
			setState(280);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(277);
					passType();
					}
					} 
				}
				setState(282);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
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
			setState(283);
			match(Vote);
			setState(287);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(284);
					passType();
					}
					} 
				}
				setState(289);
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
			setState(292);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(290);
				passSymbVar();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(291);
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
			setState(294);
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
			setState(296);
			match(Votesum);
			setState(297);
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
			setState(299);
			match(VotesumUnique);
			setState(300);
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
			setState(302);
			match(OpenBracket);
			setState(303);
			symbolicVarExp();
			setState(304);
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
			setState(306);
			match(OpenBracket);
			setState(307);
			numberExpression(0);
			setState(308);
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
			setState(310);
			match(OpenBracket);
			setState(311);
			typeByPosExp();
			setState(312);
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
			setState(314);
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
			return precpred(_ctx, 10);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\"\u013f\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\7\2L\n\2\f\2\16\2O\13\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3]\n\3\3\4\3\4\3\4\3\4\3\5\3\5"+
		"\5\5e\n\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\5\by\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\5\t\u00aa\n\t\3\t\3\t\3\t\7\t\u00af\n\t\f\t\16\t\u00b2\13\t\3\n\3\n\3"+
		"\n\5\n\u00b7\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\5\17\u00d0\n\17\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\5\21\u00dc\n\21\3\22"+
		"\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25"+
		"\3\25\3\25\5\25\u00ef\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\5\26\u00fa\n\26\3\26\3\26\3\26\3\26\3\26\3\26\7\26\u0102\n\26\f\26\16"+
		"\26\u0105\13\26\3\27\3\27\3\27\5\27\u010a\n\27\3\30\3\30\3\30\3\31\3\31"+
		"\3\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\7\34\u0119\n\34\f\34\16\34\u011c"+
		"\13\34\3\35\3\35\7\35\u0120\n\35\f\35\16\35\u0123\13\35\3\36\3\36\5\36"+
		"\u0127\n\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3"+
		"$\3$\3$\3$\3%\3%\3%\2\4\20*&\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \""+
		"$&(*,.\60\62\64\668:<>@BDFH\2\3\3\2\n\f\2\u0141\2M\3\2\2\2\4\\\3\2\2\2"+
		"\6^\3\2\2\2\bd\3\2\2\2\nf\3\2\2\2\fj\3\2\2\2\16x\3\2\2\2\20\u00a9\3\2"+
		"\2\2\22\u00b6\3\2\2\2\24\u00b8\3\2\2\2\26\u00be\3\2\2\2\30\u00c3\3\2\2"+
		"\2\32\u00c8\3\2\2\2\34\u00cf\3\2\2\2\36\u00d1\3\2\2\2 \u00db\3\2\2\2\""+
		"\u00dd\3\2\2\2$\u00e2\3\2\2\2&\u00e5\3\2\2\2(\u00ee\3\2\2\2*\u00f9\3\2"+
		"\2\2,\u0109\3\2\2\2.\u010b\3\2\2\2\60\u010e\3\2\2\2\62\u0111\3\2\2\2\64"+
		"\u0114\3\2\2\2\66\u0116\3\2\2\28\u011d\3\2\2\2:\u0126\3\2\2\2<\u0128\3"+
		"\2\2\2>\u012a\3\2\2\2@\u012d\3\2\2\2B\u0130\3\2\2\2D\u0134\3\2\2\2F\u0138"+
		"\3\2\2\2H\u013c\3\2\2\2JL\5\4\3\2KJ\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2"+
		"\2\2N\3\3\2\2\2OM\3\2\2\2PQ\5\16\b\2QR\7\3\2\2R]\3\2\2\2ST\5\6\4\2TU\7"+
		"\3\2\2U]\3\2\2\2VW\5\n\6\2WX\7\3\2\2X]\3\2\2\2YZ\5\f\7\2Z[\7\3\2\2[]\3"+
		"\2\2\2\\P\3\2\2\2\\S\3\2\2\2\\V\3\2\2\2\\Y\3\2\2\2]\5\3\2\2\2^_\7\21\2"+
		"\2_`\7\32\2\2`a\5\b\5\2a\7\3\2\2\2be\5\24\13\2ce\5\30\r\2db\3\2\2\2dc"+
		"\3\2\2\2e\t\3\2\2\2fg\5\36\20\2gh\7\32\2\2hi\5\26\f\2i\13\3\2\2\2jk\7"+
		"\22\2\2kl\7\32\2\2lm\5\32\16\2m\r\3\2\2\2ny\5\"\22\2oy\5\24\13\2py\5\26"+
		"\f\2qy\5\20\t\2ry\5$\23\2sy\5&\24\2tu\7\26\2\2uv\5\16\b\2vw\7\25\2\2w"+
		"y\3\2\2\2xn\3\2\2\2xo\3\2\2\2xp\3\2\2\2xq\3\2\2\2xr\3\2\2\2xs\3\2\2\2"+
		"xt\3\2\2\2y\17\3\2\2\2z{\b\t\1\2{|\5\"\22\2|}\7\34\2\2}~\5\16\b\2~\u00aa"+
		"\3\2\2\2\177\u0080\5$\23\2\u0080\u0081\7\34\2\2\u0081\u0082\5\16\b\2\u0082"+
		"\u00aa\3\2\2\2\u0083\u0084\5&\24\2\u0084\u0085\7\34\2\2\u0085\u0086\5"+
		"\16\b\2\u0086\u00aa\3\2\2\2\u0087\u0088\5\24\13\2\u0088\u0089\7\34\2\2"+
		"\u0089\u008a\5\16\b\2\u008a\u00aa\3\2\2\2\u008b\u008c\7\26\2\2\u008c\u008d"+
		"\5\20\t\2\u008d\u008e\7\25\2\2\u008e\u008f\7\34\2\2\u008f\u0090\5\16\b"+
		"\2\u0090\u00aa\3\2\2\2\u0091\u0092\7\26\2\2\u0092\u0093\5\"\22\2\u0093"+
		"\u0094\7\25\2\2\u0094\u0095\7\34\2\2\u0095\u0096\5\16\b\2\u0096\u00aa"+
		"\3\2\2\2\u0097\u0098\7\26\2\2\u0098\u0099\5$\23\2\u0099\u009a\7\25\2\2"+
		"\u009a\u009b\7\34\2\2\u009b\u009c\5\16\b\2\u009c\u00aa\3\2\2\2\u009d\u009e"+
		"\7\26\2\2\u009e\u009f\5&\24\2\u009f\u00a0\7\25\2\2\u00a0\u00a1\7\34\2"+
		"\2\u00a1\u00a2\5\16\b\2\u00a2\u00aa\3\2\2\2\u00a3\u00a4\7\26\2\2\u00a4"+
		"\u00a5\5\24\13\2\u00a5\u00a6\7\25\2\2\u00a6\u00a7\7\34\2\2\u00a7\u00a8"+
		"\5\16\b\2\u00a8\u00aa\3\2\2\2\u00a9z\3\2\2\2\u00a9\177\3\2\2\2\u00a9\u0083"+
		"\3\2\2\2\u00a9\u0087\3\2\2\2\u00a9\u008b\3\2\2\2\u00a9\u0091\3\2\2\2\u00a9"+
		"\u0097\3\2\2\2\u00a9\u009d\3\2\2\2\u00a9\u00a3\3\2\2\2\u00aa\u00b0\3\2"+
		"\2\2\u00ab\u00ac\f\f\2\2\u00ac\u00ad\7\34\2\2\u00ad\u00af\5\16\b\2\u00ae"+
		"\u00ab\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0\u00b1\3\2"+
		"\2\2\u00b1\21\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b7\7\21\2\2\u00b4\u00b7"+
		"\5\30\r\2\u00b5\u00b7\5\24\13\2\u00b6\u00b3\3\2\2\2\u00b6\u00b4\3\2\2"+
		"\2\u00b6\u00b5\3\2\2\2\u00b7\23\3\2\2\2\u00b8\u00b9\7\26\2\2\u00b9\u00ba"+
		"\5\22\n\2\u00ba\u00bb\7\17\2\2\u00bb\u00bc\5\22\n\2\u00bc\u00bd\7\25\2"+
		"\2\u00bd\25\3\2\2\2\u00be\u00bf\7\30\2\2\u00bf\u00c0\7\26\2\2\u00c0\u00c1"+
		"\5\22\n\2\u00c1\u00c2\7\25\2\2\u00c2\27\3\2\2\2\u00c3\u00c4\7\31\2\2\u00c4"+
		"\u00c5\7\26\2\2\u00c5\u00c6\5\22\n\2\u00c6\u00c7\7\25\2\2\u00c7\31\3\2"+
		"\2\2\u00c8\u00c9\7\20\2\2\u00c9\u00ca\7\26\2\2\u00ca\u00cb\5\34\17\2\u00cb"+
		"\u00cc\7\25\2\2\u00cc\33\3\2\2\2\u00cd\u00d0\7\22\2\2\u00ce\u00d0\5\32"+
		"\16\2\u00cf\u00cd\3\2\2\2\u00cf\u00ce\3\2\2\2\u00d0\35\3\2\2\2\u00d1\u00d2"+
		"\7\26\2\2\u00d2\u00d3\7\21\2\2\u00d3\u00d4\5 \21\2\u00d4\u00d5\7\25\2"+
		"\2\u00d5\37\3\2\2\2\u00d6\u00d7\7\4\2\2\u00d7\u00dc\7\21\2\2\u00d8\u00d9"+
		"\7\4\2\2\u00d9\u00da\7\21\2\2\u00da\u00dc\5 \21\2\u00db\u00d6\3\2\2\2"+
		"\u00db\u00d8\3\2\2\2\u00dc!\3\2\2\2\u00dd\u00de\7\27\2\2\u00de\u00df\5"+
		"B\"\2\u00df\u00e0\7\5\2\2\u00e0\u00e1\5\16\b\2\u00e1#\3\2\2\2\u00e2\u00e3"+
		"\7\6\2\2\u00e3\u00e4\5\16\b\2\u00e4%\3\2\2\2\u00e5\u00e6\5(\25\2\u00e6"+
		"\u00e7\7\33\2\2\u00e7\u00e8\5(\25\2\u00e8\'\3\2\2\2\u00e9\u00ef\5\66\34"+
		"\2\u00ea\u00ef\58\35\2\u00eb\u00ef\5*\26\2\u00ec\u00ef\5H%\2\u00ed\u00ef"+
		"\5,\27\2\u00ee\u00e9\3\2\2\2\u00ee\u00ea\3\2\2\2\u00ee\u00eb\3\2\2\2\u00ee"+
		"\u00ec\3\2\2\2\u00ee\u00ed\3\2\2\2\u00ef)\3\2\2\2\u00f0\u00f1\b\26\1\2"+
		"\u00f1\u00f2\7\26\2\2\u00f2\u00f3\5*\26\2\u00f3\u00f4\7\25\2\2\u00f4\u00fa"+
		"\3\2\2\2\u00f5\u00fa\5> \2\u00f6\u00fa\5@!\2\u00f7\u00fa\5<\37\2\u00f8"+
		"\u00fa\5\64\33\2\u00f9\u00f0\3\2\2\2\u00f9\u00f5\3\2\2\2\u00f9\u00f6\3"+
		"\2\2\2\u00f9\u00f7\3\2\2\2\u00f9\u00f8\3\2\2\2\u00fa\u0103\3\2\2\2\u00fb"+
		"\u00fc\f\b\2\2\u00fc\u00fd\7\r\2\2\u00fd\u0102\5*\26\t\u00fe\u00ff\f\7"+
		"\2\2\u00ff\u0100\7\16\2\2\u0100\u0102\5*\26\b\u0101\u00fb\3\2\2\2\u0101"+
		"\u00fe\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2"+
		"\2\2\u0104+\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u010a\5.\30\2\u0107\u010a"+
		"\5\60\31\2\u0108\u010a\5\62\32\2\u0109\u0106\3\2\2\2\u0109\u0107\3\2\2"+
		"\2\u0109\u0108\3\2\2\2\u010a-\3\2\2\2\u010b\u010c\7\7\2\2\u010c\u010d"+
		"\5D#\2\u010d/\3\2\2\2\u010e\u010f\7\b\2\2\u010f\u0110\5D#\2\u0110\61\3"+
		"\2\2\2\u0111\u0112\7\t\2\2\u0112\u0113\5D#\2\u0113\63\3\2\2\2\u0114\u0115"+
		"\7\35\2\2\u0115\65\3\2\2\2\u0116\u011a\7\22\2\2\u0117\u0119\5:\36\2\u0118"+
		"\u0117\3\2\2\2\u0119\u011c\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2"+
		"\2\2\u011b\67\3\2\2\2\u011c\u011a\3\2\2\2\u011d\u0121\7\21\2\2\u011e\u0120"+
		"\5:\36\2\u011f\u011e\3\2\2\2\u0120\u0123\3\2\2\2\u0121\u011f\3\2\2\2\u0121"+
		"\u0122\3\2\2\2\u01229\3\2\2\2\u0123\u0121\3\2\2\2\u0124\u0127\5B\"\2\u0125"+
		"\u0127\5F$\2\u0126\u0124\3\2\2\2\u0126\u0125\3\2\2\2\u0127;\3\2\2\2\u0128"+
		"\u0129\t\2\2\2\u0129=\3\2\2\2\u012a\u012b\7\23\2\2\u012b\u012c\5:\36\2"+
		"\u012c?\3\2\2\2\u012d\u012e\7\24\2\2\u012e\u012f\5:\36\2\u012fA\3\2\2"+
		"\2\u0130\u0131\7\26\2\2\u0131\u0132\5H%\2\u0132\u0133\7\25\2\2\u0133C"+
		"\3\2\2\2\u0134\u0135\7\26\2\2\u0135\u0136\5*\26\2\u0136\u0137\7\25\2\2"+
		"\u0137E\3\2\2\2\u0138\u0139\7\26\2\2\u0139\u013a\5,\27\2\u013a\u013b\7"+
		"\25\2\2\u013bG\3\2\2\2\u013c\u013d\7\36\2\2\u013dI\3\2\2\2\23M\\dx\u00a9"+
		"\u00b0\u00b6\u00cf\u00db\u00ee\u00f9\u0101\u0103\u0109\u011a\u0121\u0126";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}