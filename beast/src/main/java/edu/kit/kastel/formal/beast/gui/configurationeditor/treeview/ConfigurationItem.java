package edu.kit.kastel.formal.beast.gui.configurationeditor.treeview;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public abstract class ConfigurationItem {
    private ConfigurationItemType type;

    public ConfigurationItem(final ConfigurationItemType treeItemType) {
        this.type = treeItemType;
    }

    public final ConfigurationItemType getType() {
        return type;
    }
}
