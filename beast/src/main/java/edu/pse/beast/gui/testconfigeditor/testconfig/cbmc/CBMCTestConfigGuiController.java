package edu.pse.beast.gui.testconfigeditor.testconfig.cbmc;

import java.io.IOException;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CBMCTestConfigGuiController {
	@FXML
	private AnchorPane topLevelAnchorPane;
	@FXML
	private TextField nameTextField;
	@FXML
	private ChoiceBox<CElectionDescription> descrChoiceBox;
	@FXML
	private Button loadDescrButton;
	@FXML
	private ChoiceBox<PreAndPostConditionsDescription> propDescrChoiceBox;
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
	private Button createTestRunsButton;
	@FXML
	private CheckBox startCreatedTestsCheckbox;		
	
	private BeastWorkspace beastWorkspace;
	
	private CBMCPropertyTestConfiguration currentConfig;

	public CBMCTestConfigGuiController(BeastWorkspace beastWorkspace) {
		this.beastWorkspace = beastWorkspace;
	}

	public AnchorPane getTopLevelAnchorPane() {
		return topLevelAnchorPane;
	}

	public void display(CBMCPropertyTestConfiguration config) {
		currentConfig = config;
		
		nameTextField.setText(config.getName());
		descrChoiceBox.getSelectionModel().select(config.getDescr());
		propDescrChoiceBox.getSelectionModel().select(config.getPropDescr());
		
		minVoters.getValueFactory().setValue(config.getMinVoters());
		maxVoters.getValueFactory().setValue(config.getMaxVoters());
		minCandidates.getValueFactory().setValue(config.getMinCands());
		maxCandidates.getValueFactory().setValue(config.getMaxCands());
		minSeats.getValueFactory().setValue(config.getMinSeats());
		maxSeats.getValueFactory().setValue(config.getMaxSeats());
		
		startCreatedTestsCheckbox.setSelected(config.getStartRunsOnCreation());
	}
	
	@FXML
	private void initialize() {
		minVoters.setValueFactory(new IntegerSpinnerValueFactory(0, 500));
		maxVoters.setValueFactory(new IntegerSpinnerValueFactory(0, 500));
		minCandidates.setValueFactory(new IntegerSpinnerValueFactory(0, 500));
		maxCandidates.setValueFactory(new IntegerSpinnerValueFactory(0, 500));
		minSeats.setValueFactory(new IntegerSpinnerValueFactory(0, 500));
		maxSeats.setValueFactory(new IntegerSpinnerValueFactory(0, 500));
		
		descrChoiceBox.getItems().addAll(beastWorkspace.getLoadedDescrs());
		propDescrChoiceBox.getItems().addAll(beastWorkspace.getLoadedPropDescrs());
		
		startCreatedTestsCheckbox.selectedProperty().addListener((o, oldVal, newVal) -> {
			currentConfig.setStartRunsOnCreation(newVal);
		});
		
		createTestRunsButton.setOnAction(ae -> {
			beastWorkspace.createCBMCTestRunsAndAddToConfig(currentConfig);
		});
		
		updateFilesButton.setOnAction(e -> {
			try {
				beastWorkspace.updateFilesForRuns(currentConfig);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	
}
