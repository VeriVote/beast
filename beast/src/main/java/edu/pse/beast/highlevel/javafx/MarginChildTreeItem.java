package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;

public class MarginChildTreeItem extends ChildTreeItem {

    MarginChildTreeItem(final String name, final ParentTreeItem parent,
                        final TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    public MarginChildTreeItem(final ChildTreeItemValues values,
                               final ParentTreeItem parent,
                               final TreeItem<CustomTreeItem> treeItemReference) {
        super(values, parent, treeItemReference);
    }

    @Override
    public AnalysisType getAnalysisType() {
        return AnalysisType.Margin;
    }
}
