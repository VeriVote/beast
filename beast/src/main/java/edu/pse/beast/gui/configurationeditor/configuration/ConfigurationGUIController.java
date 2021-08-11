package edu.pse.beast.gui.configurationeditor.configuration;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PreAndPostConditions;
import edu.pse.beast.gui.workspace.BeastWorkspace;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ConfigurationGUIController {
    @FXML
    private AnchorPane topLevelAnchorPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<CElectionDescription> descrChoiceBox;
    @FXML
    private Button loadDescrButton;
    @FXML
    private ChoiceBox<PreAndPostConditions> propDescrChoiceBox;
    @FXML
    private Button loadPropDescrButton;

    private BeastWorkspace beastWorkspace;

    public ConfigurationGUIController(final BeastWorkspace workspace) {
        this.beastWorkspace = workspace;
    }

    public final AnchorPane getTopLevelAnchorPane() {
        return topLevelAnchorPane;
    }

    public final void display(final ConfigurationBatch config) {
        nameTextField.setText(config.getName());
        descrChoiceBox.getItems().clear();
        descrChoiceBox.getItems().addAll(beastWorkspace.getLoadedDescrs());
        descrChoiceBox.getSelectionModel().select(config.getDescr());
        propDescrChoiceBox.getItems().clear();
        propDescrChoiceBox.getItems()
                .addAll(beastWorkspace.getLoadedPropDescrs());
        propDescrChoiceBox.getSelectionModel().select(config.getPropDescr());
    }

    @FXML
    private void initialize() {
        descrChoiceBox.getItems().addAll(beastWorkspace.getLoadedDescrs());
        propDescrChoiceBox.getItems()
                .addAll(beastWorkspace.getLoadedPropDescrs());
    }
}
