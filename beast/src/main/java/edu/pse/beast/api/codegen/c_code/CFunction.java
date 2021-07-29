package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

public class CFunction {
    private static final String BLANK = " ";
    private static final String LINE_BREAK = "\n";
    private static final String COMMA = ",";
    private static final String SEMICOLON = ";";

    private static final String PAREN_OP = "(";
    private static final String PAREN_CL = ")";
    private static final String BRACE_OP = "{";
    private static final String BRACE_CL = "}";

    private String name;
    private List<CTypeNameBrackets> arguments = new ArrayList<>();
    private String returnType;
    private List<String> code;

    public CFunction(final String nameString,
                     final List<String> argumentList,
                     final String returnTypeString) {
        this.name = nameString;
        for (final String s : argumentList) {
            final String[] typeAndName = s.split(BLANK);
            final String argName = typeAndName[typeAndName.length - 1];
            String argType = "";
            for (int i = 0; i < typeAndName.length - 1; ++i) {
                argType += typeAndName[i];
                argType += i < typeAndName.length - 2 ? BLANK : "";
            }
            this.arguments.add(new CTypeNameBrackets(argType, argName, ""));
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
        final List<String> created = new ArrayList<>();
        created.add(signature() + BLANK + BRACE_OP);
        created.addAll(code);
        created.add(BRACE_CL);
        return String.join(LINE_BREAK, created);
    }

    public final String generateDeclCode() {
        return signature() + SEMICOLON;
    }

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
