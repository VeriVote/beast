package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;

public class MarginChildTreeItem extends ChildTreeItem {

    MarginChildTreeItem(String name, ParentTreeItem parent,
                        TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    public MarginChildTreeItem(ChildTreeItemValues values,
                               ParentTreeItem parent,
                               TreeItem<CustomTreeItem> treeItemReference) {
        super(values, parent, treeItemReference);
    }

    @Override
    public AnalysisType getAnalysisType() {
        return AnalysisType.Margin;
    }
}
