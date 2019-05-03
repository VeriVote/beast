package edu.pse.beast.toolbox;

import java.awt.Color;
import java.awt.Font;

public class RichTextInformation {
    public final String text;
    public final Font font;
    public final Color color;

    /**
     *
     * @param text the text which has to be displayed
     * @param font the font the text should have
     * @param color the color the text should have
     */
    public RichTextInformation(String text, Font font, Color color) {
        this.text = text;
        this.font = font;
        this.color = color;
    }
}
