package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CFile {

	private List<CFunction> funcs = new ArrayList<>();
	private List<CFunction> funcDecls = new ArrayList<>();
	private List<CInclude> includes = new ArrayList<>();
	private List<CDefine> defines = new ArrayList<>();
	private List<CStruct> structs = new ArrayList<>();

	public void addFunction(CFunction func) {
		this.funcs.add(func);
	}

	public void addFunctionDecl(String returnType, String name, List<String> args) {
		funcDecls.add(new CFunction(name, args, returnType));
	}

	public void include(String filePath) {
		this.includes.add(new CInclude(filePath));
	}

	public void define(String toReplace, String replaceWith) {
		defines.add(new CDefine(toReplace, replaceWith));
	}

	public String generateCode() {
		List<String> created = new ArrayList<>();
		for (CInclude inc : includes) {
			created.add(inc.generateCode());
		}
		for (CDefine def : defines) {
			created.add(def.generateCode());
		}
		for (CStruct s : structs) {
			created.add(s.generateDefCode());
		}
		for (CFunction func : funcDecls) {
			created.add(func.generateDeclCode());
		}
		for (CFunction func : funcs) {
			created.add(func.generateDefCode());
		}
		return String.join("\n", created);
	}

	public void addStructDef(CStruct struct) {
		structs.add(struct);
	}
}
