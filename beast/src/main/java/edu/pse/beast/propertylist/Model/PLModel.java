package edu.pse.beast.propertylist.Model;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.NameInterface;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ResultInterface;

import java.util.ArrayList;
import java.util.Observable;

public class PLModel extends Observable implements NameInterface {

	private ArrayList<PropertyItem> propertyList;
	private String name;

	/**
	 * Initializes the model.
	 */
	public void initialize() {
		if (propertyList == null) {
			propertyList = new ArrayList<PropertyItem>();
			name = "New PropertyList";
		}
	}

	/**
	 * Changes the name of a property.
	 * 
	 * @param prop
	 *            The property to be renamed
	 * @param newName
	 *            The new name for the property
	 * @return true, if the name change was successful
	 */
	public boolean changeName(PropertyItem prop, String newName, BooleanExpEditor editor) {
		int index = propertyList.indexOf(prop);
		if (index == -1) {
			return false;
		}
		if (indexOfName(newName) != -1 && prop.getDescription().getName() != newName) {
			return false;
		}
		PostAndPrePropertiesDescription old = propertyList.get(index).getDescription();

		if (editor != null && editor.getCurrentlyLoadedPostAndPreProp().getName().equals(old.getName())) {
			old = editor.getCurrentlyLoadedPostAndPreProp();
			propertyList.get(index).setDescription(newName, old.getPrePropertiesDescription(),
					old.getPostPropertiesDescription(), old.getSymVarList());
			editor.letUserEditPostAndPreProperties(propertyList.get(index).getDescription(), true);
		} else {
			propertyList.get(index).setDescription(newName, old.getPrePropertiesDescription(),
					old.getPostPropertiesDescription(), old.getSymVarList());
		}

		updateView();
		return true;
	}

	/**
	 * Adds a given property to the property list
	 * 
	 * @param prop
	 *            The property to add
	 * @return true when the addition was a success
	 */
	public boolean addDescription(PropertyItem prop) {
		if (propertyList.indexOf(prop) != -1) {
			PropertyItem updated = prop;
			String newName = prop.getDescription().getName() + "x";
			updated.setDescriptionName(newName);
			addDescription(updated);
		} else {
			propertyList.add(prop);
		}
		updateView();
		return true;
	}

	/**
	 * Adds a brand new property to the property list.
	 * 
	 * @param editor
	 *            The BooleanExpEditor in which you can edit the property
	 * @return Returns true if the property could be added
	 */
	public boolean addNewProperty(BooleanExpEditor editor) {
		String name = "Eigenschaft ";
		int i = 0;
		while (indexOfName(name + i) != -1)
			i++;
		PropertyItem newItem = new PropertyItem(new PostAndPrePropertiesDescription(name + i), false);
		propertyList.add(newItem);
		if (editor != null) {
			editor.letUserEditPostAndPreProperties(newItem.getDescription(), true);
			editor.getView().setVisible(true);
		}
		updateView();
		return true;
	}

	/**
	 * Edits a property in the property list.
	 * 
	 * @param prop
	 *            The property to edit
	 * @param editor
	 *            The BooleanExpEditor object in which to edit the given
	 *            property
	 */
	public void editProperty(PropertyItem prop, BooleanExpEditor editor) {
		editor.letUserEditPostAndPreProperties(prop.getDescription(), true);
		editor.getView().setVisible(true);
		updateView();
	}

	/**
	 * Deletes a property from the list
	 * 
	 * @param prop
	 *            The property to remove
	 * @return Returns true if the deletion was a success
	 */
	public boolean deleteProperty(PropertyItem prop, BooleanExpEditor editor) {
		int index = propertyList.indexOf(prop);
		if (index == -1)
			return false;
		propertyList.remove(index);
		if (propertyList.isEmpty())
			addNewProperty(editor);
		if (editor != null) {
			editor.letUserEditPostAndPreProperties(propertyList.get(0).getDescription(), true);
		}
		updateView();
		return true;
	}

	/**
	 * Sets whether the property should be analyzed by the checker or not.
	 * 
	 * @param prop
	 *            The property to analyze
	 * @param newStatus
	 *            Sets if the property will be analyzed in the next check
	 */
	public void setTestStatus(PropertyItem prop, boolean newStatus) {
		prop.setTestStatus(newStatus);
	}

	/**
	 * Clears the current list.
	 */
	public void setNewList() {
		this.propertyList.clear();
		updateView();
	}

	/**
	 * Gets the next property item that gets a result presentation.
	 * 
	 * @param res
	 *            The ResultInterface with the analysis result for the property
	 * @param index
	 *            The index of the result object
	 * @return Returns if there is a next item to receive a result
	 */
	public boolean setNextToBePresented(ResultInterface res, Integer index) {
		ArrayList<PropertyItem> testedList = getTestedPropertyList();

		res.presentTo(testedList.get(index));

		updateView();

		return true;
	}

	/**
	 * Resets all result data in the property list.
	 */
	public void resetResults() {
		for (PropertyItem item : propertyList) {
			item.setResultType(ResultType.UNTESTED);
		}
		updateView();
	}

	/**
	 * Returns a list of PropertyItem.
	 * 
	 * @return An ArrayList with all the property items in the property list
	 */
	public ArrayList<PropertyItem> getPropertyList() {
		return propertyList;
	}

	/**
	 * Sets a new property list.
	 * 
	 * @param list
	 *            The new list of property items that make up the new property
	 *            list.
	 */
	public void setPropertyList(ArrayList<PropertyItem> propertyList) {
		this.propertyList = propertyList;
	}

	/**
	 * Setter for the data model.
	 * 
	 * @param model
	 *            The new model to replace the old one
	 */
	public void loadAnotherModel(PLModel model) {
		this.propertyList = model.getPropertyList();
		updateView();
	}

	/**
	 * Getter for the data model.
	 * 
	 * @return Returns the property list model
	 */
	public PLModel getModel() {
		return this;
	}

	@Override
	public void setNewName(String newName) {
		this.name = newName;
	}

	@Override
	public String getName() {
		return name;
	}

	private int indexOfName(String name) {
		for (PropertyItem current : propertyList) {
			if (current.getDescription().getName().equals(name)) {
				return propertyList.indexOf(current);
			}
		}
		return -1;
	}

	private ArrayList<PropertyItem> getTestedPropertyList() {
		ArrayList<PropertyItem> result = new ArrayList<PropertyItem>();
		for (PropertyItem item : propertyList) {
			if (item.getTestStatus())
				result.add(item);
		}
		return result;
	}

	/**
	 * Is invoked when the list of properties has changed and observers (mainly
	 * the view) need to know about it.
	 */
	public void updateView() {
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}

}
