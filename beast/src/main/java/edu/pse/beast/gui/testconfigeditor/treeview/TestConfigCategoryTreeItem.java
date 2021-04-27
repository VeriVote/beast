package edu.pse.beast.gui.testconfigeditor.treeview;

public class TestConfigCategoryTreeItem extends TestConfigTreeItemSuper {
	private String category;

	public TestConfigCategoryTreeItem(String category) {
		super(TestConfigTreeItemType.CATEGORY);
		this.category = category;
	}

	@Override
	public String toString() {
		return category;
	}
}
