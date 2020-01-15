package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

public abstract class CustomTreeItem extends HBox {
    private TreeItem<CustomTreeItem> reference;

    public void setTreeItemReference(final TreeItem<CustomTreeItem> itemReference) {
        this.reference = itemReference;
    }

    public TreeItem<CustomTreeItem> getTreeItemReference() {
        return reference;
    }
}
