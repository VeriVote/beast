package edu.pse.beast.toolbox.antlr.booleanexp;// Generated from FormalPropertyDescription.g4 by ANTLR 4.5.1
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

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		Mult=10, Add=11, Vote=12, Elect=13, Votesum=14, ClosedBracket=15, OpenBracket=16, 
		Quantor=17, ComparisonSymbol=18, BinaryRelationSymbol=19, Integer=20, 
		Identifier=21, Whitespace=22, Newline=23, BlockComment=24, LineComment=25;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"Mult", "Add", "Vote", "Elect", "Votesum", "ClosedBracket", "OpenBracket", 
		"Quantor", "ComparisonSymbol", "BinaryRelationSymbol", "Integer", "Identifier", 
		"IdentifierNondigit", "Nondigit", "Digit", "UniversalCharacterName", "HexQuad", 
		"HexadecimalDigit", "Whitespace", "Newline", "BlockComment", "LineComment"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\33\u016e\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3"+
		"\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3"+
		"\20\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\5\22\u0104\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23"+
		"\u010f\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24"+
		"\u011c\n\24\3\25\6\25\u011f\n\25\r\25\16\25\u0120\3\26\3\26\3\26\7\26"+
		"\u0126\n\26\f\26\16\26\u0129\13\26\3\27\3\27\5\27\u012d\n\27\3\30\3\30"+
		"\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u013d"+
		"\n\32\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\35\6\35\u0147\n\35\r\35\16"+
		"\35\u0148\3\35\3\35\3\36\3\36\5\36\u014f\n\36\3\36\5\36\u0152\n\36\3\36"+
		"\3\36\3\37\3\37\3\37\3\37\7\37\u015a\n\37\f\37\16\37\u015d\13\37\3\37"+
		"\3\37\3\37\3\37\3\37\3 \3 \3 \3 \7 \u0168\n \f \16 \u016b\13 \3 \3 \3"+
		"\u015b\2!\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\2/\2\61\2\63\2\65\2\67\29\30"+
		";\31=\32?\33\3\2\n\4\2,,\61\61\4\2--//\4\2>>@@\5\2C\\aac|\3\2\62;\5\2"+
		"\62;CHch\4\2\13\13\"\"\4\2\f\f\17\17\u017d\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\3A"+
		"\3\2\2\2\5C\3\2\2\2\7E\3\2\2\2\tG\3\2\2\2\13T\3\2\2\2\r`\3\2\2\2\17l\3"+
		"\2\2\2\21n\3\2\2\2\23p\3\2\2\2\25r\3\2\2\2\27t\3\2\2\2\31v\3\2\2\2\33"+
		"~\3\2\2\2\35\u0086\3\2\2\2\37\u009f\3\2\2\2!\u00a1\3\2\2\2#\u0103\3\2"+
		"\2\2%\u010e\3\2\2\2\'\u011b\3\2\2\2)\u011e\3\2\2\2+\u0122\3\2\2\2-\u012c"+
		"\3\2\2\2/\u012e\3\2\2\2\61\u0130\3\2\2\2\63\u013c\3\2\2\2\65\u013e\3\2"+
		"\2\2\67\u0143\3\2\2\29\u0146\3\2\2\2;\u0151\3\2\2\2=\u0155\3\2\2\2?\u0163"+
		"\3\2\2\2AB\7=\2\2B\4\3\2\2\2CD\7<\2\2D\6\3\2\2\2EF\7#\2\2F\b\3\2\2\2G"+
		"H\7X\2\2HI\7Q\2\2IJ\7V\2\2JK\7G\2\2KL\7T\2\2LM\7a\2\2MN\7C\2\2NO\7V\2"+
		"\2OP\7a\2\2PQ\7R\2\2QR\7Q\2\2RS\7U\2\2S\n\3\2\2\2TU\7E\2\2UV\7C\2\2VW"+
		"\7P\2\2WX\7F\2\2XY\7a\2\2YZ\7C\2\2Z[\7V\2\2[\\\7a\2\2\\]\7R\2\2]^\7Q\2"+
		"\2^_\7U\2\2_\f\3\2\2\2`a\7U\2\2ab\7G\2\2bc\7C\2\2cd\7V\2\2de\7a\2\2ef"+
		"\7C\2\2fg\7V\2\2gh\7a\2\2hi\7R\2\2ij\7Q\2\2jk\7U\2\2k\16\3\2\2\2lm\7X"+
		"\2\2m\20\3\2\2\2no\7E\2\2o\22\3\2\2\2pq\7U\2\2q\24\3\2\2\2rs\t\2\2\2s"+
		"\26\3\2\2\2tu\t\3\2\2u\30\3\2\2\2vw\7X\2\2wx\7Q\2\2xy\7V\2\2yz\7G\2\2"+
		"z{\7U\2\2{|\3\2\2\2|}\5)\25\2}\32\3\2\2\2~\177\7G\2\2\177\u0080\7N\2\2"+
		"\u0080\u0081\7G\2\2\u0081\u0082\7E\2\2\u0082\u0083\7V\2\2\u0083\u0084"+
		"\3\2\2\2\u0084\u0085\5)\25\2\u0085\34\3\2\2\2\u0086\u0087\7X\2\2\u0087"+
		"\u0088\7Q\2\2\u0088\u0089\7V\2\2\u0089\u008a\7G\2\2\u008a\u008b\7a\2\2"+
		"\u008b\u008c\7U\2\2\u008c\u008d\7W\2\2\u008d\u008e\7O\2\2\u008e\u008f"+
		"\7a\2\2\u008f\u0090\7H\2\2\u0090\u0091\7Q\2\2\u0091\u0092\7T\2\2\u0092"+
		"\u0093\7a\2\2\u0093\u0094\7E\2\2\u0094\u0095\7C\2\2\u0095\u0096\7P\2\2"+
		"\u0096\u0097\7F\2\2\u0097\u0098\7K\2\2\u0098\u0099\7F\2\2\u0099\u009a"+
		"\7C\2\2\u009a\u009b\7V\2\2\u009b\u009c\7G\2\2\u009c\u009d\3\2\2\2\u009d"+
		"\u009e\5)\25\2\u009e\36\3\2\2\2\u009f\u00a0\7+\2\2\u00a0 \3\2\2\2\u00a1"+
		"\u00a2\7*\2\2\u00a2\"\3\2\2\2\u00a3\u00a4\7H\2\2\u00a4\u00a5\7Q\2\2\u00a5"+
		"\u00a6\7T\2\2\u00a6\u00a7\7a\2\2\u00a7\u00a8\7C\2\2\u00a8\u00a9\7N\2\2"+
		"\u00a9\u00aa\7N\2\2\u00aa\u00ab\7a\2\2\u00ab\u00ac\7X\2\2\u00ac\u00ad"+
		"\7Q\2\2\u00ad\u00ae\7V\2\2\u00ae\u00af\7G\2\2\u00af\u00b0\7T\2\2\u00b0"+
		"\u0104\7U\2\2\u00b1\u00b2\7H\2\2\u00b2\u00b3\7Q\2\2\u00b3\u00b4\7T\2\2"+
		"\u00b4\u00b5\7a\2\2\u00b5\u00b6\7C\2\2\u00b6\u00b7\7N\2\2\u00b7\u00b8"+
		"\7N\2\2\u00b8\u00b9\7a\2\2\u00b9\u00ba\7E\2\2\u00ba\u00bb\7C\2\2\u00bb"+
		"\u00bc\7P\2\2\u00bc\u00bd\7F\2\2\u00bd\u00be\7K\2\2\u00be\u00bf\7F\2\2"+
		"\u00bf\u00c0\7C\2\2\u00c0\u00c1\7V\2\2\u00c1\u00c2\7G\2\2\u00c2\u0104"+
		"\7U\2\2\u00c3\u00c4\7H\2\2\u00c4\u00c5\7Q\2\2\u00c5\u00c6\7T\2\2\u00c6"+
		"\u00c7\7a\2\2\u00c7\u00c8\7C\2\2\u00c8\u00c9\7N\2\2\u00c9\u00ca\7N\2\2"+
		"\u00ca\u00cb\7a\2\2\u00cb\u00cc\7U\2\2\u00cc\u00cd\7G\2\2\u00cd\u00ce"+
		"\7C\2\2\u00ce\u00cf\7V\2\2\u00cf\u0104\7U\2\2\u00d0\u00d1\7G\2\2\u00d1"+
		"\u00d2\7Z\2\2\u00d2\u00d3\7K\2\2\u00d3\u00d4\7U\2\2\u00d4\u00d5\7V\2\2"+
		"\u00d5\u00d6\7U\2\2\u00d6\u00d7\7a\2\2\u00d7\u00d8\7Q\2\2\u00d8\u00d9"+
		"\7P\2\2\u00d9\u00da\7G\2\2\u00da\u00db\7a\2\2\u00db\u00dc\7X\2\2\u00dc"+
		"\u00dd\7Q\2\2\u00dd\u00de\7V\2\2\u00de\u00df\7G\2\2\u00df\u0104\7T\2\2"+
		"\u00e0\u00e1\7G\2\2\u00e1\u00e2\7Z\2\2\u00e2\u00e3\7K\2\2\u00e3\u00e4"+
		"\7U\2\2\u00e4\u00e5\7V\2\2\u00e5\u00e6\7U\2\2\u00e6\u00e7\7a\2\2\u00e7"+
		"\u00e8\7Q\2\2\u00e8\u00e9\7P\2\2\u00e9\u00ea\7G\2\2\u00ea\u00eb\7a\2\2"+
		"\u00eb\u00ec\7E\2\2\u00ec\u00ed\7C\2\2\u00ed\u00ee\7P\2\2\u00ee\u00ef"+
		"\7F\2\2\u00ef\u00f0\7K\2\2\u00f0\u00f1\7F\2\2\u00f1\u00f2\7C\2\2\u00f2"+
		"\u00f3\7V\2\2\u00f3\u0104\7G\2\2\u00f4\u00f5\7G\2\2\u00f5\u00f6\7Z\2\2"+
		"\u00f6\u00f7\7K\2\2\u00f7\u00f8\7U\2\2\u00f8\u00f9\7V\2\2\u00f9\u00fa"+
		"\7U\2\2\u00fa\u00fb\7a\2\2\u00fb\u00fc\7Q\2\2\u00fc\u00fd\7P\2\2\u00fd"+
		"\u00fe\7G\2\2\u00fe\u00ff\7a\2\2\u00ff\u0100\7U\2\2\u0100\u0101\7G\2\2"+
		"\u0101\u0102\7C\2\2\u0102\u0104\7V\2\2\u0103\u00a3\3\2\2\2\u0103\u00b1"+
		"\3\2\2\2\u0103\u00c3\3\2\2\2\u0103\u00d0\3\2\2\2\u0103\u00e0\3\2\2\2\u0103"+
		"\u00f4\3\2\2\2\u0104$\3\2\2\2\u0105\u0106\7?\2\2\u0106\u010f\7?\2\2\u0107"+
		"\u0108\7#\2\2\u0108\u010f\7?\2\2\u0109\u010a\7>\2\2\u010a\u010f\7?\2\2"+
		"\u010b\u010c\7@\2\2\u010c\u010f\7?\2\2\u010d\u010f\t\4\2\2\u010e\u0105"+
		"\3\2\2\2\u010e\u0107\3\2\2\2\u010e\u0109\3\2\2\2\u010e\u010b\3\2\2\2\u010e"+
		"\u010d\3\2\2\2\u010f&\3\2\2\2\u0110\u0111\7(\2\2\u0111\u011c\7(\2\2\u0112"+
		"\u0113\7~\2\2\u0113\u011c\7~\2\2\u0114\u0115\7?\2\2\u0115\u0116\7?\2\2"+
		"\u0116\u011c\7@\2\2\u0117\u0118\7>\2\2\u0118\u0119\7?\2\2\u0119\u011a"+
		"\7?\2\2\u011a\u011c\7@\2\2\u011b\u0110\3\2\2\2\u011b\u0112\3\2\2\2\u011b"+
		"\u0114\3\2\2\2\u011b\u0117\3\2\2\2\u011c(\3\2\2\2\u011d\u011f\5\61\31"+
		"\2\u011e\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u011e\3\2\2\2\u0120\u0121"+
		"\3\2\2\2\u0121*\3\2\2\2\u0122\u0127\5-\27\2\u0123\u0126\5-\27\2\u0124"+
		"\u0126\5\61\31\2\u0125\u0123\3\2\2\2\u0125\u0124\3\2\2\2\u0126\u0129\3"+
		"\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128,\3\2\2\2\u0129\u0127"+
		"\3\2\2\2\u012a\u012d\5/\30\2\u012b\u012d\5\63\32\2\u012c\u012a\3\2\2\2"+
		"\u012c\u012b\3\2\2\2\u012d.\3\2\2\2\u012e\u012f\t\5\2\2\u012f\60\3\2\2"+
		"\2\u0130\u0131\t\6\2\2\u0131\62\3\2\2\2\u0132\u0133\7^\2\2\u0133\u0134"+
		"\7w\2\2\u0134\u0135\3\2\2\2\u0135\u013d\5\65\33\2\u0136\u0137\7^\2\2\u0137"+
		"\u0138\7W\2\2\u0138\u0139\3\2\2\2\u0139\u013a\5\65\33\2\u013a\u013b\5"+
		"\65\33\2\u013b\u013d\3\2\2\2\u013c\u0132\3\2\2\2\u013c\u0136\3\2\2\2\u013d"+
		"\64\3\2\2\2\u013e\u013f\5\67\34\2\u013f\u0140\5\67\34\2\u0140\u0141\5"+
		"\67\34\2\u0141\u0142\5\67\34\2\u0142\66\3\2\2\2\u0143\u0144\t\7\2\2\u0144"+
		"8\3\2\2\2\u0145\u0147\t\b\2\2\u0146\u0145\3\2\2\2\u0147\u0148\3\2\2\2"+
		"\u0148\u0146\3\2\2\2\u0148\u0149\3\2\2\2\u0149\u014a\3\2\2\2\u014a\u014b"+
		"\b\35\2\2\u014b:\3\2\2\2\u014c\u014e\7\17\2\2\u014d\u014f\7\f\2\2\u014e"+
		"\u014d\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0152\3\2\2\2\u0150\u0152\7\f"+
		"\2\2\u0151\u014c\3\2\2\2\u0151\u0150\3\2\2\2\u0152\u0153\3\2\2\2\u0153"+
		"\u0154\b\36\2\2\u0154<\3\2\2\2\u0155\u0156\7\61\2\2\u0156\u0157\7,\2\2"+
		"\u0157\u015b\3\2\2\2\u0158\u015a\13\2\2\2\u0159\u0158\3\2\2\2\u015a\u015d"+
		"\3\2\2\2\u015b\u015c\3\2\2\2\u015b\u0159\3\2\2\2\u015c\u015e\3\2\2\2\u015d"+
		"\u015b\3\2\2\2\u015e\u015f\7,\2\2\u015f\u0160\7\61\2\2\u0160\u0161\3\2"+
		"\2\2\u0161\u0162\b\37\2\2\u0162>\3\2\2\2\u0163\u0164\7\61\2\2\u0164\u0165"+
		"\7\61\2\2\u0165\u0169\3\2\2\2\u0166\u0168\n\t\2\2\u0167\u0166\3\2\2\2"+
		"\u0168\u016b\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u016a\3\2\2\2\u016a\u016c"+
		"\3\2\2\2\u016b\u0169\3\2\2\2\u016c\u016d\b \2\2\u016d@\3\2\2\2\20\2\u0103"+
		"\u010e\u011b\u0120\u0125\u0127\u012c\u013c\u0148\u014e\u0151\u015b\u0169"+
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