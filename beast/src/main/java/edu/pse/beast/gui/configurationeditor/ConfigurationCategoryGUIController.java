package edu.pse.beast.gui.configurationeditor;

import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.gui.configurationeditor.configuration.ConfigurationBatch;
import edu.pse.beast.gui.configurationeditor.treeview.ConfigurationItem;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ConfigurationCategoryGUIController implements WorkspaceUpdateListener {
    private static final String SAME_CONFIGURATION = "Another one.";

    @FXML
    private AnchorPane topLevelAnchorPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<CElectionDescription> descrChoiceBox;
    @FXML
    private Button loadDescrButton;
    @FXML
    private ChoiceBox<PropertyDescription> propDescrChoiceBox;
    @FXML
    private Button loadPropDescrButton;
    @FXML
    private Button createConfigurationButton;
    @FXML
    private ListView<ConfigurationBatch> configurationListView;
    @FXML
    private Button gotoConfigButton;
    @FXML
    private Button deleteConfigButton;

    private final String descriptionSortCriterion;

    private BeastWorkspace beastWorkspace;

    private String currentCategory;

    private TreeView<ConfigurationItem> configurationTreeView;

    public ConfigurationCategoryGUIController(final BeastWorkspace workspace,
                                              final String descrSortCrit,
                                              final TreeView<ConfigurationItem> treeView) {
        this.beastWorkspace = workspace;
        this.descriptionSortCriterion = descrSortCrit;
        workspace.registerUpdateListener(this);
        currentCategory = descrSortCrit;
        this.configurationTreeView = treeView;
    }

    @FXML
    public final void initialize() {
        createConfigurationButton.setOnAction(e -> {
            createConfiguration();
        });
        final BooleanBinding nameTextBlank = Bindings.createBooleanBinding(() -> {
            return nameTextField.getText().isBlank();
        }, nameTextField.textProperty());
        createConfigurationButton.disableProperty().bind(nameTextBlank);
        gotoConfigButton.setOnAction(e -> {
            configurationTreeView.getSelectionModel().select(
                    ConfigurationTreeViewHelper
                    .getItem(configurationListView.getSelectionModel()
                                .getSelectedItem().getConfigs().get(0),
                            configurationTreeView.getRoot()));
        });
        deleteConfigButton.setOnAction(e -> {
            beastWorkspace.deleteConfiguration(
                    configurationListView.getSelectionModel().getSelectedItem());
        });
        configurationListView.getSelectionModel().selectedItemProperty()
                .addListener((ob, o, n) -> {
                    gotoConfigButton.setDisable(n == null);
                    deleteConfigButton.setDisable(n == null);
                    if (n == o) {
                        System.out.println(SAME_CONFIGURATION);
                    }
                });
        loadDescrButton.setOnAction(e -> {
            beastWorkspace.letUserLoadDescr();
        });

        loadPropDescrButton.setOnAction(e -> {
            beastWorkspace.letUserLoadPropDescr();
        });
    }

    private void createConfiguration() {
        final String name = nameTextField.getText();
        final CElectionDescription descr =
                descrChoiceBox.getSelectionModel().getSelectedItem();
        final PropertyDescription propDescr =
                propDescrChoiceBox.getSelectionModel().getSelectedItem();
        if (!name.isEmpty() && descr != null && propDescr != null) {
            beastWorkspace.createConfiguration(name, descr, propDescr);
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

        configurationListView.getItems().clear();
        final boolean emptyConfigurationList =
                beastWorkspace.getConfigsByElectionDescription().isEmpty();
        gotoConfigButton.setDisable(emptyConfigurationList);
        deleteConfigButton.setDisable(emptyConfigurationList);

        if (currentCategory.equals(descriptionSortCriterion)) {
            final Map<CElectionDescription, List<ConfigurationBatch>> map =
                    beastWorkspace.getConfigsByElectionDescription();
            for (final CElectionDescription descr : map.keySet()) {
                for (final ConfigurationBatch tc : map.get(descr)) {
                    configurationListView.getItems().add(tc);
                }
            }
        } else {
            final Map<PropertyDescription, List<ConfigurationBatch>> map =
                    beastWorkspace.getConfigsByPropertyDescription();
            for (final PropertyDescription propDescr : map.keySet()) {
                for (final ConfigurationBatch tc : map.get(propDescr)) {
                    configurationListView.getItems().add(tc);
                }
            }
        }
        configurationListView.getSelectionModel().selectFirst();
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
    public final void handleAddedConfiguration(final ConfigurationBatch tc) {
        updateView();
    }

    @Override
    public final void handleAddedPropDescr(final PropertyDescription propDescr) {
        updateView();
    }
}
