package edu.pse.beast.gui.elements;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.PropertyTestRunParameters;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TestRunTabController {
	@FXML
	private Label descrNameLabel;

	private PropertyTestRunParameters parameters;

	public TestRunTabController(PropertyTestRunParameters parameters) {
		this.parameters = parameters;
	}
	
	@FXML
	private void initialize() {
		descrNameLabel.setText("asdasdasdasdasd");
	}

}
