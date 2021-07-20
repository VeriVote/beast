package edu.pse.beast.gui.testconfigeditor.treeview;

public abstract class TestConfigTreeItemSuper {
    private TestConfigTreeItemType type;

    public TestConfigTreeItemSuper(final TestConfigTreeItemType treeItemType) {
        this.type = treeItemType;
    }

    public TestConfigTreeItemType getType() {
        return type;
    }
}
