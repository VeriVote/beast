package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;

public class PropertyAndMarginBool {
	
	private PostAndPrePropertiesDescription description;
	private boolean marginStatus;
	
	public PropertyAndMarginBool(PostAndPrePropertiesDescription description, boolean marginStatus) {
		this.description = description;
		this.marginStatus = marginStatus;
	}
	
	public PostAndPrePropertiesDescription getDescription() {
		return description;
	}
	
	public boolean getMarginStatus() {
		return marginStatus;
	}
}
