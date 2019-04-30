package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.toolbox.RichTextInformation;
import javafx.scene.input.MouseEvent;

public class TextImageElement extends ResultImageElement {
    private List<RichTextInformation> richTextInfo;

    // objects needed to calculate the size of the text
    private static AffineTransform affinetransform = new AffineTransform();
    private static FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

    /**
     * Draws a String which starts at the specified location. All special characters
     * will be discarded
     * 
     * @param xPosTopLeft  the top left x value of the text
     * @param yPosTopLeft  the top left y value of the text
     * @param lineDistance the vertical distance between two lines of text
     * @param text         the text to be displayed
     */
    public TextImageElement(double xPosTopLeft, double yPosTopLeft, List<RichTextInformation> richTextInfo) {
        super(xPosTopLeft, yPosTopLeft, getMaxX(xPosTopLeft, richTextInfo), getMaxY(yPosTopLeft, richTextInfo));
        this.richTextInfo = richTextInfo;
    }

    @Override
    public void isClicked(MouseEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawElement(Graphics2D graphics, double scale) {

        double xOffset = 0;

        for (Iterator<RichTextInformation> iterator = richTextInfo.iterator(); iterator.hasNext();) {
            RichTextInformation info = (RichTextInformation) iterator.next();

            Font scaledFont = new Font(info.font.getName(), info.font.getStyle(),
                    (int) (Math.round(((info.font.getSize() * scale)))));

            graphics.setFont(scaledFont);
            graphics.setColor(info.color);

            graphics.drawString(replaceCharacters(info.text), (float)((super.getxPosTopLeft() * scale) + xOffset),
                    (float) (super.getyPosBottomRight() * scale));

            xOffset = xOffset + scaledFont.getStringBounds(replaceCharacters(info.text), frc).getWidth();
        }
    }

    private static double getMaxX(double startX, List<RichTextInformation> textInfo) {
        double maxTextWidth = 0;

        for (Iterator<RichTextInformation> iterator = textInfo.iterator(); iterator.hasNext();) {
            RichTextInformation info = (RichTextInformation) iterator.next();
            maxTextWidth = maxTextWidth + info.font.getStringBounds(replaceCharacters(info.text), frc).getWidth();
        }
        
        return startX + maxTextWidth;
    }

    private static double getMaxY(double startY, List<RichTextInformation> textInfo) {

        double maxTextHeight = 0;

        for (Iterator<RichTextInformation> iterator = textInfo.iterator(); iterator.hasNext();) {
            RichTextInformation info = (RichTextInformation) iterator.next();
            maxTextHeight = Math.max(maxTextHeight,
                    info.font.getStringBounds(replaceCharacters(info.text), frc).getHeight());
        }

        return startY + maxTextHeight;
    }

    private static String replaceCharacters(String toClean) {
        toClean = toClean.replace("\t", " " + " " + " " + " ");// 4 spaces TODO maybe extract the tabs per spaces from
                                                                // the codearea

        toClean = toClean.replace("\b", "");
        toClean = toClean.replace("\f", "");
        toClean = toClean.replace("\r", "");
        toClean = toClean.replace("\n", "");

        return toClean;
    }
}
