package edu.kit.kastel.formal.beast.api.codegen.ast;

import java.util.Arrays;
import java.util.List;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionLexer;
import edu.kit.kastel.formal.beast.api.property.antlr.FormalPropertyDescriptionParser;

/**
 * The tests for ANTLR.
 *
 * @author Nikolai Schnell
 */
public final class PropertyParserTest {
    private static final String ANTLR_TEST_STRING = "1 == 4 * 2 / (4 - 1) + 2;";

    private PropertyParserTest() {
    }

    /**
     * Show gui tree view.
     *
     * @param code the code
     */
    static void showGuiTreeView(final String code) {
        final org.antlr.v4.runtime.CharStream stream = CharStreams
                .fromString(code);
        final FormalPropertyDescriptionLexer lexer = new FormalPropertyDescriptionLexer(
                stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final FormalPropertyDescriptionParser parser = new FormalPropertyDescriptionParser(
                tokens);
        final ParseTree tree = parser.booleanExpList();
        final List<String> ruleNames = Arrays
                .asList(FormalPropertyDescriptionParser.ruleNames);
        final TreeViewer view = new TreeViewer(ruleNames, tree);
        view.open();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        showGuiTreeView(ANTLR_TEST_STRING);
    }
}
