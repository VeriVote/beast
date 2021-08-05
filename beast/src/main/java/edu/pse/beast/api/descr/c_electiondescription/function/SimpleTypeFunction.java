package edu.pse.beast.api.descr.c_electiondescription.function;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.descr.c_electiondescription.CElectionSimpleTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class SimpleTypeFunction extends CElectionDescriptionFunction {
    private static final String RESOURCES =
            "/edu/pse/beast/api/descr/c_electiondescription/function/";
    private static final String FILE_PREFIX = "simple_";
    private static final String FILE_ENDING = ".template";

    private static final String BLANK = " ";
    private static final String COMMA = ",";
    private static final String DECLARE_KEY = "DECLARATION";
    private static final String RETURN_KEY = "RETURN";

    private static final String RETURN_TYPE = "RETURN_TYPE";
    private static final String NAME = "NAME";
    private static final String ARGS = "ARGS";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

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
            args.add(st.toString() + BLANK + argVar);
        }
        return args;
    }

    private String getArgString() {
        final List<String> argsList = getArgs();
        String args = "";
        for (int i = 0; i < argsList.size() - 1; ++i) {
            args += argsList.get(i) + COMMA + BLANK;
        }
        if (!argsList.isEmpty()) {
            args += argsList.get(argsList.size() - 1);
        }
        return args;
    }

    public static final String getTemplate(final String key,
                                           final Class<?> c) {
        assert key != null;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES
                            + FILE_PREFIX + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }

    @Override
    public final String getDeclCString(final CodeGenOptions codeGenOptions) {
        final Class<?> c = this.getClass();
        return getTemplate(DECLARE_KEY, c)
                .replaceAll(RETURN_TYPE, outputType.toString())
                .replaceAll(NAME, getName())
                .replaceAll(ARGS, getArgString());
    }

    @Override
    public final String getReturnText(final CodeGenOptions codeGenOptions) {
        final Class<?> c = this.getClass();
        return getTemplate(RETURN_KEY, c);
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
