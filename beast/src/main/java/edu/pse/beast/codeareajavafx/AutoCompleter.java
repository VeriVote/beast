package edu.pse.beast.codeareajavafx;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The Class AutoCompleter.
 */
public class AutoCompleter {

    /** The Constant SCENE_WIDTH. */
    private static final int SCENE_WIDTH = 350;

    /** The Constant SCENE_HEIGHT. */
    private static final int SCENE_HEIGHT = 100;

    /** The auto completion stage. */
    private Stage autoCompletionStage = new Stage();

    /** The list. */
    private ListView<String> list = new ListView<String>();

    /** The scene. */
    private Scene scene;

    /** The caller. */
    private AutoCompletionCodeArea caller = null;

    /**
     * The Constructor.
     */
    public AutoCompleter() {
        autoCompletionStage.initStyle(StageStyle.UNDECORATED);
        // autoCompletionStage.initModality(Modality.APPLICATION_MODAL);
        VBox box = new VBox();
        box.setSpacing(0);
        box.getChildren().add(list);
        // autoCompletionStage.getIcons().add(new Image("file:///" +
        // SuperFolderFinder.getSuperFolder() + "/core/images/other/autocomplete.png"));
        list.prefWidthProperty().bind(autoCompletionStage.widthProperty());
        list.prefHeightProperty().bind(autoCompletionStage.heightProperty());
        scene = new Scene(box, SCENE_WIDTH, SCENE_HEIGHT);
        list.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                switch (event.getCode()) {
                case ENTER:
                    notifyCaller();
                    break;
                case ESCAPE:
                    reset();
                    break;
                default:
                    break;
                }
            }
        });
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                notifyCaller();
            }
        });
        autoCompletionStage.setScene(scene);
    }

    /**
     * Notify caller.
     */
    private void notifyCaller() {
        synchronized (this) {
            if (caller != null && list.getSelectionModel().getSelectedItem() != null) {
                caller.insertHiddenAutoCompletion(list.getSelectionModel().getSelectedItem());
            }
        }
        reset();
    }

    /**
     * Show auto completion windows.
     *
     * @param x the x
     * @param y the y
     * @param content the content
     * @param c the c
     */
    public synchronized void showAutoCompletionWindows(final int x, final int y,
                                                       final List<String> content,
                                                       final AutoCompletionCodeArea c) {
        this.caller = c;

        autoCompletionStage.setX(x);
        autoCompletionStage.setY(y);

        // set the items that are supposed to be displayed in the window
        list.setItems(FXCollections.observableList(content));
        autoCompletionStage.show();
        autoCompletionStage.requestFocus(); // put this window on top
    }

    /**
     * Reset AutoCompleter.
     */
    public synchronized void reset() {
        list.setItems(null);
        autoCompletionStage.hide();
    }
}
