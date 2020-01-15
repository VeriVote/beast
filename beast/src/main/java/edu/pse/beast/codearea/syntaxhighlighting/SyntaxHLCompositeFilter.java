package edu.pse.beast.codearea.syntaxhighlighting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * Class that creates a DocumentFilter.
 *
 * @author Nikolai Schnell
 */
public class SyntaxHLCompositeFilter extends DocumentFilter {
    private JTextPane textPane;
    private ArrayList<RegexAndColor> regexAndColorList;
    private StyleContext styleContext = StyleContext.getDefaultStyleContext();
    private AttributeSet blackAttributeSet
        = styleContext.addAttribute(styleContext.getEmptySet(),
                                    StyleConstants.Foreground,
                                    Color.BLACK);

    /**
     *
     * @param pane the text pane
     * @param regexAndColors the regex and color list
     */
    public SyntaxHLCompositeFilter(final JTextPane pane,
                                   final ArrayList<RegexAndColor> regexAndColors) {
        this.regexAndColorList = regexAndColors;
        this.textPane = pane;
    }

    @Override
    public void insertString(final FilterBypass fb,
                             final int offset,
                             final String text,
                             final AttributeSet attributeSet)
            throws BadLocationException {
        super.insertString(fb, offset, text, attributeSet);
        handleTextChanged();
    }

    @Override
    public void remove(final FilterBypass fb,
                       final int offset,
                       final int length) throws BadLocationException {
        super.remove(fb, offset, length);
        handleTextChanged();
    }

    @Override
    public void replace(final FilterBypass fb,
                        final int offset,
                        final int length,
                        final String text,
                        final AttributeSet attributeSet)
                                throws BadLocationException {
        super.replace(fb, offset, length, text, attributeSet);
        handleTextChanged();
    }

    /**
     * Runs your updates later, not during the event notification.
     */
    private void handleTextChanged() {
        SwingUtilities.invokeLater(() -> {
            updateTextStyles();
        });
    }

    /**
     * Build the regular expression that looks for the whole word of each word that
     * you wish to find.
     *
     * @return the regular expression as a Pattern
     */
    private Pattern buildPattern(final String token) {
        return Pattern.compile(token);
    }

    private void updateTextStyles() {
        // Remove old syntax HL
        textPane.getStyledDocument()
            .setCharacterAttributes(0, textPane.getText().length(),
                                    blackAttributeSet, true);
        // iterate over different groups of tokens and format them
        for (RegexAndColor iter : regexAndColorList) {
            // Look for tokens and highlight them
            Matcher matcher = null;
            try {
                matcher
                    = buildPattern(iter.getRegEx()).matcher(
                          textPane.getStyledDocument()
                           .getText(0,
                                    textPane.getStyledDocument().getLength()));
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            while (matcher.find()) {
                // Change the color of recognized tokens
                textPane.getStyledDocument()
                    .setCharacterAttributes(matcher.start(),
                                            matcher.end()
                                            - matcher.start(),
                                            iter.getAttributeSet(),
                                            false);
            }
        }
    }
}
