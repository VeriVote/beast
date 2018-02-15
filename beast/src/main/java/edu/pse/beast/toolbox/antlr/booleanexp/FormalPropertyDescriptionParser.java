package edu.pse.beast.toolbox.antlr.booleanexp;// Generated from FormalPropertyDescription.g4 by ANTLR 4.5.2
import java.util.List;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings("unused")
public class FormalPropertyDescriptionParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9,
		Mult=10, Add=11, Vote=12, Elect=13, Votesum=14, VotesumUnique=15, ClosedBracket=16,
		OpenBracket=17, Quantor=18, ComparisonSymbol=19, BinaryRelationSymbol=20,
		Integer=21, Identifier=22, Whitespace=23, Newline=24, BlockComment=25,
		LineComment=26;
	public static final int
		RULE_booleanExpList = 0, RULE_booleanExpListElement = 1, RULE_booleanExp = 2,
		RULE_binaryRelationExp = 3, RULE_quantorExp = 4, RULE_notExp = 5, RULE_comparisonExp = 6,
		RULE_typeExp = 7, RULE_numberExpression = 8, RULE_typeByPosExp = 9, RULE_voterByPosExp = 10,
		RULE_candByPosExp = 11, RULE_seatByPosExp = 12, RULE_integer = 13, RULE_electExp = 14,
		RULE_voteExp = 15, RULE_passType = 16, RULE_constantExp = 17, RULE_voteSumExp = 18,
		RULE_voteSumUniqueExp = 19, RULE_passSymbVar = 20, RULE_passPosition = 21,
		RULE_passByPos = 22, RULE_symbolicVarExp = 23;
	public static final String[] ruleNames = {
		"booleanExpList", "booleanExpListElement", "booleanExp", "binaryRelationExp",
		"quantorExp", "notExp", "comparisonExp", "typeExp", "numberExpression",
		"typeByPosExp", "voterByPosExp", "candByPosExp", "seatByPosExp", "integer",
		"electExp", "voteExp", "passType", "constantExp", "voteSumExp", "voteSumUniqueExp",
		"passSymbVar", "passPosition", "passByPos", "symbolicVarExp"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "'!'", "'VOTER_AT_POS'", "'CAND_AT_POS'", "'SEAT_AT_POS'",
		"'V'", "'C'", "'S'", null, null, null, null, null, null, "')'", "'('"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "Mult", "Add",
		"Vote", "Elect", "Votesum", "VotesumUnique", "ClosedBracket", "OpenBracket",
		"Quantor", "ComparisonSymbol", "BinaryRelationSymbol", "Integer", "Identifier",
		"Whitespace", "Newline", "BlockComment", "LineComment"
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitBooleanExpList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpListContext booleanExpList() throws RecognitionException {
		BooleanExpListContext _localctx = new BooleanExpListContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_booleanExpList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << Vote) | (1L << Elect) | (1L << Votesum) | (1L << VotesumUnique) | (1L << OpenBracket) | (1L << Quantor) | (1L << Integer) | (1L << Identifier))) != 0)) {
				{
				{
				setState(48);
				booleanExpListElement();
				}
				}
				setState(53);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitBooleanExpListElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpListElementContext booleanExpListElement() throws RecognitionException {
		BooleanExpListElementContext _localctx = new BooleanExpListElementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_booleanExpListElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			booleanExp();
			setState(55);
			match(T__0);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitBooleanExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpContext booleanExp() throws RecognitionException {
		BooleanExpContext _localctx = new BooleanExpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_booleanExp);
		try {
			setState(65);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(57);
				quantorExp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(58);
				binaryRelationExp(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(59);
				notExp();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(60);
				comparisonExp();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(61);
				match(OpenBracket);
				setState(62);
				booleanExp();
				setState(63);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitBinaryRelationExp(this);
			else return visitor.visitChildren(this);
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
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_binaryRelationExp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(68);
				quantorExp();
				setState(69);
				match(BinaryRelationSymbol);
				setState(70);
				booleanExp();
				}
				break;
			case 2:
				{
				setState(72);
				notExp();
				setState(73);
				match(BinaryRelationSymbol);
				setState(74);
				booleanExp();
				}
				break;
			case 3:
				{
				setState(76);
				comparisonExp();
				setState(77);
				match(BinaryRelationSymbol);
				setState(78);
				booleanExp();
				}
				break;
			case 4:
				{
				setState(80);
				match(OpenBracket);
				setState(81);
				binaryRelationExp(0);
				setState(82);
				match(ClosedBracket);
				setState(83);
				match(BinaryRelationSymbol);
				setState(84);
				booleanExp();
				}
				break;
			case 5:
				{
				setState(86);
				match(OpenBracket);
				setState(87);
				quantorExp();
				setState(88);
				match(ClosedBracket);
				setState(89);
				match(BinaryRelationSymbol);
				setState(90);
				booleanExp();
				}
				break;
			case 6:
				{
				setState(92);
				match(OpenBracket);
				setState(93);
				notExp();
				setState(94);
				match(ClosedBracket);
				setState(95);
				match(BinaryRelationSymbol);
				setState(96);
				booleanExp();
				}
				break;
			case 7:
				{
				setState(98);
				match(OpenBracket);
				setState(99);
				comparisonExp();
				setState(100);
				match(ClosedBracket);
				setState(101);
				match(BinaryRelationSymbol);
				setState(102);
				booleanExp();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(111);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BinaryRelationExpContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_binaryRelationExp);
					setState(106);
					if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
					setState(107);
					match(BinaryRelationSymbol);
					setState(108);
					booleanExp();
					}
					} 
				}
				setState(113);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitQuantorExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantorExpContext quantorExp() throws RecognitionException {
		QuantorExpContext _localctx = new QuantorExpContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_quantorExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(Quantor);
			setState(115);
			passSymbVar();
			setState(116);
			match(T__1);
			setState(117);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitNotExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotExpContext notExp() throws RecognitionException {
		NotExpContext _localctx = new NotExpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_notExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			match(T__2);
			setState(120);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitComparisonExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonExpContext comparisonExp() throws RecognitionException {
		ComparisonExpContext _localctx = new ComparisonExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_comparisonExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			typeExp();
			setState(123);
			match(ComparisonSymbol);
			setState(124);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitTypeExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeExpContext typeExp() throws RecognitionException {
		TypeExpContext _localctx = new TypeExpContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_typeExp);
		try {
			setState(131);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1);
				{
				setState(126);
				electExp();
				}
				break;
			case Vote:
				enterOuterAlt(_localctx, 2);
				{
				setState(127);
				voteExp();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
			case Votesum:
			case VotesumUnique:
			case OpenBracket:
			case Integer:
				enterOuterAlt(_localctx, 3);
				{
				setState(128);
				numberExpression(0);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(129);
				symbolicVarExp();
				}
				break;
			case T__3:
			case T__4:
			case T__5:
				enterOuterAlt(_localctx, 5);
				{
				setState(130);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitNumberExpression(this);
			else return visitor.visitChildren(this);
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
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_numberExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			switch (_input.LA(1)) {
			case OpenBracket:
				{
				setState(134);
				match(OpenBracket);
				setState(135);
				numberExpression(0);
				setState(136);
				match(ClosedBracket);
				}
				break;
			case Votesum:
				{
				setState(138);
				voteSumExp();
				}
				break;
			case VotesumUnique:
				{
				setState(139);
				voteSumUniqueExp();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
				{
				setState(140);
				constantExp();
				}
				break;
			case Integer:
				{
				setState(141);
				integer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(152);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(150);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
					case 1:
						{
						_localctx = new NumberExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
						setState(144);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(145);
						match(Mult);
						setState(146);
						numberExpression(7);
						}
						break;
					case 2:
						{
						_localctx = new NumberExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
						setState(147);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(148);
						match(Add);
						setState(149);
						numberExpression(6);
						}
						break;
					}
					} 
				}
				setState(154);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitTypeByPosExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeByPosExpContext typeByPosExp() throws RecognitionException {
		TypeByPosExpContext _localctx = new TypeByPosExpContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_typeByPosExp);
		try {
			setState(158);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(155);
				voterByPosExp();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(156);
				candByPosExp();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(157);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitVoterByPosExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VoterByPosExpContext voterByPosExp() throws RecognitionException {
		VoterByPosExpContext _localctx = new VoterByPosExpContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_voterByPosExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(T__3);
			setState(161);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitCandByPosExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CandByPosExpContext candByPosExp() throws RecognitionException {
		CandByPosExpContext _localctx = new CandByPosExpContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_candByPosExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			match(T__4);
			setState(164);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitSeatByPosExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SeatByPosExpContext seatByPosExp() throws RecognitionException {
		SeatByPosExpContext _localctx = new SeatByPosExpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_seatByPosExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			match(T__5);
			setState(167);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitInteger(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_integer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitElectExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElectExpContext electExp() throws RecognitionException {
		ElectExpContext _localctx = new ElectExpContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_electExp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			match(Elect);
			setState(175);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(172);
					passType();
					}
					} 
				}
				setState(177);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitVoteExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VoteExpContext voteExp() throws RecognitionException {
		VoteExpContext _localctx = new VoteExpContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_voteExp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(Vote);
			setState(182);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(179);
					passType();
					}
					} 
				}
				setState(184);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitPassType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PassTypeContext passType() throws RecognitionException {
		PassTypeContext _localctx = new PassTypeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_passType);
		try {
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(185);
				passSymbVar();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(186);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitConstantExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantExpContext constantExp() throws RecognitionException {
		ConstantExpContext _localctx = new ConstantExpContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_constantExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << T__8))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitVoteSumExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VoteSumExpContext voteSumExp() throws RecognitionException {
		VoteSumExpContext _localctx = new VoteSumExpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_voteSumExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			match(Votesum);
			setState(192);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitVoteSumUniqueExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VoteSumUniqueExpContext voteSumUniqueExp() throws RecognitionException {
		VoteSumUniqueExpContext _localctx = new VoteSumUniqueExpContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_voteSumUniqueExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(VotesumUnique);
			setState(195);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitPassSymbVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PassSymbVarContext passSymbVar() throws RecognitionException {
		PassSymbVarContext _localctx = new PassSymbVarContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_passSymbVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(OpenBracket);
			setState(198);
			symbolicVarExp();
			setState(199);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitPassPosition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PassPositionContext passPosition() throws RecognitionException {
		PassPositionContext _localctx = new PassPositionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_passPosition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(OpenBracket);
			setState(202);
			numberExpression(0);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitPassByPos(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PassByPosContext passByPos() throws RecognitionException {
		PassByPosContext _localctx = new PassByPosContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_passByPos);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(OpenBracket);
			setState(206);
			typeByPosExp();
			setState(207);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FormalPropertyDescriptionVisitor ) return ((FormalPropertyDescriptionVisitor<? extends T>)visitor).visitSymbolicVarExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SymbolicVarExpContext symbolicVarExp() throws RecognitionException {
		SymbolicVarExpContext _localctx = new SymbolicVarExpContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_symbolicVarExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
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
		case 3:
			return binaryRelationExp_sempred((BinaryRelationExpContext)_localctx, predIndex);
		case 8:
			return numberExpression_sempred((NumberExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean binaryRelationExp_sempred(BinaryRelationExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\34\u00d6\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\7\2\64\n\2\f\2\16\2\67\13\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\5\4D\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\5\5k\n\5\3\5\3\5\3\5\7\5p\n\5\f\5\16\5s\13\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5"+
		"\t\u0086\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u0091\n\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\7\n\u0099\n\n\f\n\16\n\u009c\13\n\3\13\3\13\3\13\5\13"+
		"\u00a1\n\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20"+
		"\7\20\u00b0\n\20\f\20\16\20\u00b3\13\20\3\21\3\21\7\21\u00b7\n\21\f\21"+
		"\16\21\u00ba\13\21\3\22\3\22\5\22\u00be\n\22\3\23\3\23\3\24\3\24\3\24"+
		"\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30"+
		"\3\30\3\31\3\31\3\31\2\4\b\22\32\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,.\60\2\3\3\2\t\13\u00d8\2\65\3\2\2\2\48\3\2\2\2\6C\3\2\2\2\bj"+
		"\3\2\2\2\nt\3\2\2\2\fy\3\2\2\2\16|\3\2\2\2\20\u0085\3\2\2\2\22\u0090\3"+
		"\2\2\2\24\u00a0\3\2\2\2\26\u00a2\3\2\2\2\30\u00a5\3\2\2\2\32\u00a8\3\2"+
		"\2\2\34\u00ab\3\2\2\2\36\u00ad\3\2\2\2 \u00b4\3\2\2\2\"\u00bd\3\2\2\2"+
		"$\u00bf\3\2\2\2&\u00c1\3\2\2\2(\u00c4\3\2\2\2*\u00c7\3\2\2\2,\u00cb\3"+
		"\2\2\2.\u00cf\3\2\2\2\60\u00d3\3\2\2\2\62\64\5\4\3\2\63\62\3\2\2\2\64"+
		"\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\66\3\3\2\2\2\67\65\3\2\2\289\5"+
		"\6\4\29:\7\3\2\2:\5\3\2\2\2;D\5\n\6\2<D\5\b\5\2=D\5\f\7\2>D\5\16\b\2?"+
		"@\7\23\2\2@A\5\6\4\2AB\7\22\2\2BD\3\2\2\2C;\3\2\2\2C<\3\2\2\2C=\3\2\2"+
		"\2C>\3\2\2\2C?\3\2\2\2D\7\3\2\2\2EF\b\5\1\2FG\5\n\6\2GH\7\26\2\2HI\5\6"+
		"\4\2Ik\3\2\2\2JK\5\f\7\2KL\7\26\2\2LM\5\6\4\2Mk\3\2\2\2NO\5\16\b\2OP\7"+
		"\26\2\2PQ\5\6\4\2Qk\3\2\2\2RS\7\23\2\2ST\5\b\5\2TU\7\22\2\2UV\7\26\2\2"+
		"VW\5\6\4\2Wk\3\2\2\2XY\7\23\2\2YZ\5\n\6\2Z[\7\22\2\2[\\\7\26\2\2\\]\5"+
		"\6\4\2]k\3\2\2\2^_\7\23\2\2_`\5\f\7\2`a\7\22\2\2ab\7\26\2\2bc\5\6\4\2"+
		"ck\3\2\2\2de\7\23\2\2ef\5\16\b\2fg\7\22\2\2gh\7\26\2\2hi\5\6\4\2ik\3\2"+
		"\2\2jE\3\2\2\2jJ\3\2\2\2jN\3\2\2\2jR\3\2\2\2jX\3\2\2\2j^\3\2\2\2jd\3\2"+
		"\2\2kq\3\2\2\2lm\f\n\2\2mn\7\26\2\2np\5\6\4\2ol\3\2\2\2ps\3\2\2\2qo\3"+
		"\2\2\2qr\3\2\2\2r\t\3\2\2\2sq\3\2\2\2tu\7\24\2\2uv\5*\26\2vw\7\4\2\2w"+
		"x\5\6\4\2x\13\3\2\2\2yz\7\5\2\2z{\5\6\4\2{\r\3\2\2\2|}\5\20\t\2}~\7\25"+
		"\2\2~\177\5\20\t\2\177\17\3\2\2\2\u0080\u0086\5\36\20\2\u0081\u0086\5"+
		" \21\2\u0082\u0086\5\22\n\2\u0083\u0086\5\60\31\2\u0084\u0086\5\24\13"+
		"\2\u0085\u0080\3\2\2\2\u0085\u0081\3\2\2\2\u0085\u0082\3\2\2\2\u0085\u0083"+
		"\3\2\2\2\u0085\u0084\3\2\2\2\u0086\21\3\2\2\2\u0087\u0088\b\n\1\2\u0088"+
		"\u0089\7\23\2\2\u0089\u008a\5\22\n\2\u008a\u008b\7\22\2\2\u008b\u0091"+
		"\3\2\2\2\u008c\u0091\5&\24\2\u008d\u0091\5(\25\2\u008e\u0091\5$\23\2\u008f"+
		"\u0091\5\34\17\2\u0090\u0087\3\2\2\2\u0090\u008c\3\2\2\2\u0090\u008d\3"+
		"\2\2\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\u009a\3\2\2\2\u0092"+
		"\u0093\f\b\2\2\u0093\u0094\7\f\2\2\u0094\u0099\5\22\n\t\u0095\u0096\f"+
		"\7\2\2\u0096\u0097\7\r\2\2\u0097\u0099\5\22\n\b\u0098\u0092\3\2\2\2\u0098"+
		"\u0095\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2"+
		"\2\2\u009b\23\3\2\2\2\u009c\u009a\3\2\2\2\u009d\u00a1\5\26\f\2\u009e\u00a1"+
		"\5\30\r\2\u009f\u00a1\5\32\16\2\u00a0\u009d\3\2\2\2\u00a0\u009e\3\2\2"+
		"\2\u00a0\u009f\3\2\2\2\u00a1\25\3\2\2\2\u00a2\u00a3\7\6\2\2\u00a3\u00a4"+
		"\5,\27\2\u00a4\27\3\2\2\2\u00a5\u00a6\7\7\2\2\u00a6\u00a7\5,\27\2\u00a7"+
		"\31\3\2\2\2\u00a8\u00a9\7\b\2\2\u00a9\u00aa\5,\27\2\u00aa\33\3\2\2\2\u00ab"+
		"\u00ac\7\27\2\2\u00ac\35\3\2\2\2\u00ad\u00b1\7\17\2\2\u00ae\u00b0\5\""+
		"\22\2\u00af\u00ae\3\2\2\2\u00b0\u00b3\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1"+
		"\u00b2\3\2\2\2\u00b2\37\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b4\u00b8\7\16\2"+
		"\2\u00b5\u00b7\5\"\22\2\u00b6\u00b5\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8"+
		"\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9!\3\2\2\2\u00ba\u00b8\3\2\2\2"+
		"\u00bb\u00be\5*\26\2\u00bc\u00be\5.\30\2\u00bd\u00bb\3\2\2\2\u00bd\u00bc"+
		"\3\2\2\2\u00be#\3\2\2\2\u00bf\u00c0\t\2\2\2\u00c0%\3\2\2\2\u00c1\u00c2"+
		"\7\20\2\2\u00c2\u00c3\5\"\22\2\u00c3\'\3\2\2\2\u00c4\u00c5\7\21\2\2\u00c5"+
		"\u00c6\5\"\22\2\u00c6)\3\2\2\2\u00c7\u00c8\7\23\2\2\u00c8\u00c9\5\60\31"+
		"\2\u00c9\u00ca\7\22\2\2\u00ca+\3\2\2\2\u00cb\u00cc\7\23\2\2\u00cc\u00cd"+
		"\5\22\n\2\u00cd\u00ce\7\22\2\2\u00ce-\3\2\2\2\u00cf\u00d0\7\23\2\2\u00d0"+
		"\u00d1\5\24\13\2\u00d1\u00d2\7\22\2\2\u00d2/\3\2\2\2\u00d3\u00d4\7\30"+
		"\2\2\u00d4\61\3\2\2\2\16\65Cjq\u0085\u0090\u0098\u009a\u00a0\u00b1\u00b8"+
		"\u00bd";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}