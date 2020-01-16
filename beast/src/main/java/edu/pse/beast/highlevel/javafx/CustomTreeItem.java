package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

/**
 * The Class CustomTreeItem.
 */
public abstract class CustomTreeItem extends HBox {

    /** The reference. */
    private TreeItem<CustomTreeItem> reference;

    /**
     * Sets the tree item reference.
     *
     * @param itemReference the new tree item reference
     */
    public void setTreeItemReference(final TreeItem<CustomTreeItem> itemReference) {
        this.reference = itemReference;
    }

    /**
     * Gets the tree item reference.
     *
     * @return the tree item reference
     */
    public TreeItem<CustomTreeItem> getTreeItemReference() {
        return reference;
    }
}
