package edu.pse.beast.api.electiondescription;

public enum CElectionSimpleTypes {
    INT, UNSIGNED_INT, FLOAT, DOUBLE, VOID, BOOL;

    @Override
    public String toString() {
        switch (this) {
        case BOOL:
            return "unsigned int";
        case INT:
            return "int";
        case DOUBLE:
            return "double";
        case FLOAT:
            return "float";
        case UNSIGNED_INT:
            return "unsigned int";
        case VOID:
            return "void";
        }
        return "";
    }
}
