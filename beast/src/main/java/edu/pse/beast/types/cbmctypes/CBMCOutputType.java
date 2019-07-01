package edu.pse.beast.types.cbmctypes;

import edu.pse.beast.types.OutputType;

public abstract class CBMCOutputType extends OutputType {

	public CBMCOutputType(String dataType, int dimensions, String[] sizeOfDimensions) {
        super(dataType, dimensions, sizeOfDimensions);
    }
	
}
