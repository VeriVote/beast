package edu.pse.beast.electionSimulator;

import java.util.List;

import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

public class InputDataDescription {
	
	public final List<List<String>> data;
	public final InputType inType;
	public final OutputType outType;
	
	public InputDataDescription(List<List<String>> data, InputType inType, OutputType outType) {
		this.data = data;
		this.inType = inType;
		this.outType = outType;
	}
}
