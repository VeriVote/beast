package edu.pse.beast.antlrtree;

import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CLexer;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser;

public class CAntlrTreeMainClass {
    private static final String LINE_BREAK = "\n";
    private static final String ANTLR_AST = "Antlr AST";
    private static final double SMALL_SCALE = 1.5;

    // private static final String CODE =
    //         "    unsigned int i = 0;" + LINE_BREAK
    //         + "    unsigned int j = 0;" + LINE_BREAK
    //         + "    for (i = 0; i < C; i++) {" + LINE_BREAK
    //         + "        result[i] = 0;\n" + "    }" + LINE_BREAK
    //         + "    for (i = 0; i < V; i++) {" + LINE_BREAK
    //         + "        for (j = 0; j < C; j++) {" + LINE_BREAK
    //         + "            if (votes[i][j] < C) {" + LINE_BREAK
    //         + "                result[votes[i][j]] += (C - j) - 1;" + LINE_BREAK
    //         + "            }" + LINE_BREAK
    //         + "        }" + LINE_BREAK
    //         + "    }";

    private static final String LOOP =
            "for (int i = 0; i < C; i++) {" + LINE_BREAK
            + "    result[i] = 0;" + LINE_BREAK
            + "}" + LINE_BREAK;

    public static void main(final String[] args) {
        final CLexer l = new CLexer(CharStreams.fromString(LOOP));
        final CommonTokenStream ts = new CommonTokenStream(l);
        final CParser p = new CParser(ts);
        final ParseTree tree = p.blockItemList();

        // show AST in GUI
        final JFrame frame = new JFrame(ANTLR_AST);
        final JPanel panel = new JPanel();
        final TreeViewer viewer =
                new TreeViewer(Arrays.asList(p.getRuleNames()), tree);
        viewer.setScale(SMALL_SCALE); // Scale a little
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
