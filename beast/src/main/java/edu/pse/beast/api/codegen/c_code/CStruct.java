package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CStruct {
	private String name;
	private List<CFuncArg> members = new ArrayList<>();
	
	public CStruct(String name, List<CFuncArg> members) {
		this.name = name;
		this.members = members;
	}
		
}
