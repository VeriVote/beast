package edu.pse.beast.api.codegen.c_code;

public class CDefine {
    private String toReplace;
    private String replaceWith;

    public CDefine(final String toReplaceString,
                   final String replaceWithString) {
        this.toReplace = toReplaceString;
        this.replaceWith = replaceWithString;
    }

    public String generateCode() {
        return "#define " + toReplace + " " + replaceWith;
    }
}
