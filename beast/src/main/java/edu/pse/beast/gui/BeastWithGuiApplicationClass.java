package edu.pse.beast.gui;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BeastWithGuiApplicationClass extends Application {

	private final static String fxmlResourceName = "/edu/pse/beast/BeastGUI.fxml";
	private BeastGUIController controller = new BeastGUIController();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResourceName));
		loader.setController(controller);
		controller.setPrimaryStage(primaryStage);
		Parent root = loader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception {
		controller.shutdown();
	}
}
