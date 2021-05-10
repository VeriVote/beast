package edu.pse.beast.api.electiondescription.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class VotingSigFunction extends CElectionDescriptionFunction {
	private String name;
	private String outputArrayName = "result";
	private List<String> code = new ArrayList<>();
	VotingInputTypes inputType;
	VotingOutputTypes outputType;

	public VotingSigFunction(String name, VotingInputTypes inputType,
			VotingOutputTypes outputType) {
		super(name);
		this.name = name;
		this.inputType = inputType;
		this.outputType = outputType;
	}

	public String getName() {
		return name;
	}

	public VotingInputTypes getInputType() {
		return inputType;
	}

	public VotingOutputTypes getOutputType() {
		return outputType;
	}

	@Override
	public String getDeclCString() {
		String template = "RETURN_TYPE NAME(ARG)";

		String returnType = "";
		switch (outputType) {
			case CANDIDATE_LIST :
				returnType = "unsigned int [C]";
				break;
			case PARLIAMENT :
				returnType = "unsigned int [S]";
				break;
			case PARLIAMENT_STACK :
				returnType = "unsigned int [S]";
				break;
			case SINGLE_CANDIDATE :
				returnType = "unsigned int ";
				break;
		}

		String arg = "";
		switch (inputType) {
			case APPROVAL :
				arg = "unsigned int[V][C] votes";
				break;
			case WEIGHTED_APPROVAL :
				arg = "unsigned int[V][C] votes";
				break;
			case PREFERENCE :
				arg = "unsigned int[V][C] votes";
				break;
			case SINGLE_CHOICE :
				arg = "unsigned int[V] votes";
				break;
			case SINGLE_CHOICE_STACK :
				arg = "unsigned int[C] votes";
				break;

		}

		return template.replaceAll("RETURN_TYPE", returnType)
				.replaceAll("ARG", arg).replaceAll("NAME", getName());
	}

}
