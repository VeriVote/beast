package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Graphics2D;

import javafx.scene.input.MouseEvent;

public abstract class ResultImageElement {

    // the position this element is located at
    private double xPosTopLeft;
    private double yPosTopLeft;
    private double xPosBottomRight;
    private double yPosBottomRight;

    public ResultImageElement(final double xPositionTopLeft,
                              final double yPositionTopLeft,
                              final double xPositionBottomRight,
                              final double yPositionBottomRight) {
        this.xPosTopLeft = xPositionTopLeft;
        this.yPosTopLeft = yPositionTopLeft;
        this.xPosBottomRight = xPositionBottomRight;
        this.yPosBottomRight = yPositionBottomRight;
    }

    public double getxPosTopLeft() {
        return xPosTopLeft;
    }

    public double getyPosTopLeft() {
        return yPosTopLeft;
    }

    public double getxPosBottomRight() {
        return xPosBottomRight;
    }

    public double getyPosBottomRight() {
        return yPosBottomRight;
    }

    /**
     * Determines if a point is inside.
     * @param posX the x position
     * @param posY the y position
     * @return true, if the point is inside this rectangle
     */
    public boolean isInside(final double posX, final double posY) {
        return ((posX <= xPosBottomRight)
                && (posX >= xPosTopLeft)
                && (posY <= yPosBottomRight)
                && (posY >= yPosTopLeft));
    }

    //abstract methods

    /**
     * Notifies the methods that it was clicked on.
     * @param event the y position where the click was made
     */
    public abstract void isClicked(MouseEvent event);

    /**
     * Draws this element, scaled by the "scale" factor.
     * @param graphics the image where the element has to be drawn on
     * @param scale the scale by which the element will be scaled
     */
    public abstract void drawElement(Graphics2D graphics, double scale);
}
