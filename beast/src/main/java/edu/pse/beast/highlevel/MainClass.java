package edu.pse.beast.highlevel;

import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.types.CommonHelpMethods;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The MainClass creates an CentralObjectProvider which creates all other parts
 * of the program and with it a BEASTCommunicator which coordinates them.
 *
 * @author Jonas
 */
public class MainClass extends Application {
	
	private static Stage mainStage;

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
		//new BEASTCommunicator();
		//PSECentralObjectProvider centralObjectProvider = new PSECentralObjectProvider(communicator);
		//communicator.setCentralObjectProvider(centralObjectProvider);
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Locale local = Locale.getDefault();
		
		mainStage = stage;
		
		try {
			GUIController controller = new GUIController(mainStage);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("javafx/BEAST.fxml"), ResourceBundle.getBundle("edu.pse.beast.highlevel.javafx.bundles.LangBundle", local));
			
			//Parent root = FXMLLoader.load(getClass().getResource("javafx/BEAST.fxml"), ResourceBundle.getBundle("edu.pse.beast.highlevel.javafx.bundles.LangBundle", local));
			
			loader.setController(controller);
			
			Parent root = loader.load();
			
			Scene scene = new Scene(root, 1000, 600);
			stage.setTitle("BEAST");
			stage.getIcons().add(new Image("file:///" + SuperFolderFinder.getSuperFolder() + "/core/images/other/BEAST.png"));
			stage.setScene(scene);
			
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
			
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getMainStage() {
		return mainStage;
	}
}
