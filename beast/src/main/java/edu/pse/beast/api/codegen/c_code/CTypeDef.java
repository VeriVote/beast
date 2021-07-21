package edu.pse.beast.api.codegen.c_code;

public class CTypeDef {
    private String type;
    private String newTypeName;

    public CTypeDef(final String typeString, final String newTypeNameString) {
        this.type = typeString;
        this.newTypeName = newTypeNameString;
    }

    public final String generateCode() {
        return "typedef " + type + " " + newTypeName + ";";
    }
}
