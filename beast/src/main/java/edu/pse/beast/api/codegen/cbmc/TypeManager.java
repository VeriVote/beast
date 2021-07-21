package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.descr.c_electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;

public class TypeManager {

    private List<ElectionTypeCStruct> elecTypeCStructs = new ArrayList<>();

    public final CStruct getCStructForVotingType(final CElectionVotingType votingType) {
        return null;
    }

    public final void add(final ElectionTypeCStruct electionTypeCStruct) {
        this.elecTypeCStructs.add(electionTypeCStruct);
    }

    public static final String simpleTypeToCType(final CElectionSimpleTypes simpleType) {
        final String type;
        switch (simpleType) {
        case INT:
            type = "int";
            break;
        case DOUBLE:
            type = "double";
            break;
        case FLOAT:
            type = "float";
            break;
        case UNSIGNED_INT:
            type = "unsigned int";
            break;
        case VOID:
            type = "void";
            break;
        case BOOL:
            type = "bool";
            break;
        default:
            type = null;
        }
        return type;
    }
}
