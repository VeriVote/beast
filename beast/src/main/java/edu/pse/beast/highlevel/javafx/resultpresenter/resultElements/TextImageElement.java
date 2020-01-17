package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.toolbox.RichTextInformation;
import javafx.scene.input.MouseEvent;

/**
 * The Class TextImageElement.
 */
public class TextImageElement extends ResultImageElement {

    /** The affine transform. */
    // objects needed to calculate the size of the text
    private static AffineTransform affineTransform = new AffineTransform();

    /** The frc. */
    private static FontRenderContext frc =
            new FontRenderContext(affineTransform, true, true);

    /** The rich text info. */
    private List<RichTextInformation> richTextInfo;

    /**
     * Draws a String which starts at the specified location. All special
     * characters will be discarded
     *
     * @param xPosTopLeft
     *            the top left x value of the text
     * @param yPosTopLeft
     *            the top left y value of the text
     * @param richTextInfoList
     *            the text to be displayed
     */
    public TextImageElement(final double xPosTopLeft, final double yPosTopLeft,
                            final List<RichTextInformation> richTextInfoList) {
        super(xPosTopLeft, yPosTopLeft, getMaxX(xPosTopLeft, richTextInfoList),
              getMaxY(yPosTopLeft, richTextInfoList));
        this.richTextInfo = richTextInfoList;
    }

    /**
     * Draws a String which starts at the specified location. All special
     * characters will be discarded
     *
     * @param xPosTopLeft
     *            the top left x value of the text
     * @param yPosTopLeft
     *            the top left y value of the text
     * @param richTextInfoList
     *            the text to be displayed
     */
    public TextImageElement(final double xPosTopLeft, final double yPosTopLeft,
                            final RichTextInformation richTextInfoList) {
        this(xPosTopLeft, yPosTopLeft,
             new ArrayList<RichTextInformation>(Arrays.asList(richTextInfoList)));
    }

    @Override
    public void isClicked(final MouseEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void drawElement(final Graphics2D graphics, final double scale) {
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

    /**
     * Gets the max X.
     *
     * @param startX
     *            the start X
     * @param textInfo
     *            the text info
     * @return the max X
     */
    @Deprecated
    private static double getMaxX(final double startX,
                                  final List<RichTextInformation> textInfo) {
        double maxTextWidth = 0;
        // for (Iterator<RichTextInformation> iterator = textInfo.iterator();
                // iterator.hasNext();) {
            // RichTextInformation info = (RichTextInformation) iterator.next();
            // maxTextWidth +=
            // info.font.getStringBounds(replaceCharacters(info.text),
            // frc).getWidth();
        // }
        return startX + maxTextWidth;
    }

    /**
     * Gets the max Y.
     *
     * @param startY
     *            the start Y
     * @param textInfo
     *            the text info
     * @return the max Y
     */
    @Deprecated
    private static double getMaxY(final double startY,
                                  final List<RichTextInformation> textInfo) {
        double maxTextHeight = 0;

        // for (Iterator<RichTextInformation> iterator = textInfo.iterator();
                // iterator.hasNext();) {
            // RichTextInformation info = (RichTextInformation) iterator.next();
            // maxTextHeight = Math.max(maxTextHeight,
            // info.font.getStringBounds(replaceCharacters(info.text),
            // frc).getHeight());
        // }
        return startY + maxTextHeight;
    }

    /**
     * Replace characters.
     *
     * @param toClean
     *            the to clean
     * @return the string
     */
    private static String replaceCharacters(final String toClean) {
        // 4 spaces TODO maybe extract the tabs per spaces from the codearea
        String replacString = toClean.replace("\t", " " + " " + " " + " ");
        replacString = replacString.replace("\b", "");
        replacString = replacString.replace("\f", "");
        replacString = replacString.replace("\r", "");
        replacString = replacString.replace("\n", "");
        return replacString;
    }
}
