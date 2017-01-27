package edu.pse.beast.toolbox.antlr.booleanexp;

// Generated from D:\studium\PSE\Implementierung\impl rep 01_23\PSE-Wahlverfahren-Implementierung\beast\src\main\resources\antlrgrammars\FormalPropertyDescription.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FormalPropertyDescriptionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, Vote=8, Elect=9, 
		ClosedBracket=10, OpenBracket=11, Quantor=12, ComparisonSymbol=13, BinaryRelationSymbol=14, 
		Integer=15, Identifier=16, Whitespace=17, Newline=18, BlockComment=19, 
		LineComment=20;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "Vote", "Elect", 
		"ClosedBracket", "OpenBracket", "Quantor", "ComparisonSymbol", "BinaryRelationSymbol", 
		"Integer", "Identifier", "IdentifierNondigit", "Nondigit", "Digit", "UniversalCharacterName", 
		"HexQuad", "HexadecimalDigit", "Whitespace", "Newline", "BlockComment", 
		"LineComment"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "'!'", "'V'", "'C'", "'S'", "'VOTE_SUM_FOR_CANDIDATE'", 
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


	public FormalPropertyDescriptionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FormalPropertyDescription.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\26\u0139\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7"+
		"\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\5\r\u00cf\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00da"+
		"\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00e7"+
		"\n\17\3\20\6\20\u00ea\n\20\r\20\16\20\u00eb\3\21\3\21\3\21\7\21\u00f1"+
		"\n\21\f\21\16\21\u00f4\13\21\3\22\3\22\5\22\u00f8\n\22\3\23\3\23\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u0108\n\25"+
		"\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\30\6\30\u0112\n\30\r\30\16\30\u0113"+
		"\3\30\3\30\3\31\3\31\5\31\u011a\n\31\3\31\5\31\u011d\n\31\3\31\3\31\3"+
		"\32\3\32\3\32\3\32\7\32\u0125\n\32\f\32\16\32\u0128\13\32\3\32\3\32\3"+
		"\32\3\32\3\32\3\33\3\33\3\33\3\33\7\33\u0133\n\33\f\33\16\33\u0136\13"+
		"\33\3\33\3\33\3\u0126\2\34\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\2%\2\'\2)\2+\2-\2/\23\61\24\63\25"+
		"\65\26\3\2\b\4\2>>@@\5\2C\\aac|\3\2\62;\5\2\62;CHch\4\2\13\13\"\"\4\2"+
		"\f\f\17\17\u0148\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\3\67\3\2\2"+
		"\2\59\3\2\2\2\7;\3\2\2\2\t=\3\2\2\2\13?\3\2\2\2\rA\3\2\2\2\17C\3\2\2\2"+
		"\21Z\3\2\2\2\23b\3\2\2\2\25j\3\2\2\2\27l\3\2\2\2\31\u00ce\3\2\2\2\33\u00d9"+
		"\3\2\2\2\35\u00e6\3\2\2\2\37\u00e9\3\2\2\2!\u00ed\3\2\2\2#\u00f7\3\2\2"+
		"\2%\u00f9\3\2\2\2\'\u00fb\3\2\2\2)\u0107\3\2\2\2+\u0109\3\2\2\2-\u010e"+
		"\3\2\2\2/\u0111\3\2\2\2\61\u011c\3\2\2\2\63\u0120\3\2\2\2\65\u012e\3\2"+
		"\2\2\678\7=\2\28\4\3\2\2\29:\7<\2\2:\6\3\2\2\2;<\7#\2\2<\b\3\2\2\2=>\7"+
		"X\2\2>\n\3\2\2\2?@\7E\2\2@\f\3\2\2\2AB\7U\2\2B\16\3\2\2\2CD\7X\2\2DE\7"+
		"Q\2\2EF\7V\2\2FG\7G\2\2GH\7a\2\2HI\7U\2\2IJ\7W\2\2JK\7O\2\2KL\7a\2\2L"+
		"M\7H\2\2MN\7Q\2\2NO\7T\2\2OP\7a\2\2PQ\7E\2\2QR\7C\2\2RS\7P\2\2ST\7F\2"+
		"\2TU\7K\2\2UV\7F\2\2VW\7C\2\2WX\7V\2\2XY\7G\2\2Y\20\3\2\2\2Z[\7X\2\2["+
		"\\\7Q\2\2\\]\7V\2\2]^\7G\2\2^_\7U\2\2_`\3\2\2\2`a\5\37\20\2a\22\3\2\2"+
		"\2bc\7G\2\2cd\7N\2\2de\7G\2\2ef\7E\2\2fg\7V\2\2gh\3\2\2\2hi\5\37\20\2"+
		"i\24\3\2\2\2jk\7+\2\2k\26\3\2\2\2lm\7*\2\2m\30\3\2\2\2no\7H\2\2op\7Q\2"+
		"\2pq\7T\2\2qr\7a\2\2rs\7C\2\2st\7N\2\2tu\7N\2\2uv\7a\2\2vw\7X\2\2wx\7"+
		"Q\2\2xy\7V\2\2yz\7G\2\2z{\7T\2\2{\u00cf\7U\2\2|}\7H\2\2}~\7Q\2\2~\177"+
		"\7T\2\2\177\u0080\7a\2\2\u0080\u0081\7C\2\2\u0081\u0082\7N\2\2\u0082\u0083"+
		"\7N\2\2\u0083\u0084\7a\2\2\u0084\u0085\7E\2\2\u0085\u0086\7C\2\2\u0086"+
		"\u0087\7P\2\2\u0087\u0088\7F\2\2\u0088\u0089\7K\2\2\u0089\u008a\7F\2\2"+
		"\u008a\u008b\7C\2\2\u008b\u008c\7V\2\2\u008c\u008d\7G\2\2\u008d\u00cf"+
		"\7U\2\2\u008e\u008f\7H\2\2\u008f\u0090\7Q\2\2\u0090\u0091\7T\2\2\u0091"+
		"\u0092\7a\2\2\u0092\u0093\7C\2\2\u0093\u0094\7N\2\2\u0094\u0095\7N\2\2"+
		"\u0095\u0096\7a\2\2\u0096\u0097\7U\2\2\u0097\u0098\7G\2\2\u0098\u0099"+
		"\7C\2\2\u0099\u009a\7V\2\2\u009a\u00cf\7U\2\2\u009b\u009c\7G\2\2\u009c"+
		"\u009d\7Z\2\2\u009d\u009e\7K\2\2\u009e\u009f\7U\2\2\u009f\u00a0\7V\2\2"+
		"\u00a0\u00a1\7U\2\2\u00a1\u00a2\7a\2\2\u00a2\u00a3\7Q\2\2\u00a3\u00a4"+
		"\7P\2\2\u00a4\u00a5\7G\2\2\u00a5\u00a6\7a\2\2\u00a6\u00a7\7X\2\2\u00a7"+
		"\u00a8\7Q\2\2\u00a8\u00a9\7V\2\2\u00a9\u00aa\7G\2\2\u00aa\u00cf\7T\2\2"+
		"\u00ab\u00ac\7G\2\2\u00ac\u00ad\7Z\2\2\u00ad\u00ae\7K\2\2\u00ae\u00af"+
		"\7U\2\2\u00af\u00b0\7V\2\2\u00b0\u00b1\7U\2\2\u00b1\u00b2\7a\2\2\u00b2"+
		"\u00b3\7Q\2\2\u00b3\u00b4\7P\2\2\u00b4\u00b5\7G\2\2\u00b5\u00b6\7a\2\2"+
		"\u00b6\u00b7\7E\2\2\u00b7\u00b8\7C\2\2\u00b8\u00b9\7P\2\2\u00b9\u00ba"+
		"\7F\2\2\u00ba\u00bb\7K\2\2\u00bb\u00bc\7F\2\2\u00bc\u00bd\7C\2\2\u00bd"+
		"\u00be\7V\2\2\u00be\u00cf\7G\2\2\u00bf\u00c0\7G\2\2\u00c0\u00c1\7Z\2\2"+
		"\u00c1\u00c2\7K\2\2\u00c2\u00c3\7U\2\2\u00c3\u00c4\7V\2\2\u00c4\u00c5"+
		"\7U\2\2\u00c5\u00c6\7a\2\2\u00c6\u00c7\7Q\2\2\u00c7\u00c8\7P\2\2\u00c8"+
		"\u00c9\7G\2\2\u00c9\u00ca\7a\2\2\u00ca\u00cb\7U\2\2\u00cb\u00cc\7G\2\2"+
		"\u00cc\u00cd\7C\2\2\u00cd\u00cf\7V\2\2\u00cen\3\2\2\2\u00ce|\3\2\2\2\u00ce"+
		"\u008e\3\2\2\2\u00ce\u009b\3\2\2\2\u00ce\u00ab\3\2\2\2\u00ce\u00bf\3\2"+
		"\2\2\u00cf\32\3\2\2\2\u00d0\u00d1\7?\2\2\u00d1\u00da\7?\2\2\u00d2\u00d3"+
		"\7#\2\2\u00d3\u00da\7?\2\2\u00d4\u00d5\7>\2\2\u00d5\u00da\7?\2\2\u00d6"+
		"\u00d7\7@\2\2\u00d7\u00da\7?\2\2\u00d8\u00da\t\2\2\2\u00d9\u00d0\3\2\2"+
		"\2\u00d9\u00d2\3\2\2\2\u00d9\u00d4\3\2\2\2\u00d9\u00d6\3\2\2\2\u00d9\u00d8"+
		"\3\2\2\2\u00da\34\3\2\2\2\u00db\u00dc\7(\2\2\u00dc\u00e7\7(\2\2\u00dd"+
		"\u00de\7~\2\2\u00de\u00e7\7~\2\2\u00df\u00e0\7?\2\2\u00e0\u00e1\7?\2\2"+
		"\u00e1\u00e7\7@\2\2\u00e2\u00e3\7>\2\2\u00e3\u00e4\7?\2\2\u00e4\u00e5"+
		"\7?\2\2\u00e5\u00e7\7@\2\2\u00e6\u00db\3\2\2\2\u00e6\u00dd\3\2\2\2\u00e6"+
		"\u00df\3\2\2\2\u00e6\u00e2\3\2\2\2\u00e7\36\3\2\2\2\u00e8\u00ea\5\'\24"+
		"\2\u00e9\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec"+
		"\3\2\2\2\u00ec \3\2\2\2\u00ed\u00f2\5#\22\2\u00ee\u00f1\5#\22\2\u00ef"+
		"\u00f1\5\'\24\2\u00f0\u00ee\3\2\2\2\u00f0\u00ef\3\2\2\2\u00f1\u00f4\3"+
		"\2\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\"\3\2\2\2\u00f4\u00f2"+
		"\3\2\2\2\u00f5\u00f8\5%\23\2\u00f6\u00f8\5)\25\2\u00f7\u00f5\3\2\2\2\u00f7"+
		"\u00f6\3\2\2\2\u00f8$\3\2\2\2\u00f9\u00fa\t\3\2\2\u00fa&\3\2\2\2\u00fb"+
		"\u00fc\t\4\2\2\u00fc(\3\2\2\2\u00fd\u00fe\7^\2\2\u00fe\u00ff\7w\2\2\u00ff"+
		"\u0100\3\2\2\2\u0100\u0108\5+\26\2\u0101\u0102\7^\2\2\u0102\u0103\7W\2"+
		"\2\u0103\u0104\3\2\2\2\u0104\u0105\5+\26\2\u0105\u0106\5+\26\2\u0106\u0108"+
		"\3\2\2\2\u0107\u00fd\3\2\2\2\u0107\u0101\3\2\2\2\u0108*\3\2\2\2\u0109"+
		"\u010a\5-\27\2\u010a\u010b\5-\27\2\u010b\u010c\5-\27\2\u010c\u010d\5-"+
		"\27\2\u010d,\3\2\2\2\u010e\u010f\t\5\2\2\u010f.\3\2\2\2\u0110\u0112\t"+
		"\6\2\2\u0111\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0111\3\2\2\2\u0113"+
		"\u0114\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0116\b\30\2\2\u0116\60\3\2\2"+
		"\2\u0117\u0119\7\17\2\2\u0118\u011a\7\f\2\2\u0119\u0118\3\2\2\2\u0119"+
		"\u011a\3\2\2\2\u011a\u011d\3\2\2\2\u011b\u011d\7\f\2\2\u011c\u0117\3\2"+
		"\2\2\u011c\u011b\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011f\b\31\2\2\u011f"+
		"\62\3\2\2\2\u0120\u0121\7\61\2\2\u0121\u0122\7,\2\2\u0122\u0126\3\2\2"+
		"\2\u0123\u0125\13\2\2\2\u0124\u0123\3\2\2\2\u0125\u0128\3\2\2\2\u0126"+
		"\u0127\3\2\2\2\u0126\u0124\3\2\2\2\u0127\u0129\3\2\2\2\u0128\u0126\3\2"+
		"\2\2\u0129\u012a\7,\2\2\u012a\u012b\7\61\2\2\u012b\u012c\3\2\2\2\u012c"+
		"\u012d\b\32\2\2\u012d\64\3\2\2\2\u012e\u012f\7\61\2\2\u012f\u0130\7\61"+
		"\2\2\u0130\u0134\3\2\2\2\u0131\u0133\n\7\2\2\u0132\u0131\3\2\2\2\u0133"+
		"\u0136\3\2\2\2\u0134\u0132\3\2\2\2\u0134\u0135\3\2\2\2\u0135\u0137\3\2"+
		"\2\2\u0136\u0134\3\2\2\2\u0137\u0138\b\33\2\2\u0138\66\3\2\2\2\20\2\u00ce"+
		"\u00d9\u00e6\u00eb\u00f0\u00f2\u00f7\u0107\u0113\u0119\u011c\u0126\u0134"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}