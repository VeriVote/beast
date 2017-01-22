package edu.pse.beast.toolbox.antlr.booleanexp;
// Generated from BooleanExpression.g by ANTLR 4.6
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BooleanExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		FOR_ALL_VOTERS=1, FOR_ALL_CANDIDATES=2, FOR_ALL_SEATS=3, EXISTS_ONE_VOTER=4, 
		EXISTS_ONE_CANDIDATE=5, EXISTS_ONE_SEAT=6, SUM_VOTES_FOR_CANDIDATE=7, 
		VOTES=8, ELECT=9, SYMBOLIC_VARIABLE=10, VOTERS_CONSTANT=11, CANDIDATES_CONSTANT=12, 
		SEATS_CONSTANT=13, LOGICAL_OR=14, LOGICAL_AND=15, LOGICAL_IMPLICATION=16, 
		LOGICAL_EQUALITY=17, RELATIONAL_EQUALITY=18, RELATIONAL_INEQUALITY=19, 
		RELATIONAL_LESS=20, RELATIONAL_LESSOREQUAL=21, RELATIONAL_GREATER=22, 
		RELATIONAL_GREATEROREQUAL=23, POSITIVE_INTEGER=24, CLOSING_BRACKET=25, 
		OPEN_BRACKET=26, COLON=27, WHITESPACE=28;
	public static final int
		RULE_property = 0, RULE_booleanExp = 1, RULE_relationalNode = 2, RULE_equalityRelation = 3, 
		RULE_inequalityRelation = 4, RULE_lessThanRelation = 5, RULE_lessOrEqualThanRelation = 6, 
		RULE_greaterThanRelation = 7, RULE_greaterOrEqualThanRelation = 8, RULE_relationParameter = 9, 
		RULE_constant = 10, RULE_forAllVoters = 11, RULE_forAllCandidates = 12, 
		RULE_forAllSeats = 13, RULE_existsOneVoter = 14, RULE_existsOneCandidate = 15, 
		RULE_existsOneSeat = 16, RULE_sumVotesForCandidate = 17, RULE_votesVariable = 18, 
		RULE_electVariable = 19;
	public static final String[] ruleNames = {
		"property", "booleanExp", "relationalNode", "equalityRelation", "inequalityRelation", 
		"lessThanRelation", "lessOrEqualThanRelation", "greaterThanRelation", 
		"greaterOrEqualThanRelation", "relationParameter", "constant", "forAllVoters", 
		"forAllCandidates", "forAllSeats", "existsOneVoter", "existsOneCandidate", 
		"existsOneSeat", "sumVotesForCandidate", "votesVariable", "electVariable"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'FOR_ALL_VOTERS('", "'FOR_ALL_CANDIDATES('", "'FOR_ALL_SEATS('", 
		"'EXISTS_ONE_VOTER('", "'EXISTS_ONE_CANDIDATE('", "'EXISTS_ONE_SEAT('", 
		"'SUM_VOTES_FOR_CANDIDATE('", "'VOTES'", "'ELECT'", null, "'V'", "'C'", 
		"'S'", "'||'", "'&&'", "'==>'", "'<==>'", "'=='", "'!='", "'<'", "'<='", 
		"'>'", "'>='", null, null, null, "':'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "FOR_ALL_VOTERS", "FOR_ALL_CANDIDATES", "FOR_ALL_SEATS", "EXISTS_ONE_VOTER", 
		"EXISTS_ONE_CANDIDATE", "EXISTS_ONE_SEAT", "SUM_VOTES_FOR_CANDIDATE", 
		"VOTES", "ELECT", "SYMBOLIC_VARIABLE", "VOTERS_CONSTANT", "CANDIDATES_CONSTANT", 
		"SEATS_CONSTANT", "LOGICAL_OR", "LOGICAL_AND", "LOGICAL_IMPLICATION", 
		"LOGICAL_EQUALITY", "RELATIONAL_EQUALITY", "RELATIONAL_INEQUALITY", "RELATIONAL_LESS", 
		"RELATIONAL_LESSOREQUAL", "RELATIONAL_GREATER", "RELATIONAL_GREATEROREQUAL", 
		"POSITIVE_INTEGER", "CLOSING_BRACKET", "OPEN_BRACKET", "COLON", "WHITESPACE"
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
	public String getGrammarFileName() { return "BooleanExpression.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BooleanExpressionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PropertyContext extends ParserRuleContext {
		public List<BooleanExpContext> booleanExp() {
			return getRuleContexts(BooleanExpContext.class);
		}
		public BooleanExpContext booleanExp(int i) {
			return getRuleContext(BooleanExpContext.class,i);
		}
		public TerminalNode LOGICAL_EQUALITY() { return getToken(BooleanExpressionParser.LOGICAL_EQUALITY, 0); }
		public TerminalNode LOGICAL_AND() { return getToken(BooleanExpressionParser.LOGICAL_AND, 0); }
		public TerminalNode LOGICAL_OR() { return getToken(BooleanExpressionParser.LOGICAL_OR, 0); }
		public TerminalNode LOGICAL_IMPLICATION() { return getToken(BooleanExpressionParser.LOGICAL_IMPLICATION, 0); }
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_property; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitProperty(this);
		}
	}

	public final PropertyContext property() throws RecognitionException {
		PropertyContext _localctx = new PropertyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_property);
		try {
			setState(57);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				booleanExp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				booleanExp();
				setState(42);
				match(LOGICAL_EQUALITY);
				setState(43);
				booleanExp();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(45);
				booleanExp();
				setState(46);
				match(LOGICAL_AND);
				setState(47);
				booleanExp();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(49);
				booleanExp();
				setState(50);
				match(LOGICAL_OR);
				setState(51);
				booleanExp();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(53);
				booleanExp();
				setState(54);
				match(LOGICAL_IMPLICATION);
				setState(55);
				booleanExp();
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

	public static class BooleanExpContext extends ParserRuleContext {
		public ForAllVotersContext forAllVoters() {
			return getRuleContext(ForAllVotersContext.class,0);
		}
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public ForAllCandidatesContext forAllCandidates() {
			return getRuleContext(ForAllCandidatesContext.class,0);
		}
		public ForAllSeatsContext forAllSeats() {
			return getRuleContext(ForAllSeatsContext.class,0);
		}
		public ExistsOneVoterContext existsOneVoter() {
			return getRuleContext(ExistsOneVoterContext.class,0);
		}
		public ExistsOneCandidateContext existsOneCandidate() {
			return getRuleContext(ExistsOneCandidateContext.class,0);
		}
		public ExistsOneSeatContext existsOneSeat() {
			return getRuleContext(ExistsOneSeatContext.class,0);
		}
		public RelationalNodeContext relationalNode() {
			return getRuleContext(RelationalNodeContext.class,0);
		}
		public BooleanExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterBooleanExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitBooleanExp(this);
		}
	}

	public final BooleanExpContext booleanExp() throws RecognitionException {
		BooleanExpContext _localctx = new BooleanExpContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_booleanExp);
		try {
			setState(78);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FOR_ALL_VOTERS:
				enterOuterAlt(_localctx, 1);
				{
				setState(59);
				forAllVoters();
				setState(60);
				booleanExp();
				}
				break;
			case FOR_ALL_CANDIDATES:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				forAllCandidates();
				setState(63);
				booleanExp();
				}
				break;
			case FOR_ALL_SEATS:
				enterOuterAlt(_localctx, 3);
				{
				setState(65);
				forAllSeats();
				setState(66);
				booleanExp();
				}
				break;
			case EXISTS_ONE_VOTER:
				enterOuterAlt(_localctx, 4);
				{
				setState(68);
				existsOneVoter();
				setState(69);
				booleanExp();
				}
				break;
			case EXISTS_ONE_CANDIDATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(71);
				existsOneCandidate();
				setState(72);
				booleanExp();
				}
				break;
			case EXISTS_ONE_SEAT:
				enterOuterAlt(_localctx, 6);
				{
				setState(74);
				existsOneSeat();
				setState(75);
				booleanExp();
				}
				break;
			case SUM_VOTES_FOR_CANDIDATE:
			case VOTES:
			case ELECT:
			case SYMBOLIC_VARIABLE:
			case VOTERS_CONSTANT:
			case CANDIDATES_CONSTANT:
			case SEATS_CONSTANT:
				enterOuterAlt(_localctx, 7);
				{
				setState(77);
				relationalNode();
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

	public static class RelationalNodeContext extends ParserRuleContext {
		public InequalityRelationContext inequalityRelation() {
			return getRuleContext(InequalityRelationContext.class,0);
		}
		public EqualityRelationContext equalityRelation() {
			return getRuleContext(EqualityRelationContext.class,0);
		}
		public LessThanRelationContext lessThanRelation() {
			return getRuleContext(LessThanRelationContext.class,0);
		}
		public LessOrEqualThanRelationContext lessOrEqualThanRelation() {
			return getRuleContext(LessOrEqualThanRelationContext.class,0);
		}
		public GreaterThanRelationContext greaterThanRelation() {
			return getRuleContext(GreaterThanRelationContext.class,0);
		}
		public GreaterOrEqualThanRelationContext greaterOrEqualThanRelation() {
			return getRuleContext(GreaterOrEqualThanRelationContext.class,0);
		}
		public RelationalNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalNode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterRelationalNode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitRelationalNode(this);
		}
	}

	public final RelationalNodeContext relationalNode() throws RecognitionException {
		RelationalNodeContext _localctx = new RelationalNodeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_relationalNode);
		try {
			setState(86);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				inequalityRelation();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				equalityRelation();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				lessThanRelation();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				lessOrEqualThanRelation();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(84);
				greaterThanRelation();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(85);
				greaterOrEqualThanRelation();
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

	public static class EqualityRelationContext extends ParserRuleContext {
		public List<RelationParameterContext> relationParameter() {
			return getRuleContexts(RelationParameterContext.class);
		}
		public RelationParameterContext relationParameter(int i) {
			return getRuleContext(RelationParameterContext.class,i);
		}
		public TerminalNode RELATIONAL_EQUALITY() { return getToken(BooleanExpressionParser.RELATIONAL_EQUALITY, 0); }
		public EqualityRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterEqualityRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitEqualityRelation(this);
		}
	}

	public final EqualityRelationContext equalityRelation() throws RecognitionException {
		EqualityRelationContext _localctx = new EqualityRelationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_equalityRelation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			relationParameter();
			setState(89);
			match(RELATIONAL_EQUALITY);
			setState(90);
			relationParameter();
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

	public static class InequalityRelationContext extends ParserRuleContext {
		public List<RelationParameterContext> relationParameter() {
			return getRuleContexts(RelationParameterContext.class);
		}
		public RelationParameterContext relationParameter(int i) {
			return getRuleContext(RelationParameterContext.class,i);
		}
		public TerminalNode RELATIONAL_INEQUALITY() { return getToken(BooleanExpressionParser.RELATIONAL_INEQUALITY, 0); }
		public InequalityRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inequalityRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterInequalityRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitInequalityRelation(this);
		}
	}

	public final InequalityRelationContext inequalityRelation() throws RecognitionException {
		InequalityRelationContext _localctx = new InequalityRelationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_inequalityRelation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			relationParameter();
			setState(93);
			match(RELATIONAL_INEQUALITY);
			setState(94);
			relationParameter();
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

	public static class LessThanRelationContext extends ParserRuleContext {
		public List<RelationParameterContext> relationParameter() {
			return getRuleContexts(RelationParameterContext.class);
		}
		public RelationParameterContext relationParameter(int i) {
			return getRuleContext(RelationParameterContext.class,i);
		}
		public TerminalNode RELATIONAL_LESS() { return getToken(BooleanExpressionParser.RELATIONAL_LESS, 0); }
		public LessThanRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lessThanRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterLessThanRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitLessThanRelation(this);
		}
	}

	public final LessThanRelationContext lessThanRelation() throws RecognitionException {
		LessThanRelationContext _localctx = new LessThanRelationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_lessThanRelation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			relationParameter();
			setState(97);
			match(RELATIONAL_LESS);
			setState(98);
			relationParameter();
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

	public static class LessOrEqualThanRelationContext extends ParserRuleContext {
		public List<RelationParameterContext> relationParameter() {
			return getRuleContexts(RelationParameterContext.class);
		}
		public RelationParameterContext relationParameter(int i) {
			return getRuleContext(RelationParameterContext.class,i);
		}
		public TerminalNode RELATIONAL_LESSOREQUAL() { return getToken(BooleanExpressionParser.RELATIONAL_LESSOREQUAL, 0); }
		public LessOrEqualThanRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lessOrEqualThanRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterLessOrEqualThanRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitLessOrEqualThanRelation(this);
		}
	}

	public final LessOrEqualThanRelationContext lessOrEqualThanRelation() throws RecognitionException {
		LessOrEqualThanRelationContext _localctx = new LessOrEqualThanRelationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_lessOrEqualThanRelation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			relationParameter();
			setState(101);
			match(RELATIONAL_LESSOREQUAL);
			setState(102);
			relationParameter();
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

	public static class GreaterThanRelationContext extends ParserRuleContext {
		public List<RelationParameterContext> relationParameter() {
			return getRuleContexts(RelationParameterContext.class);
		}
		public RelationParameterContext relationParameter(int i) {
			return getRuleContext(RelationParameterContext.class,i);
		}
		public TerminalNode RELATIONAL_GREATER() { return getToken(BooleanExpressionParser.RELATIONAL_GREATER, 0); }
		public GreaterThanRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_greaterThanRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterGreaterThanRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitGreaterThanRelation(this);
		}
	}

	public final GreaterThanRelationContext greaterThanRelation() throws RecognitionException {
		GreaterThanRelationContext _localctx = new GreaterThanRelationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_greaterThanRelation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			relationParameter();
			setState(105);
			match(RELATIONAL_GREATER);
			setState(106);
			relationParameter();
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

	public static class GreaterOrEqualThanRelationContext extends ParserRuleContext {
		public List<RelationParameterContext> relationParameter() {
			return getRuleContexts(RelationParameterContext.class);
		}
		public RelationParameterContext relationParameter(int i) {
			return getRuleContext(RelationParameterContext.class,i);
		}
		public TerminalNode RELATIONAL_GREATEROREQUAL() { return getToken(BooleanExpressionParser.RELATIONAL_GREATEROREQUAL, 0); }
		public GreaterOrEqualThanRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_greaterOrEqualThanRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterGreaterOrEqualThanRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitGreaterOrEqualThanRelation(this);
		}
	}

	public final GreaterOrEqualThanRelationContext greaterOrEqualThanRelation() throws RecognitionException {
		GreaterOrEqualThanRelationContext _localctx = new GreaterOrEqualThanRelationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_greaterOrEqualThanRelation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			relationParameter();
			setState(109);
			match(RELATIONAL_GREATEROREQUAL);
			setState(110);
			relationParameter();
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

	public static class RelationParameterContext extends ParserRuleContext {
		public TerminalNode SYMBOLIC_VARIABLE() { return getToken(BooleanExpressionParser.SYMBOLIC_VARIABLE, 0); }
		public VotesVariableContext votesVariable() {
			return getRuleContext(VotesVariableContext.class,0);
		}
		public ElectVariableContext electVariable() {
			return getRuleContext(ElectVariableContext.class,0);
		}
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public SumVotesForCandidateContext sumVotesForCandidate() {
			return getRuleContext(SumVotesForCandidateContext.class,0);
		}
		public RelationParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterRelationParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitRelationParameter(this);
		}
	}

	public final RelationParameterContext relationParameter() throws RecognitionException {
		RelationParameterContext _localctx = new RelationParameterContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_relationParameter);
		try {
			setState(117);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SYMBOLIC_VARIABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(112);
				match(SYMBOLIC_VARIABLE);
				}
				break;
			case VOTES:
				enterOuterAlt(_localctx, 2);
				{
				setState(113);
				votesVariable();
				}
				break;
			case ELECT:
				enterOuterAlt(_localctx, 3);
				{
				setState(114);
				electVariable();
				}
				break;
			case VOTERS_CONSTANT:
			case CANDIDATES_CONSTANT:
			case SEATS_CONSTANT:
				enterOuterAlt(_localctx, 4);
				{
				setState(115);
				constant();
				}
				break;
			case SUM_VOTES_FOR_CANDIDATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(116);
				sumVotesForCandidate();
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

	public static class ConstantContext extends ParserRuleContext {
		public TerminalNode VOTERS_CONSTANT() { return getToken(BooleanExpressionParser.VOTERS_CONSTANT, 0); }
		public TerminalNode CANDIDATES_CONSTANT() { return getToken(BooleanExpressionParser.CANDIDATES_CONSTANT, 0); }
		public TerminalNode SEATS_CONSTANT() { return getToken(BooleanExpressionParser.SEATS_CONSTANT, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitConstant(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VOTERS_CONSTANT) | (1L << CANDIDATES_CONSTANT) | (1L << SEATS_CONSTANT))) != 0)) ) {
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

	public static class ForAllVotersContext extends ParserRuleContext {
		public TerminalNode FOR_ALL_VOTERS() { return getToken(BooleanExpressionParser.FOR_ALL_VOTERS, 0); }
		public TerminalNode SYMBOLIC_VARIABLE() { return getToken(BooleanExpressionParser.SYMBOLIC_VARIABLE, 0); }
		public TerminalNode CLOSING_BRACKET() { return getToken(BooleanExpressionParser.CLOSING_BRACKET, 0); }
		public ForAllVotersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forAllVoters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterForAllVoters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitForAllVoters(this);
		}
	}

	public final ForAllVotersContext forAllVoters() throws RecognitionException {
		ForAllVotersContext _localctx = new ForAllVotersContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_forAllVoters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(FOR_ALL_VOTERS);
			setState(122);
			match(SYMBOLIC_VARIABLE);
			setState(123);
			match(CLOSING_BRACKET);
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

	public static class ForAllCandidatesContext extends ParserRuleContext {
		public TerminalNode FOR_ALL_CANDIDATES() { return getToken(BooleanExpressionParser.FOR_ALL_CANDIDATES, 0); }
		public TerminalNode SYMBOLIC_VARIABLE() { return getToken(BooleanExpressionParser.SYMBOLIC_VARIABLE, 0); }
		public TerminalNode CLOSING_BRACKET() { return getToken(BooleanExpressionParser.CLOSING_BRACKET, 0); }
		public ForAllCandidatesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forAllCandidates; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterForAllCandidates(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitForAllCandidates(this);
		}
	}

	public final ForAllCandidatesContext forAllCandidates() throws RecognitionException {
		ForAllCandidatesContext _localctx = new ForAllCandidatesContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_forAllCandidates);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(FOR_ALL_CANDIDATES);
			setState(126);
			match(SYMBOLIC_VARIABLE);
			setState(127);
			match(CLOSING_BRACKET);
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

	public static class ForAllSeatsContext extends ParserRuleContext {
		public TerminalNode FOR_ALL_SEATS() { return getToken(BooleanExpressionParser.FOR_ALL_SEATS, 0); }
		public TerminalNode SYMBOLIC_VARIABLE() { return getToken(BooleanExpressionParser.SYMBOLIC_VARIABLE, 0); }
		public TerminalNode CLOSING_BRACKET() { return getToken(BooleanExpressionParser.CLOSING_BRACKET, 0); }
		public ForAllSeatsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forAllSeats; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterForAllSeats(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitForAllSeats(this);
		}
	}

	public final ForAllSeatsContext forAllSeats() throws RecognitionException {
		ForAllSeatsContext _localctx = new ForAllSeatsContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_forAllSeats);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(FOR_ALL_SEATS);
			setState(130);
			match(SYMBOLIC_VARIABLE);
			setState(131);
			match(CLOSING_BRACKET);
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

	public static class ExistsOneVoterContext extends ParserRuleContext {
		public TerminalNode EXISTS_ONE_VOTER() { return getToken(BooleanExpressionParser.EXISTS_ONE_VOTER, 0); }
		public TerminalNode SYMBOLIC_VARIABLE() { return getToken(BooleanExpressionParser.SYMBOLIC_VARIABLE, 0); }
		public TerminalNode CLOSING_BRACKET() { return getToken(BooleanExpressionParser.CLOSING_BRACKET, 0); }
		public ExistsOneVoterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_existsOneVoter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterExistsOneVoter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitExistsOneVoter(this);
		}
	}

	public final ExistsOneVoterContext existsOneVoter() throws RecognitionException {
		ExistsOneVoterContext _localctx = new ExistsOneVoterContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_existsOneVoter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(EXISTS_ONE_VOTER);
			setState(134);
			match(SYMBOLIC_VARIABLE);
			setState(135);
			match(CLOSING_BRACKET);
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

	public static class ExistsOneCandidateContext extends ParserRuleContext {
		public TerminalNode EXISTS_ONE_CANDIDATE() { return getToken(BooleanExpressionParser.EXISTS_ONE_CANDIDATE, 0); }
		public TerminalNode SYMBOLIC_VARIABLE() { return getToken(BooleanExpressionParser.SYMBOLIC_VARIABLE, 0); }
		public TerminalNode CLOSING_BRACKET() { return getToken(BooleanExpressionParser.CLOSING_BRACKET, 0); }
		public ExistsOneCandidateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_existsOneCandidate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterExistsOneCandidate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitExistsOneCandidate(this);
		}
	}

	public final ExistsOneCandidateContext existsOneCandidate() throws RecognitionException {
		ExistsOneCandidateContext _localctx = new ExistsOneCandidateContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_existsOneCandidate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(EXISTS_ONE_CANDIDATE);
			setState(138);
			match(SYMBOLIC_VARIABLE);
			setState(139);
			match(CLOSING_BRACKET);
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

	public static class ExistsOneSeatContext extends ParserRuleContext {
		public TerminalNode EXISTS_ONE_SEAT() { return getToken(BooleanExpressionParser.EXISTS_ONE_SEAT, 0); }
		public TerminalNode SYMBOLIC_VARIABLE() { return getToken(BooleanExpressionParser.SYMBOLIC_VARIABLE, 0); }
		public TerminalNode CLOSING_BRACKET() { return getToken(BooleanExpressionParser.CLOSING_BRACKET, 0); }
		public ExistsOneSeatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_existsOneSeat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterExistsOneSeat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitExistsOneSeat(this);
		}
	}

	public final ExistsOneSeatContext existsOneSeat() throws RecognitionException {
		ExistsOneSeatContext _localctx = new ExistsOneSeatContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_existsOneSeat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(EXISTS_ONE_SEAT);
			setState(142);
			match(SYMBOLIC_VARIABLE);
			setState(143);
			match(CLOSING_BRACKET);
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

	public static class SumVotesForCandidateContext extends ParserRuleContext {
		public TerminalNode SUM_VOTES_FOR_CANDIDATE() { return getToken(BooleanExpressionParser.SUM_VOTES_FOR_CANDIDATE, 0); }
		public TerminalNode SYMBOLIC_VARIABLE() { return getToken(BooleanExpressionParser.SYMBOLIC_VARIABLE, 0); }
		public TerminalNode CLOSING_BRACKET() { return getToken(BooleanExpressionParser.CLOSING_BRACKET, 0); }
		public SumVotesForCandidateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sumVotesForCandidate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterSumVotesForCandidate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitSumVotesForCandidate(this);
		}
	}

	public final SumVotesForCandidateContext sumVotesForCandidate() throws RecognitionException {
		SumVotesForCandidateContext _localctx = new SumVotesForCandidateContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_sumVotesForCandidate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			match(SUM_VOTES_FOR_CANDIDATE);
			setState(146);
			match(SYMBOLIC_VARIABLE);
			setState(147);
			match(CLOSING_BRACKET);
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

	public static class VotesVariableContext extends ParserRuleContext {
		public TerminalNode VOTES() { return getToken(BooleanExpressionParser.VOTES, 0); }
		public TerminalNode OPEN_BRACKET() { return getToken(BooleanExpressionParser.OPEN_BRACKET, 0); }
		public TerminalNode POSITIVE_INTEGER() { return getToken(BooleanExpressionParser.POSITIVE_INTEGER, 0); }
		public TerminalNode SYMBOLIC_VARIABLE() { return getToken(BooleanExpressionParser.SYMBOLIC_VARIABLE, 0); }
		public TerminalNode CLOSING_BRACKET() { return getToken(BooleanExpressionParser.CLOSING_BRACKET, 0); }
		public VotesVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_votesVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterVotesVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitVotesVariable(this);
		}
	}

	public final VotesVariableContext votesVariable() throws RecognitionException {
		VotesVariableContext _localctx = new VotesVariableContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_votesVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(VOTES);
			setState(150);
			match(OPEN_BRACKET);
			setState(151);
			match(POSITIVE_INTEGER);
			setState(152);
			match(SYMBOLIC_VARIABLE);
			setState(153);
			match(CLOSING_BRACKET);
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

	public static class ElectVariableContext extends ParserRuleContext {
		public TerminalNode ELECT() { return getToken(BooleanExpressionParser.ELECT, 0); }
		public TerminalNode POSITIVE_INTEGER() { return getToken(BooleanExpressionParser.POSITIVE_INTEGER, 0); }
		public ElectVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_electVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).enterElectVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BooleanExpressionListener ) ((BooleanExpressionListener)listener).exitElectVariable(this);
		}
	}

	public final ElectVariableContext electVariable() throws RecognitionException {
		ElectVariableContext _localctx = new ElectVariableContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_electVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(ELECT);
			setState(156);
			match(POSITIVE_INTEGER);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\36\u00a1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2<\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3Q\n\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\5\4Y\n\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13"+
		"\5\13x\n\13\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\2\2"+
		"\26\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(\2\3\3\2\r\17\u009f\2"+
		";\3\2\2\2\4P\3\2\2\2\6X\3\2\2\2\bZ\3\2\2\2\n^\3\2\2\2\fb\3\2\2\2\16f\3"+
		"\2\2\2\20j\3\2\2\2\22n\3\2\2\2\24w\3\2\2\2\26y\3\2\2\2\30{\3\2\2\2\32"+
		"\177\3\2\2\2\34\u0083\3\2\2\2\36\u0087\3\2\2\2 \u008b\3\2\2\2\"\u008f"+
		"\3\2\2\2$\u0093\3\2\2\2&\u0097\3\2\2\2(\u009d\3\2\2\2*<\5\4\3\2+,\5\4"+
		"\3\2,-\7\23\2\2-.\5\4\3\2.<\3\2\2\2/\60\5\4\3\2\60\61\7\21\2\2\61\62\5"+
		"\4\3\2\62<\3\2\2\2\63\64\5\4\3\2\64\65\7\20\2\2\65\66\5\4\3\2\66<\3\2"+
		"\2\2\678\5\4\3\289\7\22\2\29:\5\4\3\2:<\3\2\2\2;*\3\2\2\2;+\3\2\2\2;/"+
		"\3\2\2\2;\63\3\2\2\2;\67\3\2\2\2<\3\3\2\2\2=>\5\30\r\2>?\5\4\3\2?Q\3\2"+
		"\2\2@A\5\32\16\2AB\5\4\3\2BQ\3\2\2\2CD\5\34\17\2DE\5\4\3\2EQ\3\2\2\2F"+
		"G\5\36\20\2GH\5\4\3\2HQ\3\2\2\2IJ\5 \21\2JK\5\4\3\2KQ\3\2\2\2LM\5\"\22"+
		"\2MN\5\4\3\2NQ\3\2\2\2OQ\5\6\4\2P=\3\2\2\2P@\3\2\2\2PC\3\2\2\2PF\3\2\2"+
		"\2PI\3\2\2\2PL\3\2\2\2PO\3\2\2\2Q\5\3\2\2\2RY\5\n\6\2SY\5\b\5\2TY\5\f"+
		"\7\2UY\5\16\b\2VY\5\20\t\2WY\5\22\n\2XR\3\2\2\2XS\3\2\2\2XT\3\2\2\2XU"+
		"\3\2\2\2XV\3\2\2\2XW\3\2\2\2Y\7\3\2\2\2Z[\5\24\13\2[\\\7\24\2\2\\]\5\24"+
		"\13\2]\t\3\2\2\2^_\5\24\13\2_`\7\25\2\2`a\5\24\13\2a\13\3\2\2\2bc\5\24"+
		"\13\2cd\7\26\2\2de\5\24\13\2e\r\3\2\2\2fg\5\24\13\2gh\7\27\2\2hi\5\24"+
		"\13\2i\17\3\2\2\2jk\5\24\13\2kl\7\30\2\2lm\5\24\13\2m\21\3\2\2\2no\5\24"+
		"\13\2op\7\31\2\2pq\5\24\13\2q\23\3\2\2\2rx\7\f\2\2sx\5&\24\2tx\5(\25\2"+
		"ux\5\26\f\2vx\5$\23\2wr\3\2\2\2ws\3\2\2\2wt\3\2\2\2wu\3\2\2\2wv\3\2\2"+
		"\2x\25\3\2\2\2yz\t\2\2\2z\27\3\2\2\2{|\7\3\2\2|}\7\f\2\2}~\7\33\2\2~\31"+
		"\3\2\2\2\177\u0080\7\4\2\2\u0080\u0081\7\f\2\2\u0081\u0082\7\33\2\2\u0082"+
		"\33\3\2\2\2\u0083\u0084\7\5\2\2\u0084\u0085\7\f\2\2\u0085\u0086\7\33\2"+
		"\2\u0086\35\3\2\2\2\u0087\u0088\7\6\2\2\u0088\u0089\7\f\2\2\u0089\u008a"+
		"\7\33\2\2\u008a\37\3\2\2\2\u008b\u008c\7\7\2\2\u008c\u008d\7\f\2\2\u008d"+
		"\u008e\7\33\2\2\u008e!\3\2\2\2\u008f\u0090\7\b\2\2\u0090\u0091\7\f\2\2"+
		"\u0091\u0092\7\33\2\2\u0092#\3\2\2\2\u0093\u0094\7\t\2\2\u0094\u0095\7"+
		"\f\2\2\u0095\u0096\7\33\2\2\u0096%\3\2\2\2\u0097\u0098\7\n\2\2\u0098\u0099"+
		"\7\34\2\2\u0099\u009a\7\32\2\2\u009a\u009b\7\f\2\2\u009b\u009c\7\33\2"+
		"\2\u009c\'\3\2\2\2\u009d\u009e\7\13\2\2\u009e\u009f\7\32\2\2\u009f)\3"+
		"\2\2\2\6;PXw";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}