package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.TreeItem;

/**
 * The Class TestChildTreeItem.
 *
 * @author Lukas Stapelbroek
 */
public class TestChildTreeItem extends ChildTreeItem {

    /**
     * Instantiates a new test child tree item.
     *
     * @param name
     *            the name
     * @param parent
     *            the parent
     * @param treeItemReference
     *            the tree item reference
     */
    TestChildTreeItem(final String name, final ParentTreeItem parent,
                      final TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    /**
     * Instantiates a new test child tree item.
     *
     * @param values
     *            the values
     * @param parent
     *            the parent
     * @param treeItemReference
     *            the tree item reference
     */
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
