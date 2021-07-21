package edu.pse.beast.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.cbmc_run_with_specific_values.VotingParameters;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SpecificValueInitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileGenerator;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

/**
 * This class can run work units and create
 * cbmc code files or a list of
 * CBMC Test Runs given a range of
 * voter, seat and candidate amounts
 * @author Holger Klein
 *
 */
public class BEAST {
    private List<Thread> createdThreads = new ArrayList<>();
    private List<CBMCPropertyCheckWorkUnit> wus = new ArrayList<>();

    public final void runWorkUnit(final CBMCPropertyCheckWorkUnit wu) {
        wus.add(wu);
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                wu.doWork();
            }
        });
        t.start();
        createdThreads.add(t);
    }

    public final void stopRun(final CBMCTestRunWithSymbolicVars run) {
        run.getWorkUnit().interrupt();
    }

    public final void shutdown() {
        for (final CBMCPropertyCheckWorkUnit wu : wus) {
            wu.shutdown();
        }
    }

    public final CBMCCodeFileData
                    generateCodeFileCBMCPropertyTest(final CElectionDescription descr,
                                                     final PreAndPostConditionsDescription
                                                             propDescr,
                                                     final CodeGenOptions codeGenOptions,
                                                     final PathHandler pathHandler)
                                                             throws IOException {
        final InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();
        return CBMCCodeFileGenerator.createCodeFileTest(descr, propDescr,
                                                        codeGenOptions,
                                                        pathHandler,
                                                        initVoteHelper);
    }

    public final CBMCCodeFileData
                    generateCodeFileCBMCSpecificInput(final CElectionDescription descr,
                                                      final PreAndPostConditionsDescription
                                                              propDescr,
                                                      final CodeGenOptions codeGenOptions,
                                                      final PathHandler pathHandler,
                                                      final VotingParameters votingParameters)
                                                              throws IOException {
        final InitVoteHelper initVoteHelper =
                new SpecificValueInitVoteHelper(votingParameters);
        return CBMCCodeFileGenerator.createCodeFileTest(descr, propDescr,
                                                        codeGenOptions,
                                                        pathHandler,
                                                        initVoteHelper);
    }

    public final List<CBMCTestRunWithSymbolicVars>
                    generateTestRuns(final CBMCCodeFileData cbmcCodeFile,
                                     final CBMCTestConfiguration testConfig,
                                     final CodeGenOptions codeGenOptions) {
        final List<CBMCTestRunWithSymbolicVars> runs = new ArrayList<>();
        for (int v = testConfig.getMinVoters(); v <= testConfig.getMaxVoters(); ++v) {
            for (int c = testConfig.getMinCands(); c <= testConfig.getMaxCands(); ++c) {
                for (int s = testConfig.getMinSeats(); s <= testConfig.getMaxSeats(); ++s) {
                    final String loopbounds =
                            cbmcCodeFile.getCodeInfo()
                            .getLoopBoundHandler().generateCBMCString(v, c, s);
                    final CBMCTestRunWithSymbolicVars.BoundValues bounds =
                            new CBMCTestRunWithSymbolicVars.BoundValues(c, s, v);
                    runs.add(new CBMCTestRunWithSymbolicVars(bounds,
                                                             codeGenOptions,
                                                             loopbounds,
                                                             cbmcCodeFile,
                                                             testConfig.getDescr(),
                                                             testConfig.getPropDescr(),
                                                             testConfig));
                }
            }
        }
        return runs;
    }
}
