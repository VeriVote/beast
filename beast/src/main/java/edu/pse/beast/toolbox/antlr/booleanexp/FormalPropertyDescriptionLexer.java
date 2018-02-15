package edu.pse.beast.toolbox.antlr.booleanexp;// Generated from FormalPropertyDescription.g4 by ANTLR 4.5.2
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

public class FormalPropertyDescriptionLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9,
		Mult=10, Add=11, Vote=12, Elect=13, Votesum=14, VotesumUnique=15, ClosedBracket=16,
		OpenBracket=17, Quantor=18, ComparisonSymbol=19, BinaryRelationSymbol=20,
		Integer=21, Identifier=22, Whitespace=23, Newline=24, BlockComment=25,
		LineComment=26;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
		"Mult", "Add", "Vote", "Elect", "Votesum", "VotesumUnique", "ClosedBracket",
		"OpenBracket", "Quantor", "ComparisonSymbol", "BinaryRelationSymbol",
		"Integer", "Identifier", "IdentifierNondigit", "Nondigit", "Digit", "UniversalCharacterName",
		"HexQuad", "HexadecimalDigit", "Whitespace", "Newline", "BlockComment",
		"LineComment"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\34\u0190\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13"+
		"\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0126\n\23\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\5\24\u0131\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\5\25\u013e\n\25\3\26\6\26\u0141\n\26\r\26\16\26\u0142"+
		"\3\27\3\27\3\27\7\27\u0148\n\27\f\27\16\27\u014b\13\27\3\30\3\30\5\30"+
		"\u014f\n\30\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\5\33\u015f\n\33\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\36\6\36"+
		"\u0169\n\36\r\36\16\36\u016a\3\36\3\36\3\37\3\37\5\37\u0171\n\37\3\37"+
		"\5\37\u0174\n\37\3\37\3\37\3 \3 \3 \3 \7 \u017c\n \f \16 \u017f\13 \3"+
		" \3 \3 \3 \3 \3!\3!\3!\3!\7!\u018a\n!\f!\16!\u018d\13!\3!\3!\3\u017d\2"+
		"\"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\27-\30/\2\61\2\63\2\65\2\67\29\2;\31=\32"+
		"?\33A\34\3\2\n\4\2,,\61\61\4\2--//\4\2>>@@\5\2C\\aac|\3\2\62;\5\2\62;"+
		"CHch\4\2\13\13\"\"\4\2\f\f\17\17\u019f\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2"+
		"\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2"+
		"\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3"+
		"\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3"+
		"\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2"+
		"\2\3C\3\2\2\2\5E\3\2\2\2\7G\3\2\2\2\tI\3\2\2\2\13V\3\2\2\2\rb\3\2\2\2"+
		"\17n\3\2\2\2\21p\3\2\2\2\23r\3\2\2\2\25t\3\2\2\2\27v\3\2\2\2\31x\3\2\2"+
		"\2\33\u0080\3\2\2\2\35\u0088\3\2\2\2\37\u00a1\3\2\2\2!\u00c1\3\2\2\2#"+
		"\u00c3\3\2\2\2%\u0125\3\2\2\2\'\u0130\3\2\2\2)\u013d\3\2\2\2+\u0140\3"+
		"\2\2\2-\u0144\3\2\2\2/\u014e\3\2\2\2\61\u0150\3\2\2\2\63\u0152\3\2\2\2"+
		"\65\u015e\3\2\2\2\67\u0160\3\2\2\29\u0165\3\2\2\2;\u0168\3\2\2\2=\u0173"+
		"\3\2\2\2?\u0177\3\2\2\2A\u0185\3\2\2\2CD\7=\2\2D\4\3\2\2\2EF\7<\2\2F\6"+
		"\3\2\2\2GH\7#\2\2H\b\3\2\2\2IJ\7X\2\2JK\7Q\2\2KL\7V\2\2LM\7G\2\2MN\7T"+
		"\2\2NO\7a\2\2OP\7C\2\2PQ\7V\2\2QR\7a\2\2RS\7R\2\2ST\7Q\2\2TU\7U\2\2U\n"+
		"\3\2\2\2VW\7E\2\2WX\7C\2\2XY\7P\2\2YZ\7F\2\2Z[\7a\2\2[\\\7C\2\2\\]\7V"+
		"\2\2]^\7a\2\2^_\7R\2\2_`\7Q\2\2`a\7U\2\2a\f\3\2\2\2bc\7U\2\2cd\7G\2\2"+
		"de\7C\2\2ef\7V\2\2fg\7a\2\2gh\7C\2\2hi\7V\2\2ij\7a\2\2jk\7R\2\2kl\7Q\2"+
		"\2lm\7U\2\2m\16\3\2\2\2no\7X\2\2o\20\3\2\2\2pq\7E\2\2q\22\3\2\2\2rs\7"+
		"U\2\2s\24\3\2\2\2tu\t\2\2\2u\26\3\2\2\2vw\t\3\2\2w\30\3\2\2\2xy\7X\2\2"+
		"yz\7Q\2\2z{\7V\2\2{|\7G\2\2|}\7U\2\2}~\3\2\2\2~\177\5+\26\2\177\32\3\2"+
		"\2\2\u0080\u0081\7G\2\2\u0081\u0082\7N\2\2\u0082\u0083\7G\2\2\u0083\u0084"+
		"\7E\2\2\u0084\u0085\7V\2\2\u0085\u0086\3\2\2\2\u0086\u0087\5+\26\2\u0087"+
		"\34\3\2\2\2\u0088\u0089\7X\2\2\u0089\u008a\7Q\2\2\u008a\u008b\7V\2\2\u008b"+
		"\u008c\7G\2\2\u008c\u008d\7a\2\2\u008d\u008e\7U\2\2\u008e\u008f\7W\2\2"+
		"\u008f\u0090\7O\2\2\u0090\u0091\7a\2\2\u0091\u0092\7H\2\2\u0092\u0093"+
		"\7Q\2\2\u0093\u0094\7T\2\2\u0094\u0095\7a\2\2\u0095\u0096\7E\2\2\u0096"+
		"\u0097\7C\2\2\u0097\u0098\7P\2\2\u0098\u0099\7F\2\2\u0099\u009a\7K\2\2"+
		"\u009a\u009b\7F\2\2\u009b\u009c\7C\2\2\u009c\u009d\7V\2\2\u009d\u009e"+
		"\7G\2\2\u009e\u009f\3\2\2\2\u009f\u00a0\5+\26\2\u00a0\36\3\2\2\2\u00a1"+
		"\u00a2\7X\2\2\u00a2\u00a3\7Q\2\2\u00a3\u00a4\7V\2\2\u00a4\u00a5\7G\2\2"+
		"\u00a5\u00a6\7a\2\2\u00a6\u00a7\7U\2\2\u00a7\u00a8\7W\2\2\u00a8\u00a9"+
		"\7O\2\2\u00a9\u00aa\7a\2\2\u00aa\u00ab\7H\2\2\u00ab\u00ac\7Q\2\2\u00ac"+
		"\u00ad\7T\2\2\u00ad\u00ae\7a\2\2\u00ae\u00af\7W\2\2\u00af\u00b0\7P\2\2"+
		"\u00b0\u00b1\7K\2\2\u00b1\u00b2\7S\2\2\u00b2\u00b3\7W\2\2\u00b3\u00b4"+
		"\7G\2\2\u00b4\u00b5\7a\2\2\u00b5\u00b6\7E\2\2\u00b6\u00b7\7C\2\2\u00b7"+
		"\u00b8\7P\2\2\u00b8\u00b9\7F\2\2\u00b9\u00ba\7K\2\2\u00ba\u00bb\7F\2\2"+
		"\u00bb\u00bc\7C\2\2\u00bc\u00bd\7V\2\2\u00bd\u00be\7G\2\2\u00be\u00bf"+
		"\3\2\2\2\u00bf\u00c0\5+\26\2\u00c0 \3\2\2\2\u00c1\u00c2\7+\2\2\u00c2\""+
		"\3\2\2\2\u00c3\u00c4\7*\2\2\u00c4$\3\2\2\2\u00c5\u00c6\7H\2\2\u00c6\u00c7"+
		"\7Q\2\2\u00c7\u00c8\7T\2\2\u00c8\u00c9\7a\2\2\u00c9\u00ca\7C\2\2\u00ca"+
		"\u00cb\7N\2\2\u00cb\u00cc\7N\2\2\u00cc\u00cd\7a\2\2\u00cd\u00ce\7X\2\2"+
		"\u00ce\u00cf\7Q\2\2\u00cf\u00d0\7V\2\2\u00d0\u00d1\7G\2\2\u00d1\u00d2"+
		"\7T\2\2\u00d2\u0126\7U\2\2\u00d3\u00d4\7H\2\2\u00d4\u00d5\7Q\2\2\u00d5"+
		"\u00d6\7T\2\2\u00d6\u00d7\7a\2\2\u00d7\u00d8\7C\2\2\u00d8\u00d9\7N\2\2"+
		"\u00d9\u00da\7N\2\2\u00da\u00db\7a\2\2\u00db\u00dc\7E\2\2\u00dc\u00dd"+
		"\7C\2\2\u00dd\u00de\7P\2\2\u00de\u00df\7F\2\2\u00df\u00e0\7K\2\2\u00e0"+
		"\u00e1\7F\2\2\u00e1\u00e2\7C\2\2\u00e2\u00e3\7V\2\2\u00e3\u00e4\7G\2\2"+
		"\u00e4\u0126\7U\2\2\u00e5\u00e6\7H\2\2\u00e6\u00e7\7Q\2\2\u00e7\u00e8"+
		"\7T\2\2\u00e8\u00e9\7a\2\2\u00e9\u00ea\7C\2\2\u00ea\u00eb\7N\2\2\u00eb"+
		"\u00ec\7N\2\2\u00ec\u00ed\7a\2\2\u00ed\u00ee\7U\2\2\u00ee\u00ef\7G\2\2"+
		"\u00ef\u00f0\7C\2\2\u00f0\u00f1\7V\2\2\u00f1\u0126\7U\2\2\u00f2\u00f3"+
		"\7G\2\2\u00f3\u00f4\7Z\2\2\u00f4\u00f5\7K\2\2\u00f5\u00f6\7U\2\2\u00f6"+
		"\u00f7\7V\2\2\u00f7\u00f8\7U\2\2\u00f8\u00f9\7a\2\2\u00f9\u00fa\7Q\2\2"+
		"\u00fa\u00fb\7P\2\2\u00fb\u00fc\7G\2\2\u00fc\u00fd\7a\2\2\u00fd\u00fe"+
		"\7X\2\2\u00fe\u00ff\7Q\2\2\u00ff\u0100\7V\2\2\u0100\u0101\7G\2\2\u0101"+
		"\u0126\7T\2\2\u0102\u0103\7G\2\2\u0103\u0104\7Z\2\2\u0104\u0105\7K\2\2"+
		"\u0105\u0106\7U\2\2\u0106\u0107\7V\2\2\u0107\u0108\7U\2\2\u0108\u0109"+
		"\7a\2\2\u0109\u010a\7Q\2\2\u010a\u010b\7P\2\2\u010b\u010c\7G\2\2\u010c"+
		"\u010d\7a\2\2\u010d\u010e\7E\2\2\u010e\u010f\7C\2\2\u010f\u0110\7P\2\2"+
		"\u0110\u0111\7F\2\2\u0111\u0112\7K\2\2\u0112\u0113\7F\2\2\u0113\u0114"+
		"\7C\2\2\u0114\u0115\7V\2\2\u0115\u0126\7G\2\2\u0116\u0117\7G\2\2\u0117"+
		"\u0118\7Z\2\2\u0118\u0119\7K\2\2\u0119\u011a\7U\2\2\u011a\u011b\7V\2\2"+
		"\u011b\u011c\7U\2\2\u011c\u011d\7a\2\2\u011d\u011e\7Q\2\2\u011e\u011f"+
		"\7P\2\2\u011f\u0120\7G\2\2\u0120\u0121\7a\2\2\u0121\u0122\7U\2\2\u0122"+
		"\u0123\7G\2\2\u0123\u0124\7C\2\2\u0124\u0126\7V\2\2\u0125\u00c5\3\2\2"+
		"\2\u0125\u00d3\3\2\2\2\u0125\u00e5\3\2\2\2\u0125\u00f2\3\2\2\2\u0125\u0102"+
		"\3\2\2\2\u0125\u0116\3\2\2\2\u0126&\3\2\2\2\u0127\u0128\7?\2\2\u0128\u0131"+
		"\7?\2\2\u0129\u012a\7#\2\2\u012a\u0131\7?\2\2\u012b\u012c\7>\2\2\u012c"+
		"\u0131\7?\2\2\u012d\u012e\7@\2\2\u012e\u0131\7?\2\2\u012f\u0131\t\4\2"+
		"\2\u0130\u0127\3\2\2\2\u0130\u0129\3\2\2\2\u0130\u012b\3\2\2\2\u0130\u012d"+
		"\3\2\2\2\u0130\u012f\3\2\2\2\u0131(\3\2\2\2\u0132\u0133\7(\2\2\u0133\u013e"+
		"\7(\2\2\u0134\u0135\7~\2\2\u0135\u013e\7~\2\2\u0136\u0137\7?\2\2\u0137"+
		"\u0138\7?\2\2\u0138\u013e\7@\2\2\u0139\u013a\7>\2\2\u013a\u013b\7?\2\2"+
		"\u013b\u013c\7?\2\2\u013c\u013e\7@\2\2\u013d\u0132\3\2\2\2\u013d\u0134"+
		"\3\2\2\2\u013d\u0136\3\2\2\2\u013d\u0139\3\2\2\2\u013e*\3\2\2\2\u013f"+
		"\u0141\5\63\32\2\u0140\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142\u0140\3"+
		"\2\2\2\u0142\u0143\3\2\2\2\u0143,\3\2\2\2\u0144\u0149\5/\30\2\u0145\u0148"+
		"\5/\30\2\u0146\u0148\5\63\32\2\u0147\u0145\3\2\2\2\u0147\u0146\3\2\2\2"+
		"\u0148\u014b\3\2\2\2\u0149\u0147\3\2\2\2\u0149\u014a\3\2\2\2\u014a.\3"+
		"\2\2\2\u014b\u0149\3\2\2\2\u014c\u014f\5\61\31\2\u014d\u014f\5\65\33\2"+
		"\u014e\u014c\3\2\2\2\u014e\u014d\3\2\2\2\u014f\60\3\2\2\2\u0150\u0151"+
		"\t\5\2\2\u0151\62\3\2\2\2\u0152\u0153\t\6\2\2\u0153\64\3\2\2\2\u0154\u0155"+
		"\7^\2\2\u0155\u0156\7w\2\2\u0156\u0157\3\2\2\2\u0157\u015f\5\67\34\2\u0158"+
		"\u0159\7^\2\2\u0159\u015a\7W\2\2\u015a\u015b\3\2\2\2\u015b\u015c\5\67"+
		"\34\2\u015c\u015d\5\67\34\2\u015d\u015f\3\2\2\2\u015e\u0154\3\2\2\2\u015e"+
		"\u0158\3\2\2\2\u015f\66\3\2\2\2\u0160\u0161\59\35\2\u0161\u0162\59\35"+
		"\2\u0162\u0163\59\35\2\u0163\u0164\59\35\2\u01648\3\2\2\2\u0165\u0166"+
		"\t\7\2\2\u0166:\3\2\2\2\u0167\u0169\t\b\2\2\u0168\u0167\3\2\2\2\u0169"+
		"\u016a\3\2\2\2\u016a\u0168\3\2\2\2\u016a\u016b\3\2\2\2\u016b\u016c\3\2"+
		"\2\2\u016c\u016d\b\36\2\2\u016d<\3\2\2\2\u016e\u0170\7\17\2\2\u016f\u0171"+
		"\7\f\2\2\u0170\u016f\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u0174\3\2\2\2\u0172"+
		"\u0174\7\f\2\2\u0173\u016e\3\2\2\2\u0173\u0172\3\2\2\2\u0174\u0175\3\2"+
		"\2\2\u0175\u0176\b\37\2\2\u0176>\3\2\2\2\u0177\u0178\7\61\2\2\u0178\u0179"+
		"\7,\2\2\u0179\u017d\3\2\2\2\u017a\u017c\13\2\2\2\u017b\u017a\3\2\2\2\u017c"+
		"\u017f\3\2\2\2\u017d\u017e\3\2\2\2\u017d\u017b\3\2\2\2\u017e\u0180\3\2"+
		"\2\2\u017f\u017d\3\2\2\2\u0180\u0181\7,\2\2\u0181\u0182\7\61\2\2\u0182"+
		"\u0183\3\2\2\2\u0183\u0184\b \2\2\u0184@\3\2\2\2\u0185\u0186\7\61\2\2"+
		"\u0186\u0187\7\61\2\2\u0187\u018b\3\2\2\2\u0188\u018a\n\t\2\2\u0189\u0188"+
		"\3\2\2\2\u018a\u018d\3\2\2\2\u018b\u0189\3\2\2\2\u018b\u018c\3\2\2\2\u018c"+
		"\u018e\3\2\2\2\u018d\u018b\3\2\2\2\u018e\u018f\b!\2\2\u018fB\3\2\2\2\20"+
		"\2\u0125\u0130\u013d\u0142\u0147\u0149\u014e\u015e\u016a\u0170\u0173\u017d"+
		"\u018b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}