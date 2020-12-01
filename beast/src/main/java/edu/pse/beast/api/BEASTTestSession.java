package edu.pse.beast.api;

public class BEASTTestSession {
	private BEASTTestRunner beastTestRunner;
	private Thread t;

	public BEASTTestSession(BEASTTestRunner beastTestRunner) {
		this.beastTestRunner = beastTestRunner;
		t = new Thread(beastTestRunner);
		t.start();
	}

	public BEASTTestSession() {
	}
	
}
