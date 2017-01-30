package edu.pse.beast.propertylist.Model;

import java.util.ArrayList;
import java.util.Observer;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.PropertyItem;

public interface PLModelInterface {
	void initialize();
	
	boolean changeName(PropertyItem prop, String newName);
	boolean addDescription(PostAndPrePropertiesDescription desc);
	boolean newDescription(String name);
	void changeDescription(PropertyItem prop);
	PropertyItem deleteDescription(PropertyItem prop);
	void changeTestedStatus(PropertyItem prop);
	
	ArrayList<PropertyItem> getDescr();

	void addObserver(Observer o);
	void deleteObserver(Observer o);
}
