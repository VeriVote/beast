package edu.pse.beast.gui.testconfigeditor.testconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class TestConfigurationList implements WorkspaceUpdateListener {
    private Map<CElectionDescription, List<TestConfiguration>> testConfigsByDescr =
            new HashMap<>();
    private Map<PreAndPostConditionsDescription, List<TestConfiguration>>
                testConfigsByPropDescr = new HashMap<>();
    private Map<String, TestConfiguration> testConfigsByName = new HashMap<>();

    public final boolean canAdd(final TestConfiguration testConfig) {
        return !testConfigsByName.containsKey(testConfig.getName());
    }

    public final void add(final TestConfiguration testConfig) {
        if (testConfigsByName.containsKey(testConfig.getName())) {
            throw new NotImplementedException();
        } else {
            testConfigsByName.put(testConfig.getName(), testConfig);
        }
        if (!testConfigsByDescr.containsKey(testConfig.getDescr())) {
            testConfigsByDescr.put(testConfig.getDescr(), new ArrayList<>());
        }
        testConfigsByDescr.get(testConfig.getDescr()).add(testConfig);
        if (!testConfigsByPropDescr.containsKey(testConfig.getPropDescr())) {
            testConfigsByPropDescr.put(testConfig.getPropDescr(),
                                       new ArrayList<>());
        }
        testConfigsByPropDescr.get(testConfig.getPropDescr()).add(testConfig);
    }

    public final Map<CElectionDescription, List<TestConfiguration>> getTestConfigsByDescr() {
        return testConfigsByDescr;
    }

    public final Map<PreAndPostConditionsDescription, List<TestConfiguration>>
                    getTestConfigsByPropDescr() {
        return testConfigsByPropDescr;
    }

    public final List<TestConfiguration>
                getTestConfigsByPropDescr(final PreAndPostConditionsDescription currentPropDescr) {
        if (testConfigsByPropDescr.containsKey(currentPropDescr)) {
            return testConfigsByPropDescr.get(currentPropDescr);
        }
        return List.of();
    }

    public final void handleDescrChange(final CElectionDescription descr) {
        if (testConfigsByDescr.containsKey(descr)) {
            for (final TestConfiguration tc : testConfigsByDescr.get(descr)) {
                tc.handleDescrChange();
            }
        }
    }

    public final List<CBMCTestRunWithSymbolicVars> getCBMCTestRuns() {
        final List<CBMCTestRunWithSymbolicVars> list = new ArrayList<>();
        for (final CElectionDescription descr : testConfigsByDescr.keySet()) {
            final List<TestConfiguration> configs = testConfigsByDescr.get(descr);
            for (final TestConfiguration config : configs) {
                for (final CBMCTestConfiguration cbmcTestConfig
                        : config.getCBMCTestConfigs()) {
                    list.addAll(cbmcTestConfig.getRuns());
                }
            }
        }
        return list;
    }

    @Override
    public final void handleExtractedFunctionLoops(final CElectionDescription descr,
                                                   final CElectionDescriptionFunction func) {
        handleDescrChange(descr);
    }

    @Override
    public final void handleDescrChangeUpdatedFunctionCode(final CElectionDescription descr,
                                                           final CElectionDescriptionFunction
                                                                   function,
                                                           final String code) {
        handleDescrChange(descr);
    }

    @Override
    public final void handleDescrChangeAddedVotingSigFunction(final CElectionDescription descr,
                                                              final VotingSigFunction func) {
        handleDescrChange(descr);
    }

    @Override
    public final void handleDescrChangeRemovedFunction(final CElectionDescription descr,
                                                       final CElectionDescriptionFunction func) {
        handleDescrChange(descr);
    }

    private void handlePropDescrChanged(final PreAndPostConditionsDescription propDescr) {
        if (testConfigsByPropDescr.containsKey(propDescr)) {
            for (final TestConfiguration tc : testConfigsByPropDescr.get(propDescr)) {
                tc.handlePropDescrChanged();
            }
        }

    }

    @Override
    public final void handleWorkspaceUpdateAddedVarToPropDescr(final PreAndPostConditionsDescription
                                                                        currentPropDescr,
                                                               final SymbolicCBMCVar var) {
        handlePropDescrChanged(currentPropDescr);
    }

    @Override
    public final void handlePropDescrChangedCode(final PreAndPostConditionsDescription propDescr) {
        handlePropDescrChanged(propDescr);
    }

    @Override
    public final void handlePropDescrRemovedVar(final PreAndPostConditionsDescription propDescr,
                                                final SymbolicCBMCVar selectedVar) {
        handlePropDescrChanged(propDescr);
    }

    public final void deleteCBMCRun(final CBMCTestRunWithSymbolicVars run) {
        run.getTc().deleteRun(run);
    }

    public final void deleteTestConfiguration(final TestConfiguration tc) {
        testConfigsByName.remove(tc.getName());
        testConfigsByDescr.get(tc.getDescr()).remove(tc);
        testConfigsByPropDescr.get(tc.getPropDescr()).remove(tc);
    }

    public final void removeAll(final CElectionDescription descr) {
        for (final TestConfiguration tc : testConfigsByDescr.get(descr)) {
            deleteTestConfiguration(tc);
        }
        testConfigsByDescr.remove(descr);
    }

    public final void removeAll(final PreAndPostConditionsDescription propDescr) {
        for (final TestConfiguration tc : testConfigsByPropDescr.get(propDescr)) {
            deleteTestConfiguration(tc);
        }
        testConfigsByPropDescr.remove(propDescr);
    }
}
