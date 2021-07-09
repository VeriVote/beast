package edu.pse.beast.api.codegen.c_code;

public class CDefine {
    private String toReplace;
    private String replaceWith;

    public CDefine(String toReplace, String replaceWith) {
        this.toReplace = toReplace;
        this.replaceWith = replaceWith;
    }

    public String generateCode() {
        return "#define " + toReplace + " " + replaceWith;
    }
}
