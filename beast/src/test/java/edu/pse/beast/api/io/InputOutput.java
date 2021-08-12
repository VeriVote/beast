package edu.pse.beast.api.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicVariable;
import edu.pse.beast.api.codegen.cbmc.SymbolicVariable.VariableType;
import edu.pse.beast.api.cparser.AntlrCLoopParser;
import edu.pse.beast.api.cparser.ExtractedCLoop;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.CElectionSimpleType;
import edu.pse.beast.api.method.VotingInputType;
import edu.pse.beast.api.method.VotingOutputType;
import edu.pse.beast.api.method.function.SimpleTypeFunction;
import edu.pse.beast.api.property.PropertyDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class InputOutput {
    private static final String EMPTY = "";

    private static final String TESTFILES = "testfiles";
    private static final String REINFORCE = "reinforce";
    private static final String PRE = "_pre";
    private static final String POST = "_post";
    private static final String BORDA = "borda";
    private static final String DESCR = "borda.belec";
    private static final String PROP = "reinforcement.bprp";

    private static final String VOTES = "votes";
    private static final String C = "C";
    private static final String V = "V";
    private static final String VOTING_FUNC = "voting";

    private static final String FUNC_NAME = "asd";
    private static final String I_VAR = "i";
    private static final String J_VAR = "j";
    private static final String V1_VAR = "v1";
    private static final String V2_VAR = "v2";
    private static final String C_VAR = "c";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, EMPTY, this.getClass());
    }

    @Test
    public void testInputOutputOfElectionDescription() throws IOException {
        final CElectionDescription descr =
                new CElectionDescription(VotingInputType.PREFERENCE,
                                         VotingOutputType.CANDIDATE_LIST,
                                         BORDA);
        final String bordaCode = getTemplate(BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final SimpleTypeFunction simpleFunc =
                new SimpleTypeFunction(FUNC_NAME,
                                       List.of(CElectionSimpleType.BOOL,
                                               CElectionSimpleType.DOUBLE),
                                       List.of(I_VAR, J_VAR), CElectionSimpleType.FLOAT);
        descr.addSimpleFunction(simpleFunc);

        final CodeGenOptions options = new CodeGenOptions();
        options.setCurrentVotesVarName(VOTES);
        options.setCurrentAmountCandsVarName(C);
        options.setCurrentAmountVotersVarName(V);
        final List<ExtractedCLoop> extractedCLoops =
                AntlrCLoopParser.findLoops(VOTING_FUNC, bordaCode, options);

        descr.getVotingFunction().setExtractedLoops(extractedCLoops);

        File f = new File(this.getClass().getResource(TESTFILES).getFile());
        f.mkdirs();
        f = new File(this.getClass().getResource(TESTFILES + File.separator + DESCR).getFile());
        InputOutputInterface.storeCElection(descr, f);

        final CElectionDescription loadedDescr =
                InputOutputInterface.loadCElection(f);

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
    public void testInputOutputOfPropertyDescription() throws IOException {
        final String pre = getTemplate(REINFORCE + PRE);
        final String post = getTemplate(REINFORCE + POST);

        final List<PropertyDescription> propDecsr =
                CreationHelper.createSimpleCondList(REINFORCE, pre, post);

        propDecsr.get(0).getVariables()
                .add(new SymbolicVariable(V1_VAR, VariableType.VOTER));
        propDecsr.get(0).getVariables()
                .add(new SymbolicVariable(V2_VAR, VariableType.VOTER));
        propDecsr.get(0).getVariables()
                .add(new SymbolicVariable(C_VAR, VariableType.CANDIDATE));

        File f = new File(this.getClass().getResource(TESTFILES).getFile());
        f.mkdirs();
        f = new File(this.getClass().getResource(TESTFILES + File.separator + PROP).getFile());
        InputOutputInterface.storePropertyDescription(propDecsr.get(0), f);
        final PropertyDescription loadedPropDescr =
                InputOutputInterface.loadPropertyDescription(f);
        assertEquals(propDecsr.get(0), loadedPropDescr);
    }
}
