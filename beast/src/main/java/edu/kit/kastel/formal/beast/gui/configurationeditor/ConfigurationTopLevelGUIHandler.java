package edu.kit.kastel.formal.beast.gui.configurationeditor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.ConfigurationBatch;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.ConfigurationGUIController;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.cbmc.CheckConfigurationGUIController;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.cbmc.Configuration;
import edu.kit.kastel.formal.beast.gui.configurationeditor.treeview.ConfigurationCategoryItem;
import edu.kit.kastel.formal.beast.gui.configurationeditor.treeview.ConfigurationItem;
import edu.kit.kastel.formal.beast.gui.configurationeditor.treeview.ConfigurationTreeItem;
import edu.kit.kastel.formal.beast.gui.configurationeditor.treeview.PropertyCheckConfigurationItem;
import edu.kit.kastel.formal.beast.gui.configurationeditor.treeview.run.RunItem;
import edu.kit.kastel.formal.beast.gui.run.RunGUIController;
import edu.kit.kastel.formal.beast.gui.workspace.BeastWorkspace;
import edu.kit.kastel.formal.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ConfigurationTopLevelGUIHandler implements WorkspaceUpdateListener {
    private static final String ELECTION_DESCRIPTION = "Election Description";
    private static final String PROPERTY_DESCRIPTION = "Property Description";
    private static final String CBMC = "Verification";
    private static final String CONFIG_FXML = "configuration.fxml";
    private static final String CBMC_CONFIG_FXML = "check.fxml";
    private static final String CBMC_RUN_FXML = "run.fxml";
    private static final String CONFIG_CATEGORY_FXML = "category.fxml";

    private ChoiceBox<String> sortCriteriumChoiceBox;
    private TreeView<ConfigurationItem> configurationTreeView;
    private AnchorPane configurationAnchorPane;

    private TreeItem<ConfigurationItem> root;

    private BeastWorkspace beastWorkspace;

    private ConfigurationCategoryGUIController categoryGUIController;

    private FXMLLoader configCategoryFXMLLoader =
            new FXMLLoader(getClass().getResource(CONFIG_CATEGORY_FXML));

    private ConfigurationGUIController configGUIController;
    private FXMLLoader configFXMLLoader =
            new FXMLLoader(getClass().getResource(CONFIG_FXML));

    private CheckConfigurationGUIController checkConfigGUIController;
    private FXMLLoader cbmcConfigFXMLLoader =
            new FXMLLoader(getClass().getResource(CBMC_CONFIG_FXML));

    private RunGUIController cbmcRunGUIController;
    private FXMLLoader cbmcRunFXMLLoader =
            new FXMLLoader(getClass().getResource(CBMC_RUN_FXML));

    private final String descrSortCrit = ELECTION_DESCRIPTION;
    private final String propDescrSortCrit = PROPERTY_DESCRIPTION;
    // private final String cbmcConfigurationHeading = "cbmc Properties";

    private String sortCriterium = descrSortCrit;

    public ConfigurationTopLevelGUIHandler(final ChoiceBox<String> sortCritChoiceBox,
                                           final TreeView<ConfigurationItem> configurationList,
                                           final AnchorPane configurationDetailsPane,
                                           final BeastWorkspace workspace) throws IOException {
        this.beastWorkspace = workspace;
        workspace.registerUpdateListener(this);

        categoryGUIController =
                new ConfigurationCategoryGUIController(workspace, descrSortCrit, configurationList);
        configGUIController = new ConfigurationGUIController(workspace);
        checkConfigGUIController = new CheckConfigurationGUIController(workspace);
        cbmcRunGUIController = new RunGUIController(workspace, configurationList);

        this.configurationAnchorPane = configurationDetailsPane;
        this.sortCriteriumChoiceBox = sortCritChoiceBox;
        this.configurationTreeView = configurationList;

        configurationList.getSelectionModel().selectedItemProperty()
                .addListener((e, oldVal, newVal) -> {
                    if (oldVal == null) {
                        configurationSelectionChanged(newVal.getValue());
                    } else if (newVal == null) {
                        configurationSelectionChanged(oldVal.getValue());
                    } else {
                        configurationSelectionChanged(newVal.getValue());
                    }
                });
        configurationList.setShowRoot(true);

        configCategoryFXMLLoader.setController(categoryGUIController);
        configCategoryFXMLLoader.load();
        configFXMLLoader.setController(configGUIController);
        configFXMLLoader.load();
        cbmcConfigFXMLLoader.setController(checkConfigGUIController);
        cbmcConfigFXMLLoader.load();
        cbmcRunFXMLLoader.setController(cbmcRunGUIController);
        cbmcRunFXMLLoader.load();

        setupSortCriteriumChoiceBox();
        handleWorkspaceUpdateGeneric();
        sortCritChoiceBox.getSelectionModel().selectFirst();
    }

    private void setDetailAnchorPane(final AnchorPane pane) {
        configurationAnchorPane.getChildren().clear();
        configurationAnchorPane.getChildren().add(pane);
        AnchorPane.setLeftAnchor(pane, 0d);
        AnchorPane.setRightAnchor(pane, 0d);
        AnchorPane.setTopAnchor(pane, 0d);
        AnchorPane.setBottomAnchor(pane, 0d);
    }

    private void configurationSelectionChanged(final ConfigurationItem selected) {
        switch (selected.getType()) {
        case CATEGORY:
            categoryGUIController.display(((ConfigurationCategoryItem) selected).getCategory());
            setDetailAnchorPane(categoryGUIController.getTopLevelAnchorPane());
            break;
        case CONFIG:
            configGUIController.display(((ConfigurationTreeItem) selected).getConfiguration());
            setDetailAnchorPane(configGUIController.getTopLevelAnchorPane());
            break;
        case CBMC:
            checkConfigGUIController
                    .display(((PropertyCheckConfigurationItem) selected)
                            .getPropertyCheckConfiguration());
            setDetailAnchorPane(checkConfigGUIController.getTopLevelAnchorPane());
            break;
        case CBMC_RUN:
            cbmcRunGUIController.display(((RunItem) selected).getRun());
            setDetailAnchorPane(cbmcRunGUIController.getTopLevelAnchorPane());
            break;
        default:
            break;
        }
    }

    private void addRunItems(final TreeItem<ConfigurationItem> treeItem,
                             final Configuration config) {
        treeItem.getChildren().clear();
        for (final PropertyCheckRun tr : config.getRuns()) {
            treeItem.getChildren()
                    .add(new TreeItem<ConfigurationItem>(new RunItem(tr)));
        }
    }

    private void updateConfigurationTreeView() {
        root = new TreeItem<ConfigurationItem>(new ConfigurationCategoryItem(sortCriterium));
        configurationTreeView.setRoot(root);

        final Map<String, List<ConfigurationBatch>> configurations;
        if (sortCriterium.equals(descrSortCrit)) {
            configurations = beastWorkspace.getConfigsByElectionDescriptionName();
        } else { // if (sortCriterium.equals(propDescrSortCrit)) {
            configurations = beastWorkspace.getConfigsByPropertyDescriptionName();
        }

        for (final String name : configurations.keySet()) {
            final TreeItem<ConfigurationItem> parentItem =
                    new TreeItem<ConfigurationItem>(new ConfigurationCategoryItem(name));
            final List<ConfigurationBatch> configurationsForParent = configurations.get(name);

            for (final ConfigurationBatch configuration : configurationsForParent) {
                final TreeItem<ConfigurationItem> configItem =
                        new TreeItem<ConfigurationItem>(new ConfigurationTreeItem(configuration));
                final Map<String, Configuration> configs = configuration.getConfigurationsByName();
                if (!configs.isEmpty()) {
                    final TreeItem<ConfigurationItem> checkParentItem =
                            new TreeItem<ConfigurationItem>(new ConfigurationCategoryItem(CBMC));
                    for (final Configuration config : configs.values()) {
                        final TreeItem<ConfigurationItem> configItems =
                                new TreeItem<ConfigurationItem>(
                                        new PropertyCheckConfigurationItem(config));
                        addRunItems(configItems, config);
                        checkParentItem.getChildren().add(configItems);
                    }
                    configItem.getChildren().add(checkParentItem);
                }
                parentItem.getChildren().add(configItem);
            }
            root.getChildren().add(parentItem);
        }
        configurationTreeView.getSelectionModel().select(0);
    }

    private void setupSortCriteriumChoiceBox() {
        sortCriteriumChoiceBox.getItems().add(descrSortCrit);
        sortCriteriumChoiceBox.getItems().add(propDescrSortCrit);
        sortCriteriumChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((e, oldVal, newVal) -> {
                    sortCriterium = newVal;
                    updateConfigurationTreeView();
                });
    }

    @Override
    public final void handleWorkspaceUpdateGeneric() {
        updateConfigurationTreeView();
    }

    @Override
    public final void handleAddedConfiguration(final ConfigurationBatch tc) {
        updateConfigurationTreeView();
    }

    @Override
    public final void handleWorkspaceUpdateAddedRuns(final Configuration config,
                                                         final List<PropertyCheckRun> createdRuns) {
        final TreeItem<ConfigurationItem> item =
                ConfigurationTreeViewHelper.getItem(config, root);
        for (final PropertyCheckRun r : createdRuns) {
            item.getChildren().add(new TreeItem<ConfigurationItem>(new RunItem(r)));
        }
    }

    @Override
    public final void handleRunDeleted(final PropertyCheckRun run) {
        updateConfigurationTreeView();
    }

    @Override
    public final void handleConfigurationDeleted(final ConfigurationBatch tc) {
        updateConfigurationTreeView();
    }
}
