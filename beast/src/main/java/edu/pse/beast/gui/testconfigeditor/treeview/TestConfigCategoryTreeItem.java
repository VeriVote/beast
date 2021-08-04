package edu.pse.beast.gui.testconfigeditor.treeview;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class TestConfigCategoryTreeItem extends TestConfigTreeItemSuper {
    private String category;

    public TestConfigCategoryTreeItem(final String categoryString) {
        super(TestConfigTreeItemType.CATEGORY);
        this.category = categoryString;
    }

    @Override
    public final String toString() {
        return category;
    }

    public final String getCategory() {
        return category;
    }
}
