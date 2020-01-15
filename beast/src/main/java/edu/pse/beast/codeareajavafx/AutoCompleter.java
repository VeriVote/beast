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

public class AutoCompleter {
    private static final int SCENE_WIDTH = 350;
    private static final int SCENE_HEIGHT = 100;

    private Stage autoCompletionStage = new Stage();
    private ListView<String> list = new ListView<String>();
    private Scene scene;
    private AutoCompletionCodeArea caller = null;

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

    private void notifyCaller() {
        synchronized (this) {
            if (caller != null && list.getSelectionModel().getSelectedItem() != null) {
                caller.insertHiddenAutoCompletion(list.getSelectionModel().getSelectedItem());
            }
        }
        reset();
    }

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

    public synchronized void reset() {
        list.setItems(null);
        autoCompletionStage.hide();
    }
}
