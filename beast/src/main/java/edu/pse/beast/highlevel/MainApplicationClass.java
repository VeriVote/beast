package edu.pse.beast.highlevel;

import static javafx.scene.input.KeyCombination.SHORTCUT_ANY;
import static org.fxmisc.wellbehaved.event.EventPattern.anyOf;
// import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.fxmisc.wellbehaved.event.InputMap;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.SuperFolderFinder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * The MainClass creates an CentralObjectProvider which creates all other parts
 * of the program and with it a BEASTCommunicator which coordinates them.
 *
 * @author Jonas Wohnig
 */
public class MainApplicationClass extends Application {
    private static final String TITLE = "BEAST";
    private static final String RESOURCE
            = "/src/main/resources/edu/pse/beast/highlevel/javafx/BEAST.fxml";
    //private static final String RESOURCE = "/edu/pse/beast/highlevel/javafx/BEAST.fxml";
    private static final String RESOURCE_BUNDLE
         // = "/src/main/resources/edu.pse.beast.highlevel.javafx.bundles.LangBundle";
            = "edu.pse.beast.highlevel.javafx.bundles.LangBundle";
    private static final String BEAST_ICON = "/core/images/other/BEAST.png";
    private static final String FILE_STRING = "file:///";

    private static Stage mainStage;
    
    

    /**
     * Starts BEAST by creating a BEASTCommunicator and corresponding
     * CentralObjectProvider. If you want to replace one or more implementation of
     * high level interfaces you have to create a new implementation of
     * CentralObjectProvider and replace PSECentralObjectProvider with it here.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        // new BEASTCommunicator();
        // PSECentralObjectProvider centralObjectProvider = new
        // PSECentralObjectProvider(communicator);
        // communicator.setCentralObjectProvider(centralObjectProvider);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Locale locale = Locale.ENGLISH;
        mainStage = stage;

        try {
            GUIController controller = new GUIController(mainStage);
            FXMLLoader loader
                  = new FXMLLoader(
                          new URL("file:///" + SuperFolderFinder.getSuperFolder() + RESOURCE),
                            ResourceBundle.getBundle(RESOURCE_BUNDLE, locale));
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle(TITLE);
            stage.getIcons().add(
                    new Image(
                            FILE_STRING
                            + SuperFolderFinder.getSuperFolder()
                            + BEAST_ICON));
            stage.setScene(scene);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            // specify the short cuts the program should ignore
            InputMap<Event> shortcutsToConsume
                  = InputMap.consume(anyOf(
                            // prevent selection via (CTRL + ) SHIFT + [LEFT, UP, DOWN]
                            // ignore the save shortcut
//                          keyPressed(KeyCode.S,
//                          KeyCombination.CONTROL_DOWN, SHORTCUT_ANY),
//                          keyPressed(KeyCode.S, KeyCombination.CONTROL_DOWN,
//                          KeyCombination.SHIFT_DOWN, SHORTCUT_ANY),
                            // ignore the save shortcut
//                          keyPressed(KeyCode.O, KeyCombination.CONTROL_DOWN,
//                                     SHORTCUT_ANY)
            ));
            controller.setShortcutsToConsume(shortcutsToConsume);
            final KeyCombination saveCombination
                  = new KeyCodeCombination(KeyCode.S,
                                           KeyCombination.CONTROL_DOWN,
                                           SHORTCUT_ANY);
            final KeyCombination saveAllCombination
                  = new KeyCodeCombination(KeyCode.S,
                                           KeyCombination.SHIFT_DOWN,
                                           KeyCombination.CONTROL_DOWN);
            final KeyCombination openCombination
                  = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
            final KeyCombination autoCompletionCombination
                  = new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN);
            // final KeyCombination copyCombination = new KeyCodeCombination(KeyCode.C,
            // KeyCombination.CONTROL_DOWN);
            final KeyCombination pasteCombination
                  = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
            final KeyCombination cutCombination
                  = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
            scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (saveCombination.match(event)) {
                        controller.getFocusedArea().save();
                    }
                    if (saveAllCombination.match(event)) {
                        controller.saveProject(null);
                    }
                    if (openCombination.match(event)) {
                        controller.openButton(null);
                    }
                    if (autoCompletionCombination.match(event)) {
                        controller.getFocusedArea().autoComplete();
                    }
                    // if (copyCombination.match(event)) {
                    // controller.getFocusedArea().copy();
                    // }
                    if (pasteCombination.match(event)) {
                        controller.getFocusedArea().paste();
                    }
                    if (cutCombination.match(event)) {
                        controller.getFocusedArea().cut();
                    }
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getMainStage() {
        return mainStage;
    }
}