package edu.pse.beast.api.savingloading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.c_electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

public class SavingLoadingTest {
    private static final String LINE_BREAK = "\n";

    private static final String BORDA_CODE =
            "    unsigned int i = 0;" + LINE_BREAK
            + "    unsigned int j = 0;" + LINE_BREAK
            + "    for (i = 0; i < C; i++) {" + LINE_BREAK
            + "        result[i] = 0;" + LINE_BREAK
            + "    }" + LINE_BREAK
            + "    for (i = 0; i < V; i++) {" + LINE_BREAK
            + "        for (j = 0; j < C; j++) {" + LINE_BREAK
            + "            result[votes[i][j]] += (C - j) - 1;" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }" + "    unsigned int max = 0;" + LINE_BREAK
            + "    for (i = 0; i < C; i++) {" + LINE_BREAK
            + "        if (max < res[i]) {" + LINE_BREAK
            + "            max = res[i];" + LINE_BREAK
            + "            for (j = 0; j < C; j++) {" + LINE_BREAK
            + "                r.arr[j] = 0;" + LINE_BREAK
            + "            }" + LINE_BREAK
            + "            r.arr[i] = 1;" + LINE_BREAK
            + "        } else if (max == res[i]) {" + LINE_BREAK
            + "            r.arr[i] = 1;" + LINE_BREAK
            + "        }" + LINE_BREAK
            + "    }";

    @Test
    public void testSavingLoadingOfElectionDescription() throws IOException {
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         "borda");
        descr.getVotingFunction().setCode(BORDA_CODE);

        final SimpleTypeFunction simpleFunc =
                new SimpleTypeFunction("asd",
                                       List.of(CElectionSimpleTypes.BOOL,
                                               CElectionSimpleTypes.DOUBLE),
                                       List.of("i", "j"), CElectionSimpleTypes.FLOAT);
        descr.addSimpleFunction(simpleFunc);

        final CodeGenOptions options = new CodeGenOptions();
        options.setCurrentAmountCandsVarName("C");
        options.setCurrentAmountVotersVarName("V");
        final List<ExtractedCLoop> extractedCLoops =
                AntlrCLoopParser.findLoops("voting", BORDA_CODE, options);

        descr.getVotingFunction().setExtractedLoops(extractedCLoops);

        File f = new File("testfiles");
        f.mkdirs();
        f = new File("testfiles/borda.belec");
        SavingLoadingInterface.storeCElection(descr, f);

        final CElectionDescription loadedDescr =
                SavingLoadingInterface.loadCElection(f);

        assertEquals(descr.getInputType(), loadedDescr.getInputType());
        assertEquals(descr.getOutputType(), loadedDescr.getOutputType());
        assertEquals(descr.getVotingFunction().getName(),
                     loadedDescr.getVotingFunction().getName());
        assertEquals(descr.getVotingFunction().getCode(),
                     loadedDescr.getVotingFunction().getCode());
        assertEquals(descr.getVotingFunction().getExtractedLoops().size(),
                     loadedDescr.getVotingFunction().getExtractedLoops().size());

        for (int i = 0; i < descr.getVotingFunction().getExtractedLoops().size(); ++i) {
            final ExtractedCLoop loop =
                    descr.getVotingFunction().getExtractedLoops().get(i);
            final ExtractedCLoop loadedLoop =
                    loadedDescr.getVotingFunction().getExtractedLoops().get(i);
            assertEquals(loop.getChildrenLoops().size(),
                         loadedLoop.getChildrenLoops().size());
            if (loop.getParentLoop() == null) {
                assertNull(loadedLoop.getParentLoop());
            } else {
                assertEquals(loop.getParentLoop().getUuid(),
                             loadedLoop.getParentLoop().getUuid());
            }
        }
    }

    @Test
    public void testSavingLoadingOfPreAndPostConditionDescription() throws IOException {
        final String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
        final String post = "(!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";

        final List<PreAndPostConditionsDescription> propDecsr =
                CreationHelper.createSimpleCondList("reinforce", pre, post);

        propDecsr.get(0).getCbmcVariables()
                .add(new SymbolicCBMCVar("v1", CBMCVarType.VOTER));
        propDecsr.get(0).getCbmcVariables()
                .add(new SymbolicCBMCVar("v2", CBMCVarType.VOTER));
        propDecsr.get(0).getCbmcVariables()
                .add(new SymbolicCBMCVar("c", CBMCVarType.CANDIDATE));

        File f = new File("testfiles");
        f.mkdirs();
        f = new File("testfiles/reinforcement.bprp");
        SavingLoadingInterface.storePreAndPostConditionDescription(propDecsr.get(0), f);
        final PreAndPostConditionsDescription loadedPropDescr =
                SavingLoadingInterface.loadPreAndPostConditionDescription(f);
        assertEquals(propDecsr.get(0), loadedPropDescr);
    }
}
