package edu.pse.beast.types;

import edu.pse.beast.toolbox.UnifiedNameContainer;

public abstract class ComplexType {

	public abstract boolean equals(Object toCompare);
	
	/**
	 * 
	 * @return the string which defines this struct
	 */
	public abstract String getStructDefinition(UnifiedNameContainer nameContainer);
	
	public abstract String getStructAccess();
	
}
