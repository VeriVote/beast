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

	@Override
	public String toString() {
		String template = "V=AMT_VOTER, C=AMT_CAND, S=AMT_SEAT | STATUS";
		return template.replaceAll("STATUS", run.getState().toString())
				.replaceAll("AMT_VOTER", String.valueOf(run.getV()))
				.replaceAll("AMT_CAND", String.valueOf(run.getC()))
				.replaceAll("AMT_SEAT", String.valueOf(run.getS()));
	}
}
