package edu.pse.beast.gui.testconfigeditor.treeview;

public abstract class TestConfigTreeItemSuper {

    private TestConfigTreeItemType type;

    public TestConfigTreeItemSuper(TestConfigTreeItemType type) {
        this.type = type;
    }

    public TestConfigTreeItemType getType() {
        return type;
    }

}
