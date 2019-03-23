// Generated from FormalPropertyDescription.g4 by ANTLR 4.7.1
package edu.pse.beast.toolbox.antlr.booleanexp;
import java.util.List;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FormalPropertyDescriptionParser extends Parser {
  static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache =
    new PredictionContextCache();
  public static final int
    T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
    T__9=10, Mult=11, Add=12, Concatenate=13, Intersect=14, Vote=15, Elect=16, 
    NotEmpty=17, Votesum=18, VotesumUnique=19, ClosedBracket=20, OpenBracket=21, 
    Quantor=22, Split=23, Permutation=24, ValueAssign=25, ComparisonSymbol=26, 
    BinaryRelationSymbol=27, Integer=28, Identifier=29, Whitespace=30, Newline=31, 
    BlockComment=32, LineComment=33;
  public static final int
    RULE_booleanExpList = 0, RULE_booleanExpListElement = 1, RULE_votingListChangeExp = 2, 
    RULE_votingListChangeContent = 3, RULE_votingTupelChangeExp = 4, RULE_candidateListChangeExp = 5, 
    RULE_booleanExp = 6, RULE_binaryRelationExp = 7, RULE_addedContentExp = 8, 
    RULE_notEmptyExp = 9, RULE_notEmptyContent = 10, RULE_voteEquivalents = 11, 
    RULE_concatenationExp = 12, RULE_splitExp = 13, RULE_permutationExp = 14, 
    RULE_intersectExp = 15, RULE_intersectContent = 16, RULE_tuple = 17, RULE_tupleContent = 18, 
    RULE_quantorExp = 19, RULE_notExp = 20, RULE_comparisonExp = 21, RULE_typeExp = 22, 
    RULE_numberExpression = 23, RULE_typeByPosExp = 24, RULE_voterByPosExp = 25, 
    RULE_candByPosExp = 26, RULE_seatByPosExp = 27, RULE_integer = 28, RULE_electExp = 29, 
    RULE_voteExp = 30, RULE_passType = 31, RULE_constantExp = 32, RULE_voteSumExp = 33, 
    RULE_voteSumUniqueExp = 34, RULE_passSymbVar = 35, RULE_passPosition = 36, 
    RULE_passByPos = 37, RULE_symbolicVarExp = 38;
  public static final String[] ruleNames = {
    "booleanExpList", "booleanExpListElement", "votingListChangeExp", "votingListChangeContent", 
    "votingTupelChangeExp", "candidateListChangeExp", "booleanExp", "binaryRelationExp", 
    "addedContentExp", "notEmptyExp", "notEmptyContent", "voteEquivalents", 
    "concatenationExp", "splitExp", "permutationExp", "intersectExp", "intersectContent", 
    "tuple", "tupleContent", "quantorExp", "notExp", "comparisonExp", "typeExp", 
    "numberExpression", "typeByPosExp", "voterByPosExp", "candByPosExp", "seatByPosExp", 
    "integer", "electExp", "voteExp", "passType", "constantExp", "voteSumExp", 
    "voteSumUniqueExp", "passSymbVar", "passPosition", "passByPos", "symbolicVarExp"
  };

  private static final String[] _LITERAL_NAMES = {
    null, "';'", "','", "':'", "'!'", "'VOTER_AT_POS'", "'CAND_AT_POS'", "'SEAT_AT_POS'", 
    "'V'", "'C'", "'S'", null, null, "'++'", "'INTERSECT'", null, null, "'NOTEMPTY'", 
    null, null, "')'", "'('", null, "'SPLIT'", "'PERM'", "'<-'"
  };
  private static final String[] _SYMBOLIC_NAMES = {
    null, null, null, null, null, null, null, null, null, null, null, "Mult", 
    "Add", "Concatenate", "Intersect", "Vote", "Elect", "NotEmpty", "Votesum", 
    "VotesumUnique", "ClosedBracket", "OpenBracket", "Quantor", "Split", "Permutation", 
    "ValueAssign", "ComparisonSymbol", "BinaryRelationSymbol", "Integer", 
    "Identifier", "Whitespace", "Newline", "BlockComment", "LineComment"
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
      setState(81);
      _errHandler.sync(this);
      _la = _input.LA(1);
      while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << Intersect) | (1L << Vote) | (1L << Elect) | (1L << NotEmpty) | (1L << Votesum) | (1L << VotesumUnique) | (1L << OpenBracket) | (1L << Quantor) | (1L << Integer) | (1L << Identifier))) != 0)) {
        {
        {
        setState(78);
        booleanExpListElement();
        }
        }
        setState(83);
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
      setState(96);
      _errHandler.sync(this);
      switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
      case 1:
        enterOuterAlt(_localctx, 1);
        {
        setState(84);
        booleanExp();
        setState(85);
        match(T__0);
        }
        break;
      case 2:
        enterOuterAlt(_localctx, 2);
        {
        setState(87);
        votingListChangeExp();
        setState(88);
        match(T__0);
        }
        break;
      case 3:
        enterOuterAlt(_localctx, 3);
        {
        setState(90);
        votingTupelChangeExp();
        setState(91);
        match(T__0);
        }
        break;
      case 4:
        enterOuterAlt(_localctx, 4);
        {
        setState(93);
        candidateListChangeExp();
        setState(94);
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
      setState(98);
      match(Vote);
      setState(99);
      match(ValueAssign);
      setState(100);
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
      setState(104);
      _errHandler.sync(this);
      switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
      case 1:
        enterOuterAlt(_localctx, 1);
        {
        setState(102);
        concatenationExp();
        }
        break;
      case 2:
        enterOuterAlt(_localctx, 2);
        {
        setState(103);
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
      setState(106);
      tuple();
      setState(107);
      match(ValueAssign);
      setState(108);
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
      setState(110);
      match(Elect);
      setState(111);
      match(ValueAssign);
      setState(112);
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
    public NotEmptyExpContext notEmptyExp() {
      return getRuleContext(NotEmptyExpContext.class,0);
    }
    public IntersectExpContext intersectExp() {
      return getRuleContext(IntersectExpContext.class,0);
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
      setState(124);
      _errHandler.sync(this);
      switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
      case 1:
        enterOuterAlt(_localctx, 1);
        {
        setState(114);
        quantorExp();
        }
        break;
      case 2:
        enterOuterAlt(_localctx, 2);
        {
        setState(115);
        notEmptyExp();
        }
        break;
      case 3:
        enterOuterAlt(_localctx, 3);
        {
        setState(116);
        intersectExp();
        }
        break;
      case 4:
        enterOuterAlt(_localctx, 4);
        {
        setState(117);
        binaryRelationExp(0);
        }
        break;
      case 5:
        enterOuterAlt(_localctx, 5);
        {
        setState(118);
        notExp();
        }
        break;
      case 6:
        enterOuterAlt(_localctx, 6);
        {
        setState(119);
        comparisonExp();
        }
        break;
      case 7:
        enterOuterAlt(_localctx, 7);
        {
        setState(120);
        match(OpenBracket);
        setState(121);
        booleanExp();
        setState(122);
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
    public NotEmptyExpContext notEmptyExp() {
      return getRuleContext(NotEmptyExpContext.class,0);
    }
    public IntersectExpContext intersectExp() {
      return getRuleContext(IntersectExpContext.class,0);
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
      setState(183);
      _errHandler.sync(this);
      switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
      case 1:
        {
        setState(127);
        quantorExp();
        setState(128);
        match(BinaryRelationSymbol);
        setState(129);
        booleanExp();
        }
        break;
      case 2:
        {
        setState(131);
        notExp();
        setState(132);
        match(BinaryRelationSymbol);
        setState(133);
        booleanExp();
        }
        break;
      case 3:
        {
        setState(135);
        comparisonExp();
        setState(136);
        match(BinaryRelationSymbol);
        setState(137);
        booleanExp();
        }
        break;
      case 4:
        {
        setState(139);
        notEmptyExp();
        setState(140);
        match(BinaryRelationSymbol);
        setState(141);
        booleanExp();
        }
        break;
      case 5:
        {
        setState(143);
        intersectExp();
        setState(144);
        match(BinaryRelationSymbol);
        setState(145);
        booleanExp();
        }
        break;
      case 6:
        {
        setState(147);
        match(OpenBracket);
        setState(148);
        binaryRelationExp(0);
        setState(149);
        match(ClosedBracket);
        setState(150);
        match(BinaryRelationSymbol);
        setState(151);
        booleanExp();
        }
        break;
      case 7:
        {
        setState(153);
        match(OpenBracket);
        setState(154);
        quantorExp();
        setState(155);
        match(ClosedBracket);
        setState(156);
        match(BinaryRelationSymbol);
        setState(157);
        booleanExp();
        }
        break;
      case 8:
        {
        setState(159);
        match(OpenBracket);
        setState(160);
        notExp();
        setState(161);
        match(ClosedBracket);
        setState(162);
        match(BinaryRelationSymbol);
        setState(163);
        booleanExp();
        }
        break;
      case 9:
        {
        setState(165);
        match(OpenBracket);
        setState(166);
        comparisonExp();
        setState(167);
        match(ClosedBracket);
        setState(168);
        match(BinaryRelationSymbol);
        setState(169);
        booleanExp();
        }
        break;
      case 10:
        {
        setState(171);
        match(OpenBracket);
        setState(172);
        notEmptyExp();
        setState(173);
        match(ClosedBracket);
        setState(174);
        match(BinaryRelationSymbol);
        setState(175);
        booleanExp();
        }
        break;
      case 11:
        {
        setState(177);
        match(OpenBracket);
        setState(178);
        intersectExp();
        setState(179);
        match(ClosedBracket);
        setState(180);
        match(BinaryRelationSymbol);
        setState(181);
        booleanExp();
        }
        break;
      }
      _ctx.stop = _input.LT(-1);
      setState(190);
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
          setState(185);
          if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
          setState(186);
          match(BinaryRelationSymbol);
          setState(187);
          booleanExp();
          }
          } 
        }
        setState(192);
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

  public static class AddedContentExpContext extends ParserRuleContext {
    public NotEmptyExpContext notEmptyExp() {
      return getRuleContext(NotEmptyExpContext.class,0);
    }
    public IntersectExpContext intersectExp() {
      return getRuleContext(IntersectExpContext.class,0);
    }
    public AddedContentExpContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_addedContentExp; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterAddedContentExp(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitAddedContentExp(this);
    }
  }

  public final AddedContentExpContext addedContentExp() throws RecognitionException {
    AddedContentExpContext _localctx = new AddedContentExpContext(_ctx, getState());
    enterRule(_localctx, 16, RULE_addedContentExp);
    try {
      setState(195);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
      case NotEmpty:
        enterOuterAlt(_localctx, 1);
        {
        setState(193);
        notEmptyExp();
        }
        break;
      case Intersect:
        enterOuterAlt(_localctx, 2);
        {
        setState(194);
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

  public static class NotEmptyExpContext extends ParserRuleContext {
    public TerminalNode NotEmpty() { return getToken(FormalPropertyDescriptionParser.NotEmpty, 0); }
    public NotEmptyContentContext notEmptyContent() {
      return getRuleContext(NotEmptyContentContext.class,0);
    }
    public NotEmptyExpContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_notEmptyExp; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterNotEmptyExp(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitNotEmptyExp(this);
    }
  }

  public final NotEmptyExpContext notEmptyExp() throws RecognitionException {
    NotEmptyExpContext _localctx = new NotEmptyExpContext(_ctx, getState());
    enterRule(_localctx, 18, RULE_notEmptyExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(197);
      match(NotEmpty);
      setState(198);
      match(OpenBracket);
      setState(199);
      notEmptyContent();
      setState(200);
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

  public static class NotEmptyContentContext extends ParserRuleContext {
    public TerminalNode Elect() { return getToken(FormalPropertyDescriptionParser.Elect, 0); }
    public IntersectExpContext intersectExp() {
      return getRuleContext(IntersectExpContext.class,0);
    }
    public NotEmptyContentContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_notEmptyContent; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).enterNotEmptyContent(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof FormalPropertyDescriptionListener ) ((FormalPropertyDescriptionListener)listener).exitNotEmptyContent(this);
    }
  }

  public final NotEmptyContentContext notEmptyContent() throws RecognitionException {
    NotEmptyContentContext _localctx = new NotEmptyContentContext(_ctx, getState());
    enterRule(_localctx, 20, RULE_notEmptyContent);
    try {
      setState(204);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
      case Elect:
        enterOuterAlt(_localctx, 1);
        {
        setState(202);
        match(Elect);
        }
        break;
      case Intersect:
        enterOuterAlt(_localctx, 2);
        {
        setState(203);
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
    enterRule(_localctx, 22, RULE_voteEquivalents);
    try {
      setState(209);
      _errHandler.sync(this);
      switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
      case 1:
        enterOuterAlt(_localctx, 1);
        {
        setState(206);
        match(Vote);
        }
        break;
      case 2:
        enterOuterAlt(_localctx, 2);
        {
        setState(207);
        permutationExp();
        }
        break;
      case 3:
        enterOuterAlt(_localctx, 3);
        {
        setState(208);
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
    enterRule(_localctx, 24, RULE_concatenationExp);
    try {
      setState(224);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
      case OpenBracket:
        enterOuterAlt(_localctx, 1);
        {
        setState(211);
        match(OpenBracket);
        setState(212);
        voteEquivalents();
        setState(213);
        match(Concatenate);
        setState(214);
        voteEquivalents();
        setState(215);
        match(ClosedBracket);
        }
        break;
      case Vote:
        enterOuterAlt(_localctx, 2);
        {
        setState(217);
        match(Vote);
        setState(218);
        match(Concatenate);
        setState(219);
        voteEquivalents();
        }
        break;
      case Permutation:
        enterOuterAlt(_localctx, 3);
        {
        setState(220);
        permutationExp();
        setState(221);
        match(Concatenate);
        setState(222);
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
    enterRule(_localctx, 26, RULE_splitExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(226);
      match(Split);
      setState(227);
      match(OpenBracket);
      setState(228);
      voteEquivalents();
      setState(229);
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
    enterRule(_localctx, 28, RULE_permutationExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(231);
      match(Permutation);
      setState(232);
      match(OpenBracket);
      setState(233);
      voteEquivalents();
      setState(234);
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
    enterRule(_localctx, 30, RULE_intersectExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(236);
      match(Intersect);
      setState(237);
      match(OpenBracket);
      setState(238);
      intersectContent();
      setState(239);
      match(T__1);
      setState(240);
      intersectContent();
      setState(241);
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
    enterRule(_localctx, 32, RULE_intersectContent);
    try {
      setState(245);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
      case Elect:
        enterOuterAlt(_localctx, 1);
        {
        setState(243);
        match(Elect);
        }
        break;
      case Intersect:
        enterOuterAlt(_localctx, 2);
        {
        setState(244);
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
    enterRule(_localctx, 34, RULE_tuple);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(247);
      match(OpenBracket);
      setState(248);
      match(Vote);
      setState(249);
      tupleContent();
      setState(250);
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
    enterRule(_localctx, 36, RULE_tupleContent);
    try {
      setState(257);
      _errHandler.sync(this);
      switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
      case 1:
        enterOuterAlt(_localctx, 1);
        {
        setState(252);
        match(T__1);
        setState(253);
        match(Vote);
        }
        break;
      case 2:
        enterOuterAlt(_localctx, 2);
        {
        setState(254);
        match(T__1);
        setState(255);
        match(Vote);
        setState(256);
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
    enterRule(_localctx, 38, RULE_quantorExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(259);
      match(Quantor);
      setState(260);
      passSymbVar();
      setState(261);
      match(T__2);
      setState(262);
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
    enterRule(_localctx, 40, RULE_notExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(264);
      match(T__3);
      setState(265);
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
    enterRule(_localctx, 42, RULE_comparisonExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(267);
      typeExp();
      setState(268);
      match(ComparisonSymbol);
      setState(269);
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
    public IntersectExpContext intersectExp() {
      return getRuleContext(IntersectExpContext.class,0);
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
    enterRule(_localctx, 44, RULE_typeExp);
    try {
      setState(277);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
      case Elect:
        enterOuterAlt(_localctx, 1);
        {
        setState(271);
        electExp();
        }
        break;
      case Vote:
        enterOuterAlt(_localctx, 2);
        {
        setState(272);
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
        setState(273);
        numberExpression(0);
        }
        break;
      case Identifier:
        enterOuterAlt(_localctx, 4);
        {
        setState(274);
        symbolicVarExp();
        }
        break;
      case T__4:
      case T__5:
      case T__6:
        enterOuterAlt(_localctx, 5);
        {
        setState(275);
        typeByPosExp();
        }
        break;
      case Intersect:
        enterOuterAlt(_localctx, 6);
        {
        setState(276);
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
    int _startState = 46;
    enterRecursionRule(_localctx, 46, RULE_numberExpression, _p);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
      setState(288);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
      case OpenBracket:
        {
        setState(280);
        match(OpenBracket);
        setState(281);
        numberExpression(0);
        setState(282);
        match(ClosedBracket);
        }
        break;
      case Votesum:
        {
        setState(284);
        voteSumExp();
        }
        break;
      case VotesumUnique:
        {
        setState(285);
        voteSumUniqueExp();
        }
        break;
      case T__7:
      case T__8:
      case T__9:
        {
        setState(286);
        constantExp();
        }
        break;
      case Integer:
        {
        setState(287);
        integer();
        }
        break;
      default:
        throw new NoViableAltException(this);
      }
      _ctx.stop = _input.LT(-1);
      setState(298);
      _errHandler.sync(this);
      _alt = getInterpreter().adaptivePredict(_input,15,_ctx);
      while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
        if ( _alt==1 ) {
          if ( _parseListeners!=null ) triggerExitRuleEvent();
          _prevctx = _localctx;
          {
          setState(296);
          _errHandler.sync(this);
          switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
          case 1:
            {
            _localctx = new NumberExpressionContext(_parentctx, _parentState);
            pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
            setState(290);
            if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
            setState(291);
            match(Mult);
            setState(292);
            numberExpression(7);
            }
            break;
          case 2:
            {
            _localctx = new NumberExpressionContext(_parentctx, _parentState);
            pushNewRecursionContext(_localctx, _startState, RULE_numberExpression);
            setState(293);
            if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
            setState(294);
            match(Add);
            setState(295);
            numberExpression(6);
            }
            break;
          }
          } 
        }
        setState(300);
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
    enterRule(_localctx, 48, RULE_typeByPosExp);
    try {
      setState(304);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
      case T__4:
        enterOuterAlt(_localctx, 1);
        {
        setState(301);
        voterByPosExp();
        }
        break;
      case T__5:
        enterOuterAlt(_localctx, 2);
        {
        setState(302);
        candByPosExp();
        }
        break;
      case T__6:
        enterOuterAlt(_localctx, 3);
        {
        setState(303);
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
    enterRule(_localctx, 50, RULE_voterByPosExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(306);
      match(T__4);
      setState(307);
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
    enterRule(_localctx, 52, RULE_candByPosExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(309);
      match(T__5);
      setState(310);
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
    enterRule(_localctx, 54, RULE_seatByPosExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(312);
      match(T__6);
      setState(313);
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
    enterRule(_localctx, 56, RULE_integer);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(315);
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
    enterRule(_localctx, 58, RULE_electExp);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
      setState(317);
      match(Elect);
      setState(321);
      _errHandler.sync(this);
      _alt = getInterpreter().adaptivePredict(_input,17,_ctx);
      while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
        if ( _alt==1 ) {
          {
          {
          setState(318);
          passType();
          }
          } 
        }
        setState(323);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input,17,_ctx);
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
    enterRule(_localctx, 60, RULE_voteExp);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
      setState(324);
      match(Vote);
      setState(328);
      _errHandler.sync(this);
      _alt = getInterpreter().adaptivePredict(_input,18,_ctx);
      while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
        if ( _alt==1 ) {
          {
          {
          setState(325);
          passType();
          }
          } 
        }
        setState(330);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input,18,_ctx);
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
    enterRule(_localctx, 62, RULE_passType);
    try {
      setState(333);
      _errHandler.sync(this);
      switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
      case 1:
        enterOuterAlt(_localctx, 1);
        {
        setState(331);
        passSymbVar();
        }
        break;
      case 2:
        enterOuterAlt(_localctx, 2);
        {
        setState(332);
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
    enterRule(_localctx, 64, RULE_constantExp);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(335);
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
    enterRule(_localctx, 66, RULE_voteSumExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(337);
      match(Votesum);
      setState(338);
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
    enterRule(_localctx, 68, RULE_voteSumUniqueExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(340);
      match(VotesumUnique);
      setState(341);
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
    enterRule(_localctx, 70, RULE_passSymbVar);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(343);
      match(OpenBracket);
      setState(344);
      symbolicVarExp();
      setState(345);
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
    enterRule(_localctx, 72, RULE_passPosition);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(347);
      match(OpenBracket);
      setState(348);
      numberExpression(0);
      setState(349);
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
    enterRule(_localctx, 74, RULE_passByPos);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(351);
      match(OpenBracket);
      setState(352);
      typeByPosExp();
      setState(353);
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
    enterRule(_localctx, 76, RULE_symbolicVarExp);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(355);
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
    case 23:
      return numberExpression_sempred((NumberExpressionContext)_localctx, predIndex);
    }
    return true;
  }
  private boolean binaryRelationExp_sempred(BinaryRelationExpContext _localctx, int predIndex) {
    switch (predIndex) {
    case 0:
      return precpred(_ctx, 12);
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
    "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3#\u0168\4\2\t\2\4"+
    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
    "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\3\2\7\2R\n\2\f\2\16"+
    "\2U\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3c\n\3\3\4"+
    "\3\4\3\4\3\4\3\5\3\5\5\5k\n\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b"+
    "\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\177\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3"+
    "\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
    "\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
    "\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00ba"+
    "\n\t\3\t\3\t\3\t\7\t\u00bf\n\t\f\t\16\t\u00c2\13\t\3\n\3\n\5\n\u00c6\n"+
    "\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\5\f\u00cf\n\f\3\r\3\r\3\r\5\r\u00d4"+
    "\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
    "\5\16\u00e3\n\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21"+
    "\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\5\22\u00f8\n\22\3\23\3\23\3\23"+
    "\3\23\3\23\3\24\3\24\3\24\3\24\3\24\5\24\u0104\n\24\3\25\3\25\3\25\3\25"+
    "\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30"+
    "\5\30\u0118\n\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u0123"+
    "\n\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u012b\n\31\f\31\16\31\u012e\13"+
    "\31\3\32\3\32\3\32\5\32\u0133\n\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35"+
    "\3\35\3\35\3\36\3\36\3\37\3\37\7\37\u0142\n\37\f\37\16\37\u0145\13\37"+
    "\3 \3 \7 \u0149\n \f \16 \u014c\13 \3!\3!\5!\u0150\n!\3\"\3\"\3#\3#\3"+
    "#\3$\3$\3$\3%\3%\3%\3%\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3(\3(\3(\2\4\20\60"+
    ")\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDF"+
    "HJLN\2\3\3\2\n\f\2\u016e\2S\3\2\2\2\4b\3\2\2\2\6d\3\2\2\2\bj\3\2\2\2\n"+
    "l\3\2\2\2\fp\3\2\2\2\16~\3\2\2\2\20\u00b9\3\2\2\2\22\u00c5\3\2\2\2\24"+
    "\u00c7\3\2\2\2\26\u00ce\3\2\2\2\30\u00d3\3\2\2\2\32\u00e2\3\2\2\2\34\u00e4"+
    "\3\2\2\2\36\u00e9\3\2\2\2 \u00ee\3\2\2\2\"\u00f7\3\2\2\2$\u00f9\3\2\2"+
    "\2&\u0103\3\2\2\2(\u0105\3\2\2\2*\u010a\3\2\2\2,\u010d\3\2\2\2.\u0117"+
    "\3\2\2\2\60\u0122\3\2\2\2\62\u0132\3\2\2\2\64\u0134\3\2\2\2\66\u0137\3"+
    "\2\2\28\u013a\3\2\2\2:\u013d\3\2\2\2<\u013f\3\2\2\2>\u0146\3\2\2\2@\u014f"+
    "\3\2\2\2B\u0151\3\2\2\2D\u0153\3\2\2\2F\u0156\3\2\2\2H\u0159\3\2\2\2J"+
    "\u015d\3\2\2\2L\u0161\3\2\2\2N\u0165\3\2\2\2PR\5\4\3\2QP\3\2\2\2RU\3\2"+
    "\2\2SQ\3\2\2\2ST\3\2\2\2T\3\3\2\2\2US\3\2\2\2VW\5\16\b\2WX\7\3\2\2Xc\3"+
    "\2\2\2YZ\5\6\4\2Z[\7\3\2\2[c\3\2\2\2\\]\5\n\6\2]^\7\3\2\2^c\3\2\2\2_`"+
    "\5\f\7\2`a\7\3\2\2ac\3\2\2\2bV\3\2\2\2bY\3\2\2\2b\\\3\2\2\2b_\3\2\2\2"+
    "c\5\3\2\2\2de\7\21\2\2ef\7\33\2\2fg\5\b\5\2g\7\3\2\2\2hk\5\32\16\2ik\5"+
    "\36\20\2jh\3\2\2\2ji\3\2\2\2k\t\3\2\2\2lm\5$\23\2mn\7\33\2\2no\5\34\17"+
    "\2o\13\3\2\2\2pq\7\22\2\2qr\7\33\2\2rs\5 \21\2s\r\3\2\2\2t\177\5(\25\2"+
    "u\177\5\24\13\2v\177\5 \21\2w\177\5\20\t\2x\177\5*\26\2y\177\5,\27\2z"+
    "{\7\27\2\2{|\5\16\b\2|}\7\26\2\2}\177\3\2\2\2~t\3\2\2\2~u\3\2\2\2~v\3"+
    "\2\2\2~w\3\2\2\2~x\3\2\2\2~y\3\2\2\2~z\3\2\2\2\177\17\3\2\2\2\u0080\u0081"+
    "\b\t\1\2\u0081\u0082\5(\25\2\u0082\u0083\7\35\2\2\u0083\u0084\5\16\b\2"+
    "\u0084\u00ba\3\2\2\2\u0085\u0086\5*\26\2\u0086\u0087\7\35\2\2\u0087\u0088"+
    "\5\16\b\2\u0088\u00ba\3\2\2\2\u0089\u008a\5,\27\2\u008a\u008b\7\35\2\2"+
    "\u008b\u008c\5\16\b\2\u008c\u00ba\3\2\2\2\u008d\u008e\5\24\13\2\u008e"+
    "\u008f\7\35\2\2\u008f\u0090\5\16\b\2\u0090\u00ba\3\2\2\2\u0091\u0092\5"+
    " \21\2\u0092\u0093\7\35\2\2\u0093\u0094\5\16\b\2\u0094\u00ba\3\2\2\2\u0095"+
    "\u0096\7\27\2\2\u0096\u0097\5\20\t\2\u0097\u0098\7\26\2\2\u0098\u0099"+
    "\7\35\2\2\u0099\u009a\5\16\b\2\u009a\u00ba\3\2\2\2\u009b\u009c\7\27\2"+
    "\2\u009c\u009d\5(\25\2\u009d\u009e\7\26\2\2\u009e\u009f\7\35\2\2\u009f"+
    "\u00a0\5\16\b\2\u00a0\u00ba\3\2\2\2\u00a1\u00a2\7\27\2\2\u00a2\u00a3\5"+
    "*\26\2\u00a3\u00a4\7\26\2\2\u00a4\u00a5\7\35\2\2\u00a5\u00a6\5\16\b\2"+
    "\u00a6\u00ba\3\2\2\2\u00a7\u00a8\7\27\2\2\u00a8\u00a9\5,\27\2\u00a9\u00aa"+
    "\7\26\2\2\u00aa\u00ab\7\35\2\2\u00ab\u00ac\5\16\b\2\u00ac\u00ba\3\2\2"+
    "\2\u00ad\u00ae\7\27\2\2\u00ae\u00af\5\24\13\2\u00af\u00b0\7\26\2\2\u00b0"+
    "\u00b1\7\35\2\2\u00b1\u00b2\5\16\b\2\u00b2\u00ba\3\2\2\2\u00b3\u00b4\7"+
    "\27\2\2\u00b4\u00b5\5 \21\2\u00b5\u00b6\7\26\2\2\u00b6\u00b7\7\35\2\2"+
    "\u00b7\u00b8\5\16\b\2\u00b8\u00ba\3\2\2\2\u00b9\u0080\3\2\2\2\u00b9\u0085"+
    "\3\2\2\2\u00b9\u0089\3\2\2\2\u00b9\u008d\3\2\2\2\u00b9\u0091\3\2\2\2\u00b9"+
    "\u0095\3\2\2\2\u00b9\u009b\3\2\2\2\u00b9\u00a1\3\2\2\2\u00b9\u00a7\3\2"+
    "\2\2\u00b9\u00ad\3\2\2\2\u00b9\u00b3\3\2\2\2\u00ba\u00c0\3\2\2\2\u00bb"+
    "\u00bc\f\16\2\2\u00bc\u00bd\7\35\2\2\u00bd\u00bf\5\16\b\2\u00be\u00bb"+
    "\3\2\2\2\u00bf\u00c2\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1"+
    "\21\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c3\u00c6\5\24\13\2\u00c4\u00c6\5 \21"+
    "\2\u00c5\u00c3\3\2\2\2\u00c5\u00c4\3\2\2\2\u00c6\23\3\2\2\2\u00c7\u00c8"+
    "\7\23\2\2\u00c8\u00c9\7\27\2\2\u00c9\u00ca\5\26\f\2\u00ca\u00cb\7\26\2"+
    "\2\u00cb\25\3\2\2\2\u00cc\u00cf\7\22\2\2\u00cd\u00cf\5 \21\2\u00ce\u00cc"+
    "\3\2\2\2\u00ce\u00cd\3\2\2\2\u00cf\27\3\2\2\2\u00d0\u00d4\7\21\2\2\u00d1"+
    "\u00d4\5\36\20\2\u00d2\u00d4\5\32\16\2\u00d3\u00d0\3\2\2\2\u00d3\u00d1"+
    "\3\2\2\2\u00d3\u00d2\3\2\2\2\u00d4\31\3\2\2\2\u00d5\u00d6\7\27\2\2\u00d6"+
    "\u00d7\5\30\r\2\u00d7\u00d8\7\17\2\2\u00d8\u00d9\5\30\r\2\u00d9\u00da"+
    "\7\26\2\2\u00da\u00e3\3\2\2\2\u00db\u00dc\7\21\2\2\u00dc\u00dd\7\17\2"+
    "\2\u00dd\u00e3\5\30\r\2\u00de\u00df\5\36\20\2\u00df\u00e0\7\17\2\2\u00e0"+
    "\u00e1\5\30\r\2\u00e1\u00e3\3\2\2\2\u00e2\u00d5\3\2\2\2\u00e2\u00db\3"+
    "\2\2\2\u00e2\u00de\3\2\2\2\u00e3\33\3\2\2\2\u00e4\u00e5\7\31\2\2\u00e5"+
    "\u00e6\7\27\2\2\u00e6\u00e7\5\30\r\2\u00e7\u00e8\7\26\2\2\u00e8\35\3\2"+
    "\2\2\u00e9\u00ea\7\32\2\2\u00ea\u00eb\7\27\2\2\u00eb\u00ec\5\30\r\2\u00ec"+
    "\u00ed\7\26\2\2\u00ed\37\3\2\2\2\u00ee\u00ef\7\20\2\2\u00ef\u00f0\7\27"+
    "\2\2\u00f0\u00f1\5\"\22\2\u00f1\u00f2\7\4\2\2\u00f2\u00f3\5\"\22\2\u00f3"+
    "\u00f4\7\26\2\2\u00f4!\3\2\2\2\u00f5\u00f8\7\22\2\2\u00f6\u00f8\5 \21"+
    "\2\u00f7\u00f5\3\2\2\2\u00f7\u00f6\3\2\2\2\u00f8#\3\2\2\2\u00f9\u00fa"+
    "\7\27\2\2\u00fa\u00fb\7\21\2\2\u00fb\u00fc\5&\24\2\u00fc\u00fd\7\26\2"+
    "\2\u00fd%\3\2\2\2\u00fe\u00ff\7\4\2\2\u00ff\u0104\7\21\2\2\u0100\u0101"+
    "\7\4\2\2\u0101\u0102\7\21\2\2\u0102\u0104\5&\24\2\u0103\u00fe\3\2\2\2"+
    "\u0103\u0100\3\2\2\2\u0104\'\3\2\2\2\u0105\u0106\7\30\2\2\u0106\u0107"+
    "\5H%\2\u0107\u0108\7\5\2\2\u0108\u0109\5\16\b\2\u0109)\3\2\2\2\u010a\u010b"+
    "\7\6\2\2\u010b\u010c\5\16\b\2\u010c+\3\2\2\2\u010d\u010e\5.\30\2\u010e"+
    "\u010f\7\34\2\2\u010f\u0110\5.\30\2\u0110-\3\2\2\2\u0111\u0118\5<\37\2"+
    "\u0112\u0118\5> \2\u0113\u0118\5\60\31\2\u0114\u0118\5N(\2\u0115\u0118"+
    "\5\62\32\2\u0116\u0118\5 \21\2\u0117\u0111\3\2\2\2\u0117\u0112\3\2\2\2"+
    "\u0117\u0113\3\2\2\2\u0117\u0114\3\2\2\2\u0117\u0115\3\2\2\2\u0117\u0116"+
    "\3\2\2\2\u0118/\3\2\2\2\u0119\u011a\b\31\1\2\u011a\u011b\7\27\2\2\u011b"+
    "\u011c\5\60\31\2\u011c\u011d\7\26\2\2\u011d\u0123\3\2\2\2\u011e\u0123"+
    "\5D#\2\u011f\u0123\5F$\2\u0120\u0123\5B\"\2\u0121\u0123\5:\36\2\u0122"+
    "\u0119\3\2\2\2\u0122\u011e\3\2\2\2\u0122\u011f\3\2\2\2\u0122\u0120\3\2"+
    "\2\2\u0122\u0121\3\2\2\2\u0123\u012c\3\2\2\2\u0124\u0125\f\b\2\2\u0125"+
    "\u0126\7\r\2\2\u0126\u012b\5\60\31\t\u0127\u0128\f\7\2\2\u0128\u0129\7"+
    "\16\2\2\u0129\u012b\5\60\31\b\u012a\u0124\3\2\2\2\u012a\u0127\3\2\2\2"+
    "\u012b\u012e\3\2\2\2\u012c\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d\61"+
    "\3\2\2\2\u012e\u012c\3\2\2\2\u012f\u0133\5\64\33\2\u0130\u0133\5\66\34"+
    "\2\u0131\u0133\58\35\2\u0132\u012f\3\2\2\2\u0132\u0130\3\2\2\2\u0132\u0131"+
    "\3\2\2\2\u0133\63\3\2\2\2\u0134\u0135\7\7\2\2\u0135\u0136\5J&\2\u0136"+
    "\65\3\2\2\2\u0137\u0138\7\b\2\2\u0138\u0139\5J&\2\u0139\67\3\2\2\2\u013a"+
    "\u013b\7\t\2\2\u013b\u013c\5J&\2\u013c9\3\2\2\2\u013d\u013e\7\36\2\2\u013e"+
    ";\3\2\2\2\u013f\u0143\7\22\2\2\u0140\u0142\5@!\2\u0141\u0140\3\2\2\2\u0142"+
    "\u0145\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144=\3\2\2\2"+
    "\u0145\u0143\3\2\2\2\u0146\u014a\7\21\2\2\u0147\u0149\5@!\2\u0148\u0147"+
    "\3\2\2\2\u0149\u014c\3\2\2\2\u014a\u0148\3\2\2\2\u014a\u014b\3\2\2\2\u014b"+
    "?\3\2\2\2\u014c\u014a\3\2\2\2\u014d\u0150\5H%\2\u014e\u0150\5L\'\2\u014f"+
    "\u014d\3\2\2\2\u014f\u014e\3\2\2\2\u0150A\3\2\2\2\u0151\u0152\t\2\2\2"+
    "\u0152C\3\2\2\2\u0153\u0154\7\24\2\2\u0154\u0155\5@!\2\u0155E\3\2\2\2"+
    "\u0156\u0157\7\25\2\2\u0157\u0158\5@!\2\u0158G\3\2\2\2\u0159\u015a\7\27"+
    "\2\2\u015a\u015b\5N(\2\u015b\u015c\7\26\2\2\u015cI\3\2\2\2\u015d\u015e"+
    "\7\27\2\2\u015e\u015f\5\60\31\2\u015f\u0160\7\26\2\2\u0160K\3\2\2\2\u0161"+
    "\u0162\7\27\2\2\u0162\u0163\5\62\32\2\u0163\u0164\7\26\2\2\u0164M\3\2"+
    "\2\2\u0165\u0166\7\37\2\2\u0166O\3\2\2\2\26Sbj~\u00b9\u00c0\u00c5\u00ce"+
    "\u00d3\u00e2\u00f7\u0103\u0117\u0122\u012a\u012c\u0132\u0143\u014a\u014f";
  public static final ATN _ATN =
    new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}