package edu.kit.kastel.formal.beast.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.init.InitVoteHelper;
import edu.kit.kastel.formal.beast.api.codegen.init.SymbVarInitVoteHelper;
import edu.kit.kastel.formal.beast.api.codegen.init.TestInitVoteHelper;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.io.PathHandler;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.api.runner.codefile.CodeFileData;
import edu.kit.kastel.formal.beast.api.runner.codefile.CodeFileGenerator;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.PropertyCheckWorkUnit;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.kit.kastel.formal.beast.api.test.VotingParameters;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.cbmc.Configuration;

/**
 * This class can run work units and create cbmc code files or a list of
 * CBMC runs given a range of voter, seat and candidate amounts.
 *
 * @author Holger Klein
 *
 */
public class BEAST {
    private List<Thread> createdThreads = new ArrayList<Thread>();
    private List<PropertyCheckWorkUnit> wus = new ArrayList<PropertyCheckWorkUnit>();

    public final void runWorkUnit(final PropertyCheckWorkUnit wu) {
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

    public final void stopRun(final PropertyCheckRun run) {
        run.getWorkUnit().interrupt();
    }

    public final void shutdown() {
        for (final PropertyCheckWorkUnit wu : wus) {
            wu.shutdown();
        }
    }

    public final CodeFileData generateCodeFilePropertyCheck(final CElectionDescription descr,
                                                            final PropertyDescription propDescr,
                                                            final CodeGenOptions codeGenOpts,
                                                            final PathHandler handler)
                                                                    throws IOException {
        final InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();
        return CodeFileGenerator.createCodeFileCheck(descr, propDescr, codeGenOpts,
                                                     handler, initVoteHelper);
    }

    public final CodeFileData generateCodeFileCheck(final CElectionDescription descr,
                                                    final PropertyDescription propDescr,
                                                    final CodeGenOptions codeGenOptions,
                                                    final PathHandler pathHandler,
                                                    final VotingParameters args)
                                                            throws IOException {
        final InitVoteHelper initVoteHelper = new TestInitVoteHelper(args);
        return CodeFileGenerator.createCodeFileCheck(descr, propDescr, codeGenOptions,
                                                     pathHandler, initVoteHelper);
    }

    public final List<PropertyCheckRun> generateCheckRuns(final CodeFileData codeFile,
                                                          final Configuration config,
                                                          final CodeGenOptions codeGenOpts) {
        final List<PropertyCheckRun> runs = new ArrayList<PropertyCheckRun>();
        for (int v = config.getMinVoters(); v <= config.getMaxVoters(); ++v) {
            for (int c = config.getMinCands(); c <= config.getMaxCands(); ++c) {
                for (int s = config.getMinSeats(); s <= config.getMaxSeats(); ++s) {
                    final CodeGenLoopBoundHandler loopbounds =
                            codeFile.getCodeInfo().getLoopBoundHandler();
                    final PropertyCheckRun.BoundValues bounds =
                            new PropertyCheckRun.BoundValues(c, s, v);
                    runs.add(new PropertyCheckRun(bounds, codeGenOpts, loopbounds,
                                                  codeFile, config.getDescr(),
                                                  config.getPropDescr(), config));
                }
            }
        }
        return runs;
    }
}
