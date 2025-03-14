package edu.kit.kastel.formal.beast.antlrtree;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import edu.kit.kastel.formal.beast.api.io.PathHandler;
import edu.kit.kastel.formal.beast.api.method.antlr.CLexer;
import edu.kit.kastel.formal.beast.api.method.antlr.CParser;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CAntlrTreeMainClass {
    private static final String EMPTY = "";
    private static final String FILE_KEY = "LOOP";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

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

    public final String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, EMPTY, this.getClass());
    }

    public static void main(final String[] args) {
        final CAntlrTreeMainClass antlrTree = new CAntlrTreeMainClass();
        final String loop = antlrTree.getTemplate(FILE_KEY);
        final CLexer l = new CLexer(CharStreams.fromString(loop));
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
