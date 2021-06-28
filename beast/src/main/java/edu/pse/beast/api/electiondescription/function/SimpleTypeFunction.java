package edu.pse.beast.api.electiondescription.function;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.c_code.CFunction;
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

	private List<String> getArgs() {
		List<String> args = new ArrayList<>();

		for (int i = 0; i < argTypes.size(); ++i) {
			CElectionSimpleTypes st = argTypes.get(i);
			String argVar = argNames.get(i);
			args.add(st.toString() + " " + argVar);
		}

		return args;
	}

	private String getArgString() {
		List<String> argsList = getArgs();

		String args = "";
		for (int i = 0; i < argsList.size() - 1; ++i) {
			args += argsList.get(i) + ", ";
		}
		if (!argsList.isEmpty()) {
			args += argsList.get(argsList.size() - 1);
		}
		return args;
	}

	@Override
	public String getDeclCString(CodeGenOptions codeGenOptions) {
		String template = "RETURN_TYPE NAME(ARGS)";
		String args = getArgString();
		template =  template.replaceAll("RETURN_TYPE", outputType.toString())
				.replaceAll("NAME", getName()).replaceAll("ARGS", args);
		return template;
	}

	@Override
	public String getReturnText(CodeGenOptions codeGenOptions) {
		return "}";
	}

	public CFunction toCFunc() {
		List<String> args = getArgs();
		CFunction created = new CFunction(getName(), args,
				outputType.toString());
		created.setCode(getCodeAsList());
		return created;
	}
	
	public CElectionSimpleTypes getOutputType() {
		return outputType;
	}
	
	public List<String> getArgNames() {
		return argNames;
	}
	
	public List<CElectionSimpleTypes> getArgTypes() {
		return argTypes;
	}

}
