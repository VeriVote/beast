package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;

public class CheckChildTreeItem extends ChildTreeItem {

    CheckChildTreeItem(final ChildTreeItemValues values,
                       final ParentTreeItem parent,
                       final TreeItem<CustomTreeItem> treeItemReference) {
        super(values, parent, treeItemReference);
    }

    CheckChildTreeItem(final String name, final ParentTreeItem parent,
                       final TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    @Override
    public AnalysisType getAnalysisType() {
        return AnalysisType.Check;
    }
}
