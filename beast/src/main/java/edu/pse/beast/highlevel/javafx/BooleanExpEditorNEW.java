package edu.pse.beast.highlevel.javafx;

import java.util.Iterator;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGeneralErrorFinder;
import edu.pse.beast.codeareaJAVAFX.NewPropertyCodeArea;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.types.InternalTypeContainer;
import javafx.scene.control.TreeItem;

public class BooleanExpEditorNEW {

	private NewPropertyCodeArea preArea;
	private NewPropertyCodeArea postArea;
	private PreAndPostConditionsDescription currentPropertyDescription;

	public BooleanExpEditorNEW(NewPropertyCodeArea preArea, NewPropertyCodeArea postArea,
			PreAndPostConditionsDescription propDesc) {
		this.preArea = preArea;
		this.postArea = postArea;
		this.currentPropertyDescription = propDesc;
	}

	public boolean containsVarName(String name) {
		boolean contains = false;

		for (Iterator<SymbolicVariable> iterator = currentPropertyDescription.getSymbolicVariableList()
				.iterator(); iterator.hasNext();) {
			SymbolicVariable variable = (SymbolicVariable) iterator.next();
			contains = contains || (variable.getId().equals(name));
		}

		return contains;
	}

	public synchronized void addSymbVar(InternalTypeContainer container, String toAdd, boolean fromExisting) {
		if (!toAdd.equals("")) {

			GUIController.getController().getVariableNameField().setText("");

			adding: if ((!containsVarName(toAdd)) || fromExisting) {

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

				if (!fromExisting) {
					currentPropertyDescription.getSymbolicVariableList().add(new SymbolicVariable(toAdd, container));
				}
			}
		}
	}

	public void removeVariable(String name) {
		for (Iterator<SymbolicVariable> iterator = currentPropertyDescription.getSymbolicVariableList()
				.iterator(); iterator.hasNext();) {
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

	public void removeAllVariables() {
		GUIController.getController().getVoterTreeItems().getChildren().clear();

		GUIController.getController().getCandidateTreeItems().getChildren().clear();

		GUIController.getController().getSeatTreeItems().getChildren().clear();
	}

	public NewPropertyCodeArea getPrePropertyArea() {
		return preArea;
	}

	public NewPropertyCodeArea getPostPropertyArea() {
		return postArea;
	}

	public PreAndPostConditionsDescription getPropertyDescription() {
		return currentPropertyDescription;
	}

	public void setCurrentPropertyDescription(ParentTreeItem propertyItem, boolean bringToFront) {

		this.currentPropertyDescription = propertyItem.getPreAndPostPropertie();

		this.removeAllVariables();

		preArea.setDescription(currentPropertyDescription.getPreConditionsDescription());
		postArea.setDescription(currentPropertyDescription.getPostConditionsDescription());

		for (Iterator<SymbolicVariable> iterator = currentPropertyDescription.getSymbolicVariableList()
				.iterator(); iterator.hasNext();) {
			SymbolicVariable variable = (SymbolicVariable) iterator.next();

			this.addSymbVar(variable.getInternalTypeContainer(), variable.getId(), true);
		}

		if (bringToFront) {
			GUIController.getController().getMainTabPane().getSelectionModel()
					.select(GUIController.getController().getPropertyTab());

			BooleanExpEditorGeneralErrorFinder.hasErrors(propertyItem); // checks if there are errors, and if there are
			// displays them in the error tab
		}

	}

	public void saveProperty() {
		preArea.saveDescription(null);
		postArea.saveDescription(null);
	}

}
