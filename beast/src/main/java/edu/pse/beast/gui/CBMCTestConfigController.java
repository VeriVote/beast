package edu.pse.beast.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CBMCTestConfigController {
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
	@FXML
	private AnchorPane outputAnchorPane;

	public AnchorPane getTopLevelAnchorPane() {
		return topLevelAnchorPane;
	}

	public TextField getNameTextField() {
		return nameTextField;
	}

	public ChoiceBox<String> getDescrChoiceBox() {
		return descrChoiceBox;
	}

	public Button getLoadDescrButton() {
		return loadDescrButton;
	}

	public ChoiceBox<String> getPropDescrChoiceBox() {
		return propDescrChoiceBox;
	}

	public Button getLoadPropDescrButton() {
		return loadPropDescrButton;
	}

	public Spinner<Integer> getMinVoter() {
		return minVoter;
	}

	public TextField getMaxVoter() {
		return maxVoter;
	}

	public TextField getMinCandidates() {
		return minCandidates;
	}

	public TextField getMaxCandidates() {
		return maxCandidates;
	}

	public TextField getMinSeats() {
		return minSeats;
	}

	public TextField getMaxSeats() {
		return maxSeats;
	}

	public AnchorPane getOutputAnchorPane() {
		return outputAnchorPane;
	}
}
