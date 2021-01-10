package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CFunction {
	private String name;
	private List<CFuncArg> args = new ArrayList<>();
	private String returnType;
	
	public CFunction(String name, List<String> args, String returnType) {
		this.name = name;
		for(String s : args) {
			String typeAndName[] = s.split(" ");
			this.args.add(new CFuncArg(typeAndName[0], typeAndName[1]));
		}
		this.returnType = returnType;
	}
}
