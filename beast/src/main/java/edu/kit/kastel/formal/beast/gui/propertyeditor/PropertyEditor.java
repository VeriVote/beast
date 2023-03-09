package edu.kit.kastel.formal.beast.gui.propertyeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.gui.DialogHelper;
import edu.kit.kastel.formal.beast.gui.workspace.BeastWorkspace;
import edu.kit.kastel.formal.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class PropertyEditor implements WorkspaceUpdateListener {
    private static final String NAME = "Name:";
    private static final String ADD_PROPERTY = "Add Property";
    private static final String INIT_VARIABLE = "Init Variable";

    private PropertyEditorCodeElement preConditionEditor;
    private PropertyEditorCodeElement postConditionEditor;
    private ListView<SymbolicVariable> symbolicVariablesListView;
    private PropertyDescription currentPropertyDescription;
    private MenuButton addSymbolicVariableMenu;
    private Button removeSymbolicVariableButton;
    private ChoiceBox<PropertyDescription> openedPropertyDescriptionChoiceBox;
    private BeastWorkspace beastWorkspace;
    private Button addPropertyDescriptionButton;
    private Button loadPropertyDescriptionButton;
    private Button savePropertyDescriptionButton;
    private Button removePropertyDescriptionButton;

    public PropertyEditor(final PropertyEditorCodeElement preEditor,
                          final PropertyEditorCodeElement postEditor,
                          final Buttons buttons,
                          final ListView<SymbolicVariable> symbVarsListView,
                          final MenuButton addSymbVarMenu,
                          final ChoiceBox<PropertyDescription> openedPropDescrChoiceBox,
                          final BeastWorkspace workspace) {
        this.preConditionEditor = preEditor;
        this.postConditionEditor = postEditor;
        this.symbolicVariablesListView = symbVarsListView;
        this.addSymbolicVariableMenu = addSymbVarMenu;
        this.removeSymbolicVariableButton = buttons.removeSymbolicVariable;
        this.openedPropertyDescriptionChoiceBox = openedPropDescrChoiceBox;
        this.beastWorkspace = workspace;
        this.addPropertyDescriptionButton = buttons.addPropertyDescription;
        this.loadPropertyDescriptionButton = buttons.loadPropertyDescription;
        this.savePropertyDescriptionButton = buttons.savePropertyDescription;
        this.removePropertyDescriptionButton = buttons.removePropertyDescription;

        savePropertyDescriptionButton.setOnAction(e -> {
            save();
        });
        removePropertyDescriptionButton.setOnAction(e -> {
            remove();
        });
        initAddPropDescrButton();
        workspace.registerUpdateListener(this);

        preEditor.setChangeListener(text -> {
            workspace.updateCodeForPropDescr(text,
                    currentPropertyDescription.getPreConditionsDescription(),
                    currentPropertyDescription);
        });
        postEditor.setChangeListener(text -> {
            workspace.updateCodeForPropDescr(text,
                    currentPropertyDescription.getPostConditionsDescription(),
                    currentPropertyDescription);
        });

        initSymbVarMenu();
        initPropDescrChoiceBox();
        handleWorkspaceUpdate();
    }

    private void initAddPropDescrButton() {
        addPropertyDescriptionButton.setOnAction(e -> {
            final List<String> names = List.of(NAME);
            final TextField nameField = new TextField();
            final BooleanBinding textBlank = Bindings.createBooleanBinding(() -> {
                return nameField.getText().isBlank();
            }, nameField.textProperty());
            final List<Node> nodes = List.of(nameField);
            final Optional<ButtonType> res =
                    DialogHelper.generateDialog(ADD_PROPERTY, textBlank, names, nodes)
                    .showAndWait();
            if (res.isPresent()
                    && !res.get().getButtonData().isCancelButton()
                    && !nameField.getText().isBlank()) {
                final String name = nameField.getText();
                final PropertyDescription propDescr =
                        new PropertyDescription(name);
                beastWorkspace.addPropertyDescription(propDescr);
            }
        });
        loadPropertyDescriptionButton.setOnAction(e -> {
            beastWorkspace.letUserLoadPropDescr();
        });
    }

    @Override
    public final void handleWorkspaceUpdateGeneric() {
        handleWorkspaceUpdate();
    }

    private void initSymbVarMenu() {
        for (final SymbolicVariable.VariableType symbVarType
                : SymbolicVariable.VariableType.values()) {
            final MenuItem item = new MenuItem(symbVarType.toString());
            item.setOnAction(e -> {
                final List<String> names = List.of(NAME);
                final TextField nameField = new TextField();
                final BooleanBinding textBlank = Bindings.createBooleanBinding(() -> {
                    return nameField.getText().isBlank();
                }, nameField.textProperty());
                final List<Node> inputs = List.of(nameField);
                final Optional<ButtonType> res =
                        DialogHelper.generateDialog(INIT_VARIABLE, textBlank, names, inputs)
                        .showAndWait();
                if (res.isPresent()
                        && !res.get().getButtonData().isCancelButton()
                        && !nameField.getText().isBlank()) {
                    final String name = nameField.getText();
                    // TODO name parsing
                    final SymbolicVariable var =
                            new SymbolicVariable(name, symbVarType);
                    beastWorkspace.addVariableToPropertyDescription(currentPropertyDescription,
                                                                    var);
                }
            });
            addSymbolicVariableMenu.getItems().add(item);
        }
        removeSymbolicVariableButton.setOnAction(e -> {
            removeSelectedSymbVar();
        });
    }

    private void removeSelectedSymbVar() {
        final SymbolicVariable selectedVar =
                symbolicVariablesListView.getSelectionModel().getSelectedItem();
        beastWorkspace.removeSymbolicVar(currentPropertyDescription, selectedVar);
    }

    private void selectedPropDescrChanged(final PropertyDescription propDescr) {
        loadProperty(propDescr);
    }

    private void initPropDescrChoiceBox() {
        openedPropertyDescriptionChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldVal, newVal) -> {
                    if (newVal != null) {
                        selectedPropDescrChanged(newVal);
                    } else if (oldVal != null) {
                        selectedPropDescrChanged(oldVal);
                    }
                });
        openedPropertyDescriptionChoiceBox.setDisable(true);
        savePropertyDescriptionButton.setDisable(true);
        removePropertyDescriptionButton.setDisable(true);
    }

    private void updatePropDescrChoiceBox() {
        openedPropertyDescriptionChoiceBox.getItems().clear();
        for (PropertyDescription propDescr : beastWorkspace
                .getLoadedPropDescrs()) {
            openedPropertyDescriptionChoiceBox.getItems().add(propDescr);
        }
        if (openedPropertyDescriptionChoiceBox.getItems().isEmpty()) {
            selectedPropDescrChanged(null);
        } else {
            openedPropertyDescriptionChoiceBox.getSelectionModel().selectLast();
        }
        final boolean noChoice = openedPropertyDescriptionChoiceBox.getItems().size() < 1;
        openedPropertyDescriptionChoiceBox.setDisable(noChoice);
    }

    private void handleWorkspaceUpdate() {
        updatePropDescrChoiceBox();
    }

    private void populateVariableList(final List<SymbolicVariable> vars) {
        symbolicVariablesListView.getItems().clear();
        addSymbolicVariableMenu.setDisable(vars == null);
        removeSymbolicVariableButton.setDisable(vars == null);
        for (SymbolicVariable v : vars != null ? vars : new ArrayList<SymbolicVariable>()) {
            symbolicVariablesListView.getItems().add(v);
        }
    }

    public final void loadProperty(final PropertyDescription propDescr) {
        this.currentPropertyDescription = propDescr;
        preConditionEditor.setDisable(propDescr == null);
        postConditionEditor.setDisable(propDescr == null);
        if (propDescr != null) {
            preConditionEditor.clear();
            preConditionEditor.insertText(0,
                    propDescr.getPreConditionsDescription().getCode());
            postConditionEditor.clear();
            postConditionEditor.insertText(0,
                    propDescr.getPostConditionsDescription().getCode());
        }
        savePropertyDescriptionButton.setDisable(propDescr == null);
        removePropertyDescriptionButton.setDisable(propDescr == null);
        populateVariableList(propDescr != null ? propDescr.getVariables() : null);
    }

    @Override
    public final void handleWorkspaceUpdateAddedVarToPropDescr(final PropertyDescription
                                                                        propDescr,
                                                               final SymbolicVariable var) {
        if (propDescr == currentPropertyDescription) {
            populateVariableList(currentPropertyDescription.getVariables());
        }
    }

    @Override
    public final void handlePropDescrRemovedVar(final PropertyDescription propDescr,
                                                final SymbolicVariable selectedVar) {
        if (propDescr == currentPropertyDescription) {
            populateVariableList(currentPropertyDescription.getVariables());
        }
    }

    @Override
    public final void handleAddedPropDescr(final PropertyDescription propDescr) {
        updatePropDescrChoiceBox();
    }

    public final void save() {
        if (currentPropertyDescription != null) {
            beastWorkspace.savePropDescr(currentPropertyDescription);
        }
    }

    public final void remove() {
        if (currentPropertyDescription != null) {
            beastWorkspace.removePropDescr(currentPropertyDescription);
        }
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class Buttons {
        final Button addPropertyDescription;
        final Button loadPropertyDescription;
        final Button savePropertyDescription;
        final Button removePropertyDescription;
        final Button removeSymbolicVariable;

        public Buttons(final Button addPropDescrButton,
                       final Button loadPropDescrButton,
                       final Button savePropDescrButton,
                       final Button deletePropDescrButton,
                       final Button removeSymbVarButton) {
            this.addPropertyDescription = addPropDescrButton;
            this.loadPropertyDescription = loadPropDescrButton;
            this.savePropertyDescription = savePropDescrButton;
            this.removePropertyDescription = deletePropDescrButton;
            this.removeSymbolicVariable = removeSymbVarButton;
        }
    }
}
