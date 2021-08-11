package edu.pse.beast.gui.configurationeditor.treeview;

import edu.pse.beast.gui.configurationeditor.configuration.ConfigurationBatch;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ConfigurationTreeItem extends ConfigurationItem {
    private ConfigurationBatch configuration;

    public ConfigurationTreeItem(final ConfigurationBatch config) {
        super(ConfigurationItemType.CONFIG);
        this.configuration = config;
    }

    @Override
    public final String toString() {
        return configuration.getName();
    }

    public final ConfigurationBatch getConfiguration() {
        return configuration;
    }
}
