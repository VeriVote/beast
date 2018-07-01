package edu.pse.beast.highlevel.javafx;

import java.io.File;
import java.util.Iterator;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGeneralErrorFinder;
import edu.pse.beast.codeareaJAVAFX.BoundedVarCodeArea;
import edu.pse.beast.codeareaJAVAFX.NewPropertyCodeArea;
import edu.pse.beast.codeareaJAVAFX.SaverLoader;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.PropertyDescriptionSaverLoader;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class BooleanExpEditorNEW implements MenuBarInterface {

	private NewPropertyCodeArea preArea;
	private NewPropertyCodeArea postArea;

	private BoundedVarCodeArea boundedVarArea;

	private PreAndPostConditionsDescription currentPropertyDescription;
	private ParentTreeItem currentItem;

	private final SaverLoader saverLoader;

	private final PropertyDescriptionSaverLoader propSaverLoader = new PropertyDescriptionSaverLoader();

	private final ObservableList<TreeItem<String>> symbolicVoterVariableList;
	private final ObservableList<TreeItem<String>> symbolicCandidateVariableList;
	private final ObservableList<TreeItem<String>> symbolicSeatVariableList;

	public BooleanExpEditorNEW(NewPropertyCodeArea preArea, NewPropertyCodeArea postArea,
			BoundedVarCodeArea boundedVarArea, PreAndPostConditionsDescription propDesc, ParentTreeItem currentItem) {

		this.saverLoader = new SaverLoader(".prop", "BEAST property description");

		this.preArea = preArea;
		this.postArea = postArea;

		this.boundedVarArea = boundedVarArea;

		this.currentPropertyDescription = propDesc;
		this.currentItem = currentItem;

		this.symbolicVoterVariableList = GUIController.getController().getVoterTreeItems().getChildren();

		symbolicCandidateVariableList = GUIController.getController().getCandidateTreeItems().getChildren();

		symbolicSeatVariableList = GUIController.getController().getSeatTreeItems().getChildren();
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

			adding: if ((!containsVarName(toAdd))) {

				if (!fromExisting) {

					TreeItem<String> newItem = new TreeItem<String>(toAdd);

					switch (container.getInternalType()) {
					case VOTER:
						symbolicVoterVariableList.add(newItem);
						break;

					case CANDIDATE:
						symbolicCandidateVariableList.add(newItem);
						break;

					case SEAT:
						symbolicSeatVariableList.add(newItem);
						break;

					default:
						break adding;
					}
				} else {
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

					for (Iterator<TreeItem<String>> treeItemIterator = symbolicVoterVariableList
							.iterator(); treeItemIterator.hasNext();) {
						TreeItem<String> item = (TreeItem<String>) treeItemIterator.next();

						if (item.getValue().equals(name)) {
							treeItemIterator.remove();
						}

					}

					break;

				case CANDIDATE:
					for (Iterator<TreeItem<String>> treeItemIterator = symbolicCandidateVariableList
							.iterator(); treeItemIterator.hasNext();) {
						TreeItem<String> item = (TreeItem<String>) treeItemIterator.next();

						if (item.getValue().equals(name)) {
							treeItemIterator.remove();
						}

					}
					break;

				case SEAT:
					for (Iterator<TreeItem<String>> treeItemIterator = symbolicSeatVariableList
							.iterator(); treeItemIterator.hasNext();) {
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

		symbolicVoterVariableList.clear();

		symbolicCandidateVariableList.clear();

		symbolicSeatVariableList.clear();
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

	public void savePropertyTextAreasIntoDescription() {
		currentPropertyDescription.getPreConditionsDescription().setCode(preArea.getText());
		currentPropertyDescription.getPostConditionsDescription().setCode(postArea.getText());
		currentPropertyDescription.getBoundedVarDescription().setCode(boundedVarArea.getText());
		
		currentPropertyDescription.getSymbolicVariableList().clear();
		
		currentPropertyDescription.getSymVarList().clearList();
		
		currentPropertyDescription.getSymVarList().addSymbolicVariableList(getAllSymbolicVariables());
	}

	public void updatePropertyTextAreas(PreAndPostConditionsDescription description) {
		preArea.setDescription(description.getPreConditionsDescription());
		postArea.setDescription(description.getPostConditionsDescription());
		boundedVarArea.setDescription(description.getBoundedVarDescription());
	}

	public void setCurrentPropertyDescription(ParentTreeItem propertyItem, boolean bringToFront) {

		savePropertyTextAreasIntoDescription();

		this.currentPropertyDescription = propertyItem.getPreAndPostPropertie();
		this.currentItem = propertyItem;

		this.removeAllVariables();

		updatePropertyTextAreas(currentPropertyDescription);

		for (Iterator<SymbolicVariable> iterator = currentPropertyDescription.getSymbolicVariableList()
				.iterator(); iterator.hasNext();) {
			SymbolicVariable variable = (SymbolicVariable) iterator.next();

			this.addSymbVar(variable.getInternalTypeContainer(), variable.getId(), true);
		}

		if (bringToFront) {
			bringToFront();
		}

		saverLoader.resetHasSaveFile();

	}

	public void bringToFront() {
		GUIController.getController().getMainTabPane().getSelectionModel()
				.select(GUIController.getController().getPropertyTab());

		if (currentItem != null) {
			BooleanExpEditorGeneralErrorFinder.hasErrors(currentItem); // checks if there are errors, and if there are
		}
		// displays them in the error tab
	}

	public ParentTreeItem getCurrentItem() {
		return currentItem;
	}
	//
	// private void saveDescription() {
	// preArea.saveDescription(null);
	// postArea.saveDescription(null);
	// }

	/**
	 * clears all fields of the editor
	 */
	public void clear() {
		this.removeAllVariables();
		this.preArea.clear();
		this.postArea.clear();
	}

	private PreAndPostConditionsDescription convert(String json) {
		PreAndPostConditionsDescription newDescription = null;

		if (!json.equals("")) {

			try {
				newDescription = propSaverLoader.createFromSaveString(json);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return newDescription;
		}
		return null;
	}

	private SymbolicVariableList getAllSymbolicVariables() {
		SymbolicVariableList toReturn = new SymbolicVariableList();
		
		for (Iterator<TreeItem<String>> iterator = symbolicVoterVariableList.iterator(); iterator.hasNext();) {
			TreeItem<String> voter = (TreeItem<String>) iterator.next();
			toReturn.addSymbolicVariable(voter.getValue(), new InternalTypeContainer(InternalTypeRep.VOTER));
		}
		
		for (Iterator<TreeItem<String>> iterator = symbolicCandidateVariableList.iterator(); iterator.hasNext();) {
			TreeItem<String> candidate = (TreeItem<String>) iterator.next();
			toReturn.addSymbolicVariable(candidate.getValue(), new InternalTypeContainer(InternalTypeRep.CANDIDATE));
		}
		
		for (Iterator<TreeItem<String>> iterator = symbolicSeatVariableList.iterator(); iterator.hasNext();) {
			TreeItem<String> seat = (TreeItem<String>) iterator.next();
			toReturn.addSymbolicVariable(seat.getValue(), new InternalTypeContainer(InternalTypeRep.SEAT));
		}
		
		return toReturn;
	}

	public PreAndPostConditionsDescription open(File file) {
		return convert(saverLoader.load(file));
	}

	@Override
	public void open() {

		PreAndPostConditionsDescription newDescription = convert(saverLoader.load());

		if (newDescription != null) {
			currentPropertyDescription = newDescription;

			saverLoader.resetHasSaveFile();

			preArea.replaceText(newDescription.getPreConditionsDescription().getCode());
			postArea.replaceText(newDescription.getPostConditionsDescription().getCode());

			GUIController.getController().setPropNameField(newDescription.getName());
			bringToFront();
		}
	}

	@Override
	public void save() {
		savePropertyTextAreasIntoDescription();

		String json = propSaverLoader.createSaveString(currentPropertyDescription);

		saverLoader.save("", json);
	}

	@Override
	public void saveAs() {
		savePropertyTextAreasIntoDescription();

		String json = propSaverLoader.createSaveString(currentPropertyDescription);

		saverLoader.saveAs("", json);
	}

	public void saveAs(PreAndPostConditionsDescription description, File file) {
		savePropertyTextAreasIntoDescription();

		String json = propSaverLoader.createSaveString(description);

		saverLoader.saveAs(file, json);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cut() {
		// TODO Auto-generated method stub

	}

	@Override
	public void copy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paste() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

}