package edu.pse.beast.toolbox.antlr.booleanexp;

// Generated from FormalPropertyDescription.g4 by ANTLR 4.6
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
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, Vote=7, Elect=8, Votesum=9, 
		ClosedBracket=10, OpenBracket=11, Quantor=12, ComparisonSymbol=13, BinaryRelationSymbol=14, 
		Integer=15, Identifier=16, Whitespace=17, Newline=18, BlockComment=19, 
		LineComment=20;
	public static final int
		RULE_booleanExpList = 0, RULE_booleanExpListElement = 1, RULE_booleanExp = 2, 
		RULE_binaryRelationExp = 3, RULE_quantorExp = 4, RULE_notExp = 5, RULE_comparisonExp = 6, 
		RULE_typeExp = 7, RULE_numberExpression = 8, RULE_electExp = 9, RULE_voteExp = 10, 
		RULE_constantExp = 11, RULE_voteSumExp = 12, RULE_passSymbVar = 13, RULE_symbolicVarExp = 14;
	public static final String[] ruleNames = {
		"booleanExpList", "booleanExpListElement", "booleanExp", "binaryRelationExp", 
		"quantorExp", "notExp", "comparisonExp", "typeExp", "numberExpression", 
		"electExp", "voteExp", "constantExp", "voteSumExp", "passSymbVar", "symbolicVarExp"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "'!'", "'V'", "'C'", "'S'", null, null, null, "')'", 
		"'('"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "Vote", "Elect", "Votesum", 
		"ClosedBracket", "OpenBracket", "Quantor", "ComparisonSymbol", "BinaryRelationSymbol", 
		"Integer", "Identifier", "Whitespace", "Newline", "BlockComment", "LineComment"
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
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << Vote) | (1L << Elect) | (1L << Votesum) | (1L << OpenBracket) | (1L << Quantor) | (1L << Integer) | (1L << Identifier))) != 0)) {
				{
				{
				setState(30);
				booleanExpListElement();
				}
				}
				setState(35);
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
			setState(36);
			booleanExp();
			setState(37);
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
			setState(47);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(39);
				quantorExp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(40);
				binaryRelationExp(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(41);
				notExp();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(42);
				comparisonExp();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(43);
				match(OpenBracket);
				setState(44);
				booleanExp();
				setState(45);
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
			setState(86);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(50);
				quantorExp();
				setState(51);
				match(BinaryRelationSymbol);
				setState(52);
				booleanExp();
				}
				break;
			case 2:
				{
				setState(54);
				notExp();
				setState(55);
				match(BinaryRelationSymbol);
				setState(56);
				booleanExp();
				}
				break;
			case 3:
				{
				setState(58);
				comparisonExp();
				setState(59);
				match(BinaryRelationSymbol);
				setState(60);
				booleanExp();
				}
				break;
			case 4:
				{
				setState(62);
				match(OpenBracket);
				setState(63);
				binaryRelationExp(0);
				setState(64);
				match(ClosedBracket);
				setState(65);
				match(BinaryRelationSymbol);
				setState(66);
				booleanExp();
				}
				break;
			case 5:
				{
				setState(68);
				match(OpenBracket);
				setState(69);
				quantorExp();
				setState(70);
				match(ClosedBracket);
				setState(71);
				match(BinaryRelationSymbol);
				setState(72);
				booleanExp();
				}
				break;
			case 6:
				{
				setState(74);
				match(OpenBracket);
				setState(75);
				notExp();
				setState(76);
				match(ClosedBracket);
				setState(77);
				match(BinaryRelationSymbol);
				setState(78);
				booleanExp();
				}
				break;
			case 7:
				{
				setState(80);
				match(OpenBracket);
				setState(81);
				comparisonExp();
				setState(82);
				match(ClosedBracket);
				setState(83);
				match(BinaryRelationSymbol);
				setState(84);
				booleanExp();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(93);
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
					setState(88);
					if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
					setState(89);
					match(BinaryRelationSymbol);
					setState(90);
					booleanExp();
					}
					} 
				}
				setState(95);
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
			setState(96);
			match(Quantor);
			setState(97);
			passSymbVar();
			setState(98);
			match(T__1);
			setState(99);
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
			setState(101);
			match(T__2);
			setState(102);
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
			setState(104);
			typeExp();
			setState(105);
			match(ComparisonSymbol);
			setState(106);
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
		public ConstantExpContext constantExp() {
			return getRuleContext(ConstantExpContext.class,0);
		}
		public VoteSumExpContext voteSumExp() {
			return getRuleContext(VoteSumExpContext.class,0);
		}
		public SymbolicVarExpContext symbolicVarExp() {
			return getRuleContext(SymbolicVarExpContext.class,0);
		}
		public NumberExpressionContext numberExpression() {
			return getRuleContext(NumberExpressionContext.class,0);
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
			setState(114);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1);
				{
				setState(108);
				electExp();
				}
				break;
			case Vote:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				voteExp();
				}
				break;
			case T__3:
			case T__4:
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(110);
				constantExp();
				}
				break;
			case Votesum:
				enterOuterAlt(_localctx, 4);
				{
				setState(111);
				voteSumExp();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 5);
				{
				setState(112);
				symbolicVarExp();
				}
				break;
			case Integer:
				enterOuterAlt(_localctx, 6);
				{
				setState(113);
				numberExpression();
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
		public TerminalNode Integer() { return getToken(FormalPropertyDescriptionParser.Integer, 0); }
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
		NumberExpressionContext _localctx = new NumberExpressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_numberExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
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
		enterRule(_localctx, 18, RULE_electExp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(Elect);
			setState(122);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(119);
					passSymbVar();
					}
					} 
				}
				setState(124);
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
		enterRule(_localctx, 20, RULE_voteExp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(Vote);
			setState(129);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(126);
					passSymbVar();
					}
					} 
				}
				setState(131);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
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
		enterRule(_localctx, 22, RULE_constantExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__4) | (1L << T__5))) != 0)) ) {
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
		enterRule(_localctx, 24, RULE_voteSumExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(Votesum);
			setState(135);
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
		enterRule(_localctx, 26, RULE_passSymbVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(OpenBracket);
			setState(138);
			symbolicVarExp();
			setState(139);
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
		enterRule(_localctx, 28, RULE_symbolicVarExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\26\u0092\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\7\2\"\n\2\f\2"+
		"\16\2%\13\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\62\n\4\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\5\5Y\n\5\3\5\3\5\3\5\7\5^\n\5\f\5\16\5a\13\5\3\6\3\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\5\tu\n\t\3\n\3\n"+
		"\3\13\3\13\7\13{\n\13\f\13\16\13~\13\13\3\f\3\f\7\f\u0082\n\f\f\f\16\f"+
		"\u0085\13\f\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20"+
		"\2\3\b\21\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36\2\3\3\2\6\b\u0095\2#"+
		"\3\2\2\2\4&\3\2\2\2\6\61\3\2\2\2\bX\3\2\2\2\nb\3\2\2\2\fg\3\2\2\2\16j"+
		"\3\2\2\2\20t\3\2\2\2\22v\3\2\2\2\24x\3\2\2\2\26\177\3\2\2\2\30\u0086\3"+
		"\2\2\2\32\u0088\3\2\2\2\34\u008b\3\2\2\2\36\u008f\3\2\2\2 \"\5\4\3\2!"+
		" \3\2\2\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2$\3\3\2\2\2%#\3\2\2\2&\'\5\6\4"+
		"\2\'(\7\3\2\2(\5\3\2\2\2)\62\5\n\6\2*\62\5\b\5\2+\62\5\f\7\2,\62\5\16"+
		"\b\2-.\7\r\2\2./\5\6\4\2/\60\7\f\2\2\60\62\3\2\2\2\61)\3\2\2\2\61*\3\2"+
		"\2\2\61+\3\2\2\2\61,\3\2\2\2\61-\3\2\2\2\62\7\3\2\2\2\63\64\b\5\1\2\64"+
		"\65\5\n\6\2\65\66\7\20\2\2\66\67\5\6\4\2\67Y\3\2\2\289\5\f\7\29:\7\20"+
		"\2\2:;\5\6\4\2;Y\3\2\2\2<=\5\16\b\2=>\7\20\2\2>?\5\6\4\2?Y\3\2\2\2@A\7"+
		"\r\2\2AB\5\b\5\2BC\7\f\2\2CD\7\20\2\2DE\5\6\4\2EY\3\2\2\2FG\7\r\2\2GH"+
		"\5\n\6\2HI\7\f\2\2IJ\7\20\2\2JK\5\6\4\2KY\3\2\2\2LM\7\r\2\2MN\5\f\7\2"+
		"NO\7\f\2\2OP\7\20\2\2PQ\5\6\4\2QY\3\2\2\2RS\7\r\2\2ST\5\16\b\2TU\7\f\2"+
		"\2UV\7\20\2\2VW\5\6\4\2WY\3\2\2\2X\63\3\2\2\2X8\3\2\2\2X<\3\2\2\2X@\3"+
		"\2\2\2XF\3\2\2\2XL\3\2\2\2XR\3\2\2\2Y_\3\2\2\2Z[\f\n\2\2[\\\7\20\2\2\\"+
		"^\5\6\4\2]Z\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`\3\2\2\2`\t\3\2\2\2a_\3\2\2\2"+
		"bc\7\16\2\2cd\5\34\17\2de\7\4\2\2ef\5\6\4\2f\13\3\2\2\2gh\7\5\2\2hi\5"+
		"\6\4\2i\r\3\2\2\2jk\5\20\t\2kl\7\17\2\2lm\5\20\t\2m\17\3\2\2\2nu\5\24"+
		"\13\2ou\5\26\f\2pu\5\30\r\2qu\5\32\16\2ru\5\36\20\2su\5\22\n\2tn\3\2\2"+
		"\2to\3\2\2\2tp\3\2\2\2tq\3\2\2\2tr\3\2\2\2ts\3\2\2\2u\21\3\2\2\2vw\7\21"+
		"\2\2w\23\3\2\2\2x|\7\n\2\2y{\5\34\17\2zy\3\2\2\2{~\3\2\2\2|z\3\2\2\2|"+
		"}\3\2\2\2}\25\3\2\2\2~|\3\2\2\2\177\u0083\7\t\2\2\u0080\u0082\5\34\17"+
		"\2\u0081\u0080\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084"+
		"\3\2\2\2\u0084\27\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0087\t\2\2\2\u0087"+
		"\31\3\2\2\2\u0088\u0089\7\13\2\2\u0089\u008a\5\34\17\2\u008a\33\3\2\2"+
		"\2\u008b\u008c\7\r\2\2\u008c\u008d\5\36\20\2\u008d\u008e\7\f\2\2\u008e"+
		"\35\3\2\2\2\u008f\u0090\7\22\2\2\u0090\37\3\2\2\2\t#\61X_t|\u0083";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}