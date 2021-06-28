package edu.pse.beast.gui.testconfigeditor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.gui.runs.CBMCTestRunGuiController;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfigGuiController;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfigGuiController;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigCBMCTreeItem;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigCategoryTreeItem;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItem;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemType;
import edu.pse.beast.gui.testconfigeditor.treeview.TestRunCBMCTreeItem;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class TestConfigTopLevelGUIHandler
		implements WorkspaceUpdateListener {

	private final String testConfigDetailFXML = "/edu/pse/beast/testConfigDetailGUI.fxml";
	private final String cbmcTestConfigDetailFXML = "/edu/pse/beast/cbmcTestConfigDetailGUI.fxml";
	private final String cbmcTestRunDetailFXML = "/edu/pse/beast/cbmcTestRunDetailGUI.fxml";
	private final String testConfigCategoryFXML = "/edu/pse/beast/categoryDetailGUI.fxml";

	private ChoiceBox<String> sortCriteriumChoiceBox;
	private TreeView<TestConfigTreeItemSuper> testConfigTreeView;
	private AnchorPane testConfigDetailsAnchorPane;

	private TreeItem<TestConfigTreeItemSuper> root;

	private BeastWorkspace beastWorkspace;

	private TestConfigCategoryGUIController categoryGUIController;

	private FXMLLoader testConfigCategoryFXMLLoader = new FXMLLoader(
			getClass().getResource(testConfigCategoryFXML));
	
	private TestConfigGuiController testConfigGuiController;
	private FXMLLoader testConfigFXMLLoader = new FXMLLoader(
			getClass().getResource(testConfigDetailFXML));

	private CBMCTestConfigGuiController cbmcTestConfigController;
	private FXMLLoader cbmcTestConfigFXMLLoader = new FXMLLoader(
			getClass().getResource(cbmcTestConfigDetailFXML));

	private CBMCTestRunGuiController cbmcTestRunGuiController;
	private FXMLLoader cbmcTestRunFXMLLoader = new FXMLLoader(
			getClass().getResource(cbmcTestRunDetailFXML));


	private final String descrSortCrit = "Election Description";
	private final String propDescrSortCrit = "Property Description";
	private final String cbmcTestConfigHeading = "cbmc Properties";

	private String sortCriterium = descrSortCrit;

	public TestConfigTopLevelGUIHandler(
			ChoiceBox<String> sortCriteriumChoiceBox,
			TreeView<TestConfigTreeItemSuper> testConfigTreeView,
			AnchorPane testConfigDetailsAnchorPane,
			BeastWorkspace beastWorkspace) throws IOException {
		this.beastWorkspace = beastWorkspace;
		beastWorkspace.registerUpdateListener(this);

		categoryGUIController = new TestConfigCategoryGUIController(beastWorkspace, descrSortCrit);
		testConfigGuiController = new TestConfigGuiController(beastWorkspace);
		cbmcTestConfigController = new CBMCTestConfigGuiController(
				beastWorkspace);
		cbmcTestRunGuiController = new CBMCTestRunGuiController(beastWorkspace, testConfigTreeView);

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
		
		
		testConfigTreeView.setShowRoot(true);
		
		testConfigCategoryFXMLLoader.setController(categoryGUIController);
		testConfigCategoryFXMLLoader.load();

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
		case CATEGORY: {
			TestConfigCategoryTreeItem casted = (TestConfigCategoryTreeItem) selected;
			categoryGUIController.display(casted.getCategory());
			setDetailAnchorPane(categoryGUIController.getTopLevelAnchorPane());
			break;
		}
		case TEST_CONFIG: {
			TestConfigTreeItem casted = (TestConfigTreeItem) selected;
			testConfigGuiController.display(casted.getTestConfig());
			setDetailAnchorPane(
					testConfigGuiController.getTopLevelAnchorPane());
			break;
		}
		case CBMC: {
			TestConfigCBMCTreeItem casted = (TestConfigCBMCTreeItem) selected;
			cbmcTestConfigController
					.display(casted.getCbmcPropertyTestConfiguration());
			setDetailAnchorPane(
					cbmcTestConfigController.getTopLevelAnchorPane());
			break;
		}
		case CBMC_RUN: {
			TestRunCBMCTreeItem casted = (TestRunCBMCTreeItem) selected;
			cbmcTestRunGuiController.display(casted.getRun());
			setDetailAnchorPane(
					cbmcTestRunGuiController.getTopLevelAnchorPane());
			break;
		}
		}
	}

	private void addCBMCRunItems(TreeItem<TestConfigTreeItemSuper> treeItem,
			CBMCTestConfiguration config) {
		treeItem.getChildren().clear();
		for (CBMCTestRun tr : config.getRuns()) {
			treeItem.getChildren()
					.add(new TreeItem<>(new TestRunCBMCTreeItem(tr)));
		}
	}

	private void updateTestConfigTreeView() {		
		root = new TreeItem<>(new TestConfigCategoryTreeItem(sortCriterium));
		testConfigTreeView.setRoot(root);
		
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
				Map<String, CBMCTestConfiguration> cbcmConfigs = testConfig
						.getCbmcTestConfigsByName();
				if (!cbcmConfigs.isEmpty()) {
					TreeItem<TestConfigTreeItemSuper> cbmcParentItem = new TreeItem<>(
							new TestConfigCategoryTreeItem("cbmc"));
					for (CBMCTestConfiguration cbmcConfig : cbcmConfigs
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
	public void handleAddedTestConfig(TestConfiguration tc) {
		updateTestConfigTreeView();
	}

	@Override
	public void handleWorkspaceUpdateAddedCBMCRuns(
			CBMCTestConfiguration config,
			List<CBMCTestRun> createdTestRuns) {
		TreeItem<TestConfigTreeItemSuper> item = TestConfigTreeViewHelper
				.getItem(config, root);
		for (CBMCTestRun r : createdTestRuns) {
			item.getChildren().add(new TreeItem<TestConfigTreeItemSuper>(
					new TestRunCBMCTreeItem(r)));
		}
	}
}
