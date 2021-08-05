package edu.pse.beast.antlrtree;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CLexer;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CAntlrTreeMainClass {
    private static final String RESOURCES = "/edu/pse/beast/antlrtree/";
    private static final String FILE_KEY = "LOOP";
    private static final String FILE_ENDING = ".template";

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

    public static final String getTemplate(final String key,
                                           final Class<?> c) {
        assert key != null;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }

    public static void main(final String[] args) {
        final String loop = getTemplate(FILE_KEY, args.getClass());
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
