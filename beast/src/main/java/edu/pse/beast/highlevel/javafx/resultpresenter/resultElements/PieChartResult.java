package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.toolbox.Triplet;

public class PieChartResult extends ResultImageElement {

	private List<Triplet<String, Double, Color>> resultValues;

	private double totalSize = 0;

	double midPointX = 0;
	double midPointY = 0;

	double width = 0;
	double height = 0;

	public PieChartResult(double xPosTopLeft, double yPosTopLeft, double xPosBottomRight, double yPosBottomRight,
			List<Triplet<String, Double, Color>> resultValues) {
		super(xPosTopLeft, yPosTopLeft, xPosBottomRight, yPosBottomRight);
		this.resultValues = resultValues;

		this.midPointX = xPosTopLeft + xPosBottomRight / 2d;
		this.midPointY = yPosTopLeft + yPosBottomRight / 2d;

		this.width = xPosBottomRight - xPosTopLeft;
		this.height = yPosBottomRight - yPosTopLeft;

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
	public void isClicked(double posX, double posY) {
		// TODO Auto-generated method stub
		System.out.println("clicked on the pie chart");
	}

	@Override
	public void drawElement(BufferedImage image, double scale) {
		if (totalSize == 0) {
			System.err.println("The pie chart was not given a size larger than zero!");
		} else {
			double currentAngle = 0;

			for (Iterator<Triplet<String, Double, Color>> iterator = resultValues.iterator(); iterator.hasNext();) {
				Triplet<String, Double, Color> value = (Triplet<String, Double, Color>) iterator.next();

				double neededAngle = 360 * (value.second / totalSize);
				;

				if (!iterator.hasNext()) { // this is the last element, so we have to fill
					neededAngle = 360 - currentAngle; //so we have to fill the rest of the circle
				}
				
				image.getGraphics().setColor(value.third);
				image.getGraphics().fillArc((int) (midPointX * scale), (int) (midPointY * scale), (int) width, (int) height,
						(int) currentAngle, (int) Math.round(neededAngle));

				currentAngle = currentAngle + Math.round(neededAngle);
			}
		}
	}
}
