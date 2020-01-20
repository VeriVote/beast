package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import java.util.Arrays;
import java.util.List;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CLexer;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser;

/**
 * The tests for ANTLR.
 *
 * @author Nikolai Schnell
 */
public final class ANTLRTest {
    private ANTLRTest() { }

    /**
     * Show gui tree view.
     *
     * @param code
     *            the code
     */
    static void showGuiTreeView(final String code) {
        final org.antlr.v4.runtime.CharStream stream = CharStreams.fromString(code);
        final CLexer lexer = new CLexer(stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final CParser parser = new CParser(tokens);
        final ParseTree tree = parser.declaration();
        final List<String> ruleNames = Arrays.asList(CParser.ruleNames);
        final TreeViewer view = new TreeViewer(ruleNames, tree);
        view.open();
    }

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        showGuiTreeView("int i = 0;");
    }
}
