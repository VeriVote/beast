package edu.pse.beast.gui.testconfigeditor.treeview;

import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;

public class TestRunCBMCTreeItem extends TestConfigTreeItemSuper {
	
	private CBMCTestRun run;
	
	public TestRunCBMCTreeItem(CBMCTestRun tr) {
		super(TestConfigTreeItemType.CBMC_RUN);
		this.run = tr;
	}
	
	public CBMCTestRun getRun() {
		return run;
	}
}
