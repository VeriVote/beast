package edu.pse.beast.gui.options;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.savingloading.options.OptionsSaverLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OptionsGUIController {
	@FXML
	private AnchorPane topLevelAnchorpane;
	@FXML
	private ListView<OptionsCategoryGUI> optionsCategoryListview;
	@FXML
	private AnchorPane currentOptionDisplayAnchorpane;
	
	private Stage optionStage;
	
	private List<OptionsCategoryGUI> categories;	
	
	private File optionsSaveFile;
	
	public OptionsGUIController(List<OptionsCategoryGUI> categories, File optionsSaveFile) {
		this.categories = categories;
		this.optionsSaveFile = optionsSaveFile;
		
		for(OptionsCategoryGUI cat : categories) {
			cat.setOptionsGUIController(this);
		}
	}
	
	public List<OptionsCategoryGUI> getCategories() {
		return categories;
	}

	@FXML
	public void initialize() {
		for(OptionsCategoryGUI cat : categories) {
			optionsCategoryListview.getItems().add(cat);
		}
		optionsCategoryListview.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> {
			displayCategory(n);
		});
		
		optionStage = new Stage();
		optionStage.setScene(new Scene(topLevelAnchorpane));
	}

	private void displayCategory(OptionsCategoryGUI category) {
		currentOptionDisplayAnchorpane.getChildren().clear();
		category.displayOptions(currentOptionDisplayAnchorpane);
	}

	public void display() {
		optionStage.showAndWait();
	}
	
	public void saveOptions() {
		try {
			OptionsSaverLoader.saveOptions(optionsSaveFile, categories);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
