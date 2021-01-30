package edu.pse.beast.api.codegen;

public class InputAndOutputElectionStructs {

	ElectionTypeCStruct input;
	ElectionTypeCStruct output;

	public InputAndOutputElectionStructs(ElectionTypeCStruct input, ElectionTypeCStruct output) {
		super();
		this.input = input;
		this.output = output;
	}

	public ElectionTypeCStruct getInput() {
		return input;
	}

	public ElectionTypeCStruct getOutput() {
		return output;
	}

}
