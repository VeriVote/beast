package edu.pse.beast.gui.testconfigeditor.treeview;

import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;

public class TestConfigTreeItem extends TestConfigTreeItemSuper {
    private TestConfiguration testConfig;

    public TestConfigTreeItem(TestConfiguration testConfig) {
        super(TestConfigTreeItemType.TEST_CONFIG);
        this.testConfig = testConfig;
    }

    @Override
    public String toString() {
        return testConfig.getName();
    }

    public TestConfiguration getTestConfig() {
        return testConfig;
    }

}
