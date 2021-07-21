package edu.pse.beast.gui.options.process_handler;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import edu.pse.beast.gui.options.OptionsCategoryGUI;
import edu.pse.beast.gui.options.OptionsCategoryType;
import edu.pse.beast.gui.processHandler.CBMCProcessHandlerCreator;

public class ProcessHandlerWindowsOptionsGUI extends OptionsCategoryGUI {

    @FXML
    private AnchorPane topLevelAnchorpane;
    @FXML
    private TextField vsDevCmdPathTextField;
    @FXML
    private Button vsDevCmdButton;

    private String fxml = "/edu/pse/beast/processHandlerWindowsOptions.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));

    private CBMCProcessHandlerCreator cbmcProcessHandlerCreator;

    public ProcessHandlerWindowsOptionsGUI(final CBMCProcessHandlerCreator procHandlerCreator)
            throws IOException {
        super(OptionsCategoryType.PROCESS_HANDLER_WINDOWS);
        this.cbmcProcessHandlerCreator = procHandlerCreator;
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    @FXML
    public final void initialize() {
        vsDevCmdPathTextField.setText(cbmcProcessHandlerCreator.getVsDevCmdPath());
        vsDevCmdPathTextField.setEditable(false);
        vsDevCmdButton.setOnAction(e -> askForProcessHandler());
    }

    private void askForProcessHandler() {
        cbmcProcessHandlerCreator.askUserForCBMCProcessHandler();
        vsDevCmdPathTextField.setText(cbmcProcessHandlerCreator.getVsDevCmdPath());
        getOptionsGUIController().saveOptions();
    }

    @Override
    public final void displayOptions(final AnchorPane currentOptionDisplayAnchorpane) {
        displayOptionsAnchorpane(currentOptionDisplayAnchorpane, topLevelAnchorpane);
    }

    public final CBMCProcessHandlerCreator getCbmcProcessHandlerCreator() {
        return cbmcProcessHandlerCreator;
    }
}
