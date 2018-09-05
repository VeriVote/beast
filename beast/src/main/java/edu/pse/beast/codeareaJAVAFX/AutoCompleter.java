package edu.pse.beast.codeareaJAVAFX;

import java.util.List;

import edu.pse.beast.toolbox.SuperFolderFinder;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AutoCompleter {

	private Stage autoCompletionStage = new Stage();

	private ListView<String> list = new ListView<String>();

	private Scene scene;

	private AutoCompletionCodeArea caller = null;

	public AutoCompleter() {

		autoCompletionStage.setTitle("Autocompletion Window");
		// autoCompletionStage.initModality(Modality.APPLICATION_MODAL);

		VBox box = new VBox();
		box.setSpacing(0);

		box.getChildren().add(list);
		
		autoCompletionStage.getIcons().add(new Image("file:///" + SuperFolderFinder.getSuperFolder() + "/core/images/other/autocomplete.png"));



		list.prefWidthProperty().bind(autoCompletionStage.widthProperty());
		list.prefHeightProperty().bind(autoCompletionStage.heightProperty());
		
		
		scene = new Scene(box, 350, 100);

		list.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

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
			public void handle(MouseEvent event) {
				notifyCaller();
			}
		});

		autoCompletionStage.setScene(scene);
	}

	private void notifyCaller() {
		synchronized (this) {
			if (caller != null && list.getSelectionModel().getSelectedItem() != null) {
				caller.insertAutoCompletion(list.getSelectionModel().getSelectedItem());
			}
		}
		reset();
	}

	public synchronized void showAutocompletionWindows(int x, int y, List<String> content,
			AutoCompletionCodeArea caller) {
		
		this.caller = caller;
		
		autoCompletionStage.setX(x);
		autoCompletionStage.setY(y);

		list.setItems(FXCollections.observableList(content)); // set the items that are supposed to be displayed in the
																// window
		autoCompletionStage.show();
		autoCompletionStage.requestFocus();// put this window on top
	}

	public synchronized void reset() {
		list.setItems(null);

		autoCompletionStage.hide();

	}

}
