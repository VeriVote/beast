package edu.pse.beast.gui.testconfigeditor.treeview;

import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCPropertyTestConfiguration;

public class TestConfigCBMCTreeItem extends TestConfigTreeItemSuper {
	private CBMCPropertyTestConfiguration cbmcPropertyTestConfiguration;

	public TestConfigCBMCTreeItem(
			CBMCPropertyTestConfiguration cbmcPropertyTestConfiguration) {
		super(TestConfigTreeItemType.CBMC);
		this.cbmcPropertyTestConfiguration = cbmcPropertyTestConfiguration;
	}

	@Override
	public String toString() {
		return cbmcPropertyTestConfiguration.getName();
	}
	
	public CBMCPropertyTestConfiguration getCbmcPropertyTestConfiguration() {
		return cbmcPropertyTestConfiguration;
	}
	
}
