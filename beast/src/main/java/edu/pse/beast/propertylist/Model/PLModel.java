package edu.pse.beast.propertylist.Model;

import java.util.ArrayList;
import java.util.Observable;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.PropertyItem;

public class PLModel extends Observable implements PLModelInterface {
	
	private ArrayList<PropertyItem> propertyList;
	private int dirtyIndex = -1;
	private int updateIndex = -1;

	@Override
	public void initialize() {
		if (propertyList == null) propertyList = new ArrayList<PropertyItem>();
		//if (propertyList.isEmpty()) propertyList.add(new PropertyItem());
	}

	@Override
	public boolean changeName(PropertyItem prop, String newName) {
		int index = propertyList.indexOf(prop);
		if (index == -1) return false;
		if (indexOfName(newName) != -1) return false;
		
		PostAndPrePropertiesDescription old = propertyList.get(index).getDescription();
		propertyList.get(index).setDescription(newName, old.getPrePropertiesDescription(),
				old.getPostPropertiesDescription(), old.getSymVarList());
		//updateIndex = index;
		return true;
	}

	@Override
	public boolean addDescription(PostAndPrePropertiesDescription desc) {
		//propertyList.add(new PropertyItem(desc)); TODO
		return true;
	}

	@Override
	public boolean addNewProperty(BooleanExpEditor editor) {
		//saveDirtyItem(editor);
		
		String name = "Eigenschaft ";
		int i = 0;
		while (indexOfName(name + i) != -1) i++;
		
		PropertyItem newItem = new PropertyItem(new PostAndPrePropertiesDescription(name + i), false);
		propertyList.add(newItem);
		//editor.loadPostAndPreProperties(new PostAndPrePropertiesDescription(name + i));
		dirtyIndex = propertyList.indexOf(newItem);
		updateView();
		return true;
	}

	@Override
	public void editProperty(PropertyItem prop, BooleanExpEditor editor) {
		//saveDirtyItem(editor);
		
		editor.loadPostAndPreProperties(prop.getDescription());
		dirtyIndex = propertyList.indexOf(prop);
		updateView();
	}

	@Override
	public boolean deleteProperty(PropertyItem prop, BooleanExpEditor editor) {
		int index = propertyList.indexOf(prop);
		if (index == -1) return false;
		
		if (dirtyIndex == index) {
			editor.loadPostAndPreProperties(propertyList.get(0).getDescription());
			dirtyIndex = 0;
		}
		
		propertyList.remove(index);
		if (index < dirtyIndex) dirtyIndex--;
		updateIndex = index;
		updateView();
		return true;
	}

	@Override
	public void setTestStatus(PropertyItem prop, boolean newStatus) {
		prop.setTestStatus(newStatus);
		
	}

	@Override
	public ArrayList<PropertyItem> getList() {
		return propertyList;
	}

	
	public int getDirtyIndex() {
		return dirtyIndex;
	}
	public int getUpdateIndex() {
		return updateIndex;
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
	
	private void saveDirtyItem(BooleanExpEditor editor) {
		if (dirtyIndex == -1) return;
		PropertyItem changed = propertyList.get(dirtyIndex);
		changed.setDescription(editor.getCurrentlyLoadedPostAndPreProp());
		propertyList.set(dirtyIndex, changed);
	}
	

	
	
}
