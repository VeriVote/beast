package edu.pse.beast.gui.testconfigeditor;

import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigCBMCTreeItem;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemType;
import javafx.scene.control.TreeItem;

public class TestConfigTreeViewHelper {
    public static TreeItem<TestConfigTreeItemSuper> getItem(
            CBMCTestConfiguration config,
            TreeItem<TestConfigTreeItemSuper> root) {
        return getItemRec(config, root);
    }

    private static TreeItem<TestConfigTreeItemSuper> getItemRec(
            CBMCTestConfiguration config,
            TreeItem<TestConfigTreeItemSuper> root) {
        if (root.getValue() != null
                && root.getValue().getType() == TestConfigTreeItemType.CBMC) {
            TestConfigCBMCTreeItem casted = (TestConfigCBMCTreeItem) root
                    .getValue();
            if (casted.getCbmcPropertyTestConfiguration().equals(config)) {
                return root;
            }
        }
        for (TreeItem<TestConfigTreeItemSuper> child : root.getChildren()) {
            TreeItem<TestConfigTreeItemSuper> childRes = getItemRec(config,
                    child);
            if (childRes != null) {
                return childRes;
            }
        }
        return null;
    }
}
