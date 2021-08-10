package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CFile {
    private static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final String LINE_BREAK = "\n";
    private static final String SEMICOLON = ";";

    private List<CFunction> funcs = new ArrayList<CFunction>();
    private List<CFunction> funcDecls = new ArrayList<CFunction>();
    private List<CInclude> includes = new ArrayList<CInclude>();
    private List<CDefine> defines = new ArrayList<CDefine>();
    private List<CStruct> structs = new ArrayList<CStruct>();
    private List<String> declarations = new ArrayList<String>();

    private static <T> void endList(final List<String> list) {
        if (list != null
                && !list.isEmpty()
                && !list.get(list.size() - 1).isBlank()
                && !list.get(list.size() - 1).endsWith(LINE_BREAK)) {
            list.add(EMPTY);
        }
    }

    public final void addFunction(final CFunction func) {
        this.funcs.add(func);
    }

    private void addFunctionDecl(final String returnType,
                                 final List<String> args,
                                 final String name) {
        funcDecls.add(new CFunction(name, args, returnType));
    }

    public final void addFunctionDecl(final String returnType,
                                      final String name,
                                      final List<CFunction.Parameter> params) {
        final List<String> args = new LinkedList<String>();
        for (final CFunction.Parameter param
                : params != null
                ? params : new LinkedList<CFunction.Parameter>()) {
            args.add(param.type + BLANK + param.name);
        }
        addFunctionDecl(returnType, args, name);
    }

    public final void addFunctionDecl(final String returnType,
                                      final String name,
                                      final CFunction.Parameter param) {
        this.addFunctionDecl(returnType, name, List.of(param));
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

    public final void declare(final String declCString) {
        declarations.add(declCString + SEMICOLON);
    }

    public final String generateCode() {
        final List<String> created = new ArrayList<String>();
        for (final CInclude inc : includes) {
            created.add(inc.generateCode());
        }
        endList(created);
        for (final CStruct s : structs) {
            created.add(s.generateDefCode());
        }
        endList(created);
        for (final String decl : declarations) {
            created.add(decl);
        }
        endList(created);
        for (final CFunction func : funcDecls) {
            created.add(func.generateDeclCode());
        }
        endList(created);
        for (final CDefine def : defines) {
            created.add(def.generateCode());
        }
        endList(created);
        for (final CFunction func : funcs) {
            created.add(func.generateDefCode());
        }
        return String.join(LINE_BREAK, created);
    }
}
