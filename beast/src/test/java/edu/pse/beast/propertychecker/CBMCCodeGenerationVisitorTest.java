package edu.pse.beast.propertychecker;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.FormalPropertySyntaxTreeToAstTranslatorTest;
import org.junit.Test;

import java.util.List;

/**
 * Created by holger on 08.03.17.
 */
public class CBMCCodeGenerationVisitorTest {

    private CBMCCodeGenerationVisitor visitor = new CBMCCodeGenerationVisitor(
            new ElectionTemplateHandler().getStandardInput()
    );


    @Test
    public void testSimpleSummationCode() {
        visitor.setToPrePropertyMode();
        BooleanExpressionNode n = FormalPropertySyntaxTreeToAstTranslatorTest.translate("1 == C - 1;", new SymbolicVariable("c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
                getBooleanExpressions().get(0).get(0);

        List<String> c = visitor.generateCode(n);
    }

    @Test
    public void testOneVoteSum() {
        visitor.setToPrePropertyMode();
        BooleanExpressionNode n = FormalPropertySyntaxTreeToAstTranslatorTest.translate("1 == VOTE_SUM_FOR_CANDIDATE1(c);",
                new SymbolicVariable(
                        "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
                getBooleanExpressions().get(0).get(0);

        List<String> c = visitor.generateCode(n);
    }

    @Test
    public void testGenerateCodeForVoteSum() {

    }

    @Test
    public void testMultipleVoteSum() {
        visitor.setToPrePropertyMode();
        BooleanExpressionNode n = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                "1 == VOTE_SUM_FOR_CANDIDATE1(c) * VOTE_SUM_FOR_CANDIDATE2(c) - VOTE_SUM_FOR_CANDIDATE3(c) / 5 + V;",
                new SymbolicVariable(
                        "c", new InternalTypeContainer(InternalTypeRep.CANDIDATE))).
                getBooleanExpressions().get(0).get(0);
        List<String> c = visitor.generateCode(n);
    }

    @Test
    public void testVoterByPos() {
        visitor.setToPrePropertyMode();
        BooleanExpressionNode n = FormalPropertySyntaxTreeToAstTranslatorTest.translate(
                "v == VOTER_AT_POS(1)",
                new SymbolicVariable(
                        "v", new InternalTypeContainer(InternalTypeRep.VOTER))).
                getBooleanExpressions().get(0).get(0);
        List<String> c = visitor.generateCode(n);
    }
}