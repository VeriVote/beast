package edu.pse.beast.api.descr.c_electiondescription.to_c;

import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.cbmc.TypeManager;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;

public class FunctionToC {
    public static CTypeNameBrackets votingTypeToC(final CElectionVotingType type,
                                                  final String name, final String v,
                                                  final String c, final String s) {
        String arrayBracks = "";
        for (int i = 0; i < type.getListDimensions(); ++i) {
            String arraySize = "";
            switch (type.getListSizes().get(i)) {
            case AMOUNT_VOTERS:
                arraySize = v;
                break;
            case AMOUNT_CANDIDATES:
                arraySize = c;
                break;
            case AMOUNT_SEATS:
                arraySize = s;
                break;
            default:
            }
            arrayBracks += "[" + arraySize + "]";
        }
        return new CTypeNameBrackets(TypeManager.simpleTypeToCType(type.getSimpleType()),
                                     name, arrayBracks);
    }
}
