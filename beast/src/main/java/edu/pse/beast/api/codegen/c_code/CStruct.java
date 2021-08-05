package edu.pse.beast.api.codegen.c_code;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CStruct {
    private static final String RESOURCES = "/edu/pse/beast/api/codegen/c_code/";
    private static final String FILE_ENDING = ".template";
    private static final String FILE_KEY = "TYPEDEF";

    private static final String LINE_BREAK = "\n";
    private static final String INDENT = "    ";
    private static final String SEMI = ";";

    private static final String STRUCT_NAME = "STRUCT_NAME";
    private static final String MEMBER_LIST = "MEMBER_LIST";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private String name;
    private List<CTypeNameBrackets> members = new ArrayList<>();

    public CStruct(final String nameString, final List<CTypeNameBrackets> memberList) {
        this.name = nameString;
        this.members = memberList;
    }

    public static final String getTemplate(final Class<?> c) {
        final String key = FILE_KEY;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }

    public final String generateDefCode() {
        final List<String> memberList = new ArrayList<>();
        for (final CTypeNameBrackets member : members) {
            memberList.add(member.generateCode() + SEMI);
        }
        final Class<?> c = this.getClass();
        return getTemplate(c)
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
