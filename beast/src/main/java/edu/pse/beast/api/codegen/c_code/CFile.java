package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CFile {
    private static final String LINE_BREAK = "\n";
    private static final String SEMICOLON = ";";

    private List<CFunction> funcs = new ArrayList<CFunction>();
    private List<CFunction> funcDecls = new ArrayList<CFunction>();
    private List<CInclude> includes = new ArrayList<CInclude>();
    private List<CDefine> defines = new ArrayList<CDefine>();
    private List<CStruct> structs = new ArrayList<CStruct>();
    private List<CTypeDef> typedefs = new ArrayList<CTypeDef>();
    private List<String> declarations = new ArrayList<String>();

    public final void addTypeDef(final CTypeDef typeDef) {
        this.typedefs.add(typeDef);
    }

    public final void addTypeDef(final List<CTypeDef> typeDefs) {
        for (final CTypeDef td : typeDefs) {
            addTypeDef(td);
        }
    }

    public final void addFunction(final CFunction func) {
        this.funcs.add(func);
    }

    public final void addFunctionDecl(final String returnType,
                                      final String name,
                                      final List<String> args) {
        funcDecls.add(new CFunction(name, args, returnType));
    }

    public final void include(final String filePath) {
        this.includes.add(new CInclude(filePath));
    }

    public final void define(final String toReplace,
                             final String replaceWith) {
        defines.add(new CDefine(toReplace, replaceWith));
    }

    public final void addStructDef(final CStruct struct) {
        structs.add(struct);
    }

    public final String generateCode() {
        final List<String> created = new ArrayList<String>();
        for (final CInclude inc : includes) {
            created.add(inc.generateCode());
        }
        created.add(LINE_BREAK);
        for (final CDefine def : defines) {
            created.add(def.generateCode());
        }
        created.add(LINE_BREAK);
        for (final CTypeDef tdef : typedefs) {
            created.add(tdef.generateCode());
        }
        created.add(LINE_BREAK);
        for (final String decl : declarations) {
            created.add(decl);
        }
        created.add(LINE_BREAK);
        for (final CStruct s : structs) {
            created.add(s.generateDefCode());
        }
        created.add(LINE_BREAK);
        for (final CFunction func : funcDecls) {
            created.add(func.generateDeclCode());
        }
        created.add(LINE_BREAK);
        for (final CFunction func : funcs) {
            created.add(func.generateDefCode());
            created.add(LINE_BREAK);
        }
        created.add(LINE_BREAK);
        return String.join(LINE_BREAK, created);
    }

    public final void declare(final String declCString) {
        declarations.add(declCString + SEMICOLON + LINE_BREAK);
    }
}
