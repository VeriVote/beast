package edu.pse.beast.toolbox.antlr.booleanexp;

// Generated from FormalPropertyDescription.g4 by ANTLR 4.5.3
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
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, Vote=8, Elect=9, 
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
		null, "';'", "':'", "'!'", "'V'", "'C'", "'S'", "'VOTE_SUM_FOR_CANDIDATE('", 
		null, null, "')'", "'('"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, "Vote", "Elect", "ClosedBracket", 
		"OpenBracket", "Quantor", "ComparisonSymbol", "BinaryRelationSymbol", 
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << Vote) | (1L << Elect) | (1L << OpenBracket) | (1L << Quantor) | (1L << Integer) | (1L << Identifier))) != 0)) {
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
		public BinaryRelationExpContext binaryRelationExp() {
			return getRuleContext(BinaryRelationExpContext.class,0);
		}
		public QuantorExpContext quantorExp() {
			return getRuleContext(QuantorExpContext.class,0);
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
				binaryRelationExp(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(40);
				quantorExp();
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
			setState(62);
			switch (_input.LA(1)) {
			case Quantor:
				{
				setState(50);
				quantorExp();
				setState(51);
				match(BinaryRelationSymbol);
				setState(52);
				booleanExp();
				}
				break;
			case T__2:
				{
				setState(54);
				notExp();
				setState(55);
				match(BinaryRelationSymbol);
				setState(56);
				booleanExp();
				}
				break;
			case T__3:
			case T__4:
			case T__5:
			case T__6:
			case Vote:
			case Elect:
			case Integer:
			case Identifier:
				{
				setState(58);
				comparisonExp();
				setState(59);
				match(BinaryRelationSymbol);
				setState(60);
				booleanExp();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(69);
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
					setState(64);
					if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
					setState(65);
					match(BinaryRelationSymbol);
					setState(66);
					booleanExp();
					}
					} 
				}
				setState(71);
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
			setState(72);
			match(Quantor);
			setState(73);
			passSymbVar();
			setState(74);
			match(T__1);
			setState(75);
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
			setState(77);
			match(T__2);
			setState(78);
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
			setState(80);
			typeExp();
			setState(81);
			match(ComparisonSymbol);
			setState(82);
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
			setState(90);
			switch (_input.LA(1)) {
			case Elect:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				electExp();
				}
				break;
			case Vote:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				voteExp();
				}
				break;
			case T__3:
			case T__4:
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(86);
				constantExp();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 4);
				{
				setState(87);
				voteSumExp();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 5);
				{
				setState(88);
				symbolicVarExp();
				}
				break;
			case Integer:
				enterOuterAlt(_localctx, 6);
				{
				setState(89);
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
			setState(92);
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
			setState(94);
			match(Elect);
			setState(98);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(95);
					passSymbVar();
					}
					} 
				}
				setState(100);
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
			setState(101);
			match(Vote);
			setState(105);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(102);
					passSymbVar();
					}
					} 
				}
				setState(107);
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
			setState(108);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__4) | (1L << T__5))) != 0)) ) {
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
			setState(110);
			match(T__6);
			setState(111);
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
			setState(113);
			match(OpenBracket);
			setState(114);
			symbolicVarExp();
			setState(115);
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
			setState(117);
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
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\26z\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\7\2\"\n\2\f\2\16\2%\13"+
		"\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\62\n\4\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5A\n\5\3\5\3\5\3\5\7\5F\n\5"+
		"\f\5\16\5I\13\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\5\t]\n\t\3\n\3\n\3\13\3\13\7\13c\n\13\f\13\16\13f\13"+
		"\13\3\f\3\f\7\fj\n\f\f\f\16\fm\13\f\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3"+
		"\17\3\17\3\20\3\20\3\20\2\3\b\21\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		"\2\3\3\2\6\by\2#\3\2\2\2\4&\3\2\2\2\6\61\3\2\2\2\b@\3\2\2\2\nJ\3\2\2\2"+
		"\fO\3\2\2\2\16R\3\2\2\2\20\\\3\2\2\2\22^\3\2\2\2\24`\3\2\2\2\26g\3\2\2"+
		"\2\30n\3\2\2\2\32p\3\2\2\2\34s\3\2\2\2\36w\3\2\2\2 \"\5\4\3\2! \3\2\2"+
		"\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2$\3\3\2\2\2%#\3\2\2\2&\'\5\6\4\2\'(\7"+
		"\3\2\2(\5\3\2\2\2)\62\5\b\5\2*\62\5\n\6\2+\62\5\f\7\2,\62\5\16\b\2-.\7"+
		"\r\2\2./\5\6\4\2/\60\7\f\2\2\60\62\3\2\2\2\61)\3\2\2\2\61*\3\2\2\2\61"+
		"+\3\2\2\2\61,\3\2\2\2\61-\3\2\2\2\62\7\3\2\2\2\63\64\b\5\1\2\64\65\5\n"+
		"\6\2\65\66\7\20\2\2\66\67\5\6\4\2\67A\3\2\2\289\5\f\7\29:\7\20\2\2:;\5"+
		"\6\4\2;A\3\2\2\2<=\5\16\b\2=>\7\20\2\2>?\5\6\4\2?A\3\2\2\2@\63\3\2\2\2"+
		"@8\3\2\2\2@<\3\2\2\2AG\3\2\2\2BC\f\6\2\2CD\7\20\2\2DF\5\6\4\2EB\3\2\2"+
		"\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\t\3\2\2\2IG\3\2\2\2JK\7\16\2\2KL\5\34"+
		"\17\2LM\7\4\2\2MN\5\6\4\2N\13\3\2\2\2OP\7\5\2\2PQ\5\6\4\2Q\r\3\2\2\2R"+
		"S\5\20\t\2ST\7\17\2\2TU\5\20\t\2U\17\3\2\2\2V]\5\24\13\2W]\5\26\f\2X]"+
		"\5\30\r\2Y]\5\32\16\2Z]\5\36\20\2[]\5\22\n\2\\V\3\2\2\2\\W\3\2\2\2\\X"+
		"\3\2\2\2\\Y\3\2\2\2\\Z\3\2\2\2\\[\3\2\2\2]\21\3\2\2\2^_\7\21\2\2_\23\3"+
		"\2\2\2`d\7\13\2\2ac\5\34\17\2ba\3\2\2\2cf\3\2\2\2db\3\2\2\2de\3\2\2\2"+
		"e\25\3\2\2\2fd\3\2\2\2gk\7\n\2\2hj\5\34\17\2ih\3\2\2\2jm\3\2\2\2ki\3\2"+
		"\2\2kl\3\2\2\2l\27\3\2\2\2mk\3\2\2\2no\t\2\2\2o\31\3\2\2\2pq\7\t\2\2q"+
		"r\5\34\17\2r\33\3\2\2\2st\7\r\2\2tu\5\36\20\2uv\7\f\2\2v\35\3\2\2\2wx"+
		"\7\22\2\2x\37\3\2\2\2\t#\61@G\\dk";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}