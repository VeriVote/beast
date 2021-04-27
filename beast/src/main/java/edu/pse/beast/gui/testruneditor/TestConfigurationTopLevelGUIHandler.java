package edu.pse.beast.gui.testruneditor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfigGuiController;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCTestConfigGuiController;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRunGuiController;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigCBMCTreeItem;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigCategoryTreeItem;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigTreeItem;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigTreeItemType;
import edu.pse.beast.gui.testruneditor.treeview.TestRunCBMCTreeItem;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class TestConfigurationTopLevelGUIHandler
		implements
			WorkspaceUpdateListener {

	private final String testConfigDetailFXML = "/edu/pse/beast/testConfigDetailGUI.fxml";
	private final String cbmcTestConfigDetailFXML = "/edu/pse/beast/cbmcTestConfigDetailGUI.fxml";
	private final String cbmcTestRunDetailFXML = "/edu/pse/beast/cbmcTestRunDetailGUI.fxml";

	private ChoiceBox<String> sortCriteriumChoiceBox;
	private TreeView<TestConfigTreeItemSuper> testConfigTreeView;
	private AnchorPane testConfigDetailsAnchorPane;

	private TreeItem<TestConfigTreeItemSuper> root = new TreeItem();

	private BeastWorkspace beastWorkspace;

	private TestConfigGuiController testConfigGuiController;
	private FXMLLoader testConfigFXMLLoader = new FXMLLoader(
			getClass().getResource(testConfigDetailFXML));

	private CBMCTestConfigGuiController cbmcTestConfigController;
	private FXMLLoader cbmcTestConfigFXMLLoader = new FXMLLoader(
			getClass().getResource(cbmcTestConfigDetailFXML));

	private CBMCTestRunGuiController cbmcTestRunGuiController;
	private FXMLLoader cbmcTestRunFXMLLoader = new FXMLLoader(
			getClass().getResource(cbmcTestRunDetailFXML));

	private Button startTestConfigButton;
	private Button stopTestConfigButton;

	private final String descrSortCrit = "Election Description";
	private final String propDescrSortCrit = "Property Description";
	private final String cbmcTestConfigHeading = "cbmc Properties";

	private String sortCriterium = descrSortCrit;

	public TestConfigurationTopLevelGUIHandler(Button startTestConfigButton,
			Button stopTestConfigButton,
			ChoiceBox<String> sortCriteriumChoiceBox,
			TreeView<TestConfigTreeItemSuper> testConfigTreeView,
			AnchorPane testConfigDetailsAnchorPane,
			BeastWorkspace beastWorkspace) throws IOException {
		this.beastWorkspace = beastWorkspace;
		beastWorkspace.registerUpdateListener(this);

		testConfigGuiController = new TestConfigGuiController(beastWorkspace);
		cbmcTestConfigController = new CBMCTestConfigGuiController(
				beastWorkspace);
		cbmcTestRunGuiController = new CBMCTestRunGuiController(beastWorkspace);

		this.testConfigDetailsAnchorPane = testConfigDetailsAnchorPane;

		this.sortCriteriumChoiceBox = sortCriteriumChoiceBox;
		this.testConfigTreeView = testConfigTreeView;

		testConfigTreeView.getSelectionModel().selectedItemProperty()
				.addListener((e, oldVal, newVal) -> {
					if (oldVal == null) {
						testConfigSelectionChanged(newVal.getValue());
					} else if (newVal == null) {
						testConfigSelectionChanged(oldVal.getValue());
					} else {
						testConfigSelectionChanged(newVal.getValue());
					}
				});
		testConfigTreeView.setShowRoot(false);
		testConfigTreeView.setRoot(root);

		testConfigFXMLLoader.setController(testConfigGuiController);
		testConfigFXMLLoader.load();

		cbmcTestConfigFXMLLoader.setController(cbmcTestConfigController);
		cbmcTestConfigFXMLLoader.load();

		cbmcTestRunFXMLLoader.setController(cbmcTestRunGuiController);
		cbmcTestRunFXMLLoader.load();

		setupSortCriteriumChoiceBox();

		handleWorkspaceUpdateGeneric();

		sortCriteriumChoiceBox.getSelectionModel().selectFirst();
	}

	private void setDetailAnchorPane(AnchorPane pane) {
		testConfigDetailsAnchorPane.getChildren().clear();
		testConfigDetailsAnchorPane.getChildren().add(pane);
		AnchorPane.setLeftAnchor(pane, 0d);
		AnchorPane.setRightAnchor(pane, 0d);
		AnchorPane.setTopAnchor(pane, 0d);
		AnchorPane.setBottomAnchor(pane, 0d);
	}

	private void testConfigSelectionChanged(TestConfigTreeItemSuper selected) {
		switch (selected.getType()) {
			case CATEGORY : {
				testConfigDetailsAnchorPane.getChildren().clear();
				break;
			}
			case TEST_CONFIG : {
				TestConfigTreeItem casted = (TestConfigTreeItem) selected;
				testConfigGuiController.display(casted.getTestConfig());
				setDetailAnchorPane(
						testConfigGuiController.getTopLevelAnchorPane());
				break;
			}
			case CBMC : {
				TestConfigCBMCTreeItem casted = (TestConfigCBMCTreeItem) selected;
				cbmcTestConfigController
						.display(casted.getCbmcPropertyTestConfiguration());
				setDetailAnchorPane(
						cbmcTestConfigController.getTopLevelAnchorPane());
				break;
			}
			case CBMC_RUN : {
				TestRunCBMCTreeItem casted = (TestRunCBMCTreeItem) selected;
				cbmcTestRunGuiController.display(casted.getRun());
				setDetailAnchorPane(
						cbmcTestRunGuiController.getTopLevelAnchorPane());
				break;
			}
		}
	}

	private void addCBMCRunItems(TreeItem<TestConfigTreeItemSuper> treeItem,
			CBMCPropertyTestConfiguration config) {
		treeItem.getChildren().clear();
		for (CBMCTestRun tr : config.getRuns()) {
			treeItem.getChildren()
					.add(new TreeItem<>(new TestRunCBMCTreeItem(tr)));
		}
	}

	private void updateTestConfigTreeView() {
		root.getChildren().clear();
		Map<String, List<TestConfiguration>> testConfigs;
		if (sortCriterium.equals(descrSortCrit)) {
			testConfigs = beastWorkspace.getConfigsByElectionDescriptionName();
		} else {// if (sortCriterium.equals(propDescrSortCrit)) {
			testConfigs = beastWorkspace.getConfigsByPropertyDescriptionName();
		}

		for (String name : testConfigs.keySet()) {
			TreeItem<TestConfigTreeItemSuper> parentItem = new TreeItem<>(
					new TestConfigCategoryTreeItem(name));

			List<TestConfiguration> testConfigsForParent = testConfigs
					.get(name);
			
			for (TestConfiguration testConfig : testConfigsForParent) {
				TreeItem<TestConfigTreeItemSuper> testConfigItem = new TreeItem<>(
						new TestConfigTreeItem(testConfig));
				Map<String, CBMCPropertyTestConfiguration> cbcmConfigs = testConfig
						.getCbmcTestConfigsByName();
				if (!cbcmConfigs.isEmpty()) {
					TreeItem<TestConfigTreeItemSuper> cbmcParentItem = new TreeItem<>(
							new TestConfigCategoryTreeItem("cbmc"));
					for (CBMCPropertyTestConfiguration cbmcConfig : cbcmConfigs
							.values()) {
						TreeItem<TestConfigTreeItemSuper> cbmcConfigItem = new TreeItem<>(
								new TestConfigCBMCTreeItem(cbmcConfig));
						addCBMCRunItems(cbmcConfigItem, cbmcConfig);
						cbmcParentItem.getChildren().add(cbmcConfigItem);
					}
					testConfigItem.getChildren().add(cbmcParentItem);
				}
				parentItem.getChildren().add(testConfigItem);
			}
			root.getChildren().add(parentItem);
		}
	}

	private void setupSortCriteriumChoiceBox() {
		sortCriteriumChoiceBox.getItems().add(descrSortCrit);
		sortCriteriumChoiceBox.getItems().add(propDescrSortCrit);

		sortCriteriumChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((e, oldVal, newVal) -> {
					sortCriterium = newVal;
					updateTestConfigTreeView();
				});
	}

	@Override
	public void handleWorkspaceUpdateGeneric() {
		updateTestConfigTreeView();
	}

	@Override
	public void handleWorkspaceUpdateAddedCBMCRuns(
			CBMCPropertyTestConfiguration config,
			List<CBMCTestRun> createdTestRuns) {
		TreeItem<TestConfigTreeItemSuper> item = TestConfigTreeViewHelper
				.getItem(config, root);
		for (CBMCTestRun r : createdTestRuns) {
			item.getChildren().add(new TreeItem<TestConfigTreeItemSuper>(
					new TestRunCBMCTreeItem(r)));
		}
	}
}
