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
}