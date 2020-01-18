package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

/**
 * @author Holger Klein
 */
public class FormalPropertySyntaxTreeToAstTranslatorTest {
//    private FormalPropertySyntaxTreeToAstTranslator translator =
//        new FormalPropertySyntaxTreeToAstTranslator();
//    private BooleanExpANTLRHandler handler;
//    private JTextPane pane = new JTextPane();
//
//    @Before
//    public void setUp() {
//        handler = new BooleanExpANTLRHandler(pane.getStyledDocument());
//    }
//
//
//    @Test
//    public void parseVoterAtPos() {
//        BooleanExpListNode listNode  = translate(
//                "v == VOTER_AT_POS(1)",
//                new SymbolicVariable("v", new InternalTypeContainer(InternalTypeRep.VOTER)));
//        ComparisonNode firstChild =
//            (ComparisonNode) listNode.getBooleanExpressions().get(0).get(0);
//        AtPosExp voterbypos = (AtPosExp) firstChild.getRHSBooleanExpNode();
//    }
//
//    @Test
//    public void parseComplicatedExpression() {
//        setPaneTest("1 == V / 2 * (3 + VOTE_SUM_FOR_CANDIDATE1(c)) - C - S;");
//        BooleanExpListNode listNode = translator.generateFromSyntaxTree(
//                handler.getParseTree(),
//                getStandardInput(),
//                getStandardOutput(),
//                getNewScope("c", InternalTypeRep.CANDIDATE)
//        );
//        ComparisonNode firstChild =
//            (ComparisonNode) listNode.getBooleanExpressions().get(0).get(0);
//    }
//
//    private BooleanExpScope getNewScope(String id, InternalTypeRep internalType) {
//        BooleanExpScope scope= new BooleanExpScope ();
//        scope.addTypeForId(id, new InternalTypeContainer(internalType));
//        return scope;
//    }
//
//
//    private static ElectionTypeContainer getStandardInput() {
//        return new ElectionTemplateHandler().getStandardInput();
//    }
//
//    private static InternalTypeContainer getStandardOutput() {
//        return new ElectionTemplateHandler().getStandardResult().getType();
//    }
//
//    private void setPaneTest(String s) {
//        pane.setText(s);
//    }
//
//    public static BooleanExpListNode translate(String s, SymbolicVariable c) {
//        JTextPane pane = new JTextPane();
//        pane.setText(s);
//        BooleanExpANTLRHandler booleanExpANTLRHandler =
//            new BooleanExpANTLRHandler(pane.getStyledDocument());
//        FormalPropertySyntaxTreeToAstTranslator translator =
//            new FormalPropertySyntaxTreeToAstTranslator();
//        BooleanExpScope scope;
//        scope = new BooleanExpScope();
//        scope.addTypeForId(c.getId(), c.getInternalTypeContainer());
//
//        return translator.generateFromSyntaxTree(booleanExpANTLRHandler.getParseTree(),
//                getStandardInput(),
//                getStandardOutput(),
//                scope);
//    }
//
//    public static BooleanExpListNode
//                translate(String s,
//                          List<Tuple<String, InternalTypeRep>>
//                              nameAndTypes) {
//        JTextPane pane = new JTextPane();
//        pane.setText(s);
//        BooleanExpANTLRHandler booleanExpANTLRHandler =
//            new BooleanExpANTLRHandler(pane.getStyledDocument());
//        FormalPropertySyntaxTreeToAstTranslator translator =
//            new FormalPropertySyntaxTreeToAstTranslator();
//        BooleanExpScope scope;
//        scope = new BooleanExpScope();
//        nameAndTypes.forEach(t -> scope.addTypeForId(t.x, new InternalTypeContainer(t.y)));
//        return translator.generateFromSyntaxTree(booleanExpANTLRHandler.getParseTree(),
//                getStandardInput(),
//                getStandardOutput(),
//                scope);
//    }
//
//    public static BooleanExpListNode
//                translate(String s,
//                          List<Tuple<String, InternalTypeRep>>
//                              nameAndTypes,
//                          ElectionTypeContainer inputType) {
//        JTextPane pane = new JTextPane();
//        pane.setText(s);
//        BooleanExpANTLRHandler booleanExpANTLRHandler =
//            new BooleanExpANTLRHandler(pane.getStyledDocument());
//        FormalPropertySyntaxTreeToAstTranslator translator =
//            new FormalPropertySyntaxTreeToAstTranslator();
//        BooleanExpScope scope;
//        scope = new BooleanExpScope();
//        nameAndTypes.forEach(t -> scope.addTypeForId(t.x, new InternalTypeContainer(t.y)));
//        return translator.generateFromSyntaxTree(booleanExpANTLRHandler.getParseTree(),
//                inputType.getType(),
//                getStandardOutput(),
//                scope);
//    }
//
//    public static BooleanExpListNode
//                translate(String expression,
//                          List<Tuple<String, InternalTypeRep>>
//                              nameAndTypes,
//                          ElectionTypeContainer byId,
//                          ElectionTypeContainer byId1) {
//        JTextPane pane = new JTextPane();
//        pane.setText(expression);
//        BooleanExpANTLRHandler booleanExpANTLRHandler =
//            new BooleanExpANTLRHandler(pane.getStyledDocument());
//        FormalPropertySyntaxTreeToAstTranslator translator =
//            new FormalPropertySyntaxTreeToAstTranslator();
//        BooleanExpScope scope;
//        scope = new BooleanExpScope();
//        nameAndTypes.forEach(t -> scope.addTypeForId(t.x, new InternalTypeContainer(t.y)));
//        return translator.generateFromSyntaxTree(booleanExpANTLRHandler.getParseTree(),
//                byId.getType(),
//                byId1.getType(),
//                scope);
//    }
}
