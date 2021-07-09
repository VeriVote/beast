package edu.pse.beast.api.codegen.c_code;

public class CTypeNameBrackets {

    String type;
    String name;
    String brackets;

    public CTypeNameBrackets(String type, String name, String brackets) {
        this.type = type;
        this.name = name;
        this.brackets = brackets;
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
