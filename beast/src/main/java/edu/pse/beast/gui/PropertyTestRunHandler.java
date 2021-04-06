package edu.pse.beast.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.elements.TestRunTabController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class PropertyTestRunHandler {
	private final String fxmlResourceString = "/edu/pse/beast/SinglePropertyTestGui.fxml";

	private TabPane propertyTestRunPane;

	private Button propertyTestStartButton;
	private Button propertyTestStopButton;
	private Button addPropertyTestRunButton;
	private Button removePropertyTestRunButton;

	private List<PropertyTestRunParameters> testRunParams = new ArrayList<>();

	private CElectionDescription currentDescr;
	private PreAndPostConditionsDescription currentPropDescr;

	private FXMLLoader loader = new FXMLLoader(
			getClass().getResource(fxmlResourceString));

	public PropertyTestRunHandler(TabPane propertyTestRunPane,
			Button propertyTestStartButton, Button propertyTestStopButton,
			Button addPropertyTestRunButton,
			Button removePropertyTestRunButton) {
		this.propertyTestRunPane = propertyTestRunPane;
		this.propertyTestStartButton = propertyTestStartButton;
		this.propertyTestStopButton = propertyTestStopButton;
		this.addPropertyTestRunButton = addPropertyTestRunButton;
		this.removePropertyTestRunButton = removePropertyTestRunButton;

		addPropertyTestRunButton.setOnAction(e -> {
			try {
				createNewRun();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	public void handleNewElectionDescription(CElectionDescription descr) {
		this.currentDescr = descr;
	}

	public void handleNewPropDescr(PreAndPostConditionsDescription propDescr) {
		this.currentPropDescr = propDescr;
	}

	private void createNewRun() throws IOException {
		TextField nameField = new TextField();
		Optional<String> res = DialogHelper
				.generateDialog(List.of("name"), List.of(nameField))
				.showAndWait();
		if (res.isPresent()) {
			PropertyTestRunParameters trParam = new PropertyTestRunParameters();
			String name = nameField.getText();
			trParam.setName(name);
			trParam.setDescr(currentDescr);
			trParam.setPropDescr(currentPropDescr);
			testRunParams.add(trParam);
			displayTestRunParameters();
		}
	}

	private void displayTestRunParameters() throws IOException {
		propertyTestRunPane.getTabs().clear();
		for (PropertyTestRunParameters trp : testRunParams) {
			Tab t = new Tab(trp.getName());

			TestRunTabController controller = new TestRunTabController(trp);
			loader.setController(controller);

			Node r = (Node) loader.load();

			t.setContent(r);

			propertyTestRunPane.getTabs().add(t);
		}
	}

}
