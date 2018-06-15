package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.propertychecker.CBMCResult;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.ResultPresenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class ChildTreeItem extends CustomTreeItem {

	private Label propName;
	private CheckBox checkBox = new CheckBox();
	private final ParentTreeItem parent;
	private ImageView statusIcon = new ImageView();
	private boolean disabled = false;
	private AnalysisStatus status = AnalysisStatus.NONE;

	protected Result result = null;

	ChildTreeItem(ChildTreeItemValues values, ParentTreeItem parent) {
		this.parent = parent;
		this.checkBox.setSelected(values.checkBoxStatus);
		this.propName = new Label(values.propertyName);
		this.disabled = values.disabled;

		init();
	}

	ChildTreeItem(String name, ParentTreeItem parent) {

		this.parent = parent;
		propName = new Label(name);
		init();
	}

	private void init() {
		this.setAlignment(Pos.CENTER_LEFT);

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

		parent.wasClicked(false);

		if (result != null && result.isFinished()) {
			if (!result.isValid()) {
			} else {
				if (result.isSuccess()) {
					GUIController.getController().getResultField().setText("ASSERTION HOLDS");
					GUIController.getController().getMainTabPane().getSelectionModel()
							.select(GUIController.getController().getResultTab());
				} else {
					ResultPresenter.presentFailureExample(result);
					
					
					GUIController.getController().getResultField().setText(String.join("\n", result.getResult()));
					GUIController.getController().getMainTabPane().getSelectionModel()
							.select(GUIController.getController().getResultTab());
				}
			}
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
		this.update();
	}

	public void setStatus(AnalysisStatus status) {
		this.status = status;
		checkBox.setText(status.toString());
	}

	public Result getResult() {
		return result;
	}

	/**
	 * notifies the child object that its result object changed, so it has to update
	 * its gui
	 */
	public void update() {

	}

	public abstract void resetResult(Result result);

	public abstract AnalysisType getAnalysisType();

	public PreAndPostConditionsDescription getPreAndPostProperties() {
		return parent.getPreAndPostPropertie();
	}

	public void setPresentable() {

		if (result != null) {
			if (!result.isValid()) {
				this.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
			} else {
				if (result.isSuccess()) {
					this.setBackground(
							new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
				} else {
					this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
				}
			}
		}

	}

	public void resetPresentable() {
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public ChildTreeItemValues getValues() {
		return new ChildTreeItemValues(propName.getText(), checkBox.isSelected(), false, status, result);
	}
}