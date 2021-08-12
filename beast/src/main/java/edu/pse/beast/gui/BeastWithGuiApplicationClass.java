package edu.pse.beast.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class BeastWithGuiApplicationClass extends Application {
    private static final String TITLE = "BEAST";
    private static final String FXML_RESOURCE_NAME = "/edu/pse/beast/gui/beast.fxml";
    private BeastGUIController controller = new BeastGUIController();

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public final void start(final Stage primaryStage) throws Exception {
        final FXMLLoader loader =
                new FXMLLoader(getClass().getResource(FXML_RESOURCE_NAME));
        loader.setController(controller);
        controller.setPrimaryStage(primaryStage);
        final Parent root = loader.load();
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public final void stop() throws Exception {
        controller.shutdown();
    }
}
