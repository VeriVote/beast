package edu.pse.beast.types.cbmctypes;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.InOutType.DataType;

public abstract class CBMCOutputType extends OutputType {

	public CBMCOutputType(boolean unsigned, DataType dataType, int dimensions, String[] sizeOfDimensions) {
        super(unsigned, dataType, dimensions, sizeOfDimensions);
    }
	
}
