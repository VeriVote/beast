package edu.pse.beast.api.descr.c_electiondescription.to_c;

import edu.pse.beast.api.descr.c_electiondescription.CBMCVars;

public class CBMCVarNames {
    public static String name(final CBMCVars var) {
        final String name;
        switch (var) {
        case AMOUNT_CANDIDATES:
            name = "C";
            break;
        case AMOUNT_SEATS:
            name = "S";
            break;
        case AMOUNT_VOTERS:
            name = "V";
            break;
        default:
            name = null;
        }
        return name;
    }
}
