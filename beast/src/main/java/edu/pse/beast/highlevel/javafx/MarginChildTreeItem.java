package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;

/**
 * The Class MarginChildTreeItem.
 */
public class MarginChildTreeItem extends ChildTreeItem {

    /**
     * The constructor.
     *
     * @param name
     *            the name
     * @param parent
     *            the parent
     * @param treeItemReference
     *            the tree item reference
     */
    MarginChildTreeItem(final String name, final ParentTreeItem parent,
                        final TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    /**
     * The constructor.
     *
     * @param values
     *            the values
     * @param parent
     *            the parent
     * @param treeItemReference
     *            the tree item reference
     */
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
