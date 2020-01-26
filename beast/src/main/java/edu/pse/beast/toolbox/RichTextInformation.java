package edu.pse.beast.toolbox;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The Class RichTextInformation.
 *
 * @author Lukas Stapelbroek
 */
public class RichTextInformation {

    /** The Constant DEFAULT_FONT_SIZE. */
    private static final int DEFAULT_FONT_SIZE = 12;

    /** The text. */
    private final String text;

    /** The font. */
    private final javafx.scene.text.Font font;

    /** The color. */
    private final Color color;

    /**
     * The constructor.
     *
     * @param textString
     *            the text which has to be displayed
     * @param fnt
     *            the font the text should have
     * @param clr
     *            the color the text should have
     */
    public RichTextInformation(final String textString, final Font fnt,
                               final Color clr) {
        this.text = textString;
        this.font = fnt;
        this.color = clr;
    }

    /**
     * Instantiates a new rich text information.
     *
     * @param textString
     *            standard text in black with size 12
     */
    public RichTextInformation(final String textString) {
        this.text = textString;
        this.font = new Font(DEFAULT_FONT_SIZE);
        this.color = Color.BLACK;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public Bounds getSize() {
        final Text textField = new Text(this.text);
        textField.setFont(this.font);
        return textField.getBoundsInLocal();
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }
}
