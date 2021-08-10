package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CFunction {
    private static final String NONE = "";
    private static final String BLANK = " ";
    private static final String INDENT = BLANK + BLANK + BLANK + BLANK;
    private static final String LINE_BREAK = "\n";
    private static final String COMMA = ",";
    private static final String SEMICOLON = ";";

    private static final String PAREN_OP = "(";
    private static final String PAREN_CL = ")";
    private static final String BRACE_OP = "{";
    private static final String BRACE_CL = "}";

    private String name;
    private List<CTypeNameBrackets> arguments = new ArrayList<CTypeNameBrackets>();
    private String returnType;
    private List<String> code;

    public CFunction(final String nameString,
                     final List<String> argumentList,
                     final String returnTypeString) {
        this.name = nameString;
        for (final String s : argumentList) {
            final String[] typeAndName = s.split(BLANK);
            final String argName = typeAndName[typeAndName.length - 1];
            String argType = NONE;
            for (int i = 0; i < typeAndName.length - 1; ++i) {
                argType += typeAndName[i];
                argType += i < typeAndName.length - 2 ? BLANK : NONE;
            }
            this.arguments.add(new CTypeNameBrackets(argType, argName, NONE));
        }
        this.returnType = returnTypeString;
    }

    public CFunction(final String nameString,
                     final String returnTypeString,
                     final List<CTypeNameBrackets> argumentList) {
        this.name = nameString;
        this.arguments.addAll(argumentList);
        this.returnType = returnTypeString;
    }

    public static List<String> sanitize(final List<String> list) {
        if (list != null && !list.isEmpty()) {
            final String fst = list.get(0);
            final int strip = fst != null ? fst.length() - fst.stripLeading().length() : 0;
            if (0 < strip) {
                // first sanitize (assuming relative indentations are okay) in case
                // the code has already been indented
                list.replaceAll(c -> c.substring(strip < c.length() ? strip : 0));
            }
        }
        return list;
    }

    public static List<String> indent(final List<String> list) {
        final List<String> indentedList = new ArrayList<String>();
        final List<String> sanitizedList = sanitize(list);
        if (sanitizedList != null && !sanitizedList.isEmpty()) {
            sanitizedList.forEach(c
                    -> indentedList.add(
                            c.replaceAll(LINE_BREAK, NONE).isBlank()
                            ? (c.contains(LINE_BREAK) ? LINE_BREAK : NONE)
                                    : INDENT.concat(c.replaceAll(LINE_BREAK, LINE_BREAK + INDENT))
                            )
            );
        }
        return indentedList;
    }

    public final void setCode(final List<String> codeStringList) {
        this.code = codeStringList;
    }

    private String signature() {
        return returnType + BLANK + name + PAREN_OP
                + String.join(COMMA + BLANK,
                              arguments.stream().map(a -> a.generateCode())
                              .collect(Collectors.toList()))
                + PAREN_CL;
    }

    public final String generateDefCode() {
        final List<String> created = new ArrayList<String>();
        created.add(signature() + BLANK + BRACE_OP);
        created.addAll(indent(code));
        created.add(BRACE_CL);
        created.add(NONE);
        return String.join(LINE_BREAK, created);
    }

    public final String generateDeclCode() {
        return signature() + SEMICOLON;
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class PropertyExpressions {
        public final BooleanExpASTData preAstData;
        public final BooleanExpASTData postAstData;
        public final List<SymbolicCBMCVar> symCbmcVars;

        public PropertyExpressions(final BooleanExpASTData precondition,
                                   final BooleanExpASTData postcondition,
                                   final List<SymbolicCBMCVar> symbolicVariables) {
            this.preAstData = precondition;
            this.postAstData = postcondition;
            this.symCbmcVars = symbolicVariables;
        }
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class Parameter {
        private static final String DEFAULT_PARAM_NAME = "x";

        public final String type;
        public final String name;

        public Parameter(final String parameterType,
                         final String parameterName) {
            this.type = parameterType;
            this.name = parameterName;
        }

        public Parameter(final String parameterType) {
            this.type = parameterType;
            this.name = DEFAULT_PARAM_NAME;
        }
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class VotingFunction {
        public final ElectionTypeCStruct votes;
        public final ElectionTypeCStruct result;
        public final VotingInputTypes profileType;
        public final VotingOutputTypes resultType;
        public final String function;

        public VotingFunction(final ElectionTypeCStruct voteStruct,
                              final ElectionTypeCStruct resultStruct,
                              final VotingInputTypes inputType,
                              final VotingOutputTypes outputType,
                              final String functionName) {
            this.votes = voteStruct;
            this.result = resultStruct;
            this.profileType = inputType;
            this.resultType = outputType;
            this.function = functionName;
        }
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class Input {
        public final VotingInputTypes type;
        public final ElectionTypeCStruct struct;
        public final String structVarName;

        public Input(final VotingInputTypes votingInputType,
                     final ElectionTypeCStruct inputStruct,
                     final String votingStructVarName) {
            this.type = votingInputType;
            this.struct = inputStruct;
            this.structVarName = votingStructVarName;
        }
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class Output {
        public final VotingOutputTypes type;
        public final ElectionTypeCStruct struct;
        public final String structVarName;

        public Output(final VotingOutputTypes votingOutputType,
                      final ElectionTypeCStruct outputStruct,
                      final String resultStructVarName) {
            this.type = votingOutputType;
            this.struct = outputStruct;
            this.structVarName = resultStructVarName;
        }
    }
}
