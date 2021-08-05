package edu.pse.beast.api.codegen.c_code;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CTypeNameBrackets {
    private static final String BLANK = " ";

    private String type;
    private String name;
    private String brackets;

    public CTypeNameBrackets(final String typeString,
                             final String nameString,
                             final String bracketsString) {
        this.type = typeString;
        this.name = nameString;
        this.brackets = bracketsString;
    }

    public final String generateCode() {
        return type + BLANK + name + brackets;
    }

    public final String getName() {
        return name;
    }

    public final String getType() {
        return type;
    }

    public final String getBrackets() {
        return brackets;
    }
}
