package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.ResultPresenter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ResultTreeItem extends CustomTreeItem {

	private final Result result;
	private final ChildTreeItem owner;
	
	private Label name = new Label("Result");
	private Button button = new Button("delete");
	private ImageView statusIcon = new ImageView();
	
	public ResultTreeItem(Result result, ChildTreeItem owner) {
		this.result = result;
		this.owner = owner;
		
		this.result.setOwner(this);
		
		init();
	}
	
	private void init() {
		this.setAlignment(Pos.CENTER_LEFT);
		
		button.setOnAction((event) -> {
			owner.deleteResult(this);
		});

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				wasClicked();
			}
		});

		this.getChildren().add(name);
		this.getChildren().add(new Separator(Orientation.VERTICAL));
		this.getChildren().add(button);
	}

	public void wasClicked() {
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

	public void setPresentable() {
		if (result != null && result.isFinished()) {
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

	public Result getResult() {
		return result;
	}

	public ChildTreeItem getOwner() {
		return owner;
	}

}
