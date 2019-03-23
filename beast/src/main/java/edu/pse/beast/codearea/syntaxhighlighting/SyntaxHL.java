package edu.pse.beast.codearea.syntaxhighlighting;

import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;

/**
 * Instances of this class are able to apply syntax highlighting to the given
 * JTextPane
 * 
 * @author NikolaiLMS
 */
public class SyntaxHL {
    private JTextPane textPane;

    /**
     * Constructor
     * 
     * @param textPane
     *            JTextPane the filter should be applied to
     */
    public SyntaxHL(JTextPane textPane) {
        this.textPane = textPane;
    }

    /**
     * Creates a new SyntaxHlCompositeFilter and gives it to the textpane
     * 
     * @param regexAndColorList
     *            the list of RegexAndColor elements containint the information
     *            for syntaxHL
     */
    public void updateFilter(ArrayList<RegexAndColor> regexAndColorList) {
        ((AbstractDocument) textPane.getStyledDocument())
                .setDocumentFilter(new SyntaxHLCompositeFilter(textPane, regexAndColorList));
    }
}