package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.propertychecker.Result;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChildTreeItem extends CustomTreeItem {

	private Label propName;
	private CheckBox checkBox = new CheckBox();
	private final ParentTreeItem parent;
	private ImageView statusIcon = new ImageView();
	private boolean disabled = false;
	private AnalysisStatus status = AnalysisStatus.NONE;
	
	private Result result;

	ChildTreeItem(String name, ParentTreeItem parent) {

		this.parent = parent;

		this.setAlignment(Pos.CENTER_LEFT);

		propName = new Label(name);

		checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
				if (!disabled) {
					parent.childCheckboxChanged();
				}
			}
		});

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				wasClicked();
			}
		});

		this.getChildren().add(checkBox);
		this.getChildren().add(new Separator(Orientation.VERTICAL));
		this.getChildren().add(propName);
	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}
	
	private void wasClicked() {
		if(result != null && result.isFinished()) {
			GUIController.getController().getResultField().setText(String.join("\n", result.getResult()));
			GUIController.getController().getMainTabPane().getSelectionModel().select(GUIController.getController().getResultTab());
		}
	}

	/**
	 * sets the value of the checkbox, while disabling the listener for the checkbox
	 * beforehand, and reenabling it afterwards
	 * 
	 * @param state
	 */
	public void setSelected(boolean state) {
		this.disabled = true;
		checkBox.setSelected(state);
		this.disabled = false;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void setStatus(AnalysisStatus status) {
		this.status = status;
		checkBox.setText(status.toString());
	}
}