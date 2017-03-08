// Generated from FormalPropertyDescription.g4 by ANTLR 4.5.1
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
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		Mult=10, Add=11, Vote=12, Elect=13, Votesum=14, ClosedBracket=15, OpenBracket=16, 
		Quantor=17, ComparisonSymbol=18, BinaryRelationSymbol=19, Integer=20, 
		Identifier=21, Whitespace=22, Newline=23, BlockComment=24, LineComment=25;
	public static final int
		RULE_booleanExpList = 0, RULE_booleanExpListElement = 1, RULE_booleanExp = 2, 
		RULE_binaryRelationExp = 3, RULE_quantorExp = 4, RULE_notExp = 5, RULE_comparisonExp = 6, 
		RULE_typeExp = 7, RULE_numberExpression = 8, RULE_typeByPosExp = 9, RULE_voterByPosExp = 10, 
		RULE_candByPosExp = 11, RULE_seatByPosExp = 12, RULE_integer = 13, RULE_electExp = 14, 
		RULE_voteExp = 15, RULE_constantExp = 16, RULE_voteSumExp = 17, RULE_passSymbVar = 18, 
		RULE_passPosition = 19, RULE_passByPos = 20, RULE_symbolicVarExp = 21;
	public static final String[] ruleNames = {
		"booleanExpList", "booleanExpListElement", "booleanExp", "binaryRelationExp", 
		"quantorExp", "notExp", "comparisonExp", "typeExp", "numberExpression", 
		"typeByPosExp", "voterByPosExp", "candByPosExp", "seatByPosExp", "integer", 
		"electExp", "voteExp", "constantExp", "voteSumExp", "passSymbVar", "passPosition", 
		"passByPos", "symbolicVarExp"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "'!'", "'VOTER_AT_POS'", "'CAND_AT_POS'", "'SEAT_AT_POS'", 
		"'V'", "'C'", "'S'", null, null, null, null, null, "')'", "'('"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "Mult", "Add", 
		"Vote", "Elect", "Votesum", "ClosedBracket", "OpenBracket", "Quantor", 
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
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << Vote) | (1L << Elect) | (1L << Votesum) | (1L << OpenBracket) | (1L << Quantor) | (1L << Integer) | (1L << Identifier))) != 0)) {
				{
				{
				setState(44);
				booleanExpListElement();
				}
				}
				setState(49);
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
			setState(50);
			booleanExp();
			setState(51);
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
			setState(61);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(53);
				quantorExp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(54);
				binaryRelationExp(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(55);
				notExp();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(56);
				comparisonExp();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(57);
				match(OpenBracket);
				setState(58);
				booleanExp();
				setState(59);
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
			setState(100);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(64);
				quantorExp();
				setState(65);
				match(BinaryRelationSymbol);
				setState(66);
				booleanExp();
				}
				break;
			case 2:
				{
				setState(68);
				notExp();
				setState(69);
				match(BinaryRelationSymbol);
				setState(70);
				booleanExp();
				}
				break;
			case 3:
				{
				setState(72);
				comparisonExp();
				setState(73);
				match(BinaryRelationSymbol);
				setState(74);
				booleanExp();
				}
				break;
			case 4:
				{
				setState(76);
				match(OpenBracket);
				setState(77);
				binaryRelationExp(0);
				setState(78);
				match(ClosedBracket);
				setState(79);
				match(BinaryRelationSymbol);
				setState(80);
				booleanExp();
				}
				break;
			case 5:
				{
				setState(82);
				match(OpenBracket);
				setState(83);
				quantorExp();
				setState(84);
				match(ClosedBracket);
				setState(85);
				match(BinaryRelationSymbol);
				setState(86);
				booleanExp();
				}
				break;
			case 6:
				{
				setState(88);
				match(OpenBracket);
				setState(89);
				notExp();
				setState(90);
				match(ClosedBracket);
				setState(91);
				match(BinaryRelationSymbol);
				setState(92);
				booleanExp();
				}
				break;
			case 7:
				{
				setState(94);
				match(OpenBracket);
				setState(95);
				comparisonExp();
				setState(96);
				match(ClosedBracket);
				setState(97);
				match(BinaryRelationSymbol);
				setState(98);
				booleanExp();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(107);
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
					setState(102);
					if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
					setState(103);
					match(BinaryRelationSymbol);
					setState(104);
					booleanExp();
					}
					} 
				}
				setState(109);
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
			setState(110);
			match(Quantor);
			setState(111);
			passSymbVar();
			setState(112);
			match(T__1);
			setState(113);
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
			setState(115);
			match(T__2);
			setState(116);
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
			setState(118);
			typeExp();
			setState(119);
			match(ComparisonSymbol);
			setState(120);
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
			setState(127);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1);
				{
				setState(122);
				electExp();
				}
				break;
			case Vote:
				enterOuterAlt(_localctx, 2);
				{
				setState(123);
				voteExp();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
			case Votesum:
			case OpenBracket:
			case Integer:
				enterOuterAlt(_localctx, 3);
				{
				setState(124);
				numberExpression(0);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(125);
				symbolicVarExp();
				}
				break;
			case T__3:
			case T__4:
			case T__5:
				enterOuterAlt(_localctx, 5);
				{
				setState(126);
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
			setState(137);
			switch (_input.LA(1)) {
			case OpenBracket:
				{
				setState(130);
				match(OpenBracket);
				setState(131);
				numberExpression(0);
				setState(132);
				match(ClosedBracket);
				}
				break;
			case Votesum:
				{
				setState(134);
				voteSumExp();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
				{
				setState(135);
				constantExp();
				}
				break;
			case Integer:
				{
				setState(136);
				integer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(147);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(145);
					switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
					case 1:
						{
						_localctx = new NumberExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
						setState(139);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(140);
						match(Mult);
						setState(141);
						numberExpression(6);
						}
						break;
					case 2:
						{
						_localctx = new NumberExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
						setState(142);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(143);
						match(Add);
						setState(144);
						numberExpression(5);
						}
						break;
					}
					} 
				}
				setState(149);
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
			setState(153);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(150);
				voterByPosExp();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(151);
				candByPosExp();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(152);
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
			setState(155);
			match(T__3);
			setState(156);
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
			setState(158);
			match(T__4);
			setState(159);
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
			setState(161);
			match(T__5);
			setState(162);
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
			setState(164);
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
		public List<PassSymbVarContext> passSymbVar() {
			return getRuleContexts(PassSymbVarContext.class);
		}
		public PassSymbVarContext passSymbVar(int i) {
			return getRuleContext(PassSymbVarContext.class,i);
		}
		public List<PassByPosContext> passByPos() {
			return getRuleContexts(PassByPosContext.class);
		}
		public PassByPosContext passByPos(int i) {
			return getRuleContext(PassByPosContext.class,i);
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
			setState(166);
			match(Elect);
			setState(179);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(170);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(167);
						passSymbVar();
						}
						} 
					}
					setState(172);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				}
				}
				break;
			case 2:
				{
				setState(176);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(173);
						passByPos();
						}
						} 
					}
					setState(178);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				}
				}
				break;
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
		public List<PassSymbVarContext> passSymbVar() {
			return getRuleContexts(PassSymbVarContext.class);
		}
		public PassSymbVarContext passSymbVar(int i) {
			return getRuleContext(PassSymbVarContext.class,i);
		}
		public List<PassByPosContext> passByPos() {
			return getRuleContexts(PassByPosContext.class);
		}
		public PassByPosContext passByPos(int i) {
			return getRuleContext(PassByPosContext.class,i);
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
			setState(181);
			match(Vote);
			setState(194);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(185);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(182);
						passSymbVar();
						}
						} 
					}
					setState(187);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				}
				break;
			case 2:
				{
				setState(191);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(188);
						passByPos();
						}
						} 
					}
					setState(193);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				}
				}
				break;
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
		enterRule(_localctx, 32, RULE_constantExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
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
		public PassSymbVarContext passSymbVar() {
			return getRuleContext(PassSymbVarContext.class,0);
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
		enterRule(_localctx, 34, RULE_voteSumExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(Votesum);
			setState(199);
			passSymbVar();
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
		enterRule(_localctx, 36, RULE_passSymbVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(OpenBracket);
			setState(202);
			symbolicVarExp();
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
		enterRule(_localctx, 38, RULE_passPosition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(OpenBracket);
			setState(206);
			numberExpression(0);
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
		enterRule(_localctx, 40, RULE_passByPos);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(OpenBracket);
			setState(210);
			typeByPosExp();
			setState(211);
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
		enterRule(_localctx, 42, RULE_symbolicVarExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
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
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\33\u00da\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\7\2\60\n\2\f\2"+
		"\16\2\63\13\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4@\n\4\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\5\5g\n\5\3\5\3\5\3\5\7\5l\n\5\f\5\16\5o\13\5\3\6\3\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\t\u0082\n\t\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u008c\n\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u0094"+
		"\n\n\f\n\16\n\u0097\13\n\3\13\3\13\3\13\5\13\u009c\n\13\3\f\3\f\3\f\3"+
		"\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\7\20\u00ab\n\20\f\20\16"+
		"\20\u00ae\13\20\3\20\7\20\u00b1\n\20\f\20\16\20\u00b4\13\20\5\20\u00b6"+
		"\n\20\3\21\3\21\7\21\u00ba\n\21\f\21\16\21\u00bd\13\21\3\21\7\21\u00c0"+
		"\n\21\f\21\16\21\u00c3\13\21\5\21\u00c5\n\21\3\22\3\22\3\23\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27"+
		"\3\27\2\4\b\22\30\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,\2\3\3"+
		"\2\t\13\u00e0\2\61\3\2\2\2\4\64\3\2\2\2\6?\3\2\2\2\bf\3\2\2\2\np\3\2\2"+
		"\2\fu\3\2\2\2\16x\3\2\2\2\20\u0081\3\2\2\2\22\u008b\3\2\2\2\24\u009b\3"+
		"\2\2\2\26\u009d\3\2\2\2\30\u00a0\3\2\2\2\32\u00a3\3\2\2\2\34\u00a6\3\2"+
		"\2\2\36\u00a8\3\2\2\2 \u00b7\3\2\2\2\"\u00c6\3\2\2\2$\u00c8\3\2\2\2&\u00cb"+
		"\3\2\2\2(\u00cf\3\2\2\2*\u00d3\3\2\2\2,\u00d7\3\2\2\2.\60\5\4\3\2/.\3"+
		"\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\62\3\3\2\2\2\63\61\3\2"+
		"\2\2\64\65\5\6\4\2\65\66\7\3\2\2\66\5\3\2\2\2\67@\5\n\6\28@\5\b\5\29@"+
		"\5\f\7\2:@\5\16\b\2;<\7\22\2\2<=\5\6\4\2=>\7\21\2\2>@\3\2\2\2?\67\3\2"+
		"\2\2?8\3\2\2\2?9\3\2\2\2?:\3\2\2\2?;\3\2\2\2@\7\3\2\2\2AB\b\5\1\2BC\5"+
		"\n\6\2CD\7\25\2\2DE\5\6\4\2Eg\3\2\2\2FG\5\f\7\2GH\7\25\2\2HI\5\6\4\2I"+
		"g\3\2\2\2JK\5\16\b\2KL\7\25\2\2LM\5\6\4\2Mg\3\2\2\2NO\7\22\2\2OP\5\b\5"+
		"\2PQ\7\21\2\2QR\7\25\2\2RS\5\6\4\2Sg\3\2\2\2TU\7\22\2\2UV\5\n\6\2VW\7"+
		"\21\2\2WX\7\25\2\2XY\5\6\4\2Yg\3\2\2\2Z[\7\22\2\2[\\\5\f\7\2\\]\7\21\2"+
		"\2]^\7\25\2\2^_\5\6\4\2_g\3\2\2\2`a\7\22\2\2ab\5\16\b\2bc\7\21\2\2cd\7"+
		"\25\2\2de\5\6\4\2eg\3\2\2\2fA\3\2\2\2fF\3\2\2\2fJ\3\2\2\2fN\3\2\2\2fT"+
		"\3\2\2\2fZ\3\2\2\2f`\3\2\2\2gm\3\2\2\2hi\f\n\2\2ij\7\25\2\2jl\5\6\4\2"+
		"kh\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2n\t\3\2\2\2om\3\2\2\2pq\7\23\2"+
		"\2qr\5&\24\2rs\7\4\2\2st\5\6\4\2t\13\3\2\2\2uv\7\5\2\2vw\5\6\4\2w\r\3"+
		"\2\2\2xy\5\20\t\2yz\7\24\2\2z{\5\20\t\2{\17\3\2\2\2|\u0082\5\36\20\2}"+
		"\u0082\5 \21\2~\u0082\5\22\n\2\177\u0082\5,\27\2\u0080\u0082\5\24\13\2"+
		"\u0081|\3\2\2\2\u0081}\3\2\2\2\u0081~\3\2\2\2\u0081\177\3\2\2\2\u0081"+
		"\u0080\3\2\2\2\u0082\21\3\2\2\2\u0083\u0084\b\n\1\2\u0084\u0085\7\22\2"+
		"\2\u0085\u0086\5\22\n\2\u0086\u0087\7\21\2\2\u0087\u008c\3\2\2\2\u0088"+
		"\u008c\5$\23\2\u0089\u008c\5\"\22\2\u008a\u008c\5\34\17\2\u008b\u0083"+
		"\3\2\2\2\u008b\u0088\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2\2\2\u008c"+
		"\u0095\3\2\2\2\u008d\u008e\f\7\2\2\u008e\u008f\7\f\2\2\u008f\u0094\5\22"+
		"\n\b\u0090\u0091\f\6\2\2\u0091\u0092\7\r\2\2\u0092\u0094\5\22\n\7\u0093"+
		"\u008d\3\2\2\2\u0093\u0090\3\2\2\2\u0094\u0097\3\2\2\2\u0095\u0093\3\2"+
		"\2\2\u0095\u0096\3\2\2\2\u0096\23\3\2\2\2\u0097\u0095\3\2\2\2\u0098\u009c"+
		"\5\26\f\2\u0099\u009c\5\30\r\2\u009a\u009c\5\32\16\2\u009b\u0098\3\2\2"+
		"\2\u009b\u0099\3\2\2\2\u009b\u009a\3\2\2\2\u009c\25\3\2\2\2\u009d\u009e"+
		"\7\6\2\2\u009e\u009f\5(\25\2\u009f\27\3\2\2\2\u00a0\u00a1\7\7\2\2\u00a1"+
		"\u00a2\5(\25\2\u00a2\31\3\2\2\2\u00a3\u00a4\7\b\2\2\u00a4\u00a5\5(\25"+
		"\2\u00a5\33\3\2\2\2\u00a6\u00a7\7\26\2\2\u00a7\35\3\2\2\2\u00a8\u00b5"+
		"\7\17\2\2\u00a9\u00ab\5&\24\2\u00aa\u00a9\3\2\2\2\u00ab\u00ae\3\2\2\2"+
		"\u00ac\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00b6\3\2\2\2\u00ae\u00ac"+
		"\3\2\2\2\u00af\u00b1\5*\26\2\u00b0\u00af\3\2\2\2\u00b1\u00b4\3\2\2\2\u00b2"+
		"\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b6\3\2\2\2\u00b4\u00b2\3\2"+
		"\2\2\u00b5\u00ac\3\2\2\2\u00b5\u00b2\3\2\2\2\u00b6\37\3\2\2\2\u00b7\u00c4"+
		"\7\16\2\2\u00b8\u00ba\5&\24\2\u00b9\u00b8\3\2\2\2\u00ba\u00bd\3\2\2\2"+
		"\u00bb\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00c5\3\2\2\2\u00bd\u00bb"+
		"\3\2\2\2\u00be\u00c0\5*\26\2\u00bf\u00be\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1"+
		"\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2"+
		"\2\2\u00c4\u00bb\3\2\2\2\u00c4\u00c1\3\2\2\2\u00c5!\3\2\2\2\u00c6\u00c7"+
		"\t\2\2\2\u00c7#\3\2\2\2\u00c8\u00c9\7\20\2\2\u00c9\u00ca\5&\24\2\u00ca"+
		"%\3\2\2\2\u00cb\u00cc\7\22\2\2\u00cc\u00cd\5,\27\2\u00cd\u00ce\7\21\2"+
		"\2\u00ce\'\3\2\2\2\u00cf\u00d0\7\22\2\2\u00d0\u00d1\5\22\n\2\u00d1\u00d2"+
		"\7\21\2\2\u00d2)\3\2\2\2\u00d3\u00d4\7\22\2\2\u00d4\u00d5\5\24\13\2\u00d5"+
		"\u00d6\7\21\2\2\u00d6+\3\2\2\2\u00d7\u00d8\7\27\2\2\u00d8-\3\2\2\2\21"+
		"\61?fm\u0081\u008b\u0093\u0095\u009b\u00ac\u00b2\u00b5\u00bb\u00c1\u00c4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}