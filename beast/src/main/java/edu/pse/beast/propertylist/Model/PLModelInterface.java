package edu.pse.beast.propertylist.Model;

import java.util.ArrayList;
import java.util.Observer;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.PropertyItem;

public interface PLModelInterface {
	void initialize();
	
	boolean changeName(PropertyItem prop, String newName);
	boolean addDescription(PostAndPrePropertiesDescription desc);
	boolean addNewProperty();
	void editProperty(PropertyItem prop);
	PropertyItem deleteProperty(PropertyItem prop);
	void setTestStatus(PropertyItem prop);
	
	ArrayList<PropertyItem> getList();
	
	int getDirtyIndex();

	void addObserver(Observer o);
	void deleteObserver(Observer o);
}
