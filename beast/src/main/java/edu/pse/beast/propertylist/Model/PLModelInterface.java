package edu.pse.beast.propertylist.Model;

import java.util.ArrayList;
import java.util.Observer;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.PropertyItem;

public interface PLModelInterface {
	void initialize();
	
	boolean changeName(PropertyItem prop, String newName);
	boolean addDescription(PostAndPrePropertiesDescription desc);
	boolean addNewProperty(BooleanExpEditor editor);
	void editProperty(PropertyItem prop, BooleanExpEditor editor);
	boolean deleteProperty(PropertyItem prop, BooleanExpEditor editor);
	void setTestStatus(PropertyItem prop, boolean newStatus);
	
	ArrayList<PropertyItem> getList();
	
	int getDirtyIndex();
	int getUpdateIndex();

	void addObserver(Observer o);
	void deleteObserver(Observer o);
}
