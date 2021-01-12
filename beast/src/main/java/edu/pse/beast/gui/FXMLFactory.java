package edu.pse.beast.gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import edu.pse.beast.toolbox.SuperFolderFinder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FXMLFactory {

	private static final String MAIN_WINDOW_FXML = "/src/test/resources/fxml/test_new_descr/Test.fxml";

	/** The Constant FILE_STRING. */
	private static final String FILE_STRING = "file:///";


	public static Parent loadMainWindow(TestGUIController controller) {
		FXMLLoader loader;
		Parent root = null;

		try {
			loader = new FXMLLoader(new URL(FILE_STRING + SuperFolderFinder.getSuperFolder() + MAIN_WINDOW_FXML));
			loader.setController(controller);
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return root;
	}
}
