package edu.pse.beast.propertylist.Model;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.NameInterface;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ResultInterface;

import java.util.ArrayList;
import java.util.Observable;


public class PLModel extends Observable implements PLModelInterface, NameInterface {

	private ArrayList<PropertyItem> propertyList;
	private String name;

	@Override
	public void initialize() {
		if (propertyList == null) {
			propertyList = new ArrayList<PropertyItem>();
			name = "New PropertyList";
		}
	}

	@Override
	public boolean changeName(PropertyItem prop, String newName) {
		int index = propertyList.indexOf(prop);
		if (index == -1) {
			return false;
		}
		if (indexOfName(newName) != -1 && prop.getDescription().getName() != newName) {
			return false;
		}
		PostAndPrePropertiesDescription old = propertyList.get(index).getDescription();
		propertyList.get(index).setDescription(newName, old.getPrePropertiesDescription(),
				old.getPostPropertiesDescription(), old.getSymVarList());
		updateView();
		return true;
	}

	@Override
	public boolean addDescription(PropertyItem prop) {
		if (propertyList.indexOf(prop) != -1) {
			PropertyItem updated = prop;
			String newName = prop.getDescription().getName() + "x";
			updated.setDescriptionName(newName);
			propertyList.add(updated);
		}
		else {
			propertyList.add(prop);
		}
		updateView();
		return true;
	}

	@Override
	public boolean addNewProperty(BooleanExpEditor editor) {
		String name = "Eigenschaft ";
		int i = 0;
		while (indexOfName(name + i) != -1) i++;
		PropertyItem newItem = new PropertyItem(new PostAndPrePropertiesDescription(name + i), false);
		propertyList.add(newItem);
		editor.letUserEditPostAndPreProperties(newItem.getDescription(), true);
		editor.getView().setVisible(true);
		updateView();
		return true;
	}

	@Override
	public void editProperty(PropertyItem prop, BooleanExpEditor editor) {
		editor.letUserEditPostAndPreProperties(prop.getDescription(), true);
		editor.getView().setVisible(true);
		prop.setResultType(ResultType.UNTESTED);
		updateView();
	}

	@Override
	public boolean deleteProperty(PropertyItem prop) {
		int index = propertyList.indexOf(prop);
		if (index == -1) return false;
		propertyList.remove(index);
		updateView();
		return true;
	}

	@Override
	public void setTestStatus(PropertyItem prop, boolean newStatus) {
		prop.setTestStatus(newStatus);
	}

	@Override
	public void setNewList() {
		this.propertyList.clear();
		updateView();
	}

	@Override
	public boolean setNextToBePresented(ResultInterface res, Integer index) {
		ArrayList<PropertyItem> testedList = getTestedPropertyList();
		
		res.presentTo(testedList.get(index));
		
		updateView();
		
		return true;
		
		
		/*for (PropertyItem item : propertyList) {
			if (item.getResultType() == ResultType.UNTESTED && item.getTestStatus()) {
				res.presentTo(item);
				updateView();
				return true;
			}
		}
		return false;*/
	}

	@Override
	public void resetResults() {
		for (PropertyItem item : propertyList) {
			item.setResultType(ResultType.UNTESTED);
		}
		updateView();
	}

	@Override
	public ArrayList<PropertyItem> getPropertyList() {
		return propertyList;
	}

	@Override
	public void setPropertyList(ArrayList<PropertyItem> propertyList) {
		this.propertyList = propertyList;
	}

	@Override
	public void loadAnotherModel(PLModelInterface model) {
		this.propertyList = model.getPropertyList();
		updateView();
	}

	@Override
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
			if (item.getTestStatus()) result.add(item);
		}
		return result;
	}

	/**
	 * Is invoked when the list of properties has changed and observers (mainly the view) need to know about it.
	 */
	public void updateView() {
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}

}
