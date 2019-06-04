package edu.pse.beast.highlevel.javafx.resultpresenter;

import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.Default;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.ResultPresentationType;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * This class is responsible to show the result of Checks. It provides ways of
 * simply printing Text, drawing images with support for charting, and display
 * any JavaFX node
 * 
 * @author Lukas Stapelbroek
 *
 */
public class ResultPresenterNEW {

	private static ResultPresenterNEW instance;
	
	ResultPresentationType presentationType;
	
	private final Pane resultPane;

	private Result result = null;

	private ResultPresenterNEW() {
		this.resultPane = GUIController.getController().getResultPane();
	}

	/**
	 * removes all children from the result pane
	 */
	private void reset() {
		ResultImageRenderer.reset();
		resultPane.getChildren().clear();
	}

	public void setResult(Result result) {
		boolean changed = (this.result != result);
		this.result = result;
		if (changed) {
			showResult();
		}
	}
	
	public void setPresentationType(ResultPresentationType presentationType) {
		boolean changed = (this.presentationType != presentationType);
		this.presentationType = presentationType;
		if (changed) {
			showResult();
		}
	}

	private void showResult() {	
    	reset();
    	if (result == null) {
    		return;
    	}

    	Node finishedResult = presentationType.presentResult(result);
    	
    	this.setResultNode(finishedResult);
    	
//    	System.out.println("current: " + currentSelection);
//    	
//		if (currentSelection.equals("output")) {
//			setResultText(result.getResultText());
//		} else
//		if (currentSelection.equals("error")) {
//			setResultText(result.getErrorText());
//		} else
//		if (currentSelection.equals("previous")) {
//
//		} else
//		if (currentSelection.equals("result")) {
//			
//		}         
//        System.out.println("result");
//        
//        InputType inType = result.getElectionDescription().getContainer().getInputType();
//        OutputType outType = result.getElectionDescription().getContainer().getOutputType();
//        
//        
//        int maxY = inType.drawResult(result, 0);
//        
//        outType.drawResult(result, maxY);
//        
//	    
//	    ResultImageRenderer.drawElements();
//	    
//	    setResultNode(ResultImageRenderer.getImageView());
//        
//        //var inType = result
//        
//        
//        //TODO implement
    }

	/**
	 * Give the caller complete freedom how he wants to display the result. It can
	 * be done in any way javafx permits
	 * 
	 * @param resultNode the Node which will be shown in the result window
	 */
	public void setResultNode(Node resultNode) {
		reset();
		ResultImageRenderer.resetScrollBars();
		
		resultPane.getChildren().add(resultNode);
	}

	/**
	 * sets the Text of the result pane.
	 * 
	 * @param resultText A list of JavaFX.scene.text.Text, which can be colored and
	 *                   sized as wished
	 */
	private void setResultText(List<Text> resultText) {
		reset();
		TextFlow resultTextField = new TextFlow();
		resultTextField.getChildren().addAll(resultText);
		resultPane.getChildren().add(resultTextField);
	}

	public synchronized static ResultPresenterNEW getInstance() {
		if (instance == null) {
			instance = new ResultPresenterNEW();
		}
		return instance;
	}
}