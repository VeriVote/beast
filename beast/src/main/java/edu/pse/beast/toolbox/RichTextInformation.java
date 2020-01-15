package edu.pse.beast.toolbox;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RichTextInformation {
    private static final int DEFAULT_FONT_SIZE = 12;

    private final String text;
    private final javafx.scene.text.Font font;
    private final Color color;

    /**
     *
     * @param textString
     *            the text which has to be displayed
     * @param fnt
     *            the font the text should have
     * @param clr
     *            the color the text should have
     */
    public RichTextInformation(final String textString,
                               final Font fnt, final Color clr) {
        this.text = textString;
        this.font = fnt;
        this.color = clr;
    }

    /**
     *
     * @param represents
     *            standard text in black with size 12
     */
    public RichTextInformation(final String textString) {
        this.text = textString;
        this.font = new Font(DEFAULT_FONT_SIZE);
        this.color = Color.BLACK;
    }

    public Bounds getSize() {
        Text textField = new Text(this.text);
        textField.setFont(this.font);
        return textField.getBoundsInLocal();
    }

    public Color getColor() {
        return color;
    }
}
