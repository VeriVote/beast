package edu.pse.beast.api.electiondescription.function;

import java.util.List;

import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;

public class SimpleTypeFunction extends CElectionDescriptionFunction {
	private List<CElectionSimpleTypes> argTypes;
	private List<String> argNames;
	private CElectionSimpleTypes outputType;

	public SimpleTypeFunction(String name, List<CElectionSimpleTypes> argTypes,
			List<String> argNames, CElectionSimpleTypes outputType) {
		super(name);
		this.argTypes = argTypes;
		this.argNames = argNames;
		this.outputType = outputType;
	}

	@Override
	public String getDeclCString(CodeGenOptions codeGenOptions) {
		String template = "RETURN_TYPE NAME(ARGS)";
		String args = "";

		for (CElectionSimpleTypes st : argTypes) {
			args += st.toString();
		}

		return template.replaceAll("RETURN_TYPE", outputType.toString())
				.replaceAll("NAME", getName()).replaceAll("ARGS", args);

	}

	@Override
	public String getReturnText(CodeGenOptions codeGenOptions) {
		return "}";
	}

}
