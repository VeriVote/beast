package edu.pse.beast.toolbox;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.highlevel.javafx.resultpresenter.ResultImageRenderer;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultElements.TextImageElement;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;

public class CBMCResultPresentationHelper {

	public static Point2D.Double printNameResult(String name, double xPos, double yPos) {
		TextImageElement nameElement = new TextImageElement(xPos, yPos, new RichTextInformation(name));

		ResultImageRenderer.addElement(nameElement);

		return new Point2D.Double(nameElement.getxPosBottomRight(), nameElement.getyPosBottomRight());
	}
	
	public static double printSingleElement(CBMCResultValueSingle single, double startX, double startY) {
		TextImageElement singleElement = new TextImageElement(startX, startY, new RichTextInformation(single.getValue()));
		
		ResultImageRenderer.addElement(singleElement);
		
		return singleElement.getyPosBottomRight();
	}

	public static double printOneDimResult(CBMCResultValueArray array, double startX, double startY) {
		double lastYPos = startY;

		List<RichTextInformation> text = new ArrayList<RichTextInformation>();

		List<CBMCResultValueWrapper> arrayValues = array.getValues();

		for (int i = 0; i < arrayValues.size(); i++) {
			
			CBMCResultValueSingle singleValue = (CBMCResultValueSingle) arrayValues.get(i).getResultValue();

			text.add(new RichTextInformation(singleValue.getValue() + " "));
		}

		TextImageElement voteElement = new TextImageElement(startX, lastYPos, text);

		lastYPos = voteElement.getyPosBottomRight();

		ResultImageRenderer.addElement(voteElement);

		return lastYPos;
	}

	public static double printTwoDimResult(CBMCResultValueArray array, double startX, double startY) {
		double lastYPos = startY;

		List<CBMCResultValueWrapper> arrayValues = array.getValues();

		for (int i = 0; i < arrayValues.size(); i++) {

			CBMCResultValueArray current_array = (CBMCResultValueArray) arrayValues.get(i).getResultValue();

			lastYPos = printOneDimResult(current_array, startX, lastYPos);
		}
		
		return lastYPos;
	}
}
