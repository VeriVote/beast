package edu.pse.beast.gui.configurationeditor.treeview;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ConfigurationCategoryItem extends ConfigurationItem {
    private String category;

    public ConfigurationCategoryItem(final String categoryString) {
        super(ConfigurationItemType.CATEGORY);
        this.category = categoryString;
    }

    @Override
    public final String toString() {
        return category;
    }

    public final String getCategory() {
        return category;
    }
}
