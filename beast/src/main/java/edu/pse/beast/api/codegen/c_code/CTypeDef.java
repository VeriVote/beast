package edu.pse.beast.api.codegen.c_code;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CTypeDef {
    private static final String BLANK = " ";
    private static final String SEMICOLON = ";";
    private static final String TYPEDEF = "typedef";

    private String type;
    private String newTypeName;

    public CTypeDef(final String typeString, final String newTypeNameString) {
        this.type = typeString;
        this.newTypeName = newTypeNameString;
    }

    public final String generateCode() {
        return TYPEDEF + BLANK + type + BLANK + newTypeName + SEMICOLON;
    }
}
