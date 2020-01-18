package edu.pse.beast.propertychecker;

/**
 * @author Holger Klein
 */
public class CBMCCodeGenerationVisitorTest {
//    private CBMCCodeGenerationVisitor visitor = new CBMCCodeGenerationVisitor();
//
//    private String listToString(List<String> c) {
//        StringBuilder b = new StringBuilder();
//        c.forEach(l -> b.append(l + "\n"));
//        return b.toString();
//    }
//
//    @Test
//    public void testSimpleSummationCode() {
//        String expression = "1 == C - 1;";
//        String expected = "unsigned int integerVar_0 = 1;\n"
//                + "unsigned int integerVar_1 = 1;\n"
//                + "unsigned int integerVar_2 = C - integerVar_1;\n"
//                + "unsigned int integer_comp_0 = integerVar_0 == integerVar_2;\n"
//                + "assume(integer_comp_0);\n";
//        visitor.setToPreConditionMode();
//        BooleanExpressionNode n =
//                FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                        expression,
//                        new SymbolicVariable(
//                            "c",
//                            new InternalTypeContainer(
//                                InternalTypeRep.CANDIDATE)
//                        )
//                ).getBooleanExpressions().get(0).get(0);
//
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testOneVoteSum() {
//        String expression = "1 == VOTE_SUM_FOR_CANDIDATE1(c);";
//        String expected = "unsigned int integerVar_0 = 1;\n"
//                + "unsigned int voteSumExp_0 = voteSumForCandidate(votes1, c);\n"
//                + "unsigned int integer_comp_0 = integerVar_0 == voteSumExp_0;\n"
//                + "assume(integer_comp_0);\n";
//        visitor.setToPreConditionMode();
//        BooleanExpressionNode n
//                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                        expression,
//                        new SymbolicVariable(
//                                "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
//                        getBooleanExpressions().get(0).get(0);
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGenerateCodeForVoterAtPos() {
//        String expression = "v == VOTER_AT_POS(V/2*3-C);";
//        String expected = "unsigned int integerVar_0 = 2;\n"
//                + "unsigned int integerVar_1 = V / integerVar_0;\n"
//                + "unsigned int integerVar_2 = 3;\n"
//                + "unsigned int integerVar_3 = integerVar_1 * integerVar_2;\n"
//                + "unsigned int integerVar_4 = integerVar_3 - C;\n"
//                + "unsigned int voterAtPos_0 = integerVar_4;\n"
//                + "unsigned int comparison_0 = 1;\n"
//                + "comparison_0 = v == voterAtPos_0;\n"
//                + "assume(comparison_0);\n";
//
//        visitor.setToPreConditionMode();
//        BooleanExpressionNode n = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                expression,
//                new SymbolicVariable(
//                        "v", new InternalTypeContainer(InternalTypeRep.VOTER))).
//                getBooleanExpressions().get(0).get(0);
//
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testMultipleVoteSum() {
//        String expected = "unsigned int integerVar_0 = 1;\n"
//                + "unsigned int voteSumExp_0 = voteSumForCandidate(votes1, c);\n"
//                + "unsigned int voteSumExp_1 = voteSumForCandidate(votes2, c);\n"
//                + "unsigned int integerVar_1 = voteSumExp_0 * voteSumExp_1;\n"
//                + "unsigned int voteSumExp_2 = voteSumForCandidate(votes3, c);\n"
//                + "unsigned int integerVar_2 = 5;\n"
//                + "unsigned int integerVar_3 = voteSumExp_2 / integerVar_2;\n"
//                + "unsigned int integerVar_4 = integerVar_1 - integerVar_3;\n"
//                + "unsigned int integerVar_5 = integerVar_4 + V;\n"
//                + "unsigned int integer_comp_0 = integerVar_0 == integerVar_5;\n"
//                + "assume(integer_comp_0);\n";
//        String expression
//                = "1 == VOTE_SUM_FOR_CANDIDATE1(c) * VOTE_SUM_FOR_CANDIDATE2(c)"
//                + " - VOTE_SUM_FOR_CANDIDATE3(c) / 5 + V;";
//
//        visitor.setToPreConditionMode();
//        BooleanExpressionNode n = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                expression,
//                new SymbolicVariable(
//                        "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
//                getBooleanExpressions().get(0).get(0);
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testSimpleSymbolicVarEquality() {
//        String expression = "c == c;";
//        String expected = "unsigned int comparison_0 = 1;\n"
//                + "comparison_0 = c == c;\n"
//                + "assume(comparison_0);\n";
//        BooleanExpressionNode n
//                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                        expression,
//                        new SymbolicVariable(
//                                "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
//                        getBooleanExpressions().get(0).get(0);
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGenerateForAllCode() {
//        String expression = "FOR_ALL_VOTERS(v) : v == v;";
//        String expected = "unsigned int forAll_0 = 1;\n"
//                + "for(unsigned int v = 0; v < V && forAll_0; v++) {\n"
//                + "\tunsigned int comparison_0 = 1;\n"
//                + "\tcomparison_0 = v == v;\n"
//                + "\tforAll_0 = comparison_0;\n"
//                + "}\n"
//                + "assume(forAll_0);\n";
//        BooleanExpressionNode n
//                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                        expression,
//                        new SymbolicVariable(
//                                "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
//                        getBooleanExpressions().get(0).get(0);
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGenerateThereExists() {
//        String expression = "EXISTS_ONE_SEAT(s) : s == s;";
//        String expected = "unsigned int thereExists_0 = 0;\n"
//                + "for(unsigned int s = 0; s < S && !thereExists_0; s++) {\n"
//                + "\tunsigned int comparison_0 = 1;\n"
//                + "\tcomparison_0 = s == s;\n"
//                + "\tthereExists_0 = comparison_0;\n"
//                + "}\n"
//                + "assume(thereExists_0);\n";
//        BooleanExpressionNode n
//                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                        expression,
//                        new SymbolicVariable(
//                                "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
//                        getBooleanExpressions().get(0).get(0);
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testVotes() {
//        String expression = "VOTES1(v) == c";
//        String expected = "unsigned int comparison_0 = 1;\n"
//                + "comparison_0 = votes1[" + UnifiedNameContainer.getVoter() + "] == c;\n"
//                + "assume(comparison_0);\n";
//        BooleanExpressionNode n =
//                FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                        expression,
//                        Arrays.asList(
//                            new Tuple<String, InternalTypeRep>("c", InternalTypeRep.CANDIDATE),
//                            new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER))
//                )
//                .   getBooleanExpressions().get(0).get(0);
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testElectExpression() {
//        String expression = "ELECT1 == c";
//        String expected = "unsigned int comparison_0 = 1;\n"
//                + "comparison_0 = elect1 == c;\n"
//                + "assume(comparison_0);\n";
//        BooleanExpressionNode n =
//                FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                        expression,
//                        Arrays.asList(
//                            new Tuple<String, InternalTypeRep>("c", InternalTypeRep.CANDIDATE),
//                            new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER))
//                )
//                .getBooleanExpressions().get(1).get(0);
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testCompareExpression() {
//        String expression = "VOTES1(v) == c;";
//        String expected = "unsigned int comparison_0 = 1;\n" +
//                "comparison_0 = votes1[" + UnifiedNameContainer.getVoter() + "] == c;\n" +
//                "assume(comparison_0);\n";
//        BooleanExpressionNode n
//                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                expression,
//                Arrays.asList(
//                        new Tuple<String, InternalTypeRep>("c", InternalTypeRep.CANDIDATE),
//                        new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER))
//        )
//        .getBooleanExpressions().get(0).get(0);
//
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testCompareExpressionList1Deep() {
//        String expression = "VOTES1 == VOTES2;";
//        String expected = "unsigned int comparison_0 = 1;\n" +
//                "for(unsigned int count_0 = 0; count_0 < V && comparison_0; ++count_0) {\n" +
//                "\tcomparison_0 = votes1[count_0] == votes2[count_0];\n" +
//                "}\n" +
//                "assume(comparison_0);\n";
//        BooleanExpressionNode n
//                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                expression,
//                Arrays.asList(
//                        new Tuple<String, InternalTypeRep>("c", InternalTypeRep.CANDIDATE),
//                        new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER))
//        )
//        .getBooleanExpressions().get(0).get(0);
//
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testCompareList2Deep() {
//        String expression = "VOTES1 == VOTES2;";
//        String expected = "unsigned int comparison_0 = 1;\n" +
//                "for(unsigned int count_0 = 0; count_0 < V && comparison_0; ++count_0) {\n" +
//                "\tfor(unsigned int count_1 = 0; count_1 < C && comparison_0; ++count_1) {\n" +
//                "\t\tcomparison_0 = votes1[count_0][count_1] == votes2[count_0][count_1];\n" +
//                "\t}\n" +
//                "}\n" +
//                "assume(comparison_0);\n";
//        BooleanExpressionNode n =
//                FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                        expression,
//                        Arrays.asList(
//                            new Tuple<String, InternalTypeRep>("c", InternalTypeRep.CANDIDATE),
//                            new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER)),
//                        new ElectionTemplateHandler().getById(
//                            ElectionTypeContainer.ElectionInputTypeIds.PREFERENCE)
//        ).getBooleanExpressions().get(0).get(0);
//
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testAnonymityPreForWeightedApproval() {
//        String expression = "FOR_ALL_VOTERS(i) : (i != v && i != w) ==> VOTES1(i) == VOTES2(i);";
//        String expected = "unsigned int forAll_0 = 1;\n" +
//                "for(unsigned int i = 0; i < V && forAll_0; i++) {\n" +
//                "\tunsigned int comparison_0 = 1;\n" +
//                "\tcomparison_0 = i != v;\n" +
//                "\tunsigned int comparison_1 = 1;\n" +
//                "\tcomparison_1 = i != w;\n" +
//                "\tunsigned int and_0 = ((comparison_1) && (comparison_0));\n" +
//                "\tunsigned int comparison_2 = 1;\n" +
//                "\tfor(unsigned int count_0 = 0; count_0 < C && comparison_2; ++count_0) {\n" +
//                "\t\tcomparison_2 = votes1[i][count_0] == votes2[i][count_0];\n" +
//                "\t}\n" +
//                "\tunsigned int implication_0 = (!(and_0) || (comparison_2));\n" +
//                "\tforAll_0 = implication_0;\n" +
//                "}\n" +
//                "assume(forAll_0);\n";
//        BooleanExpressionNode n
//                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                expression,
//                Arrays.asList(
//                        new Tuple<String, InternalTypeRep>("w", InternalTypeRep.VOTER),
//                        new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER)),
//                new ElectionTemplateHandler().getById(
//                    ElectionTypeContainer.ElectionInputTypeIds.WEIGHTED_APPROVAL)
//        ).getBooleanExpressions().get(0).get(0);
//
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testCandidateWithMostApprovalWins() {
//        String expression =
//            "(FOR_ALL_CANDIDATES(i) : ((i != c) "
//            + "==> (VOTE_SUM_FOR_CANDIDATE1(c) > VOTE_SUM_FOR_CANDIDATE1(i)))) "
//            + "==> ELECT1 == c;";
//        String expected = "unsigned int forAll_0 = 1;\n" +
//                "for(unsigned int i = 0; i < C && forAll_0; i++) {\n" +
//                "\tunsigned int comparison_0 = 1;\n" +
//                "\tcomparison_0 = i != c;\n" +
//                "\tunsigned int voteSumExp_0 = voteSumForCandidate(votes1, c);\n" +
//                "\tunsigned int voteSumExp_1 = voteSumForCandidate(votes1, i);\n" +
//                "\tunsigned int integer_comp_1 = voteSumExp_0 > voteSumExp_1;\n" +
//                "\tunsigned int implication_1 = (!(comparison_0) || (integer_comp_1));\n" +
//                "\tforAll_0 = implication_1;\n" +
//                "}\n" +
//                "unsigned int comparison_2 = 1;\n" +
//                "comparison_2 = elect1 == c;\n" +
//                "unsigned int implication_0 = (!(forAll_0) || (comparison_2));\n" +
//                "assume(implication_0);\n";
//        BooleanExpressionNode n
//                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                expression,
//                Arrays.asList(
//                        new Tuple<String, InternalTypeRep>("c", InternalTypeRep.CANDIDATE),
//                        new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER)),
//                new ElectionTemplateHandler().getById(
//                    ElectionTypeContainer.ElectionInputTypeIds.APPROVAL)
//        ).getBooleanExpressions().get(1).get(0);
//
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testCompareTwoElect() {
//        String expression =
//                "ELECT1 == ELECT2;";
//        String expected = "unsigned int comparison_0 = 1;\n" +
//                "for(unsigned int count_0 = 0; count_0 < S && comparison_0; ++count_0) {\n" +
//                "\tcomparison_0 = elect1[count_0] == elect2[count_0];\n" +
//                "}\n" +
//                "assume(comparison_0);\n";
//        BooleanExpressionNode n
//                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
//                expression,
//                Arrays.asList(
//                        new Tuple<String, InternalTypeRep>("c", InternalTypeRep.CANDIDATE),
//                        new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER)),
//                new ElectionTemplateHandler().getById(
//                    ElectionTypeContainer.ElectionInputTypeIds.APPROVAL),
//                new ElectionTemplateHandler().getById(
//                    ElectionTypeContainer.ElectionOutputTypeIds.CAND_PER_SEAT)
//        ).getBooleanExpressions().get(2).get(0);
//
//        visitor.setToPreConditionMode();
//        List<String> c = visitor.generateCode(n);
//        String actual = listToString(c);
//        Assert.assertEquals(expected, actual);
//    }
}
