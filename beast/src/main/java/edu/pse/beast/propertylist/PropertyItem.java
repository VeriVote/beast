package edu.pse.beast.propertylist;

import java.util.UUID;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;

/**
*
* @author Justin
*/
public class PropertyItem {
	
	private final PostAndPrePropertiesDescription description;
	private Boolean willBeTested;
	
	public PropertyItem(PostAndPrePropertiesDescription descr, Boolean testStatus) {
		description = descr;
		willBeTested = testStatus;
	}
	public PropertyItem(PostAndPrePropertiesDescription descr) {
		this(descr, true);
	}
	public PropertyItem() {
		this(new PostAndPrePropertiesDescription(UUID.randomUUID().toString()), false);
	}
	
	
	public PostAndPrePropertiesDescription getDescription() {
		return description;
	}
	
	public Boolean willBeTested() {
		return willBeTested;
	}

	@Override
	public boolean equals (Object o) {
		if (o == null || this.getClass() != o.getClass()) return false;
		if (this.description.getName() == ((PropertyItem)o).description.getName()) return true;
		else return false;
	}
}
