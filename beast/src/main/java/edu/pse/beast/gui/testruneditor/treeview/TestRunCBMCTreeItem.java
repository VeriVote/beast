package edu.pse.beast.gui.testruneditor.treeview;

import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;

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
