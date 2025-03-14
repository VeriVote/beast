package edu.kit.kastel.formal.beast.api.method.function;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.TypeManager;
import edu.kit.kastel.formal.beast.api.codegen.ccode.CTypeNameBrackets;
import edu.kit.kastel.formal.beast.api.method.CElectionVotingType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class FunctionToC {
    private static final String NONE = "";
    private static final String BRACKET_OPEN = "[";
    private static final String BRACKET_CLOSE = "]";

    public static CTypeNameBrackets votingTypeToC(final CElectionVotingType type,
                                                  final String name, final String v,
                                                  final String c, final String s) {
        final StringBuilder arrayBracks = new StringBuilder();
        for (int i = 0; i < type.getListDimensions(); ++i) {
            String arraySize = NONE;
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
            arrayBracks.append(BRACKET_OPEN + arraySize + BRACKET_CLOSE);
        }
        return new CTypeNameBrackets(TypeManager.simpleTypeToCType(type.getSimpleType()),
                                     name, arrayBracks.toString());
    }
}
