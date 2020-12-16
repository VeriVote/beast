package edu.pse.beast.api;

import java.util.List;

import edu.pse.beast.api.testrunner.BEASTAfterTestOp;
import edu.pse.beast.api.testrunner.BEASTResult;
import edu.pse.beast.api.testrunner.BEASTTestRunner;

public class BEASTTestSession {
	private BEASTTestRunner beastTestRunner;

	public BEASTTestSession(BEASTTestRunner beastTestRunner) {
		this.beastTestRunner = beastTestRunner;
		beastTestRunner.start();
	}

	public BEASTTestSession() {
	}
	
	public void doAfter(BEASTAfterTestOp op) {
		beastTestRunner.doAfter(op);
	}
	public List<BEASTResult> getResults() {
		return beastTestRunner.getResults();
	}
	
	public void waitSync() throws InterruptedException {
		while(beastTestRunner.isRunning()) {
			Thread.sleep(200);
		}
	}	
	
}
