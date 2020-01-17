package edu.pse.beast.codearea.syntaxhighlighting;

import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;

/**
 * Instances of this class are able to apply syntax highlighting to the given
 * JTextPane.
 *
 * @author Nikolai Schnell
 */
public class SyntaxHL {

    /** The text pane. */
    private JTextPane textPane;

    /**
     * Constructor.
     *
     * @param pane
     *            JTextPane the filter should be applied to
     */
    public SyntaxHL(final JTextPane pane) {
        this.textPane = pane;
    }

    /**
     * Creates a new SyntaxHlCompositeFilter and gives it to the textpane.
     *
     * @param regexAndColorList
     *            the list of RegexAndColor elements containint the information
     *            for syntaxHL
     */
    public void updateFilter(final ArrayList<RegexAndColor> regexAndColorList) {
        ((AbstractDocument) textPane.getStyledDocument())
            .setDocumentFilter(new SyntaxHLCompositeFilter(textPane,
                                                           regexAndColorList));
    }
}
