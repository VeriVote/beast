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
	
	
	
	public static void main(String[] args) {
        launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {			
		Parent root = FXMLFactory.loadMainWindow(new TestGUIController());		
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
	}
}
