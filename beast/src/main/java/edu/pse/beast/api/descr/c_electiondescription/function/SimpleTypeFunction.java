package edu.pse.beast.api.descr.c_electiondescription.function;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.descr.c_electiondescription.CElectionSimpleTypes;

public class SimpleTypeFunction extends CElectionDescriptionFunction {
    private List<CElectionSimpleTypes> argTypes;
    private List<String> argNames;
    private CElectionSimpleTypes outputType;

    public SimpleTypeFunction(final String name,
                              final List<CElectionSimpleTypes> argTypeList,
                              final List<String> argNameList,
                              final CElectionSimpleTypes outType) {
        super(name);
        this.argTypes = argTypeList;
        this.argNames = argNameList;
        this.outputType = outType;
    }

    private List<String> getArgs() {
        final List<String> args = new ArrayList<>();
        for (int i = 0; i < argTypes.size(); ++i) {
            final CElectionSimpleTypes st = argTypes.get(i);
            final String argVar = argNames.get(i);
            args.add(st.toString() + " " + argVar);
        }
        return args;
    }

    private String getArgString() {
        final List<String> argsList = getArgs();
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
    public final String getDeclCString(final CodeGenOptions codeGenOptions) {
        final String template = "RETURN_TYPE NAME(ARGS)";
        final String args = getArgString();
        return template.replaceAll("RETURN_TYPE", outputType.toString())
                       .replaceAll("NAME", getName())
                       .replaceAll("ARGS", args);
    }

    @Override
    public final String getReturnText(final CodeGenOptions codeGenOptions) {
        return "}";
    }

    public final CFunction toCFunc() {
        final List<String> args = getArgs();
        final CFunction created =
                new CFunction(getName(), args, outputType.toString());
        created.setCode(getCodeAsList());
        return created;
    }

    public final CElectionSimpleTypes getOutputType() {
        return outputType;
    }

    public final List<String> getArgNames() {
        return argNames;
    }

    public final List<CElectionSimpleTypes> getArgTypes() {
        return argTypes;
    }
}
