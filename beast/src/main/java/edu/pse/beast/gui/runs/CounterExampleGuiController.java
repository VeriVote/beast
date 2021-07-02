package edu.pse.beast.gui.runs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCAssignmentType;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCCounterExample;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCStructAssignment;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class CounterExampleGuiController {
	@FXML
	private AnchorPane topLevelAnchorPane;
	@FXML
	private CheckBox votesCheckbox;
	@FXML
	private CheckBox generatedVotesCheckbox;
	@FXML
	private CheckBox resultsCheckbox;
	@FXML
	private CheckBox generatedResultsCheckbox;
	@FXML
	private ListView<CBMCStructAssignment> overviewListView;
	@FXML
	private AnchorPane detailsAnchorPane;

	private CBMCCounterExample currentExample;

	public AnchorPane display(CBMCCounterExample example) {
		currentExample = example;
		display();
		return topLevelAnchorPane;
	}

	private void display() {
		Set<CBMCAssignmentType> types = new HashSet<>();
		if (votesCheckbox.isSelected()) {
			types.add(CBMCAssignmentType.VOTE);
		}
		if (generatedVotesCheckbox.isSelected()) {
			types.add(CBMCAssignmentType.GENERATED_VOTE);
		}
		if (resultsCheckbox.isSelected()) {
			types.add(CBMCAssignmentType.ELECT);
		}
		if (generatedResultsCheckbox.isSelected()) {
			types.add(CBMCAssignmentType.GENERATED_ELECT);
		}
		List<CBMCStructAssignment> asses = currentExample.getAssignments(types);

		overviewListView.getItems().clear();
		overviewListView.getItems().addAll(asses);
	}
	
	private void displayOnChange(CheckBox cb) {
		cb.setOnAction(e -> {
			display();
		});
	}
	
	@FXML
	public void initialize() {
		displayOnChange(votesCheckbox);
		displayOnChange(generatedVotesCheckbox);
		displayOnChange(resultsCheckbox);
		displayOnChange(generatedResultsCheckbox);
	}

}
