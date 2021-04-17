package edu.pse.beast.gui.testruneditor.treeview;

public abstract class TestConfigTreeItemSuper {
	
	private TestConfigTreeItemType type;
	
	public TestConfigTreeItemSuper(TestConfigTreeItemType type) {
		this.type = type;
	}

	public TestConfigTreeItemType getType() {
		return type;
	}

}
