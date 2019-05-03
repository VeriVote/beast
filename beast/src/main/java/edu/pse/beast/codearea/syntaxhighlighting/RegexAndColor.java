package edu.pse.beast.codearea.syntaxhighlighting;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * Class encapsulating a regular Expression in a String object and a color in an
 * AttributeSet object. Used for the SyntaxHL in the code areas.
 *
 * @author Nikolai Schnell
 */
public class RegexAndColor {
    private String regEx;
    private AttributeSet attributeSetForColor;

    public RegexAndColor(String regEx, Color color) {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        this.regEx = regEx;
        this.attributeSetForColor
            = styleContext.addAttribute(styleContext.getEmptySet(),
                                        StyleConstants.Foreground,
                                        color);
    }

    /**
     * Getter
     *
     * @return the String containing the regex
     */
    public String getRegEx() {
        return regEx;
    }

    /**
     * Getter
     *
     * @return the AttributeSet containing the color
     */
    public AttributeSet getAttributeSet() {
        return attributeSetForColor;
    }
}