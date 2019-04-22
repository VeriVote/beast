package edu.pse.beast.highlevel.javafx.resultpresenter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.highlevel.javafx.resultpresenter.resultElements.ResultImageElement;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

public class ResultImageRenderer {
	
	private static int startingWidth = 500;
	private static int startingHeight = 500;
	
	private static double currentScale = 1;
	
	private static List<ResultImageElement> elementList = new ArrayList<ResultImageElement>();
	
	private static ImageView view = new ImageView();

	//the image which is currently shown
	private static BufferedImage currentImage = new BufferedImage(startingWidth, startingHeight, BufferedImage.TYPE_3BYTE_BGR);
	
	//the next image which can be prepared
	private static BufferedImage nextImage = new BufferedImage(startingWidth, startingHeight, BufferedImage.TYPE_3BYTE_BGR);
	
	
	static {
		view.resize(startingWidth, startingHeight);
	}
	
	public static void reset() {
		elementList.clear();
	}
	
	
	/**
	 * adds an element which will be printed on the next image. 
	 * @param element the element which will be added to the list
	 */
	public static void addElement(ResultImageElement element) {
		elementList.add(element);
	}
	
	public static void drawElements() {
		for (Iterator<ResultImageElement> iterator = elementList.iterator(); iterator.hasNext();) {
			ResultImageElement element = (ResultImageElement) iterator.next();
			
			element.drawElement(nextImage, currentScale);
		}

		view.setImage(SwingFXUtils.toFXImage(nextImage, null));
	}
	
	public static ImageView getImageView() {
		return view;
	}
	
	
}
