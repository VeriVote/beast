package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;

public class TestChildTreeItem extends ChildTreeItem {

    TestChildTreeItem(String name, ParentTreeItem parent,
                      TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    public TestChildTreeItem(ChildTreeItemValues values,
                             ParentTreeItem parent,
                             TreeItem<CustomTreeItem> treeItemReference) {
        super(values, parent, treeItemReference);
    }

    @Override
    public AnalysisType getAnalysisType() {
        return AnalysisType.Test;
    }
}