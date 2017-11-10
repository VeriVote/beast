package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class PropertyAndMarginBool {
	
	private PreAndPostConditionsDescription description;
	private boolean marginStatus;
	
	public PropertyAndMarginBool(PreAndPostConditionsDescription description, boolean marginStatus) {
		this.description = description;
		this.marginStatus = marginStatus;
	}
	
	public PreAndPostConditionsDescription getDescription() {
		return description;
	}
	
	public boolean getMarginStatus() {
		return marginStatus;
	}
}
