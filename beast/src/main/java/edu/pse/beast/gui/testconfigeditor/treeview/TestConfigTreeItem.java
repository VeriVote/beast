package edu.pse.beast.gui.testconfigeditor.treeview;

import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class TestConfigTreeItem extends TestConfigTreeItemSuper {
    private TestConfiguration testConfiguration;

    public TestConfigTreeItem(final TestConfiguration testConfig) {
        super(TestConfigTreeItemType.TEST_CONFIG);
        this.testConfiguration = testConfig;
    }

    @Override
    public final String toString() {
        return testConfiguration.getName();
    }

    public final TestConfiguration getTestConfig() {
        return testConfiguration;
    }
}
