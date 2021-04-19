package edu.pse.beast.gui.testruneditor;

import java.util.List;
import java.util.Map;

import edu.pse.beast.gui.testruneditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigCBMCTreeItem;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigCategoryTreeItem;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigTreeItem;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.testruneditor.treeview.TestConfigTreeItemType;
import edu.pse.beast.gui.testruneditor.treeview.TestRunCBMCTreeItem;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TestConfigTreeViewHelper {
	public static TreeItem<TestConfigTreeItemSuper> getItem(
			CBMCPropertyTestConfiguration config,
			TreeItem<TestConfigTreeItemSuper> root) {
		return getItemRec(config, root);
	}

	private static TreeItem<TestConfigTreeItemSuper> getItemRec(
			CBMCPropertyTestConfiguration config,
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
