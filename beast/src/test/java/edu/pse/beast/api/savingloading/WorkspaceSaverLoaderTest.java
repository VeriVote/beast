package edu.pse.beast.api.savingloading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
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

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class WorkspaceSaverLoaderTest {
    private static final String NONE = "";
    private static final String BLANK = " ";

    private static final String C = "C";
    private static final String V = "V";
    private static final String S = "S";

    private static final String TEST = "test";
    private static final String TESTFILES = "testfiles";
    private static final int TEST_AMOUNT = 5;
    private static final String TEST_STRING = "five";

    private static final String HELPER_FUNCTION = "votehelper";
    private static final String CODE = "code";
    private static final String DESCR = "borda.belec";
    private static final String PROP = "reinforcement.bprp";
    private static final String WS = "test.beastws";
    private static final String RESOURCES = "/savingloading/";
    private static final String FILE_ENDING = ".template";

    private static String getTemplate(final String key, final Class<?> c) {
        final InputStream stream =
                c.getResourceAsStream(RESOURCES + key.toLowerCase() + FILE_ENDING);
        final StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    private static TestConfiguration createTestConfig(final CElectionDescription descr,
                                                      final PreAndPostConditionsDescription prop) {
        final CBMCTestConfiguration cc = new CBMCTestConfiguration();
        cc.setMinVoters(TEST_AMOUNT);
        cc.setMinCands(TEST_AMOUNT);
        cc.setMinSeats(TEST_AMOUNT);
        cc.setMaxCands(TEST_AMOUNT);
        cc.setMaxVoters(TEST_AMOUNT);
        cc.setMaxSeats(TEST_AMOUNT);
        cc.setDescr(descr);
        cc.setPropDescr(prop);
        cc.setName(TEST + BLANK + TEST_STRING);

        final TestConfiguration testConfig = new TestConfiguration(descr, prop, TEST);
        testConfig.addCBMCTestConfiguration(cc);
        cc.setStartRunsOnCreation(false);
        return testConfig;
    }

    @Test
    public void testSavingLoading() throws IOException {
        final Class<?> c = this.getClass();
        new BeastWorkspace();

        CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         TEST);
        final String code = getTemplate(CODE, c);
        descr.getVotingFunction().setCode(code);
        descr.createNewVotingSigFunctionAndAdd(HELPER_FUNCTION);

        final File descrFile = new File(TESTFILES + File.separator + DESCR);
        try {
            descr = SavingLoadingInterface.loadCElection(descrFile);
        } catch (NotImplementedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final File propDescrFile = new File(TESTFILES + File.separator + PROP);
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
        codeGenOptions.setCbmcAmountMaxCandidatesVarName(C);
        codeGenOptions.setCbmcAmountMaxVotersVarName(V);
        codeGenOptions.setCbmcAmountMaxSeatsVarName(S);

        final CBMCProcessHandlerCreator cbmcProcessHandlerCreator = new CBMCProcessHandlerCreator();
        final BeastWorkspace beastWorkspace =
                BeastWorkspace.getStandardWorkspace(cbmcProcessHandlerCreator);
        beastWorkspace.setCodeGenOptions(codeGenOptions);

        final Path currentRelativePath = Paths.get(NONE);
        final String s = currentRelativePath.toAbsolutePath().toString();
        new File(s);

        beastWorkspace.addElectionDescription(descr);
        beastWorkspace.addFileForDescr(descr, descrFile);
        beastWorkspace.addPropertyDescription(propDescr);
        beastWorkspace.addFileForPropDescr(propDescr, propDescrFile);
        beastWorkspace.addTestConfiguration(testConfig);

        File f = new File(TESTFILES);
        f.mkdirs();
        f = new File(TESTFILES + File.separator + WS);

        SavingLoadingInterface.storeBeastWorkspace(beastWorkspace, f,
                                                   beastWorkspace.getPathHandler());
        SavingLoadingInterface.loadBeastWorkspace(f, beastWorkspace.getPathHandler());
    }
}
