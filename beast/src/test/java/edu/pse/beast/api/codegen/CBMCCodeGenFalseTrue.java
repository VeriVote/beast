package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

public class CBMCCodeGenFalseTrue {

    @Test
    public void testTrue() {
        String votingCode = "";

        CElectionDescription descr = new CElectionDescription(
                VotingInputTypes.SINGLE_CHOICE,
                VotingOutputTypes.CANDIDATE_LIST, "borda");
        descr.getVotingFunction().setCode(votingCode);

        CodeGenOptions codeGenOptions = new CodeGenOptions();

        List<ExtractedCLoop> loops = AntlrCLoopParser.findLoops("voting",
                votingCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        String pre = "";
        String post = "FALSE;";

        PreAndPostConditionsDescription propDescr = CreationHelper
                .createSimpleCondList("reinforce", pre, post).get(0);
        propDescr.addCBMCVar(new SymbolicCBMCVar("c1", CBMCVarType.CANDIDATE));
        propDescr.addCBMCVar(new SymbolicCBMCVar("c2", CBMCVarType.CANDIDATE));

        InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();

        String code = CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr,
                propDescr, codeGenOptions, initVoteHelper).getCode();

        System.out.println(code);      
        
    }
}
