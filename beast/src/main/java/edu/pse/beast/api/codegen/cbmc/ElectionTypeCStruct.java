package edu.pse.beast.api.codegen.cbmc;

import edu.pse.beast.api.codegen.c_code.CForLoop;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.electiondescription.CElectionVotingType;

public class ElectionTypeCStruct {
    private CElectionVotingType votingType;
    private CStruct struct;
    private String listName;
    private String amtName;

    public ElectionTypeCStruct(CElectionVotingType votingType, CStruct struct,
            String listName, String amtName) {
        super();
        this.votingType = votingType;
        this.struct = struct;
        this.listName = listName;
        this.amtName = amtName;
    }

    public CStruct getStruct() {
        return struct;
    }

    public CElectionVotingType getVotingType() {
        return votingType;
    }

    private final String condString = "COUNTER < AMT";

    public CForLoop loopOverOuterList(CTypeNameBrackets counterVar,
            String tempInnerVarName) {
        String cond = condString.replaceAll("COUNTER", counterVar.getName())
                .replaceAll("AMT", "");
        CForLoop created = new CForLoop(counterVar, "", "");
        return created;
    }

    public String getListName() {
        return listName;
    }

    public String getAmtName() {
        return amtName;
    }
}
