package edu.pse.beast.gui.testconfigeditor.treeview;

import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;

public class TestConfigTreeItem extends TestConfigTreeItemSuper {
    private TestConfiguration testConfiguration;

    public TestConfigTreeItem(final TestConfiguration testConfig) {
        super(TestConfigTreeItemType.TEST_CONFIG);
        this.testConfiguration = testConfig;
    }

    @Override
    public String toString() {
        return testConfiguration.getName();
    }

    public TestConfiguration getTestConfig() {
        return testConfiguration;
    }
}
