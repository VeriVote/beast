package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.toolbox.RichTextInformation;
import javafx.scene.input.MouseEvent;

public class TextImageElement extends ResultImageElement {
    // objects needed to calculate the size of the text
    private static AffineTransform affinetransform = new AffineTransform();
    private static FontRenderContext frc =
            new FontRenderContext(affinetransform, true, true);
    private List<RichTextInformation> richTextInfo;

    /**
     * Draws a String which starts at the specified location. All special
     * characters will be discarded
     *
     * @param xPosTopLeft
     *            the top left x value of the text
     * @param yPosTopLeft
     *            the top left y value of the text
     * @param richTextInfo
     *            the text to be displayed
     */
    public TextImageElement(double xPosTopLeft, double yPosTopLeft,
                            List<RichTextInformation> richTextInfo) {
        super(xPosTopLeft, yPosTopLeft, getMaxX(xPosTopLeft, richTextInfo),
                getMaxY(yPosTopLeft, richTextInfo));
        this.richTextInfo = richTextInfo;
    }

    /**
     * Draws a String which starts at the specified location. All special
     * characters will be discarded
     *
     * @param xPosTopLeft
     *            the top left x value of the text
     * @param yPosTopLeft
     *            the top left y value of the text
     * @param richTextInfo
     *            the text to be displayed
     */
    public TextImageElement(double xPosTopLeft, double yPosTopLeft,
                            RichTextInformation richTextInfo) {
        this(xPosTopLeft, yPosTopLeft, new ArrayList<>(Arrays.asList(richTextInfo)));
    }

    @Override
    public void isClicked(MouseEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void drawElement(Graphics2D graphics, double scale) {
        // double xOffset = 0;
        // for (RichTextInformation info : richTextInfo) {
            // TODO change richtextinfo maybe to TextStyle
            //
            // Font scaledFont = new Font(info.font.getName(),
            // info.font.getStyle(),
            // (int) (Math.round(((info.font.getSize() * scale)))));
            //
            // graphics.setFont(scaledFont);
            // graphics.setColor(info.color);
            //
            // graphics.drawString(replaceCharacters(info.text), (float)
            // ((super.getxPosTopLeft() * scale) + xOffset),
            // (float) (super.getyPosBottomRight() * scale));
            //
            // xOffset +=
            // scaledFont.getStringBounds(replaceCharacters(info.text),
            // frc).getWidth();
        // }
    }

    @Deprecated
    private static double getMaxX(double startX,
                                  List<RichTextInformation> textInfo) {
        double maxTextWidth = 0;
        // for (Iterator<RichTextInformation> iterator = textInfo.iterator(); iterator.hasNext();) {
            // RichTextInformation info = (RichTextInformation) iterator.next();
            // maxTextWidth +=
            // info.font.getStringBounds(replaceCharacters(info.text),
            // frc).getWidth();
        // }
        return startX + maxTextWidth;
    }

    @Deprecated
    private static double getMaxY(double startY,
                                  List<RichTextInformation> textInfo) {
        double maxTextHeight = 0;

        // for (Iterator<RichTextInformation> iterator = textInfo.iterator(); iterator.hasNext();) {
            // RichTextInformation info = (RichTextInformation) iterator.next();
            // maxTextHeight = Math.max(maxTextHeight,
            // info.font.getStringBounds(replaceCharacters(info.text),
            // frc).getHeight());
        // }
        return startY + maxTextHeight;
    }

    private static String replaceCharacters(String toClean) {
        // 4 spaces TODO maybe extract the tabs per spaces from the codearea
        String replacString = toClean.replace("\t", " " + " " + " " + " ");
        replacString = replacString.replace("\b", "");
        replacString = replacString.replace("\f", "");
        replacString = replacString.replace("\r", "");
        replacString = replacString.replace("\n", "");
        return replacString;
    }
}
