package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CFile {
    private List<CFunction> funcs = new ArrayList<>();
    private List<CFunction> funcDecls = new ArrayList<>();
    private List<CInclude> includes = new ArrayList<>();
    private List<CDefine> defines = new ArrayList<>();
    private List<CStruct> structs = new ArrayList<>();
    private List<CTypeDef> typedefs = new ArrayList<>();
    private List<String> declarations = new ArrayList<>();

    public void addTypeDef(final CTypeDef typeDef) {
        this.typedefs.add(typeDef);
    }

    public void addTypeDef(final List<CTypeDef> typeDefs) {
        for (final CTypeDef td : typeDefs) {
            addTypeDef(td);
        }
    }

    public void addFunction(final CFunction func) {
        this.funcs.add(func);
    }

    public void addFunctionDecl(final String returnType,
                                final String name,
                                final List<String> args) {
        funcDecls.add(new CFunction(name, args, returnType));
    }

    public void include(final String filePath) {
        this.includes.add(new CInclude(filePath));
    }

    public void define(final String toReplace,
                       final String replaceWith) {
        defines.add(new CDefine(toReplace, replaceWith));
    }

    public void addStructDef(final CStruct struct) {
        structs.add(struct);
    }

    public String generateCode() {
        final List<String> created = new ArrayList<>();
        for (final CInclude inc : includes) {
            created.add(inc.generateCode());
        }
        created.add("\n");
        for (final CDefine def : defines) {
            created.add(def.generateCode());
        }
        created.add("\n");
        for (final CTypeDef tdef : typedefs) {
            created.add(tdef.generateCode());
        }
        created.add("\n");
        for (final String decl : declarations) {
            created.add(decl);
        }
        created.add("\n");
        for (final CStruct s : structs) {
            created.add(s.generateDefCode());
        }
        created.add("\n");
        for (final CFunction func : funcDecls) {
            created.add(func.generateDeclCode());
        }
        created.add("\n");
        for (final CFunction func : funcs) {
            created.add(func.generateDefCode());
            created.add("\n");
        }
        created.add("\n");
        return String.join("\n", created);
    }

    public void declare(final String declCString) {
        declarations.add(declCString + ";\n");
    }
}
