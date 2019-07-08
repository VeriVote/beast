package edu.pse.beast.types.cbmctypes.cbmcstructs;

import java.util.Arrays;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.ComplexType;
import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InOutType.DataType;

public class CBMCStruct extends ComplexType {

	public final InOutType inOutType;
	
	public final DataType dataType;
	public final int dimensions;
	public final String[] sizeOfDimensions;
	public final boolean unsigned;
	
	private boolean isSet = false;
	private String structName = "";

	public CBMCStruct(InOutType inOutType) {
		this.inOutType = inOutType;
		
		this.dataType = inOutType.getDataType();
		this.dimensions = inOutType.getAmountOfDimensions();
		this.sizeOfDimensions = inOutType.getSizeOfDimensions();
		this.unsigned = inOutType.isDataTypeUnsigned();
	}

	@Override
	public boolean equals(Object toCompare) {
		if (this == toCompare) {
			return true;
		}

		if (this.getClass().equals(toCompare.getClass())) {
			CBMCStruct otherStruct = (CBMCStruct) toCompare;

			return (this.dataType == otherStruct.dataType && this.dimensions == otherStruct.dimensions
					&& Arrays.equals(this.sizeOfDimensions, otherStruct.sizeOfDimensions)
					&& this.unsigned == otherStruct.unsigned);
		}

		return false;

	}
	
	/**
	 * 
	 * @return the string which defines this struct
	 */
	@Override
	public String getStructDefinition(UnifiedNameContainer nameContainer) {
		this.structName = dataType.toString();
		
		for(int i = 0; i < dimensions; i++) {
			this.structName = structName + sizeOfDimensions[i];
		}
		
		this.isSet = true;
		
		String sign = "";
		
		if (inOutType.isDataTypeUnsigned()) {
			sign = "unsigned ";
		}
		
		return "struct " + structName + " { "
				+ sign + inOutType.getDataType() + " " + nameContainer.getResultArrName() 
				+ " " + inOutType.getDimensionDescriptor(true) 
				+ "]; };";	
	}
	
	@Override
	public String getStructAccess() {
		if (isSet) {
			return "struct " + structName;
		} else {
			System.out.println("The struct was not yet created, so no name was set");
			throw new IllegalArgumentException();
		}
	}
}
