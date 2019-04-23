package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Graphics2D;

import javafx.scene.input.MouseEvent;

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
	 * Determine if a point is inside 
	 * @param posX the x position
	 * @param posY the y position
	 * @return true, if the point is inside this rectangle
	 */
	public boolean isInside(double posX, double posY) {
		System.out.println("posx: " + posX);
		System.out.println("posy: " + posY);
		
		return ((posX <= xPosBottomRight) && (posX >= xPosTopLeft) && (posY <= yPosBottomRight)
				&& (posY >= yPosTopLeft));
	}
	
	//abstract methods
	
	/**
	 * notifies the methdos that it was clicked on
	 * @param event the y position where the click was made
	 */
	public abstract void isClicked(MouseEvent event);
	
	/**
	 * draws this element, scaled by the "scale" factor
	 * @param graphics the image where the element has to be drawn on
	 * @param scale the scale by which the element will be scaled
	 */
	public abstract void drawElement(Graphics2D graphics, double scale);
}