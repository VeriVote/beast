package edu.pse.beast.propertylist.Model;

import java.util.ArrayList;
import java.util.Observable;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.PropertyItem;

public class PLModel extends Observable implements PLModelInterface {
	
	private ArrayList<PropertyItem> propertyList;

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
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
	public boolean newDescription(String name) {
		PropertyItem newItem = new PropertyItem(new PostAndPrePropertiesDescription(name), false);
		propertyList.add(newItem);
		return true;
	}

	@Override
	public void changeDescription(PropertyItem prop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PropertyItem deleteDescription(PropertyItem prop) {
		int index = propertyList.indexOf(prop);
		if (index == -1) return null;
		return propertyList.remove(index);
	}

	@Override
	public void changeTestedStatus(PropertyItem prop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<PropertyItem> getDescr() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	private int indexOfName(String name) {
		for (PropertyItem current : propertyList) {
			if (current.getDescription().getName() == name) return propertyList.indexOf(current);
		}
		return -1;
	}

	
	
}
