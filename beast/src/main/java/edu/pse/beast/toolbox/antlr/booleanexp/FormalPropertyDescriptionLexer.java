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
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, VoterByPos=7, CandByPos=8, 
		SeatByPos=9, Mult=10, Add=11, Vote=12, Elect=13, Votesum=14, ClosedBracket=15, 
		OpenBracket=16, Quantor=17, ComparisonSymbol=18, BinaryRelationSymbol=19, 
		Integer=20, Identifier=21, Whitespace=22, Newline=23, BlockComment=24, 
		LineComment=25;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "VoterByPos", "CandByPos", 
		"SeatByPos", "Mult", "Add", "Vote", "Elect", "Votesum", "ClosedBracket", 
		"OpenBracket", "Quantor", "ComparisonSymbol", "BinaryRelationSymbol", 
		"Integer", "Identifier", "IdentifierNondigit", "Nondigit", "Digit", "UniversalCharacterName", 
		"HexQuad", "HexadecimalDigit", "Whitespace", "Newline", "BlockComment", 
		"LineComment"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "'!'", "'V'", "'C'", "'S'", null, null, null, null, 
		null, null, null, null, "')'", "'('"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "VoterByPos", "CandByPos", "SeatByPos", 
		"Mult", "Add", "Vote", "Elect", "Votesum", "ClosedBracket", "OpenBracket", 
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\33\u0174\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\5\22\u010a\n\22\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\5\23\u0115\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\5\24\u0122\n\24\3\25\6\25\u0125\n\25\r\25\16\25\u0126"+
		"\3\26\3\26\3\26\7\26\u012c\n\26\f\26\16\26\u012f\13\26\3\27\3\27\5\27"+
		"\u0133\n\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\5\32\u0143\n\32\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\35\6\35"+
		"\u014d\n\35\r\35\16\35\u014e\3\35\3\35\3\36\3\36\5\36\u0155\n\36\3\36"+
		"\5\36\u0158\n\36\3\36\3\36\3\37\3\37\3\37\3\37\7\37\u0160\n\37\f\37\16"+
		"\37\u0163\13\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \7 \u016e\n \f \16"+
		" \u0171\13 \3 \3 \3\u0161\2!\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\2/\2\61\2"+
		"\63\2\65\2\67\29\30;\31=\32?\33\3\2\n\4\2,,\61\61\4\2--//\4\2>>@@\5\2"+
		"C\\aac|\3\2\62;\5\2\62;CHch\4\2\13\13\"\"\4\2\f\f\17\17\u0183\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2"+
		"\2\2\2?\3\2\2\2\3A\3\2\2\2\5C\3\2\2\2\7E\3\2\2\2\tG\3\2\2\2\13I\3\2\2"+
		"\2\rK\3\2\2\2\17M\3\2\2\2\21\\\3\2\2\2\23j\3\2\2\2\25x\3\2\2\2\27z\3\2"+
		"\2\2\31|\3\2\2\2\33\u0084\3\2\2\2\35\u008c\3\2\2\2\37\u00a5\3\2\2\2!\u00a7"+
		"\3\2\2\2#\u0109\3\2\2\2%\u0114\3\2\2\2\'\u0121\3\2\2\2)\u0124\3\2\2\2"+
		"+\u0128\3\2\2\2-\u0132\3\2\2\2/\u0134\3\2\2\2\61\u0136\3\2\2\2\63\u0142"+
		"\3\2\2\2\65\u0144\3\2\2\2\67\u0149\3\2\2\29\u014c\3\2\2\2;\u0157\3\2\2"+
		"\2=\u015b\3\2\2\2?\u0169\3\2\2\2AB\7=\2\2B\4\3\2\2\2CD\7<\2\2D\6\3\2\2"+
		"\2EF\7#\2\2F\b\3\2\2\2GH\7X\2\2H\n\3\2\2\2IJ\7E\2\2J\f\3\2\2\2KL\7U\2"+
		"\2L\16\3\2\2\2MN\7X\2\2NO\7Q\2\2OP\7V\2\2PQ\7G\2\2QR\7T\2\2RS\7a\2\2S"+
		"T\7D\2\2TU\7[\2\2UV\7a\2\2VW\7R\2\2WX\7Q\2\2XY\7U\2\2YZ\3\2\2\2Z[\5)\25"+
		"\2[\20\3\2\2\2\\]\7E\2\2]^\7C\2\2^_\7P\2\2_`\7F\2\2`a\7a\2\2ab\7D\2\2"+
		"bc\7[\2\2cd\7a\2\2de\7R\2\2ef\7Q\2\2fg\7U\2\2gh\3\2\2\2hi\5)\25\2i\22"+
		"\3\2\2\2jk\7U\2\2kl\7G\2\2lm\7C\2\2mn\7V\2\2no\7a\2\2op\7D\2\2pq\7[\2"+
		"\2qr\7a\2\2rs\7R\2\2st\7Q\2\2tu\7U\2\2uv\3\2\2\2vw\5)\25\2w\24\3\2\2\2"+
		"xy\t\2\2\2y\26\3\2\2\2z{\t\3\2\2{\30\3\2\2\2|}\7X\2\2}~\7Q\2\2~\177\7"+
		"V\2\2\177\u0080\7G\2\2\u0080\u0081\7U\2\2\u0081\u0082\3\2\2\2\u0082\u0083"+
		"\5)\25\2\u0083\32\3\2\2\2\u0084\u0085\7G\2\2\u0085\u0086\7N\2\2\u0086"+
		"\u0087\7G\2\2\u0087\u0088\7E\2\2\u0088\u0089\7V\2\2\u0089\u008a\3\2\2"+
		"\2\u008a\u008b\5)\25\2\u008b\34\3\2\2\2\u008c\u008d\7X\2\2\u008d\u008e"+
		"\7Q\2\2\u008e\u008f\7V\2\2\u008f\u0090\7G\2\2\u0090\u0091\7a\2\2\u0091"+
		"\u0092\7U\2\2\u0092\u0093\7W\2\2\u0093\u0094\7O\2\2\u0094\u0095\7a\2\2"+
		"\u0095\u0096\7H\2\2\u0096\u0097\7Q\2\2\u0097\u0098\7T\2\2\u0098\u0099"+
		"\7a\2\2\u0099\u009a\7E\2\2\u009a\u009b\7C\2\2\u009b\u009c\7P\2\2\u009c"+
		"\u009d\7F\2\2\u009d\u009e\7K\2\2\u009e\u009f\7F\2\2\u009f\u00a0\7C\2\2"+
		"\u00a0\u00a1\7V\2\2\u00a1\u00a2\7G\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4"+
		"\5)\25\2\u00a4\36\3\2\2\2\u00a5\u00a6\7+\2\2\u00a6 \3\2\2\2\u00a7\u00a8"+
		"\7*\2\2\u00a8\"\3\2\2\2\u00a9\u00aa\7H\2\2\u00aa\u00ab\7Q\2\2\u00ab\u00ac"+
		"\7T\2\2\u00ac\u00ad\7a\2\2\u00ad\u00ae\7C\2\2\u00ae\u00af\7N\2\2\u00af"+
		"\u00b0\7N\2\2\u00b0\u00b1\7a\2\2\u00b1\u00b2\7X\2\2\u00b2\u00b3\7Q\2\2"+
		"\u00b3\u00b4\7V\2\2\u00b4\u00b5\7G\2\2\u00b5\u00b6\7T\2\2\u00b6\u010a"+
		"\7U\2\2\u00b7\u00b8\7H\2\2\u00b8\u00b9\7Q\2\2\u00b9\u00ba\7T\2\2\u00ba"+
		"\u00bb\7a\2\2\u00bb\u00bc\7C\2\2\u00bc\u00bd\7N\2\2\u00bd\u00be\7N\2\2"+
		"\u00be\u00bf\7a\2\2\u00bf\u00c0\7E\2\2\u00c0\u00c1\7C\2\2\u00c1\u00c2"+
		"\7P\2\2\u00c2\u00c3\7F\2\2\u00c3\u00c4\7K\2\2\u00c4\u00c5\7F\2\2\u00c5"+
		"\u00c6\7C\2\2\u00c6\u00c7\7V\2\2\u00c7\u00c8\7G\2\2\u00c8\u010a\7U\2\2"+
		"\u00c9\u00ca\7H\2\2\u00ca\u00cb\7Q\2\2\u00cb\u00cc\7T\2\2\u00cc\u00cd"+
		"\7a\2\2\u00cd\u00ce\7C\2\2\u00ce\u00cf\7N\2\2\u00cf\u00d0\7N\2\2\u00d0"+
		"\u00d1\7a\2\2\u00d1\u00d2\7U\2\2\u00d2\u00d3\7G\2\2\u00d3\u00d4\7C\2\2"+
		"\u00d4\u00d5\7V\2\2\u00d5\u010a\7U\2\2\u00d6\u00d7\7G\2\2\u00d7\u00d8"+
		"\7Z\2\2\u00d8\u00d9\7K\2\2\u00d9\u00da\7U\2\2\u00da\u00db\7V\2\2\u00db"+
		"\u00dc\7U\2\2\u00dc\u00dd\7a\2\2\u00dd\u00de\7Q\2\2\u00de\u00df\7P\2\2"+
		"\u00df\u00e0\7G\2\2\u00e0\u00e1\7a\2\2\u00e1\u00e2\7X\2\2\u00e2\u00e3"+
		"\7Q\2\2\u00e3\u00e4\7V\2\2\u00e4\u00e5\7G\2\2\u00e5\u010a\7T\2\2\u00e6"+
		"\u00e7\7G\2\2\u00e7\u00e8\7Z\2\2\u00e8\u00e9\7K\2\2\u00e9\u00ea\7U\2\2"+
		"\u00ea\u00eb\7V\2\2\u00eb\u00ec\7U\2\2\u00ec\u00ed\7a\2\2\u00ed\u00ee"+
		"\7Q\2\2\u00ee\u00ef\7P\2\2\u00ef\u00f0\7G\2\2\u00f0\u00f1\7a\2\2\u00f1"+
		"\u00f2\7E\2\2\u00f2\u00f3\7C\2\2\u00f3\u00f4\7P\2\2\u00f4\u00f5\7F\2\2"+
		"\u00f5\u00f6\7K\2\2\u00f6\u00f7\7F\2\2\u00f7\u00f8\7C\2\2\u00f8\u00f9"+
		"\7V\2\2\u00f9\u010a\7G\2\2\u00fa\u00fb\7G\2\2\u00fb\u00fc\7Z\2\2\u00fc"+
		"\u00fd\7K\2\2\u00fd\u00fe\7U\2\2\u00fe\u00ff\7V\2\2\u00ff\u0100\7U\2\2"+
		"\u0100\u0101\7a\2\2\u0101\u0102\7Q\2\2\u0102\u0103\7P\2\2\u0103\u0104"+
		"\7G\2\2\u0104\u0105\7a\2\2\u0105\u0106\7U\2\2\u0106\u0107\7G\2\2\u0107"+
		"\u0108\7C\2\2\u0108\u010a\7V\2\2\u0109\u00a9\3\2\2\2\u0109\u00b7\3\2\2"+
		"\2\u0109\u00c9\3\2\2\2\u0109\u00d6\3\2\2\2\u0109\u00e6\3\2\2\2\u0109\u00fa"+
		"\3\2\2\2\u010a$\3\2\2\2\u010b\u010c\7?\2\2\u010c\u0115\7?\2\2\u010d\u010e"+
		"\7#\2\2\u010e\u0115\7?\2\2\u010f\u0110\7>\2\2\u0110\u0115\7?\2\2\u0111"+
		"\u0112\7@\2\2\u0112\u0115\7?\2\2\u0113\u0115\t\4\2\2\u0114\u010b\3\2\2"+
		"\2\u0114\u010d\3\2\2\2\u0114\u010f\3\2\2\2\u0114\u0111\3\2\2\2\u0114\u0113"+
		"\3\2\2\2\u0115&\3\2\2\2\u0116\u0117\7(\2\2\u0117\u0122\7(\2\2\u0118\u0119"+
		"\7~\2\2\u0119\u0122\7~\2\2\u011a\u011b\7?\2\2\u011b\u011c\7?\2\2\u011c"+
		"\u0122\7@\2\2\u011d\u011e\7>\2\2\u011e\u011f\7?\2\2\u011f\u0120\7?\2\2"+
		"\u0120\u0122\7@\2\2\u0121\u0116\3\2\2\2\u0121\u0118\3\2\2\2\u0121\u011a"+
		"\3\2\2\2\u0121\u011d\3\2\2\2\u0122(\3\2\2\2\u0123\u0125\5\61\31\2\u0124"+
		"\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2"+
		"\2\2\u0127*\3\2\2\2\u0128\u012d\5-\27\2\u0129\u012c\5-\27\2\u012a\u012c"+
		"\5\61\31\2\u012b\u0129\3\2\2\2\u012b\u012a\3\2\2\2\u012c\u012f\3\2\2\2"+
		"\u012d\u012b\3\2\2\2\u012d\u012e\3\2\2\2\u012e,\3\2\2\2\u012f\u012d\3"+
		"\2\2\2\u0130\u0133\5/\30\2\u0131\u0133\5\63\32\2\u0132\u0130\3\2\2\2\u0132"+
		"\u0131\3\2\2\2\u0133.\3\2\2\2\u0134\u0135\t\5\2\2\u0135\60\3\2\2\2\u0136"+
		"\u0137\t\6\2\2\u0137\62\3\2\2\2\u0138\u0139\7^\2\2\u0139\u013a\7w\2\2"+
		"\u013a\u013b\3\2\2\2\u013b\u0143\5\65\33\2\u013c\u013d\7^\2\2\u013d\u013e"+
		"\7W\2\2\u013e\u013f\3\2\2\2\u013f\u0140\5\65\33\2\u0140\u0141\5\65\33"+
		"\2\u0141\u0143\3\2\2\2\u0142\u0138\3\2\2\2\u0142\u013c\3\2\2\2\u0143\64"+
		"\3\2\2\2\u0144\u0145\5\67\34\2\u0145\u0146\5\67\34\2\u0146\u0147\5\67"+
		"\34\2\u0147\u0148\5\67\34\2\u0148\66\3\2\2\2\u0149\u014a\t\7\2\2\u014a"+
		"8\3\2\2\2\u014b\u014d\t\b\2\2\u014c\u014b\3\2\2\2\u014d\u014e\3\2\2\2"+
		"\u014e\u014c\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0150\3\2\2\2\u0150\u0151"+
		"\b\35\2\2\u0151:\3\2\2\2\u0152\u0154\7\17\2\2\u0153\u0155\7\f\2\2\u0154"+
		"\u0153\3\2\2\2\u0154\u0155\3\2\2\2\u0155\u0158\3\2\2\2\u0156\u0158\7\f"+
		"\2\2\u0157\u0152\3\2\2\2\u0157\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159"+
		"\u015a\b\36\2\2\u015a<\3\2\2\2\u015b\u015c\7\61\2\2\u015c\u015d\7,\2\2"+
		"\u015d\u0161\3\2\2\2\u015e\u0160\13\2\2\2\u015f\u015e\3\2\2\2\u0160\u0163"+
		"\3\2\2\2\u0161\u0162\3\2\2\2\u0161\u015f\3\2\2\2\u0162\u0164\3\2\2\2\u0163"+
		"\u0161\3\2\2\2\u0164\u0165\7,\2\2\u0165\u0166\7\61\2\2\u0166\u0167\3\2"+
		"\2\2\u0167\u0168\b\37\2\2\u0168>\3\2\2\2\u0169\u016a\7\61\2\2\u016a\u016b"+
		"\7\61\2\2\u016b\u016f\3\2\2\2\u016c\u016e\n\t\2\2\u016d\u016c\3\2\2\2"+
		"\u016e\u0171\3\2\2\2\u016f\u016d\3\2\2\2\u016f\u0170\3\2\2\2\u0170\u0172"+
		"\3\2\2\2\u0171\u016f\3\2\2\2\u0172\u0173\b \2\2\u0173@\3\2\2\2\20\2\u0109"+
		"\u0114\u0121\u0126\u012b\u012d\u0132\u0142\u014e\u0154\u0157\u0161\u016f"+
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