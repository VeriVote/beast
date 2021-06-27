package edu.pse.beast.gui.options;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<OptionsCategory> categories;	
	
	public OptionsGUIController(List<OptionsCategory> categories) {
		this.categories = categories;
	}

	@FXML
	public void initialize() {
		for(OptionsCategory cat : categories) {
			optionsCategoryListview.getItems().add(cat);
		}
		optionsCategoryListview.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> {
			displayCategory(n);
		});
		
		optionStage = new Stage();
		optionStage.setScene(new Scene(topLevelAnchorpane));
	}

	private void displayCategory(OptionsCategory category) {
		currentOptionDisplayAnchorpane.getChildren().clear();
		category.displayOptions(currentOptionDisplayAnchorpane);
	}

	public void display() {
		optionStage.showAndWait();
	}
}
