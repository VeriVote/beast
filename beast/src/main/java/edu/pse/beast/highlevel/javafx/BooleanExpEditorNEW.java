package edu.pse.beast.highlevel.javafx;

import java.util.Iterator;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGeneralErrorFinder;
import edu.pse.beast.codeareaJAVAFX.NewPostPropertyCodeArea;
import edu.pse.beast.codeareaJAVAFX.NewPrePropertyCodeArea;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.types.InternalTypeContainer;
import javafx.scene.control.TreeItem;

public class BooleanExpEditorNEW {

	private SymbolicVariableList symbVars = new SymbolicVariableList();
	private NewPrePropertyCodeArea preArea;
	private NewPostPropertyCodeArea postArea;
	private PreAndPostConditionsDescription currentPropertyDescription;

	public BooleanExpEditorNEW(NewPrePropertyCodeArea preArea, NewPostPropertyCodeArea postArea,
			PreAndPostConditionsDescription propDesc) {
		this.preArea = preArea;
		this.postArea = postArea;
		this.currentPropertyDescription = propDesc;
	}

	public boolean containsVarName(String name) {
		boolean contains = false;

		for (Iterator<SymbolicVariable> iterator = symbVars.getSymbolicVariables().iterator(); iterator.hasNext();) {
			SymbolicVariable variable = (SymbolicVariable) iterator.next();
			contains = contains || (variable.getId().equals(name));
		}

		return contains;
	}

	public synchronized void addSymbVar(InternalTypeContainer container) {
		String toAdd = GUIController.getController().getVariableNameField().getText();

		if (!toAdd.equals("")) {

			GUIController.getController().getVariableNameField().setText("");

			adding: if (!containsVarName(toAdd)) {

				TreeItem<String> newItem = new TreeItem<String>(toAdd);

				switch (container.getInternalType()) {
				case VOTER:
					GUIController.getController().getVoterTreeItems().getChildren().add(newItem);
					break;

				case CANDIDATE:
					GUIController.getController().getCandidateTreeItems().getChildren().add(newItem);
					break;

				case SEAT:
					GUIController.getController().getSeatTreeItems().getChildren().add(newItem);
					break;

				default:
					break adding;
				}

				symbVars.getSymbolicVariables().add(new SymbolicVariable(toAdd, container));
			}
		}
	}

	public void removeVariable(String name) {
		for (Iterator<SymbolicVariable> iterator = symbVars.getSymbolicVariables().iterator(); iterator.hasNext();) {
			SymbolicVariable variable = (SymbolicVariable) iterator.next();
			if (variable.getId().equals(name)) {
				switch (variable.getInternalTypeContainer().getInternalType()) {
				case VOTER:

					for (Iterator<TreeItem<String>> treeItemIterator = GUIController.getController().getVoterTreeItems()
							.getChildren().iterator(); treeItemIterator.hasNext();) {
						TreeItem<String> item = (TreeItem<String>) treeItemIterator.next();

						if (item.getValue().equals(name)) {
							treeItemIterator.remove();
						}

					}

					break;

				case CANDIDATE:
					for (Iterator<TreeItem<String>> treeItemIterator = GUIController.getController()
							.getCandidateTreeItems().getChildren().iterator(); treeItemIterator.hasNext();) {
						TreeItem<String> item = (TreeItem<String>) treeItemIterator.next();

						if (item.getValue().equals(name)) {
							treeItemIterator.remove();
						}

					}
					break;

				case SEAT:
					for (Iterator<TreeItem<String>> treeItemIterator = GUIController.getController().getSeatTreeItems()
							.getChildren().iterator(); treeItemIterator.hasNext();) {
						TreeItem<String> item = (TreeItem<String>) treeItemIterator.next();

						if (item.getValue().equals(name)) {
							treeItemIterator.remove();
						}

					}
					break;

				default:
					break;
				}
				iterator.remove();
			}
		}
	}

	public NewPrePropertyCodeArea getPrePropertyArea() {
		return preArea;
	}

	public NewPostPropertyCodeArea getPostPropertyArea() {
		return postArea;
	}

	public SymbolicVariableList getSymbVarList() {
		return symbVars;
	}

	public void setCurrentPropertyDescription(ParentTreeItem propertyItem) {
		this.currentPropertyDescription = propertyItem.getPreAndPostPropertie();

		preArea.setPreDescription(currentPropertyDescription.getPreConditionsDescription());
		postArea.setPostDescription(currentPropertyDescription.getPostConditionsDescription());

		GUIController.getController().getMainTabPane().getSelectionModel()
				.select(GUIController.getController().getPropertyTab());

		BooleanExpEditorGeneralErrorFinder.hasErrors(propertyItem); // checks if there are errors, and if there are
		// displays them in the error tab

	}

}
