package edu.pse.beast.gui.options;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OptionsGUIController {
	@FXML
	private AnchorPane topLevelAnchorpane;
	@FXML
	private ListView<OptionsCategory> optionsCategoryListview;
	@FXML
	private AnchorPane currentOptionDisplayAnchorpane;
	
	private Stage optionStage;
	
	@FXML
	public void initialize() {
		optionStage = new Stage();
		optionStage.setScene(new Scene(topLevelAnchorpane));
	}

	public void display() {
		optionStage.showAndWait();
	}
}
