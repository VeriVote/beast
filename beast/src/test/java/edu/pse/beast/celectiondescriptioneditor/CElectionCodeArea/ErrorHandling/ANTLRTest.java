package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

//import edu.pse.beast.toolbox.antlr.booleanexp.ANTLRBooleanExpressionListener;

import edu.pse.beast.BooleanExpEditor.*;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CLexer;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CParser;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Arrays;
import java.util.List;

/**
 * @author NikolaiLMS
 */
public class ANTLRTest {

    static void showGuiTreeView(final String code) {
        final org.antlr.v4.runtime.CharStream stream = new ANTLRInputStream(code);
        final CLexer lexer = new CLexer(stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final CParser parser = new CParser(tokens);
        final ParseTree tree = parser.compilationUnit();
        final List<String> ruleNames = Arrays.asList(CParser.ruleNames);
        final TreeViewer view = new TreeViewer(ruleNames, tree);
        view.open();
    }
    public static void main(String[] args) {
        showGuiTreeView("void f() { i = 0;}");
    }
}
