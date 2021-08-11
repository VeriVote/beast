package edu.pse.beast.gui.configurationeditor.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PreAndPostConditions;
import edu.pse.beast.gui.configurationeditor.configuration.cbmc.Configuration;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ConfigurationBatch {
    private static final String TEMPLATE = "CONFIG_NAME: DESCR_NAME + PROP_NAME";
    private static final String CONFIG_NAME = "CONFIG_NAME";
    private static final String DESCR_NAME = "DESCR_NAME";
    private static final String PROP_NAME = "PROP_NAME";

    private CElectionDescription description;
    private PreAndPostConditions propertyDescription;
    private String name;

    private Map<String, Configuration> configsByName =
            new LinkedHashMap<String, Configuration>();

    public ConfigurationBatch(final CElectionDescription descr,
                              final PreAndPostConditions propDescr,
                              final String nameString) {
        this.description = descr;
        this.propertyDescription = propDescr;
        this.name = nameString;
    }

    public final CElectionDescription getDescr() {
        return description;
    }

    public final PreAndPostConditions getPropDescr() {
        return propertyDescription;
    }

    public final String getName() {
        return name;
    }

    public final void addConfiguration(final Configuration config) {
        configsByName.put(config.getName(), config);
    }

    public final Map<String, Configuration> getConfigurationsByName() {
        return Collections.unmodifiableMap(configsByName);
    }

    public final boolean contains(final Configuration config) {
        return configsByName.containsKey(config.getName());
    }

    public final void addCBMCConfigurations(final List<Configuration> configs) {
        for (final Configuration config : configs) {
            addConfiguration(config);
        }
    }

    public final void handleDescrChange() {
        for (final Configuration config : configsByName.values()) {
            config.handleDescrCodeChange();
        }
    }

    public final List<Configuration> getConfigs() {
        final List<Configuration> list = new ArrayList<Configuration>();
        list.addAll(configsByName.values());
        return list;
    }

    @Override
    public final String toString() {
        return TEMPLATE
                .replaceAll(CONFIG_NAME, name)
                .replaceAll(DESCR_NAME, description.getName())
                .replaceAll(PROP_NAME, propertyDescription.getName());
    }

    public final void handlePropDescrChanged() {
        // TODO this can be made smarter by giving the run items a reference to
        // the current propdescr and descr as well as a copy of the state they had
        // when the runs were created. Then, the runs can figure out themselves
        // whether they are out of date
        for (final Configuration config : configsByName.values()) {
            config.handlePropDescrChanged();
        }
    }
}
