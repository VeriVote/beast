package edu.pse.beast.api.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONException;
import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.VotingInputTypes;
import edu.pse.beast.api.method.VotingOutputTypes;
import edu.pse.beast.api.property.PreAndPostConditions;
import edu.pse.beast.gui.configurationeditor.configuration.ConfigurationBatch;
import edu.pse.beast.gui.configurationeditor.configuration.cbmc.Configuration;
import edu.pse.beast.gui.processhandler.CBMCProcessHandlerCreator;
import edu.pse.beast.gui.workspace.BeastWorkspace;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class WorkspaceInputOutputCreation {
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

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, NONE, this.getClass());
    }

    private static ConfigurationBatch createConfig(final CElectionDescription descr,
                                                   final PreAndPostConditions prop) {
        final Configuration cc = new Configuration();
        cc.setMinVoters(TEST_AMOUNT);
        cc.setMinCands(TEST_AMOUNT);
        cc.setMinSeats(TEST_AMOUNT);
        cc.setMaxCands(TEST_AMOUNT);
        cc.setMaxVoters(TEST_AMOUNT);
        cc.setMaxSeats(TEST_AMOUNT);
        cc.setDescr(descr);
        cc.setPropDescr(prop);
        cc.setName(TEST + BLANK + TEST_STRING);

        final ConfigurationBatch config = new ConfigurationBatch(descr, prop, TEST);
        config.addConfiguration(cc);
        cc.setStartRunsOnCreation(false);
        return config;
    }

    @Test
    public void testInputOutput() throws IOException {
        new BeastWorkspace();

        CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         TEST);
        final String code = getTemplate(CODE);
        descr.getVotingFunction().setCode(code);
        descr.createNewVotingSigFunctionAndAdd(HELPER_FUNCTION);

        final File descrFile =
                new File(this.getClass()
                        .getResource(TESTFILES + File.separator + DESCR)
                        .getFile());
        try {
            descr = InputOutputInterface.loadCElection(descrFile);
        } catch (NotImplementedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final File propDescrFile =
                new File(this.getClass()
                        .getResource(TESTFILES + File.separator + PROP)
                        .getFile());
        PreAndPostConditions propDescr = null;
        try {
            propDescr = InputOutputInterface.loadPreAndPostConditionDescription(propDescrFile);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final ConfigurationBatch config = createConfig(descr, propDescr);
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
        beastWorkspace.addConfiguration(config);

        File f = new File(this.getClass().getResource(TESTFILES).getFile());
        f.mkdirs();
        f = new File(this.getClass().getResource(TESTFILES + File.separator + WS).getFile());

        InputOutputInterface.storeBeastWorkspace(beastWorkspace, f,
                                                   beastWorkspace.getPathHandler());
        InputOutputInterface.loadBeastWorkspace(f, beastWorkspace.getPathHandler());
    }
}
