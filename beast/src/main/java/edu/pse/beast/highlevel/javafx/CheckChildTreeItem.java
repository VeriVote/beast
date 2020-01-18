package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;

/**
 * The Class CheckChildTreeItem.
 *
 * @author Lukas Stapelbroek
 */
public final class CheckChildTreeItem extends ChildTreeItem {
    /**
     * Instantiates a new check child tree item.
     *
     * @param values
     *            the values
     * @param parent
     *            the parent
     * @param treeItemReference
     *            the tree item reference
     */
    CheckChildTreeItem(final ChildTreeItemValues values,
                       final ParentTreeItem parent,
                       final TreeItem<CustomTreeItem> treeItemReference) {
        super(values, parent, treeItemReference);
    }

    /**
     * Instantiates a new check child tree item.
     *
     * @param name
     *            the name
     * @param parent
     *            the parent
     * @param treeItemReference
     *            the tree item reference
     */
    CheckChildTreeItem(final String name, final ParentTreeItem parent,
                       final TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    @Override
    public AnalysisType getAnalysisType() {
        return AnalysisType.Check;
    }
}
