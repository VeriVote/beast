package edu.pse.beast.gui.testconfigeditor.treeview;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public abstract class TestConfigTreeItemSuper {
    private TestConfigTreeItemType type;

    public TestConfigTreeItemSuper(final TestConfigTreeItemType treeItemType) {
        this.type = treeItemType;
    }

    public final TestConfigTreeItemType getType() {
        return type;
    }
}
