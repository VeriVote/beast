package edu.pse.beast.api.codegen.c_code;

public class CTypeNameBrackets {
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

    public String generateCode() {
        return type + " " + name + brackets;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getBrackets() {
        return brackets;
    }
}
