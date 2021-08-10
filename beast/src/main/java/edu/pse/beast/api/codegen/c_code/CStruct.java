package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.paths.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CStruct {
    private static final String FILE_KEY = "TYPEDEF";

    private static final String EMPTY = "";
    private static final String LINE_BREAK = "\n";
    private static final String INDENT = "    ";
    private static final String SEMI = ";";

    private static final String STRUCT_NAME = "STRUCT_NAME";
    private static final String MEMBER_LIST = "MEMBER_LIST";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private String name;
    private List<CTypeNameBrackets> members = new ArrayList<CTypeNameBrackets>();

    public CStruct(final String nameString, final List<CTypeNameBrackets> memberList) {
        this.name = nameString;
        this.members = memberList;
    }

    public final String getTemplate() {
        return PathHandler.getTemplate(FILE_KEY, TEMPLATES, EMPTY, this.getClass());
    }

    public final String generateDefCode() {
        final List<String> memberList = new ArrayList<String>();
        for (final CTypeNameBrackets member : members) {
            memberList.add(member.generateCode() + SEMI);
        }
        return getTemplate()
                .replaceAll(STRUCT_NAME, name)
                .replace(MEMBER_LIST, String.join(LINE_BREAK + INDENT, memberList));
    }

    public final String getName() {
        return name;
    }

    public final List<CTypeNameBrackets> getMembers() {
        return members;
    }
}
