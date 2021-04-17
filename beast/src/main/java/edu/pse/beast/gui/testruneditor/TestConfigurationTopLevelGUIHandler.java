package edu.pse.beast.gui.testruneditor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.gui.testruneditor.testconfig.TestConfigGuiController;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCTestConfigGuiController;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigCBMCTreeItem;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigCategoryTreeItem;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigTreeItem;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateEvent;
import edu.pse.beast.gui.workspace.WorkspaceUpdateEventType;
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

	private ChoiceBox<String> sortCriteriumChoiceBox;
	private TreeView<TestConfigTreeItemSuper> testConfigTreeView;
	private AnchorPane testConfigDetailsAnchorPane;

	private TreeItem<TestConfigTreeItemSuper> root = new TreeItem();

	private BeastWorkspace beastWorkspace;

	private TestConfigGuiController testConfigGuiController;
	private FXMLLoader testConfigFXMLLoader = new FXMLLoader(
			getClass().getResource(testConfigDetailFXML));

	private CBMCTestConfigGuiController cbmcTestConfigController = new CBMCTestConfigGuiController();
	private FXMLLoader cbmcTestConfigFXMLLoader = new FXMLLoader(
			getClass().getResource(cbmcTestConfigDetailFXML));

	private Button startTestConfigButton;
	private Button stopTestConfigButton;

	private final String descrSortCrit = "Election Description";
	private final String propDescrSortCrit = "Property Description";
	private final String cbmcTestConfigHeading = "cbmc Properties";

	public TestConfigurationTopLevelGUIHandler(Button startTestConfigButton,
			Button stopTestConfigButton,
			ChoiceBox<String> sortCriteriumChoiceBox,
			TreeView<TestConfigTreeItemSuper> testConfigTreeView,
			AnchorPane testConfigDetailsAnchorPane,
			BeastWorkspace beastWorkspace) throws IOException {
		this.beastWorkspace = beastWorkspace;
		testConfigGuiController = new TestConfigGuiController(beastWorkspace);
		
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

		setupSortCriteriumChoiceBox();

		handleWorkspaceUpdate(
				WorkspaceUpdateEvent.fromType(WorkspaceUpdateEventType.ALL));

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

				break;
			}
			case TEST_CONFIG : {
				TestConfigTreeItem casted = (TestConfigTreeItem) selected;
				testConfigGuiController.display(casted.getTestConfig());
				setDetailAnchorPane(testConfigGuiController.getTopLevelAnchorPane());
				break;
			}
			case CBMC : {
				TestConfigCBMCTreeItem casted = (TestConfigCBMCTreeItem) selected;
				break;
			}
		}
	}

	private void updateTestConfigTreeView(
			Map<String, List<TestConfiguration>> testConfigs) {
		for (String k : testConfigs.keySet()) {
			TreeItem<TestConfigTreeItemSuper> parentItem = new TreeItem<>(
					new TestConfigCategoryTreeItem(k));
			List<TestConfiguration> testConfigsForParent = testConfigs.get(k);
			for (TestConfiguration testConfig : testConfigsForParent) {
				TreeItem<TestConfigTreeItemSuper> testConfigItem = new TreeItem<>(
						new TestConfigTreeItem(testConfig));
				List<CBMCPropertyTestConfiguration> cbcmConfigs = testConfig
						.getCbmcTestConfigs();
				if (!cbcmConfigs.isEmpty()) {
					TreeItem<TestConfigTreeItemSuper> cbmcParentItem = new TreeItem<>(
							new TestConfigCategoryTreeItem("cbmc"));
					for (CBMCPropertyTestConfiguration cbmcConfig : cbcmConfigs) {
						TreeItem<TestConfigTreeItemSuper> cbmcConfigItem = new TreeItem<>(
								new TestConfigCBMCTreeItem(cbmcConfig));
						cbmcParentItem.getChildren().add(cbmcConfigItem);
					}
					testConfigItem.getChildren().add(cbmcParentItem);
				}
				parentItem.getChildren().add(testConfigItem);
			}
			root.getChildren().add(parentItem);
		}
	}

	private void sortCriteriumChoiceBoxSelectionChanged(String oldVal,
			String newVal) {
		root.getChildren().clear();
		if (newVal.equals(descrSortCrit)) {
			updateTestConfigTreeView(
					beastWorkspace.getConfigsByElectionDescription());
		} else if (newVal.equals(propDescrSortCrit)) {
			updateTestConfigTreeView(
					beastWorkspace.getConfigsByPropertyDescription());
		}
	}

	private void setupSortCriteriumChoiceBox() {
		sortCriteriumChoiceBox.getItems().add(descrSortCrit);
		sortCriteriumChoiceBox.getItems().add(propDescrSortCrit);

		sortCriteriumChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((e, oldVal, newVal) -> {
					sortCriteriumChoiceBoxSelectionChanged(oldVal, newVal);
				});
	}

	@Override
	public void handleWorkspaceUpdate(WorkspaceUpdateEvent evt) {

	}

}
