package edu.pse.beast.highlevel.javafx.resultpresenter;

import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


/**
 * This class is responsible to show the result of Checks.
 * It provides ways of simply printing Text,
 * drawing images with support for charting,
 * and display any JavaFX node
 * @author lukas
 *
 */
public class ResultPresenterNEW {
	
	private static double scrollPosV;
	private static double scrollPosH;
	private static boolean scrollChanged = false;
	
	/**
	 * removes all children from the result pane
	 */
	private static void reset() {
		GUIController.getController().getResultPane().getChildren().clear();
	}
	
	/**
	 * Give the caller complete freedom how he wants to display the result.
	 * It can be done in any way javafx permits
	 * @param resultNode the Node which will be shown in the result window
	 */
	public static void setResultNode(Node resultNode) {
		reset();
		GUIController.getController().getResultPane().getChildren().add(resultNode);
		setScrollBars();
	}
	
	/**
	 * sets the Text of the result pane.
	 * @param resultText A list of JavaFX.scene.text.Text, which can be colored and sized as wished
	 */
	public static void setResultText(List<Text> resultText) {
		reset();
		
		TextFlow resultTextField = new TextFlow();
		resultTextField.getChildren().addAll(resultText);
		GUIController.getController().getResultPane().getChildren().add(resultTextField);
	}
	
	private static void setScrollBars() {
		GUIController.getController().getResultScrollPane()
				.setVvalue(GUIController.getController().getResultScrollPane().getVmax() * scrollPosV);

		GUIController.getController().getResultScrollPane()
				.setHvalue(GUIController.getController().getResultScrollPane().getHmax() * scrollPosH);
		
		scrollChanged = false;
	}
	
	public static void setNextScrollPostion(double newScrollPosV, double newScrollPosH) {
		scrollChanged = true;
		scrollPosV = newScrollPosV;
		scrollPosH = newScrollPosH;
	}
}