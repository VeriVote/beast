package edu.pse.beast.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.cbmc_run_with_specific_values.VotingParameters;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SpecificValueInitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileGeneratorNEW;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

public class BEAST {

    private List<Thread> createdThreads = new ArrayList<>();
    private List<CBMCPropertyCheckWorkUnit> wus = new ArrayList<>();

    public void runWorkUnit(CBMCPropertyCheckWorkUnit wu) {
        wus.add(wu);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                wu.doWork();
            }
        });
        t.start();
        createdThreads.add(t);
    }

    public void stopRun(CBMCTestRunWithSymbolicVars run) {
        run.getWorkUnit().interrupt();
    }

    public void shutdown() {
        for (CBMCPropertyCheckWorkUnit wu : wus) {
            wu.shutdown();
        }
    }

    public CBMCCodeFileData generateCodeFileCBMCPropertyTest(
            CElectionDescription descr,
            PreAndPostConditionsDescription propDescr,
            CodeGenOptions codeGenOptions, PathHandler pathHandler)
            throws IOException {
        InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();
        return CBMCCodeFileGeneratorNEW.createCodeFileTest(descr, propDescr,
                codeGenOptions, pathHandler, initVoteHelper);
    }

    public CBMCCodeFileData generateCodeFileCBMCSpecificInput(
            CElectionDescription descr,
            PreAndPostConditionsDescription propDescr,
            CodeGenOptions codeGenOptions, PathHandler pathHandler,
            VotingParameters votingParameters) throws IOException {
        InitVoteHelper initVoteHelper = new SpecificValueInitVoteHelper(
                votingParameters);
        return CBMCCodeFileGeneratorNEW.createCodeFileTest(descr, propDescr,
                codeGenOptions, pathHandler, initVoteHelper);
    }

    public List<CBMCTestRunWithSymbolicVars> generateTestRuns(
            CBMCCodeFileData cbmcCodeFile, CBMCTestConfiguration testConfig,
            CodeGenOptions codeGenOptions) {
        List<CBMCTestRunWithSymbolicVars> runs = new ArrayList<>();
        for (int v = testConfig.getMinVoters(); v <= testConfig
                .getMaxVoters(); ++v) {
            for (int c = testConfig.getMinCands(); c <= testConfig
                    .getMaxCands(); ++c) {
                for (int s = testConfig.getMinSeats(); s <= testConfig
                        .getMaxSeats(); ++s) {
                    String loopbounds = cbmcCodeFile.getCodeInfo()
                            .getLoopBoundHandler().generateCBMCString(v, c, s);

                    runs.add(new CBMCTestRunWithSymbolicVars(v, s, c,
                            codeGenOptions, loopbounds, cbmcCodeFile,
                            testConfig.getDescr(), testConfig.getPropDescr(),
                            testConfig));
                }
            }
        }
        return runs;
    }

}
