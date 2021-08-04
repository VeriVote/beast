package edu.pse.beast.api.codegen.cbmc;

import edu.pse.beast.api.codegen.c_code.CForLoop;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;

public class ElectionTypeCStruct {
    private static final String NONE = "";
    private static final String AMOUNT = "AMT";
    private static final String COUNTER = "COUNTER";
    private static final String COND_STRING = COUNTER + " < " + AMOUNT;

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

    public final CForLoop loopOverOuterList(final CTypeNameBrackets counterVar,
                                            final String tempInnerVarName) {
        COND_STRING.replaceAll(COUNTER, counterVar.getName()).replaceAll(AMOUNT, NONE);
        return new CForLoop(counterVar, NONE, NONE);
    }

    public final String getListName() {
        return listName;
    }

    public final String getAmtName() {
        return amtName;
    }
}
