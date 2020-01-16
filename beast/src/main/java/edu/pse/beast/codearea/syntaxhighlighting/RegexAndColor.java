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

    /** The reg ex. */
    private String regEx;

    /** The attribute set for color. */
    private AttributeSet attributeSetForColor;

    /**
     * Instantiates a new regex and color.
     *
     * @param regExStr the reg ex str
     * @param color the color
     */
    public RegexAndColor(final String regExStr, final Color color) {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        this.regEx = regExStr;
        this.attributeSetForColor
            = styleContext.addAttribute(styleContext.getEmptySet(),
                                        StyleConstants.Foreground,
                                        color);
    }

    /**
     * Getter of the regular expression.
     *
     * @return the String containing the regex
     */
    public String getRegEx() {
        return regEx;
    }

    /**
     * Getter of the attribute set.
     *
     * @return the AttributeSet containing the color
     */
    public AttributeSet getAttributeSet() {
        return attributeSetForColor;
    }
}
