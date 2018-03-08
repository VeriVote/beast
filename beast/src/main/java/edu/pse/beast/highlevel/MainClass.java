package edu.pse.beast.highlevel;

import java.net.URL;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The MainClass creates an CentralObjectProvider which creates all other parts
 * of the program and with it a BEASTCommunicator which coordinates them.
 *
 * @author Jonas
 */
public class MainClass extends Application {
	
	/**
	 * Starts BEAST by creating a BEASTCommunicator and corresponding
	 * CentralObjectProvider. If you want to replace one or more implementation of
	 * high level interfaces you have to create a new implementation of
	 * CentralObjectProvider and replace PSECentralObjectProvider with it here.
	 *
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		BEASTCommunicator communicator = new BEASTCommunicator();
		PSECentralObjectProvider centralObjectProvider = new PSECentralObjectProvider(communicator);
		communicator.setCentralObjectProvider(centralObjectProvider);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Locale local = Locale.getDefault();
		
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("javafx/BEAST.fxml"), ResourceBundle.getBundle("edu.pse.beast.highlevel.javafx.bundles.LangBundle", local));
			
			
			Scene scene = new Scene(root, 800, 600);

			stage.setTitle("BEAST");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
