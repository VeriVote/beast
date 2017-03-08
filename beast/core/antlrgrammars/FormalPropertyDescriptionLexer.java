// Generated from FormalPropertyDescription.g4 by ANTLR 4.5.1
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
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, Mult=7, Add=8, Vote=9, 
		Elect=10, Votesum=11, ClosedBracket=12, OpenBracket=13, Quantor=14, ComparisonSymbol=15, 
		BinaryRelationSymbol=16, Integer=17, Identifier=18, Whitespace=19, Newline=20, 
		BlockComment=21, LineComment=22;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "Mult", "Add", "Vote", 
		"Elect", "Votesum", "ClosedBracket", "OpenBracket", "Quantor", "ComparisonSymbol", 
		"BinaryRelationSymbol", "Integer", "Identifier", "IdentifierNondigit", 
		"Nondigit", "Digit", "UniversalCharacterName", "HexQuad", "HexadecimalDigit", 
		"Whitespace", "Newline", "BlockComment", "LineComment"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "'!'", "'V'", "'C'", "'S'", null, null, null, null, 
		null, "')'", "'('"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "Mult", "Add", "Vote", "Elect", 
		"Votesum", "ClosedBracket", "OpenBracket", "Quantor", "ComparisonSymbol", 
		"BinaryRelationSymbol", "Integer", "Identifier", "Whitespace", "Newline", 
		"BlockComment", "LineComment"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\30\u0143\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\5\17\u00d9\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\5\20\u00e4\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\5\21\u00f1\n\21\3\22\6\22\u00f4\n\22\r\22\16\22\u00f5\3\23\3\23\3\23"+
		"\7\23\u00fb\n\23\f\23\16\23\u00fe\13\23\3\24\3\24\5\24\u0102\n\24\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27"+
		"\u0112\n\27\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\32\6\32\u011c\n\32\r"+
		"\32\16\32\u011d\3\32\3\32\3\33\3\33\5\33\u0124\n\33\3\33\5\33\u0127\n"+
		"\33\3\33\3\33\3\34\3\34\3\34\3\34\7\34\u012f\n\34\f\34\16\34\u0132\13"+
		"\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\7\35\u013d\n\35\f\35"+
		"\16\35\u0140\13\35\3\35\3\35\3\u0130\2\36\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\2)\2+\2"+
		"-\2/\2\61\2\63\25\65\26\67\279\30\3\2\n\4\2,,\61\61\4\2--//\4\2>>@@\5"+
		"\2C\\aac|\3\2\62;\5\2\62;CHch\4\2\13\13\"\"\4\2\f\f\17\17\u0152\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		"%\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\3;\3\2\2\2"+
		"\5=\3\2\2\2\7?\3\2\2\2\tA\3\2\2\2\13C\3\2\2\2\rE\3\2\2\2\17G\3\2\2\2\21"+
		"I\3\2\2\2\23K\3\2\2\2\25S\3\2\2\2\27[\3\2\2\2\31t\3\2\2\2\33v\3\2\2\2"+
		"\35\u00d8\3\2\2\2\37\u00e3\3\2\2\2!\u00f0\3\2\2\2#\u00f3\3\2\2\2%\u00f7"+
		"\3\2\2\2\'\u0101\3\2\2\2)\u0103\3\2\2\2+\u0105\3\2\2\2-\u0111\3\2\2\2"+
		"/\u0113\3\2\2\2\61\u0118\3\2\2\2\63\u011b\3\2\2\2\65\u0126\3\2\2\2\67"+
		"\u012a\3\2\2\29\u0138\3\2\2\2;<\7=\2\2<\4\3\2\2\2=>\7<\2\2>\6\3\2\2\2"+
		"?@\7#\2\2@\b\3\2\2\2AB\7X\2\2B\n\3\2\2\2CD\7E\2\2D\f\3\2\2\2EF\7U\2\2"+
		"F\16\3\2\2\2GH\t\2\2\2H\20\3\2\2\2IJ\t\3\2\2J\22\3\2\2\2KL\7X\2\2LM\7"+
		"Q\2\2MN\7V\2\2NO\7G\2\2OP\7U\2\2PQ\3\2\2\2QR\5#\22\2R\24\3\2\2\2ST\7G"+
		"\2\2TU\7N\2\2UV\7G\2\2VW\7E\2\2WX\7V\2\2XY\3\2\2\2YZ\5#\22\2Z\26\3\2\2"+
		"\2[\\\7X\2\2\\]\7Q\2\2]^\7V\2\2^_\7G\2\2_`\7a\2\2`a\7U\2\2ab\7W\2\2bc"+
		"\7O\2\2cd\7a\2\2de\7H\2\2ef\7Q\2\2fg\7T\2\2gh\7a\2\2hi\7E\2\2ij\7C\2\2"+
		"jk\7P\2\2kl\7F\2\2lm\7K\2\2mn\7F\2\2no\7C\2\2op\7V\2\2pq\7G\2\2qr\3\2"+
		"\2\2rs\5#\22\2s\30\3\2\2\2tu\7+\2\2u\32\3\2\2\2vw\7*\2\2w\34\3\2\2\2x"+
		"y\7H\2\2yz\7Q\2\2z{\7T\2\2{|\7a\2\2|}\7C\2\2}~\7N\2\2~\177\7N\2\2\177"+
		"\u0080\7a\2\2\u0080\u0081\7X\2\2\u0081\u0082\7Q\2\2\u0082\u0083\7V\2\2"+
		"\u0083\u0084\7G\2\2\u0084\u0085\7T\2\2\u0085\u00d9\7U\2\2\u0086\u0087"+
		"\7H\2\2\u0087\u0088\7Q\2\2\u0088\u0089\7T\2\2\u0089\u008a\7a\2\2\u008a"+
		"\u008b\7C\2\2\u008b\u008c\7N\2\2\u008c\u008d\7N\2\2\u008d\u008e\7a\2\2"+
		"\u008e\u008f\7E\2\2\u008f\u0090\7C\2\2\u0090\u0091\7P\2\2\u0091\u0092"+
		"\7F\2\2\u0092\u0093\7K\2\2\u0093\u0094\7F\2\2\u0094\u0095\7C\2\2\u0095"+
		"\u0096\7V\2\2\u0096\u0097\7G\2\2\u0097\u00d9\7U\2\2\u0098\u0099\7H\2\2"+
		"\u0099\u009a\7Q\2\2\u009a\u009b\7T\2\2\u009b\u009c\7a\2\2\u009c\u009d"+
		"\7C\2\2\u009d\u009e\7N\2\2\u009e\u009f\7N\2\2\u009f\u00a0\7a\2\2\u00a0"+
		"\u00a1\7U\2\2\u00a1\u00a2\7G\2\2\u00a2\u00a3\7C\2\2\u00a3\u00a4\7V\2\2"+
		"\u00a4\u00d9\7U\2\2\u00a5\u00a6\7G\2\2\u00a6\u00a7\7Z\2\2\u00a7\u00a8"+
		"\7K\2\2\u00a8\u00a9\7U\2\2\u00a9\u00aa\7V\2\2\u00aa\u00ab\7U\2\2\u00ab"+
		"\u00ac\7a\2\2\u00ac\u00ad\7Q\2\2\u00ad\u00ae\7P\2\2\u00ae\u00af\7G\2\2"+
		"\u00af\u00b0\7a\2\2\u00b0\u00b1\7X\2\2\u00b1\u00b2\7Q\2\2\u00b2\u00b3"+
		"\7V\2\2\u00b3\u00b4\7G\2\2\u00b4\u00d9\7T\2\2\u00b5\u00b6\7G\2\2\u00b6"+
		"\u00b7\7Z\2\2\u00b7\u00b8\7K\2\2\u00b8\u00b9\7U\2\2\u00b9\u00ba\7V\2\2"+
		"\u00ba\u00bb\7U\2\2\u00bb\u00bc\7a\2\2\u00bc\u00bd\7Q\2\2\u00bd\u00be"+
		"\7P\2\2\u00be\u00bf\7G\2\2\u00bf\u00c0\7a\2\2\u00c0\u00c1\7E\2\2\u00c1"+
		"\u00c2\7C\2\2\u00c2\u00c3\7P\2\2\u00c3\u00c4\7F\2\2\u00c4\u00c5\7K\2\2"+
		"\u00c5\u00c6\7F\2\2\u00c6\u00c7\7C\2\2\u00c7\u00c8\7V\2\2\u00c8\u00d9"+
		"\7G\2\2\u00c9\u00ca\7G\2\2\u00ca\u00cb\7Z\2\2\u00cb\u00cc\7K\2\2\u00cc"+
		"\u00cd\7U\2\2\u00cd\u00ce\7V\2\2\u00ce\u00cf\7U\2\2\u00cf\u00d0\7a\2\2"+
		"\u00d0\u00d1\7Q\2\2\u00d1\u00d2\7P\2\2\u00d2\u00d3\7G\2\2\u00d3\u00d4"+
		"\7a\2\2\u00d4\u00d5\7U\2\2\u00d5\u00d6\7G\2\2\u00d6\u00d7\7C\2\2\u00d7"+
		"\u00d9\7V\2\2\u00d8x\3\2\2\2\u00d8\u0086\3\2\2\2\u00d8\u0098\3\2\2\2\u00d8"+
		"\u00a5\3\2\2\2\u00d8\u00b5\3\2\2\2\u00d8\u00c9\3\2\2\2\u00d9\36\3\2\2"+
		"\2\u00da\u00db\7?\2\2\u00db\u00e4\7?\2\2\u00dc\u00dd\7#\2\2\u00dd\u00e4"+
		"\7?\2\2\u00de\u00df\7>\2\2\u00df\u00e4\7?\2\2\u00e0\u00e1\7@\2\2\u00e1"+
		"\u00e4\7?\2\2\u00e2\u00e4\t\4\2\2\u00e3\u00da\3\2\2\2\u00e3\u00dc\3\2"+
		"\2\2\u00e3\u00de\3\2\2\2\u00e3\u00e0\3\2\2\2\u00e3\u00e2\3\2\2\2\u00e4"+
		" \3\2\2\2\u00e5\u00e6\7(\2\2\u00e6\u00f1\7(\2\2\u00e7\u00e8\7~\2\2\u00e8"+
		"\u00f1\7~\2\2\u00e9\u00ea\7?\2\2\u00ea\u00eb\7?\2\2\u00eb\u00f1\7@\2\2"+
		"\u00ec\u00ed\7>\2\2\u00ed\u00ee\7?\2\2\u00ee\u00ef\7?\2\2\u00ef\u00f1"+
		"\7@\2\2\u00f0\u00e5\3\2\2\2\u00f0\u00e7\3\2\2\2\u00f0\u00e9\3\2\2\2\u00f0"+
		"\u00ec\3\2\2\2\u00f1\"\3\2\2\2\u00f2\u00f4\5+\26\2\u00f3\u00f2\3\2\2\2"+
		"\u00f4\u00f5\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6$\3"+
		"\2\2\2\u00f7\u00fc\5\'\24\2\u00f8\u00fb\5\'\24\2\u00f9\u00fb\5+\26\2\u00fa"+
		"\u00f8\3\2\2\2\u00fa\u00f9\3\2\2\2\u00fb\u00fe\3\2\2\2\u00fc\u00fa\3\2"+
		"\2\2\u00fc\u00fd\3\2\2\2\u00fd&\3\2\2\2\u00fe\u00fc\3\2\2\2\u00ff\u0102"+
		"\5)\25\2\u0100\u0102\5-\27\2\u0101\u00ff\3\2\2\2\u0101\u0100\3\2\2\2\u0102"+
		"(\3\2\2\2\u0103\u0104\t\5\2\2\u0104*\3\2\2\2\u0105\u0106\t\6\2\2\u0106"+
		",\3\2\2\2\u0107\u0108\7^\2\2\u0108\u0109\7w\2\2\u0109\u010a\3\2\2\2\u010a"+
		"\u0112\5/\30\2\u010b\u010c\7^\2\2\u010c\u010d\7W\2\2\u010d\u010e\3\2\2"+
		"\2\u010e\u010f\5/\30\2\u010f\u0110\5/\30\2\u0110\u0112\3\2\2\2\u0111\u0107"+
		"\3\2\2\2\u0111\u010b\3\2\2\2\u0112.\3\2\2\2\u0113\u0114\5\61\31\2\u0114"+
		"\u0115\5\61\31\2\u0115\u0116\5\61\31\2\u0116\u0117\5\61\31\2\u0117\60"+
		"\3\2\2\2\u0118\u0119\t\7\2\2\u0119\62\3\2\2\2\u011a\u011c\t\b\2\2\u011b"+
		"\u011a\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011b\3\2\2\2\u011d\u011e\3\2"+
		"\2\2\u011e\u011f\3\2\2\2\u011f\u0120\b\32\2\2\u0120\64\3\2\2\2\u0121\u0123"+
		"\7\17\2\2\u0122\u0124\7\f\2\2\u0123\u0122\3\2\2\2\u0123\u0124\3\2\2\2"+
		"\u0124\u0127\3\2\2\2\u0125\u0127\7\f\2\2\u0126\u0121\3\2\2\2\u0126\u0125"+
		"\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u0129\b\33\2\2\u0129\66\3\2\2\2\u012a"+
		"\u012b\7\61\2\2\u012b\u012c\7,\2\2\u012c\u0130\3\2\2\2\u012d\u012f\13"+
		"\2\2\2\u012e\u012d\3\2\2\2\u012f\u0132\3\2\2\2\u0130\u0131\3\2\2\2\u0130"+
		"\u012e\3\2\2\2\u0131\u0133\3\2\2\2\u0132\u0130\3\2\2\2\u0133\u0134\7,"+
		"\2\2\u0134\u0135\7\61\2\2\u0135\u0136\3\2\2\2\u0136\u0137\b\34\2\2\u0137"+
		"8\3\2\2\2\u0138\u0139\7\61\2\2\u0139\u013a\7\61\2\2\u013a\u013e\3\2\2"+
		"\2\u013b\u013d\n\t\2\2\u013c\u013b\3\2\2\2\u013d\u0140\3\2\2\2\u013e\u013c"+
		"\3\2\2\2\u013e\u013f\3\2\2\2\u013f\u0141\3\2\2\2\u0140\u013e\3\2\2\2\u0141"+
		"\u0142\b\35\2\2\u0142:\3\2\2\2\20\2\u00d8\u00e3\u00f0\u00f5\u00fa\u00fc"+
		"\u0101\u0111\u011d\u0123\u0126\u0130\u013e\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}