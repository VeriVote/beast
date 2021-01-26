package edu.pse.beast.api.codegen;

import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class AllInputAndOutputTypes {

	VotingInputTypes inputTypes;
	VotingOutputTypes outputTypes;
	ElectionTypeCStruct inputStruct;
	ElectionTypeCStruct outputStruct;

	public AllInputAndOutputTypes(VotingInputTypes inputTypes, VotingOutputTypes outputTypes,
			ElectionTypeCStruct inputStruct, ElectionTypeCStruct outputStruct) {
		super();
		this.inputTypes = inputTypes;
		this.outputTypes = outputTypes;
		this.inputStruct = inputStruct;
		this.outputStruct = outputStruct;
	}

	public VotingInputTypes getInputTypes() {
		return inputTypes;
	}

	public VotingOutputTypes getOutputTypes() {
		return outputTypes;
	}

	public ElectionTypeCStruct getInputStruct() {
		return inputStruct;
	}

	public ElectionTypeCStruct getOutputStruct() {
		return outputStruct;
	}

}
