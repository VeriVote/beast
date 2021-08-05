package edu.pse.beast.api.codegen.cbmc;

import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ElectionTypeCStruct {
    private CElectionVotingType votingType;
    private CStruct struct;
    private String listName;
    private String amtName;

    public ElectionTypeCStruct(final CElectionVotingType votType,
                               final CStruct voteStruct,
                               final String listNameString,
                               final String amountNameString) {
        super();
        this.votingType = votType;
        this.struct = voteStruct;
        this.listName = listNameString;
        this.amtName = amountNameString;
    }

    public final CStruct getStruct() {
        return struct;
    }

    public final CElectionVotingType getVotingType() {
        return votingType;
    }

    public final String getListName() {
        return listName;
    }

    public final String getAmountName() {
        return amtName;
    }
}
