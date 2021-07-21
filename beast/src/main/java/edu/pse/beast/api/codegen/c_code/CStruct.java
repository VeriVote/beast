package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CStruct {
    private String name;
    private List<CTypeNameBrackets> members = new ArrayList<>();
    private final String template =
            "typedef struct STRUCT_NAME {\nMEMBER_LIST\n} STRUCT_NAME;\n";

    public CStruct(final String nameString, final List<CTypeNameBrackets> memberList) {
        this.name = nameString;
        this.members = memberList;
    }

    public final String generateDefCode() {
        final List<String> memberList = new ArrayList<>();
        for (final CTypeNameBrackets member : members) {
            memberList.add(member.generateCode() + ";");
        }
        return template
                .replaceAll("STRUCT_NAME", name)
                .replace("MEMBER_LIST", String.join("\n", memberList));
    }

    public final String getName() {
        return name;
    }

    public final List<CTypeNameBrackets> getMembers() {
        return members;
    }
}
