package edu.pse.beast.gui.testconfigeditor;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TestConfigCategoryGUIController
		implements WorkspaceUpdateListener {
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
	private Button createTestConfigButton;
	@FXML
	private ListView<TestConfiguration> testConfigListView;

	private final String descrSortCrit;

	private BeastWorkspace beastWorkspace;

	private String currentCategory;

	public TestConfigCategoryGUIController(BeastWorkspace beastWorkspace,
			String descrSortCrit) {
		this.beastWorkspace = beastWorkspace;
		this.descrSortCrit = descrSortCrit;
		beastWorkspace.registerUpdateListener(this);
		currentCategory = descrSortCrit;
	}

	@FXML
	public void initialize() {
		createTestConfigButton.setOnAction(e -> {
			createTestConfig();
		});
	}

	private void createTestConfig() {
		String name = nameTextField.getText();
		CElectionDescription descr = descrChoiceBox.getSelectionModel()
				.getSelectedItem();
		PreAndPostConditionsDescription propDescr = propDescrChoiceBox
				.getSelectionModel().getSelectedItem();
		beastWorkspace.createTestConfig(name, descr, propDescr);
	}

	public AnchorPane getTopLevelAnchorPane() {
		return topLevelAnchorPane;
	}

	private void updateView() {
		descrChoiceBox.getItems().clear();
		descrChoiceBox.getItems().addAll(beastWorkspace.getLoadedDescrs());
		propDescrChoiceBox.getItems().clear();
		propDescrChoiceBox.getItems()
				.addAll(beastWorkspace.getLoadedPropDescrs());

		testConfigListView.getItems().clear();
		if (currentCategory.equals(descrSortCrit)) {
			Map<CElectionDescription, List<TestConfiguration>> map = beastWorkspace
					.getConfigsByElectionDescription();
			for (CElectionDescription descr : map.keySet()) {
				for (TestConfiguration tc : map.get(descr)) {
					testConfigListView.getItems().add(tc);
				}
			}
		} else {
			Map<PreAndPostConditionsDescription, List<TestConfiguration>> map = beastWorkspace
					.getConfigsByPropertyDescription();
			for (PreAndPostConditionsDescription propDescr : map.keySet()) {
				for (TestConfiguration tc : map.get(propDescr)) {
					testConfigListView.getItems().add(tc);
				}
			}
		}
	}

	public void display(String category) {
		currentCategory = category;
		updateView();
	}

	@Override
	public void handleWorkspaceUpdateGeneric() {
		updateView();
	}
	
	@Override
	public void handleAddedTestConfig(TestConfiguration tc) {
		updateView();
	}

}
