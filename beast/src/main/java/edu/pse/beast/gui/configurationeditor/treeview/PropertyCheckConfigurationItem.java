package edu.pse.beast.gui.configurationeditor.treeview;

import edu.pse.beast.gui.configurationeditor.configuration.cbmc.Configuration;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class PropertyCheckConfigurationItem extends ConfigurationItem {
    private Configuration propertyCheckConfiguration;

    public PropertyCheckConfigurationItem(final Configuration propertyCheckConfig) {
        super(ConfigurationItemType.CBMC);
        this.propertyCheckConfiguration = propertyCheckConfig;
    }

    @Override
    public final String toString() {
        return propertyCheckConfiguration.getName();
    }

    public final Configuration getPropertyCheckConfiguration() {
        return propertyCheckConfiguration;
    }
}
