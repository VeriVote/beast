package edu.pse.beast.api.codegen.c_code;

public class CInclude {
    private String filePath;

    public CInclude(final String filePathString) {
        this.filePath = filePathString;
    }

    public final String generateCode() {
        return "#include <" + filePath + ">";
    }
}
