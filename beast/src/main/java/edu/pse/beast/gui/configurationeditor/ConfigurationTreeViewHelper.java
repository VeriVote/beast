package edu.pse.beast.gui.configurationeditor;

import javafx.scene.control.TreeItem;

import edu.pse.beast.gui.configurationeditor.configuration.cbmc.Configuration;
import edu.pse.beast.gui.configurationeditor.treeview.ConfigurationItem;
import edu.pse.beast.gui.configurationeditor.treeview.ConfigurationItemType;
import edu.pse.beast.gui.configurationeditor.treeview.PropertyCheckConfigurationItem;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ConfigurationTreeViewHelper {
    public static TreeItem<ConfigurationItem>
            getItem(final Configuration config,
                    final TreeItem<ConfigurationItem> root) {
        return getItemRec(config, root);
    }

    private static TreeItem<ConfigurationItem>
            getItemRec(final Configuration config,
                       final TreeItem<ConfigurationItem> root) {
        if (root.getValue() != null
                && root.getValue().getType() == ConfigurationItemType.CBMC) {
            final PropertyCheckConfigurationItem casted =
                    (PropertyCheckConfigurationItem) root.getValue();
            if (casted.getPropertyCheckConfiguration().equals(config)) {
                return root;
            }
        }
        for (final TreeItem<ConfigurationItem> child : root.getChildren()) {
            final TreeItem<ConfigurationItem> childRes =
                    getItemRec(config, child);
            if (childRes != null) {
                return childRes;
            }
        }
        return null;
    }
}
