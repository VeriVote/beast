package edu.pse.beast.gui.testconfigeditor;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
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
	@FXML
	private Button gotoConfigButton;
	@FXML
	private Button deleteConfigButton;

	private final String descrSortCrit;

	private BeastWorkspace beastWorkspace;

	private String currentCategory;

	private TreeView<TestConfigTreeItemSuper> tcTreeView;

	public TestConfigCategoryGUIController(BeastWorkspace beastWorkspace,
			String descrSortCrit,
			TreeView<TestConfigTreeItemSuper> tcTreeView) {
		this.beastWorkspace = beastWorkspace;
		this.descrSortCrit = descrSortCrit;
		beastWorkspace.registerUpdateListener(this);
		currentCategory = descrSortCrit;
		this.tcTreeView = tcTreeView;
	}

	@FXML
	public void initialize() {
		createTestConfigButton.setOnAction(e -> {
			createTestConfig();
		});
		gotoConfigButton.setOnAction(e -> {
			tcTreeView.getSelectionModel().select(TestConfigTreeViewHelper
					.getItem(testConfigListView.getSelectionModel()
							.getSelectedItem().getCBMCTestConfigs().get(0),
							tcTreeView.getRoot()));
		});
		deleteConfigButton.setOnAction(e -> {
			beastWorkspace.deleteTestConfig(
					testConfigListView.getSelectionModel().getSelectedItem());
		});
		testConfigListView.getSelectionModel().selectedItemProperty()
				.addListener((ob, o, n) -> {
					if (n == null) {
						gotoConfigButton.setDisable(true);
						deleteConfigButton.setDisable(true);
					} else {
						gotoConfigButton.setDisable(false);
						deleteConfigButton.setDisable(false);
					}
					if (n == o) {
						System.out.println("anotha oen");
					}
				});
		loadDescrButton.setOnAction(e -> {
			beastWorkspace.letUserLoadDescr();
		});

		loadPropDescrButton.setOnAction(e -> {
			beastWorkspace.letUserLoadPropDescr();
		});
	}

	private void createTestConfig() {
		String name = nameTextField.getText();
		CElectionDescription descr = descrChoiceBox.getSelectionModel()
				.getSelectedItem();
		PreAndPostConditionsDescription propDescr = propDescrChoiceBox
				.getSelectionModel().getSelectedItem();
		if (!name.isEmpty() && descr != null && propDescr != null)
			beastWorkspace.createTestConfig(name, descr, propDescr);
	}

	public AnchorPane getTopLevelAnchorPane() {
		return topLevelAnchorPane;
	}

	private void updateView() {
		descrChoiceBox.getItems().clear();
		descrChoiceBox.getItems().addAll(beastWorkspace.getLoadedDescrs());
		descrChoiceBox.getSelectionModel().select(0);

		propDescrChoiceBox.getItems().clear();
		propDescrChoiceBox.getItems()
				.addAll(beastWorkspace.getLoadedPropDescrs());
		propDescrChoiceBox.getSelectionModel().select(0);

		testConfigListView.getItems().clear();
		if (beastWorkspace.getConfigsByElectionDescription().isEmpty()) {
			gotoConfigButton.setDisable(true);
			deleteConfigButton.setDisable(true);
			return;
		}
		gotoConfigButton.setDisable(false);
		deleteConfigButton.setDisable(false);

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

		testConfigListView.getSelectionModel().selectFirst();
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

	@Override
	public void handleAddedPropDescr(
			PreAndPostConditionsDescription propDescr) {
		updateView();
	}

}
