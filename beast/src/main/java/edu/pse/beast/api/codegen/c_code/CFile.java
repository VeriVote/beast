package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CFile {

	private List<CFunction> funcs = new ArrayList<>();
	private List<CInclude> includes = new ArrayList<>();
	private List<CDefine> defines = new ArrayList<>(); 
	
	public void addFunctionDecl(String returnType, String name, List<String> args) {
		funcs.add(new CFunction(name, args, returnType));
	}
	
	public void include(String filePath) {
		this.includes.add(new CInclude(filePath));
	}
	
	public void define(String toReplace, String replaceWith) {
		defines.add(new CDefine(toReplace, replaceWith));
	}
}
