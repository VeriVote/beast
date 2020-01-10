package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;

public class CheckChildTreeItem extends ChildTreeItem {

    CheckChildTreeItem(ChildTreeItemValues values,
                       ParentTreeItem parent,
                       TreeItem<CustomTreeItem> treeItemReference) {
        super(values, parent, treeItemReference);
    }

    CheckChildTreeItem(String name, ParentTreeItem parent,
                       TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    @Override
    public AnalysisType getAnalysisType() {
        return AnalysisType.Check;
    }
}