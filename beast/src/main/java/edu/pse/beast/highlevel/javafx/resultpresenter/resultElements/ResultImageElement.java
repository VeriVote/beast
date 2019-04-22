package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.image.BufferedImage;

public abstract class ResultImageElement {

	// the position this element is located at
	private double xPosTopLeft;
	private double yPosTopLeft;
	private double xPosBottomRight;
	private double yPosBottomRight;

	public ResultImageElement(double xPosTopLeft, double yPosTopLeft, double xPosBottomRight, double yPosBottomRight) {
		this.xPosTopLeft = xPosTopLeft;
		this.yPosTopLeft = yPosTopLeft;
		this.xPosBottomRight = xPosBottomRight;
		this.yPosBottomRight = yPosBottomRight;
	}

	/**
	 * Determine if a point is inside 
	 * @param posX the x position
	 * @param posY the y position
	 * @return true, if the point is inside this rectangle
	 */
	public boolean isInside(double posX, double posY) {
		return ((posX <= xPosBottomRight) && (posX >= xPosTopLeft) && (posY <= yPosBottomRight)
				&& (posY >= yPosTopLeft));
	}
	
	//abstract methods
	
	/**
	 * notifies the methdos that it is 
	 * @param posX the x position where the click was made
	 * @param posY the y position where the click was made
	 */
	public abstract void isClicked(double posX, double posY);
	
	/**
	 * draws this element, scaled by the "scale" factor
	 * @param image the image where the element has to be drawn on
	 * @param scale the scale by which the element will be scaled
	 */
	public abstract void drawElement(BufferedImage image, double scale);
}