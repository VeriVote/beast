package edu.kit.kastel.formal.beast.gui;

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
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class BeastWithGuiApplicationClass extends Application {
    private static final int SCENE_WIDTH = 1000;
    private static final int SCENE_HEIGHT = 600;

    private static final String FILE_STRING = "file:///";
    private static final String TITLE = "BEAST";
    private static final String FXML_RESOURCE_NAME = "/edu/kit/kastel/formal/beast/gui/beast.fxml";
    private static final String BEAST_LOGO = "/edu/kit/kastel/formal/beast/gui/beast.png";

    private BeastGUIController controller = new BeastGUIController();

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public final void start(final Stage primaryStage) throws Exception {
        final FXMLLoader loader =
                new FXMLLoader(getClass().getResource(FXML_RESOURCE_NAME));
        loader.setController(controller);
        final Parent root = loader.load();
        primaryStage.setTitle(TITLE);
        final Image icon = new Image(FILE_STRING + getClass().getResource(BEAST_LOGO).getFile());
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.show();
    }

    @Override
    public final void stop() throws Exception {
        controller.shutdown();
    }
}
