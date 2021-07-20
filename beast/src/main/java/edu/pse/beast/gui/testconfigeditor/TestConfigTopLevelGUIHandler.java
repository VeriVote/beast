package edu.pse.beast.gui.testconfigeditor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.gui.runs.CBMCTestRunGuiController;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfigGuiController;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfigGuiController;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigCBMCTreeItem;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigCategoryTreeItem;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItem;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.testconfigeditor.treeview.runs.TestRunCBMCTreeItem;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

public class TestConfigTopLevelGUIHandler implements WorkspaceUpdateListener {
    private static final String DIR = "/edu/pse/beast/";
    private static final String TEST_CONFIG_DETAIL_FXML = DIR + "testConfigDetailGUI.fxml";
    private static final String CBMC_TEST_CONFIG_DETAIL_FXML = DIR + "cbmcTestConfigDetailGUI.fxml";
    private static final String CBMC_TEST_RUN_DETAIL_FXML = DIR + "cbmcTestRunDetailGUI.fxml";
    private static final String TEST_CONFIG_CATEGORY_FXML = DIR + "categoryDetailGUI.fxml";

    private ChoiceBox<String> sortCriteriumChoiceBox;
    private TreeView<TestConfigTreeItemSuper> testConfigTreeView;
    private AnchorPane testConfigDetailsAnchorPane;

    private TreeItem<TestConfigTreeItemSuper> root;

    private BeastWorkspace beastWorkspace;

    private TestConfigCategoryGUIController categoryGUIController;

    private FXMLLoader testConfigCategoryFXMLLoader = new FXMLLoader(
            getClass().getResource(TEST_CONFIG_CATEGORY_FXML));

    private TestConfigGuiController testConfigGuiController;
    private FXMLLoader testConfigFXMLLoader = new FXMLLoader(
            getClass().getResource(TEST_CONFIG_DETAIL_FXML));

    private CBMCTestConfigGuiController cbmcTestConfigController;
    private FXMLLoader cbmcTestConfigFXMLLoader = new FXMLLoader(
            getClass().getResource(CBMC_TEST_CONFIG_DETAIL_FXML));

    private CBMCTestRunGuiController cbmcTestRunGuiController;
    private FXMLLoader cbmcTestRunFXMLLoader = new FXMLLoader(
            getClass().getResource(CBMC_TEST_RUN_DETAIL_FXML));

    private final String descrSortCrit = "Election Description";
    private final String propDescrSortCrit = "Property Description";
    // private final String cbmcTestConfigHeading = "cbmc Properties";

    private String sortCriterium = descrSortCrit;

    public TestConfigTopLevelGUIHandler(final ChoiceBox<String> sortCritChoiceBox,
                                        final TreeView<TestConfigTreeItemSuper> testConfigList,
                                        final AnchorPane testConfigDetailsPane,
                                        final BeastWorkspace workspace) throws IOException {
        this.beastWorkspace = workspace;
        workspace.registerUpdateListener(this);

        categoryGUIController =
                new TestConfigCategoryGUIController(workspace, descrSortCrit,
                                                    testConfigList);
        testConfigGuiController = new TestConfigGuiController(workspace);
        cbmcTestConfigController = new CBMCTestConfigGuiController(workspace);
        cbmcTestRunGuiController = new CBMCTestRunGuiController(workspace,
                                                                testConfigList);

        this.testConfigDetailsAnchorPane = testConfigDetailsPane;
        this.sortCriteriumChoiceBox = sortCritChoiceBox;
        this.testConfigTreeView = testConfigList;

        testConfigList.getSelectionModel().selectedItemProperty()
                .addListener((e, oldVal, newVal) -> {
                    if (oldVal == null) {
                        testConfigSelectionChanged(newVal.getValue());
                    } else if (newVal == null) {
                        testConfigSelectionChanged(oldVal.getValue());
                    } else {
                        testConfigSelectionChanged(newVal.getValue());
                    }
                });
        testConfigList.setShowRoot(true);

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
        sortCritChoiceBox.getSelectionModel().selectFirst();
    }

    private void setDetailAnchorPane(final AnchorPane pane) {
        testConfigDetailsAnchorPane.getChildren().clear();
        testConfigDetailsAnchorPane.getChildren().add(pane);
        AnchorPane.setLeftAnchor(pane, 0d);
        AnchorPane.setRightAnchor(pane, 0d);
        AnchorPane.setTopAnchor(pane, 0d);
        AnchorPane.setBottomAnchor(pane, 0d);
    }

    private void testConfigSelectionChanged(final TestConfigTreeItemSuper selected) {
        switch (selected.getType()) {
        case CATEGORY:
            final TestConfigCategoryTreeItem casted = (TestConfigCategoryTreeItem) selected;
            categoryGUIController.display(casted.getCategory());
            setDetailAnchorPane(categoryGUIController.getTopLevelAnchorPane());
            break;
        case TEST_CONFIG:
            final TestConfigTreeItem casted = (TestConfigTreeItem) selected;
            testConfigGuiController.display(casted.getTestConfig());
            setDetailAnchorPane(
                    testConfigGuiController.getTopLevelAnchorPane());
            break;
        case CBMC:
            final TestConfigCBMCTreeItem casted = (TestConfigCBMCTreeItem) selected;
            cbmcTestConfigController
                    .display(casted.getCbmcPropertyTestConfiguration());
            setDetailAnchorPane(
                    cbmcTestConfigController.getTopLevelAnchorPane());
            break;
        case CBMC_RUN:
            final TestRunCBMCTreeItem casted = (TestRunCBMCTreeItem) selected;
            cbmcTestRunGuiController.display(casted.getRun());
            setDetailAnchorPane(
                    cbmcTestRunGuiController.getTopLevelAnchorPane());
            break;
        default:
            break;
        }
    }

    private void addCBMCRunItems(final TreeItem<TestConfigTreeItemSuper> treeItem,
                                 final CBMCTestConfiguration config) {
        treeItem.getChildren().clear();
        for (final CBMCTestRunWithSymbolicVars tr : config.getRuns()) {
            treeItem.getChildren()
                    .add(new TreeItem<>(new TestRunCBMCTreeItem(tr)));
        }
    }

    private void updateTestConfigTreeView() {
        root = new TreeItem<>(new TestConfigCategoryTreeItem(sortCriterium));
        testConfigTreeView.setRoot(root);

        final Map<String, List<TestConfiguration>> testConfigs;
        if (sortCriterium.equals(descrSortCrit)) {
            testConfigs = beastWorkspace.getConfigsByElectionDescriptionName();
        } else { // if (sortCriterium.equals(propDescrSortCrit)) {
            testConfigs = beastWorkspace.getConfigsByPropertyDescriptionName();
        }

        for (final String name : testConfigs.keySet()) {
            final TreeItem<TestConfigTreeItemSuper> parentItem =
                    new TreeItem<>(new TestConfigCategoryTreeItem(name));
            final List<TestConfiguration> testConfigsForParent =
                    testConfigs.get(name);

            for (final TestConfiguration testConfig : testConfigsForParent) {
                final TreeItem<TestConfigTreeItemSuper> testConfigItem =
                        new TreeItem<>(new TestConfigTreeItem(testConfig));
                final Map<String, CBMCTestConfiguration> cbcmConfigs =
                        testConfig.getCbmcTestConfigsByName();
                if (!cbcmConfigs.isEmpty()) {
                    final TreeItem<TestConfigTreeItemSuper> cbmcParentItem =
                            new TreeItem<>(new TestConfigCategoryTreeItem("cbmc"));
                    for (final CBMCTestConfiguration cbmcConfig
                            : cbcmConfigs.values()) {
                        final TreeItem<TestConfigTreeItemSuper> cbmcConfigItem =
                                new TreeItem<>(new TestConfigCBMCTreeItem(cbmcConfig));
                        addCBMCRunItems(cbmcConfigItem, cbmcConfig);
                        cbmcParentItem.getChildren().add(cbmcConfigItem);
                    }
                    testConfigItem.getChildren().add(cbmcParentItem);
                }
                parentItem.getChildren().add(testConfigItem);
            }
            root.getChildren().add(parentItem);
        }
        testConfigTreeView.getSelectionModel().select(0);
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
    public void handleAddedTestConfig(final TestConfiguration tc) {
        updateTestConfigTreeView();
    }

    @Override
    public void handleWorkspaceUpdateAddedCBMCRuns(final CBMCTestConfiguration config,
                                                   final List<CBMCTestRunWithSymbolicVars>
                                                        createdTestRuns) {
        final TreeItem<TestConfigTreeItemSuper> item =
                TestConfigTreeViewHelper.getItem(config, root);
        for (final CBMCTestRunWithSymbolicVars r : createdTestRuns) {
            item.getChildren().add(new TreeItem<TestConfigTreeItemSuper>(
                    new TestRunCBMCTreeItem(r)));
        }
    }

    @Override
    public void handleCBMCRunDeleted(final CBMCTestRunWithSymbolicVars run) {
        updateTestConfigTreeView();
    }

    @Override
    public void handleTestConfigDeleted(final TestConfiguration tc) {
        updateTestConfigTreeView();
    }
}
