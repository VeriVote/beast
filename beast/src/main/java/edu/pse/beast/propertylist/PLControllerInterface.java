package edu.pse.beast.propertylist;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;

public interface PLControllerInterface {
	
	void toggleResult();
	
	boolean changeName(PropertyItem prop, String newName);
	void setTestStatus(PropertyItem prop);
	void editProperty(PropertyItem prop);
	PropertyItem deleteProperty(PropertyItem prop);
	boolean addDescription(PostAndPrePropertiesDescription desc);
	boolean addNewProperty(String name);
	
	
}
