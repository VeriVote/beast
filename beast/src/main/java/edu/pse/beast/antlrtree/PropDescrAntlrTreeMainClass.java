package edu.pse.beast.antlrtree;

import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;

public class PropDescrAntlrTreeMainClass {
    private static String code = "ELECT1 == CUT(ELECT2, ELECT3);";

    public static void main(final String[] args) {
        final FormalPropertyDescriptionLexer l =
                new FormalPropertyDescriptionLexer(CharStreams.fromString(code));
        final CommonTokenStream ts = new CommonTokenStream(l);
        final FormalPropertyDescriptionParser p =
                new FormalPropertyDescriptionParser(ts);
        final ParseTree tree = p.booleanExpList();

        // show AST in GUI
        final JFrame frame = new JFrame("Antlr AST");
        final JPanel panel = new JPanel();
        final TreeViewer viewer =
                new TreeViewer(Arrays.asList(p.getRuleNames()), tree);
        viewer.setScale(1.5); // Scale a little
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
