package edu.pse.beast.types;

import java.util.Arrays;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.cbmctypes.cbmcstructs.CBMCStruct;

public abstract class ComplexType {

	public abstract boolean equals(Object toCompare);
	
	/**
	 * 
	 * @return the string which defines this struct
	 */
	public abstract String getStructDefinition(UnifiedNameContainer nameContainer);
	
	public abstract String getStructAccess();
	
}
