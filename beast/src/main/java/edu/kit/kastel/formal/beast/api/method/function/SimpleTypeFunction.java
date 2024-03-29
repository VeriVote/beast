package edu.kit.kastel.formal.beast.api.method.function;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.ccode.CFunction;
import edu.kit.kastel.formal.beast.api.io.PathHandler;
import edu.kit.kastel.formal.beast.api.method.CElectionSimpleType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class SimpleTypeFunction extends CElectionDescriptionFunction {
    private static final String FILE_PREFIX = "simple_";

    private static final String BLANK = " ";
    private static final String COMMA = ",";
    private static final String DECLARE_KEY = "DECLARATION";
    private static final String RETURN_KEY = "RETURN";

    private static final String RETURN_TYPE = "RETURN_TYPE";
    private static final String NAME = "NAME";
    private static final String ARGS = "ARGS";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private List<CElectionSimpleType> argTypes;
    private List<String> argNames;
    private CElectionSimpleType outputType;

    public SimpleTypeFunction(final String name,
                              final List<CElectionSimpleType> argTypeList,
                              final List<String> argNameList,
                              final CElectionSimpleType outType) {
        super(name);
        this.argTypes = argTypeList;
        this.argNames = argNameList;
        this.outputType = outType;
    }

    private List<String> getArgs() {
        final List<String> args = new ArrayList<String>();
        for (int i = 0; i < argTypes.size(); ++i) {
            final CElectionSimpleType st = argTypes.get(i);
            final String argVar = argNames.get(i);
            args.add(st.toString() + BLANK + argVar);
        }
        return args;
    }

    private String getArgString() {
        final List<String> argsList = getArgs();
        final StringBuilder args = new StringBuilder();
        for (int i = 0; i < argsList.size() - 1; ++i) {
            args.append(argsList.get(i) + COMMA + BLANK);
        }
        if (!argsList.isEmpty()) {
            args.append(argsList.get(argsList.size() - 1));
        }
        return args.toString();
    }

    public final String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }

    @Override
    public final String getDeclCString(final CodeGenOptions codeGenOptions) {
        return getTemplate(DECLARE_KEY)
                .replaceAll(RETURN_TYPE, outputType.toString())
                .replaceAll(NAME, getName())
                .replaceAll(ARGS, getArgString());
    }

    @Override
    public final String getReturnText(final CodeGenOptions codeGenOptions) {
        return getTemplate(RETURN_KEY);
    }

    public final CFunction toCFunc() {
        final List<String> args = getArgs();
        final CFunction created =
                new CFunction(getName(), args, outputType.toString());
        created.setCode(getCodeAsList());
        return created;
    }

    public final CElectionSimpleType getOutputType() {
        return outputType;
    }

    public final List<String> getArgNames() {
        return argNames;
    }

    public final List<CElectionSimpleType> getArgTypes() {
        return argTypes;
    }
}
