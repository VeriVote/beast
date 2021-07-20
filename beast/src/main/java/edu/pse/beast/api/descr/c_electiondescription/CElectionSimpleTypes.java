package edu.pse.beast.api.descr.c_electiondescription;

public enum CElectionSimpleTypes {
    INT, UNSIGNED_INT, FLOAT, DOUBLE, VOID, BOOL;

    private static final String BLANK = " ";
    private static final String USGND = "unsigned";
    private static final String INT_TYPE = "int";
    private static final String DOUBLE_TYPE = "double";
    private static final String FLOAT_TYPE = "floatt";
    private static final String VOID_TYPE = "void";
    private static final String UINT = USGND + BLANK + INT_TYPE;

    @Override
    public String toString() {
        final String type;
        switch (this) {
        case BOOL:
            type = UINT;
            break;
        case INT:
            type = INT_TYPE;
            break;
        case DOUBLE:
            type = DOUBLE_TYPE;
            break;
        case FLOAT:
            type = FLOAT_TYPE;
            break;
        case UNSIGNED_INT:
            type = UINT;
            break;
        case VOID:
            type = VOID_TYPE;
            break;
        default:
            type = "";
        }
        return type;
    }
}
