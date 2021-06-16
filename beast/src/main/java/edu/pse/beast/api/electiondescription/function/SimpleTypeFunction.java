package edu.pse.beast.api.electiondescription.function;

import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;

public class SimpleTypeFunction extends CElectionDescriptionFunction {
	private List<CElectionSimpleTypes> arguments;
	private CElectionSimpleTypes outputType;

	public SimpleTypeFunction(String name, List<CElectionSimpleTypes> arguments,
			CElectionSimpleTypes outputType) {
		super(name);
		this.arguments = arguments;
		this.outputType = outputType;
	}

	@Override
	public String getDeclCString(CodeGenOptions codeGenOptions) {
		String template = "RETURN_TYPE NAME(ARGS)";
		String args = "";

		for (CElectionSimpleTypes st : arguments) {
			args += st.toString();
		}

		return template.replaceAll("RETURN_TYPE", outputType.toString())
				.replaceAll("NAME", getName()).replaceAll("ARGS", args);

	}

}
