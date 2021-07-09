package edu.pse.beast.api.electiondescription.to_c;

import edu.pse.beast.api.electiondescription.CBMCVars;

public class CBMCVarNames {
    public static String name(CBMCVars var) {
        switch (var) {
        case AMT_CANDIDATES:
            return "C";
        case AMT_SEATS:
            return "S";
        case AMT_VOTERS:
            return "V";
        default:
            return null;
        }
    }
}
