package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CFunction {
    private String name;
    private List<CTypeNameBrackets> arguments = new ArrayList<>();
    private String returnType;
    private List<String> code;

    public CFunction(final String nameString,
                     final List<String> argumentList,
                     final String returnTypeString) {
        this.name = nameString;
        for (final String s : argumentList) {
            final String[] typeAndName = s.split(" ");
            final String argName = typeAndName[typeAndName.length - 1];
            String argType = "";
            for (int i = 0; i < typeAndName.length - 1; ++i) {
                argType += typeAndName[i];
                argType += i < typeAndName.length - 2 ? " " : "";
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

    public void setCode(final List<String> codeStringList) {
        this.code = codeStringList;
    }

    private String signature() {
        return returnType + " " + name + "(" + String.join(", ", arguments.stream()
                .map(a -> a.generateCode()).collect(Collectors.toList())) + ")";
    }

    public String generateDefCode() {
        final List<String> created = new ArrayList<>();
        created.add(signature());
        created.add("{");
        created.addAll(code);
        created.add("}");
        return String.join("\n", created);
    }

    public String generateDeclCode() {
        return signature() + ";";
    }
}
