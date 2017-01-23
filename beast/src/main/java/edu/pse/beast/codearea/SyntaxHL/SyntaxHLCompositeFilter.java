package edu.pse.beast.codearea.SyntaxHL;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
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
 * @author NikolaiLMS
 */
public class SyntaxHLCompositeFilter extends DocumentFilter{
    private JTextPane textPane;
    private ArrayList<RegexAndColor> regexAndColorList;
    private StyleContext styleContext = StyleContext.getDefaultStyleContext();
    private AttributeSet blackAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

    public SyntaxHLCompositeFilter(JTextPane textPane, ArrayList<RegexAndColor> regexAndColorList) {
        this.regexAndColorList = regexAndColorList;
        this.textPane = textPane;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attributeSet) throws BadLocationException {
        super.insertString(fb, offset, text, attributeSet);
        handleTextChanged();
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
        handleTextChanged();
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attributeSet) throws BadLocationException {
        super.replace(fb, offset, length, text, attributeSet);
        handleTextChanged();
    }

    /**
     * Runs your updates later, not during the event notification.
     */
    private void handleTextChanged()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                updateTextStyles();
            }
        });
    }

    /**
     * Build the regular expression that looks for the whole word of each word that you wish to find.
     * @return
     */
    private Pattern buildPattern(String token)
    {
        Pattern p = Pattern.compile(token);
        return p;
    }


    private void updateTextStyles()
    {
        // Remove old syntax HL
        textPane.getStyledDocument().setCharacterAttributes(0, textPane.getText().length(), blackAttributeSet, true);

        // iterate over differnt groups of tokens and format them
        for (RegexAndColor iter : regexAndColorList) {

            // Look for tokens and highlight them
            Matcher matcher = buildPattern(iter.getRegEx()).matcher(textPane.getText());

            while (matcher.find()) {
                // Change the color of recognized tokens
                textPane.getStyledDocument().setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), iter.getAttributeSet(), false);
            }

        }
    }

}