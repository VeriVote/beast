package edu.pse.beast.gui.configurationeditor.configuration.cbmc;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.gui.workspace.BeastWorkspace;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CheckConfigurationGUIController {
    private static final int MIN_SPIN = 0;
    private static final int MAX_SPIN = 500;

    @FXML
    private AnchorPane topLevelAnchorPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<CElectionDescription> descrChoiceBox;
    @FXML
    private Button loadDescrButton;
    @FXML
    private ChoiceBox<PropertyDescription> propDescrChoiceBox;
    @FXML
    private Button loadPropDescrButton;
    @FXML
    private Spinner<Integer> minVoters;
    @FXML
    private Spinner<Integer> maxVoters;
    @FXML
    private Spinner<Integer> minCandidates;
    @FXML
    private Spinner<Integer> maxCandidates;
    @FXML
    private Spinner<Integer> minSeats;
    @FXML
    private Spinner<Integer> maxSeats;

    @FXML
    private Button updateFilesButton;
    @FXML
    private Button createRunsButton;
    @FXML
    private CheckBox startCreatedChecksCheckbox;

    private BeastWorkspace beastWorkspace;

    private Configuration currentConfig;

    public CheckConfigurationGUIController(final BeastWorkspace workspace) {
        this.beastWorkspace = workspace;
    }

    public final AnchorPane getTopLevelAnchorPane() {
        return topLevelAnchorPane;
    }

    private void updateView() {
        nameTextField.setText(currentConfig.getName());
        descrChoiceBox.getSelectionModel().select(currentConfig.getDescr());
        propDescrChoiceBox.getSelectionModel().select(currentConfig.getPropDescr());
        minVoters.getValueFactory().setValue(currentConfig.getMinVoters());
        maxVoters.getValueFactory().setValue(currentConfig.getMaxVoters());
        minCandidates.getValueFactory().setValue(currentConfig.getMinCands());
        maxCandidates.getValueFactory().setValue(currentConfig.getMaxCands());
        minSeats.getValueFactory().setValue(currentConfig.getMinSeats());
        maxSeats.getValueFactory().setValue(currentConfig.getMaxSeats());
        startCreatedChecksCheckbox.setSelected(currentConfig.getStartRunsOnCreation());
    }

    public final void display(final Configuration config) {
        currentConfig = config;
        updateView();
    }

    @FXML
    private void initialize() {
        minVoters.setValueFactory(new IntegerSpinnerValueFactory(MIN_SPIN, MAX_SPIN));
        minVoters.getValueFactory().valueProperty().addListener((e, o, n) -> {
            currentConfig.setMinVoters(minVoters.getValue());
            updateView();
        });

        maxVoters.setValueFactory(new IntegerSpinnerValueFactory(MIN_SPIN, MAX_SPIN));
        maxVoters.getValueFactory().valueProperty().addListener((e, o, n) -> {
            currentConfig.setMaxVoters(maxVoters.getValue());
            updateView();
        });

        minCandidates.setValueFactory(new IntegerSpinnerValueFactory(MIN_SPIN, MAX_SPIN));
        minCandidates.getValueFactory().valueProperty()
                .addListener((e, o, n) -> {
                    currentConfig.setMinCands(minCandidates.getValue());
                    updateView();
                });

        maxCandidates.setValueFactory(new IntegerSpinnerValueFactory(MIN_SPIN, MAX_SPIN));
        maxCandidates.getValueFactory().valueProperty()
                .addListener((e, o, n) -> {
                    currentConfig.setMaxCands(maxCandidates.getValue());
                    updateView();
                });

        minSeats.setValueFactory(new IntegerSpinnerValueFactory(MIN_SPIN, MAX_SPIN));
        minSeats.getValueFactory().valueProperty().addListener((e, o, n) -> {
            currentConfig.setMinSeats(minSeats.getValue());
            updateView();
        });

        maxSeats.setValueFactory(new IntegerSpinnerValueFactory(MIN_SPIN, MAX_SPIN));
        maxSeats.getValueFactory().valueProperty().addListener((e, o, n) -> {
            currentConfig.setMaxSeats(maxSeats.getValue());
            updateView();
        });

        startCreatedChecksCheckbox.selectedProperty()
                .addListener((o, oldVal, newVal) -> {
                    currentConfig.setStartRunsOnCreation(newVal);
                });

        createRunsButton.setOnAction(ae -> {
            beastWorkspace.createRunsAndAddToConfig(currentConfig);
        });

        descrChoiceBox.getItems().addAll(beastWorkspace.getLoadedDescrs());
        propDescrChoiceBox.getItems()
                .addAll(beastWorkspace.getLoadedPropDescrs());

        updateFilesButton.setOnAction(e -> {
            try {
                beastWorkspace.updateFilesForRuns(currentConfig);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}
