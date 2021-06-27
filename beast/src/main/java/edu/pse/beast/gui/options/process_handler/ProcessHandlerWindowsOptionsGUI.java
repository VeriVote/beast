package edu.pse.beast.gui.options.process_handler;

import java.io.IOException;

import edu.pse.beast.gui.options.OptionsCategoryGUI;
import edu.pse.beast.gui.options.OptionsCategoryType;
import edu.pse.beast.gui.processHandler.CBMCProcessHandlerCreator;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProcessHandlerWindowsOptionsGUI extends OptionsCategoryGUI {

	@FXML
	private AnchorPane topLevelAnchorpane;
	@FXML
	private TextField vsDevCmdPathTextField;
	@FXML
	private Button vsDevCmdButton;
	
	private String fxml = "/edu/pse/beast/processHandlerWindowsOptions.fxml";	
	private FXMLLoader fxmlLoader = new FXMLLoader(
			getClass().getResource(fxml));
	
	private CBMCProcessHandlerCreator cbmcProcessHandlerCreator;

	public ProcessHandlerWindowsOptionsGUI(CBMCProcessHandlerCreator cbmcProcessHandlerCreator) throws IOException {
		super(OptionsCategoryType.PROCESS_HANDLER_WINDOWS);
		this.cbmcProcessHandlerCreator = cbmcProcessHandlerCreator;
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}
	
	@FXML
	public void initialize() {
		vsDevCmdPathTextField.setText(cbmcProcessHandlerCreator.getVsDevCmdPath());
		vsDevCmdPathTextField.setEditable(false);
		vsDevCmdButton.setOnAction(e -> askForProcessHandler());
	}

	private void askForProcessHandler() {
		cbmcProcessHandlerCreator.askUserForCBMCProcessHandler();
		vsDevCmdPathTextField.setText(cbmcProcessHandlerCreator.getVsDevCmdPath());
		optionsGUIController.optionUpdated();
	}

	@Override
	public void displayOptions(AnchorPane currentOptionDisplayAnchorpane) {
		displayOptionsAnchorpane(currentOptionDisplayAnchorpane, topLevelAnchorpane);
	}
	
	public CBMCProcessHandlerCreator getCbmcProcessHandlerCreator() {
		return cbmcProcessHandlerCreator;
	}

}
