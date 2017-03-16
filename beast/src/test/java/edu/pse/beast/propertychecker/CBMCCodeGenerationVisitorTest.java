package edu.pse.beast.propertychecker;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.Tuple;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.FormalPropertySyntaxTreeToAstTranslatorTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by holger on 08.03.17.
 */
public class CBMCCodeGenerationVisitorTest {

    private CBMCCodeGenerationVisitor visitor = new CBMCCodeGenerationVisitor();

    private String listToString(List<String> c) {
        StringBuilder b = new StringBuilder();
        c.forEach(l -> b.append(l + "\n"));
        return b.toString();
    }

    @Test
    public void testSimpleSummationCode() {
        String expression = "1 == C - 1;";
        String expected = "unsigned int integerVar_0 = 1;\n"
                + "unsigned int integerVar_1 = 1;\n"
                + "unsigned int integerVar_2 = C - integerVar_1;\n"
                + "unsigned int integer_comp_0 = integerVar_2 == integerVar_0;\n"
                + "assume(integer_comp_0);\n";
        visitor.setToPrePropertyMode();
        BooleanExpressionNode n
                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                        expression,
                        new SymbolicVariable("c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
                        getBooleanExpressions().get(0).get(0);

        List<String> c = visitor.generateCode(n);
        String actual = listToString(c);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testOneVoteSum() {
        String expression = "1 == VOTE_SUM_FOR_CANDIDATE1(c);";
        String expected = "unsigned int integerVar_0 = 1;\n"
                + "unsigned int voteSumExp_0 = voteSumForCandidate(votes1, c);\n"
                + "unsigned int integer_comp_0 = voteSumExp_0 == integerVar_0;\n"
                + "assume(integer_comp_0);\n";
        visitor.setToPrePropertyMode();
        BooleanExpressionNode n
                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                        expression,
                        new SymbolicVariable(
                                "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
                        getBooleanExpressions().get(0).get(0);
        List<String> c = visitor.generateCode(n);
        String actual = listToString(c);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGenerateCodeForVoterAtPos() {
        String expression = "v == VOTER_AT_POS(V/2*3-C);";
        String expected = "unsigned int integerVar_0 = 2;\n"
                + "unsigned int integerVar_1 = V / integerVar_0;\n"
                + "unsigned int integerVar_2 = 3;\n"
                + "unsigned int integerVar_3 = integerVar_1 * integerVar_2;\n"
                + "unsigned int integerVar_4 = integerVar_3 - C;\n"
                + "unsigned int voterAtPos_0 = integerVar_4;\n"
                + "unsigned int comparison_0 = 1;\n"
                + "comparison_0 = voterAtPos_0 == v;\n"
                + "assume(comparison_0);\n";

        visitor.setToPrePropertyMode();
        BooleanExpressionNode n = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                expression,
                new SymbolicVariable(
                        "v", new InternalTypeContainer(InternalTypeRep.VOTER))).
                getBooleanExpressions().get(0).get(0);

        List<String> c = visitor.generateCode(n);
        String actual = listToString(c);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMultipleVoteSum() {
        String expected = "unsigned int integerVar_0 = 1;\n"
                + "unsigned int voteSumExp_0 = voteSumForCandidate(votes1, c);\n"
                + "unsigned int voteSumExp_1 = voteSumForCandidate(votes2, c);\n"
                + "unsigned int integerVar_1 = voteSumExp_0 * voteSumExp_1;\n"
                + "unsigned int voteSumExp_2 = voteSumForCandidate(votes3, c);\n"
                + "unsigned int integerVar_2 = 5;\n"
                + "unsigned int integerVar_3 = voteSumExp_2 / integerVar_2;\n"
                + "unsigned int integerVar_4 = integerVar_1 - integerVar_3;\n"
                + "unsigned int integerVar_5 = integerVar_4 + V;\n"
                + "unsigned int integer_comp_0 = integerVar_5 == integerVar_0;\n"
                + "assume(integer_comp_0);\n";
        String expression
                = "1 == VOTE_SUM_FOR_CANDIDATE1(c) * VOTE_SUM_FOR_CANDIDATE2(c)"
                + " - VOTE_SUM_FOR_CANDIDATE3(c) / 5 + V;";

        visitor.setToPrePropertyMode();
        BooleanExpressionNode n = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                expression,
                new SymbolicVariable(
                        "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
                getBooleanExpressions().get(0).get(0);
        List<String> c = visitor.generateCode(n);
        String actual = listToString(c);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSimpleSymbolicVarEquality() {
        String expression = "c == c;";
        String expected = "unsigned int comparison_0 = 1;\n"
                + "comparison_0 = c == c;\n"
                + "assume(comparison_0);\n";
        BooleanExpressionNode n
                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                        expression,
                        new SymbolicVariable(
                                "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
                        getBooleanExpressions().get(0).get(0);
        visitor.setToPrePropertyMode();
        List<String> c = visitor.generateCode(n);
        String actual = listToString(c);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGenerateForAllCode() {
        String expression = "FOR_ALL_VOTERS(v) : v == v;";
        String expected = "unsigned int forAll_0 = 1;\n"
                + "for(unsigned int v = 0; v < V && forAll_0; v++) {\n"
                + "\tunsigned int comparison_0 = 1;\n"
                + "\tcomparison_0 = v == v;\n"
                + "\tforAll_0 = comparison_0;\n"
                + "}\n"
                + "assume(forAll_0);\n";
        BooleanExpressionNode n
                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                        expression,
                        new SymbolicVariable(
                                "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
                        getBooleanExpressions().get(0).get(0);
        visitor.setToPrePropertyMode();
        List<String> c = visitor.generateCode(n);
        String actual = listToString(c);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGenerateThereExists() {
        String expression = "EXISTS_ONE_SEAT(s) : s == s;";
        String expected = "unsigned int thereExists_0 = 0;\n"
                + "for(unsigned int s = 0; s < S && !thereExists_0; s++) {\n"
                + "\tunsigned int comparison_0 = 1;\n"
                + "\tcomparison_0 = s == s;\n"
                + "\tthereExists_0 = comparison_0;\n"
                + "}\n"
                + "assume(thereExists_0);\n";
        BooleanExpressionNode n
                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                        expression,
                        new SymbolicVariable(
                                "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
                        getBooleanExpressions().get(0).get(0);
        visitor.setToPrePropertyMode();
        List<String> c = visitor.generateCode(n);
        String actual = listToString(c);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testVotes() {
        String expression = "VOTES1(v) == c";
        String expected = "unsigned int comparison_0 = 1;\n"
                + "comparison_0 = c == votes1[v];\n"
                + "assume(comparison_0);\n";
        BooleanExpressionNode n
                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                        expression,
                        Arrays.asList(
                                new Tuple<String, InternalTypeRep>("c", InternalTypeRep.CANDIDATE),
                                new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER))
                ).
                        getBooleanExpressions().get(0).get(0);
        visitor.setToPrePropertyMode();
        List<String> c = visitor.generateCode(n);
        String actual = listToString(c);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testElectExpression() {
        String expression = "ELECT1 == c";
        String expected = "unsigned int comparison_0 = 1;\n"
                + "comparison_0 = c == elect1;\n"
                + "assume(comparison_0);\n";
        BooleanExpressionNode n
                = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                        expression,
                        Arrays.asList(
                                new Tuple<String, InternalTypeRep>("c", InternalTypeRep.CANDIDATE),
                                new Tuple<String, InternalTypeRep>("v", InternalTypeRep.VOTER))
                ).
                        getBooleanExpressions().get(1).get(0);
        visitor.setToPrePropertyMode();
        List<String> c = visitor.generateCode(n);
        String actual = listToString(c);
        Assert.assertEquals(expected, actual);
    }

}
