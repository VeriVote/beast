package edu.pse.beast.toolbox.antlr.booleanexp;
// Generated from BooleanExpression.g by ANTLR 4.6
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BooleanExpressionLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"FOR_ALL_VOTERS", "FOR_ALL_CANDIDATES", "FOR_ALL_SEATS", "EXISTS_ONE_VOTER", 
		"EXISTS_ONE_CANDIDATE", "EXISTS_ONE_SEAT", "SUM_VOTES_FOR_CANDIDATE", 
		"VOTES", "ELECT", "SYMBOLIC_VARIABLE", "VOTERS_CONSTANT", "CANDIDATES_CONSTANT", 
		"SEATS_CONSTANT", "LOGICAL_OR", "LOGICAL_AND", "LOGICAL_IMPLICATION", 
		"LOGICAL_EQUALITY", "RELATIONAL_EQUALITY", "RELATIONAL_INEQUALITY", "RELATIONAL_LESS", 
		"RELATIONAL_LESSOREQUAL", "RELATIONAL_GREATER", "RELATIONAL_GREATEROREQUAL", 
		"POSITIVE_INTEGER", "CLOSING_BRACKET", "OPEN_BRACKET", "COLON", "WHITESPACE"
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


	public BooleanExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BooleanExpression.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\36\u0108\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\6\13\u00ce\n\13\r\13\16\13\u00cf\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30"+
		"\3\30\3\31\6\31\u00f8\n\31\r\31\16\31\u00f9\3\32\3\32\3\33\3\33\3\34\3"+
		"\34\3\35\6\35\u0103\n\35\r\35\16\35\u0104\3\35\3\35\2\2\36\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36\3\2\5\3\2c|\3\2"+
		"\62;\5\2\13\f\16\17\"\"\u010a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\3;\3\2\2\2\5K\3\2\2\2\7_\3\2\2\2\tn\3\2\2\2\13"+
		"\u0080\3\2\2\2\r\u0096\3\2\2\2\17\u00a7\3\2\2\2\21\u00c0\3\2\2\2\23\u00c6"+
		"\3\2\2\2\25\u00cd\3\2\2\2\27\u00d1\3\2\2\2\31\u00d3\3\2\2\2\33\u00d5\3"+
		"\2\2\2\35\u00d7\3\2\2\2\37\u00da\3\2\2\2!\u00dd\3\2\2\2#\u00e1\3\2\2\2"+
		"%\u00e6\3\2\2\2\'\u00e9\3\2\2\2)\u00ec\3\2\2\2+\u00ee\3\2\2\2-\u00f1\3"+
		"\2\2\2/\u00f3\3\2\2\2\61\u00f7\3\2\2\2\63\u00fb\3\2\2\2\65\u00fd\3\2\2"+
		"\2\67\u00ff\3\2\2\29\u0102\3\2\2\2;<\7H\2\2<=\7Q\2\2=>\7T\2\2>?\7a\2\2"+
		"?@\7C\2\2@A\7N\2\2AB\7N\2\2BC\7a\2\2CD\7X\2\2DE\7Q\2\2EF\7V\2\2FG\7G\2"+
		"\2GH\7T\2\2HI\7U\2\2IJ\7*\2\2J\4\3\2\2\2KL\7H\2\2LM\7Q\2\2MN\7T\2\2NO"+
		"\7a\2\2OP\7C\2\2PQ\7N\2\2QR\7N\2\2RS\7a\2\2ST\7E\2\2TU\7C\2\2UV\7P\2\2"+
		"VW\7F\2\2WX\7K\2\2XY\7F\2\2YZ\7C\2\2Z[\7V\2\2[\\\7G\2\2\\]\7U\2\2]^\7"+
		"*\2\2^\6\3\2\2\2_`\7H\2\2`a\7Q\2\2ab\7T\2\2bc\7a\2\2cd\7C\2\2de\7N\2\2"+
		"ef\7N\2\2fg\7a\2\2gh\7U\2\2hi\7G\2\2ij\7C\2\2jk\7V\2\2kl\7U\2\2lm\7*\2"+
		"\2m\b\3\2\2\2no\7G\2\2op\7Z\2\2pq\7K\2\2qr\7U\2\2rs\7V\2\2st\7U\2\2tu"+
		"\7a\2\2uv\7Q\2\2vw\7P\2\2wx\7G\2\2xy\7a\2\2yz\7X\2\2z{\7Q\2\2{|\7V\2\2"+
		"|}\7G\2\2}~\7T\2\2~\177\7*\2\2\177\n\3\2\2\2\u0080\u0081\7G\2\2\u0081"+
		"\u0082\7Z\2\2\u0082\u0083\7K\2\2\u0083\u0084\7U\2\2\u0084\u0085\7V\2\2"+
		"\u0085\u0086\7U\2\2\u0086\u0087\7a\2\2\u0087\u0088\7Q\2\2\u0088\u0089"+
		"\7P\2\2\u0089\u008a\7G\2\2\u008a\u008b\7a\2\2\u008b\u008c\7E\2\2\u008c"+
		"\u008d\7C\2\2\u008d\u008e\7P\2\2\u008e\u008f\7F\2\2\u008f\u0090\7K\2\2"+
		"\u0090\u0091\7F\2\2\u0091\u0092\7C\2\2\u0092\u0093\7V\2\2\u0093\u0094"+
		"\7G\2\2\u0094\u0095\7*\2\2\u0095\f\3\2\2\2\u0096\u0097\7G\2\2\u0097\u0098"+
		"\7Z\2\2\u0098\u0099\7K\2\2\u0099\u009a\7U\2\2\u009a\u009b\7V\2\2\u009b"+
		"\u009c\7U\2\2\u009c\u009d\7a\2\2\u009d\u009e\7Q\2\2\u009e\u009f\7P\2\2"+
		"\u009f\u00a0\7G\2\2\u00a0\u00a1\7a\2\2\u00a1\u00a2\7U\2\2\u00a2\u00a3"+
		"\7G\2\2\u00a3\u00a4\7C\2\2\u00a4\u00a5\7V\2\2\u00a5\u00a6\7*\2\2\u00a6"+
		"\16\3\2\2\2\u00a7\u00a8\7U\2\2\u00a8\u00a9\7W\2\2\u00a9\u00aa\7O\2\2\u00aa"+
		"\u00ab\7a\2\2\u00ab\u00ac\7X\2\2\u00ac\u00ad\7Q\2\2\u00ad\u00ae\7V\2\2"+
		"\u00ae\u00af\7G\2\2\u00af\u00b0\7U\2\2\u00b0\u00b1\7a\2\2\u00b1\u00b2"+
		"\7H\2\2\u00b2\u00b3\7Q\2\2\u00b3\u00b4\7T\2\2\u00b4\u00b5\7a\2\2\u00b5"+
		"\u00b6\7E\2\2\u00b6\u00b7\7C\2\2\u00b7\u00b8\7P\2\2\u00b8\u00b9\7F\2\2"+
		"\u00b9\u00ba\7K\2\2\u00ba\u00bb\7F\2\2\u00bb\u00bc\7C\2\2\u00bc\u00bd"+
		"\7V\2\2\u00bd\u00be\7G\2\2\u00be\u00bf\7*\2\2\u00bf\20\3\2\2\2\u00c0\u00c1"+
		"\7X\2\2\u00c1\u00c2\7Q\2\2\u00c2\u00c3\7V\2\2\u00c3\u00c4\7G\2\2\u00c4"+
		"\u00c5\7U\2\2\u00c5\22\3\2\2\2\u00c6\u00c7\7G\2\2\u00c7\u00c8\7N\2\2\u00c8"+
		"\u00c9\7G\2\2\u00c9\u00ca\7E\2\2\u00ca\u00cb\7V\2\2\u00cb\24\3\2\2\2\u00cc"+
		"\u00ce\t\2\2\2\u00cd\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00cd\3\2"+
		"\2\2\u00cf\u00d0\3\2\2\2\u00d0\26\3\2\2\2\u00d1\u00d2\7X\2\2\u00d2\30"+
		"\3\2\2\2\u00d3\u00d4\7E\2\2\u00d4\32\3\2\2\2\u00d5\u00d6\7U\2\2\u00d6"+
		"\34\3\2\2\2\u00d7\u00d8\7~\2\2\u00d8\u00d9\7~\2\2\u00d9\36\3\2\2\2\u00da"+
		"\u00db\7(\2\2\u00db\u00dc\7(\2\2\u00dc \3\2\2\2\u00dd\u00de\7?\2\2\u00de"+
		"\u00df\7?\2\2\u00df\u00e0\7@\2\2\u00e0\"\3\2\2\2\u00e1\u00e2\7>\2\2\u00e2"+
		"\u00e3\7?\2\2\u00e3\u00e4\7?\2\2\u00e4\u00e5\7@\2\2\u00e5$\3\2\2\2\u00e6"+
		"\u00e7\7?\2\2\u00e7\u00e8\7?\2\2\u00e8&\3\2\2\2\u00e9\u00ea\7#\2\2\u00ea"+
		"\u00eb\7?\2\2\u00eb(\3\2\2\2\u00ec\u00ed\7>\2\2\u00ed*\3\2\2\2\u00ee\u00ef"+
		"\7>\2\2\u00ef\u00f0\7?\2\2\u00f0,\3\2\2\2\u00f1\u00f2\7@\2\2\u00f2.\3"+
		"\2\2\2\u00f3\u00f4\7@\2\2\u00f4\u00f5\7?\2\2\u00f5\60\3\2\2\2\u00f6\u00f8"+
		"\t\3\2\2\u00f7\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9"+
		"\u00fa\3\2\2\2\u00fa\62\3\2\2\2\u00fb\u00fc\7+\2\2\u00fc\64\3\2\2\2\u00fd"+
		"\u00fe\7+\2\2\u00fe\66\3\2\2\2\u00ff\u0100\7<\2\2\u01008\3\2\2\2\u0101"+
		"\u0103\t\4\2\2\u0102\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0102\3\2"+
		"\2\2\u0104\u0105\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0107\b\35\2\2\u0107"+
		":\3\2\2\2\6\2\u00cf\u00f9\u0104\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}