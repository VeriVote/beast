package edu.pse.beast.gui.testconfigeditor;

import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class TestConfigCategoryGUIController
        implements WorkspaceUpdateListener {
    private static final String SAME_TEST_CONFIGURATION = "Another one.";

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

    private final String descriptionSortCriterion;

    private BeastWorkspace beastWorkspace;

    private String currentCategory;

    private TreeView<TestConfigTreeItemSuper> testConfigTreeView;

    public TestConfigCategoryGUIController(final BeastWorkspace workspace,
                                           final String descrSortCrit,
                                           final TreeView<TestConfigTreeItemSuper> treeView) {
        this.beastWorkspace = workspace;
        this.descriptionSortCriterion = descrSortCrit;
        workspace.registerUpdateListener(this);
        currentCategory = descrSortCrit;
        this.testConfigTreeView = treeView;
    }

    @FXML
    public final void initialize() {
        createTestConfigButton.setOnAction(e -> {
            createTestConfig();
        });
        gotoConfigButton.setOnAction(e -> {
            testConfigTreeView.getSelectionModel().select(
                    TestConfigTreeViewHelper
                    .getItem(testConfigListView.getSelectionModel()
                                .getSelectedItem().getCBMCTestConfigs().get(0),
                            testConfigTreeView.getRoot()));
        });
        deleteConfigButton.setOnAction(e -> {
            beastWorkspace.deleteTestConfig(
                    testConfigListView.getSelectionModel().getSelectedItem());
        });
        testConfigListView.getSelectionModel().selectedItemProperty()
                .addListener((ob, o, n) -> {
                    gotoConfigButton.setDisable(n == null);
                    deleteConfigButton.setDisable(n == null);
                    if (n == o) {
                        System.out.println(SAME_TEST_CONFIGURATION);
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
        final String name = nameTextField.getText();
        final CElectionDescription descr =
                descrChoiceBox.getSelectionModel().getSelectedItem();
        final PreAndPostConditionsDescription propDescr =
                propDescrChoiceBox.getSelectionModel().getSelectedItem();
        if (!name.isEmpty() && descr != null && propDescr != null) {
            beastWorkspace.createTestConfig(name, descr, propDescr);
        }
    }

    public final AnchorPane getTopLevelAnchorPane() {
        return topLevelAnchorPane;
    }

    private void updateView() {
        descrChoiceBox.getItems().clear();
        descrChoiceBox.getItems().addAll(beastWorkspace.getLoadedDescrs());
        descrChoiceBox.getSelectionModel().select(0);

        propDescrChoiceBox.getItems().clear();
        propDescrChoiceBox.getItems().addAll(beastWorkspace.getLoadedPropDescrs());
        propDescrChoiceBox.getSelectionModel().select(0);

        testConfigListView.getItems().clear();
        final boolean emptyConfigurationList =
                beastWorkspace.getConfigsByElectionDescription().isEmpty();
        gotoConfigButton.setDisable(emptyConfigurationList);
        deleteConfigButton.setDisable(emptyConfigurationList);

        if (currentCategory.equals(descriptionSortCriterion)) {
            final Map<CElectionDescription, List<TestConfiguration>> map =
                    beastWorkspace.getConfigsByElectionDescription();
            for (final CElectionDescription descr : map.keySet()) {
                for (final TestConfiguration tc : map.get(descr)) {
                    testConfigListView.getItems().add(tc);
                }
            }
        } else {
            final Map<PreAndPostConditionsDescription, List<TestConfiguration>> map =
                    beastWorkspace.getConfigsByPropertyDescription();
            for (final PreAndPostConditionsDescription propDescr : map.keySet()) {
                for (final TestConfiguration tc : map.get(propDescr)) {
                    testConfigListView.getItems().add(tc);
                }
            }
        }
        testConfigListView.getSelectionModel().selectFirst();
    }

    public final void display(final String category) {
        currentCategory = category;
        updateView();
    }

    @Override
    public final void handleWorkspaceUpdateGeneric() {
        updateView();
    }

    @Override
    public final void handleAddedTestConfig(final TestConfiguration tc) {
        updateView();
    }

    @Override
    public final void handleAddedPropDescr(final PreAndPostConditionsDescription propDescr) {
        updateView();
    }
}
