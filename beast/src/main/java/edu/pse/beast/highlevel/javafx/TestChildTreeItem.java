package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;

public class TestChildTreeItem extends ChildTreeItem {

    TestChildTreeItem(final String name, final ParentTreeItem parent,
                      final TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    public TestChildTreeItem(final ChildTreeItemValues values,
                             final ParentTreeItem parent,
                             final TreeItem<CustomTreeItem> treeItemReference) {
        super(values, parent, treeItemReference);
    }

    @Override
    public AnalysisType getAnalysisType() {
        return AnalysisType.Test;
    }
}
