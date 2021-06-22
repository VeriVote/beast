package edu.pse.beast.api.electiondescription.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.to_c.FunctionToC;

public class VotingSigFunction
		extends CElectionDescriptionFunction {
	private String name;
	private String resultArrayName = "result";
	private String votesArrayName = "votes";
	private List<String> code = new ArrayList<>();
	VotingInputTypes inputType;
	VotingOutputTypes outputType;

	public VotingSigFunction(String name,
			VotingInputTypes inputType,
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

	private String getReturnType() {
		switch (outputType) {
		case CANDIDATE_LIST:
			return "unsigned int [C]";
		case PARLIAMENT:
			return "unsigned int [S]";
		case PARLIAMENT_STACK:
			return "unsigned int [S]";
		case SINGLE_CANDIDATE:
			return "unsigned int ";
		}
		return "";
	}

	private String getArgType() {
		switch (inputType) {
		case APPROVAL:
			return "unsigned int[V][C] votes";
		case WEIGHTED_APPROVAL:
			return "unsigned int[V][C] votes";
		case PREFERENCE:
			return "unsigned int[V][C] votes";
		case SINGLE_CHOICE:
			return "unsigned int[V] votes";
		case SINGLE_CHOICE_STACK:
			return "unsigned int[C] votes";
		}
		return "";
	}

	// TODO(Holger) This can be moves somewhere else, just dont know where yet.
	// Probably together with the rest of code generation.
	@Override
	public String getDeclCString(
			CodeGenOptions codeGenOptions) {
		String template = "RETURN_TYPE NAME(ARG) {\n"
				+ "    RESULT_ARR;";

		String returnType = getReturnType();

		String arg = getArgType();

		CTypeNameBrackets resultType = FunctionToC
				.votingTypeToC(
						CElectionVotingType.of(outputType),
						resultArrayName, codeGenOptions);

		return template
				.replaceAll("RETURN_TYPE", returnType)
				.replaceAll("ARG", arg)
				.replaceAll("NAME", getName())
				.replaceAll("RESULT_ARR",
						resultType.generateCode());
	}

	public String getResultArrayName() {
		return resultArrayName;
	}

	public String getVotesArrayName() {
		return votesArrayName;
	}

	@Override
	public String getReturnText(
			CodeGenOptions codeGenOptions) {
		String template = "return RETURN_NAME;\n}";
		return template.replaceAll("RETURN_NAME",
				resultArrayName);
	}

}
