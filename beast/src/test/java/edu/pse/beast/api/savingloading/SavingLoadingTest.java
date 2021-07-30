package edu.pse.beast.api.savingloading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
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
    private static final String RESOURCES = "/codegen/";
    private static final String FILE_ENDING = ".template";

    private static final String TESTFILES = "testfiles";
    private static final String REINFORCE = "reinforce";
    private static final String PRE = "_pre";
    private static final String POST = "_post";
    private static final String BORDA = "borda";
    private static final String DESCR = "borda.belec";
    private static final String PROP = "reinforcement.bprp";

    private static final String C = "C";
    private static final String V = "V";
    private static final String VOTING_FUNC = "voting";

    private static final String FUNC_NAME = "asd";
    private static final String I_VAR = "i";
    private static final String J_VAR = "j";
    private static final String V1_VAR = "v1";
    private static final String V2_VAR = "v2";
    private static final String C_VAR = "c";

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

    @Test
    public void testSavingLoadingOfElectionDescription() throws IOException {
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        final String bordaCode = getTemplate(BORDA, this.getClass());
        descr.getVotingFunction().setCode(bordaCode);

        final SimpleTypeFunction simpleFunc =
                new SimpleTypeFunction(FUNC_NAME,
                                       List.of(CElectionSimpleTypes.BOOL,
                                               CElectionSimpleTypes.DOUBLE),
                                       List.of(I_VAR, J_VAR), CElectionSimpleTypes.FLOAT);
        descr.addSimpleFunction(simpleFunc);

        final CodeGenOptions options = new CodeGenOptions();
        options.setCurrentAmountCandsVarName(C);
        options.setCurrentAmountVotersVarName(V);
        final List<ExtractedCLoop> extractedCLoops =
                AntlrCLoopParser.findLoops(VOTING_FUNC, bordaCode, options);

        descr.getVotingFunction().setExtractedLoops(extractedCLoops);

        File f = new File(TESTFILES);
        f.mkdirs();
        f = new File(TESTFILES + File.separator + DESCR);
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
        final Class<?> clazz = this.getClass();
        final String pre = getTemplate(REINFORCE + PRE, clazz);
        final String post = getTemplate(REINFORCE + POST, clazz);

        final List<PreAndPostConditionsDescription> propDecsr =
                CreationHelper.createSimpleCondList(REINFORCE, pre, post);

        propDecsr.get(0).getCbmcVariables()
                .add(new SymbolicCBMCVar(V1_VAR, CBMCVarType.VOTER));
        propDecsr.get(0).getCbmcVariables()
                .add(new SymbolicCBMCVar(V2_VAR, CBMCVarType.VOTER));
        propDecsr.get(0).getCbmcVariables()
                .add(new SymbolicCBMCVar(C_VAR, CBMCVarType.CANDIDATE));

        File f = new File(TESTFILES);
        f.mkdirs();
        f = new File(TESTFILES + File.separator + PROP);
        SavingLoadingInterface.storePreAndPostConditionDescription(propDecsr.get(0), f);
        final PreAndPostConditionsDescription loadedPropDescr =
                SavingLoadingInterface.loadPreAndPostConditionDescription(f);
        assertEquals(propDecsr.get(0), loadedPropDescr);
    }
}
