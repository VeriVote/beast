package edu.pse.beast.gui.options.ceditor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CEditorOptions {
    private static final int DEFAULT_FONT_SIZE = 12;

    private double fontSize = DEFAULT_FONT_SIZE;

    public final double getFontSize() {
        return fontSize;
    }

    public final void setFontSize(final double fontSizeNumber) {
        this.fontSize = fontSizeNumber;
    }
}
