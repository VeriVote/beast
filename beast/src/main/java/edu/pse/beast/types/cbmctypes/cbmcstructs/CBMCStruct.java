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

	private final String structName;

	public CBMCStruct(InOutType inOutType) {
		this.inOutType = inOutType;

		this.dataType = inOutType.getDataType();
		this.dimensions = inOutType.getAmountOfDimensions();
		this.sizeOfDimensions = inOutType.getSizeOfDimensions();
		this.unsigned = inOutType.isDataTypeUnsigned();
		String generatedName = "auto_" + this.dataType.toString().replaceAll("\\s","");

		for (int i = 0; i < dimensions; i++) {
			generatedName = generatedName + sizeOfDimensions[i];
		}
		
		this.structName = generatedName;
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
		String sign = "";

		if (inOutType.isDataTypeUnsigned()) {
			sign = "unsigned ";
		}

		return "struct " + structName + " { " + sign + inOutType.getDataType() + " " + nameContainer.getResultArrName()
				+ " " + inOutType.getDimensionDescriptor(true) + ";};";
	}

	@Override
	public String getStructAccess() {
		return "struct " + structName;
	}
}
