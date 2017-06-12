package edu.pse.beast.codearea.SyntaxHL;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.util.ArrayList;

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
