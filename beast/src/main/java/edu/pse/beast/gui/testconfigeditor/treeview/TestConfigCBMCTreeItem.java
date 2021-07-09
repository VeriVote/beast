package edu.pse.beast.gui.testconfigeditor.treeview;

import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

public class TestConfigCBMCTreeItem extends TestConfigTreeItemSuper {
    private CBMCTestConfiguration cbmcPropertyTestConfiguration;

    public TestConfigCBMCTreeItem(
            CBMCTestConfiguration cbmcPropertyTestConfiguration) {
        super(TestConfigTreeItemType.CBMC);
        this.cbmcPropertyTestConfiguration = cbmcPropertyTestConfiguration;
    }

    @Override
    public String toString() {
        return cbmcPropertyTestConfiguration.getName();
    }

    public CBMCTestConfiguration getCbmcPropertyTestConfiguration() {
        return cbmcPropertyTestConfiguration;
    }

}
