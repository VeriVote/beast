package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Graphics2D;

import javafx.scene.input.MouseEvent;

/**
 * The Class ResultImageElement.
 */
public abstract class ResultImageElement {

    /** The x pos top left. */
    // the position this element is located at
    private double xPosTopLeft;

    /** The y pos top left. */
    private double yPosTopLeft;

    /** The x pos bottom right. */
    private double xPosBottomRight;

    /** The y pos bottom right. */
    private double yPosBottomRight;

    /**
     * Instantiates a new result image element.
     *
     * @param xPositionTopLeft
     *            the x position top left
     * @param yPositionTopLeft
     *            the y position top left
     * @param xPositionBottomRight
     *            the x position bottom right
     * @param yPositionBottomRight
     *            the y position bottom right
     */
    public ResultImageElement(final double xPositionTopLeft,
                              final double yPositionTopLeft,
                              final double xPositionBottomRight,
                              final double yPositionBottomRight) {
        this.xPosTopLeft = xPositionTopLeft;
        this.yPosTopLeft = yPositionTopLeft;
        this.xPosBottomRight = xPositionBottomRight;
        this.yPosBottomRight = yPositionBottomRight;
    }

    /**
     * Gets the x pos top left.
     *
     * @return the x pos top left
     */
    public double getxPosTopLeft() {
        return xPosTopLeft;
    }

    /**
     * Gets the y pos top left.
     *
     * @return the y pos top left
     */
    public double getyPosTopLeft() {
        return yPosTopLeft;
    }

    /**
     * Gets the x pos bottom right.
     *
     * @return the x pos bottom right
     */
    public double getxPosBottomRight() {
        return xPosBottomRight;
    }

    /**
     * Gets the y pos bottom right.
     *
     * @return the y pos bottom right
     */
    public double getyPosBottomRight() {
        return yPosBottomRight;
    }

    /**
     * Determines if a point is inside.
     *
     * @param posX
     *            the x position
     * @param posY
     *            the y position
     * @return true, if the point is inside this rectangle
     */
    public boolean isInside(final double posX, final double posY) {
        return ((posX <= xPosBottomRight) && (posX >= xPosTopLeft)
                && (posY <= yPosBottomRight) && (posY >= yPosTopLeft));
    }

    // abstract methods

    /**
     * Notifies the methods that it was clicked on.
     *
     * @param event
     *            the y position where the click was made
     */
    public abstract void isClicked(MouseEvent event);

    /**
     * Draws this element, scaled by the "scale" factor.
     *
     * @param graphics
     *            the image where the element has to be drawn on
     * @param scale
     *            the scale by which the element will be scaled
     */
    public abstract void drawElement(Graphics2D graphics, double scale);
}
