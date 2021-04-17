package edu.pse.beast.gui.testruneditor.testconfig.cbmc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CBMCTestConfigGuiController {
	@FXML
	private AnchorPane topLevelAnchorPane;
	@FXML
	private TextField nameTextField;
	@FXML
	private ChoiceBox<String> descrChoiceBox;
	@FXML
	private Button loadDescrButton;
	@FXML
	private ChoiceBox<String> propDescrChoiceBox;
	@FXML
	private Button loadPropDescrButton;
	@FXML
	private Spinner<Integer> minVoter;
	@FXML
	private TextField maxVoter;
	@FXML
	private TextField minCandidates;
	@FXML
	private TextField maxCandidates;
	@FXML
	private TextField minSeats;
	@FXML
	private TextField maxSeats;

	public AnchorPane getTopLevelAnchorPane() {
		return topLevelAnchorPane;
	}


}
