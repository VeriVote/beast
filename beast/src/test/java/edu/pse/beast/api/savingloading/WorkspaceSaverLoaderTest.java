package edu.pse.beast.api.savingloading;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONException;
import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.gui.processHandler.CBMCProcessHandlerCreator;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.workspace.BeastWorkspace;

public class WorkspaceSaverLoaderTest {

    private static TestConfiguration createTestConfig(final CElectionDescription descr,
                                                      final PreAndPostConditionsDescription prop) {
        final CBMCTestConfiguration cc = new CBMCTestConfiguration();
        cc.setMinVoters(5);
        cc.setMinCands(5);
        cc.setMinSeats(5);
        cc.setMaxCands(5);
        cc.setMaxVoters(5);
        cc.setMaxSeats(5);
        cc.setDescr(descr);
        cc.setPropDescr(prop);
        cc.setName("test five");

        final TestConfiguration testConfig = new TestConfiguration(descr, prop, "test");
        testConfig.addCBMCTestConfiguration(cc);
        cc.setStartRunsOnCreation(false);
        return testConfig;
    }

    @Test
    public void testSavingLoading() throws IOException {
        new BeastWorkspace();

        CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         "test");
        descr.getVotingFunction().setCode("for(int i = 0; i < V; ++i) {}\n" + "return 0;\n");
        descr.createNewVotingSigFunctionAndAdd("votehelper");

        final File descrFile = new File("testfiles/borda.belec");
        try {
            descr = SavingLoadingInterface.loadCElection(descrFile);
        } catch (NotImplementedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final File propDescrFile = new File("testfiles/reinforcement.bprp");
        PreAndPostConditionsDescription propDescr = null;
        try {
            propDescr = SavingLoadingInterface.loadPreAndPostConditionDescription(propDescrFile);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final TestConfiguration testConfig = createTestConfig(descr, propDescr);
        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        codeGenOptions.setCbmcAmountMaxCandidatesVarName("C");
        codeGenOptions.setCbmcAmountMaxVotersVarName("V");
        codeGenOptions.setCbmcAmountMaxSeatsVarName("S");

        final CBMCProcessHandlerCreator cbmcProcessHandlerCreator = new CBMCProcessHandlerCreator();
        final BeastWorkspace beastWorkspace =
                BeastWorkspace.getStandardWorkspace(cbmcProcessHandlerCreator);
        beastWorkspace.setCodeGenOptions(codeGenOptions);

        final Path currentRelativePath = Paths.get("");
        final String s = currentRelativePath.toAbsolutePath().toString();
        new File(s);

        beastWorkspace.addElectionDescription(descr);
        beastWorkspace.addFileForDescr(descr, descrFile);
        beastWorkspace.addPropertyDescription(propDescr);
        beastWorkspace.addFileForPropDescr(propDescr, propDescrFile);
        beastWorkspace.addTestConfiguration(testConfig);

        File f = new File("testfiles");
        f.mkdirs();
        f = new File("testfiles/test.beastws");

        SavingLoadingInterface.storeBeastWorkspace(beastWorkspace, f,
                                                   beastWorkspace.getPathHandler());
        SavingLoadingInterface.loadBeastWorkspace(f, beastWorkspace.getPathHandler());
    }
}
