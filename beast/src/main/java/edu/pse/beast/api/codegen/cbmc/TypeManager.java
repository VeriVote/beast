package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.descr.c_electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class TypeManager {
    private static final String BLANK = " ";
    private static final String UNDERSCORE = "_";

    private List<ElectionTypeCStruct> elecTypeCStructs = new ArrayList<ElectionTypeCStruct>();

    public final CStruct getCStructForVotingType(final CElectionVotingType votingType) {
        return null;
    }

    public final void add(final ElectionTypeCStruct electionTypeCStruct) {
        this.elecTypeCStructs.add(electionTypeCStruct);
    }

    public static final String simpleTypeToCType(final CElectionSimpleTypes simpleType) {
        return simpleType != null
                ? simpleType.name().replaceAll(UNDERSCORE, BLANK).toLowerCase()
                        : null;
    }
}
