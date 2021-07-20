package edu.pse.beast.gui.testconfigeditor;

import javafx.scene.control.TreeItem;

import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigCBMCTreeItem;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemType;

public class TestConfigTreeViewHelper {
    public static TreeItem<TestConfigTreeItemSuper>
            getItem(final CBMCTestConfiguration config,
                    final TreeItem<TestConfigTreeItemSuper> root) {
        return getItemRec(config, root);
    }

    private static TreeItem<TestConfigTreeItemSuper>
            getItemRec(final CBMCTestConfiguration config,
                       final TreeItem<TestConfigTreeItemSuper> root) {
        if (root.getValue() != null
                && root.getValue().getType() == TestConfigTreeItemType.CBMC) {
            final TestConfigCBMCTreeItem casted =
                    (TestConfigCBMCTreeItem) root.getValue();
            if (casted.getCbmcPropertyTestConfiguration().equals(config)) {
                return root;
            }
        }
        for (final TreeItem<TestConfigTreeItemSuper> child : root.getChildren()) {
            final TreeItem<TestConfigTreeItemSuper> childRes =
                    getItemRec(config, child);
            if (childRes != null) {
                return childRes;
            }
        }
        return null;
    }
}
