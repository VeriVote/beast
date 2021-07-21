package edu.pse.beast.gui.testconfigeditor.treeview;

import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

public class TestConfigCBMCTreeItem extends TestConfigTreeItemSuper {
    private CBMCTestConfiguration cbmcPropertyTestConfiguration;

    public TestConfigCBMCTreeItem(final CBMCTestConfiguration propertyTestConfiguration) {
        super(TestConfigTreeItemType.CBMC);
        this.cbmcPropertyTestConfiguration = propertyTestConfiguration;
    }

    @Override
    public final String toString() {
        return cbmcPropertyTestConfiguration.getName();
    }

    public final CBMCTestConfiguration getCbmcPropertyTestConfiguration() {
        return cbmcPropertyTestConfiguration;
    }
}
