package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.toolbox.Triplet;
import javafx.scene.input.MouseEvent;

public class PieChartResult extends ResultImageElement {

	private List<Triplet<String, Double, Color>> resultValues;
	
	private List<Boolean> clicked = new ArrayList<Boolean>();

	private double totalSize = 0;

	private double width = 0;
	private double height = 0;
	

	public PieChartResult(double xPosTopLeft, double yPosTopLeft, double xPosBottomRight, double yPosBottomRight,
			List<Triplet<String, Double, Color>> resultValues) {
		super(xPosTopLeft, yPosTopLeft, xPosBottomRight, yPosBottomRight);
		this.resultValues = resultValues;

		this.width = xPosBottomRight - xPosTopLeft;
		System.out.println("width: " + width);
		this.height = yPosBottomRight - yPosTopLeft;
		System.out.println("heigh: " + height);

		init();
	}

	private void init() {
		double tmpSize = 0;
		// iterate over the values, adding up their sizes, therefore getting the total
		// size of the pie chart;
		for (Iterator<Triplet<String, Double, Color>> iterator = resultValues.iterator(); iterator.hasNext();) {
			Triplet<String, Double, Color> value = (Triplet<String, Double, Color>) iterator.next();

			if (value.second == 0) {
				System.err.println("You are not allowed to have fields with zero size in this chart");
				iterator.remove(); //remove this element from the chart
			} else {
				tmpSize = tmpSize + Math.abs(value.second);
			}
		}

		this.totalSize = tmpSize;
	}

	@Override
	public void isClicked(MouseEvent event) {
		//do nothing so far
	}

	@Override
	public void drawElement(Graphics2D graphics, double scale) {
		
		if (totalSize == 0) {
			System.err.println("The pie chart was not given a size larger than zero!");
		} else {
			double currentAngle = 0;
			
			
			for (Iterator<Triplet<String, Double, Color>> iterator = resultValues.iterator(); iterator.hasNext();) {
				Triplet<String, Double, Color> value = (Triplet<String, Double, Color>) iterator.next();

				double neededAngle = 360 * (value.second / totalSize);

				if (!iterator.hasNext()) { // this is the last element, so we have to fill
					neededAngle = 360 - currentAngle; //so we have to fill the rest of the circle
				}
				
				graphics.setColor(value.third);
				
				graphics.fill(new Arc2D.Double(super.getxPosTopLeft() * scale, super.getyPosTopLeft() * scale, width * scale, height * scale, currentAngle, neededAngle, Arc2D.PIE));
				
				currentAngle = currentAngle + neededAngle;
			}
		}
	}
}
