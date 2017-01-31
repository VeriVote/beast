package edu.pse.beast.propertylist;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;

public interface PLControllerInterface {
	
	void toggleResult();
	
	void changeName(PropertyItem prop, String newName);
	void setTestStatus(PropertyItem prop);
	void editProperty(PropertyItem prop);
	void deleteProperty(PropertyItem prop);
	void addDescription(PostAndPrePropertiesDescription desc);
	void addNewProperty();
	
	
}
