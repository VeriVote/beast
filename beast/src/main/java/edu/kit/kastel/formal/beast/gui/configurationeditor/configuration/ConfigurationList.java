package edu.kit.kastel.formal.beast.gui.configurationeditor.configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.method.function.CElectionDescriptionFunction;
import edu.kit.kastel.formal.beast.api.method.function.VotingSigFunction;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.cbmc.Configuration;
import edu.kit.kastel.formal.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ConfigurationList implements WorkspaceUpdateListener {
    private Map<CElectionDescription, List<ConfigurationBatch>> configsByDescr =
            new LinkedHashMap<CElectionDescription, List<ConfigurationBatch>>();
    private Map<PropertyDescription, List<ConfigurationBatch>> configsByPropDescr =
                new LinkedHashMap<PropertyDescription, List<ConfigurationBatch>>();
    private Map<String, ConfigurationBatch> configsByName =
            new LinkedHashMap<String, ConfigurationBatch>();

    public final boolean canAdd(final ConfigurationBatch config) {
        return !configsByName.containsKey(config.getName());
    }

    public final void add(final ConfigurationBatch config) {
        if (configsByName.containsKey(config.getName())) {
            throw new NotImplementedException();
        } else {
            configsByName.put(config.getName(), config);
        }
        if (!configsByDescr.containsKey(config.getDescr())) {
            configsByDescr.put(config.getDescr(), new ArrayList<ConfigurationBatch>());
        }
        configsByDescr.get(config.getDescr()).add(config);
        if (!configsByPropDescr.containsKey(config.getPropDescr())) {
            configsByPropDescr.put(config.getPropDescr(),
                                       new ArrayList<ConfigurationBatch>());
        }
        configsByPropDescr.get(config.getPropDescr()).add(config);
    }

    public final Map<CElectionDescription, List<ConfigurationBatch>> getConfigsByDescr() {
        return configsByDescr;
    }

    public final Map<PropertyDescription, List<ConfigurationBatch>> getConfigsByPropDescr() {
        return configsByPropDescr;
    }

    public final List<ConfigurationBatch>
                getConfigsByPropDescr(final PropertyDescription currentPropDescr) {
        if (configsByPropDescr.containsKey(currentPropDescr)) {
            return configsByPropDescr.get(currentPropDescr);
        }
        return List.of();
    }

    public final void handleDescrChange(final CElectionDescription descr) {
        if (configsByDescr.containsKey(descr)) {
            for (final ConfigurationBatch tc : configsByDescr.get(descr)) {
                tc.handleDescrChange();
            }
        }
    }

    public final List<PropertyCheckRun> getRuns() {
        final List<PropertyCheckRun> list = new ArrayList<PropertyCheckRun>();
        for (final CElectionDescription descr : configsByDescr.keySet()) {
            final List<ConfigurationBatch> configBatches = configsByDescr.get(descr);
            for (final ConfigurationBatch configBatch : configBatches) {
                for (final Configuration config : configBatch.getConfigs()) {
                    list.addAll(config.getRuns());
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

    private void handlePropDescrChanged(final PropertyDescription propDescr) {
        if (configsByPropDescr.containsKey(propDescr)) {
            for (final ConfigurationBatch tc : configsByPropDescr.get(propDescr)) {
                tc.handlePropDescrChanged();
            }
        }

    }

    @Override
    public final void handleWorkspaceUpdateAddedVarToPropDescr(final PropertyDescription
                                                                        currentPropDescr,
                                                               final SymbolicVariable var) {
        handlePropDescrChanged(currentPropDescr);
    }

    @Override
    public final void handlePropDescrChangedCode(final PropertyDescription propDescr) {
        handlePropDescrChanged(propDescr);
    }

    @Override
    public final void handlePropDescrRemovedVar(final PropertyDescription propDescr,
                                                final SymbolicVariable selectedVar) {
        handlePropDescrChanged(propDescr);
    }

    public final void deleteRun(final PropertyCheckRun run) {
        run.getTc().deleteRun(run);
    }

    public final void deleteConfiguration(final ConfigurationBatch tc) {
        configsByName.remove(tc.getName());
        configsByDescr.get(tc.getDescr()).remove(tc);
        final List<ConfigurationBatch> emptyList = new LinkedList<ConfigurationBatch>();
        configsByPropDescr.getOrDefault(tc.getPropDescr(), emptyList).remove(tc);
    }

    public final void removeAll(final CElectionDescription descr) {
        for (final ConfigurationBatch tc : configsByDescr.get(descr)) {
            deleteConfiguration(tc);
        }
        configsByDescr.remove(descr);
    }

    public final void removeAll(final PropertyDescription propDescr) {
        for (final ConfigurationBatch tc : configsByPropDescr.get(propDescr)) {
            deleteConfiguration(tc);
        }
        configsByPropDescr.remove(propDescr);
    }
}
