package edu.pse.beast.gui.propertyeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.DialogHelper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class PreAndPostPropertyEditor implements WorkspaceUpdateListener {
	private PropertyEditorCodeElement preEditor;
	private PropertyEditorCodeElement postEditor;
	private TreeView<String> variableTreeView;
	private PreAndPostConditionsDescription currentPropDescr;
	private MenuButton addSymbVarMenu;
	private ChoiceBox<PreAndPostConditionsDescription> openedPropertyDescriptionChoiceBox;
	private BeastWorkspace beastWorkspace;
	private Button addPropDescrButton;

	public PreAndPostPropertyEditor(PropertyEditorCodeElement preEditor,
			PropertyEditorCodeElement postEditor, Button addPropDescrButton,
			TreeView<String> variableTreeView, MenuButton addSymbVarMenu,
			ChoiceBox<PreAndPostConditionsDescription> openedPropertyDescriptionChoiceBox,
			BeastWorkspace beastWorkspace) {
		this.preEditor = preEditor;
		this.postEditor = postEditor;
		this.variableTreeView = variableTreeView;
		this.addSymbVarMenu = addSymbVarMenu;
		this.openedPropertyDescriptionChoiceBox = openedPropertyDescriptionChoiceBox;
		this.beastWorkspace = beastWorkspace;
		this.addPropDescrButton = addPropDescrButton;

		addPropDescrButton.setOnAction(e -> {
			List<String> names = List.of("name");
			TextField nameField = new TextField();
			List<Node> nodes = List.of(nameField);
			Optional<ButtonType> res = DialogHelper.generateDialog(names, nodes)
					.showAndWait();
			if (res.isPresent()
					&& !res.get().getButtonData().isCancelButton()) {
				String name = nameField.getText();
				PreAndPostConditionsDescription propDescr = new PreAndPostConditionsDescription(
						name);
				beastWorkspace.addPropertyDescription(propDescr);
			}
		});

		beastWorkspace.registerUpdateListener(this);
		preEditor.setChangeListener(text -> {
			beastWorkspace.updateCodeForPropDescr(text,
					currentPropDescr.getPreConditionsDescription(),
					currentPropDescr);
		});
		postEditor.setChangeListener(text -> {
			beastWorkspace.updateCodeForPropDescr(text,
					currentPropDescr.getPostConditionsDescription(),
					currentPropDescr);
		});

		initSymbVarMenu();
		initPropDescrChoiceBox();
		handleWorkspaceUpdate();
	}

	@Override
	public void handleWorkspaceUpdateGeneric() {
		handleWorkspaceUpdate();
	}

	private void initSymbVarMenu() {
		for (SymbolicCBMCVar.CBMCVarType symbVarType : SymbolicCBMCVar.CBMCVarType
				.values()) {
			MenuItem item = new MenuItem(symbVarType.toString());
			item.setOnAction(e -> {
				List<String> names = List.of("name");
				TextField nameField = new TextField();
				List<Node> inputs = List.of(nameField);
				Optional<ButtonType> res = DialogHelper
						.generateDialog(names, inputs).showAndWait();
				if (res.isPresent()
						&& !res.get().getButtonData().isCancelButton()) {
					String name = nameField.getText();
					// TODO name parsing
					SymbolicCBMCVar var = new SymbolicCBMCVar(name,
							symbVarType);
					beastWorkspace.addCBCMVarToPropDescr(currentPropDescr, var);
				}
			});
			addSymbVarMenu.getItems().add(item);
		}
	}

	private void selectedPropDescrChanged(
			PreAndPostConditionsDescription propDescr) {
		if (propDescr == null)
			return;
		loadProperty(propDescr);
	}

	private void initPropDescrChoiceBox() {
		openedPropertyDescriptionChoiceBox.getSelectionModel()
				.selectedItemProperty()
				.addListener((observable, oldVal, newVal) -> {
					if (newVal != null)
						selectedPropDescrChanged(newVal);
					else if (oldVal != null) {
						selectedPropDescrChanged(oldVal);
					}
				});
	}

	private void handleWorkspaceUpdate() {
		openedPropertyDescriptionChoiceBox.getItems().clear();
		for (PreAndPostConditionsDescription propDescr : beastWorkspace
				.getLoadedPropDescrs()) {
			openedPropertyDescriptionChoiceBox.getItems().add(propDescr);
		}
		openedPropertyDescriptionChoiceBox.getSelectionModel().selectLast();
	}

	private void populateVariableList(List<SymbolicCBMCVar> vars) {
		TreeItem<String> voter = new TreeItem("Voter");
		TreeItem<String> candidate = new TreeItem("Candidate");
		for (SymbolicCBMCVar v : vars) {
			TreeItem<String> item = new TreeItem(v.getName());
			if (v.getVarType() == CBMCVarType.VOTER) {
				voter.getChildren().add(item);
			} else {
				candidate.getChildren().add(item);
			}
		}
		TreeItem<String> root = new TreeItem();
		root.getChildren().add(voter);
		root.getChildren().add(candidate);
		variableTreeView.setRoot(root);
		variableTreeView.setShowRoot(false);
	}

	public void loadProperty(PreAndPostConditionsDescription propDescr) {
		preEditor.clear();
		preEditor.insertText(0,
				propDescr.getPreConditionsDescription().getCode());
		postEditor.clear();
		postEditor.insertText(0,
				propDescr.getPostConditionsDescription().getCode());
		this.currentPropDescr = propDescr;
		populateVariableList(propDescr.getCbmcVariables());
	}

	@Override
	public void handleWorkspaceUpdateAddedVarToPropDescr(
			PreAndPostConditionsDescription propDescr, SymbolicCBMCVar var) {
		if (propDescr == currentPropDescr) {
			populateVariableList(currentPropDescr.getCbmcVariables());
		}
	}

	public void save() {
		beastWorkspace.savePropDescr(currentPropDescr);
	}

}
