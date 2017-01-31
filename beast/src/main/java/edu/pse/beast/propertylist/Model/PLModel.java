package edu.pse.beast.propertylist.Model;

import java.util.ArrayList;
import java.util.Observable;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.PropertyItem;

public class PLModel extends Observable implements PLModelInterface {
	
	private ArrayList<PropertyItem> propertyList;
	private int dirtyIndex;

	@Override
	public void initialize() {
		if (propertyList == null) propertyList = new ArrayList<PropertyItem>();
		if (propertyList.isEmpty()) propertyList.add(new PropertyItem());
	}

	@Override
	public boolean changeName(PropertyItem prop, String newName) {
		
		int index = propertyList.indexOf(prop);
		if (index == -1) return false;
		
		if (indexOfName(newName) != -1) return false;
		
		PostAndPrePropertiesDescription old = propertyList.get(index).getDescription();
		propertyList.get(index).setDescription(newName, old.getPrePropertiesDescription(),
				old.getPostPropertiesDescription(), old.getSymVarList());
		
		return true;
	}

	@Override
	public boolean addDescription(PostAndPrePropertiesDescription desc) {
		propertyList.add(new PropertyItem(desc));
		return true;
	}

	@Override
	public boolean addNewProperty() {
		String name = "Eigenschaft ";
		int i = 0;
		while (indexOfName(name + i) != -1) i++;
		
		PropertyItem newItem = new PropertyItem(new PostAndPrePropertiesDescription(name + i), false);
		propertyList.add(newItem);
		dirtyIndex = propertyList.indexOf(newItem);
		updateView();
		return true;
	}

	@Override
	public void editProperty(PropertyItem prop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PropertyItem deleteProperty(PropertyItem prop) {
		int index = propertyList.indexOf(prop);
		if (index == -1) return null;
		return propertyList.remove(index);
	}

	@Override
	public void setTestStatus(PropertyItem prop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<PropertyItem> getList() {
		return propertyList;
	}

	
	public int getDirtyIndex() {
		return dirtyIndex;
	}
	
	private int indexOfName(String name) {
		for (PropertyItem current : propertyList) {
			if (current.getDescription().getName().equals(name)) return propertyList.indexOf(current);
		}
		return -1;
	}
	
	private void updateView() {
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}
	

	
	
}
