package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CStruct {
	private String name;
	private List<CTypeAndName> members = new ArrayList<>();

	private final String template = "typedef struct STRUCT_NAME { MEMBER_LIST } STRUCT_NAME;";

	public CStruct(String name, List<CTypeAndName> members) {
		this.name = name;
		this.members = members;
	}

	public String generateDefCode() {
		List<String> memberList = new ArrayList<>();
		for (CTypeAndName member : members) {
			memberList.add(member.generateCode() + ";");
		}
		return template.replaceAll("STRUCT_NAME", name).replace("MEMBER_LIST", String.join("\n", memberList));
	}

	public String getName() {
		return name;
	}

	public List<CTypeAndName> getMembers() {
		return members;
	}
	
}
