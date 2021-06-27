package edu.pse.beast.gui.options;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProcessHandlerWindowsOptions extends OptionsCategory {

	@FXML
	private AnchorPane topLevelAnchorpane;
	@FXML
	private TextField vsDevCmdPathTextField;
	@FXML
	private Button vsDevCmdButton;
	
	private String fxml = "/edu/pse/beast/processHandlerWindowsOptions.fxml";	
	private FXMLLoader fxmlLoader = new FXMLLoader(
			getClass().getResource(fxml));

	public ProcessHandlerWindowsOptions() throws IOException {
		super(OptionsCategoryType.PROCESS_HANDLER_WINDOWS);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	@Override
	public void displayOptions(AnchorPane currentOptionDisplayAnchorpane) {
		displayOptionsAnchorpane(currentOptionDisplayAnchorpane, topLevelAnchorpane);
	}

}
