package edu.pse.beast.gui;


import java.net.URL;
import java.util.ResourceBundle;

import edu.pse.beast.toolbox.SuperFolderFinder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestFXMLClass extends Application {
	
	 /** The Constant RESOURCE. */
    private static final String RESOURCE =
            "/src/test/resources/fxml/test_new_descr/Test.fxml";
    
    /** The Constant FILE_STRING. */
    private static final String FILE_STRING = "file:///";
	
	public static void main(String[] args) {
        launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {	
		final FXMLLoader loader = new FXMLLoader(
                new URL(FILE_STRING + SuperFolderFinder.getSuperFolder()
                        + RESOURCE));
		TestGUIController controller = new TestGUIController();
		loader.setController(controller);
		
		Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
	}
}
