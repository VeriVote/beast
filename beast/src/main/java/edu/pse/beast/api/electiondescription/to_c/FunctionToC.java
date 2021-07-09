package edu.pse.beast.api.electiondescription.to_c;

import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.cbmc.TypeManager;
import edu.pse.beast.api.electiondescription.CElectionVotingType;

public class FunctionToC {

    public static CTypeNameBrackets votingTypeToC(CElectionVotingType type,
            String name, String V, String C, String S) {
        String arrayBracks = "";
        for (int i = 0; i < type.getListDimensions(); ++i) {
            String arraySize = "";
            switch (type.getListSizes().get(i)) {
            case AMT_VOTERS:
                arraySize = V;
                break;
            case AMT_CANDIDATES:
                arraySize = C;
                break;
            case AMT_SEATS:
                arraySize = S;
                break;
            }
            arrayBracks += "[" + arraySize + "]";
        }
        return new CTypeNameBrackets(
                TypeManager.SimpleTypeToCType(type.getSimpleType()), name,
                arrayBracks);
    }
}
